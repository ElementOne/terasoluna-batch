/*
 * $Id: FileControlImplTest.java 5576 2007-11-15 13:13:32Z pakucn $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.util.FileControlImpl} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> FileControl�C���^�t�F�[�X����������N���X.
 * <p>
 * @author �g�M���
 * @see jp.terasoluna.fw.file.util.FileControlImpl
 */
public class FileControlImplTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FileControlImplTest.class);
    }

    /**
     * �������������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        VMOUTUtil.initialize();
        // FileUtility�̏������~�߂�
        VMOUTUtil.setReturnValueAtAllTimes(FileUtility.class, "copyFile", null);
        VMOUTUtil.setReturnValueAtAllTimes(FileUtility.class, "deleteFile",
                null);
        VMOUTUtil
                .setReturnValueAtAllTimes(FileUtility.class, "mergeFile", null);
        VMOUTUtil.setReturnValueAtAllTimes(FileUtility.class, "renameFile",
                null);
        VMOUTUtil.setReturnValueAtAllTimes(FileUtility.class,
                "isCheckFileExist", null);
        VMOUTUtil.setReturnValueAtAllTimes(FileUtility.class,
                "setCheckFileExist", null);
    }

    /**
     * �I���������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public FileControlImplTest(String name) {
        super(name);
    }

    /**
     * testCopyFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile01_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile01_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility#copyFile():�Ăяo����邱�Ƃ��m�F����B<br>
     * ����srcFile�AnewFile�̏���FileUtility�̈����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * �R�s�[���ƃR�s�[��̃t�@�C���p�X�ɐ�΃p�X���w�肷��P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCopyFile01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile01_src.txt";
        String newFile = directoryPath + "testCopyFile01_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "copyFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "copyFile", 0);
            assertEquals(srcFile, arguments.get(0));
            assertEquals(newFile, arguments.get(1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testCopyFile02_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testCopyFile02_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility#copyFile():�Ăяo����邱�Ƃ��m�F����B<br>
     * ����srcFile�AnewFile�̏���<br>
     * FileControl.basePath�����ꂼ��̐擪�ɂ���<br>
     * FileUtility�̈����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * �R�s�[���ƃR�s�[��̃t�@�C���p�X�ɑ��΃p�X���w�肷��P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCopyFile02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testCopyFile02_src.txt";
        String newFile = "testCopyFile02_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "copyFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "copyFile", 0);
            assertEquals(basePath + srcFile, arguments.get(0));
            assertEquals(basePath + newFile, arguments.get(1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile03_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility#copyFile():�Ăяo����邱�Ƃ��m�F����B<br>
     * ����srcFile�AnewFile�̏���<br>
     * FileControl.basePath�����ꂼ��̐擪�ɂ����ƂȂ�<br>
     * FileUtility�̈����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * �����̃t�@�C���p�X����΃p�X�ł���A��p�X���ݒ肳��Ă���ꍇ�͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCopyFile03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile03_src.txt";
        String newFile = directoryPath + "testCopyFile03_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "copyFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "copyFile", 0);
            assertEquals(srcFile, arguments.get(0));
            assertEquals(newFile, arguments.get(1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testCopyFile04_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testCopyFile04_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility#copyFile():�Ăяo����邱�Ƃ��m�F����B<br>
     * ����srcFile�AnewFile�̏��ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X�����΃p�X�ł���A��p�X���ݒ肳��Ă��Ȃ��P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCopyFile04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testCopyFile04_src.txt";
        String newFile = "testCopyFile04_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "copyFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "copyFile", 0);
            assertEquals(srcFile, arguments.get(0));
            assertEquals(newFile, arguments.get(1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:null<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile05_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��OFileException����������B<br>
     * ���b�Z�[�W�F"File name is not set."<br>
     * <br>
     * ����srcFile��null���ݒ肳�ꂽ�ꍇ�́AFileException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = null;
        String newFile = directoryPath + "testCopyFile05_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertFalse(VMOUTUtil.isCalled(FileUtility.class, "copyFile"));
            assertEquals(1, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(FileException.class, e.getClass());
            assertEquals("File name is not set.", e.getMessage());
            assertNull(e.getFileName());
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile06_src.txt"<br>
     * (����) newFile:null<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��OFileException����������B<br>
     * ���b�Z�[�W�F"File name is not set."<br>
     * <br>
     * ����newFile��null���ݒ肳�ꂽ�ꍇ�́AFileException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile06_src.txt";
        String newFile = null;

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertFalse(VMOUTUtil.isCalled(FileUtility.class, "copyFile"));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
            assertEquals(FileException.class, e.getClass());
            assertEquals("File name is not set.", e.getMessage());
            assertNull(e.getFileName());
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testCopyFile07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testCopyFile07_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile07_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility#copyFile():�Ăяo�����B<br>
     * ����srcFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * ����newFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * ����������File�̃t�@�C���p�X�����΃p�X�ŁA����newFile�̃t�@�C���p�X����΃p�X�̏ꍇ�AsrcFile�̃t�@�C���p�X�Ɋ�p�X���t�^����newFile�̃t�@�C���p�X�ɂ͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCopyFile07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testCopyFile07_src.txt";
        String newFile = directoryPath + "testCopyFile07_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "copyFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "copyFile", 0);
            assertEquals(basePath + srcFile, arguments.get(0));
            assertEquals(newFile, arguments.get(1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testCopyFile08_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testCopyFile08_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility#copyFile():�Ăяo�����B<br>
     * ����srcFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * ����newFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * ����������File�̃t�@�C���p�X����΃p�X�ŁA����newFile�̃t�@�C���p�X�����΃p�X�̏ꍇ�AsrcFile�̃t�@�C���p�X�Ɋ�p�X���t�^���ꂸnewFile�̃t�@�C���p�X�ɂ͊�p�X���t�^����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCopyFile08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile08_src.txt";
        String newFile = "testCopyFile08_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "copyFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "copyFile", 0);
            assertEquals(srcFile, arguments.get(0));
            assertEquals(basePath + newFile, arguments.get(1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testDeleteFile01_src.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.deleteFile():�Ăяo�����B<br>
     * ����srcFile���n����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X����΃p�X�ł���P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testDeleteFile01_src.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.deleteFile(srcFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "deleteFile"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "deleteFile", 0, 0));
            assertEquals(1, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testDeleteFile02_src.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.deleteFile():�Ăяo�����B<br>
     * ����srcFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X�����΃p�X�ł���P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testDeleteFile02_src.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.deleteFile(srcFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "deleteFile"));
            assertEquals(basePath + srcFile, VMOUTUtil.getArgument(
                    FileUtility.class, "deleteFile", 0, 0));
            assertEquals(1, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testDeleteFile03_src.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.deleteFile():�Ăяo�����B<br>
     * ����srcFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * �����̃t�@�C���p�X����΃p�X�ł���A��p�X���ݒ肳��Ă���ꍇ�͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testDeleteFile03_src.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.deleteFile(srcFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "deleteFile"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "deleteFile", 0, 0));
            assertEquals(1, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testDeleteFile04_src.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.deleteFile():�Ăяo�����B<br>
     * ����srcFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X�����΃p�X�ł���A��p�X���ݒ肳��Ă��Ȃ��P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testDeleteFile04_src.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.deleteFile(srcFile);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "deleteFile"));
            assertEquals(basePath + srcFile, VMOUTUtil.getArgument(
                    FileUtility.class, "deleteFile", 0, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:null<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��OFileException����������B<br>
     * <br>
     * ������null���ݒ肳�ꂽ�ꍇ�́AFileException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String srcFile = null;

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.deleteFile(srcFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertFalse(VMOUTUtil.isCalled(FileUtility.class, "deleteFile"));
            assertEquals(1, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(FileException.class, e.getClass());
            assertEquals("File name is not set.", e.getMessage());
            assertNull(e.getFileName());
        }
    }

    /**
     * testMergeFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD, E, F <br>
     * <br>
     * ���͒l�F(����) fileList:�v�f�������Ȃ�ArrayList�C���X�^���X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testMergeFile01_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����newFile�AfileList���n����邱�Ƃ��m�F����B<br>
     * <br>
     * fileList�̗v�f����ł���P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String newFile = directoryPath + "testMergeFile01_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            assertEquals(fileList, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 1));
            assertEquals(1, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD, E, F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"(�p�X)testMergeFile02-01_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testMergeFile02_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����newFile�AfileList���n����邱�Ƃ��m�F����B<br>
     * <br>
     * fileList�̗v�f��1�ŁA�����t�@�C���̃p�X����΃p�X�ł���P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile02-01_src.txt";
        fileList.add(srcFile1);

        String newFile = directoryPath + "testMergeFile02_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            assertEquals(fileList, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile1, VMOUTUtil.getArgument(
                    fileControl.getClass(), "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile1);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD, E, F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"testMergeFile03-01_src.txt"<br>
     * �v�f2:"testMergeFile03-02_src.txt"<br>
     * �v�f3:"testMergeFile03-03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testMergeFile03_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����newFile�AfileList��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * fileList�̗v�f��3�ŁA�����t�@�C���̃p�X�����΃p�X�ł���P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testMergeFile03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = "testMergeFile03-01_src.txt";
        fileList.add(srcFile1);

        String srcFile2 = "testMergeFile03-02_src.txt";
        fileList.add(srcFile2);

        String srcFile3 = "testMergeFile03-03_src.txt";
        fileList.add(srcFile3);

        String newFile = "testMergeFile03_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + srcFile2);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + srcFile3);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "mergeFile", 0);
            List getFileList = (List) arguments.get(0);
            assertEquals(basePath + srcFile1, getFileList.get(0));
            assertEquals(basePath + srcFile2, getFileList.get(1));
            assertEquals(basePath + srcFile3, getFileList.get(2));
            assertEquals(basePath + newFile, arguments.get(1));

            assertEquals(4, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            String fileName1 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 0, 0);
            assertEquals(srcFile1, fileName1);
            String fileName2 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 1, 0);
            assertEquals(srcFile2, fileName2);
            String fileName3 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 2, 0);
            assertEquals(srcFile3, fileName3);
            String getNewFile = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 3, 0);
            assertEquals(newFile, getNewFile);
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile1);
            file.delete();
            file = new File(directoryPath + srcFile2);
            file.delete();
            file = new File(directoryPath + srcFile3);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD, E, F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"(�p�X)testMergeFile04-01_src.txt"<br>
     * �v�f2:"(�p�X)testMergeFile04-02_src.txt"<br>
     * �v�f3:"(�p�X)testMergeFile04-03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testMergeFile04_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����newFile�AfileList��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * �����̃t�@�C���p�X����΃p�X�ł���A��p�X���ݒ肳��Ă���ꍇ�͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile04-01_src.txt";
        fileList.add(srcFile1);

        String srcFile2 = directoryPath + "testMergeFile04-02_src.txt";
        fileList.add(srcFile2);

        String srcFile3 = directoryPath + "testMergeFile04-03_src.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile04_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(srcFile2);
        file.delete();
        file.createNewFile();

        file = new File(srcFile3);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            assertEquals(fileList, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 1));

            assertEquals(4, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            String fileName1 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 0, 0);
            assertEquals(srcFile1, fileName1);
            String fileName2 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 1, 0);
            assertEquals(srcFile2, fileName2);
            String fileName3 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 2, 0);
            assertEquals(srcFile3, fileName3);
            String getNewFile = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 3, 0);
            assertEquals(newFile, getNewFile);
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile1);
            file.delete();
            file = new File(srcFile2);
            file.delete();
            file = new File(srcFile3);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"testMergeFile05-01_src.txt"<br>
     * �v�f2:"testMergeFile05-02_src.txt"<br>
     * �v�f3:"testMergeFile05-03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testMergeFile05_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����newFile�AfileList��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X�����΃p�X�ł���A��p�X���ݒ肳��Ă��Ȃ��P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = "testMergeFile05-01_src.txt";
        fileList.add(srcFile1);

        String srcFile2 = "testMergeFile05-02_src.txt";
        fileList.add(srcFile2);

        String srcFile3 = "testMergeFile05-03_src.txt";
        fileList.add(srcFile3);

        String newFile = "testMergeFile05_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + srcFile2);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + srcFile3);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            assertEquals(fileList, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "mergeFile", 0, 1));

            assertEquals(4, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            String fileName1 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 0, 0);
            assertEquals(srcFile1, fileName1);
            String fileName2 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 1, 0);
            assertEquals(srcFile2, fileName2);
            String fileName3 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 2, 0);
            assertEquals(srcFile3, fileName3);
            String getNewFile = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 3, 0);
            assertEquals(newFile, getNewFile);
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile1);
            file.delete();
            file = new File(directoryPath + srcFile2);
            file.delete();
            file = new File(directoryPath + srcFile3);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileList:null<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testMergeFile06_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��ONullPointerException����������B<br>
     * <br>
     * ����fileList��null���ݒ肳�ꂽ�ꍇ�́ANullPointerException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = null;
        String newFile = directoryPath + "testMergeFile06_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);
            fail("NullPointerException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(NullPointerException.class, e.getClass());
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"(�p�X)testMergeFile07-01_src.txt"<br>
     * (����) newFile:null<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��OFileException����������B<br>
     * ���b�Z�[�W�F"File name is not set."<br>
     * <br>
     * ����newFile��null���ݒ肳�ꂽ�ꍇ�́AFileException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile07-01_src.txt";
        fileList.add(srcFile1);

        String newFile = null;

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile1);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile1, VMOUTUtil.getArgument(
                    fileControl.getClass(), "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
            assertEquals(FileException.class, e.getClass());
            assertEquals("File name is not set.", e.getMessage());
            assertNull(e.getFileName());
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile1);
            file.delete();
        }
    }

    /**
     * testMergeFile08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"(�p�X)testMergeFile08-01_src.txt"<br>
     * �v�f2:null<br>
     * �v�f3:"(�p�X)testMergeFile08-03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testMergeFile08_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��OFileException����������B<br>
     * ���b�Z�[�W�F"File name is not set."<br>
     * <br>
     * ����fileList�̗v�f��null���܂܂�Ă���ꍇ�́AFileException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile08-01_src.txt";
        fileList.add(srcFile1);

        String srcFile2 = null;
        fileList.add(srcFile2);

        String srcFile3 = directoryPath + "testMergeFile08-03_src.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile08_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(srcFile3);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            String fileName1 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 0, 0);
            assertEquals(srcFile1, fileName1);
            String fileName2 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 1, 0);
            assertEquals(srcFile2, fileName2);

            assertEquals(FileException.class, e.getClass());
            assertEquals("File name is not set.", e.getMessage());
            assertNull(e.getFileName());
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile1);
            file.delete();
            file = new File(srcFile3);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD, E, F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"testMergeFile09-01_src.txt"<br>
     * �v�f2:"testMergeFile09-02_src.txt"<br>
     * �v�f3:"testMergeFile09-03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testMergeFile09_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����fileList��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * ����newFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * ����fileList�̃t�@�C���p�X�����΃p�X�ŁA����newFile�̃t�@�C���p�X����΃p�X�̏ꍇ�B<br>
     * fileList�̃t�@�C���p�X�Ɋ�p�X���t�^����newFile�̃t�@�C���p�X�ɂ͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testMergeFile09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = "testMergeFile09-01_src.txt";
        fileList.add(srcFile1);

        String srcFile2 = "testMergeFile09-02_src.txt";
        fileList.add(srcFile2);

        String srcFile3 = "testMergeFile09-03_src.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile09_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + srcFile2);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + srcFile3);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "mergeFile", 0);
            List getFileList = (List) arguments.get(0);
            assertEquals(basePath + srcFile1, getFileList.get(0));
            assertEquals(basePath + srcFile2, getFileList.get(1));
            assertEquals(basePath + srcFile3, getFileList.get(2));
            assertEquals(newFile, arguments.get(1));

            assertEquals(4, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            String fileName1 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 0, 0);
            assertEquals(srcFile1, fileName1);
            String fileName2 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 1, 0);
            assertEquals(srcFile2, fileName2);
            String fileName3 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 2, 0);
            assertEquals(srcFile3, fileName3);
            String getNewFile = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 3, 0);
            assertEquals(newFile, getNewFile);
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile1);
            file.delete();
            file = new File(directoryPath + srcFile2);
            file.delete();
            file = new File(directoryPath + srcFile3);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD, E, F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"(�p�X)testMergeFile10-01_src.txt"<br>
     * �v�f2:"testMergeFile10-02_src.txt"<br>
     * �v�f3:"(�p�X)testMergeFile10-03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testMergeFile10_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����fileList�̗v�f1�A3��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * ����fileList�̗v�f2��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * ����newFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * ����fileList�̗v�f�Ƀt�@�C���p�X�����΃p�X�Ɛ�΃p�X���܂܂�Ă��āA����newFile�̃t�@�C���p�X����΃p�X�̏ꍇ�B<br>
     * fileList�̗v�f2�̃t�@�C���p�X�Ɋ�p�X���t�^���ꂻ�̑��̗v�f�ɂ͕t�^���ꂸ�AnewFile�̃t�@�C���p�X�ɂ͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testMergeFile10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile10-01_src.txt";
        fileList.add(srcFile1);

        String srcFile2 = "testMergeFile10-02_src.txt";
        fileList.add(srcFile2);

        String srcFile3 = directoryPath + "testMergeFile10-03_src.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile10_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + srcFile2);
        file.delete();
        file.createNewFile();

        file = new File(srcFile3);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "mergeFile", 0);
            List getFileList = (List) arguments.get(0);
            assertEquals(srcFile1, getFileList.get(0));
            assertEquals(basePath + srcFile2, getFileList.get(1));
            assertEquals(srcFile3, getFileList.get(2));
            assertEquals(newFile, arguments.get(1));

            assertEquals(4, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            String fileName1 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 0, 0);
            assertEquals(srcFile1, fileName1);
            String fileName2 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 1, 0);
            assertEquals(srcFile2, fileName2);
            String fileName3 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 2, 0);
            assertEquals(srcFile3, fileName3);
            String getNewFile = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 3, 0);
            assertEquals(newFile, getNewFile);
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile1);
            file.delete();
            file = new File(directoryPath + srcFile2);
            file.delete();
            file = new File(srcFile3);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD, E, F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������ArrayList�C���X�^���X<br>
     * �v�f1:"(�p�X)testMergeFile11-01_src.txt"<br>
     * �v�f2:"(�p�X)testMergeFile11-02_src.txt"<br>
     * �v�f3:"(�p�X)testMergeFile11-03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testMergeFile11_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.mergeFile():1��Ăяo�����B<br>
     * ����fileList��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * ����newFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * ����fileList�̃t�@�C���p�X����΃p�X�ŁA����newFile�̃t�@�C���p�X�����΃p�X�̏ꍇ�B<br>
     * fileList�̃t�@�C���p�X�Ɋ�p�X���t�^���ꂸnewFile�̃t�@�C���p�X�ɂ͊�p�X���t�^����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testMergeFile11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile11-01_src.txt";
        fileList.add(srcFile1);

        String srcFile2 = directoryPath + "testMergeFile11-02_src.txt";
        fileList.add(srcFile2);

        String srcFile3 = directoryPath + "testMergeFile11-03_src.txt";
        fileList.add(srcFile3);

        String newFile = "testMergeFile11_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile1);
        file.delete();
        file.createNewFile();

        file = new File(srcFile2);
        file.delete();
        file.createNewFile();

        file = new File(srcFile3);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "mergeFile"));
            List arguments = VMOUTUtil.getArguments(FileUtility.class,
                    "mergeFile", 0);
            List getFileList = (List) arguments.get(0);
            assertEquals(srcFile1, getFileList.get(0));
            assertEquals(srcFile2, getFileList.get(1));
            assertEquals(srcFile3, getFileList.get(2));
            assertEquals(basePath + newFile, arguments.get(1));

            assertEquals(4, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            String fileName1 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 0, 0);
            assertEquals(srcFile1, fileName1);
            String fileName2 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 1, 0);
            assertEquals(srcFile2, fileName2);
            String fileName3 = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 2, 0);
            assertEquals(srcFile3, fileName3);
            String getNewFile = (String) VMOUTUtil.getArgument(fileControl
                    .getClass(), "getAbsolutePath", 3, 0);
            assertEquals(newFile, getNewFile);
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile1);
            file.delete();
            file = new File(srcFile2);
            file.delete();
            file = new File(srcFile3);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile01_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile01_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.renameFile():�Ăяo�����B<br>
     * ����srcFile�AnewFile���n����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X����΃p�X�ł���P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile01_src.txt";
        String newFile = directoryPath + "testRenameFile01_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "renameFile"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testRenameFile02_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testRenameFile02_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.renameFile():�Ăяo�����B<br>
     * ����srcFile�AnewFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X�����΃p�X�ł���P�[�X�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testRenameFile02_src.txt";
        String newFile = "testRenameFile02_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "renameFile"));
            assertEquals(basePath + srcFile, VMOUTUtil.getArgument(
                    FileUtility.class, "renameFile", 0, 0));
            assertEquals(basePath + newFile, VMOUTUtil.getArgument(
                    FileUtility.class, "renameFile", 0, 1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile03_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile03_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.renameFile():�Ăяo�����B<br>
     * ����srcFile�AnewFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * �����̃t�@�C���p�X����΃p�X�ł���A��p�X���ݒ肳��Ă���ꍇ�͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile03_src.txt";
        String newFile = directoryPath + "testRenameFile03_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "renameFile"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testRenameFile04_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testRenameFile04_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.renameFile():�Ăяo�����B<br>
     * ����srcFile�AnewFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���̃p�X�����΃p�X�ł���A��p�X���ݒ肳��Ă��Ȃ��P�[�X�B<br>
     * ��OFileException���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testRenameFile04_src.txt";
        String newFile = "testRenameFile04_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "renameFile"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:null<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile05_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��OFileException����������B<br>
     * ���b�Z�[�W�F"File name is not set."<br>
     * <br>
     * ����srcFile��null���ݒ肳�ꂽ�ꍇ�́AFileException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = null;
        String newFile = directoryPath + "testRenameFile05_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertFalse(VMOUTUtil.isCalled(FileUtility.class, "renameFile"));
            assertEquals(FileException.class, e.getClass());
            assertEquals("File name is not set.", e.getMessage());
            assertNull(e.getFileName());
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile06_src.txt"<br>
     * (����) newFile:null<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��OFileException����������B<br>
     * ���b�Z�[�W�F"File name is not set."<br>
     * <br>
     * ����newFile��null���ݒ肳�ꂽ�ꍇ�́AFileException���X���[����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile06_src.txt";
        String newFile = null;

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = "";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
            assertFalse(VMOUTUtil.isCalled(FileUtility.class, "renameFile"));
            assertEquals(FileException.class, e.getClass());
            assertEquals("File name is not set.", e.getMessage());
            assertNull(e.getFileName());
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testRenameFile07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testRenameFile07_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile07_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.renameFile():�Ăяo�����B<br>
     * ����srcFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * ����newFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * <br>
     * ����������File�̃t�@�C���p�X�����΃p�X�ŁA����newFile�̃t�@�C���p�X����΃p�X�̏ꍇ�AsrcFile�̃t�@�C���p�X�Ɋ�p�X���t�^����newFile�̃t�@�C���p�X�ɂ͊�p�X���t�^����Ȃ����ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testRenameFile07_src.txt";
        String newFile = directoryPath + "testRenameFile07_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(directoryPath + srcFile);
        file.delete();
        file.createNewFile();

        file = new File(newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "renameFile"));
            assertEquals(basePath + srcFile, VMOUTUtil.getArgument(
                    FileUtility.class, "renameFile", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)testRenameFile08_src.txt"<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testRenameFile08_new.txt"<br>
     * (���) FileControl.basePath:String�C���X�^���X<br>
     * "(�p�X)"<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileUtility.renameFile():�Ăяo�����B<br>
     * ����srcFile��FileControl.basePath��擪�ɂ����ɓn����邱�Ƃ��m�F����B<br>
     * ����newFile��FileControl.basePath��擪�ɂ��ēn����邱�Ƃ��m�F����B<br>
     * <br>
     * ����������File�̃t�@�C���p�X����΃p�X�ŁA����newFile�̃t�@�C���p�X�����΃p�X�̏ꍇ�AsrcFile�̃t�@�C���p�X�Ɋ�p�X���t�^���ꂸnewFile�̃t�@�C���p�X�ɂ͊�p�X���t�^����邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile08_src.txt";
        String newFile = "testRenameFile08_new.txt";

        // �e�X�g�O�A�Ώۃt�@�C����������
        File file = new File(srcFile);
        file.delete();
        file.createNewFile();

        file = new File(directoryPath + newFile);
        file.delete();
        file.createNewFile();

        // �O������̐ݒ�
        String basePath = directoryPath;
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        try {
            // �e�X�g���{
            fileControl.renameFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "renameFile"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "renameFile", 0, 0));
            assertEquals(basePath + newFile, VMOUTUtil.getArgument(
                    FileUtility.class, "renameFile", 0, 1));
            assertEquals(2, VMOUTUtil.getCallCount(fileControl.getClass(),
                    "getAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(fileControl.getClass(),
                    "getAbsolutePath", 1, 0));
        } finally {
            // �e�X�g��A�Ώۃt�@�C����������
            file = new File(srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testGetBasePath01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) basePath:String�C���X�^���X<br>
     * "aaa"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:�O�������basePath�Ɠ���<br>
     * String�C���X�^���X<br>
     * <br>
     * �������擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBasePath01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����Ȃ�

        // �O������̐ݒ�
        String basePath = "aaa";
        UTUtil.setPrivateField(fileControl, "basePath", basePath);

        // �e�X�g���{
        String getBasePath = fileControl.getBasePath();

        // �ԋp�l�̊m�F
        assertEquals(basePath, getBasePath);

        // ��ԕω��Ȃ�
    }

    /**
     * testSetBasePath01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) basePath:String�C���X�^���X<br>
     * "aaa"<br>
     * (���) basePath:""<br>
     * <br>
     * ���Ғl�F(��ԕω�) basePath:������basePath�Ɠ���<br>
     * String�C���X�^���X<br>
     * <br>
     * �����������������ɐݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBasePath01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        String basePath = "aaa";

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileControl, "basePath", "");

        // �e�X�g���{
        fileControl.setBasePath(basePath);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(basePath, UTUtil.getPrivateField(fileControl, "basePath"));
    }

    /**
     * testSetCheckFileExist01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) checkFileExist:true<br>
     * <br>
     * ���Ғl�F(��ԕω�) setCheckFileExist:�Ăяo�����B<br>
     * ����checkFileExist���n����邱�Ƃ��m�F����B<br>
     * <br>
     * ������true <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCheckFileExist01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        boolean checkFileExist = true;

        // �O������Ȃ�

        // �e�X�g���{
        fileControl.setCheckFileExist(checkFileExist);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                "setCheckFileExist"));
        assertEquals(checkFileExist, VMOUTUtil.getArgument(FileUtility.class,
                "setCheckFileExist", 0, 0));
    }

    /**
     * testSetCheckFileExist02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) checkFileExist:false<br>
     * <br>
     * ���Ғl�F(��ԕω�) setCheckFileExist:�Ăяo�����B<br>
     * ����checkFileExist���n����邱�Ƃ��m�F����B<br>
     * <br>
     * ������false <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCheckFileExist02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileControlImpl fileControl = new FileControlImpl();

        // �����̐ݒ�
        boolean checkFileExist = false;

        // �O������Ȃ�

        // �e�X�g���{
        fileControl.setCheckFileExist(checkFileExist);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                "setCheckFileExist"));
        assertEquals(checkFileExist, VMOUTUtil.getArgument(FileUtility.class,
                "setCheckFileExist", 0, 0));
    }
}

/*
 * $Id: FileUtilityTest.java 5576 2007-11-15 13:13:32Z pakucn $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.util.FileUtility} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C������@�\����������N���X.
 * <p>
 * @author �g�M���
 * @author ���O
 * @see jp.terasoluna.fw.file.util.FileUtility
 */
public class FileUtilityTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FileUtilityTest.class);
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
    }

    /**
     * �I���������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        // FileUtility��static�t�B�[���h���f�t�H���g�l�ɏ���������B
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);
    }

    /**
     * �R���X�g���N�^�B
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public FileUtilityTest(String name) {
        super(name);
    }

    /*
     * testCopyFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile01_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile01_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:false<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile01_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�R�s�[����srcFile�Ŏw�肵���t�@�C���Ɠ���̓��e�ł��邱�Ƃ��m�F����B<br>
     * <br>
     * ����P�[�X<br>
     * �R�s�[��̃t�@�C�������݂��Ȃ��ꍇ�A�������t�@�C���R�s�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    // This testcase is ignored, because of Windows environment dependency.
    public void _ignore_testCopyFile01() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile01_src.txt";
        String newFile = directoryPath + "testCopyFile01_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();

        FileWriter testSrcFileFileWriter = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile01_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File getFile = new File(newFile);
            UTUtil.assertEqualsFile(testSrcFile, getFile);
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }

            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile02_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile02_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile02_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile02_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�R�s�[����srcFile�Ŏw�肵���t�@�C���Ɠ���̓��e�ł��邱�Ƃ��m�F����B<br>
     * <br>
     * ����P�[�X<br>
     * �icheckFileExist��TRUE�j<br>
     * �R�s�[��̃t�@�C�������݂���ꍇ�A�������t�@�C���R�s�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile02() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile02_src.txt";
        String newFile = directoryPath + "testCopyFile02_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile02_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile02_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File getFile = new File(newFile);
            UTUtil.assertEqualsFile(testSrcFile, getFile);
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile03_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile03_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:false<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile03_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ύX�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testCopyFile03_src.txt is not exist."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �R�s�[���̃t�@�C�����Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile03() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile03_src.txt";
        String newFile = directoryPath + "testCopyFile03_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile03_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(srcFile + " is not exist.", e.getMessage());
            assertEquals(srcFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(newFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile03_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile04_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile04_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:false<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile04_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile04_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ύX�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testCopyFile04_new.txt is exist."<br>
     * �E�t�@�C�����FnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �icheckFileExist��FALSE�j<br>
     * �R�s�[��̃t�@�C�������݂���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile04() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile04_src.txt";
        String newFile = directoryPath + "testCopyFile04_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile04_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile04_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(newFile + " is exist.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(newFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile04_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile05_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile05_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile05_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile05_new.txt�f�[�^<br>
     * (���) FileOutputStream#<init>():FileNotFoundException����<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�t�@�C�������݂��Ȃ��B<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * �E������O�FFileNotFoundException<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�@�C���̑��݃`�F�b�N��̃^�C�~���O�ŃR�s�[��̃t�@�C�����폜���ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile05() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile05_src.txt";
        String newFile = directoryPath + "testCopyFile05_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist",
                Boolean.TRUE);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileNotFoundException fileNotFoundException = new FileNotFoundException(
                "testCopyFile05��O");
        VMOUTUtil.setExceptionAtAllTimes(FileOutputStream.class, "<init>",
                fileNotFoundException);

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile05_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile05_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertSame(fileNotFoundException, e.getCause());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File file = new File(newFile);
            assertFalse(file.exists());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile06_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile06_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile06_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile06_new.txt�f�[�^<br>
     * (���) FileChannel#position():IOException����<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�t�@�C�������݂��邪�A���e���Ȃ��B<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * �E������O�FIOException<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�@�C���̃R�s�[�����r����IOException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile06() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile06_src.txt";
        String newFile = directoryPath + "testCopyFile06_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist",
                Boolean.TRUE);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        IOException exception = new IOException("testCopyFile06��O");
        VMOUTUtil.setExceptionAtAllTimes(FileChannel.class, "position",
                exception);

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile06_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile06_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertSame(exception, e.getCause());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(newFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile07_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile07_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile07_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile07_new.txt�f�[�^<br>
     * (���) FileOutputStream#close():IOException����<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�R�s�[����srcFile�Ŏw�肵���t�@�C���Ɠ���̓��e�ł��邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�@�C���̃R�s�[���������ナ�\�[�X�̊J��������IOException�����������ꍇ�A���̂܂܏����𑱂�����I������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile07() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile07_src.txt";
        String newFile = directoryPath + "testCopyFile07_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist",
                Boolean.TRUE);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        IOException exception = new IOException("testCopyFile07��O");
        VMOUTUtil.setExceptionAtAllTimes(FileOutputStream.class, "close",
                exception);

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile07_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile07_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File getFile = new File(newFile);
            UTUtil.assertEqualsFile(testSrcFile, getFile);
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:null<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile08_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile08_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ăяo�����B<br>
     * �����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ύX�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not set."<br>
     * �E�t�@�C�����Fnull<br>
     * <br>
     * �ُ�P�[�X<br>
     * �R�s�[���̃t�@�C������null�œ��͂��ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile08() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = null;
        String newFile = directoryPath + "testCopyFile08_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist",
                Boolean.TRUE);

        // �e�X�g�Ώۃt�@�C��������������B
        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        IOException exception = new IOException("TEST");
        VMOUTUtil.setExceptionAtAllTimes(FileLock.class, "release", exception);

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile08_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not set.", e.getMessage());
            assertNull(e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(newFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile08_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile09_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testCopyFile09_new.txt"<br>
     * <br>
     * �����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile09_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile09_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ύX�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not absolute."<br>
     * �E�t�@�C�����FnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �R�s�[��̃t�@�C���������΃p�X�œ��͂��ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile09() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile09_src.txt";
        String newFile = "testCopyFile09_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist",
                Boolean.TRUE);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(directoryPath + newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile09_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile09_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not absolute.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(directoryPath + newFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile09_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testCopyFile10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile10_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:null<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile10_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ύX�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not set."<br>
     * �E�t�@�C�����Fnull<br>
     * <br>
     * �ُ�P�[�X<br>
     * �R�s�[��̃t�@�C������null�œ��͂��ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile10() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile10_src.txt";
        String newFile = null;

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist",
                Boolean.TRUE);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile10_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not set.", e.getMessage());
            assertNull(e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testCopyFile11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testCopyFile11_src.txt"<br>
     * <br>
     * �����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile11_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile11_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ăяo�����B<br>
     * �����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ύX�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not absolute."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �R�s�[���̃t�@�C���������΃p�X�œ��͂��ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCopyFile11() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testCopyFile10_src.txt";
        String newFile = directoryPath + "testCopyFile10_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist",
                Boolean.TRUE);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(directoryPath + srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile11_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile11_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not absolute.", e.getMessage());
            assertEquals(srcFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(newFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile11_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /*
     * testCopyFile12() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile12_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testCopyFile02_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile02_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �EtestCopyFile02_new.txt�f�[�^<br>
     * <br>
     * �����b�N���|�����Ă���B<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F�����Ƃ��āA����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F�����Ƃ��āA����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ύX�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * �E�t�@�C�����FnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �icheckFileExist��TRUE�j<br>
     * �R�s�[��̃t�@�C�������݂��邪���b�N����Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    // This testcase is ignored, because of Windows environment dependency.
    public void _ignore_testCopyFile12() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testCopyFile12_src.txt";
        String newFile = directoryPath + "testCopyFile12_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        FileInputStream fis = null;
        FileLock lock = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile02_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testCopyFile02_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            fis = new FileInputStream(testNewFile);
            lock = fis.getChannel().lock(0L, Long.MAX_VALUE, true);

            // �e�X�g���{
            FileUtility.copyFile(srcFile, newFile);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �R�s�[��̃t�@�C�����e�m�F
            lock.release();
            // �t�@�C�����̕ύX���m�F
            lock.release();
            File getFile = new File(newFile);
            assertTrue(getFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(getFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile02_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
            postReader.close();

        } finally {
            if (lock != null) {
                lock.release();
            }
            if (fis != null) {
                fis.close();
            }
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testDeleteFile01_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile01_src.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * <br>
     * ����P�[�X<br>
     * �Ώۃt�@�C�����������폜����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile01() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testDeleteFile01_src.txt";

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile01_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            // �e�X�g���{
            FileUtility.deleteFile(srcFile);

            // ����
            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(srcFile);
            assertFalse(resultFile.exists());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            File file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testDeleteFile02_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) srcFile�Ŏw�肵���t�@�C��:�ω��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testDeleteFile02_src.txt  is not exist."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �Ώۃt�@�C�������݂��Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile02() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testDeleteFile01_src.txt";

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();

        try {
            FileUtility.deleteFile(srcFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(srcFile + " is not exist.", e.getMessage());
            assertEquals(srcFile, e.getFileName());
        } finally {
            File file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testDeleteFile03_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile03_src.txt�f�[�^<br>
     * (���) File#delete():false<br>
     * <br>
     * ���Ғl�F(��ԕω�) srcFile�Ŏw�肵���t�@�C��:�ω��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �Ώۃt�@�C���̍폜���t�@�C�����b�N�Ȃǂɂ���Ď��s�����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile03() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testDeleteFile03_src.txt";

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        VMOUTUtil.setReturnValueAtAllTimes(File.class, "delete", false);

        FileWriter testSrcFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile03_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            // �e�X�g���{
            FileUtility.deleteFile(srcFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertEquals(srcFile, e.getFileName());

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(srcFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile03_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            File file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testDeleteFile04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:null<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) srcFile�Ŏw�肵���t�@�C��:�ω��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not set."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �Ώۃt�@�C���̃p�X��null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile04() throws Exception {
        // �����̐ݒ�
        String srcFile = null;

        try {
            FileUtility.deleteFile(srcFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not set.", e.getMessage());
            assertNull(e.getFileName());
        }
    }

    /**
     * testDeleteFile05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testDeleteFile05_src.txt"<br>
     * <br>
     * �����΃p�X<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestCopyFile05_src.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) srcFile�Ŏw�肵���t�@�C��:�ω��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not absolute."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �Ώۃt�@�C���̃p�X�����΃p�X�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteFile05() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testDeleteFile05_src.txt";

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(directoryPath + srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testCopyFile05_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            // �e�X�g���{
            FileUtility.deleteFile(srcFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not absolute.", e.getMessage());
            assertEquals(srcFile, e.getFileName());

            // �R�s�[��̃t�@�C�����e�m�F
            File resultFile = new File(directoryPath + srcFile);
            assertTrue(resultFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(resultFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testCopyFile05_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            File file = new File(directoryPath + srcFile);
            file.delete();
        }
    }

    /**
     * testMergeFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,D,F <br>
     * <br>
     * ���͒l�F(����) fileList:�v�f�������Ȃ�List�C���X�^���X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile01_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:false<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * ����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:��̃t�@�C��<br>
     * <br>
     * ����P�[�X<br>
     * �����Ώۃt�@�C�����X�g����̏ꍇ�A��t�@�C������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile01() throws Exception {
        // �����̐ݒ�
        List<String> fileList = new ArrayList<String>();

        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String newFile = directoryPath + "testMergeFile01_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testNewFile = new File(newFile);
        testNewFile.delete();

        BufferedReader postReader = null;
        try {
            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(newFile)));
            assertFalse(postReader.ready());
        } finally {
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(newFile);
            file.delete();
        }

    }

    /**
     * testMergeFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,D,F <br>
     * <br>
     * ���͒l�F(����) fileList:�v�f�������Ȃ�List�C���X�^���X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile02_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile02_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * ����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:��̃t�@�C��<br>
     * <br>
     * ����P�[�X<br>
     * (checkFileExist�ݒ�FTRUE)<br>
     * �w�肳�ꂽ���ʃt�@�C�������ɑ��݂���ꍇ�A���Ȃ����ʃt�@�C������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile02() throws Exception {
        // �����̐ݒ�
        List<String> fileList = new ArrayList<String>();

        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String newFile = directoryPath + "testMergeFile02_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile02_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�v�f�������Ȃ�List�C���X�^���X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile03_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:false<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile03_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * ����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ω��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testMergeFile03_new.txt is exist."<br>
     * �E�t�@�C�����FnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * (checkFileExist�ݒ�FFALSE)<br>
     * �w�肳�ꂽ���ʃt�@�C�������ɑ��݂���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile03() throws Exception {
        // �����̐ݒ�
        List<String> fileList = new ArrayList<String>();

        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String newFile = directoryPath + "testMergeFile03_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile03_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(newFile + " is exist.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile03_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(newFile);
            file.delete();
        }

    }

    /**
     * testMergeFile04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,D,F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile04_src1.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile04_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile04_src1.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile04_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile04_src1.txt�f�[�^<br>
     * <br>
     * ����P�[�X<br>
     * �����Ώۃt�@�C�����X�g�ɑ��݂���t�@�C���̃p�X����ݒ肳��Ă���ꍇ�A���̈�̃t�@�C���Ɠ������e�̃t�@�C������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile04() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile04_src1.txt";
        fileList.add(srcFile1);

        String newFile = directoryPath + "testMergeFile04_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile04_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile04_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            UTUtil.assertEqualsFile(testSrcFile1, mergeFile);
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,D,F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile05_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile05_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile05_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile05_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile05_src1.txt�f�[�^<br>
     * �EtestMergeFile05_src2.txt�f�[�^<br>
     * �EtestMergeFile05_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile05_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():4��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * 3��ڂ̌Ăяo���F����fileList�̗v�f2�̃t�@�C���p�X���n����邱�ƁB<br>
     * 4��ڂ̌Ăяo���F����fileList�̗v�f3�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�����Ă���B<br>
     * �EtestMergeFile05_src1.txt�f�[�^testMergeFile05_src2.txt�f�[�^testMergeFile05_src3.txt�f�[�^<br>
     * <br>
     * ����P�[�X<br>
     * �����Ώۃt�@�C�����X�g�ɑ��݂���t�@�C���̃p�X�������ݒ肳��Ă���ꍇ�A���̑S�t�@�C���̓��e�����ԂɌ�������Č��ʃt�@�C���Ƃ��Đ�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile05() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile05_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile05_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile05_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile05_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile05_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile05_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile05_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile05_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(4, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));
            assertEquals(srcFile2, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 2, 0));
            assertEquals(srcFile3, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 3, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile05_src1.txt�f�[�^"
                    + "testMergeFile05_src2.txt�f�[�^testMergeFile05_src3.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
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
     * testMergeFile06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile06_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile06_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile06_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile06_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile06_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:��̃t�@�C��<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testMergeFile06_src1.txt is exist."<br>
     * �E�t�@�C�����F"(�p�X)/testMergeFile06_src1.txt"<br>
     * <br>
     * �ُ�P�[�X<br>
     * �����Ώۃt�@�C�����X�g�ɑ��݂��Ȃ��t�@�C���̃p�X�������ݒ肳��Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile06() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile06_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile06_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile06_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile06_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile06_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̔���
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(srcFile1 + " is not exist.", e.getMessage());
            assertEquals(srcFile1, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
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
     * testMergeFile07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile07_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile07_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile07_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile07_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:"(�p�X)/testMergeFile07_src3.txt"�̂ݑ��݂��Ȃ�<br>
     * <br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile07_src1.txt�f�[�^<br>
     * �EtestMergeFile07_src2.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile07_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():4��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * 3��ڂ̌Ăяo���F����fileList�̗v�f2�̃t�@�C���p�X���n����邱�ƁB<br>
     * 4��ڂ̌Ăяo���F����fileList�̗v�f3�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile07_src1.txt�f�[�^testMergeFile07_src2.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testMergeFile07_src3.txt is not exist."<br>
     * �E�t�@�C�����F"(�p�X)/testMergeFile07_src3.txt"<br>
     * <br>
     * �ُ�P�[�X<br>
     * �����Ώۃt�@�C�����X�g�Ɉꕔ���݂��Ȃ��t�@�C���̃p�X���ݒ肳��Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile07() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile07_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile07_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile07_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile07_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile07_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile07_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile07_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(srcFile3 + " is not exist.", e.getMessage());
            assertEquals(srcFile3, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(4, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));
            assertEquals(srcFile2, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 2, 0));
            assertEquals(srcFile3, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 3, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile07_src1.txt�f�[�^"
                    + "testMergeFile07_src2.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
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
     * testMergeFile08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile08_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile08_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile08_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile08_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile08_src1.txt�f�[�^<br>
     * �EtestMergeFile08_src2.txt�f�[�^<br>
     * �EtestMergeFile08_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile08_new.txt�f�[�^<br>
     * (���) inputFileChannel#position():IOException����<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:��̃t�@�C��<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * �E������O�FFileOutputStream#close()�Ŕ�������IOException<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�@�C��������IOException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile08() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile08_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile08_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile08_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile08_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        IOException ioException = new IOException("testMergeFile08��O");
        VMOUTUtil.setExceptionAtAllTimes(FileChannel.class, "position",
                ioException);

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile08_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile08_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile08_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile08_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertSame(ioException, e.getCause());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
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
     * testMergeFile09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile09_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile09_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile09_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile09_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile09_src1.txt�f�[�^<br>
     * �EtestMergeFile09_src2.txt�f�[�^<br>
     * �EtestMergeFile09_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile09_new.txt�f�[�^<br>
     * (���) FileOutputStream#close():IOException����<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():4��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * 3��ڂ̌Ăяo���F����fileList�̗v�f2�̃t�@�C���p�X���n����邱�ƁB<br>
     * 4��ڂ̌Ăяo���F����fileList�̗v�f3�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile09_src1.txt�f�[�^testMergeFile09_src2.txt�f�[�^testMergeFile09_src3.txt�f�[�^<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�@�C���������������p���ă��\�[�X�̊J���Ɏ��s�����ꍇ�A��O���������Ȃ����Ƃ��m�F����B<br>
     * �܂��A���ʃt�@�C������������������Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile09() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile09_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile09_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile09_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile09_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        IOException ioException = new IOException("testMergeFile09��O");
        VMOUTUtil.setExceptionAtAllTimes(FileOutputStream.class, "close",
                ioException);

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile09_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile09_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile09_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile09_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);

            // ��ԕω��̊m�F
            assertEquals(4, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));
            assertEquals(srcFile2, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 2, 0));
            assertEquals(srcFile3, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 3, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile09_src1.txt�f�[�^"
                    + "testMergeFile09_src2.txt�f�[�^testMergeFile09_src3.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());

        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
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
     * testMergeFile10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile10_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile10_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile10_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile10_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile10_src1.txt�f�[�^<br>
     * �EtestMergeFile10_src2.txt�f�[�^<br>
     * �EtestMergeFile10_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile10_new.txt�f�[�^<br>
     * (���) FileOutputStream.<init>:FileNotFountException����<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�t�@�C�������݂��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * �E������O�FFileOutputStream.<init>�Ŕ�������FileNotFoundException<br>
     * <br>
     * �ُ�P�[�X<br>
     * �����r���Ƀt�@�C�����폜����FileNotFoundException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile10() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile10_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile10_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile10_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile10_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileNotFoundException fileNotFoundException = new FileNotFoundException(
                "testMergeFile10��O");
        VMOUTUtil.setExceptionAtAllTimes(FileOutputStream.class, "<init>",
                fileNotFoundException);

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile10_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile10_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile10_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile10_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertSame(fileNotFoundException, e.getCause());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertFalse(mergeFile.exists());
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
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
     * testMergeFile11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:null<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile11_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile11_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:��̃t�@�C��<br>
     * (��ԕω�) ��O:NullPointerException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �����Ώۃt�@�C�����X�g��null�̏ꍇ�ANullPointerException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile11() throws Exception {
        // �����̐ݒ�
        List<String> fileList = null;

        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String newFile = directoryPath + "testMergeFile11_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile11_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("NullPointerException���������܂���ł����B���s�ł��B");
        } catch (NullPointerException e) {
            // ��O�̊m�F
            assertTrue(NullPointerException.class
                    .isAssignableFrom(e.getClass()));

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile12() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile12_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile12_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile12_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:null<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile12_src1.txt�f�[�^<br>
     * �EtestMergeFile12_src2.txt�f�[�^<br>
     * �EtestMergeFile12_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * ����newFile���n����邱�ƁB<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not set."<br>
     * �E�t�@�C�����Fnull<br>
     * <br>
     * �ُ�P�[�X<br>
     * ���ʃt�@�C���̃p�X��null�̏ꍇ�A��O���o�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile12() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile12_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile12_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile12_src3.txt";
        fileList.add(srcFile3);

        String newFile = null;

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile12_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile12_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile12_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not set.", e.getMessage());
            assertNull(e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
            file.delete();
            file = new File(srcFile2);
            file.delete();
            file = new File(srcFile3);
            file.delete();
        }
    }

    /**
     * testMergeFile13() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile13_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile13_src2.txt"<br>
     * 3�D"(�p�X)/testMergeFile13_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testMergeFile13_new.txt"<br>
     * <br>
     * �����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile13_src1.txt�f�[�^<br>
     * �EtestMergeFile13_src2.txt�f�[�^<br>
     * �EtestMergeFile13_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile13_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * ����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ω��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not absolute."<br>
     * �E�t�@�C�����FnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * ���ʃt�@�C���̃p�X�����΃p�X�̏ꍇ�A��O���o�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile13() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile13_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile13_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile13_src3.txt";
        fileList.add(srcFile3);

        String newFile = "testMergeFile13_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        File testNewFile = new File(directoryPath + newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile13_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile13_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile13_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile13_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not absolute.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(directoryPath + newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile13_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
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
     * testMergeFile14() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile14_src1.txt"<br>
     * 2�Dnull<br>
     * 3�D"(�p�X)/testMergeFile14_src3.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile14_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:2�Ԗڂ̂ݑ��݂��Ȃ�<br>
     * <br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile14_src1.txt�f�[�^<br>
     * �EtestMergeFile14_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile14_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():3��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * 3��ڂ̌Ăяo���F����fileList�̗v�f2�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile14_src1.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not set."<br>
     * �E�t�@�C�����Fnull<br>
     * <br>
     * �ُ�P�[�X<br>
     * �����Ώۃt�@�C�����X�g�̍��ڂ̒���null������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile14() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile14_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = null;
        fileList.add(srcFile2);
        String srcFile3 = directoryPath + "testMergeFile14_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile14_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile3 = new File(srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile14_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile14_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile14_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not set.", e.getMessage());
            assertNull(e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(3, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));
            assertNull(VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 2, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile14_src1.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
            file.delete();
            file = new File(srcFile3);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testMergeFile15() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�ȉ��̗v�f������List�C���X�^���X<br>
     * 1�D"(�p�X)/testMergeFile15_src1.txt"<br>
     * 2�D"(�p�X)/testMergeFile15_src1.txt"<br>
     * 3�D"testMergeFile15_src3.txt"(���΃p�X)<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile15_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^���e�t�@�C���ɓ����Ă���B<br>
     * �EtestMergeFile15_src1.txt�f�[�^<br>
     * �EtestMergeFile15_src2.txt�f�[�^<br>
     * �EtestMergeFile15_src3.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile15_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():4��Ă΂��B<br>
     * 1��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����fileList�̗v�f1�̃t�@�C���p�X���n����邱�ƁB<br>
     * 3��ڂ̌Ăяo���F����fileList�̗v�f2�̃t�@�C���p�X���n����邱�ƁB<br>
     * 4��ڂ̌Ăяo���F����fileList�̗v�f3�̃t�@�C���p�X���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile15_src1.txt�f�[�^testMergeFile15_src2.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not absolute."<br>
     * �E�t�@�C�����FsrcFile3�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �����Ώۃt�@�C�����X�g�̍��ڂ̒��ɑ��΃p�X������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMergeFile15() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());
        List<String> fileList = new ArrayList<String>();

        String srcFile1 = directoryPath + "testMergeFile14_src1.txt";
        fileList.add(srcFile1);
        String srcFile2 = directoryPath + "testMergeFile14_src2.txt";
        fileList.add(srcFile2);
        String srcFile3 = "testMergeFile14_src3.txt";
        fileList.add(srcFile3);

        String newFile = directoryPath + "testMergeFile14_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile1 = new File(srcFile1);
        testSrcFile1.delete();
        testSrcFile1.createNewFile();

        File testSrcFile2 = new File(srcFile2);
        testSrcFile2.delete();
        testSrcFile2.createNewFile();

        File testSrcFile3 = new File(directoryPath + srcFile3);
        testSrcFile3.delete();
        testSrcFile3.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFile1FileWriter = null;
        FileWriter testSrcFile2FileWriter = null;
        FileWriter testSrcFile3FileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFile1FileWriter = new FileWriter(testSrcFile1);
            testSrcFile1FileWriter.write("testMergeFile15_src1.txt�f�[�^");
            testSrcFile1FileWriter.flush();
            testSrcFile1FileWriter.close();

            testSrcFile2FileWriter = new FileWriter(testSrcFile2);
            testSrcFile2FileWriter.write("testMergeFile15_src2.txt�f�[�^");
            testSrcFile2FileWriter.flush();
            testSrcFile2FileWriter.close();

            testSrcFile3FileWriter = new FileWriter(testSrcFile3);
            testSrcFile3FileWriter.write("testMergeFile15_src3.txt�f�[�^");
            testSrcFile3FileWriter.flush();
            testSrcFile3FileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile15_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not absolute.", e.getMessage());
            assertEquals(srcFile3, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(4, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(srcFile1, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));
            assertEquals(srcFile2, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 2, 0));
            assertEquals(srcFile3, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 3, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile15_src1.txt�f�[�^"
                    + "testMergeFile15_src2.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFile1FileWriter != null) {
                testSrcFile1FileWriter.close();
            }
            if (testSrcFile2FileWriter != null) {
                testSrcFile2FileWriter.close();
            }
            if (testSrcFile3FileWriter != null) {
                testSrcFile3FileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile1);
            file.delete();
            file = new File(srcFile2);
            file.delete();
            file = new File(directoryPath + srcFile3);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /*
     * testMergeFile16() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,D,F,G <br>
     * <br>
     * ���͒l�F(����) fileList:�v�f�������Ȃ�List�C���X�^���X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testMergeFile16_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) this.checkFileExist:true<br>
     * (���) fileList�Ŏw�肵���t�@�C��:���݂��Ȃ�<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestMergeFile16_new.txt�f�[�^<br>
     * <br>
     * �����b�N���|�����Ă���B<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ă΂��B<br>
     * ����newFile���n����邱�ƁB<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:�ω��Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * �E�t�@�C�����FnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * (checkFileExist�ݒ�FTRUE)<br>
     * �w�肳�ꂽ���ʃt�@�C�������ɑ��݂��邪���b�N����Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    // This testcase is ignored, because of Windows environment dependency.
    public void _ignore_testMergeFile16() throws Exception {
        // �����̐ݒ�
        List<String> fileList = new ArrayList<String>();

        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String newFile = directoryPath + "testMergeFile16_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        FileInputStream fis = null;
        FileLock lock = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testMergeFile16_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            fis = new FileInputStream(testNewFile);
            lock = fis.getChannel().lock(0L, Long.MAX_VALUE, true);

            // �e�X�g���{
            FileUtility.mergeFile(fileList, newFile);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �}�[�W��̃t�@�C�����e�m�F
            lock.release();
            File mergeFile = new File(newFile);
            assertTrue(mergeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mergeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testMergeFile16_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
            postReader.close();

        } finally {
            if (lock != null) {
                lock.release();
            }
            if (fis != null) {
                fis.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile01_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile01_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():false<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile01_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile01_src.txt�f�[�^<br>
     * <br>
     * ����P�[�X<br>
     * �ύX��̃t�@�C�������݂��Ȃ��ꍇ�A�������t�@�C�������ύX����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile01() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile01_src.txt";

        String newFile = directoryPath + "testRenameFile01_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();

        FileWriter testSrcFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile01_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �t�@�C�����̕ύX���m�F
            File removeFile = new File(srcFile);
            assertFalse(removeFile.exists());

            File renameFile = new File(newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile01_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile02_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile02_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile02_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile02_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile02_src.txt�f�[�^<br>
     * <br>
     * ����P�[�X<br>
     * (checkFileExist�ݒ�FTRUE)<br>
     * �ύX��̃t�@�C�������݂���ꍇ�A���̃t�@�C�����폜������A�������t�@�C�������ύX����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile02() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile02_src.txt";

        String newFile = directoryPath + "testRenameFile02_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile02_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile02_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);

            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �t�@�C�����̕ύX���m�F
            File removeFile = new File(srcFile);
            assertFalse(removeFile.exists());

            File renameFile = new File(newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile02_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile03_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile03_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():false<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile03_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile03_new.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testRenameFile03_src.txt is not exist."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �ύX�O�t�@�C�������݂��Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile03() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile03_src.txt";

        String newFile = directoryPath + "testRenameFile03_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile03_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(srcFile + " is not exist.", e.getMessage());
            assertEquals(srcFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �t�@�C�����̕ύX���m�F
            File renameFile = new File(newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile03_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile04_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile04_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():false<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile04_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile04_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile04_src.txt�f�[�^<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile04_new.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"(�p�X)/testRenameFile04_new.txt is exist."<br>
     * �EnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * (checkFileExist�ݒ�FFALSE)<br>
     * �ύX��̃t�@�C�������݂���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile04() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile04_src.txt";

        String newFile = directoryPath + "testRenameFile04_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile04_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile04_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(newFile + " is exist.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));
            assertTrue(testSrcFile.exists());

            // �t�@�C�����̕ύX���m�F
            File removeFile = new File(srcFile);
            assertTrue(removeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(removeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile04_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
            postReader.close();

            File renameFile = new File(newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            expectationResultData = "testRenameFile04_new.txt�f�[�^";
            data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /*
     * testRenameFile05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile05_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile05_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile05_src.txt�f�[�^<br>
     * <br>
     * �����b�N���|�����Ă���B<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile05_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile05_src.txt�f�[�^<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File control operation was failed."<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�@�C�����b�N�ȂǂŃt�@�C����Rename�����Ɏ��s�����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    // This testcase is ignored, because of Windows environment dependency.
    public void _ignore_testRenameFile05() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile05_src.txt";

        String newFile = directoryPath + "testRenameFile05_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        FileInputStream fis = null;
        FileLock lock = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile05_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            fis = new FileInputStream(testSrcFile);
            lock = fis.getChannel().lock(0L, Long.MAX_VALUE, true);

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile05_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �t�@�C�����̕ύX���m�F
            lock.release();
            File removeFile = new File(srcFile);
            assertTrue(removeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(removeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile05_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
            postReader.close();

            File renameFile = new File(newFile);
            assertFalse(renameFile.exists());
        } finally {
            if (lock != null) {
                lock.release();
            }
            if (fis != null) {
                fis.close();
            }
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:null<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile06_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile06_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ăяo�����B<br>
     * ����srcFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile06_new.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not set."<br>
     * �E�t�@�C�����Fnull<br>
     * <br>
     * �ُ�P�[�X<br>
     * �ύX�O�t�@�C����null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile06() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = null;

        String newFile = directoryPath + "testRenameFile06_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile06_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not set.", e.getMessage());
            assertNull(e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �t�@�C�����̕ύX���m�F
            File renameFile = new File(newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile06_new.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile07_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "testRenameFile07_new.txt"<br>
     * <br>
     * �����΃p�X<br>
     * (���) FileUtility#checkFileExist():true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile07_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile07_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile07_src.txt�f�[�^<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile07_new.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not absolute."<br>
     * �E�t�@�C�����FnewFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �ύX��t�@�C�������΃p�X�Őݒ肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile07() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile07_src.txt";

        String newFile = "testRenameFile07_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(directoryPath + newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile07_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile07_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not absolute.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �t�@�C�����̕ύX���m�F
            File removeFile = new File(srcFile);
            assertTrue(removeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(removeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile07_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
            postReader.close();

            File renameFile = new File(directoryPath + newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            expectationResultData = "testRenameFile07_new.txt�f�[�^";
            data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(directoryPath + newFile);
            file.delete();
        }
    }

    /**
     * testRenameFile08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile08_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:null<br>
     * (���) FileUtility#checkFileExist():true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile08_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile08_src.txt�f�[�^<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not set."<br>
     * �E�t�@�C�����Fnull<br>
     * <br>
     * �ُ�P�[�X<br>
     * �ύX��t�@�C����null�Őݒ肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile08() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile08_src.txt";

        String newFile = null;

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile08_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not set.", e.getMessage());
            assertNull(e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �t�@�C�����̕ύX���m�F
            File removeFile = new File(srcFile);
            assertTrue(removeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(removeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile08_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
            postReader.close();
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
        }
    }

    /**
     * testRenameFile09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "testRenameFile09_src.txt"<br>
     * <br>
     * �����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile09_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile09_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile09_new.txt�f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():1��Ăяo�����B<br>
     * ����srcFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile09_src.txt�f�[�^<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile09_new.txt�f�[�^<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"File path is not absolute."<br>
     * �E�t�@�C�����FsrcFile�Ɠ����l<br>
     * <br>
     * �ُ�P�[�X<br>
     * �ύX�O�t�@�C�������΃p�X�Őݒ肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenameFile09() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = "testRenameFile09_src.txt";

        String newFile = directoryPath + "testRenameFile09_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(directoryPath + srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile09_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile09_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B���s�ł��B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File path is not absolute.", e.getMessage());
            assertEquals(srcFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));

            // �t�@�C�����̕ύX���m�F
            File removeFile = new File(directoryPath + srcFile);
            assertTrue(removeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(removeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile09_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
            postReader.close();

            File renameFile = new File(newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            expectationResultData = "testRenameFile09_new.txt�f�[�^";
            data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(directoryPath + srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /*
     * testRenameFile10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,F,G <br>
     * <br>
     * ���͒l�F(����) srcFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile10_src.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (����) newFile:String�C���X�^���X<br>
     * "(�p�X)/testRenameFile10_new.txt"<br>
     * <br>
     * ����΃p�X<br>
     * (���) FileUtility#checkFileExist():true<br>
     * (���) srcFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile10_src.txt�f�[�^<br>
     * (���) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile10_new.txt�f�[�^<br>
     * <br>
     * �����b�N���|�����Ă���B<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkAbsolutePath():2��Ăяo�����B<br>
     * 1��ڂ̌Ăяo���F����srcFile���n����邱�ƁB<br>
     * 2��ڂ̌Ăяo���F����newFile���n����邱�ƁB<br>
     * (��ԕω�) srcFile�Ŏw�肵���t�@�C��:���݂��Ȃ��B<br>
     * (��ԕω�) newFile�Ŏw�肵���t�@�C��:���݂���B<br>
     * �ȉ��̃f�[�^�������Ă���B<br>
     * �EtestRenameFile02_src.txt�f�[�^<br>
     * <br>
     * �ُ�P�[�X<br>
     * (checkFileExist�ݒ�FTRUE)<br>
     * �ύX��̃t�@�C�������݂��邪���b�N����Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    // This testcase is ignored, because of Windows environment dependency.
    public void _ignore_testRenameFile10() throws Exception {
        // �����̐ݒ�
        String classFileName = this.getClass().getSimpleName() + ".class";
        URL url = this.getClass().getResource(classFileName);
        String directoryPath = url.getPath().substring(0,
                url.getPath().length() - classFileName.length());

        String srcFile = directoryPath + "testRenameFile10_src.txt";

        String newFile = directoryPath + "testRenameFile10_new.txt";

        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", true);

        // �e�X�g�Ώۃt�@�C��������������B
        File testSrcFile = new File(srcFile);
        testSrcFile.delete();
        testSrcFile.createNewFile();

        File testNewFile = new File(newFile);
        testNewFile.delete();
        testNewFile.createNewFile();

        FileWriter testSrcFileFileWriter = null;
        FileWriter testNewFileFileWriter = null;
        FileInputStream fis = null;
        FileLock lock = null;
        BufferedReader postReader = null;
        try {
            testSrcFileFileWriter = new FileWriter(testSrcFile);
            testSrcFileFileWriter.write("testRenameFile10_src.txt�f�[�^");
            testSrcFileFileWriter.flush();
            testSrcFileFileWriter.close();

            testNewFileFileWriter = new FileWriter(testNewFile);
            testNewFileFileWriter.write("testRenameFile10_new.txt�f�[�^");
            testNewFileFileWriter.flush();
            testNewFileFileWriter.close();

            fis = new FileInputStream(testNewFile);
            lock = fis.getChannel().lock(0L, Long.MAX_VALUE, true);

            // �e�X�g���{
            FileUtility.renameFile(srcFile, newFile);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("File control operation was failed.", e.getMessage());
            assertEquals(newFile, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(FileUtility.class,
                    "checkAbsolutePath"));
            assertEquals(srcFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 0, 0));
            assertEquals(newFile, VMOUTUtil.getArgument(FileUtility.class,
                    "checkAbsolutePath", 1, 0));

            // �t�@�C�����̕ύX���m�F
            File removeFile = new File(srcFile);
            assertTrue(removeFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(removeFile)));
            assertTrue(postReader.ready());
            String expectationResultData = "testRenameFile10_src.txt�f�[�^";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());

            File renameFile = new File(newFile);
            assertTrue(renameFile.exists());
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(renameFile)));
            assertTrue(postReader.ready());
            expectationResultData = "testRenameFile10_new.txt�f�[�^";
            data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (lock != null) {
                lock.release();
            }
            if (fis != null) {
                fis.close();
            }
            if (testSrcFileFileWriter != null) {
                testSrcFileFileWriter.close();
            }
            if (testNewFileFileWriter != null) {
                testNewFileFileWriter.close();
            }
            if (postReader != null) {
                postReader.close();
            }
            // �e�X�g��t�@�C�����폜
            File file = new File(srcFile);
            file.delete();
            file = new File(newFile);
            file.delete();
        }
    }

    /**
     * testIsCheckFileExist01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) FileUtility#checkFileExist():false<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     * <br>
     * �����̒l���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsCheckFileExist01() throws Exception {
        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g���{
        boolean result = FileUtility.isCheckFileExist();
        // ����
        assertFalse(result);
    }

    /**
     * testSetCheckFileExist01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) checkFileExist:true<br>
     * (���) FileUtility#checkFileExist():false<br>
     * <br>
     * ���Ғl�F(��ԕω�) checkFileExist:true<br>
     * <br>
     * �����̒l�������ɐݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCheckFileExist01() throws Exception {
        // �O������̐ݒ�
        UTUtil.setPrivateField(FileUtility.class, "checkFileExist", false);

        // �e�X�g���{
        FileUtility.setCheckFileExist(true);

        // ��ԕω��̊m�F
        assertEquals(true, UTUtil.getPrivateField(FileUtility.class,
                "checkFileExist"));
    }
}

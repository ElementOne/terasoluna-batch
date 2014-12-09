/*
 * $Id: PlainFileLineWriterTest.java 5230 2007-09-28 10:04:13Z anh $
 * 
 * Copyright (c) 2006 NTT DATA Corporation
 * 
 */

package jp.terasoluna.fw.file.dao.standard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.PlainFileLineWriter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �r�W�l�X���W�b�N�Ȃǂ���󂯎������������t�@�C���ɏo�͂���B ���̃t�@�C���A�N�Z�X�@�\�Ƃ͈قȂ�A�t�@�C���s�I�u�W�F�N�g���g��Ȃ��B<br>
 * AbstractFileLineWriter�̃T�u�N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineWriter
 */
public class PlainFileLineWriterTest {

    private static final String TEMP_FILE_NAME = PlainFileLineWriterTest.class
            .getResource("PlainFileLineWriterTest_tmp.txt").getPath();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(PlainFileLineWriterTest.class);
    }

    @Before
    public void setUp() throws Exception {
        VMOUTUtil.initialize();
        // �t�@�C���̏�����
        File file = new File(TEMP_FILE_NAME);
        file.delete();
        file.createNewFile();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        // �t�@�C���̏�����
        File file = new File(TEMP_FILE_NAME);
        file.delete();
        file.createNewFile();
    }

    /**
     * testPlainFileLineWriter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:PlainFileLineWriter01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:PlainFileLineWriter_Stub01<br>
     * �@@FileFormat�̐ݒ�L��A���ׂď����l�B<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) super:1��Ăяo����邱�Ƃ��m�F����B<br>
     * �Ăяo�����Ƃ��̈������A����fileName,clazz,columnFormatterMap�Ɠ����C���X�^���X�ł��邱��<br>
     * (��ԕω�) super.init:1��Ăяo����邱�Ƃ��m�F����B<br>
     * <br>
     * �e�N���X�̃R���X�g���N�^���Ă΂�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPlain01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Class<PlainFileLineWriter_Stub01> clazz = PlainFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        PlainFileLineWriter plainFileLineWriter = new PlainFileLineWriter(
                fileName, clazz, columnFormatterMap);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(AbstractFileLineWriter.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertEquals(columnFormatterMap, arguments.get(2));
        plainFileLineWriter.closeFile();
    }

    /**
     * testPrintDataLine01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) t:String�C���X�^���X<br>
     * (���) AbstractFileLineWriter#checkWriteTrailer():������s<br>
     * (���) Writer.writer():������s<br>
     * (���) AbstractFileLineWriter#getLineFeedChar():String�C���X�^���X<br>
     * (���) AbstractFileLineWriter#getWriter():Writer�C���X�^���X<br>
     * (���) AbstractFileLineWriter#setWriteData():������s<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineWriter#checkWriteTrailer():1��Ă΂��<br>
     * (��ԕω�) Writer.writer():2��Ă΂��<br>
     * (��ԕω�) AbstractFileLineWriter#getLineFeedChar():1��Ă΂��<br>
     * (��ԕω�) AbstractFileLineWriter#getWriter():2��Ă΂��<br>
     * (��ԕω�) AbstractFileLineWriter#setWriteData():1��Ă΂��<br>
     * <br>
     * ����p�^�[�� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPrintDataLine01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        PlainFileLineWriter plainFileLineWriter = new PlainFileLineWriter(
                fileName, PlainFileLineWriter_Stub01.class, columnFormatterMap);

        // �����̐ݒ�
        String t = "�f�[�^";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        plainFileLineWriter.printDataLine(t);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "checkWriteTrailer"));
        assertEquals(2, VMOUTUtil.getCallCount(Writer.class, "write"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "getLineFeedChar"));
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "getWriter"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "setWriteData"));
        plainFileLineWriter.closeFile();
    }

    /**
     * testPrintDataLine02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:String�C���X�^���X<br>
     * (���) AbstractFileLineWriter#checkWriteTrailer():������s<br>
     * (���) Writer.writer():IOException<br>
     * ��O����<br>
     * (���) AbstractFileLineWriter#getLineFeedChar():String�C���X�^���X<br>
     * (���) AbstractFileLineWriter#getWriter():Writer�C���X�^���X<br>
     * (���) AbstractFileLineWriter#setWriteData():������s<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineWriter#checkWriteTrailer():1��Ă΂��<br>
     * (��ԕω�) Writer.writer():�P��Ă΂��<br>
     * (��ԕω�) AbstractFileLineWriter#getLineFeedChar():�Ă΂�Ȃ�<br>
     * (��ԕω�) AbstractFileLineWriter#getWriter():1��Ă΂��<br>
     * (��ԕω�) AbstractFileLineWriter#setWriteData():�Ă΂�Ȃ�<br>
     * (��ԕω�) �Ȃ�:�ȉ��̗v�f������FileException��O����<br>
     * �E���b�Z�[�W�F"writer control operation was failed."<br>
     * �E������O�FWriter.writer()���甭������IOException<br>
     * �E�t�@�C�����igetFileName�̌��ʁj<br>
     * <br>
     * Writer.writer()����IOException�����������ꍇFileException���X���[�����̂��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPrintDataLine02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Class<PlainFileLineWriter_Stub01> clazz = PlainFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        PlainFileLineWriter plainFileLineWriter = new PlainFileLineWriter(
                fileName, clazz, columnFormatterMap);

        // �����̐ݒ�
        String t = "�f�[�^";

        // �O������̐ݒ�
        IOException exception = new IOException();
        VMOUTUtil.setExceptionAtAllTimes(Writer.class, "write", exception);
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            plainFileLineWriter.printDataLine(t);
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "checkWriteTrailer"));
            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getLineFeedChar"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getWriter"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));

            assertSame(FileException.class, e.getClass());
            assertEquals("writer control operation was failed.", e.getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        } finally {
            plainFileLineWriter.closeFile();
        }
    }

    /**
     * testPrintDataLine03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:String�C���X�^���X<br>
     * (���) AbstractFileLineWriter#checkWriteTrailer():FileException<br>
     * ��O����<br>
     * (���) Writer.writer():������s<br>
     * (���) AbstractFileLineWriter#getLineFeedChar():String�C���X�^���X<br>
     * (���) AbstractFileLineWriter#getWriter():Writer�C���X�^���X<br>
     * (���) AbstractFileLineWriter#setWriteData():������s<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineWriter#checkWriteTrailer():1��Ă΂��<br>
     * (��ԕω�) Writer.writer():�Ă΂�Ȃ�<br>
     * (��ԕω�) AbstractFileLineWriter#getLineFeedChar():�Ă΂�Ȃ�<br>
     * (��ԕω�) AbstractFileLineWriter#getWriter():�Ă΂�Ȃ�<br>
     * (��ԕω�) AbstractFileLineWriter#setWriteData():�Ă΂�Ȃ�<br>
     * (��ԕω�) �Ȃ�:checkWriteTrailer()���甭������FileException<br>
     * <br>
     * AbstractFileLineWriter#checkWriteTrailer()����FileException���X���[�����̂��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPrintDataLine03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Class<PlainFileLineWriter_Stub01> clazz = PlainFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        PlainFileLineWriter plainFileLineWriter = new PlainFileLineWriter(
                fileName, clazz, columnFormatterMap);

        // �����̐ݒ�
        String t = "�f�[�^";

        // �O������̐ݒ�
        FileException exception = new FileException("checkWriteTrailer����̃G���[�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineWriter.class,
                "checkWriteTrailer", exception);
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            plainFileLineWriter.printDataLine(t);
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "checkWriteTrailer"));
            assertEquals(0, VMOUTUtil.getCallCount(Writer.class, "write"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getLineFeedChar"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getWriter"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));

            assertSame(exception, e);
        } finally {
            plainFileLineWriter.closeFile();
        }
    }

    /**
     * testPrintDataLine04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:null<br>
     * (���) AbstractFileLineWriter#checkWriteTrailer():������s<br>
     * (���) Writer.writer():������s<br>
     * (���) AbstractFileLineWriter#getLineFeedChar():String�C���X�^���X<br>
     * (���) AbstractFileLineWriter#getWriter():Writer�C���X�^���X<br>
     * (���) AbstractFileLineWriter#setWriteData():������s<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:NullPointerException<br>
     * <br>
     * ��������Null��ݒ肵���ꍇ�́A��O���X���[����邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPrintDataLine04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Class<PlainFileLineWriter_Stub01> clazz = PlainFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        PlainFileLineWriter plainFileLineWriter = new PlainFileLineWriter(
                fileName, clazz, columnFormatterMap);

        // �����̐ݒ�
        String t = null;

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            plainFileLineWriter.printDataLine(t);
            fail("NullPointerException���X���[����܂���ł���");
        } catch (NullPointerException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertSame(NullPointerException.class, e.getClass());
        } finally {
            plainFileLineWriter.closeFile();
        }
    }

    /**
     * ����n<br>
     * FileFormat��encloseChar��delimiter���ݒ肳��Ă��Ă��A��������
     * @throws Exception
     */
    @Test
    public void testPrintDataLine05() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        PlainFileLineWriter fileLineWriter = new PlainFileLineWriter(fileName,
                PlainFileLineIterator_Stub02.class, columnFormatterMap);

        // �e�X�g���{
        fileLineWriter.printDataLine("\"1\",'22',\"333\",|4444|");
        fileLineWriter.printDataLine("\"5\",'66',\"777\",|8888|");
        fileLineWriter.printDataLine("\"9\",'AA',\"BBB\",|CCCC|");

        fileLineWriter.closeFile();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertEquals("\"1\",'22',\"333\",|4444|", reader.readLine());
            assertEquals("\"5\",'66',\"777\",|8888|", reader.readLine());
            assertEquals("\"9\",'AA',\"BBB\",|CCCC|", reader.readLine());
        } finally {
            reader.close();
        }
    }

    /**
     * testGetDelimiter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F <br>
     * ���Ғl�F(�߂�l) char:0<br>
     * <br>
     * getDelimiter()�����s����Ƃ��K���O��ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetDelimiter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Class<PlainFileLineWriter_Stub01> clazz = PlainFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        PlainFileLineWriter plainFileLineWriter = new PlainFileLineWriter(
                fileName, clazz, columnFormatterMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        char result = plainFileLineWriter.getDelimiter();

        // �ԋp�l�̊m�F
        assertEquals(0, result);

        // ��ԕω��̊m�F
        // �Ȃ�

        plainFileLineWriter.closeFile();
    }

    /**
     * testGetEncloseChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F <br>
     * ���Ғl�F(�߂�l) char:0<br>
     * <br>
     * getEncloseChar()�����s����Ƃ��K���O��ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetEncloseChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Class<PlainFileLineWriter_Stub01> clazz = PlainFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        PlainFileLineWriter plainFileLineWriter = new PlainFileLineWriter(
                fileName, clazz, columnFormatterMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        char result = plainFileLineWriter.getEncloseChar();

        // �ԋp�l�̊m�F
        assertEquals(0, result);

        // ��ԕω��̊m�F
        // �Ȃ�

        plainFileLineWriter.closeFile();
    }

}

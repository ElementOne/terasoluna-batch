/*
 * $Id: FixedFileLineWriterTest.java 5576 2007-11-15 13:13:32Z pakucn $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.NullStringConverter;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;
import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.FixedFileLineWriter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���s�I�u�W�F�N�g����f�[�^��ǂݍ��݁A1�s���̃f�[�^���Œ蒷�`���Ńt�@�C���ɏ������ށB<br>
 * AbstractFileLineWriter�̃T�u�N���X�B
 * <p>
 * @author ���c�N�i
 * @author ���O
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineWriter
 */
public class FixedFileLineWriterTest {

    private static final String TEMP_FILE_NAME = FixedFileLineWriterTest.class
            .getResource("FixedFileLineWriterTest_tmp.txt").getPath();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FixedFileLineWriterTest.class);
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
     * testFixedFileLineWriter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) fileName:"File_Empty.txt"(���΃p�X)<br>
     * (����) clazz:FixedFileLineWriter_Stub01<br>
     * @FileFormat()<br> (����) columnFormatterrMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                   �v�f1<br>
     *                   key:"test"<br>
     *                   value:ColumnFormatter�C���X�^���X<br>
     *                   FixedFileLineWriter_ColumnFormatterStub01�C���X�^���X<br>
     *                   �����<br>
     * <br>
     *                   ���Ғl�F(��ԕω�) super:1��Ă΂��<br>
     *                   �����Ɠ����C���X�^���X���ݒ肳���<br>
     *                   (��ԕω�) super.init:1��Ă΂��<br>
     * <br>
     *                   ����fileName�����΃p�X�Ŏw�肳�ꂽ�t�@�C�����������ꍇ�ɁA�R���X�g���N�^�̌Ăяo��������ɍs���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFixedFileLineWriter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Class<FixedFileLineWriter_Stub01> clazz = FixedFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        ColumnFormatter formatter = new FixedFileLineWriter_ColumnFormatterStub01();
        columnFormatterMap.put("java.lang.String", formatter);

        // �O������Ȃ�

        // �e�X�g���{
        FixedFileLineWriter<FixedFileLineWriter_Stub01> fileWriter = new FixedFileLineWriter<FixedFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(AbstractFileLineWriter.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnFormatterMap, arguments.get(2));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "init"));

        // �N���[�Y����
        fileWriter.closeFile();
    }

    /**
     * testFixedFileLineWriter02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FF.G <br>
     * <br>
     * ���͒l�F(����) fileName:(���΃p�X)<br>
     * (����) clazz:FixedFileLineWriter_Stub02<br>
     * @FileFormat(delimiter='�A')<br> (����) columnFormatterrMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                                �v�f1<br>
     *                                key:"test"<br>
     *                                value:ColumnFormatter�C���X�^���X<br>
     *                                FixedFileLineWriter_ColumnFormatterStub01�C���X�^���X<br>
     *                                �����<br>
     * <br>
     *                                ���Ғl�F(��ԕω�) super:1��Ă΂��<br>
     *                                �����Ɠ����C���X�^���X���ݒ肳���<br>
     *                                (��ԕω�) super.init:�Ă΂�Ȃ�<br>
     *                                (��ԕω�)
     *                                �Ȃ�:"Delimiter can not change."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException���������� �B<br>
     * <br>
     *                                ��O�B@FileFormat��delimiter�ɏ����l�ȊO��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     *                                �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFixedFileLineWriter02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Class<FixedFileLineWriter_Stub02> clazz = FixedFileLineWriter_Stub02.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        ColumnFormatter formatter = new FixedFileLineWriter_ColumnFormatterStub01();
        columnFormatterMap.put("java.lang.String", formatter);

        // �e�X�g���{
        try {
            new FixedFileLineWriter<FixedFileLineWriter_Stub02>(fileName,
                    clazz, columnFormatterMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F]
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "<init>"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "<init>", 0);
            assertEquals(fileName, arguments.get(0));
            assertEquals(FixedFileLineWriter_Stub02.class, arguments.get(1));
            assertSame(columnFormatterMap, arguments.get(2));
            assertFalse(VMOUTUtil
                    .isCalled(AbstractFileLineWriter.class, "init"));

            assertEquals("Delimiter can not change.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testFixedFileLineWriter03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FF.G <br>
     * <br>
     * ���͒l�F(����) fileName:(���΃p�X)<br>
     * (����) clazz:FixedFileLineWriter_Stub03<br>
     * @FileFormat(encloseChar='"')<br> (����) columnFormatterrMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                                  �v�f1<br>
     *                                  key:"test"<br>
     *                                  value:ColumnFormatter�C���X�^���X<br>
     *                                  FixedFileLineWriter_ColumnFormatterStub01�C���X�^���X<br>
     *                                  �����<br>
     * <br>
     *                                  ���Ғl�F(��ԕω�) super:1��Ă΂��<br>
     *                                  �����Ɠ����C���X�^���X���ݒ肳���<br>
     *                                  (��ԕω�) super.init:�Ă΂�Ȃ�<br>
     *                                  (��ԕω�)
     *                                  �Ȃ�:"EncloseChar can not change."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException����������
     *                                  �B<br>
     * <br>
     *                                  ��O�B@FileFormat��encloseChar�ɏ����l�ȊO��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     *                                  �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFixedFileLineWriter03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Class<FixedFileLineWriter_Stub03> clazz = FixedFileLineWriter_Stub03.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        ColumnFormatter formatter = new FixedFileLineWriter_ColumnFormatterStub01();
        columnFormatterMap.put("java.lang.String", formatter);

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new FixedFileLineWriter<FixedFileLineWriter_Stub03>(fileName,
                    clazz, columnFormatterMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "<init>"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "<init>", 0);
            assertEquals(fileName, arguments.get(0));
            assertEquals(FixedFileLineWriter_Stub03.class, arguments.get(1));
            assertSame(columnFormatterMap, arguments.get(2));
            assertFalse(VMOUTUtil
                    .isCalled(AbstractFileLineWriter.class, "init"));
            assertEquals("EncloseChar can not change.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testFixedFileLineWriter04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) fileName:"aaa.txt"�i��΃p�X�j<br>
     * (����) clazz:FixedFileLineWriter_Stub01<br>
     * @FileFormat()<br> (����) columnFormatterrMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                   �v�f1<br>
     *                   key:"test"<br>
     *                   value:ColumnFormatter�C���X�^���X<br>
     *                   FixedFileLineWriter_ColumnFormatterStub01�C���X�^���X<br>
     *                   �����<br>
     * <br>
     *                   ���Ғl�F(��ԕω�) super:1��Ă΂��<br>
     *                   �����Ɠ����C���X�^���X���ݒ肳���<br>
     *                   (��ԕω�) super.init:1��Ă΂��<br>
     * <br>
     *                   ����fileName����΃p�X�Ŏw�肳�ꂽ�t�@�C�����������ꍇ�ɁA�R���X�g���N�^�̌Ăяo��������ɍs���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFixedFileLineWriter04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineWriter_Stub01> clazz = FixedFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        ColumnFormatter formatter = new FixedFileLineWriter_ColumnFormatterStub01();
        columnFormatterMap.put("java.lang.String", formatter);

        // �O������Ȃ�

        // �e�X�g���{
        FixedFileLineWriter<FixedFileLineWriter_Stub01> fileWriter = new FixedFileLineWriter<FixedFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(AbstractFileLineWriter.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnFormatterMap, arguments.get(2));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineWriter.class,
                "init"));

        // �N���[�Y����
        fileWriter.closeFile();
    }

    /**
     * �ُ�n<br>
     * �t�@�C���s�I�u�W�F�N�g��OutputFileColumn�A�m�e�[�V����������
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testFixedFileLineWriter05() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Class<FileLineObject_Empty> clazz = FileLineObject_Empty.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new FixedFileLineWriter<FileLineObject_Empty>(fileName, clazz,
                    columnFormatterMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals("OutputFileColumn is not found.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * �ُ�n<br>
     * OutputFileColumn��EncloseChar���ݒ肳��Ă���ꍇ�A�G���[�Ƃ���
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testFixedFileLineWriter06() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new FixedFileLineWriter<CSVFileLine_Stub01>(fileName,
                    CSVFileLine_Stub01.class, columnFormatterMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals("columnEncloseChar can not change.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * ����n<br>
     * �s��؂薳���̌Œ蒷�`���t�@�C���Ńw�b�_�s�����w�肵���ꍇ�A<br>
     * �ُ�I�����Ȃ������m�F����B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testFixedFileLineWriter07() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        FixedFileLineWriter<FixedFileLine_Stub03> fileWriter = null;

        // �e�X�g���{
        try {
            fileWriter = new FixedFileLineWriter<FixedFileLine_Stub03>(
                    fileName, FixedFileLine_Stub03.class, columnFormatterMap);
        } finally {// �N���[�Y����
            fileWriter.closeFile();
        }
    }

    /**
     * ����n<br>
     * �s��؂薳���̌Œ蒷�`���t�@�C���Ńg���C���s�����w�肵���ꍇ�A<br>
     * �ُ�I�����Ȃ������m�F����B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testFixedFileLineWriter08() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        FixedFileLineWriter<FixedFileLine_Stub04> fileWriter = null;

        // �e�X�g���{
        try {
            fileWriter = new FixedFileLineWriter<FixedFileLine_Stub04>(
                    fileName, FixedFileLine_Stub04.class, columnFormatterMap);
        } finally {// �N���[�Y����
            fileWriter.closeFile();
        }
    }

    /**
     * testIsCheckByte01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) outputFileColumn:null<br>
     * (���) -:�Ȃ�<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * (��ԕω�) -:�Ȃ�<br>
     * <br>
     * true���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testIsCheckBytes01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;
        Class<FixedFileLineWriter_Stub01> clazz = FixedFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        ColumnFormatter formatter = new FixedFileLineWriter_ColumnFormatterStub01();
        columnFormatterMap.put("java.lang.String", formatter);

        FixedFileLineWriter<FixedFileLineWriter_Stub01> fileWriter = new FixedFileLineWriter<FixedFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �����̐ݒ�
        OutputFileColumn outputFileColumn = null;

        // �O������Ȃ�

        // �e�X�g���{
        boolean result = fileWriter.isCheckByte(outputFileColumn);
        assertTrue(result);

        // ��ԕω��Ȃ�

        // �N���[�Y����
        fileWriter.closeFile();
    }

    /**
     * testGetDelimiter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F <br>
     * ���Ғl�F(�߂�l) DELIMITER:not null<br>
     * '\u0000'<br>
     * <br>
     * delimiter��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetDelimiter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;
        Class<FixedFileLineWriter_Stub01> clazz = FixedFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        ColumnFormatter formatter = new FixedFileLineWriter_ColumnFormatterStub01();
        columnFormatterMap.put("java.lang.String", formatter);
        FixedFileLineWriter<FixedFileLineWriter_Stub01> lineWriter = new FixedFileLineWriter<FixedFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �����Ȃ�

        // �O������Ȃ�

        // �e�X�g���{
        char result = lineWriter.getDelimiter();

        // �ԋp�l�̊m�F
        assertEquals(Character.MIN_VALUE, result);

        // ��ԕω��Ȃ�

        // �N���[�Y����
        lineWriter.closeFile();
    }

    /**
     * testGetEncloseChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F <br>
     * ���Ғl�F(�߂�l) ENCLOSE_CHAR:not null<br>
     * '\u0000'<br>
     * <br>
     * encloseChar��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetEncloseChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;
        Class<FixedFileLineWriter_Stub01> clazz = FixedFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        ColumnFormatter formatter = new FixedFileLineWriter_ColumnFormatterStub01();
        columnFormatterMap.put("java.lang.String", formatter);
        FixedFileLineWriter<FixedFileLineWriter_Stub01> lineWriter = new FixedFileLineWriter<FixedFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �����Ȃ�

        // �O������Ȃ�

        // �e�X�g���{
        char result = lineWriter.getEncloseChar();

        // �ԋp�l�̊m�F
        assertEquals(Character.MIN_VALUE, result);

        // ��ԕω��Ȃ�

        // �N���[�Y����
        lineWriter.closeFile();
    }

    /**
     * ����n<br>
     * �Œ蒷���s����
     * @throws Exception
     */
    @Test
    public void testPrintDataLine01() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<FixedFileLine_Stub01> clazz = FixedFileLine_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        FixedFileLineWriter<FixedFileLine_Stub01> fileLineWriter = new FixedFileLineWriter<FixedFileLine_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        FixedFileLine_Stub01 t1 = new FixedFileLine_Stub01();
        FixedFileLine_Stub01 t2 = new FixedFileLine_Stub01();
        FixedFileLine_Stub01 t3 = new FixedFileLine_Stub01();

        // �ԋp�l�̊m�F
        t1.setColumn1("1");
        t1.setColumn2("22");
        t1.setColumn3("333");
        t1.setColumn4("4444");
        t2.setColumn1("5");
        t2.setColumn2("66");
        t2.setColumn3("777");
        t2.setColumn4("8888");
        t3.setColumn1("9");
        t3.setColumn2("AA");
        t3.setColumn3("BBB");
        t3.setColumn4("CCCC");

        // �e�X�g���{
        fileLineWriter.printDataLine(t1);
        fileLineWriter.printDataLine(t2);
        fileLineWriter.printDataLine(t3);

        fileLineWriter.closeFile();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertEquals("122333444456677788889AABBBCCCC", reader.readLine());
        } finally {
            reader.close();
        }
    }

    /**
     * ����n<br>
     * �Œ蒷
     * @throws Exception
     */
    @Test
    public void testPrintDataLine02() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        FixedFileLineWriter<FixedFileLine_Stub02> fileLineWriter = new FixedFileLineWriter<FixedFileLine_Stub02>(
                fileName, FixedFileLine_Stub02.class, columnFormatterMap);

        // �O����(����)
        FixedFileLine_Stub02 t1 = new FixedFileLine_Stub02();
        FixedFileLine_Stub02 t2 = new FixedFileLine_Stub02();
        FixedFileLine_Stub02 t3 = new FixedFileLine_Stub02();

        t1.setColumn1("1");
        t1.setColumn2("22");
        t1.setColumn3("333");
        t1.setColumn4("4444");
        t2.setColumn1("5");
        t2.setColumn2("66");
        t2.setColumn3("777");
        t2.setColumn4("8888");
        t3.setColumn1("9");
        t3.setColumn2("AA");
        t3.setColumn3("BBB");
        t3.setColumn4("CCCC");

        // �e�X�g���{
        fileLineWriter.printDataLine(t1);
        fileLineWriter.printDataLine(t2);
        fileLineWriter.printDataLine(t3);

        fileLineWriter.closeFile();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertEquals("1223334444", reader.readLine());
            assertEquals("5667778888", reader.readLine());
            assertEquals("9AABBBCCCC", reader.readLine());
        } finally {
            reader.close();
        }
    }

    /**
     * ����n<br>
     * �L���b�V�����Ă���A�m�e�[�V�����̏��𗘗p���Ă��鎖���m�F����B<br>
     * @throws Exception
     */
    @Test
    public void testPrintDataLine03() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        FixedFileLineWriter<CSVFileLine_Stub04> fileLineWriter = new FixedFileLineWriter<CSVFileLine_Stub04>(
                fileName, CSVFileLine_Stub04.class, columnFormatterMap);

        // �O����(����)
        CSVFileLine_Stub04 t1 = new CSVFileLine_Stub04();

        t1.setColumn1("1");
        t1.setColumn2("22");
        t1.setColumn3("333");
        t1.setColumn4("4444");

        // �t�@�C���s�I�u�W�F�N�g�ɐݒ肵�Ă������l��S�ď㏑��
        // �ȉ��̐ݒ肪�K�p�����΁A�t�@�C���s�I�u�W�F�N�g��
        // �A�m�e�[�V�����ɃA�N�Z�X���Ă��Ȃ����ƂɂȂ�B
        char[] charArray = new char[] { 0, 0, 0, 0 };
        // �O�����
        UTUtil.setPrivateField(fileLineWriter, "lineFeedChar", "\r\n");
        // UTUtil.setPrivateField(fileLineWriter, "delimiter", '_');
        UTUtil.setPrivateField(fileLineWriter, "outputFileColumns", null);
        UTUtil.setPrivateField(fileLineWriter, "columnFormats", new String[] {
                "", "", "", "" });
        UTUtil.setPrivateField(fileLineWriter, "columnBytes", new int[] { 1, 2,
                3, 4 });
        // UTUtil.setPrivateField(fileLineWriter, "totalBytes", 0);
        UTUtil.setPrivateField(fileLineWriter, "paddingTypes",
                new PaddingType[] { PaddingType.NONE, PaddingType.NONE,
                        PaddingType.NONE, PaddingType.NONE });
        UTUtil.setPrivateField(fileLineWriter, "paddingChars", charArray);
        UTUtil.setPrivateField(fileLineWriter, "trimChars", charArray);
        UTUtil.setPrivateField(fileLineWriter, "columnEncloseChar", charArray);
        UTUtil.setPrivateField(fileLineWriter, "stringConverters",
                new NullStringConverter[] { new NullStringConverter(),
                        new NullStringConverter(), new NullStringConverter(),
                        new NullStringConverter() });

        // �e�X�g���{
        fileLineWriter.printDataLine(t1);

        fileLineWriter.closeFile();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertEquals("1223334444", reader.readLine());
        } finally {
            reader.close();
        }
    }
}

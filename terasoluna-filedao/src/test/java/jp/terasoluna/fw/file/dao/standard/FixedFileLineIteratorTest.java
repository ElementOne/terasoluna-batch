/*
 * $Id: FixedFileLineIteratorTest.java 5354 2007-10-03 06:06:25Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.NullStringConverter;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.FixedFileLineIterator} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �Œ蒷�t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^�擾)�N���X�B
 * <p>
 * @author ���c�N�i
 * @author ���O
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineIterator
 */
public class FixedFileLineIteratorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FixedFileLineIteratorTest.class);
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
    }

    /**
     * �R���X�g���N�^�B
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public FixedFileLineIteratorTest(String name) {
        super(name);
    }

    /**
     * testFixedFileLineIterator01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:"aaa.txt"<br>
     * (����) clazz:�ȉ��̐ݒ������FileFormat�A�m�e�[�V���������X�^�u<br>
     * FixedFileLineIterator_Stub01<br>
     * �@ �A�m�e�[�V����FileFormat�F�����l<br>
     * (����) columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     * �v�f1<br>
     * key:"java.lang.String"<br>
     * value:ColumnParser�C���X�^���X<br>
     * FixedFileLineWriter_ColumnParserStub01�C���X�^���X<br>
     * �����<br>
     * (���) totalDefineBytes:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator�R���X�g���N�^:1��Ă΂��B<br>
     * �����Ɠ����C���X�^���X���n�����B<br>
     * (��ԕω�) AbstractFileLineIterator#init():1��Ă΂��B<br>
     * <br>
     * ����ɃR���X�g���N�^�̏������s���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFixedFileLineIterator01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub01> clazz = FixedFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser parser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", parser);
        columnParserMap.put("java.util.Date", parser);
        columnParserMap.put("java.math.BigDecimal", parser);
        columnParserMap.put("java.lang.int", parser);

        // �O������Ȃ�

        // �e�X�g���{
        new FixedFileLineIterator<FixedFileLineIterator_Stub01>(fileName,
                clazz, columnParserMap);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(AbstractFileLineIterator.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnParserMap, arguments.get(2));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "init"));
    }

    /**
     * testFixedFileLineIterator02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileName:"aaa.txt"<br>
     * (����) clazz:�ȉ��̐ݒ������FileFormat�A�m�e�[�V���������X�^�u<br>
     * FixedFileLineIterator_Stub05<br>
     * �@�A�m�e�[�V����FileFormat�Fdelimiter�������l�ȊO<br>
     * @FileFormat(delimiter='�A')<br> (����) columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                                �v�f1<br>
     *                                key:"java.lang.String"<br>
     *                                value:ColumnParser�C���X�^���X<br>
     *                                FixedFileLineWriter_ColumnParserStub01�C���X�^���X<br>
     *                                �����<br>
     *                                (���) totalDefineBytes:0<br>
     * <br>
     *                                ���Ғl�F(��ԕω�) AbstractFileLineIterator�R���X�g���N�^:1��Ă΂��B<br>
     *                                �����Ɠ����C���X�^���X���n�����B<br>
     *                                (��ԕω�) AbstractFileLineIterator#init():�Ă΂�Ȃ��B<br>
     *                                (��ԕω�)
     *                                �Ȃ�:"Delimiter can not change."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException����������B<br>
     * <br>
     *                                ��O�B@FileFormat��delimiter�ɏ����l�ȊO��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     *                                �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFixedFileLineIterator02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub05> clazz = FixedFileLineIterator_Stub05.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser parser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", parser);
        columnParserMap.put("java.util.Date", parser);
        columnParserMap.put("java.math.BigDecimal", parser);
        columnParserMap.put("java.lang.int", parser);

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new FixedFileLineIterator<FixedFileLineIterator_Stub05>(fileName,
                    clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "<init>"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineIterator.class, "<init>", 0);
            assertEquals(fileName, arguments.get(0));
            assertEquals(clazz, arguments.get(1));
            assertSame(columnParserMap, arguments.get(2));
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals("Delimiter can not change.", e.getMessage());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testFixedFileLineIterator03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileName:"aaa.txt"<br>
     * (����) clazz:�ȉ��̐ݒ������FileFormat�A�m�e�[�V���������X�^�u<br>
     * FixedFileLineIterator_Stub06<br>
     * �@�A�m�e�[�V����FileFormat�FencloseChar�������l�ȊO<br>
     * @FileFormat(encloseChar='"')<br> (����) columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                                  �v�f1<br>
     *                                  key:"java.lang.String"<br>
     *                                  value:ColumnParser�C���X�^���X<br>
     *                                  FixedFileLineWriter_ColumnParserStub01�C���X�^���X<br>
     *                                  �����<br>
     *                                  (���) totalDefineBytes:0<br>
     * <br>
     *                                  ���Ғl�F(��ԕω�) AbstractFileLineIterator�R���X�g���N�^:1��Ă΂��B<br>
     *                                  �����Ɠ����C���X�^���X���n�����B<br>
     *                                  (��ԕω�) AbstractFileLineIterator#init():�Ă΂�Ȃ��B<br>
     *                                  (��ԕω�)
     *                                  �Ȃ�:"EncloseChar can not change."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException����������B<br>
     * <br>
     *                                  ��O�B@FileFormat��encloseChar�ɏ����l�ȊO��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     *                                  �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFixedFileLineIterator03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub06> clazz = FixedFileLineIterator_Stub06.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser parser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", parser);
        columnParserMap.put("java.util.Date", parser);
        columnParserMap.put("java.math.BigDecimal", parser);
        columnParserMap.put("java.lang.int", parser);

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new FixedFileLineIterator<FixedFileLineIterator_Stub06>(fileName,
                    clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "<init>"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineIterator.class, "<init>", 0);
            assertEquals(fileName, arguments.get(0));
            assertEquals(clazz, arguments.get(1));
            assertSame(columnParserMap, arguments.get(2));
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals("EncloseChar can not change.", e.getMessage());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testFixedFileLineIterator04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:"aaa.txt"<br>
     * (����) clazz:�ȉ��̐ݒ������FileFormat�A�m�e�[�V���������X�^�u<br>
     * FixedFileLineIterator_Stub02<br>
     * �@ �A�m�e�[�V����FileFormat�F�����l<br>
     * �����F<br>
     * �@�@�@@InputFileColumn(columnIndex = 1, bytes = 5)<br>
     * �@�@�@String column1;<br>
     * �@�@�@@InputFileColumn(columnIndex = 1, bytes = 3)<br>
     * �@�@�@String column2;<br>
     * (����) columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     * �v�f1<br>
     * key:"java.lang.String"<br>
     * value:ColumnParser�C���X�^���X<br>
     * FixedFileLineWriter_ColumnParserStub01�C���X�^���X<br>
     * �����<br>
     * (���) totalDefineBytes:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator�R���X�g���N�^:1��Ă΂��B<br>
     * �����Ɠ����C���X�^���X���n�����B<br>
     * (��ԕω�) AbstractFileLineIterator#init():1��Ă΂��B<br>
     * <br>
     * ����ɃR���X�g���N�^�̏������s���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFixedFileLineIterator04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub02> clazz = FixedFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser parser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", parser);
        columnParserMap.put("java.util.Date", parser);
        columnParserMap.put("java.math.BigDecimal", parser);
        columnParserMap.put("java.lang.int", parser);

        // �O������Ȃ�

        // �e�X�g���{
        new FixedFileLineIterator<FixedFileLineIterator_Stub02>(fileName,
                clazz, columnParserMap);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(AbstractFileLineIterator.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnParserMap, arguments.get(2));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "init"));
    }

    /**
     * �ُ�n<br>
     * �t�@�C���s�I�u�W�F�N�g��InputFileColumn�A�m�e�[�V����������
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFixedFileLineIterator05() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<FileLineObject_Empty> clazz = FileLineObject_Empty.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);

        // �e�X�g���{
        try {
            new FixedFileLineIterator<FileLineObject_Empty>(fileName, clazz,
                    columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals("InputFileColumn is not found.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * �ُ�n<br>
     * InputFileColumn��EncloseChar���ݒ肳��Ă���ꍇ�A�G���[�Ƃ���
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFixedFileLineIterator06() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);

        // �e�X�g���{
        try {
            new FixedFileLineIterator<CSVFileLine_Stub01>(fileName,
                    CSVFileLine_Stub01.class, columnParserMap);
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
     * �ُ�n<br>
     * �s��؂薳���̌Œ蒷�`���t�@�C���Ńw�b�_�s�����w�肵���ꍇ�A<br>
     * �ُ�I�����鎖���m�F����B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testFixedFileLineIterator07() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);

        // �e�X�g���{
        try {
            new FixedFileLineIterator<FixedFileLine_Stub03>(fileName,
                    FixedFileLine_Stub03.class, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals("HeaderLineCount or trailerLineCount cannot be used.",
                    e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * �ُ�n<br>
     * �s��؂薳���̌Œ蒷�`���t�@�C���Ńg���C���s�����w�肵���ꍇ�A<br>
     * �ُ�I�����鎖���m�F����B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testFixedFileLineIterator08() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);

        // �e�X�g���{
        try {
            new FixedFileLineIterator<FixedFileLine_Stub04>(fileName,
                    FixedFileLine_Stub04.class, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals("HeaderLineCount or trailerLineCount cannot be used.",
                    e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testSeparateColumns01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) fileLineString:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:new String[0]<br>
     * <br>
     * ����p�^�[���B<br>
     * null�������Ƃ��ēn���ꂽ�ꍇ�A�v�f��0�̔z���ԋp���邱�Ƃ��m�F����B<br>
     * �ʏ�̏����ł��̕ԋp�l���߂邱�Ƃ͂Ȃ��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub01> clazz = FixedFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser parser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", parser);
        columnParserMap.put("java.util.Date", parser);
        columnParserMap.put("java.math.BigDecimal", parser);
        columnParserMap.put("java.lang.int", parser);
        FixedFileLineIterator<FixedFileLineIterator_Stub01> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = null;

        // �O������Ȃ�

        // �e�X�g���{
        String[] columns = lineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(0, columns.length);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa"<br>
     * (���) this.fields[]:�z��v�f��0<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:Illega��StateException�������BFileException�Ƀ��b�v����邱�Ƃ��m�F����B<br>
     * �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B<br>
     * ���b�Z�[�W�F"Total Columns byte is different from Total FileLineObject's columns byte."<br>
     * <br>
     * ��O�B�A�m�e�[�V�����Őݒ肵���o�C�g���̍��v�ƁA�t�@�C����1�s������̃o�C�g�����قȂ�ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     * �t�@�C���s�I�u�W�F�N�g�̃C���X�^���X�ϐ����Ȃ��ꍇ�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub01> clazz = FixedFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser parser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", parser);
        columnParserMap.put("java.util.Date", parser);
        columnParserMap.put("java.math.BigDecimal", parser);
        columnParserMap.put("java.lang.int", parser);
        FixedFileLineIterator<FixedFileLineIterator_Stub01> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa";

        // �O�����(�C���X�^���X����fileds[]�ɐݒ肳���)

        // �e�X�g���{
        try {
            lineIterator.separateColumns(fileLineString);
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            String message = "Total Columns byte is different from "
                    + "Total FileLineObject's columns byte.";
            assertEquals(message, e.getMessage());
        }
    }

    /**
     * testSeparateColumns03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"12345"<br>
     * (���) this.fields[]:�z��v�f��1<br>
     * (���) this.field[]�̃A�m�e�[�V����InputFileColumn:@InputFileColumn(columnIndex = 0, bytes = 5, stringConverter =
     * StringConverterToUpperCase.class, trimChar = '0', trimType = TrimType.LEFT)<br>
     * private String shopId = null;<br>
     * (���) AbstractFileLineIterator.fileEncoding:�V�X�e���f�t�H���g<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:�ȉ��̗v�f������String�z��<br>
     * �@�v�f1�F"12345"<br>
     * <br>
     * ����p�^�[���B�z��v�f1�̕�����^�z���ԋp����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub03> clazz = FixedFileLineIterator_Stub03.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        FixedFileLineIterator<FixedFileLineIterator_Stub03> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub03>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "12345";

        // �O�����(�C���X�^���X����fileds[]�ɐݒ肳���)

        // �e�X�g���{
        String[] columns = lineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, columns.length);
        assertEquals("12345", columns[0]);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileLineString:"12345"<br>
     * (���) this.fields[]:�z��v�f��1<br>
     * (���) this.field[]�̃A�m�e�[�V����InputFileColumn:@InputFileColumn(columnIndex = 0, bytes = 5, stringConverter =
     * StringConverterToUpperCase.class, trimChar = '0', trimType = TrimType.LEFT)<br>
     * private String shopId = null;<br>
     * (���) AbstractFileLineIterator.fileEncoding:���݂��Ȃ��G���R�[�h<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:UnsupoortedEncodingException�������BFileException�Ƀ��b�v����邱�Ƃ��m�F����<br>
     * �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B<br>
     * ���b�Z�[�W�F"fileEncoding which isn't supported was set."<br>
     * <br>
     * ��O�B�ݒ肳��Ă���G���R�[�h�����݂��Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub03> clazz = FixedFileLineIterator_Stub03.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        FixedFileLineIterator<FixedFileLineIterator_Stub03> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub03>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "12345";

        // �O������̐ݒ�
        UTUtil.setPrivateField(lineIterator, "fileEncoding", "aaa");

        // �e�X�g���{
        try {
            lineIterator.separateColumns(fileLineString);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(UnsupportedEncodingException.class, e.getCause()
                    .getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals("fileEncoding which isn't supported was set.", e
                    .getMessage());
        }
    }

    /**
     * testSeparateColumns05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"2006/12/10aaaaa123456789"<br>
     * (���) this.fields[]:�z��v�f��3<br>
     * (���) this.field[]�̃A�m�e�[�V����InputFileColumn:@InputFileColumn(columnIndex = 0, bytes = 10, columnFormat = "yyyy/MM/dd")<br>
     * private Date hiduke = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 1, bytes = 5, stringConverter = StringConverterToUpperCase.class, trimChar = '0', trimType
     *                              = TrimType.LEFT)<br>
     *                              private String shopId = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 2, bytes = 9, columnFormat = "#########")<br>
     *                              private BigDecimal uriage = null;<br>
     *                              (���) AbstractFileLineIterator.fileEncoding:�V�X�e���f�t�H���g<br>
     * <br>
     *                              ���Ғl�F(�߂�l) String[]:�ȉ��̗v�f������String�z��<br>
     *                              �@�v�f1�F"2006/12/10"<br>
     *                              �@�v�f2�F"aaaaa"<br>
     *                              �@�v�f3�F"123456789"<br>
     * <br>
     *                              ����p�^�[��<br>
     *                              �z��v�f3�̕�������z���ԋp����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub04> clazz = FixedFileLineIterator_Stub04.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        FixedFileLineIterator<FixedFileLineIterator_Stub04> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub04>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "2006/12/10aaaaa123456789";

        // �O�����(�C���X�^���X����fileds[]�ɐݒ肳���)

        // �e�X�g���{
        String[] columns = lineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, columns.length);
        assertEquals("2006/12/10", columns[0]);
        assertEquals("aaaaa", columns[1]);
        assertEquals("123456789", columns[2]);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:�󕶎�<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:new String[0]<br>
     * <br>
     * ����p�^�[���B<br>
     * �󕶎��������Ƃ��ēn���ꂽ�ꍇ�A�v�f��0�̔z���ԋp���邱�Ƃ��m�F����B<br>
     * �ʏ�̏����ł��̕ԋp�l���߂邱�Ƃ͂Ȃ��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub01> clazz = FixedFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        FixedFileLineIterator<FixedFileLineIterator_Stub01> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "";

        // �O�����(�C���X�^���X����fileds[]�ɐݒ肳���)

        // �e�X�g���{
        String[] columns = lineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(0, columns.length);
    }

    /**
     * testSeparateColumns07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileLineString:"2006/01/01����������012345678"�i�S�p�������g�p�j<br>
     * (���) this.fields[]:�z��v�f��3<br>
     * (���) this.field[]�̃A�m�e�[�V����InputFileColumn:@InputFileColumn(columnIndex = 0, bytes = 10, columnFormat = "yyyy/MM/dd")<br>
     * private Date hiduke = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 1, bytes = 5, stringConverter = StringConverterToUpperCase.class, trimChar = '0', trimType
     *                              = TrimType.LEFT)<br>
     *                              private String shopId = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 2, bytes = 9, columnFormat = "#########")<br>
     *                              private BigDecimal uriage = null;<br>
     *                              (���) AbstractFileLineIterator.fileEncoding:�V�X�e���f�t�H���g<br>
     * <br>
     *                              ���Ғl�F(��ԕω�) ��O:Illega��StateException�������BFileException�Ƀ��b�v����邱�Ƃ��m�F����B<br>
     *                              �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B<br>
     *                              ���b�Z�[�W�F"Total Columns byte is different from Total FileLineObject's columns byte."<br>
     * <br>
     *                              ��O�B�A�m�e�[�V�����Őݒ肵���o�C�g���̍��v�ƁA�t�@�C����1�s������̃o�C�g�����قȂ�ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     *                              �t�@�C���s�I�u�W�F�N�g�̃C���X�^���X�ϐ����Ȃ��ꍇ�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub04> clazz = FixedFileLineIterator_Stub04.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        FixedFileLineIterator<FixedFileLineIterator_Stub04> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub04>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "2006/01/01����������012345678";

        // �O�����(�C���X�^���X����fileds[]�ɐݒ肳���)

        // �e�X�g���{
        try {
            lineIterator.separateColumns(fileLineString);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            String message = "Total Columns byte is different from "
                    + "Total FileLineObject's columns byte.";
            assertEquals(message, e.getMessage());
        }
    }

    /**
     * testIsCheckByte01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) inputFileColumn:null<br>
     * (���) -:�Ȃ�<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     * (��ԕω�) -:�Ȃ�<br>
     * <br>
     * false���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsCheckByte01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub01> clazz = FixedFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);

        FixedFileLineIterator<FixedFileLineIterator_Stub01> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        InputFileColumn inputFileColumn = null;

        // �O������Ȃ�

        // �e�X�g���{
        boolean result = lineIterator.isCheckByte(inputFileColumn);

        // �ԋp�l�̊m�F
        assertFalse(result);

        // ��ԕω��Ȃ�
    }

    /**
     * testGetDelimiter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F <br>
     * ���Ғl�F(�߂�l) DELIMITER:,(�J���})'<br>
     * <br>
     * delimiter��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDelimiter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub01> clazz = FixedFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();

        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);

        FixedFileLineIterator<FixedFileLineIterator_Stub01> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����Ȃ�

        // �O������Ȃ�

        // �e�X�g���{
        char result = lineIterator.getDelimiter();

        // �ԋp�l�̊m�F
        assertEquals(',', result);

        // ��ԕω��Ȃ�
    }

    /**
     * testGetEncloseChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F <br>
     * ���Ғl�F(�߂�l) ENCLOSE_CHAR:Character.MIN_VALUE<br>
     * <br>
     * encloseChar��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetEncloseChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileLineIterator_Stub01> clazz = FixedFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();

        ColumnParser columnParser = new FixedFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);

        FixedFileLineIterator<FixedFileLineIterator_Stub01> lineIterator = new FixedFileLineIterator<FixedFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����Ȃ�

        // �O������Ȃ�

        // �e�X�g���{
        char result = lineIterator.getEncloseChar();

        // �ԋp�l�̊m�F
        assertEquals(Character.MIN_VALUE, result);

        // ��ԕω��Ȃ�
    }

    /**
     * ����n<br>
     * �Œ蒷���s����
     * @throws Exception
     */
    public void testNext01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "FixedFileLineIterator_next01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        FixedFileLineIterator<FixedFileLine_Stub01> fileLineIterator = new FixedFileLineIterator<FixedFileLine_Stub01>(
                fileName, FixedFileLine_Stub01.class, columnParserMap);

        // �e�X�g���{
        FixedFileLine_Stub01 result1 = fileLineIterator.next();
        FixedFileLine_Stub01 result2 = fileLineIterator.next();
        FixedFileLine_Stub01 result3 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("1", result1.getColumn1());
        assertEquals("22", result1.getColumn2());
        assertEquals("333", result1.getColumn3());
        assertEquals("4444", result1.getColumn4());

        assertEquals("5", result2.getColumn1());
        assertEquals("66", result2.getColumn2());
        assertEquals("777", result2.getColumn3());
        assertEquals("8888", result2.getColumn4());

        assertEquals("9", result3.getColumn1());
        assertEquals("AA", result3.getColumn2());
        assertEquals("BBB", result3.getColumn3());
        assertEquals("CCCC", result3.getColumn4());
    }

    /**
     * ����n<br>
     * �Œ蒷
     * @throws Exception
     */
    public void testNext02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "FixedFileLineIterator_next02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        FixedFileLineIterator<FixedFileLine_Stub02> fileLineIterator = new FixedFileLineIterator<FixedFileLine_Stub02>(
                fileName, FixedFileLine_Stub02.class, columnParserMap);

        // �e�X�g���{
        FixedFileLine_Stub02 result1 = fileLineIterator.next();
        FixedFileLine_Stub02 result2 = fileLineIterator.next();
        FixedFileLine_Stub02 result3 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("1", result1.getColumn1());
        assertEquals("22", result1.getColumn2());
        assertEquals("333", result1.getColumn3());
        assertEquals("4444", result1.getColumn4());

        assertEquals("5", result2.getColumn1());
        assertEquals("66", result2.getColumn2());
        assertEquals("777", result2.getColumn3());
        assertEquals("8888", result2.getColumn4());

        assertEquals("9", result3.getColumn1());
        assertEquals("AA", result3.getColumn2());
        assertEquals("BBB", result3.getColumn3());
        assertEquals("CCCC", result3.getColumn4());
    }

    /**
     * ����n<br>
     * �L���b�V�����Ă���A�m�e�[�V�����̏��𗘗p���Ă��鎖���m�F����B<br>
     * @throws Exception
     */
    public void testNext03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        FixedFileLineIterator<CSVFileLine_Stub04> fileLineIterator = new FixedFileLineIterator<CSVFileLine_Stub04>(
                fileName, CSVFileLine_Stub04.class, columnParserMap);

        // �t�@�C���s�I�u�W�F�N�g�ɐݒ肵�Ă������l��S�ď㏑��
        // �ȉ��̐ݒ肪�K�p�����΁A�t�@�C���s�I�u�W�F�N�g��
        // �A�m�e�[�V�����ɃA�N�Z�X���Ă��Ȃ����ƂɂȂ�B
        char[] charArray = new char[] { 0, 0, 0, 0 };
        // �O�����
        UTUtil.setPrivateField(fileLineIterator, "lineFeedChar", "\r\n");
        UTUtil.setPrivateField(fileLineIterator, "inputFileColumns", null);
        UTUtil.setPrivateField(fileLineIterator, "columnFormats", new String[] {
                "", "", "", "" });
        UTUtil.setPrivateField(fileLineIterator, "columnBytes", new int[] { 4,
                3, 4, 6 });
        UTUtil.setPrivateField(fileLineIterator, "totalBytes", 17);

        UTUtil.setPrivateField(fileLineIterator, "trimChars", charArray);
        UTUtil
                .setPrivateField(fileLineIterator, "columnEncloseChar",
                        charArray);
        UTUtil.setPrivateField(fileLineIterator, "stringConverters",
                new NullStringConverter[] { new NullStringConverter(),
                        new NullStringConverter(), new NullStringConverter(),
                        new NullStringConverter() });
        LineReader reader = (LineReader) UTUtil.getPrivateField(
                fileLineIterator, "lineReader");
        UTUtil.setPrivateField(reader, "lineFeedChar", "\r\n");

        // �e�X�g���{
        CSVFileLine_Stub04 result1 = fileLineIterator.next();
        CSVFileLine_Stub04 result2 = fileLineIterator.next();
        CSVFileLine_Stub04 result3 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("\"1\",", result1.getColumn1());
        assertEquals("22,", result1.getColumn2());
        assertEquals("333,", result1.getColumn3());
        assertEquals("|4444|", result1.getColumn4());

        assertEquals("\"5\",", result2.getColumn1());
        assertEquals("66,", result2.getColumn2());
        assertEquals("777,", result2.getColumn3());
        assertEquals("|8888|", result2.getColumn4());

        assertEquals("\"9\",", result3.getColumn1());
        assertEquals("AA,", result3.getColumn2());
        assertEquals("BBB,", result3.getColumn3());
        assertEquals("|CCCC|", result3.getColumn4());
    }
}

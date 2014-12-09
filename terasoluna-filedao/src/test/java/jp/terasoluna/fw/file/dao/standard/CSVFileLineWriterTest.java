/*
 * $Id:$
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
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.NullStringConverter;
import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.CSVFileLineWriter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���s�I�u�W�F�N�g����f�[�^��ǂݍ��݁A 1�s���̃f�[�^��CSV�`���Ńt�@�C�� �ɏ������ށB<br>
 * AbstractFileLineWriter�̃T�u�N���X�B
 * <p>
 * @author ���c �N�i
 * @author �� ��O
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineWriter
 */
public class CSVFileLineWriterTest {

    private static final String TEMP_FILE_NAME = CSVFileLineWriterTest.class
            .getResource("CSVFileLineWriterTest_tmp.txt").getPath();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(CSVFileLineWriterTest.class);
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
     * testCSVFileLineWriter01() <br>
     * <br>
     * (�ُ�n) <br>
     * <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileName:"(�p�X)CSVFileLineWriter_testCSVFileLineWriter01.txt"<br>
     * (����) clazz:CSVFileLineWriter_Stub05�C���X�^���X<br>
     * �@@FileFormat�̐ݒ�<br>
     * �@�@delimiter='�A'<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:"Delimiter can not change."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException����������B<br>
     * <br>
     * ��O�B@FileFormat��delimiter�ɏ����l�ȊO��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     * �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testCSVFileLineWriter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎���������s�v

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Class<CSVFileLineWriter_Stub05> clazz = CSVFileLineWriter_Stub05.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O������̐ݒ�
        // �Ȃ�
        CSVFileLineWriter<CSVFileLineWriter_Stub05> writer = null;

        try {
            // �e�X�g���{
            writer = new CSVFileLineWriter<CSVFileLineWriter_Stub05>(fileName,
                    clazz, columnFormatterMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("Delimiter can not change.", e.getMessage());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
        } finally {
            if (writer != null) {
                writer.closeFile();
            }
        }
    }

    /**
     * testCSVFileLineWriter02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:"(�p�X)CSVFileLineWriter_testCSVFileLineWriter02.txt"<br>
     * (����) clazz:CSVFileLineWriter_Stub01<br>
     * �@@FileFormat�̐ݒ�<br>
     * �@�@delimiter�ȊO=�f�t�H���g�l�ȊO<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.encloseChar:����clazz�̃A�m�e�[�V����FileFormat��encloseChar()�̒l�B<br>
     * (��ԕω�) AbstractFileLineWriter#AbstractFileLineWriter():1��Ăяo����邱��<br>
     * �������m�F���邱��<br>
     * (��ԕω�) AbstractFileLineWriter#init():1��Ăяo����邱��<br>
     * <br>
     * @FileFormat��delimiter�ȊO�̐ݒ���f�t�H���g�l�ł͂Ȃ��f�[�^�Őݒ肵���ꍇ�A�R���X�g���N�^�̌Ăяo��������ɍs���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCSVFileLineWriter02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎���������s�v

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Class<CSVFileLineWriter_Stub01> clazz = CSVFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O������̐ݒ�
        // �Ȃ�

        CSVFileLineWriter<CSVFileLineWriter_Stub01> result = null;
        try {
            // �e�X�g���{
            result = new CSVFileLineWriter<CSVFileLineWriter_Stub01>(fileName,
                    clazz, columnFormatterMap);

            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals('\"', UTUtil.getPrivateField(result, "encloseChar"));

            int superCallCount = VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "<init>");
            assertEquals(1, superCallCount);
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "<init>", 0);
            assertEquals(3, arguments.size());
            assertEquals(fileName, arguments.get(0));
            assertEquals(CSVFileLineWriter_Stub01.class, arguments.get(1));
            assertEquals(columnFormatterMap, arguments.get(2));

            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "init"));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            if (result != null) {
                result.closeFile();
            }
        }
    }

    /**
     * �ُ�n<br>
     * �t�@�C���s�I�u�W�F�N�g��OutputFileColumn�A�m�e�[�V����������
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testCSVFileLineWriter03() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Class<FileLineObject_Empty> clazz = FileLineObject_Empty.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        CSVFileLineWriter<FileLineObject_Empty> writer = null;
        // �e�X�g���{
        try {
            new CSVFileLineWriter<FileLineObject_Empty>(fileName, clazz,
                    columnFormatterMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals("OutputFileColumn is not found.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        } finally {
            if (writer != null) {
                writer.closeFile();
            }
        }
    }

    /**
     * testGetColumn01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) t:CSVFileLineWriter_Stub06�C���X�^���X<br>
     * �@@FileFormat()<br>
     * �@String�ϐ�column01<br>
     * �@�@�A�m�e�[�V�����F@OutputFileColumn(columnIndex = 0)<br>
     * �@�@�l�F"abcdef"<br>
     * (����) index:0<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"abcdef"<br>
     * (��ԕω�) AbstractFileLineWriter#getColumn():�������n����āA1��Ăяo����邱��<br>
     * <br>
     * ����t�ɐݒ肳�ꂽ�N���X�i�͂ݕ������ݒ肳��Ă��Ȃ��ꍇ�j�̃J�����C���f�b�N�X1�̑����l�i�͂ݕ������܂܂�Ȃ��j���擾�ł��邱�Ƃ��m�F����e�X�g�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetColumn01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        CSVFileLineWriter<CSVFileLineWriter_Stub06> lineWriter = new CSVFileLineWriter<CSVFileLineWriter_Stub06>(
                fileName, CSVFileLineWriter_Stub06.class, columnFormatterMap);

        // �����̐ݒ�
        CSVFileLineWriter_Stub06 stub = new CSVFileLineWriter_Stub06();
        stub.setColumn01("abcdef");

        // �O������̐ݒ�
        // �Ȃ�

        try {
            // �e�X�g���{
            String result = lineWriter.getColumn(stub, 0);

            // �ԋp�l�̊m�F
            assertEquals("abcdef", result);

            // ��ԕω��̊m�F
            int superCallCount = VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn");
            assertEquals(1, superCallCount);
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, arguments.size());
            assertSame(stub, arguments.get(0));
            assertEquals(0, arguments.get(1));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetColumn02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) t:CSVFileLineWriter_Stub07�C���X�^���X<br>
     * �@@FileFormat(encloseChar='\"')<br>
     * �@String�ϐ�column01<br>
     * �@�@�A�m�e�[�V�����F@OutputFileColumn(columnIndex = 0)<br>
     * �@�@�l�F"abcdef"<br>
     * (����) index:0<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"abcdef"<br>
     * (��ԕω�) AbstractFileLineWriter#getColumn():�������n����āA1��Ăяo����邱��<br>
     * <br>
     * ����t�ɐݒ肳�ꂽ�N���X�i�͂ݕ������ݒ肳��Ă���ꍇ�j�̃J�����C���f�b�N�X1�̑����l�i�͂ݕ������܂܂�Ȃ��j���擾�ł��邱�Ƃ��m�F����e�X�g�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetColumn02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        CSVFileLineWriter<CSVFileLineWriter_Stub07> lineWriter = new CSVFileLineWriter<CSVFileLineWriter_Stub07>(
                fileName, CSVFileLineWriter_Stub07.class, columnFormatterMap);

        // �����̐ݒ�
        CSVFileLineWriter_Stub07 stub = new CSVFileLineWriter_Stub07();
        stub.setColumn01("abcdef");

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            String result = lineWriter.getColumn(stub, 0);

            // �ԋp�l�̊m�F
            assertEquals("abcdef", result);

            // ��ԕω��̊m�F
            int superCallCount = VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn");
            assertEquals(1, superCallCount);
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, arguments.size());
            assertSame(stub, arguments.get(0));
            assertEquals(0, arguments.get(1));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetColumn03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) t:CSVFileLineWriter_Stub07�C���X�^���X<br>
     * �@@FileFormat(encloseChar='\"')<br>
     * �@String�ϐ�column01<br>
     * �@�@�A�m�e�[�V�����F@OutputFileColumn(columnIndex = 0)<br>
     * �@�@�l�F"ab\"cdef"<br>
     * (����) index:0<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"ab""cdef"<br>
     * (��ԕω�) AbstractFileLineWriter#getColumn():�������n����āA1��Ăяo����邱��<br>
     * <br>
     * ����t�ɐݒ肳�ꂽ�N���X�i�͂ݕ������ݒ肳��Ă���ꍇ�j�̃J�����C���f�b�N�X1�̑����l�i�͂ݕ������܂܂��j���G�X�P�[�v��������āA�擾�ł��邱�Ƃ��m�F����e�X�g�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetColumn03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        CSVFileLineWriter<CSVFileLineWriter_Stub07> lineWriter = new CSVFileLineWriter<CSVFileLineWriter_Stub07>(
                fileName, CSVFileLineWriter_Stub07.class, columnFormatterMap);

        // �����̐ݒ�
        CSVFileLineWriter_Stub07 stub = new CSVFileLineWriter_Stub07();
        UTUtil.setPrivateField(stub, "column01", "ab\"cdef");

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            String result = lineWriter.getColumn(stub, 0);

            // �ԋp�l�̊m�F
            assertEquals("ab\"\"cdef", result);

            // ��ԕω��̊m�F
            int superCallCount = VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn");
            assertEquals(1, superCallCount);
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, arguments.size());
            assertSame(stub, arguments.get(0));
            assertEquals(0, arguments.get(1));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetColumn04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:CSVFileLineWriter_Stub06�C���X�^���X<br>
     * �@@FileFormat()<br>
     * �@String�ϐ�column01<br>
     * �@�@�A�m�e�[�V�����F@OutputFileColumn(columnIndex = 0)<br>
     * �@�@�l�F"abcdef"<br>
     * (����) index:1<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineWriter#getColumn():�������n����āA1��Ăяo����邱��<br>
     * (��ԕω�) ��O:ArrayIndexOutOfBoundsException<br>
     * AbstractFileLineWriter#getColumn()�Ŕ�������<br>
     * <br>
     * ����index�ɃJ�����C���f�b�N�X�ɑ��݂��Ȃ��l��n���ƁAArrayIndexOutOfBoundsException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetColumn04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        CSVFileLineWriter<CSVFileLineWriter_Stub06> lineWriter = new CSVFileLineWriter<CSVFileLineWriter_Stub06>(
                fileName, CSVFileLineWriter_Stub06.class, columnFormatterMap);

        // �����̐ݒ�
        CSVFileLineWriter_Stub06 stub = new CSVFileLineWriter_Stub06();
        UTUtil.setPrivateField(stub, "column01", "abcdef");

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            lineWriter.getColumn(stub, 1);
            fail("ArrayIndexOutOfBoundsException���X���[����܂���ł����B");
        } catch (ArrayIndexOutOfBoundsException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            int superCallCount = VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn");
            assertEquals(1, superCallCount);
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, arguments.size());
            assertSame(stub, arguments.get(0));
            assertEquals(1, arguments.get(1));

            assertEquals(ArrayIndexOutOfBoundsException.class, e.getClass());
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetDelimiter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.delimiter:"','"<br>
     * <br>
     * ���Ғl�F(�߂�l) this.delimiter:"','"<br>
     * <br>
     * delimiter��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetDelimiter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        CSVFileLineWriter<CSVFileLineWriter_Stub04> lineWriter = new CSVFileLineWriter<CSVFileLineWriter_Stub04>(
                fileName, CSVFileLineWriter_Stub04.class, columnFormatterMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �f�t�H���g�ňȉ��ɂȂ��Ă��邽�߁A�������Ȃ�
        // this.delimiter:"','"

        // �e�X�g���{
        try {
            char result = lineWriter.getDelimiter();

            // �ԋp�l�̊m�F
            assertEquals(',', result);

            // ��ԕω��̊m�F
            // �Ȃ�
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetEncloseChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.encloseChar:'\u0000'<br>
     * <br>
     * ���Ғl�F(�߂�l) this.encloseChar:'\u0000'<br>
     * <br>
     * encloseChar��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetEncloseChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        CSVFileLineWriter<CSVFileLineWriter_Stub04> lineWriter = new CSVFileLineWriter<CSVFileLineWriter_Stub04>(
                fileName, CSVFileLineWriter_Stub04.class, columnFormatterMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �f�t�H���g�ňȉ��ɂȂ��Ă��邽�߁A�������Ȃ�
        // this.encloseChar:\u0000'

        // �e�X�g���{
        try {
            char result = lineWriter.getEncloseChar();

            // �ԋp�l�̊m�F
            assertEquals('\u0000', result);

            // ��ԕω��̊m�F
            // �Ȃ�
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * ����n<br>
     * OutputFileColumn��columnEncloseChar�ɂ���āA�X�̃J�����Ɉ͂ݕ�����ݒ�
     * @throws Exception
     */
    @Test
    public void testPrintDataLine01() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        CSVFileLineWriter<CSVFileLine_Stub01> fileLineWriter = new CSVFileLineWriter<CSVFileLine_Stub01>(
                fileName, CSVFileLine_Stub01.class, columnFormatterMap);

        // �O����(����)
        CSVFileLine_Stub01 t1 = new CSVFileLine_Stub01();
        CSVFileLine_Stub01 t2 = new CSVFileLine_Stub01();
        CSVFileLine_Stub01 t3 = new CSVFileLine_Stub01();

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
            assertEquals("\"1\",22,333,|4444|", reader.readLine());
            assertEquals("\"5\",66,777,|8888|", reader.readLine());
            assertEquals("\"9\",AA,BBB,|CCCC|", reader.readLine());
        } finally {
            reader.close();
        }
    }

    /**
     * ����n<br>
     * FileFormat��encloseChar��OutputFileColumn��columnEncloseChar�ɂ���āA�X�̃J�����Ɉ͂ݕ�����ݒ�
     * @throws Exception
     */
    @Test
    public void testPrintDataLine02() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        CSVFileLineWriter<CSVFileLine_Stub02> fileLineWriter = new CSVFileLineWriter<CSVFileLine_Stub02>(
                fileName, CSVFileLine_Stub02.class, columnFormatterMap);

        // �O����(����)
        CSVFileLine_Stub02 t1 = new CSVFileLine_Stub02();
        CSVFileLine_Stub02 t2 = new CSVFileLine_Stub02();
        CSVFileLine_Stub02 t3 = new CSVFileLine_Stub02();

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
            assertEquals("\"1\",'22',\"333\",|4444|", reader.readLine());
            assertEquals("\"5\",'66',\"777\",|8888|", reader.readLine());
            assertEquals("\"9\",'AA',\"BBB\",|CCCC|", reader.readLine());
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

        CSVFileLineWriter<CSVFileLine_Stub03> fileLineWriter = new CSVFileLineWriter<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnFormatterMap);

        // �O����(����)
        CSVFileLine_Stub03 t1 = new CSVFileLine_Stub03();

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
        UTUtil.setPrivateField(fileLineWriter, "delimiter", '_');
        UTUtil.setPrivateField(fileLineWriter, "outputFileColumns", null);
        UTUtil.setPrivateField(fileLineWriter, "columnFormats", new String[] {
                "", "", "", "" });
        UTUtil.setPrivateField(fileLineWriter, "columnBytes", new int[] { -1,
                -1, -1, -1 });
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
            assertEquals("1_22_333_4444", reader.readLine());
        } finally {
            reader.close();
        }
    }
}

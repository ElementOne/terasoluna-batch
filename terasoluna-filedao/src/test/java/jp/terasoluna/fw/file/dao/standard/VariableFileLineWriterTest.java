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
 * {@link jp.terasoluna.fw.file.dao.standard.VariableFileLineWriter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���s�I�u�W�F�N�g����f�[�^��ǂݍ��݁A1�s���̃f�[�^���ϒ��`���� �t�@�C���ɏ������ށB<br>
 * AbstractFileLineWriter�̃T�u�N���X�B
 * <p>
 * @author ���c�N�i
 * @author ���O
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineWriter
 */
public class VariableFileLineWriterTest {

    private static final String TEMP_FILE_NAME = VariableFileLineWriterTest.class
            .getResource("VariableFileLineWriterTest_tmp.txt").getPath();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(VariableFileLineWriterTest.class);
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
     * testVariableFileLineWriter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileName:"(�p�X)VariableFileLineWriter_testVariableFileLineWriter01.txt"<br>
     * (����) clazz:FileFormat�A�m�e�[�V�����������Adelimiter�AencloseChar�������l<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineWriter#init():1��Ă΂�邱��<br>
     * (��ԕω�) AbstractFileLineWriter#AbstractFileLineWriter():1��Ă΂�邱�ƁB<br>
     * �������n���Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�) this.encloseChar:parameterClass�̃A�m�e�[�V����FileFormat��encloseChar()�̒l�B<br>
     * (��ԕω�) this.delimiter:parameterClass�̃A�m�e�[�V����FileFormat��delimiter()�̒l�B<br>
     * <br>
     * ����p�^�[��<br>
     * @FileFormat�����ׂăf�t�H���g�l�̏ꍇ�ɁA�R���X�g���N�^�̌ďo������ɍs���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testVariableFileLineWriter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Class<VariableFileLineWriter_Stub01> clazz = VariableFileLineWriter_Stub01.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        VariableFileLineWriter<VariableFileLineWriter_Stub01> result = null;
        try {
            result = new VariableFileLineWriter<VariableFileLineWriter_Stub01>(
                    fileName, clazz, columnFormatterMap);

            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "init"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "<init>"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "<init>", 0);
            assertEquals(3, arguments.size());
            assertEquals(fileName, arguments.get(0));
            assertEquals(VariableFileLineWriter_Stub01.class, arguments.get(1));
            assertEquals(columnFormatterMap, arguments.get(2));
            assertEquals(Character.MIN_VALUE, UTUtil.getPrivateField(result,
                    "encloseChar"));
            assertEquals(',', UTUtil.getPrivateField(result, "delimiter"));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            if (result != null) {
                result.closeFile();
            }
        }
    }

    /**
     * testVariableFileLineWriter02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileName:"(�p�X)VariableFileLineWriter_testVariableFileLineWriter02.txt"<br>
     * (����) clazz:FileFormat�A�m�e�[�V�����������Adelimiter��'\u0000'�AencloseChar��'\"'<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineWriter#init():�Ă΂�Ȃ����ƁB<br>
     * (��ԕω�) AbstractFileLineWriter#AbstractFileLineWriter():1��Ă΂�邱�ƁB<br>
     * �������n���Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�) ��O:"Delimiter can not use '\u0000'."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException����������B<br>
     * <br>
     * ��O�B@FileFormat��delimiter��'\u0000'��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     * �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testVariableFileLineWriter02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Class<VariableFileLineWriter_Stub04> clazz = VariableFileLineWriter_Stub04.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O������̐ݒ�
        // �Ȃ�

        try {
            // �e�X�g���{
            new VariableFileLineWriter<VariableFileLineWriter_Stub04>(fileName,
                    clazz, columnFormatterMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "init"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "<init>"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "<init>", 0);
            assertEquals(3, arguments.size());
            assertEquals(fileName, arguments.get(0));
            assertEquals(VariableFileLineWriter_Stub04.class, arguments.get(1));
            assertEquals(columnFormatterMap, arguments.get(2));

            assertEquals(FileException.class, e.getClass());
            assertEquals("Delimiter can not use '\\u0000'.", e.getMessage());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testVariableFileLineWriter03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileName:"(�p�X)VariableFileLineWriter_testVariableFileLineWriter03.txt"<br>
     * (����) clazz:FileFormat�A�m�e�[�V�����������Adelimiter��'#'<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineWriter#init():�Ă΂�邱��<br>
     * (��ԕω�) AbstractFileLineWriter#AbstractFileLineWriter():1��Ă΂�邱�ƁB<br>
     * �������n���Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�) this.encloseChar:parameterClass�̃A�m�e�[�V����FileFormat��encloseChar()�̒l�B<br>
     * (��ԕω�) this.delimiter:parameterClass�̃A�m�e�[�V����FileFormat��delimiter()�̒l�B<br>
     * <br>
     * @FileFormat��delimiter��'#'��ݒ�AencloseChar��'"'��ݒ肵���ꍇ�A�R���X�g���N�^�̌ďo������ɍs���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testVariableFileLineWriter03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Class<VariableFileLineWriter_Stub07> clazz = VariableFileLineWriter_Stub07.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O������̐ݒ�
        // �Ȃ�

        VariableFileLineWriter<VariableFileLineWriter_Stub07> result = null;
        try {
            // �e�X�g���{
            result = new VariableFileLineWriter<VariableFileLineWriter_Stub07>(
                    fileName, clazz, columnFormatterMap);

            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "init"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "<init>"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "<init>", 0);
            assertEquals(3, arguments.size());
            assertEquals(fileName, arguments.get(0));
            assertEquals(VariableFileLineWriter_Stub07.class, arguments.get(1));
            assertEquals(columnFormatterMap, arguments.get(2));
            assertEquals('"', UTUtil.getPrivateField(result, "encloseChar"));
            assertEquals('#', UTUtil.getPrivateField(result, "delimiter"));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            if (result != null) {
                result.closeFile();
            }
        }
    }

    /**
     * �ُ�n<br>
     * ���s�����ɋ�؂蕶�����܂܂��
     */
    @Test
    public void testVariableFileLineWriter04() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new VariableFileLineWriter<VariableFileLine_Stub01>(fileName,
                    VariableFileLine_Stub01.class, columnFormatterMap);
            fail();
        } catch (FileException e) {
            assertEquals(
                    "delimiter is the same as lineFeedChar and is no use.", e
                            .getMessage());
            assertEquals(fileName, e.getFileName());
            assertSame(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * �ُ�n<br>
     * ���s�����Ƌ�؂蕶��������
     */
    @Test
    public void testVariableFileLineWriter05() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new VariableFileLineWriter<VariableFileLine_Stub02>(fileName,
                    VariableFileLine_Stub02.class, columnFormatterMap);
            fail();
        } catch (FileException e) {
            assertEquals(
                    "delimiter is the same as lineFeedChar and is no use.", e
                            .getMessage());
            assertEquals(fileName, e.getFileName());
            assertSame(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * �ُ�n<br>
     * �t�@�C���s�I�u�W�F�N�g��OutputFileColumn�A�m�e�[�V����������
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testVariableFileLineWriter06() throws Exception {
        // �����̐ݒ�
        String fileName = TEMP_FILE_NAME;
        Class<FileLineObject_Empty> clazz = FileLineObject_Empty.class;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new VariableFileLineWriter<FileLineObject_Empty>(fileName, clazz,
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
     * testGetColumn01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) t:�ȉ��̃J�����������VariableFileLineWriter�X�^�u<br>
     * "abcdef"<br>
     * (����) index:0<br>
     * (���) this.encloseChar:Charcator.MIN_VALUE<br>
     * <br>
     * ���Ғl�F(�߂�l) String:abcdef<br>
     * (��ԕω�) AbstractFileLineWriter#getColumn():1��Ăяo����邱��<br>
     * �������n����邱��<br>
     * <br>
     * �������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        VariableFileLineWriter<VariableFileLineWriter_Stub05> lineWriter = new VariableFileLineWriter<VariableFileLineWriter_Stub05>(
                fileName, VariableFileLineWriter_Stub05.class,
                columnFormatterMap);

        // �����̐ݒ�
        VariableFileLineWriter_Stub05 t = new VariableFileLineWriter_Stub05();
        t.setColumn01("abcdef");
        int index = 0;

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肳��Ă���B

        try {
            // �e�X�g���{
            String result = lineWriter.getColumn(t, index);
            // �ԋp�l�̊m�F
            assertEquals("abcdef", result);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertSame(t, arguments.get(0));
            assertEquals(index, arguments.get(1));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetColumn02() <br>
     * <br>
     * (����n <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) t:�ȉ��̃J�����������VariableFileLineWriter�X�^�u<br>
     * "abcdef"<br>
     * (����) index:0<br>
     * (���) this.encloseChar:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:abcdef<br>
     * (��ԕω�) AbstractFileLineWriter#getColumn():1��Ăяo����邱��<br>
     * �������n����邱��<br>
     * <br>
     * �������l���擾���邱�Ƃ��m�F����B<br>
     * �o�͑ΏۂɃG�X�P�[�v�Ώۂ̕����i�͂ݕ����j�������ꍇ�͒ʏ�̕��� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        VariableFileLineWriter<VariableFileLineWriter_Stub06> lineWriter = new VariableFileLineWriter<VariableFileLineWriter_Stub06>(
                fileName, VariableFileLineWriter_Stub06.class,
                columnFormatterMap);

        // �����̐ݒ�
        VariableFileLineWriter_Stub06 t = new VariableFileLineWriter_Stub06();
        t.setColumn01("abcdef");
        int index = 0;

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肳��Ă���B

        try {
            // �e�X�g���{
            String result = lineWriter.getColumn(t, index);

            // �ԋp�l�̊m�F
            assertEquals("abcdef", result);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertSame(t, arguments.get(0));
            assertEquals(index, arguments.get(1));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetColumn03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) t:�ȉ��̃J�����������VariableFileLineWriter�X�^�u<br>
     * "ab\"cdef"<br>
     * (����) index:0<br>
     * (���) this.encloseChar:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"ab\"\"cdef"<br>
     * (��ԕω�) AbstractFileLineWriter#getColumn():1��Ăяo����邱��<br>
     * �������n����邱��<br>
     * <br>
     * �������l���擾���邱�Ƃ��m�F����B<br>
     * �o�͑ΏۂɃG�X�P�[�v�Ώۂ̕����i�͂ݕ����j������ꍇ�ɁA�G�X�P�[�v����� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> textGetterMap = new HashMap<String, ColumnFormatter>();
        textGetterMap.put("java.lang.String", new NullColumnFormatter());
        VariableFileLineWriter<VariableFileLineWriter_Stub06> lineWriter = new VariableFileLineWriter<VariableFileLineWriter_Stub06>(
                fileName, VariableFileLineWriter_Stub06.class, textGetterMap);

        // �����̐ݒ�
        VariableFileLineWriter_Stub06 t = new VariableFileLineWriter_Stub06();
        t.setColumn01("ab\"cdef");
        int index = 0;

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肳��Ă���B

        try {
            // �e�X�g���{
            String result = lineWriter.getColumn(t, index);

            // �ԋp�l�̊m�F
            assertEquals("ab\"\"cdef", result);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertSame(t, arguments.get(0));
            assertEquals(index, arguments.get(1));
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetColumn04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:null<br>
     * (����) index:0<br>
     * (���) this.encloseChar:'\"'<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     * <br>
     * ����t��null�ɂ���ƁANullPointerException���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetColumn04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> textGetterMap = new HashMap<String, ColumnFormatter>();
        textGetterMap.put("java.lang.String", new NullColumnFormatter());
        VariableFileLineWriter<VariableFileLineWriter_Stub06> lineWriter = new VariableFileLineWriter<VariableFileLineWriter_Stub06>(
                fileName, VariableFileLineWriter_Stub06.class, textGetterMap);

        // �����̐ݒ�
        VariableFileLineWriter_Stub06 t = null;
        int index = 0;

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肳��Ă���B

        // �e�X�g���{
        try {
            lineWriter.getColumn(t, index);
            fail("NullPointerException���X���[����܂���ł����B");
        } catch (NullPointerException e) {
            assertEquals(NullPointerException.class, e.getClass());
        } finally {
            // �e�X�g�Ώۂ̃N���[�Y����
            lineWriter.closeFile();
        }
    }

    /**
     * testGetColumn05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) t:�ȉ��̃J����������VariableFileLineWriter�X�^�u<br>
     * "abcdef"<br>
     * "aaabbb"<br>
     * (����) index:1<br>
     * (���) this.encloseChar:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:aaabbb<br>
     * (��ԕω�) AbstractFileLineWriter#getColumn():1��Ăяo����邱��<br>
     * �������n����邱��<br>
     * <br>
     * index�̒l�ɕR�Â��J�����̒l�i�͂ݕ����Ȃ��̏ꍇ�j���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String fileName = TEMP_FILE_NAME;

        Map<String, ColumnFormatter> textGetterMap = new HashMap<String, ColumnFormatter>();
        textGetterMap.put("java.lang.String", new NullColumnFormatter());
        VariableFileLineWriter<VariableFileLineWriter_Stub08> lineWriter = new VariableFileLineWriter<VariableFileLineWriter_Stub08>(
                fileName, VariableFileLineWriter_Stub08.class, textGetterMap);

        // �����̐ݒ�
        VariableFileLineWriter_Stub08 t = new VariableFileLineWriter_Stub08();
        t.setColumn01("abcdef");
        t.setColumn02("aaabbb");
        int index = 1;

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肳��Ă���B

        try {
            // �e�X�g���{
            String result = lineWriter.getColumn(t, index);

            // �ԋp�l�̊m�F
            assertEquals("aaabbb", result);

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertSame(t, arguments.get(0));
            assertEquals(index, arguments.get(1));
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
     * ���͒l�F(���) this.delimiter:not null<br>
     * ','<br>
     * <br>
     * ���Ғl�F(�߂�l) delimiter:not null<br>
     * ','<br>
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
        VariableFileLineWriter<VariableFileLineWriter_Stub01> lineWriter = new VariableFileLineWriter<VariableFileLineWriter_Stub01>(
                fileName, VariableFileLineWriter_Stub01.class,
                columnFormatterMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(lineWriter, "delimiter", ',');

        try {
            // �e�X�g���{
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
     * ���͒l�F(���) this.encloseChar:not null<br>
     * '\u0000'<br>
     * <br>
     * ���Ғl�F(�߂�l) encloseChar:not null<br>
     * '\u0000'<br>
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
        VariableFileLineWriter<VariableFileLineWriter_Stub01> lineWriter = new VariableFileLineWriter<VariableFileLineWriter_Stub01>(
                fileName, VariableFileLineWriter_Stub01.class,
                columnFormatterMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(lineWriter, "encloseChar", '\u0000');

        try {
            // �e�X�g���{
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
     * FileFormat��encloseChar��OutputFileColumn��columnEncloseChar�ɂ���āA�X�̃J�����Ɉ͂ݕ�����ݒ�
     * @throws Exception
     */
    @Test
    public void testPrintDataLine01() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        VariableFileLineWriter<CSVFileLine_Stub01> fileLineWriter = new VariableFileLineWriter<CSVFileLine_Stub01>(
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
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        VariableFileLineWriter<CSVFileLine_Stub02> fileLineWriter = new VariableFileLineWriter<CSVFileLine_Stub02>(
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

        VariableFileLineWriter<CSVFileLine_Stub03> fileLineWriter = new VariableFileLineWriter<CSVFileLine_Stub03>(
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

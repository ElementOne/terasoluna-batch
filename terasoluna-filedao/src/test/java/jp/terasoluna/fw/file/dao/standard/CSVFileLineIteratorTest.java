/*
 * $Id: CSVFileLineIteratorTest.java 5654 2007-12-04 06:34:19Z pakucn $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.NullStringConverter;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.CSVFileLineIterator} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> CSV�t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^�擾)�N���X�B
 * <p>
 * @author ���c�N�i
 * @author ���O
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineIterator
 */
public class CSVFileLineIteratorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(CSVFileLineIteratorTest.class);
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
    public CSVFileLineIteratorTest(String name) {
        super(name);
    }

    /**
     * testCSVFileLineIterator01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:CSVFileLineIterator01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:�ȉ��̐ݒ������FileFormat�A�m�e�[�V���������X�^�u<br>
     * CSVFileLineIterator_Stub02<br>
     * �@�A�m�e�[�V����FileFormat�FencloseChar(�͂ݕ���)�������l�ȊO<br>
     * @FileFormat(encloseChar = '"')<br>
     *                         (����) columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                         �v�f1<br>
     *                         key:"java.lang.String"<br>
     *                         value:ColumnParser�C���X�^���X<br>
     *                         CSVFileLineIterator_ColumnParserStub01�C���X�^���X<br>
     *                         �����<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) AbstractFileLineIterator�R���X�g���N�^:1��Ă΂�A���������ׂēn���Ă��邱�ƁB<br>
     *                         (��ԕω�) encloseChar:�����l�ȊO(�ݒ肵������)<br>
     *                         (��ԕω�) AbstractFileLineIterator#init():2��Ă΂��B<br>
     * <br>
     *                         ����p�^�[��<br>
     *                         �R���X�g���N�^�̌ďo������ɍs���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCSVFileLineIterator01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub02> clazz = CSVFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);

        // �O������Ȃ�

        // �e�X�g���{
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub02>(
                fileName, clazz, columnParserMap);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(AbstractFileLineIterator.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnParserMap, arguments.get(2));
        assertEquals('"', csvFileLineIterator.getEncloseChar());
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "init"));
    }

    /**
     * testCSVFileLineIterator02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileName:CSVFileLineIterator01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:�ȉ��̐ݒ������FileFormat�A�m�e�[�V���������X�^�u<br>
     * CSVFileLineIterator_Stub03<br>
     * �@�A�m�e�[�V����FileFormat�Fdelimiter(��؂蕶��)�������l�ȊO<br>
     * @FileFormat(delimiter = '"')<br>
     *                       (����) columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                       �v�f1<br>
     *                       key:"java.lang.String"<br>
     *                       value:ColumnParser�C���X�^���X<br>
     *                       CSVFileLineIterator_ColumnParserStub01�C���X�^���X<br>
     *                       �����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) AbstractFileLineIterator�R���X�g���N�^:1��Ă΂��B<br>
     *                       �����Ɠ����C���X�^���X���n�����B<br>
     *                       (��ԕω�) AbstractFileLineIterator#init():�Ă΂�Ȃ��B<br>
     *                       (��ԕω�) �Ȃ�:"Delimiter can not change."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException����������B<br>
     * <br>
     *                       ��O�B@FileFormat��delimiter�ɏ����l�ȊO��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     *                       �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCSVFileLineIterator02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub03> clazz = CSVFileLineIterator_Stub03.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new CSVFileLineIterator<CSVFileLineIterator_Stub03>(fileName,
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
            assertFalse(VMOUTUtil.isCalled(VariableFileLineIterator.class,
                    "init"));
            assertEquals("Delimiter can not change.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testCSVFileLineIterator03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:CSVFileLineIterator01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:�ȉ��̐ݒ������FileFormat�A�m�e�[�V���������X�^�u<br>
     * CSVFileLineIterator_Stub01<br>
     * �@�A�m�e�[�V����FileFormat�F�����l<br>
     * @FileFormat()<br> (����) columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     *                   �v�f1<br>
     *                   key:"java.lang.String"<br>
     *                   value:ColumnParser�C���X�^���X<br>
     *                   CSVFileLineIterator_ColumnParserStub01�C���X�^���X<br>
     *                   �����<br>
     * <br>
     *                   ���Ғl�F(��ԕω�) AbstractFileLineIterator�R���X�g���N�^:1��Ă΂�A���������ׂēn���Ă��邱�ƁB<br>
     *                   (��ԕω�) encloseChar:�����l���ݒ肳��Ă��邱�ƁB<br>
     * <br>
     *                   ����clazz�ɐݒ肳�ꂽ�N���X���͂ݕ����A��؂蕶�����f�t�H���g�̂܂܂̏ꍇ�́A�R���X�g���N�^�Ăяo��������ɍs���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testCSVFileLineIterator03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub01> clazz = CSVFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);

        // �O������Ȃ�

        // �e�X�g���{
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(AbstractFileLineIterator.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnParserMap, arguments.get(2));
        assertEquals(Character.MIN_VALUE, csvFileLineIterator.getEncloseChar());
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "init"));
    }

    /**
     * �ُ�n<br>
     * �t�@�C���s�I�u�W�F�N�g��InputFileColumn�A�m�e�[�V����������
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCSVFileLineIterator04() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<FileLineObject_Empty> clazz = FileLineObject_Empty.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);

        // �e�X�g���{
        try {
            new CSVFileLineIterator<FileLineObject_Empty>(fileName, clazz,
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
     * testSeparateColumns01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) fileLineString:null<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:new String[0]<br>
     * <br>
     * ����p�^�[���B<br>
     * null�������͋󕶎��������Ƃ��ēn���ꂽ�ꍇ�A�v�f��0�̔z���ԋp���邱�Ƃ��m�F����B<br>
     * �ʏ�̏����ł��̕ԋp�l���߂邱�Ƃ͂Ȃ��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub01> clazz = CSVFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = null;

        // �O�����
        UTUtil.setPrivateField(csvFileLineIterator, "columnEncloseChar",
                new char[] {});

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(0, result.length);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:{"aaa"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �v�f��1�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub01> clazz = CSVFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa";

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, result.length);
        assertEquals(fileLineString, result[0]);
    }

    /**
     * testSeparateColumns03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa,bbb,ccc"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:{"aaa","bbb","ccc"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub01> clazz = CSVFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa,bbb,ccc";

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("bbb", result[1]);
        assertEquals("ccc", result[2]);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aaa\""<br>
     * (���) this.encloseChar:\"'<br>
     * (���) this.fields[]:�z��v�f��0<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:{"aaa"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ���������ꍇ�̏���)<br>
     * �͂ݕ������G�X�P�[�v����A�v�f��1�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub02> clazz = CSVFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub02>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aaa\"";

        // �O�����
        UTUtil.setPrivateField(csvFileLineIterator, "columnEncloseChar",
                new char[] { '\"', '\"' });

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, result.length);
        assertEquals("aaa", result[0]);
    }

    /**
     * testSeparateColumns05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aaa\""<br>
     * (���) this.encloseChar:\"<br>
     * (���) this.fields[]:�z��v�f��1<br>
     * (���) this.field[]�̃A�m�e�[�V����InputFileColumn:@InputFileColumn(columnIndex = 0)<br>
     * private String column1 = null;<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:{"aaa"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �v�f��1�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub04> clazz = CSVFileLineIterator_Stub04.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub04>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aaa\"";

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, result.length);
        assertEquals("aaa", result[0]);
    }

    /**
     * testSeparateColumns06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aaa\",\"bbb\",\"ccc\""<br>
     * (���) this.encloseChar:\"'<br>
     * (���) this.fields[]:�z��v�f��3<br>
     * (���) this.field[]�̃A�m�e�[�V����InputFileColumn:@InputFileColumn(columnIndex = 0)<br>
     * private String column1 = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 1)<br>
     *                              private String column2 = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 2)<br>
     *                              private String column3 = null;<br>
     * <br>
     *                              ���Ғl�F(�߂�l) String[]:{"aaa","bbb","ccc"}<br>
     * <br>
     *                              ����p�^�[���B(�͂ݕ���������ꍇ�̏����B)<br>
     *                              �͂ݕ������G�X�P�[�v����A�v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub05> clazz = CSVFileLineIterator_Stub05.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub05>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aaa\",\"bbb\",\"ccc\"";

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("bbb", result[1]);
        assertEquals("ccc", result[2]);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aa\ra\",\"bb,b\",\"cc\"\"c\""<br>
     * �͂ݕ�������A<br>
     * �s��؂蕶�����܂܂��A<br>
     * ��؂蕶�����܂܂��A<br>
     * �͂ݕ������܂܂��ꍇ<br>
     * (���) this.encloseChar:\"'<br>
     * (���) this.fields[]:�z��v�f��3<br>
     * (���) this.field[]�̃A�m�e�[�V����InputFileColumn:@InputFileColumn(columnIndex = 0)<br>
     * private String column1 = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 1)<br>
     *                              private String column2 = null;<br>
     * <br>
     * @InputFileColumn(columnIndex = 2)<br>
     *                              private String column3 = null;<br>
     * <br>
     *                              ���Ғl�F(�߂�l) String[]:{"aa\ra","bb,b","cc\"c"}<br>
     * <br>
     *                              ����p�^�[���B(�͂ݕ���������ꍇ�̏����B)<br>
     *                              �v�f��3�̔z���ԋp���邱�Ƃ��m�F����B<br>
     *                              ��؂蕶���A�͂ݕ��������ꂼ��G�X�P�[�v����邱�Ƃ��m�F����B�s��؂蕶���̓G�X�P�[�v����Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub05> clazz = CSVFileLineIterator_Stub05.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub05>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aa\ra\",\"bb,b\",\"cc\"\"c\"";

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aa\ra", result[0]);
        assertEquals("bb,b", result[1]);
        assertEquals("cc\"c", result[2]);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa,bbb,ccc"<br>
     * (���) this.encloseChar:\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:{"aaa","bbb","ccc"}<br>
     * <br>
     * ����p�^�[���B(encloseChar���ݒ肳��Ă���A�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub02> clazz = CSVFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub02>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa,bbb,ccc";

        // �O�����
        UTUtil.setPrivateField(csvFileLineIterator, "columnEncloseChar",
                new char[] { Character.MIN_VALUE, Character.MIN_VALUE,
                        Character.MIN_VALUE });

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("bbb", result[1]);
        assertEquals("ccc", result[2]);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) fileLineString:,,,,<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:{"", "", "", "", ""}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �󔒕����T�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub01> clazz = CSVFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = ",,,,";

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(5, result.length);
        assertEquals("", result[0]);
        assertEquals("", result[1]);
        assertEquals("", result[2]);
        assertEquals("", result[3]);
        assertEquals("", result[4]);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) fileLineString:""(�󕶎�)<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:new String[0]<br>
     * <br>
     * �󕶎��������Ƃ��ēn���ꂽ�ꍇ�A�v�f��0�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub01> clazz = CSVFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "";

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(0, result.length);

        // ��ԕω��Ȃ�
    }

    /**
     * testSeparateColumns11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aa\"bb\""<br>
     * (���) this.encloseChar:\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String[]:{"aabb\""}<br>
     * <br>
     * �͂ݕ������ݒ肳��Ă���A�G�X�P�[�v����Ă��Ȃ��͂ݕ����������f�[�^�Ƃ��Ċi�[����Ă���ꍇ�́A�\�����ʃf�[�^���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub02> clazz = CSVFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub02>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aa\"bb\"";

        // �O�����
        UTUtil.setPrivateField(csvFileLineIterator, "columnEncloseChar",
                new char[] { '\"', '\"', '\"' });

        // �e�X�g���{
        String[] result = csvFileLineIterator.separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, result.length);
        assertEquals("aabb\"", result[0]);

        // ��ԕω��Ȃ�
    }

    /**
     * testGetDelimiter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.delimiter:','(�Œ�)<br>
     * <br>
     * ���Ғl�F(�߂�l) char:','(�Œ�)<br>
     * <br>
     * delimiter��getter������ɓ��삷�邱�Ƃ��m�F����B<br>
     * �K���A�J���}��ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetDelimiter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub01> clazz = CSVFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "buildLineReader", null);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        char result = csvFileLineIterator.getDelimiter();

        // �ԋp�l�̊m�F
        assertEquals(',', result);

        // ��ԕω��Ȃ�
    }

    /**
     * testGetEncloseChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.encloseChar:not null<br>
     * '"'<br>
     * <br>
     * ���Ғl�F(�߂�l) char:not null<br>
     * '"'<br>
     * <br>
     * encloseChar��getter������ɓ��삷�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetEncloseChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileLineIterator_Stub02> clazz = CSVFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);
        columnParserMap.put("java.util.Date", columnParser);
        columnParserMap.put("java.math.BigDecimal", columnParser);
        columnParserMap.put("java.lang.int", columnParser);
        CSVFileLineIterator csvFileLineIterator = new CSVFileLineIterator<CSVFileLineIterator_Stub02>(
                fileName, clazz, columnParserMap);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        char result = csvFileLineIterator.getEncloseChar();

        // �ԋp�l�̊m�F
        assertEquals('"', result);

        // ��ԕω��Ȃ�
    }

    /**
     * ����n<br>
     * InputFileColumn��columnEncloseChar�ɂ���āA�X�̃J�����Ɉ͂ݕ�����ݒ�
     * @throws Exception
     */
    public void testNext01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        CSVFileLineIterator<CSVFileLine_Stub01> fileLineIterator = new CSVFileLineIterator<CSVFileLine_Stub01>(
                fileName, CSVFileLine_Stub01.class, columnParserMap);

        // �e�X�g���{
        CSVFileLine_Stub01 result1 = fileLineIterator.next();
        CSVFileLine_Stub01 result2 = fileLineIterator.next();
        CSVFileLine_Stub01 result3 = fileLineIterator.next();

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
     * FileFormat��encloseChar��InputFileColumn��columnEncloseChar�ɂ���āA�J�����Ɉ͂ݕ�����ݒ�
     * @throws Exception
     */
    public void testNext02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        CSVFileLineIterator<CSVFileLine_Stub02> fileLineIterator = new CSVFileLineIterator<CSVFileLine_Stub02>(
                fileName, CSVFileLine_Stub02.class, columnParserMap);

        // �e�X�g���{
        CSVFileLine_Stub02 result1 = fileLineIterator.next();
        CSVFileLine_Stub02 result2 = fileLineIterator.next();
        CSVFileLine_Stub02 result3 = fileLineIterator.next();

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
        URL url = this.getClass().getResource("CsvFileLineIterator_next03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        CSVFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new CSVFileLineIterator<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnParserMap);

        // �t�@�C���s�I�u�W�F�N�g�ɐݒ肵�Ă������l��S�ď㏑��
        // �ȉ��̐ݒ肪�K�p�����΁A�t�@�C���s�I�u�W�F�N�g��
        // �A�m�e�[�V�����ɃA�N�Z�X���Ă��Ȃ����ƂɂȂ�B
        char[] charArray = new char[] { 0, 0, 0, 0 };
        // �O�����
        UTUtil.setPrivateField(fileLineIterator, "lineFeedChar", "\r\n");
        UTUtil.setPrivateField(fileLineIterator, "delimiter", '_');
        UTUtil.setPrivateField(fileLineIterator, "inputFileColumns", null);
        UTUtil.setPrivateField(fileLineIterator, "columnFormats", new String[] {
                "", "", "", "" });
        UTUtil.setPrivateField(fileLineIterator, "columnBytes", new int[] { -1,
                -1, -1, -1 });
        UTUtil.setPrivateField(fileLineIterator, "totalBytes", 0);
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
        CSVFileLine_Stub03 result1 = fileLineIterator.next();
        CSVFileLine_Stub03 result2 = fileLineIterator.next();
        CSVFileLine_Stub03 result3 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("\"1\"", result1.getColumn1());
        assertEquals("22", result1.getColumn2());
        assertEquals("333", result1.getColumn3());
        assertEquals("|4444|", result1.getColumn4());

        assertEquals("\"5\"", result2.getColumn1());
        assertEquals("66", result2.getColumn2());
        assertEquals("777", result2.getColumn3());
        assertEquals("|8888|", result2.getColumn4());

        assertEquals("\"9\"", result3.getColumn1());
        assertEquals("AA", result3.getColumn2());
        assertEquals("BBB", result3.getColumn3());
        assertEquals("|CCCC|", result3.getColumn4());
    }
}

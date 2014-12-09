/*
 * $Id: VariableFileLineIteratorTest.java 5655 2007-12-04 06:41:04Z pakucn $
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
 * {@link jp.terasoluna.fw.file.dao.standard.VariableFileLineIterator} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �ϒ��t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^�擾)�N���X�B
 * <p>
 * @author ���c�N�i
 * @author ���O
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineIterator
 */
public class VariableFileLineIteratorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(VariableFileLineIteratorTest.class);
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
    public VariableFileLineIteratorTest(String name) {
        super(name);
    }

    /**
     * testVariableFileLineIterator01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:File_Empty.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:VariableFileLineIterator_Stub02<br>
     * �@delimiter�AencloseChar�������l�ȊO<br>
     * (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator#AbstractFileLineIterator():1��Ă΂��B<br>
     * �p�����[�^�Ɉ������n����邱�ƁB<br>
     * (��ԕω�) this.delimiter:���͒l<br>
     * (��ԕω�) this.encloseChar:���͒l<br>
     * (��ԕω�) AbstractFileLineIterator#init():1��Ăяo����邱��<br>
     * <br>
     * ����p�^�[���B<br>
     * ����clazz�Ɏw�肳��Ă���N���X�̒l��delimiter�AencloseChar������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testVariableFileLineIterator01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŕs�v

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub02> clazz = VariableFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub02>(
                fileName, clazz, columnParserMap);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        // ����
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "<init>"));

        List arguments = VMOUTUtil.getArguments(AbstractFileLineIterator.class,
                "<init>", 0);
        assertEquals(3, arguments.size());
        assertEquals(fileName, arguments.get(0));
        assertSame(clazz, arguments.get(1));
        assertSame(columnParserMap, arguments.get(2));

        assertEquals('"', variableFileLineIterator.getEncloseChar());
        assertEquals('�A', variableFileLineIterator.getDelimiter());

        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "init"));
    }

    /**
     * testVariableFileLineIterator02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileName:File_Empty.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:VariableFileLineIterator_Stub03<br>
     * �@delimiter��'\u0000'����encloseChar�������l�i\u0000�j�ȊO<br>
     * (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator#AbstractFileLineIterator():1��Ă΂��B<br>
     * �p�����[�^�Ɉ������n����邱�ƁB<br>
     * (��ԕω�) ��O:"Delimiter can not use '\u0000'."�̃��b�Z�[�W�AIllegalStateException�A�t�@�C����������FileException����������B<br>
     * <br>
     * ��O�B@FileFormat��delimiter��'\u0000'��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B<br>
     * �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testVariableFileLineIterator02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŕs�v

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub03> clazz = VariableFileLineIterator_Stub03.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            new VariableFileLineIterator<VariableFileLineIterator_Stub03>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            // ����
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "<init>"));

            List arguments = VMOUTUtil.getArguments(
                    AbstractFileLineIterator.class, "<init>", 0);
            assertEquals(3, arguments.size());
            assertEquals(fileName, arguments.get(0));
            assertSame(clazz, arguments.get(1));
            assertSame(columnParserMap, arguments.get(2));

            assertEquals("Delimiter can not use '\\u0000'.", e.getMessage());
            assertEquals(fileName, e.getFileName());
            assertSame(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testVariableFileLineIterator03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:File_Empty.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:VariableFileLineIterator_Stub01<br>
     * �@delimiter�AencloseChar�������l<br>
     * (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser.java<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator#AbstractFileLineIterator():1��Ă΂��B<br>
     * �p�����[�^�Ɉ������n����邱�ƁB<br>
     * (��ԕω�) this.delimiter:�����l<br>
     * (��ԕω�) this.encloseChar:�����l<br>
     * (��ԕω�) AbstractFileLineIterator#init():1��Ăяo����邱��<br>
     * <br>
     * ����clazz�Ŏw�肵���N���X��@FileFormat��delimiter�AencloseChar�������l�̏ꍇ�́Adelimiter�AencloseChar�������l�̂܂܂ł��邱�Ƃ̃e�X�g�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testVariableFileLineIterator03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŕs�v

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub01> clazz = VariableFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        // ����
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "<init>"));

        List arguments = VMOUTUtil.getArguments(AbstractFileLineIterator.class,
                "<init>", 0);
        assertEquals(3, arguments.size());
        assertEquals(fileName, arguments.get(0));
        assertSame(clazz, arguments.get(1));
        assertSame(columnParserMap, arguments.get(2));

        assertEquals(Character.MIN_VALUE, variableFileLineIterator
                .getEncloseChar());
        assertEquals(',', variableFileLineIterator.getDelimiter());

        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "init"));
    }

    /**
     * �ُ�n<br>
     * ���s�����ɋ�؂蕶�����܂܂��
     */
    public void testVariableFileLineIterator04() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();

        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �e�X�g���{
        try {
            new VariableFileLineIterator<VariableFileLine_Stub01>(fileName,
                    VariableFileLine_Stub01.class, columnParserMap);
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
    public void testVariableFileLineIterator05() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();

        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �e�X�g���{
        try {
            new VariableFileLineIterator<VariableFileLine_Stub02>(fileName,
                    VariableFileLine_Stub02.class, columnParserMap);
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
     * �t�@�C���s�I�u�W�F�N�g��InputFileColumn�A�m�e�[�V����������
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testVariableFileLineIterator06() throws Exception {
        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<FileLineObject_Empty> clazz = FileLineObject_Empty.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        ColumnParser columnParser = new CSVFileLineIterator_ColumnParserStub01();
        columnParserMap.put("java.lang.String", columnParser);

        // �e�X�g���{
        try {
            new VariableFileLineIterator<FileLineObject_Empty>(fileName, clazz,
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
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:new String[0]<br>
     * <br>
     * ����p�^�[���B<br>
     * null�������Ƃ��ēn���ꂽ�ꍇ�A�v�f��0�̔z���ԋp���邱�Ƃ��m�F����B<br>
     * �ʏ�̏����ł��̕ԋp�l���߂邱�Ƃ͂Ȃ��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub10> clazz = VariableFileLineIterator_Stub10.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub10>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = null;

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(0, result.length);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa"}<br>
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
        Class<VariableFileLineIterator_Stub10> clazz = VariableFileLineIterator_Stub10.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub10>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, result.length);
        assertEquals("aaa", result[0]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa,aaa,aaa"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa","aaa","aaa"}<br>
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
        Class<VariableFileLineIterator_Stub10> clazz = VariableFileLineIterator_Stub10.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub10>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa,aaa,aaa";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("aaa", result[1]);
        assertEquals("aaa", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aaa\""<br>
     * (���) this.encloseChar:'\"'<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ���������ꍇ�̏���)<br>
     * �v�f��1�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub11> clazz = VariableFileLineIterator_Stub11.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub11>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aaa\"";

        // �O�����
        UTUtil.setPrivateField(variableFileLineIterator, "columnEncloseChar",
                new char[] { '\"', '\"' });

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, result.length);
        assertEquals("aaa", result[0]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aaa\",\"aaa\",\"aaa\""<br>
     * (���) this.encloseChar:'\"'<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa","aaa","aaa"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ���������ꍇ�̏����B)<br>
     * �v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub11> clazz = VariableFileLineIterator_Stub11.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub11>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aaa\",\"aaa\",\"aaa\"";

        // �O�����
        UTUtil.setPrivateField(variableFileLineIterator, "columnEncloseChar",
                new char[] { '\"', '\"', '\"' });

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("aaa", result[1]);
        assertEquals("aaa", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aa\ra\",\"aa,a\",\"aa\"\"a\""<br>
     * �͂ݕ�������A<br>
     * �s��؂蕶�����܂܂��A<br>
     * ��؂蕶�����܂܂��A<br>
     * �͂ݕ������܂܂��ꍇ<br>
     * (���) this.encloseChar:'\"'<br>
     * (���) super.lineFeedChar:\r<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aa\ra","aa,a","aa\"a"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ���������ꍇ�̏����B)<br>
     * �v�f��4�̔z���ԋp���邱�Ƃ��m�F����B<br>
     * �s��؂蕶���A��؂蕶���A�͂ݕ��������ꂼ������̕������f�g�܂�Ă���ꍇ�A�G�X�P�[�v����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub12> clazz = VariableFileLineIterator_Stub12.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub12>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aa\ra\",\"aa,a\",\"aa\"\"a\"";

        // �O�����
        UTUtil.setPrivateField(variableFileLineIterator, "columnEncloseChar",
                new char[] { '\"', '\"', '\"', '\"', '\"', '\"', '\"', '\"' });

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aa\ra", result[0]);
        assertEquals("aa,a", result[1]);
        assertEquals("aa\"a", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) fileLineString:�󕶎�<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:new String[0]<br>
     * <br>
     * ����p�^�[���B<br>
     * �󕶎��������Ƃ��ēn���ꂽ�ꍇ�A�v�f��0�̔z���ԋp���邱�Ƃ��m�F����B<br>
     * �ʏ�̏����ł��̕ԋp�l���߂邱�Ƃ͂Ȃ��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub13> clazz = VariableFileLineIterator_Stub13.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub13>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(0, result.length);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa,aaa,aaa"<br>
     * (���) this.encloseChar:'\"'<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa","aaa","aaa"}<br>
     * <br>
     * ����p�^�[���B(encloseChar���ݒ肳��Ă���A��؂蕶�����Ȃ��ꍇ�̏���)<br>
     * �v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub11> clazz = VariableFileLineIterator_Stub11.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub11>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa,aaa,aaa";

        // �O�����
        UTUtil.setPrivateField(variableFileLineIterator, "columnEncloseChar",
                new char[] { Character.MIN_VALUE, Character.MIN_VALUE,
                        Character.MIN_VALUE });

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("aaa", result[1]);
        assertEquals("aaa", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:",,,,"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"", "", "", "", ""}<br>
     * <br>
     * ����p�^�[���B(��؂蕶�����Ȃ��ꍇ�̏���)<br>
     * �󔒕����T�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub10> clazz = VariableFileLineIterator_Stub10.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub10>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = ",,,,";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(5, result.length);
        assertEquals("", result[0]);
        assertEquals("", result[1]);
        assertEquals("", result[2]);
        assertEquals("", result[3]);
        assertEquals("", result[4]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa#aaa#aaa"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * (���) delimiter:'#'<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa","aaa","aaa"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * ��؂蕶�����f�t�H���g�ȊO�̂��̂ɐݒ肵���ꍇ�ł��A�v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub14> clazz = VariableFileLineIterator_Stub14.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub14>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa#aaa#aaa";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("aaa", result[1]);
        assertEquals("aaa", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:",bbb,ccc"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"","bbb","ccc"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �󕶎����擪�ɂ���ꍇ�ł��A�v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub10> clazz = VariableFileLineIterator_Stub10.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub10>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = ",bbb,ccc";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("", result[0]);
        assertEquals("bbb", result[1]);
        assertEquals("ccc", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns12() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa,,ccc"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa","","ccc"}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �󕶎����r���Ɋ܂܂��ꍇ�ł��A�v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns12() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub10> clazz = VariableFileLineIterator_Stub10.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub10>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa,,ccc";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("", result[1]);
        assertEquals("ccc", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns13() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"aaa,bbb,"<br>
     * (���) this.encloseChar:Character.MIN_VALUE<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aaa","bbb",""}<br>
     * <br>
     * ����p�^�[���B(�͂ݕ������Ȃ��ꍇ�̏���)<br>
     * �󕶎����Ō�Ɋ܂܂��ꍇ�ł��A�v�f��3�̔z���ԋp���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns13() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub10> clazz = VariableFileLineIterator_Stub10.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub10>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa,bbb,";

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(3, result.length);
        assertEquals("aaa", result[0]);
        assertEquals("bbb", result[1]);
        assertEquals("", result[2]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testSeparateColumns14() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) fileLineString:"\"aa\"bb\""<br>
     * (���) this.encloseChar:'\"'<br>
     * (���) delimiter:','<br>
     * <br>
     * ���Ғl�F(�߂�l) columns[]:{"aabb\""}<br>
     * <br>
     * �͂ݕ������ݒ肳��Ă���A�G�X�P�[�v����Ă��Ȃ��͂ݕ����������f�[�^�Ƃ��Ċi�[����Ă���ꍇ�́A�\�����ʃf�[�^���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testSeparateColumns14() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub11> clazz = VariableFileLineIterator_Stub11.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub11>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "\"aa\"bb\"";

        // �O�����
        UTUtil.setPrivateField(variableFileLineIterator, "columnEncloseChar",
                new char[] { '\"', '\"' });

        // �e�X�g���{
        String[] result = variableFileLineIterator
                .separateColumns(fileLineString);

        // �ԋp�l�̊m�F
        assertEquals(1, result.length);
        assertEquals("aabb\"", result[0]);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetEncloseChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) this.encloseChar:not null<br>
     * 'a'<br>
     * <br>
     * ���Ғl�F(�߂�l) encloseChar:not null<br>
     * 'a'<br>
     * <br>
     * encloseChar��getter������ɓ��삷�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetEncloseChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileLineIterator_Stub20> clazz = VariableFileLineIterator_Stub20.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        VariableFileLineIterator variableFileLineIterator = new VariableFileLineIterator<VariableFileLineIterator_Stub20>(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        char result = variableFileLineIterator.getEncloseChar();

        // �ԋp�l�̊m�F
        assertEquals('a', result);

        // ��ԕω��̊m�F
        // �Ȃ�
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

        VariableFileLineIterator<CSVFileLine_Stub01> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub01>(
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

        VariableFileLineIterator<CSVFileLine_Stub02> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub02>(
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
        VariableFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub03>(
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

    /**
     * testGetEncloseCharcter001.
     * @throws Exception
     */
    public void testGetEncloseCharcter001() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        VariableFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnParserMap);

        char[] columnEncloseChar = new char[] {};
        int index = 0;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(fileLineIterator,
                "getEncloseCharcter", char[].class, int.class,
                columnEncloseChar, index);

        assertNotNull(result);
        assertEquals(Character.class, result.getClass());
        assertEquals(Character.valueOf('c'), (Character) result);
    }

    /**
     * testGetEncloseCharcter002.
     * @throws Exception
     */
    public void testGetEncloseCharcter002() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        VariableFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnParserMap);

        char[] columnEncloseChar = new char[] { 'A', 'B', 'C' };
        int index = 0;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(fileLineIterator,
                "getEncloseCharcter", char[].class, int.class,
                columnEncloseChar, index);

        assertNotNull(result);
        assertEquals(Character.class, result.getClass());
        assertEquals(Character.valueOf('A'), (Character) result);
    }

    /**
     * testGetEncloseCharcter003.
     * @throws Exception
     */
    public void testGetEncloseCharcter003() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        VariableFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnParserMap);

        char[] columnEncloseChar = new char[] { 'A', 'B', 'C' };
        int index = 1;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(fileLineIterator,
                "getEncloseCharcter", char[].class, int.class,
                columnEncloseChar, index);

        assertNotNull(result);
        assertEquals(Character.class, result.getClass());
        assertEquals(Character.valueOf('B'), (Character) result);
    }

    /**
     * testGetEncloseCharcter004.
     * @throws Exception
     */
    public void testGetEncloseCharcter004() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        VariableFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnParserMap);

        char[] columnEncloseChar = new char[] { 'A', 'B', 'C' };
        int index = 2;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(fileLineIterator,
                "getEncloseCharcter", char[].class, int.class,
                columnEncloseChar, index);

        assertNotNull(result);
        assertEquals(Character.class, result.getClass());
        assertEquals(Character.valueOf('C'), (Character) result);
    }

    /**
     * testGetEncloseCharcter005.
     * @throws Exception
     */
    public void testGetEncloseCharcter005() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        VariableFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnParserMap);

        char[] columnEncloseChar = new char[] { 'A', 'B', 'C' };
        int index = 3;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(fileLineIterator,
                "getEncloseCharcter", char[].class, int.class,
                columnEncloseChar, index);

        assertNotNull(result);
        assertEquals(Character.class, result.getClass());
        assertEquals(Character.valueOf('c'), (Character) result);
    }

    /**
     * testGetEncloseCharcter006.
     * @throws Exception
     */
    public void testGetEncloseCharcter006() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �l�X�Ȑݒ肪����Ă���t�@�C���s�I�u�W�F�N�g��ݒ�
        VariableFileLineIterator<CSVFileLine_Stub03> fileLineIterator = new VariableFileLineIterator<CSVFileLine_Stub03>(
                fileName, CSVFileLine_Stub03.class, columnParserMap);

        char[] columnEncloseChar = new char[] { 'A', 'B', 'C' };
        int index = 4;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(fileLineIterator,
                "getEncloseCharcter", char[].class, int.class,
                columnEncloseChar, index);

        assertNotNull(result);
        assertEquals(Character.class, result.getClass());
        assertEquals(Character.valueOf('c'), (Character) result);
    }
}

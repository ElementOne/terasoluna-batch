/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.PlainFileLineIterator} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���s�I�u�W�F�N�g��p���Ȃ��t�@�C���Ǎ��N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineIterator
 */
public class PlainFileLineIteratorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(PlainFileLineIteratorTest.class);
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
    public PlainFileLineIteratorTest(String name) {
        super(name);
    }

    /**
     * testPlainFileLineIterator01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) PlainFileLineIterator01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:PlainFileLineIterator_Stub01<br>
     * �@@FileFormat�̐ݒ�L��A���ׂď����l�B<br>
     * (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator#AbstractFileLineIterator():1��Ăяo����邱�Ƃ��m�F����B<br>
     * �Ăяo�����Ƃ��̈������A����fileName,clazz,columnParserMap�Ɠ����C���X�^���X�ł��邱��<br>
     * (��ԕω�) AbstractFileLineIterator#init():1��Ăяo����邱�ƁB<br>
     * <br>
     * �e�N���X�̃R���X�g���N�^���Ă΂�A�e�N���X��init���\�b�h�����s����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testPlainFileLineIterator01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŕs�v

        // �����̐ݒ�
        URL url = this.getClass().getResource("PlainFileLineIterator01.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub01> clazz = PlainFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        new PlainFileLineIterator(fileName, clazz, columnParserMap);

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

        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "init"));
    }

    /**
     * testSeparateColumns01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileLineString:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:UnSupportedOperationException<br>
     * ���b�Z�[�W�F"separateColumns(String) isn't supported."<br>
     * <br>
     * ���̃N���X�ł�separateColumns���T�|�[�g���Ă��Ȃ��B����āAUnSupportedOperationException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSeparateColumns01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("PlainFileLineIterator01.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub01> clazz = PlainFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        String fileLineString = "aaa";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            plainFileLineIterator.separateColumns(fileLineString);
            fail("UnSupportedOperationException���X���[����܂���ł���");
        } catch (UnsupportedOperationException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            // ����
            assertSame(UnsupportedOperationException.class, e.getClass());
            assertEquals("separateColumns(String) isn't supported.", e
                    .getMessage());
        }
    }

    /**
     * testNext01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_next01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * �P�s�ڃf�[�^<br>
     * -------------------<br>
     * ������f�[�^<br>
     * (���) this.readTrailer:false<br>
     * <br>
     * ���Ғl�F(�߂�l) String:String�C���X�^���X<br>
     * �ireadLine�̌��ʁj<br>
     * (��ԕω�) AbstractFileLineIterator#readLine():1��Ă΂��<br>
     * (��ԕω�) currentLineCount:1<br>
     * <br>
     * hasNext���b�\�h��TRUE�ɂȂ��Ă���ꍇreadLine���b�\�h���Ă΂�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("PlainFileLineIterator01.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub01> clazz = PlainFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        String result = plainFileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("1�s�ڃf�[�^", result);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "readLine"));
        assertEquals(1, UTUtil.getPrivateField(plainFileLineIterator,
                "currentLineCount"));
    }

    /**
     * testNext02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FF.G <br>
     * <br>
     * ���͒l�F(���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_next02.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * ��<br>
     * -------------------<br>
     * ������f�[�^<br>
     * (���) this.readTrailer:false<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator#readLine():�Ă΂�Ȃ�<br>
     * (��ԕω�) currentLineCount:0<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"The data which can be acquired doesn't exist."<br>
     * �E������O�FNoSuchElementException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * �E�s�ԍ��F0<br>
     * �E�J�������Fnull<br>
     * �E�J�����C���f�b�N�X�F-1<br>
     * <br>
     * hasNext���b�\�h��FALSE�ɂȂ��Ă���ꍇ<br>
     * NoSuchElementException���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub01> clazz = PlainFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            plainFileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            assertSame(FileLineException.class, e.getClass());
            assertEquals("The data which can be acquired doesn't exist.", e
                    .getMessage());
            assertSame(NoSuchElementException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals(1, e.getLineNo());
            assertNull(e.getColumnName());
            assertEquals(-1, e.getColumnIndex());

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, UTUtil.getPrivateField(plainFileLineIterator,
                    "currentLineCount"));

        }
    }

    /**
     * testNext03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FF.G <br>
     * <br>
     * ���͒l�F(���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_next03.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * 1�s�ڃf�[�^<br>
     * 2�s�ڃf�[�^<br>
     * -------------------<br>
     * ������f�[�^<br>
     * (���) this.readTrailer:true<br>
     * <br>
     * ���Ғl�F(��ԕω�) AbstractFileLineIterator#readLine():�Ă΂�Ȃ�<br>
     * (��ԕω�) currentLineCount:0<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Data part should be called before trailer part."<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * �E�s�ԍ��F0<br>
     * <br>
     * hasNext���b�\�h��FALSE�ɂȂ��Ă���ꍇ<br>
     * NoSuchElementException���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("PlainFileLineIterator03.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub03> clazz = PlainFileLineIterator_Stub03.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(plainFileLineIterator, "readTrailer", true);

        // �e�X�g���{
        try {
            plainFileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            assertSame(FileLineException.class, e.getClass());
            assertEquals("Data part should be called before trailer part.", e
                    .getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals(0, e.getLineNo());

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, UTUtil.getPrivateField(plainFileLineIterator,
                    "currentLineCount"));

        }
    }

    /**
     * ����n<br>
     * FileFormat��encloseChar��delimiter���ݒ肳��Ă��Ă��A��������
     * @throws Exception
     */
    public void testNext04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("CsvFileLineIterator_next01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        PlainFileLineIterator fileLineIterator = new PlainFileLineIterator(
                fileName, PlainFileLineIterator_Stub02.class, columnParserMap);

        // �e�X�g���{
        String result1 = fileLineIterator.next();
        String result2 = fileLineIterator.next();
        String result3 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("\"1\",22,333,|4444|", result1);
        assertEquals("\"5\",66,777,|8888|", result2);
        assertEquals("\"9\",AA,BBB,|CCCC|", result3);
    }

    /**
     * testSkipint01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(����) skipLines:0<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_skip01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * 3�s�ڃf�[�^<br>
     * -------------------<br>
     * (���) this.readLine():������s<br>
     * (���) this.currentLineCount:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.readLine:�Ă΂�Ȃ�<br>
     * (��ԕω�) this.currentLineCount:0<br>
     * <br>
     * ����p�^�[���B<br>
     * Skip�Ώۍs���Ȃ��ꍇ�A���̂܂ܐ���I�����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSkipint01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = PlainFileLineIteratorTest.class
                .getResource("PlainFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        PlainFileLineIterator fileLineIterator = new PlainFileLineIterator(
                fileName, PlainFileLineIterator_Stub01.class, columnParserMap);
        UTUtil.setPrivateField(fileLineIterator, "currentLineCount", 0);

        // �����̐ݒ�
        int skipLines = 0;

        // �O������̐ݒ�
        fileLineIterator.init();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        fileLineIterator.skip(skipLines);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "readLine"));
        assertEquals(0, UTUtil.getPrivateField(fileLineIterator,
                "currentLineCount"));
    }

    /**
     * testSkipint02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(����) skipLines:1<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_skip01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * 3�s�ڃf�[�^<br>
     * -------------------<br>
     * (���) this.readLine():������s<br>
     * (���) this.currentLineCount:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.readLine:1��Ă΂��<br>
     * (��ԕω�) this.currentLineCount:1<br>
     * <br>
     * ����p�^�[���B<br>
     * Skip�Ώۍs���P�s�̏ꍇ�A�Ώۃf�[�^���P�s�ǂނ��Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSkipint02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = AbstractFileLineIteratorTest.class
                .getResource("PlainFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        PlainFileLineIterator fileLineIterator = new PlainFileLineIterator(
                fileName, PlainFileLineIterator_Stub01.class, columnParserMap);
        UTUtil.setPrivateField(fileLineIterator, "currentLineCount", 0);

        // �����̐ݒ�
        int skipLines = 1;

        // �O������̐ݒ�
        fileLineIterator.init();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        fileLineIterator.skip(skipLines);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "readLine"));
        assertEquals(1, UTUtil.getPrivateField(fileLineIterator,
                "currentLineCount"));
    }

    /**
     * testSkipint03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(����) skipLines:3<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_skip01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * 3�s�ڃf�[�^<br>
     * -------------------<br>
     * (���) this.readLine():������s<br>
     * (���) this.currentLineCount:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.readLine:3��Ă΂��<br>
     * (��ԕω�) this.currentLineCount:3<br>
     * <br>
     * ����p�^�[���B<br>
     * Skip�Ώۍs���R�s�̏ꍇ�A�Ώۃf�[�^���R�s�ǂނ��Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSkipint03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = AbstractFileLineIteratorTest.class
                .getResource("PlainFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        PlainFileLineIterator fileLineIterator = new PlainFileLineIterator(
                fileName, PlainFileLineIterator_Stub01.class, columnParserMap);
        UTUtil.setPrivateField(fileLineIterator, "currentLineCount", 0);

        // �����̐ݒ�
        int skipLines = 3;

        // �O������̐ݒ�
        fileLineIterator.init();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        fileLineIterator.skip(skipLines);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(3, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "readLine"));
        assertEquals(3, UTUtil.getPrivateField(fileLineIterator,
                "currentLineCount"));
    }

    /**
     * testSkipint04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) skipLines:1<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_skip01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * 3�s�ڃf�[�^<br>
     * -------------------<br>
     * (���) this.readLine():FileException��O�𔭐�����B<br>
     * (���) this.currentLineCount:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.currentLineCount:0<br>
     * (��ԕω�) �Ȃ�:this.readLine()�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B<br>
     * �Ώۃf�[�^��ǂޏ����ŗ�O�����������ꍇ�A���̗�O�����̂܂ܕԂ���邱�Ƃ��m�F����B�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSkipint04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = AbstractFileLineIteratorTest.class
                .getResource("PlainFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        PlainFileLineIterator fileLineIterator = new PlainFileLineIterator(
                fileName, PlainFileLineIterator_Stub01.class, columnParserMap);
        UTUtil.setPrivateField(fileLineIterator, "currentLineCount", 0);

        // �����̐ݒ�
        int skipLines = 1;

        // �O������̐ݒ�
        fileLineIterator.init();
        FileException exception = new FileException("readLine����̗�O�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "readLine", exception);
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            fileLineIterator.skip(skipLines);
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // ��O�̊m�F
            assertSame(exception, e);

            // ��ԕω��̊m�F
            assertEquals(0, UTUtil.getPrivateField(fileLineIterator,
                    "currentLineCount"));
        }
    }

    /**
     * testSkipint05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) skipLines:100<br>
     * �������Ώۃt�@�C���̍s���𒴂���ݒ�<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"PlainFileLineIterator_skip01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * 3�s�ڃf�[�^<br>
     * -------------------<br>
     * (���) this.readLine():������s<br>
     * (���) this.currentLineCount:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.readLine:3��Ă΂��<br>
     * (��ԕω�) this.currentLineCount:3<br>
     * (��ԕω�) �Ȃ�:�ȉ��̐ݒ������FileLineException����������B<br>
     * �E���b�Z�[�W�F"The data which can be acquired doesn't exist."<br>
     * �E������O�FNoSuchElementException<br>
     * �E�t�@�C�����F�����Ώۃt�@�C����<br>
     * �E�s�ԍ��F4<br>
     * <br>
     * ��O�B<br>
     * Skip�Ώۍs�̐����Ώۃf�[�^�̐����z����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSkipint05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = AbstractFileLineIteratorTest.class
                .getResource("PlainFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        PlainFileLineIterator fileLineIterator = new PlainFileLineIterator(
                fileName, PlainFileLineIterator_Stub01.class, columnParserMap);
        UTUtil.setPrivateField(fileLineIterator, "currentLineCount", 0);

        // �����̐ݒ�
        int skipLines = 100;

        // �O������̐ݒ�
        fileLineIterator.init();

        // �e�X�g���{
        try {
            fileLineIterator.skip(skipLines);
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // ��O�̊m�F
            assertEquals("The data which can be acquired doesn't exist.", e
                    .getMessage());
            assertTrue(NoSuchElementException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());
            assertEquals(4, e.getLineNo());

            // ��ԕω��̊m�F
            assertEquals(3, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(3, UTUtil.getPrivateField(fileLineIterator,
                    "currentLineCount"));
        }
    }

    /**
     * testGetDelimiter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) delimiter:'#'<br>
     * <br>
     * ���Ғl�F(�߂�l) char:','<br>
     * <br>
     * delimiter��getter������ɓ��삷�邱�Ƃ��m�F����B<br>
     * �K��','���ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDelimiter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("PlainFileLineIterator01.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub02> clazz = PlainFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        char result = plainFileLineIterator.getDelimiter();

        // �ԋp�l�̊m�F
        assertEquals(',', result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetEncloseChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) encloseChar:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) char:Character.MIN_VALUE<br>
     * <br>
     * encloseChar��getter������ɓ��삷�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetEncloseChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("PlainFileLineIterator01.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub02> clazz = PlainFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        char result = plainFileLineIterator.getEncloseChar();

        // �ԋp�l�̊m�F
        assertEquals(Character.MIN_VALUE, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetCurrentLineCount01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) currentLineCount:5<br>
     * <br>
     * ���Ғl�F(�߂�l) int:5<br>
     * <br>
     * currentLineCount��getter������ɓ��삷�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCurrentLineCount01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("PlainFileLineIterator01.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub02> clazz = PlainFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(plainFileLineIterator, "currentLineCount", 5);
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        int result = plainFileLineIterator.getCurrentLineCount();

        // �ԋp�l�̊m�F
        assertEquals(5, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetTrailer01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) readTrailer:false<br>
     * <br>
     * ���Ғl�F(�߂�l) List<String>:super#getTrailer()�̖߂�l<br>
     * �g���C���f�[�^<br>
     * (��ԕω�) readTrailer:true<br>
     * <br>
     * getTrailer()���ĂԂ��Ƃɂ���āA�g���C���f�[�^���擾���A�g���C���������m�F�p�t���O��True�ɕω����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTrailer01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("PlainFileLineIterator03.txt");
        String fileName = url.getPath();
        Class<PlainFileLineIterator_Stub03> clazz = PlainFileLineIterator_Stub03.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        PlainFileLineIterator plainFileLineIterator = new PlainFileLineIterator(
                fileName, clazz, columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        List<String> trailer = plainFileLineIterator.getTrailer();

        // �ԋp�l�̊m�F
        assertEquals(1, trailer.size());
        assertEquals("�g���C���f�[�^", trailer.get(0));

        // ��ԕω��̊m�F
        assertTrue((Boolean) UTUtil.getPrivateField(plainFileLineIterator,
                "readTrailer"));
    }
}

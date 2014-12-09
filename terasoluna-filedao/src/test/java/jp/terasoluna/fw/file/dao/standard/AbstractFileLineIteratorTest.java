/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.NullStringConverter;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.annotation.StringConverterToLowerCase;
import jp.terasoluna.fw.file.annotation.StringConverterToUpperCase;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.AbstractFileLineIterator} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���A�N�Z�X(�ǎ�)�̃X�[�p�[�N���X�B���ۃN���X�̂��߁AAbstractFileLineIteratorImpl�N���X���쐬���Ď��������{����B
 * <p>
 * @author ���c�N�i
 * @author ���O
 * @see jp.terasoluna.fw.file.dao.standard.AbstractFileLineIterator
 */
public class AbstractFileLineIteratorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(AbstractFileLineIteratorTest.class
        // .getClass());
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
    public AbstractFileLineIteratorTest(String name) {
        super(name);
    }

    /**
     * testAbstractFileLineIteratorStringClassMap01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��01"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     *                       - fileEncoding�F"MS932"(�f�t�H���g�l�ȊO)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                       �E"int"=IntColumnParser<br>
     *                       �E"java.lang.String"=NullColumnParser.java<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) this.fileName:����fileName�Ɠ����C���X�^���X<br>
     *                       "AbstractFileLineIterator_��init��01"<br>
     *                       (��ԕω�) this.clazz:����clazz�̂Ɠ����C���X�^���X<br>
     *                       (��ԕω�) lineFeedChar:"\r"<br>
     *                       (��ԕω�) fileEncoding:"MS932"<br>
     *                       (��ԕω�) headerLineCount:1<br>
     *                       (��ԕω�) trailerLineCount:1<br>
     *                       (��ԕω�) this.columnParserMap:����columnParserMap�����C���X�^���X<br>
     *                       (��ԕω�) ��O:�Ȃ�<br>
     * <br>
     *                       ����p�^�[���B<br>
     *                       �����ɐݒ肳�ꂽ���ɂ��AbstractFileLineIterator�N���X������������Đ�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAbstractFileLineIteratorStringClassMap01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��01";
        Class<AbstractFileLineIterator_Stub01> clazz = AbstractFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, clazz, columnParserMap);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(fileName, UTUtil.getPrivateField(fileLineIterator,
                "fileName"));
        assertSame(AbstractFileLineIterator_Stub01.class, UTUtil
                .getPrivateField(fileLineIterator, "clazz"));
        assertEquals("\r", UTUtil.getPrivateField(fileLineIterator,
                "lineFeedChar"));
        assertEquals("MS932", UTUtil.getPrivateField(fileLineIterator,
                "fileEncoding"));
        assertEquals(1, UTUtil.getPrivateField(fileLineIterator,
                "headerLineCount"));
        assertEquals(1, UTUtil.getPrivateField(fileLineIterator,
                "trailerLineCount"));
        assertSame(columnParserMap, UTUtil.getPrivateField(fileLineIterator,
                "columnParserMap"));
    }

    /**
     * testAbstractFileLineIteratorStringClassMap02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:null<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     *                       - fileEncoding�F"MS932"(�f�t�H���g�l�ȊO)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                       �E"int"=IntColumnParser<br>
     *                       �E"java.lang.String"=NullColumnParser.java<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                       �E���b�Z�[�W�F"fileName is required."<br>
     *                       �E������O�FIllegalArgumentException<br>
     *                       �E�t�@�C�����Fnull<br>
     * <br>
     *                       ��O�B<br>
     *                       �t�@�C�������ݒ肳��Ă��Ȃ�(null)�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAbstractFileLineIteratorStringClassMap02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = null;
        Class<AbstractFileLineIterator_Stub01> clazz = AbstractFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("fileName is required.", e.getMessage());
            assertSame(IllegalArgumentException.class, e.getCause().getClass());
            assertNull(e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��03"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ�����ĂȂ�<br> (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                         �E"int"=IntColumnParser<br>
     *                         �E"java.lang.String"=NullColumnParser.java<br>
     *                         (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                         �@�����<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                         �E���b�Z�[�W�F"FileFormat annotation is not found."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                         ��O�B<br>
     *                         ����clazz�ɓn���ꂽ�N���X�C���X�^���X�ɁA@FileFormat�̐ݒ肪���݂��Ȃ��ꍇ�́A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractFileLineIteratorStringClassMap03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��03";
        Class<AbstractFileLineIterator_Stub02> clazz = AbstractFileLineIterator_Stub02.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub02>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("FileFormat annotation is not found.", e.getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,E <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��04"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F""(�󕶎�)<br>
     *                       - fileEncoding�F""(�󕶎�)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                       �E"int"=IntColumnParser<br>
     *                       �E"java.lang.String"=NullColumnParser.java<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) this.fileName:����fileName�Ɠ����C���X�^���X<br>
     *                       "AbstractFileLineIterator_��init��04"<br>
     *                       (��ԕω�) this.clazz:����clazz�̂Ɠ����C���X�^���X<br>
     *                       (��ԕω�) lineFeedChar:�V�X�e���̍s��؂蕶��<br>
     *                       System.getProperty("line.separator")<br>
     *                       (��ԕω�) fileEncoding:�V�X�e���̃t�@�C���G���R�[�f�B���O<br>
     *                       System.getProperty("file.encoding")<br>
     *                       (��ԕω�) headerLineCount:1<br>
     *                       (��ԕω�) trailerLineCount:1<br>
     *                       (��ԕω�) this.columnParserMap:����columnParserMap�����C���X�^���X<br>
     * <br>
     *                       ����p�^�[���B<br>
     *                       ����clazz�ɓn���ꂽ�N���X�C���X�^���X�́�FileFormat�ɁulineFeedChar�v�ƁufileEncoding�v���󕶎��Őݒ肳��Ă���ꍇ�AAbstractFileLineIterator�N���X��this
     *                       .lineFeddChar��this.fileEncoding���V�X�e���f�t�H���g�l�ŏ���������Đ�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractFileLineIteratorStringClassMap04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��04";
        Class<AbstractFileLineIterator_Stub03> clazz = AbstractFileLineIterator_Stub03.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub03> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub03>(
                fileName, clazz, columnParserMap);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(fileName, UTUtil.getPrivateField(fileLineIterator,
                "fileName"));
        assertSame(AbstractFileLineIterator_Stub03.class, UTUtil
                .getPrivateField(fileLineIterator, "clazz"));
        assertSame(System.getProperty("line.separator"), UTUtil
                .getPrivateField(fileLineIterator, "lineFeedChar"));
        assertSame(System.getProperty("file.encoding"), UTUtil.getPrivateField(
                fileLineIterator, "fileEncoding"));
        assertEquals(1, UTUtil.getPrivateField(fileLineIterator,
                "headerLineCount"));
        assertEquals(1, UTUtil.getPrivateField(fileLineIterator,
                "trailerLineCount"));
        assertSame(columnParserMap, UTUtil.getPrivateField(fileLineIterator,
                "columnParserMap"));
    }

    /**
     * testAbstractFileLineIteratorStringClassMap05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��05"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F";"(encloseChar�Ɠ����l)<br>
     *                       - encloseChar�F";"(delimiter�Ɠ����l)<br>
     *                       - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     *                       - fileEncoding�F"MS932"(�f�t�H���g�l�ȊO)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                       �E"int"=IntColumnParser<br>
     *                       �E"java.lang.String"=NullColumnParser.java<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) lineFeedChar:I8<br>
     *                       (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                       �E���b�Z�[�W�F"Delimiter is the same as EncloseChar and is no use."<br>
     *                       �E������O�FIllegalStateException<br>
     *                       �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                       ��O�B<br>
     *                       ����clazz�ɓn���ꂽ�N���X�C���X�^���X��@FileForma�Ɂudelimiter�v�ƁuencloseChar�v�������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractFileLineIteratorStringClassMap05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��05";
        Class<AbstractFileLineIterator_Stub04> clazz = AbstractFileLineIterator_Stub04.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub04>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("Delimiter is the same as EncloseChar and is no use.",
                    e.getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * ""<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     *                       - fileEncoding�F"MS932"(�f�t�H���g�l�ȊO)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                       �E"int"=IntColumnParser<br>
     *                       �E"java.lang.String"=NullColumnParser.java<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                       �E���b�Z�[�W�F"fileName is required."<br>
     *                       �E������O�FIllegalArgumentException<br>
     *                       �E�t�@�C�����F""(�󕶎�)<br>
     * <br>
     *                       ��O�B<br>
     *                       �t�@�C�������󕶎��̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAbstractFileLineIteratorStringClassMap06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "";
        Class<AbstractFileLineIterator_Stub01> clazz = AbstractFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("fileName is required.", e.getMessage());
            assertSame(IllegalArgumentException.class, e.getCause().getClass());
            assertEquals("", e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��07"<br>
     * (����) clazz:null<br>
     * (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     * �E"int"=IntColumnParser<br>
     * �E"java.lang.String"=NullColumnParser.java<br>
     * (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"clazz is required."<br>
     * �E������O�FIllegalArgumentException<br>
     * �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ����clazz���unull�v�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAbstractFileLineIteratorStringClassMap07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��07";
        Class<AbstractFileLineIterator_Stub01> clazz = null;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("clazz is required.", e.getMessage());
            assertSame(IllegalArgumentException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��08"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     *                       - fileEncoding�F"MS932"(�f�t�H���g�l�ȊO)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       (����) columnParserMap:null<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                       �E���b�Z�[�W�F"columnFormaterMap is required."<br>
     *                       �E������O�FIllegalArgumentException<br>
     *                       �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                       ��O�B<br>
     *                       ����columnParserMap���unull�v�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAbstractFileLineIteratorStringClassMap08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��08";
        Class<AbstractFileLineIterator_Stub01> clazz = AbstractFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = null;

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("columnFormaterMap is required.", e.getMessage());
            assertSame(IllegalArgumentException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��11"<br>
     * (����) clazz:�ȉ��̐ݒ��������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F""(�󕶎�)<br>
     *                       - fileEncoding�F""(�󕶎�)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     * <br>
     *                       �����ۃN���X�ł���B<br>
     *                       (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                       �E"int"=IntColumnParser<br>
     *                       �E"java.lang.String"=NullColumnParser.java<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                       �E���b�Z�[�W�F"Failed in instantiation of clazz."<br>
     *                       �E������O�FInstantiationException<br>
     *                       �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                       ��O�B<br>
     *                       �C���X�^���X���ł��Ȃ�Class������Clazz�ɐݒ肳�ꂽ�ꍇ�ɗ�O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractFileLineIteratorStringClassMap09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��11";
        Class<AbstractFileLineIterator_Stub05> clazz = AbstractFileLineIterator_Stub05.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub05>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("Failed in instantiation of clazz.", e.getMessage());
            assertSame(InstantiationException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��12"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F""(�󕶎�)<br>
     *                       - fileEncoding�F""(�󕶎�)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     * <br>
     *                       ���f�t�H���g�R���X�g���N�^���uprivate�v�Ő錾����Ă���B<br>
     *                       (����) columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     *                       �E"int"=IntColumnParser<br>
     *                       �E"java.lang.String"=NullColumnParser.java<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                       �E���b�Z�[�W�F"clazz's nullary  constructor is not accessible"<br>
     *                       �E������O�FIllegalAccessException<br>
     *                       �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                       ��O�B<br>
     *                       �f�t�H���g�R���X�g���N�^�̒��ڃA�N�Z�X���o���Ȃ�Class������Clazz�ɐݒ肳�ꂽ�ꍇ�ɗ�O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractFileLineIteratorStringClassMap10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��12";
        Class<AbstractFileLineIterator_Stub06> clazz = AbstractFileLineIterator_Stub06.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub06>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("clazz's nullary  constructor is not accessible", e
                    .getMessage());
            assertSame(IllegalAccessException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineIteratorStringClassMap11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_��init��13"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * @FileFormat�̐ݒ������<br> - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     *                       - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     *                       - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     *                       - fileEncoding�F"MS932"(�f�t�H���g�l�ȊO)<br>
     *                       - headerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       - trailerLineCount�F1(�f�t�H���g�l�ȊO)<br>
     *                       (����) columnParserMap:�v�f�������Ȃ�Map<String, ColumnParser>�C���X�^���X<br>
     *                       (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     *                       �@�����<br>
     * <br>
     *                       ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                       �E���b�Z�[�W�F"columnFormaterMap is required."<br>
     *                       �E������O�FIllegalArgumentException<br>
     *                       �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                       ��O�B<br>
     *                       ����columnParserMap�͂��邪�A����Map�ɗv�f�������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAbstractFileLineIteratorStringClassMap11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String fileName = "AbstractFileLineIterator_��init��13";
        Class<AbstractFileLineIterator_Stub01> clazz = AbstractFileLineIterator_Stub01.class;
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "init", null);

        // �e�X�g���{
        try {
            new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                    fileName, clazz, columnParserMap);
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("columnFormaterMap is required.", e.getMessage());
            assertSame(IllegalArgumentException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testHasNext01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.reader:not null<br>
     * Reader�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_hasNext01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * 1�s��<br>
     * -------------------<br>
     * ������f�[�^<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * (��ԕω�) reader.ready():1��Ă΂��<br>
     * <br>
     * ����p�^�[��<br>
     * �t�B�[���hreader���玟�̍s�̃��R�[�h�̎擾���\�ȏꍇ��true���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNext01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_hasNext01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        boolean result = fileLineIterator.hasNext();

        // �ԋp�l�̊m�F
        assertTrue(result);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(BufferedReader.class, "read"));
    }

    /**
     * testHasNext02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.reader:not null<br>
     * Reader�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_hasNext02.txt"<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_hasNext02.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * ��<br>
     * -------------------<br>
     * ������f�[�^<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     * (��ԕω�) reader.ready():1��Ă΂��<br>
     * <br>
     * ����p�^�[��<br>
     * �t�B�[���hreader���玟�̍s�̃��R�[�h�̎擾���s�\�ȏꍇ��false���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNext02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_hasNext02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        boolean result = fileLineIterator.hasNext();

        // �ԋp�l�̊m�F
        assertFalse(result);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(BufferedReader.class, "read"));
    }

    /**
     * testHasNext03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.reader:not null<br>
     * Reader�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_hasNext02.txt"<br>
     * (���) reader.ready():IOException�𔭐�����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) reader.ready():1��Ă΂��<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Processing of reader was failed."<br>
     * �E������O�FIOException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ���ʃN���X���ݒ肳��Ă��Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNext03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_hasNext02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        IOException exception = new IOException();
        VMOUTUtil.setExceptionAtAllTimes(BufferedReader.class, "read",
                exception);

        // �e�X�g���{
        try {
            fileLineIterator.hasNext();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil
                    .getCallCount(BufferedReader.class, "read"));

            assertSame(FileException.class, e.getClass());
            assertEquals("Processing of reader was failed.", e.getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testHasNext04() <br>
     * <br>
     * (�ُ�n) <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.reader:not null<br>
     * Reader�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * <br>
     * (���) reader.ready():IOException�𔭐�����B<br>
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Processing of reader#reset was failed."<br>
     * �E������O�FIOException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ���ʃN���X���ݒ肳��Ă��Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNext04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        IOException exception = new IOException();
        VMOUTUtil.setExceptionAtAllTimes(BufferedReader.class, "reset",
                exception);

        // �e�X�g���{
        try {
            fileLineIterator.hasNext();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F

            assertSame(FileException.class, e.getClass());
            assertEquals("Processing of reader#reset was failed.", e
                    .getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testNext01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�C���^�t�F�[�X�Ȃǂ̃C���X�^���X���ł��Ȃ�Class�̃C���X�^���X<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_next06.txt"<br>
     * (���) this.currentLineCount:0<br>
     * (���) this.readTrailer:false<br>
     * (���) this.isCheckByte():true<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.hasNext():2��Ă΂��<br>
     * ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     * (��ԕω�) this.readLine():1��Ă΂��<br>
     * (��ԕω�) this.separateColumns(String):�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Failed in an instantiate of a FileLineObject."<br>
     * �E������O�FInstantiationException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * �t�B�[���hclazz�ɃC���X�^���X���ł��Ȃ��N���X���ݒ肳�ꂽ�ꍇ�AFileException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next06.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "clazz",
                AbstractFileLineIterator_Stub14.class);
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, VMOUTUtil.getCallCount(fileLineIterator.getClass(),
                    "separateColumns"));

            assertSame(FileException.class, e.getClass());
            assertEquals("Failed in an instantiate of a FileLineObject.", e
                    .getMessage());
            assertSame(InstantiationException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testNext02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�f�t�H���g�R���X�g���N�^�̒��ڎ��s���s�\(private�錾)��Class�̃C���X�^���X<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_next06.txt"<br>
     * (���) this.currentLineCount:0<br>
     * (���) this.readTrailer:false<br>
     * (���) this.isCheckByte():true<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.hasNext():2��Ă΂��<br>
     * ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     * (��ԕω�) this.readLine():1��Ă΂��<br>
     * (��ԕω�) this.separateColumns(String):�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Failed in an instantiate of a FileLineObject."<br>
     * �E������O�FIllegalAccessException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * �t�B�[���hclazz�̃R���X�g���N�^�����ڎ��s�ł��Ȃ��悤�ɐݒ肳��Ă���ꍇ�AFileException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next06.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "clazz",
                AbstractFileLineIterator_Stub15.class);
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, VMOUTUtil.getCallCount(fileLineIterator.getClass(),
                    "separateColumns"));

            assertSame(FileException.class, e.getClass());
            assertEquals("Failed in an instantiate of a FileLineObject.", e
                    .getMessage());
            assertSame(IllegalAccessException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testNext03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h����сA@InputFileColumn�̐ݒ�͂Ȃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_next06.txt"<br>
     * (���) this.currentLineCount:0<br>
     * (���) this.readTrailer:false<br>
     * (���) this.isCheckByte():true<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next06.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * ��s<br>
     * ��s<br>
     * ��s<br>
     * -------------------<br>
     * ������f�[�^<br>
     * <br>
     * ���Ғl�F(�߂�l) this.clazz.getClass():this.clazz�Őݒ肳��Ă���N���X�̃C���X�^���X<br>
     * (��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):�Ă΂�Ȃ�<br>
     * (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     * (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.hasNext():2��Ă΂��<br>
     * ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     * (��ԕω�) this.readLine():1��Ă΂��<br>
     * (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     * (��ԕω�) this.currentLineCount:1<br>
     * <br>
     * �t�@�C������ǂݎ�����J�������󕶎��ŁA�t�@�C���s�I�u�W�F�N�gclazz�Ƀt�B�[���h���Ȃ��ꍇ�́A�t�@�C���s�I�u�W�F�N�g���擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next06.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub08> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub08>(
                fileName, AbstractFileLineIterator_Stub08.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        Object result = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertSame(AbstractFileLineIterator_Stub08.class, result.getClass());

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class, "parse"));
        assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class, "parse"));
        assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                "parse"));
        assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class, "parse"));
        assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                AbstractFileLineIterator.class, "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }
        assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                fileLineIterator, "currentLineCount"));
    }

    /**
     * testNext04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FF,G <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肪�Ȃ��t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) this.currentLineCount:0<br>
     * (���) this.readTrailer:false<br>
     * (���) this.isCheckByte():true<br>
     * (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_bulidFields01.txt"�t�@�C�������݂���B<br>
     * -------------------<br>
     * test<br>
     * -------------------<br>
     * ������f�[�^<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):�Ă΂�Ȃ�<br>
     * (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     * (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     * (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.hasNext():2��Ă΂��<br>
     * ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     * (��ԕω�) this.readLine():1��Ă΂��<br>
     * (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     * (��ԕω�) this.currentLineCount:0<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Column Count is different from FileLineObject's column counts"<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * �E�s�ԍ��F1<br>
     * �E�J�������Fnull<br>
     * �E�J�����C���f�b�N�X�F-1<br>
     * <br>
     * �t�@�C������ǂݎ�����J�������ƃt�@�C���s�I�u�W�F�N�g�̃J������������Ȃ��ꍇ�ɁAFileLineException���������邱�Ƃ��m�F����B<br>
     * �����ł́A�t�@�C������ǂݎ�����J������1���݂��A�t�@�C���s�I�u�W�F�N�gclazz��@InputFileColumn��`���Ȃ��t�B�[���h�݂̂���ꍇ���������Ă���B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next08.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("int", new IntColumnParser());
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub08> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub08>(
                fileName, AbstractFileLineIterator_Stub08.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(FileLineException.class, e.getClass());
            assertEquals(
                    "Column Count is different from FileLineObject's column counts",
                    e.getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertNull(e.getColumnName());
            assertEquals(-1, e.getColumnIndex());
        }
    }

    /**
     * testNext05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next09.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next09.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(�߂�l) this.clazz.getClass():this.clazz�Őݒ肳��Ă���N���X�̃C���X�^���X<br>
     *                        - column1�F1<br>
     *                        - column2�F"line1"<br>
     *                        - column3�F111111<br>
     *                        - column4�F1980/01/21<br>
     *                        (��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):4��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):4��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) DateColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) DecimalColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():1��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:1<br>
     * <br>
     *                        ����B(�o�C�g���`�F�b�N����)<br>
     *                        �Ώۃt�@�C���̓��e���������ݒ肳�ꂽ�t�@�C���s�I�u�W�F�N�g���擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next09.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        AbstractFileLineIterator_Stub09 result = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("getColumn1", 1, result.getColumn1());
        assertEquals("line1", result.getColumn2());
        assertEquals(new BigDecimal(111111), result.getColumn3());
        Calendar column4 = new GregorianCalendar();
        column4.set(1980, 0, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result.getColumn4()
                .toString());

        // ��ԕω��̊m�F
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals("NullColumnParser", 1, VMOUTUtil.getCallCount(
                NullColumnParser.class, "parse"));
        assertEquals("DateColumnParser", 1, VMOUTUtil.getCallCount(
                DateColumnParser.class, "parse"));
        assertEquals("DecimalColumnParser", 1, VMOUTUtil.getCallCount(
                DecimalColumnParser.class, "parse"));
        assertEquals("", 1, VMOUTUtil.getCallCount(IntColumnParser.class,
                "parse"));
        assertEquals(4, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                AbstractFileLineIterator.class, "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                fileLineIterator, "currentLineCount"));
    }

    /**
     * testNext06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE,G <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > bytes�F5<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next12.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next12.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        ABCDE,1234<br>
     *                        -------------------<br>
     *                        �������ُ�f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):1��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):1��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():1��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Data size is different from a set point of a column."<br>
     *                        �E������O�FIllegalStateException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F1<br>
     *                        �E�J�������Fcolumn2<br>
     *                        �E�J�����C���f�b�N�X�F1<br>
     * <br>
     *                        ��O�B<br>
     *                        �t�B�[���hclazz��@InputFileColumn��bytes��`��������͂��ꂽ�t�@�C���̏��bytes�ݒ�ɂ����ĂȂ��ꍇ�AFileLineException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next12.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub10> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub10>(
                fileName, AbstractFileLineIterator_Stub10.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals("FileDAOUtility:trim", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "trim"));
            assertEquals("FileDAOUtility:padding", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "padding"));
            assertEquals("NullColumnParser", 1, VMOUTUtil.getCallCount(
                    NullColumnParser.class, "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals("NullStringConverter", 1, VMOUTUtil.getCallCount(
                    NullStringConverter.class, "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(FileLineException.class, e.getClass());
            assertEquals(
                    "Data size is different from a set point of a column.", e
                            .getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals("getColumnIndex", 1, e.getColumnIndex());
        }
    }

    /**
     * testNext07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > bytes�F5<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next13.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next13.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        ABCDE,12345<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(�߂�l) this.clazz.getClass():this.clazz�Őݒ肳��Ă���N���X�̃C���X�^���X<br>
     *                        - column1�F"ABCDE"<br>
     *                        - column2�F"12345"<br>
     *                        (��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):2��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):2��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():2��Ă΂��<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():2��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:1<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn��bytes��`��������͂��ꂽ�t�@�C���̏��bytes�ݒ�ɂ����Ă���ꍇ�A�Ώۃt�@�C���̓��e���������ݒ肳�ꂽ�t�@�C���s�I�u�W�F�N�g���擾����邱�Ƃ��m�F����
     *                        �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next13.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub10> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub10>(
                fileName, AbstractFileLineIterator_Stub10.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        AbstractFileLineIterator_Stub10 result = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("ABCDE", result.getColumn1());
        assertEquals("12345", result.getColumn2());

        // ��ԕω��̊m�F
        assertEquals(2, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(2, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals(2, VMOUTUtil.getCallCount(NullColumnParser.class, "parse"));
        assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class, "parse"));
        assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                "parse"));
        assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class, "parse"));
        assertEquals(2, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                AbstractFileLineIterator.class, "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                fileLineIterator, "currentLineCount"));
    }

    /**
     * testNext08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next15.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=ColumnParser�X�^�u(���s����Ɨ�O�𓊂���B)<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) ColumnParser.parse():IllegalArgumentException��O������<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next15.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,ABCDE<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):2��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):2��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():2��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Failed in coluomn data parsing."<br>
     *                        �E������O�FllegalArgumentException<br>
     *                        (ColumnParser.parse()�Ŕ���������O)<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F1<br>
     *                        �E�J�������Fcolumn2<br>
     *                        �E�J�����C���f�b�N�X�F1<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C������擾�����f�[�^���t�B�[���h�̌^�ɂ��킹�ăp�[�Y���鏈����llegalArgumentException�����������ꍇ�AFileLineException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next15.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String",
                new AbstractFileLineIterator_ColumnParserStub01());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11>(
                fileName, AbstractFileLineIterator_Stub11.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(2, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals("IntColumnParser", 1, VMOUTUtil.getCallCount(
                    IntColumnParser.class, "parse"));
            assertEquals(2, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("AbstractFileLineIterator", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(FileLineException.class, e.getClass());
            assertEquals("Failed in coluomn data parsing.", e.getMessage());
            assertSame(IllegalArgumentException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals("getColumnIndex", 1, e.getColumnIndex());
        }
    }

    /**
     * testNext09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next15.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=ColumnParser�X�^�u(���s����Ɨ�O�𓊂���B)<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) ColumnParser.parse():IllegalAccessException��O������<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next15.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,ABCDE<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):2��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):2��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():2��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Failed in coluomn data parsing."<br>
     *                        �E������O�FIllegalAccessException<br>
     *                        (ColumnParser.parse()�Ŕ���������O)<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F1<br>
     *                        �E�J�������Fcolumn2<br>
     *                        �E�J�����C���f�b�N�X�F1<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C������擾�����f�[�^���t�B�[���h�̌^�ɂ��킹�ăp�[�Y���鏈����IllegalAccessException�����������ꍇ�AFileLineException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next15.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String",
                new AbstractFileLineIterator_ColumnParserStub02());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11>(
                fileName, AbstractFileLineIterator_Stub11.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(2, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals("IntColumnParser", 1, VMOUTUtil.getCallCount(
                    IntColumnParser.class, "parse"));
            assertEquals(2, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("AbstractFileLineIterator", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(FileLineException.class, e.getClass());
            assertEquals("Failed in coluomn data parsing.", e.getMessage());
            assertSame(IllegalAccessException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals("getColumnIndex", 1, e.getColumnIndex());
        }
    }

    /**
     * testNext10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next15.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=ColumnParser�X�^�u(���s����Ɨ�O�𓊂���B)<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) ColumnParser.parse():InvocationTargetException��O������<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next15.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,ABCDE<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):2��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):2��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():2��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Failed in coluomn data parsing."<br>
     *                        �E������O�FInvocationTargetException<br>
     *                        (ColumnParser.parse()�Ŕ���������O)<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F1<br>
     *                        �E�J�������Fcolumn2<br>
     *                        �E�J�����C���f�b�N�X�F1<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C������擾�����f�[�^���t�B�[���h�̌^�ɂ��킹�ăp�[�Y���鏈����InvocationTargetException�����������ꍇ�AFileLineException���������邱�Ƃ��m�F����B
     * <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next15.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String",
                new AbstractFileLineIterator_ColumnParserStub03());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11>(
                fileName, AbstractFileLineIterator_Stub11.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(2, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals("IntColumnParser", 1, VMOUTUtil.getCallCount(
                    IntColumnParser.class, "parse"));
            assertEquals(2, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("AbstractFileLineIterator", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(FileLineException.class, e.getClass());
            assertEquals("Failed in coluomn data parsing.", e.getMessage());
            assertSame(InvocationTargetException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals("getColumnIndex", 1, e.getColumnIndex());
        }
    }

    /**
     * testNext11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next15.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=ColumnParser�X�^�u(���s����Ɨ�O�𓊂���B)<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) ColumnParser.parse():ParsrException��O����<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next15.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,ABCDE<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):2��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):2��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():2��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Failed in coluomn data parsing."<br>
     *                        �E������O�FParsrException<br>
     *                        (ColumnParser.parse()�Ŕ���������O)<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F1<br>
     *                        �E�J�������Fcolumn2<br>
     *                        �E�J�����C���f�b�N�X�F1<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C������擾�����f�[�^���t�B�[���h�̌^�ɂ��킹�ăp�[�Y���鏈����ParsrException�����������ꍇ�AFileLineException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next15.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String",
                new AbstractFileLineIterator_ColumnParserStub04());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub11>(
                fileName, AbstractFileLineIterator_Stub11.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł���");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(2, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals("IntColumnParser", 1, VMOUTUtil.getCallCount(
                    IntColumnParser.class, "parse"));
            assertEquals(2, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(FileLineException.class, e.getClass());
            assertEquals("Failed in coluomn data parsing.", e.getMessage());
            assertSame(ParseException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals("getColumnIndex", 1, e.getColumnIndex());
        }
    }

    /**
     * testNext12() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next19.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next19.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        2,line2,222222,1980/02/21<br>
     *                        3,line3,333333,1980/03/21<br>
     *                        -------------------<br>
     *                        ����������f�[�^<br>
     * <br>
     *                        ���Ғl�F(�߂�l) this.clazz.getClass():this.clazz�Őݒ肳��Ă���N���X�̃C���X�^���X<br>
     *                        �E1��ڂ̎��s����<br>
     *                        - column1�F1<br>
     *                        - column2�F"line1"<br>
     *                        - column3�F111111<br>
     *                        - column4�F1980/01/21<br>
     * <br>
     *                        �E2��ڂ̎��s����<br>
     *                        - column1�F2<br>
     *                        - column2�F"line2"<br>
     *                        - column3�F222222<br>
     *                        - column4�F1980/02/21<br>
     * <br>
     *                        �E3��ڂ̎��s����<br>
     *                        - column1�F3<br>
     *                        - column2�F"line3"<br>
     *                        - column3�F333333<br>
     *                        - column4�F1980/03/21<br>
     *                        (��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) DateColumnParser#parse():1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) DecimalColumnParser#parse():1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():1��̎��s����2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:�E1��ڎ��s��F1<br>
     *                        �E2��ڎ��s��F2<br>
     *                        �E3��ڎ��s��F3<br>
     * <br>
     *                        ����B<br>
     *                        �Ώۃt�@�C���ɂ��镡���s�̏�񂪊e1�s�ɑ΂���next()���\�b�h���Ăԓx�ɐ������ݒ肳�ꂽ�t�@�C���s�I�u�W�F�N�g���擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext12() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next19.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{�i1��ځj
        AbstractFileLineIterator_Stub09 result = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("getColumn1", 1, result.getColumn1());
        assertEquals("line1", result.getColumn2());
        assertEquals(new BigDecimal(111111), result.getColumn3());
        Calendar column4 = new GregorianCalendar();
        column4.set(1980, 0, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result.getColumn4()
                .toString());

        // ��ԕω��̊m�F
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals("NullColumnParser", 1, VMOUTUtil.getCallCount(
                NullColumnParser.class, "parse"));
        assertEquals("DateColumnParser", 1, VMOUTUtil.getCallCount(
                DateColumnParser.class, "parse"));
        assertEquals("DecimalColumnParser", 1, VMOUTUtil.getCallCount(
                DecimalColumnParser.class, "parse"));
        assertEquals("IntColumnParser", 1, VMOUTUtil.getCallCount(
                IntColumnParser.class, "parse"));
        assertEquals(4, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals("AbstractFileLineIterator:hasNext", 2, VMOUTUtil
                .getCallCount(AbstractFileLineIterator.class, "hasNext"));
        assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                AbstractFileLineIterator.class, "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                fileLineIterator, "currentLineCount"));

        // �e�X�g���{�i2��ځj
        AbstractFileLineIterator_Stub09 result02 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("getColumn1", 2, result02.getColumn1());
        assertEquals("line2", result02.getColumn2());
        assertEquals(new BigDecimal(222222), result02.getColumn3());
        column4.set(1980, 1, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result02.getColumn4()
                .toString());

        // ��ԕω��̊m�F�i�Ăяo���񐔂�1��ځ{����ɂȂ�j
        assertEquals(8, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(8, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals("NullColumnParser", 2, VMOUTUtil.getCallCount(
                NullColumnParser.class, "parse"));
        assertEquals("DateColumnParser", 2, VMOUTUtil.getCallCount(
                DateColumnParser.class, "parse"));
        assertEquals("DecimalColumnParser", 2, VMOUTUtil.getCallCount(
                DecimalColumnParser.class, "parse"));
        assertEquals("IntColumnParser", 2, VMOUTUtil.getCallCount(
                IntColumnParser.class, "parse"));
        assertEquals(8, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(4, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals("AbstractFileLineIterator", 2, VMOUTUtil.getCallCount(
                AbstractFileLineIterator.class, "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 2, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals("currentLineCount", 2, UTUtil.getPrivateField(
                fileLineIterator, "currentLineCount"));

        // �e�X�g���{�i3��ځj
        AbstractFileLineIterator_Stub09 result03 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals(3, result03.getColumn1());
        assertEquals("line3", result03.getColumn2());
        assertEquals(new BigDecimal(333333), result03.getColumn3());
        column4.set(1980, 2, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result03.getColumn4()
                .toString());

        // ��ԕω��̊m�F�i�Ăяo���񐔂�1��ځ{2��ځ{����ɂȂ�j
        assertEquals(12, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(12, VMOUTUtil
                .getCallCount(FileDAOUtility.class, "padding"));
        assertEquals(3, VMOUTUtil.getCallCount(NullColumnParser.class, "parse"));
        assertEquals(3, VMOUTUtil.getCallCount(DateColumnParser.class, "parse"));
        assertEquals(3, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                "parse"));
        assertEquals(3, VMOUTUtil.getCallCount(IntColumnParser.class, "parse"));
        assertEquals(12, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(6, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals(3, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 3, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals(3, UTUtil.getPrivateField(fileLineIterator,
                "currentLineCount"));
    }

    /**
     * testNext13() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "File_Empty.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"File_Empty.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        ��<br>
     *                        -------------------<br>
     *                        ��hasNext()��false�ɂȂ�f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.hasNext():1��Ă΂��<br>
     *                        (��ԕω�) this.readLine():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.separateColumns(String):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"The data which can be acquired doesn't exist."<br>
     *                        �E������O�FNoSuchElementException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F0<br>
     *                        �E�J�������Fnull<br>
     *                        �E�J�����C���f�b�N�X�F-1<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C���̓��e����̏ꍇ�i�t�@�C���ɓǂ߂�f�[�^���Ȃ��ꍇ�j�ɁAFileLineException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext13() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next20.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub12> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub12>(
                fileName, AbstractFileLineIterator_Stub12.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł����B");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals("hasNext", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, VMOUTUtil.getCallCount(fileLineIterator.getClass(),
                    "separateColumns"));
            assertEquals(0, UTUtil.getPrivateField(fileLineIterator,
                    "currentLineCount"));

            assertEquals(FileLineException.class, e.getClass());
            assertEquals("The data which can be acquired doesn't exist.", e
                    .getMessage());
            assertSame(NoSuchElementException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertNull(e.getColumnName());
            assertEquals(-1, e.getColumnIndex());
        }
    }

    /**
     * testNext14() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next09.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:true<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next09.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.hasNext():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.readLine():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.separateColumns(String):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Data part should be called before trailer part."<br>
     *                        �E������O�FIllegalStateException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F0<br>
     *                        �E�J�������Fnull<br>
     *                        �E�J�����C���f�b�N�X�F-1<br>
     * <br>
     *                        ��O�B<br>
     *                        �g���C�����̃f�[�^�擾���s��ꂽ���next()���Ă΂ꂽ�ꍇ�ɁAFileLineException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext14() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next09.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "readTrailer", true);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł����B");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, VMOUTUtil.getCallCount(fileLineIterator.getClass(),
                    "separateColumns"));
            assertEquals(0, UTUtil.getPrivateField(fileLineIterator,
                    "currentLineCount"));

            assertEquals(FileLineException.class, e.getClass());
            assertEquals("Data part should be called before trailer part.", e
                    .getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals(0, e.getLineNo());
            assertNull(e.getColumnName());
            assertEquals(-1, e.getColumnIndex());
        }
    }

    /**
     * testNext15() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next23.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next23.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111<br>
     *                        -------------------<br>
     *                        ���J���������R�Ńt�B�[���h�̐��ƍ����ĂȂ��B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Column Count is different from FileLineObject's column counts"<br>
     *                        �E������O�FIllegalStateException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F1<br>
     *                        �E�J�������Fnull<br>
     *                        �E�J�����C���f�b�N�X�F-1<br>
     * <br>
     *                        ��O�B<br>
     *                        �t�@�C���s�I�u�W�F�N�g�̃}�b�s���O�Ώۃt�B�[���h�̐��ƍ���Ȃ��f�[�^��Ώۃt�@�C������ǂޏꍇ�ɁAFileLineException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext15() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next23.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileLineException���X���[����܂���ł����B");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertEquals(FileLineException.class, e.getClass());
            assertEquals("Column Count is different from FileLineObject's "
                    + "column counts", e.getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertNull(e.getColumnName());
            assertEquals(-1, e.getColumnIndex());
        }
    }

    /**
     * testNext16() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next09.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.readLine():FileException��O������<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next09.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.hasNext():�P��Ă΂��<br>
     *                        ��readLine()�̒��ŃG���[���N���邽�߁A������hasNext�͌Ă΂Ȃ�<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:this.readLine()�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C���̓ǂ݂Ɏ��s�����ꍇ�ɁA������O�����̂܂܃X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext16() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next09.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        FileException exception = new FileException("readLine�ł̃G���[�ł��B");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "readLine", exception);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals("hasNext", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, VMOUTUtil.getCallCount(fileLineIterator.getClass(),
                    "separateColumns"));
            assertEquals(0, UTUtil.getPrivateField(fileLineIterator,
                    "currentLineCount"));

            assertSame(exception, e);
        }
    }

    /**
     * testNext17() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     *                        - �t�B�[���h�FString noMappingColumn1<br>
     *                        - �t�B�[���h�FString noMappingColumn2<br>
     *                        - �t�B�[���h�FString noMappingColumn3<br>
     *                        - �t�B�[���h�FString noMappingColumn4<br>
     *                        ���N���X��`���A@�L�薳���̃t�B�[���h�̏��Ԃ������Ē�`���邱�ƁB<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next19.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next19.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        2,line2,222222,1980/02/21<br>
     *                        3,line3,333333,1980/03/21<br>
     *                        -------------------<br>
     *                        ����������f�[�^<br>
     * <br>
     *                        ���Ғl�F(�߂�l) this.clazz.getClass():this.clazz�Őݒ肳��Ă���N���X�̃C���X�^���X<br>
     *                        �E1��ڂ̎��s����<br>
     *                        - column1�F1<br>
     *                        - column2�F"line1"<br>
     *                        - column3�F111111<br>
     *                        - column4�F1980/01/21<br>
     * <br>
     *                        �E2��ڂ̎��s����<br>
     *                        - column1�F2<br>
     *                        - column2�F"line2"<br>
     *                        - column3�F222222<br>
     *                        - column4�F1980/02/21<br>
     * <br>
     *                        �E3��ڂ̎��s����<br>
     *                        - column1�F3<br>
     *                        - column2�F"line3"<br>
     *                        - column3�F333333<br>
     *                        - column4�F1980/03/21<br>
     *                        (��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) DateColumnParser#parse():1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) DecimalColumnParser#parse():1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():1��̎��s����4��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():1��̎��s����2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��̎��s����1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:�E1��ڎ��s��F1<br>
     *                        �E2��ڎ��s��F2<br>
     *                        �E3��ڎ��s��F3<br>
     * <br>
     *                        ����B<br>
     *                        �t�@�C���s�I�u�W�F�N�g��@InputFileColumn�̐ݒ肪�Ȃ��t�B�[���h���L���Ă����Ȃ��ȉ��̏���������邱�Ƃ��m�F����B<br>
     *                        �Ώۃt�@�C���ɂ��镡���s�̏�񂪊e1�s�ɑ΂���next()���\�b�h���Ăԓx�ɐ������ݒ肳�ꂽ�t�@�C���s�I�u�W�F�N�g���擾�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext17() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next19.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub13> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub13>(
                fileName, AbstractFileLineIterator_Stub13.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{�i1��ځj
        AbstractFileLineIterator_Stub13 result = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("getColumn1", 1, result.getColumn1());
        assertEquals("line1", result.getColumn2());
        assertEquals(new BigDecimal(111111), result.getColumn3());
        Calendar column4 = new GregorianCalendar();
        column4.set(1980, 0, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result.getColumn4()
                .toString());

        // ��ԕω��̊m�F
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals("NullColumnParser", 1, VMOUTUtil.getCallCount(
                NullColumnParser.class, "parse"));
        assertEquals("DateColumnParser", 1, VMOUTUtil.getCallCount(
                DateColumnParser.class, "parse"));
        assertEquals("DecimalColumnParser", 1, VMOUTUtil.getCallCount(
                DecimalColumnParser.class, "parse"));
        assertEquals("IntColumnParser", 1, VMOUTUtil.getCallCount(
                IntColumnParser.class, "parse"));
        assertEquals(4, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals("AbstractFileLineIterator", 1, VMOUTUtil.getCallCount(
                AbstractFileLineIterator.class, "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                fileLineIterator, "currentLineCount"));

        // �e�X�g���{�i2��ځj
        AbstractFileLineIterator_Stub13 result02 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals(2, result02.getColumn1());
        assertEquals("line2", result02.getColumn2());
        assertEquals(new BigDecimal(222222), result02.getColumn3());
        column4.set(1980, 1, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result02.getColumn4()
                .toString());

        // ��ԕω��̊m�F�i�Ăяo���񐔂�1��ځ{����ɂȂ�j
        assertEquals(8, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(8, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals(2, VMOUTUtil.getCallCount(NullColumnParser.class, "parse"));
        assertEquals(2, VMOUTUtil.getCallCount(DateColumnParser.class, "parse"));
        assertEquals(2, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                "parse"));
        assertEquals(2, VMOUTUtil.getCallCount(IntColumnParser.class, "parse"));
        assertEquals(8, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(4, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 2, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals(2, UTUtil.getPrivateField(fileLineIterator,
                "currentLineCount"));

        // �e�X�g���{�i3��ځj
        AbstractFileLineIterator_Stub13 result03 = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals(3, result03.getColumn1());
        assertEquals("line3", result03.getColumn2());
        assertEquals(new BigDecimal(333333), result03.getColumn3());
        column4.set(1980, 2, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result03.getColumn4()
                .toString());

        // ��ԕω��̊m�F�i�Ăяo���񐔂�1��ځ{2��ځ{����ɂȂ�j
        assertEquals(12, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(12, VMOUTUtil
                .getCallCount(FileDAOUtility.class, "padding"));
        assertEquals(3, VMOUTUtil.getCallCount(NullColumnParser.class, "parse"));
        assertEquals(3, VMOUTUtil.getCallCount(DateColumnParser.class, "parse"));
        assertEquals(3, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                "parse"));
        assertEquals(3, VMOUTUtil.getCallCount(IntColumnParser.class, "parse"));
        assertEquals(12, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(6, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals(3, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 3, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals(3, UTUtil.getPrivateField(fileLineIterator,
                "currentLineCount"));
    }

    /**
     * testNext18() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next09.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) FileDAOUtility.trim(String, String, char, TrimType):FileException��O������<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next09.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):1��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:FileDAOUtility.trim(String, String, char, TrimType)�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C���˃t�@�C���s�I�u�W�F�N�g�̃}�b�s���O�������̃g���������ŗ�O�����������ꍇ�ɁA������O�����̂܂܃X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext18() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next09.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        FileException exception = new FileException("trim�ł̃G���[�ł��B");
        VMOUTUtil.setExceptionAtAllTimes(FileDAOUtility.class, "trim",
                exception);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals("FileDAOUtility", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "trim"));
            assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(exception, e);
        }
    }

    /**
     * testNext19() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next09.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) FileDAOUtility.padding(String, String, int, char, PaddingType):FileException��O������<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next09.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):1��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):1��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:FileDAOUtility.padding(String, String, int, char,
     *                        PaddingType)�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C���˃t�@�C���s�I�u�W�F�N�g�̃}�b�s���O�������̃p�f�B���O�����ŗ�O�����������ꍇ�ɁA������O�����̂܂܃X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext19() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next09.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        FileException exception = new FileException("padding�ł̃G���[�ł��B");
        VMOUTUtil.setExceptionAtAllTimes(FileDAOUtility.class, "padding",
                exception);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals("FileDAOUtility:trim", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "trim"));
            assertEquals("FileDAOUtility:padding", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertSame(exception, e);
        }
    }

    /**
     * testNext20() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next09.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.hasNext():FileException��O������<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next09.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.hasNext():�P��Ă΂��<br>
     *                        (��ԕω�) this.readLine():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.separateColumns(String):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:this.hasNext()�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     *                        ��O�B<br>
     *                        �Ώۃt�@�C���ɑ΂��Ď��̏����f�[�^�����邩�̃`�F�b�N�����ŗ�O�����������ꍇ�ɁA������O�����̂܂܃X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext20() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next09.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        FileException exception = new FileException("padding�ł̃G���[�ł��B");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "hasNext", exception);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(0, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            assertEquals(0, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            assertEquals(0, VMOUTUtil.getCallCount(NullColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            assertEquals("hasNext", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals(0, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));
            assertEquals(0, VMOUTUtil.getCallCount(fileLineIterator.getClass(),
                    "separateColumns"));
            assertEquals(0, UTUtil.getPrivateField(fileLineIterator,
                    "currentLineCount"));

            assertSame(exception, e);
        }
    }

    /**
     * testNext21() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next09.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():false<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next09.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        1,line1,111111,1980/01/21<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(�߂�l) this.clazz.getClass():this.clazz�Őݒ肳��Ă���N���X�̃C���X�^���X<br>
     *                        - column1�F1<br>
     *                        - column2�F"line1"<br>
     *                        - column3�F111111<br>
     *                        - column4�F1980/01/21<br>
     *                        (��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):4��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):4��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) DateColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) DecimalColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) IntColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) NullStringConverter#convert():4��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) String.getBytes():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.currentLineCount:1<br>
     * <br>
     *                        ����B(�o�C�g���`�F�b�N���Ȃ�)<br>
     *                        �Ώۃt�@�C���̓��e���������ݒ肳�ꂽ�t�@�C���s�I�u�W�F�N�g���擾����邱�Ƃ��m�F����B<br>
     *                        �A���A�o�C�g���`�F�b�N������Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext21() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next09.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub09>(
                fileName, AbstractFileLineIterator_Stub09.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "isCheckByte", false);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B

        // �e�X�g���{
        AbstractFileLineIterator_Stub09 result = fileLineIterator.next();

        // �ԋp�l�̊m�F
        assertEquals("getColumn1", 1, result.getColumn1());
        assertEquals("line1", result.getColumn2());
        assertEquals(new BigDecimal(111111), result.getColumn3());
        Calendar column4 = new GregorianCalendar();
        column4.set(1980, 0, 21, 0, 0, 0);
        assertEquals(column4.getTime().toString(), result.getColumn4()
                .toString());

        // ��ԕω��̊m�F
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "trim"));
        assertEquals(4, VMOUTUtil.getCallCount(FileDAOUtility.class, "padding"));
        assertEquals("NullColumnParser", 1, VMOUTUtil.getCallCount(
                NullColumnParser.class, "parse"));
        assertEquals("DateColumnParser", 1, VMOUTUtil.getCallCount(
                DateColumnParser.class, "parse"));
        assertEquals("DecimalColumnParser", 1, VMOUTUtil.getCallCount(
                DecimalColumnParser.class, "parse"));
        assertEquals("IntColumnParser", 1, VMOUTUtil.getCallCount(
                IntColumnParser.class, "parse"));
        assertEquals(4, VMOUTUtil.getCallCount(NullStringConverter.class,
                "convert"));
        assertEquals(2, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "hasNext"));
        assertEquals("AbstractFileLineIterator", 1, VMOUTUtil.getCallCount(
                AbstractFileLineIterator.class, "readLine"));

        // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
        if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                .getProperty("java.system.class.loader")))) {
            assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                    fileLineIterator.getClass(), "separateColumns"));
        }

        assertEquals(0, VMOUTUtil.getCallCount(String.class, "getBytes"));
        assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                fileLineIterator, "currentLineCount"));
    }

    /**
     * testNext22() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl<br>
     * �ǂݎ�����f�[�^1�s�����J���}�i,�j�ŋ�؂��āA�J�����Ƃ��ĕԂ�<br>
     * <br>
     * ���C���X�^���X��������this.fileEncoding��"aaa"�ɒu�������邱��<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > bytes�F5<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_next13.txt"<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     *                        �E"java.util.Date"=DateColumnParser�C���X�^���X<br>
     *                        �E"java.math.BigDecimal"=DecimalColumnParser�C���X�^���X<br>
     *                        �E"int"=IntColumnParser�C���X�^���X<br>
     *                        (���) this.readTrailer:false<br>
     *                        (���) this.isCheckByte():true<br>
     *                        (���) �Ώۃt�@�C��:�ȉ��̓��e������"AbstractFileLineIterator_next13.txt"�t�@�C�������݂���B<br>
     *                        -------------------<br>
     *                        ABCDE,12345<br>
     *                        -------------------<br>
     *                        ������f�[�^<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) FileDAOUtility.trim(String, String, char, TrimType):1��Ă΂��<br>
     *                        (��ԕω�) FileDAOUtility.padding(String, String, int, char, PaddingType):1��Ă΂��<br>
     *                        (��ԕω�) NullColumnParser#parse():1��Ă΂��<br>
     *                        (��ԕω�) DateColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) DecimalColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) IntColumnParser#parse():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) NullStringConverter#convert():1��Ă΂��<br>
     *                        (��ԕω�) this.hasNext():2��Ă΂��<br>
     *                        ��readLine()�̒���hasNext���P��Ă΂�Ă���B<br>
     *                        (��ԕω�) this.readLine():1��Ă΂��<br>
     *                        (��ԕω�) this.separateColumns(String):1��Ă΂��<br>
     *                        (��ԕω�) this.currentLineCount:0<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"fileEncoding which isn't supported was set."<br>
     *                        �E������O�FUnsupportedEncodingException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                        ��O�B<br>
     *                        �R���X�g���N�^������ɁAfileEncoding��s���ȃG���R�[�f�B���O������ɒu���������ꍇ�́A#next()�Ăяo�����ɗ�O���N���邱�Ƃ��m�F����B<br>
     * <br>
     *                        ���s���ɃN���X�����������Ȃ���΁A�R���X�g���N�^�ŌĂ΂��#buildLineReader()�ɂ��A��O���N����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testNext22() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next13.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub10> fileLineIterator = new AbstractFileLineIteratorImpl01<AbstractFileLineIterator_Stub10>(
                fileName, AbstractFileLineIterator_Stub10.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ肵�Ă���B
        UTUtil.setPrivateField(fileLineIterator, "fileEncoding", "aaa");

        // �e�X�g���{
        try {
            fileLineIterator.next();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals("FileDAOUtility:trim", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "trim"));
            assertEquals("FileDAOUtility:padding", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "padding"));
            assertEquals("NullColumnParser", 1, VMOUTUtil.getCallCount(
                    NullColumnParser.class, "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DateColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(DecimalColumnParser.class,
                    "parse"));
            assertEquals(0, VMOUTUtil.getCallCount(IntColumnParser.class,
                    "parse"));
            assertEquals("NullStringConverter", 1, VMOUTUtil.getCallCount(
                    NullStringConverter.class, "convert"));
            assertEquals(2, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "hasNext"));
            assertEquals("readLine", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            // maven����N������ƂȂ���separateColumns���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals("separateColumns", 1, VMOUTUtil.getCallCount(
                        fileLineIterator.getClass(), "separateColumns"));
            }

            assertEquals("currentLineCount", 1, UTUtil.getPrivateField(
                    fileLineIterator, "currentLineCount"));

            assertEquals(FileException.class, e.getClass());
            assertEquals("fileEncoding which isn't supported was set.", e
                    .getMessage());
            assertEquals(UnsupportedEncodingException.class, e.getCause()
                    .getClass());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testRemove01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������UnsupportedOperationException()����������B<br>
     * �E���b�Z�[�W�F"remove() isn't supported."<br>
     * <br>
     * ���\�b�h�����s����ƃA���T�|�[�g��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRemove01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next06.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            fileLineIterator.remove();
            fail("UnsupportedOperationException���X���[����܂���ł����B");
        } catch (UnsupportedOperationException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(UnsupportedOperationException.class, e.getClass());
            assertEquals("remove() isn't supported.", e.getMessage());
        }
    }

    /**
     * testInit01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:true<br>
     * (���) this.buildFields():���폈��<br>
     * (���) this.buildStringConverters():���폈��<br>
     * (���) this.buildMethods():���폈��<br>
     * (���) this.buildHeader():���폈��<br>
     * (���) this.buildTrailerQueue():���폈��<br>
     * (���) this.buildLineReader():���폈��<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:true<br>
     * (��ԕω�) this.buildFields():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildStringConverters():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildMethods():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildLineReader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildHeader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildTrailerQueue():�Ă΂�Ȃ�<br>
     * <br>
     * ����B<br>
     * ����init()���Ă΂ꂽ�ꍇ�́Ainit()�������s��Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_next06.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", true);

        // �e�X�g���{
        fileLineIterator.init();

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertTrue((Boolean) UTUtil.getPrivateField(fileLineIterator,
                "calledInit"));
        assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                "buildFields"));
        assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                "buildStringConverters"));
        assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                "buildMethods"));
        assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                "buildLineReader"));
        assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                "buildHeader"));
        assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                "buildTrailerQueue"));
    }

    /**
     * testInit02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:false<br>
     * (���) this.buildFields():���폈��<br>
     * (���) this.buildStringConverters():���폈��<br>
     * (���) this.buildMethods():���폈��<br>
     * (���) this.buildHeader():���폈��<br>
     * (���) this.buildTrailerQueue():���폈��<br>
     * (���) this.buildLineReader():���폈��<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:true<br>
     * (��ԕω�) this.buildFields():1��Ă΂��<br>
     * (��ԕω�) this.buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) this.buildMethods():1��Ă΂��<br>
     * (��ԕω�) this.buildLineReader():1��Ă΂��<br>
     * (��ԕω�) this.buildHeader():1��Ă΂��<br>
     * (��ԕω�) this.buildTrailerQueue():1��Ă΂��<br>
     * <br>
     * ����B<br>
     * �ŏ���Init()�������Ă΂ꂽ�ꍇ�́Ainit()�������s���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", false);

        // �e�X�g���{
        fileLineIterator.init();

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertTrue((Boolean) UTUtil.getPrivateField(fileLineIterator,
                "calledInit"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "buildFields"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "buildStringConverters"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "buildMethods"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "buildLineReader"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "buildHeader"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileLineIterator.class,
                "buildTrailerQueue"));
    }

    /**
     * testInit03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:false<br>
     * (���) this.buildFields():FileException��O����������B<br>
     * (���) this.buildStringConverters():���폈��<br>
     * (���) this.buildMethods():���폈��<br>
     * (���) this.buildHeader():���폈��<br>
     * (���) this.buildTrailerQueue():���폈��<br>
     * (���) this.buildLineReader():���폈��<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:false<br>
     * (��ԕω�) this.buildFields():1��Ă΂��<br>
     * (��ԕω�) this.buildStringConverters():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildMethods():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildLineReader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildHeader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildTrailerQueue():�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:this.buildFields()�Ŕ���������O�����̂܂ܓ������邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B<br>
     * this.buildFields()�����ŗ�O�����������ꍇ�ɁA��O�����̂܂ܓ������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", false);
        FileException exception = new FileException("buildFields�̃G���[�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "buildFields", exception);

        // �e�X�g���{
        try {
            fileLineIterator.init();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertFalse((Boolean) UTUtil.getPrivateField(fileLineIterator,
                    "calledInit"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildFields"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildStringConverters"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildMethods"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildLineReader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildHeader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildTrailerQueue"));

            assertSame(exception, e);
        }
    }

    /**
     * testInit04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:false<br>
     * (���) this.buildFields():���폈��<br>
     * (���) this.buildStringConverters():FileLineException��O����������B<br>
     * (���) this.buildMethods():���폈��<br>
     * (���) this.buildHeader():���폈��<br>
     * (���) this.buildTrailerQueue():���폈��<br>
     * (���) this.buildLineReader():���폈��<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:false<br>
     * (��ԕω�) this.buildFields():1��Ă΂��<br>
     * (��ԕω�) this.buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) this.buildMethods():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildLineReader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildHeader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildTrailerQueue():�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:this.buildStringConverter()�Ŕ���������O�����̂܂ܓ������邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B<br>
     * this.buildStringConverter()�����ŗ�O�����������ꍇ�ɁA��O�����̂܂ܓ������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", false);
        FileLineException exception = new FileLineException(
                "buildStringConverters�̃G���[�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "buildStringConverters", exception);

        // �e�X�g���{
        try {
            fileLineIterator.init();
            fail("FileLineException���X���[����܂���ł����B");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertFalse((Boolean) UTUtil.getPrivateField(fileLineIterator,
                    "calledInit"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildFields"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildStringConverters"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildMethods"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildLineReader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildHeader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildTrailerQueue"));

            assertSame(exception, e);
        }
    }

    /**
     * testInit05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:false<br>
     * (���) this.buildFields():���폈��<br>
     * (���) this.buildStringConverters():���폈��<br>
     * (���) this.buildMethods():FileException��O����������B<br>
     * (���) this.buildHeader():���폈��<br>
     * (���) this.buildTrailerQueue():���폈��<br>
     * (���) this.buildLineReader():���폈��<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:false<br>
     * (��ԕω�) this.buildFields():1��Ă΂��<br>
     * (��ԕω�) this.buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) this.buildMethods():1��Ă΂��<br>
     * (��ԕω�) this.buildLineReader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildHeader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildTrailerQueue():�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:this.buildMethods()�Ŕ���������O�����̂܂ܓ������邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B<br>
     * this.buildMethods()�����ŗ�O�����������ꍇ�ɁA��O�����̂܂ܓ������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", false);
        FileException exception = new FileException("buildMethods�̃G���[�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "buildMethods", exception);

        // �e�X�g���{
        try {
            fileLineIterator.init();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertFalse((Boolean) UTUtil.getPrivateField(fileLineIterator,
                    "calledInit"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildFields"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildStringConverters"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildMethods"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildLineReader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildHeader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildTrailerQueue"));

            assertSame(exception, e);
        }
    }

    /**
     * testInit06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:false<br>
     * (���) this.buildFields():���폈��<br>
     * (���) this.buildStringConverters():���폈��<br>
     * (���) this.buildMethods():���폈��<br>
     * (���) this.buildHeader():FileException��O����������B<br>
     * (���) this.buildTrailerQueue():���폈��<br>
     * (���) this.buildLineReader():���폈��<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:false<br>
     * (��ԕω�) this.buildFields():1��Ă΂��<br>
     * (��ԕω�) this.buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) this.buildMethods():1��Ă΂��<br>
     * (��ԕω�) this.buildLineReader():1��Ă΂��<br>
     * (��ԕω�) this.buildHeader():1��Ă΂��<br>
     * (��ԕω�) this.buildTrailerQueue():�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:this.buildHeader()�Ŕ���������O�����̂܂ܓ������邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B<br>
     * this.buildHeader()�����ŗ�O�����������ꍇ�ɁA��O�����̂܂ܓ������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", false);
        FileException exception = new FileException("buildHeaders�̃G���[�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "buildHeader", exception);

        // �e�X�g���{
        try {
            fileLineIterator.init();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertFalse((Boolean) UTUtil.getPrivateField(fileLineIterator,
                    "calledInit"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildFields"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildStringConverters"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildMethods"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildHeader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildTrailerQueue"));

            assertSame(exception, e);
        }
    }

    /**
     * testInit07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:false<br>
     * (���) this.buildFields():���폈��<br>
     * (���) this.buildStringConverters():���폈��<br>
     * (���) this.buildMethods():���폈��<br>
     * (���) this.buildHeader():���폈��<br>
     * (���) this.buildTrailerQueue():FileException��O����������B<br>
     * (���) this.buildLineReader():���폈��<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:false<br>
     * (��ԕω�) this.buildFields():1��Ă΂��<br>
     * (��ԕω�) this.buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) this.buildMethods():1��Ă΂��<br>
     * (��ԕω�) this.buildLineReader():1��Ă΂��<br>
     * (��ԕω�) this.buildHeader():1��Ă΂��<br>
     * (��ԕω�) this.buildTrailerQueue():1��Ă΂��<br>
     * (��ԕω�) ��O:this.buildTrailerQueue()�Ŕ���������O�����̂܂ܓ������邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B<br>
     * this.buildTrailerQueue()�����ŗ�O�����������ꍇ�ɁA��O�����̂܂ܓ������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", false);
        FileException exception = new FileException("buildTrailerQueue�̃G���[�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "buildTrailerQueue", exception);

        // �e�X�g���{
        try {
            fileLineIterator.init();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertFalse((Boolean) UTUtil.getPrivateField(fileLineIterator,
                    "calledInit"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildFields"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildStringConverters"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildMethods"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildHeader"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildTrailerQueue"));

            assertSame(exception, e);
        }
    }

    /**
     * testInit08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.calledInit:false<br>
     * (���) this.buildFields():���폈��<br>
     * (���) this.buildStringConverters():���폈��<br>
     * (���) this.buildMethods():���폈��<br>
     * (���) this.buildHeader():���폈��<br>
     * (���) this.buildTrailerQueue():���폈��<br>
     * (���) this.buildLineReader():FileException��O����������B<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.calledInit:false<br>
     * (��ԕω�) this.buildFields():1��Ă΂��<br>
     * (��ԕω�) this.buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) this.buildMethods():1��Ă΂��<br>
     * (��ԕω�) this.buildLineReader():1��Ă΂��<br>
     * (��ԕω�) this.buildHeader():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.buildTrailerQueue():�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:this.buildLineReader()�Ŕ���������O�����̂܂ܓ������邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B<br>
     * this.buildLineReader()�����ŗ�O�����������ꍇ�ɁA��O�����̂܂ܓ������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "calledInit", false);
        FileException exception = new FileException("buildLineReader�̃G���[�ł�");
        VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineIterator.class,
                "buildLineReader", exception);

        // �e�X�g���{
        try {
            fileLineIterator.init();
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertFalse((Boolean) UTUtil.getPrivateField(fileLineIterator,
                    "calledInit"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildFields"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildStringConverters"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildMethods"));
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "buildLineReader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildHeader"));
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineIterator.class,
                    "buildTrailerQueue"));

            assertSame(exception, e);
        }
    }

    /**
     * testBuildLineReader01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl03<br>
     * �@#getEncloseChar()�F'\"'��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) this.fileEncoding:�V�X�e���f�t�H���g�l<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * (���) this.lineReader:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * (��ԕω�) this.lineReader:�ȉ��̐ݒ������EncloseCharLineFeed2LineReader�C���X�^���X<br>
     * �EdelimiterCharacter�F','<br>
     * �EencloseCharacter�F''\"'<br>
     * �ElineFeedChar�F"\r\n"<br>
     * �Ereader�F"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�@�C���s�I�u�W�F�N�g�̃N���X��@FileFormat�̐ݒ�Ɂu�͂ݕ����v�Ɓu��؂蕶���v�Ɓu2���̍s��؂蕶���v���ݒ肳��Ă���ꍇ�ɁA��������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20> fileLineIterator = new AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20>(
                fileName, AbstractFileLineIterator_Stub20.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "enclosed", true);
        UTUtil.setPrivateField(fileLineIterator, "columnEncloseChar",
                new char[] {});

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object reader = UTUtil.getPrivateField(fileLineIterator, "reader");
        assertEquals(BufferedReader.class, reader.getClass());

        Object reader02 = UTUtil
                .getPrivateField(fileLineIterator, "lineReader");
        assertEquals(EncloseCharLineFeed2LineReader.class, reader02.getClass());
        assertEquals(',', UTUtil
                .getPrivateField(reader02, "delimiterCharacter"));
        assertEquals('\"', UTUtil.getPrivateField(reader02, "encloseCharacter"));
        assertEquals("\r\n", UTUtil.getPrivateField(reader02, "lineFeedChar"));
        assertSame(reader, UTUtil.getPrivateField(reader02, "reader"));

        String result = ((BufferedReader) reader).readLine();
        assertEquals("AbstractFileLineIterator_buildLineReader01", result);

        // �㏈��
        ((BufferedReader) reader).close();
    }

    /**
     * testBuildLineReader02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl03<br>
     * �@#getEncloseChar()�F'\"'��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildLineReader01.txt"<br>
     * (���) this.fileEncoding:�V�X�e���f�t�H���g�l<br>
     * (���) this.lineFeedChar:"\r"<br>
     * (���) this.lineReader:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * (��ԕω�) this.lineReader:�ȉ��̐ݒ������EncloseCharLineFeed1LineReader�C���X�^���X<br>
     * �EdelimiterCharacter�F','<br>
     * �EencloseCharacter�F''\"'<br>
     * �ElineFeedChar�F"\r"<br>
     * �Ereader�F"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�@�C���s�I�u�W�F�N�g�̃N���X��@FileFormat�̐ݒ�Ɂu�͂ݕ����v�Ɓu��؂蕶���v�Ɓu1���̍s��؂蕶���v���ݒ肳��Ă���ꍇ�ɁA��������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub21> fileLineIterator = new AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub21>(
                fileName, AbstractFileLineIterator_Stub21.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "enclosed", true);
        UTUtil.setPrivateField(fileLineIterator, "columnEncloseChar",
                new char[] {});

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object reader = UTUtil.getPrivateField(fileLineIterator, "reader");
        assertEquals(BufferedReader.class, reader.getClass());

        Object reader02 = UTUtil
                .getPrivateField(fileLineIterator, "lineReader");
        assertEquals(EncloseCharLineFeed1LineReader.class, reader02.getClass());
        assertEquals(',', UTUtil
                .getPrivateField(reader02, "delimiterCharacter"));
        assertEquals('\"', UTUtil.getPrivateField(reader02, "encloseCharacter"));
        assertEquals("\r", UTUtil.getPrivateField(reader02, "lineFeedChar"));
        assertSame(reader, UTUtil.getPrivateField(reader02, "reader"));

        String result = ((BufferedReader) reader).readLine();
        assertEquals("AbstractFileLineIterator_buildLineReader01", result);

        // �㏈��
        ((BufferedReader) reader).close();
    }

    /**
     * testBuildLineReader03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl04<br>
     * �@#getEncloseChar()�FCharacter.MIN_VALUE��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildLineReader01.txt"<br>
     * (���) this.fileEncoding:�V�X�e���f�t�H���g�l<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * (���) this.lineReader:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * (��ԕω�) this.lineReader:�ȉ��̐ݒ������LineFeed2LineReader�C���X�^���X<br>
     * �ElineFeedChar�F"\r\n"<br>
     * �Ereader�F"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�@�C���s�I�u�W�F�N�g�̃N���X��@FileFormat�̐ݒ�Ɂu��؂蕶���v�Ɓu2���̍s��؂蕶���v���ݒ肳��Ă���ꍇ�ɁA��������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub20> fileLineIterator = new AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub20>(
                fileName, AbstractFileLineIterator_Stub20.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object reader = UTUtil.getPrivateField(fileLineIterator, "reader");
        assertEquals(BufferedReader.class, reader.getClass());

        Object reader02 = UTUtil
                .getPrivateField(fileLineIterator, "lineReader");
        assertEquals(LineFeed2LineReader.class, reader02.getClass());
        assertEquals("\r\n", UTUtil.getPrivateField(reader02, "lineFeedChar"));
        assertSame(reader, UTUtil.getPrivateField(reader02, "reader"));

        String result = ((BufferedReader) reader).readLine();
        assertEquals("AbstractFileLineIterator_buildLineReader01", result);

        // �㏈��
        ((BufferedReader) reader).close();
    }

    /**
     * testBuildLineReader04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl04<br>
     * �@#getEncloseChar()�FCharacter.MIN_VALUE��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildLineReader01.txt"<br>
     * (���) this.fileEncoding:�V�X�e���f�t�H���g�l<br>
     * (���) this.lineFeedChar:"\r"<br>
     * (���) this.lineReader:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * (��ԕω�) this.lineReader:�ȉ��̐ݒ������LineFeed1LineReader�C���X�^���X<br>
     * �ElineFeedChar�F"\r"<br>
     * �Ereader�F"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�@�C���s�I�u�W�F�N�g�̃N���X��@FileFormat�̐ݒ�Ɂu��؂蕶���v�Ɓu1���̍s��؂蕶���v���ݒ肳��Ă���ꍇ�ɁA��������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub21> fileLineIterator = new AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub21>(
                fileName, AbstractFileLineIterator_Stub21.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object reader = UTUtil.getPrivateField(fileLineIterator, "reader");
        assertEquals(BufferedReader.class, reader.getClass());

        Object reader02 = UTUtil
                .getPrivateField(fileLineIterator, "lineReader");
        assertEquals(LineFeed1LineReader.class, reader02.getClass());
        assertEquals("\r", UTUtil.getPrivateField(reader02, "lineFeedChar"));
        assertSame(reader, UTUtil.getPrivateField(reader02, "reader"));

        String result = ((BufferedReader) reader).readLine();
        assertEquals("AbstractFileLineIterator_buildLineReader01", result);

        // �㏈��
        ((BufferedReader) reader).close();
    }

    /**
     * testBuildLineReader05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl03<br>
     * �@#getEncloseChar()�F'\"'��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildLineReader_noExist.txt"�i���݂��Ȃ��t�@�C���j<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * (���) this.fileEncoding:�V�X�e���f�t�H���g�l<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * (���) this.lineReader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * (���) this.reader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:null<br>
     * (��ԕω�) this.lineReader:null<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Failed in generation of reader."<br>
     * �E������O�FFileNotFoundException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ���݂��Ȃ��t�@�C����fileName�ɐݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20> fileLineIterator = new AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20>(
                fileName, AbstractFileLineIterator_Stub20.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        String fileName_test = "AbstractFileLineIterator_buildLineReader_noExist.txt";
        UTUtil.setPrivateField(fileLineIterator, "fileName", fileName_test);
        UTUtil.setPrivateField(fileLineIterator, "lineReader", null);
        UTUtil.setPrivateField(fileLineIterator, "reader", null);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildLineReader");
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertNull(UTUtil.getPrivateField(fileLineIterator, "reader"));
            assertNull(UTUtil.getPrivateField(fileLineIterator, "lineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("Failed in generation of reader.", e.getMessage());
            assertSame(FileNotFoundException.class, e.getCause().getClass());
            assertSame(fileName_test, e.getFileName());
        }
    }

    /**
     * testBuildLineReader06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl03<br>
     * �@#getEncloseChar()�F'\"'��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "File_1Linecsv"<br>
     * (���) this.fileEncoding:���݂��Ȃ��G���R�[�h<br>
     * ���C���X�^���X������ɐݒ肷��<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * (���) this.lineReader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * (���) this.reader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:null<br>
     * (��ԕω�) this.lineReader:null<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Failed in generation of reader."<br>
     * �E������O�FUnsupportedEncodingException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ���݂��Ȃ��G���R�[�h��fileEncode�ɐݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20> fileLineIterator = new AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20>(
                fileName, AbstractFileLineIterator_Stub20.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fileEncoding", "aaa");
        UTUtil.setPrivateField(fileLineIterator, "lineReader", null);
        UTUtil.setPrivateField(fileLineIterator, "reader", null);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildLineReader");
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertNull(UTUtil.getPrivateField(fileLineIterator, "reader"));
            assertNull(UTUtil.getPrivateField(fileLineIterator, "lineReader"));

            assertSame(FileException.class, e.getClass());
            assertEquals("Failed in generation of reader.", e.getMessage());
            assertSame(UnsupportedEncodingException.class, e.getCause()
                    .getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testBuildLineReader07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl04<br>
     * �@#getEncloseChar()�FCharacter.MIN_VALUE��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildLineReader01.txt"<br>
     * (���) this.fileEncoding:�V�X�e���f�t�H���g�l<br>
     * (���) this.lineFeedChar:"\r\r\r"<br>
     * ���C���X�^���X������ɐݒ肷��<br>
     * (���) this.lineReader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * (���) this.reader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * (��ԕω�) this.lineReader:null<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"lineFeedChar length must be 0 or 1 or 2. but: 3"<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * �t�@�C���s�I�u�W�F�N�g�̃N���X��@FileFormat�̐ݒ�Ɂu��؂蕶���v�Ɓu3���ȏ�̍s��؂蕶���v���ݒ肳��Ă���ꍇ�ɁA��O���X���[����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub20> fileLineIterator = new AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub20>(
                fileName, AbstractFileLineIterator_Stub20.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "lineFeedChar", "\r\r\r");
        UTUtil.setPrivateField(fileLineIterator, "lineReader", null);
        UTUtil.setPrivateField(fileLineIterator, "reader", null);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildLineReader");
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            Object reader = UTUtil.getPrivateField(fileLineIterator, "reader");
            assertEquals(BufferedReader.class, reader.getClass());

            Object reader02 = UTUtil.getPrivateField(fileLineIterator,
                    "lineReader");
            assertNull(reader02);

            assertSame(FileException.class, e.getClass());
            assertEquals("lineFeedChar length must be 0 or 1 or 2. but: 3", e
                    .getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testBuildLineReader08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl03<br>
     * �@#getEncloseChar()�F'\"'��Ԃ�<br>
     * �@#getDelimiter()�F','��Ԃ�<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildLineReader01.txt"<br>
     * (���) this.fileEncoding:�V�X�e���f�t�H���g�l<br>
     * (���) this.lineFeedChar:"\r\r\r"<br>
     * ���C���X�^���X������ɐݒ肷��<br>
     * (���) this.lineReader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * (���) this.reader:null<br>
     * ���C���X�^���X������ɐݒ肷��B<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:"AbstractFileLineIterator_buildLineReader01.txt"�ɑ΂���BufferedReader�C���X�^���X<br>
     * (��ԕω�) this.lineReader:null<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"lineFeedChar length must be 0 or 1 or 2. but: 3"<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * �t�@�C���s�I�u�W�F�N�g�̃N���X��@FileFormat�̐ݒ�Ɂu�͂ݕ����v�Ɓu��؂蕶���v�Ɓu3���ȏ�̍s��؂蕶���v���ݒ肳��Ă���ꍇ�ɁA��O���X���[����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildLineReader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20> fileLineIterator = new AbstractFileLineIteratorImpl03<AbstractFileLineIterator_Stub20>(
                fileName, AbstractFileLineIterator_Stub20.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "lineFeedChar", "\r\r\r");
        UTUtil.setPrivateField(fileLineIterator, "lineReader", null);
        UTUtil.setPrivateField(fileLineIterator, "reader", null);
        // ���̑��́A�e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildLineReader");
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            Object reader = UTUtil.getPrivateField(fileLineIterator, "reader");
            assertEquals(BufferedReader.class, reader.getClass());

            Object reader02 = UTUtil.getPrivateField(fileLineIterator,
                    "lineReader");
            assertNull(reader02);

            assertSame(FileException.class, e.getClass());
            assertEquals("lineFeedChar length must be 0 or 1 or 2. but: 3", e
                    .getMessage());
            assertSame(IllegalStateException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testBuildLineReader09() <br>
     * <br>
     * (�ُ�n) <br>
     * <br>
     * ���͒l�FmarkSupported ��false��Ԃ�<br>
     * <br>
     * ���Ғl�F ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"BufferedReader of this JVM dose not support mark method"<br>
     * <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildLineReader09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub21> fileLineIterator = new AbstractFileLineIteratorImpl04<AbstractFileLineIterator_Stub21>(
                fileName, AbstractFileLineIterator_Stub21.class,
                columnParserMap);

        // markSupported��false��ԋp����悤�ɐݒ�
        VMOUTUtil.setReturnValueAtAllTimes(BufferedReader.class,
                "markSupported", false);
        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineIterator, "buildLineReader");
            fail();
        } catch (FileException e) {
            assertEquals(
                    "BufferedReader of this JVM dose not support mark method",
                    e.getMessage());
        }
    }

    /**
     * testBuildFields01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�����ĂȂ��B<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) this.fields:null<br>
     * (���) this.columnParserMap:�ȉ��̗v�f������<br>
     * Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) Field.getAnnotation():�Ă΂�Ȃ�<br>
     * (��ԕω�) this.fields:�v�f�������Ȃ�Field�z��C���X�^���X<br>
     * (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz���t�B�[���h�������ĂȂ��ꍇ�A����I�����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildFields");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        Object result_fields = (Field[]) UTUtil.getPrivateField(
                fileLineIterator, "fields");
        assertEquals(Field[].class, result_fields.getClass());
        Field[] fields = (Field[]) result_fields;
        assertEquals(0, fields.length);
        assertEquals(2, VMOUTUtil
                .getCallCount(Class.class, "getDeclaredFields"));
    }

    /**
     * testBuildFields02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) this.fields:null<br>
     * (���) this.columnParserMap:�ȉ��̗v�f������<br>
     * Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) Field.getAnnotation():3��Ă΂��<br>
     * (��ԕω�) this.fields:�v�f�������Ȃ�Field�z��C���X�^���X<br>
     * (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz��@InputFileColumn�ݒ�Ȃ��̃t�B�[���h�̂ݎ��ꍇ�A�}�b�s���O�Ώۃt�B�[���h�̏�񂪐������ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub30> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub30>(
                fileName, AbstractFileLineIterator_Stub30.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildFields");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(3, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        Object result_fields = (Field[]) UTUtil.getPrivateField(
                fileLineIterator, "fields");
        assertEquals(Field[].class, result_fields.getClass());
        Field[] fields = (Field[]) result_fields;
        assertEquals(0, fields.length);
        assertEquals(2, VMOUTUtil
                .getCallCount(Class.class, "getDeclaredFields"));
    }

    /**
     * testBuildFields03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():6��Ă΂��<br>
     *                        (��ԕω�) this.fields:this.clazz�ɑ΂���t�B�[���h�v�f3������Field�z��C���X�^���X<br>
     *                        �P�Dcolumn1<br>
     *                        �Q�Dcolumn2<br>
     *                        �R�Dcolumn3<br>
     * <br>
     *                        �����Ԃ�columnIndex��<br>
     *                        (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��̃t�B�[���h�̂ݎ����A�܂��e�t�B�[���h��columnIndex�l�ɏd�����Ȃ��ꍇ�A�}�b�s���O�Ώۃt�B�[���h�̏�񂪐������ݒ肳��邱�Ƃ��m�F����
     *                        �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub31> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub31>(
                fileName, AbstractFileLineIterator_Stub31.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildFields");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(6, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        Object result_fields = (Field[]) UTUtil.getPrivateField(
                fileLineIterator, "fields");
        assertEquals(Field[].class, result_fields.getClass());
        Field[] fields = (Field[]) result_fields;
        assertEquals(3, fields.length);
        assertEquals("column1", fields[0].getName());
        assertEquals("column2", fields[1].getName());
        assertEquals("column3", fields[2].getName());
        assertEquals(2, VMOUTUtil
                .getCallCount(Class.class, "getDeclaredFields"));
    }

    /**
     * testBuildFields04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        ���N���X��`���A@�L�薳���̃t�B�[���h�̏��Ԃ������Ē�`���邱�ƁB<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():9��Ă΂��<br>
     *                        (��ԕω�) this.fields:this.clazz�ɑ΂���t�B�[���h�v�f3������Field�z��C���X�^���X<br>
     *                        �P�Dcolumn1<br>
     *                        �Q�Dcolumn2<br>
     *                        �R�Dcolumn3<br>
     * <br>
     *                        �����Ԃ�columnIndex��<br>
     *                        (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��E�Ȃ��̃t�B�[���h�������A�܂��e�t�B�[���h��columnIndex�l�ɏd�����Ȃ��ꍇ�A�}�b�s���O�Ώۃt�B�[���h�̏�񂪐������ݒ肳��邱�Ƃ��m�F����
     *                        �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub32> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub32>(
                fileName, AbstractFileLineIterator_Stub32.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildFields");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(9, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        Object result_fields = (Field[]) UTUtil.getPrivateField(
                fileLineIterator, "fields");
        assertEquals(Field[].class, result_fields.getClass());
        Field[] fields = (Field[]) result_fields;
        assertEquals(3, fields.length);
        assertEquals("column1", fields[0].getName());
        assertEquals("column2", fields[1].getName());
        assertEquals("column3", fields[2].getName());
        assertEquals(2, VMOUTUtil
                .getCallCount(Class.class, "getDeclaredFields"));
    }

    /**
     * testBuildFields05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d������B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():3��Ă΂��<br>
     *                        (��ԕω�) this.fields:null<br>
     *                        (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     *                        (��ԕω�) -:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Column Index is duplicate : 1"<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                        ��O�B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��̃t�B�[���h�̂ݎ����A�܂��e�t�B�[���h��columnIndex�l���d�����Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub33> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub33>(
                fileName, AbstractFileLineIterator_Stub33.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildFields");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(3, VMOUTUtil
                    .getCallCount(Field.class, "getAnnotation"));
            Object result_fields = (Field[]) UTUtil.getPrivateField(
                    fileLineIterator, "fields");
            assertNull(result_fields);
            assertEquals(2, VMOUTUtil.getCallCount(Class.class,
                    "getDeclaredFields"));

            assertEquals(FileException.class, e.getClass());
            assertEquals("Column Index is duplicate : 1", e.getMessage());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testBuildFields06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex�͏d�����Ȃ����A�A�Ԃł͂Ȃ��B<br>
     *                        ���N���X��`���A@�L�薳���̃t�B�[���h�̏��Ԃ������Ē�`���邱�ƁB<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():6��Ă΂��<br>
     *                        (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     *                        (��ԕω�) -:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"columnIndex in FileLineObject is not sequential order."<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X<br>
     *                        �E���b�v���ꂽ��O�FIllegalStateException<br>
     * <br>
     *                        ��O�B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��E�Ȃ��̃t�B�[���h�������A�܂��e�t�B�[���h��columnIndex�l�����Ԃł͂Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub34> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub34>(
                fileName, AbstractFileLineIterator_Stub34.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildFields");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(6, VMOUTUtil
                    .getCallCount(Field.class, "getAnnotation"));
            assertEquals(2, VMOUTUtil.getCallCount(Class.class,
                    "getDeclaredFields"));

            assertEquals(FileException.class, e.getClass());
            assertEquals(
                    "columnIndex in FileLineObject is not sequential order.", e
                            .getMessage());
            assertSame(fileName, e.getFileName());
            assertSame(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testBuildFields07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     * <br>
     *                        �ȉ��̐ݒ�͌p�����̐e�N���X�ɒ�`����Ă���B<br>
     *                        �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     *                        - �t�B�[���h�FString noMappingColumn1<br>
     *                        - �t�B�[���h�FString noMappingColumn2<br>
     *                        �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex�͏d�����Ȃ��B<br>
     *                        ���N���X���p���֌W�ɂ���A�e�N���X�ɂ�@InputFileColumn�ݒ肠��̃t�B�[���h������B<br>
     *                        ���N���X��`���A@�L�薳���̃t�B�[���h�̏��Ԃ������Ē�`���邱�ƁB<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():9��Ă΂��<br>
     *                        (��ԕω�) this.fields:this.clazz�ɑ΂���t�B�[���h�v�f3������Field�z��C���X�^���X<br>
     *                        �P�Dcolumn1<br>
     *                        �Q�Dcolumn2<br>
     *                        �R�Dcolumn3<br>
     * <br>
     *                        �����Ԃ�columnIndex��<br>
     *                        (��ԕω�) Class.getDeclaredFields():3��Ă΂��<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��E�Ȃ��̃t�B�[���h�������A�e�t�B�[���h��columnIndex�l�ɏd�����Ȃ��A�܂��t�B�[���h�̒�`���e�N���X�ɂ�����ꍇ�A
     *                        �e�N���X���܂߂��}�b�s���O�Ώۃt�B�[���h�̏�񂪐������ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub36> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub36>(
                fileName, AbstractFileLineIterator_Stub36.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildFields");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(9, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        Object result_fields = (Field[]) UTUtil.getPrivateField(
                fileLineIterator, "fields");
        assertEquals(Field[].class, result_fields.getClass());
        Field[] fields = (Field[]) result_fields;
        assertEquals(3, fields.length);
        assertEquals("column1", fields[0].getName());
        assertEquals("column2", fields[1].getName());
        assertEquals("column3", fields[2].getName());
        assertEquals(3, VMOUTUtil
                .getCallCount(Class.class, "getDeclaredFields"));
    }

    /**
     * testBuildFields08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn��@OutputFileColumn�̐ݒ肪����t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn��@OutputFileColumn�̐ݒ�<br> > columnIndex�F2<br>
     *                                           > ���̑����ځF�f�t�H���g�l<br>
     *                                           - �t�B�[���h�FString column1<br>
     * @InputFileColumn��@OutputFileColumn�̐ݒ�<br> > columnIndex�F0<br>
     *                                           > ���̑����ځF�f�t�H���g�l<br>
     *                                           - �t�B�[���h�FString column2<br>
     * @InputFileColumn��@OutputFileColumn�̐ݒ�<br> > columnIndex�F1<br>
     *                                           > ���̑����ځF�f�t�H���g�l<br>
     *                                           ��columnIndex���d�����Ȃ��B<br>
     *                                           (���) this.fileName:String�C���X�^���X<br>
     *                                           "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                                           (���) this.fields:null<br>
     *                                           (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                                           Map<String, ColumnParser>�C���X�^���X<br>
     *                                           �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                                           ���Ғl�F(��ԕω�) Field.getAnnotation():3��Ă΂��<br>
     *                                           (��ԕω�) this.fields:this.clazz�ɑ΂���t�B�[���h�v�f3������Field�z��C���X�^���X<br>
     *                                           �P�Dcolumn1<br>
     *                                           �Q�Dcolumn2<br>
     *                                           �R�Dcolumn3<br>
     * <br>
     *                                           �����Ԃ�columnIndex��<br>
     *                                           (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     * <br>
     *                                           ����B<br>
     *                                           �t�B�[���hclazz��������@InputFileColumn��@OutputFileColumn�̐ݒ肠��̃t�B�[���h�������A�܂��e�t�B�[���h��columnIndex�l�ɏd�����Ȃ��ꍇ
     *                                           �A�}�b�s���O�Ώۃt�B�[���h�̏�񂪐������ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub37> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub37>(
                fileName, AbstractFileLineIterator_Stub37.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildFields");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(6, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        Object result_fields = (Field[]) UTUtil.getPrivateField(
                fileLineIterator, "fields");
        assertEquals(Field[].class, result_fields.getClass());
        Field[] fields = (Field[]) result_fields;
        assertEquals(3, fields.length);
        assertEquals("column1", fields[0].getName());
        assertEquals("column2", fields[1].getName());
        assertEquals("column3", fields[2].getName());
        assertEquals(2, VMOUTUtil
                .getCallCount(Class.class, "getDeclaredFields"));
    }

    /**
     * testBuildFields09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FB,G <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():1��Ă΂��<br>
     *                        (��ԕω�) this.fields:null<br>
     *                        (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     *                        (��ԕω�) -:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                        �E������O�FIllegalStateException<br>
     *                        �E���b�Z�[�W�F"Column Index in FileLineObject is bigger than the total number of the field."<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                        �ُ�B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��̃t�B�[���h�̂ݎ����A�܂��e�t�B�[���h��columnIndex�l�ɏd�����Ȃ���0����n�܂�A�Ԃł͂Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����
     *                        �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub38> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub38>(
                fileName, AbstractFileLineIterator_Stub38.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildFields");
            fail("FileException���������Ă��܂���B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals("Column Index in FileLineObject is bigger than the "
                    + "total number of the field.", e.getMessage());
            assertEquals(fileName, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil
                    .getCallCount(Field.class, "getAnnotation"));
            Object result_fields = UTUtil.getPrivateField(fileLineIterator,
                    "fields");
            assertNull(result_fields);
            assertEquals(2, VMOUTUtil.getCallCount(Class.class,
                    "getDeclaredFields"));
        }
    }

    /**
     * testBuildFields10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FB,G <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F-1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():3��Ă΂��<br>
     *                        (��ԕω�) this.fields:null<br>
     *                        (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     *                        (��ԕω�) -:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                        �E������O�FIllegalStateException<br>
     *                        �E���b�Z�[�W�F"Column Index in FileLineObject is the minus number."<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                        �ُ�B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��̃t�B�[���h�̂ݎ����A�܂��e�t�B�[���h��columnIndex�l�ɏd�����Ȃ����A�}�C�i�X�l���ݒ肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����
     *                        �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub39> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub39>(
                fileName, AbstractFileLineIterator_Stub39.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildFields");
            fail("FileException���������Ă��܂���B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals("Column Index in FileLineObject is the minus number.",
                    e.getMessage());
            assertEquals(fileName, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(3, VMOUTUtil
                    .getCallCount(Field.class, "getAnnotation"));
            Object result_fields = UTUtil.getPrivateField(fileLineIterator,
                    "fields");
            assertNull(result_fields);
            assertEquals(2, VMOUTUtil.getCallCount(Class.class,
                    "getDeclaredFields"));
        }
    }

    /**
     * testBuildFields11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FB,G <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Flong column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        ��this.columnParserMap�ɑ��݂��Ȃ��^�C�v�̃t�B�[���h������B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.fields:null<br>
     *                        (���) this.columnParserMap:�ȉ��̗v�f������<br>
     *                        Map<String, ColumnParser>�C���X�^���X<br>
     *                        �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Field.getAnnotation():2��Ă΂��<br>
     *                        (��ԕω�) this.fields:null<br>
     *                        (��ԕω�) Class.getDeclaredFields():2��Ă΂��<br>
     *                        (��ԕω�) -:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                        �E������O�FIllegalStateException<br>
     *                        �E���b�Z�[�W�F"There is a type which isn't supported in a mapping target field in FileLineObject."<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                        �ُ�B<br>
     *                        �t�B�[���hclazz��������@InputFileColumn�ݒ肠��̃t�B�[���h�̂ݎ����A�Ώۃt�B�[���h��this.columnParserMap�ɑ��݂��Ȃ��^�̃t�B�[���h�̏ꍇ�A��O���������邱�Ƃ��m�F����
     *                        �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildFields11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub90> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub90>(
                fileName, AbstractFileLineIterator_Stub90.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildFields");
            fail("FileException���������Ă��܂���B");
        } catch (FileException e) {
            // ��O�̊m�F
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals("There is a type which isn't supported in a mapping "
                    + "target field in FileLineObject.", e.getMessage());
            assertEquals(fileName, e.getFileName());

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil
                    .getCallCount(Field.class, "getAnnotation"));
            Object result_fields = UTUtil.getPrivateField(fileLineIterator,
                    "fields");
            assertNull(result_fields);
            assertEquals(2, VMOUTUtil.getCallCount(Class.class,
                    "getDeclaredFields"));
        }
    }

    /**
     * testBuildStringConverter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�����ĂȂ��B<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) this.stringConverter:null<br>
     * (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     * (���) this.currentLineCount:0<br>
     * (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) Field.getAnnotation�i�j:�Ă΂�Ȃ�<br>
     * (��ԕω�) Class.newInstance():�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.containsKey(Object):�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.put(K, V):�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.get(Object):�Ă΂�Ȃ�<br>
     * (��ԕω�) this.stringConverters:�v�f�������Ȃ�StringConverter�z��C���X�^���X<br>
     * (��ԕω�) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz���t�B�[���h�������ĂȂ��ꍇ�AStringConverter�֘A�t�B�[���h�������(StringConverter���Ȃ�)����������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        assertEquals(0, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(0, stringConverters.length);
        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(0, stringConverterCacheMap.size());
    }

    /**
     * testBuildStringConverter02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) this.stringConverter:null<br>
     * (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     * (���) this.currentLineCount:0<br>
     * (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) Field.getAnnotation�i�j:�Ă΂�Ȃ�<br>
     * (��ԕω�) Class.newInstance():�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.containsKey(Object):�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.put(K, V):�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.get(Object):�Ă΂�Ȃ�<br>
     * (��ԕω�) this.stringConverters:�v�f�������Ȃ�StringConverter�z��C���X�^���X<br>
     * (��ԕω�) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz��@InputFileColumn�ݒ�Ȃ��̃t�B�[���h�i�P�j�̂ݎ��ꍇ�AStringConverter�֘A�t�B�[���h�������(StringConverter���Ȃ�)����������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub40> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub40>(
                fileName, AbstractFileLineIterator_Stub40.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        assertEquals(0, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(0, stringConverters.length);
        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(0, stringConverterCacheMap.size());
    }

    /**
     * testBuildStringConverter03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.stringConverter:null<br>
     *                        (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.newInstance():1��Ă΂��<br>
     *                        (��ԕω�) Map.containsKey(Object):1��Ă΂��<br>
     *                        (��ԕω�) Map.put(K, V):1��Ă΂��<br>
     *                        (��ԕω�) Map.get(Object):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��C���X�^���X<br>
     *                        �E[0]�FStringConverterToUpperCase�C���X�^���X<br>
     * <br>
     *                        ��this.stringConverterCacheMap�Ɋi�[����Ă���StringConverterToUpperCase�C���X�^���X�Ɠ������́B<br>
     *                        (��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E StringConverterToUpperCase.class<br>
     *                        =StringConverterToUpperCase�C���X�^���X<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���h�i
     *                        �P�j�̂ݎ��ꍇ�AStringConverter�z��ɂ�1��StringConverter���A�L���b�V���ɂ͂P��StringConverter���ݒ肳���A���S�������C���X�^���X�̂��Ƃ��m
     *                        �F �� �� �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub41> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub41>(
                fileName, AbstractFileLineIterator_Stub41.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(1, stringConverters.length);
        assertEquals(StringConverterToUpperCase.class, stringConverters[0]
                .getClass());
        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(1, stringConverterCacheMap.size());
        assertSame(stringConverters[0], stringConverterCacheMap
                .get(StringConverterToUpperCase.class));
    }

    /**
     * testBuildStringConverter04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) this.stringConverter:null<br>
     * (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     * (���) this.currentLineCount:0<br>
     * (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) Field.getAnnotation�i�j:�Ă΂�Ȃ�<br>
     * (��ԕω�) Class.newInstance():�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.containsKey(Object):�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.put(K, V):�Ă΂�Ȃ�<br>
     * (��ԕω�) Map.get(Object):�Ă΂�Ȃ�<br>
     * (��ԕω�) this.stringConverters:�v�f�������Ȃ�StringConverter�z��C���X�^���X<br>
     * (��ԕω�) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz��@InputFileColumn�ݒ�Ȃ��̃t�B�[���h�i�R�j�̂ݎ��ꍇ�AStringConverter�֘A�t�B�[���h�������(StringConverter���Ȃ�)����������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub42> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub42>(
                fileName, AbstractFileLineIterator_Stub42.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Field.class, "getAnnotation"));
        assertEquals(0, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(0, stringConverters.length);
        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(0, stringConverterCacheMap.size());
    }

    /**
     * testBuildStringConverter05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        ��stringConverter���S�������B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.stringConverter:null<br>
     *                        (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.newInstance():1��Ă΂��<br>
     *                        (��ԕω�) Map.containsKey(Object):3��Ă΂��<br>
     *                        (��ԕω�) Map.put(K, V):1��Ă΂��<br>
     *                        (��ԕω�) Map.get(Object):2��Ă΂��<br>
     *                        (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��C���X�^���X<br>
     *                        �E[0]�FStringConverterToUpperCase�C���X�^���X<br>
     *                        �E[1]�FStringConverterToUpperCase�C���X�^���X<br>
     *                        �E[2]�FStringConverterToUpperCase�C���X�^���X<br>
     * <br>
     *                        ���R�S��this.stringConverterCacheMap�Ɋi�[����Ă���StringConverterToUpperCase�C���X�^���X�Ɠ������́B<br>
     *                        (��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E StringConverterToUpperCase.class<br>
     *                        =StringConverterToUpperCase�C���X�^���X<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���h�i
     *                        �R�j�̂ݎ����A�ݒ肳�ꂽstringConverter���S�t�B�[���h�����̏ꍇ�AStringConverter�z��ɂ͂R��StringConverter���A�L���b�V���ɂ͂P��StringCo
     *                        n v e r t e r �� �� �� �� �� �� �A �� �� �S �� �� �� �C �� �X �^ �� �X �� �� �� �� �m �F �� �� �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub43> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub43>(
                fileName, AbstractFileLineIterator_Stub43.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(3, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(2, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(3, stringConverters.length);
        assertEquals(StringConverterToUpperCase.class, stringConverters[0]
                .getClass());
        assertSame(stringConverters[0], stringConverters[1]);
        assertSame(stringConverters[0], stringConverters[2]);
        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(1, stringConverterCacheMap.size());
        assertSame(stringConverters[0], stringConverterCacheMap
                .get(StringConverterToUpperCase.class));
    }

    /**
     * testBuildStringConverter06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��E�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString noMappingColumn1<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        ��stringConverter���S�������B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.stringConverter:null<br>
     *                        (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.newInstance():1��Ă΂��<br>
     *                        (��ԕω�) Map.containsKey(Object):2��Ă΂��<br>
     *                        (��ԕω�) Map.put(K, V):1��Ă΂��<br>
     *                        (��ԕω�) Map.get(Object):1��Ă΂��<br>
     *                        (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��C���X�^���X<br>
     *                        �E[0]�FStringConverterToUpperCase�C���X�^���X<br>
     *                        �E[1]�Fnull<br>
     *                        �E[2]�FStringConverterToUpperCase�C���X�^���X<br>
     * <br>
     *                        ���i�[����Ă���C���X�^���X��this.stringConverterCacheMap�Ɋi�[����Ă���StringConverterToUpperCase�C���X�^���X�Ɠ������́B<br>
     *                        (��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E StringConverterToUpperCase.class<br>
     *                        =StringConverterToUpperCase�C���X�^���X<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���i
     *                        �Q�j�h�ƂȂ��t�B�[���h�i�P�j�������A�ݒ肳�ꂽstringConverter���S�t�B�[���h�����̏ꍇ�AStringConverter�z��ɂ͂Q��StringConverter���A�L���b�V����
     *                        �� �P �� �� S t r i n g C o n v e r t e r �� �� �� �� �� �� �A �� �� �S �� �� �� �C �� �X �^ �� �X �� �� �� �� �m �F �� �� �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub44> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub44>(
                fileName, AbstractFileLineIterator_Stub44.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(2, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(2, stringConverters.length);
        assertEquals(StringConverterToUpperCase.class, stringConverters[0]
                .getClass());
        assertSame(stringConverters[0], stringConverters[1]);
        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(1, stringConverterCacheMap.size());
        assertSame(stringConverters[0], stringConverterCacheMap
                .get(StringConverterToUpperCase.class));
    }

    /**
     * testBuildStringConverter07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��E�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column5<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F4<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToLowerCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > stringConverter�F<br>
     *                        NullStringConverter.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToLowerCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        ��2�ӏ��œ���stringConverter�𗘗p���Ă���B<br>
     *                        stringConverter�̎�ނ͂R��<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.stringConverter:null<br>
     *                        (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.newInstance():3��Ă΂��<br>
     *                        (��ԕω�) Map.containsKey(Object):5��Ă΂��<br>
     *                        (��ԕω�) Map.put(K, V):3��Ă΂��<br>
     *                        (��ԕω�) Map.get(Object):2��Ă΂��<br>
     *                        (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��C���X�^���X<br>
     *                        �E[0]�FNullStringConverter�C���X�^���X<br>
     *                        �E[1]�FStringConverterToLowerCase�C���X�^���X<br>
     *                        �E[2]�FNullStringConverter�C���X�^���X<br>
     *                        �E[3]�FStringConverterToUpperCase�C���X�^���X<br>
     *                        �E[4]�FStringConverterToLowerCase�C���X�^���X<br>
     * <br>
     *                        ���i�[����Ă���C���X�^���X��this.stringConverterCacheMap�Ɋi�[����Ă��铯���^�̃C���X�^���X�Ɠ������́B<br>
     *                        (��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E NullStringConverter.class<br>
     *                        =NullStringConverter�C���X�^���X<br>
     *                        �E StringConverterToUpperCase.class<br>
     *                        =StringConverterToUpperCase�C���X�^���X<br>
     *                        �E StringConverterToLowerCase.class<br>
     *                        =StringConverterToLowerCase�C���X�^���X<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���h�i5�j�̂ݎ����A�ݒ肳�ꂽstringConverter���d��(2�ӏ�)������ꍇ�AStringConverter�z��ɂ͂T��StringConverter��
     *                        �A�L���b�V���ɂ͂R��StringConverter���ݒ肳���A������^�C�v�͓����C���X�^���X�𗘗p���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub45> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub45>(
                fileName, AbstractFileLineIterator_Stub45.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(3, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(5, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(3, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(2, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(5, stringConverters.length);
        assertSame(NullStringConverter.class, stringConverters[0].getClass());
        assertSame(StringConverterToLowerCase.class, stringConverters[1]
                .getClass());
        assertSame(NullStringConverter.class, stringConverters[2].getClass());
        assertSame(StringConverterToUpperCase.class, stringConverters[3]
                .getClass());
        assertSame(StringConverterToLowerCase.class, stringConverters[4]
                .getClass());

        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(3, stringConverterCacheMap.size());
        assertSame(stringConverters[0], stringConverterCacheMap
                .get(NullStringConverter.class));
        assertSame(stringConverters[1], stringConverterCacheMap
                .get(StringConverterToLowerCase.class));
        assertSame(stringConverters[2], stringConverterCacheMap
                .get(NullStringConverter.class));
        assertSame(stringConverters[3], stringConverterCacheMap
                .get(StringConverterToUpperCase.class));
        assertSame(stringConverters[4], stringConverterCacheMap
                .get(StringConverterToLowerCase.class));
    }

    /**
     * testBuildStringConverter08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > stringConverter�F<br>
     *                        StringConverter.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��stringConverter���C���^�t�F�[�X�B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.stringConverter:null<br>
     *                        (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.newInstance():2��Ă΂��<br>
     *                        (��ԕω�) Map.containsKey(Object):2��Ă΂��<br>
     *                        (��ԕω�) Map.put(K, V):1��Ă΂��<br>
     *                        (��ԕω�) Map.get(Object):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.stringConverters:null<br>
     *                        (��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E NullStringConverter.class<br>
     *                        =NullStringConverter�C���X�^���X<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Failed in an instantiate of a stringConverter."<br>
     *                        �E������O�FInstantiationException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F-1<br>
     *                        �E�J�������Fcolumn2<br>
     *                        �E�J�����C���f�b�N�X�F1<br>
     * <br>
     *                        ��O�B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���h�������A�ݒ肳�ꂽstringConverter���C���^�t�F�[�X�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub46> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub46>(
                fileName, AbstractFileLineIterator_Stub46.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");
            fail("FileLineException���X���[����܂���ł����B");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(2, VMOUTUtil.getCallCount(Class.class, "newInstance"));
            assertEquals(2, VMOUTUtil.getCallCount(Map.class, "containsKey"));
            assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
            assertEquals(0, VMOUTUtil.getCallCount(Map.class, "get"));
            StringConverter[] stringConverters = (StringConverter[]) UTUtil
                    .getPrivateField(fileLineIterator, "stringConverters");
            assertNull(stringConverters);

            Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                    .getPrivateField(fileLineIterator.getClass(),
                            "stringConverterCacheMap");
            assertEquals(1, stringConverterCacheMap.size());
            Object cacheMap01 = stringConverterCacheMap
                    .get(NullStringConverter.class);
            assertSame(NullStringConverter.class, cacheMap01.getClass());

            assertEquals(FileLineException.class, e.getClass());
            assertEquals("Failed in an instantiate of a stringConverter.", e
                    .getMessage());
            assertEquals(InstantiationException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
            assertEquals(-1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals(1, e.getColumnIndex());
        }
    }

    /**
     * testBuildStringConverter09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > stringConverter�F�f�t�H���g�Œ��ڃC���X�^���X���ł��Ȃ��Ǝ�StringConverter.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.stringConverter:null<br>
     *                        (���) this.stringConverterCacheMap:�v�f�������ĂȂ�HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.newInstance():2��Ă΂��<br>
     *                        (��ԕω�) Map.containsKey(Object):2��Ă΂��<br>
     *                        (��ԕω�) Map.put(K, V):1��Ă΂��<br>
     *                        (��ԕω�) Map.get(Object):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) this.stringConverters:null<br>
     *                        (��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E NullStringConverter.class<br>
     *                        =NullStringConverter�C���X�^���X<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileLineException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"Failed in an instantiate of a stringConverter."<br>
     *                        �E������O�FIllegalAccessException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     *                        �E�s�ԍ��F-1<br>
     *                        �E�J�������Fcolumn2<br>
     *                        �E�J�����C���f�b�N�X�F1<br>
     * <br>
     *                        ��O�B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���h�������A�ݒ肳�ꂽstringConverter���f�t�H���g�R���X�g���N�^�ŃC���X�^���X���ł��Ȃ��N���X�̏ꍇ�A��O���������邱�Ƃ��m�F����
     *                        �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub47> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub47>(
                fileName, AbstractFileLineIterator_Stub47.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");
            fail("FileLineException���X���[����܂���ł����B");
        } catch (FileLineException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F

            assertEquals(2, VMOUTUtil.getCallCount(Class.class, "newInstance"));
            assertEquals(2, VMOUTUtil.getCallCount(Map.class, "containsKey"));
            assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
            assertEquals(0, VMOUTUtil.getCallCount(Map.class, "get"));
            StringConverter[] stringConverters = (StringConverter[]) UTUtil
                    .getPrivateField(fileLineIterator, "stringConverters");
            assertNull(stringConverters);

            Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                    .getPrivateField(fileLineIterator.getClass(),
                            "stringConverterCacheMap");
            assertEquals(1, stringConverterCacheMap.size());
            Object cacheMap01 = stringConverterCacheMap
                    .get(NullStringConverter.class);
            assertSame(NullStringConverter.class, cacheMap01.getClass());

            assertEquals(FileLineException.class, e.getClass());
            assertEquals("Failed in an instantiate of a stringConverter.", e
                    .getMessage());
            assertEquals(IllegalAccessException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
            assertEquals(-1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals(1, e.getColumnIndex());
        }
    }

    /**
     * testBuildStringConverter10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToUpperCase.class<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > stringConverter�F<br>
     *                        StringConverterToLowerCase<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        ��stringConverter���S���Ⴄ�B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) this.stringConverter:null<br>
     *                        (���) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E NullStringConverter.class<br>
     *                        =NullStringConverter�C���X�^���X<br>
     *                        �E StringConverterToUpperCase.class<br>
     *                        =StringConverterToUpperCase�C���X�^���X<br>
     *                        �E StringConverterToLowerCase.class<br>
     *                        =StringConverterToLowerCase�C���X�^���X<br>
     *                        (���) this.currentLineCount:0<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.newInstance():�Ă΂�Ȃ�<br>
     *                        (��ԕω�) Map.containsKey(Object):3��Ă΂��<br>
     *                        (��ԕω�) Map.put(K, V):�Ă΂�Ȃ�<br>
     *                        (��ԕω�) Map.get(Object):3��Ă΂��<br>
     *                        (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��C���X�^���X<br>
     *                        �E[0]�FNullStringConverter�C���X�^���X<br>
     *                        �E[1]�FStringConverterToLowerCase�C���X�^���X<br>
     *                        �E[2]�FStringConverterToUpperCase�C���X�^���X<br>
     * <br>
     *                        ���i�[����Ă���C���X�^���X��this.stringConverterCacheMap�Ɋi�[����Ă��铯���^�̃C���X�^���X�Ɠ������́B<br>
     *                        (��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������HashMap<Class, StringConverter>�C���X�^���X<br>
     *                        �E NullStringConverter.class<br>
     *                        =NullStringConverter�C���X�^���X<br>
     *                        �E StringConverterToUpperCase.class<br>
     *                        =StringConverterToUpperCase�C���X�^���X<br>
     *                        �E StringConverterToLowerCase.class<br>
     *                        =StringConverterToLowerCase�C���X�^���X<br>
     * <br>
     *                        ����B<br>
     *                        Static�t�B�[���hthis.stringConverterCacheMap�ɃL���b�V��������ꍇ�AStringConverter�̃C���X�^���X�����Ȃ��ŃL���b�V���𗘗p���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildStringConverter10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub48> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub48>(
                fileName, AbstractFileLineIterator_Stub48.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<Class, StringConverter> cache_stringConverterCacheMap = new HashMap<Class, StringConverter>();
        StringConverter cache01 = new NullStringConverter();
        StringConverter cache02 = new StringConverterToLowerCase();
        StringConverter cache03 = new StringConverterToUpperCase();
        cache_stringConverterCacheMap.put(NullStringConverter.class, cache01);
        cache_stringConverterCacheMap.put(StringConverterToLowerCase.class,
                cache02);
        cache_stringConverterCacheMap.put(StringConverterToUpperCase.class,
                cache03);
        UTUtil.setPrivateField(fileLineIterator, "stringConverterCacheMap",
                cache_stringConverterCacheMap);

        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���ł��łɐݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildStringConverters");
        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Class.class, "newInstance"));
        assertEquals(3, VMOUTUtil.getCallCount(Map.class, "containsKey"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "put"));
        assertEquals(3, VMOUTUtil.getCallCount(Map.class, "get"));
        StringConverter[] stringConverters = (StringConverter[]) UTUtil
                .getPrivateField(fileLineIterator, "stringConverters");
        assertEquals(3, stringConverters.length);
        assertSame(cache01, stringConverters[0]);
        assertSame(cache02, stringConverters[1]);
        assertSame(cache03, stringConverters[2]);

        Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                .getPrivateField(fileLineIterator.getClass(),
                        "stringConverterCacheMap");
        assertEquals(3, stringConverterCacheMap.size());
        assertSame(cache01, stringConverterCacheMap
                .get(NullStringConverter.class));
        assertSame(cache02, stringConverterCacheMap
                .get(StringConverterToLowerCase.class));
        assertSame(cache03, stringConverterCacheMap
                .get(StringConverterToUpperCase.class));
    }

    /**
     * testBuildMethods01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�����ĂȂ��B<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) Class.getMethod(String):�Ă΂�Ȃ�<br>
     * (��ԕω�) this.methods:�v�f�������Ȃ�Method�z��C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz���t�B�[���h�������ĂȂ��ꍇ�Athis.methods�Ƀ��\�b�h��񂪐�������Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildMethods01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildMethods");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Class.class, "getMethod"));
        Method[] methods = (Method[]) UTUtil.getPrivateField(fileLineIterator,
                "methods");
        assertEquals(0, methods.length);
    }

    /**
     * testBuildMethods02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) Class.getMethod(String):�Ă΂�Ȃ�<br>
     * (��ԕω�) this.methods:�v�f�������Ȃ�Method�z��C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz��@InputFileColumn�ݒ�Ȃ��̃t�B�[���h�i�P�j�̂ݎ��ꍇ�Athis.methods�Ƀ��\�b�h��񂪐�������Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildMethods02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub50> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub50>(
                fileName, AbstractFileLineIterator_Stub50.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildMethods");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Class.class, "getMethod"));
        Method[] methods = (Method[]) UTUtil.getPrivateField(fileLineIterator,
                "methods");
        assertEquals(0, methods.length);
    }

    /**
     * testBuildMethods03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        �E�t�B�[���h�ɑ΂���Z�b�^���\�b�h�����݂���B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.getMethod(String):1��Ă΂��<br>
     *                        (��ԕω�) this.methods:�ȉ��̗v�f������Method�z��C���X�^���X<br>
     *                        �E[0]�FsetColumn1(String)�̃��\�b�h�C���X�^���X<br>
     * <br>
     *                        �����\�b�h���ƈ����̌^���m�F�|�C���g<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���h�i�P�j�̂ݎ����A���̃t�B�[���h�ɑ΂���Z�b�^���\�b�h�����݂���ꍇ�Athis.methods�ɂ��̃t�B�[���h�ɑ΂���Z�b�^
     *                        �� �\ �b �h �� �� �� �� �� �� �� �� �� �� �� �� �m �F �� �� �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildMethods03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub51> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub51>(
                fileName, AbstractFileLineIterator_Stub51.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildMethods");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Class.class, "getMethod"));
        Method[] methods = (Method[]) UTUtil.getPrivateField(fileLineIterator,
                "methods");
        assertEquals(1, methods.length);
        assertEquals("setColumn1", methods[0].getName());
        Class[] method01_param = methods[0].getParameterTypes();
        assertEquals(1, method01_param.length);
        assertSame(String.class, method01_param[0]);
    }

    /**
     * testBuildMethods04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        �E�t�B�[���h�ɑ΂���Z�b�^���\�b�h�����݂��Ȃ��B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.getMethod(String):1��Ă΂��<br>
     *                        (��ԕω�) this.methods:null<br>
     *                        (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     *                        �E���b�Z�[�W�F"The setter method of column doesn't exist."<br>
     *                        �E������O�FNoSuchMethodException<br>
     *                        �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                        ��O�B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��̃t�B�[���h�������A���̃t�B�[���h�ɑ΂���Z�b�^���\�b�h�������Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildMethods04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub52> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub52>(
                fileName, AbstractFileLineIterator_Stub52.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildMethods");
            fail("FileException���X���[����܂���ł����B");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(1, VMOUTUtil.getCallCount(Class.class, "getMethod"));
            Method[] methods = (Method[]) UTUtil.getPrivateField(
                    fileLineIterator, "methods");
            assertNull(methods);

            assertSame(FileException.class, e.getClass());
            assertEquals("The setter method of column doesn't exist.", e
                    .getMessage());
            assertSame(NoSuchMethodException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());
        }
    }

    /**
     * testBuildMethods05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_bulidFields01.txt"<br>
     * (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) Class.getMethod(String):�Ă΂�Ȃ�<br>
     * (��ԕω�) this.methods:�v�f�������Ȃ�Method�z��C���X�^���X<br>
     * <br>
     * ����B<br>
     * �t�B�[���hclazz��@InputFileColumn�ݒ�Ȃ��̃t�B�[���h�i�R�j�̂ݎ��ꍇ�Athis.methods�Ƀ��\�b�h��񂪐�������Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildMethods05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub53> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub53>(
                fileName, AbstractFileLineIterator_Stub53.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildMethods");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(0, VMOUTUtil.getCallCount(Class.class, "getMethod"));
        Method[] methods = (Method[]) UTUtil.getPrivateField(fileLineIterator,
                "methods");
        assertEquals(0, methods.length);
    }

    /**
     * testBuildMethods06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��E�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FString noMappingColumn1<br>
     *                        - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.getMethod(String):2��Ă΂��<br>
     *                        (��ԕω�) this.methods:�ȉ��̗v�f������Method�z��C���X�^���X<br>
     *                        �E[0]�FsetColumn1(String)�̃��\�b�h�C���X�^���X<br>
     *                        �E[1]�FsetColumn2(String)�̃��\�b�h�C���X�^���X<br>
     * <br>
     *                        �����\�b�h���ƈ����̌^���m�F�|�C���g<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ肠��E�Ȃ��̃t�B�[���h�i
     *                        �R�j�𗼕������A@�ݒ肠��t�B�[���h�̃Z�b�^���\�b�h�����݂���ꍇ�Athis.methods�Ɂ��ݒ肠��t�B�[���h�ɑ΂��郁�\�b�h���̂ݐ�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildMethods06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub54> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub54>(
                fileName, AbstractFileLineIterator_Stub54.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildMethods");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(2, VMOUTUtil.getCallCount(Class.class, "getMethod"));
        Method[] methods = (Method[]) UTUtil.getPrivateField(fileLineIterator,
                "methods");
        assertEquals(2, methods.length);
        assertEquals("setColumn1", methods[0].getName());
        Class[] method01_param = methods[0].getParameterTypes();
        assertEquals(1, method01_param.length);
        assertSame(String.class, method01_param[0]);
        assertEquals("setColumn2", methods[1].getName());
        Class[] method02_param = methods[1].getParameterTypes();
        assertEquals(1, method01_param.length);
        assertSame(String.class, method02_param[0]);
    }

    /**
     * testBuildMethods07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.clazz:�ȉ��̐ݒ�����C���X�^���X���\Class�̃C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F"\""<br>
     * - ���̑����ځF�f�t�H���g�l<br>
     * �E@InputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�Fint column1<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FDate column4<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                        > columnFormat�Fyyyy/MM/dd<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        - �t�B�[���h�FBigDecimal column3<br>
     * @InputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                        > columnFormat�F###,###<br>
     *                        > ���̑����ځF�f�t�H���g�l<br>
     *                        ��columnIndex���d�����Ȃ��B<br>
     *                        (���) this.fileName:String�C���X�^���X<br>
     *                        "AbstractFileLineIterator_bulidFields01.txt"<br>
     *                        (���) #buildFields�̎��s:�e�X�g�O��#buildFields()�����s����B<br>
     * <br>
     *                        ���Ғl�F(��ԕω�) Class.getMethod(String):4��Ă΂��<br>
     *                        (��ԕω�) this.methods:�ȉ��̗v�f������Method�z��C���X�^���X<br>
     *                        �E[0]�FsetColumn1(int)�̃��\�b�h�C���X�^���X<br>
     *                        �E[1]�FsetColumn2(String)�̃��\�b�h�C���X�^���X<br>
     *                        �E[2]�FsetColumn3(BigDecimal)�̃��\�b�h�C���X�^���X<br>
     *                        �E[3]�FsetColumn4(Date)�̃��\�b�h�C���X�^���X<br>
     * <br>
     *                        �����\�b�h���ƈ����̌^���m�F�|�C���g<br>
     * <br>
     *                        ����B<br>
     *                        �t�B�[���hclazz��@InputFileColumn�ݒ�Ȃ��̃t�B�[���h�i�S�j�̂ݎ����A���̃t�B�[���h�ɑ΂���Z�b�^���\�b�h�����݂���ꍇ�Athis.methods�Ƀt�B�[���h�ɑ΂���Z�b�^���\
     *                        �b �h �� �� �� �� �� �� �� �� �� �� �� �� �m �F �� �� �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildMethods07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_bulidFields01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        columnParserMap.put("java.util.Date", new DateColumnParser());
        columnParserMap.put("java.math.BigDecimal", new DecimalColumnParser());
        columnParserMap.put("int", new IntColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub55> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub55>(
                fileName, AbstractFileLineIterator_Stub55.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.invokePrivate(fileLineIterator, "buildFields");
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildMethods");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(4, VMOUTUtil.getCallCount(Class.class, "getMethod"));
        Method[] methods = (Method[]) UTUtil.getPrivateField(fileLineIterator,
                "methods");
        assertEquals(4, methods.length);
        assertEquals("setColumn1", methods[0].getName());
        Class[] method01_param = methods[0].getParameterTypes();
        assertEquals(1, method01_param.length);
        assertSame(int.class, method01_param[0]);

        assertEquals("setColumn2", methods[1].getName());
        Class[] method02_param = methods[1].getParameterTypes();
        assertEquals(1, method02_param.length);
        assertSame(String.class, method02_param[0]);

        assertEquals("setColumn3", methods[2].getName());
        Class[] method03_param = methods[2].getParameterTypes();
        assertEquals(1, method03_param.length);
        assertSame(BigDecimal.class, method03_param[0]);

        assertEquals("setColumn4", methods[3].getName());
        Class[] method04_param = methods[3].getParameterTypes();
        assertEquals(1, method04_param.length);
        assertSame(Date.class, method04_param[0]);
    }

    /**
     * testBuildHeader01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildHeader01.txt"<br>
     * (���) this.headerLineCount:0<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.header:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext(�j:this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.header:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * �w�b�_�����Ȃ��ꍇ��this.header����̂��Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildHeader01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildHeader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub60> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub60>(
                fileName, AbstractFileLineIterator_Stub60.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildHeader");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object header_object = UTUtil.getPrivateField(fileLineIterator,
                "header");
        assertEquals(ArrayList.class, header_object.getClass());
        List header = (List) header_object;
        assertEquals(0, header.size());

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testBuildHeader02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildHeader01.txt"<br>
     * (���) this.headerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.header:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext(�j:this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.header:�ȉ��̏�������ArrayList<String>�C���X�^���X<br>
     * �E[0]�F"�P�s�ڃf�[�^"<br>
     * (��ԕω�) LineReader.readLine():1��Ă΂��<br>
     * <br>
     * ����p�^�[���B<br>
     * �w�b�_����1�s�̏ꍇ�Athis.header��1�s�̏�񂪊i�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildHeader02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildHeader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub61> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub61>(
                fileName, AbstractFileLineIterator_Stub61.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildHeader");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object header_object = UTUtil.getPrivateField(fileLineIterator,
                "header");
        assertEquals(ArrayList.class, header_object.getClass());
        List header = (List) header_object;
        assertEquals(1, header.size());
        assertEquals("1�s�ڃf�[�^", header.get(0));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(1, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testBuildHeader03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildHeader01.txt"<br>
     * (���) this.headerLineCount:3<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.header:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext(�j:this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.header:�ȉ��̏�������ArrayList<String>�C���X�^���X<br>
     * �E[0]�F"�P�s�ڃf�[�^"<br>
     * �E[1]�F"�Q�s�ڃf�[�^"<br>
     * �E[2]�F"�R�s�ڃf�[�^"<br>
     * (��ԕω�) LineReader.readLine():3��Ă΂��<br>
     * <br>
     * ����p�^�[���B<br>
     * �w�b�_����3�s�̏ꍇ�Athis.header��3�s�̏�񂪊i�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildHeader03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildHeader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub62> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub62>(
                fileName, AbstractFileLineIterator_Stub62.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildHeader");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object header_object = UTUtil.getPrivateField(fileLineIterator,
                "header");
        assertEquals(ArrayList.class, header_object.getClass());
        List header = (List) header_object;
        assertEquals(3, header.size());
        assertEquals("1�s�ڃf�[�^", header.get(0));
        assertEquals("2�s�ڃf�[�^", header.get(1));
        assertEquals("3�s�ڃf�[�^", header.get(2));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(3, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testBuildHeader04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildHeader02.txt"<br>
     * (���) this.headerLineCount:3<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * <br>
     * ���w�b�_�s���f�[�^�t�@�C���̍s�������Ȃ��B<br>
     * (���) this.header:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext(�j:this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) LineReader.readLine():2��Ă΂��<br>
     * (��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"The data which can be acquired doesn't exist."<br>
     * �E������O�FNoSuchElementException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O<br>
     * �w�b�_���̍s�����Ώۃf�[�^�̍s�������Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildHeader04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildHeader02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub62> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub62>(
                fileName, AbstractFileLineIterator_Stub62.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildHeader");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("The data which can be acquired doesn't exist.", e
                    .getMessage());
            assertEquals(NoSuchElementException.class, e.getCause().getClass());
            assertSame(fileName, e.getFileName());

            Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                    "lineReader");
            assertEquals(2, VMOUTUtil.getCallCount(lineReader.getClass(),
                    "readLine"));
        }
    }

    /**
     * testBuildHeader05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildHeader01.txt"<br>
     * (���) this.headerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.header:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext(�j:this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():FileException����������B<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Error occurred by reading processing of a File."<br>
     * �E������O�FFileException(LineReader.readLine()�̌��ʃC���X�^���X)<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O<br>
     * �w�b�_���̍s�f�[�^�擾��FileException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildHeader05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildHeader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub61> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub61>(
                fileName, AbstractFileLineIterator_Stub61.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        FileException exception = new FileException("readLine����̗�O�ł�");
        VMOUTUtil.setReturnValueAtAllTimes(lineReader.getClass(), "readLine",
                exception);
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildHeader");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("Error occurred by reading processing of a File.", e
                    .getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testBuildHeader06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildHeader01.txt"<br>
     * (���) this.headerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.header:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext(�j:FileException����������B<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:this.hasNext(�j�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * ��O<br>
     * �Ώۃt�@�C���̏����Ώۍs�m�F�`�F�b�N�ŃG���[�����������ꍇ�A���̗�O�����̂܂ܕԂ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildHeader06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildHeader01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub61> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub61>(
                fileName, AbstractFileLineIterator_Stub61.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        FileException exception = new FileException("readLine����̗�O�ł�");
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "hasNext", exception);
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildHeader");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertSame(exception, e);
        }
    }

    /**
     * testBuildTrailerQueue01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer01.txt"<br>
     * (���) this.trailerLineCount:0<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.trailerQueue:null<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * <br>
     * ����p�^�[���B�i�g���C����0�s�j<br>
     * �g���C�������Ȃ��ꍇ�A�g���C���L���[����������Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildTrailerQueue01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub70> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub70>(
                fileName, AbstractFileLineIterator_Stub70.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildTrailerQueue");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object trailerQueue_object = UTUtil.getPrivateField(fileLineIterator,
                "trailerQueue");
        assertNull(trailerQueue_object);

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testBuildTrailerQueue02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer01.txt"<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.trailerQueue:�ȉ��̗v�f������ArrayBlockingQueue<String>�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * (��ԕω�) LineReader.readLine():1��Ă΂��<br>
     * <br>
     * ����p�^�[���B�i�g���C����1�s�j<br>
     * �g���C������1�s����ꍇ�A�g���C���L���[����������A���̒��Ƀf�[�^����1�s�f�[�^���i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildTrailerQueue02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub71> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub71>(
                fileName, AbstractFileLineIterator_Stub71.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildTrailerQueue");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object trailerQueue_object = UTUtil.getPrivateField(fileLineIterator,
                "trailerQueue");
        assertEquals(ArrayBlockingQueue.class, trailerQueue_object.getClass());
        ArrayBlockingQueue trailerQueue = (ArrayBlockingQueue) trailerQueue_object;
        assertEquals(1, trailerQueue.size());
        assertEquals("3�s�ڃf�[�^", trailerQueue.poll());

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(1, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testBuildTrailerQueue03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer01.txt"<br>
     * (���) this.trailerLineCount:3<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.trailerQueue:�ȉ��̗v�f������ArrayBlockingQueue<String>�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (��ԕω�) LineReader.readLine():3��Ă΂��<br>
     * <br>
     * ����p�^�[���B�i�g���C����3�s�j<br>
     * �g���C������3�s����ꍇ�A�g���C���L���[����������A���̒��Ƀf�[�^����3�s�f�[�^���i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildTrailerQueue03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub72> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub72>(
                fileName, AbstractFileLineIterator_Stub72.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineIterator, "buildTrailerQueue");

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object trailerQueue_object = UTUtil.getPrivateField(fileLineIterator,
                "trailerQueue");
        assertEquals(ArrayBlockingQueue.class, trailerQueue_object.getClass());
        ArrayBlockingQueue trailerQueue = (ArrayBlockingQueue) trailerQueue_object;
        assertEquals(3, trailerQueue.size());
        assertEquals("3�s�ڃf�[�^", trailerQueue.poll());
        assertEquals("4�s�ڃf�[�^", trailerQueue.poll());
        assertEquals("5�s�ڃf�[�^", trailerQueue.poll());

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(3, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testBuildTrailerQueue04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer01.txt"<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.hasNext():FileException��O����������B<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.trailerQueue:�v�f�������Ȃ�ArrayBlockingQueue<String>�C���X�^���X<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * (��ԕω�) ��O:this.hasNext(�j�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B�i�g���C����1�s�j<br>
     * ���̏����Ώۍs�ɑ΂��鑶�݃`�F�b�N�����ŃG���[�����������ꍇ�A���̗�O�����̂܂ܕԂ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testBuildTrailerQueue04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub71> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub71>(
                fileName, AbstractFileLineIterator_Stub71.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�
        FileException exception = new FileException("hasNext�ł̃G���[�ł�");
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "hasNext", exception);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildTrailerQueue");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            Object trailerQueue_object = UTUtil.getPrivateField(
                    fileLineIterator, "trailerQueue");
            assertEquals(ArrayBlockingQueue.class, trailerQueue_object
                    .getClass());
            ArrayBlockingQueue trailerQueue = (ArrayBlockingQueue) trailerQueue_object;
            assertEquals(0, trailerQueue.size());

            Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                    "lineReader");
            assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                    "readLine"));

            assertSame(exception, e);
        }
    }

    /**
     * testBuildTrailerQueue05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer01.txt"<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():FileException��O����������B<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Error occurred by reading processing of a File."<br>
     * �E������O�FFileException(LineReader.readLine()�̌��ʃC���X�^���X)<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B�i�g���C����1�s�j<br>
     * �g���C�����̍s�f�[�^�擾��FileException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildTrailerQueue05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub71> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub71>(
                fileName, AbstractFileLineIterator_Stub71.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�
        FileException exception = new FileException("readLine�ł̃G���[�ł�");
        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        VMOUTUtil.setReturnValueAtAllTimes(lineReader.getClass(), "readLine",
                exception);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildTrailerQueue");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("Error occurred by reading processing of a File.", e
                    .getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testBuildTrailerQueue06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_buildTrailerQueue02.txt"<br>
     * (���) this.trailerLineCount:3<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * <br>
     * ���g���C���s���f�[�^�t�@�C���̍s�������Ȃ��B<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"The data which can be acquired doesn't exist."<br>
     * �E������O�FNoSuchElementException<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * �g���C�����̍s�����Ώۃf�[�^�̍s�������Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildTrailerQueue06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_buildTrailerQueue02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub72> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub72>(
                fileName, AbstractFileLineIterator_Stub72.class,
                columnParserMap);
        UTUtil.invokePrivate(fileLineIterator, "buildLineReader");

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineIterator, "buildTrailerQueue");
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("The data which can be acquired doesn't exist.", e
                    .getMessage());
            assertEquals(NoSuchElementException.class, e.getCause().getClass());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testCloseFile01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_closeFile01.txt"<br>
     * (���) this.reader:Reader�C���X�^���X<br>
     * (���) Reader.close():����I��<br>
     * <br>
     * ���Ғl�F(��ԕω�) reader.close():�Ă΂��<br>
     * <br>
     * ����<br>
     * �N���[�Y���������������s����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCloseFile01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_closeFile01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);
        fileLineIterator.init();

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        fileLineIterator.closeFile();

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(BufferedReader.class, "close"));
    }

    /**
     * testCloseFile02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_closeFile01.txt"<br>
     * (���) this.reader:Reader�C���X�^���X<br>
     * (���) Reader.close():IOException��O������<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Processing of reader was failed."<br>
     * �E������O�FIOException(Reader.close()����̂���)<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O<br>
     * �N���[�X������IOException�����������ꍇ�AFileException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCloseFile02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_closeFile01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);
        fileLineIterator.init();

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�
        IOException exception = new IOException();
        VMOUTUtil.setExceptionAtAllTimes(BufferedReader.class, "close",
                exception);

        // �e�X�g���{
        try {
            fileLineIterator.closeFile();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("Processing of reader was failed.", e.getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testGetHeader01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.header:�ȉ��̏�������ArrayList<String>�C���X�^���X<br>
     * �P�D"�w�b�_�s1"<br>
     * �Q�D"�w�b�_�s2"<br>
     * �R�D"�w�b�_�s3"<br>
     * <br>
     * ���Ғl�F(�߂�l) List<String>:�ȉ��̏�������ArrayList<String>�C���X�^���X<br>
     * �P�D"�w�b�_�s1"<br>
     * �Q�D"�w�b�_�s2"<br>
     * �R�D"�w�b�_�s3"<br>
     * <br>
     * ����p�^�[���B<br>
     * �w�b�_���̏�񂪐������擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetHeader01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub07>(
                fileName, AbstractFileLineIterator_Stub07.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        List<String> header = new ArrayList<String>();
        header.add("�w�b�_�s1");
        header.add("�w�b�_�s2");
        header.add("�w�b�_�s3");
        UTUtil.setPrivateField(fileLineIterator, "header", header);

        // �e�X�g���{
        List result = fileLineIterator.getHeader();

        // �ԋp�l�̊m�F
        assertEquals(ArrayList.class, result.getClass());
        assertEquals("�w�b�_�s1", result.get(0));
        assertEquals("�w�b�_�s2", result.get(1));
        assertEquals("�w�b�_�s3", result.get(2));

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetTrailer01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer01.txt"<br>
     * (���) this.readTrailer:false<br>
     * (���) this.trailerLineCount:0<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:�v�f�������Ȃ�Queue<String>�C���X�^���X<br>
     * (���) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) List<String>:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * <br>
     * ��this.trailer�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (��ԕω�) this.readTrailer:true<br>
     * (��ԕω�) LineReader.readLine():3��Ă΂��<br>
     * <br>
     * ����p�^�[���B�i�g���C����0�s�j<br>
     * �g���C�������Ȃ��ꍇ�͌��ʂ���̂��Ƃ��m�F����B<br>
     * �܂��A�f�[�^�s�̏�񂪑S����΂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetTrailer01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        List<String> result = fileLineIterator.getTrailer();

        // �ԋp�l�̊m�F
        assertSame(ArrayList.class, result.getClass());
        assertEquals(0, result.size());

        // ��ԕω��̊m�F
        Object trailer_object = UTUtil.getPrivateField(fileLineIterator,
                "trailer");
        assertSame(ArrayList.class, trailer_object.getClass());
        assertEquals(0, ((List) trailer_object).size());
        assertTrue((Boolean) UTUtil.getPrivateField(fileLineIterator,
                "readTrailer"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(3, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testGetTrailer02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer02.txt"<br>
     * (���) this.readTrailer:false<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * (���) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) List<String>:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * <br>
     * ��this.trailer�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.trailer:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (��ԕω�) this.readTrailer:true<br>
     * (��ԕω�) LineReader.readLine():3��Ă΂��<br>
     * <br>
     * ����p�^�[���B�i�g���C����1�s�j<br>
     * �f�[�^�s��S���ǂ�łȂ��ꍇ(�����s)�Ƀg���C�������擾����ꍇ�A������this.trailer��񂪕Ԃ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetTrailer02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81>(
                fileName, AbstractFileLineIterator_Stub81.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        List<String> result = fileLineIterator.getTrailer();

        // �ԋp�l�̊m�F
        assertSame(ArrayList.class, result.getClass());
        assertEquals(1, result.size());
        assertEquals("5�s�ڃf�[�^", result.get(0));

        // ��ԕω��̊m�F
        Object trailer_object = UTUtil.getPrivateField(fileLineIterator,
                "trailer");
        assertSame(ArrayList.class, trailer_object.getClass());
        List<String> trailer = (List<String>) trailer_object;
        assertEquals(1, trailer.size());
        assertEquals("5�s�ڃf�[�^", trailer.get(0));

        assertTrue((Boolean) UTUtil.getPrivateField(fileLineIterator,
                "readTrailer"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(3, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testGetTrailer03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer02.txt"<br>
     * (���) this.readTrailer:false<br>
     * (���) this.trailerLineCount:3<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * (���) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) List<String>:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * <br>
     * ��this.trailer�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.trailer:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (��ԕω�) this.readTrailer:true<br>
     * (��ԕω�) LineReader.readLine():1��Ă΂��<br>
     * <br>
     * ����p�^�[���B�i�g���C����3�s�j<br>
     * �f�[�^�s��S���ǂ�łȂ��ꍇ(�����s)�Ƀg���C�������擾����ꍇ�A������this.trailer��񂪕Ԃ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetTrailer03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub82> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub82>(
                fileName, AbstractFileLineIterator_Stub82.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        List<String> result = fileLineIterator.getTrailer();

        // �ԋp�l�̊m�F
        assertSame(ArrayList.class, result.getClass());
        assertEquals(3, result.size());
        assertEquals("3�s�ڃf�[�^", result.get(0));
        assertEquals("4�s�ڃf�[�^", result.get(1));
        assertEquals("5�s�ڃf�[�^", result.get(2));

        // ��ԕω��̊m�F
        Object trailer_object = UTUtil.getPrivateField(fileLineIterator,
                "trailer");
        assertSame(ArrayList.class, trailer_object.getClass());
        List<String> trailer = (List<String>) trailer_object;
        assertEquals(3, trailer.size());
        assertEquals("3�s�ڃf�[�^", trailer.get(0));
        assertEquals("4�s�ڃf�[�^", trailer.get(1));
        assertEquals("5�s�ڃf�[�^", trailer.get(2));

        assertTrue((Boolean) UTUtil.getPrivateField(fileLineIterator,
                "readTrailer"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(1, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testGetTrailer04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer02.txt"<br>
     * (���) this.readTrailer:false<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * (���) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext():FileException��O����������B<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (��ԕω�) this.readTrailer:false<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * (��ԕω�) �Ȃ�:this.hasNext(�j�Ŕ�������FileException�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�B�i�g���C����1�s�j<br>
     * ���̏����Ώۍs�ɑ΂��鑶�݃`�F�b�N�����ŃG���[�����������ꍇ�A���̗�O�����̂܂ܕԂ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetTrailer04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81>(
                fileName, AbstractFileLineIterator_Stub81.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        FileException exception = new FileException("hasNext�ł̃G���[�ł�");
        VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineIterator.class,
                "hasNext", exception);
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            fileLineIterator.getTrailer();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            Object trailer_object = UTUtil.getPrivateField(fileLineIterator,
                    "trailer");
            assertSame(ArrayList.class, trailer_object.getClass());
            List<String> trailer = (List<String>) trailer_object;
            assertEquals(0, trailer.size());

            assertFalse((Boolean) UTUtil.getPrivateField(fileLineIterator,
                    "readTrailer"));

            Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                    "lineReader");
            assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                    "readLine"));

            assertSame(exception, e);
        }
    }

    /**
     * testGetTrailer05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer02.txt"<br>
     * (���) this.readTrailer:true<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:���������Ȃ�LineReader�C���X�^���X<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailer:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) List<String>:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * <br>
     * ��this.trailer�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.trailer:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (��ԕω�) this.readTrailer:true<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * <br>
     * ����p�^�[���B�i�g���C����1�s�j<br>
     * �g���C���������ɐ�������Ă���ꍇ�A�L���b�V������Ă���g���C���������̂܂ܕԂ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetTrailer05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81>(
                fileName, AbstractFileLineIterator_Stub81.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        fileLineIterator.getTrailer();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        List<String> result = fileLineIterator.getTrailer();

        // �ԋp�l�̊m�F
        assertSame(ArrayList.class, result.getClass());
        assertEquals(1, result.size());
        assertEquals("5�s�ڃf�[�^", result.get(0));

        // ��ԕω��̊m�F
        Object trailer_object = UTUtil.getPrivateField(fileLineIterator,
                "trailer");
        assertSame(ArrayList.class, trailer_object.getClass());
        List<String> trailer = (List<String>) trailer_object;
        assertEquals(1, trailer.size());
        assertEquals("5�s�ڃf�[�^", trailer.get(0));

        assertTrue((Boolean) UTUtil.getPrivateField(fileLineIterator,
                "readTrailer"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testGetTrailer06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer03.txt"<br>
     * (���) this.readTrailer:false<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:���������Ȃ�LineReader�C���X�^���X<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) List<String>:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * <br>
     * ��this.trailer�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.trailer:�ȉ��̗v�f������ArrayList<String>�C���X�^���X<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (��ԕω�) this.readTrailer:true<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * <br>
     * ����p�^�[���B�i�g���C����1�s�j<br>
     * �f�[�^�s��S���ǂ񂾌�Ƀg���C�������擾����ꍇ�A������this.trailer��񂪕Ԃ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetTrailer06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81>(
                fileName, AbstractFileLineIterator_Stub81.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        List<String> result = fileLineIterator.getTrailer();

        // �ԋp�l�̊m�F
        assertSame(ArrayList.class, result.getClass());
        assertEquals(1, result.size());
        assertEquals("5�s�ڃf�[�^", result.get(0));

        // ��ԕω��̊m�F
        Object trailer_object = UTUtil.getPrivateField(fileLineIterator,
                "trailer");
        assertSame(ArrayList.class, trailer_object.getClass());
        List<String> trailer = (List<String>) trailer_object;
        assertEquals(1, trailer.size());
        assertEquals("5�s�ڃf�[�^", trailer.get(0));

        assertTrue((Boolean) UTUtil.getPrivateField(fileLineIterator,
                "readTrailer"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testGetTrailer07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_getTrailer02.txt"<br>
     * (���) this.readTrailer:false<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * (���) this.trailer:�v�f�������Ȃ�ArrayList<String>�C���X�^���X<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():FileException��O����������B<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Processing of lineReader was failed."<br>
     * �E������O�FLineReader.readLine()�ŃX���[���ꂽ��O<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B�i�g���C����1�s�j<br>
     * ���̏����Ώۍs�ɑ΂��鑶�݃`�F�b�N�����ŃG���[�����������ꍇ�A���̗�O�����̂܂ܕԂ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTrailer07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_getTrailer02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81>(
                fileName, AbstractFileLineIterator_Stub81.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        FileException exception = new FileException("readLine�ł̃G���[�ł�");
        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        VMOUTUtil.setReturnValueAtAllTimes(lineReader.getClass(), "readLine",
                exception);
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            fileLineIterator.getTrailer();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("Processing of lineReader was failed.", e.getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testReadLine01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_skip01.txt"<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * �E"�U�s�ڃf�[�^"<br>
     * �E"�V�s�ڃf�[�^"<br>
     * (���) this.trailerLineCount:0<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"�P�s�ڃf�[�^"<br>
     * (��ԕω�) this.trailerQueue:null<br>
     * (��ԕω�) LineReader.readLine():1��Ă΂��<br>
     * <br>
     * ����p�^�[��<br>
     * (�g���C�����Ȃ�)�B<br>
     * �g���C�������Ȃ��ꍇ�A�f�[�^���̃f�[�^1�s�����Ԃ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        String result = fileLineIterator.readLine();

        // �ԋp�l�̊m�F
        assertEquals("1�s�ڃf�[�^", result);

        // ��ԕω��̊m�F
        assertNull(UTUtil.getPrivateField(fileLineIterator, "trailerQueue"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(1, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testReadLine02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_readLine02.txt"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * �E"�U�s�ڃf�[�^"<br>
     * �E"�V�s�ڃf�[�^"<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"�R�s�ڃf�[�^"<br>
     * (��ԕω�) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�S�s�ڃf�[�^"<br>
     * (��ԕω�) Queue#add():1��Ă΂��<br>
     * (��ԕω�) LineReader.readLine():1��Ă΂��<br>
     * <br>
     * ����p�^�[��<br>
     * (�g���C����1�s�A�Q��ڈȌ�̎��s)�B<br>
     * �g���C�������L��f�[�^�ɑ΂���readLine()��1��ȏ���s�������readLine()�����s�����ꍇ�A�g���C���L���[�̓��e���X�V����L���[�̍ŏ��̃f�[�^���f�[�^���̃f�[�^�Ƃ��ĕԂ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReadLine02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_readLine02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81>(
                fileName, AbstractFileLineIterator_Stub81.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        String result = fileLineIterator.readLine();

        // �ԋp�l�̊m�F
        assertEquals("3�s�ڃf�[�^", result);

        // ��ԕω��̊m�F
        Object trailerQueue_object = UTUtil.getPrivateField(fileLineIterator,
                "trailerQueue");
        assertEquals(ArrayBlockingQueue.class, trailerQueue_object.getClass());
        ArrayBlockingQueue trailerQueue = (ArrayBlockingQueue) trailerQueue_object;
        assertEquals(1, trailerQueue.size());
        assertEquals("4�s�ڃf�[�^", trailerQueue.poll());

        assertEquals(1, VMOUTUtil.getCallCount(Queue.class, "add"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(1, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testReadLine03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_readLine02.txt"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�U�s�ڃf�[�^"<br>
     * �E"�V�s�ڃf�[�^"<br>
     * (���) this.trailerLineCount:3<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"�R�s�ڃf�[�^"<br>
     * (��ԕω�) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * �E"�U�s�ڃf�[�^"<br>
     * (��ԕω�) Queue#add():1��Ă΂��<br>
     * (��ԕω�) LineReader.readLine():1��Ă΂��<br>
     * <br>
     * ����p�^�[��<br>
     * (�g���C���������s�A�Q��ڈȌ�̎��s)�B<br>
     * �g���C�������L��f�[�^�ɑ΂���readLine()��1��ȏ���s�������readLine()�����s�����ꍇ�A�g���C���L���[�̓��e���X�V����L���[�̍ŏ��̃f�[�^���f�[�^���̃f�[�^�Ƃ��ĕԂ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReadLine03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_readLine02.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub82> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub82>(
                fileName, AbstractFileLineIterator_Stub82.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        String result = fileLineIterator.readLine();

        // �ԋp�l�̊m�F
        assertEquals("3�s�ڃf�[�^", result);

        // ��ԕω��̊m�F
        Object trailerQueue_object = UTUtil.getPrivateField(fileLineIterator,
                "trailerQueue");
        assertEquals(ArrayBlockingQueue.class, trailerQueue_object.getClass());
        ArrayBlockingQueue trailerQueue = (ArrayBlockingQueue) trailerQueue_object;
        assertEquals(3, trailerQueue.size());
        assertEquals("4�s�ڃf�[�^", trailerQueue.poll());
        assertEquals("5�s�ڃf�[�^", trailerQueue.poll());
        assertEquals("6�s�ڃf�[�^", trailerQueue.poll());

        assertEquals(1, VMOUTUtil.getCallCount(Queue.class, "add"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(1, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testReadLine04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_readLine03.txt"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�V�s�ڃf�[�^"<br>
     * (���) this.lineReader:���������Ȃ�LineReader�C���X�^���X<br>
     * (���) this.trailerLineCount:1<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�` �� false<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * (��ԕω�) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"�V�s�ڃf�[�^"<br>
     * (��ԕω�) Queue#add():�Ă΂�Ȃ�<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * <br>
     * �f�[�^����S���ǂ񂾌��readLine()�����s�����ꍇ�Anull��Ԃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReadLine04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_readLine03.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub81>(
                fileName, AbstractFileLineIterator_Stub81.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        String result = fileLineIterator.readLine();

        // �ԋp�l�̊m�F
        assertNull(result);

        // ��ԕω��̊m�F
        Object trailerQueue_object = UTUtil.getPrivateField(fileLineIterator,
                "trailerQueue");
        assertEquals(ArrayBlockingQueue.class, trailerQueue_object.getClass());
        ArrayBlockingQueue trailerQueue = (ArrayBlockingQueue) trailerQueue_object;
        assertEquals(1, trailerQueue.size());
        assertEquals("7�s�ڃf�[�^", trailerQueue.poll());

        assertEquals(0, VMOUTUtil.getCallCount(Queue.class, "add"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testReadLine05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_skip01.txt"<br>
     * (���) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"4�s�ڃf�[�^"<br>
     * �E"5�s�ڃf�[�^"<br>
     * �E"6�s�ڃf�[�^"<br>
     * �E"7�s�ڃf�[�^"<br>
     * (���) this.lineReader:���������Ȃ�LineReader�C���X�^���X<br>
     * (���) this.trailerLineCount:3<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * (��ԕω�) this.trailerQueue:�ȉ��̗v�f������Queue<String>�C���X�^���X<br>
     * �E"4�s�ڃf�[�^"<br>
     * �E"5�s�ڃf�[�^"<br>
     * �E"6�s�ڃf�[�^"<br>
     * �E"7�s�ڃf�[�^"<br>
     * (��ԕω�) Queue#add():�Ă΂�Ȃ�<br>
     * (��ԕω�) LineReader.readLine():�Ă΂�Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * �w�b�_���A�g���C�����͗L�邪�f�[�^�����Ȃ��f�[�^�ɑ΂���readLine()�����s�����ꍇ�Anull���Ԃ���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReadLine05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub83> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub83>(
                fileName, AbstractFileLineIterator_Stub83.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        VMOUTUtil.initialize();
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        String result = fileLineIterator.readLine();

        // �ԋp�l�̊m�F
        assertNull(result);

        // ��ԕω��̊m�F
        Object trailerQueue_object = UTUtil.getPrivateField(fileLineIterator,
                "trailerQueue");
        assertEquals(ArrayBlockingQueue.class, trailerQueue_object.getClass());
        ArrayBlockingQueue trailerQueue = (ArrayBlockingQueue) trailerQueue_object;
        assertEquals(4, trailerQueue.size());
        assertEquals("4�s�ڃf�[�^", trailerQueue.poll());
        assertEquals("5�s�ڃf�[�^", trailerQueue.poll());
        assertEquals("6�s�ڃf�[�^", trailerQueue.poll());
        assertEquals("7�s�ڃf�[�^", trailerQueue.poll());

        assertEquals(0, VMOUTUtil.getCallCount(Queue.class, "add"));

        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        assertEquals(0, VMOUTUtil.getCallCount(lineReader.getClass(),
                "readLine"));
    }

    /**
     * testReadLine06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineIterator_skip01.txt"<br>
     * (���) this.trailerQueue:null<br>
     * (���) this.lineReader:�ȉ��̏�������LineReader�C���X�^���X<br>
     * �E"�P�s�ڃf�[�^"<br>
     * �E"�Q�s�ڃf�[�^"<br>
     * �E"�R�s�ڃf�[�^"<br>
     * �E"�S�s�ڃf�[�^"<br>
     * �E"�T�s�ڃf�[�^"<br>
     * �E"�U�s�ڃf�[�^"<br>
     * �E"�V�s�ڃf�[�^"<br>
     * (���) this.trailerLineCount:0<br>
     * (���) this.hasNext():this.lineReader�̒�`�ɏ]��<br>
     * <br>
     * ���Ă΂��^�C�~���O�ɂ��킹�Ē�`<br>
     * (���) LineReader.readLine():FileException��O����������B<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Processing of lineReader was failed."<br>
     * �E������O�FLineReader.readLine()�ŃX���[���ꂽ��O<br>
     * �E�t�@�C�����F�t�B�[���hfileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * LineReader�����O�����������ꍇ�A���̗�O��FileException�Ƀ��b�v����ăX���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        fileLineIterator.init();
        Object lineReader = UTUtil.getPrivateField(fileLineIterator,
                "lineReader");
        FileException exception = new FileException("readLine����̗�O�ł�");
        VMOUTUtil.setExceptionAtAllTimes(lineReader.getClass(), "readLine",
                exception);
        // �e�X�g�Ώۂ̃C���X�^���X���Őݒ�ς�

        // �e�X�g���{
        try {
            fileLineIterator.readLine();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals("Processing of lineReader was failed.", e.getMessage());
            assertSame(exception, e.getCause());
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testSkipint01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(����) skipLines:0<br>
     * (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
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
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);
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
     * (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
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
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);
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
     * (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.readLine():������s<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.readLine:3��Ă΂��<br>
     * (���) this.currentLineCount:3<br>
     * <br>
     * ����p�^�[���B<br>
     * Skip�Ώۍs���R�s�̏ꍇ�A�Ώۃf�[�^���R�s�ǂނ��Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSkipint03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);
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
     * (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
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
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);
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
     * <br>
     * �������Ώۃt�@�C���̍s���𒴂���ݒ�<br>
     * (���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.readLine():������s<br>
     * (���) this.currentLineCount:0<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.readLine:7��Ă΂��<br>
     * (��ԕω�) this.currentLineCount:7<br>
     * ���t�@�C���̃f�[�^�s��<br>
     * (��ԕω�) �Ȃ�:�ȉ��̐ݒ������FileLineException����������B<br>
     * �E���b�Z�[�W�F"The data which can be acquired doesn't exist."<br>
     * �E������O�FNoSuchElementException<br>
     * �E�t�@�C�����F�����Ώۃt�@�C����<br>
     * �E�s�ԍ��F8<br>
     * <br>
     * ��O�B<br>
     * Skip�Ώۍs�̐����Ώۃf�[�^�̐����z����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSkipint05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);
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
            assertEquals(8, e.getLineNo());

            // ��ԕω��̊m�F
            assertEquals(7, VMOUTUtil.getCallCount(
                    AbstractFileLineIterator.class, "readLine"));

            assertEquals(7, UTUtil.getPrivateField(fileLineIterator,
                    "currentLineCount"));
        }
    }

    /**
     * testGetLineFeedChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) this.lineFeedChar:"\r"<br>
     * <br>
     * lineFeedChar��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetLineFeedChar01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, AbstractFileLineIterator_Stub01.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �O�������lineFeedChar��"\r"��ݒ�ς�

        // �e�X�g���{
        String result = fileLineIterator.getLineFeedChar();

        // �ԋp�l�̊m�F
        assertEquals("\r", result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetFileEncoding01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileEncoding:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) this.fileEncoding:not null<br>
     * <br>
     * fileEncoding��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetFileEncoding01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, AbstractFileLineIterator_Stub01.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �O�������fileEncoding��"MS932"��ݒ�ς�

        // �e�X�g���{
        String result = fileLineIterator.getFileEncoding();

        // �ԋp�l�̊m�F
        assertEquals("MS932", result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetHeaderLineCount01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.headerLineCount:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) this.headerLineCount:not null<br>
     * <br>
     * headerLineCount��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetHeaderLineCount01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, AbstractFileLineIterator_Stub01.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �O�������headerLineCount��1��ݒ�ς�

        // �e�X�g���{
        int result = fileLineIterator.getHeaderLineCount();

        // �ԋp�l�̊m�F
        assertEquals(1, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetTrailerLineCount01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.trailerLineCount:100<br>
     * <br>
     * ���Ғl�F(�߂�l) this.trailerLineCount:100<br>
     * <br>
     * trailerLineCount��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetTrailerLineCount01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, AbstractFileLineIterator_Stub01.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �O�������trailerLineCount��1��ݒ�ς�

        // �e�X�g���{
        int result = fileLineIterator.getTrailerLineCount();

        // �ԋp�l�̊m�F
        assertEquals(1, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetCurrentLineCount01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.currentLineCount:100<br>
     * <br>
     * ���Ғl�F(�߂�l) this.currentLineCount:100<br>
     * <br>
     * currentLineCount��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetCurrentLineCount01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, AbstractFileLineIterator_Stub01.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        UTUtil.setPrivateField(fileLineIterator, "currentLineCount", 1);

        // �e�X�g���{
        int result = fileLineIterator.getCurrentLineCount();

        // �ԋp�l�̊m�F
        assertEquals(1, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetFields01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fields:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) this.fields:not null<br>
     * <br>
     * fields��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetFields01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, AbstractFileLineIterator_Stub01.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Field[] fields = new Field[] {};
        UTUtil.setPrivateField(fileLineIterator, "fields", fields);

        // �e�X�g���{
        Field[] result = fileLineIterator.getFields();

        // �ԋp�l�̊m�F
        assertSame(fields, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetFileName01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) AbstractFileLineIterator�����N���X:AbstractFileLineIteratorImpl02<br>
     * �@�����<br>
     * (���) this.fileName:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) this.fileName:not null<br>
     * <br>
     * fileName��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetFileName01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        URL url = this.getClass().getResource(
                "AbstractFileLineIterator_skip01.txt");
        String fileName = url.getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01> fileLineIterator = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub01>(
                fileName, AbstractFileLineIterator_Stub01.class,
                columnParserMap);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        String fileName_dummy = "�t�@�C����";
        UTUtil.setPrivateField(fileLineIterator, "fileName", fileName_dummy);

        // �e�X�g���{
        String result = fileLineIterator.getFileName();

        // �ԋp�l�̊m�F
        assertEquals(fileName_dummy, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testIsCheckByte01() <br>
     * <br>
     * (����n) <br>
     * <br>
     * ���͒l�F(����) inputFileColumn:not null<br>
     * (���) inputFileColumn#bytes():1<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsCheckByte01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = this.getClass().getResource("File_Empty.txt")
                .getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIterator<FixedFileLine_Stub01> fileLineWriter = new AbstractFileLineIteratorImpl02<FixedFileLine_Stub01>(
                fileName, FixedFileLine_Stub01.class, columnParserMap);

        // �O����(����)
        Field column = FixedFileLine_Stub01.class.getDeclaredFields()[0];
        InputFileColumn inputFileColumn = column
                .getAnnotation(InputFileColumn.class);

        // �e�X�g���{
        boolean result = fileLineWriter.isCheckByte(inputFileColumn);

        // ����(�߂�l)
        assertTrue(result);
    }

    /**
     * testIsCheckByte02() <br>
     * <br>
     * (�ُ�n) <br>
     * <br>
     * ���͒l�F(����) inputFileColumn:not null<br>
     * (���) inputFileColumn#bytes():-1<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     * <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsCheckByte02() throws Exception {
        // �O����(�����Ώ�)
        String fileName = this.getClass().getResource("File_Empty.txt")
                .getPath();
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());

        AbstractFileLineIterator<AbstractFileLineIterator_Stub80> fileLineWriter = new AbstractFileLineIteratorImpl02<AbstractFileLineIterator_Stub80>(
                fileName, AbstractFileLineIterator_Stub80.class,
                columnParserMap);

        // �O����(����)
        Field column = AbstractFileLineIterator_Stub80.class
                .getDeclaredFields()[0];
        InputFileColumn inputFileColumn = column
                .getAnnotation(InputFileColumn.class);

        // �e�X�g���{
        boolean result = fileLineWriter.isCheckByte(inputFileColumn);

        // ����(�߂�l)
        assertFalse(result);
    }

}

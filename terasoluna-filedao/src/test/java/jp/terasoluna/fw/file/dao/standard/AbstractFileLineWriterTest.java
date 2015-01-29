/*
 * $Id: AbstractFileLineWriterTest.java 5819 2007-12-20 05:55:47Z fukuyot $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.NullStringConverter;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;
import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.annotation.StringConverterToLowerCase;
import jp.terasoluna.fw.file.annotation.StringConverterToUpperCase;
import jp.terasoluna.fw.file.annotation.TrimType;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.AbstractFileLineWriter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���A�N�Z�X(�o��)�̃X�[�p�[�N���X�B���ۃN���X�̂��߁AAbstractFileLineWriterImpl�N���X���쐬���Ď��������{����B
 * <p>
 * @author ���c �N�i
 * @author �� ��O
 * @see jp.terasoluna.fw.file.dao.standard.AbstractFileLineWriter
 */
public class AbstractFileLineWriterTest<T> {

    private static final String TEMP_FILE_NAME = AbstractFileLineWriterTest.class
            .getResource("AbstractFileLineWriterTest_tmp.txt").getPath();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(AbstractFileLineWriterTest.class);
    }

    @Before
    public void setUp() throws Exception {
        VMOUTUtil.initialize();
        // �t�@�C���̏�����
        File file = new File(TEMP_FILE_NAME);
//        file.delete();
        while (!file.delete()) {
            System.gc();
        }
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
     * testAbstractFileLineWriter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>01_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.fileName:����fileName�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.clazz:����clazz�̂Ɠ����C���X�^���X<br>
     * (��ԕω�) lineFeedChar:"\r"<br>
     * (��ԕω�) fileEncoding:"UTF-8"<br>
     * (��ԕω�) columnFormatterMap:����columnFormatterMap�����C���X�^���X<br>
     * (��ԕω�) ��O:�Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * �����ɐݒ肳�ꂽ@FileFormat�̏��ɏ]���Đ�����AbstractFileLineWriter�C���X�^���X����������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter01() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>01_fileName";

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> result = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // ����(�߂�l)
        assertNotNull(result);

        assertSame(fileName, UTUtil.getPrivateField(result, "fileName"));

        assertSame(clazz, UTUtil.getPrivateField(result, "clazz"));

        assertEquals("\r", UTUtil.getPrivateField(result, "lineFeedChar"));

        assertEquals("UTF-8", UTUtil.getPrivateField(result, "fileEncoding"));

        assertSame(columnFormatterMap, UTUtil.getPrivateField(result,
                "columnFormatterMap"));
    }

    /**
     * testAbstractFileLineWriter02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:null<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"fileName is required."<br>
     * �E������O�FIllegalArgumentException<br>
     * �E�t�@�C�����Fnull<br>
     * <br>
     * ��O�B<br>
     * �t�@�C�������ݒ肳��Ă��Ȃ�(null)�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter02() throws Exception {
        // �O����(����)
        String fileName = null;

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)

            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("fileName is required.", e.getMessage());

            assertTrue(IllegalArgumentException.class.isAssignableFrom(e
                    .getCause().getClass()));
        }
    }

    /**
     * testAbstractFileLineWriter03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>03_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ�����ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"FileFormat annotation is not found."<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ����clazz�ɓn���ꂽ�N���X�C���X�^���X�ɁA@FileFormat�̐ݒ肪���݂��Ȃ��ꍇ�́A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter03() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>03_fileName";

        Class<AbstractFileLineWriter_Stub02> clazz = AbstractFileLineWriter_Stub02.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub02>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("FileFormat annotation is not found.", e.getMessage());

            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));

            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineWriter04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,E <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>04_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F""(�󕶎��A�f�t�H���g�l)<br>
     * - fileEncoding�F""(�󕶎��A�f�t�H���g�l)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.fileName:����fileName�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.clazz:����clazz�̂Ɠ����C���X�^���X<br>
     * (��ԕω�) lineFeedChar:�V�X�e���̍s��؂蕶��<br>
     * System.getProperty("line.separator")<br>
     * (��ԕω�) fileEncoding:�V�X�e���̃t�@�C���G���R�[�f�B���O<br>
     * System.getProperty("file.encoding")<br>
     * (��ԕω�) columnFormatterMap:����columnFormatterMap�����C���X�^���X<br>
     * <br>
     * ����p�^�[���B<br>
     * ����clazz�ɓn���ꂽ�N���X�C���X�^���X�́�FileFormat�ɁulineFeedChar�v�ƁufileEncoding�v���󕶎��Őݒ肳��Ă���ꍇ�AAbstractFileLineWriter�N���X��this.
     * lineFeddChar��this.fileEncoding���V�X�e���f�t�H���g�l�ŏ���������Đ�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter04() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>04_fileName";

        Class<AbstractFileLineWriter_Stub03> clazz = AbstractFileLineWriter_Stub03.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        AbstractFileLineWriter<AbstractFileLineWriter_Stub03> result = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub03>(
                fileName, clazz, columnFormatterMap);
        // ����(�߂�l)
        assertNotNull(result);

        assertSame(fileName, UTUtil.getPrivateField(result, "fileName"));

        assertSame(clazz, UTUtil.getPrivateField(result, "clazz"));

        assertEquals(System.getProperty("line.separator"), UTUtil
                .getPrivateField(result, "lineFeedChar"));

        assertEquals(System.getProperty("file.encoding"), UTUtil
                .getPrivateField(result, "fileEncoding"));

        assertSame(columnFormatterMap, UTUtil.getPrivateField(result,
                "columnFormatterMap"));
    }

    /**
     * testAbstractFileLineWriter05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>05_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F";"(encloseChar�Ɠ����l)<br>
     * - encloseChar�F";"(delimiter�Ɠ����l)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"Delimiter is the same as EncloseChar and is no use."<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ����clazz�ɓn���ꂽ�N���X�C���X�^���X��@FileForma�Ɂudelimiter�v�ƁuencloseChar�v�������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter05() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>05_fileName";

        Class<AbstractFileLineWriter_Stub04> clazz = AbstractFileLineWriter_Stub04.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub04>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("Delimiter is the same as EncloseChar and is no use.",
                    e.getMessage());

            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));

            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testAbstractFileLineWriter06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * ""<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"fileName is required."<br>
     * �E������O�FIllegalArgumentException<br>
     * �E�t�@�C�����F""(�󕶎�)<br>
     * <br>
     * ��O�B<br>
     * �t�@�C�������󕶎��̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter06() throws Exception {
        // �O����(����)
        String fileName = "";

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("fileName is required.", e.getMessage());

            assertTrue(IllegalArgumentException.class.isAssignableFrom(e
                    .getCause().getClass()));
        }
    }

    /**
     * testAbstractFileLineWriter07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>07_fileName"<br>
     * (����) clazz:null<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
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
    @Test
    public void testAbstractFileLineWriter07() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>07_fileName";

        Class<AbstractFileLineWriter_Stub01> clazz = null;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("clazz is required.", e.getMessage());

            assertTrue(IllegalArgumentException.class.isAssignableFrom(e
                    .getCause().getClass()));
        }
    }

    /**
     * testAbstractFileLineWriter08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>08_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"columnFormaterMap is required."<br>
     * �E������O�FIllegalArgumentException<br>
     * �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ����columnFormatterMap���unull�v�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter08() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>08_fileName";

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = null;

        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("columnFormatterMap is required.", e.getMessage());

            assertTrue(IllegalArgumentException.class.isAssignableFrom(e
                    .getCause().getClass()));
        }
    }

    /**
     * testAbstractFileLineWriter09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,G <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>09_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�v�f�������Ȃ�Map<String, ColumnFormatter>�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"columnFormaterMap is required."<br>
     * �E������O�FIllegalArgumentException<br>
     * �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ����columnFormatterMap�͂��邪�A����Map�ɗv�f�������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter09() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>09_fileName";

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();

        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("columnFormatterMap is required.", e.getMessage());

            assertTrue(IllegalArgumentException.class.isAssignableFrom(e
                    .getCause().getClass()));
        }
    }

    /**
     * testAbstractFileLineWriter10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>10_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r\n\t"(�f�t�H���g�l�ȊO�A3���ȏ�)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�ȉ��̏�������FileException���������邱�Ƃ��m�F����B<br>
     * �E���b�Z�[�W�F"lineFeedChar length must be 1 or 2. but: 3"<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * ��O�B<br>
     * ��FileFormat��lineFeedChar��`��3�����ȏ�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter10() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>10_fileName";

        Class<AbstractFileLineWriter_Stub05> clazz = AbstractFileLineWriter_Stub05.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        try {
            new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub05>(
                    fileName, clazz, columnFormatterMap);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("lineFeedChar length must be 1 or 2. but: 3", e
                    .getMessage());

            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
        }
    }

    /**
     * testAbstractFileLineWriter11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>11_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.fileName:����fileName�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.clazz:����clazz�̂Ɠ����C���X�^���X<br>
     * (��ԕω�) lineFeedChar:�V�X�e���̍s��؂蕶��<br>
     * System.getProperty("line.separator")<br>
     * (��ԕω�) fileEncoding:�V�X�e���̃t�@�C���G���R�[�f�B���O<br>
     * System.getProperty("file.encoding")<br>
     * (��ԕω�) columnFormatterMap:����columnFormatterMap�����C���X�^���X<br>
     * (��ԕω�) ��O:�Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * �����ɐݒ肳�ꂽ@FileFormat�̏�񂪃f�t�H���g�܂܂̏ꍇ�AAbstractFileLineWriter�C���X�^���X�����Ȃ���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter11() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>11_fileName";

        Class<AbstractFileLineWriter_Stub08> clazz = AbstractFileLineWriter_Stub08.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        AbstractFileLineWriter<AbstractFileLineWriter_Stub08> result = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub08>(
                fileName, clazz, columnFormatterMap);

        // ����(�߂�l)
        assertNotNull(result);

        assertSame(fileName, UTUtil.getPrivateField(result, "fileName"));

        assertSame(clazz, UTUtil.getPrivateField(result, "clazz"));

        assertEquals(System.getProperty("line.separator"), UTUtil
                .getPrivateField(result, "lineFeedChar"));

        assertEquals(System.getProperty("file.encoding"), UTUtil
                .getPrivateField(result, "fileEncoding"));

        assertSame(columnFormatterMap, UTUtil.getPrivateField(result,
                "columnFormatterMap"));
    }

    /**
     * testAbstractFileLineWriter12() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>13_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * <br>
     * �ȉ��e�N���X�̒�`<br>
     * �E@FileFormat�̐ݒ�������Ȃ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.fileName:����fileName�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.clazz:����clazz�̂Ɠ����C���X�^���X<br>
     * (��ԕω�) lineFeedChar:�V�X�e���̍s��؂蕶��<br>
     * System.getProperty("line.separator")<br>
     * (��ԕω�) fileEncoding:�V�X�e���̃t�@�C���G���R�[�f�B���O<br>
     * System.getProperty("file.encoding")<br>
     * (��ԕω�) columnFormatterMap:����columnFormatterMap�����C���X�^���X<br>
     * (��ԕω�) ��O:�Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * ����clazz����FileFormat�������Ȃ��N���X���p�����Ă���ꍇ�A�ݒ肳�ꂽ@FileFormat�̏��ɏ]����AbstractFileLineWriter�C���X�^���X����������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter12() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>12_fileName";

        Class<AbstractFileLineWriter_Stub38> clazz = AbstractFileLineWriter_Stub38.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        AbstractFileLineWriter<AbstractFileLineWriter_Stub38> result = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub38>(
                fileName, clazz, columnFormatterMap);

        // ����(�߂�l)
        assertNotNull(result);

        assertSame(fileName, UTUtil.getPrivateField(result, "fileName"));

        assertSame(clazz, UTUtil.getPrivateField(result, "clazz"));

        assertEquals(System.getProperty("line.separator"), UTUtil
                .getPrivateField(result, "lineFeedChar"));

        assertEquals(System.getProperty("file.encoding"), UTUtil
                .getPrivateField(result, "fileEncoding"));

        assertSame(columnFormatterMap, UTUtil.getPrivateField(result,
                "columnFormatterMap"));
    }

    /**
     * testAbstractFileLineWriter13() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_<init>12_fileName"<br>
     * (����) clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * <br>
     * �ȉ��e�N���X�̒�`<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l�ȊO<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (����) columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.fileName:����fileName�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.clazz:����clazz�̂Ɠ����C���X�^���X<br>
     * (��ԕω�) lineFeedChar:�V�X�e���̍s��؂蕶��<br>
     * System.getProperty("line.separator")<br>
     * (��ԕω�) fileEncoding:�V�X�e���̃t�@�C���G���R�[�f�B���O<br>
     * System.getProperty("file.encoding")<br>
     * (��ԕω�) columnFormatterMap:����columnFormatterMap�����C���X�^���X<br>
     * (��ԕω�) ��O:�Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * ����clazz����FileFormat�����N���X���p�����Ă���ꍇ�A�e�N���X�ł͂Ȃ�clazz�ɐݒ肳�ꂽ@FileFormat�̏��ɏ]����AbstractFileLineWriter�C���X�^���X����������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testAbstractFileLineWriter13() throws Exception {
        // �O����(����)
        String fileName = "AbstractFileLineWriter_<init>13_fileName";

        Class<AbstractFileLineWriter_Stub39> clazz = AbstractFileLineWriter_Stub39.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �e�X�g���{
        AbstractFileLineWriter<AbstractFileLineWriter_Stub39> result = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub39>(
                fileName, clazz, columnFormatterMap);

        // ����(�߂�l)
        assertNotNull(result);

        assertSame(fileName, UTUtil.getPrivateField(result, "fileName"));

        assertSame(clazz, UTUtil.getPrivateField(result, "clazz"));

        assertEquals(System.getProperty("line.separator"), UTUtil
                .getPrivateField(result, "lineFeedChar"));

        assertEquals(System.getProperty("file.encoding"), UTUtil
                .getPrivateField(result, "fileEncoding"));

        assertSame(columnFormatterMap, UTUtil.getPrivateField(result,
                "columnFormatterMap"));
    }

    /**
     * testInit01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "File_1Line.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.calledInit:true<br>
     * (���) this.fileEncoding:����clazz��@FileFormat�̐ݒ�ɏ]���B<br>
     * (���) this.writer:�ȉ��̐ݒ������BufferedWriter�C���X�^���X<br>
     * �Enew BufferedWriter(<br>
     * new OutputStreamWriter(<br>
     * (new FileOutputStream(fileName, true)),<br>
     * fileEncoding))<br>
     * <br>
     * ��Writer�̐����\���͕��G�Ȃ��ߊm�F����B<br>
     * �����DJUnit�Ōďo�����̈������m�F���邱�Ƃɂ���B<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "File_1Line.txt"<br>
     * �E1�s�f�[�^����<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.writer:�ȉ��̐ݒ������BufferedWriter�C���X�^���X<br>
     * �Enew BufferedWriter(<br>
     * new OutputStreamWriter(<br>
     * (new FileOutputStream(fileName, true)),<br>
     * fileEncoding))<br>
     * <br>
     * ���ω��Ȃ�<br>
     * (��ԕω�) #buildFields():�Ă΂�Ȃ�<br>
     * (��ԕω�) #buildStringConverters():�Ă΂�Ȃ�<br>
     * (��ԕω�) #buildMethods():�Ă΂�Ȃ�<br>
     * (��ԕω�) file#delete():�Ă΂�Ȃ�<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "File_1Line.txt"<br>
     * �E1�s�f�[�^����<br>
     * <br>
     * ���ω��Ȃ�<br>
     * <br>
     * ����p�^�[��<br>
     * ����init()�����s����Ă���ꍇ�́A�������s��Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testInit01() throws Exception {
        // �O����(����)
        String fileName = TEMP_FILE_NAME;

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O����(�����Ώ�)
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        BufferedWriter writer = null;
        BufferedReader postReader = null;
        try {
            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "calledInit", Boolean.TRUE);

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName, true), "UTF-8"));

            UTUtil.setPrivateField(fileLineWriter, "writer", writer);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter_Stub01.class,
                    "buildFields", 0, null);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter_Stub01.class,
                    "buildStringConverts", 0, null);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter_Stub01.class,
                    "buildMethods", 0, null);

            // �e�X�g���{
            fileLineWriter.init();

            // ����(�t�B�[���h)
            assertSame(writer, UTUtil.getPrivateField(fileLineWriter, "writer"));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "buildFields"));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "buildStringConverts"));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "buildMethods"));

            assertFalse(VMOUTUtil.isCalled(File.class, "delete"));

            // ����(�t�@�C��)
            assertTrue(new File(fileName).exists());

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "UTF-8"));
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (postReader != null) {
                postReader.close();
            }
        }
    }

    /**
     * testInit02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "File_1Line.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.calledInit:false<br>
     * (���) this.fileEncoding:����clazz��@FileFormat�̐ݒ�ɏ]���B<br>
     * (���) this.writer:null<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "File_1Line.txt"<br>
     * �E1�s�f�[�^����<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.writer:�ȉ��̐ݒ������BufferedWriter�C���X�^���X<br>
     * �Enew BufferedWriter(<br>
     * new OutputStreamWriter(<br>
     * (new FileOutputStream(fileName, true)),<br>
     * fileEncoding))<br>
     * <br>
     * ��Writer�̐����\���͕��G�Ȃ��ߊm�F����B<br>
     * �����DJUnit�Ōďo�����̈������m�F���邱�Ƃɂ���B<br>
     * (��ԕω�) #buildFields():1��Ă΂��<br>
     * (��ԕω�) #buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) #buildMethods():1��Ă΂��<br>
     * (��ԕω�) file#delete():1��Ă΂��<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "File_1Line.txt"<br>
     * �E�f�[�^�Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * (overWriteFlg�ݒ�FTrue)<br>
     * �������t�@�C���s�I�u�W�F�N�g�ɑ΂���init()�������s���邱�Ƃ��m�F����B<br>
     * �܂��A�������ݑΏۃt�@�C���̏�񂪍폜����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInit02() throws Exception {
        // �O����(����)
        String fileName = TEMP_FILE_NAME;

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O����(�����Ώ�)
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "calledInit", Boolean.FALSE);

        UTUtil.setPrivateField(fileLineWriter, "writer", null);

        BufferedReader postReader = null;
        try {
            // �e�X�g���{
            fileLineWriter.init();

            // ����(�t�B�[���h)
            assertNotNull(UTUtil.getPrivateField(fileLineWriter, "writer"));
            assertEquals(1, VMOUTUtil.getCallCount(BufferedWriter.class,
                    "<init>"));
            List bufferedWriterInitArguments = VMOUTUtil.getArguments(
                    BufferedWriter.class, "<init>", 0);
            assertEquals(1, bufferedWriterInitArguments.size());
            assertTrue(OutputStreamWriter.class
                    .isAssignableFrom(bufferedWriterInitArguments.get(0)
                            .getClass()));
            assertEquals(1, VMOUTUtil.getCallCount(OutputStreamWriter.class,
                    "<init>"));
            List outputStreamWriterInitArguments = VMOUTUtil.getArguments(
                    OutputStreamWriter.class, "<init>", 0);
            assertEquals(2, outputStreamWriterInitArguments.size());
            assertTrue(FileOutputStream.class
                    .isAssignableFrom(outputStreamWriterInitArguments.get(0)
                            .getClass()));
            assertEquals("UTF-8", outputStreamWriterInitArguments.get(1));
            assertEquals(1, VMOUTUtil.getCallCount(FileOutputStream.class,
                    "<init>"));
            List fileOutputStreamInitArguments = VMOUTUtil.getArguments(
                    FileOutputStream.class, "<init>", 0);
            assertEquals(2, fileOutputStreamInitArguments.size());
            assertEquals(fileName, fileOutputStreamInitArguments.get(0));
            assertTrue(Boolean.class.cast(fileOutputStreamInitArguments.get(1)));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildFields"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildStringConverters"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildMethods"));

            assertEquals(1, VMOUTUtil.getCallCount(File.class, "delete"));

            // ����(�t�@�C��)
            assertTrue(new File(fileName).exists());

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "UTF-8"));
            assertFalse(postReader.ready());

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }
        }
    }

    /**
     * testInit03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testInit03.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ffalse(�f�t�H���g�l)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.calledInit:false<br>
     * (���) this.fileEncoding:����clazz��@FileFormat�̐ݒ�ɏ]���B<br>
     * (���) this.writer:null<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testInit03.txt"<br>
     * �E1�s�f�[�^����<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.writer:�ȉ��̐ݒ������BufferedWriter�C���X�^���X<br>
     * �Enew BufferedWriter(<br>
     * new OutputStreamWriter(<br>
     * (new FileOutputStream(fileName, true)),<br>
     * fileEncoding))<br>
     * <br>
     * ��Writer�̐����\���͕��G�Ȃ��ߊm�F����B<br>
     * �����DJUnit�Ōďo�����̈������m�F���邱�Ƃɂ���B<br>
     * (��ԕω�) #buildFields():1��Ă΂��<br>
     * (��ԕω�) #buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) #buildMethods():1��Ă΂��<br>
     * (��ԕω�) file#delete():�Ă΂�Ȃ�<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testInit03.txt"<br>
     * �E1�s�f�[�^����<br>
     * <br>
     * ���ω��Ȃ�<br>
     * <br>
     * ����p�^�[���B<br>
     * (overWriteFlg�ݒ�Ffalse(�f�t�H���g�l))<br>
     * �������t�@�C���s�I�u�W�F�N�g�ɑ΂���init()�������s���邱�Ƃ��m�F����B<br>
     * �܂��A�������ݑΏۃt�@�C���̏�񂪍폜����Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInit03() throws Exception {
        // �O����(����)
        String fileName = TEMP_FILE_NAME;

        Class<AbstractFileLineWriter_Stub06> clazz = AbstractFileLineWriter_Stub06.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O����(�����Ώ�)
        AbstractFileLineWriter<AbstractFileLineWriter_Stub06> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub06>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "calledInit", Boolean.FALSE);

        UTUtil.setPrivateField(fileLineWriter, "writer", null);

        BufferedReader postReader = null;
        try {
            // �e�X�g���{
            fileLineWriter.init();

            // ����(�t�B�[���h)
            assertNotNull(UTUtil.getPrivateField(fileLineWriter, "writer"));
            assertEquals(1, VMOUTUtil.getCallCount(BufferedWriter.class,
                    "<init>"));
            List bufferedWriterInitArguments = VMOUTUtil.getArguments(
                    BufferedWriter.class, "<init>", 0);
            assertEquals(1, bufferedWriterInitArguments.size());
            assertTrue(OutputStreamWriter.class
                    .isAssignableFrom(bufferedWriterInitArguments.get(0)
                            .getClass()));
            assertEquals(1, VMOUTUtil.getCallCount(OutputStreamWriter.class,
                    "<init>"));
            List outputStreamWriterInitArguments = VMOUTUtil.getArguments(
                    OutputStreamWriter.class, "<init>", 0);
            assertEquals(2, outputStreamWriterInitArguments.size());
            assertTrue(FileOutputStream.class
                    .isAssignableFrom(outputStreamWriterInitArguments.get(0)
                            .getClass()));
            assertEquals("UTF-8", outputStreamWriterInitArguments.get(1));
            assertEquals(1, VMOUTUtil.getCallCount(FileOutputStream.class,
                    "<init>"));
            List fileOutputStreamInitArguments = VMOUTUtil.getArguments(
                    FileOutputStream.class, "<init>", 0);
            assertEquals(2, fileOutputStreamInitArguments.size());
            assertEquals(fileName, fileOutputStreamInitArguments.get(0));
            assertTrue(Boolean.class.cast(fileOutputStreamInitArguments.get(1)));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildFields"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildStringConverters"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildMethods"));

            assertFalse(VMOUTUtil.isCalled(File.class, "delete"));

            // ����(�t�@�C��)
            assertTrue(new File(fileName).exists());

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "UTF-8"));

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }
        }
    }

    /**
     * testInit04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testInit04.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-X"(���݂��Ȃ��G���R�[�f�B���O)<br>
     * - overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.calledInit:false<br>
     * (���) this.fileEncoding:����clazz��@FileFormat�̐ݒ�ɏ]���B<br>
     * (���) this.writer:null<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testInit04.txt"<br>
     * �E1�s�f�[�^����<br>
     * <br>
     * ���Ғl�F(��ԕω�) #buildFields():1��Ă΂��<br>
     * (��ԕω�) #buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) #buildMethods():1��Ă΂��<br>
     * (��ԕω�) file#delete():�Ă΂�Ȃ�<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testInit03.txt"<br>
     * �E1�s�f�[�^����<br>
     * <br>
     * ���ω��Ȃ�<br>
     * (��ԕω�) -:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"Failed in generation of writer."<br>
     * �E������O�FUnsupportedEncodingException<br>
     * �EfileName�F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * �ُ�p�^�[���B<br>
     * ���݂��Ȃ��G���R�[�f�B���O���ݒ肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testInit04() throws Exception {
        // �O����(����)
        String fileName = TEMP_FILE_NAME;

        Class<AbstractFileLineWriter_Stub07> clazz = AbstractFileLineWriter_Stub07.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O����(�����Ώ�)
        AbstractFileLineWriter<AbstractFileLineWriter_Stub07> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub07>(
                fileName, clazz, columnFormatterMap);
        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "calledInit", Boolean.FALSE);

        UTUtil.setPrivateField(fileLineWriter, "writer", null);

        BufferedReader postReader = null;
        try {
            // �e�X�g���{
            fileLineWriter.init();
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Failed in generation of writer.", e.getMessage());
            assertTrue(UnsupportedEncodingException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildFields"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildStringConverters"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildMethods"));

            assertFalse(VMOUTUtil.isCalled(File.class, "delete"));

            // ����(�t�@�C��)
            assertTrue(new File(fileName).exists());

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "UTF-8"));

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }
        }
    }

    /**
     * testInit05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * ".txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F"|"(�f�t�H���g�l�ȊO)<br>
     * - encloseChar�F"\""(�f�t�H���g�l�ȊO)<br>
     * - lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)<br>
     * - fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)<br>
     * - overWriteFlg�Ffalse(�f�t�H���g�l)<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.calledInit:false<br>
     * (���) this.fileEncoding:����clazz��@FileFormat�̐ݒ�ɏ]���B<br>
     * (���) this.writer:null<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C������сA�f�B���N�g���͑��݂��Ȃ��B<br>
     * "dummy/.txt"<br>
     * <br>
     * ���Ғl�F(��ԕω�) #buildFields():1��Ă΂��<br>
     * (��ԕω�) #buildStringConverters():1��Ă΂��<br>
     * (��ԕω�) #buildMethods():1��Ă΂��<br>
     * (��ԕω�) file#delete():�Ă΂�Ȃ�<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C������сA�f�B���N�g���͑��݂��Ȃ��B<br>
     * "dummy/.txt"<br>
     * (��ԕω�) -:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"Failed in generation of writer."<br>
     * �E������O�FFileNotFoundException<br>
     * �EfileName�F����fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     * �ُ�p�^�[���B<br>
     * ���݂��Ȃ��t�@�C�����ݒ肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testInit05() throws Exception {
        // �O����(����)
        URL url = this.getClass().getResource("File_Empty.txt");

        String fileName = url.getPath().substring(0,
                url.getPath().indexOf("File_Empty.txt"))
                + "dummy/AbstractFileLineWriter_testInit05.txt";

        Class<AbstractFileLineWriter_Stub06> clazz = AbstractFileLineWriter_Stub06.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O����(�����Ώ�)
        AbstractFileLineWriter<AbstractFileLineWriter_Stub06> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub06>(
                fileName, clazz, columnFormatterMap);
        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "calledInit", Boolean.FALSE);

        UTUtil.setPrivateField(fileLineWriter, "writer", null);

        try {
            // �e�X�g���{
            fileLineWriter.init();
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Failed in generation of writer.", e.getMessage());
            assertTrue(FileNotFoundException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildFields"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildStringConverters"));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "buildMethods"));

            assertFalse(VMOUTUtil.isCalled(File.class, "delete"));

            // ����(�t�@�C��)
            assertFalse(new File(fileName).exists());
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * testBuildFields01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields01.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.filelds:null<br>
     * (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.fields:�v�f�������Ȃ�Field�z��<br>
     * <br>
     * ����P�[�X<br>
     * (�e�N���X����A�t�B�[���h��`�Ȃ�)<br>
     * �t�@�C���s�I�u�W�F�N�g�N���X(�e�N���X���܂�)�Ƀt�B�[���h��`���Ȃ��ꍇ�Afields���v�f�������Ȃ��z��ŏ���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub38> clazz = AbstractFileLineWriter_Stub38.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub38> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub38>(
                fileName, clazz, columnFormatterMap);

        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(0, resultFields.length);
    }

    /**
     * testBuildFields02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields02.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * (���) this.filelds:null<br>
     * (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F (��ԕω�) this.fields:�v�f�������Ȃ�Field�z��<br>
     * <br>
     * ����P�[�X<br>
     * (�e�N���X�Ȃ��A�t�B�[���h��`����F1��)<br>
     * (@OutputFileColumn�ݒ�Ȃ��F1��)<br>
     * �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`������ꍇ�Afields������������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields02() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub09> clazz = AbstractFileLineWriter_Stub09.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub09> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub09>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(0, resultFields.length);
    }

    /**
     * testBuildFields03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields03.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.fields:�ȉ��̗v�f������Field�z��<br>
     *                         �P�DField�I�u�W�F�N�g�Fcolumn1<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F1��)<br>
     *                         (@OutputFileColumn�ݒ肠��F1��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`������ꍇ�Afields������������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields03() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(1, resultFields.length);
        Field resultFields1 = resultFields[0];
        assertEquals("column1", resultFields1.getName());
    }

    /**
     * testBuildFields04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields04.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * (���) this.filelds:null<br>
     * (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"int"=IntColumnFormatter<br>
     * �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.fields:�v�f�������Ȃ�Field�z��<br>
     * <br>
     * ����P�[�X<br>
     * (�e�N���X�Ȃ��A�t�B�[���h��`����F3��)<br>
     * (@OutputFileColumn�ݒ�Ȃ��F3��)<br>
     * �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`������ꍇ�Afields������������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields04() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub11> clazz = AbstractFileLineWriter_Stub11.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub11> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub11>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(0, resultFields.length);
    }

    /**
     * testBuildFields05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields05.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.fields:�ȉ��̗v�f������Field�z��<br>
     *                         �P�DField�I�u�W�F�N�g�Fcolumn1<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F3��)<br>
     *                         (@OutputFileColumn�ݒ�Ȃ��F2��)<br>
     *                         (@OutputFileColumn�ݒ肠��F1��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`������ꍇ�AfieldArray��fields������������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields05() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub12> clazz = AbstractFileLineWriter_Stub12.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub12> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub12>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(1, resultFields.length);
        Field resultFields1 = resultFields[0];
        assertNotNull(resultFields1);
        assertEquals("column1", resultFields1.getName());
    }

    /**
     * testBuildFields06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields06.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.fields:�ȉ��̗v�f������Field�z��<br>
     *                         �P�DField�I�u�W�F�N�g�Fcolumn1<br>
     *                         �Q�DField�I�u�W�F�N�g�Fcolumn2<br>
     *                         �R�DField�I�u�W�F�N�g�Fcolumn3<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F3��)<br>
     *                         (@OutputFileColumn�ݒ肠��F3��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`������ꍇ�Afields������������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields06() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub13> clazz = AbstractFileLineWriter_Stub13.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub13> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub13>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(3, resultFields.length);
        Field resultFieldsArray1 = resultFields[0];
        assertNotNull(resultFieldsArray1);
        assertEquals("column1", resultFieldsArray1.getName());
        Field resultFieldsArray2 = resultFields[1];
        assertNotNull(resultFieldsArray2);
        assertEquals("column2", resultFieldsArray2.getName());
        Field resultFieldsArray3 = resultFields[2];
        assertNotNull(resultFieldsArray3);
        assertEquals("column3", resultFieldsArray3.getName());
    }

    /**
     * testBuildFields07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields07.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         ��columnIndex���d�����Ă���B<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) ��O:�ȉ��̐ݒ������FileException����������<br>
     *                         ����b�Z�[�W�F"Column Index is duplicate : 1"<br>
     *                         �EfileName�Fthis.fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F3��)<br>
     *                         (@OutputFileColumn�ݒ肠��F3��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`��columnIndex���d�������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields07() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub14> clazz = AbstractFileLineWriter_Stub14.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub14> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub14>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineWriter, "buildFields");
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));

            assertEquals("Column Index is duplicate : 1", e.getMessage());

            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testBuildFields08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields08.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         ���e�t�B�[���h�̏��Ԃ͋t���i���Ԃł͂Ȃ��j�̂��ƁB<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�)this.fields:�ȉ��̗v�f������Field�z��<br>
     *                         �P�DField�I�u�W�F�N�g�Fcolumn1<br>
     *                         �Q�DField�I�u�W�F�N�g�Fcolumn2<br>
     *                         �R�DField�I�u�W�F�N�g�Fcolumn3<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F3��)<br>
     *                         (@OutputFileColumn�ݒ肠��F3��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`������ꍇ�Afields������������������邱�Ƃ��m�F����B<br>
     *                         �܂��Afield�̒�`���Ԃł͂Ȃ�columnIndex���ԂɊi�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields08() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub15> clazz = AbstractFileLineWriter_Stub15.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub15> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub15>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(3, resultFields.length);
        Field resultFieldsArray1 = resultFields[0];
        assertNotNull(resultFieldsArray1);
        assertEquals("column1", resultFieldsArray1.getName());
        Field resultFieldsArray2 = resultFields[1];
        assertNotNull(resultFieldsArray2);
        assertEquals("column2", resultFieldsArray2.getName());
        Field resultFieldsArray3 = resultFields[2];
        assertNotNull(resultFieldsArray3);
        assertEquals("column3", resultFieldsArray3.getName());

    }

    /**
     * testBuildFields09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields09.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) ��O:�ȉ��̐ݒ������FileException����������<br>
     *                         ����b�Z�[�W�F"Column Index in FileLineObject is bigger than the total number of the field."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �EfileName�Fthis.fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F1��)<br>
     *                         (@OutputFileColumn�ݒ肠��F1��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`��columnIndex���t�B�[���h�̐����ȏ��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields09() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub16> clazz = AbstractFileLineWriter_Stub16.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub16> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub16>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineWriter, "buildFields");
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals("Column Index in FileLineObject is bigger than the "
                    + "total number of the field.", e.getMessage());
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A�t�B�[���h)
            Field[] resultFields = (Field[]) UTUtil.getPrivateField(
                    fileLineWriter, "fields");
            assertNull(resultFields);
        }
    }

    /**
     * testBuildFields10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields10.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F-1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) ��O:�ȉ��̐ݒ������FileException����������<br>
     *                         ����b�Z�[�W�F"Column Index in FileLineObject is the minus number."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �EfileName�Fthis.fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F1��)<br>
     *                         (@OutputFileColumn�ݒ肠��F1��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`��columnIndex���}�C�i�X�l��ݒ肵���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields10() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub17> clazz = AbstractFileLineWriter_Stub17.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub17> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub17>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineWriter, "buildFields");
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals("Column Index in FileLineObject is the minus number.",
                    e.getMessage());
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A�t�B�[���h)
            Field[] resultFields = (Field[]) UTUtil.getPrivateField(
                    fileLineWriter, "fields");
            assertNull(resultFields);
        }
    }

    /**
     * testBuildFields11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields11.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * - �t�B�[���h�FString noMappingColumn3<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     * <br>
     *                         ��columnIndex�Ɍ��Ԃ�����B<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) ��O:�ȉ��̐ݒ������FileException����������<br>
     *                         ����b�Z�[�W�F"columnIndex in FileLineObject is not sequential order."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �EfileName�Fthis.fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F5��)<br>
     *                         (@OutputFileColumn�ݒ�Ȃ��F3��)<br>
     *                         (@OutputFileColumn�ݒ肠��F2��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`��columnIndex�̒�`�Ō��Ԃ�����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields11() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub18> clazz = AbstractFileLineWriter_Stub18.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub18> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub18>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineWriter, "buildFields");
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals(
                    "columnIndex in FileLineObject is not sequential order.", e
                            .getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
        }
    }

    /**
     * testBuildFields12() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields12.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     * <br>
     *                         �ȉ��e�N���X�̒�`<br>
     *                         �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     *                         - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     * <br>
     *                         ���e�N���X�Ƀt�B�[���h�̏�񂪂���B<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.fields:�ȉ��̗v�f������Field�z��<br>
     *                         �P�DField�I�u�W�F�N�g�Fcolumn1<br>
     *                         �Q�DField�I�u�W�F�N�g�Fcolumn2<br>
     *                         �R�DField�I�u�W�F�N�g�Fcolumn3<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�e�N���X����A�t�B�[���h��`����F5��)<br>
     *                         (@OutputFileColumn�ݒ�Ȃ��F2��)<br>
     *                         (@OutputFileColumn�ݒ肠��F3��(�e�N���X1��))<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�Ƀt�B�[���h��`�����鐳��������Ă���ꍇ�Afields������������������邱�Ƃ��m�F����B<br>
     *                         �e�N���X�̃t�B�[���h�̒�`���F�����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields12() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub19> clazz = AbstractFileLineWriter_Stub19.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub19> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub19>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // ����(��ԕω��A�t�B�[���h)
        Field[] resultFields = (Field[]) UTUtil.getPrivateField(fileLineWriter,
                "fields");
        assertEquals(3, resultFields.length);
        Field resultFields1 = resultFields[0];
        assertNotNull(resultFields1);
        assertEquals("column1", resultFields1.getName());
        Field resultFields2 = resultFields[1];
        assertNotNull(resultFields2);
        assertEquals("column2", resultFields2.getName());
        Field resultFields3 = resultFields[2];
        assertNotNull(resultFields3);
        assertEquals("column3", resultFields3.getName());
    }

    /**
     * testBuildFields13() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FB,G <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildFields15.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�Flong column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         ���T�|�[�g���Ȃ��^�C�v�̃t�B�[���h������B<br>
     *                         (���) this.filelds:null<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.fields:null<br>
     *                         (��ԕω�) ��O:�ȉ��̐ݒ������FileException����������<br>
     *                         ����b�Z�[�W�F"There is a type which isn't supported in a mapping target field in FileLineObject."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �EfileName�Fthis.fileName�Ɠ����C���X�^���X�B<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�e�N���X�Ȃ��A�t�B�[���h��`����F3��)<br>
     *                         (@OutputFileColumn�ݒ肠��F3��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�̃t�B�[���h�^��this.columnFormatterrMap�ɑ��݂��Ȃ��^�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testBuildFields13() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub40> clazz = AbstractFileLineWriter_Stub40.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub40> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub40>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fields", null);

        // �e�X�g���{
        try {
            UTUtil.invokePrivate(fileLineWriter, "buildFields");
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals("There is a type which isn't supported in a mapping "
                    + "target field in FileLineObject.", e.getMessage());
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A�t�B�[���h)
            Field[] resultFields = (Field[]) UTUtil.getPrivateField(
                    fileLineWriter, "fields");
            assertNull(resultFields);
        }
    }

    /**
     * testBuildStringConverters01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildStringConverters01.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * (��ԕω�) this.stringConverters:�v�f�������Ȃ�StringConverter�z��<br>
     * <br>
     * ����P�[�X<br>
     * �t�@�C���s�I�u�W�F�N�g�N���X���t�B�[���h�������Ȃ��ꍇ�AstringConveters�z�񂪋�ŏ���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @SuppressWarnings("unchecked")
    @Test
    public void testBuildStringConverters01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub08> clazz = AbstractFileLineWriter_Stub08.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub08> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub08>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // �O����(�t�B�[���h)
        Map<Class, StringConverter> preStringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(AbstractFileLineWriter.class,
                "stringConverterCacheMap", preStringConverterCacheMap);

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

            // ����(��ԕω��A�t�B�[���h)
            Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverterCacheMap");
            assertNotNull(stringConverterCacheMap);
            assertEquals(0, stringConverterCacheMap.size());

            StringConverter[] stringConverters = (StringConverter[]) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverters");
            assertNotNull(stringConverters);
            assertEquals(0, stringConverters.length);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildStringConverters02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildStringConverters02.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > StringConverter.class�FNullStringConverter.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������Map�C���X�^���X<br>
     *                         �Ekey�FNullStringConverter.class,<br>
     *                         value�FNullStringConverter�C���X�^���X<br>
     *                         (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��<br>
     *                         1�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (stringConverter�ݒ肠��t�B�[���h�F1��)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X�̃t�B�[���h��stringConverter�ݒ肪����ꍇ�AstringConverters�z�񂪐�������������邱�ƂƁA���̃C���X�^���X���L���b�V������邱�Ƃ��m�F����
     *                         �B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @SuppressWarnings("unchecked")
    @Test
    public void testBuildStringConverters02() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub20> clazz = AbstractFileLineWriter_Stub20.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub20> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub20>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // �O����(�t�B�[���h)
        Map<Class, StringConverter> preStringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(AbstractFileLineWriter.class,
                "stringConverterCacheMap", preStringConverterCacheMap);

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

            // ����(��ԕω��A�t�B�[���h)
            Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverterCacheMap");
            assertNotNull(stringConverterCacheMap);
            assertEquals(1, stringConverterCacheMap.size());
            assertTrue(stringConverterCacheMap
                    .containsKey(NullStringConverter.class));
            StringConverter nullStringConverter = stringConverterCacheMap
                    .get(NullStringConverter.class);
            assertNotNull(nullStringConverter);
            assertTrue(NullStringConverter.class
                    .isAssignableFrom(nullStringConverter.getClass()));

            StringConverter[] stringConverters = (StringConverter[]) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverters");
            assertNotNull(stringConverters);
            assertEquals(1, stringConverters.length);
            StringConverter stringConverters1 = stringConverters[0];
            assertSame(nullStringConverter, stringConverters1);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildStringConverters03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildStringConverters03.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > StringConverter.class�FNullStringConverter.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > StringConverter.class�FNullStringConverter.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > StringConverter.class�FNullStringConverter.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������Map�C���X�^���X<br>
     *                         �Ekey�FNullStringConverter.class,<br>
     *                         value�FNullStringConverter�C���X�^���X<br>
     *                         (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��<br>
     *                         1�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         2�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         3�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (stringConverter�ݒ肠��t�B�[���h�F3�A<br>
     *                         ����stringConverter�𗘗p����B)<br>
     *                         stringConverter�ݒ肪����ꍇ�AstringConverters�z�񂪐�������������邱�ƂƁA���̃C���X�^���X���L���b�V������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @SuppressWarnings("unchecked")
    @Test
    public void testBuildStringConverters03() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub21> clazz = AbstractFileLineWriter_Stub21.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub21> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub21>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // �O����(�t�B�[���h)
        Map<Class, StringConverter> preStringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(AbstractFileLineWriter.class,
                "stringConverterCacheMap", preStringConverterCacheMap);

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

            // ����(��ԕω��A�t�B�[���h)
            Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverterCacheMap");
            assertNotNull(stringConverterCacheMap);
            assertEquals(1, stringConverterCacheMap.size());
            assertTrue(stringConverterCacheMap
                    .containsKey(NullStringConverter.class));
            StringConverter nullStringConverter = stringConverterCacheMap
                    .get(NullStringConverter.class);
            assertNotNull(nullStringConverter);
            assertTrue(NullStringConverter.class
                    .isAssignableFrom(nullStringConverter.getClass()));

            StringConverter[] stringConverters = (StringConverter[]) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverters");
            assertNotNull(stringConverters);
            assertEquals(3, stringConverters.length);
            StringConverter stringConverters1 = stringConverters[0];
            assertNotNull(stringConverters1);
            assertSame(nullStringConverter, stringConverters1);
            StringConverter stringConverters2 = stringConverters[1];
            assertNotNull(stringConverters2);
            assertSame(nullStringConverter, stringConverters2);
            StringConverter stringConverters3 = stringConverters[2];
            assertNotNull(stringConverters3);
            assertSame(nullStringConverter, stringConverters3);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildStringConverters04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildStringConverters04.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > stringConverter�FNullStringConverter.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > stringConverter�FSringConverterToLowerCase.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > stringConverter�FSringConverterToUpperCase.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column4<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F3<br>
     *                         > stringConverter�FNullStringConverter.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column5<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F4<br>
     *                         > stringConverter�FSringConverterToLowerCase.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column6<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F5<br>
     *                         > stringConverter�FSringConverterToUpperCase.class<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������Map�C���X�^���X<br>
     *                         �Ekey�FNullStringConverter.class,<br>
     *                         value�FNullStringConverter�C���X�^���X<br>
     *                         �Ekey�FSringConverterToLowerCase.class,<br>
     *                         value�FSringConverterToLowerCase�C���X�^���X<br>
     *                         �Ekey�FSringConverterToUpperCase.class,<br>
     *                         value�FSringConverterToUpperCase�C���X�^���X<br>
     *                         (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��<br>
     *                         1�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         2�DSringConverterToLowerCase�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         3�DSringConverterToUpperCase�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         4�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         5�DSringConverterToLowerCase�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         6�DSringConverterToUpperCase�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (stringConverter�ݒ肠��t�B�[���h�F5�A<br>
     *                         ����stringConverter�𗘗p����B)<br>
     *                         stringConverter�ݒ肪����ꍇ�AstringConverters�z�񂪐�������������邱�ƂƁA���̃C���X�^���X���L���b�V������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @SuppressWarnings("unchecked")
    @Test
    public void testBuildStringConverters04() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub22> clazz = AbstractFileLineWriter_Stub22.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub22> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub22>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // �O����(�t�B�[���h)
        Map<Class, StringConverter> preStringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(AbstractFileLineWriter.class,
                "stringConverterCacheMap", preStringConverterCacheMap);

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

            // ����(��ԕω��A�t�B�[���h)
            Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverterCacheMap");
            assertNotNull(stringConverterCacheMap);
            assertEquals(3, stringConverterCacheMap.size());
            assertTrue(stringConverterCacheMap
                    .containsKey(NullStringConverter.class));
            StringConverter nullStringConverter = stringConverterCacheMap
                    .get(NullStringConverter.class);
            assertNotNull(nullStringConverter);
            assertTrue(NullStringConverter.class
                    .isAssignableFrom(nullStringConverter.getClass()));
            assertTrue(stringConverterCacheMap
                    .containsKey(NullStringConverter.class));
            StringConverter stringConverterToLowerCase = stringConverterCacheMap
                    .get(StringConverterToLowerCase.class);
            assertNotNull(stringConverterToLowerCase);
            assertTrue(StringConverterToLowerCase.class
                    .isAssignableFrom(stringConverterToLowerCase.getClass()));
            assertTrue(stringConverterCacheMap
                    .containsKey(StringConverterToUpperCase.class));
            StringConverter stringConverterToUpperCase = stringConverterCacheMap
                    .get(StringConverterToUpperCase.class);
            assertNotNull(stringConverterToUpperCase);
            assertTrue(StringConverterToUpperCase.class
                    .isAssignableFrom(stringConverterToUpperCase.getClass()));

            StringConverter[] stringConverters = (StringConverter[]) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverters");
            assertNotNull(stringConverters);
            assertEquals(6, stringConverters.length);
            StringConverter stringConverters1 = stringConverters[0];
            assertNotNull(stringConverters1);
            assertSame(nullStringConverter, stringConverters1);
            StringConverter stringConverters2 = stringConverters[1];
            assertNotNull(stringConverters2);
            assertSame(stringConverterToLowerCase, stringConverters2);
            StringConverter stringConverters3 = stringConverters[2];
            assertNotNull(stringConverters3);
            assertSame(stringConverterToUpperCase, stringConverters3);
            StringConverter stringConverters4 = stringConverters[3];
            assertNotNull(stringConverters4);
            assertSame(nullStringConverter, stringConverters4);
            StringConverter stringConverters5 = stringConverters[4];
            assertNotNull(stringConverters5);
            assertSame(stringConverterToLowerCase, stringConverters5);
            StringConverter stringConverters6 = stringConverters[5];
            assertNotNull(stringConverters6);
            assertSame(stringConverterToUpperCase, stringConverters6);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildStringConverters05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildStringConverters05.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > stringConverter�F�f�t�H���g�R���X�g���N�^�������ĂȂ�StringConverter�̃N���X�C���X�^���X<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) -:�ȉ��̏�������FileLineException()����������<br>
     *                         �E���b�Z�[�W�F"Failed in an instantiate of a stringConverter."<br>
     *                         �E������O�FInstantiationException<br>
     *                         �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F-1<br>
     *                         �E�J�������Fcolumn1<br>
     *                         �E�J�����ԍ��F0<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         �w�肵��StringConverter�Ƀf�t�H���g�R���X�g���N�^�����݂��Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testBuildStringConverters05() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub23> clazz = AbstractFileLineWriter_Stub23.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub23> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub23>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // �O����(�t�B�[���h)
        Map<Class, StringConverter> preStringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(AbstractFileLineWriter.class,
                "stringConverterCacheMap", preStringConverterCacheMap);

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals("Failed in an instantiate of a stringConverter.", e
                    .getMessage());
            assertTrue(InstantiationException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());
            assertEquals(-1, e.getLineNo());
            assertEquals("column1", e.getColumnName());
            assertEquals(0, e.getColumnIndex());
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildStringConverters06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�F�f <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildStringConverters06.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > stringConverter�F�f�t�H���g�R���X�g���N�^��private�Ő錾����Ă���StringConverter�̃N���X�C���X�^���X<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) -:�ȉ��̏�������FileLineException()����������<br>
     *                         �E���b�Z�[�W�F"Failed in an instantiate of a stringConverter."<br>
     *                         �E������O�FIllegalAccessException<br>
     *                         �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F-1<br>
     *                         �E�J�������Fcolumn1<br>
     *                         �E�J�����ԍ��F0<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         �w�肵��StringConverter�̃f�t�H���g�R���X�g���N�^��private�Ő錾����Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testBuildStringConverters06() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub24> clazz = AbstractFileLineWriter_Stub24.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub24> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub24>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // �O����(�t�B�[���h)
        Map<Class, StringConverter> preStringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(AbstractFileLineWriter.class,
                "stringConverterCacheMap", preStringConverterCacheMap);

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals("Failed in an instantiate of a stringConverter.", e
                    .getMessage());
            assertTrue(IllegalAccessException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());
            assertEquals(-1, e.getLineNo());
            assertEquals("column1", e.getColumnName());
            assertEquals(0, e.getColumnIndex());
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildStringConverters07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildStringConverters07.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         (���) this.stringConverterCacheMap:�v�f�������Ȃ�Map�C���X�^���X<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.stringConverterCacheMap:�ȉ��̗v�f������Map�C���X�^���X<br>
     *                         �Ekey�FNullStringConverter.class,<br>
     *                         value�FNullStringConverter�C���X�^���X<br>
     *                         (��ԕω�) this.stringConverters:�ȉ��̗v�f������StringConverter�z��<br>
     *                         1�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         2�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     *                         3�DNullStringConverter�C���X�^���X(�L���b�V�����ꂽ���̂Ɠ����C���X�^���X)<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (stringConverter�ݒ�Ȃ��t�B�[���h�F3��)<br>
     *                         stringConverter�ݒ肪�Ȃ��ꍇ�ANullStringConverter�ŏ���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @SuppressWarnings("unchecked")
    @Test
    public void testBuildStringConverters07() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub13> clazz = AbstractFileLineWriter_Stub13.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub13> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub13>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");

        // �O����(�t�B�[���h)
        Map<Class, StringConverter> preStringConverterCacheMap = new HashMap<Class, StringConverter>();
        UTUtil.setPrivateField(AbstractFileLineWriter.class,
                "stringConverterCacheMap", preStringConverterCacheMap);

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

            // ����(��ԕω��A�t�B�[���h)
            Map<Class, StringConverter> stringConverterCacheMap = (Map<Class, StringConverter>) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverterCacheMap");
            assertNotNull(stringConverterCacheMap);
            assertEquals(1, stringConverterCacheMap.size());
            assertTrue(stringConverterCacheMap
                    .containsKey(NullStringConverter.class));
            StringConverter nullStringConverter = stringConverterCacheMap
                    .get(NullStringConverter.class);
            assertNotNull(nullStringConverter);
            assertTrue(NullStringConverter.class
                    .isAssignableFrom(nullStringConverter.getClass()));

            StringConverter[] stringConverters = (StringConverter[]) UTUtil
                    .getPrivateField(fileLineWriter, "stringConverters");
            assertNotNull(stringConverters);
            assertEquals(3, stringConverters.length);
            StringConverter stringConverters1 = stringConverters[0];
            assertNotNull(stringConverters1);
            assertSame(nullStringConverter, stringConverters1);
            StringConverter stringConverters2 = stringConverters[1];
            assertNotNull(stringConverters2);
            assertSame(nullStringConverter, stringConverters2);
            StringConverter stringConverters3 = stringConverters[2];
            assertNotNull(stringConverters3);
            assertSame(nullStringConverter, stringConverters3);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildMethods01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildMethodsConverters01.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.methods:�v�f�������Ȃ�Method�z��C���X�^���X<br>
     * <br>
     * ����P�[�X<br>
     * �t�@�C���s�I�u�W�F�N�g�N���X���t�B�[���h�������Ȃ��ꍇ�Amethods�z�񂪋�ŏ���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testBuildMethods01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub08> clazz = AbstractFileLineWriter_Stub08.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub08> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub08>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");
        UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildMethods");

            // ����(��ԕω��A�t�B�[���h)
            Method[] methods = (Method[]) UTUtil.getPrivateField(
                    fileLineWriter, "methods");
            assertNotNull(methods);
            assertEquals(0, methods.length);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildMethods02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildMethodsConverters02.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.methods:�ȉ��̗v�f������Method�z��C���X�^���X<br>
     *                         �P�DMethod�I�u�W�F�N�g<br>
     *                         - ���\�b�h���FgetColumn1<br>
     *                         - �����F�Ȃ�<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (@OutputFileColumn�ݒ肠��t�B�[���h�F1�A<br>
     *                         �t�B�[���h�ɑ΂���getter���\�b�h����)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X��@OutputFileColumn�ݒ肠��t�B�[���h�����ꍇ�Amethods�z�񂪐���������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testBuildMethods02() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");
        UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildMethods");

            // ����(��ԕω��A�t�B�[���h)
            Method[] methods = (Method[]) UTUtil.getPrivateField(
                    fileLineWriter, "methods");
            assertNotNull(methods);
            assertEquals(1, methods.length);
            Method methods1 = methods[0];
            assertEquals("getColumn1", methods1.getName());
            assertEquals(0, methods1.getParameterTypes().length);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildMethods03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildMethodsConverters03.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�������Ȃ��B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) -:�ȉ��̐ݒ������FileException�C���X�^���X<br>
     *                         �E���b�Z�[�W�F"The getter method of column doesn't exist."<br>
     *                         �E������O�FNoSuchMethodException<br>
     *                         �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (@OutputFileColumn�ݒ肠��t�B�[���h�F1�A<br>
     *                         �t�B�[���h�ɑ΂���getter���\�b�h�Ȃ�)<br>
     *                         �t�B�[���h�ɑ΂���getter���\�b�h���Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testBuildMethods03() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub25> clazz = AbstractFileLineWriter_Stub25.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub25> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub25>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");
        UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildMethods");
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("The getter method of column doesn't exist.", e
                    .getMessage());
            assertTrue(NoSuchMethodException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());

        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildMethods04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildMethodsConverters04.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.methods:�ȉ��̗v�f������Method�z��C���X�^���X<br>
     *                         1�DMethod�I�u�W�F�N�g<br>
     *                         - ���\�b�h���FgetColumn1<br>
     *                         - �����F�Ȃ�<br>
     *                         2�DMethod�I�u�W�F�N�g<br>
     *                         - ���\�b�h���FgetColumn2<br>
     *                         - �����F�Ȃ�<br>
     *                         3�DMethod�I�u�W�F�N�g<br>
     *                         - ���\�b�h���FgetColumn3<br>
     *                         - �����F�Ȃ�<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (@OutputFileColumn�ݒ肠��t�B�[���h�F3�A<br>
     *                         �t�B�[���h�ɑ΂���getter���\�b�h����)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X��@OutputFileColumn�ݒ肠��t�B�[���h�����ꍇ�Amethods�z�񂪐���������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testBuildMethods04() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub13> clazz = AbstractFileLineWriter_Stub13.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub13> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub13>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");
        UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildMethods");

            // ����(��ԕω��A�t�B�[���h)
            Method[] methods = (Method[]) UTUtil.getPrivateField(
                    fileLineWriter, "methods");
            assertNotNull(methods);
            assertEquals(3, methods.length);
            Method methods1 = methods[0];
            assertEquals("getColumn1", methods1.getName());
            assertEquals(0, methods1.getParameterTypes().length);
            Method methods2 = methods[1];
            assertEquals("getColumn2", methods2.getName());
            assertEquals(0, methods2.getParameterTypes().length);
            Method methods3 = methods[2];
            assertEquals("getColumn3", methods3.getName());
            assertEquals(0, methods3.getParameterTypes().length);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testBuildMethods05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testBuildMethodsConverters05.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ�Ȃ��̃t�B�[���h������<br>
     * - �t�B�[���h�FString noMappingColumn1<br>
     * - �t�B�[���h�FString noMappingColumn2<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     * <br>
     *                         �ȉ��e�N���X�̒�`<br>
     *                         �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     *                         - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     * <br>
     *                         ���e�N���X�Ƀt�B�[���h�̏�񂪂���B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.methods:�ȉ��̗v�f������Method�z��C���X�^���X<br>
     *                         1�DMethod�I�u�W�F�N�g<br>
     *                         - ���\�b�h���FgetColumn1<br>
     *                         - �����F�Ȃ�<br>
     *                         2�DMethod�I�u�W�F�N�g<br>
     *                         - ���\�b�h���FgetColumn2<br>
     *                         - �����F�Ȃ�<br>
     *                         3�DMethod�I�u�W�F�N�g<br>
     *                         - ���\�b�h���FgetColumn3<br>
     *                         - �����F�Ȃ�<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (@OutputFileColumn�ݒ肠��t�B�[���h�F3�A<br>
     *                         �t�B�[���h�ɑ΂���getter���\�b�h����A<br>
     *                         �e�N���X�ɂ��t�B�[���h��`�����݂���)<br>
     *                         �t�@�C���s�I�u�W�F�N�g�N���X��@OutputFileColumn�ݒ肠��t�B�[���h�����ꍇ�Amethods�z�񂪐���������������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testBuildMethods05() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub19> clazz = AbstractFileLineWriter_Stub19.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub19> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub19>(
                fileName, clazz, columnFormatterMap);
        UTUtil.invokePrivate(fileLineWriter, "buildFields");
        UTUtil.invokePrivate(fileLineWriter, "buildStringConverters");

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "buildMethods");

            // ����(��ԕω��A�t�B�[���h)
            Method[] methods = (Method[]) UTUtil.getPrivateField(
                    fileLineWriter, "methods");
            assertNotNull(methods);
            assertEquals(3, methods.length);
            Method methods1 = methods[0];
            assertEquals("getColumn1", methods1.getName());
            assertEquals(0, methods1.getParameterTypes().length);
            Method methods2 = methods[1];
            assertEquals("getColumn2", methods2.getName());
            assertEquals(0, methods2.getParameterTypes().length);
            Method methods3 = methods[2];
            assertEquals("getColumn3", methods3.getName());
            assertEquals(0, methods3.getParameterTypes().length);
        } finally {
            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintHeaderLine01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) headerLine:�v�f�������Ȃ�List�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintHeaderLine01.txt"<br>
     * (���) writeTrailer:false<br>
     * (���) writeData:false<br>
     * <br>
     * ���Ғl�F(��ԕω�) #printList():1��Ă΂��B<br>
     * �����̊m�F���s���B<br>
     * <br>
     * ����P�[�X<br>
     * �f�[�^���ƃg���C�����̏������ݏ������s���O�Ɏ��s���ꂽ�ꍇ�A�Ώ�List�̏o�͏������s�����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintHeaderLine01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = TEMP_FILE_NAME;
        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> headerLine = new ArrayList<String>();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "writeData", Boolean.FALSE);

            // �e�X�g���{
            fileLineWriter.printHeaderLine(headerLine);

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "printList"));
            List printListArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "printList", 0);
            assertSame(headerLine, printListArguments.get(0));
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintHeaderLine02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) headerLine:�v�f�������Ȃ�List�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintHeaderLine02.txt"<br>
     * (���) writeTrailer:true<br>
     * (���) writeData:false<br>
     * <br>
     * ���Ғl�F(��ԕω�) #printList():�Ă΂�Ȃ�<br>
     * (��ԕω�) -:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"Header part should be called before data part or trailer part."<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     * <br>
     * �ُ�P�[�X<br>
     * �w�b�_�s���o�͂���O�Ƀg���C���s���o�͂���Ă����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintHeaderLine02() throws Exception {
        // �O����(�����Ώ�)
        String fileName = TEMP_FILE_NAME;

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> headerLine = new ArrayList<String>();

            // �O����(�t�B�[���h)
            UTUtil
                    .setPrivateField(fileLineWriter, "writeTrailer",
                            Boolean.TRUE);

            UTUtil.setPrivateField(fileLineWriter, "writeData", Boolean.FALSE);

            // �e�X�g���{
            fileLineWriter.printHeaderLine(headerLine);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Header part should be called before data part or "
                    + "trailer part.", e.getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "printList"));
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintHeaderLine03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) headerLine:�v�f�������Ȃ�List�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintHeaderLine03.txt"<br>
     * (���) writeTrailer:false<br>
     * (���) writeData:true<br>
     * <br>
     * ���Ғl�F(��ԕω�) #printList():�Ă΂�Ȃ�<br>
     * (��ԕω�) -:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"Header part should be called before data part or trailer part."<br>
     * �E������O�FIllegalStateException<br>
     * �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     * <br>
     * �ُ�P�[�X<br>
     * �w�b�_�s���o�͂���O�Ƀf�[�^�s���o�͂���Ă����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintHeaderLine03() throws Exception {
        // �O����(�����Ώ�)
        String fileName = TEMP_FILE_NAME;

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> headerLine = new ArrayList<String>();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "writeData", Boolean.TRUE);

            // �e�X�g���{
            fileLineWriter.printHeaderLine(headerLine);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Header part should be called before data part or "
                    + "trailer part.", e.getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "printList"));
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine01.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:true<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():�ُ�I��<br>
     *                         �E�ȉ��̗�O��Ԃ��B<br>
     *                         - ArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:0<br>
     *                         (��ԕω�) #getColumn():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) #getWriter().write():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) #setWriteData():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���ω��Ȃ�<br>
     *                         (��ԕω�) ��O:�ȉ��̏�������FileException����������<br>
     *                         �E���b�Z�[�W�F"Header part or data part should be called before TrailerPart"<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         �g���C�����̏o�͂����Ɋ�������Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine01() throws Exception {

        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub10 t = new AbstractFileLineWriter_Stub10();

            // �O����(�t�B�[���h)
            UTUtil
                    .setPrivateField(fileLineWriter, "writeTrailer",
                            Boolean.TRUE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineWriter.class,
                    "getColumn", new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);
            fail("FileException���������܂���ł����B");

        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Header part or data part should be called before "
                    + "TrailerPart", e.getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(0, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "getColumn"));

            assertFalse(VMOUTUtil.isCalled(Writer.class, "writer"));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "setWriteData"));

            // ����(�t�@�C��)
            writer.flush();

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine02.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.writeTrailer:false<br>
     * (���) this.currentLineCount:0<br>
     * (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) #getColumn():�ُ�I��<br>
     * �E�ȉ��̗�O��Ԃ��B<br>
     * - ArrayIndexOutOfBoundException<br>
     * (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     * (��ԕω�) #getColumn():�Ă΂�Ȃ�<br>
     * (��ԕω�) #getWriter().write():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) #setWriteData():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�F�s��؂蕶���P�̂�<br>
     * <br>
     * ����P�[�X<br>
     * (�o�͑Ώۃt�B�[���h�F�Ȃ��A��؂蕶���F�f�t�H���g�l�A<br>
     * �͂ݕ����F�f�t�H���g�l)<br>
     * �o�͑Ώۂ̃t�B�[���h���Ȃ��ꍇ�A�s��؂蕶���̂݃t�@�C���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine02() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub08> clazz = AbstractFileLineWriter_Stub08.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub08> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub08>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub08 t = new AbstractFileLineWriter_Stub08();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineWriter.class,
                    "getColumn", new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "getColumn"));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine03.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine03_column1"<br>
     *                         - 2��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"testPrintDataLine03_column1<�s��؂蕶��>"<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F1�A��؂蕶���F�f�t�H���g�l�A<br>
     *                         �͂ݕ����F�f�t�H���g�l)<br>
     *                         �o�͑Ώۂ̃t�B�[���h���P����ꍇ�A�o�͑Ώۃt�B�[���h�̏�񂪏o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine03() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub10 t = new AbstractFileLineWriter_Stub10();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine03_column1");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    1, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtument = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtument.size());
            assertSame(t, getColumnArtument.get(0));
            assertEquals(0, getColumnArtument.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "testPrintDataLine03_column1"
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine04.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine04_column1"<br>
     *                         - 2��ځF"testPrintDataLine04_column2"<br>
     *                         - 3��ځF"testPrintDataLine04_column3"<br>
     *                         - 4��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():3��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"testPrintDataLine04_column1<��؂蕶��>testPrintDataLine04_column2<��؂蕶��>testPrintDataLine04_column3<�s��؂蕶��>"
     * <br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F3�A��؂蕶���F�f�t�H���g�l�A<br>
     *                         �͂ݕ����F�f�t�H���g�l)<br>
     *                         �o�͑Ώۂ̃t�B�[���h��3����ꍇ�A�S�o�͑Ώۃt�B�[���h�̏��Ƃ��̊Ԃɋ�؂蕶�����o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine04() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub13> clazz = AbstractFileLineWriter_Stub13.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub13> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub13>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub13 t = new AbstractFileLineWriter_Stub13();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine04_column1");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 1, "testPrintDataLine04_column2");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 2, "testPrintDataLine04_column3");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    3, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(3, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtuments1 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtuments1.size());
            assertSame(t, getColumnArtuments1.get(0));
            assertEquals(0, getColumnArtuments1.get(1));
            List getColumnArtuments2 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 1);
            assertEquals(2, getColumnArtuments2.size());
            assertSame(t, getColumnArtuments2.get(0));
            assertEquals(1, getColumnArtuments2.get(1));
            List getColumnArtuments3 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 2);
            assertEquals(2, getColumnArtuments3.size());
            assertSame(t, getColumnArtuments3.get(0));
            assertEquals(2, getColumnArtuments3.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "testPrintDataLine04_column1,"
                    + "testPrintDataLine04_column2,testPrintDataLine04_column3"
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine05.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         ���ɃN���[�Y����Ă���B<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine05_column1"<br>
     *                         - 2��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:0<br>
     *                         (��ԕω�) #getColumn():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���ω��Ȃ�<br>
     *                         (��ԕω�) ��O:�ȉ��̏�������FileException����������<br>
     *                         �E���b�Z�[�W�F"Processing of writer was failed."<br>
     *                         �E������O�FIOException<br>
     *                         �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         �t�@�C���������ݗp��writer�����ɃN���[�Y���ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine05() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;

        // �O����(����)
        AbstractFileLineWriter_Stub10 t = new AbstractFileLineWriter_Stub10();

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));
            writer.close();

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine05_column1");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    1, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Processing of writer was failed.", e.getMessage());
            assertTrue(IOException.class.isAssignableFrom(e.getCause()
                    .getClass()));
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(0, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtument = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtument.size());
            assertSame(t, getColumnArtument.get(0));
            assertEquals(0, getColumnArtument.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "testPrintDataLine05_column1"
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "setWriteData"));

            // ����(�t�@�C��)
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine06.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F'\"'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.writeTrailer:false<br>
     * (���) this.currentLineCount:0<br>
     * (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) #getColumn():�ُ�I��<br>
     * �E�ȉ��̗�O��Ԃ��B<br>
     * - ArrayIndexOutOfBoundException<br>
     * (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     * (��ԕω�) #getColumn():�Ă΂�Ȃ�<br>
     * (��ԕω�) #getWriter().write():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) #setWriteData():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�F�s��؂蕶���P�̂�<br>
     * <br>
     * ����P�[�X<br>
     * (�o�͑Ώۃt�B�[���h�F�Ȃ��A��؂蕶���F�f�t�H���g�l�A<br>
     * �͂ݕ����F�f�t�H���g�l�ȊO)<br>
     * �o�͑Ώۂ̃t�B�[���h���Ȃ��ꍇ�A�s��؂蕶���̂݃t�@�C���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine06() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub26> clazz = AbstractFileLineWriter_Stub26.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub26> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub26>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub26 t = new AbstractFileLineWriter_Stub26();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineWriter.class,
                    "getColumn", new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "getColumn"));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine07.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F'\"'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine07_column1"<br>
     *                         - 2��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"\"testPrintDataLine07_column1\"<�s��؂蕶��>"<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F1�A��؂蕶���F�f�t�H���g�l�A<br>
     *                         �͂ݕ����F�f�t�H���g�l�ȊO)<br>
     *                         �o�͑Ώۂ̃t�B�[���h���P����ꍇ�A�͂ݕ����Ɉ͂܂ꂽ�o�͑Ώۃt�B�[���h�̏�񂪏o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine07() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub27> clazz = AbstractFileLineWriter_Stub27.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub27> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub27>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub27 t = new AbstractFileLineWriter_Stub27();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine07_column1");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    1, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtument = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtument.size());
            assertSame(t, getColumnArtument.get(0));
            assertEquals(0, getColumnArtument.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "\"testPrintDataLine07_column1\""
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine08.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - encloseChar�F'\"'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine08_column1"<br>
     *                         - 2��ځF"testPrintDataLine08_column2"<br>
     *                         - 3��ځF"testPrintDataLine08_column3"<br>
     *                         - 4��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():3��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F
     *                         "\"testPrintDataLine08_column1\"<��؂蕶��>\"testPrintDataLine08_column2\"<��؂蕶��>\"testPrintDataLine08_column3\"<�s��؂蕶��>"
     * <br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F3�A��؂蕶���F�f�t�H���g�l�A<br>
     *                         �͂ݕ����F�f�t�H���g�l�ȊO)<br>
     *                         �o�͑Ώۂ̃t�B�[���h��3����ꍇ�A�͂ݕ����Ɉ͂܂ꂽ�S�o�͑Ώۃt�B�[���h�̏��Ƃ��̊Ԃɋ�؂蕶�����o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine08() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub28> clazz = AbstractFileLineWriter_Stub28.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub28> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub28>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub28 t = new AbstractFileLineWriter_Stub28();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine08_column1");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 1, "testPrintDataLine08_column2");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 2, "testPrintDataLine08_column3");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    3, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(3, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtuments1 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtuments1.size());
            assertSame(t, getColumnArtuments1.get(0));
            assertEquals(0, getColumnArtuments1.get(1));
            List getColumnArtuments2 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 1);
            assertEquals(2, getColumnArtuments2.size());
            assertSame(t, getColumnArtuments2.get(0));
            assertEquals(1, getColumnArtuments2.get(1));
            List getColumnArtuments3 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 2);
            assertEquals(2, getColumnArtuments3.size());
            assertSame(t, getColumnArtuments3.get(0));
            assertEquals(2, getColumnArtuments3.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "\"testPrintDataLine08_column1\","
                    + "\"testPrintDataLine08_column2\",\"testPrintDataLine08_column3\""
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine09.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F'|'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.writeTrailer:false<br>
     * (���) this.currentLineCount:0<br>
     * (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) #getColumn():�ُ�I��<br>
     * �E�ȉ��̗�O��Ԃ��B<br>
     * - ArrayIndexOutOfBoundException<br>
     * (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     * (��ԕω�) #getColumn():�Ă΂�Ȃ�<br>
     * (��ԕω�) #getWriter().write():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) #setWriteData():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�F�s��؂蕶���P�̂�<br>
     * <br>
     * ����P�[�X<br>
     * (�o�͑Ώۃt�B�[���h�F�Ȃ��A�͂ݕ����F�f�t�H���g�l�A<br>
     * ��؂蕶���FCharacter.MIN_VALUE)<br>
     * �o�͑Ώۂ̃t�B�[���h���Ȃ��ꍇ�A�s��؂蕶���̂݃t�@�C���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine09() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub29> clazz = AbstractFileLineWriter_Stub29.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub29> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub29>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub29 t = new AbstractFileLineWriter_Stub29();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineWriter.class,
                    "getColumn", new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "getColumn"));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine10.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F'|'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine10_column1"<br>
     *                         - 2��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"testPrintDataLine10_column1<�s��؂蕶��>"<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F1�A�͂ݕ����F�f�t�H���g�l�A<br>
     *                         ��؂蕶���FCharacter.MIN_VALUE)<br>
     *                         �o�͑Ώۂ̃t�B�[���h���P����ꍇ�A�o�͑Ώۃt�B�[���h�̏�񂪏o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine10() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub30> clazz = AbstractFileLineWriter_Stub30.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub30> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub30>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub30 t = new AbstractFileLineWriter_Stub30();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine10_column1");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    1, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtument = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtument.size());
            assertSame(t, getColumnArtument.get(0));
            assertEquals(0, getColumnArtument.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "testPrintDataLine10_column1"
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine11.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�F'|'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine11_column1"<br>
     *                         - 2��ځF"testPrintDataLine11_column2"<br>
     *                         - 3��ځF"testPrintDataLine11_column3"<br>
     *                         - 4��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():3��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"testPrintDataLine11_column1|testPrintDataLine11_column2|testPrintDataLine11_column3<�s��؂蕶��>"<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F3�A�͂ݕ����F�f�t�H���g�l�A<br>
     *                         ��؂蕶���FCharacter.MIN_VALUE)<br>
     *                         �o�͑Ώۂ̃t�B�[���h��3����ꍇ�A�S�o�͑Ώۃt�B�[���h�̏��Ƃ��̊Ԃɋ�؂蕶�����o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine11() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub31> clazz = AbstractFileLineWriter_Stub31.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub31> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub31>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub31 t = new AbstractFileLineWriter_Stub31();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine11_column1");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 1, "testPrintDataLine11_column2");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 2, "testPrintDataLine11_column3");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    3, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(3, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtuments1 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtuments1.size());
            assertSame(t, getColumnArtuments1.get(0));
            assertEquals(0, getColumnArtuments1.get(1));
            List getColumnArtuments2 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 1);
            assertEquals(2, getColumnArtuments2.size());
            assertSame(t, getColumnArtuments2.get(0));
            assertEquals(1, getColumnArtuments2.get(1));
            List getColumnArtuments3 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 2);
            assertEquals(2, getColumnArtuments3.size());
            assertSame(t, getColumnArtuments3.get(0));
            assertEquals(2, getColumnArtuments3.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "testPrintDataLine11_column1|"
                    + "testPrintDataLine11_column2|testPrintDataLine11_column3"
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine12() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine12.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():null<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine12_column1"<br>
     *                         - 2��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:0<br>
     *                         (��ԕω�) #getColumn():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���ω��Ȃ�<br>
     *                         (��ԕω�) ��O:NullPointerException����������<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         getWriter()�̌��ʂ�null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine12() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;

        // �O����(����)
        AbstractFileLineWriter_Stub10 t = new AbstractFileLineWriter_Stub10();

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = null;

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine12_column1");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    1, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����(��O)
            assertTrue(NullPointerException.class
                    .isAssignableFrom(e.getClass()));

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(0, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtument = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtument.size());
            assertSame(t, getColumnArtument.get(0));
            assertEquals(0, getColumnArtument.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "testPrintDataLine12_column1"
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "setWriteData"));

            // ����(�t�@�C��)
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine13() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine13.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�FCharacter.MIN_VALUE<br>
     * - encloseChar�F'\"'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.writeTrailer:false<br>
     * (���) this.currentLineCount:0<br>
     * (���) #getWriter():not null<br>
     * (���) #getColumn():�ُ�I��<br>
     * �E�ȉ��̗�O��Ԃ��B<br>
     * - ArrayIndexOutOfBoundException<br>
     * (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     * (��ԕω�) #getColumn():�Ă΂�Ȃ�<br>
     * (��ԕω�) #getWriter().write():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) #setWriteData():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�F�s��؂蕶���P�̂�<br>
     * <br>
     * ����P�[�X<br>
     * (�o�͑Ώۃt�B�[���h�F�Ȃ��A�͂ݕ����F�f�t�H���g�l�ȊO�A<br>
     * ��؂蕶���FCharacter.MIN_VALUE)<br>
     * �o�͑Ώۂ̃t�B�[���h���Ȃ��ꍇ�A�s��؂蕶���̂݃t�@�C���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine13() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub32> clazz = AbstractFileLineWriter_Stub32.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub32> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub32>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub32 t = new AbstractFileLineWriter_Stub32();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineWriter.class,
                    "getColumn", new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "getColumn"));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine14() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine14.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�FCharacter.MIN_VALUE<br>
     * - encloseChar�F'\"'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine14_column1"<br>
     *                         - 2��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"\"testPrintDataLine14_column1\"<�s��؂蕶��>"<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F1�A��؂蕶���F�f�t�H���g�l�A<br>
     *                         �͂ݕ����F�f�t�H���g�l�ȊO)<br>
     *                         �o�͑Ώۂ̃t�B�[���h���P����ꍇ�A�͂ݕ����Ɉ͂܂ꂽ�o�͑Ώۃt�B�[���h�̏��̂ݏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine14() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub33> clazz = AbstractFileLineWriter_Stub33.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub33> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub33>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub33 t = new AbstractFileLineWriter_Stub33();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine14_column1");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    1, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtument = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtument.size());
            assertSame(t, getColumnArtument.get(0));
            assertEquals(0, getColumnArtument.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "\"testPrintDataLine14_column1\""
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine15() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine15.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - delimiter�FCharacter.MIN_VALUE<br>
     * - encloseChar�F'\"'<br>
     * - ���̑��F�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine15_column1"<br>
     *                         - 2��ځF"testPrintDataLine15_column2"<br>
     *                         - 3��ځF"testPrintDataLine15_column3"<br>
     *                         - 4��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) #getEncloseChar():this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():3��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"\"testPrintDataLine15_column1\"\"testPrintDataLine15_column2\"\"testPrintDataLine15_column3\"<�s��؂蕶��>"
     * <br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F3�A��؂蕶���F�f�t�H���g�l�A<br>
     *                         �͂ݕ����F�f�t�H���g�l�ȊO)<br>
     *                         �o�͑Ώۂ̃t�B�[���h��3����ꍇ�A�͂ݕ����Ɉ͂܂ꂽ�S�o�͑Ώۃt�B�[���h�̏�񂪏o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine15() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub34> clazz = AbstractFileLineWriter_Stub34.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub34> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub34>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub34 t = new AbstractFileLineWriter_Stub34();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine15_column1");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 1, "testPrintDataLine15_column2");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 2, "testPrintDataLine15_column3");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    3, new ArrayIndexOutOfBoundsException("�킴��"));

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(3, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtuments1 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtuments1.size());
            assertSame(t, getColumnArtuments1.get(0));
            assertEquals(0, getColumnArtuments1.get(1));
            List getColumnArtuments2 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 1);
            assertEquals(2, getColumnArtuments2.size());
            assertSame(t, getColumnArtuments2.get(0));
            assertEquals(1, getColumnArtuments2.get(1));
            List getColumnArtuments3 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 2);
            assertEquals(2, getColumnArtuments3.size());
            assertSame(t, getColumnArtuments3.get(0));
            assertEquals(2, getColumnArtuments3.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "\"testPrintDataLine15_column1\""
                    + Character.MIN_VALUE + "\"testPrintDataLine15_column2\""
                    + Character.MIN_VALUE + "\"testPrintDataLine15_column3\""
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine16() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine16.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E�t�B�[���h�������ĂȂ�<br>
     * (���) this.writeTrailer:false<br>
     * (���) this.currentLineCount:0<br>
     * (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) #getColumn():�ُ�I��<br>
     * �E�ȉ��̗�O��Ԃ��B<br>
     * - ArrayIndexOutOfBoundException<br>
     * (���) #getDelimiter():Character.MIN_VALUE��Ԃ�<br>
     * (���) #getEncloseChar():Character.MIN_VALUE��Ԃ�<br>
     * (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     * (��ԕω�) #getColumn():�Ă΂�Ȃ�<br>
     * (��ԕω�) #getWriter().write():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) #setWriteData():1��Ă΂��<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     * �E���e�F�s��؂蕶���P�̂�<br>
     * <br>
     * ����P�[�X<br>
     * (�o�͑Ώۃt�B�[���h�F�Ȃ��A<br>
     * �͂ݕ����FCharacter.MIN_VALUE�A<br>
     * ��؂蕶���FCharacter.MIN_VALUE)<br>
     * �o�͑Ώۂ̃t�B�[���h���Ȃ��ꍇ�A�s��؂蕶���̂݃t�@�C���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine16() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub08> clazz = AbstractFileLineWriter_Stub08.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub08> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub08>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub08 t = new AbstractFileLineWriter_Stub08();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setExceptionAtAllTimes(AbstractFileLineWriter.class,
                    "getColumn", new ArrayIndexOutOfBoundsException("�킴��"));

            VMOUTUtil.setReturnValueAtAllTimes(
                    AbstractFileLineWriterImpl01.class, "getDelimiter",
                    Character.MIN_VALUE);

            VMOUTUtil.setReturnValueAtAllTimes(
                    AbstractFileLineWriterImpl01.class, "getEncloseChar",
                    Character.MIN_VALUE);

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "getColumn"));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine17() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine17.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine10_column1"<br>
     *                         - 2��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():Character.MIN_VALUE��Ԃ�<br>
     *                         (���) #getEncloseChar():Character.MIN_VALUE��Ԃ�<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"testPrintDataLine17_column1<�s��؂蕶��>"<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F1�A<br>
     *                         �͂ݕ����FCharacter.MIN_VALUE�A<br>
     *                         ��؂蕶���FCharacter.MIN_VALUE)<br>
     *                         �o�͑Ώۂ̃t�B�[���h���P����ꍇ�A�o�͑Ώۃt�B�[���h�̏�񂪏o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine17() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub10 t = new AbstractFileLineWriter_Stub10();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine17_column1");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    1, new ArrayIndexOutOfBoundsException("�킴��"));

            VMOUTUtil.setReturnValueAtAllTimes(
                    AbstractFileLineWriterImpl01.class, "getDelimiter",
                    Character.MIN_VALUE);

            VMOUTUtil.setReturnValueAtAllTimes(
                    AbstractFileLineWriterImpl01.class, "getEncloseChar",
                    Character.MIN_VALUE);

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtument = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtument.size());
            assertSame(t, getColumnArtument.get(0));
            assertEquals(0, getColumnArtument.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");
            String expectationResultData = "testPrintDataLine17_column1"
                    + systemLineSeparator;
            assertEquals(expectationResultData, writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintDataLine18() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) t:this.clazz�̃C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintDataLine18.txt"<br>
     * (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.writeTrailer:false<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #getWriter():this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     *                         (���) #getColumn():����I��<br>
     *                         �E�ȉ��̌��ʂ�Ԃ��B<br>
     *                         - 1��ځF"testPrintDataLine11_column1"<br>
     *                         - 2��ځF"testPrintDataLine11_column2"<br>
     *                         - 3��ځF"testPrintDataLine11_column3"<br>
     *                         - 4��ڈȌ�FArrayIndexOutOfBoundException<br>
     *                         (���) #getDelimiter():Character.MIN_VALUE��Ԃ�<br>
     *                         (���) #getEncloseChar():Character.MIN_VALUE��Ԃ�<br>
     *                         (���) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�͂Ȃ�(Obyte)<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) this.currentLineCount:1<br>
     *                         (��ԕω�) #getColumn():3��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #getWriter().write():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #setWriteData():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) �t�@�C��:�N���X�p�X��this.fileName�ɑ΂���t�@�C�������݂���B<br>
     *                         �E���e�F"testPrintDataLine18_column1testPrintDataLine18_column2testPrintDataLine18_column3<�s��؂蕶��>"<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�͑Ώۃt�B�[���h�F3�A<br>
     *                         �͂ݕ����FCharacter.MIN_VALUE�A<br>
     *                         ��؂蕶���FCharacter.MIN_VALUE)<br>
     *                         �o�͑Ώۂ̃t�B�[���h��3����ꍇ�A�S�o�͑Ώۃt�B�[���h�̏�񂪏o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintDataLine18() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub13> clazz = AbstractFileLineWriter_Stub13.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub13> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub13>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            AbstractFileLineWriter_Stub13 t = new AbstractFileLineWriter_Stub13();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "getWriter", writer);

            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 0, "testPrintDataLine18_column1");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 1, "testPrintDataLine18_column2");
            VMOUTUtil.setReturnValueAt(AbstractFileLineWriter.class,
                    "getColumn", 2, "testPrintDataLine18_column3");
            VMOUTUtil.setExceptionAt(AbstractFileLineWriter.class, "getColumn",
                    3, new ArrayIndexOutOfBoundsException("�킴��"));

            VMOUTUtil.setReturnValueAtAllTimes(
                    AbstractFileLineWriterImpl01.class, "getDelimiter",
                    Character.MIN_VALUE);

            VMOUTUtil.setReturnValueAtAllTimes(
                    AbstractFileLineWriterImpl01.class, "getEncloseChar",
                    Character.MIN_VALUE);

            // �e�X�g���{
            fileLineWriter.printDataLine(t);

            // ����(��ԕω��A�t�B�[���h)
            assertEquals(1, UTUtil.getPrivateField(fileLineWriter,
                    "currentLineCount"));

            // ����(��ԕω��A���\�b�h)
            assertEquals(3, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "getColumn"));
            List getColumnArtuments1 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 0);
            assertEquals(2, getColumnArtuments1.size());
            assertSame(t, getColumnArtuments1.get(0));
            assertEquals(0, getColumnArtuments1.get(1));
            List getColumnArtuments2 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 1);
            assertEquals(2, getColumnArtuments2.size());
            assertSame(t, getColumnArtuments2.get(0));
            assertEquals(1, getColumnArtuments2.get(1));
            List getColumnArtuments3 = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "getColumn", 2);
            assertEquals(2, getColumnArtuments3.size());
            assertSame(t, getColumnArtuments3.get(0));
            assertEquals(2, getColumnArtuments3.get(1));

            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals(1, writeArguments.size());
            String systemLineSeparator = System.getProperty("line.separator");

            String expectationResultData = null;

            // maven����N������ƂȂ������ʂ��قȂ邽�߁Amaven����N�����͑z��l��ύX����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                expectationResultData = "testPrintDataLine18_column1"
                        + "testPrintDataLine18_column2testPrintDataLine18_column3"
                        + systemLineSeparator;
            } else {
                expectationResultData = "testPrintDataLine18_column1,"
                        + "testPrintDataLine18_column2,testPrintDataLine18_column3"
                        + systemLineSeparator;
            }

            // System.out.println("========");
            // System.out.println(expectationResultData);
            // System.out.println("----");
            // System.out.println(writeArguments.get(0));
            // System.out.println("========");
            assertEquals("writeArguments.get(0)", expectationResultData,
                    writeArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "setWriteData"));
            List setWriteDataArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "setWriteData", 0);
            assertEquals(1, setWriteDataArguments.size());
            assertTrue(Boolean.class.cast(setWriteDataArguments.get(0)));

            // ����(�t�@�C��)
            writer.flush();
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintTrailerLine01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) trailerLine:�v�f�������Ȃ�List�C���X�^���X<br>
     * (���) writeTailer:false<br>
     * <br>
     * ���Ғl�F(��ԕω�) writeTrailer:true<br>
     * (��ԕω�) #printList():1��Ă΂��<br>
     * �������m�F����B<br>
     * <br>
     * ����P�[�X<br>
     * �Ώۃf�[�^�̏o�͏������s���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintTrailerLine01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = TEMP_FILE_NAME;

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> trailerLine = new ArrayList<String>();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "writeTrailer",
                    Boolean.FALSE);

            // �e�X�g���{
            fileLineWriter.printTrailerLine(trailerLine);

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "printList"));
            List printListArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "printList", 0);
            assertSame(trailerLine, printListArguments.get(0));
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");
            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintList01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) stringList:�ȉ��̗v�f������List�C���X�^���X<br>
     * �E"testPrintList01_data1"<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintList01.txt"<br>
     * (���) this.lineFeedChar:�V�X�e���f�t�H���g�l<br>
     * (���) this.writer:this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * ���ɃN���[�Y����Ă���B<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList01.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) writer#write():1��Ă΂��B<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList01.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���ω��Ȃ�<br>
     * (��ԕω�) -:�ȉ��̏�������FileException����������<br>
     * �E���b�Z�[�W�F"Processing of writer was failed."<br>
     * �E������O�FIOException<br>
     * �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     * <br>
     * �ُ�P�[�X<br>
     * writer�����ɃN���[�Y����Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintList01() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> stringList = new ArrayList<String>();
            stringList.add("testPrintList01_data1");

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "lineFeedChar", System
                    .getProperty("line.separator"));

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "printList",
                    new Class[] { List.class }, new Object[] { stringList });
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Processing of writer was failed.", e.getMessage());
            assertTrue(IOException.class.isAssignableFrom(e.getCause()
                    .getClass()));
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments = VMOUTUtil.getArguments(Writer.class, "write",
                    0);
            assertEquals("testPrintList01_data1", writeArguments.get(0));

            // ����(�t�@�C��)
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintList02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(����) stringList:�v�f�������Ȃ�List�C���X�^���X<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintList02.txt"<br>
     * (���) this.lineFeedChar:�V�X�e���f�t�H���g�l<br>
     * (���) this.writer:this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList02.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) writer#write():�Ă΂�Ȃ��B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList02.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���ω��Ȃ�<br>
     * <br>
     * ����P�[�X�B<br>
     * �o�͑Ώۃf�[�^���Ȃ��ꍇ�A�����o�͂���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintList02() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> stringList = new ArrayList<String>();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "lineFeedChar", System
                    .getProperty("line.separator"));

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "printList",
                    new Class[] { List.class }, new Object[] { stringList });

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(Writer.class, "write"));

            // ����(�t�@�C��)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.flush();

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintList03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(����) stringList:�ȉ��̗v�f������List�C���X�^���X<br>
     * �E"testPrintList03_data1"<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintList03.txt"<br>
     * (���) this.lineFeedChar:�V�X�e���f�t�H���g�l<br>
     * (���) this.writer:this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList03.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) writer#write():2��Ă΂��B<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList03.txt"<br>
     * �E���e�F"testPrintList03_data1<�s��؂蕶��>"<br>
     * <br>
     * ����P�[�X�B<br>
     * �o�͑Ώۃf�[�^���P����ꍇ�A���̃f�[�^�ƍs��؂蕶�����o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintList03() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> stringList = new ArrayList<String>();
            stringList.add("testPrintList03_data1");

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "lineFeedChar", System
                    .getProperty("line.separator"));

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "printList",
                    new Class[] { List.class }, new Object[] { stringList });

            // ����(��ԕω��A���\�b�h)
            assertEquals(2, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments1 = VMOUTUtil.getArguments(Writer.class,
                    "write", 0);
            assertEquals(1, writeArguments1.size());
            assertEquals("testPrintList03_data1", writeArguments1.get(0));
            List writeArguments2 = VMOUTUtil.getArguments(Writer.class,
                    "write", 1);
            assertEquals(1, writeArguments2.size());
            String systemLineSeparator = System.getProperty("line.separator");
            assertEquals(systemLineSeparator, writeArguments2.get(0));

            // ����(�t�@�C��)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.flush();

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String expectationResultData = "testPrintList03_data1"
                    + systemLineSeparator;
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintList04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(����) stringList:�ȉ��̗v�f������List�C���X�^���X<br>
     * �E"testPrintList04_data1"<br>
     * �E"testPrintList04_data2"<br>
     * �E"testPrintList04_data3"<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintList04.txt"<br>
     * (���) this.lineFeedChar:�V�X�e���f�t�H���g�l<br>
     * (���) this.writer:this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList04.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) writer#write():6��Ă΂��B<br>
     * �������m�F����B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList03.txt"<br>
     * �E���e�F"testPrintList04_data1<�s��؂蕶��>testPrintList04_data2<�s��؂蕶��>testPrintList04_data3<�s��؂蕶��>"<br>
     * <br>
     * ����P�[�X�B<br>
     * �o�͑Ώۃf�[�^��3����ꍇ�A���̃f�[�^�ƍs��؂蕶�����o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintList04() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> stringList = new ArrayList<String>();
            stringList.add("testPrintList04_data1");
            stringList.add("testPrintList04_data2");
            stringList.add("testPrintList04_data3");

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "lineFeedChar", System
                    .getProperty("line.separator"));

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "printList",
                    new Class[] { List.class }, new Object[] { stringList });

            // ����(��ԕω��A���\�b�h)
            assertEquals(6, VMOUTUtil.getCallCount(Writer.class, "write"));
            List writeArguments1 = VMOUTUtil.getArguments(Writer.class,
                    "write", 0);
            assertEquals(1, writeArguments1.size());
            assertEquals("testPrintList04_data1", writeArguments1.get(0));
            List writeArguments2 = VMOUTUtil.getArguments(Writer.class,
                    "write", 1);
            assertEquals(1, writeArguments2.size());
            String systemLineSeparator = System.getProperty("line.separator");
            assertEquals(systemLineSeparator, writeArguments2.get(0));
            List writeArguments3 = VMOUTUtil.getArguments(Writer.class,
                    "write", 2);
            assertEquals(1, writeArguments3.size());
            assertEquals("testPrintList04_data2", writeArguments3.get(0));
            List writeArguments4 = VMOUTUtil.getArguments(Writer.class,
                    "write", 3);
            assertEquals(1, writeArguments4.size());
            assertEquals(systemLineSeparator, writeArguments4.get(0));
            List writeArguments5 = VMOUTUtil.getArguments(Writer.class,
                    "write", 4);
            assertEquals(1, writeArguments5.size());
            assertEquals("testPrintList04_data3", writeArguments5.get(0));
            List writeArguments6 = VMOUTUtil.getArguments(Writer.class,
                    "write", 5);
            assertEquals(1, writeArguments6.size());
            assertEquals(systemLineSeparator, writeArguments6.get(0));

            // ����(�t�@�C��)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.flush();

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String expectationResultData = "testPrintList04_data1"
                    + systemLineSeparator + "testPrintList04_data2"
                    + systemLineSeparator + "testPrintList04_data3"
                    + systemLineSeparator;
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testPrintList05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) stringList:null<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testPrintList05.txt"<br>
     * (���) this.lineFeedChar:�V�X�e���f�t�H���g�l<br>
     * (���) this.writer:this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList05.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testPrintList02.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���ω��Ȃ�<br>
     * (��ԕω�) -:NullPointerException����������<br>
     * <br>
     * �ُ�P�[�X<br>
     * ������null�̏ꍇ�͗�O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPrintList05() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(����)
            List<String> stringList = null;

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "lineFeedChar", System
                    .getProperty("line.separator"));

            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �e�X�g���{
            UTUtil.invokePrivate(fileLineWriter, "printList",
                    new Class[] { List.class }, new Object[] { stringList });
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����(��O)
            assertTrue(NullPointerException.class
                    .isAssignableFrom(e.getClass()));

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(Writer.class, "write"));

            // ����(�t�@�C��)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.flush();

            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testCloseFile01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testCloseFile01.txt"<br>
     * (���) this.writer:this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * ���ɃN���[�Y����Ă���B<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testCloseFile01.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) writer#flush():1��Ă΂��B<br>
     * (��ԕω�) writer#close():�Ă΂�Ȃ��B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testCloseFile01.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���ω��Ȃ�<br>
     * (��ԕω�) FileException:�ȉ��̏�������FileException����������<br>
     * �E���b�Z�[�W�F"Closing of writer was failed."<br>
     * �E������O�FIOException<br>
     * �E�t�@�C�����FfileName�Ɠ����C���X�^���X<br>
     * <br>
     * �ُ�P�[�X<br>
     * writer�����ɃN���[�Y����Ă���ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCloseFile01() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.close();

            // �e�X�g���{
            fileLineWriter.closeFile();
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Closing of writer was failed.", e.getMessage());
            assertTrue(IOException.class.isAssignableFrom(e.getCause()
                    .getClass()));
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "flush"));

            assertFalse(VMOUTUtil.isCalled(Writer.class, "write"));

            // ����(�t�@�C��)
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertFalse(postReader.ready());
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testCloseFile02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testCloseFile02.txt"<br>
     * (���) this.writer:this.fileName�t�@�C���ɑ΂���Writer�C���X�^���X<br>
     * �o�̓f�[�^�Ƃ��Ĉȉ��̃f�[�^�������Ă���B<br>
     * �E"testCloseFile02_data1"<br>
     * (���) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testCloseFile02.txt"<br>
     * �E���e�Ȃ�(0Byte)<br>
     * <br>
     * ���Ғl�F(��ԕω�) writer#flush():1��Ă΂��B<br>
     * (��ԕω�) writer#close():1��Ă΂��B<br>
     * (��ԕω�) �t�@�C��:�N���X�p�X�Ɉȉ��̃t�@�C�������݂���B<br>
     * "AbstractFileLineWriter_testCloseFile02.txt"<br>
     * �E���e�F"testCloseFile02_data1"<br>
     * <br>
     * ����P�[�X<br>
     * writer�ɐݒ肳�ꂽ�S�Ă̏�񂪃t�@�C���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCloseFile02() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub10> clazz = AbstractFileLineWriter_Stub10.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub10> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub10>(
                fileName, clazz, columnFormatterMap);

        Reader postReader = null;
        Writer writer = null;
        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            writer = (Writer) UTUtil.getPrivateField(fileLineWriter, "writer");
            writer.write("testCloseFile02_data1");

            // �e�X�g���{
            fileLineWriter.closeFile();

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil.getCallCount(Writer.class, "flush"));

            assertFalse(VMOUTUtil.isCalled(Writer.class, "write"));

            // ����(�t�@�C��)
            postReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), System
                            .getProperty("file.encoding")));
            assertTrue(postReader.ready());

            String expectationResultData = "testCloseFile02_data1";
            String data = "";
            for (int i = 0; i < expectationResultData.length(); i++) {
                assertTrue(i + "��ڂ̔���Ŏ��s���܂����B", postReader.ready());
                data += (char) postReader.read();
            }
            assertEquals(expectationResultData, data);
            assertFalse(postReader.ready());

        } finally {
            if (writer != null) {
                writer.close();
            }

            if (postReader != null) {
                postReader.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn01_data1"<br>
     * �Ecolumn2�F"testGetColumn01_data2"<br>
     * �Ecolumn3�F"testGetColumn01_data3"<br>
     * (����) index:1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn01.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():false<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():�ُ�I��<br>
     *                         IllegalAccessException����������B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) FileDAOUtility#trim():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) FileDAOUtility#padding():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) StringConverter#convert():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) #isCheckByte():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) -:�ȉ��̏�������FileLineException����������<br>
     *                         �E���b�Z�[�W�F"Failed in column data formatting."<br>
     *                         �E������O�FIllegalAccessException<br>
     *                         �E�t�@�C�����Fthis.fileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F1<br>
     *                         �E�J�������Fcolumn2<br>
     *                         �E�J�����ԍ�:1<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         �t�@�C���s�I�u�W�F�N�g����f�[�^���擾���鏈����IlleageAccessException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn01() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn01_data1");
        t.setColumn2("testGetColumn01_data2");
        t.setColumn3("testGetColumn01_data3");

        int index = 1;
        IllegalAccessException illegalAccessException = new IllegalAccessException(
                "testGetColumn01��O");
        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.FALSE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn01_data2_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn01_data2_trim()_padding()");

            VMOUTUtil.setExceptionAtAllTimes(NullColumnFormatter.class,
                    "format", illegalAccessException);

            // �e�X�g���{
            fileLineWriter.getColumn(t, index);
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals("Failed in column data formatting.", e.getMessage());
            assertSame(illegalAccessException, e.getCause());
            assertEquals(fileName, e.getFileName());
            assertEquals(1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals(1, e.getColumnIndex());

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(FileDAOUtility.class, "trim"));

            assertFalse(VMOUTUtil.isCalled(FileDAOUtility.class, "padding"));

            assertFalse(VMOUTUtil.isCalled(
                    AbstractFileLineWriter_StringConverterStub03.class,
                    "convert"));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "isCheckByte"));
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn02_data1"<br>
     * �Ecolumn2�F"testGetColumn02_data2"<br>
     * �Ecolumn3�F"testGetColumn02_data3"<br>
     * (����) index:1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn02.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():false<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():�ُ�I��<br>
     *                         llegalArgumentException����������B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) FileDAOUtility#trim():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) FileDAOUtility#padding():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) StringConverter#convert():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) #isCheckByte():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) -:�ȉ��̏�������FileLineException����������<br>
     *                         �E���b�Z�[�W�F"Failed in column data formatting."<br>
     *                         �E������O�FIllegalArgumentException<br>
     *                         �E�t�@�C�����Fthis.fileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F1<br>
     *                         �E�J�������Fcolumn2<br>
     *                         �E�J�����ԍ�:1<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         �t�@�C���s�I�u�W�F�N�g����f�[�^���擾���鏈����illegalArgumentException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn02() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn02_data1");
        t.setColumn2("testGetColumn02_data2");
        t.setColumn3("testGetColumn02_data3");

        int index = 1;
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(
                "testGetColumn02��O");
        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.FALSE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn02_data2_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn02_data2_trim()_padding()");

            VMOUTUtil.setExceptionAtAllTimes(NullColumnFormatter.class,
                    "format", illegalArgumentException);

            // �e�X�g���{
            fileLineWriter.getColumn(t, index);
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals("Failed in column data formatting.", e.getMessage());
            assertSame(illegalArgumentException, e.getCause());
            assertEquals(fileName, e.getFileName());
            assertEquals(1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals(1, e.getColumnIndex());

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(FileDAOUtility.class, "trim"));

            assertFalse(VMOUTUtil.isCalled(FileDAOUtility.class, "padding"));

            assertFalse(VMOUTUtil.isCalled(
                    AbstractFileLineWriter_StringConverterStub03.class,
                    "convert"));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "isCheckByte"));
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn03_data1"<br>
     * �Ecolumn2�F"testGetColumn03_data2"<br>
     * �Ecolumn3�F"testGetColumn03_data3"<br>
     * (����) index:1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn03.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():false<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():�ُ�I��<br>
     *                         InvocationTargetException����������B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) FileDAOUtility#trim():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) FileDAOUtility#padding():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) StringConverter#convert():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) #isCheckByte():�Ă΂�Ȃ�<br>
     *                         (��ԕω�) -:�ȉ��̏�������FileLineException����������<br>
     *                         �E���b�Z�[�W�F"Failed in column data formatting."<br>
     *                         �E������O�FInvocationTargetException<br>
     *                         �E�t�@�C�����Fthis.fileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F1<br>
     *                         �E�J�������Fcolumn2<br>
     *                         �E�J�����ԍ�:1<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         �t�@�C���s�I�u�W�F�N�g����f�[�^���擾���鏈����InvocationTargetException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn03() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn03_data1");
        t.setColumn2("testGetColumn03_data2");
        t.setColumn3("testGetColumn03_data3");

        int index = 1;
        InvocationTargetException invocationTargetException = new InvocationTargetException(
                new Exception("testGetColumn03��O"));
        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.FALSE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn03_data2_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn03_data2_trim()_padding()");

            VMOUTUtil.setExceptionAtAllTimes(NullColumnFormatter.class,
                    "format", invocationTargetException);

            // �e�X�g���{
            fileLineWriter.getColumn(t, index);
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals("Failed in column data formatting.", e.getMessage());
            assertSame(invocationTargetException, e.getCause());
            assertEquals(fileName, e.getFileName());
            assertEquals(1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals(1, e.getColumnIndex());

            // ����(��ԕω��A���\�b�h)
            assertFalse(VMOUTUtil.isCalled(FileDAOUtility.class, "trim"));

            assertFalse(VMOUTUtil.isCalled(FileDAOUtility.class, "padding"));

            assertFalse(VMOUTUtil.isCalled(
                    AbstractFileLineWriter_StringConverterStub03.class,
                    "convert"));

            assertFalse(VMOUTUtil.isCalled(AbstractFileLineWriter.class,
                    "isCheckByte"));
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn04_data1"<br>
     * �Ecolumn2�F"testGetColumn04_data2XX"(bytes�ݒ�ƍ���Ȃ��f�[�^)<br>
     * �Ecolumn3�F"testGetColumn04_data3"<br>
     * (����) index:1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn04.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():true<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():����I��<br>
     *                         �������t�B�[���h�̏���Ԃ��B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) FileDAOUtility#trim():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) FileDAOUtility#padding():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) StringConverter#convert():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #isCheckByte():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) -:�ȉ��̏�������FileLineException����������<br>
     *                         �E���b�Z�[�W�F"The data size is different from bytes value of the set value of the column ."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �E�t�@�C�����Fthis.fileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F1<br>
     *                         �E�J�������Fcolumn2<br>
     *                         �E�J�����ԍ�:1<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�o�C�g���`�F�b�N����)<br>
     *                         �擾�Ώۃt�B�[���h�l�̃o�C�g�����A�m�e�[�V�����̐ݒ�l�ƈقȂ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn04() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn04_data1");
        t.setColumn2("testGetColumn04_data2XX");
        t.setColumn3("testGetColumn04_data3");

        int index = 1;

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.TRUE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn04_data2XX_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn04_data2XX_trim()_padding()");

            // �e�X�g���{
            fileLineWriter.getColumn(t, index);
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals("The data size is different from bytes value of the "
                    + "set value of the column .", e.getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());
            assertEquals("getLineNo", 1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals("getColumnIndex", 1, e.getColumnIndex());

            // ����(��ԕω��A���\�b�h)
            assertEquals("FileDAOUtility", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "trim"));
            List trimArguments = VMOUTUtil.getArguments(FileDAOUtility.class,
                    "trim", 0);
            assertEquals(4, trimArguments.size());
            assertEquals("testGetColumn04_data2XX", trimArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), trimArguments
                    .get(1));
            assertEquals(' ', trimArguments.get(2));
            assertEquals(TrimType.NONE, trimArguments.get(3));

            assertEquals("FileDAOUtility", 1, VMOUTUtil.getCallCount(
                    FileDAOUtility.class, "padding"));
            List paddingArguments = VMOUTUtil.getArguments(
                    FileDAOUtility.class, "padding", 0);
            assertEquals(5, paddingArguments.size());
            assertEquals("testGetColumn04_data2XX_trim()", paddingArguments
                    .get(0));
            assertEquals(System.getProperty("file.encoding"), paddingArguments
                    .get(1));
            assertEquals(48, paddingArguments.get(2));
            assertEquals(' ', paddingArguments.get(3));
            assertEquals(PaddingType.NONE, paddingArguments.get(4));

            // maven����N������ƂȂ���convert���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals(
                        "AbstractFileLineWriter_StringConverterStub03",
                        1,
                        VMOUTUtil
                                .getCallCount(
                                        AbstractFileLineWriter_StringConverterStub03.class,
                                        "convert"));
                List convertArguments = VMOUTUtil.getArguments(
                        AbstractFileLineWriter_StringConverterStub03.class,
                        "convert", 0);
                assertEquals("convertArguments", 1, convertArguments.size());
                assertEquals("testGetColumn04_data2XX_trim()_padding()",
                        convertArguments.get(0));
            }

            assertEquals("isCheckByte", 1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "isCheckByte"));
            List isCheckByteArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "isCheckByte", 0);
            assertEquals("isCheckByteArguments", 1, isCheckByteArguments.size());

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn05_data1"<br>
     * �Ecolumn2�F"testGetColumn05_data2"<br>
     * �Ecolumn3�F"testGetColumn05_data3"<br>
     * (����) index:1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn05.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():true<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():����I��<br>
     *                         �������t�B�[���h�̏���Ԃ��B<br>
     *                         (���) String#getBytes():�ُ�I��<br>
     *                         UnsupportedEncodingException����������B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) FileDAOUtility#trim():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) FileDAOUtility#padding():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) StringConverter#convert():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #isCheckByte():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) -:�ȉ��̏�������FileException����������<br>
     *                         �E���b�Z�[�W�F"fileEncoding which isn't supported was set."<br>
     *                         �E������O�FUnsupportedEncodingException<br>
     *                         �E�t�@�C�����Fthis.fileName�Ɠ����C���X�^���X<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�o�C�g���`�F�b�N����)<br>
     *                         �擾�Ώۃt�B�[���h�l�̃o�C�g���`�F�b�N��UnsupportedEncodingException�����������ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn05() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn05_data1");
        t.setColumn2("testGetColumn05_data2");
        t.setColumn3("testGetColumn05_data3");

        int index = 1;

        UnsupportedEncodingException unsupportedEncodingException = new UnsupportedEncodingException(
                "testGetColumn05��O");

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.TRUE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn05_data2_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn05_data2_trim()_padding()");

            VMOUTUtil.setExceptionAt(String.class, "getBytes", 0,
                    unsupportedEncodingException);

            // �e�X�g���{
            fileLineWriter.getColumn(t, index);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("fileEncoding which isn't supported was set.", e
                    .getMessage());
            assertSame(unsupportedEncodingException, e.getCause());
            assertEquals(fileName, e.getFileName());

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            List trimArguments = VMOUTUtil.getArguments(FileDAOUtility.class,
                    "trim", 0);
            assertEquals(4, trimArguments.size());
            assertEquals("testGetColumn05_data2", trimArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), trimArguments
                    .get(1));
            assertEquals(' ', trimArguments.get(2));
            assertEquals(TrimType.NONE, trimArguments.get(3));

            assertEquals(1, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            List paddingArguments = VMOUTUtil.getArguments(
                    FileDAOUtility.class, "padding", 0);
            assertEquals(5, paddingArguments.size());
            assertEquals("testGetColumn05_data2_trim()", paddingArguments
                    .get(0));
            assertEquals(System.getProperty("file.encoding"), paddingArguments
                    .get(1));
            assertEquals(48, paddingArguments.get(2));
            assertEquals(' ', paddingArguments.get(3));
            assertEquals(PaddingType.NONE, paddingArguments.get(4));

            // maven����N������ƂȂ���convert���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals(
                        "AbstractFileLineWriter_StringConverterStub03",
                        1,
                        VMOUTUtil
                                .getCallCount(
                                        AbstractFileLineWriter_StringConverterStub03.class,
                                        "convert"));
                List convertArguments = VMOUTUtil.getArguments(
                        AbstractFileLineWriter_StringConverterStub03.class,
                        "convert", 0);
                assertEquals(1, convertArguments.size());
                assertEquals("testGetColumn05_data2_trim()_padding()",
                        convertArguments.get(0));
            }

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "isCheckByte"));
            List isCheckByteArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "isCheckByte", 0);
            assertEquals(1, isCheckByteArguments.size());

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn06_data1"<br>
     * �Ecolumn2�Fnull<br>
     * �Ecolumn3�F"testGetColumn06_data3"<br>
     * (����) index:1<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn06.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=�ȉ��̏���������ColumnFormatter�̃N���X�C���X�^���X<br>
     *                         - �K��null��Ԃ��B<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():false<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():����I��<br>
     *                         null��Ԃ��B<br>
     * <br>
     *                         ���Ғl�F(�߂�l) String:"_trim()_padding()_convert()"<br>
     *                         (��ԕω�) FileDAOUtility#trim():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) FileDAOUtility#padding():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) StringConverter#convert():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #isCheckByte():1��Ă΂��<br>
     *                         �������m�F����B<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�C�g���`�F�b�N�Ȃ�)<br>
     *                         ColumnFormmater#format()�̌��ʁA�t�B�[���h�l��null�̏ꍇ�A�󕶎��Ƃ��ď�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn06() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String",
                new AbstractFileLineWriter_ColumnFormatterStub01());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn06_data1");
        t.setColumn2(null);
        t.setColumn3("testGetColumn06_data3");

        int index = 1;

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.FALSE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "_trim()_padding()");

            // �e�X�g���{
            String result = fileLineWriter.getColumn(t, index);

            // ����(�߂�l)
            assertNotNull(result);
            assertEquals("_trim()_padding()_convert()", result);

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            List trimArguments = VMOUTUtil.getArguments(FileDAOUtility.class,
                    "trim", 0);
            assertEquals(4, trimArguments.size());
            assertEquals("", trimArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), trimArguments
                    .get(1));
            assertEquals(' ', trimArguments.get(2));
            assertEquals(TrimType.NONE, trimArguments.get(3));

            assertEquals(1, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            List paddingArguments = VMOUTUtil.getArguments(
                    FileDAOUtility.class, "padding", 0);
            assertEquals(5, paddingArguments.size());
            assertEquals("_trim()", paddingArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), paddingArguments
                    .get(1));
            assertEquals(48, paddingArguments.get(2));
            assertEquals(' ', paddingArguments.get(3));
            assertEquals(PaddingType.NONE, paddingArguments.get(4));

            // maven����N������ƂȂ���convert���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals(
                        "AbstractFileLineWriter_StringConverterStub03",
                        1,
                        VMOUTUtil
                                .getCallCount(
                                        AbstractFileLineWriter_StringConverterStub03.class,
                                        "convert"));
                List convertArguments = VMOUTUtil.getArguments(
                        AbstractFileLineWriter_StringConverterStub03.class,
                        "convert", 0);
                assertEquals(1, convertArguments.size());
                assertEquals("_trim()_padding()", convertArguments.get(0));
            }

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "isCheckByte"));
            List isCheckByteArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "isCheckByte", 0);
            assertEquals(1, isCheckByteArguments.size());

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn07_data1"<br>
     * �Ecolumn2�F""(�󕶎�)<br>
     * �Ecolumn3�F"testGetColumn07_data3"<br>
     * (����) index:0<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn07.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():false<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():����I��<br>
     *                         �������t�B�[���h�̏���Ԃ��B<br>
     * <br>
     *                         ���Ғl�F(�߂�l) String:"_trim()_padding()_convert()"<br>
     *                         (��ԕω�) FileDAOUtility#trim():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) FileDAOUtility#padding():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) StringConverter#convert():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #isCheckByte():1��Ă΂��<br>
     *                         �������m�F����B<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�C�g���`�F�b�N�Ȃ�)<br>
     *                         ColumnFormmater#format()�̌��ʁA�t�B�[���h�l���󕶎��̏ꍇ�A���̂܂܏�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn07() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String",
                new AbstractFileLineWriter_ColumnFormatterStub01());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn07_data1");
        t.setColumn2("");
        t.setColumn3("testGetColumn07_data3");

        int index = 1;

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.FALSE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "_trim()_padding()");

            // �e�X�g���{
            String result = fileLineWriter.getColumn(t, index);

            // ����(�߂�l)
            assertNotNull(result);
            assertEquals("_trim()_padding()_convert()", result);

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            List trimArguments = VMOUTUtil.getArguments(FileDAOUtility.class,
                    "trim", 0);
            assertEquals(4, trimArguments.size());
            assertEquals("", trimArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), trimArguments
                    .get(1));
            assertEquals(' ', trimArguments.get(2));
            assertEquals(TrimType.NONE, trimArguments.get(3));

            assertEquals(1, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            List paddingArguments = VMOUTUtil.getArguments(
                    FileDAOUtility.class, "padding", 0);
            assertEquals(5, paddingArguments.size());
            assertEquals("_trim()", paddingArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), paddingArguments
                    .get(1));
            assertEquals(48, paddingArguments.get(2));
            assertEquals(' ', paddingArguments.get(3));
            assertEquals(PaddingType.NONE, paddingArguments.get(4));

            // maven����N������ƂȂ���convert���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals(
                        "AbstractFileLineWriter_StringConverterStub03",
                        1,
                        VMOUTUtil
                                .getCallCount(
                                        AbstractFileLineWriter_StringConverterStub03.class,
                                        "convert"));
                List convertArguments = VMOUTUtil.getArguments(
                        AbstractFileLineWriter_StringConverterStub03.class,
                        "convert", 0);
                assertEquals(1, convertArguments.size());
                assertEquals("_trim()_padding()", convertArguments.get(0));
            }

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "isCheckByte"));
            List isCheckByteArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "isCheckByte", 0);
            assertEquals(1, isCheckByteArguments.size());

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,F <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn09_data1"<br>
     * �Ecolumn2�F"testGetColumn09_data2"<br>
     * �Ecolumn3�F"testGetColumn09_data3"<br>
     * (����) index:0<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn09.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F48<br>
     *                         > stringConverter�F�ȉ��̏���������StringConverter�̃N���X�C���X�^���X<br>
     *                         - ���͂��ꂽ�f�[�^��"_convert()"��ǉ��������ʂ�Ԃ��B<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > bytes�F48<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():false<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():����I��<br>
     *                         �������t�B�[���h�̏���Ԃ��B<br>
     * <br>
     *                         ���Ғl�F(�߂�l) String:"testGetColumn09_data2_trim()_padding()_convert()"<br>
     *                         (��ԕω�) FileDAOUtility#trim():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) FileDAOUtility#padding():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) StringConverter#convert():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #isCheckByte():1��Ă΂��<br>
     *                         �������m�F����B<br>
     * <br>
     *                         ����P�[�X<br>
     *                         (�o�C�g���`�F�b�N����)<br>
     *                         �擾�Ώۃt�B�[���h�l�̃o�C�g�����A�m�e�[�V�����̐ݒ�l�ƈ�v�����ꍇ�A���Ȃ���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn09() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub35> clazz = AbstractFileLineWriter_Stub35.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub35> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub35>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub35 t = new AbstractFileLineWriter_Stub35();
        t.setColumn1("testGetColumn09_data1");
        t.setColumn2("testGetColumn09_data2");
        t.setColumn3("testGetColumn09_data3");

        int index = 1;

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.TRUE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn09_data2_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn09_data2_trim()_padding()");

            // �e�X�g���{
            String result = fileLineWriter.getColumn(t, index);

            // ����(�߂�l)
            assertNotNull(result);
            assertEquals("testGetColumn09_data2_trim()_padding()_convert()",
                    result);

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            List trimArguments = VMOUTUtil.getArguments(FileDAOUtility.class,
                    "trim", 0);
            assertEquals(4, trimArguments.size());
            assertEquals("testGetColumn09_data2", trimArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), trimArguments
                    .get(1));
            assertEquals(' ', trimArguments.get(2));
            assertEquals(TrimType.NONE, trimArguments.get(3));

            assertEquals(1, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            List paddingArguments = VMOUTUtil.getArguments(
                    FileDAOUtility.class, "padding", 0);
            assertEquals(5, paddingArguments.size());
            assertEquals("testGetColumn09_data2_trim()", paddingArguments
                    .get(0));
            assertEquals(System.getProperty("file.encoding"), paddingArguments
                    .get(1));
            assertEquals(48, paddingArguments.get(2));
            assertEquals(' ', paddingArguments.get(3));
            assertEquals(PaddingType.NONE, paddingArguments.get(4));

            // maven����N������ƂȂ���convert���擾�ł��Ȃ����߁A�X�L�b�v����
            if (!("jp.co.dgic.testing.common.DJUnitClassLoader".equals(System
                    .getProperty("java.system.class.loader")))) {
                assertEquals(
                        "AbstractFileLineWriter_StringConverterStub03",
                        1,
                        VMOUTUtil
                                .getCallCount(
                                        AbstractFileLineWriter_StringConverterStub03.class,
                                        "convert"));
                List convertArguments = VMOUTUtil.getArguments(
                        AbstractFileLineWriter_StringConverterStub03.class,
                        "convert", 0);
                assertEquals(1, convertArguments.size());
                assertEquals("testGetColumn09_data2_trim()_padding()",
                        convertArguments.get(0));
            }

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "isCheckByte"));
            List isCheckByteArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "isCheckByte", 0);
            assertEquals(1, isCheckByteArguments.size());

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn10_data1"<br>
     * �Ecolumn2�F"testGetColumn10_data2"<br>
     * �Ecolumn3�F"testGetColumn10_data3"<br>
     * (����) index:0<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn10.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():true<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():����I��<br>
     *                         �������t�B�[���h�̏���Ԃ��B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) FileDAOUtility#trim():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) FileDAOUtility#padding():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) StringConverter#convert():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #isCheckByte():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) -:�ȉ��̏�������FileLineException����������<br>
     *                         �E���b�Z�[�W�F"bytes is not set or a number equal to or less than 0 is set."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �E�t�@�C�����Fthis.fileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F1<br>
     *                         �E�J�������Fcolumn2<br>
     *                         �E�J�����ԍ�:1<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�o�C�g���`�F�b�N����)<br>
     *                         �Ώۃt�B�[���h�ɑ΂��ăo�C�g�����}�C�i�X�l(�f�t�H���g�l�Ȃ�)�Őݒ肳��Ă����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn10() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub13> clazz = AbstractFileLineWriter_Stub13.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub13> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub13>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub13 t = new AbstractFileLineWriter_Stub13();
        t.setColumn1("testGetColumn10_data1");
        t.setColumn2("testGetColumn10_data2");
        t.setColumn3("testGetColumn10_data3");

        int index = 1;

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.TRUE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn10_data2_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn10_data2_trim()_padding()");

            // �e�X�g���{
            fileLineWriter.getColumn(t, index);
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals(
                    "bytes is not set or a number equal to or less than 0 "
                            + "is set.", e.getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());
            assertEquals(1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals(1, e.getColumnIndex());

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            List trimArguments = VMOUTUtil.getArguments(FileDAOUtility.class,
                    "trim", 0);
            assertEquals(4, trimArguments.size());
            assertEquals("testGetColumn10_data2", trimArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), trimArguments
                    .get(1));
            assertEquals(' ', trimArguments.get(2));
            assertEquals(TrimType.NONE, trimArguments.get(3));

            assertEquals(1, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            List paddingArguments = VMOUTUtil.getArguments(
                    FileDAOUtility.class, "padding", 0);
            assertEquals(5, paddingArguments.size());
            assertEquals("testGetColumn10_data2_trim()", paddingArguments
                    .get(0));
            assertEquals(System.getProperty("file.encoding"), paddingArguments
                    .get(1));
            assertEquals(-1, paddingArguments.get(2));
            assertEquals(' ', paddingArguments.get(3));
            assertEquals(PaddingType.NONE, paddingArguments.get(4));

            assertEquals(1, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            List convertArguments = VMOUTUtil.getArguments(
                    NullStringConverter.class, "convert", 0);
            assertEquals(1, convertArguments.size());
            assertEquals("testGetColumn10_data2_trim()_padding()",
                    convertArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "isCheckByte"));
            List isCheckByteArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "isCheckByte", 0);
            assertEquals(1, isCheckByteArguments.size());

        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetColumn11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏�������this.clazz�̃C���X�^���X<br>
     * �Ecolumn1�F"testGetColumn11_data1"<br>
     * �Ecolumn2�F"testGetColumn11_data2"<br>
     * �Ecolumn3�F"testGetColumn11_data3"<br>
     * (����) index:0<br>
     * (���) this.fileName:String�C���X�^���X<br>
     * "AbstractFileLineWriter_testGetColumn11.txt"<br>
     * (���) this.clazz:�ȉ��̐ݒ������Class�C���X�^���X<br>
     * �E@FileFormat�̐ݒ������<br>
     * - �S���ځF�f�t�H���g�l<br>
     * �E@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
     * - �t�B�[���h�FString column1<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column2<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F1<br>
     *                         > bytes�F0<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         - �t�B�[���h�FString column3<br>
     * @OutputFileColumn�ݒ�<br> > columnIndex�F2<br>
     *                         > ���̑����ځF�f�t�H���g�l<br>
     *                         �E�e�t�B�[���h��getter/setter���\�b�h�����B<br>
     *                         (���) this.fields:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.methods:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.stringConverters:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     *                         �E"int"=IntColumnFormatter<br>
     *                         �E"java.lang.String"=NullColumnFormatter<br>
     *                         �E"java.util.Date"=DateColumnFormatter<br>
     *                         �E"java.math.BigDecimal"=DecimalColumnFormatter<br>
     *                         (���) this.fileEncoding:this.clazz�̃t�B�[���h��`�ɏ]���B<br>
     *                         (���) this.currentLineCount:0<br>
     *                         (���) #isCheckByte():true<br>
     *                         (���) FileDAOUtility#trim():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_trim()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) FileDAOUtility#padding():����I��<br>
     *                         ���͂��ꂽ�f�[�^��"_padding()"��ǉ��������ʂ�Ԃ��B<br>
     * <br>
     *                         �������m�F�̂���<br>
     *                         (���) ColumnFormatter#format():����I��<br>
     *                         �������t�B�[���h�̏���Ԃ��B<br>
     * <br>
     *                         ���Ғl�F(��ԕω�) FileDAOUtility#trim():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) FileDAOUtility#padding():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) StringConverter#convert():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) #isCheckByte():1��Ă΂��<br>
     *                         �������m�F����B<br>
     *                         (��ԕω�) -:�ȉ��̏�������FileLineException����������<br>
     *                         �E���b�Z�[�W�F"bytes is not set or a number equal to or less than 0 is set."<br>
     *                         �E������O�FIllegalStateException<br>
     *                         �E�t�@�C�����Fthis.fileName�Ɠ����C���X�^���X<br>
     *                         �E�s���F1<br>
     *                         �E�J�������Fcolumn2<br>
     *                         �E�J�����ԍ�:1<br>
     * <br>
     *                         �ُ�P�[�X<br>
     *                         (�o�C�g���`�F�b�N����)<br>
     *                         �Ώۃt�B�[���h�ɑ΂��ăo�C�g����0�Őݒ肳��Ă����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetColumn11() throws Exception {
        // �O����(�t�@�C��)
        String fileName = TEMP_FILE_NAME;
        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub36> clazz = AbstractFileLineWriter_Stub36.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        columnFormatterMap.put("java.util.Date", new DateColumnFormatter());
        columnFormatterMap.put("java.math.BigDecimal",
                new DecimalColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub36> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub36>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        AbstractFileLineWriter_Stub36 t = new AbstractFileLineWriter_Stub36();
        t.setColumn1("testGetColumn11_data1");
        t.setColumn2("testGetColumn11_data2");
        t.setColumn3("testGetColumn11_data3");

        int index = 1;

        try {
            fileLineWriter.init();

            // �O����(�t�B�[���h)
            UTUtil.setPrivateField(fileLineWriter, "currentLineCount", 0);

            // �O����(���\�b�h)
            VMOUTUtil.setReturnValueAtAllTimes(AbstractFileLineWriter.class,
                    "isCheckByte", Boolean.TRUE);

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "trim", 0,
                    "testGetColumn11_data2_trim()");

            VMOUTUtil.setReturnValueAt(FileDAOUtility.class, "padding", 0,
                    "testGetColumn11_data2_trim()_padding()");

            // �e�X�g���{
            fileLineWriter.getColumn(t, index);
            fail("FileLineException���������܂���ł����B");
        } catch (FileLineException e) {
            // ����(��O)
            assertTrue(FileLineException.class.isAssignableFrom(e.getClass()));
            assertEquals(
                    "bytes is not set or a number equal to or less than 0 "
                            + "is set.", e.getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());
            assertEquals(1, e.getLineNo());
            assertEquals("column2", e.getColumnName());
            assertEquals(1, e.getColumnIndex());

            // ����(��ԕω��A���\�b�h)
            assertEquals(1, VMOUTUtil
                    .getCallCount(FileDAOUtility.class, "trim"));
            List trimArguments = VMOUTUtil.getArguments(FileDAOUtility.class,
                    "trim", 0);
            assertEquals(4, trimArguments.size());
            assertEquals("testGetColumn11_data2", trimArguments.get(0));
            assertEquals(System.getProperty("file.encoding"), trimArguments
                    .get(1));
            assertEquals(' ', trimArguments.get(2));
            assertEquals(TrimType.NONE, trimArguments.get(3));

            assertEquals(1, VMOUTUtil.getCallCount(FileDAOUtility.class,
                    "padding"));
            List paddingArguments = VMOUTUtil.getArguments(
                    FileDAOUtility.class, "padding", 0);
            assertEquals(5, paddingArguments.size());
            assertEquals("testGetColumn11_data2_trim()", paddingArguments
                    .get(0));
            assertEquals(System.getProperty("file.encoding"), paddingArguments
                    .get(1));
            assertEquals(0, paddingArguments.get(2));
            assertEquals(' ', paddingArguments.get(3));
            assertEquals(PaddingType.NONE, paddingArguments.get(4));

            assertEquals(1, VMOUTUtil.getCallCount(NullStringConverter.class,
                    "convert"));
            List convertArguments = VMOUTUtil.getArguments(
                    NullStringConverter.class, "convert", 0);
            assertEquals(1, convertArguments.size());
            assertEquals("testGetColumn11_data2_trim()_padding()",
                    convertArguments.get(0));

            assertEquals(1, VMOUTUtil.getCallCount(
                    AbstractFileLineWriter.class, "isCheckByte"));
            List isCheckByteArguments = VMOUTUtil.getArguments(
                    AbstractFileLineWriter.class, "isCheckByte", 0);
            assertEquals(1, isCheckByteArguments.size());
        } finally {
            Writer writer = (Writer) UTUtil.getPrivateField(fileLineWriter,
                    "writer");

            if (writer != null) {
                writer.close();
            }

            UTUtil.setPrivateField(AbstractFileLineWriter.class,
                    "stringConverterCacheMap",
                    new HashMap<Class, StringConverter>());
        }
    }

    /**
     * testGetFileName01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) fileName:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) fileName:not null<br>
     * <br>
     * fileName��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetFileName01() throws Exception {
        // �O����(����)
        String fileName = "fileName";

        // �O����(�����Ώ�)
        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "fileName", fileName);

        // �e�X�g���{
        String result = fileLineWriter.getFileName();

        // ����(�߂�l)
        assertNotNull(result);
        assertSame(fileName, result);
    }

    /**
     * testGetLineFeedChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.lineFeedChar:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) lineFeedChar:not null<br>
     * <br>
     * lineFeedChar��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testGetLineFeedChar01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);
        // �O����(����)
        String lineFeedChar = "testGetLineFeedChar01_lineFeedChar";

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "lineFeedChar", lineFeedChar);

        // �e�X�g���{
        String result = fileLineWriter.getLineFeedChar();

        // ����(�߂�l)
        assertNotNull(result);
        assertSame(lineFeedChar, result);
    }

    /**
     * testSetColumnFormatterMap01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(����) columnFormatterMap:not null<br>
     * <br>
     * ���Ғl�F(��ԕω�) columnFormatterMap:not null<br>
     * <br>
     * columnFormatterMap��setter���\�b�h�̒l���������ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSetColumnFormatterMap01() throws Exception {
        // �O����(����)
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "columnFormatterMap", null);

        // �e�X�g���{
        fileLineWriter.setColumnFormatterMap(columnFormatterMap);

        // ����(��ԕω��A�t�B�[���h)
        Map<String, ColumnFormatter> resultMap = (Map<String, ColumnFormatter>) UTUtil
                .getPrivateField(fileLineWriter, "columnFormatterMap");
        assertNotNull(resultMap);
        assertSame(columnFormatterMap, resultMap);
    }

    /**
     * testGetWriter01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.writer:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) writer:not null<br>
     * <br>
     * writer��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testGetWriter01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), System
                            .getProperty("file.encoding")));

            UTUtil.setPrivateField(fileLineWriter, "writer", writer);
            // �e�X�g���{
            Writer result = fileLineWriter.getWriter();

            // ����(�߂�l)
            assertNotNull(result);
            assertSame(writer, result);
        } finally {
            if (writer != null) {
                writer.close();
            }
            // �����㐶�������t�@�C���̍폜
            File file = new File(fileName);
            file.delete();
        }
    }

    /**
     * testGetFields01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.fields:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) fields:not null<br>
     * <br>
     * fields��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    @Test
    public void testGetFields01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        Field[] fields = new Field[] { null, null, null };
        UTUtil.setPrivateField(fileLineWriter, "fields", fields);

        // �e�X�g���{
        Field[] result = fileLineWriter.getFields();

        // ����(�߂�l)
        assertNotNull(result);
        assertSame(fields, result);
    }

    /**
     * testGetMethods01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.getMethods:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) methods:not null<br>
     * <br>
     * methods��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testGetMethods01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        Method[] methods = new Method[] { null, null, null };
        UTUtil.setPrivateField(fileLineWriter, "methods", methods);

        // �e�X�g���{
        Method[] result = fileLineWriter.getMethods();

        // ����(�߂�l)
        assertNotNull(result);
        assertSame(methods, result);
    }

    /**
     * testSetWriteData01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) writeData:true<br>
     * (���) this.writeData:false<br>
     * <br>
     * ���Ғl�F(��ԕω�) writeData:true<br>
     * <br>
     * writeData��setter���\�b�h�̒l���������ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testSetWriteData01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());

        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "writeData", Boolean.FALSE);

        // �e�X�g���{
        fileLineWriter.setWriteData(true);

        // ����(��ԕω��A�t�B�[���h)
        boolean resultBoolean = Boolean.class.cast(UTUtil.getPrivateField(
                fileLineWriter, "writeData"));
        assertNotNull(resultBoolean);
        assertTrue(resultBoolean);
    }

    /**
     * testCheckWriteTrailer01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(���) this.isWriteTrailer:false<br>
     * <br>
     * ���Ғl�F <br>
     * �g���C�����o�͂��Ă��Ȃ��Ƃ��͉������Ȃ� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testCheckWriteTrailer01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "writeTrailer", Boolean.FALSE);

        // �e�X�g���{
        try {
            fileLineWriter.checkWriteTrailer();
        } catch (FileException e) {
            // ����
            fail("FileException��O���������܂����B");

        }
    }

    /**
     * testCheckWriteTrailer02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.isWriteTrailer:true<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:FileException����������<br>
     * ������O�FIllegalStateException<br>
     * �t�@�C���������͒l��fileName�Ɉ�v���邱�Ƃ��m�F����B<br>
     * Header part or data part should be called before TrailerPart",<br>
     * <br>
     * �g���C���̏o�͂��������Ă���ꍇ�A��O���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testCheckWriteTrailer02() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub01> clazz = AbstractFileLineWriter_Stub01.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub01> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub01>(
                fileName, clazz, columnFormatterMap);

        // �O����(�t�B�[���h)
        UTUtil.setPrivateField(fileLineWriter, "writeTrailer", Boolean.TRUE);

        // �e�X�g���{
        try {
            fileLineWriter.checkWriteTrailer();
            fail("FileException��O���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(FileException.class.isAssignableFrom(e.getClass()));
            assertEquals("Header part or data part should be called before "
                    + "TrailerPart", e.getMessage());
            assertTrue(IllegalStateException.class.isAssignableFrom(e
                    .getCause().getClass()));
            assertEquals(fileName, e.getFileName());
        }
    }

    /**
     * testIsCheckByte01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) outputFileColumn:not null<br>
     * (���) outputFileColumn#bytes():0<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     * <br>
     * �o�C�g����0�̏ꍇ�Afalse���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testIsCheckByte01() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Class<AbstractFileLineWriter_Stub13> clazz = AbstractFileLineWriter_Stub13.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub13> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub13>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        Field column2 = AbstractFileLineWriter_Stub13.class.getDeclaredFields()[1];
        OutputFileColumn outputFileColumn = column2
                .getAnnotation(OutputFileColumn.class);

        // �e�X�g���{
        boolean result = fileLineWriter.isCheckByte(outputFileColumn);

        // ����(�߂�l)
        assertFalse(result);
    }

    /**
     * testIsCheckByte02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) outputFileColumn:not null<br>
     * (���) outputFileColumn#bytes():1<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * <br>
     * �o�C�g����48�̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testIsCheckByte02() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";
        Class<AbstractFileLineWriter_Stub37> clazz = AbstractFileLineWriter_Stub37.class;

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("int", new IntColumnFormatter());
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<AbstractFileLineWriter_Stub37> fileLineWriter = new AbstractFileLineWriterImpl01<AbstractFileLineWriter_Stub37>(
                fileName, clazz, columnFormatterMap);

        // �O����(����)
        Field column2 = AbstractFileLineWriter_Stub37.class.getDeclaredFields()[1];
        OutputFileColumn outputFileColumn = column2
                .getAnnotation(OutputFileColumn.class);

        // �e�X�g���{
        boolean result = fileLineWriter.isCheckByte(outputFileColumn);

        // ����(�߂�l)
        assertTrue(result);
    }

    /**
     * testIsCheckByte03() <br>
     * <br>
     * (����n) <br>
     * <br>
     * ���͒l�F(����) int:-1<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * <br>
     * �o�C�g����-1�̏ꍇ�Afalse���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testIsCheckByte03() throws Exception {
        // �O����(�����Ώ�)
        String fileName = "fileName";

        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        AbstractFileLineWriter<FileLineObject_Empty> fileLineWriter = new AbstractFileLineWriterImpl01<FileLineObject_Empty>(
                fileName, FileLineObject_Empty.class, columnFormatterMap);

        // �e�X�g���{
        boolean result = fileLineWriter.isCheckByte(1);

        // ����(�߂�l)
        assertTrue(result);
    }
}

/*
 * $Id: EncloseCharLineFeed1LineReaderTest.java 5354 2007-10-03 06:06:25Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.EncloseCharLineFeed1LineReader} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �͂ݕ����L��A�s��؂蕶����1�����̏ꍇ�̃t�@�C������1�s���̕�������擾���鏈�����s���B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.EncloseCharLineFeed1LineReader
 */
public class EncloseCharLineFeed1LineReaderTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(EncloseCharLineFeed1LineReaderTest.class);
    }

    /**
     * �������������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
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
    public EncloseCharLineFeed1LineReaderTest(String name) {
        super(name);
    }

    /**
     * testEncloseCharLineFeed1LineReader01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) delimiterCharacter:a'<br>
     * (����) encloseCharacter:b'<br>
     * (����) reader:BufferedReader�̃C���X�^���X<br>
     * (����) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.delimiterCharacter:����delimiterCharacter�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.encloseCharacter:����encloseCharacter�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.reader:����reader�Ɠ����C���X�^���X<br>
     * (��ԕω�) this.lineFeedChar:����lineFeedChar�Ɠ����C���X�^���X<br>
     * <br>
     * �I�u�W�F�N�g�������ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncloseCharLineFeed1LineReader01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        char delimiterCharacter = 'a';
        char[] encloseCharacter = { 'b' };
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r";

        // �O������Ȃ�

        // �e�X�g���{
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                delimiterCharacter, Character.MIN_VALUE, encloseCharacter,
                bufferedReader, lineFeedChar);

        // �ԋp�l�Ȃ�

        // ��ԕω��̊m�F
        char char01 = (Character) UTUtil.getPrivateField(testCalss,
                "delimiterCharacter");
        assertEquals(delimiterCharacter, char01);
        char[] char02 = (char[]) UTUtil.getPrivateField(testCalss,
                "columnEncloseCharacter");
        assertEquals(encloseCharacter, char02);
        Reader reader = (Reader) UTUtil.getPrivateField(testCalss, "reader");
        assertEquals(bufferedReader, reader);
        String getLineFeedChar = (String) UTUtil.getPrivateField(testCalss,
                "lineFeedChar");
        assertEquals(lineFeedChar, getLineFeedChar);
    }

    /**
     * testEncloseCharLineFeed1LineReader02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) delimiterCharacter:Character.MIN_VALUE<br>
     * (����) encloseCharacter:\"<br>
     * (����) reader:BufferedReader�̃C���X�^���X<br>
     * (����) lineFeedChar:""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�F"delimiterCharacter can not use '\\u0000'."<br>
     * <br>
     * ��؂蕶����'\u0000'�������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncloseCharLineFeed1LineReader02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        char delimiterCharacter = Character.MIN_VALUE;
        char[] encloseCharacter = { '"' };
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "";

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new EncloseCharLineFeed1LineReader(delimiterCharacter,
                    Character.MIN_VALUE, encloseCharacter, bufferedReader,
                    lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("delimiterCharacter can not use '\\u0000'.", e
                    .getMessage());
        }
    }

    /**
     * testEncloseCharLineFeed1LineReader03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) delimiterCharacter:,'<br>
     * (����) encloseCharacter:Character.MIN_VALUE<br>
     * (����) reader:BufferedReader�̃C���X�^���X<br>
     * (����) lineFeedChar:""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�F"encloseCharacter can not use '\\u0000'."<br>
     * <br>
     * �͂ݕ�����'\u0000'�������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncloseCharLineFeed1LineReader03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        char delimiterCharacter = ',';
        char[] encloseCharacter = null;
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "";

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new EncloseCharLineFeed1LineReader(delimiterCharacter,
                    Character.MIN_VALUE, encloseCharacter, bufferedReader,
                    lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("columnEncloseCharacter is required.", e.getMessage());
        }
    }

    /**
     * testEncloseCharLineFeed1LineReader04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) delimiterCharacter:,'<br>
     * (����) encloseCharacter:"\"<br>
     * (����) reader:null<br>
     * (����) lineFeedChar:""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�F"reader is required."<br>
     * <br>
     * �t�@�C���A�N�Z�X�p�̕����X�g���[����null�������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncloseCharLineFeed1LineReader04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        char delimiterCharacter = ',';
        char[] encloseCharacter = { '\"' };
        BufferedReader bufferedReader = null;
        String lineFeedChar = "";

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new EncloseCharLineFeed1LineReader(delimiterCharacter,
                    Character.MIN_VALUE, encloseCharacter, bufferedReader,
                    lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("reader is required.", e.getMessage());
        }
    }

    /**
     * testEncloseCharLineFeed1LineReader05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) delimiterCharacter:,'<br>
     * (����) encloseCharacter:"\"<br>
     * (����) reader:BufferedReader�̃C���X�^���X<br>
     * (����) lineFeedChar:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�F"lineFeedChar is required."<br>
     * <br>
     * �s��؂蕶����null�������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncloseCharLineFeed1LineReader05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        char delimiterCharacter = ',';
        char[] encloseCharacter = { '\"' };
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineFeedChar = null;

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new EncloseCharLineFeed1LineReader(delimiterCharacter,
                    Character.MIN_VALUE, encloseCharacter, bufferedReader,
                    lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("lineFeedChar is required.", e.getMessage());
        }
    }

    /**
     * testEncloseCharLineFeed1LineReader06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) delimiterCharacter:,'<br>
     * (����) encloseCharacter:"\"<br>
     * (����) reader:BufferedReader�̃C���X�^���X<br>
     * (����) lineFeedChar:""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�F"lineFeedChar should be defined by 1 digit of character string."<br>
     * <br>
     * �s��؂蕶����1�����ȊO�������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncloseCharLineFeed1LineReader06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        char delimiterCharacter = ',';
        char[] encloseCharacter = { '\"' };
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "";

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new EncloseCharLineFeed1LineReader(delimiterCharacter,
                    Character.MIN_VALUE, encloseCharacter, bufferedReader,
                    lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("lineFeedChar should be defined by 1"
                    + " digit of character string.", e.getMessage());
        }
    }

    /**
     * testEncloseCharLineFeed1LineReader07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) delimiterCharacter:,'<br>
     * (����) encloseCharacter:"\"<br>
     * (����) reader:BufferedReader�̃C���X�^���X<br>
     * (����) lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�F"lineFeedChar should be defined by 1 digit of character string."<br>
     * <br>
     * �s��؂蕶����1�����ȊO�������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncloseCharLineFeed1LineReader07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X���Ȃ�

        // �����̐ݒ�
        char delimiterCharacter = ',';
        char[] encloseCharacter = { '\"' };
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";

        // �O������Ȃ�

        // �e�X�g���{
        try {
            new EncloseCharLineFeed1LineReader(delimiterCharacter,
                    Character.MIN_VALUE, encloseCharacter, bufferedReader,
                    lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("lineFeedChar should be defined by 1"
                    + " digit of character string.", e.getMessage());
        }
    }

    /**
     * testReadLine01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) reader:not null<br>
     * Reader�����X�^�u<br>
     * #ready(), #read()��IOException���X���[����<br>
     * (���) lineFeedChar:''\r'<br>
     * (���) delimiter:not null<br>
     * 'a'<br>
     * (���) encloseCharacter:not null<br>
     * 'b'<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:IOException�������BFileExceptio�Ƀ��b�v����邱�Ƃ��m�F����B<br>
     * ���b�Z�[�W�F"Reader control operation was failed."<br>
     * <br>
     * Reader���f�[�^�̓ǂݎ��Ɏ��s�����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine01() throws Exception {
        // Mock�쐬
        Reader reader = EasyMock.createMock(Reader.class);
        EasyMock.expect(reader.read()).andReturn(null).andThrow(
                new IOException());
        EasyMock.replay(reader);

        // �e�X�g�Ώۂ̃C���X�^���X��
        char delimiterCharacter = 'a';
        char[] encloseCharacter = { 'b' };
        String parm = "\r";
        EncloseCharLineFeed1LineReader target = new EncloseCharLineFeed1LineReader(
                delimiterCharacter, Character.MIN_VALUE, encloseCharacter,
                reader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        try {
            target.readLine();
            fail("FileException���X���[����Ȃ�����");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(FileException.class, e.getClass());
            assertEquals(IOException.class.getName(), e.getCause().getClass()
                    .getName());
            assertEquals("Reader control operation was failed.", e.getMessage());
        }
    }

    /**
     * testReadLine02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:""(��)<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:not null<br>
     * 'a'<br>
     * (���) encloseCharacter:not null<br>
     * 'b'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     * <br>
     * �ǂݍ��񂾃f�[�^���Ȃ��i�󕶎��j�̏ꍇ�A�󕶎���������Ƃ��ĕԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = 'a';
        char[] eChar = { 'b' };
        byte[] buf = "".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�̊m�F
        assertEquals("", result);

        // ��ԕω��Ȃ�
    }

    /**
     * testReadLine03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"aaa,aaa,aaa\r"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa,aaa,aaa"<br>
     * <br>
     * �͂ݕ������Ȃ��ꍇ�́A�s��؂蕶���������������񂪕ԋp����� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', '\"', '\"', '\"' };
        byte[] buf = "aaa,aaa,aaa\r".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�Ȃ�
        assertEquals("aaa,aaa,aaa", result);
    }

    /**
     * testReadLine04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"aaa\",\"aaa\",\"aaa\"\r"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aaa\",\"aaa\",\"aaa\""<br>
     * <br>
     * �͂ݕ���������ꍇ�́A�͂ݕ���������܂܂̕����񂪕ԋp�����B<br>
     * �s��؂蕶���������������񂪕ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', '\"', '\"', '\"' };
        byte[] buf = "\"aaa\",\"aaa\",\"aaa\"\r".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�̊m�F
        assertEquals("\"aaa\",\"aaa\",\"aaa\"", result);

        // ��ԕω��Ȃ�
    }

    /**
     * testReadLine05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"aa,a\",\"aa\"\"a\",\"aa\ra\"\r"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aa,a\",\"aa\"\"a\",\"aa\ra\""<br>
     * <br>
     * ������̒��Ɉ͂ݕ����A��؂蕶���A�s��؂蕶�����܂܂�Ă���ꍇ�B�@�i�͂ݕ����̃G�X�P�[�v�Ȃǁj<br>
     * �s��؂蕶���������������񂪕ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', '\"', '\"', '\"' };
        byte[] buf = "\"aa,a\",\"aa\"\"a\",\"aa\ra\"\r".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�̊m�F
        assertEquals("\"aa,a\",\"aa\"\"a\",\"aa\ra\"", result);

        // ��ԕω��Ȃ�
    }

    /**
     * testReadLine06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"aaa\",\"aaa\",\"aaa\"aaa\r"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aaa\",\"aaa\",\"aaa\"aaa"<br>
     * <br>
     * ������1�s�ԋp�����B�͂ݕ���(�I����)�̌��ɕ����񂪂Ȃ���ꍇ�A��؂蕶���������͍s��؂蕶��������܂ł̕�����͑S�đO�̃J�����Ɋ܂܂��B<br>
     * �s��؂蕶���������������񂪕ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', '\"', '\"', '\"' };
        byte[] buf = "\"aaa\",\"aaa\",\"aaa\"aaa\r".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�̊m�F
        assertEquals("\"aaa\",\"aaa\",\"aaa\"aaa", result);

        // ��ԕω��Ȃ�
    }

    /**
     * testReadLine07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\r"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     * <br>
     * ������̒��ɍs��؂蕶���̂݁B<br>
     * �s��؂蕶���������������񂪕ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"' };
        byte[] buf = "\r".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�̊m�F
        assertEquals("", result);

        // ��ԕω��Ȃ�
    }

    /**
     * testReadLine08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"aaa"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:\r'<br>
     * (���) delimiter:,'<br>
     * (���) encloseCharacter:\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa"<br>
     * <br>
     * �s��؂蕶�����Ȃ��f�[�^��ǂݍ��񂾏ꍇ�A�ŏI�f�[�^�܂ł̕����񂪕ԋp����� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"' };
        byte[] buf = "aaa".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�̊m�F
        assertEquals("aaa", result);

        // ��ԕω��Ȃ�
    }

    /**
     * testReadLine09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"aaa,aaa,aaa\raaa,aaa"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa,aaa,aaa"<br>
     * <br>
     * �ǂݍ��񂾃f�[�^�ɉ��s�������܂܂�Ă����炻���܂ł̕����񂪕ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', '\"', '\"', '\"' };
        byte[] buf = "aaa,aaa,aaa\raaa,aaa".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�Ȃ�
        assertEquals("aaa,aaa,aaa", result);
    }

    /**
     * testReadLine10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"������,������,������\r"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"������,������,������"<br>
     * <br>
     * ������̒��ɁA�S�p�������܂܂�Ă���ꍇ�B<br>
     * �s��؂蕶���������������񂪕ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', '\"', '\"', '\"' };
        byte[] buf = "������,������,������\r".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�Ȃ�
        assertEquals("������,������,������", result);
    }

    /**
     * testReadLine11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"aaa,aaa,aaaaaa\r\naaa"<br>
     * (���) reader:not null <br>
     * BufferedReader�̃C���X�^���X<br>
     * (���) lineFeedChar:'\r'<br>
     * (���) delimiter:','<br>
     * (���) encloseCharacter:'\"'<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa,aaa,aaaaaa"<br>
     * <br>
     * �ǂݍ��񂾃f�[�^�ɉ��s����(\r\n)���܂܂�Ă����ꍇ�A\r�܂ł̕����񂪕ԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', '\"', '\"', '\"' };
        byte[] buf = "aaa,aaa,aaaaaa\r\naaa".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �����Ȃ�

        // �O�����(�C���X�^���X���Őݒ肳���)

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�Ȃ�
        assertEquals("aaa,aaa,aaaaaa", result);
    }

    /**
     * ����n<br>
     * �J�������̈͂ݕ����ݒ�L��
     * @throws Exception
     */
    public void testReadLine12() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', Character.MIN_VALUE, '|' };
        byte[] buf = "\"aaa\",\"aaa\",|aaaaaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�Ȃ�
        assertEquals("\"aaa\",\"aaa\",|aaaaaa|", result);
    }

    /**
     * ����n<br>
     * �J�������̈͂ݕ����ݒ�L��
     * @throws Exception
     */
    public void testReadLine13() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eChar = { '\"', Character.MIN_VALUE, '|' };
        byte[] buf = "\"a\raa\",\"aaa\",|aaa\raaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, Character.MIN_VALUE, eChar, bufferedReader, parm);

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�Ȃ�
        assertEquals("\"a\raa\",\"aaa\",|aaa\raaa|", result);
    }

    /**
     * ����n<br>
     * eColChar.length : 0
     * @throws Exception
     */
    public void testReadLine14() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eColChar = {};
        char eChar = '\"';
        byte[] buf = "\"a\raa\",\"aaa\",|aaaaaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, eChar, eColChar, bufferedReader, parm);

        // �e�X�g���{
        String result = testCalss.readLine();

        // �ԋp�l�Ȃ�
        assertEquals("\"a\raa\",\"aaa\",|aaaaaa|", result);
    }

    /**
     * testGetEncloseCharcter001.
     * @throws Exception
     */
    public void testGetEncloseCharcter001() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eColChar = { '\"', '\"', '\"', '\"' };
        char eChar = '\'';
        byte[] buf = "\"a\raa\",\"aaa\",|aaaaaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, eChar, eColChar, bufferedReader, parm);

        int index = 0;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(testCalss, "getEncloseCharcter",
                int.class, index);

        // �ԋp�l�Ȃ�
        assertNotNull(result);
        assertEquals(Character.valueOf('\"'), (Character) result);
    }

    /**
     * testGetEncloseCharcter002.
     * @throws Exception
     */
    public void testGetEncloseCharcter002() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eColChar = { '\"', '\"', '\"', '\"' };
        char eChar = '\'';
        byte[] buf = "\"a\raa\",\"aaa\",|aaaaaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, eChar, eColChar, bufferedReader, parm);

        int index = 1;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(testCalss, "getEncloseCharcter",
                int.class, index);

        // �ԋp�l�Ȃ�
        assertNotNull(result);
        assertEquals(Character.valueOf('\"'), (Character) result);
    }

    /**
     * testGetEncloseCharcter003.
     * @throws Exception
     */
    public void testGetEncloseCharcter003() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eColChar = { '\"', '\"', '\"', '\"' };
        char eChar = '\'';
        byte[] buf = "\"a\raa\",\"aaa\",|aaaaaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, eChar, eColChar, bufferedReader, parm);

        int index = 3;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(testCalss, "getEncloseCharcter",
                int.class, index);

        // �ԋp�l�Ȃ�
        assertNotNull(result);
        assertEquals(Character.valueOf('\"'), (Character) result);
    }

    /**
     * testGetEncloseCharcter004.
     * @throws Exception
     */
    public void testGetEncloseCharcter004() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eColChar = { '\"', '\"', '\"', '\"' };
        char eChar = '\'';
        byte[] buf = "\"a\raa\",\"aaa\",|aaaaaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, eChar, eColChar, bufferedReader, parm);

        int index = 4;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(testCalss, "getEncloseCharcter",
                int.class, index);

        // �ԋp�l�Ȃ�
        assertNotNull(result);
        assertEquals(Character.valueOf('\''), (Character) result);
    }

    /**
     * testGetEncloseCharcter005.
     * @throws Exception
     */
    public void testGetEncloseCharcter005() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        char dChar = ',';
        char[] eColChar = { '\"', '\"', '\"', '\"' };
        char eChar = '\'';
        byte[] buf = "\"a\raa\",\"aaa\",|aaaaaa|\r\naaa".getBytes();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf)));

        String parm = "\r";
        EncloseCharLineFeed1LineReader testCalss = new EncloseCharLineFeed1LineReader(
                dChar, eChar, eColChar, bufferedReader, parm);

        int index = 5;

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(testCalss, "getEncloseCharcter",
                int.class, index);

        // �ԋp�l�Ȃ�
        assertNotNull(result);
        assertEquals(Character.valueOf('\''), (Character) result);
    }

}

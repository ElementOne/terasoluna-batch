/*
 * $Id: LineFeed1LineReaderTest.java 5354 2007-10-03 06:06:25Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

/**
 * {@link jp..terasoluna.fw.file.dao.standard.LineFeed1LineReader} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �͂ݕ����Ȃ��A�s��؂蕶����1�����̏ꍇ�̃t�@�C������1�s���̕�������擾���鏈�����s���B
 * <p>
 * @author ���c�N�i
 * @see jp..terasoluna.fw.file.dao.standard.LineFeed1LineReader
 */
public class LineFeed1LineReaderTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(LineFeed1LineReaderTest.class);
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
    public LineFeed1LineReaderTest(String name) {
        super(name);
    }

    /**
     * testLineFeed1LineReader01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) reader:Reader�C���X�^���X<br>
     * (����) lineFeedChar:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:����reader�Ɠ���̃C���X�^���X<br>
     * (��ԕω�) this.lineFeedChar:����lineFeedChar�Ɠ���̃C���X�^���X<br>
     * <br>
     * �I�u�W�F�N�g�������ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed1LineReader01() throws Exception {
        // �O����(����)
        Reader reader = new BufferedReader(new StringReader(""));
        String lineFeedChar = new String("a");

        // �e�X�g���{
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                reader, lineFeedChar);

        // ����
        assertNotNull(lineFeed1LineReader);

        Reader lineFeed1Reader = (Reader) UTUtil.getPrivateField(
                lineFeed1LineReader, "reader");
        assertSame(reader, lineFeed1Reader);

        String lineFeed1LineFeedChar = (String) UTUtil.getPrivateField(
                lineFeed1LineReader, "lineFeedChar");
        assertSame(lineFeedChar, lineFeed1LineFeedChar);
    }

    /**
     * testLineFeed1LineReader02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE,G <br>
     * <br>
     * ���͒l�F(����) reader:null<br>
     * (����) lineFeedChar:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:IllegalArgumentException����������<br>
     * �E���b�Z�[�W�F"reader is required."<br>
     * <br>
     * ����reader��null�̏ꍇIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed1LineReader02() throws Exception {
        // �O����(����)
        Reader reader = null;
        String lineFeedChar = new String();

        try {
            // �e�X�g���{
            new LineFeed1LineReader(reader, lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("reader is required.", e.getMessage());
        }
    }

    /**
     * testLineFeed1LineReader03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE,G <br>
     * <br>
     * ���͒l�F(����) reader:Reader�C���X�^���X<br>
     * (����) lineFeedChar:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:IllegalArgumentException����������<br>
     * �E���b�Z�[�W�F"lineFeedChar is required."<br>
     * <br>
     * ����lineFeedChar��null�̏ꍇIllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed1LineReader03() throws Exception {
        // �O����(����)
        Reader reader = new BufferedReader(new StringReader(""));
        String lineFeedChar = null;

        try {
            // �e�X�g���{
            new LineFeed1LineReader(reader, lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("lineFeedChar is required.", e.getMessage());
        }
    }

    /**
     * testLineFeed1LineReader04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE,G <br>
     * <br>
     * ���͒l�F(����) reader:Reader�C���X�^���X<br>
     * (����) lineFeedChar:�s��؂蕶����1��������Ȃ��ꍇ<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:IllegalArgumentException����������<br>
     * �E���b�Z�[�W�F"lineFeedChar should be defined by 1 digit of character string."<br>
     * <br>
     * ����lineFeedChar�̍s��؂蕶����1��������Ȃ��ꍇ IllegalArgumentException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed1LineReader04() throws Exception {
        // �O����(����)
        Reader reader = new BufferedReader(new StringReader(""));
        String lineFeedChar = "aa";

        try {
            // �e�X�g���{
            new LineFeed1LineReader(reader, lineFeedChar);
            fail("IllegalArgumentException���������܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("lineFeedChar should be defined"
                    + " by 1 digit of character string.", e.getMessage());
        }
    }

    /**
     * testReadLine01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FF.G <br>
     * <br>
     * ���͒l�F(���) reader:not null<br>
     * reader�̃X�^�u<br>
     * IOException����������B<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IOException�������BFileExceptio�Ƀ��b�v����邱�Ƃ��m�F����B<br>
     * <br>
     * ��O�Breader����O���X���[�����ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine01() throws Exception {
        // Mock�쐬
        Reader reader = EasyMock.createMock(Reader.class);
        EasyMock.expect(reader.read()).andReturn(null).andThrow(
                new IOException());
        EasyMock.replay(reader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                reader, tempLineFeedChar);

        // �e�X�g���{
        try {
            lineFeed1LineReader.readLine();
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����
            assertTrue(e instanceof FileException);
            assertEquals("Reader control operation was failed.", e.getMessage());
            assertTrue(e.getCause() instanceof IOException);
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
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     * <br>
     * ��̕����񂪕ԋp�����̂��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine02() throws Exception {
        // �O����(�����Ώ�)

        String str = "";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        String lineFeedChar = "\r";
        UTUtil.setPrivateField(lineFeed1LineReader, "lineFeedChar",
                lineFeedChar);

        // �e�X�g���{
        String resutl = lineFeed1LineReader.readLine();

        // ����
        assertEquals("", resutl);
    }

    /**
     * testReadLine03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"aaa,aaa,aaa\r"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa,aaa,aaa"<br>
     * <br>
     * ������1�s�ԋp�����̂��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine03() throws Exception {
        // �O����(�����Ώ�)
        String str = "aaa,aaa,aaa\r";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String resutl = lineFeed1LineReader.readLine();

        // ����
        assertEquals("aaa,aaa,aaa", resutl);
    }

    /**
     * testReadLine04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"aaa\",\"aaa\",\"aaa\"\r"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aaa\",\"aaa\",\"aaa\""<br>
     * <br>
     * ������1�s�ԋp�����B�͂ݕ���������ꍇ�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine04() throws Exception {
        // �O����(�����Ώ�)
        String str = "\"aaa\",\"aaa\",\"aaa\"\r";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String resutl = lineFeed1LineReader.readLine();

        // ����
        assertEquals("\"aaa\",\"aaa\",\"aaa\"", resutl);
    }

    /**
     * testReadLine05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"aa,a\",\"aa\"\"a\",\"aaa\"\r"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aa,a\",\"aa\"\"a\",\"aaa\""<br>
     * <br>
     * ������̒��Ɉ͂ݕ����A��؂蕶�����܂܂�Ă���ꍇ�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine05() throws Exception {
        // �O����(�����Ώ�)
        String str = "\"aa,a\",\"aa\"\"a\",\"aaa\"\r";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String result = lineFeed1LineReader.readLine();

        // ����
        assertEquals("\"aa,a\",\"aa\"\"a\",\"aaa\"", result);
    }

    /**
     * testReadLine06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"aaa\",\"aaa\",\"aa\ra\"\r"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aaa\",\"aaa\",\"aa"<br>
     * <br>
     * ������̒��ɍs��؂蕶�����܂܂�Ă���ꍇ�B<br>
     * �s��؂蕶���̂Ƃ���œǂݍ��݂͏I������B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine06() throws Exception {
        // �O����(�����Ώ�)
        String str = "\"aaa\",\"aaa\",\"aa\ra\"\r";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String result = lineFeed1LineReader.readLine();

        // ����
        assertEquals("\"aaa\",\"aaa\",\"aa", result);
    }

    /**
     * testReadLine07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"aaa\",\"aaa\",\"aaa\"aaa\r"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aaa\",\"aaa\",\"aaa\"aaa"<br>
     * <br>
     * ������1�s�ԋp�����B�͂ݕ���(�I����)�̌��ɕ����񂪂Ȃ���ꍇ�A ��؂蕶���������͍s��؂蕶��������܂ł̕�����͑S�đO�̃J�����Ɋ܂܂��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine07() throws Exception {
        // �O����(�����Ώ�)
        String str = "\"aaa\",\"aaa\",\"aaa\"aaa\r";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String resutl = lineFeed1LineReader.readLine();

        // ����
        assertEquals("\"aaa\",\"aaa\",\"aaa\"aaa", resutl);
    }

    /**
     * testReadLine08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"aaa,aaa,aaa"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa,aaa,aaa"<br>
     * <br>
     * �s��؂蕶�����܂܂�ĂȂ��ꍇ���ׂẴf�[�^���o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine08() throws Exception {
        // �O����(�����Ώ�)
        String str = "aaa,aaa,aaa";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String resutl = lineFeed1LineReader.readLine();

        // ����
        assertEquals("aaa,aaa,aaa", resutl);
    }

    /**
     * testReadLine09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"\"����,��\",\"����\"\"��\",\"������\"\r"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"����,��\",\"����\"\"��\",\"������\""<br>
     * <br>
     * ������̒��Ɉ͂ݕ����A�S�p�������܂܂�Ă���ꍇ�B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine09() throws Exception {
        // �O����(�����Ώ�)
        String str = "\"����,��\",\"����\"\"��\",\"������\"\r";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String resutl = lineFeed1LineReader.readLine();

        // ����
        assertEquals("\"����,��\",\"����\"\"��\",\"������\"", resutl);
    }

    /**
     * testReadLine10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,F <br>
     * <br>
     * ���͒l�F(���) reader���ǂݎ�镶����:"aaa,aaa,aaa\raaa,aaa,aaa\raaa,aaa,aaa\r"<br>
     * (���) reader:not null <br>
     * Reader�C���X�^���X<br>
     * (���) lineFeedChar:"\r"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa,aaa,aaa"<br>
     * <br>
     * �����s���̕������񂪂���ꍇ�A���ʕ�����1�s���̂ݕԋp�����̂��m�F����B<br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine10() throws Exception {
        // �O����(�����Ώ�)
        String str = "aaa,aaa,aaa\raaa,aaa,aaa\raaa,aaa,aaa\r";
        StringReader stringReader = new StringReader(str);
        BufferedReader bufReader = new BufferedReader(stringReader);

        String tempLineFeedChar = "\r";
        LineFeed1LineReader lineFeed1LineReader = new LineFeed1LineReader(
                bufReader, tempLineFeedChar);

        // �e�X�g���{
        String resutl = lineFeed1LineReader.readLine();

        // ����
        assertEquals("aaa,aaa,aaa", resutl);
    }
}

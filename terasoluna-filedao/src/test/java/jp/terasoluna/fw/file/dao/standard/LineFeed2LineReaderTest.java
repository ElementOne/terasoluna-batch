/*
 * $Id: LineFeed2LineReaderTest.java 5354 2007-10-03 06:06:25Z anh $
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
 * {@link jp.terasoluna.fw.file.dao.standard.LineFeed2LineReader} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �͂ݕ����Ȃ��A�s��؂蕶����2�����̏ꍇ�̃t�@�C������1�s���̕�������擾���鏈�����s���B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.LineFeed2LineReader
 */
public class LineFeed2LineReaderTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(LineFeed2LineReaderTest.class);
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
    public LineFeed2LineReaderTest(String name) {
        super(name);
    }

    /**
     * testLineFeed2LineReaderReaderString01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE,F <br>
     * <br>
     * ���͒l�F(����) reader:Reader�̃C���X�^���X<br>
     * (����) lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.reader:����reader�Ɠ���̃C���X�^���X<br>
     * (��ԕω�) this.lineFeedChar:����lineFeedChar�Ɠ���̃C���X�^���X<br>
     * <br>
     * ����P�[�X�B<br>
     * �n���ꂽ�����̏������I�u�W�F�N�g����������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed2LineReader01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        LineFeed2LineReader result = new LineFeed2LineReader(reader,
                lineFeedChar);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(reader, UTUtil.getPrivateField(result, "reader"));
        assertEquals(lineFeedChar, UTUtil.getPrivateField(result,
                "lineFeedChar"));
    }

    /**
     * testLineFeed2LineReaderReaderString02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) reader:null<br>
     * (����) lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalArgumentException<br>
     * �E���b�Z�[�W�F"reader is required."<br>
     * <br>
     * ��O�B<br>
     * ����reader��null�̏ꍇ�ɁA�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed2LineReader02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        BufferedReader reader = null;
        String lineFeedChar = "\r\n";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            new LineFeed2LineReader(reader, lineFeedChar);
            fail("IllegalArgumentException���X���[����܂���ł���");
        } catch (IllegalArgumentException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertSame(IllegalArgumentException.class, e.getClass());
            assertEquals("reader is required.", e.getMessage());
        }
    }

    /**
     * testLineFeed2LineReaderReaderString03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) reader:Reader�̃C���X�^���X<br>
     * (����) lineFeedChar:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalArgumentException<br>
     * �E���b�Z�[�W�F"lineFeedChar is required."<br>
     * <br>
     * ��O�B<br>
     * ����lineFeedChar��null�̏ꍇ�ɁA�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed2LineReader03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = null;

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            new LineFeed2LineReader(reader, lineFeedChar);
            fail("IllegalArgumentException���X���[����܂���ł���");
        } catch (IllegalArgumentException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertSame(IllegalArgumentException.class, e.getClass());
            assertEquals("lineFeedChar is required.", e.getMessage());
        }
    }

    /**
     * testLineFeed2LineReaderReaderString04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) reader:Reader�̃C���X�^���X<br>
     * (����) lineFeedChar:"123"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalArgumentException<br>
     * �E���b�Z�[�W�F"lineFeedChar should be defined by 2 digit of character string."<br>
     * <br>
     * ��O�B<br>
     * ����lineFeedChar��2��������ł͂Ȃ��ꍇ�ɁA�������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLineFeed2LineReader04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        byte[] buf = {};
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "123";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            new LineFeed2LineReader(reader, lineFeedChar);
            fail("IllegalArgumentException���X���[����܂���ł���");
        } catch (IllegalArgumentException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertSame(IllegalArgumentException.class, e.getClass());
            assertEquals(
                    "lineFeedChar should be defined by 2 digit of character string.",
                    e.getMessage());
        }
    }

    /**
     * testReadLine01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.reader:���������Ȃ�Reader�C���X�^���X<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * (���) Reader.ready():IOException��O����������B<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:�ȉ��̏�������FileException����������<br>
     * �E������O�FIOException(Reader.ready()�Ŕ�����������)<br>
     * �E���b�Z�[�W�F"Reader control operation was failed."<br>
     * <br>
     * ��O�B<br>
     * Reader�̏�ԃ`�F�b�N�ŗ�O�����������ꍇ�A FileException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine01() throws Exception {
        // Mock�쐬
        Reader reader = EasyMock.createMock(Reader.class);
        EasyMock.expect(reader.read()).andReturn(null).andThrow(
                new IOException());
        EasyMock.replay(reader);

        // �e�X�g�Ώۂ̃C���X�^���X��
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            lineFeed2LineReader.readLine();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertSame(FileException.class, e.getClass());
            assertEquals("Reader control operation was failed.", e.getMessage());
            assertSame(IOException.class, e.getCause().getClass());
        }
    }

    /**
     * testReadLine02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) this.reader:�ȉ��̏�������Reader�C���X�^���X<br>
     * ""(��)<br>
     * <br>
     * ���f�[�^���Ȃ��B<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     * <br>
     * ����p�^�[���B<br>
     * Reader�Ɏ擾��񂪖����ꍇ�A��̕����񂪕ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        byte[] buf = "".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buf);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String result = lineFeed2LineReader.readLine();

        // �ԋp�l�̊m�F
        assertEquals("", result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testReadLine03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) this.reader:�ȉ��̏�������Reader�C���X�^���X<br>
     * "aaa,aaa,aaa\r\n"<br>
     * <br>
     * ���Ō�̍s��؂蕶��������1�s�f�[�^<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa,aaa,aaa"<br>
     * <br>
     * ����p�^�[���B<br>
     * Reader�ɂP�s�̏��݂̂���ꍇ(���Ō�ɍs��؂蕶������)�A���̂P�s�̏�񂪕ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String byteParm = "aaa,aaa,aaa\r\n";
        InputStream inputStream = new ByteArrayInputStream(byteParm.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String result = lineFeed2LineReader.readLine();

        // �ԋp�l�̊m�F
        assertEquals("aaa,aaa,aaa", result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testReadLine04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) this.reader:�ȉ��̏�������Reader�C���X�^���X<br>
     * "\"aaa\",\"aaa\",\"aa\r\na\"\r\n"<br>
     * <br>
     * ���s��؂蕶�����������镡���s�f�[�^<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"\"aaa\",\"aaa\",\"aa"<br>
     * <br>
     * ����p�^�[���B<br>
     * Reader�ɕ����s�̏�񂪂���ꍇ�A�ŏ��̂P�s�̏��̂ݕԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String byteParm = "\"aaa\",\"aaa\",\"aa\r\na\"\r\n";
        InputStream inputStream = new ByteArrayInputStream(byteParm.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String result = lineFeed2LineReader.readLine();

        // �ԋp�l�̊m�F
        assertEquals("\"aaa\",\"aaa\",\"aa", result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testReadLine05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) this.reader:���������Ȃ�Reader�C���X�^���X<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * (���) Reader.read():IOException��O����������B<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:�ȉ��̏�������FileException����������<br>
     * �E������O�FIOException(Reader.read()�Ŕ�����������)<br>
     * �E���b�Z�[�W�F"Reader control operation was failed."<br>
     * <br>
     * ��O�B<br>
     * Reader�̓ǂݏ����ŗ�O�����������ꍇ�AFileException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine05() throws Exception {
        // Mock�쐬
        Reader reader = EasyMock.createMock(Reader.class);
        EasyMock.expect(reader.read()).andReturn(null).andThrow(
                new IOException());
        EasyMock.replay(reader);

        // �e�X�g�Ώۂ̃C���X�^���X��
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        try {
            lineFeed2LineReader.readLine();
            fail("FileException���X���[����܂���ł���");
        } catch (FileException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertSame(FileException.class, e.getClass());
            assertEquals("Reader control operation was failed.", e.getMessage());
            assertSame(IOException.class, e.getCause().getClass());
        }
    }

    /**
     * testReadLine06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) this.reader:�ȉ��̏�������Reader�C���X�^���X<br>
     * "999,999,999"<br>
     * <br>
     * ���s��؂蕶�����܂߂Ȃ��f�[�^<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"999,999,999"<br>
     * <br>
     * ����p�^�[���B<br>
     * Reader�ɂP�s�̏��݂̂���ꍇ(���s��؂蕶���Ȃ�)�A���̂P�s�̏�񂪕ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String byteParm = "999,999,999";
        InputStream inputStream = new ByteArrayInputStream(byteParm.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String result = lineFeed2LineReader.readLine();

        // �ԋp�l�̊m�F
        assertEquals(byteParm, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testReadLine07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) this.reader:�ȉ��̏�������Reader�C���X�^���X<br>
     * "101010,101010,101010\rn"<br>
     * <br>
     * ���s��؂蕶���̍ŏ��̌������݂̂���f�[�^<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"101010,101010,101010\rn"<br>
     * <br>
     * ����p�^�[���B<br>
     * �s��؂蕶���̐擪�����̂݃f�[�^�Ɋ܂܂�Ă���ꍇ�A���̈ʒu�ōs��؂菈�����s��Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String byteParm = "101010,101010,101010\rn";
        InputStream inputStream = new ByteArrayInputStream(byteParm.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String result = lineFeed2LineReader.readLine();

        // �ԋp�l�̊m�F
        assertEquals("101010,101010,101010\rn", result);

        // ��ԕω��̊m�F
        // �Ȃ�

    }

    /**
     * testReadLine08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E,F <br>
     * <br>
     * ���͒l�F(���) this.reader:�ȉ��̏�������Reader�C���X�^���X<br>
     * "������\r\n"<br>
     * <br>
     * ���s��؂蕶���̍ŏ��̌������݂̂���f�[�^<br>
     * (���) this.lineFeedChar:"\r\n"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"������"<br>
     * <br>
     * ������̒��ɑS�p�������܂܂�Ă���ꍇ�B�P�s�̏�񂪕ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadLine08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String byteParm = "������\r\n";
        InputStream inputStream = new ByteArrayInputStream(byteParm.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String lineFeedChar = "\r\n";
        LineFeed2LineReader lineFeed2LineReader = new LineFeed2LineReader(
                reader, lineFeedChar);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �e�X�g�Ώۂ̃C���X�^���X�����ɐݒ�ς�

        // �e�X�g���{
        String result = lineFeed2LineReader.readLine();

        // �ԋp�l�̊m�F
        assertEquals("������", result);

        // ��ԕω��̊m�F
        // �Ȃ�

    }
}

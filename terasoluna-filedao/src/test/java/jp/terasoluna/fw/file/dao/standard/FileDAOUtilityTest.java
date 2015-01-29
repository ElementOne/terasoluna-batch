/*
 * $Id: FileDAOUtilityTest.java 5230 2007-09-28 10:04:13Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.annotation.TrimType;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.FileDAOUtility} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �p�f�B���O�A�g����������񋟂���B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.FileDAOUtility
 */
public class FileDAOUtilityTest {

    @Before
    public void setUp() throws Exception {
        VMOUTUtil.initialize();
    }

    /**
     * testPadding01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:0<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O)<br>
     * ����columnBytes��columnString��菬�����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding01() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 0;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:1<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O)<br>
     * ����columnBytes��columnString�̃o�C�g���Ɠ����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding02() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 1;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"  1"(1�̍��ɔ��p�X�y�[�X2����)<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O)<br>
     * ����columnString�ɕs�����������������f�[�^�̍���paddingChar�Ŗ��߂ĕ����񂪎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding03() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("  1", result);
    }

    /**
     * testPadding04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:0<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (�E�p�f�B���O)<br>
     * ����columnBytes��columnString��菬�����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding04() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 0;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:1<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (�E�p�f�B���O)<br>
     * ����columnBytes��columnString�̃o�C�g���Ɠ����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding05() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 1;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1  "(1�̉E�ɔ��p�X�y�[�X2����)<br>
     * <br>
     * ����P�[�X<br>
     * (�E�p�f�B���O)<br>
     * ����columnString�ɕs�����������������f�[�^�̉E��paddingChar�Ŗ��߂ĕ����񂪎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding06() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1  ", result);
    }

    /**
     * testPadding07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (�p�f�B���O�Ȃ�)<br>
     * columnString�����̂܂܎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding07() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:'�@'(�S�p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"Padding char is not half-width character."<br>
     * <br>
     * �ُ�P�[�X<br>
     * (�p�f�B���O����)<br>
     * �p�f�B���O�����͔��p�����ł͂Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding08() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = '�@';
        PaddingType paddingType = PaddingType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.padding(columnString, fileEncoding, columnBytes,
                    paddingChar, paddingType);
            fail("FileException��O���������܂���ł����B");
        } catch (FileException e) {
            // ����
            assertEquals("Padding char is not half-width character.", e
                    .getMessage());
        }
    }

    /**
     * testPadding09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:"XXX"<br>
     * �����݂��Ȃ��G���R�[�f�B���O<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"Specified Encoding : XXX is not supported"<br>
     * �E������O�FUnsupportedEncodingException<br>
     * <br>
     * �ُ�P�[�X<br>
     * (�p�f�B���O����)<br>
     * ���݂��Ȃ��G���R�[�f�B���O���w�肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding09() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "XXX";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.padding(columnString, fileEncoding, columnBytes,
                    paddingChar, paddingType);
            fail("FileException��O���������܂���ł����B");
        } catch (FileException e) {
            // ����
            assertEquals("Specified Encoding : XXX is not supported", e
                    .getMessage());
            assertTrue(e.getCause() instanceof UnsupportedEncodingException);
        }
    }

    /**
     * testPadding10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:"XXX"<br>
     * �����݂��Ȃ��G���R�[�f�B���O<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:'�@'(�S�p�󔒕���)<br>
     * (����) paddingType:PaddingType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (�p�f�B���O�Ȃ�)<br>
     * �������̏�ԂƊ֌W�Ȃ�columnString�����̂܂܎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding10() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "XXX";
        int columnBytes = 3;
        char paddingChar = '�@';
        PaddingType paddingType = PaddingType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:0<br>
     * (����) paddingChar:'!'(���p����)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O�A�Ώۃf�[�^���󕶎�)<br>
     * ����columnBytes��columnString��菬�����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding11() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 0;
        char paddingChar = '!';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testPadding12() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:0<br>
     * (����) paddingChar:'!'(���p����)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O�A�Ώۃf�[�^���󕶎�)<br>
     * ����columnBytes��columnString�̃o�C�g���Ɠ����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding12() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 0;
        char paddingChar = '!';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testPadding13() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:'!'(���p����)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"!!!"<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O�A�Ώۃf�[�^���󕶎�)<br>
     * ����columnString�ɕs�����������������f�[�^�̍���paddingChar�Ŗ��߂ĕ����� �擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding13() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = '!';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("!!!", result);
    }

    /**
     * testPadding14() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:0<br>
     * (����) paddingChar:'!'(���p����)<br>
     * (����) paddingType:PaddingType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (�E�p�f�B���O�A�Ώۃf�[�^���󕶎�)<br>
     * ����columnBytes��columnString��菬�����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding14() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 0;
        char paddingChar = '!';
        PaddingType paddingType = PaddingType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testPadding15() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:0<br>
     * (����) paddingChar:'!'(���p����)<br>
     * (����) paddingType:PaddingType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (�E�p�f�B���O�A�Ώۃf�[�^���󕶎�)<br>
     * ����columnBytes��columnString�̃o�C�g���Ɠ����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding15() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 0;
        char paddingChar = '!';
        PaddingType paddingType = PaddingType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testPadding16() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:'!'(���p����)<br>
     * (����) paddingType:PaddingType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"!!!"<br>
     * <br>
     * ����P�[�X<br>
     * (�E�p�f�B���O�A�Ώۃf�[�^���󕶎�)<br>
     * ����columnString�ɕs�����������������f�[�^�̉E��paddingChar�Ŗ��߂ĕ����� �擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding16() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = '!';
        PaddingType paddingType = PaddingType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("!!!", result);
    }

    /**
     * testPadding17() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:'!'(���p����)<br>
     * (����) paddingType:PaddingType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (�p�f�B���O�Ȃ��A�Ώۃf�[�^���󕶎�)<br>
     * columnString�����̂܂܎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding17() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = '!';
        PaddingType paddingType = PaddingType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testPadding18() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) columnString:null<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:NullPointerException����������B<br>
     * <br>
     * �ُ�P�[�X<br>
     * ����columnString��null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding18() throws Exception {
        // �O����(����)
        String columnString = null;
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.padding(columnString, fileEncoding, columnBytes,
                    paddingChar, paddingType);
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����
            assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * testPadding19() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:null<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:NullPointerException����������B<br>
     * <br>
     * �ُ�P�[�X<br>
     * ����fileEncoding��null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding19() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = null;
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.padding(columnString, fileEncoding, columnBytes,
                    paddingChar, paddingType);
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����
            assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * testPadding20() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:-3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O)<br>
     * ����columnBytes��0��菬�����ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding20() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = -3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding21() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:null<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * ����paddingType��null�̏ꍇ�A�������s��Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding21() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = null;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testPadding22() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"�P"(�S�p����)<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:" 1"(1�̍��ɔ��p�X�y�[�X1����)<br>
     * <br>
     * ����P�[�X<br>
     * (���p�f�B���O�A�S�p����)<br>
     * ����columnString�ɕs�����������������f�[�^�̍���paddingChar�� ���߂ĕ����񂪎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding22() throws Exception {
        // �O����(����)
        String columnString = "�P";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals(" �P", result);
    }

    /**
     * testPadding23() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"�P"(�S�p����)<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1 "(1�̉E�ɔ��p�X�y�[�X1����)<br>
     * <br>
     * ����P�[�X<br>
     * (�E�p�f�B���O�A�S�p����)<br>
     * ����columnString�ɕs�����������������f�[�^�̉E��paddingChar�Ŗ��߂ĕ����񂪎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding23() throws Exception {
        // �O����(����)
        String columnString = "�P";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("�P ", result);
    }

    /**
     * testPadding24() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"�P"(�S�p����)<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) columnBytes:3<br>
     * (����) paddingChar:' '(���p�󔒕���)<br>
     * (����) paddingType:PaddingType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (�p�f�B���O�Ȃ��A�S�p����)<br>
     * columnString�����̂܂܎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testPadding24() throws Exception {
        // �O����(����)
        String columnString = "�P";
        String fileEncoding = "Shift_JIS";
        int columnBytes = 3;
        char paddingChar = ' ';
        PaddingType paddingType = PaddingType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes, paddingChar, paddingType);

        // ����
        assertNotNull(result);
        assertEquals("�P", result);
    }

    /**
     * testTrim01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (���g����)<br>
     * trimChar�Őݒ肵��������columnString�ɂȂ��ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim01() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testTrim02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1aaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1aaa"<br>
     * <br>
     * ����P�[�X<br>
     * (���g����)<br>
     * ������̍�����trimChar�Őݒ肵���������Ȃ��ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim02() throws Exception {
        // �O����(����)
        String columnString = "1aaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("1aaa", result);
    }

    /**
     * testTrim03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa1aaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1aaa"<br>
     * <br>
     * ����P�[�X<br>
     * (���g����)<br>
     * colum�̕�����̐擪���珇��trimChar�Őݒ肵���������폜����B trimChar�ƈقȂ镶�������ꂽ���_�ŏ������I��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim03() throws Exception {
        // �O����(����)
        String columnString = "aaa1aaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("1aaa", result);
    }

    /**
     * testTrim04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (�E�g����)<br>
     * trimChar�Őݒ肵���������Ȃ��ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim04() throws Exception {
        // �O����(����)
        String columnString = "1";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testTrim05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa1"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"aaa1"<br>
     * <br>
     * ����P�[�X<br>
     * (�E�g����)<br>
     * ������̉E����trimChar�Őݒ肵���������Ȃ��ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim05() throws Exception {
        // �O����(����)
        String columnString = "aaa1";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("aaa1", result);
    }

    /**
     * testTrim06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa1aaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"aaa1"<br>
     * <br>
     * ����P�[�X<br>
     * (�E�g����)<br>
     * colum�̕�����̌�납�珇��trimChar�Őݒ肵���������폜����BtrimChar�ƈقȂ镶�������ꂽ���_�ŏ������I��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim06() throws Exception {
        // �O����(����)
        String columnString = "aaa1aaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("aaa1", result);
    }

    /**
     * testTrim07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"aaa"<br>
     * <br>
     * ����P�[�X<br>
     * (�g�����Ȃ�)<br>
     * ����p�^�[���B<br>
     * column�����̂܂ܕԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim07() throws Exception {
        // �O����(����)
        String columnString = "aaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("aaa", result);
    }

    /**
     * testTrim08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'��'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"Trim char is not half-width character."<br>
     * <br>
     * �ُ�P�[�X<br>
     * (�g��������)<br>
     * �p�f�B���O�����͔��p�����ł͂Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim08() throws Exception {
        // �O����(����)
        String columnString = "aaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = '��';
        TrimType trimType = TrimType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.trim(columnString, fileEncoding, trimChar, trimType);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����
            assertEquals("Trim char is not half-width character.", e
                    .getMessage());
        }
    }

    /**
     * testTrim09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa"<br>
     * (����) fileEncoding:"XXX"<br>
     * �����݂��Ȃ��G���R�[�f�B���O<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:�ȉ��̐ݒ������FileException����������B<br>
     * �E���b�Z�[�W�F"Specified Encoding : XXX is not supported"<br>
     * �E������O�FUnsupportedEncodingException<br>
     * <br>
     * �ُ�P�[�X<br>
     * (�g��������)<br>
     * ���݂��Ȃ��G���R�[�f�B���O���w�肳�ꂽ�ꍇ�A��O���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim09() throws Exception {
        // �O����(����)
        String columnString = "aaa";
        String fileEncoding = "XXX";
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.trim(columnString, fileEncoding, trimChar, trimType);
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����
            assertEquals("Specified Encoding : XXX is not supported", e
                    .getMessage());
            assertTrue(e.getCause() instanceof UnsupportedEncodingException);
        }
    }

    /**
     * testTrim10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa"<br>
     * (����) fileEncoding:"XXX"<br>
     * �����݂��Ȃ��G���R�[�f�B���O<br>
     * (����) trimChar:'��'<br>
     * (����) trimType:TrimType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"aaa"<br>
     * <br>
     * ����P�[�X<br>
     * (�g�����Ȃ�)<br>
     * �������̏�ԂƊ֌W�Ȃ�columnString�����̂܂܎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim10() throws Exception {
        // �O����(����)
        String columnString = "aaa";
        String fileEncoding = "XXX";
        char trimChar = '��';
        TrimType trimType = TrimType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("aaa", result);
    }

    /**
     * testTrim11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:' '<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (���g�����A�Ώۃf�[�^���󕶎�)<br>
     * trimChar�Őݒ肵��������columnString�ɂȂ��ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim11() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testTrim12() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (�E�g�����A�Ώۃf�[�^���󕶎�)<br>
     * trimChar�Őݒ肵���������Ȃ��ꍇ�A�������s���Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim12() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testTrim13() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (�g�����Ȃ��A�Ώۃf�[�^���󕶎�)<br>
     * ����p�^�[���B<br>
     * column�����̂܂ܕԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim13() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testTrim14() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa1aaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.BOTH<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (���g����)<br>
     * colum�̕�����̐擪���珇��trimChar�Őݒ肵���������폜����BtrimChar�ƈقȂ镶�������ꂽ���_�ŏ������I��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim14() throws Exception {
        // �O����(����)
        String columnString = "aaa1aaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.BOTH;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("1", result);
    }

    /**
     * testTrim15() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:""<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.BOTH<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1"<br>
     * <br>
     * ����P�[�X<br>
     * (���g�����A�Ώۃf�[�^���󕶎�)<br>
     * �Ώۃf�[�^�����̂܂܎擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim15() throws Exception {
        // �O����(����)
        String columnString = "";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.BOTH;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testTrim16() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC, D, E, G <br>
     * <br>
     * ���͒l�F(����) columnString:null<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:NullPointerException����������B<br>
     * <br>
     * �ُ�P�[�X<br>
     * ����columnString��null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim16() throws Exception {
        // �O����(����)
        String columnString = null;
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.trim(columnString, fileEncoding, trimChar, trimType);
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����
            assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * testTrim17() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC, D, E, G <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa1aaa"<br>
     * (����) fileEncoding:null<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:NullPointerException����������B<br>
     * <br>
     * �ُ�P�[�X<br>
     * ����fileEncoding��null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim17() throws Exception {
        // �O����(����)
        String columnString = "aaa1aaa";
        String fileEncoding = null;
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        try {
            // �e�X�g���{
            FileDAOUtility.trim(columnString, fileEncoding, trimChar, trimType);
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����
            assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * testTrim18() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa1aaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:null<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"aaa1aaa"<br>
     * <br>
     * ����P�[�X<br>
     * ����trimType��null�̏ꍇ�A�������s��Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim18() throws Exception {
        // �O����(����)
        String columnString = "aaa1aaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = null;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("aaa1aaa", result);
    }

    /**
     * testTrim19() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaaaaa"<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.BOTH<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * (���g����)<br>
     * �S�������g�����Ώە����̏ꍇ�A�󕶎��ɂȂ邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim19() throws Exception {
        // �O����(����)
        String columnString = "aaaaaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.BOTH;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testTrim20() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa�Paaa"�i�S�p�����j<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.LEFT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"�Paaa"<br>
     * <br>
     * ����P�[�X<br>
     * (���g�����A�S�p����)<br>
     * colum�̕�����̐擪���珇��trimChar�Őݒ肵���������폜����B trimChar�ƈقȂ镶�������ꂽ���_�ŏ������I��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim20() throws Exception {
        // �O����(����)
        String columnString = "aaa�Paaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.LEFT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("�Paaa", result);
    }

    /**
     * testTrim21() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa�Paaa"�i�S�p�����j<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.RIGHT<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"aaa�P"<br>
     * <br>
     * ����P�[�X<br>
     * (�E�g�����A�S�p����)<br>
     * colum�̕�����̌�납�珇��trimChar�Őݒ肵���������폜����B trimChar�ƈقȂ镶�������ꂽ���_�ŏ������I��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim21() throws Exception {
        // �O����(����)
        String columnString = "aaa�Paaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.RIGHT;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("aaa�P", result);
    }

    /**
     * testTrim22() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa�Paaa"�i�S�p�����j<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.NONE<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"aaa�Paaa"<br>
     * <br>
     * ����P�[�X<br>
     * (�g�����Ȃ��A�S�p����)<br>
     * ����p�^�[���B<br>
     * column�����̂܂ܕԋp�����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim22() throws Exception {
        // �O����(����)
        String columnString = "aaa�Paaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.NONE;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("aaa�Paaa", result);
    }

    /**
     * testTrim23() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) columnString:"aaa�Paaa"(�S�p�����j<br>
     * (����) fileEncoding:Shift_JIS<br>
     * (����) trimChar:'a'<br>
     * (����) trimType:TrimType.BOTH<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"�P"<br>
     * <br>
     * ����P�[�X<br>
     * (���g�����A�S�p����)<br>
     * colum�̕�����̐擪���珇��trimChar�Őݒ肵���������폜����B trimChar�ƈقȂ镶�������ꂽ���_�ŏ������I��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Test
    public void testTrim23() throws Exception {
        // �O����(����)
        String columnString = "aaa�Paaa";
        String fileEncoding = "Shift_JIS";
        char trimChar = 'a';
        TrimType trimType = TrimType.BOTH;

        // �e�X�g���{
        String result = FileDAOUtility.trim(columnString, fileEncoding,
                trimChar, trimType);

        // ����
        assertNotNull(result);
        assertEquals("�P", result);
    }

    /**
     * testIsHalfWidthChar01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) fileEncoding:"Shift_JIS"<br>
     * (����) checkChar:','<br>
     * (���) encodingCache:�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * (��ԕω�) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F',' | value�FTRUE<br>
     * (��ԕω�) Map#put():2��Ă΂��<br>
     * <br>
     * ����P�[�X<br>
     * (�L���b�V���Ȃ�)<br>
     * �G���R�[�f�B���O�ɍ������p���������͂��ꂽ�ꍇ�ATRUE���Ԃ���邱�Ƃ��m�F����B<br>
     * �܂��A���̏�񂪃L���b�V���Ɏc�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsHalfWidthChar01() throws Exception {
        // �O����(����)
        String fileEncoding = "Shift_JIS";
        char checkChar = ',';

        // �O����(���)
        Map<String, Map<Character, Boolean>> encodingCache = (Map<String, Map<Character, Boolean>>) UTUtil
                .getPrivateField(FileDAOUtility.class, "encodingCache");
        encodingCache.clear();

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(FileDAOUtility.class,
                "isHalfWidthChar", new Class[] { String.class, char.class },
                new Object[] { fileEncoding, checkChar });

        // ����
        assertTrue(Boolean.class.cast(result));

        assertEquals(1, encodingCache.size());
        assertTrue(encodingCache.containsKey(fileEncoding));

        Map<Character, Boolean> shiftJISCacheMap = encodingCache
                .get(fileEncoding);
        assertEquals(1, shiftJISCacheMap.size());
        assertTrue(shiftJISCacheMap.containsKey(checkChar));
        assertTrue(Boolean.class.cast(shiftJISCacheMap.get(checkChar)));

        assertEquals(2, VMOUTUtil.getCallCount(Map.class, "put"));

        // �����Ώۏ�����
        encodingCache.clear();
    }

    /**
     * testIsHalfWidthChar02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) fileEncoding:"Shift_JIS"<br>
     * (����) checkChar:','<br>
     * (���) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"UTF-8"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F',' | value�FTRUE<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * (��ԕω�) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"UTF-8"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F',' | value�FTRUE<br>
     * �ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F',' | value�FTRUE<br>
     * (��ԕω�) Map#put():2��Ă΂��<br>
     * <br>
     * ����P�[�X<br>
     * (�L���b�V������A�G���R�[�f�B���O�ɑ΂���L���b�V���Ȃ�)<br>
     * �G���R�[�f�B���O�ɍ������p���������͂��ꂽ�ꍇ�ATRUE���Ԃ���邱�Ƃ��m�F����B<br>
     * �܂��A���̏�񂪃L���b�V���Ɏc�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsHalfWidthChar02() throws Exception {
        // �O����(����)
        String fileEncoding = "Shift_JIS";
        char checkChar = ',';

        // �O����(���)
        Map<String, Map<Character, Boolean>> encodingCache = (Map<String, Map<Character, Boolean>>) UTUtil
                .getPrivateField(FileDAOUtility.class, "encodingCache");
        encodingCache.clear();

        Map<Character, Boolean> inputEncodingCache = new ConcurrentHashMap<Character, Boolean>();

        inputEncodingCache.put(checkChar, Boolean.TRUE);
        encodingCache.put("UTF-8", inputEncodingCache);

        VMOUTUtil.initialize();

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(FileDAOUtility.class,
                "isHalfWidthChar", new Class[] { String.class, char.class },
                new Object[] { fileEncoding, checkChar });

        // ����(�߂�l)
        assertTrue(Boolean.class.cast(result));

        // ����(encodingCache)
        assertEquals(2, encodingCache.size());

        // ����(UTF-8)
        assertTrue(encodingCache.containsKey("UTF-8"));
        Map<Character, Boolean> uTF8CacheMap = encodingCache.get("UTF-8");
        assertEquals(1, uTF8CacheMap.size());
        assertTrue(uTF8CacheMap.containsKey(checkChar));
        assertTrue(Boolean.class.cast(uTF8CacheMap.get(checkChar)));

        // ����(Shif_JIS)
        assertTrue(encodingCache.containsKey(fileEncoding));
        Map<Character, Boolean> shiftJISCacheMap = encodingCache
                .get(fileEncoding);
        assertEquals(1, shiftJISCacheMap.size());
        assertTrue(shiftJISCacheMap.containsKey(checkChar));
        assertTrue(Boolean.class.cast(shiftJISCacheMap.get(checkChar)));

        assertEquals(2, VMOUTUtil.getCallCount(Map.class, "put"));
    }

    /**
     * testIsHalfWidthChar03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) fileEncoding:"Shift_JIS"<br>
     * (����) checkChar:','<br>
     * (���) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F',' | value�FTRUE<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * (��ԕω�) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F',' | value�FTRUE<br>
     * (��ԕω�) Map#put():�Ă΂�Ȃ�<br>
     * <br>
     * ����P�[�X<br>
     * (�L���b�V������A�G���R�[�f�B���O�E�`�F�b�N�����ɑ΂���L���b�V������)<br>
     * �G���R�[�f�B���O�ɍ������p���������͂��ꂽ�ꍇ�ATRUE���Ԃ���邱�Ƃ��m�F����B<br>
     * �܂��A���̏�񂪃L���b�V������擾���ꂽ���Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsHalfWidthChar03() throws Exception {
        // �O����(����)
        String fileEncoding = "Shift_JIS";
        char checkChar = ',';

        // �O����(���)
        Map<String, Map<Character, Boolean>> encodingCache = (Map<String, Map<Character, Boolean>>) UTUtil
                .getPrivateField(FileDAOUtility.class, "encodingCache");
        encodingCache.clear();

        Map<Character, Boolean> inputEncodingCache = new ConcurrentHashMap<Character, Boolean>();

        inputEncodingCache.put(checkChar, Boolean.TRUE);
        encodingCache.put("Shift_JIS", inputEncodingCache);

        VMOUTUtil.initialize();
        // �e�X�g���{
        Object result = UTUtil.invokePrivate(FileDAOUtility.class,
                "isHalfWidthChar", new Class[] { String.class, char.class },
                new Object[] { fileEncoding, checkChar });

        // ����(�߂�l)
        assertTrue(Boolean.class.cast(result));

        // ����(encodingCache)
        assertEquals(1, encodingCache.size());

        assertTrue(encodingCache.containsKey(fileEncoding));
        Map<Character, Boolean> shiftJISCacheMap = encodingCache
                .get(fileEncoding);
        assertEquals(1, shiftJISCacheMap.size());
        assertTrue(shiftJISCacheMap.containsKey(checkChar));
        assertTrue(Boolean.class.cast(shiftJISCacheMap.get(checkChar)));
        assertFalse(VMOUTUtil.isCalled(Map.class, "put"));
    }

    /**
     * testIsHalfWidthChar04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) fileEncoding:"Shift_JIS"<br>
     * (����) checkChar:','<br>
     * (���) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F'�A' | value�FFALSE<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * (��ԕω�) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F'�A' | value�FFALSE<br>
     * - key�F',' | value�FTRUE<br>
     * (��ԕω�) Map#put():1��Ă΂��<br>
     * <br>
     * ����P�[�X<br>
     * (�L���b�V������A�G���R�[�f�B���O�ɑ΂���L���b�V���͂��邪�A �`�F�b�N�����ɑ΂���L���b�V���ł͂Ȃ��B)<br>
     * �G���R�[�f�B���O�ɍ������p���������͂��ꂽ�ꍇ�ATRUE���Ԃ���邱�Ƃ��m�F����B<br>
     * �܂��A���̏�񂪃L���b�V������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsHalfWidthChar04() throws Exception {
        // �O����(����)
        String fileEncoding = "Shift_JIS";
        char checkChar = ',';

        // �O����(���)
        Map<String, Map<Character, Boolean>> encodingCache = (Map<String, Map<Character, Boolean>>) UTUtil
                .getPrivateField(FileDAOUtility.class, "encodingCache");
        encodingCache.clear();

        Map<Character, Boolean> inputEncodingCache = new ConcurrentHashMap<Character, Boolean>();
        inputEncodingCache.put('�A', Boolean.FALSE);
        encodingCache.put("Shift_JIS", inputEncodingCache);

        VMOUTUtil.initialize();

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(FileDAOUtility.class,
                "isHalfWidthChar", new Class[] { String.class, char.class },
                new Object[] { fileEncoding, checkChar });

        // ����(�߂�l)
        assertTrue(Boolean.class.cast(result));

        // ����(encodingCache)
        assertEquals(1, encodingCache.size());
        assertTrue(encodingCache.containsKey(fileEncoding));

        Map<Character, Boolean> shiftJISCacheMap = encodingCache
                .get(fileEncoding);
        assertEquals(2, shiftJISCacheMap.size());
        assertTrue(shiftJISCacheMap.containsKey(checkChar));
        assertFalse(Boolean.class.cast(shiftJISCacheMap.get('�A')));
        assertTrue(Boolean.class.cast(shiftJISCacheMap.get(checkChar)));

        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
    }

    /**
     * testIsHalfWidthChar05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC, D, E <br>
     * <br>
     * ���͒l�F(����) fileEncoding:"Shift_JIS"<br>
     * (����) checkChar:'�A'<br>
     * (���) encodingCache:�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     * (��ԕω�) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * - key�F'�A' | value�FFALSE<br>
     * (��ԕω�) Map#put():2��Ă΂��<br>
     * <br>
     * ����P�[�X<br>
     * (�L���b�V���Ȃ�)<br>
     * �G���R�[�f�B���O�ɍ����S�p���������͂��ꂽ�ꍇ�AFALSE���Ԃ���邱�Ƃ��m�F����B<br>
     * �܂��A���̏�񂪃L���b�V���Ɏc�邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsHalfWidthChar05() throws Exception {
        // �O����(����)
        String fileEncoding = "Shift_JIS";
        char checkChar = '�A';

        // �O����(���)
        Map<String, Map<Character, Boolean>> encodingCache = (Map<String, Map<Character, Boolean>>) UTUtil
                .getPrivateField(FileDAOUtility.class, "encodingCache");
        encodingCache.clear();

        // �e�X�g���{
        Object result = UTUtil.invokePrivate(FileDAOUtility.class,
                "isHalfWidthChar", new Class[] { String.class, char.class },
                new Object[] { fileEncoding, checkChar });

        // ����(�߂�l)
        assertFalse(Boolean.class.cast(result));

        // ����(encodingCache)
        assertEquals(1, encodingCache.size());
        assertTrue(encodingCache.containsKey(fileEncoding));

        Map<Character, Boolean> shiftJISCacheMap = encodingCache
                .get(fileEncoding);
        assertEquals(1, shiftJISCacheMap.size());
        assertTrue(shiftJISCacheMap.containsKey(checkChar));
        assertFalse(Boolean.class.cast(shiftJISCacheMap.get(checkChar)));

        assertEquals(2, VMOUTUtil.getCallCount(Map.class, "put"));

        // �����Ώۏ�����
        encodingCache.clear();
    }

    /**
     * testIsHalfWidthChar06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileEncoding:"XXX"<br>
     * (����) checkChar:','<br>
     * (���) encodingCache:�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) encodingCache:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"Shift_JIS"<br>
     * value�F�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * (��ԕω�) Map#put():1��Ă΂��<br>
     * (��ԕω�) -:�ȉ��̏�������FileException����������B<br>
     * �E���b�Z�[�W�F"Specified Encoding : XXX is not supported"<br>
     * �E������O�FUnsupportedEncodingException<br>
     * <br>
     * �ُ�P�[�X<br>
     * (�L���b�V���Ȃ�)<br>
     * ���݂��Ȃ��G���R�[�f�B���O�����͂��ꂽ�ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsHalfWidthChar06() throws Exception {
        // �O����(����)
        String fileEncoding = "XXX";
        char checkChar = ',';

        // �O����(���)
        Map<String, Map<Character, Boolean>> encodingCache = (Map<String, Map<Character, Boolean>>) UTUtil
                .getPrivateField(FileDAOUtility.class, "encodingCache");
        encodingCache.clear();

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(FileDAOUtility.class, "isHalfWidthChar",
                    new Class[] { String.class, char.class }, new Object[] {
                            fileEncoding, checkChar });
            fail("FileException���������܂���ł����B");
        } catch (FileException e) {
            // ����(��O)
            assertTrue(e instanceof FileException);
            assertEquals("Specified Encoding : XXX is not supported", e
                    .getMessage());
            assertTrue(e.getCause() instanceof UnsupportedEncodingException);

            // ����(encodingCache)
            assertEquals(1, encodingCache.size());

            assertTrue(encodingCache.containsKey(fileEncoding));
            Map<Character, Boolean> shiftJISCacheMap = encodingCache
                    .get(fileEncoding);
            assertEquals(0, shiftJISCacheMap.size());
            assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        }

        // �����Ώۏ�����
        encodingCache.clear();
    }

    /**
     * testIsHalfWidthChar07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) fileEncoding:null<br>
     * (����) checkChar:','<br>
     * (���) encodingCache:�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) encodingCache:�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * (��ԕω�) Map#put():�Ă΂�Ȃ�<br>
     * (��ԕω�) -:NullPointerException����������B<br>
     * <br>
     * �ُ�P�[�X<br>
     * (�L���b�V���Ȃ�)<br>
     * �G���R�[�f�B���O��null�̏ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testIsHalfWidthChar07() throws Exception {
        // �O����(����)
        String fileEncoding = null;
        char checkChar = ',';

        // �O����(���)
        Map<String, Map<Character, Boolean>> encodingCache = (Map<String, Map<Character, Boolean>>) UTUtil
                .getPrivateField(FileDAOUtility.class, "encodingCache");
        encodingCache.clear();

        try {
            // �e�X�g���{
            UTUtil.invokePrivate(FileDAOUtility.class, "isHalfWidthChar",
                    new Class[] { String.class, char.class }, new Object[] {
                            fileEncoding, checkChar });
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����(��O)
            assertTrue(e instanceof NullPointerException);
            assertEquals(0, encodingCache.size());
            assertFalse(VMOUTUtil.isCalled(Map.class, "put"));
        }
    }
}

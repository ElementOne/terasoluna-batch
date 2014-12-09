/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.FileException} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���A�N�Z�X�@�\�Ŕ��������O�����b�v����RuntimeException
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.FileException
 */
public class FileExceptionTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FileExceptionTest.class);
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
    public FileExceptionTest(String name) {
        super(name);
    }

    /**
     * testFileExceptionException01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) e:Exception�N���X�̃C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileException.cause:����e�Ɠ����I�u�W�F�N�g�B<br>
     * (��ԕω�) FileException.fileName:null���ݒ�<br>
     * <br>
     * �R���X�g���N�^�̈����Ŏ󂯎����Exception�����b�v���邱�Ƃ��m�F����B<br>
     * ����P�[�X <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFileExceptionException01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŁA�s�v

        // �����̐ݒ�
        Exception e = new Exception();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileException fe = new FileException(e);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(e, fe.getCause());
        assertNull(UTUtil.getPrivateField(fe, "fileName"));
    }

    /**
     * testFileExceptionExceptionString01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) e:not null<br>
     * Exception�N���X�̃C���X�^���X<br>
     * (����) fileName:not null<br>
     * ��String�N���X�̃C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileException.fileName:����filename�Ɠ����I�u�W�F�N�g<br>
     * (��ԕω�) FileException.cause:����e�Ɠ����I�u�W�F�N�g�B<br>
     * <br>
     * �R���X�g���N�^�̈����Ŏ󂯎����Exception�����b�v���邱�Ƃ��m�F����B<br>
     * fileName�������ɐݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFileExceptionExceptionString01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŁA�s�v

        // �����̐ݒ�
        Exception e = new Exception();
        String fileName = new String();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileException fe = new FileException(e, fileName);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(fileName, UTUtil.getPrivateField(fe, "fileName"));
        assertSame(e, fe.getCause());
    }

    /**
     * testFileExceptionString01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:not null<br>
     * ���String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileException.detailMessage: ����StringObject�Ɠ���String�I�u�W�F�N�g<br>
     * (��ԕω�) FileException.fileName:null<br>
     * <br>
     * �R���X�g���N�^�����ɗ�O���b�Z�[�W��ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀ��b�Z�[�W���i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFileExceptionString01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŁA�s�v

        // �����̐ݒ�
        String message = new String();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileException fe = new FileException(message);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(message, UTUtil.getPrivateField(fe, "detailMessage"));
        assertNull(UTUtil.getPrivateField(fe, "fileName"));
    }

    /**
     * testFileExceptionStringString01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:not null<br>
     * ���String�C���X�^���X<br>
     * (����) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileException.detailMessage: ����StringObject�Ɠ����I�u�W�F�N�g�B<br>
     * (��ԕω�) FileException.fileName:����filename�Ɠ����I�u�W�F�N�g<br>
     * <br>
     * �R���X�g���N�^�����ɗ�O���b�Z�[�W��ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀ��b�Z�[�W���i�[����Ă��邱�Ƃ��m�F����B<br>
     * fileName�������ɐݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFileExceptionStringString01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŁA�s�v

        // �����̐ݒ�
        String message = new String();
        String fileName = new String();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileException fe = new FileException(message, fileName);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(message, UTUtil.getPrivateField(fe, "detailMessage"));
        assertSame(fileName, UTUtil.getPrivateField(fe, "fileName"));
    }

    /**
     * testFileExceptionStringException01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:not null<br>
     * ���String�C���X�^���X<br>
     * (����) e:not null<br>
     * Exception�N���X�̃C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileException.detailMessage:����stringObject�Ɠ����I�u�W�F�N�g�B<br>
     * (��ԕω�) FileException.cause:����e�Ɠ����I�u�W�F�N�g<br>
     * (��ԕω�) FileException.fileName:null<br>
     * <br>
     * �R���X�g���N�^�����ɗ�O���b�Z�[�W��ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀ��b�Z�[�W���i�[����Ă��邱�Ƃ��m�F����B<br>
     * message�������ɐݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFileExceptionStringException01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŁA�s�v

        // �����̐ݒ�
        Exception e = new Exception();
        String message = new String();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileException fe = new FileException(message, e);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(message, UTUtil.getPrivateField(fe, "detailMessage"));
        assertNull(UTUtil.getPrivateField(fe, "fileName"));
        assertSame(e, UTUtil.getPrivateField(fe, "cause"));
    }

    /**
     * testFileExceptionStringExceptionString01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:not null<br>
     * ���String�C���X�^���X<br>
     * (����) e:not null<br>
     * Exception�N���X�̃C���X�^���X<br>
     * (����) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileException.detailMessage:����stringObject�� �����I�u�W�F�N�g�B<br>
     * (��ԕω�) FileException.cause:����e�Ɠ����I�u�W�F�N�g<br>
     * (��ԕω�) FileException.fileName:����filename�Ɠ����I�u�W�F�N�g<br>
     * <br>
     * �R���X�g���N�^�̈����Ŏ󂯎����Exception�����b�v���邱�Ƃ��m�F����B<br>
     * �R���X�g���N�^�����ɗ�O���b�Z�[�W��ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀ��b�Z�[�W���i�[����Ă��邱�Ƃ��m�F����B<br>
     * fileName�������ɐݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFileExceptionStringExceptionString01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�̎����Ȃ̂ŁA�s�v

        // �����̐ݒ�
        Exception e = new Exception();
        String message = new String();
        String fileName = new String();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileException fe = new FileException(message, e, fileName);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(message, UTUtil.getPrivateField(fe, "detailMessage"));
        assertSame(fileName, UTUtil.getPrivateField(fe, "fileName"));
        assertSame(e, UTUtil.getPrivateField(fe, "cause"));
    }

    /**
     * testGetFileName01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) String:�O�������filename�Ɠ����I�u�W�F�N�g<br>
     * <br>
     * �������������擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testGetFileName01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileException fe = new FileException(new String());

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        String fileName = new String();
        UTUtil.setPrivateField(fe, "fileName", fileName);

        // �e�X�g���{
        String result = fe.getFileName();

        // �ԋp�l�̊m�F
        assertSame(fileName, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

}

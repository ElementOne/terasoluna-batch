/*
 * $Id: StringConverterToUpperCaseTest.java 5819 2007-12-20 05:55:47Z fukuyot $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.annotation;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.annotation.StringConverterToUpperCase} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �A�m�e�[�V����StringConverter�̋L�q�ɏ]���A������̕ϊ��������s���B<br>
 * StringConverterToUpperCase�͓��͂��ꂽ������i�A���t�@�x�b�g�j �������S�đ啶���ɂ��ĕԋp����B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.annotation.StringConverterToUpperCase
 */
public class StringConverterToUpperCaseTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(StringConverterToUpperCaseTest.class);
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
    public StringConverterToUpperCaseTest(String name) {
        super(name);
    }

    /**
     * testConvert01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) string:null<br>
     * <br>
     * ���Ғl�F(�߂�l) resultString:null<br>
     * <br>
     * ������Null���������ꍇ�̏����B<br>
     * null��ԋp����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvert01() throws Exception {
        // �O����(����)
        StringConverter stringTransformer = new StringConverterToUpperCase();

        // �e�X�g���{
        String resultString = stringTransformer.convert(null);

        // ����
        assertNull(resultString);
    }

    /**
     * testConvert02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) string:not null<br>
     * �ȉ���String�C���X�^���X<br>
     * "abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ"<br>
     * <br>
     * ���Ғl�F(�߂�l) resultString:�ȉ���String�C���X�^���X<br>
     * "ABCDEFGHIJKLMNOPQRSTUVWXYZ_ABCDEFGHIJKLMNOPQRSTUVWXYZ"<br>
     * <br>
     * �����ϊ����������{�B<br>
     * ���̏ꍇ�A�啶���ϊ������Ȃ̂ŁA���͂����������S�đ啶���ɕϊ����ĕԋp����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvert02() throws Exception {
        // �O����(����)
        StringConverter stringTransformer = new StringConverterToUpperCase();

        // �e�X�g���{
        String resultString = stringTransformer
                .convert("abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        // ����
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ_ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                resultString);
    }

    /**
     * testConvert03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) string:not null<br>
     * �ȉ���String�C���X�^���X<br>
     * "�������J�L�N����"<br>
     * <br>
     * ���Ғl�F(�߂�l) resultString:�ȉ���String�C���X�^���X<br>
     * "�������J�L�N����"<br>
     * <br>
     * �����ϊ����������{�B<br>
     * �Ђ炪�ȁA�J�^�J�i�A����������ꍇ�́A���̂܂܏o�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvert03() throws Exception {
        // �O����(����)
        StringConverter stringTransformer = new StringConverterToLowerCase();

        // �e�X�g���{
        String resultString = stringTransformer.convert("�������J�L�N����");
        // ����
        assertEquals("�������J�L�N����", resultString);
    }

    /**
     * testConvert04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) string:�ȉ���String�C���X�^���X<br>
     * " "(�󕶎��j<br>
     * <br>
     * ���Ғl�F(�߂�l) resultString:�ȉ���String�C���X�^���X<br>
     * " "(�󕶎��j<br>
     * <br>
     * �����ϊ����������{�B<br>
     * ""(�󕶎��j�̏ꍇ�́A���̂܂ܕϊ����ꂸ�ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvert04() throws Exception {
        // �O����(����)
        StringConverter stringTransformer = new StringConverterToLowerCase();

        // �e�X�g���{
        String resultString = stringTransformer.convert("");

        // ����
        assertEquals("", resultString);
    }

}

/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.text.DecimalFormat;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.DecimalFormatLocal} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> DecimalFormat���X���b�h�Z�[�t�ł͂Ȃ����߁AThreadLocal���g�p���ăX���b�h�Z�[�t�ɂ���B
 * <p>
 * @author �I ����
 * @see jp.terasoluna.fw.file.dao.standard.DecimalFormatLocal
 */
public class DecimalFormatLocalTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(DecimalFormatLocalTest.class);
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
    public DecimalFormatLocalTest(String name) {
        super(name);
    }

    /**
     * testDecimalFormatLocalStringpattern01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) pattern:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.pattern:����pattern�Ɠ����l<br>
     * <br>
     * ����pattern�Ɠ����l���ݒ肳��邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDecimalFormatLocalStringpattern01() throws Exception {
        // �O����(����)
        String pattern = new String();

        // �e�X�g���{
        DecimalFormatLocal decimalFormatLocal = new DecimalFormatLocal(pattern);

        // ����
        assertNotNull(decimalFormatLocal);
        assertSame(pattern, UTUtil.getPrivateField(decimalFormatLocal,
                "pattern"));
    }

    /**
     * testInitialValue01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE.�f <br>
     * <br>
     * ���͒l�F(���) pattern:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:NullPointerException<br>
     * <br>
     * �O�������null�̏ꍇNullPointerException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialValue01() throws Exception {
        // �O����
        DecimalFormatLocal decimalFormatLocal = new DecimalFormatLocal(null);

        // �O����(���)
        UTUtil.setPrivateField(decimalFormatLocal, "pattern", null);

        try {
            // �e�X�g���{
            decimalFormatLocal.initialValue();
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * testInitialValue02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(���) pattern:"-\\#,##0.##"<br>
     * <br>
     * ���Ғl�F(�߂�l) DecimalFormat:pattern�ɑ΂���DecimalFormat�C���X�^���X<br>
     * <br>
     * �O�������null����Ȃ��ꍇ������{���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialValue02() throws Exception {
        // �O����
        DecimalFormatLocal decimalFormatLocal = new DecimalFormatLocal(null);
        String pattern = "-\\#,##0.##";

        // �O����(���)
        UTUtil.setPrivateField(decimalFormatLocal, "pattern", pattern);

        // �e�X�g���{
        DecimalFormat result = decimalFormatLocal.initialValue();

        // ����
        assertNotNull(result);
        assertTrue(result instanceof DecimalFormat);
        assertEquals(pattern, DecimalFormat.class.cast(result).toPattern());
    }
}

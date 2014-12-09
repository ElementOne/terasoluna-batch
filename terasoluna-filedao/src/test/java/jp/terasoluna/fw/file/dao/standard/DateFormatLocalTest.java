/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.DateFormatLocal} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> SimpleDateFormat���X���b�h�Z�[�t�ł͂Ȃ����߁AThreadLocal���g�p���ăX���b�h�Z�[�t�ɂ���B
 * <p>
 * @author �I ����
 * @see jp.terasoluna.fw.file.dao.standard.DateFormatLocal
 */
public class DateFormatLocalTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(DateFormatLocalTest.class);
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
    public DateFormatLocalTest(String name) {
        super(name);
    }

    /**
     * testDateFormatLocalStringpattern01() <br>
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
    public void testDateFormatLocalStringpattern01() throws Exception {
        // �O����(����)
        String pattern = new String();

        // �e�X�g���{
        DateFormatLocal dateFormatLocal = new DateFormatLocal(pattern);

        // ����
        assertNotNull(dateFormatLocal);
        assertSame(pattern, UTUtil.getPrivateField(dateFormatLocal, "pattern"));
    }

    /**
     * testDateFormatinitialValue01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE.G <br>
     * <br>
     * ���͒l�F(���) pattern:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:NullPointerException<br>
     * <br>
     * �O�������null�̏ꍇNullPointerException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDateFormatinitialValue01() throws Exception {
        // �O����
        DateFormatLocal dateFormatLocal = new DateFormatLocal(null);

        // �O����(���)
        UTUtil.setPrivateField(dateFormatLocal, "pattern", null);

        try {
            // �e�X�g���{
            dateFormatLocal.initialValue();
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * testDateFormatinitialValue02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(���) pattern:"yyyy/MM/dd"<br>
     * <br>
     * ���Ғl�F(�߂�l) DateFormat:�ȉ��̗v�f������SimpleDateFormat�C���X�^���X<br>
     * �@pattern�F�@"yyyy/MM/dd"<br>
     * �@setLenient�F�@FALSE<br>
     * <br>
     * �O�������null����Ȃ��ꍇ������{���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDateFormatinitialValue02() throws Exception {
        // �O����
        DateFormatLocal dateFormatLocal = new DateFormatLocal(null);

        // �O����(���)
        String pattern = "yyyy/MM/dd";
        UTUtil.setPrivateField(dateFormatLocal, "pattern", pattern);

        // �e�X�g���{
        DateFormat result = dateFormatLocal.initialValue();

        // ����
        assertNotNull(result);
        assertTrue(result instanceof SimpleDateFormat);
        assertEquals(pattern, SimpleDateFormat.class.cast(result).toPattern());
        assertFalse(result.isLenient());
    }
}

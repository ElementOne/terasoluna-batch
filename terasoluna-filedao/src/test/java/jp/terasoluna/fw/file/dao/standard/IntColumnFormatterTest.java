/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.IntColumnFormatter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �A�m�e�[�V����columnFormat�̋L�q�ɏ]���A������̕ϊ��������s���B
 * <p>
 * @author ���c �N�i
 * @see jp.terasoluna.fw.file.dao.standard.IntColumnFormatter
 */
public class IntColumnFormatterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(IntColumnFormatterTest.class);
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
    public IntColumnFormatterTest(String name) {
        super(name);
    }

    /**
     * testFormat01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�t�B�[���h��1���X�^�u�N���X<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ getter���\�b�h<br>
     * method:�ΏۂƂȂ�getter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:���String�C���X�^���X<br>
     * (���) ����������o���t�@�C���s�I�u�W�F�N�g(t)��int�^�̑���: int i=3<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:������method��int�^������ �i�[����Ă���l�̕�����B<br>
     * "3"<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����int�^�����Ɋi�[����Ă���I�u�W�F�N�g�� ��������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new IntColumnFormatter();

        // �����̐ݒ�
        IntColumnFormatter_Stub01 t = new IntColumnFormatter_Stub01();
        Method method = t.getClass().getMethod("getIntValue");
        String columnFormat = new String();

        // �O������̐ݒ�
        t.setIntValue(3);

        // �e�X�g���{
        String testResult = columnFormatter.format(t, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("3", testResult);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testFormat02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�t�B�[���h��1���X�^�u�N���X<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ getter���\�b�h<br>
     * method:�ΏۂƂȂ�getter���\�b�h�̉�����private<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:���String�C���X�^���X<br>
     * (���) ����������o���t�@�C���s�I�u�W�F�N�g(t)�� int�^�̑���:int i=0<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalAccessException���������邱�Ƃ� �m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��int�^������getter���\�b�h�ɃA�N�Z�X�ł��Ȃ��ꍇ�A IllegalAccessException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new IntColumnFormatter();

        // �����̐ݒ�
        IntColumnFormatter_Stub02 t = new IntColumnFormatter_Stub02();
        Method method = t.getClass().getDeclaredMethod("getIntValue",
                new Class[0]);
        String columnFormat = new String();

        // �O������̐ݒ�
        UTUtil.setPrivateField(t, "intValue", 0);

        try {
            // �e�X�g���{
            columnFormatter.format(t, method, columnFormat);
            fail("IllegalAccessException���X���[����܂���ł����B");
        } catch (IllegalAccessException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalAccessException.class.getName(), e.getClass()
                    .getName());
        }
    }

    /**
     * testFormat03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�t�B�[���h��1���X�^�u�N���X<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ getter���\�b�h<br>
     * method:getter���\�b�h����O���X���[����<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:���String�C���X�^���X<br>
     * (���) ����������o���t�@�C���s�I�u�W�F�N�g(t)�� int�^�̑���:int i=0<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvocationTargetException���������邱�Ƃ� �m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��int�^������getter���\�b�h����O���X���[����ꍇ�A getter���\�b�h���X���[������O�����b�v���� InvocationTargetException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new IntColumnFormatter();

        // �����̐ݒ�
        IntColumnFormatter_Stub03 t = new IntColumnFormatter_Stub03();
        Method method = t.getClass().getMethod("getIntValue");
        String columnFormat = new String();

        // �O������̐ݒ�
        UTUtil.setPrivateField(t, "intValue", 0);

        try {
            // �e�X�g���{
            columnFormatter.format(t, method, columnFormat);
            fail("InvocationTargetException���X���[����܂���ł����B");
        } catch (InvocationTargetException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(InvocationTargetException.class.getName(), e
                    .getClass().getName());
        }
    }

    /**
     * testFormat04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�t�B�[���h��1���X�^�u�N���X<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ��� int�^������getter���\�b�h<br>
     * method:getter�̈�������������<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:���String�C���X�^���X<br>
     * (���) ����������o���t�@�C���s�I�u�W�F�N�g(t)�� int�^�̑���:int i=0<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException���������邱�Ƃ� �m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��int�^������getter���\�b�h�������̈��������ꍇ�A IllegalArgumentException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new IntColumnFormatter();

        // �����̐ݒ�
        IntColumnFormatter_Stub04 t = new IntColumnFormatter_Stub04();
        Method method = t.getClass().getMethod("getIntValue",
                new Class[] { int.class, String.class });
        String columnFormat = new String();

        // �O������̐ݒ�
        UTUtil.setPrivateField(t, "intValue", 0);

        try {
            // �e�X�g���{
            columnFormatter.format(t, method, columnFormat);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class.getName(), e.getClass()
                    .getName());
        }
    }
}

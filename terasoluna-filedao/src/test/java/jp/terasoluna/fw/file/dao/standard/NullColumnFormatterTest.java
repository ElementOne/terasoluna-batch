/*
 * $Id: NullColumnFormatterTest.java 5354 2007-10-03 06:06:25Z anh $
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
 * {@link jp.terasoluna.fw.file.dao.standard.NullColumnFormatter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �A�m�e�[�V����columnFormat�̋L�q�ɏ]���A������̕ϊ��������s���B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.NullColumnFormatter
 */
public class NullColumnFormatterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(NullColumnFormatterTest.class);
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
    public NullColumnFormatterTest(String name) {
        super(name);
    }

    /**
     * testFormat01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�I�u�W�F�N�g<br>
     * �ȉ��̒l������<br>
     * �@�l�F"aaa"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��String�^�����Ɋi�[����Ă���l�̕�����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����String�^�����Ɋi�[����Ă���I�u�W�F�N�g�̕�������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub01 stub = new NullColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "string", "aaa");
        Method method = stub.getClass().getDeclaredMethod("getString");
        String columnFormat = new String();

        // �O������Ȃ�

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("aaa", testResult);

        // ��ԕω��Ȃ�
    }

    /**
     * testFormat02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�I�u�W�F�N�g<br>
     * �ȉ��̒l������<br>
     * �@�l�F"aaa"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fprivate<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalAccessException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��String�^������getter���\�b�h�ɃA�N�Z�X�ł��Ȃ��ꍇ�AIllegalAccessException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub02 stub = new NullColumnFormatter_Stub02();
        UTUtil.setPrivateField(stub, "string", "aaa");
        Method method = stub.getClass().getDeclaredMethod("getString");
        String columnFormat = new String();

        // �O������Ȃ�

        // �e�X�g���{
        try {
            columnFormatter.format(stub, method, columnFormat);
            fail("IllegalAccessException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalAccessException.class, e.getClass());
        }
    }

    /**
     * testFormat03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�I�u�W�F�N�g<br>
     * �ȉ��̒l������<br>
     * �@�l�F��O���X���[����<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:InvocationTargetException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��String�^������getter���\�b�h����O���X���[����ꍇ�Agetter���\�b�h���X���[������O�����b�v����InvocationTargetException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub03 stub = new NullColumnFormatter_Stub03();
        UTUtil.setPrivateField(stub, "string", "aaa");
        Method method = stub.getClass().getMethod("getString");
        String columnFormat = new String();

        // �O������Ȃ�

        // �e�X�g���{
        try {
            columnFormatter.format(stub, method, columnFormat);
            fail("InvocationTargetException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(InvocationTargetException.class, e.getClass());
        }
    }

    /**
     * testFormat04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�I�u�W�F�N�g<br>
     * �ȉ��̒l������<br>
     * �@�l�F"aaa"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F����<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalArgumentException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��String�^������getter���\�b�h�������̈��������ꍇ�AIllegalArgumentException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub04 stub = new NullColumnFormatter_Stub04();
        UTUtil.setPrivateField(stub, "string", "aaa");
        Method method = stub.getClass().getDeclaredMethod("getString",
                new Class[] { String.class });
        String columnFormat = new String();

        // �O������Ȃ�

        // �e�X�g���{
        try {
            columnFormatter.format(stub, method, columnFormat);
            fail("IllegalArgumentException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    /**
     * testFormat05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�I�u�W�F�N�g<br>
     * �ȉ��̒l������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) String:""(�󕶎�)<br>
     * <br>
     * �����t�@�C���s�I�u�W�F�N�g�̃t�B�[���h��Null�̏ꍇ�A�󕶎����ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub01 stub = new NullColumnFormatter_Stub01();
        Method method = stub.getClass().getDeclaredMethod("getString");
        String columnFormat = new String();

        // �O������Ȃ�

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("", testResult);

        // ��ԕω��Ȃ�
    }

    /**
     * testFormat06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:null<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:Method�C���X�^���X<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:NullPointException�������邱�Ƃ��m�F����<br>
     * <br>
     * ����t��null�������ꍇ�́ANullPointerException���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub01 stub = new NullColumnFormatter_Stub01();
        Method method = stub.getClass().getDeclaredMethod("getString");
        String columnFormat = new String();

        // �O������Ȃ�

        // �e�X�g���{
        try {
            columnFormatter.format(null, method, columnFormat);
            fail("NullPointerException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    /**
     * testFormat07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�I�u�W�F�N�g<br>
     * �ȉ��̒l������<br>
     * �@�l�F"aaa"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:null<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:NullPointException�������邱�Ƃ��m�F����<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�hmethod��Null�̏ꍇNullPointException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub01 stub = new NullColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "string", "aaa");
        String columnFormat = new String();

        // �O������Ȃ�

        // �e�X�g���{
        try {
            columnFormatter.format(stub, null, columnFormat);
            fail("NullPointerException���������܂���ł����B���s�ł��B");
        } catch (Exception e) {
            // �ԋp�l�Ȃ�

            // ��ԕω��̊m�F
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    /**
     * testFormat08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:�I�u�W�F�N�g<br>
     * �ȉ��̒l������<br>
     * �@�l�F"aaa"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��String�^�����Ɋi�[����Ă���l�̕�����B<br>
     * <br>
     * �t�H�[�}�b�g�p�̕�����columnFormat��Null�̏ꍇ�ɐ�����s�̌��ʂ��ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new NullColumnFormatter();

        // �����̐ݒ�
        NullColumnFormatter_Stub01 stub = new NullColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "string", "aaa");
        Method method = stub.getClass().getDeclaredMethod("getString");
        String columnFormat = null;

        // �O������Ȃ�

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("aaa", testResult);

        // ��ԕω��Ȃ�
    }
}

/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.IntColumnParser} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �w�肳�ꂽ��������p�[�Y���Aint�^�ɕϊ�����B<br>
 * �ϊ����ʂ��t�@�C���s�I�u�W�F�N�g��int�^�̑����ɒl���i�[����B
 * <p>
 * @author ���c �N�i
 * @see jp.terasoluna.fw.file.dao.standard.IntColumnParser
 */
public class IntColumnParserTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(IntColumnParserTest.class);
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
    public IntColumnParserTest(String name) {
        super(name);
    }

    /**
     * testParse01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * "1"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� ������int�ɕϊ�����Ċi�[�����B<br>
     * "1"<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��int�^�����ɕ������ݒ肷�邱�Ƃ��ł��邱�Ƃ� �m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        IntColumnParser intColumnParser = new IntColumnParser();

        // �����̐ݒ�
        String column = "1";
        IntColumnParser_Stub01 t = new IntColumnParser_Stub01();
        Method method = IntColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Integer.TYPE });
        String columnFormat = "";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        intColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        assertEquals(1, result);
    }

    /**
     * testParse02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * "1"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����private<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalAccessException���������邱�Ƃ� �m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��int�^������setter���\�b�h�ɃA�N�Z�X�ł��Ȃ��ꍇ�A IllegalAccessException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        IntColumnParser intColumnParser = new IntColumnParser();

        // �����̐ݒ�
        String column = "1";
        IntColumnParser_Stub01 t = new IntColumnParser_Stub01();
        Method method = IntColumnParser_Stub01.class.getDeclaredMethod(
                "setAPrivate", new Class[] { Integer.TYPE });
        String columnFormat = "";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            intColumnParser.parse(column, t, method, columnFormat);
            fail("IllegalAccessException���X���[����܂���ł����B");
        } catch (IllegalAccessException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalAccessException.class, e.getClass());
        }
    }

    /**
     * testParse03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * "1"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ setter���\�b�h<br>
     * method:setter���\�b�h����O���X���[����<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvocationTargetException���������邱�Ƃ� �m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��int�^������setter���\�b�h����O���X���[����ꍇ�A setter���\�b�h���X���[������O�����b�v����InvocationTargetException�� �X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        IntColumnParser intColumnParser = new IntColumnParser();

        // �����̐ݒ�
        String column = "1";
        IntColumnParser_Stub01 t = new IntColumnParser_Stub01();
        Method method = IntColumnParser_Stub01.class.getDeclaredMethod(
                "setAException", new Class[] { Integer.TYPE });
        String columnFormat = "";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            intColumnParser.parse(column, t, method, columnFormat);
            fail();
        } catch (InvocationTargetException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(InvocationTargetException.class, e.getClass());
        }
    }

    /**
     * testParse04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * "1"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ setter���\�b�h<br>
     * method:setter���\�b�h�̈�������������<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException���������邱�Ƃ� �m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��int�^������setter���\�b�h�̈�������������ꍇ�A IllegalArgumentException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        IntColumnParser intColumnParser = new IntColumnParser();

        // �����̐ݒ�
        String column = "1";
        IntColumnParser_Stub01 t = new IntColumnParser_Stub01();
        Method method = IntColumnParser_Stub01.class.getDeclaredMethod(
                "setAAndB", new Class[] { Integer.TYPE, Integer.TYPE });
        String columnFormat = "";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            intColumnParser.parse(column, t, method, columnFormat);

            // ����
            fail();
        } catch (IllegalArgumentException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    /**
     * testParse05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:�����ȊO�̕�����<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������ setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NumberFormatException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �����̕����񂪐����ȊO(int�^�ɕϊ��ł��Ȃ�)�̏ꍇ�A NumberFormatException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        IntColumnParser intColumnParser = new IntColumnParser();

        // �����̐ݒ�
        String column = "a";
        IntColumnParser_Stub01 t = new IntColumnParser_Stub01();
        Method method = IntColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Integer.TYPE });
        String columnFormat = "";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            intColumnParser.parse(column, t, method, columnFormat);

            // ����
            fail();
        } catch (NumberFormatException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    /**
     * testParse06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:null<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * ""<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NumberFormatException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �����̕�����null�̏ꍇ�ANumberFormatException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        IntColumnParser intColumnParser = new IntColumnParser();

        // �����̐ݒ�
        String column = null;
        IntColumnParser_Stub01 t = new IntColumnParser_Stub01();
        Method method = IntColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Integer.TYPE });
        String columnFormat = "";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            intColumnParser.parse(column, t, method, columnFormat);

            // ����
            fail();
        } catch (NumberFormatException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    /**
     * testParse07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * "1"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���int�^������setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵��������int�ɕϊ�����Ċi�[�����B<br>
     * "1"<br>
     * <br>
     * �����̃t�H�[�}�b�g�p������null�̏ꍇ�A�t�@�C���s�I�u�W�F�N�g��int�^�����ɕ������ݒ肷�邱�Ƃ��ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        IntColumnParser intColumnParser = new IntColumnParser();

        // �����̐ݒ�
        String column = "1";
        IntColumnParser_Stub01 t = new IntColumnParser_Stub01();
        Method method = IntColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Integer.TYPE });
        String columnFormat = null;

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        intColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        assertEquals(1, result);
    }
}

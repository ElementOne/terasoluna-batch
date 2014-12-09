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
 * {@link jp.terasoluna.fw.file.dao.standard.NullColumnParser} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> String�^�̃t�@�C���s�I�u�W�F�N�g�̑����Ƀt�@�C������ǂݍ��񂾃J�����̕�������i�[����B
 * <p>
 * @author ���c �N�i
 * @see jp.terasoluna.fw.file.dao.standard.NullColumnParser
 */
public class NullColumnParserTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(NullColumnParserTest.class);
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
    public NullColumnParserTest(String name) {
        super(name);
    }

    /**
     * testParse01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������ setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� �����񂪊i�[�����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��String�^�����ɁA����column�̒l���ݒ肳��� ���Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        NullColumnParser nullColumnParser = new NullColumnParser();

        // �����̐ݒ�
        String column = "AA";
        NullColumnParser_Stub01 t = new NullColumnParser_Stub01();
        Method method = NullColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { String.class });
        String columnFormat = "BB";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        nullColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        assertSame(column, result);
    }

    /**
     * testParse02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������ setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����private<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalAccessException���������� ���Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��String�^������setter���\�b�h�� �A�N�Z�X�ł��Ȃ��ꍇ�AIllegalAccessException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        NullColumnParser nullColumnParser = new NullColumnParser();

        // �����̐ݒ�
        String column = "AA";
        NullColumnParser_Stub01 t = new NullColumnParser_Stub01();
        Method method = NullColumnParser_Stub01.class.getDeclaredMethod(
                "setAPrivate", new Class[] { String.class });
        String columnFormat = "BB";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            nullColumnParser.parse(column, t, method, columnFormat);
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
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������ setter���\�b�h<br>
     * method:setter���\�b�h����O���X���[����<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvocationTargetException���������� ���Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��String�^������setter���\�b�h����O�� �X���[����ꍇ�Asetter���\�b�h���X���[������O�����b�v���� InvocationTargetException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        NullColumnParser nullColumnParser = new NullColumnParser();

        // �����̐ݒ�
        String column = "AA";
        NullColumnParser_Stub01 t = new NullColumnParser_Stub01();
        Method method = NullColumnParser_Stub01.class.getDeclaredMethod(
                "setAException", new Class[] { String.class });
        String columnFormat = "BB";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            nullColumnParser.parse(column, t, method, columnFormat);

            // ����
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
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������ setter���\�b�h<br>
     * method:setter���\�b�h�̈�������������<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException���������邱�� ���m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��String�^������setter���\�b�h�̈����� ��������ꍇ�AIllegalArgumentException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        NullColumnParser nullColumnParser = new NullColumnParser();

        // �����̐ݒ�
        String column = "AA";
        NullColumnParser_Stub01 t = new NullColumnParser_Stub01();
        Method method = NullColumnParser_Stub01.class.getDeclaredMethod(
                "setAAndB", new Class[] { String.class, String.class });
        String columnFormat = "BB";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        try {
            nullColumnParser.parse(column, t, method, columnFormat);

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
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) column:null<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������ setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:null�� �ݒ肳���B<br>
     * <br>
     * column��Null�̏ꍇ�ɁA�t�@�C���s�I�u�W�F�N�g�̑�����null�� �ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        NullColumnParser nullColumnParser = new NullColumnParser();

        // �����̐ݒ�
        String column = null;
        NullColumnParser_Stub01 t = new NullColumnParser_Stub01();
        Method method = NullColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { String.class });
        String columnFormat = null;

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        nullColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        assertNull(result);
    }

    /**
     * testParse06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) column:String�C���X�^���X<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * t:�t�@�C���s�I�u�W�F�N�g�X�^�u<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���String�^������setter���\�b�h<br>
     * method:�ΏۂƂȂ�setter���\�b�h�̉�����public<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�������񂪊i�[�����B<br>
     * <br>
     * columnFormat��Null�̏ꍇ�ɁA�t�@�C���s�I�u�W�F�N�g��String�^�����ɁA����column�̒l���ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        NullColumnParser nullColumnParser = new NullColumnParser();

        // �����̐ݒ�
        String column = "AA";
        NullColumnParser_Stub01 t = new NullColumnParser_Stub01();
        Method method = NullColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { String.class });
        String columnFormat = "BB";

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        nullColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        assertSame(column, result);
    }
}

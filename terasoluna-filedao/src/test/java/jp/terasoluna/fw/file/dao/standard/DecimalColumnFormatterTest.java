/*
 * $Id: DecimalColumnFormatterTest.java 5354 2007-10-03 06:06:25Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.DecimalColumnFormatter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �A�m�e�[�V����columnFormat�̋L�q�ɏ]���A������̕ϊ��������s���B
 * <p>
 * @author ���c �N�i
 * @see jp.terasoluna.fw.file.dao.standard.DecimalColumnFormatter
 */
public class DecimalColumnFormatterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(DecimalColumnFormatterTest.class);
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
    public DecimalColumnFormatterTest(String name) {
        super(name);
    }

    /**
     * testFormat01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,E <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal01<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Ȃ�<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:null<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1000000"<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕�����null�̏ꍇ�A ���t�B�[���h��getter���\�b�h���������ݒ肳��Ă���ꍇ�ɁA �Ώۃt�B�[���h�̏�񂪐����݂̂̕�����(BigDecimal.toPlainString()�̌���)
     * �Ƃ��Ď擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat01() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal01(new BigDecimal(1000000));

        Method method = stub.getClass().getMethod("getDecimal01");

        String columnFormat = null;

        // �e�X�g���{
        String result = decimalColumnFormatter.format(stub, method,
                columnFormat);

        // ����
        assertNotNull(result);
        assertEquals("1000000", result);
    }

    /**
     * testFormat02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal02<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Ȃ�<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:"\\##,###,###.00"<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"\\1,000,000.00"<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕����񂪐������ݒ肳�ꂽ�ꍇ�A ���t�B�[���h��getter���\�b�h���������ݒ肳��Ă���ꍇ�ɁA �Ώۃt�B�[���h�̏�񂪃t�H�[�}�b�g�ɏ]����������Ƃ��Ď擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat02() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal02(new BigDecimal(1000000));

        Method method = stub.getClass().getMethod("getDecimal02");

        String columnFormat = "\\##,###,###.00";

        // �e�X�g���{
        String result = decimalColumnFormatter.format(stub, method,
                columnFormat);

        // ����
        assertNotNull(result);
        assertEquals("\\1,000,000.00", result);
    }

    /**
     * testFormat03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal03<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fprivate<br>
     * �E�����F�Ȃ�<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:"\\##,###,###.00"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalAccessException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�B�[���h��getter���\�b�h��private�Ő錾���ꂽ�ꍇ�A IllegalAccessException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat03() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal03(new BigDecimal(1000000));

        Method method = stub.getClass().getDeclaredMethod("getDecimal03");

        String columnFormat = "\\##,###,###.00";

        // �e�X�g���{
        try {
            decimalColumnFormatter.format(stub, method, columnFormat);
            fail("IllegalAccessException���������܂���ł����B");
        } catch (IllegalAccessException e) {
            // ����
            assertTrue(e instanceof IllegalAccessException);
        }
    }

    /**
     * testFormat04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal04<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Ȃ�<br>
     * �E�����F��O����������B<br>
     * (����) columnFormat:"\\##,###,###.00"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:InvocationTargetException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�B�[���h��getter���\�b�h��������O�����������ꍇ�A InvocationTargetException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat04() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal04(new BigDecimal(1000000));

        Method method = stub.getClass().getMethod("getDecimal04");

        String columnFormat = "\\##,###,###.00";

        // �e�X�g���{
        try {
            decimalColumnFormatter.format(stub, method, columnFormat);
            fail("InvocationTargetException���������܂���ł����B");
        } catch (InvocationTargetException e) {
            // ����
            assertTrue(e instanceof InvocationTargetException);
        }
    }

    /**
     * testFormat05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal05<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F����<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:new String()<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalArgumentException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�B�[���h��getter���\�b�h�Ƃ��Ĉ����Ȃ��̃��\�b�h�����݂��Ȃ��ꍇ�A IllegalArgumentException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat05() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal05(new BigDecimal(1000000));

        Method method = stub.getClass().getMethod("getDecimal05",
                new Class[] { BigDecimal.class });

        String columnFormat = new String();

        // �e�X�g���{
        try {
            decimalColumnFormatter.format(stub, method, columnFormat);
            fail("IllegalArgumentException���������܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * testFormat06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,E <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal06<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Ȃ�<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:null<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:""<br>
     * <br>
     * ����P�[�X<br>
     * �t�@�C���s�I�u�W�F�N�g�̃t�B�[���h�l��null�̏ꍇ�A�󕶎����擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat06() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal06(null);

        Method method = stub.getClass().getMethod("getDecimal06");

        String columnFormat = null;

        // �e�X�g���{
        String result = decimalColumnFormatter.format(stub, method,
                columnFormat);

        // ����
        assertNotNull(result);
        assertEquals("", result);

    }

    /**
     * testFormat07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,E <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal07<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Ȃ�<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:""<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"1000000"<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕����񂪋󕶎��̏ꍇ�A���t�B�[���h��getter���\�b�h�� �������ݒ肳��Ă���ꍇ�ɁA�Ώۃt�B�[���h�̏�񂪐����݂̂̕����� (BigDecimal.toPlainString()�̌���)�Ƃ��Ď擾����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat07() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal07(new BigDecimal(1000000));

        Method method = stub.getClass().getMethod("getDecimal07");

        String columnFormat = "";

        // �e�X�g���{
        String result = decimalColumnFormatter.format(stub, method,
                columnFormat);

        // ����
        assertNotNull(result);
        assertEquals("1000000", result);
    }

    /**
     * testFormat08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal08<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Ȃ�<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:"\\##,###,###.00"<br>
     * (���) dfMap:�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"\\1,000,000.00"<br>
     * (��ԕω�) dfMap:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"\\##,###,###.00"<br>
     * value�F�L�[�ɑ΂���DecimalFormatLocal�C���X�^���X<br>
     * (��ԕω�) DecimalFormatLocal#<init>:1��Ă΂��<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕�����ɑ΂���DecimalFormatLocal���L���b�V���ɑ��݂��Ȃ��ꍇ�A ���Ȃ����s����邱�Ƃ��m�F����B<br>
     * �܂��A�V�����������ꂽ�t�H�[�}�b�g�p�̕�����ɑ΂��� DecimalFormatLocal���L���b�V������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat08() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal08(new BigDecimal(1000000));

        Method method = stub.getClass().getMethod("getDecimal08");

        String columnFormat = "\\##,###,###.00";

        ConcurrentHashMap<String, DecimalFormatLocal> dfMap = new ConcurrentHashMap<String, DecimalFormatLocal>();
        UTUtil.setPrivateField(decimalColumnFormatter, "dfMap", dfMap);
        dfMap.clear();

        // �e�X�g���{
        String result = decimalColumnFormatter.format(stub, method,
                columnFormat);

        // ����
        assertNotNull(result);
        assertEquals("\\1,000,000.00", result);

        assertSame(dfMap, UTUtil.getPrivateField(decimalColumnFormatter,
                "dfMap"));
        assertEquals(1, dfMap.size());
        assertTrue(dfMap.containsKey(columnFormat));
        DecimalFormatLocal dfMapValue = dfMap.get(columnFormat);
        assertNotNull(dfMapValue);
        assertEquals(columnFormat, UTUtil
                .getPrivateField(dfMapValue, "pattern"));

        assertEquals(1, VMOUTUtil.getCallCount(DecimalFormatLocal.class,
                "<init>"));
    }

    /**
     * testFormat09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) ��:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal09<br>
     * - �^�FBigDecimal<br>
     * - �ݒ�l�Fnew BigDecimal(1000000)<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��getter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���getter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Ȃ�<br>
     * �E�����F�Ώۃt�B�[���̏���Ԃ��B<br>
     * (����) columnFormat:"\\##,###,###.00"<br>
     * (���) dfMap:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"\\##,###,###.00"<br>
     * value�F�L�[�ɑ΂���DecimalFormatLocal�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) ������:"\\1,000,000.00"<br>
     * (��ԕω�) dfMap:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"\\##,###,###.00"<br>
     * value�F�L�[�ɑ΂���DecimalFormatLocal�C���X�^���X<br>
     * (��ԕω�) DecimalFormatLocal#<init>:�Ă΂�Ȃ�<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕�����ɑ΂���DecimalFormatLocal���L���b�V���ɑ��݂���ꍇ�A ���Ȃ����s����邱�Ƃ��m�F����B<br>
     * �܂��A�t�H�[�}�b�g�p�̕�����ɑ΂���DecimalFormatLocal���V������������Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat09() throws Exception {
        // �O����
        DecimalColumnFormatter decimalColumnFormatter = new DecimalColumnFormatter();

        // �O����(����)
        DecimalColumnFormatter_FileLineObjectStub01 stub = new DecimalColumnFormatter_FileLineObjectStub01();
        stub.setDecimal09(new BigDecimal(1000000));

        Method method = stub.getClass().getMethod("getDecimal09");

        String columnFormat = "\\##,###,###.00";

        ConcurrentHashMap<String, DecimalFormatLocal> dfMap = new ConcurrentHashMap<String, DecimalFormatLocal>();
        DecimalFormatLocal dfMapValue = new DecimalFormatLocal(columnFormat);
        dfMap.put(columnFormat, dfMapValue);
        UTUtil.setPrivateField(decimalColumnFormatter, "dfMap", dfMap);

        VMOUTUtil.initialize();

        // �e�X�g���{
        String result = decimalColumnFormatter.format(stub, method,
                columnFormat);

        // ����
        assertNotNull(result);
        assertEquals("\\1,000,000.00", result);

        assertSame(dfMap, UTUtil.getPrivateField(decimalColumnFormatter,
                "dfMap"));
        assertEquals(1, dfMap.size());
        assertTrue(dfMap.containsKey(columnFormat));
        DecimalFormatLocal formatLocal = dfMap.get(columnFormat);
        assertSame(dfMapValue, formatLocal);

        assertFalse(VMOUTUtil.isCalled(DecimalFormatLocal.class, "<init>"));
    }
}

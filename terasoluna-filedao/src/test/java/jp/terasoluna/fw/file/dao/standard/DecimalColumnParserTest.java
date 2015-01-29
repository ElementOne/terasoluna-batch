/*
 * $Id: DecimalColumnParserTest.java 5354 2007-10-03 06:06:25Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.concurrent.ConcurrentHashMap;

import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.DecimalColumnParser} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �w�肳�ꂽ��������p�[�Y���ABigDecimal�^�ɕϊ�����B<br>
 * �ϊ����ʂ��t�@�C���s�I�u�W�F�N�g��BigDecimal�^�̑����ɒl���i�[����B
 * <p>
 * @author ���c �N�i
 * @see jp.terasoluna.fw.file.dao.standard.DecimalColumnParser
 */
public class DecimalColumnParserTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(DecimalColumnParserTest.class);
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
    public DecimalColumnParserTest(String name) {
        super(name);
    }

    /**
     * testParse01() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FC,E <br>
     * <br>
     * ���͒l�F(����) column:"123456"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal01<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) t:�Ώۃt�B�[���h��BigDecimal.valueOf(123456)�l���i�[�����B<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕�����null�̏ꍇ�A ���t�B�[���h��setter���\�b�h���������ݒ肳��Ă���ꍇ�ɁA �����̕����񂪐�����BigDecimal�ɕϊ�����Ċi�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse01() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "123456";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        stub.setDecimal01(null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal01",
                        new Class[] { BigDecimal.class });
        String columnFormat = null;

        // �e�X�g���{
        decimalColumnParser.parse(column, stub, method, columnFormat);

        // ����
        BigDecimal result = stub.getDecimal01();
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(123456), result);
    }

    /**
     * testParse02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) column:"-\\123,456.00"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal02<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * <br>
     * ���Ғl�F(��ԕω�) t:�Ώۃt�B�[���h��BigDecimal.valueOf(12345600, 2)�l���i�[�����B<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕����񂪂���ꍇ�A ���t�B�[���h��setter���\�b�h���������ݒ肳��Ă���ꍇ�ɁA �����̕����񂪐������t�H�[�}�b�g�ɏ]����BigDecimal�ɕϊ�����Ċi�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse02() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "-\\123,456.00";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        stub.setDecimal02(null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal02",
                        new Class[] { BigDecimal.class });
        String columnFormat = "-\\###,###,###.##";

        // �e�X�g���{
        decimalColumnParser.parse(column, stub, method, columnFormat);

        // ����
        BigDecimal result = stub.getDecimal02();
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(12345600, 2), result);
    }

    /**
     * testParse03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"-\\123,456.00"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal03<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"###,###,###.##"<br>
     * <br>
     * ���������t�H�[�}�b�g�ł��Ȃ�������<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:ParseException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�H�[�}�b�g������Ńp�[�V���O�ł��Ȃ��f�[�^���n���ꂽ�ꍇ�A ParseException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse03() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "-\\123,456.00";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        stub.setDecimal03(null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal03",
                        new Class[] { BigDecimal.class });
        String columnFormat = "###,###,###.##";

        try {
            // �e�X�g���{
            decimalColumnParser.parse(column, stub, method, columnFormat);
            fail("ParseException���������܂���ł����B");
        } catch (ParseException e) {
            // ����
            assertTrue(e instanceof ParseException);
        }
    }

    /**
     * testParse04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"-\\123,456.00"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal04<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fprivate<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalAccessException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�B�[���h��setter���\�b�h��private�Ő錾���ꂽ�ꍇ�A IllegalAccessException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse04() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "-\\123,456.00";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        UTUtil.setPrivateField(stub, "decimal04", null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal04",
                        new Class[] { BigDecimal.class });
        String columnFormat = "\\###,###,###.##";

        try {
            // �e�X�g���{
            decimalColumnParser.parse(column, stub, method, columnFormat);
            fail("IllegalAccessException���������܂���ł����B");
        } catch (IllegalAccessException e) {
            // ����
            assertTrue(e instanceof IllegalAccessException);
        }
    }

    /**
     * testParse05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"-\\123,456.00"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal05<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F��O����������B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:InvocationTargetException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�B�[���h��setter���\�b�h�̏����ŗ�O�����������ꍇ�A IvocationTargetException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse05() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "-\\123,456.00";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        UTUtil.setPrivateField(stub, "decimal05", null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal05",
                        new Class[] { BigDecimal.class });
        String columnFormat = "-\\###,###,###.##";

        try {
            // �e�X�g���{
            decimalColumnParser.parse(column, stub, method, columnFormat);
            fail("InvocationTargetException���������܂���ł����B");
        } catch (InvocationTargetException e) {
            // ����
            assertTrue(e instanceof InvocationTargetException);
        }
    }

    /**
     * testParse06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"-\\123,456.00"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal06<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F�Q��(BigDecimal�^�Q��)<br>
     * �E�����F��Ԗڂ̈�����Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:IllegalArgumentException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �t�B�[���h��setter���\�b�h��������BigDecimal�^�P�ȊO�����ꍇ�A llegalArgumentException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse06() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "-\\123,456.00";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        UTUtil.setPrivateField(stub, "decimal06", null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal06", new Class[] {
                        BigDecimal.class, BigDecimal.class });

        String columnFormat = "-\\###,###,###.##";

        try {
            // �e�X�g���{
            decimalColumnParser.parse(column, stub, method, columnFormat);
            fail("IllegalArgumentException���������܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * testParse07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:null<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal06<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:NullPointerException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * ������null�̏ꍇ�ANullPointerException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse07() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = null;
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        UTUtil.setPrivateField(stub, "decimal07", null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal07",
                        new Class[] { BigDecimal.class });
        String columnFormat = "-\\###,###,###.##";

        try {
            // �e�X�g���{
            decimalColumnParser.parse(column, stub, method, columnFormat);
            fail("NullPointerException���������܂���ł����B");
        } catch (NullPointerException e) {
            // ����
            assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * testParse08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"abc�������A�C�E���L"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal06<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * <br>
     * ���Ғl�F(��ԕω�) -:ParseException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �ُ�P�[�X<br>
     * �����������ł͂Ȃ��ꍇ�AParseException���������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse08() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "abc�������A�C�E���L";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        UTUtil.setPrivateField(stub, "decimal08", null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal08",
                        new Class[] { BigDecimal.class });
        String columnFormat = "-\\###,###,###.##";

        try {
            // �e�X�g���{
            decimalColumnParser.parse(column, stub, method, columnFormat);
            fail("ParseException���������܂���ł����B");
        } catch (ParseException e) {
            // ����
            assertTrue(e instanceof ParseException);
            String errorMessage = "Unparseable number: \"abc�������A�C�E���L\"";
            assertEquals(errorMessage, e.getMessage());
        }
    }

    /**
     * testParse09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC,E <br>
     * <br>
     * ���͒l�F(����) column:"123456"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal09<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:""<br>
     * <br>
     * ���Ғl�F(��ԕω�) t:�Ώۃt�B�[���h��BigDecimal.valueOf(123456)�l���i�[�����B<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕����񂪋󕶎��̏ꍇ�A ���t�B�[���h��setter���\�b�h���������ݒ肳��Ă���ꍇ�ɁA �����̕����񂪐�����BigDecimal�ɕϊ�����Ċi�[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse09() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "123456";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        stub.setDecimal09(null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal09",
                        new Class[] { BigDecimal.class });
        String columnFormat = "";

        // �e�X�g���{
        decimalColumnParser.parse(column, stub, method, columnFormat);

        // ����
        BigDecimal result = stub.getDecimal09();
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(123456), result);
    }

    /**
     * testParse10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) column:"-\\123,456.00"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal02<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * (���) dfMap:�v�f�������Ȃ�ConcurrentHashMap�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) t:�Ώۃt�B�[���h��BigDecimal.valueOf(12345600, 2)�l���i�[�����B<br>
     * (��ԕω�) DecimalFormatLocal#<init>:1��Ă΂��<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕�����ɑ΂���DecimalFormatLocal���L���b�V���ɑ��݂��Ȃ��ꍇ�A ���Ȃ����s����邱�Ƃ��m�F����B<br>
     * �܂��A�V�����������ꂽ�t�H�[�}�b�g�p�̕�����ɑ΂��� DecimalFormatLocal���L���b�V������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse10() throws Exception {
        // �O����
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "-\\123,456.00";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        stub.setDecimal10(null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal10",
                        new Class[] { BigDecimal.class });
        String columnFormat = "-\\###,###,###.##";

        ConcurrentHashMap<String, DecimalFormatLocal> dfMap = new ConcurrentHashMap<String, DecimalFormatLocal>();
        UTUtil.setPrivateField(decimalColumnParser, "dfMap", dfMap);
        dfMap.clear();

        // �e�X�g���{
        decimalColumnParser.parse(column, stub, method, columnFormat);

        // ����
        BigDecimal result = stub.getDecimal10();
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(12345600, 2), result);

        assertSame(dfMap, UTUtil.getPrivateField(decimalColumnParser, "dfMap"));
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
     * testParse11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) column:"-\\123,456.00"<br>
     * (����) t:�ȉ��̏���Object�C���X�^���X<br>
     * �E�t�B�[���h<br>
     * - �t�B�[���h���Fdecimal02<br>
     * - �^�FBigDecimal<br>
     * - �����l�Fnull<br>
     * �E���\�b�h<br>
     * - �t�B�[���h�ɑ΂���getter�Asetter���\�b�h�����݂���B<br>
     * ��setter���\�b�h�̒�`�͈���method�̋L�q�ɏ]���B<br>
     * (����) method:�Ώۃt�B�[���h�ɑ΂���setter���\�b�h�̃C���X�^���X<br>
     * �E�����Fpublic<br>
     * �E�����F1��(BigDecimal�^)<br>
     * �E�����F������Ώۃt�B�[���h�Ɋi�[����B<br>
     * (����) columnFormat:"-\\###,###,###.##"<br>
     * (���) dfMap:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"-\\###,###,###.##"<br>
     * value�F�L�[�ɑ΂���DecimalFormatLocal�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) t:�Ώۃt�B�[���h��BigDecimal.valueOf(12345600, 2)�l���i�[�����B<br>
     * (��ԕω�) dfMap:�ȉ��̗v�f������ConcurrentHashMap�C���X�^���X<br>
     * �Ekey�F"-\\###,###,###.##"<br>
     * value�F�L�[�ɑ΂���DecimalFormatLocal�C���X�^���X<br>
     * (��ԕω�) DecimalFormatLocal#<init>:�Ă΂�Ȃ�<br>
     * <br>
     * ����P�[�X<br>
     * �t�H�[�}�b�g�p�̕�����ɑ΂���DecimalFormatLocal���L���b�V���ɑ��݂���ꍇ�A ���Ȃ����s����邱�Ƃ��m�F����B<br>
     * �܂��A�t�H�[�}�b�g�p�̕�����ɑ΂��� DecimalFormatLocal���V������������Ȃ����Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse11() throws Exception {
        DecimalColumnParser decimalColumnParser = new DecimalColumnParser();

        // �O����(����)
        String column = "-\\123,456.00";
        DecimalColumnParser_FileLineObjectStub01 stub = new DecimalColumnParser_FileLineObjectStub01();

        stub.setDecimal11(null);

        Method method = DecimalColumnParser_FileLineObjectStub01.class
                .getDeclaredMethod("setDecimal11",
                        new Class[] { BigDecimal.class });
        String columnFormat = "-\\###,###,###.##";

        ConcurrentHashMap<String, DecimalFormatLocal> dfMap = new ConcurrentHashMap<String, DecimalFormatLocal>();
        DecimalFormatLocal dfMapValue = new DecimalFormatLocal(columnFormat);
        dfMap.put(columnFormat, dfMapValue);
        UTUtil.setPrivateField(decimalColumnParser, "dfMap", dfMap);

        VMOUTUtil.initialize();

        // �e�X�g���{
        decimalColumnParser.parse(column, stub, method, columnFormat);

        // ����
        BigDecimal result = stub.getDecimal11();
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(12345600, 2), result);

        assertSame(dfMap, UTUtil.getPrivateField(decimalColumnParser, "dfMap"));
        assertEquals(1, dfMap.size());
        assertTrue(dfMap.containsKey(columnFormat));
        DecimalFormatLocal formatLocal = dfMap.get(columnFormat);
        assertSame(dfMapValue, formatLocal);

        assertFalse(VMOUTUtil.isCalled(DecimalFormatLocal.class, "<init>"));
    }
}

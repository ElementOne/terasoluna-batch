/*
 * $Id: 
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.DateColumnParser} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �w�肳�ꂽ��������p�[�Y���ADate�^�ɕϊ�����B<br>
 * �ϊ����ʂ��t�@�C���s�I�u�W�F�N�g��Date�^�̑����ɒl���i�[����B
 * <p>
 * @author ���c �N�i
 * @see jp.terasoluna.fw.file.dao.standard.DateColumnParser
 */
public class DateColumnParserTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(DateColumnParserTest.class);
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
    public DateColumnParserTest(String name) {
        super(name);
    }

    /**
     * testParse01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) column:"20060101"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:null �������� �󕶎�<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� yyyyMMdd�`���̕������Date�^�Ƀp�[�X�����l<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * <br>
     * ����columnFormat��null���󕶎��ŁA�}�b�v����̏ꍇ�A �t�H�[�}�b�g�p�̃C���X�^���X���L���b�V������邱�Ƃ��m�F����B<br>
     * �t�@�C���s�I�u�W�F�N�g�ɁA����column�Őݒ肵��������� Date�^�Ƀp�[�X�����l���ݒ�ł��邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "20060101";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        UTUtil.setPrivateField(dateColumnParser, "map", map);

        // �e�X�g���{
        dateColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        // �����񂩂�Date�^�ɕϊ�
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setLenient(false);
        Date expected = simpleDateFormat.parse(column);
        assertEquals(expected, result);

        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));

        assertEquals(1, map.size());
        assertNotNull(map.get("yyyyMMdd"));
    }

    /**
     * testParse02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) column:"20060101"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:null �������� �󕶎�<br>
     * (���) map:key:"yyyyMMdd"<br>
     * value:new DateFormatLocal("yyyyMMdd")<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� yyyyMMdd�`���̕������Date�^�Ƀp�[�X�����l<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h���Ă΂�Ȃ��B<br>
     * <br>
     * ����columnFormat��null���󕶎��ŁA�}�b�v��"yyyyMMdd"���L�[�ɂ��� �L���b�V�������݂���ꍇ�́A�V���ɃL���b�V������Ȃ����ƁB<br>
     * �t�@�C���s�I�u�W�F�N�g�ɁA����column�Őݒ肵��������� Date�^�Ƀp�[�X�����l���ݒ�ł��邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "20060101";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        DateFormatLocal cache = new DateFormatLocal("yyyyMMdd");
        map.put("yyyyMMdd", cache);
        UTUtil.setPrivateField(dateColumnParser, "map", map);

        // �e�X�g���{
        dateColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setLenient(false);
        Date expected = simpleDateFormat.parse(column);
        assertEquals(expected, result);
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "put"));

        assertEquals(1, map.size());
        assertSame(cache, map.get("yyyyMMdd"));
    }

    /**
     * testParse03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) column:"20060101"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ��� Date�^������setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:null �������� �󕶎�<br>
     * (���) map:key:"yyyy-MM-dd"<br>
     * value:new DateFormatLocal("yyyy-MM-dd")<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� yyyyMMdd�`���̕������Date�^�Ƀp�[�X�����l<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * <br>
     * ����columnFormat��null���󕶎��ŁA�}�b�v��"yyyyMMdd"�ȊO���L�[�ɂ��� �L���b�V�������݂���ꍇ�́A"yyyyMMdd"���L�[�ɂ����t�H�[�}�b�g�p �C���X�^���X���L���b�V������邱�ƁB<br>
     * �t�@�C���s�I�u�W�F�N�g�ɁA����column�Őݒ肵���������Date�^�� �p�[�X�����l���ݒ�ł��邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "20060101";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        DateFormatLocal cache = new DateFormatLocal("yyyy-MM-dd");
        map.put("yyyy-MM-dd", cache);
        UTUtil.setPrivateField(dateColumnParser, "map", map);

        // �e�X�g���{
        dateColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setLenient(false);
        Date expected = simpleDateFormat.parse(column);
        assertEquals(expected, result);
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));

        assertEquals(2, map.size());
        assertNotNull(map.get("yyyyMMdd"));
        assertSame(cache, map.get("yyyy-MM-dd"));
    }

    /**
     * testParse04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) column:"2006-01-01"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� yyyy-MM-dd�`���̕������Date�^�Ƀp�[�X�����l<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * <br>
     * ����columnFormat�Ƀt�H�[�}�b�g�p�����񂪐ݒ肳��Ă���A �}�b�v����̏ꍇ�́AcolumnFormat�̕�������L�[�ɃL���b�V������邱�ƁB<br>
     * �t�@�C���s�I�u�W�F�N�g�ɁA����column�Őݒ肵���������Date�^�� �p�[�X�����l���ݒ�ł��邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2006-01-01";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        UTUtil.setPrivateField(dateColumnParser, "map", map);

        // �e�X�g���{
        dateColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        Date expected = simpleDateFormat.parse(column);
        assertEquals(expected, result);
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));

        assertEquals(1, map.size());
        assertNotNull(map.get("yyyy-MM-dd"));
    }

    /**
     * testParse05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) column:"2006-01-01"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ��� Date�^������setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:key:"yyyy-MM-dd"<br>
     * value:new DateFormatLocal("yyyy-MM-dd")<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� yyyy-MM-dd�`���̕������Date�^�Ƀp�[�X�����l<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h���Ă΂�Ȃ��B<br>
     * <br>
     * ����columnFormat�Ƀt�H�[�}�b�g�p�����񂪐ݒ肳��Ă���A �}�b�v�Ƀt�H�[�}�b�g�p��������L�[�ɂ����L���b�V�������݂���ꍇ�́A �V���ɃL���b�V������Ȃ����ƁB<br>
     * �t�@�C���s�I�u�W�F�N�g�ɁA����column�Őݒ肵���������Date�^�� �p�[�X�����l���ݒ�ł��邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2006-01-01";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        DateFormatLocal cache = new DateFormatLocal(columnFormat);
        map.put(columnFormat, cache);
        UTUtil.setPrivateField(dateColumnParser, "map", map);

        // �e�X�g���{
        dateColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        Date expected = simpleDateFormat.parse(column);
        assertEquals(expected, result);
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(0, VMOUTUtil.getCallCount(Map.class, "put"));

        assertEquals(1, map.size());
        assertSame(cache, map.get("yyyy-MM-dd"));
    }

    /**
     * testParse06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) column:"2006-01-01"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:key:"yyyy/MM/dd"<br>
     * value:new DateFormatLocal("yyyy/MM/dd")<br>
     * <br>
     * ���Ғl�F(��ԕω�) �t�@�C���s�I�u�W�F�N�g(t)�̑���:column�Őݒ肵�� yyyy-MM-dd�`���̕������Date�^�Ƀp�[�X�����l<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * <br>
     * ����columnFormat�Ƀt�H�[�}�b�g�p�����񂪐ݒ肳��Ă���A �}�b�v�Ƀt�H�[�}�b�g�p��������L�[�ɂ����L���b�V�������݂��Ȃ��ꍇ�́A �L���b�V������邱�ƁB<br>
     * �t�@�C���s�I�u�W�F�N�g�ɁA����column�Őݒ肵���������Date�^�� �p�[�X�����l���ݒ�ł��邱�ƁB <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2006-01-01";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        DateFormatLocal cache = new DateFormatLocal("yyyy/MM/dd");
        map.put("yyyy/MM/dd", cache);
        UTUtil.setPrivateField(dateColumnParser, "map", map);

        // �e�X�g���{
        dateColumnParser.parse(column, t, method, columnFormat);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(t, "a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        Date expected = simpleDateFormat.parse(column);
        assertEquals(expected, result);
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));

        assertEquals(2, map.size());
        assertSame(cache, map.get("yyyy/MM/dd"));
        assertNotNull(map.get("yyyy-MM-dd"));
    }

    /**
     * testParse07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"2006-01-01"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:format�ł��Ȃ�������<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ParseException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�H�[�}�b�g������ɂ��肦�Ȃ��l���ݒ肳�ꂽ�ꍇ�AParseException�� �������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2006-01-01";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd-MM";

        // �O������̐ݒ�
        // �f�t�H���g�ŗv�f�Ȃ�map�����̂ŁA�������Ȃ��B

        try {
            // �e�X�g���{
            dateColumnParser.parse(column, t, method, columnFormat);
            fail("ParseException���X���[����܂����B");
        } catch (ParseException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(ParseException.class, e.getClass());
            assertEquals("Unparseable date: \"2006-01-01\"", e.getMessage());
        }
    }

    /**
     * testParse08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"2006-01-01"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fprivate<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalAccessException���������邱�Ƃ� �m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��Date�^������setter���\�b�h�� �A�N�Z�X�ł��Ȃ��ꍇ�AIllegalAccessException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2006-01-01";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod(
                "setAPrivate", new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        // �f�t�H���g�ŗv�f�Ȃ�map�����̂ŁA�������Ȃ��B

        try {
            // �e�X�g���{
            dateColumnParser.parse(column, t, method, columnFormat);
            fail("IllegalAccessException���X���[����܂���ł����B");
        } catch (IllegalAccessException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalAccessException.class, e.getClass());
        }
    }

    /**
     * testParse09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"2006-01-01"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�F��O���X���[����<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvocationTargetException�� �������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��Date�^������setter���\�b�h����O�� �X���[����ꍇ�Asetter���\�b�h���X���[������O�����b�v���� InvocationTargetException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2006-01-01";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod(
                "setAException", new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        // �f�t�H���g�ŗv�f�Ȃ�map�����̂ŁA�������Ȃ��B

        try {
            // �e�X�g���{
            dateColumnParser.parse(column, t, method, columnFormat);
            fail("InvocationTargetException���X���[����܂����B");
        } catch (InvocationTargetException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(InvocationTargetException.class, e.getClass());
        }
    }

    /**
     * testParse10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"2006-01-01"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F����<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�� �������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��Date�^������setter���\�b�h�̈�������������ꍇ�A IllegalArgumentException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2006-01-01";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod(
                "setAAndB", new Class[] { Date.class, Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        // �f�t�H���g�ŗv�f�Ȃ�map�����̂ŁA�������Ȃ��B

        try {
            // �e�X�g���{
            dateColumnParser.parse(column, t, method, columnFormat);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    /**
     * testParse11() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"2008-01-32"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ParseException���������邱�Ƃ��m�F����B<br>
     * <br>
     * Date�^�̑����̒l���z�肳��Ȃ����t�̏ꍇ�A ParseException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "2008-01-32";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        // �f�t�H���g�ŗv�f�Ȃ�map�����̂ŁA�������Ȃ��B

        try {
            // �e�X�g���{
            dateColumnParser.parse(column, t, method, columnFormat);
            fail("ParseException���X���[����܂���ł����B");
        } catch (ParseException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(ParseException.class, e.getClass());
            assertEquals("Unparseable date: \"2008-01-32\"", e.getMessage());
        }
    }

    /**
     * testParse12() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:null<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException�� �������邱�Ƃ��m�F����B<br>
     * <br>
     * ����column��null���ݒ肳�ꂽ�ꍇ�́ANullPointerException�� �������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse12() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = null;
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        // �f�t�H���g�ŗv�f�Ȃ�map�����̂ŁA�������Ȃ��B

        try {
            // �e�X�g���{
            dateColumnParser.parse(column, t, method, columnFormat);
            fail("NullPointerException���X���[����܂���ł����B");
        } catch (NullPointerException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    /**
     * testParse13() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) column:"20080101"<br>
     * (����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:Date�^�̃t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������ setter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F1��<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy-MM-dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ParseException���������邱�Ƃ��m�F����B<br>
     * <br>
     * ������column�̃t�H�[�}�b�g�ƃt�H�[�}�b�g�p�̕����񂪈قȂ�ꍇ�́A ParseException���������邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParse13() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        DateColumnParser dateColumnParser = new DateColumnParser();

        // �����̐ݒ�
        String column = "20080101";
        DateColumnParser_Stub01 t = new DateColumnParser_Stub01();
        Method method = DateColumnParser_Stub01.class.getDeclaredMethod("setA",
                new Class[] { Date.class });
        String columnFormat = "yyyy-MM-dd";

        // �O������̐ݒ�
        // �f�t�H���g�ŗv�f�Ȃ�map�����̂ŁA�������Ȃ��B

        try {
            // �e�X�g���{
            dateColumnParser.parse(column, t, method, columnFormat);
            fail("ParseException���X���[����܂���ł����B");
        } catch (ParseException e) {
            // �ԋp�l�̊m�F
            // �Ȃ�

            // ��ԕω��̊m�F
            assertEquals(ParseException.class, e.getClass());
            assertEquals("Unparseable date: \"20080101\"", e.getMessage());
        }
    }

}

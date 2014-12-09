/*
 * $Id: DateColumnFormatterTest.java 5354 2007-10-03 06:06:25Z anh $
 * 
 * Copyright (c) 2006 NTT DATA Corporation
 * 
 */

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.DateColumnFormatter} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �A�m�e�[�V����columnFormat�̋L�q�ɏ]���A������̕ϊ��������s���B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.DateColumnFormatter
 */
public class DateColumnFormatterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(DateColumnFormatterTest.class);
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
    public DateColumnFormatterTest(String name) {
        super(name);
    }

    /**
     * testFormat01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:null<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��Date�^�����Ɋi�[����Ă���l��yyyyMMdd�t�H�[�}�b�g�̕�����ŏo�͂����<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) DateFormatLocal�R���X�g���N�^:���\�b�h��1��Ă΂��B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����Date�^�����Ɋi�[����Ă���I�u�W�F�N�g�̕�������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B�t�H�[�}�b�g�p�̕�����null�������͋󕶎��̏ꍇ�̓t�H�[�}�b�g�������s�킸("yyyyMMdd")�ɏo�͂���邱�Ƃ��m�F����
     * �B�}�b�v�Ƀt�H�[�}�b�g�p�̕�������L���b�V������B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFormat01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = null;

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        UTUtil.setPrivateField(columnFormatter, "map", map);

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("19700101", testResult);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals("yyyyMMdd", VMOUTUtil.getArgument(Map.class, "get", 0, 0));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        List arguments = VMOUTUtil.getArguments(Map.class, "put", 0);
        assertEquals("yyyyMMdd", arguments.get(0));
        assertTrue(arguments.get(1) instanceof DateFormatLocal);
        Map getMap = (Map) UTUtil.getPrivateField(columnFormatter, "map");
        assertEquals(1, getMap.size());
        assertEquals(1, VMOUTUtil.getCallCount(DateFormatLocal.class, "<init>"));
        assertEquals("yyyyMMdd", VMOUTUtil.getArgument(DateFormatLocal.class,
                "<init>", 0, 0));
    }

    /**
     * testFormat02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:""<br>
     * (���) map:key:"yyyyMMdd"<br>
     * value:new DateFormatLocal("yyyyMMdd")<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��Date�^�����Ɋi�[����Ă���l��yyyyMMdd�t�H�[�}�b�g�̕�����ŏo�͂����<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h���Ă΂�Ȃ��B<br>
     * (��ԕω�) DateFormatLocal�R���X�g���N�^:���\�b�h���Ă΂�Ȃ��B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����Date�^�����Ɋi�[����Ă���I�u�W�F�N�g�̕�������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B�}�b�v����擾�����t�H�[�}�b�g�p�̕�����Őݒ肳�ꂽ�Ƃ���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFormat02() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        map.put("yyyyMMdd", new DateFormatLocal("yyyyMMdd"));
        UTUtil.setPrivateField(columnFormatter, "map", map);

        VMOUTUtil.initialize();

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("19700101", testResult);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals("yyyyMMdd", VMOUTUtil.getArgument(Map.class, "get", 0, 0));
        assertFalse(VMOUTUtil.isCalled(Map.class, "put"));
        assertFalse(VMOUTUtil.isCalled(DateFormatLocal.class, "<init>"));
    }

    /**
     * testFormat03() <br>
     * <br>
     * (����n)<br>
     * <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:""<br>
     * (���) map:key:"yyyy-MM-dd"<br>
     * value:new DateFormatLocal("yyyy-MM-dd")<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��Date�^�����Ɋi�[����Ă���l��yyyyMMdd�t�H�[�}�b�g�̕�����ŏo�͂����<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) DateFormatLocal�R���X�g���N�^:���\�b�h��1��Ă΂��B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����Date�^�����Ɋi�[����Ă���I�u�W�F�N�g�̕�������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B�t�H�[�}�b�g�p�̕�����null�������͋󕶎��̏ꍇ�̓t�H�[�}�b�g�������s�킸("yyyyMMdd")�ɏo�͂���邱�Ƃ��m�F����
     * �B�}�b�v�Ƀt�H�[�}�b�g�p�̕�������L���b�V������B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFormat03() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        map.put("yyyy-MM-dd", new DateFormatLocal("yyyy-MM-dd"));
        UTUtil.setPrivateField(columnFormatter, "map", map);

        VMOUTUtil.initialize();

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("19700101", testResult);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals("yyyyMMdd", VMOUTUtil.getArgument(Map.class, "get", 0, 0));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        List arguments = VMOUTUtil.getArguments(Map.class, "put", 0);
        assertEquals("yyyyMMdd", arguments.get(0));
        assertTrue(arguments.get(1) instanceof DateFormatLocal);
        Map getMap = (Map) UTUtil.getPrivateField(columnFormatter, "map");
        assertEquals(2, getMap.size());
        assertEquals(1, VMOUTUtil.getCallCount(DateFormatLocal.class, "<init>"));
        assertEquals("yyyyMMdd", VMOUTUtil.getArgument(DateFormatLocal.class,
                "<init>", 0, 0));
    }

    /**
     * testFormat04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��Date�^�����Ɋi�[����Ă���l�̕�����BcolumnFormat�Œ�`�����t�H�[�}�b�g�ŏo�͂����B<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) DateFormatLocal�R���X�g���N�^:���\�b�h��1��Ă΂��B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����Date�^�����Ɋi�[����Ă���I�u�W�F�N�g�̕�������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B�t�H�[�}�b�g�p�̕�����Őݒ肳�ꂽ�Ƃ���ɏo�͂���邱�Ƃ��m�F����B�}�b�v�Ƀt�H�[�}�b�g�p�̕�������L���b�V������B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFormat04() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "yyyy/MM/dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        UTUtil.setPrivateField(columnFormatter, "map", map);

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("1970/01/01", testResult);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(columnFormat, VMOUTUtil
                .getArgument(Map.class, "get", 0, 0));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        List arguments = VMOUTUtil.getArguments(Map.class, "put", 0);
        assertEquals(columnFormat, arguments.get(0));
        assertTrue(arguments.get(1) instanceof DateFormatLocal);
        Map getMap = (Map) UTUtil.getPrivateField(columnFormatter, "map");
        assertEquals(1, getMap.size());
        assertEquals(1, VMOUTUtil.getCallCount(DateFormatLocal.class, "<init>"));
        assertEquals(columnFormat, VMOUTUtil.getArgument(DateFormatLocal.class,
                "<init>", 0, 0));
    }

    /**
     * testFormat05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * (���) map:key:"yyyy/MM/dd"<br>
     * value:new DateFormatLocal("yyyy/MM/dd")<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��Date�^�����Ɋi�[����Ă���l�̕�����BcolumnFormat�Œ�`�����t�H�[�}�b�g�ŏo�͂����B<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h���Ă΂�Ȃ��B<br>
     * (��ԕω�) DateFormatLocal�R���X�g���N�^:���\�b�h���Ă΂�Ȃ��B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����Date�^�����Ɋi�[����Ă���I�u�W�F�N�g�̕�������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B�}�b�v����擾�����t�H�[�}�b�g�p�̕�����Őݒ肳�ꂽ�Ƃ���ɏo�͂���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat05() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "yyyy/MM/dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        map.put(columnFormat, new DateFormatLocal(columnFormat));
        UTUtil.setPrivateField(columnFormatter, "map", map);

        VMOUTUtil.initialize();

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("1970/01/01", testResult);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(columnFormat, VMOUTUtil
                .getArgument(Map.class, "get", 0, 0));
        assertFalse(VMOUTUtil.isCalled(Map.class, "put"));
        assertFalse(VMOUTUtil.isCalled(DateFormatLocal.class, "<init>"));
    }

    /**
     * testFormat06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * (���) map:key:"yyyy-MM-dd"<br>
     * value:new DateFormatLocal("yyyy-MM-dd")<br>
     * <br>
     * ���Ғl�F(�߂�l) String:������method��Date�^�����Ɋi�[����Ă���l�̕�����BcolumnFormat�Œ�`�����t�H�[�}�b�g�ŏo�͂����B<br>
     * (��ԕω�) Map.get():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) Map.put():���\�b�h��1��Ă΂��B<br>
     * (��ԕω�) DateFormatLocal�R���X�g���N�^:���\�b�h��1��Ă΂��B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g����Date�^�����Ɋi�[����Ă���I�u�W�F�N�g�̕�������擾���邱�Ƃ��ł��邱�Ƃ��m�F����B�t�H�[�}�b�g�p�̕�����Őݒ肳�ꂽ�Ƃ���ɏo�͂���邱�Ƃ��m�F����B�}�b�v�Ƀt�H�[�}�b�g�p�̕�������L���b�V������B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFormat06() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "yyyy/MM/dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        map.put("yyyy-MM-dd", new DateFormatLocal("yyyy-MM-dd"));
        UTUtil.setPrivateField(columnFormatter, "map", map);

        VMOUTUtil.initialize();

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("1970/01/01", testResult);

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "get"));
        assertEquals(columnFormat, VMOUTUtil
                .getArgument(Map.class, "get", 0, 0));
        assertEquals(1, VMOUTUtil.getCallCount(Map.class, "put"));
        List arguments = VMOUTUtil.getArguments(Map.class, "put", 0);
        assertEquals(columnFormat, arguments.get(0));
        assertTrue(arguments.get(1) instanceof DateFormatLocal);
        Map getMap = (Map) UTUtil.getPrivateField(columnFormatter, "map");
        assertEquals(2, getMap.size());
        assertEquals(1, VMOUTUtil.getCallCount(DateFormatLocal.class, "<init>"));
        assertEquals(columnFormat, VMOUTUtil.getArgument(DateFormatLocal.class,
                "<init>", 0, 0));
    }

    /**
     * testFormat07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fprivate<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalAccessException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��Date�^������getter���\�b�h�ɃA�N�Z�X�ł��Ȃ��ꍇ�AIllegalAccessException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat07() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub02 stub = new DateColumnFormatter_Stub02();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getDeclaredMethod("getDate");
        String columnFormat = "yyyy/MM/dd";

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
     * testFormat08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�F��O���X���[����<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvocationTargetException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��Date�^������getter���\�b�h����O���X���[����ꍇ�Agetter���\�b�h���X���[������O�����b�v����InvocationTargetException���X���[���邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat08() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub03 stub = new DateColumnFormatter_Stub03();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "yyyy/MM/dd";

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
     * testFormat09() <br>
     * <br>
     * (�ُ�n) <br>
     * <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F����<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException���������邱�Ƃ��m�F����B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g��Date�^������getter���\�b�h�������̈��������ꍇ�AIllegalArgumentException���X���[���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat09() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub04 stub = new DateColumnFormatter_Stub04();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate",
                new Class[] { Date.class });
        String columnFormat = "yyyy/MM/dd";

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
     * testFormat10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnew Date(0)<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"AAA"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException���������邱�Ƃ��m�F����B<br>
     * <br>
     * columnFormat�ɐ������Ȃ����t�p�^�[������ꂽ�ꍇ�́AIllegalArgumentException���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat10() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "AAA";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        UTUtil.setPrivateField(columnFormatter, "map", map);

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
     * testFormat11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE, F <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:not null<br>
     * Date�^�t�B�[���h������<br>
     * �@�l�Fnull<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(�߂�l) String:�󕶎�<br>
     * (��ԕω�) Map.get():���\�b�h���Ă΂�Ȃ��B<br>
     * (��ԕω�) Map.put():���\�b�h���Ă΂�Ȃ��B<br>
     * (��ԕω�) DateFormatLocal�R���X�g���N�^:���\�b�h���Ă΂�Ȃ��B<br>
     * <br>
     * ����t��Date�^�t�B�[���h��null���ݒ肳��Ă����ꍇ�́A�󕶎����ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat11() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "yyyy/MM/dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        UTUtil.setPrivateField(columnFormatter, "map", map);

        // �e�X�g���{
        String testResult = columnFormatter.format(stub, method, columnFormat);

        // �ԋp�l�̊m�F
        assertEquals("", testResult);

        // ��ԕω��̊m�F
        assertFalse(VMOUTUtil.isCalled(Map.class, "get"));
        assertFalse(VMOUTUtil.isCalled(Map.class, "put"));
        assertFalse(VMOUTUtil.isCalled(DateFormatLocal.class, "<init>"));
    }

    /**
     * testFormat12() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) �t�@�C���s�I�u�W�F�N�g<br>
     * ��:null<br>
     * (����) �t�@�C���s�I�u�W�F�N�g(t)�ɂ���Date�^������getter���\�b�h<br>
     * method:�ȉ��̐ݒ������Method�C���X�^���X<br>
     * �@�����Fpublic<br>
     * �@�����F�Ȃ�<br>
     * (����) �t�H�[�}�b�g�p�̕�����<br>
     * columnFormat:"yyyy/MM/dd"<br>
     * (���) map:�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException���������邱�Ƃ��m�F����B<br>
     * <br>
     * ����t��null�������ꍇ�́ANullPointerException���X���[����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testFormat12() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        ColumnFormatter columnFormatter = new DateColumnFormatter();

        // �����̐ݒ�
        DateColumnFormatter_Stub01 stub = new DateColumnFormatter_Stub01();
        UTUtil.setPrivateField(stub, "date", new Date(0));
        Method method = stub.getClass().getMethod("getDate");
        String columnFormat = "yyyy/MM/dd";

        // �O������̐ݒ�
        Map<String, DateFormatLocal> map = new ConcurrentHashMap<String, DateFormatLocal>();
        UTUtil.setPrivateField(columnFormatter, "map", map);

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

}

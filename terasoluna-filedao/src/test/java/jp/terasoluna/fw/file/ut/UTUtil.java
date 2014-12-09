/*
 * $Id: UTUtil.java 5230 2007-09-28 10:04:13Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */
package jp.terasoluna.fw.file.ut;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

/**
 * �� JUnit�ł̒P�̃e�X�g���T�|�[�g���郆�[�e�B���e�B�N���X�B
 * @version 2003.08.29
 * @author �O�H ��
 */
public class UTUtil {

    /**
     * ���t�̃t�H�[�}�b�g������
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * ���t���r���邽�߂�assert���[�e�B���e�B���\�b�h�B <BR>
     * <BR>
     * selectRows()��DATE�^�̒l��"yyyy-MM-dd HH:mm:ss.fffffffff"�t�H�[�}�b�g�� �ԋp����悤�ɂȂ��Ă��邽�߁A���̂܂܂ł͒l�̔�r�����Â炢�B<BR>
     * ���̂��߁A���t�p��assert���[�e�B���e�B���\�b�h��p�ӂ��Ă���B <BR>
     * <BR>
     * 
     * <pre>
     * [�g�p��]
     * List list = UTUtil.selectRows(&quot;CUSTOMER&quot;, &quot;C_ID=1&quot;);
     * Map map = (Map) list.get(0);
     * UTUtil.assertEqualsDate(&quot;2003-01-01&quot;, map.get(&quot;C_REGIST_DATE&quot;));
     * </pre>
     * @param expected ���Ғl
     * @param actual ���ۂ̒l
     */
    public static void assertEqualsDate(Object expected, Object actual) {

        // �p�����[�^��String�^�łȂ���Η�O��Ԃ��B
        if ((expected != null) && (!(expected instanceof String))) {
            throw new IllegalArgumentException();
        }
        if ((actual != null) && (!(actual instanceof String))) {
            throw new IllegalArgumentException();
        }

        // "yyyy-MM-dd HH:mm:ss.fffffffff"��"yyyy-MM-dd"�������o���B
        String actualDate = (actual != null) ? ((String) actual).substring(0,
                10) : null;

        // String�ɃL���X�g�������assertEquals����B
        String expectedDate = (String) expected;
        Assert.assertEquals(expectedDate, actualDate);
    }

    /**
     * �������r���邽�߂�assert���[�e�B���e�B���\�b�h�B <BR>
     * <BR>
     * selectRows()��DATE�^�̒l��"yyyy-MM-dd HH:mm:ss.fffffffff"�t�H�[�}�b�g�� �ԋp����悤�ɂȂ��Ă��邽�߁A���̂܂܂ł͒l�̔�r�����Â炢�B<BR>
     * ���̂��߁A�����p��assert���[�e�B���e�B���\�b�h��p�ӂ��Ă���B <BR>
     * <BR>
     * 
     * <pre>
     * [�g�p��]
     * List list = UTUtil.selectRows(&quot;CUSTOMER&quot;, &quot;C_ID=1&quot;);
     * Map map = (Map) list.get(0);
     * UTUtil.assertEqualsDate(&quot;12:34:56&quot;, map.get(&quot;C_UPDATE_TIME&quot;));
     * </pre>
     * @param expected ���Ғl
     * @param actual ���ۂ̒l
     */
    public static void assertEqualsTime(Object expected, Object actual) {

        // �p�����[�^��String�^�łȂ���Η�O��Ԃ��B
        if ((expected != null) && (!(expected instanceof String))) {
            throw new IllegalArgumentException();
        }
        if ((actual != null) && (!(actual instanceof String))) {
            throw new IllegalArgumentException();
        }

        // "yyyy-MM-dd HH:mm:ss.fffffffff"��"HH:mm:ss"�������o���B
        String actualTime = (actual != null) ? ((String) actual).substring(11,
                19) : null;

        // String�ɃL���X�g�������assertEquals����B
        String expectedTime = (String) expected;
        Assert.assertEquals(expectedTime, actualTime);
    }

    /**
     * ���t�{�������r���邽�߂�assert���[�e�B���e�B���\�b�h�B <BR>
     * <BR>
     * selectRows()��DATE�^�̒l��"yyyy-MM-dd HH:mm:ss.fffffffff"�t�H�[�}�b�g�� �ԋp����悤�ɂȂ��Ă��邽�߁A���̂܂܂ł͒l�̔�r�����Â炢�B<BR>
     * ���̂��߁A���t�{�����p��assert���[�e�B���e�B���\�b�h��p�ӂ��Ă���B <BR>
     * <BR>
     * 
     * <pre>
     * [�g�p��]
     * List list = UTUtil.selectRows(&quot;CUSTOMER&quot;, &quot;C_ID=1&quot;);
     * Map map = (Map) list.get(0);
     * UTUtil.assertEqualsDateTime(&quot;2003-01-01 12:00:05&quot;,
     *     map.get(&quot;C_UPDATE_DATE&quot;));
     * </pre>
     * @param expected ���Ғl
     * @param actual ���ۂ̒l
     */
    public static void assertEqualsDateTime(Object expected, Object actual) {

        // �p�����[�^��String�^�łȂ���Η�O��Ԃ��B
        if ((expected != null) && (!(expected instanceof String))) {
            throw new IllegalArgumentException();
        }
        if ((actual != null) && (!(actual instanceof String))) {
            throw new IllegalArgumentException();
        }

        // "yyyy-MM-dd HH:mm:ss.fffffffff"��"yyyy-MM-dd HH:mm:ss"�������o���B
        String actualDateTime = (actual != null) ? ((String) actual).substring(
                0, 19) : null;

        // String�ɃL���X�g�������assertEquals����B
        String expectedDateTime = (String) expected;
        Assert.assertEquals(expectedDateTime, actualDateTime);
    }

    /**
     * ���ݓ��t�Ɣ�r���邽�߂�assert���[�e�B���e�B���\�b�h�B <BR>
     * <BR>
     * selectRows()��DATE�^�̒l��"yyyy-MM-dd HH:mm:ss.fffffffff"�t�H�[�}�b�g�� �ԋp����悤�ɂȂ��Ă��邽�߁A���̂܂܂ł͒l�̔�r�����Â炢�B<BR>
     * ���̂��߁A���ݓ��t�Ɣ�r����assert���[�e�B���e�B���\�b�h��p�ӂ��Ă���B
     * 
     * <pre>
     * [�g�p��]
     * List list = UTUtil.selectRows(&quot;CUSTOMER&quot;, &quot;C_ID=1&quot;);
     * Map map = (Map) list.get(0);
     * UTUtil.assertEqualsToday(map.get(&quot;C_UPDATE_DATE&quot;));
     * </pre>
     * @param actual ���ۂ̒l
     */
    public static void assertEqualsToday(Object actual) {

        // �p�����[�^��String�^�łȂ���Η�O��Ԃ��B
        if ((actual != null) && (!(actual instanceof String))) {
            throw new IllegalArgumentException();
        }

        // ���ݓ��t�̕�������擾����B
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT);
        String currentDate = f.format(d);

        // ���t�̕������assertEquals����B
        assertEqualsDate(currentDate, actual);
    }

    /**
     * �t�@�C���̒��g���o�C�i����r����B
     * 
     * <pre>
     * [�g�p��]
     * class SampleTest extends TestCase {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testDoSomething() throws Excepton {
     *         // �Ȃ�炩�̏��������s�����ʂ̃t�@�C�����߂����B
     *         File actual = Sample.doSomething();
     * 
     *         // ��r���邽�߂̊��Ғl�f�[�^�̃t�@�C�����擾����B
     *         // �t�@�C����SampleTest�N���X�Ɠ����t�H���_�ɒu���Ă���B
     *         File expected = UTUtil.getFile(this, &quot;expected.txt&quot;);
     * 
     *         // �t�@�C���̔�r������B
     *         UTUtil.assertEqualsFile(expected, actual);
     *          
     *     }
     * }
     * </pre>
     * @param expected ���Ғl�̃t�@�C��
     * @param actual ���ۂ̒l�̃t�@�C��
     */
    public static void assertEqualsFile(File expected, File actual) {

        // �p�����[�^��null�̏ꍇ�͗�O��Ԃ��B
        if ((expected == null) || (actual == null)) {
            throw new IllegalArgumentException();
        }

        // ���g���r���邽�߂̃��b�p�[�I�u�W�F�N�g(FileContent)�𐶐�
        FileContent expectedContent = new FileContent(expected);
        FileContent actualContent = new FileContent(actual);

        // assert�ɂ�����B
        Assert.assertTrue(expectedContent.equals(actualContent));
    }

    /**
     * �e�X�g�N���X�Ɠ����t�H���_�ɒu���Ă���e�X�g�p�t�@�C���� File�I�u�W�F�N�g���擾����B
     * 
     * <pre>
     * [�g�p��]
     * class SampleTest extends TestCase {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testDoSomething() throws Excepton {
     *         // �Ȃ�炩�̏��������s�����ʂ̃t�@�C�����߂����B
     *         File actual = Sample.doSomething();
     * 
     *         // ��r���邽�߂̊��Ғl�f�[�^�̃t�@�C�����擾����B
     *         // �t�@�C����SampleTest�N���X�Ɠ����t�H���_�ɒu���Ă���B
     *         File expected = UTUtil.getFile(SampleTest.class, &quot;expected.txt&quot;);
     * 
     *         // �t�@�C���̔�r������B
     *         UTUtil.assertEqualsFile(expected, actual);
     *          
     *     }
     * }
     * </pre>
     * @param cls �e�X�g�N���X��Class�I�u�W�F�N�g
     * @param filename �t�@�C����
     * @return �w�肳�ꂽ�t�@�C������File�I�u�W�F�N�g�B �t�@�C�������݂��Ȃ��ꍇ��null��Ԃ��B
     */
    @SuppressWarnings("unchecked")
    public static File getFile(Class cls, String filename) {

        // �t�@�C�������k���A�󕶎���̏ꍇ�̓k����Ԃ��B
        if ((filename == null) || ("".equals(filename))) {
            return null;
        }

        // �w�肳�ꂽ�t�@�C������URL���擾����B
        // "file://c:/folder/filename"�̂悤��URL��������B
        URL url = cls.getResource(filename);

        // URL���k���Ȃ�΃k����Ԃ��B
        if (url == null) {
            return null;
        }

        // URL����URI�𐶐�����B
        // URISyntaxException���������邱�Ƃ͎����゠�蓾�Ȃ��̂ŁAcatch�ȉ���
        // �R�[�h�̓k����Ԃ������ɂ��Ă���B
        URI uri = null;
        try {
            uri = new URI(url.toString());
        } catch (URISyntaxException e) {
            return null;
        }

        // File�I�u�W�F�N�g�𐶐����ĕԂ��B
        return new File(uri);
    }

    /**
     * �e�X�g�N���X�Ɠ����t�H���_�ɒu���Ă���e�X�g�p�t�@�C���� File�I�u�W�F�N�g���擾����B
     * 
     * <pre>
     * [�g�p��]
     * class SampleTest extends TestCase {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testDoSomething() throws Excepton {
     *         // �Ȃ�炩�̏��������s�����ʂ̃t�@�C�����߂����B
     *         File actual = Sample.doSomething();
     * 
     *         // ��r���邽�߂̊��Ғl�f�[�^�̃t�@�C�����擾����B
     *         // �t�@�C����SampleTest�N���X�Ɠ����t�H���_�ɒu���Ă���B
     *         File expected = UTUtil.getFile(this, &quot;expected.txt&quot;);
     * 
     *         // �t�@�C���̔�r������B
     *         UTUtil.assertEqualsFile(expected, actual);
     *          
     *     }
     * }
     * </pre>
     * @param instance �e�X�g�N���X�̃C���X�^���X
     * @param filename �t�@�C����
     * @return �w�肳�ꂽ�t�@�C������File�I�u�W�F�N�g�B �t�@�C�������݂��Ȃ��ꍇ��null��Ԃ��B
     */
    public static File getFile(Object instance, String filename) {
        return getFile(instance.getClass(), filename);
    }

    /**
     * private���\�b�h�istatic�łȂ����́j���Ăяo���B<BR>
     * �p�����[�^0�`2�̃��\�b�h�ɂ͐�p�̃��\�b�h���p�ӂ���Ă���̂ŁA ������𗘗p���������V���v���ɋL�q�ł���B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private int calcAdd(int val1, int val2, int val3) {
     *         return val1 + val2 + val3;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testCalcAdd() {
     *         Sample sample = new Sample();
     *         Integer result = (Integer) UTUtil.invokePrivate(
     *             sample,
     *             &quot;calcAdd&quot;,
     *             new Class[] { int.class, int.class, int.class },
     *             new Object[] { new Integer(1), new Integer(2), 
     *                            new Integer(3) }
     *         );
     *         assertEquals(6, result.intValue());
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃I�u�W�F�N�g
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @param argTypes �����̌^�̔z��
     * @param args �����̒l�̔z��B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokePrivate(Object target, String methodName,
            Class[] argTypes, Object[] args) throws Exception {

        return PrivateAccessUtil.invokePrivate(target, methodName, argTypes,
                args);
    }

    /**
     * private���\�b�h�istatic�łȂ����́j���Ăяo���i�p�����[�^0�p�j�B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private int getString() {
     *         return &quot;success&quot;;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testGetString() {
     *         Sample sample = new Sample();
     *         String result = 
     *             (String) UTUtil.invokePrivate(sample, &quot;getString&quot;):
     *         assertEquals(&quot;success&quot;, result);
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃I�u�W�F�N�g
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    public static Object invokePrivate(Object target, String methodName)
                                                                        throws Exception {

        return invokePrivate(target, methodName, new Class[] {},
                new Object[] {});
    }

    /**
     * private���\�b�h�istatic�łȂ����́j���Ăяo���i�p�����[�^1�p�j�B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private long square(long val) {
     *         return val &circ; 2;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testSquare() {
     *         Sample sample = new Sample();
     *         Long result = (Long) UTUtil.invokePrivate(
     *             sample, &quot;square&quot;, long.class, new Long(2L)):
     *         assertEquals(4, result.longValue());
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃I�u�W�F�N�g
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @param argType �����̌^
     * @param arg �����̒l�B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokePrivate(Object target, String methodName,
            Class argType, Object arg) throws Exception {

        return invokePrivate(target, methodName, new Class[] { argType },
                new Object[] { arg });
    }

    /**
     * private���\�b�h�istatic�łȂ����́j���Ăяo���i�p�����[�^2�p�j�B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private static int calcAdd(int val1, int val2) {
     *         return val1 + val2;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testCalcAdd() {
     *         Sample sample = new Sample();
     *         Integer result = (Integer) UTUtil.invokePrivate(
     *             sample,
     *             &quot;calcAdd&quot;,
     *             int.class,
     *             int.class,
     *             new Integer(1),
     *             new Integer(2)
     *         );
     *         assertEquals(3, result.intValue());
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃I�u�W�F�N�g
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @param argType1 �������̌^
     * @param argType2 �������̌^
     * @param arg1 �������̒l�B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @param arg2 �������̒l�B
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokePrivate(Object target, String methodName,
            Class argType1, Class argType2, Object arg1, Object arg2)
                                                                     throws Exception {

        return invokePrivate(target, methodName, new Class[] { argType1,
                argType2 }, new Object[] { arg1, arg2 });
    }

    /**
     * static��private���\�b�h���Ăяo���B <BR>
     * <BR>
     * �p�����[�^0�`2�̃��\�b�h�ɂ͐�p�̃��\�b�h���p�ӂ���Ă���̂ŁA ������𗘗p���������V���v���ɋL�q�ł���B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private static int calcAdd(int val1, int val2, int val3) {
     *         return val1 + val2 + val3;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testCalcAdd() {
     *         Integer result = (Integer) UTUtil.invokePrivate(
     *             Sample.class,
     *             &quot;calcAdd&quot;,
     *             new Class[] { int.class, int.class, int.class },
     *             new Object[] { new Integer(1), new Integer(2), 
     *                            new Integer(3) }
     *         );
     *         assertEquals(6, result.intValue());
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃N���X
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @param argTypes �����̌^�̔z��
     * @param args �����̒l�̔z��B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokePrivate(Class target, String methodName,
            Class[] argTypes, Object[] args) throws Exception {

        return PrivateAccessUtil.invokePrivate(target, methodName, argTypes,
                args);
    }

    /**
     * static��private���\�b�h���Ăяo���i�p�����[�^0�p�j�B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private static int getString() {
     *         return &quot;success&quot;;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testGetString() {
     *         String result = (String) UTUtil.invokePrivate(
     *             Sample.class, &quot;getString&quot;):
     *         assertEquals(&quot;success&quot;, result);
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃N���X
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokePrivate(Class target, String methodName)
                                                                       throws Exception {

        return invokePrivate(target, methodName, new Class[] {},
                new Object[] {});
    }

    /**
     * static��private���\�b�h���Ăяo���i�p�����[�^1�p�j�B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private static long square(long val) {
     *         return val &circ; 2;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testSquare() {
     *         Long result = (Long) UTUtil.invokePrivate(
     *             Sample.class, &quot;square&quot;, long.class, new Long(2L)):
     *         assertEquals(4, result.longValue());
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃N���X
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @param argType �����̌^
     * @param arg �����̒l�B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokePrivate(Class target, String methodName,
            Class argType, Object arg) throws Exception {

        return invokePrivate(target, methodName, new Class[] { argType },
                new Object[] { arg });
    }

    /**
     * static��private���\�b�h���Ăяo���i�p�����[�^2�p�j�B
     * 
     * <pre>
     * [�g�p��]
     * class Sample {
     *     private static int calcAdd(int val1, int val2) {
     *         return val1 + val2;
     *     }
     * }
     * 
     * class SampleTest {
     *     &#064;SuppressWarnings(&quot;unchecked&quot;) public void testCalcAdd() {
     *         Integer result = (Integer) UTUtil.invokePrivate(
     *             Sample.class,
     *             &quot;calcAdd&quot;,
     *             int.class,
     *             int.class,
     *             new Integer(1),
     *             new Integer(2)
     *         );
     *         assertEquals(3, result.intValue());
     *     }
     * }
     * </pre>
     * @param target �Ăяo���Ώۂ̃N���X
     * @param methodName �Ăяo���������\�b�h�̖��O
     * @param argType1 �������̌^
     * @param argType2 �������̌^
     * @param arg1 �������̒l�B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @param arg2 �������̒l�B
     * @return ���\�b�h�̖߂�l�B�Ăяo�����Ń_�E���L���X�g���K�v�B int, boolean���̊�{�f�[�^�^�́AInteger, Boolean���� ���b�p�[�N���X�Ɋi�[����Ēl���߂����B
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokePrivate(Class target, String methodName,
            Class argType1, Class argType2, Object arg1, Object arg2)
                                                                     throws Exception {

        return invokePrivate(target, methodName, new Class[] { argType1,
                argType2 }, new Object[] { arg1, arg2 });
    }

    /**
     * �w�肵���I�u�W�F�N�g��private�t�B�[���h�̒l��Ԃ��B
     * @param target �Ώۂ̃I�u�W�F�N�g
     * @param fieldName �l���擾����private�t�B�[���h�̖��O
     * @return private�t�B�[���h�̒l
     * @exception NoSuchFieldException
     */
    @SuppressWarnings("unchecked")
    public static Object getField(Object target, String fieldName)
                                                                  throws NoSuchFieldException {

        // �p�����[�^�l�̃`�F�b�N
        if (target == null) {
            throw new IllegalArgumentException();
        }
        if (fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException();
        }

        // private�t�B�[���h�擾�����B
        // �X�[�p�[�N���X�S�Ăɂ��ČĂяo�����g���C����B
        for (Class c = target.getClass(); c != null; c = c.getSuperclass()) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception e) {
                // �擾���悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ�A�������Ȃ��B
                // (�e�N���X�œ����g���C���J��Ԃ�)
            }
        }
        // �擾���悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ
        throw new NoSuchFieldException("Could get value for field "
                + target.getClass().getName() + "." + fieldName);
    }

    /**
     * �w�肵���N���X��static��private�t�B�[���h�̒l��Ԃ��B
     * @param target �Ώۂ̃N���X
     * @param fieldName �l���擾����private�t�B�[���h�̖��O
     * @return private�t�B�[���h�̒l
     * @exception NoSuchFieldException
     */
    @SuppressWarnings("unchecked")
    public static Object getField(Class target, String fieldName)
                                                                 throws NoSuchFieldException {

        // �p�����[�^�l�̃`�F�b�N
        if (target == null) {
            throw new IllegalArgumentException();
        }
        if (fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException();
        }

        // private�t�B�[���h�擾�����B
        // �X�[�p�[�N���X�S�Ăɂ��ČĂяo�����g���C����B
        Class c = target;
        while (c != null) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(c);
            } catch (Exception e) {
                // �擾���悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ�A�������Ȃ��B
                // (�e�N���X�œ����g���C���J��Ԃ�)
            }
            c = c.getSuperclass();
        }
        // �擾���悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ
        throw new NoSuchFieldException("Could get value for field "
                + target.getName() + "." + fieldName);
    }

    /**
     * �w�肵���I�u�W�F�N�g��private�t�B�[���h�ɒl��ݒ肷��B
     * @param target �Ώۂ̃I�u�W�F�N�g
     * @param fieldName �l��ݒ肷��private�t�B�[���h�̖��O
     * @param value �ݒ肷��l�B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @exception NoSuchFieldException
     */
    @SuppressWarnings("unchecked")
    public static void setField(Object target, String fieldName, Object value)
                                                                              throws NoSuchFieldException {

        // �p�����[�^�l�̃`�F�b�N
        if (target == null) {
            throw new IllegalArgumentException();
        }
        if (fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException();
        }

        // private�t�B�[���h�ݒ菈���B
        // �X�[�p�[�N���X�S�Ăɂ��ČĂяo�����g���C����B
        for (Class c = target.getClass(); c != null; c = c.getSuperclass()) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (Exception e) {
                // �ݒ肵�悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ�A�������Ȃ��B
                // (�e�N���X�œ����g���C���J��Ԃ�)
            }
        }
        // �ݒ肵�悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ
        throw new NoSuchFieldException("Could set value for field "
                + target.getClass().getName() + "." + fieldName);
    }

    /**
     * �w�肵���N���X��static��private�t�B�[���h�ɒl��ݒ肷��B
     * @param target �Ώۂ̃N���X
     * @param fieldName �l��ݒ肷��private�t�B�[���h�̖��O
     * @param value �ݒ肷��l�B int,boolean���̊�{�f�[�^�^�́AInteger, Boolean���̃��b�p�[�N���X�� �i�[���Ēl��n���K�v����B
     * @exception NoSuchFieldException
     */
    @SuppressWarnings("unchecked")
    public static void setField(Class target, String fieldName, Object value)
                                                                             throws NoSuchFieldException {

        // �p�����[�^�l�̃`�F�b�N
        if (target == null) {
            throw new IllegalArgumentException();
        }
        if (fieldName == null || fieldName.equals("")) {
            throw new IllegalArgumentException();
        }

        Class c = target;
        while (c != null) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(c, value);
                return;
            } catch (Exception ex) {
                // �ݒ肵�悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ�A�������Ȃ��B
                // (�e�N���X�œ����g���C���J��Ԃ�)
            }
            c = c.getSuperclass();
        }
        // �ݒ肵�悤�Ƃ����t�B�[���h�����݂��Ȃ������ꍇ
        throw new NoSuchFieldException("Could set value for static field "
                + target.getName() + "." + fieldName);
    }
}

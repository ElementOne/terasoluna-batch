/*
 * $Id: PrivateAccessUtil.java 5230 2007-09-28 10:04:13Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */
package jp.terasoluna.fw.file.ut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * �� private���\�b�h���Ăяo�����߂̃��[�e�B���e�B�N���X�B
 * @version 2003.08.29
 * @author �O�H ��
 */
class PrivateAccessUtil {

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
     *         Integer result = (Integer) PrivateAccessUtil.invokePrivate(
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

        // �p�����[�^�l�̃`�F�b�N
        if (target == null) {
            throw new IllegalArgumentException();
        }
        if (methodName == null || methodName.equals("")) {
            throw new IllegalArgumentException();
        }
        if (argTypes.length != args.length) {
            throw new IllegalArgumentException();
        }

        // private���\�b�h�Ăяo�������B
        // �X�[�p�[�N���X�S�Ăɂ��ČĂяo�����g���C����B
        Class c = target.getClass();
        while (c != null) {
            try {
                Method method = c.getDeclaredMethod(methodName, argTypes);
                method.setAccessible(true);
                return method.invoke(target, args);
            } catch (InvocationTargetException e) {
                // �Ăяo�������\�b�h����O�𓊂����ꍇ�B
                throw (Exception) e.getTargetException();
            } catch (Exception e) {
                // �Ăяo�����Ƃ������\�b�h�����݂��Ȃ������ꍇ�A�������Ȃ��B
                // (�e�N���X�œ����g���C���J��Ԃ��B)
            }
            c = c.getSuperclass();
        }
        // �Ăяo�����Ƃ������\�b�h�����݂��Ȃ������ꍇ�B
        throw new NoSuchMethodException("Could not invoke "
                + target.getClass().getName() + "." + methodName + "()");
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
     *            (String) PrivateAccessUtil.invokePrivate(sample, &quot;getString&quot;):
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
     *         Long result = (Long) PrivateAccessUtil.invokePrivate(
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
     *         Integer result = (Integer) PrivateAccessUtil.invokePrivate(
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
     *         Integer result = (Integer) PrivateAccessUtil.invokePrivate(
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

        // �p�����[�^�l�̃`�F�b�N
        if (target == null) {
            throw new IllegalArgumentException();
        }
        if (methodName == null || methodName.equals("")) {
            throw new IllegalArgumentException();
        }
        if (argTypes.length != args.length) {
            throw new IllegalArgumentException();
        }

        // private���\�b�h�Ăяo�������B
        // �X�[�p�[�N���X�S�Ăɂ��ČĂяo�����g���C����B
        Class c = target;
        while (c != null) {
            try {
                Method method = c.getDeclaredMethod(methodName, argTypes);
                method.setAccessible(true);
                return method.invoke(target, args);
            } catch (InvocationTargetException e) {
                // �Ăяo�������\�b�h����O�𓊂����ꍇ�B
                throw (Exception) e.getTargetException();
            } catch (Exception e) {
                // �Ăяo�����Ƃ������\�b�h�����݂��Ȃ������ꍇ�A�������Ȃ��B
                // (�e�N���X�œ����g���C���J��Ԃ��B)
            }
            c = c.getSuperclass();
        }
        // �Ăяo�����Ƃ������\�b�h�����݂��Ȃ������ꍇ�B
        throw new NoSuchMethodException("Could not invoke "
                + target.getClass().getName() + "." + methodName + "()");
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
     *         String result = (String) PrivateAccessUtil.invokePrivate(
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
     *         Long result = (Long) PrivateAccessUtil.invokePrivate(
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
     *         Integer result = (Integer) PrivateAccessUtil.invokePrivate(
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
}

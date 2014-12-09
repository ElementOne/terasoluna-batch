/*
 * $Id: VMOUTUtil.java 5230 2007-09-28 10:04:13Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */
package jp.terasoluna.fw.file.ut;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import jp.co.dgic.testing.common.virtualmock.InternalMockObjectManager;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;

/**
 * djUnit��Virtual Mock Object�@�\�𗘗p���邽�߂̃��[�e�B���e�B�N���X�B JUnit�̃e�X�g�P�[�X���痘�p����ꍇ�́AsetUp���\�b�h�ɂ����� {@link VMOUTUtil#initialize()}
 * ���\�b�h���Ăяo���K�v������B
 * @author �r�c�@�M�V
 * @see jp.co.dgic.testing.common.virtualmock.MockObjectManager
 */
public class VMOUTUtil {

    /**
     * VMO�̏��������s���B <code>JUnit</code>�̃e�X�g�P�[�X���痘�p����ꍇ�́A <code>setUp</code>���\�b�h�����ŕK�����̃��\�b�h���Ăяo���Ȃ���΂Ȃ�Ȃ��B
     */
    public static void initialize() {
        MockObjectManager.initialize();
    }

    /**
     * �w�肵�����\�b�h��<code>index</code>��ڂ̌Ăяo�����L�����Z�����A �w�肵���I�u�W�F�N�g��ԋp����B
     * @param cls �������������N���X�B
     * @param methodname ���������������\�b�h���B
     * @param index ����ڂ̌Ăяo�������������������B�I�t�Z�b�g�̓[���B
     * @param returnValue �ԋp�������I�u�W�F�N�g�B�v���~�e�B�u�^��Ԃ��ꍇ�̓��b�p�[�I�u�W�F�N�g���w�肷��B <code>null</code>���w��\�B
     */
    @SuppressWarnings("unchecked")
    public static void setReturnValueAt(Class cls, String methodname,
            int index, Object returnValue) {
        if (returnValue == null) {
            MockObjectManager.setReturnNullAt(cls.getName(), methodname, index);
        } else {
            MockObjectManager.setReturnValueAt(cls.getName(), methodname,
                    index, returnValue);
        }
    }

    /**
     * �w�肵�����\�b�h�̖���̌Ăяo�����L�����Z�����A�w�肵���I�u�W�F�N�g��ԋp����B
     * @param cls �������������N���X�B
     * @param methodname ���������������\�b�h���B
     * @param returnValue �ԋp�������I�u�W�F�N�g�B�v���~�e�B�u�^��Ԃ��ꍇ�̓��b�p�[�I�u�W�F�N�g���w�肷��B <code>null</code>���w��\�B
     */
    @SuppressWarnings("unchecked")
    public static void setReturnValueAtAllTimes(Class cls, String methodname,
            Object returnValue) {
        if (returnValue == null) {
            MockObjectManager
                    .setReturnNullAtAllTimes(cls.getName(), methodname);
        } else {
            MockObjectManager.setReturnValueAtAllTimes(cls.getName(),
                    methodname, returnValue);
        }
    }

    /**
     * �w�肵�����\�b�h��<code>index</code>��ڂ̌Ăяo�����L�����Z�����A�w�肵����O��ԋp����B
     * @param cls �������������N���X�B
     * @param methodname ���������������\�b�h���B
     * @param index ����ڂ̌Ăяo�������������������B�I�t�Z�b�g�̓[���B
     * @param exception �ԋp��������O�B
     */
    @SuppressWarnings("unchecked")
    public static void setExceptionAt(Class cls, String methodname, int index,
            Throwable exception) {
        MockObjectManager.setReturnValueAt(cls.getName(), methodname, index,
                exception);
    }

    /**
     * �w�肵�����\�b�h�̖���̌Ăяo�����L�����Z�����A�w�肵����O��ԋp����B
     * @param cls �������������N���X�B
     * @param methodname ���������������\�b�h���B
     * @param exception �ԋp��������O�B
     */
    @SuppressWarnings("unchecked")
    public static void setExceptionAtAllTimes(Class cls, String methodname,
            Throwable exception) {
        MockObjectManager.setReturnValueAtAllTimes(cls.getName(), methodname,
                exception);
    }

    /**
     * �w�肵�����\�b�h��<code>index</code>��ڂ̌Ăяo�����L�����Z������B <code>void</code>�^�̃��\�b�h�݂̂ŗ��p�\�B
     * @param cls �������������N���X�B
     * @param methodname ���������������\�b�h���B
     * @param index ����ڂ̌Ăяo�������������������B�I�t�Z�b�g�̓[���B
     */
    @SuppressWarnings("unchecked")
    public static void cancelMethodAt(Class cls, String methodname, int index) {
        MockObjectManager.setReturnValueAt(cls.getName(), methodname, index);
    }

    /**
     * �w�肵�����\�b�h�̖���̌Ăяo�����L�����Z������B<code>void</code>�^�̃��\�b�h�݂̂ŗ��p�\�B
     * @param cls �������������N���X�B
     * @param methodname ���������������\�b�h���B
     */
    @SuppressWarnings("unchecked")
    public static void cancelMethodAtAllTimes(Class cls, String methodname) {
        MockObjectManager.setReturnValueAtAllTimes(cls.getName(), methodname);
    }

    /**
     * �w�肵�����\�b�h���Ăяo���ꂽ���ǂ������m�F����B
     * @param cls �m�F�������N���X�B
     * @param methodname �m�F���������\�b�h���B
     * @return �Ăяo����Ă����true�B
     */
    @SuppressWarnings("unchecked")
    public static boolean isCalled(Class cls, String methodname) {
        return MockObjectManager.isCalled(cls.getName(), methodname);
    }

    /**
     * �w�肵�����\�b�h���Ăяo���ꂽ�񐔂��擾����B
     * @param cls �m�F�������N���X�B
     * @param methodname �m�F���������\�b�h���B
     * @return �Ăяo���ꂽ�񐔁B
     */
    @SuppressWarnings("unchecked")
    public static int getCallCount(Class cls, String methodname) {
        return MockObjectManager.getCallCount(cls.getName(), methodname);
    }

    /**
     * �w�肳�ꂽ���\�b�h��<code>methodindex</code>��ڂ̌Ăяo���ɂ����āA <code>argumentindex</code>�ڂ̈������Ƃ�l���擾����B
     * @param cls �m�F�������N���X�B
     * @param methodname �m�F���������\�b�h���B
     * @param methodindex ����ڂ̌Ăяo�����m�F���������B�I�t�Z�b�g�̓[���B
     * @param argumentindex ���ڂ̈������擾���������B�I�t�Z�b�g�̓[���B
     * @return �擾���������̒l�B
     */
    @SuppressWarnings("unchecked")
    public static Object getArgument(Class cls, String methodname,
            int methodindex, int argumentindex) {
        return MockObjectManager.getArgument(cls.getName(), methodname,
                methodindex, argumentindex);
    }

    /**
     * �w�肳�ꂽ���\�b�h��<code>methodindex</code>��ڂ̌Ăяo���̂��ׂĂ̈������擾����B
     * @param cls �m�F�������N���X�B
     * @param methodname �m�F���������\�b�h���B
     * @param methodindex ����ڂ̌Ăяo�����m�F���������B�I�t�Z�b�g�̓[���B
     * @return �擾���������̒l��List�B
     */
    @SuppressWarnings("unchecked")
    public static List getArguments(Class cls, String methodname,
            int methodindex) {
        ArrayList<Object> list = new ArrayList<Object>();
        int argumentindex = 0;
        while (true) {
            try {
                list.add(getArgument(cls, methodname, methodindex,
                        argumentindex++));
            } catch (ArrayIndexOutOfBoundsException ex) {
                break;
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static Hashtable getTestDataTable() throws NoSuchFieldException {
        return (Hashtable) UTUtil.getField(InternalMockObjectManager
                .getTestData(), "valueTable");
    }

    @SuppressWarnings("unchecked")
    public static Hashtable getAllTimesTestDataTable()
                                                      throws NoSuchFieldException {
        return (Hashtable) UTUtil.getField(InternalMockObjectManager
                .getTestData(), "valueAtAllTimesTable");
    }

    @SuppressWarnings("unchecked")
    public static Hashtable getArgumentValues() throws NoSuchFieldException {
        return (Hashtable) UTUtil.getField(InternalMockObjectManager
                .getCallsMade(), "argumentValues");
    }

}

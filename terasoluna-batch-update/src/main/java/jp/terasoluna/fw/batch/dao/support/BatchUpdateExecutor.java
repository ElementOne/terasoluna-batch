/*
 * Copyright (c) 2011 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.batch.dao.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.fw.logger.TLogger;

/**
 * �o�b�`�X�V�ꊇ���s�N���X<br>
 */
public class BatchUpdateExecutor {
    /**
     * ���K�[.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(BatchUpdateExecutor.class);

    /**
     * �o�b�`�X�V�ꊇ���s.<br>
     * <p>
     * �I�u�W�F�N�g����BatchUpdateSupport�t�B�[���h��T�����āA���ׂẴo�b�`�X�V�����s����B<br>
     * </p>
     * @param value Object �T������I�u�W�F�N�g
     * @param updateDAO UpdateDAO ���s�Ɏg�p����UpdateDAO
     * @return executeBatch�̎��s���ʃ��X�g
     */
    public static List<BatchUpdateResult> executeBatch(Object value,
            UpdateDAO updateDAO) {
        List<BatchUpdateResult> resultList = new ArrayList<BatchUpdateResult>();

        if (value != null) {
            if (value instanceof BatchUpdateSupport) {
                BatchUpdateSupport bus = (BatchUpdateSupport) value;
                // 1�����s
                int res = 0;
                try {
                    res = bus.executeBatch(updateDAO);
                    resultList.add(new BatchUpdateResult(bus, Integer
                            .valueOf(res)));
                } catch (Throwable e) {
                    // ����������O���i�[
                    resultList.add(new BatchUpdateResult(bus, e));
                }
            } else if (value instanceof List) {
                // ���X�g��T��
                List<?> valueList = (List<?>) value;

                for (Object obj : valueList) {
                    List<BatchUpdateResult> res = executeBatch(obj, updateDAO);
                    resultList.addAll(res);
                }
            } else if (value.getClass().isArray()) {
                // �z���T��
                Object[] valueArray = (Object[]) value;

                for (Object obj : valueArray) {
                    List<BatchUpdateResult> res = executeBatch(obj, updateDAO);
                    resultList.addAll(res);
                }
            } else {
                // �I�u�W�F�N�g��T��
                List<BatchUpdateResult> res = executeBatchInnerObject(value,
                        updateDAO);
                resultList.addAll(res);
            }
        }

        return resultList;
    }

    /**
     * �I�u�W�F�N�g�ɑ΂���o�b�`�X�V�ꊇ���s�i�����Ăяo���p�j.<br>
     * @param value Object �T������I�u�W�F�N�g
     * @param updateDAO UpdateDAO ���s�Ɏg�p����UpdateDAO
     * @return executeBatch�̎��s���ʃ��X�g
     */
    protected static List<BatchUpdateResult> executeBatchInnerObject(
            Object value, UpdateDAO updateDAO) {

        List<BatchUpdateResult> resultList = new ArrayList<BatchUpdateResult>();

        if (value != null) {
            BeanInfo bi = null;

            try {
                bi = Introspector.getBeanInfo(value.getClass());

                if (bi != null) {
                    PropertyDescriptor[] pds = bi.getPropertyDescriptors();

                    for (PropertyDescriptor pd : pds) {
                        if (pd != null) {
                            Class<?> pt = pd.getPropertyType();
                            Method rm = pd.getReadMethod();

                            if (rm != null && isTargetClass(pt)) {
                                try {
                                    Object readValue = rm.invoke(value);
                                    List<BatchUpdateResult> res = executeBatch(
                                            readValue, updateDAO);
                                    resultList.addAll(res);
                                } catch (IllegalArgumentException e) {
                                    outputExceptionLog(e);
                                } catch (IllegalAccessException e) {
                                    outputExceptionLog(e);
                                } catch (InvocationTargetException e) {
                                    outputExceptionLog(e);
                                }
                            }
                        }
                    }
                }
            } catch (IntrospectionException e) {
                outputExceptionLog(e);
            }

        }

        return resultList;
    }

    /**
     * �o�b�`�X�V���X�g�N���A.<br>
     * <p>
     * �I�u�W�F�N�g����BatchUpdateSupport�t�B�[���h��T�����āA���ׂẴo�b�`�X�V���N���A����B<br>
     * </p>
     * @param value Object �T������I�u�W�F�N�g
     */
    public static void clearAll(Object value) {
        if (value != null) {
            if (value instanceof BatchUpdateSupport) {
                // �N���A
                ((BatchUpdateSupport) value).clear();
            } else if (value instanceof List) {
                // ���X�g��T��
                List<?> valueList = (List<?>) value;

                for (Object obj : valueList) {
                    clearAll(obj);
                }
            } else if (value.getClass().isArray()) {
                // �z���T��
                Object[] valueArray = (Object[]) value;

                for (Object obj : valueArray) {
                    clearAll(obj);
                }
            } else {
                // �I�u�W�F�N�g��T��
                clearAllInnerObject(value);
            }
        }

        return;
    }

    /**
     * �I�u�W�F�N�g�ɑ΂���o�b�`�X�V���X�g�N���A�i�����Ăяo���p�j.<br>
     * @param value Object �T������I�u�W�F�N�g
     */
    protected static void clearAllInnerObject(Object value) {

        if (value != null) {
            BeanInfo bi = null;

            try {
                bi = Introspector.getBeanInfo(value.getClass());

                if (bi != null) {
                    PropertyDescriptor[] pds = bi.getPropertyDescriptors();

                    for (PropertyDescriptor pd : pds) {
                        if (pd != null) {
                            Class<?> pt = pd.getPropertyType();
                            Method rm = pd.getReadMethod();

                            if (rm != null && isTargetClass(pt)) {
                                try {
                                    Object readValue = rm.invoke(value);
                                    clearAll(readValue);
                                } catch (IllegalArgumentException e) {
                                    outputExceptionLog(e);
                                } catch (IllegalAccessException e) {
                                    outputExceptionLog(e);
                                } catch (InvocationTargetException e) {
                                    outputExceptionLog(e);
                                }
                            }
                        }
                    }
                }
            } catch (IntrospectionException e) {
                outputExceptionLog(e);
            }

        }

        return;
    }

    /**
     * �����ΏۃN���X�ł��邩���肷��B<br>
     * @param clazz �N���X�^
     * @return true:�����Ώ� / false:�����ΏۊO�N���X
     */
    protected static boolean isTargetClass(Class<?> clazz) {
        if (clazz != null && clazz != Object.class && clazz != Class.class
                && !(Date.class.isAssignableFrom(clazz))
                && !(clazz.isPrimitive()) && !(isPrimitiveWrapper(clazz))) {
            return true;
        }
        return false;
    }

    /**
     * �v���~�e�B�u�̃��b�p�[�N���X�𔻒肷��.<br>
     * @param pt Class&lt;?&gt;
     * @return true:�v���~�e�B�u�̃��b�p�[�N���X�ł��� / false:�v���~�e�B�u�̃��b�p�[�N���X�ł͂Ȃ�
     */
    protected static boolean isPrimitiveWrapper(Class<?> clazz) {
        if (clazz != null) {
            if (Number.class.isAssignableFrom(clazz)
                    && !AtomicInteger.class.isAssignableFrom(clazz)
                    && !AtomicLong.class.isAssignableFrom(clazz)
                    && !AtomicBoolean.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Boolean.class == clazz || Character.class == clazz
                    || String.class == clazz || Date.class == clazz
                    || Void.class == clazz) {
                return true;
            }
        }
        return false;
    }

    /**
     * ��O���O���o�͂���B
     * @param e Throwable
     */
    protected static void outputExceptionLog(Throwable e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(LogId.WAL036001, e, (e.getMessage() == null ? "" : e
                    .getMessage()));
        }
    }

}

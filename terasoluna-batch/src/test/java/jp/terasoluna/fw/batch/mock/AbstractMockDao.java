/*
 * Copyright (c) 2014 NTT DATA Corporation
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

package jp.terasoluna.fw.batch.mock;

import java.util.*;

/**
 * ���b�NRepository���ۃN���X�ł��B
 * 
 * <pre>
 * �{�N���X���p���������b�NRepository�N���X�ł�
 * 
 * �E���Ғl�̐ݒ�i�����O�j
 * �E���\�b�h���s���̈����擾�i������j
 * 
 * ���s���܂��B
 * 
 * �ݒ肵�����Ғl�͓����ŃL���[�ɒǉ�����A�ǉ��������ԂŃ��\�b�h���s���ɕԂ�l�Ƃ��Ď��o����܂��B
 * </pre>
 * 
 */
public class AbstractMockDao {
    /**
     * ���\�b�h���s���ʂ̊��Ғl��ێ�����L���[
     */
    protected final LinkedList<Object> results = new LinkedList<Object>();

    /**
     * ���\�b�h���s���̃p�����[�^��ێ����郊�X�g
     */
    protected final List<DaoParam> params = new ArrayList<DaoParam>();

    /**
     * ���Ғl���L���[������o���ĕԋp���܂��B
     * 
     * <pre>
     * �L���[����̏ꍇ��null��ԋp���܂��B
     * ���Ғl��{@link RuntimeException}�p����O�̏ꍇ�A���̗�O���X���[���܂��B
     * </pre>
     * 
     * @return ���Ғl
     */
    protected Object poll() {
        Object result = results.poll();

        if (result instanceof RuntimeException) {
            throw (RuntimeException) result;
        }

        return result;
    }

    /**
     * ���Ғl�����o����int�Ƃ��ĕԋp���܂��B
     * 
     * <pre>
     * ��肾�����I�u�W�F�N�g��Integer�o�Ȃ��ꍇ�A{@link ClassCastException}���X���[���܂��B
     * </pre>
     * 
     * @return ���Ғl(int)
     * @throws ClassCastException
     * @see {@link #poll()}
     */
    protected int pollInt() throws ClassCastException {
        Object result = poll();

        if (result != null && result instanceof Integer) {
            return (Integer) result;
        } else {
            throw new ClassCastException(
                    "Integer was expected but the actual was " + (result == null ? null : result.getClass().getName()));
        }
    }

    /**
     * ���Ғl�����o���Ĕz��Ƃ��ĕԋp���܂��B
     * 
     * <pre>
     * ���o�����I�u�W�F�N�g���z��łȂ��ꍇ�A{@link ClassCastException}���X���[���܂��B
     * </pre>
     * 
     * @return ���Ғl(�z��)
     * @throws ClassCastException
     * @see {@link #poll()}
     */
    protected Object[] pollArray() throws ClassCastException {
        Object result = poll();

        if (result == null || !result.getClass().isArray()) {
            throw new ClassCastException(
                    "Array was expected but the actual was " + (result == null ? null : result.getClass().getName()));
        }

        return (Object[]) result;
    }

    /**
     * ���Ғl�����o���ă��X�g�Ƃ��ĕԋp���܂��B
     * 
     * <pre>
     * ���o�����I�u�W�F�N�g�����X�g�łȂ��ꍇ�A{@link ClassCastException}���X���[���܂��B
     * </pre>
     * 
     * @return ���Ғl(���X�g)
     * @throws ClassCastException
     * @see {@link #poll()}
     */
    protected List<?> pollList() throws ClassCastException {
        Object result = poll();

        if (!(result instanceof List)) {
            throw new ClassCastException(
                    "java.util.List was expected but the actual was " + (result == null ? null : result.getClass().getName()));
        }

        return (List<?>) result;
    }

    /**
     * ���Ғl�����o���ă}�b�v�Ƃ��ĕԋp���܂��B
     * 
     * <pre>
     * ���o�����I�u�W�F�N�g���}�b�v�łȂ��ꍇ�A{@link ClassCastException}���X���[���܂��B
     * </pre>
     * 
     * @return ���Ғl(�}�b�v)
     * @throws ClassCastException
     * @see {@link #poll()}
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> pollMap() throws ClassCastException {
        Object result = poll();

        if (!(result instanceof Map)) {
            throw new ClassCastException(
                    "java.util.Map was expected but the actual was " + (result == null ? null : result.getClass().getName()));
        }

        return (Map<String, Object>) result;
    }

    /**
     * ���Ғl�����o���ă}�b�v�z��Ƃ��ĕԋp���܂��B
     * 
     * <pre>
     * ���o�����I�u�W�F�N�g���z��łȂ��ꍇ�A{@link ClassCastException}���X���[���܂��B
     * </pre>
     * 
     * @return ���Ғl(�}�b�v�z��)
     * @throws ClassCastException
     * @see {@link #pollArray()}
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object>[] pollMapArray() throws ClassCastException {
        return (Map<String, Object>[]) pollArray();
    }

    /**
     * ���Ғl�����o���ă}�b�v���X�g�Ƃ��ĕԋp���܂��B
     * 
     * <pre>
     * ���o�����I�u�W�F�N�g�����X�g�łȂ��ꍇ�A{@link ClassCastException}���X���[���܂��B
     * </pre>
     * 
     * @return ���Ғl(�}�b�v���X�g)
     * @throws ClassCastException
     * @see {@link #pollList()}
     */
    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> pollMapList() throws ClassCastException {
        return (List<Map<String, Object>>) pollList();
    }

    /**
     * �R�[���o�b�N�����o���Ď��s���܂��B
     * 
     * <pre>
     * ���o�����I�u�W�F�N�g���R�[���o�b�N�̏ꍇ�A���s���܂��B
     * ���҈ʒu��{@link RuntimeException}�p����O�̏ꍇ�A���̗�O���X���[���܂��B
     * </pre>
     * 
     * @param args
     * @throws ClassCastException
     * @see {@link #poll()}
     */
    protected void pollAndExecuteIfCallback(Object[] args)
            throws ClassCastException {
        Object first = results.peek();
        if (first instanceof Callback) {
            ((Callback) poll()).execute(args);
        } else if (first instanceof RuntimeException) {
            poll();
        }
    }

    /**
     * ���\�b�h���s���̃p�����[�^�����X�g�ɒǉ����܂��B
     * 
     * @param param �p�����[�^
     */
    protected void addParam(DaoParam param) {
        String methodName = null;

        StackTraceElement[] stacTraceElements = new Throwable().getStackTrace();
        if (stacTraceElements != null && stacTraceElements.length > 0) {
            // �Ăяo�������\�b�h�����擾
            methodName = stacTraceElements[1].getMethodName();
        }
        param.setMethodName(methodName);
        params.add(param);
    }

    /**
     * ���Ғl���L���[�ɒǉ����܂��B
     * 
     * @param result ���Ғl
     */
    public void addResult(Object result) {
        results.add(result);
    }

    /**
     * {@link java.util.Collection}�œn���ꂽ���Ғl��S�ăL���[�ɒǉ����܂��B
     * @param results
     * @since 2.1.0
     */
    public void addResults(Collection<?> results) {
        this.results.addAll(results);
    }

    /**
     * ���\�b�h���s���̃p�����[�^��ێ����郊�X�g��ԋp���܂��B
     * 
     * @return ���\�b�h���s���̃p�����[�^��ێ����郊�X�g
     */
    public List<DaoParam> getParams() {
        return params;
    }

    /**
     * �������������s���܂��B
     * 
     * <pre>
     * ���s���ʊ��Ғl�L���[����сA���s���p�����[�^���X�g����ɂ��܂��B
     * </pre>
     */
    public void clear() {
        results.clear();
        params.clear();
    }
}

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

package jp.terasoluna.fw.batch.util;

import java.util.concurrent.ConcurrentHashMap;

import jp.terasoluna.fw.batch.message.MessageAccessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���b�Z�[�W�Ǘ����[�e�B���e�B�B<br>
 * <br>
 * <p>
 * ���b�Z�[�W�̊Ǘ����s�������ꍇ�ɗ��p����B<br>
 * �{�N���X�̃N���X�ϐ��ɕۑ������MessageAccessor�̓X���b�h�O���[�v���ɃC���X�^���X���ێ������B
 * </p>
 */
public class MessageUtil {

    /** ���O. */
    private static Log log = LogFactory.getLog(MessageUtil.class);

    /** �X���b�h�O���[�v���Ƀ��b�Z�[�W�\�[�X�A�N�Z�T��ێ�����. */
    private static final ConcurrentHashMap<ThreadGroup, MessageAccessor> tgm = new ConcurrentHashMap<ThreadGroup, MessageAccessor>();

    /**
     * �R�[�h�ɉ��������b�Z�[�W��ԋp����<br>
     * @param errorCode �R�[�h
     * @return �R�[�h�ɉ��������b�Z�[�W��ԋp����
     */
    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * �R�[�h�ɉ��������b�Z�[�W��ԋp����<br>
     * @param errorCode �R�[�h
     * @return �R�[�h�ɉ��������b�Z�[�W��ԋp����
     */
    public static String getMessage(String code, Object[] args) {
        String mes = null;
        MessageAccessor ma = null;

        ThreadGroup tg = getThreadGroup();
        if (tg != null) {
            ma = tgm.get(tg);

            if (log.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Get MessageAccessor.");
                sb.append(getThreadMessage());
                log.trace(sb.toString());
            }
        }

        if (ma != null) {
            try {
                mes = ma.getMessage(code, args);
            } catch (Throwable e) {
                // �������Ȃ�
            }
        } else {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("MessageAccessor is not found.");
                log.debug(sb.toString());
            }
        }

        // ���b�Z�[�W��������Ȃ������ꍇ
        if (mes == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Message not found. CODE:[");
            sb.append(code);
            sb.append("]");
            return sb.toString();
        }

        return mes;
    }

    /**
     * ���b�Z�[�W�\�[�X�A�N�Z�T��ݒ肷��.<br>
     * <p>
     * �����Őݒ肷��MessageAccessor�̓X���b�h�O���[�v���ɕێ������B
     * </p>
     * @param messageAccessor MessageAccessor
     */
    public static void setMessageAccessor(MessageAccessor messageAccessor) {
        if (messageAccessor == null) {
            if (log.isWarnEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("MessageAccessor is null.");
                sb.append(getThreadMessage());
                log.warn(sb.toString());
            }
            return;
        }

        ThreadGroup tg = getThreadGroup();
        if (tg != null) {
            tgm.put(tg, messageAccessor);

            if (log.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Set MessageAccessor.");
                sb.append(getThreadMessage());
                log.trace(sb.toString());
            }
        }
    }

    /**
     * ���b�Z�[�W�\�[�X�A�N�Z�T���폜����.<br>
     */
    public static void removeMessageAccessor() {
        ThreadGroup tg = getThreadGroup();
        if (tg != null) {
            tgm.remove(tg);

            if (log.isTraceEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Remove MessageAccessor.");
                sb.append(getThreadMessage());
                log.trace(sb.toString());
            }
        }
    }

    /**
     * �X���b�h�O���[�v���擾����.
     * @return ThreadGroup
     */
    private static ThreadGroup getThreadGroup() {
        Thread ct = Thread.currentThread();
        if (ct != null && ct.getThreadGroup() != null) {
            return ct.getThreadGroup();
        }
        return null;
    }

    /**
     * �X���b�h�O���[�v�ƃX���b�h����Ԃ����\�b�h.<br>
     * �f�o�b�O�p���b�Z�[�W��Ԃ��B
     * @return String
     */
    private static String getThreadMessage() {
        StringBuilder sb = new StringBuilder();
        Thread ct = Thread.currentThread();

        if (ct != null && getThreadGroup() != null) {
            sb.append(" tg:[");
            sb.append(getThreadGroup().getName());
            sb.append("]");
        }
        if (ct != null) {
            sb.append(" t:[");
            sb.append(ct.getName());
            sb.append("]");
        }

        return sb.toString();
    }
}

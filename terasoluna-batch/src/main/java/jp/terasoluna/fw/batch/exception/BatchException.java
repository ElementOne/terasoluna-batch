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

package jp.terasoluna.fw.batch.exception;

import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.batch.util.MessageUtil;

/**
 * �o�b�`��O�B<br>
 * <br>
 * �o�b�`���s���ɔ���������O����ێ�����B
 */
public class BatchException extends RuntimeException {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 7677068837918514733L;

    /**
     * ���b�Z�[�WID
     */
    private String messageId = null;

    /**
     * ��O������̂��߂̃p�����[�^
     */
    private Object[] params = null;

    /**
     * BatchException�𐶐�����
     */
    public BatchException() {
        super();
    }

    /**
     * BatchException�𐶐�����
     * @param message
     */
    public BatchException(String message) {
        super(message);
    }

    /**
     * BatchException�𐶐�����
     * @param message
     * @param cause
     */
    public BatchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * BatchException�𐶐�����
     * @param cause
     */
    public BatchException(Throwable cause) {
        super(cause);
    }

    /**
     * BatchException�𐶐�����
     * @param messageId �G���[�R�[�h
     * @param message �G���[���b�Z�[�W
     */
    public BatchException(String messageId, String message) {
        super(message);

        this.messageId = messageId;
    }

    /**
     * BatchException�𐶐�����
     * @param messageId ���b�Z�[�WID
     * @param message �G���[���b�Z�[�W
     * @param cause �����ƂȂ�����O
     */
    public BatchException(String messageId, String message, Throwable cause) {
        super(message, cause);

        this.messageId = messageId;
    }

    /**
     * BatchException�𐶐�����
     * @param messageId ���b�Z�[�WID
     * @param message �G���[���b�Z�[�W
     * @param params ��O������̂��߂̃p�����[�^
     */
    public BatchException(String messageId, String message, Object... params) {
        super(message);

        this.messageId = messageId;
        this.params = params;
    }

    /**
     * BatchException�𐶐�����
     * @param messageId ���b�Z�[�WID
     * @param message �G���[���b�Z�[�W
     * @param cause �����ƂȂ�����O
     * @param params ��O������̂��߂̃p�����[�^
     */
    public BatchException(String messageId, String message, Throwable cause,
            Object... params) {
        super(message, cause);

        this.messageId = messageId;
        this.params = params;
    }

    /**
     * BatchException�̃t�@�N�g�����\�b�h
     * @param messageId ���b�Z�[�WID
     * @return �����̓��e�ō쐬���ꂽBatchException�C���X�^���X
     */
    public static BatchException createException(String messageId) {
        return new BatchException(messageId, MessageUtil.getMessage(messageId));
    }

    /**
     * BatchException�̃t�@�N�g�����\�b�h
     * @param messageId ���b�Z�[�WID
     * @param params ��O������̂��߂̃p�����[�^
     * @return �����̓��e�ō쐬���ꂽBatchException�C���X�^���X
     */
    public static BatchException createException(String messageId,
            Object... params) {
        return new BatchException(messageId, MessageUtil.getMessage(messageId,
                params), params);
    }

    /**
     * BatchException�̃t�@�N�g�����\�b�h
     * @param messageId ���b�Z�[�WID
     * @param cause �����ƂȂ�����O
     * @return �����̓��e�ō쐬���ꂽBatchException�C���X�^���X
     */
    public static BatchException createException(String messageId,
            Throwable cause) {
        return new BatchException(messageId, MessageUtil.getMessage(messageId),
                cause);
    }

    /**
     * BatchException�̃t�@�N�g�����\�b�h
     * @param messageId ���b�Z�[�WID
     * @param cause �����ƂȂ�����O
     * @param params ��O������̂��߂̃p�����[�^
     * @return �����̓��e�ō쐬���ꂽBatchException�C���X�^���X
     */
    public static BatchException createException(String messageId,
            Throwable cause, Object... params) {
        return new BatchException(messageId, MessageUtil.getMessage(messageId,
                params), cause, params);
    }

    /**
     * ���O�o�͗p������쐬
     * @return ���O�o�͗p������
     */
    public String getLogMessage() {

        StringBuilder logMsg = new StringBuilder();

        logMsg.append(BatchUtil.cat("[", messageId, "] ", getMessage()));

        if (params != null) {
            logMsg.append(" (\n");
            for (Object option : params) {
                logMsg.append(BatchUtil.cat("\t", option, "\n"));
            }
            logMsg.append(")");
        }

        return logMsg.toString();
    }

    /**
     * ���b�Z�[�WID���擾.
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }
}

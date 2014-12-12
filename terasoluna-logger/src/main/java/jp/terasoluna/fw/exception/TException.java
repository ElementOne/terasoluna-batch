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
package jp.terasoluna.fw.exception;

import java.util.Locale;

import jp.terasoluna.fw.logger.TLogger;

/**
 * �ėp��O�N���X
 * 
 * <p>
 * ���b�Z�[�W�̊Ǘ����@��{@link TLogger}�Ɠ��l�ł��B<br>
 * �������A�ݒ�t�@�C����<code>META-INF/terasoluna-logger.properties</code>�ł͂Ȃ��A <code>META-INF/terasoluna-exception.properties</code>�ɂȂ�܂��B
 * </p>
 */
@SuppressWarnings("serial")
public class TException extends Exception {
    /**
     * ���b�Z�[�WID
     */
    private final String messageId;
    /**
     * �u���p�����[�^
     */
    private final Object[] args;

    /**
     * �R���X�g���N�^
     * 
     * @param messageId ���b�Z�[�WID
     * @param args �u���p�����[�^
     */
    public TException(String messageId, Object... args) {
        super(getMessage(messageId, ExceptionConfig.getLocale(), args));
        this.messageId = messageId;
        this.args = args;
    }

    /**
     * �R���X�g���N�^
     * 
     * @param messageId ���b�Z�[�WId
     * @param cause �N����O
     * @param args �u���p�����[�^
     */
    public TException(String messageId, Throwable cause, Object... args) {
        super(getMessage(messageId, ExceptionConfig.getLocale(), args), cause);
        this.messageId = messageId;
        this.args = args;
    }

    /**
     * ���b�Z�[�W���擾���܂��B
     * 
     * @param messageId ���b�Z�[�WID
     * @param locale ���P�[��
     * @param args �u���p�����[�^
     * @return ���b�Z�[�W
     */
    protected static String getMessage(String messageId, Locale locale,
            Object... args) {
        return ExceptionConfig.MESSAGE_MANAGER.getMessage(true, messageId,
                locale, args);
    }

    /**
     * ���b�Z�[�WID���擾���܂�
     * 
     * @return ���b�Z�[�WID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * �u���p�����[�^���擾���܂��B
     * 
     * @return �u���p�����[�^
     */
    public Object[] getArgs() {
        return args;
    }
}

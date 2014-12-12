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

import jp.terasoluna.fw.message.MessageManager;

/**
 * �ėp��O�N���X�̐ݒ�N���X�B
 * 
 */
public class ExceptionConfig {
    /**
     * �ėp��O�N���X�̐ݒ�t�@�C���p�X
     */
    public static final String MESSAGE_CONFIG_FILE = "META-INF/terasoluna-exception.properties";

    /**
     * �ėp��O�N���X�̃��b�Z�[�W�Ǘ��N���X�B
     */
    protected static final MessageManager MESSAGE_MANAGER = new MessageManager(
            MESSAGE_CONFIG_FILE);

    /**
     * �ėp��O�N���X�̃��P�[���B
     */
    private static final ThreadLocal<Locale> locale = new ThreadLocal<Locale>();

    /**
     * �R���X�g���N�^�B
     */
    protected ExceptionConfig() {
    }

    /**
     * �ėp��O�N���X�̃��P�[����ݒ肵�܂��B
     * 
     * @param locale ���P�[��
     */
    public static void setLocale(Locale locale) {
        ExceptionConfig.locale.set(locale);
    }

    /**
     * �ėp��O�N���X�̃��P�[�����擾���܂��B
     * 
     * @return ���P�[��
     */
    public static Locale getLocale() {
        return ExceptionConfig.locale.get();
    }
}

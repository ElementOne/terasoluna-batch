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
package jp.terasoluna.fw.message;

import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.terasoluna.fw.message.execption.MessageRuntimeException;

/**
 * ���b�Z�[�W�Ǘ��N���X
 * 
 */
public class MessageManager {
    /**
     * ���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����X�g
     */
    protected final List<String> basenames = new CopyOnWriteArrayList<String>();
    /**
     * ���b�Z�[�WID�̃t�H�[�}�b�g
     */
    protected String messageIdFormat = "[%s] ";
    /**
     * ���b�Z�[�WID��������Ȃ������ꍇ�ɗ�O���X���[���邩�ۂ�
     */
    protected boolean throwIfResourceNotFound = false;
    /**
     * ���b�Z�[�W�t�H�[�}�b�^
     */
    protected final MessageFormatter messageFormatter;
    /**
     * ���b�Z�[�WID�̃t�H�[�}�b�g�̃L�[�̃f�t�H���g�l
     */
    protected static final String DEFAULT_MESSAGE_ID_FORMAT_KEY = "message.id.format";
    /**
     * ���b�Z�[�W�v���p�e�B�t�@�C���x�[�X�l�[���̃L�[�̃f�t�H���g�l
     */
    protected static final String DEFAULT_MESSAGE_BASE_NAME_KEY = "message.basename";
    /**
     * ���b�Z�[�WID��������Ȃ������ꍇ�ɗ�O���X���[���邩�ۂ��̃L�[�̃f�t�H���g�l
     */
    protected static final String DEFAULT_THROW_IF_RESOURCE_NOT_FOUND_KEY = "throw.if.resource.not.found";
    /**
     * ���b�Z�[�W�t�H�[�}�b�^��FQCN���w�肷��L�[�̃f�t�H���g�l
     */
    protected static final String DEFAULT_MESSAGE_FORMATTER_FQCN_KEY = "message.formatter.fqcn";

    /**
     * �N���X���[�_��ԋp���܂��B
     * 
     * <p>
     * �Ăяo���ꂽ�X���b�h�ɃR���e�L�X�g�E�N���X���[�_���ݒ肳��Ă���ꍇ�͂��̃R���e�L�X�g�E�N���X���[�_��ԋp���܂��B<br>
     * �����łȂ��ꍇ�͂��̃N���X�����[�h�����N���X���[�_��ԋp���܂��B
     * </p>
     * 
     * @return �N���X���[�_
     */
    protected static ClassLoader getClassLoader() {
        ClassLoader contextClassLoader = Thread.currentThread()
                .getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }
        ClassLoader thisClassLoader = MessageManager.class.getClassLoader();
        return thisClassLoader;
    }

    /**
     * �R���X�g���N�^
     * <p>
     * ���b�Z�[�WID�̃t�H�[�}�b�g�̃L�[�A���b�Z�[�W�v���p�e�B�t�@�C���x�[�X�l�[���̃L�[�A ���b�Z�[�WID��������Ȃ������ꍇ�ɗ�O���X���[���邩�ۂ��̃L�[�A���b�Z�[�W�t�H�[�}�b�^��FQCN���w�肷��L�[�̓f�t�H���g�l��ݒ肵�A {@link MessageManager#MessageManager(String, String, String, String, String)}
     * ���Ăяo���܂��B
     * </p>
     * 
     * @see MessageManager#MessageManager(String, String, String, String)
     * @param configFile �ݒ�t�@�C���p�X(�N���X���[�_����)
     */
    public MessageManager(String configFile) {
        this(configFile, DEFAULT_MESSAGE_ID_FORMAT_KEY,
                DEFAULT_MESSAGE_BASE_NAME_KEY,
                DEFAULT_THROW_IF_RESOURCE_NOT_FOUND_KEY,
                DEFAULT_MESSAGE_FORMATTER_FQCN_KEY);
    }

    /**
     * �R���X�g���N�^
     * 
     * �N���X���[�_����v���p�e�B�t�@�C�����擾���A�V�������b�Z�[�W�}�l�[�W���[���\�z���܂��B
     * 
     * <p>
     * �����Ŏ擾�����ݒ�t�@�C���p�X�ɊY������v���p�e�B�t�@�C�����擾���܂��B<br>
     * �v���p�e�B�t�@�C���擾�̍ۂ͈ȉ��̍��ڂ����킹�Đݒ肵�܂��B<br>
     * �i�ݒ�l���Ȃ��ꍇ�́A�f�t�H���g�l���g�p���܂��B�j<br>
     * �E���b�Z�[�WID�̃t�H�[�}�b�g�̃L�[<br>
     * �E���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[���̃L�[<br>
     * �E���b�Z�[�WID�s�����̗�O�X���[�L��<br>
     * �E���b�Z�[�W�t�H�[�}�b�^��FQCN<br>
     * 
     * �v���p�e�B�t�@�C�����瓾�����b�Z�[�WID�̃t�H�[�}�b�g�A��O�X���[�t���O�̓����o�ϐ��Ɋi�[���܂��B<br>
     * ���b�Z�[�W�x�[�X�l�[���̓N���X���[�_����擾�ł�����̂�S�Ēǉ����A������̃��X�g�̃����o�ϐ��Ɋi�[���܂��B
     * </p>
     * 
     * @param configFile �ݒ�t�@�C���p�X(�N���X���[�_����)
     * @param messageIdFormatKey ���b�Z�[�WID�̃t�H�[�}�b�g�̃L�[
     * @param messageBaseNameKey ���b�Z�[�W�v���p�e�B�t�@�C���x�[�X�l�[���̃L�[
     * @param throwIfResourceNotFoundKey ���b�Z�[�WID��������Ȃ������ꍇ�ɗ�O���X���[���邩�ۂ��̃L�[
     * @param messageFormatterFqcnKey ���b�Z�[�W�t�H�[�}�b�^��FQCN���w�肷��L�[
     */
    public MessageManager(String configFile, String messageIdFormatKey,
                          String messageBaseNameKey,
                          String throwIfResourceNotFoundKey,
                          String messageFormatterFqcnKey) {
        try {
            ClassLoader cl = getClassLoader();
            {
                String format = null;
                String throwIfNotFound = null;
                String messageFormatterFqcn = null;
                // messageIdFormat,throwIfResourceNotFound,messageFormatterFqcn�̓N���X���[�_�ŗD��x�̍�������
                InputStream strm = cl.getResourceAsStream(configFile);
                if (strm != null) {
                    Properties p = new Properties();
                    p.load(strm);
                    format = p.getProperty(messageIdFormatKey);
                    throwIfNotFound = p.getProperty(throwIfResourceNotFoundKey);
                    messageFormatterFqcn = p
                            .getProperty(messageFormatterFqcnKey);
                }
                if (format != null) {
                    messageIdFormat = format;
                }
                if (throwIfNotFound != null) {
                    throwIfResourceNotFound = Boolean
                            .parseBoolean(throwIfNotFound);
                }
                if (messageFormatterFqcn != null) {
                    try {
                        Class<?> clazz = Class.forName(messageFormatterFqcn,
                                true, cl);
                        messageFormatter = (MessageFormatter) clazz
                                .newInstance();
                    } catch (Exception e) {
                        StringBuilder sb = new StringBuilder(
                                "MessageFormatter[").append(
                                messageFormatterFqcn).append("] is not found.");
                        throw new MessageRuntimeException(sb.toString(), e);
                    }
                } else {
                    messageFormatter = new DefaultMessageFormatter();
                }
            }

            Enumeration<URL> urls = cl.getResources(configFile);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    Properties p = new Properties();
                    InputStream strm = url.openStream();
                    p.load(strm);
                    // messageBasename�̓N���X���[�_����ǂݍ��߂���̂͑S�Ēǉ�����
                    if (p.containsKey(messageBaseNameKey)) {
                        String[] basenameArray = p.getProperty(
                                messageBaseNameKey).split(",");
                        for (String s : basenameArray) {
                            String basename = s.trim();
                            if (!"".equals(basename)) {
                                basenames.add(basename);
                            }
                        }
                    }
                }
            }
        } catch (MessageRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new MessageRuntimeException(e);
        }
    }

    /**
     * ���b�Z�[�W�v���p�e�B�t�@�C�����̃x�[�X�l�[���ɑΉ��������\�[�X�o���h����ԋp���܂��B
     * 
     * �����̃��P�[����null�̏ꍇ�́A�f�t�H���g�̃��P�[�X���g�p���܂��B �����̃x�[�X�l�[���ɑΉ����郊�\�[�X�����݂��Ȃ��ꍇ�́A��O�X���[�t���O�����̏ꍇ�Ɍ���A�x�[�X�l�[���𕶎��o�͂��܂��B �x�[�X�l�[���̕����o�͂Ɠ�����{@link MessageRuntimeException}���X���[���܂��B ��O�X���[�t���O�����̏ꍇ�́Anull�l��ݒ肵�����\�[�X�o���h����ԋp���܂��B
     * 
     * @param basename ���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[��
     * @param locale ���P�[��
     * @return ���\�[�X�o���h��
     */
    protected ResourceBundle getResourceBundle(String basename, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        ResourceBundle bundle = null;
        try {
            bundle = ResourceBundle.getBundle(basename, locale);
        } catch (MissingResourceException e) {
            if (throwIfResourceNotFound) {
                StringBuilder sb = new StringBuilder("resource[").append(
                        basename).append("] is not found");
                throw new MessageRuntimeException(sb.toString(), e);
            }
        }
        return bundle;
    }

    /**
     * �v���p�e�B�t�@�C���ɑ��݂���A���b�Z�[�WID�ɑΉ��������b�Z�[�W��ԋp���܂��B
     * 
     * <dl>
     * <dt><code>bundle</code>��<code>false</code>�̏ꍇ</dt>
     * <dd>
     * null�l��ԋp���܂��B
     * 
     * <dt><code>key</code>��<code>false</code>�̏ꍇ</dt> null�l��ԋp���܂��B
     * 
     * </dl>
     * 
     * <p>
     * �����̃��\�[�X�o���h���ɑ΂��āA�����̃��b�Z�[�WID�ɑΉ����郁�b�Z�[�W���擾���܂��B ���b�Z�[�WID�ɑΉ����郁�b�Z�[�W�����݂��Ȃ��ꍇ�́A��O�X���[�t���O�����̏ꍇ�Ɍ���A�擾�ł��Ȃ��������b�Z�[�WID���𕶎��o�͂��܂��B ���b�Z�[�WID���̕����o�͂Ɠ�����{@link MessageRuntimeException}���X���[���܂��B
     * ��O�X���[�t���O�����̏ꍇ�́Anull�l��ԋp���܂��B
     * </p>
     * 
     * @param bundle ���\�[�X�o���h��
     * @param key ���b�Z�[�WID
     * @return ���b�Z�[�WID�ɂ��擾���郁�b�Z�[�W
     */
    protected String getStringOrNull(ResourceBundle bundle, String key) {
        if (bundle == null) {
            return null;
        }
        if (key == null) {
            if (throwIfResourceNotFound) {
                throw new MessageRuntimeException("key is null");
            }
            return null;
        }

        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * ���b�Z�[�W�p�^�[����ԋp���܂��B
     * 
     * @param messageId ���b�Z�[�WID
     * @param locale ���b�Z�[�W�̃��P�[��
     * @return ���b�Z�[�WID�ɑ΂��郁�b�Z�[�W�p�^�[��
     */
    protected String getMessagePattern(String messageId, Locale locale) {
        String message = null;
        for (String basename : basenames) {
            ResourceBundle bundle = getResourceBundle(basename, locale);
            message = getStringOrNull(bundle, messageId);
            if (message != null) {
                break;
            }
        }
        if (message == null && throwIfResourceNotFound) {
            StringBuilder sb = new StringBuilder("key[").append(messageId)
                    .append("] is not found");
            throw new MessageRuntimeException(sb.toString());
        }
        return message;
    }

    /**
     * ���b�Z�[�W��ԋp���܂��B
     * 
     * ���P�[����null�l��ݒ肵�A {@link MessageManager#getMessage(boolean, String, Locale, Object...)} ���Ăяo���܂��B ���P�[���ɂ̓f�t�H���g�l���g�p����܂��B
     * 
     * @param resource ���\�[�X�̗L��
     * @param messageIdOrPattern ���b�Z�[�WID�i���\�[�X�L�̏ꍇ�j / ���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     * @return ���b�Z�[�W
     * @throws MessageRuntimeException �s���ȃp�^�[�����w�肵���ꍇ
     */
    public String getMessage(boolean resource, String messageIdOrPattern,
            Object... args) throws MessageRuntimeException {
        return getMessage(resource, messageIdOrPattern, null, args);
    }

    /**
     * ���b�Z�[�W��ԋp���܂��B
     * 
     * <dl>
     * <dt><code>resource</code>��<code>true</code>�̏ꍇ</dt>
     * <dd>
     * ���b�Z�[�WID�ɑ΂��郁�b�Z�[�W�p�^�[�������b�Z�[�W�v���p�e�B�t�@�C���x�[�X�l�[�����X�g�Ή�����e�v���p�e�B�t�@�C������T�����܂��B<br>
     * �ŏ��Ɍ����������b�Z�[�W�p�^�[���ɒu���p�����[�^�𖄂ߍ��񂾃��b�Z�[�W��ԋp���܂��B�p�^�[���� {@link MessageFormat} �̌`���ɂ��Ă��������B�s���ȃp�^�[�����w�肵���ꍇ�A{@link MessageRuntimeException}���X���[���܂�<br>
     * �x�[�X�l�[���ɑΉ������v���p�e�B�t�@�C���͎w�肵�����P�[���Ŏ擾���܂��B���P�[����null��ݒ肵���ꍇ�� {@link Locale#getDefault()}���g�p����܂��B</dd>
     * 
     * <dt><code>resource</code>��<code>false</code>�̏ꍇ</dt>
     * <dd>
     * ���b�Z�[�W�p�^�[��(<code>messageIdOrPattern</code>)�ɒu���p�����[�^�𖄂ߍ��񂾃��b�Z�[�W��ԋp���܂��B�p�^�[���� {@link MessageFormat}�̌`���ɂ��Ă��������B�s���ȃp�^�[�����w�肵���ꍇ�A {@link MessageRuntimeException}���X���[���܂��B</dd>
     * </dl>
     * 
     * @param resource ���\�[�X�̗L��
     * @param messageIdOrPattern ���b�Z�[�WID�i���\�[�X�L�̏ꍇ�j / ���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param locale ���P�[��
     * @param args �u���p�����[�^
     * @return ���b�Z�[�W
     * @throws MessageRuntimeException �s���ȃp�^�[�����w�肵���ꍇ
     */
    public String getMessage(boolean resource, String messageIdOrPattern,
            Locale locale, Object... args) throws MessageRuntimeException {
        String pattern = null;
        StringBuilder message = new StringBuilder();

        if (resource) {
            pattern = getMessagePattern(messageIdOrPattern, locale);
            message.append(String.format(messageIdFormat, messageIdOrPattern));
        } else {
            pattern = messageIdOrPattern;
        }

        if (pattern != null) {
            try {
                String body = messageFormatter.format(pattern, args);
                message.append(body);
            } catch (IllegalArgumentException e) {
                StringBuilder sb = new StringBuilder(
                        "message pattern is illeagal. pattern=")
                        .append(pattern).append("]");
                if (resource) {
                    sb.append(" logId=");
                    sb.append(messageIdOrPattern);
                }
                throw new MessageRuntimeException(sb.toString(), e);
            }
        }
        return message.toString();
    }
}

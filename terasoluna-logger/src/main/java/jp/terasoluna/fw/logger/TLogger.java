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
package jp.terasoluna.fw.logger;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.terasoluna.fw.message.MessageManager;

/**
 * �ėp���K�[�N���X�B<br>
 * <p>
 * CommonsLogging�̃��K�[�����b�v�������K�[�ł��B<br>
 * ���O���b�Z�[�W���v���p�e�B�t�@�C���ŊǗ����A���O�o�̓��\�b�h�̈����Ƀ��OID��n�����ƂŁA�v���p�e�B�t�@�C�����̃��OID�ɑ΂��郁�b�Z�[�W���o�͂ł��܂��B
 * </p>
 * <h3>���K�[�擾</h3>
 * <p>
 * ���K�[�̎擾�͑��̃��K�[���C�u�����Ƃقړ����ł��B
 * </p>
 * 
 * <pre>
 * TLogger logger = TLogger.getLogger(XX.class);
 * �܂���
 * TLogger logger = TLogger.getLogger("�J�e�S����");
 * </pre>
 * 
 * <h3>���O�o��</h3>
 * <p>
 * ���̂悤�ȃ��O���b�Z�[�W�v���p�e�B�t�@�C��������ꍇ
 * </p>
 * 
 * <pre>
 * DEB001=debug message
 * ERR001=error message
 * </pre>
 * <p>
 * ���̃��b�Z�[�W���o�͂���ɂ�
 * </p>
 * 
 * <pre>
 * logger.debug(&quot;DEB001&quot;);
 * logger.error(&quot;ERR001&quot;);
 * </pre>
 * <p>
 * �̂悤�Ƀ��O���x���ɉ��������\�b�h�Ƀ��OID��n���Ď��s���܂��B �o�̓��b�Z�[�W��
 * </p>
 * 
 * <pre>
 * [DEB001] debug message
 * [ERR001] error message
 * </pre>
 * <p>
 * �ƂȂ�܂��B���b�Z�[�W�̑O��[���OID]�������ŕt���܂��B<br>
 * 
 * ���O���x����
 * <ul>
 * <li>FATAL</li>
 * <li>ERROR</li>
 * <li>WARN</li>
 * <li>INFO</li>
 * <li>DEBUG</li>
 * <li>TRACE</li>
 * </ul>
 * ������܂��B<code>log(String logId)</code>���\�b�h���g�p����ƁA���OID�̈ꕶ���ڂ����ă��O���x���𔻒f���܂��B
 * </p>
 * <h3>�p�����[�^�u��</h3>
 * <p>
 * �o�͂��郍�O���b�Z�[�W���쐬����ۂɁA{@link java.text.MessageFormat}
 * ���g�p���Ă��܂��B�u���p�����[�^���ϒ��z��œn�����Ƃ��ł��܂��B
 * </p>
 * 
 * <pre>
 * DEB002={0} is {1}.
 * </pre>
 * <p>
 * �Ƃ�����`������ꍇ�A
 * </p>
 * 
 * <pre>
 * logger.debug(&quot;DEB002&quot;, &quot;hoge&quot;, &quot;foo&quot;);
 * </pre>
 * <p>
 * �����s����Əo�̓��b�Z�[�W��
 * </p>
 * 
 * <pre>
 * [DEB002] hoge is foo.
 * </pre>
 * <p>
 * �ƂȂ�܂��B <br>
 * �����Ń��b�Z�[�W��������쐬����ۂɃ��O���x���̃`�F�b�N���s���Ă���̂ŁA
 * </p>
 * 
 * <pre>
 * if (logger.isDebugEnabled()) {
 *     logger.debug(&quot;DEB002&quot;, &quot;hoge&quot;, &quot;foo&quot;);
 * }
 * </pre>
 * <p>
 * �Ƃ���if���������K�v������܂���B(�������A
 * �p�����[�^���쐬����ۂɃ��\�b�h���Ăяo���Ă���ꍇ��if���������Ė����I�Ƀ��O���x���`�F�b�N���s���Ă��������B)
 * </p>
 * <h3>���b�Z�[�W�v���p�e�B�t�@�C���ɂȂ����b�Z�[�W�̏o��</h3>
 * <p>
 * ���b�Z�[�W�v���p�e�B�t�@�C�����̃��OID��n�����ɁA���ڃ��b�Z�[�W��n�����@������܂��B��1������false
 * ��ݒ肵�A��2�����Ƀ��b�Z�[�W�{���𒼐ڋL�q�ł��܂��B��3�����ȍ~�͒u���p�����[�^�ł��B���̏ꍇ�͓��R���OID�͏o�͂���܂���B
 * </p>
 * 
 * <pre>
 * logger.warn(false, &quot;warn!!&quot;);
 * logger.info(false, &quot;Hello {0}!&quot;, &quot;World&quot;);
 * </pre>
 * <p>
 * �o�̓��b�Z�[�W��
 * </p>
 * 
 * <pre>
 * warn!!
 * Hello World!
 * </pre>
 * <p>
 * �ƂȂ�܂��B
 * </p>
 * <h3>�ݒ�t�@�C��</h3>
 * <p>
 * �N���X�p�X������<code>META-INF</code>�f�B���N�g����<code>terasoluna-logger.properties</code>
 * ���쐬���Ă��������B
 * </p>
 * <h4>���b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[���ݒ�</h4>
 * <p>
 * <code>terasoluna-logger.properties</code>��<code>message.basename</code>
 * �L�[�Ƀ��b�Z�[�W�v���p�e�B�t�@�C���̃x�[�X�l�[�����N���X�p�X����(FQCN)�Őݒ肵�Ă��������B<br>
 * {@link java.util.ResourceBundle}�œǂݍ��ނ̂ŁA���ۉ��ɑΉ����Ă��܂��B
 * </p>
 * 
 * <pre>
 * message.basename = hoge
 * </pre>
 * <p>
 * �Ə����ƃN���X�p�X������hoge.properties���ǂݍ��܂�܂��B
 * </p>
 * 
 * <pre>
 * message.basename=hoge,foo,bar
 * </pre>
 * <p>
 * �̂悤�ɔ��p�J���}��؂�Őݒ肷��ƑS�Ă�ǂݍ��݂܂��B<br>
 * <code>META-INF/terasoluna-logger.properies</code>��
 * <code>message.basename</code>�̓��W���[�����ɐݒ�ł��܂��B ���K�[�͑S�Ẵ��W���[��(jar)�����A
 * <code>message.basename</code>�̒l���}�[�W���ă��b�Z�[�W���擾���܂��B <br>
 * ����ɂ��A���W���[�����Ƀ��O���b�Z�[�W���Ǘ����邱�Ƃ��ł��܂��B
 * </p>
 * <h4>�o�̓��OID�t�H�[�}�b�g�ݒ�</h4>
 * <p>
 * ���O�o�͎��Ɏ����ŕt������郍�OID�̃t�H�[�}�b�g��ݒ�ł��܂��B<br>
 * <code>message.id.format</code>�L�[��
 * {@link java.lang.String#format(String, Object...)}
 * �̃t�H�[�}�b�g�`���Őݒ肵�Ă��������B���OID��������Ƃ��ēn����܂��B <br>
 * �ݒ肵�Ȃ��ꍇ�́u[%s]�v���f�t�H���g�l�Ƃ��Ďg�p����܂��B <br>
 * </p>
 * 
 * <pre>
 * message.id.format=[%-8s]
 * </pre>
 * <p>
 * �̂悤�ɐݒ肷��ƁA���W���[���ԂňقȂ钷���̃��OID�����񂹂ő����ďo�͂ł��܂��B <br>
 * ���̐ݒ�l�̓��W���[�����ɊǗ����邱�Ƃ͂ł��܂���B <br>
 * �N���X���[�_�̓ǂݍ��ݗD��x����ԍ���<code>terasoluna-logger.properties</code>�̒l�����f����܂��B
 * (�ʏ�A�A�v�����̐ݒ�ƂȂ�܂��B)
 * </p>
 */
public class TLogger implements Log {
    /**
     * ���K�[���́B
     */
    private final Log logger;

    /**
     * ���K�[�ݒ�t�@�C���B
     */
    private static final String CONFIG_FILENAME = "META-INF/terasoluna-logger.properties";
    /**
     * ���b�Z�[�W�Ǘ��B
     */
    private static final MessageManager MESSAGE_MANAGER = new MessageManager(
            CONFIG_FILENAME);

    /**
     * ���P�[����ێ�����X���b�h���[�J���B
     */
    private static final ThreadLocal<Locale> locale = new ThreadLocal<Locale>();

    /**
     * �R���X�g���N�^�B
     * 
     * @param clazz �J�e�S�����ƂȂ�N���X
     */
    protected TLogger(Class<?> clazz) {
        logger = LogFactory.getLog(clazz);
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name �J�e�S����
     */
    protected TLogger(String name) {
        logger = LogFactory.getLog(name);
    }

    /**
     * ���P�[����ݒ肵�܂��B
     * 
     * @param locale ���P�[��
     */
    public static void setLocale(Locale locale) {
        TLogger.locale.set(locale);
    }

    /**
     * ���O���b�Z�[�W���쐬���܂��B
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     * @return ���O���b�Z�[�W������
     * @see {@link MessageManager#getMessage(boolean, String, Locale, Object...)}
     */
    protected String createMessage(boolean resource, String logIdOrPattern,
            Object... args) {
        String message = MESSAGE_MANAGER.getMessage(resource, logIdOrPattern,
                locale.get(), args);
        return message;
    }

    /**
     * ���K�[���擾���܂��B
     * 
     * @param clazz �J�e�S�����ƂȂ�N���X
     * @return ���K�[
     */
    public static TLogger getLogger(Class<?> clazz) {
        return new TLogger(clazz);
    }

    /**
     * ���K�[���擾���܂��B
     * 
     * @param name �J�e�S����
     * @return ���K�[
     */
    public static TLogger getLogger(String name) {
        return new TLogger(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.logging.Log#isFatalEnabled()
     */
    public boolean isFatalEnabled() {
        return logger.isFatalEnabled();
    }

    /**
     * FATAL���O���o�͂��܂��B
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     */
    public void fatal(boolean resource, String logIdOrPattern, Object... args) {
        if (isFatalEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.fatal(message);
        }
    }

    /**
     * FATAL���O���o�͂��܂��B
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void fatal(boolean resource, String logIdOrPattern,
            Throwable throwable, Object... args) {
        if (isFatalEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.fatal(message, throwable);
        }
    }

    /**
     * FATAL���O���o�͂��܂��B
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     */
    public void fatal(String logId, Object... args) {
        fatal(true, logId, args);
    }

    /**
     * FATAL���O���o�͂��܂��B
     * 
     * @param logId ���OID
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void fatal(String logId, Throwable throwable, Object... args) {
        fatal(true, logId, throwable, args);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.logging.Log#isErrorEnabled()
     */
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����ERROR��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     */
    public void error(boolean resource, String logIdOrPattern, Object... args) {
        if (isErrorEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.error(message);
        }
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����ERROR��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void error(boolean resource, String logIdOrPattern,
            Throwable throwable, Object... args) {
        if (isErrorEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.error(message, throwable);
        }
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����ERROR��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     */
    public void error(String logId, Object... args) {
        error(true, logId, args);
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����ERROR��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void error(String logId, Throwable throwable, Object... args) {
        error(true, logId, throwable, args);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.logging.Log#isWarnEnabled()
     */
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    /**
     * WARN���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����WARN��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     */
    public void warn(boolean resource, String logIdOrPattern, Object... args) {
        if (isWarnEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.warn(message);
        }
    }

    /**
     * WARN���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����WARN��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void warn(boolean resource, String logIdOrPattern,
            Throwable throwable, Object... args) {
        if (isWarnEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.warn(message, throwable);
        }
    }

    /**
     * WARN���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����WARN��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId �@���OID
     * @param args �u���p�����[�^
     */
    public void warn(String logId, Object... args) {
        warn(true, logId, args);
    }

    /**
     * WARN���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����WARN��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void warn(String logId, Throwable throwable, Object... args) {
        warn(true, logId, throwable, args);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.logging.Log#isInfoEnabled()
     */
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����INFO��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     */
    public void info(boolean resource, String logIdOrPattern, Object... args) {
        if (isInfoEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.info(message);
        }
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����INFO��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void info(boolean resource, String logIdOrPattern,
            Throwable throwable, Object... args) {
        if (isInfoEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.info(message, throwable);
        }
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����INFO��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     */
    public void info(String logId, Object... args) {
        info(true, logId, args);
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����INFO��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void info(String logId, Throwable throwable, Object... args) {
        info(true, logId, throwable, args);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.logging.Log#isDebugEnabled()
     */
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����DEBUG��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     */
    public void debug(boolean resource, String logIdOrPattern, Object... args) {
        if (isDebugEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.debug(message);
        }
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����DEBUG��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void debug(boolean resource, String logIdOrPattern,
            Throwable throwable, Object... args) {
        if (isDebugEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.debug(message, throwable);
        }
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����DEBUG��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     */
    public void debug(String logId, Object... args) {
        debug(true, logId, args);
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����DEBUG��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void debug(String logId, Throwable throwable, Object... args) {
        debug(true, logId, throwable, args);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.logging.Log#isTraceEnabled()
     */
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����TRACE��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param args �u���p�����[�^
     */
    public void trace(boolean resource, String logIdOrPattern, Object... args) {
        if (isTraceEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.trace(message);
        }
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����TRACE��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param resource ���\�[�X�L��
     * @param logIdOrPattern ���OID�i���\�[�X�L�̏ꍇ�j / ���O���b�Z�[�W�p�^�[��(���\�[�X���̏ꍇ)
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void trace(boolean resource, String logIdOrPattern,
            Throwable throwable, Object... args) {
        if (isTraceEnabled()) {
            String message = createMessage(resource, logIdOrPattern, args);
            logger.trace(message, throwable);
        }
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����TRACE��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     */
    public void trace(String logId, Object... args) {
        trace(true, logId, args);
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����TRACE��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void trace(String logId, Throwable throwable, Object... args) {
        trace(true, logId, throwable, args);
    }

    /**
     * ���OID�̈ȉ��̋K��ɍ��������x���̃��O���o�͂��܂��B<br>
     * <p>
     * ���OID�̐擪������
     * <ul>
     * <li>T...TRACE���O</li>
     * <li>D...DEBUG���O</li>
     * <li>I...INFO���O</li>
     * <li>W...WARN���O</li>
     * <li>E...ERROR���O</li>
     * <li>F...FATAL���O</li>
     * <li>����ȊO...DEBUG���O</li> </u>
     * </p>
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     */
    public void log(String logId, Object... args) {
        if (logId != null && logId.length() > 0) {
            char messageType = logId.charAt(0);
            switch (messageType) {
            case 'T':
                trace(logId, args);
                break;
            case 'D':
                debug(logId, args);
                break;
            case 'I':
                info(logId, args);
                break;
            case 'W':
                warn(logId, args);
                break;
            case 'E':
                error(logId, args);
                break;
            case 'F':
                fatal(logId, args);
                break;
            default:
                debug(logId, args);
                break;
            }
        }
    }

    /**
     * ���OID�̈ȉ��̋K��ɍ��������x���̃��O���o�͂��܂��B<br>
     * <p>
     * ���OID�̐擪������
     * <ul>
     * <li>T...TRACE���O</li>
     * <li>D...DEBUG���O</li>
     * <li>I...INFO���O</li>
     * <li>W...WARN���O</li>
     * <li>E...ERROR���O</li>
     * <li>F...FATAL���O</li>
     * <li>����ȊO...DEBUG���O</li> </u>
     * </p>
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     */
    public void log(String logId, Throwable throwable, Object... args) {
        if (logId != null && logId.length() > 0) {
            char messageType = logId.charAt(0);
            switch (messageType) {
            case 'T':
                trace(logId, throwable, args);
                break;
            case 'D':
                debug(logId, throwable, args);
                break;
            case 'I':
                info(logId, throwable, args);
                break;
            case 'W':
                warn(logId, throwable, args);
                break;
            case 'E':
                error(logId, throwable, args);
                break;
            case 'F':
                fatal(logId, throwable, args);
                break;
            default:
                debug(logId, throwable, args);
                break;
            }
        }
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #trace(String, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * 
     */
    @Deprecated
    public void trace(Object message) {
        trace(false, "{0}", message);
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #trace(String, Throwable, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * @param t �N����O
     * 
     */
    @Deprecated
    public void trace(Object message, Throwable t) {
        trace(false, "{0}", t, message);
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #debug(String, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * 
     */
    @Deprecated
    public void debug(Object message) {
        debug(false, "{0}", message);
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #debug(String, Throwable, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * @param t �N����O
     * 
     */
    @Deprecated
    public void debug(Object message, Throwable t) {
        debug(false, "{0}", t, message);
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #info(String, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * 
     */
    @Deprecated
    public void info(Object message) {
        info(false, "{0}", message);
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #info(String, Throwable, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * @param t �N����O
     * 
     */
    @Deprecated
    public void info(Object message, Throwable t) {
        info(false, "{0}", t, message);
    }

    /**
     * WARN���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #warn(String, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * 
     */
    @Deprecated
    public void warn(Object message) {
        warn(false, "{0}", message);
    }

    /**
     * WARN���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #warn(String, Throwable, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * @param t �N����O
     * 
     */
    @Deprecated
    public void warn(Object message, Throwable t) {
        warn(false, "{0}", t, message);
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #error(String, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * 
     */
    @Deprecated
    public void error(Object message) {
        error(false, "{0}", message);
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #error(String, Throwable, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * @param t �N����O
     * 
     */
    @Deprecated
    public void error(Object message, Throwable t) {
        error(false, "{0}", t, message);
    }

    /**
     * FATAL���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #fatal(String, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * 
     */
    @Deprecated
    public void fatal(Object message) {
        fatal(false, "{0}", message);
    }

    /**
     * FATAL���O���o�͂��܂��B<br>
     * <p>
     * CommonsLogging��Log�C���^�t�F�[�X���������邽�߂�API�ł���A�g�p���Ȃ��ł��������B<br>
     * �����{@link #fatal(String, Throwable, Object...)}���g�p���Ă��������B
     * </p>
     * 
     * @param message ���b�Z�[�W
     * @param t �N����O
     * 
     */
    @Deprecated
    public void fatal(Object message, Throwable t) {
        fatal(false, "{0}", t, message);
    }

    /**
     * ���O���b�Z�[�W���擾���܂��B
     * 
     * @param logId ���OID
     * @param args �u���p�����[�^
     * @return ���O���b�Z�[�W
     */
    public String getLogMessage(String logId, Object... args) {
        String message = createMessage(true, logId, args);
        return message;
    }

    // CommonsLogging��Log�C���^�t�F�[�X�ŗ��p���邽�߂�API��p�ӂ������߂̑Ή�

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����TRACE��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     */
    public void trace(String logId) {
        trace(logId, (Object[]) null);
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����TRACE��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     */
    public void trace(String logId, Throwable throwable) {
        trace(logId, throwable, (Object[]) null);
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����DEBUG��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     */
    public void debug(String logId) {
        debug(logId, (Object[]) null);
    }

    /**
     * DEBUG���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����DEBUG��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     */
    public void debug(String logId, Throwable throwable) {
        debug(logId, throwable, (Object[]) null);
    }

    /**
     * WARN���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����WARN��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     */
    public void warn(String logId) {
        warn(logId, (Object[]) null);
    }

    /**
     * TRACE���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����TRACE��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     */
    public void warn(String logId, Throwable throwable) {
        warn(logId, throwable, (Object[]) null);
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����INFO��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     */
    public void info(String logId) {
        info(logId, (Object[]) null);
    }

    /**
     * INFO���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����INFO��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     */
    public void info(String logId, Throwable throwable) {
        info(logId, throwable, (Object[]) null);
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����ERROR��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     */
    public void error(String logId) {
        error(logId, (Object[]) null);
    }

    /**
     * ERROR���O���o�͂��܂��B<br>
     * <p>
     * ���K�[�̃��O���x����ERROR��荂���ꍇ�͏o�͂���܂���B<br>
     * �ڍׂ�{@link TLogger}���Q�Ƃ��Ă��������B
     * </p>
     * 
     * @param logId ���OID
     * @param throwable �N����O
     */
    public void error(String logId, Throwable throwable) {
        error(logId, throwable, (Object[]) null);
    }

    /**
     * FATAL���O���o�͂��܂��B
     * 
     * @param logId ���OID
     * @param throwable �N����O
     * @param args �u���p�����[�^
     */
    public void fatal(String logId) {
        fatal(logId, (Object[]) null);
    }

    /**
     * FATAL���O���o�͂��܂��B
     * 
     * @param logId ���OID
     * @param throwable �N����O
     */
    public void fatal(String logId, Throwable throwable) {
        fatal(logId, throwable, (Object[]) null);
    }
}

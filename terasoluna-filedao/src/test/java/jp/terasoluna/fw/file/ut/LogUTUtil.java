/*
 * $Id: LogUTUtil.java 5230 2007-09-28 10:04:13Z anh $
 *
 * Copyright (c) 2005 NTT DATA Corporation
 */
package jp.terasoluna.fw.file.ut;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

/**
 * �����p�̃��[�e�B���e�B�N���X�B
 * <p>
 * <code>Log</code> �C���^�[�t�F�[�X�����N���X�ł��� <code>LogFactory</code> �N���X�ł�����B�B
 * </p>
 * @author �E�c �N��
 * @version 1.1 2005/03/01
 */
public class LogUTUtil extends LogFactoryImpl implements Log {

    // ------------------------------------------------------- Test Properties

    /**
     * ���O�̏o�͌��ʂ��i�[���� <code>List
     * </code> �I�u�W�F�N�g�B
     */
    private final static List<LogObject> __logObjects = Collections
            .synchronizedList(new ArrayList<LogObject>());

    // ----------------------------------------------------------- Test Method

    /**
     * �~�ς��ꂽ���O�I�u�W�F�N�g����������B
     */
    public static void initialize() {
        __logObjects.clear();
    }

    /**
     * �o�͂��ꂽ���O�̃`�F�b�N���s���B
     * <p>
     * <code>fatal, error, warn, info, debug, trace</code> �̏��Ƀ��O�̏o�͊m�F���s���B
     * </p>
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean check(Object message) {
        return check(message, null);
    }

    /**
     * �o�͂��ꂽ���O�̃`�F�b�N���s���B
     * <p>
     * <code>fatal, error, warn, info, debug, trace</code> �̏��Ƀ��O�̏o�͊m�F���s���B
     * </p>
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @return �w�肳�ꂽ���b�Z�[�W�Ɨ�O�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean check(Object message, Throwable t) {
        return checkFatal(message, t) || checkError(message, t)
                || checkWarn(message, t) || checkInfo(message, t)
                || checkDebug(message, t) || checkTrace(message, t);
    }

    /**
     * <code>debug</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkDebug(Object message) {
        return checkDebug(message, null);
    }

    /**
     * <code>debug</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @return �w�肳�ꂽ���b�Z�[�W�Ɨ�O�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkDebug(Object message, Throwable t) {
        return searchLogObject(message, t, LogObject.DEBUG);
    }

    /**
     * <code>error</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkError(Object message) {
        return checkError(message, null);
    }

    /**
     * <code>error</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @return �w�肳�ꂽ���b�Z�[�W�Ɨ�O�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkError(Object message, Throwable t) {
        return searchLogObject(message, t, LogObject.ERROR);
    }

    /**
     * <code>fatal</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkFatal(Object message) {
        return checkFatal(message, null);
    }

    /**
     * <code>fatal</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @return �w�肳�ꂽ���b�Z�[�W�Ɨ�O�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkFatal(Object message, Throwable t) {
        return searchLogObject(message, t, LogObject.FATAL);
    }

    /**
     * <code>info</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkInfo(Object message) {
        return checkInfo(message, null);
    }

    /**
     * <code>info</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @return �w�肳�ꂽ���b�Z�[�W�Ɨ�O�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkInfo(Object message, Throwable t) {
        return searchLogObject(message, t, LogObject.INFO);
    }

    /**
     * <code>trace</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkTrace(Object message) {
        return checkTrace(message, null);
    }

    /**
     * <code>trace</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @return �w�肳�ꂽ���b�Z�[�W�Ɨ�O�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkTrace(Object message, Throwable t) {
        return searchLogObject(message, t, LogObject.TRACE);
    }

    /**
     * <code>warn</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkWarn(Object message) {
        return checkWarn(message, null);
    }

    /**
     * <code>warn</code> ���x���ŏo�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @return �w�肳�ꂽ���b�Z�[�W�Ɨ�O�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    public static boolean checkWarn(Object message, Throwable t) {
        return searchLogObject(message, t, LogObject.WARN);
    }

    /**
     * ���̃��\�b�h���Ăяo�����X���b�h���A�ǂ̃e�X�g�P�[�X���\�b�h���甭���������̂��𒲂ׂ�B
     * @return ���̃��\�b�h���Ăяo�����X���b�h�̋N�����\�b�h�B
     */
    @SuppressWarnings("unchecked")
    protected static Method getCalledTestCaseMethod() {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        for (int index = 0; index < elements.length; index++) {

            StackTraceElement stackTraceElement = elements[index];
            String elementClassName = stackTraceElement.getClassName();
            String elementMethodName = stackTraceElement.getMethodName();

            Class elementClass = null;
            try {
                elementClass = Class.forName(elementClassName);
            } catch (ClassNotFoundException e) {
                // do nothing.
            }

            Class targetClass = elementClass;
            while (targetClass != null) {
                if (TestCase.class.getName().equals(targetClass.getName())) {

                    if (elementMethodName.startsWith("test")) {
                        try {
                            return elementClass.getDeclaredMethod(
                                    elementMethodName, (Class[]) null);
                        } catch (SecurityException e) {
                            // do nothing.
                        } catch (NoSuchMethodException e) {
                            // do nothing.
                        }
                    }
                }

                targetClass = targetClass.getSuperclass();
            }
        }

        return null;
    }

    /**
     * ���O�̃L���[��ۑ����A�W���o�͂Ƀ��O���o�͂���B
     * @param message ���O���b�Z�[�W
     * @param t �g���[�X�Ώۂ̗�O
     * @param level ���O���x��
     * @param logName <code>Log</code> �C���X�^���X�̖��O
     */
    protected synchronized static void recordLogObject(Object message,
            Throwable t, String level, String logName) {
        LogObject logObject = new LogObject(getCalledTestCaseMethod(), message,
                t, level);

        __logObjects.add(logObject);

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(logName);
        buffer.append("]");
        buffer.append(logObject.toString());

        System.out.println(buffer);
    }

    /**
     * �o�͂��ꂽ���O�̃`�F�b�N���s���B
     * @param message �o�͂���Ă��郁�b�Z�[�W�B
     * @param t �o�͂���Ă����O�B
     * @param level ���O���x���B
     * @return �w�肳�ꂽ���b�Z�[�W�����O�o�͂���Ă����ꍇ�́A <code>true</code>�B
     */
    protected static boolean searchLogObject(Object message, Throwable t,
            String level) {
        LogObject logObject = new LogObject(getCalledTestCaseMethod(), message,
                t, level);
        boolean result = __logObjects.contains(logObject);
        __logObjects.remove(logObject);
        return result;
    }

    // ---------------------------------------------------- Logging Properties

    /**
     * ���̃��O�C���X�^���X���B
     */
    private String _name;

    // ------------------------------------------------ LogFactory Constructor

    /**
     * �R���X�g���N�^�B
     */
    public LogUTUtil() {
        super();
        _name = LogUTUtil.class.getName();
    }

    // --------------------------------------------------- Logging Constructor

    /**
     * �R���X�g���N�^�B
     * @param name ���O�̑ΏۂƂȂ�N���X��
     */
    public LogUTUtil(String name) {
        super();
        _name = name;
    }

    // -------------------------------------------------------- Logging Method

    /**
     * <code>debug</code> ���x���Ń��M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @see org.apache.commons.logging.Log#debug(java.lang.Object)
     */
    public void debug(Object message) {
        debug(message, null);
    }

    /**
     * <code>debug</code> ���x���ŃG���[���M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @param t �����ƂȂ�����O
     * @see org.apache.commons.logging.Log#debug(java.lang.Object, java.lang.Throwable)
     */
    public void debug(Object message, Throwable t) {
        recordLogObject(message, t, LogObject.DEBUG, _name);
    }

    /**
     * <code>error</code> ���x���Ń��M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @see org.apache.commons.logging.Log#error(java.lang.Object)
     */
    public void error(Object message) {
        error(message, null);
    }

    /**
     * <code>error</code> ���x���ŃG���[���M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @param t �����ƂȂ�����O
     * @see org.apache.commons.logging.Log#error(java.lang.Object, java.lang.Throwable)
     */
    public void error(Object message, Throwable t) {
        recordLogObject(message, t, LogObject.ERROR, _name);
    }

    /**
     * <code>fatal</code> ���x���Ń��M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @see org.apache.commons.logging.Log#fatal(java.lang.Object)
     */
    public void fatal(Object message) {
        fatal(message, null);
    }

    /**
     * <code>fatal</code> ���x���ŃG���[���M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @param t �����ƂȂ�����O
     * @see org.apache.commons.logging.Log#fatal(java.lang.Object, java.lang.Throwable)
     */
    public void fatal(Object message, Throwable t) {
        recordLogObject(message, t, LogObject.FATAL, _name);
    }

    /**
     * <code>info</code> ���x���Ń��M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @see org.apache.commons.logging.Log#info(java.lang.Object)
     */
    public void info(Object message) {
        info(message, null);
    }

    /**
     * <code>info</code> ���x���ŃG���[���M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @param t �����ƂȂ�����O
     * @see org.apache.commons.logging.Log#info(java.lang.Object, java.lang.Throwable)
     */
    public void info(Object message, Throwable t) {
        recordLogObject(message, t, LogObject.INFO, _name);
    }

    /**
     * <code>trace</code> ���x���Ń��M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @since 1.1
     * @see org.apache.commons.logging.Log#trace(java.lang.Object)
     */
    public void trace(Object message) {
        trace(message, null);
    }

    /**
     * <code>trace</code> ���x���ŃG���[���M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @param t �����ƂȂ�����O
     * @see org.apache.commons.logging.Log#trace(java.lang.Object, java.lang.Throwable)
     */
    public void trace(Object message, Throwable t) {
        recordLogObject(message, t, LogObject.TRACE, _name);
    }

    /**
     * <code>warn</code> ���x���Ń��M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @since 1.1
     * @see org.apache.commons.logging.Log#warn(java.lang.Object)
     */
    public void warn(Object message) {
        warn(message, null);
    }

    /**
     * <code>warn</code> ���x���ŃG���[���M���O�������s�Ȃ��B
     * @param message �o�͂��郁�b�Z�[�W
     * @param t �����ƂȂ�����O
     * @see org.apache.commons.logging.Log#warn(java.lang.Object, java.lang.Throwable)
     */
    public void warn(Object message, Throwable t) {
        recordLogObject(message, t, LogObject.WARN, _name);
    }

    /**
     * <code>debug</code> ���x���̃��O���������ݗL�����ǂ����`�F�b�N����B
     * @return ��� <code>true</code>
     * @see org.apache.commons.logging.Log#isDebugEnabled()
     */
    public boolean isDebugEnabled() {
        return true;
    }

    /**
     * <code>error</code> ���x���̃��O���������ݗL�����ǂ����`�F�b�N����B
     * @return ��� <code>true</code>
     * @see org.apache.commons.logging.Log#isErrorEnabled()
     */
    public boolean isErrorEnabled() {
        return true;
    }

    /**
     * <code>fatal</code> ���x���̃��O���������ݗL�����ǂ����`�F�b�N����B
     * @return ���true
     * @see org.apache.commons.logging.Log#isFatalEnabled()
     */
    public boolean isFatalEnabled() {
        return true;
    }

    /**
     * <code>info</code> ���x���̃��O���������ݗL�����ǂ����`�F�b�N����B
     * @return ��� <code>true</code>
     * @see org.apache.commons.logging.Log#isInfoEnabled()
     */
    public boolean isInfoEnabled() {
        return true;
    }

    /**
     * <code>trace</code> ���x���̃��O���������ݗL�����ǂ����`�F�b�N����B
     * @return ��� <code>true</code>
     * @see org.apache.commons.logging.Log#isTraceEnabled()
     */
    public boolean isTraceEnabled() {
        return true;
    }

    /**
     * <code>warn</code> ���x���̃��O���������ݗL�����ǂ����`�F�b�N����B
     * @return ��� <code>true</code>
     * @see org.apache.commons.logging.Log#isWarnEnabled()
     */
    public boolean isWarnEnabled() {
        return true;
    }

    // ----------------------------------------------------- LogFactory Method

    /**
     * <code>Log</code> �C���X�^���X�𐶐�����B
     * @param name ���� <code>Log</code> �C���X�^���X�ɕR�Â����O�B
     * @return <code>LogUTUtil</code> �C���X�^���X�B
     * @see LogFactoryImpl#newInstance(java.lang.String)
     */
    @Override
    protected Log newInstance(String name) {
        return new LogUTUtil(name);
    }

    // ------------------------------------------------------------- LogObject

    /**
     * ���O�̃L���[��\���N���X�B
     * @author �E�c �N��
     */
    private static class LogObject {

        // ------------------------------------- LogObject Constant Properties

        /**
         * <code>debug</code> ���x����\��������B
         */
        public static final String DEBUG = "DEBUG";

        /**
         * <code>error</code> ���x����\��������B
         */
        public static final String ERROR = "ERROR";

        /**
         * <code>error</code> ���x����\��������B
         */
        public static final String FATAL = "FATAL";

        /**
         * <code>info</code> ���x����\��������B
         */
        public static final String INFO = "INFO";

        /**
         * <code>trace</code> ���x����\��������B
         */
        public static final String TRACE = "TRACE";

        /**
         * <code>warn</code> ���x����\��������B
         */
        public static final String WARN = "WARN";

        // ---------------------------------------------- LogObject Properties

        /**
         * ���̃I�u�W�F�N�g�𐶐������X���b�h�ɕR�Â����\�b�h�I�u�W�F�N�g�B
         */
        private final Method _calledMethod;

        /**
         * ���O���x���B
         */
        private final String _level;

        /**
         * ���O���b�Z�[�W�B
         */
        private final Object _message;

        /**
         * ���O�o�͂̌����ƂȂ�����O�B
         */
        private final Throwable _t;

        // --------------------------------------------- LogObject Constructor

        /**
         * �R���X�g���N�^�B
         * @param calledMethod ���̃I�u�W�F�N�g�𐶐������X���b�h�ɕR�Â����\�b�h
         * @param message ���O���b�Z�[�W
         * @param t ���O�o�͂̌����ƂȂ�����O
         * @param level ���O���x��
         */
        public LogObject(Method calledMethod, Object message, Throwable t,
                String level) {
            super();
            _calledMethod = calledMethod;
            _message = message;
            _t = t;
            _level = level;
        }

        // ---------------------------------------------------- Logging Method

        /**
         * ���̃I�u�W�F�N�g�Ƒ��̃I�u�W�F�N�g�����������ǂ����������B
         * @param obj ��r�Ώۂ̎Q�ƃI�u�W�F�N�g
         * @return obj �����Ɏw�肳�ꂽ�I�u�W�F�N�g�Ƃ��̃I�u�W�F�N�g���������ꍇ�� <code>true</code> �A�����łȂ��ꍇ�� <code>false</code>�B
         */
        @Override
        public boolean equals(Object obj) {
            LogObject other = null;
            if (obj instanceof LogObject) {
                other = (LogObject) obj;
                if (other._calledMethod == null) {
                    return false;
                }
            } else {
                return false;
            }

            if (!other._calledMethod.equals(this._calledMethod)) {
                return false;
            }

            if ((other._message == null) ? this._message != null
                    : this._message == null
                            || !other._message.equals(this._message)) {
                return false;
            }

            if ((other._t == null) ? (this._t != null) : (this._t == null)
                    || !other._t.getClass().getName().equals(
                            this._t.getClass().getName())) {
                return false;
            }

            if ((other._level == null) ? (this._level != null)
                    : (this._level == null)
                            || !other._level.equals(this._level)) {
                return false;
            }

            return true;
        }

        /**
         * ���̃C���X�^���X�̏��𕶎���Ƃ��Ď擾����B
         * @return ���̃C���X�^���X�̏��
         * @since 1.1
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            buffer.append(_level);
            if (_level.length() == 4) {
                buffer.append(" ");
            }
            buffer.append("] ");
            buffer.append(_message);

            if (_t != null) {
                ByteArrayOutputStream byteArrayOutputStream = null;
                PrintStream printStream = null;
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    printStream = new PrintStream(byteArrayOutputStream);
                    _t.printStackTrace(printStream);
                    buffer.append("\n");
                    buffer.append(byteArrayOutputStream.toString());
                } finally {
                    if (printStream != null) {
                        printStream.close();
                    } else if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e) {
                            // do nothing.
                        }
                    }
                }
            }

            return buffer.toString();
        }
    }

}

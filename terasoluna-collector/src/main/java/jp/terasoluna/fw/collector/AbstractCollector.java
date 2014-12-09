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

package jp.terasoluna.fw.collector;

import java.beans.Introspector;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import jp.terasoluna.fw.collector.concurrent.ArrayBlockingQueueEx;
import jp.terasoluna.fw.collector.concurrent.NotificationBlockingQueue;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandlerStatus;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.collector.vo.CollectorStatus;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.logger.TLogger;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * AbstractCollector���ۃN���X
 * @param &lt;P&gt;
 */
public abstract class AbstractCollector<P> implements Collector<P>, Closeable,
                                           Callable<Integer>, Cloneable {
    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(AbstractCollector.class);

    /** �f�t�H���g�̃L���[�T�C�Y */
    public static final int DEFAULT_QUEUE_SIZE = 20;

    /** �f�t�H���g�X���[�v����(msec) */
    protected static final int DEFAULT_SLEEP_WAIT = 1;

    /** ���݃L���[�ێ����`�F�b�N�T�C�Y */
    protected static final int CURRENT_QUEUE_CHECK_SIZE = 1;

    /** ����L���[�ێ����`�F�b�N�T�C�Y */
    protected static final int PREVIOUS_QUEUE_CHECK_SIZE = 2;

    /** �璷���O�o�̓t���O. */
    protected static AtomicBoolean verboseLog = new AtomicBoolean(false);

    /** �L���[�T�C�Y */
    protected int queueSize = DEFAULT_QUEUE_SIZE;

    /** �X���[�v����(msec) */
    protected int sleepWait = DEFAULT_SLEEP_WAIT;

    /** �L���[ */
    protected BlockingQueue<DataValueObject> queue = null;

    /** ���݃L���[ */
    protected Queue<DataValueObject> currentQueue = null;

    /** ����L���[ */
    protected Queue<DataValueObject> previousQueue = null;

    /** �񓯊������̌��ʂ��擾���邽�߂̃N���X */
    protected volatile Future<?> fo = null;

    /** �I���t���O */
    protected volatile boolean finish = false;

    /** ���s�J�n�t���O */
    protected volatile boolean beginning = false;

    /** Validator */
    protected Validator validator = null;

    /** ValidationErrorHandler */
    protected ValidationErrorHandler validationErrorHandler = null;

    /** CollectorExceptionHandler */
    protected CollectorExceptionHandler exceptionHandler = null;

    /** �q�X���b�h���C���X�^���X */
    protected AbstractCollector<?> child = null;

    /**
     * AbstractCollector�����s����B
     */
    @SuppressWarnings("unchecked")
    protected void execute() {
        if (this.beginning) {
            return;
        }
        synchronized (this) {
            if (!this.beginning) {
                try {
                    // ���s�O����
                    beforeExecute();

                    if (this.queue == null) {
                        // �L���[����
                        this.queue = createQueue();
                    }

                    if (this.fo == null) {
                        // �������g�̃N���[�����쐬
                        Callable<Integer> callable = null;
                        try {
                            callable = (Callable<Integer>) this.clone();
                        } catch (CloneNotSupportedException e) {
                            SystemException exception = new SystemException(e);
                            exception.setMessage("The clone cannot be made.");
                            throw exception;
                        }

                        if (callable instanceof AbstractCollector) {
                            this.child = (AbstractCollector<P>) callable;
                        }

                        // ExecutorService�擾
                        ExecutorService ex = getExecutor();

                        try {
                            // �ʃX���b�h�Ŏ��s
                            this.fo = ex.submit(callable);
                        } catch (Throwable e) {
                            SystemException exception = new SystemException(e);
                            exception
                                    .setMessage("The thread cannot be started.");
                            throw exception;
                        } finally {
                            ex.shutdown();
                        }
                    }
                } finally {
                    // ���s�㏈��
                    afterExecute();
                }

                // ���s�J�n�t���O�𗧂Ă�
                this.beginning = true;
            }
        }
    }

    /**
     * ���s�O�����B<br>
     * �q�X���b�h(�R���N�^�X���b�h)���N������O�Ɏ��s�����B
     */
    protected void beforeExecute() {
    }

    /**
     * ���s�㏈���B<br>
     * �q�X���b�h(�R���N�^�X���b�h)���N��������Ɏ��s�����B
     * �q�X���b�h�̏I���͑҂����A�q�X���b�h�̋N��������������Ɏ��s�����B
     * �q�X���b�h�̋N���Ɏ��s�����ꍇ���A���s�����B
     */
    protected void afterExecute() {
    }

    /**
     * �J��Ԃ������ł���ɗv�f������ꍇ�� true ��Ԃ��܂��B<br>
     * �܂�Anext �̌Ăяo������O���X���[���邱�ƂȂ��v�f��Ԃ��ꍇ�́Atrue ��Ԃ��܂��B
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return �����q������ɗv�f�����ꍇ�� true
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return (getNextObject() != null);
    }

    /**
     * �J��Ԃ������Ŏ��̗v�f��Ԃ��܂��B
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return �J��Ԃ������Ŏ��̗v�f
     * @throws NoSuchElementException �J��Ԃ������ł���ȏ�v�f���Ȃ��ꍇ
     * @see java.util.Iterator#next()
     */
    @SuppressWarnings("unchecked")
    public P next() {
        // ���s�J�n�i����̂݁j
        execute();

        DataValueObject nextValue = getNextObject();
        if (nextValue != null) {
            if (this.previousQueue != null) {
                while (this.previousQueue.size() > PREVIOUS_QUEUE_CHECK_SIZE) {
                    this.previousQueue.remove();
                }
                this.previousQueue.add(nextValue);
            }
            if (this.currentQueue != null) {
                while (this.currentQueue.size() > CURRENT_QUEUE_CHECK_SIZE) {
                    this.currentQueue.remove();
                }
                this.currentQueue.add(nextValue);
            }
        } else {
            if (verboseLog.get() && LOGGER.isTraceEnabled()) {
                LOGGER.trace(LogId.TAL041007, this.queue.size());
            }
            setFinish(true);
            close();
            throw new NoSuchElementException();
        }

        // �L���[����1���f�[�^���擾����
        try {
            this.queue.poll(this.sleepWait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn(LogId.WAL041003, e);
        }

        if (nextValue.getThrowable() != null) {
            Throwable throwable = nextValue.getThrowable();
            // ��O���X���[����
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            } else {
                throw new SystemException(throwable);
            }
        }
        return (P) nextValue.getValue();
    }

    /**
     * �|�C���^�����̗v�f�Ɉڂ����Ɏ��̗v�f��Ԃ��܂��B<br>
     * <p>
     * null�̏ꍇ�͎��̗v�f�����݂��Ȃ����Ƃ������܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * <p>
     * �Y���f�[�^�̎擾���ɗ�O�����������ꍇ�A�ȉ������ɂ��قȂ��O���X���[����܂��B
     * <ul>
     * <li>�����^�C����O�������FRuntimeException�����̂܂܃X���[����</li>
     * <li>���̑���O�������FSystemException�Ń��b�v���ăX���[����</li>
     * </ul>
     * </p>
     * <p>���̓`�F�b�N�Ɏ��s�����ꍇ��next()���\�b�h�ŊY���f�[�^�ɃA�N�Z�X�����
     * ��O����������P�[�X�ł��A�{���\�b�h�ł͊Y���f�[�^���擾�ł��܂��B</p>
     * </p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return &lt;P&gt;
     * @see jp.terasoluna.fw.collector.Collector#getNext()
     */
    @SuppressWarnings("unchecked")
    public P getNext() {
        DataValueObject value = getNextObject();

        if (value == null) {
            return null;
        } else if (value.getValue() == null) {
            // ��O�������̓X���[����
            if (value.getThrowable() != null) {
                Throwable throwable = value.getThrowable();
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                } else {
                    throw new SystemException(throwable);
                }
            }
            return null;
        }

        return (P) value.getValue();
    }

    /**
     * �|�C���^�����̗v�f�Ɉڂ����Ɏ���Queue�v�f��Ԃ��܂��B<br>
     * <p>
     * null�̏ꍇ�͎��̗v�f�����݂��Ȃ����Ƃ������܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * <p>���Y�f�[�^�擾�ɂ��X�e�[�^�X���ԋp���ꂽ�ꍇ�Ɉȉ��̋����ƂȂ�܂��B
     * <table border="1">
     * <tr><th>���Y�f�[�^�擾�ɂ��X�e�[�^�X</th><th>getNextObject()�̋���</th></tr>
     * <tr>
     * <td>CollectorExceptionHandlerStatus.SKIP</td><td>���Y�f�[�^�͈ꌏ�ǂݎ̂Ă��A���̃f�[�^���擾����B</td>
     * </tr>
     * <tr>
     * <td>ValidateErrorStatus.END</td><td>�f�[�^�I�[�Ƃ���null��ԋp����B</td>
     * </tr>
     * <tr>
     * <td>CollectorExceptionHandlerStatus.END</td><td>�f�[�^�I�[�Ƃ���null��ԋp����B</td>
     * </tr>
     * <tr>
     * <td>CollectorStatus.END</td><td>�f�[�^�I�[�Ƃ���null��ԋp����B</td>
     * </tr>
     * </table>
     * </p>
     * <p>
     * �擾���ꂽ���Y�f�[�^��getThrowable()��null�ł͂Ȃ��ꍇ�A��O�n���h�������s����L�X�e�[�^�X�̔�����s���Ă���B
     * ����āA�{���\�b�h����ԋp���ꂽ�I�u�W�F�N�g��getThrowable()��null�ł͂Ȃ��ꍇ�A��O�n���h���̌��ʂ�null
     * ���邢��CollectorExceptionHandlerStatus.THROW�ƂȂ�B
     * </p>
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return ���Ɏ擾�����f�[�^��DataValueObject�i�����Ȃ��ꍇ��null���ԋp�����j
     */
    protected DataValueObject getNextObject() {
        // ���s�J�n�i����̂݁j
        execute();

        DataValueObject value = null;
        do {
            // �L���[����1���f�[�^���擾����i�폜���Ȃ��j
            if (this.queue != null) {
                value = this.queue.peek();
            }

            // �I���t���O������
            if (isFinish() && this.queue.isEmpty()) {
                if (verboseLog.get() && LOGGER.isTraceEnabled()) {
                    LOGGER.trace(LogId.TAL041014);
                }
                break;
            }

            if (value != null && value.getValidateStatus() != null) {
                ValidateErrorStatus validateStatus = value
                        .getValidateStatus();
                if (ValidateErrorStatus.END.equals(validateStatus)) {
                    return null;
                }
            }

            CollectorExceptionHandlerStatus es = null;

            if (value != null && value.getThrowable() != null) {
                try {
                    // ��O�n���h�������s����
                    es = handleException(value);
                } catch (Throwable e) {
                    LOGGER.warn(LogId.WAL041004, e);
                    // �����ł̗�O�̓��O�Ɏc���݂̂ŉ������Ȃ�
                }
                if (es == null || CollectorExceptionHandlerStatus.THROW.equals(es)) {
                    break;
                } else if (CollectorExceptionHandlerStatus.SKIP.equals(es)) {
                    // �X�e�[�^�X��SKIP�̏ꍇ�A�L���[����1���ǂݎ̂Ăă��[�v���p��������B
                    this.queue.poll();
                    value = null;
                    continue;
                } else if (CollectorExceptionHandlerStatus.END.equals(es)) {
                    // ���[�v�𔲂���null��ԋp����B
                    return null;
                }
            }

            if (value != null && CollectorStatus.END.equals(value.getCollectorStatus())) {
                setFinish(true);
                return null;
            }

            // null�̏ꍇ�̓X���[�v����
            if (value == null) {
                try {
                    if (verboseLog.get() && LOGGER.isTraceEnabled()) {
                        LOGGER.trace(LogId.TAL041019, this.sleepWait);
                    }
                    // sleepWait ms�҂�
                    Thread.sleep(this.sleepWait);

                } catch (InterruptedException e) {
                    LOGGER.warn(LogId.WAL041003, e);
                    break;
                }
                if (verboseLog.get() && LOGGER.isTraceEnabled()) {
                    LOGGER.trace(LogId.TAL041008, this.queue.size());
                }
            }
        } while (value == null);

        return value;
    }

    /**
     * 1���O�̗v�f��Ԃ��܂��B<br>
     * <p>
     * 1���ڂ̏ꍇ��null���Ԃ�܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * <p>
     * �Y���f�[�^�̎擾���ɗ�O�����������ꍇ�A�ȉ������ɂ��قȂ��O���X���[����܂��B
     * <ul>
     * <li>�����^�C����O�������FRuntimeException�����̂܂܃X���[����</li>
     * <li>���̑���O�������FSystemException�Ń��b�v���ăX���[����</li>
     * </ul>
     * </p>
     * <p>���̓`�F�b�N�Ɏ��s�����ꍇ��next()���\�b�h�ŊY���f�[�^�ɃA�N�Z�X�����
     * ��O����������P�[�X�ł��A�{���\�b�h�ł͊Y���f�[�^���擾�ł��܂��B</p>
     * </p>
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return &lt;P&gt;
     * @see jp.terasoluna.fw.collector.Collector#getPrevious()
     */
    @SuppressWarnings("unchecked")
    public P getPrevious() {
        DataValueObject value = getPreviousObject();

        if (value == null) {
            return null;
        } else if (value.getValue() == null) {
            // ��O�������̓X���[����
            if (value.getThrowable() != null) {
                Throwable throwable = value.getThrowable();
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                } else {
                    throw new SystemException(throwable);
                }
            }
            return null;
        }

        return (P) value.getValue();
    }

    /**
     * 1���O��Queue�v�f��Ԃ��܂��B<br>
     * <p>
     * 1���ڂ̏ꍇ��null���Ԃ�܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return next()�ɂ��擾���ꂽ�f�[�^�̂ЂƂO�̃f�[�^��DataValueObject
     */
    protected DataValueObject getPreviousObject() {
        // ���s�J�n�i����̂݁j
        execute();

        DataValueObject value = null;
        if (this.previousQueue != null && this.previousQueue.size() > 1) {
            while (this.previousQueue.size() > PREVIOUS_QUEUE_CHECK_SIZE) {
                this.previousQueue.remove();
            }

            value = this.previousQueue.peek();
        }

        return value;
    }

    /**
     * ���݂̗v�f��Ԃ��܂��B<br>
     * <p>
     * null�̏ꍇ�͌��݂̗v�f�����݂��Ȃ����Ƃ������܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * <p>
     * �Y���f�[�^�̎擾���ɗ�O�����������ꍇ�A�ȉ������ɂ��قȂ��O���X���[����܂��B
     * <ul>
     * <li>�����^�C����O�������FRuntimeException�����̂܂܃X���[����</li>
     * <li>���̑���O�������FSystemException�Ń��b�v���ăX���[����</li>
     * </ul>
     * </p>
     * <p>���̓`�F�b�N�Ɏ��s�����ꍇ��next()���\�b�h�ŊY���f�[�^�ɃA�N�Z�X�����
     * ��O����������P�[�X�ł��A�{���\�b�h�ł͊Y���f�[�^���擾�ł��܂��B</p>
     * </p>
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return &lt;P&gt;
     * @see jp.terasoluna.fw.collector.Collector#getCurrent()
     */
    @SuppressWarnings("unchecked")
    public P getCurrent() {
        // ���s�J�n�i����̂݁j
        execute();

        DataValueObject value = getCurrentObject();

        if (value == null) {
            return null;
        } else if (value.getValue() == null) {
            // ��O�������̓X���[����
            if (value.getThrowable() != null) {
                Throwable throwable = value.getThrowable();
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                } else {
                    throw new SystemException(throwable);
                }
            }
            return null;
        }

        return (P) value.getValue();
    }

    /**
     * ���݂�Queue�v�f��Ԃ��܂��B<br>
     * <p>
     * null�̏ꍇ�͌��݂̗v�f�����݂��Ȃ����Ƃ������܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @return next()�ɂ�蒼�߂Ŏ擾���ꂽ�f�[�^��DataValueObject
     */
    protected DataValueObject getCurrentObject() {
        // ���s�J�n�i����̂݁j
        execute();

        DataValueObject value = null;

        if (this.currentQueue != null && this.currentQueue.size() > 0) {
            while (this.currentQueue.size() > CURRENT_QUEUE_CHECK_SIZE) {
                this.currentQueue.remove();
            }
            value = this.currentQueue.peek();
        }

        return value;
    }

    /**
     * ���̃X�g���[������āA����Ɋ֘A���邷�ׂẴV�X�e�����\�[�X��������܂��B<br>
     * �X�g���[�������łɕ����Ă���ꍇ�́A���̃��\�b�h���Ăяo���Ă����̌��ʂ�����܂���B
     * <p>
     * <b>���{���\�b�h�̓}���`�X���b�h�Z�[�t�ł���܂���B</b>
     * </p>
     * @see java.io.Closeable#close()
     */
    public void close() {
        if (!isFinish()) {
            if (this.fo != null) {
                this.fo.cancel(true);
            }
        }
    }

    /**
     * ��ɂȂ�R���N�V��������A�����q�ɂ���čŌ�ɕԂ��ꂽ�v�f���폜���܂� (�C�ӂ̃I�y���[�V����)�B<br>
     * ���̃��\�b�h�́Anext �̌Ăяo�����Ƃ� 1 �񂾂��Ăяo�����Ƃ��ł��܂��B�����q�̓���́A<br>
     * �J��Ԃ����������̃��\�b�h�̌Ăяo���ȊO�̕��@�Ŏ��s����Ă���Ƃ��Ɋ�ɂȂ�R���N�V�������ύX���ꂽ�ꍇ�͕ۏ؂���܂���B
     * <p>
     * <b>���{���\�b�h�̓T�|�[�g����Ă��܂���B</b>
     * </p>
     * @throws UnsupportedOperationException Iterator �� remove �I�y���[�V�������T�|�[�g���Ȃ��ꍇ
     * @throws IllegalStateException next ���\�b�h���܂��Ăяo����ĂȂ��ꍇ�A�܂��� next ���\�b�h�̍Ō�̌Ăяo���̂��Ƃ� remove ���\�b�h�����łɌĂяo����Ă���ꍇ
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        if (verboseLog.get() && LOGGER.isTraceEnabled()) {
            LOGGER.trace(LogId.TAL041011, Thread.currentThread().getName());
        }
        if (!isFinish()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(LogId.WAL041005, Thread.currentThread().getName());
            }
        }
        super.finalize();

        // ���b�N����댯�̂���close()�̌Ăяo������߂�
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<P> iterator() {
        return this;
    }

    /**
     * getExecutor���\�b�h.
     * @return ExecutorService
     */
    protected ExecutorService getExecutor() {
        // �V�����G�O�[�L���[�^��ԋp
        return Executors.newSingleThreadExecutor(createThreadFactory());
    }

    /**
     * �X���b�h�t�@�N�g���𐶐�����.
     * @return �X���b�h�t�@�N�g��
     */
    protected ThreadFactory createThreadFactory() {
        return new CollectorThreadFactory();
    }

    /**
     * �L���[���쐬����
     * @return
     */
    protected BlockingQueue<DataValueObject> createQueue() {
        if (this.currentQueue == null) {
            // current�L���[����
            this.currentQueue = createCurrentQueue();
        }
        if (this.previousQueue == null) {
            // previous�L���[����
            this.previousQueue = createPreviousQueue();
        }
        return new ArrayBlockingQueueEx<DataValueObject>(this.queueSize);
    }

    /**
     * current�L���[���쐬����
     * @return Queue&lt;DataValueObject&gt;
     */
    protected Queue<DataValueObject> createCurrentQueue() {
        return new ConcurrentLinkedQueue<DataValueObject>();
    }

    /**
     * previous�L���[���쐬����
     * @return Queue&lt;DataValueObject&gt;
     */
    protected Queue<DataValueObject> createPreviousQueue() {
        return new ConcurrentLinkedQueue<DataValueObject>();
    }

    /**
     * �L���[���擾����B
     * @return Queue&lt;DataValueObject&gt;
     */
    protected Queue<DataValueObject> getQueue() {
        return this.queue;
    }

    /**
     * �L���[�T�C�Y���w�肷��B<br>
     * @param queueSize int
     */
    protected void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    /**
     * �X���[�v����(msec)���擾����<br>
     * @return �X���[�v����(msec)
     */
    protected int getSleepWait() {
        return sleepWait;
    }

    /**
     * �X���[�v����(msec)��ݒ肷��<br>
     * @param sleepWait �X���[�v����(msec)
     */
    protected void setSleepWait(int sleepWait) {
        this.sleepWait = sleepWait;
    }

    /**
     * �L���[�Ƀf�[�^��ǉ�����B
     * @param dataValueObject DataValueObject
     * @throws InterruptedException
     */
    protected void addQueue(DataValueObject dataValueObject)
                                                            throws InterruptedException {
        addQueue(dataValueObject, false);
    }

    /**
     * �L���[�Ƀf�[�^��ǉ�����B
     * @param dataValueObject DataValueObject
     * @param force boolean �����L���[�C���O�t���O
     * @throws InterruptedException
     */
    protected void addQueue(DataValueObject dataValueObject, boolean force)
                                                                           throws InterruptedException {
        if (force && this.queue != null) {
            this.queue.offer(dataValueObject);
            return;
        }

        boolean finish = isFinish();

        if (!finish) {
            // ���̓`�F�b�N
            ValidateErrorStatus vs = null;
            if (this.validator != null) {
                try {
                    vs = validate(dataValueObject);
                } catch (Throwable e) {
                    // �擾�����f�[�^�ɔ���������O��ݒ肵1���L���[�ɂ߂�
                    if (dataValueObject == null) {
                        this.queue.put(new DataValueObject(e));
                    } else {
                        dataValueObject.setThrowable(e);
                        this.queue.put(dataValueObject);
                    }
                    return;
                }
            }

            if (vs == null || ValidateErrorStatus.CONTINUE.equals(vs)) {
                // �擾�����f�[�^��1���L���[�ɂ߂�
                this.queue.put(dataValueObject);
            } else if (ValidateErrorStatus.END.equals(vs)) {
                DataValueObject errorStop = new DataValueObject(vs);
                this.queue.put(errorStop);
                // ������~�i�ȍ~�̃L���[�C���O��������~�j
                setFinish(true);
            } else if (ValidateErrorStatus.SKIP.equals(vs)) {
            	// �X�L�b�v�̓L���[�ɂ߂Ȃ�
            }
        } else {
            if (LOGGER.isTraceEnabled()) {
                long dc = -1;
                if (dataValueObject != null) {
                    dc = dataValueObject.getDataCount();
                }
                LOGGER.trace(LogId.TAL041013, finish, "", dc);
            }
            throw new InterruptedException(
                    "The stop demand of the thread is carried out.");
        }
    }

    /**
     * ���̓`�F�b�N���s��.<br>
     * @param dataValueObject DataValueObject
     * @return ValidateStatus
     */
    protected ValidateErrorStatus validate(DataValueObject dataValueObject) {
        ValidateErrorStatus vs = ValidateErrorStatus.CONTINUE;

        if (this.validator != null) {
            Class<?> clazz = null;
            String objectName = null;
            Errors errors = null;

            // ���̓I�u�W�F�N�g�̃N���X�^���擾
            if (dataValueObject != null && dataValueObject.getValue() != null) {
                clazz = dataValueObject.getValue().getClass();

                if (clazz != null) {
                    objectName = clazz.getSimpleName();
                    if (objectName != null) {
                        objectName = Introspector.decapitalize(objectName);
                        // Errors�I�u�W�F�N�g����
                        errors = new BindException(dataValueObject.getValue(),
                                objectName);
                    }
                }
            }

            if (clazz != null && errors != null
                    && this.validator.supports(clazz)) {
                // ���̓`�F�b�N
                this.validator.validate(dataValueObject.getValue(), errors);

                if (errors.hasErrors()) {
                    vs = handleValidationError(dataValueObject, errors);
                }
            }
        }

        return vs;
    }

    /**
     * ���̓`�F�b�N�G���[���̏���.<br>
     * @param dataValueObject DataValueObject
     * @param errors Errors
     * @return ValidateErrorStatus
     */
    protected ValidateErrorStatus handleValidationError(
            DataValueObject dataValueObject, Errors errors) {

        if (this.validationErrorHandler != null) {
            return this.validationErrorHandler.handleValidationError(
                    dataValueObject, errors);
        }

        return ValidateErrorStatus.SKIP;
    }

    /**
     * ��O�������̏���
     * @param dataValueObject DataValueObject
     * @return CollectorExceptionHandlerStatus
     */
    protected CollectorExceptionHandlerStatus handleException(
            DataValueObject dataValueObject) {
        CollectorExceptionHandlerStatus result = dataValueObject.getExceptionHandlerStatus();
        // ���ɔ���ς݂Ȃ�΂��̂Ƃ��̌��ʂ�Ԃ��B
        if (result != null) {
            return result;
        }

        if (this.exceptionHandler != null) {
            result = this.exceptionHandler.handleException(dataValueObject);
            dataValueObject.setExceptionHandlerStatus(result);
        }
        return result;
    }

    /**
     * �I���t���O�̏�Ԃ��m�F����B
     * @return boolean
     */
    protected boolean isFinish() {
        boolean finish = this.finish;
        AbstractCollector<?> localChild = this.child;
        Future<?> future = this.fo;

        if (future != null) {
            boolean done = future.isDone();

            if (localChild != null) {
                // �q�X���b�h���̏I���t���O���Q�Ƃ���
                if (localChild.isFinish()) {
                    finish = localChild.isFinish();
                }
            }
            return finish || done;
        }

        if (localChild != null) {
            // �q�X���b�h���̏I���t���O���Q�Ƃ���
            if (localChild.isFinish()) {
                finish = localChild.isFinish();
            }
        }
        return finish;
    }

    /**
     * �I���t���O��ݒ肷��B
     */
    protected void setFinish() {
        if (verboseLog.get() && LOGGER.isTraceEnabled()) {
            LOGGER.trace(LogId.TAL041012, Thread.currentThread().getName());
        }
        setFinish(true);

        // �I���t���O���L���[�ɂ߂�
        try {
            addQueue(new DataValueObject(CollectorStatus.END), true);
        } catch (InterruptedException ie) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(LogId.TAL041012, ie, Thread.currentThread()
                        .getName());
            }
        }

        // �L���[�ɑ΂��L���[�C���O�̏I����ʒm����B
        if (queue instanceof NotificationBlockingQueue) {
            ((NotificationBlockingQueue<DataValueObject>) queue)
                    .finishQueueing();
        }
    }

    /**
     * �I���t���O��ݒ肷��B
     * @param finish
     */
    protected void setFinish(boolean finish) {
        this.finish = finish;
    }

    /**
     * Collector���N���[�Y����B<br>
     * <p>
     * �����ɓn���ꂽcollector��null�łȂ���΃N���[�Y����B<br>
     * �܂��A�N���[�Y����ۂ�IO��O�����������ꍇ�͖�������B<br>
     * </p>
     * @param collector Collector
     */
    public static void closeQuietly(Collector<?> collector) {
        try {
            if (collector != null) {
                collector.close();
            }
        } catch (IOException e) {
            // �Ȃɂ����Ȃ�
        }
    }

    /**
     * �璷���O�o�̓t���O��ݒ肷��B
     * @param verbose �璷���O�o�̓t���O
     */
    public static void setVerbose(boolean verbose) {
        verboseLog.set(verbose);
    }
}

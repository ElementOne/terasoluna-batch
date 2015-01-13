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

package jp.terasoluna.fw.collector.db;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import jp.terasoluna.fw.collector.LogId;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.logger.TLogger;
import org.apache.ibatis.session.ResultContext;

/**
 * QueueingDataRowHandler�̎����N���X<br>
 */
public class QueueingDataRowHandlerImpl implements QueueingDataRowHandler {

    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(QueueingDataRowHandlerImpl.class);

    /** �璷���O�o�̓t���O. */
    protected static AtomicBoolean verboseLog = new AtomicBoolean(false);

    /**
     * �O��handleResult���\�b�h�ɓn���ꂽ�I�u�W�F�N�g
     */
    protected Object prevRow = null;

    /** DBCollector */
    protected DBCollector<?> dbCollector = null;

    /** �f�[�^�J�E���g */
    protected AtomicLong dataCount = new AtomicLong(0);

    /*
     * (non-Javadoc)
     * @see org.apache.ibatis.session.ResultHandler#handleResult(org.apache.ibatis.session.ResultContext)
     */
    public void handleResult(ResultContext context) {
        if (!Thread.currentThread().isInterrupted()) {
            delayCollect();
            this.prevRow = context.getResultObject();
        } else {
            // ���荞�݂�����������L���[���X�L�b�v����
            if (verboseLog.get()) {
                LOGGER.trace(LogId.TAL041003);
            }
            // ���荞�݂�����������s�t�F�b�`�𒆒f����B
            throw new InterruptedRuntimeException();
        }
    }

    /**
     * �O��handleResult���\�b�h�ɓn���ꂽ<code>Row</code>�f�[�^���L���[�Ɋi�[����B
     */
    public void delayCollect() {
        if (this.prevRow != null) {
            if (!Thread.currentThread().isInterrupted()) {
                long dtcnt = this.dataCount.incrementAndGet();
                try {
                    if (this.dbCollector != null) {
                        // �擾�����I�u�W�F�N�g��1���L���[�ɂ߂�
                        this.dbCollector.addQueue(new DataValueObject(
                                this.prevRow, dtcnt));
                    }
                } catch (InterruptedException e) {
                    if (LOGGER.isTraceEnabled()) {
                        LOGGER.trace(LogId.TAL041002, Thread.currentThread()
                                .getName());
                    }
                    // �ďo�����̗�O�L���b�`���ɃL���[���Ńu���b�N����Ȃ��悤�A�{�X���b�h���u���荞�݁v��ԂƂ���B
                    Thread.currentThread().interrupt();
                    throw new InterruptedRuntimeException(e);
                }
            } else {
                // ���荞�݂�����������L���[���X�L�b�v����
                if (verboseLog.get()) {
                    LOGGER.trace(LogId.TAL041003);
                }
                // ���荞�݂�����������s�t�F�b�`�𒆒f����B
                throw new InterruptedRuntimeException();
            }
        }
    }

    /**
     * DBCollector��ݒ肷��B<br>
     * @param dbCollector DBCollector&lt;?&gt;
     */
    public void setDbCollector(DBCollector<?> dbCollector) {
        this.dbCollector = dbCollector;
    }

    /**
     * �璷���O�o�̓t���O��ݒ肷��B
     * @param verbose �璷���O�o�̓t���O
     */
    public static void setVerbose(boolean verbose) {
        verboseLog.set(verbose);
    }
}

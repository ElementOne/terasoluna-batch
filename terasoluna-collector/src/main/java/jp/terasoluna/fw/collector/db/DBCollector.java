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

import jp.terasoluna.fw.collector.AbstractCollector;
import jp.terasoluna.fw.collector.LogId;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.logger.TLogger;

/**
 * DBCollector<br>
 * �Ɨ������ʃX���b�h���N�����AQueryRowHandleDAO��񓯊��Ŏ��s����B
 * @param &ltP&gt
 */
public class DBCollector<P> extends AbstractCollector<P> {

    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger.getLogger(DBCollector.class);

    /** QueryRowHandleDAO */
    protected QueryRowHandleDAO queryRowHandleDAO = null;

    /** ���s����SQL��ID */
    protected String sqlID = null;

    /** SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g */
    protected Object bindParams = null;

    /** QueueingDataRowHandler�C���X�^���X */
    protected QueueingDataRowHandler rowHandler = null;

    /** QueueingDataRowHandler�̃N���X�^ */
    protected Class<? extends QueueingDataRowHandler> queueingDataRowHandlerClass = QueueingDataRowHandlerImpl.class;

    /** DBCollector�O�㏈�� */
    protected DBCollectorPrePostProcess dbCollectorPrePostProcess = null;

    /**
     * DBCollector�R���X�g���N�^<br>
     */
    protected DBCollector() {
    }

    /**
     * DBCollector�R���X�g���N�^<br>
     * @param queryRowHandleDAO QueryRowHandleDAO�C���X�^���X
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    public DBCollector(QueryRowHandleDAO queryRowHandleDAO, String sqlID,
            Object bindParams) {
        this(new DBCollectorConfig(queryRowHandleDAO, sqlID, bindParams));
    }

    /**
     * DBCollector�R���X�g���N�^<br>
     * @param queryRowHandleDAO QueryRowHandleDAO�C���X�^���X
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param relation1n 1:N�}�b�s���O�g�p����true
     */
    public DBCollector(QueryRowHandleDAO queryRowHandleDAO, String sqlID,
            Object bindParams, boolean relation1n) {

        this(new DBCollectorConfig(queryRowHandleDAO, sqlID, bindParams)
                .addRelation1n(relation1n));
    }

    /**
     * DBCollector�R���X�g���N�^<br>
     * @param queryRowHandleDAO QueryRowHandleDAO�C���X�^���X
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�ƁB0�ȉ��͖����j
     */
    public DBCollector(QueryRowHandleDAO queryRowHandleDAO, String sqlID,
            Object bindParams, int queueSize) {
        this(new DBCollectorConfig(queryRowHandleDAO, sqlID, bindParams)
                .addQueueSize(queueSize));
    }

    /**
     * DBCollector�R���X�g���N�^<br>
     * @param queryRowHandleDAO QueryRowHandleDAO�C���X�^���X
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param exceptionHandler ��O�n���h��
     */
    public DBCollector(QueryRowHandleDAO queryRowHandleDAO, String sqlID,
            Object bindParams, CollectorExceptionHandler exceptionHandler) {
        this(new DBCollectorConfig(queryRowHandleDAO, sqlID, bindParams)
                .addExceptionHandler(exceptionHandler));
    }

    /**
     * DBCollector�R���X�g���N�^<br>
     * @param queryRowHandleDAO QueryRowHandleDAO�C���X�^���X
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�ƁB0�ȉ��͖����j
     * @param relation1n 1:N�}�b�s���O�g�p����true
     * @param exceptionHandler ��O�n���h��
     * @param dbCollectorPrePostProcess DBCollector�O�㏈��
     */
    public DBCollector(QueryRowHandleDAO queryRowHandleDAO, String sqlID,
            Object bindParams, int queueSize, boolean relation1n,
            CollectorExceptionHandler exceptionHandler,
            DBCollectorPrePostProcess dbCollectorPrePostProcess) {
        this(new DBCollectorConfig(queryRowHandleDAO, sqlID, bindParams)
                .addQueueSize(queueSize).addRelation1n(relation1n)
                .addExceptionHandler(exceptionHandler)
                .addDbCollectorPrePostProcess(dbCollectorPrePostProcess));
    }

    /**
     * DBCollector�R���X�g���N�^<br>
     * @param config DBCollectorConfig DBCollector�ݒ荀��
     */
    public DBCollector(DBCollectorConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("The parameter is null.");
        }

        this.queryRowHandleDAO = config.getQueryRowHandleDAO();
        this.sqlID = config.getSqlID();
        this.bindParams = config.getBindParams();
        if (config.getQueueSize() > 0) {
            setQueueSize(config.getQueueSize());
        }
        if (config.isRelation1n()) {
            this.queueingDataRowHandlerClass = Queueing1NRelationDataRowHandlerImpl.class;
        }
        this.exceptionHandler = config.getExceptionHandler();
        this.dbCollectorPrePostProcess = config.getDbCollectorPrePostProcess();

        if (config.isExecuteByConstructor()) {
            // ���s�J�n
            execute();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    public Integer call() throws Exception {
        try {
            DBCollectorPrePostProcessStatus expStatus = null;
            do {
                try {
                    // SQL���s�O����
                    preprocess();

                    // QueryRowHandleDAO ���s
                    this.queryRowHandleDAO.executeWithRowHandler(this.sqlID,
                            bindParams, this.rowHandler);
                    this.rowHandler.delayCollect();

                } catch (Throwable th) {
                    // SQL���s�㏈���i��O�j
                    expStatus = postprocessException(th);

                    // �X�e�[�^�X����
                    if (expStatus == null
                            || DBCollectorPrePostProcessStatus.THROW
                                    .equals(expStatus)) {
                        // ��O���X���[
                        throw th;
                    } else if (expStatus == null
                            || DBCollectorPrePostProcessStatus.END
                                    .equals(expStatus)) {
                        // ��O���X���[�����ɏI��
                        break;
                    }
                } finally {
                    // SQL���s�㏈��
                    postprocessComplete();
                }

                // �X�e�[�^�X�����g���C�Ȃ�ēxSQL�����s����
            } while (expStatus != null
                    && DBCollectorPrePostProcessStatus.RETRY.equals(expStatus));
        } catch (Throwable e) {
            // �V���b�g�_�E�����͔���������O���L���[�ɋl�߂Ȃ�
            if (!isFinish()) {
                // ����������O���L���[�ɂ߂�
                try {
                    addQueue(new DataValueObject(e));
                } catch (InterruptedException ie) {
                    LOGGER.warn(LogId.WAL041003, e);
                    LOGGER.warn(LogId.WAL041003, ie);
                }

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(LogId.DAL041002, e.getClass().getName(),
                            Thread.currentThread().getName());
                }
            }

            return Integer.valueOf(-1);
        } finally {
            setFinish();
        }

        return Integer.valueOf(0);
    }

    /**
     * SQL���s�O����
     */
    protected void preprocess() {
        if (this.dbCollectorPrePostProcess != null) {
            this.dbCollectorPrePostProcess.preprocess(this);
        }
    }

    /**
     * SQL���s�㏈���i��O�j
     * @param th Throwable
     * @return DBCollectorPrePostProcessStatus
     */
    protected DBCollectorPrePostProcessStatus postprocessException(Throwable th) {
        DBCollectorPrePostProcessStatus expStatus = null;
        if (this.dbCollectorPrePostProcess != null) {
            expStatus = this.dbCollectorPrePostProcess.postprocessException(
                    this, th);
        }
        return expStatus;
    }

    /**
     * SQL���s�㏈��
     */
    protected void postprocessComplete() {
        if (this.dbCollectorPrePostProcess != null) {
            this.dbCollectorPrePostProcess.postprocessComplete(this);
        }
    }

    /**
     * getDataRowHandler���\�b�h.
     * @return QueueingDataRowHandler
     */
    protected QueueingDataRowHandler getDataRowHandler() {
        QueueingDataRowHandler dataRowHandler = null;

        try {
            dataRowHandler = this.queueingDataRowHandlerClass.newInstance();
        } catch (InstantiationException e) {
            SystemException exception = new SystemException(e);
            exception.setMessage("Generation of an instance goes wrong.");
            throw exception;
        } catch (IllegalAccessException e) {
            SystemException exception = new SystemException(e);
            exception.setMessage("Generation of an instance goes wrong.");
            throw exception;
        }

        return dataRowHandler;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        if (obj instanceof DBCollector) {
            DBCollector<P> qac = (DBCollector<P>) obj;
            qac.rowHandler = getDataRowHandler();
            qac.rowHandler.setDbCollector(this);
        }
        return obj;
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.collector.AbstractCollector#addQueue(jp.terasoluna.fw.collector.vo.DataValueObject)
     */
    @Override
    protected void addQueue(DataValueObject dataValueObject)
                                                            throws InterruptedException {
        super.addQueue(dataValueObject);
    }
}

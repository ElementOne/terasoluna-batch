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
import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.logger.TLogger;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.Method;

/**
 * DaoCollector<br>
 * �Ɨ������ʃX���b�h���N�����AQueryRowHandleDao��񓯊��Ŏ��s����B
 * @param &ltP&gt
 */
public class DaoCollector<P> extends AbstractCollector<P> {

    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger.getLogger(DaoCollector.class);

    /** QueryRowHandleDao */
    protected Object queryRowHandleDao = null;

    /** SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g */
    protected Object bindParams = null;

    /** �s�P�ʃf�[�^�A�N�Z�X�̌Ăяo���Ŏg�p�����Dao�̃��\�b�h�� */
    protected String methodName = null;

    /** QueueingDataRowHandler�C���X�^���X */
    protected QueueingDataRowHandler rowHandler = null;

    /** QueueingDataRowHandler�̃N���X�^ */
    protected Class<? extends QueueingDataRowHandler> queueingDataRowHandlerClass = QueueingDataRowHandlerImpl.class;

    /** DaoCollector�O�㏈�� */
    protected DaoCollectorPrePostProcess daoCollectorPrePostProcess = null;

    /**
     * DaoCollector�R���X�g���N�^<br>
     */
    protected DaoCollector() {
    }

    /**
     * DaoCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s����Dao�̃��\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    public DaoCollector(Object queryRowHandleDao, String methodName,
            Object bindParams) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams));
    }

    /**
     * DaoCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s����Dao�̃��\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param relation1n 1:N�}�b�s���O�g�p����true
     */
    public DaoCollector(Object queryRowHandleDao, String methodName,
            Object bindParams, boolean relation1n) {

        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addRelation1n(relation1n));
    }

    /**
     * DaoCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s����Dao�̃��\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�ƁB0�ȉ��͖����j
     */
    public DaoCollector(Object queryRowHandleDao, String methodName,
            Object bindParams, int queueSize) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize));
    }

    /**
     * DaoCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s����Dao�̃��\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param exceptionHandler ��O�n���h��
     */
    public DaoCollector(Object queryRowHandleDao, String methodName,
            Object bindParams, CollectorExceptionHandler exceptionHandler) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addExceptionHandler(exceptionHandler));
    }

    /**
     * DaoCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s����Dao�̃��\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�ƁB0�ȉ��͖����j
     * @param relation1n 1:N�}�b�s���O�g�p����true
     * @param exceptionHandler ��O�n���h��
     * @param daoCollectorPrePostProcess DaoCollector�O�㏈��
     */
    public DaoCollector(Object queryRowHandleDao, String methodName,
            Object bindParams, int queueSize, boolean relation1n,
            CollectorExceptionHandler exceptionHandler,
            DaoCollectorPrePostProcess daoCollectorPrePostProcess) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addRelation1n(relation1n)
                .addExceptionHandler(exceptionHandler)
                .addDaoCollectorPrePostProcess(daoCollectorPrePostProcess));
    }

    /**
     * DaoCollector�R���X�g���N�^<br>
     * @param config DaoCollectorConfig DaoCollector�ݒ荀��
     */
    public DaoCollector(DaoCollectorConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("The parameter is null.");
        }

        this.queryRowHandleDao = config.getQueryRowHandleDao();
        this.methodName = config.getMethodName();
        this.bindParams = config.getBindParams();
        if (config.getQueueSize() > 0) {
            setQueueSize(config.getQueueSize());
        }
        if (config.isRelation1n()) {
            this.queueingDataRowHandlerClass = Queueing1NRelationDataRowHandlerImpl.class;
        }
        this.exceptionHandler = config.getExceptionHandler();
        this.daoCollectorPrePostProcess = config.getDaoCollectorPrePostProcess();

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
            DaoCollectorPrePostProcessStatus expStatus = null;
            do {
                try {
                    // SQL���s�O����
                    preprocess();

                    Class<?> queryRowHandleDaoClazz = this.queryRowHandleDao.getClass();
                    Method collectMethod = queryRowHandleDaoClazz.getMethod(this.methodName,
                            Object.class, ResultHandler.class);

                    // QueryRowHandleDAO ���s
                    collectMethod.invoke(this.queryRowHandleDao, this.bindParams, this.rowHandler);

                    this.rowHandler.delayCollect();

                } catch (Throwable th) {
                    // SQL���s�㏈���i��O�j
                    expStatus = postprocessException(th);

                    // �X�e�[�^�X����
                    if (expStatus == null
                            || DaoCollectorPrePostProcessStatus.THROW
                                    .equals(expStatus)) {
                        // ��O���X���[
                        throw th;
                    } else if (DaoCollectorPrePostProcessStatus.END
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
                    && DaoCollectorPrePostProcessStatus.RETRY.equals(expStatus));
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

            return -1;
        } finally {
            setFinish();
        }

        return 0;
    }

    /**
     * SQL���s�O����
     */
    protected void preprocess() {
        if (this.daoCollectorPrePostProcess != null) {
            this.daoCollectorPrePostProcess.preprocess(this);
        }
    }

    /**
     * SQL���s�㏈���i��O�j
     * @param th Throwable
     * @return DaoCollectorPrePostProcessStatus
     */
    protected DaoCollectorPrePostProcessStatus postprocessException(Throwable th) {
        DaoCollectorPrePostProcessStatus expStatus = null;
        if (this.daoCollectorPrePostProcess != null) {
            expStatus = this.daoCollectorPrePostProcess.postprocessException(
                    this, th);
        }
        return expStatus;
    }

    /**
     * SQL���s�㏈��
     */
    protected void postprocessComplete() {
        if (this.daoCollectorPrePostProcess != null) {
            this.daoCollectorPrePostProcess.postprocessComplete(this);
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
        if (obj instanceof DaoCollector) {
            DaoCollector<P> qac = (DaoCollector<P>) obj;
            qac.rowHandler = getDataRowHandler();
            qac.rowHandler.setDaoCollector(this);
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

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

package jp.terasoluna.fw.batch.executor;

import jp.terasoluna.fw.batch.constants.EventConstants;
import jp.terasoluna.fw.batch.constants.JobStatusConstants;
import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.executor.dao.SystemDao;
import jp.terasoluna.fw.batch.executor.vo.BLogicResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.util.JobUtil;
import jp.terasoluna.fw.logger.TLogger;
import jp.terasoluna.fw.util.PropertyUtil;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * �񓯊��o�b�`�G�O�[�L���[�^���ۃN���X�B<br>
 * <br>
 * �񓯊��W���u�N���p�̃o�b�`�G�O�[�L���[�^�B
 * @see jp.terasoluna.fw.batch.executor.BatchExecutor
 * @see jp.terasoluna.fw.batch.executor.AbstractBatchExecutor
 * @see jp.terasoluna.fw.batch.executor.AsyncBatchExecutor
 */
public abstract class AbstractJobBatchExecutor extends AbstractBatchExecutor {

    /**
     * ���O.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(AbstractJobBatchExecutor.class);

    /**
     * �W���u�̎��s�Ԋu�i�~���b�j�擾�p�L�[.
     */
    protected static final String JOB_INTERVAL_TIME = "polling.interval";

    /**
     * Executor�̏풓���[�h���̏I���t���O�Ď��t�@�C���i�t���p�X�ŋL�q�j�擾�p�L�[.
     */
    protected static final String EXECUTOR_END_MONITORING_FILE = "executor.endMonitoringFile";

    /**
     * Executor�̃W���u�I���҂��`�F�b�N�Ԋu�i�~���b�j�擾�p�L�[.
     */
    protected static final String EXECUTOR_JOB_TERMINATE_WAIT_INTERVAL_TIME = "executor.jobTerminateWaitInterval";

    /**
     * �W���u�̎��s�Ԋu�i�~���b�j�̃f�t�H���g�l
     */
    protected static final long DEFAULT_JOB_INTERVAL_TIME = 1000;

    /**
     * �W���u���s���GC���s���itrue/false�j�̃f�t�H���g�l
     */
    protected static final boolean DEFAULT_JOB_AFTER_GC = true;

    /**
     * Executor�̏풓���[�h���̃W���u���X�g�擾�Ԋu�i�~���b�j�̃f�t�H���g�l
     */
    protected static final long DEFAULT_EXECUTOR_LOOP_INTERVAL_TIME = 1000;

    /**
     * Executor�̏풓���[�h���̏I���t���O�Ď��t�@�C���i�t���p�X�ŋL�q�j�̃f�t�H���g�l
     */
    protected static final String DEFAULT_EXECUTOR_END_MONITORING_FILE = null;

    /**
     * Executor�̃X���b�h�L���[�T�C�Y�`�F�b�N�Ԋu�i�~���b�j�̃f�t�H���g�l
     */
    protected static final long DEFAULT_EXECUTOR_QUEUE_CHECK_INTERVAL_TIME = 1000;

    /**
     * Executor�̃W���u�I���҂��`�F�b�N�Ԋu�i�~���b�j�̃f�t�H���g�l
     */
    protected static final long DEFAULT_EXECUTOR_JOB_TERMINATE_WAIT_INTERVAL_TIME = 5000;

    /**
     * �W���u�̎��s�Ԋu�i�~���b�j
     */
    protected long jobIntervalTime = DEFAULT_JOB_INTERVAL_TIME;

    /**
     * Executor�̏풓���[�h���̏I���t���O�Ď��t�@�C���i�t���p�X�ŋL�q�j
     */
    protected String executorEndMonitoringFile = DEFAULT_EXECUTOR_END_MONITORING_FILE;

    /**
     * Executor�̃W���u�I���҂��`�F�b�N�Ԋu�i�~���b�j
     */
    protected long executorJobTerminateWaitIntervalTime = DEFAULT_EXECUTOR_JOB_TERMINATE_WAIT_INTERVAL_TIME;

    /**
     * �J�n���̃X�e�[�^�X�ύX���s�����ǂ���
     */
    protected boolean changeStartStatus = false;

    /**
     * �R���X�g���N�^
     */
    protected AbstractJobBatchExecutor() {
        super();
        initParameter();
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.executor.AbstractBatchExecutor# initDefaultAppContext()
     */
    @Override
    protected void initDefaultAppContext() {
        // �V�X�e���A�v���P�[�V�����R���e�L�X�g�擾
        String defaultAppContextName = getDefaultBeanFileName();
        if (defaultAppContextName == null || "".equals(defaultAppContextName)) {
            LOGGER.error(LogId.EAL025003);
            return;
        }
        // �f�[�^�\�[�X�A�v���P�[�V�����R���e�L�X�g�擾
        String dataSourceAppContextName = getDataSourceBeanFileName();
        if (dataSourceAppContextName == null
                || "".equals(dataSourceAppContextName)) {
            LOGGER.error(LogId.EAL025003);
            return;
        }

        defaultApplicationContext = getApplicationContext(
                defaultAppContextName, dataSourceAppContextName);
        if (defaultApplicationContext == null) {
            LOGGER.error(LogId.EAL025016, defaultAppContextName,
                    dataSourceAppContextName);
            return;
        }
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.executor.AbstractBatchExecutor# initSystemDatasourceDao()
     */
    @Override
    protected void initSystemDatasourceDao() {
        if (defaultApplicationContext == null) {
            return;
        }

        String sysDaoKey = PropertyUtil
                .getProperty(SYSTEM_DATASOURCE_DAO);
        String transactionManagerKey = PropertyUtil
                .getProperty(SYSTEM_DATASOURCE_TRANSACTION_MANAGER);

        // �V�X�e��DAO�擾
        if (sysDaoKey != null && sysDaoKey.length() != 0) {
            if (defaultApplicationContext.containsBean(sysDaoKey)) {
                try {
                    sysDao = defaultApplicationContext.getBean(
                            sysDaoKey, SystemDao.class);
                } catch (Throwable e) {
                    LOGGER.error(LogId.EAL025051, e, sysDaoKey);
                }
            }
        }

        // transactionManager�擾
        if (transactionManagerKey != null
                && transactionManagerKey.length() != 0) {
            if (defaultApplicationContext.containsBean(transactionManagerKey)) {
                try {
                    sysTransactionManager = defaultApplicationContext
                            .getBean(transactionManagerKey,
                                    PlatformTransactionManager.class);
                } catch (Throwable e) {
                    LOGGER.error(LogId.EAL025019, e, transactionManagerKey);
                }
            }
        }

        if (sysDao == null) {
            LOGGER.error(LogId.EAL025052);
        }
        if (sysTransactionManager == null) {
            LOGGER.error(LogId.EAL025022);
        }
    }

    /**
     * ������
     */
    protected void initParameter() {
        // �W���u�̎��s�Ԋu�i�~���b�j
        String jobIntervalTimeStr = PropertyUtil.getProperty(JOB_INTERVAL_TIME);
        if (jobIntervalTimeStr != null && jobIntervalTimeStr.length() != 0) {
            try {
                this.jobIntervalTime = Long.parseLong(jobIntervalTimeStr);
            } catch (NumberFormatException e) {
                this.jobIntervalTime = DEFAULT_JOB_INTERVAL_TIME;
            }
        } else {
            this.jobIntervalTime = DEFAULT_JOB_INTERVAL_TIME;
        }

        // Executor�̏풓���[�h���̏I���t���O�Ď��t�@�C���i�t���p�X�ŋL�q�j
        String executorEndMonitoringFileStr = PropertyUtil
                .getProperty(EXECUTOR_END_MONITORING_FILE);
        if (executorEndMonitoringFileStr != null
                && executorEndMonitoringFileStr.length() != 0) {
            this.executorEndMonitoringFile = executorEndMonitoringFileStr;
        } else {
            this.executorEndMonitoringFile = DEFAULT_EXECUTOR_END_MONITORING_FILE;
        }

        // Executor�̃W���u�I���҂��`�F�b�N�Ԋu�i�~���b�j
        String executorJobTerminateWaitIntervalTimeStr = PropertyUtil
                .getProperty(EXECUTOR_JOB_TERMINATE_WAIT_INTERVAL_TIME);
        if (executorJobTerminateWaitIntervalTimeStr != null
                && executorJobTerminateWaitIntervalTimeStr.length() != 0) {
            try {
                this.executorJobTerminateWaitIntervalTime = Long
                        .parseLong(executorJobTerminateWaitIntervalTimeStr);
            } catch (NumberFormatException e) {
                this.executorJobTerminateWaitIntervalTime = DEFAULT_EXECUTOR_JOB_TERMINATE_WAIT_INTERVAL_TIME;
            }
        } else {
            this.executorJobTerminateWaitIntervalTime = DEFAULT_EXECUTOR_JOB_TERMINATE_WAIT_INTERVAL_TIME;
        }

    }

    /**
     * <h6>�o�b�`���s.</h6>
     * @param jobSequenceId �W���u�V�[�P���X�R�[�h
     * @return �r�W�l�X���W�b�N���s����
     */
    public BLogicResult executeBatch(String jobSequenceId) {
        BLogicResult result = new BLogicResult();
        boolean st = false;

        LOGGER.info(LogId.IAL025001, jobSequenceId);

        // DAO���g�p�\���`�F�b�N
        if (sysDao == null || sysTransactionManager == null) {
            LOGGER.error(LogId.EAL025023, jobSequenceId);
            return result;
        }

        try {
            // �W���u���R�[�h�擾
            BatchJobData jobRecord = null;
            try {
                jobRecord = JobUtil.selectJob(jobSequenceId, false,
                        sysDao);
            } catch (DataAccessException e) {
                LOGGER.error(LogId.EAL025049, jobSequenceId);
                return result;
            }
                    
            if (jobRecord == null) {
                LOGGER.error(LogId.EAL025024, jobSequenceId);
                return result;
            }

            if (changeStartStatus) {
                boolean status;
                try {
                    // �W���u�X�e�[�^�X�ݒ�i�J�n�j
                    status = startBatchStatus(jobSequenceId, sysDao, sysTransactionManager);
                } catch (DataAccessException e) {
                    LOGGER.error(LogId.EAL025050, e, jobSequenceId);
                    return result;
                } catch (TransactionException e) {
                    LOGGER.error(LogId.EAL025050, e, jobSequenceId);
                    return result;
                }
                if (!status) {
                    // �X�e�[�^�X�X�V���s���̕s������Ԍ��o��
                    LOGGER.error(LogId.EAL025050, jobSequenceId);
                    return result;
                }
            }

            // �O�̂��߃g��������
            if (jobRecord.getJobAppCd() != null) {
                jobRecord.setJobAppCd(jobRecord.getJobAppCd().trim());
            }

            // �o�b�`�������s
            result = executeBatch(jobRecord);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(LogId.DAL025021, result.getBlogicStatus());
            }
        } finally {
            try {
                // �����ς� ���W���u�X�e�[�^�X�ݒ�i�I���j
                st = endBatchStatus(jobSequenceId, result,
                        sysDao, sysTransactionManager);
            } catch (DataAccessException e) {
                LOGGER.error(LogId.EAL025025, e, jobSequenceId, result
                        .getBlogicStatus());
                return result;
            } catch (TransactionException e) {
                LOGGER.error(LogId.EAL025025, e, jobSequenceId, result
                        .getBlogicStatus());
                return result;
            }
            if (!st) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(LogId.EAL025025, jobSequenceId, result
                            .getBlogicStatus());
                }
                return result;
            }
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(LogId.IAL025003, jobSequenceId, result
                    .getBlogicStatus());
        }
        return result;
    }

    /**
     * <h6>�W���u�X�e�[�^�X�X�V�i�W���u�J�n�j.</h6>
     * @param jobSequenceId �X�V�Ώۂ̃W���u�V�[�P���X�R�[�h
     * @param sysDao SystemDAO
     * @param transactionManager TransactionManager
     * @return �X�e�[�^�X�X�V������������true
     */
    protected boolean startBatchStatus(String jobSequenceId, SystemDao sysDao,
                                       PlatformTransactionManager transactionManager) {
        return updateBatchStatus(jobSequenceId,
                EventConstants.EVENT_STATUS_START, null, sysDao,transactionManager);
    }

    /**
     * <h6>�W���u�X�e�[�^�X�X�V�i�W���u�I���j.</h6>
     * @param jobSequenceId �X�V�Ώۂ̃W���u�V�[�P���X�R�[�h
     * @param result �X�e�[�^�X
     * @param sysDao SystemDao
     * @param transactionManager TransactionManager
     * @return �X�e�[�^�X�X�V������������true
     */
    protected boolean endBatchStatus(String jobSequenceId, BLogicResult result,
            SystemDao sysDao, PlatformTransactionManager transactionManager) {
        String blogicStatus = null;
        if (result != null) {
            blogicStatus = Integer.toString(result.getBlogicStatus());
        }
        return updateBatchStatus(jobSequenceId,
                EventConstants.EVENT_STATUS_NORMAL_TERMINATION, blogicStatus,
                sysDao, transactionManager);
    }

    /**
     * <h6>�W���u�X�e�[�^�X�X�V.</h6>
     * <p>
     * �X�e�[�^�X����}�b�v�ɂ��������āA�W���u�̃X�e�[�^�X�𔽉f
     * </p>
     * @param jobSequenceId �X�V�Ώۂ̃W���u�V�[�P���X�R�[�h
     * @param eventCode �C�x���g�R�[�h
     * @param blogicStatus blogic�̖߂�l
     * @param sysDao SystemDAO
     * @param transactionManager TransactionManager
     * @return �X�e�[�^�X�X�V������������true
     */
    protected boolean updateBatchStatus(String jobSequenceId, String eventCode,
            String blogicStatus, SystemDao sysDao, PlatformTransactionManager transactionManager) {
        TransactionStatus tranStatus = null;

        try {
            DefaultTransactionDefinition tranDef = new DefaultTransactionDefinition();

            // �g�����U�N�V�����J�n
            tranStatus = transactionManager.getTransaction(tranDef);
            LOGGER.debug(LogId.DAL025022);

            // �W���u���R�[�h�擾
            BatchJobData job = JobUtil.selectJob(jobSequenceId, true, sysDao);
            if (job == null) {
                LOGGER.error(LogId.EAL025026, jobSequenceId);
                return false;
            }

            // �X�e�[�^�X���菈��
            String changeStatus = judgmentStatus(job, jobSequenceId, eventCode,
                    blogicStatus);
            if (changeStatus == null) {
                // �X�e�[�^�X�s�����ɂ��o�b�`���s�X�L�b�v
                return false;
            }

            LOGGER.debug(LogId.DAL025023, jobSequenceId, changeStatus);

            // �X�e�[�^�X�X�V
            JobUtil.updateJobStatus(job.getJobSequenceId(), changeStatus, null,
                    blogicStatus, sysDao);

            // �g�����U�N�V�����R�~�b�g
            transactionManager.commit(tranStatus);
            LOGGER.debug(LogId.DAL025024);
        } catch (Exception e) {
            LOGGER.error(LogId.EAL025027, e);
            if (e instanceof DataAccessException) {
                throw (DataAccessException) e;
            } else if (e instanceof TransactionException) {
                throw (TransactionException) e;
            }
            throw new BatchException(e);
        } finally {
            try {
                // �g�����U�N�V�����I���i�g�����U�N�V�������������̏ꍇ�̓��[���o�b�N����j
                if (tranStatus != null && !tranStatus.isCompleted()) {
                    transactionManager.rollback(tranStatus);
                }
                LOGGER.debug(LogId.DAL025025);
            } catch (Exception e) {
                LOGGER.error(LogId.EAL025028, e);
            }
        }
        return true;
    }

    /**
     * <h6>�W���u�X�e�[�^�X�̍X�V���胁�\�b�h</h6> �C�x���g�R�[�h�ƃW���u�X�e�[�^�X���m�F���A�W���u�X�e�[�^�X�̍X�V���K�v��������s���B<br>
     * �X�V���K�v�Ȃ��ꍇ��Info���O���o�͂��Anull��ԋp����B
     * @param job �W���u���R�[�h
     * @param jobSequenceId �X�V�Ώۂ̃W���u�V�[�P���X�R�[�h
     * @param eventCode �C�x���g�R�[�h
     * @param blogicStatus blogic�̖߂�l
     * @return
     */
    protected String judgmentStatus(BatchJobData job, String jobSequenceId,
            String eventCode, String blogicStatus) {

        String judge = null;

        if (EventConstants.EVENT_STATUS_START.equals(eventCode)) {
            if (JobStatusConstants.JOB_STATUS_UNEXECUTION.equals(job
                    .getCurAppStatus())) {
                judge = JobStatusConstants.JOB_STATUS_EXECUTING;
            } else {
                judge = null;
            }
        } else {
            if (JobStatusConstants.JOB_STATUS_EXECUTING.equals(job
                    .getCurAppStatus())) {
                judge = JobStatusConstants.JOB_STATUS_PROCESSED;
            } else {
                judge = null;
            }
        }

        // �X�e�[�^�XNG�`�F�b�N
        if (judge == null) {
            // INFO����Ȃ��Ȃ��H
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(LogId.IAL025004, jobSequenceId, blogicStatus,
                        eventCode, job.getCurAppStatus(), judge);
            }
            return null;
        }

        return judge.toString();
    }

    /**
     * �W���u�̎��s�Ԋu�i�~���b�j
     * @return the jobIntervalTime
     */
    public long getJobIntervalTime() {
        return jobIntervalTime;
    }

    /**
     * Executor�̏풓���[�h���̏I���t���O�Ď��t�@�C���i�t���p�X�ŋL�q�j
     * @return the executorEndMonitoringFile
     */
    public String getExecutorEndMonitoringFile() {
        return executorEndMonitoringFile;
    }

    /**
     * Executor�̃W���u�I���҂��`�F�b�N�Ԋu�i�~���b�j
     * @return the executorJobTerminateWaitIntervalTime
     */
    public long getExecutorJobTerminateWaitIntervalTime() {
        return executorJobTerminateWaitIntervalTime;
    }

    /**
     * �J�n���̃X�e�[�^�X�ύX���s�����ǂ���
     * @param changeStartStatus the changeStartStatus to set
     */
    public void setChangeStartStatus(boolean changeStartStatus) {
        this.changeStartStatus = changeStartStatus;
    }

}

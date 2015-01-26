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

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.batch.executor.concurrent.BatchServant;
import jp.terasoluna.fw.batch.executor.dao.SystemDao;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListResult;
import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.batch.util.JobUtil;
import jp.terasoluna.fw.logger.TLogger;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;

/**
 * �񓯊��o�b�`�G�O�[�L���[�^�B<br>
 * <br>
 * �풓�v���Z�X�Ƃ��ċN�����A�W���u�Ǘ��e�[�u���ɓo�^���ꂽ�W���u���擾���A�W���u�̎��s��BatchServant�N���X�Ɉڏ�����B<br>
 * �܂��W���u�Ǘ��e�[�u���ɃW���u���s���ʂ��X�V����B<br>
 * @see jp.terasoluna.fw.batch.executor.AbstractJobBatchExecutor
 */
public class AsyncBatchExecutor extends AbstractJobBatchExecutor {

    /**
     * ���K�[.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(AsyncBatchExecutor.class);

    /**
     * �^�X�N�G�O�[�L���[�^��Bean��
     */
    private static final String BATCH_TASK_EXECUTOR = "batchTaskExecutor.default";

    /**
     * �X���b�h���s�p��BatchServant�N���X��Bean��
     */
    private static final String BATCH_TASK_SERVANT = "batchTaskExecutor.batchServant";

    /**
     * �f�[�^�x�[�X�ُ펞�̃��g���C�񐔒�`��
     */
    private static final String BATCH_DB_ABNORMAL_RETRY_MAX = "batchTaskExecutor.dbAbnormalRetryMax";

    /**
     * �f�[�^�x�[�X�ُ펞�̃��g���C�Ԋu��`��
     */
    private static final String BATCH_DB_ABNORMAL_RETRY_INTERVAL = "batchTaskExecutor.dbAbnormalRetryInterval";

    /**
     * �f�[�^�x�[�X�ُ펞�̃��g���C�񐔂����Z�b�g����O�񂩂�̔����Ԋu��`��
     */
    private static final String BATCH_DB_ABNORMAL_RETRY_RESET = "batchTaskExecutor.dbAbnormalRetryReset";

    /**
     * �W���u���s���g���C�Ԋu�i�~���b�j��`��
     */
    private static final String BATCH_EXECUTE_RETRY_INTERVAL = "batchTaskExecutor.executeRetryInterval";

    /**
     * �W���u���s���g���C�񐔒�`��
     */
    private static final String BATCH_EXECUTE_RETRY_COUNTMAX = "batchTaskExecutor.executeRetryCountMax";

    /**
     * �󂫃X���b�h�c��臒l�̃f�t�H���g�l
     */
    private static final String BATCH_AVAILABLE_THREADTHRESHOLD_COUNT = "batchTaskExecutor.availableThreadThresholdCount";

    /**
     * �󂫃X���b�h�c��臒l�ȉ��̏ꍇ�̃E�F�C�g���ԁi�~���b�j�̃f�t�H���g�l
     */
    private static final String BATCH_AVAILABLE_THREADTHRESHOLD_WAIT = "batchTaskExecutor.availableThreadThresholdWait";

    /**
     * �f�[�^�x�[�X�ُ펞�̃��g���C�񐔂̃f�t�H���g�l
     */
    private static final long BATCH_DB_ABNORMAL_RETRY_MAX_DEFAULT = 0;

    /**
     * �f�[�^�x�[�X�ُ펞�̃��g���C�Ԋu�̃f�t�H���g�l�i�~���b�j
     */
    private static final long BATCH_DB_ABNORMAL_RETRY_INTERVAL_DEFAULT = 20000;

    /**
     * �f�[�^�x�[�X�ُ펞�̃��g���C�񐔂����Z�b�g����O�񂩂�̔����Ԋu�̃f�t�H���g�l�i�~���b�j
     */
    private static final long BATCH_DB_ABNORMAL_RETRY_RESET_DEFAULT = 600000;

    /**
     * �v���Z�X�I���R�[�h�i����j
     */
    private static final int PROCESS_END_STATUS_NORMAL = 0;

    /**
     * �v���Z�X�I���R�[�h�i�ُ�j
     */
    private static final int PROCESS_END_STATUS_FAILURE = 255;

    /**
     * �W���u���s���g���C�Ԋu�i�~���b�j�̃f�t�H���g�l
     */
    protected static final long DEFAULT_EXECUTE_RETRY_INTERVAL = 1000;

    /**
     * �W���u���s���g���C�񐔂̃f�t�H���g�l
     */
    protected static final long DEFAULT_EXECUTE_RETRY_COUNTMAX = 10;

    /**
     * �󂫃X���b�h�c��臒l�̃f�t�H���g�l
     */
    protected static final long DEFAULT_AVAILABLE_THREADTHRESHOLD_COUNT = 1;

    /**
     * �󂫃X���b�h�c��臒l�ȉ��̏ꍇ�̃E�F�C�g���ԁi�~���b�j�̃f�t�H���g�l
     */
    protected static final long DEFAULT_AVAILABLE_THREADTHRESHOLD_WAIT = 100;

    /** �X���b�h�O���[�v�v���t�B�b�N�X. */
    public static final String THREAD_GROUP_PREFIX = AsyncBatchExecutor.class
            .getSimpleName()
            + "ThreadGroup";

    /** �X���b�h���v���t�B�b�N�X. */
    public static final String THREAD_NAME_PREFIX = AsyncBatchExecutor.class
            .getSimpleName()
            + "Thread";

    /** �X���b�h�O���[�v�Z�p���[�^. */
    public static final String THREAD_GROUP_SEPARATOR = "-";

    /** �X���b�h���Z�p���[�^. */
    public static final String THREAD_NAME_SEPARATOR = "-";

    /** �X���b�h�O���[�v�ԍ�. */
    protected static AtomicInteger threadGroupNo = new AtomicInteger(0);

    /**
     * �W���u���s���g���C�Ԋu�i�~���b�j
     */
    protected static long executeRetryInterval = DEFAULT_EXECUTE_RETRY_INTERVAL;

    /**
     * �W���u���s���g���C��
     */
    protected static long executeRetryCountMax = DEFAULT_EXECUTE_RETRY_COUNTMAX;

    /**
     * �󂫃X���b�h�c��臒l
     */
    protected static long availableThreadThresholdCount = DEFAULT_AVAILABLE_THREADTHRESHOLD_COUNT;

    /**
     * �󂫃X���b�h�c��臒l�ȉ��̏ꍇ�̃E�F�C�g���ԁi�~���b�j
     */
    protected static long availableThreadThresholdWait = DEFAULT_AVAILABLE_THREADTHRESHOLD_WAIT;

    /**
     * �j������(activeCount��0�ɂȂ�����j�����Ă悢)ThreadGroup��List�B
     */
    protected static List<ThreadGroup> destroyCandidateThreadGroupList = new LinkedList<ThreadGroup>();

    /**
     * �R���X�g���N�^
     */
    protected AsyncBatchExecutor() {
        super();
    }

    /**
     * ���C�����\�b�h.
     * @param args
     */
    public static void main(String[] args) {
        long retryCount = 0;
        long retryCountMax = BATCH_DB_ABNORMAL_RETRY_MAX_DEFAULT;
        long retryCountReset = BATCH_DB_ABNORMAL_RETRY_RESET_DEFAULT;
        long retryInterval = BATCH_DB_ABNORMAL_RETRY_INTERVAL_DEFAULT;
        int status = PROCESS_END_STATUS_FAILURE;
        long lastExceptionTime = System.currentTimeMillis();

        // �v���p�e�B����l���擾
        String dbAbnormalRetryMaxStr = PropertyUtil
                .getProperty(BATCH_DB_ABNORMAL_RETRY_MAX);
        String dbAbnormalRetryIntervalStr = PropertyUtil
                .getProperty(BATCH_DB_ABNORMAL_RETRY_INTERVAL);
        String dbAbnormalRetryResetStr = PropertyUtil
                .getProperty(BATCH_DB_ABNORMAL_RETRY_RESET);
        String executeRetryIntervalStr = PropertyUtil
                .getProperty(BATCH_EXECUTE_RETRY_INTERVAL);
        String executeRetryCountMaxStr = PropertyUtil
                .getProperty(BATCH_EXECUTE_RETRY_COUNTMAX);
        String availableThreadThresholdCountStr = PropertyUtil
                .getProperty(BATCH_AVAILABLE_THREADTHRESHOLD_COUNT);
        String availableThreadThresholdWaitStr = PropertyUtil
                .getProperty(BATCH_AVAILABLE_THREADTHRESHOLD_WAIT);

        // �f�[�^�x�[�X�ُ펞�̃��g���C��
        if (dbAbnormalRetryMaxStr != null
                && dbAbnormalRetryMaxStr.length() != 0) {
            try {
                retryCountMax = Long.parseLong(dbAbnormalRetryMaxStr);
            } catch (NumberFormatException e) {
                LOGGER.error(LogId.EAL025046, e, BATCH_DB_ABNORMAL_RETRY_MAX,
                        dbAbnormalRetryMaxStr);
                System.exit(status);
                return;
            }
        }

        // �f�[�^�x�[�X�ُ펞�̃��g���C�Ԋu�i�~���b�j
        if (dbAbnormalRetryIntervalStr != null
                && dbAbnormalRetryIntervalStr.length() != 0) {
            try {
                retryInterval = Long.parseLong(dbAbnormalRetryIntervalStr);
            } catch (NumberFormatException e) {
                LOGGER.error(LogId.EAL025046, e,
                        BATCH_DB_ABNORMAL_RETRY_INTERVAL,
                        dbAbnormalRetryIntervalStr);
                System.exit(status);
                return;
            }
        }

        // �f�[�^�x�[�X�ُ펞�̃��g���C�񐔂����Z�b�g����O�񂩂�̔����Ԋu�i�~���b�j
        if (dbAbnormalRetryResetStr != null
                && dbAbnormalRetryResetStr.length() != 0) {
            try {
                retryCountReset = Long.parseLong(dbAbnormalRetryResetStr);
            } catch (NumberFormatException e) {
                LOGGER.error(LogId.EAL025046, e, BATCH_DB_ABNORMAL_RETRY_RESET,
                        dbAbnormalRetryResetStr);
                System.exit(status);
                return;
            }
        }

        // �W���u���s���g���C�Ԋu�i�~���b�j
        if (executeRetryIntervalStr != null
                && executeRetryIntervalStr.length() != 0) {
            try {
                executeRetryInterval = Long.parseLong(executeRetryIntervalStr);
            } catch (NumberFormatException e) {
                LOGGER.error(LogId.EAL025046, e, BATCH_EXECUTE_RETRY_INTERVAL,
                        executeRetryIntervalStr);
                System.exit(status);
                return;
            }
        }

        // �W���u���s���g���C��
        if (executeRetryCountMaxStr != null
                && executeRetryCountMaxStr.length() != 0) {
            try {
                executeRetryCountMax = Long.parseLong(executeRetryCountMaxStr);
            } catch (NumberFormatException e) {
                LOGGER.error(LogId.EAL025046, e, BATCH_EXECUTE_RETRY_COUNTMAX,
                        executeRetryCountMaxStr);
                System.exit(status);
                return;
            }
        }

        // �󂫃X���b�h�c��臒l
        if (availableThreadThresholdCountStr != null
                && availableThreadThresholdCountStr.length() != 0) {
            try {
                availableThreadThresholdCount = Long
                        .parseLong(availableThreadThresholdCountStr);
            } catch (NumberFormatException e) {
                LOGGER.error(LogId.EAL025046, e,
                        BATCH_AVAILABLE_THREADTHRESHOLD_COUNT,
                        availableThreadThresholdCountStr);
                System.exit(status);
                return;
            }
        }

        // �󂫃X���b�h�c��臒l�ȉ��̏ꍇ�̃E�F�C�g���ԁi�~���b�j
        if (availableThreadThresholdWaitStr != null
                && availableThreadThresholdWaitStr.length() != 0) {
            try {
                availableThreadThresholdWait = Long
                        .parseLong(availableThreadThresholdWaitStr);
            } catch (NumberFormatException e) {
                LOGGER.error(LogId.EAL025046, e,
                        BATCH_AVAILABLE_THREADTHRESHOLD_WAIT,
                        availableThreadThresholdWaitStr);
                System.exit(status);
                return;
            }
        }

        do {
            try {
                status = executorMain(args);
                break;
            } catch (RetryableExecuteException e) {
                Throwable cause = e.getCause();
                // �O�񂩂�w�莞�Ԉȏ�o�߂��Ă����烊�g���C�񐔃��Z�b�g
                if ((System.currentTimeMillis() - lastExceptionTime) > retryCountReset) {
                    retryCount = 0;
                }
                lastExceptionTime = System.currentTimeMillis();

                // ���g���C�񐔃`�F�b�N
                if (retryCount >= retryCountMax) {
                    LOGGER.error(LogId.EAL025031, cause);
                    break;
                }

                // �X���[�v���ԑ҂�
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e1) {
                    // �Ȃɂ����Ȃ�
                }

                retryCount++;

                LOGGER.info(LogId.IAL025017, retryCount, retryCountMax,
                        retryCountReset, retryInterval);
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(LogId.TAL025010, BatchUtil.getMemoryInfo());
                }
            }
        } while (true);

        System.exit(status);
    }

    /**
     * �G�O�[�L���[�^���C�����\�b�h.
     * @param args
     */
    public static int executorMain(String[] args) {
        int status = PROCESS_END_STATUS_FAILURE;
        Throwable throwable = null;
        String jobAppCd = null;
        String batchTaskExecutorName = null;
        String batchTaskServantName = null;
        ThreadPoolTaskExecutor taskExecutor = null;

        LOGGER.info(LogId.IAL025005);

        // ��1��������W���u�Ɩ��R�[�h���擾
        if (args.length > 0) {
            jobAppCd = args[0];
        }

        // �����Ɏw�肳��Ă��Ȃ��ꍇ�͊��ϐ�����W���u�Ɩ��R�[�h���擾
        if (jobAppCd == null || jobAppCd.length() == 0) {
            jobAppCd = JobUtil.getenv(ENV_JOB_APP_CD);
            if (jobAppCd != null && jobAppCd.length() == 0) {
                jobAppCd = null;
            }
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(LogId.IAL025006, jobAppCd == null ? "" : jobAppCd);
        }

        // �G�O�[�L���[�^����
        AsyncBatchExecutor executor = new AsyncBatchExecutor();

        // �V�X�e��DAO���擾
        SystemDao systemDao = executor.getSystemDao();
        if (systemDao == null) {
            LOGGER.info(LogId.IAL025018);
            return status;
        }

        // �^�X�N�G�O�[�L���[�^��BatchServant�N���X��Bean���擾
        batchTaskExecutorName = PropertyUtil.getProperty(BATCH_TASK_EXECUTOR);
        batchTaskServantName = PropertyUtil.getProperty(BATCH_TASK_SERVANT);

        // �^�X�N�G�O�[�L���[�^�擾
        ApplicationContext ctx = executor.getDefaultApplicationContext();
        if (ctx != null) {
            if (ctx.containsBean(batchTaskExecutorName)) {
                Object batchTaskExecutorObj = null;
                try {
                    batchTaskExecutorObj = ctx.getBean(batchTaskExecutorName,
                            ThreadPoolTaskExecutor.class);
                } catch (Throwable e) {
                    LOGGER.error(LogId.EAL025029, e, batchTaskExecutorName);
                }
                if (batchTaskExecutorObj instanceof ThreadPoolTaskExecutor) {
                    taskExecutor = (ThreadPoolTaskExecutor) batchTaskExecutorObj;
                }
            }
        }
        if (taskExecutor == null) {
            LOGGER.info(LogId.IAL025009);
            return status;
        }

        try {
            do {
                // �W���u���X�g����1���̂ݎ擾�i���s�X���b�h�ɋ󂫂�����ꍇ�̂ݎ擾����j
                List<BatchJobListResult> jobList = null;
                if (checkTaskQueue(taskExecutor)) {
                    if (jobAppCd == null) {
                        jobList = JobUtil.selectJobList(systemDao, 0, 1);
                    } else {
                        jobList = JobUtil.selectJobList(jobAppCd, systemDao, 0,
                                1);
                    }
                }

                if (jobList != null && !jobList.isEmpty()) {
                    // ���X�g�̂P���ڂ̂ݎ擾
                    BatchJobListResult batchJobListResult = jobList.get(0);

                    if (batchJobListResult != null) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(LogId.DAL025026, batchJobListResult
                                    .getJobSequenceId());
                        }

                        if (LOGGER.isDebugEnabled()) {
                            // �X���b�h�v�[���^�X�N�G�O�[�L���[�^�̃X�e�[�^�X���f�o�b�O���O�ɏo��
                            logOutputTaskExecutor(LOGGER, taskExecutor);
                        }

                        // ���s�X���b�h�ɋ󂫂�����΃o�b�`�������s
                        if (checkTaskQueue(taskExecutor)) {

                            // �o�b�`�������s
                            boolean executeResult = executeJob(executor, ctx,
                                    taskExecutor, batchTaskServantName,
                                    batchJobListResult);
                            if (!executeResult) {
                                break;
                            }
                        }
                    }
                }

                // �I���t���O�t�@�C���`�F�b�N
                if (checkEndFile(executor.getExecutorEndMonitoringFile())) {
                    // �I���t���O�t�@�C������
                    LOGGER.info(LogId.IAL025011);
                    break;
                }

                // �W���u���X�g����̂Ƃ� or ���s�X���b�h�ɋ󂫂������ꍇ �͎w�莞�ԃE�F�C�g
                if (jobList == null || jobList.size() == 0) {
                    // �W���u�̎��s�Ԋu�i�~���b�j
                    if (executor.getJobIntervalTime() >= 0) {
                        try {
                            Thread.sleep(executor.getJobIntervalTime());
                        } catch (InterruptedException e) {
                            // ���荞�ݎ�M�ŏ����I��
                            if (LOGGER.isInfoEnabled()) {
                                LOGGER.info(LogId.IAL025012, e.getMessage());
                            }
                            break;
                        }
                    }
                }

                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(LogId.TAL025010, BatchUtil.getMemoryInfo());
                }
                // �풓�p�Ƀ��[�v
            } while (true);
        } catch (Throwable e) {
            // ��U�ۑ����Ă���
            throwable = e;
        } finally {
            LOGGER.debug(LogId.DAL025028);

            // �I�����Ƀ^�X�N���͂���܂ő҂�
            taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

            LOGGER.debug(LogId.DAL025029);

            taskExecutor.shutdown();

            LOGGER.debug(LogId.DAL025030);

            while (taskExecutor.getActiveCount() != 0) {
                try {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(LogId.DAL025031, taskExecutor
                                .getActiveCount());
                    }

                    Thread.sleep(executor
                            .getExecutorJobTerminateWaitIntervalTime());
                } catch (InterruptedException e) {
                }
            }

            closeRootApplicationContext(ctx);
        }

        if (throwable != null) {
            if (throwable instanceof DataAccessException) {
                throw new RetryableExecuteException(throwable);
            } else if (throwable instanceof TransactionException) {
                throw new RetryableExecuteException(throwable);
            }
            Throwable cause = throwable.getCause();
            if (cause != null && cause instanceof DataAccessException) {
                // ������O��DataAccessException�̏ꍇ�̓X�e�[�^�X�J�n�E�ύX����
                // DB�t�F�C���I�[�o�[���l�����A���g���C��O�Ƃ��ă��C�����\�b�h�ɃX���[����B
                throw new RetryableExecuteException(cause);
            }
        } else {
            LOGGER.info(LogId.IAL025013);

            status = PROCESS_END_STATUS_NORMAL;
        }
        return status;
    }

    /**
     * �o�b�`�������s.
     * @param executor AsyncBatchExecutor
     * @param ctx ApplicationContext
     * @param taskExecutor ThreadPoolTaskExecutor
     * @param batchTaskServantName String
     * @param batchJobListResult BatchJobListResult
     * @return boolean
     */
    protected static boolean executeJob(AsyncBatchExecutor executor,
            ApplicationContext ctx, ThreadPoolTaskExecutor taskExecutor,
            String batchTaskServantName, BatchJobListResult batchJobListResult) {
        boolean status = false;
        BatchServant job = null;

        // �j�����Ă悢ThreadGroup��j��
        destroyThreadGroupsIfPossible();

        if (executor == null || ctx == null || taskExecutor == null
                || batchTaskServantName == null || batchJobListResult == null) {
            return status;
        }

        // �V�X�e��DAO���擾
        SystemDao systemDao = executor.getSystemDao();
        if (systemDao == null) {
            LOGGER.info(LogId.IAL025018);
            return status;
        }

        // PlatformTransactionManager���擾
        PlatformTransactionManager transactionManager = executor
                .getSysTransactionManager();
        if (transactionManager == null) {
            LOGGER.info(LogId.IAL025016);
            return status;
        }

        // �R���e�L�X�g����BatchServant�C���X�^���X�擾
        if (ctx != null) {
            try {
                job = (BatchServant) ctx.getBean(batchTaskServantName,
                        BatchServant.class);
            } catch (Throwable e) {
                LOGGER.error(LogId.EAL025030, e, batchTaskServantName);
                return status;
            }
        }

        if (job == null) {
            LOGGER.error(LogId.EAL025030, batchTaskServantName);
            return status;
        } else {
            // �W���u�X�e�[�^�X�ݒ�i�J�n�j
            boolean st = executor.startBatchStatus(batchJobListResult
                    .getJobSequenceId(), systemDao, transactionManager);
            if (st) {
                // BatchServant�ɃW���u�V�[�P���X�R�[�h��ݒ�
                job.setJobSequenceId(batchJobListResult.getJobSequenceId());

                // �X���b�h�O���[�v�v���t�B�b�N�X�ݒ�
                StringBuilder tgn = new StringBuilder();
                tgn.append(THREAD_GROUP_PREFIX);
                tgn.append(THREAD_GROUP_SEPARATOR);
                tgn.append(threadGroupNo.incrementAndGet());
                taskExecutor.setThreadGroupName(tgn.toString());

                // �X���b�h���v���t�B�b�N�X�ݒ�
                StringBuilder tn = new StringBuilder();
                tn.append(THREAD_NAME_PREFIX);
                tn.append(THREAD_NAME_SEPARATOR);
                tn.append(threadGroupNo.get());
                tn.append(THREAD_NAME_SEPARATOR);
                taskExecutor.setThreadNamePrefix(tn.toString());

                if (LOGGER.isDebugEnabled()) {
                    LOGGER
                            .debug(LogId.DAL025027, tgn.toString(), tn
                                    .toString());
                }

                long executeRetryCount = 0;
                do {
                    try {
                        // �W���u���s
                        taskExecutor.execute(new BatchServantTaskEndTracker(job, taskExecutor.getThreadGroup()));
                        break;
                    } catch (TaskRejectedException tre) {

                        // ���g���C�񐔃`�F�b�N
                        if (executeRetryCount >= executeRetryCountMax) {
                            LOGGER.error(LogId.EAL025047, batchJobListResult
                                    .getJobSequenceId());
                            synchronized (destroyCandidateThreadGroupList) {
                                destroyCandidateThreadGroupList.add(taskExecutor.getThreadGroup());
                            }
                            return status;
                        }

                        // �X���[�v���ԑ҂�
                        try {
                            Thread.sleep(executeRetryInterval);
                        } catch (InterruptedException e1) {
                            // �Ȃɂ����Ȃ�
                        }

                        executeRetryCount++;
                    }
                } while (true);

                // �ԋp�X�e�[�^�X�𐳏��
                status = true;
            } else {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(LogId.IAL025010, batchJobListResult
                            .getJobSequenceId());
                }
                // �W���u�X�e�[�^�X�X�V���s���|�[�����O���[�v�͌p��������B
                status = true;
            }
        }
        return status;
    }

    /**
     * �^�X�N�G�O�[�L���[�^�ɋ󂫂����邩�`�F�b�N
     * @param taskExecutor �^�X�N�G�O�[�L���[�^
     * @return �󂫂̗L��
     */
    protected static boolean checkTaskQueue(ThreadPoolTaskExecutor taskExecutor) {
        int maxPoolSize = 0;
        int activeCount = 0;

        if (taskExecutor != null) {
            activeCount = taskExecutor.getActiveCount();
            maxPoolSize = taskExecutor.getMaxPoolSize();

            // �X���b�h�󂫐���臒l�ȉ��̏ꍇ�̓E�F�C�g
            if ((maxPoolSize - activeCount) <= availableThreadThresholdCount) {
                // �X���[�v���ԑ҂�
                try {
                    Thread.sleep(availableThreadThresholdWait);
                } catch (InterruptedException e1) {
                    // �Ȃɂ����Ȃ�
                }
            }

            // �X���b�h�̋󂫃`�F�b�N
            if (activeCount < maxPoolSize) {
                // �󂫂���
                return true;
            }
            // �X���b�h�̋󂫃`�F�b�N
            if (taskExecutor.getThreadPoolExecutor().getQueue()
                    .remainingCapacity() > 0) {
                // �󂫂���
                return true;
            }
        }

        // �󂫂Ȃ�
        return false;
    }

    /**
     * �I���t���O�t�@�C���`�F�b�N
     * @param endFilePath �I���t���O�t�@�C���p�X
     * @return �I���t���O�t�@�C���`�F�b�N����
     */
    protected static boolean checkEndFile(String endFilePath) {
        if (endFilePath != null && endFilePath.length() != 0) {
            File endFile = new File(endFilePath);
            return endFile.exists();
        }
        return false;
    }

    /**
     * �X���b�h�v�[���^�X�N�G�O�[�L���[�^�̃X�e�[�^�X���f�o�b�O���O�ɏo��
     * @param log Log
     * @param taskExec ThreadPoolTaskExecutor
     */
    protected static void logOutputTaskExecutor(Log log,
            ThreadPoolTaskExecutor taskExec) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LogId.DAL025032, taskExec.getActiveCount(), taskExec
                    .getCorePoolSize(), taskExec.getMaxPoolSize(), taskExec
                    .getPoolSize(), taskExec.getThreadPoolExecutor()
                    .getActiveCount(), taskExec.getThreadPoolExecutor()
                    .getTaskCount(), taskExec.getThreadPoolExecutor()
                    .getQueue().size(), taskExec.getThreadPoolExecutor()
                    .getQueue().remainingCapacity());
        }
    }

    /**
     * ApplicationContext���N���[�Y����.
     * @param context
     */
    protected static void closeRootApplicationContext(ApplicationContext context) {
        if (context instanceof AbstractApplicationContext) {
            AbstractApplicationContext aac = (AbstractApplicationContext) context;
            aac.close();
            aac.destroy();
        }
    }

    /**
     * BatchServant��run���\�b�h�̏I�������m���āA
     * �W���u���s���ɐV���ɍ쐬����Ă���ThreadGroup���A�j�����ɉ����邽�߂̃N���X�B
     */
    protected static class BatchServantTaskEndTracker implements Runnable {

        /**
         * �񓯊��Ŏ��s����BatchServant(�Ϗ���)�B
         */
        private BatchServant job = null;

        /**
         * �W���u���s���ɐV���ɍ쐬����Ă���ThreadGroup�B
         */
        private ThreadGroup newThreadGroup = null;

        /**
         * �R���X�g���N�^�B
         * �X���b�h�v�[���̃X���b�h���g���܂킳���Ƃ��A
         * run���\�b�h�����s����X���b�h��ThreadGroup�ƁA�W���u���s���ɐV���ɍ쐬����Ă���ThreadGroup�́A
         * ����ł͂Ȃ��A
         * �W���u���s���ɐV���ɍ쐬����Ă���ThreadGroup�̕���j�����ɉ�����K�v�����邽�߁A
         * �W���u���s���ɐV���ɍ쐬����Ă���ThreadGroup�������Ŏ󂯎��B
         * @param job �񓯊��Ŏ��s����BatchServant(�Ϗ���)(null�ȊO)
         * @param newThreadGroup �W���u���s���ɐV���ɍ쐬����Ă���ThreadGroup(null�ȊO)
         */
        public BatchServantTaskEndTracker(BatchServant job, ThreadGroup newThreadGroup) {
            this.job = job;
            this.newThreadGroup = newThreadGroup;
        }

        /**
         * BatchServant��run���\�b�h�̏I�����ɁA
         * �W���u���s���ɐV���ɍ쐬����Ă���ThreadGroup���A�j�����ɉ�����B
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                job.run();
            } finally {
                synchronized (destroyCandidateThreadGroupList) {
                    destroyCandidateThreadGroupList.add(newThreadGroup);
                }
            }
        }
    }

    /**
     * �j�����̊eThreadGroup�̃A�N�e�B�u�X���b�h�����������A
     * �A�N�e�B�u�X���b�h����0��ThreadGroup��destroy���\�b�h�����s����B
     * (�X���b�h�v�[���ɕێ�����Ă���A�C�h���X���b�h���A�A�N�e�B�u�X���b�h���ɃJ�E���g�����B)
     */
    protected static void destroyThreadGroupsIfPossible() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LogId.DAL025054);
            logActiveThreadGroupsInfo();
        }

        synchronized (destroyCandidateThreadGroupList) {
            Iterator<ThreadGroup> it = destroyCandidateThreadGroupList.iterator();
            while (it.hasNext()) {
                ThreadGroup threadGroup = it.next();
                int activeCount = threadGroup.activeCount();
                
                // �������_�ŁA�A�N�e�B�u�X���b�h�����Ȃ��X���b�h�O���[�v�̂�destroy�B
                // �����ŃA�N�e�B�u�X���b�h���c���Ă�����̂́A
                // ���񂱂̃��\�b�h�����s���ꂽ�Ƃ��Ɍ�������B
                if (activeCount == 0) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(LogId.DAL025058, threadGroup.getName(), activeCount);
                    }
                    threadGroup.destroy();
                    it.remove();
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(LogId.DAL025059, threadGroup.getName(), activeCount);
                    }
                }
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(LogId.DAL025055);
            logActiveThreadGroupsInfo();
        }
    }

    /**
     * �A�N�e�B�uThreadGroups�̏����f�o�b�O���O�o�͂���B
     * ThreadGroups��destroy�O��ŁAdestroy�O�Ȃ̂�destroy��Ȃ̂��̏������O�o�͌�ɁA
     * ���̃��\�b�h�����s����邱�Ƃ�z�肵�Ă���B
     * LOGGER.isDebugEnabled()�ɂ�郍�O���x���̔���́A���̃��\�b�h�̌Ăяo�����ōs�����Ƃ�z�肵�Ă���B
     */
    protected static void logActiveThreadGroupsInfo() {
        ThreadGroup currentThreadGroup = Thread.currentThread().getThreadGroup();
        int activeGroupCount = currentThreadGroup.activeGroupCount();
        ThreadGroup[] threadGroups = new ThreadGroup[activeGroupCount + 1];
        int enumerateNum = currentThreadGroup.enumerate(threadGroups);
        while (threadGroups.length <= enumerateNum) {
            activeGroupCount = currentThreadGroup.activeGroupCount();
            threadGroups = new ThreadGroup[activeGroupCount + 1];
            enumerateNum = currentThreadGroup.enumerate(threadGroups);
        }
        int actualThreadGroupCount = 0;
        for (ThreadGroup threadGroup : threadGroups) {
            if (threadGroup == null) {
                continue;
            }
            LOGGER.debug(LogId.DAL025056, actualThreadGroupCount, threadGroup.getName(), threadGroup.getParent().getName());
            actualThreadGroupCount++;
        }
        LOGGER.debug(LogId.DAL025057, actualThreadGroupCount);
    }
}

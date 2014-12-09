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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.batch.executor.vo.BLogicResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.util.JobUtil;
import jp.terasoluna.fw.logger.TLogger;

/**
 * �����o�b�`�G�O�[�L���[�^�B<br>
 * <p>
 * �w��̃W���u�Ɩ������s����
 * </p>
 * @see jp.terasoluna.fw.batch.executor.AbstractBatchExecutor
 */
public class SyncBatchExecutor extends AbstractBatchExecutor {

    /**
     * ���K�[.
     */
    private static final TLogger LOGGER  = TLogger.getLogger(SyncBatchExecutor.class);

    /**
     * �����p�����[�^�̊�{����
     */
    private static final String JOB_ARG_PARAM_BASE = "JobArgNm";

    /**
     * �R���X�g���N�^
     */
    protected SyncBatchExecutor() {
        super();
    }

    /**
     * ���C�����\�b�h.
     * @param args
     */
    public static void main(String[] args) {
        BLogicResult result = new BLogicResult();
        String jobAppCd = null;

        LOGGER.info(LogId.IAL025014);

        // �W���u���R�[�h�f�[�^
        BatchJobData jobRecord = new BatchJobData();

        // ��P��������W���u�V�[�P���X�R�[�h���擾
        if (args.length > 0) {
            jobAppCd = args[0];
        }

        // ��Q���������Q�P�����܂ŁA�W���u�ւ̈������擾
        for (int i = 1; i < args.length && i <= ENV_JOB_ARG_MAX; i++) {
            String arg = args[i];
            if (arg != null && arg.length() != 0) {
                setParam(jobRecord, JOB_ARG_PARAM_BASE, i, arg);
            }
        }

        // �����Ɂu�W���u�Ɩ��R�[�h�v���w�肳��Ă��Ȃ���΁A���ϐ�����擾����
        if (jobAppCd == null || jobAppCd.length() == 0) {
            jobAppCd = JobUtil.getenv(ENV_JOB_APP_CD);
        }
        // �W���u�Ɩ��R�[�h
        jobRecord.setJobAppCd(jobAppCd);

        // �����Ɂu����1�v�`�u����20�v���w�肳��Ă��Ȃ���΁A���ϐ�����擾����
        StringBuilder envName = new StringBuilder();
        for (int i = 1; i <= ENV_JOB_ARG_MAX; i++) {
            String param = getParam(jobRecord, JOB_ARG_PARAM_BASE, i);

            if (param == null || param.length() == 0) {
                envName.setLength(0);
                envName.append(ENV_JOB_ARG_NM);
                envName.append(i);

                param = JobUtil.getenv(envName.toString());

                if (param != null && param.length() != 0) {
                    setParam(jobRecord, JOB_ARG_PARAM_BASE, i, param);
                }
            }
        }

        // �W���u�V�[�P���X�R�[�h
        jobRecord.setJobSequenceId(JobUtil.getenv(ENV_JOB_SEQ_ID));
        // �Ɩ��X�e�[�^�X
        jobRecord.setErrAppStatus(JobUtil.getenv(ENV_BLOGIC_APP_STATUS));
        // �X�e�[�^�X
        jobRecord.setCurAppStatus(JobUtil.getenv(ENV_CUR_APP_STATUS));

        // �o�b�`�������s
        SyncBatchExecutor executor = new SyncBatchExecutor();
        result = executor.executeBatch(jobRecord);

        if(LOGGER.isInfoEnabled()){
            LOGGER.info(LogId.IAL025015,result.getBlogicStatus());
        }

        System.exit(result.getBlogicStatus());
        return;
    }

    /**
     * �p�����[�^���擾����
     * @param obj
     * @param paramName
     * @param i
     * @return
     */
    private static String getParam(Object obj, String paramName, int i) {
        String result = null;

        if (obj != null) {
            Method method = null;
            StringBuilder methodName = new StringBuilder();
            methodName.append("get");
            methodName.append(paramName);
            methodName.append(i);

            try {
                method = obj.getClass().getMethod(methodName.toString());
            } catch (SecurityException e) {
                LOGGER.error(LogId.EAL025014, e);
                return null;
            } catch (NoSuchMethodException e) {
                LOGGER.error(LogId.EAL025015, e);
                return null;
            }

            if (method != null) {
                Object resultObj = null;
                try {
                    resultObj = method.invoke(obj);
                } catch (IllegalArgumentException e) {
                    LOGGER.error(LogId.EAL025032, e);
                    return null;
                } catch (IllegalAccessException e) {
                    LOGGER.error(LogId.EAL025033, e);
                    return null;
                } catch (InvocationTargetException e) {
                    LOGGER.error(LogId.EAL025034, e);
                    return null;
                }

                if (resultObj instanceof String) {
                    result = (String) resultObj;
                }
            }
        }
        return result;
    }

    /**
     * �p�����[�^�ݒ�
     * @param obj �ΏۃI�u�W�F�N�g
     * @param paramName �p�����[�^��
     * @param i
     * @param value �ݒ肷��l
     */
    private static void setParam(Object obj, String paramName, int i,
            String value) {
        if (obj != null) {
            Method method = null;
            StringBuilder methodName = new StringBuilder();
            methodName.append("set");
            methodName.append(paramName);
            methodName.append(i);
            try {
                method = obj.getClass().getMethod(methodName.toString(),
                        String.class);
            } catch (SecurityException e) {
                LOGGER.error(LogId.EAL025014, e);
                return;
            } catch (NoSuchMethodException e) {
                LOGGER.error(LogId.EAL025015, e);
                return;
            }

            if (method != null) {
                try {
                    method.invoke(obj, value);
                } catch (IllegalArgumentException e) {
                    LOGGER.error(LogId.EAL025032, e);
                    return;
                } catch (IllegalAccessException e) {
                    LOGGER.error(LogId.EAL025033, e);
                    return;
                } catch (InvocationTargetException e) {
                    LOGGER.error(LogId.EAL025034, e);
                    return;
                }
            }
        }
    }
}

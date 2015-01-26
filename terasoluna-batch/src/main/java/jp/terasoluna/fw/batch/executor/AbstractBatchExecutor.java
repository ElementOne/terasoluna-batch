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

import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jp.terasoluna.fw.batch.annotation.JobComponent;
import jp.terasoluna.fw.batch.annotation.util.GenericBeanFactoryAccessorEx;
import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.batch.exception.handler.ExceptionHandler;
import jp.terasoluna.fw.batch.executor.dao.SystemDao;
import jp.terasoluna.fw.batch.executor.vo.BLogicResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.message.MessageAccessor;
import jp.terasoluna.fw.batch.util.MessageUtil;
import jp.terasoluna.fw.logger.TLogger;
import jp.terasoluna.fw.util.PropertyUtil;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * �����o�b�`�G�O�[�L���[�^���ۃN���X�B<br>
 * <br>
 * �����W���u�N���p�̃o�b�`�G�O�[�L���[�^�B
 * @see jp.terasoluna.fw.batch.executor.BatchExecutor
 * @see jp.terasoluna.fw.batch.executor.SyncBatchExecutor
 */
public abstract class AbstractBatchExecutor implements BatchExecutor {

    /**
     * ���O.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(AbstractBatchExecutor.class);

    /**
     * ���ϐ��F�W���u�V�[�P���X�R�[�h.
     */
    protected static final String ENV_JOB_SEQ_ID = "JOB_SEQ_ID";

    /**
     * ���ϐ��F�W���u�Ɩ��R�[�h.
     */
    protected static final String ENV_JOB_APP_CD = "JOB_APP_CD";

    /**
     * ���ϐ��Fbean��`�t�@�C���p�X.
     */
    protected static final String ENV_JOB_BEAN_DEFINITION_PATH = "JOB_BEAN_DEFINITION_PATH";

    /**
     * ���ϐ��F�����̍ő��.
     */
    protected static final int ENV_JOB_ARG_MAX = 20;

    /**
     * ���ϐ��F����.
     */
    protected static final String ENV_JOB_ARG_NM = "JOB_ARG_NM";

    /**
     * ���ϐ��F����1.
     */
    protected static final String ENV_JOB_ARG_NM1 = "JOB_ARG_NM1";

    /**
     * ���ϐ��F����2.
     */
    protected static final String ENV_JOB_ARG_NM2 = "JOB_ARG_NM2";

    /**
     * ���ϐ��F����3.
     */
    protected static final String ENV_JOB_ARG_NM3 = "JOB_ARG_NM3";

    /**
     * ���ϐ��F����4.
     */
    protected static final String ENV_JOB_ARG_NM4 = "JOB_ARG_NM4";

    /**
     * ���ϐ��F����5.
     */
    protected static final String ENV_JOB_ARG_NM5 = "JOB_ARG_NM5";

    /**
     * ���ϐ��F����6.
     */
    protected static final String ENV_JOB_ARG_NM6 = "JOB_ARG_NM6";

    /**
     * ���ϐ��F����7.
     */
    protected static final String ENV_JOB_ARG_NM7 = "JOB_ARG_NM7";

    /**
     * ���ϐ��F����8.
     */
    protected static final String ENV_JOB_ARG_NM8 = "JOB_ARG_NM8";

    /**
     * ���ϐ��F����9.
     */
    protected static final String ENV_JOB_ARG_NM9 = "JOB_ARG_NM9";

    /**
     * ���ϐ��F����10.
     */
    protected static final String ENV_JOB_ARG_NM10 = "JOB_ARG_NM10";

    /**
     * ���ϐ��F����11.
     */
    protected static final String ENV_JOB_ARG_NM11 = "JOB_ARG_NM11";

    /**
     * ���ϐ��F����12.
     */
    protected static final String ENV_JOB_ARG_NM12 = "JOB_ARG_NM12";

    /**
     * ���ϐ��F����13.
     */
    protected static final String ENV_JOB_ARG_NM13 = "JOB_ARG_NM13";

    /**
     * ���ϐ��F����14.
     */
    protected static final String ENV_JOB_ARG_NM14 = "JOB_ARG_NM14";

    /**
     * ���ϐ��F����15.
     */
    protected static final String ENV_JOB_ARG_NM15 = "JOB_ARG_NM15";

    /**
     * ���ϐ��F����16.
     */
    protected static final String ENV_JOB_ARG_NM16 = "JOB_ARG_NM16";

    /**
     * ���ϐ��F����17.
     */
    protected static final String ENV_JOB_ARG_NM17 = "JOB_ARG_NM17";

    /**
     * ���ϐ��F����18.
     */
    protected static final String ENV_JOB_ARG_NM18 = "JOB_ARG_NM18";

    /**
     * ���ϐ��F����19.
     */
    protected static final String ENV_JOB_ARG_NM19 = "JOB_ARG_NM19";

    /**
     * ���ϐ��F����20.
     */
    protected static final String ENV_JOB_ARG_NM20 = "JOB_ARG_NM20";

    /**
     * ���ϐ��F�Ɩ��X�e�[�^�X.
     */
    protected static final String ENV_BLOGIC_APP_STATUS = "BLOGIC_APP_STATUS";

    /**
     * ���ϐ��F�X�e�[�^�X.
     */
    protected static final String ENV_CUR_APP_STATUS = "CUR_APP_STATUS";

    /**
     * �V�X�e���pDAO��`�i�X�e�[�^�X�Q�ƁE�X�V�p�j�擾�p�L�[.
     */
    protected static final String SYSTEM_DATASOURCE_DAO = "systemDataSource.systemDao";

    /**
     * �V�X�e���ptransactionManager��`�i�X�e�[�^�X�Q�ƁE�X�V�p�j�擾�p�L�[.
     */
    protected static final String SYSTEM_DATASOURCE_TRANSACTION_MANAGER = "systemDataSource.transactionManager";

    /**
     * �Ǘ��pBean��`�t�@�C����z�u����N���X�p�X.
     */
    protected static final String BEAN_DEFINITION_ADMIN_CLASSPATH_KEY = "beanDefinition.admin.classpath";

    /**
     * �Ǘ��pBean��`�i��{���j
     */
    protected static final String BEAN_DEFINITION_DEFAULT = "beanDefinition.admin.default";

    /**
     * �Ǘ��pBean��`�i�f�[�^�\�[�X���j
     */
    protected static final String BEAN_DEFINITION_DATASOURCE = "beanDefinition.admin.dataSource";

    /**
     * �Ɩ��pBean��`�t�@�C����z�u����N���X�p�X.
     */
    protected static final String BEAN_DEFINITION_BUSINESS_CLASSPATH_KEY = "beanDefinition.business.classpath";

    /**
     * Bean��`�t�@�C����.
     */
    protected static final String PROPERTY_BEAN_FILENAME_SUFFIX = ".xml";

    /**
     * ���b�Z�[�W�\�[�X�A�N�Z�T��Bean���擾�L�[.
     */
    protected static final String BEAN_MESSAGE_ACCESSOR_DEFAULT = "messageAccessor.default";

    /**
     * JobComponent�A�m�e�[�V�����L�����t���O�擾�L�[.
     */
    protected static final String ENABLE_JOBCOMPONENT_ANNOTATION = "enableJobComponentAnnotation";

    /**
     * BLogic��Bean���ɕt�^����ڔ���.
     */
    protected static final String DEFAULT_BLOGIC_BEAN_NAME_SUFFIX = "BLogic";

    /**
     * ��O�n���h����Bean���ɕt�^����ڔ���.
     */
    protected static final String DEFAULT_BLOGIC_EXCEPTION_HANDLER_BEAN_NAME_SUFFIX = "ExceptionHandler";

    /**
     * �f�t�H���g�̗�O�n���h����Bean��.
     */
    protected static final String DEFAULT_BLOGIC_EXCEPTION_HANDLER_BEAN_NAME = "defaultExceptionHandler";

    /**
     * �A�v���P�[�V�����R���e�L�X�g�N���X��.
     */
    protected static final String APPLICATION_CONTEXT = "org.springframework.context.support.ClassPathXmlApplicationContext";

    /**
     * �u��������ړ���
     */
    protected static final String REPLACE_STRING_PREFIX = "\\$\\{";

    /**
     * �u��������ڔ���
     */
    protected static final String REPLACE_STRING_SUFFIX = "\\}";

    /**
     * �u��������F�W���u�Ɩ��R�[�h
     */
    protected static final String REPLACE_STRING_JOB_APP_CD = "jobAppCd";

    /**
     * �u��������F�W���u�Ɩ��R�[�h�i�啶���j
     */
    protected static final String REPLACE_STRING_JOB_APP_CD_UPPER = "jobAppCdUpper";

    /**
     * �u��������F�W���u�Ɩ��R�[�h�i�������j
     */
    protected static final String REPLACE_STRING_JOB_APP_CD_LOWER = "jobAppCdLower";

    /**
     * �u��������F����.
     */
    protected static final String REPLACE_STRING_JOB_ARG = "jobArg";

    /**
     * �u��������F�����̍ő��.
     */
    protected static final int REPLACE_STRING_JOB_ARG_MAX = 20;

    /**
     * �o�b�`�����̐�.
     */
    protected static final int ARGUMENT_COUNT = 20;

    /**
     * �o�b�`�����̃t�B�[���h��.
     */
    protected static final String FIELD_JOB_ARG = "JobArgNm";

    /**
     * �N���X���[�_.
     */
    protected static ClassLoader cl = null;

    /**
     * �V�X�e���pDAO��`�i�X�e�[�^�X�Q�ƁE�X�V�p�j.
     */
    protected SystemDao systemDao = null;

    /**
     * �V�X�e���ptransactionManager��`�i�X�e�[�^�X�Q�ƁE�X�V�p�j.
     */
    protected PlatformTransactionManager sysTransactionManager = null;

    /**
     * �V�X�e���p�A�v���P�[�V�����R���e�L�X�g.
     */
    protected ApplicationContext defaultApplicationContext = null;

    /**
     * JobComponent�A�m�e�[�V�����L�����t���O
     */
    protected boolean enableJobComponentAnnotation = false;

    /**
     * It's not a good idea to put code that can fail in a class initializer, but for sake of argument, here's how you configure
     * an SQL Map.
     */
    static {
        // �N���X���[�_�擾�i�J�����g�X���b�h�̃R���e�L�X�g�N���X���[�_�j
        cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            LOGGER.error(LogId.EAL025002);
        }
    }

    /**
     * �R���X�g���N�^
     */
    protected AbstractBatchExecutor() {
        super();
        init();
    }

    /**
     * ������
     */
    protected void init() {
        // �V�X�e������AppContextName������
        initDefaultAppContext();

        // �V�X�e������DAO������
        initSystemDatasourceDao();

        // �G���[���b�Z�[�W�̏�����
        initDefaultErrorMessage();

        // JobComponent�A�m�e�[�V�����L�����t���O�擾
        String enableJobComponentAnnotationStr = PropertyUtil
                .getProperty(ENABLE_JOBCOMPONENT_ANNOTATION);
        if (enableJobComponentAnnotationStr != null
                && enableJobComponentAnnotationStr.length() != 0) {
            this.enableJobComponentAnnotation = Boolean
                    .parseBoolean(enableJobComponentAnnotationStr);
        }
    }

    /**
     * �Ǘ��p�ɗp������ApplicationContext������������.
     */
    protected void initDefaultAppContext() {
        // �V�X�e���A�v���P�[�V�����R���e�L�X�g�擾
        String defaultAppContextName = getDefaultBeanFileName();
        if (defaultAppContextName == null || "".equals(defaultAppContextName)) {
            LOGGER.error(LogId.EAL025003);
            return;
        }
        defaultApplicationContext = getApplicationContext(defaultAppContextName);
        if (defaultApplicationContext == null) {
            LOGGER.error(LogId.EAL025004, defaultAppContextName);
            return;
        }
    }

    /**
     * �V�X�e�����ʂŗp������DAO��Bean��`�t�@�C������擾����.
     */
    protected void initSystemDatasourceDao() {
        // AbstractJobBatchExecutor�Ɉړ�
    }

    /**
     * �G���[���b�Z�[�W�̏�����.
     */
    protected void initDefaultErrorMessage() {
        if (defaultApplicationContext == null) {
            return;
        }

        // ���b�Z�[�W�\�[�X�A�N�Z�T��Bean���擾
        String value = PropertyUtil.getProperty(BEAN_MESSAGE_ACCESSOR_DEFAULT);

        if (value == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(LogId.WAL025001, value, getThreadMessage());
            }
            return;
        }

        // ���b�Z�[�W�\�[�X�A�N�Z�T�擾.
        if (defaultApplicationContext.containsBean(value)) {
            MessageAccessor messageAccessor = null;
            try {
                messageAccessor = defaultApplicationContext
                        .getBean(value, MessageAccessor.class);
            } catch (Throwable e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(LogId.WAL025001, value, getThreadMessage());
                }
            }
            if (messageAccessor != null) {
                MessageUtil.setMessageAccessor(messageAccessor);
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(LogId.TAL025001, getThreadMessage());
                }
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(LogId.TAL025009, getThreadMessage());
                }
            }
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(LogId.WAL025001, value, getThreadMessage());
            }
        }
    }

    /**
     * �X���b�h�O���[�v�ƃX���b�h����Ԃ����\�b�h.
     * @return String
     */
    protected String getThreadMessage() {
        StringBuilder sb = new StringBuilder();
        Thread ct = Thread.currentThread();

        if (ct != null && ct.getThreadGroup() != null) {
            sb.append(" tg:[");
            sb.append(ct.getThreadGroup().getName());
            sb.append("]");
        }
        if (ct != null) {
            sb.append(" t:[");
            sb.append(ct.getName());
            sb.append("]");
        }

        return sb.toString();
    }

    /**
     * �W���u�pApplicationContext������������.
     * @param jobAppCd String
     * @param jobRecord BatchJobData
     * @return ApplicationContext
     */
    protected ApplicationContext initJobAppContext(String jobAppCd,
            BatchJobData jobRecord) {
        ApplicationContext context = null;
        String beanFileName = null;

        beanFileName = getBeanFileName(jobAppCd, jobRecord);
        LOGGER.debug(LogId.DAL025018, beanFileName);

        if (beanFileName != null && 0 < beanFileName.length()) {
            context = getApplicationContext(beanFileName);

            if (context == null) {
                LOGGER.error(LogId.EAL025006, beanFileName);
            }

            LOGGER.debug(LogId.DAL025019);
        }

        return context;
    }

    /**
     * ApplicationContext���N���[�Y����.
     * @param context
     */
    protected void closeApplicationContext(ApplicationContext context) {
        MessageUtil.removeMessageAccessor();

        if (context instanceof AbstractApplicationContext) {
            AbstractApplicationContext aac = (AbstractApplicationContext) context;
            aac.close();
            aac.destroy();
        }
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.executor.BatchExecutor#executeBatch(jp. terasoluna.fw.batch.executor.vo.BatchJobData)
     */
    public BLogicResult executeBatch(BatchJobData jobRecord) {
        BLogicResult result = new BLogicResult();

        // �o�b�`�W���u���R�[�h�f�[�^��BLogicParam�ɕϊ�����
        BLogicParam param = convertBLogicParam(jobRecord);
        if (param == null) {
            // �ُ�I��
            return result;
        }

        LOGGER.debug(LogId.DAL025044, param);

        // �W���u�Ɩ��R�[�h�i�������j����N���X�����擾
        String jobAppCd = jobRecord.getJobAppCd();

        // BLogic��Bean���擾
        String blogicBeanName = getBlogicBeanName(jobAppCd);
        if ((blogicBeanName == null || blogicBeanName.length() == 0)) {
            LOGGER.error(LogId.EAL025007, jobRecord.getJobAppCd());
            return result;
        }

        // ��O�n���h����Bean���擾
        String exceptionHandlerBeanName = getExceptionHandlerBeanName(jobAppCd);
        if ((exceptionHandlerBeanName == null || exceptionHandlerBeanName
                .length() == 0)) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(LogId.EAL025007, jobRecord.getJobAppCd());
            }
            return result;
        }

        // Bean��`�t�@�C���擾
        ApplicationContext context = initJobAppContext(jobAppCd, jobRecord);
        ThreadGroupApplicationContextHolder.setApplicationContext(context);

        try {
            // �o�b�`���s
            if (blogicBeanName != null && 0 < blogicBeanName.length()) {
                // �N���X
                result = executeBatchClass(blogicBeanName,
                        exceptionHandlerBeanName, param, context);
            }
        } finally {
            ThreadGroupApplicationContextHolder.removeApplicationContext();
            // �W���u�pApplicationContext���N���[�Y����
            closeApplicationContext(context);
        }

        return result;
    }

    /**
     * �o�b�`�W���u���R�[�h�f�[�^��BLogicParam�ɕϊ�����
     * @param jobRecord BatchJobData
     * @return BLogicParam
     */
    protected BLogicParam convertBLogicParam(BatchJobData jobRecord) {
        BLogicParam param = new BLogicParam();
        param.setJobSequenceId(jobRecord.getJobSequenceId());
        param.setJobAppCd(jobRecord.getJobAppCd());

        // �����R�s�[
        boolean ret = argumentCopy(jobRecord, param, FIELD_JOB_ARG);
        if (!ret) {
            // �ُ�I��
            return null;
        }
        return param;
    }

    /**
     * �Ǘ��pBean��`�i��{���j�t�@�C�������擾����B
     * @return �Ǘ��pBean��`�i��{���j�t�@�C����
     */
    protected static String getDefaultBeanFileName() {
        StringBuilder str = new StringBuilder();
        String classpath = PropertyUtil
                .getProperty(BEAN_DEFINITION_ADMIN_CLASSPATH_KEY);
        String beanFileName = PropertyUtil.getProperty(BEAN_DEFINITION_DEFAULT);
        str.append(classpath == null ? "" : classpath);
        str.append(beanFileName == null ? "" : beanFileName);
        LOGGER.debug(LogId.DAL025020, str);
        return str.toString();
    }

    /**
     * �Ǘ��pBean��`�i�f�[�^�\�[�X���j�t�@�C�������擾����B
     * @return �Ǘ��pBean��`�i�f�[�^�\�[�X���j�t�@�C����
     */
    protected static String getDataSourceBeanFileName() {
        StringBuilder str = new StringBuilder();
        String classpath = PropertyUtil
                .getProperty(BEAN_DEFINITION_ADMIN_CLASSPATH_KEY);
        String beanFileName = PropertyUtil
                .getProperty(BEAN_DEFINITION_DATASOURCE);
        str.append(classpath == null ? "" : classpath);
        str.append(beanFileName == null ? "" : beanFileName);
        LOGGER.debug(LogId.DAL025020, str);
        return str.toString();
    }

    /**
     * <h6>�o�b�`�N���X���s.</h6>
     * @param blogicBeanName �r�W�l�X���W�b�NBean��
     * @param exceptionHandlerBeanName ��O�n���h��Bean��
     * @param param �r�W�l�X���W�b�N�p�����[�^
     * @param context �R���e�L�X�g
     * @return ���s�X�e�[�^�X
     */
    protected BLogicResult executeBatchClass(String blogicBeanName,
            String exceptionHandlerBeanName, BLogicParam param,
            ApplicationContext context) {
        BLogicResult result = new BLogicResult();
        BLogic blogic = null;
        ExceptionHandler exceptionHandler = null;
        String findBlogicBeanName = null;

        // ApplicationContext���擾�ł��Ȃ������ꍇ
        if (context == null) {
            LOGGER.error(LogId.EAL025008);
            return result;
        }

        // �A�m�e�[�V����������
        if (this.enableJobComponentAnnotation) {
            GenericBeanFactoryAccessorEx gbfa = new GenericBeanFactoryAccessorEx(
                    context);
            Map<String, Object> jobMap = gbfa
                    .getBeansWithAnnotation(JobComponent.class);
            if (param != null) {
                Set<Entry<String, Object>> jobEs = jobMap.entrySet();
                for (Entry<String, Object> jobEnt : jobEs) {
                    Object jobObj = jobEnt.getValue();
                    if (jobObj != null) {
                        JobComponent jobComp = jobObj.getClass().getAnnotation(
                                JobComponent.class);
                        if (jobComp != null
                                && param.getJobAppCd().equals(jobComp.jobId())) {
                            findBlogicBeanName = jobEnt.getKey();
                            break;
                        }
                    }
                }
            }
        }

        if (findBlogicBeanName == null) {
            // �r�W�l�X���W�b�N��Bean�����݂��邩�m�F
            if (context.containsBean(blogicBeanName)) {
                findBlogicBeanName = blogicBeanName;
            } else if (context.containsBean(Introspector
                    .decapitalize(blogicBeanName))) {
                findBlogicBeanName = Introspector.decapitalize(blogicBeanName);
            }
        }

        // �r�W�l�X���W�b�N�̃C���X�^���X��Bean��`����擾
        if (findBlogicBeanName != null) {
            try {
                blogic = (BLogic) context.getBean(findBlogicBeanName,
                        BLogic.class);
            } catch (Throwable e) {
                LOGGER.error(LogId.EAL025009, blogicBeanName);
                return result;
            }
        }

        if (blogic == null) {
            LOGGER.error(LogId.EAL025009, blogicBeanName);
            return result;
        }

        // ��O�n���h����Bean�����݂��邩�m�F
        String findExceptionHandlerBeanName = null;
        if (context.containsBean(exceptionHandlerBeanName)) {
            findExceptionHandlerBeanName = exceptionHandlerBeanName;
        } else if (context.containsBean(Introspector
                .decapitalize(exceptionHandlerBeanName))) {
            findExceptionHandlerBeanName = Introspector
                    .decapitalize(exceptionHandlerBeanName);
        }

        // ��O�n���h���̃C���X�^���X��Bean��`����擾
        if (findExceptionHandlerBeanName != null) {
            try {
                exceptionHandler = (ExceptionHandler) context.getBean(
                        findExceptionHandlerBeanName, ExceptionHandler.class);
            } catch (Throwable e) {
                LOGGER.trace(LogId.TAL025002, e, exceptionHandlerBeanName);
                // ��O�n���h���͌�����Ȃ��Ă����s
            }
        }

        // �Ɩ��ʗ�O�n���h�����Ȃ��ꍇ�͋��ʗ�O�n���h�����擾����
        if (exceptionHandler == null) {
            // �f�t�H���g�̗�O�n���h���̃C���X�^���X��Bean��`����擾
            if (context.containsBean(getDefaultExceptionHandlerBeanName())) {
                try {
                    exceptionHandler = (ExceptionHandler) context.getBean(
                            getDefaultExceptionHandlerBeanName(),
                            ExceptionHandler.class);
                } catch (Throwable e) {
                    LOGGER.trace(LogId.TAL025002, e, exceptionHandlerBeanName);
                    // ��O�n���h���͌�����Ȃ��Ă����s
                }
            }
        }

        try {
            // BLogic#execute���\�b�h���s
            int blogicStatus = blogic.execute(param);

            result.setBlogicStatus(blogicStatus);

        } catch (Throwable e) {
            // ��O�n���h�������݂���ꍇ
            if (exceptionHandler != null) {
                // ��O�������s��
                result.setBlogicStatus(exceptionHandler
                        .handleThrowableException(e));
            }
        }

        return result;
    }

    /**
     * <h6>�����t�B�[���h(1�`20)�R�s�[.</h6>
     * @param from �R�s�[���C���X�^���X
     * @param to �R�s�[��C���X�^���X
     * @param field �t�B�[���h��
     * @return �R�s�[������������true
     */
    protected boolean argumentCopy(Object from, Object to, String field) {
        for (int i = 1; i <= ARGUMENT_COUNT; i++) {
            // getter���\�b�h������
            StringBuilder getterName = new StringBuilder();
            getterName.append("get");
            getterName.append(field);
            getterName.append(i);

            // getter���s
            String argument = (String) getMethod(from, getterName.toString());

            if (argument != null) {
                // setter���\�b�h������
                StringBuilder setterName = new StringBuilder();
                setterName.append("set");
                setterName.append(field);
                setterName.append(i);

                // setter���s
                boolean ret = setMethod(to, setterName.toString(), argument);
                if (!ret) {
                    // �ُ�I��
                    return ret;
                }
            }
        }
        return true;
    }

    /**
     * <h6>�p�����[�^�ݒ�.</h6>
     * @param obj �ΏۃC���X�^���X
     * @param methodName ���\�b�h��
     * @param argument ����
     * @return ���\�b�h�����s�ł����true
     */
    protected boolean setMethod(Object obj, String methodName, String argument) {

        if (obj == null) {
            LOGGER.error(LogId.EAL025010);
            return false;
        }

        try {
            // Bean�Ƀp�����[�^�ݒ�
            Method method = obj.getClass().getMethod(methodName,
                    new Class[] { String.class });
            method.invoke(obj, new Object[] { argument });
        } catch (SecurityException e) {
            LOGGER.error(LogId.EAL025011, e);
            return false;
        } catch (NoSuchMethodException e) {
            LOGGER.error(LogId.EAL025011, e);
            return false;
        } catch (IllegalArgumentException e) {
            LOGGER.error(LogId.EAL025011, e);
            return false;
        } catch (InvocationTargetException e) {
            LOGGER.error(LogId.EAL025011, e);
            return false;
        } catch (IllegalAccessException e) {
            LOGGER.error(LogId.EAL025011, e);
            return false;
        }

        return true;
    }

    /**
     * <h6>�p�����[�^�ݒ�.</h6>
     * @param obj �ΏۃC���X�^���X
     * @param methodName ���\�b�h��
     * @return �p�����[�^���ݒ肳�ꂽ�I�u�W�F�N�g
     */
    protected Object getMethod(Object obj, String methodName) {
        Method method = null;
        Object result = null;

        if (obj == null) {
            LOGGER.error(LogId.EAL025010);
            return null;
        }

        try {
            // Bean����p�����[�^�擾
            method = obj.getClass().getMethod(methodName, new Class[] {});
            result = method.invoke(obj, new Object[] {});
        } catch (SecurityException e) {
            LOGGER.error(LogId.EAL025012, e);
            return null;
        } catch (NoSuchMethodException e) {
            LOGGER.error(LogId.EAL025012, e);
            return null;
        } catch (IllegalArgumentException e) {
            LOGGER.error(LogId.EAL025012, e);
            return null;
        } catch (IllegalAccessException e) {
            LOGGER.error(LogId.EAL025012, e);
            return null;
        } catch (InvocationTargetException e) {
            LOGGER.error(LogId.EAL025012, e);
            return null;
        }
        return result;
    }

    /**
     * <h6>�A�v���P�[�V�����R���e�L�X�g�擾.</h6>
     * @param batchBeanFileName Bean��`�t�@�C����
     * @return �A�v���P�[�V�����R���e�L�X�g
     */
    protected static ApplicationContext getApplicationContext(
            String... batchBeanFileName) {
        ApplicationContext ctx = null;
        Class<?> clazz = null;
        Constructor<?> constructor = null;
        try {
            // �N���X�ǂݍ���
            clazz = cl.loadClass(APPLICATION_CONTEXT);
        } catch (ClassNotFoundException e) {
            LOGGER.error(LogId.EAL025013, e);
            return null;
        }
        try {
            // �R���X�g���N�^�擾
            Class<?>[] arrClass = new Class[] { String[].class };
            constructor = clazz.getConstructor(arrClass);
        } catch (SecurityException e) {
            LOGGER.error(LogId.EAL025014, e);
            return null;
        } catch (NoSuchMethodException e) {
            LOGGER.error(LogId.EAL025015, e);
            return null;
        }
        try {
            Object[] array = new Object[] { (Object[]) batchBeanFileName };
            // �R���e�L�X�g�̃C���X�^���X����
            ctx = (ApplicationContext) constructor.newInstance(array);
            return ctx;
        } catch (IllegalArgumentException e) {
            // �������Ȃ�
            LOGGER.warn(LogId.WAL025002, e);
        } catch (InstantiationException e) {
            // �������Ȃ�
            LOGGER.warn(LogId.WAL025002, e);
        } catch (IllegalAccessException e) {
            // �������Ȃ�
            LOGGER.warn(LogId.WAL025002, e);
        } catch (InvocationTargetException e) {
            // �������Ȃ�
            LOGGER.warn(LogId.WAL025002, e);
        } catch (RuntimeException e) {
            // �������Ȃ�
            LOGGER.warn(LogId.WAL025002, e);
        }

        return ctx;
    }

    /**
     * <h6>Bean��`�t�@�C�����擾.</h6>
     * @param jobAppCd �W���u�A�v���P�[�V�����R�[�h
     * @param jobRecord BatchJobData
     * @return Bean��`�t�@�C����
     */
    protected String getBeanFileName(String jobAppCd, BatchJobData jobRecord) {
        StringBuilder str = new StringBuilder();

        String classpath = PropertyUtil
                .getProperty(BEAN_DEFINITION_BUSINESS_CLASSPATH_KEY);

        // �u���������u������
        classpath = replaceString(classpath, jobAppCd, jobRecord);

        str.append(classpath == null ? "" : classpath);
        str.append(jobAppCd == null ? "" : jobAppCd);
        str.append(PROPERTY_BEAN_FILENAME_SUFFIX);

        return str.toString();
    }

    /**
     * �u���������u������
     * @param value ���͕�����
     * @param jobAppCd �W���u�A�v���P�[�V�����R�[�h
     * @param jobRecord BatchJobData
     * @return ���ʕ�����
     */
    protected String replaceString(String value, String jobAppCd,
            BatchJobData jobRecord) {
        String result = value;

        if (result != null && jobAppCd != null && result.length() != 0
                && jobAppCd.length() != 0) {
            Map<String, String> kv = new HashMap<String, String>();
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            sb.setLength(0);
            sb.append(REPLACE_STRING_PREFIX);
            sb.append(REPLACE_STRING_JOB_APP_CD);
            sb.append(REPLACE_STRING_SUFFIX);
            kv.put(sb.toString(), jobAppCd);

            sb.setLength(0);
            sb.append(REPLACE_STRING_PREFIX);
            sb.append(REPLACE_STRING_JOB_APP_CD_UPPER);
            sb.append(REPLACE_STRING_SUFFIX);
            kv.put(sb.toString(), jobAppCd.toUpperCase());

            sb.setLength(0);
            sb.append(REPLACE_STRING_PREFIX);
            sb.append(REPLACE_STRING_JOB_APP_CD_LOWER);
            sb.append(REPLACE_STRING_SUFFIX);
            kv.put(sb.toString(), jobAppCd.toLowerCase());

            for (int i = 1; i <= REPLACE_STRING_JOB_ARG_MAX; i++) {
                sb.setLength(0);
                sb.append(REPLACE_STRING_PREFIX);
                sb.append(REPLACE_STRING_JOB_ARG);
                sb.append(i);
                sb.append(REPLACE_STRING_SUFFIX);
                sb2.setLength(0);
                sb2.append("get");
                sb2.append(FIELD_JOB_ARG);
                sb2.append(i);
                kv.put(sb.toString(), (String) getMethod(jobRecord, sb2
                        .toString()));
            }

            for (Entry<String, String> et : kv.entrySet()) {
                result = result.replaceAll(et.getKey(), et.getValue());
            }
        }

        return result;
    }

    /**
     * <h6>���s����BLogic��Bean�����擾����.</h6>
     * @param jobAppCd �W���u�A�v���P�[�V�����R�[�h
     * @return BLogic��Bean��
     */
    protected String getBlogicBeanName(String jobAppCd) {
        StringBuilder str = new StringBuilder();

        if (jobAppCd != null && jobAppCd.length() != 0) {
            str.append(jobAppCd);
            str.append(DEFAULT_BLOGIC_BEAN_NAME_SUFFIX);
        }

        return str.toString();
    }

    /**
     * <h6>��O�n���h����Bean�����擾����.</h6>
     * @param jobAppCd �W���u�A�v���P�[�V�����R�[�h
     * @return ��O�n���h����Bean��
     */
    protected String getExceptionHandlerBeanName(String jobAppCd) {
        StringBuilder str = new StringBuilder();

        if (jobAppCd != null && jobAppCd.length() != 0) {
            str.append(jobAppCd);
            str.append(DEFAULT_BLOGIC_EXCEPTION_HANDLER_BEAN_NAME_SUFFIX);
        }

        return str.toString();
    }

    /**
     * <h6>�f�t�H���g�̗�O�n���h����Bean�����擾����.</h6>
     * @return ��O�n���h����Bean��
     */
    protected String getDefaultExceptionHandlerBeanName() {
        return DEFAULT_BLOGIC_EXCEPTION_HANDLER_BEAN_NAME;
    }

    /**
     * �V�X�e���p�A�v���P�[�V�����R���e�L�X�g�擾.
     * @return the defaultApplicationContext
     */
    protected ApplicationContext getDefaultApplicationContext() {
        return defaultApplicationContext;
    }

    /**
     * �V�X�e���pDAO��`�i�X�e�[�^�X�Q�ƁE�X�V�p�j
     * @return the queryDao
     */
    public SystemDao getSystemDao() {
        return systemDao;
    }

    /**
     * �V�X�e���ptransactionManager��`�i�X�e�[�^�X�Q�ƁE�X�V�p�j
     * @return the sysTransactionManager
     */
    public PlatformTransactionManager getSysTransactionManager() {
        return sysTransactionManager;
    }

}

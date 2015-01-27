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

import jp.terasoluna.fw.collector.AbstractCollectorConfig;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;

import org.springframework.validation.Validator;

/**
 * DaoCollector�ݒ荀��
 */
public class DaoCollectorConfig extends AbstractCollectorConfig {

    /** QueryResultHandleDao */
    protected Object queryResultHandleDao = null;

    /** ���s���郁�\�b�h�� */
    protected String methodName = null;

    /** SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g */
    protected Object bindParams = null;

    /** 1:N�}�b�s���O�g�p�t���O�i�g�p����true�j */
    protected boolean relation1n = false;

    /** DaoCollector�O�㏈�� */
    protected DaoCollectorPrePostProcess daoCollectorPrePostProcess = null;

    /**
     * �R���X�g���N�^
     * @param queryResultHandleDao QueryResultHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    public DaoCollectorConfig(Object queryResultHandleDao, String methodName,
            Object bindParams) {
        this.queryResultHandleDao = queryResultHandleDao;
        this.methodName = methodName;
        this.bindParams = bindParams;
    }

    /**
     * �L���[�T�C�Y��ݒ肷��
     * @param queueSize �L���[�T�C�Y
     * @return DaoCollectorConfig
     */
    public DaoCollectorConfig addQueueSize(int queueSize) {
        this.setQueueSize(queueSize);
        return this;
    }

    /**
     * CollectorExceptionHandler��ݒ肷��
     * @param exceptionHandler CollectorExceptionHandler
     * @return DaoCollectorConfig
     */
    public DaoCollectorConfig addExceptionHandler(
            CollectorExceptionHandler exceptionHandler) {
        this.setExceptionHandler(exceptionHandler);
        return this;
    }

    /**
     * Validator��ݒ肷��
     * @param validator Validator
     * @return DaoCollectorConfig
     */
    public DaoCollectorConfig addValidator(Validator validator) {
        this.setValidator(validator);
        return this;
    }

    /**
     * ValidationErrorHandler��ݒ肷��
     * @param validationErrorHandler ValidationErrorHandler
     * @return DaoCollectorConfig
     */
    public DaoCollectorConfig addValidationErrorHandler(
            ValidationErrorHandler validationErrorHandler) {
        this.setValidationErrorHandler(validationErrorHandler);
        return this;
    }

    /**
     * 1:N�}�b�s���O�g�p�t���O��ݒ肷��
     * @param relation1n 1:N�}�b�s���O�g�p�t���O
     * @return DaoCollectorConfig
     */
    public DaoCollectorConfig addRelation1n(boolean relation1n) {
        this.setRelation1n(relation1n);
        return this;
    }

    /**
     * DaoCollector�O�㏈����ݒ肷��
     * @param daoCollectorPrePostProcess DaoCollector�O�㏈��
     * @return DaoCollectorConfig
     */
    public DaoCollectorConfig addDaoCollectorPrePostProcess(
            DaoCollectorPrePostProcess daoCollectorPrePostProcess) {
        this.setDaoCollectorPrePostProcess(daoCollectorPrePostProcess);
        return this;
    }

    /**
     * �R���X�g���N�^�ŏ��������s����t���O��ݒ肷��
     * @param executeByConstructor �R���X�g���N�^�ŏ��������s����t���O
     * @return DaoCollectorConfig
     */
    public DaoCollectorConfig addExecuteByConstructor(
            boolean executeByConstructor) {
        this.setExecuteByConstructor(executeByConstructor);
        return this;
    }

    /**
     * QueryResultHandleDao���擾����B
     * @return QueryResultHandleDao
     */
    public Object getQueryResultHandleDao() {
        return queryResultHandleDao;
    }

    /**
     * QueryResultHandleDao��ݒ肷��B
     * @param queryResultHandleDao QueryResultHandleDao
     */
    public void setQueryResultHandleDao(Object queryResultHandleDao) {
        this.queryResultHandleDao = queryResultHandleDao;
    }

    /**
     * ���s���郁�\�b�h�����擾����B
     * @return ���s���郁�\�b�h��
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * ���s���郁�\�b�h����ݒ肷��B
     * @param methodName ���s���郁�\�b�h��
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g���擾����B
     * @return SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    public Object getBindParams() {
        return bindParams;
    }

    /**
     * SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g��ݒ肷��B
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    public void setBindParams(Object bindParams) {
        this.bindParams = bindParams;
    }

    /**
     * 1:N�}�b�s���O�g�p�t���O���擾����B
     * @return 1:N�}�b�s���O�g�p�t���O
     */
    public boolean isRelation1n() {
        return relation1n;
    }

    /**
     * 1:N�}�b�s���O�g�p�t���O��ݒ肷��B
     * @param relation1n 1:N�}�b�s���O�g�p�t���O
     */
    public void setRelation1n(boolean relation1n) {
        this.relation1n = relation1n;
    }

    /**
     * DaoCollector�O�㏈�����擾����B
     * @return DaoCollector�O����
     */
    public DaoCollectorPrePostProcess getDaoCollectorPrePostProcess() {
        return daoCollectorPrePostProcess;
    }

    /**
     * DaoCollector�O�㏈����ݒ肷��B
     * @param daoCollectorPrePostProcess DaoCollector�O�㏈��
     */
    public void setDaoCollectorPrePostProcess(
            DaoCollectorPrePostProcess daoCollectorPrePostProcess) {
        this.daoCollectorPrePostProcess = daoCollectorPrePostProcess;
    }
}

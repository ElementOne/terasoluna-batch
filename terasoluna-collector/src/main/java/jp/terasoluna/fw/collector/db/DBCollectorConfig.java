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
import jp.terasoluna.fw.dao.QueryRowHandleDAO;

import org.springframework.validation.Validator;

/**
 * DBCollector�ݒ荀��
 */
public class DBCollectorConfig extends AbstractCollectorConfig {

    /** QueryRowHandleDAO */
    protected QueryRowHandleDAO queryRowHandleDAO = null;

    /** ���s����SQL��ID */
    protected String sqlID = null;

    /** SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g */
    protected Object bindParams = null;

    /** 1:N�}�b�s���O�g�p�t���O�i�g�p����true�j */
    protected boolean relation1n = false;

    /** DBCollector�O�㏈�� */
    protected DBCollectorPrePostProcess dbCollectorPrePostProcess = null;

    /**
     * �R���X�g���N�^
     * @param queryRowHandleDAO QueryRowHandleDAO�C���X�^���X
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    public DBCollectorConfig(QueryRowHandleDAO queryRowHandleDAO, String sqlID,
            Object bindParams) {
        this.queryRowHandleDAO = queryRowHandleDAO;
        this.sqlID = sqlID;
        this.bindParams = bindParams;
    }

    /**
     * �L���[�T�C�Y��ݒ肷��
     * @param queueSize �L���[�T�C�Y
     * @return DBCollectorConfig
     */
    public DBCollectorConfig addQueueSize(int queueSize) {
        this.setQueueSize(queueSize);
        return this;
    }

    /**
     * CollectorExceptionHandler��ݒ肷��
     * @param exceptionHandler CollectorExceptionHandler
     * @return DBCollectorConfig
     */
    public DBCollectorConfig addExceptionHandler(
            CollectorExceptionHandler exceptionHandler) {
        this.setExceptionHandler(exceptionHandler);
        return this;
    }

    /**
     * Validator��ݒ肷��
     * @param validator Validator
     * @return DBCollectorConfig
     */
    public DBCollectorConfig addValidator(Validator validator) {
        this.setValidator(validator);
        return this;
    }

    /**
     * ValidationErrorHandler��ݒ肷��
     * @param validationErrorHandler ValidationErrorHandler
     * @return DBCollectorConfig
     */
    public DBCollectorConfig addValidationErrorHandler(
            ValidationErrorHandler validationErrorHandler) {
        this.setValidationErrorHandler(validationErrorHandler);
        return this;
    }

    /**
     * 1:N�}�b�s���O�g�p�t���O��ݒ肷��
     * @param relation1n 1:N�}�b�s���O�g�p�t���O
     * @return DBCollectorConfig
     */
    public DBCollectorConfig addRelation1n(boolean relation1n) {
        this.setRelation1n(relation1n);
        return this;
    }

    /**
     * DBCollector�O�㏈����ݒ肷��
     * @param dbCollectorPrePostProcess DBCollector�O�㏈��
     * @return DBCollectorConfig
     */
    public DBCollectorConfig addDbCollectorPrePostProcess(
            DBCollectorPrePostProcess dbCollectorPrePostProcess) {
        this.setDbCollectorPrePostProcess(dbCollectorPrePostProcess);
        return this;
    }

    /**
     * �R���X�g���N�^�ŏ��������s����t���O��ݒ肷��
     * @param executeByConstructor �R���X�g���N�^�ŏ��������s����t���O
     * @return DBCollectorConfig
     */
    public DBCollectorConfig addExecuteByConstructor(
            boolean executeByConstructor) {
        this.setExecuteByConstructor(executeByConstructor);
        return this;
    }

    /**
     * QueryRowHandleDAO���擾����B
     * @return QueryRowHandleDAO
     */
    public QueryRowHandleDAO getQueryRowHandleDAO() {
        return queryRowHandleDAO;
    }

    /**
     * QueryRowHandleDAO��ݒ肷��B
     * @param queryRowHandleDAO QueryRowHandleDAO
     */
    public void setQueryRowHandleDAO(QueryRowHandleDAO queryRowHandleDAO) {
        this.queryRowHandleDAO = queryRowHandleDAO;
    }

    /**
     * ���s����SQL��ID���擾����B
     * @return ���s����SQL��ID
     */
    public String getSqlID() {
        return sqlID;
    }

    /**
     * ���s����SQL��ID��ݒ肷��B
     * @param sqlID ���s����SQL��ID
     */
    public void setSqlID(String sqlID) {
        this.sqlID = sqlID;
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
     * DBCollector�O�㏈�����擾����B
     * @return DBCollector�O����
     */
    public DBCollectorPrePostProcess getDbCollectorPrePostProcess() {
        return dbCollectorPrePostProcess;
    }

    /**
     * DBCollector�O�㏈����ݒ肷��B
     * @param dbCollectorPrePostProcess DBCollector�O�㏈��
     */
    public void setDbCollectorPrePostProcess(
            DBCollectorPrePostProcess dbCollectorPrePostProcess) {
        this.dbCollectorPrePostProcess = dbCollectorPrePostProcess;
    }
}

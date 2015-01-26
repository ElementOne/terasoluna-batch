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

import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.validate.ExceptionValidationErrorHandler;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;

import org.springframework.validation.Validator;

/**
 * DaoValidateCollector<br>
 * �Ɨ������ʃX���b�h���N�����AQueryRowHandleDAO��񓯊��Ŏ��s����B
 * @param &ltP&gt
 */
public class DaoValidateCollector<P> extends DaoCollector<P> {

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, Validator validator) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addValidator(validator));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, Validator validator,
            ValidationErrorHandler validationErrorHandler) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param relation1n 1:N�}�b�s���O�g�p����true
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, boolean relation1n,
            Validator validator) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addRelation1n(relation1n).addValidator(validator));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param relation1n 1:N�}�b�s���O�g�p����true
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, boolean relation1n,
            Validator validator, ValidationErrorHandler validationErrorHandler) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addRelation1n(relation1n).addValidator(validator)
                .addValidationErrorHandler(validationErrorHandler));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize, Validator validator) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addValidator(validator));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize,
            Validator validator, ValidationErrorHandler validationErrorHandler) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addValidator(validator)
                .addValidationErrorHandler(validationErrorHandler));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param exceptionHandler ��O�n���h��
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize,
            CollectorExceptionHandler exceptionHandler, Validator validator) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addExceptionHandler(exceptionHandler)
                .addValidator(validator));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param exceptionHandler ��O�n���h��
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize,
            CollectorExceptionHandler exceptionHandler, Validator validator,
            ValidationErrorHandler validationErrorHandler) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addExceptionHandler(exceptionHandler)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param relation1n 1:N�}�b�s���O�g�p����true
     * @param exceptionHandler ��O�n���h��
     * @param daoCollectorPrePostProcess DaoCollector�O�㏈��
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize, boolean relation1n,
            CollectorExceptionHandler exceptionHandler,
            DaoCollectorPrePostProcess daoCollectorPrePostProcess,
            Validator validator) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addRelation1n(relation1n)
                .addExceptionHandler(exceptionHandler)
                .addDaoCollectorPrePostProcess(daoCollectorPrePostProcess)
                .addValidator(validator));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param queryRowHandleDao QueryRowHandleDao�C���X�^���X
     * @param methodName ���s���郁�\�b�h��
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param relation1n 1:N�}�b�s���O�g�p����true
     * @param exceptionHandler ��O�n���h��
     * @param daoCollectorPrePostProcess DaoCollector�O����
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public DaoValidateCollector(Object queryRowHandleDao,
            String methodName, Object bindParams, int queueSize, boolean relation1n,
            CollectorExceptionHandler exceptionHandler,
            DaoCollectorPrePostProcess daoCollectorPrePostProcess,
            Validator validator, ValidationErrorHandler validationErrorHandler) {
        this(new DaoCollectorConfig(queryRowHandleDao, methodName, bindParams)
                .addQueueSize(queueSize).addRelation1n(relation1n)
                .addExceptionHandler(exceptionHandler)
                .addDaoCollectorPrePostProcess(daoCollectorPrePostProcess)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * DaoValidateCollector�R���X�g���N�^<br>
     * @param config DaoCollectorConfig DaoCollector�ݒ荀��
     */
    public DaoValidateCollector(DaoCollectorConfig config) {
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
        this.validator = config.getValidator();
        if (config.getValidator() != null) {
            if (config.getValidationErrorHandler() != null) {
                this.validationErrorHandler = config
                        .getValidationErrorHandler();
            } else {
                this.validationErrorHandler = new ExceptionValidationErrorHandler();
            }
        }
        this.exceptionHandler = config.getExceptionHandler();
        this.daoCollectorPrePostProcess = config.getDaoCollectorPrePostProcess();

        if (config.isExecuteByConstructor()) {
            // ���s�J�n
            execute();
        }
    }

}

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

package jp.terasoluna.fw.collector.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import jp.terasoluna.fw.collector.LogId;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.logger.TLogger;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

/**
 * ���̓`�F�b�N�G���[�n���h���̒��ۃN���X.<br>
 * <p>
 * ���̓`�F�b�N�G���[���������ꍇ�́AINFO���O�ɃG���[�R�[�h���o�͂���B<br>
 * �߂�l�͕K��ValidateStatus.SKIP��Ԃ��B
 * </p>
 */
public abstract class AbstractValidationErrorHandler implements
                                                    ValidationErrorHandler {
    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(AbstractValidationErrorHandler.class);

    /**
     * ���̓`�F�b�N�G���[����.<br>
     */
    protected int errorFieldCount = 0;

    /**
     * �G���[�L���[.<br>
     */
    protected Queue<Errors> errorsQueue = new ConcurrentLinkedQueue<Errors>();

    /**
     * ���O���x��.<br>
     */
    protected ValidationErrorLoglevel logLevel = ValidationErrorLoglevel.INFO;

    /**
     * �R���X�g���N�^.<br>
     */
    public AbstractValidationErrorHandler() {
        super();
    }

    /**
     * �R���X�g���N�^.<br>
     * @param logLevel String ���O���x��
     */
    public AbstractValidationErrorHandler(ValidationErrorLoglevel logLevel) {
        this();
        this.logLevel = logLevel;
    }

    /*
     * (non-Javadoc)
     * @seejp.terasoluna.fw.collector.validate.ValidationErrorHandler#handleValidationError(jp.terasoluna.fw.collector.vo. DataValueObject, org.springframework.validation.Errors)
     */
    public ValidateErrorStatus handleValidationError(
            DataValueObject dataValueObject, Errors errors) {
        errorFieldCount++;

        if (errors != null) {
            errorsQueue.add(errors);
        }

        // ���O�o��
        outputLog(dataValueObject, errors);

        // ValidateStatus��Ԃ�
        return getValidateStatus(dataValueObject, errors);
    }

    /**
     * ���O�o��
     * @param dataValueObject DataValueObject
     * @param errors Errors
     */
    protected void outputLog(DataValueObject dataValueObject, Errors errors) {
        if (ValidationErrorLoglevel.TRACE == this.logLevel
                && LOGGER.isTraceEnabled()) {
            LOGGER.trace(LogId.TAL041001, logEdit(dataValueObject, errors));
        } else if (ValidationErrorLoglevel.DEBUG == this.logLevel
                && LOGGER.isDebugEnabled()) {
            LOGGER.debug(LogId.DAL041001, logEdit(dataValueObject, errors));
        } else if (ValidationErrorLoglevel.INFO == this.logLevel
                && LOGGER.isInfoEnabled()) {
            LOGGER.info(LogId.IAL041001, logEdit(dataValueObject, errors));
        } else if (ValidationErrorLoglevel.WARN == this.logLevel
                && LOGGER.isWarnEnabled()) {
            LOGGER.warn(LogId.WAL041001, logEdit(dataValueObject, errors));
        } else if (ValidationErrorLoglevel.ERROR == this.logLevel
                && LOGGER.isErrorEnabled()) {
            LOGGER.error(LogId.EAL041001, logEdit(dataValueObject, errors));
        } else if (ValidationErrorLoglevel.FATAL == this.logLevel
                && LOGGER.isFatalEnabled()) {
            LOGGER.fatal(LogId.FAL041001, logEdit(dataValueObject, errors));
        } else if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(LogId.TAL041001, logEdit(dataValueObject, errors));
        }
    }

    /**
     * ���O�ҏW.<br>
     * @param dataValueObject DataValueObject
     * @param errors Errors
     * @return ���O
     */
    protected String logEdit(DataValueObject dataValueObject, Errors errors) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fel = getFieldErrorList(errors);

        for (FieldError fe : fel) {
            sb.setLength(0);
            sb.append("ValidationError");
            sb.append(" dataCount:[");
            if (dataValueObject != null) {
                sb.append(dataValueObject.getDataCount());
            }
            sb.append("]");
            sb.append(" code:[");
            sb.append(fe.getCode());
            sb.append("]");
            sb.append(" objectName:[");
            sb.append(fe.getObjectName());
            sb.append("]");
            sb.append(" field:[");
            sb.append(fe.getField());
            sb.append("]");
            sb.append(" rejectedValue:[");
            sb.append(fe.getRejectedValue());
            sb.append("]");
        }
        return sb.toString();
    }

    /**
     * ValidateStatus��Ԃ��B
     * @param dataValueObject DataValueObject
     * @param errors Errors
     * @return ValidateStatus
     */
    abstract protected ValidateErrorStatus getValidateStatus(
            DataValueObject dataValueObject, Errors errors);

    /**
     * Errors����FieldError�̃��X�g���擾����
     * @param errors Errors
     * @return List<FieldError>
     */
    public static List<FieldError> getFieldErrorList(Errors errors) {
        List<FieldError> resultList = new ArrayList<FieldError>();

        if (errors != null) {
            List<?> errs = errors.getAllErrors();
            for (Object errObj : errs) {
                if (errObj instanceof FieldError) {
                    FieldError fe = (FieldError) errObj;
                    resultList.add(fe);
                }
            }
        }

        return resultList;
    }

    /**
     * ���̓`�F�b�N�G���[�������擾����
     * @return int ���̓`�F�b�N�G���[����
     */
    public int getErrorFieldCount() {
        return errorFieldCount;
    }

    /**
     * ���̓`�F�b�N�G���[�̔z����擾����
     * @return Errors[] ���̓`�F�b�N�G���[�̔z��
     */
    public Errors[] getErrors() {
        return errorsQueue.toArray(new Errors[0]);
    }

    /**
     * ���O���x����ݒ肷��.<br>
     * <p>
     * <li>ValidationErrorLoglevel.TRACE</li>
     * <li>ValidationErrorLoglevel.DEBUG</li>
     * <li>ValidationErrorLoglevel.INFO</li>
     * <li>ValidationErrorLoglevel.WARN</li>
     * <li>ValidationErrorLoglevel.ERROR</li>
     * <li>ValidationErrorLoglevel.FATAL</li>
     * </p>
     * @param logLevel ���O���x��
     */
    public void setLogLevel(ValidationErrorLoglevel logLevel) {
        this.logLevel = logLevel;
    }
}

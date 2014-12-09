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

package jp.terasoluna.fw.collector;

import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;

import org.springframework.validation.Validator;

/**
 * AbstractCollector�ݒ荀��
 */
public class AbstractCollectorConfig {
    /** �L���[�T�C�Y */
    protected int queueSize = AbstractCollector.DEFAULT_QUEUE_SIZE;

    /** CollectorExceptionHandler */
    protected CollectorExceptionHandler exceptionHandler = null;

    /** Validator */
    protected Validator validator = null;

    /** ValidationErrorHandler */
    protected ValidationErrorHandler validationErrorHandler = null;

    /** �R���X�g���N�^�ŏ��������s����t���O�itrue:���s����Afalse:���s���Ȃ��j */
    protected boolean executeByConstructor = false;

    /**
     * �L���[�T�C�Y���擾����B
     * @return �L���[�T�C�Y
     */
    public int getQueueSize() {
        return queueSize;
    }

    /**
     * �L���[�T�C�Y��ݒ肷��B
     * @param queueSize �L���[�T�C�Y
     */
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    /**
     * CollectorExceptionHandler���擾����B
     * @return CollectorExceptionHandler
     */
    public CollectorExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    /**
     * CollectorExceptionHandler��ݒ肷��B
     * @param exceptionHandler CollectorExceptionHandler
     */
    public void setExceptionHandler(CollectorExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Validator���擾����B
     * @return Validator
     */
    public Validator getValidator() {
        return validator;
    }

    /**
     * Validator��ݒ肷��B
     * @param validator Validator
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * ValidationErrorHandler���擾����B
     * @return ValidationErrorHandler
     */
    public ValidationErrorHandler getValidationErrorHandler() {
        return validationErrorHandler;
    }

    /**
     * ValidationErrorHandler��ݒ肷��B
     * @param validationErrorHandler ValidationErrorHandler
     */
    public void setValidationErrorHandler(
            ValidationErrorHandler validationErrorHandler) {
        this.validationErrorHandler = validationErrorHandler;
    }

    /**
     * �R���X�g���N�^�ŏ��������s����t���O���擾����B
     * @return �R���X�g���N�^�ŏ��������s����t���O
     */
    public boolean isExecuteByConstructor() {
        return executeByConstructor;
    }

    /**
     * �R���X�g���N�^�ŏ��������s����t���O��ݒ肷��B
     * @param executeByConstructor �R���X�g���N�^�ŏ��������s����t���O
     */
    public void setExecuteByConstructor(boolean executeByConstructor) {
        this.executeByConstructor = executeByConstructor;
    }
}

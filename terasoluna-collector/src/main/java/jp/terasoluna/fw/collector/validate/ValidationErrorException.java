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

import jp.terasoluna.fw.collector.vo.DataValueObject;

import org.springframework.validation.Errors;

public class ValidationErrorException extends RuntimeException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5886358053601538376L;

    /**
     * �L���[�Ƀf�[�^���O���i�[����ۂ̔�
     */
    private DataValueObject dataValueObject = null;

    /**
     * �G���[���
     */
    private Errors errors = null;

    /**
     * �R���X�g���N�^
     */
    public ValidationErrorException() {
        super();
    }

    /**
     * �R���X�g���N�^
     * @param message String
     */
    public ValidationErrorException(String message) {
        super(message);
    }

    /**
     * �R���X�g���N�^
     * @param message String
     * @param cause Throwable
     */
    public ValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * �R���X�g���N�^
     * @param cause Throwable
     */
    public ValidationErrorException(Throwable cause) {
        super(cause);
    }

    /**
     * �R���X�g���N�^
     * @param dataValueObject DataValueObject
     * @param errors Errors
     */
    public ValidationErrorException(DataValueObject dataValueObject,
            Errors errors) {
        this.dataValueObject = dataValueObject;
        this.errors = errors;
    }

    /**
     * �L���[�Ƀf�[�^���O���i�[����ۂ̔����擾����
     * @return DataValueObject
     */
    public DataValueObject getDataValueObject() {
        return dataValueObject;
    }

    /**
     * �G���[�����擾����
     * @return Errors
     */
    public Errors getErrors() {
        return errors;
    }
}

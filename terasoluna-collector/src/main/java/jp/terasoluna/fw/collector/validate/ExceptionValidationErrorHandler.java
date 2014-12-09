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

/**
 * ���̓`�F�b�N�G���[�n���h���̎����N���X.<br>
 * <p>
 * ���̓`�F�b�N�G���[���������ꍇ�́ATRACE���O�ɃG���[�R�[�h���o�͂���B<br>
 * �G���[����ێ�������O���X���[����B
 * </p>
 */
public class ExceptionValidationErrorHandler extends
                                            AbstractValidationErrorHandler {

    /**
     * �R���X�g���N�^
     */
    public ExceptionValidationErrorHandler() {
        super();
        this.logLevel = ValidationErrorLoglevel.TRACE;
    }

    /*
     * (non-Javadoc)
     * @seejp.terasoluna.fw.collector.validate.AbstractValidationErrorHandler#getValidateStatus(jp.terasoluna.fw.collector.vo.
     * DataValueObject, org.springframework.validation.Errors)
     */
    @Override
    protected ValidateErrorStatus getValidateStatus(
            DataValueObject dataValueObject, Errors errors) {
        throw new ValidationErrorException(dataValueObject, errors);
    }
}

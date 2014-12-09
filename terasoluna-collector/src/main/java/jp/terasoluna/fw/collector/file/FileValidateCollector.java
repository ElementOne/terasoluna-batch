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

package jp.terasoluna.fw.collector.file;

import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.validate.ExceptionValidationErrorHandler;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.springframework.validation.Validator;

/**
 * FileValidateCollector.<br>
 * �Ɨ������ʃX���b�h���N�����AFileQueryDAO��񓯊��Ŏ��s����B
 * @param &ltP&gt
 */
public class FileValidateCollector<P> extends FileCollector<P> {

    /**
     * FileValidateCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public FileValidateCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, Validator validator) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addValidator(validator));
    }

    /**
     * FileValidateCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public FileValidateCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, Validator validator,
            ValidationErrorHandler validationErrorHandler) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * FileValidateCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param exceptionHandler ��O�n���h��
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public FileValidateCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, CollectorExceptionHandler exceptionHandler,
            Validator validator) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addExceptionHandler(exceptionHandler).addValidator(validator));
    }

    /**
     * FileValidateCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param exceptionHandler ��O�n���h��
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public FileValidateCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, CollectorExceptionHandler exceptionHandler,
            Validator validator, ValidationErrorHandler validationErrorHandler) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addExceptionHandler(exceptionHandler).addValidator(validator)
                .addValidationErrorHandler(validationErrorHandler));
    }

    /**
     * FileValidateCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param exceptionHandler ��O�n���h��
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     */
    public FileValidateCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, int queueSize,
            CollectorExceptionHandler exceptionHandler, Validator validator) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addQueueSize(queueSize).addExceptionHandler(exceptionHandler)
                .addValidator(validator));
    }

    /**
     * FileValidateCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param exceptionHandler ��O�n���h��
     * @param validator Validator ���̓`�F�b�N���s���o���f�[�^
     * @param validationErrorHandler ValidationErrorHandler ���̓`�F�b�N�G���[���ɍs������
     */
    public FileValidateCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, int queueSize,
            CollectorExceptionHandler exceptionHandler, Validator validator,
            ValidationErrorHandler validationErrorHandler) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addQueueSize(queueSize).addExceptionHandler(exceptionHandler)
                .addValidator(validator).addValidationErrorHandler(
                        validationErrorHandler));
    }

    /**
     * FileValidateCollector�R���X�g���N�^.<br>
     * @param config FileCollectorConfig FileCollector�ݒ荀��
     */
    public FileValidateCollector(FileCollectorConfig<P> config) {
        if (config == null) {
            throw new IllegalArgumentException("The parameter is null.");
        }

        this.fileQueryDAO = config.getFileQueryDAO();
        this.fileName = config.getFileName();
        this.clazz = config.getClazz();
        if (config.getQueueSize() > 0) {
            setQueueSize(config.getQueueSize());
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

        if (config.isExecuteByConstructor()) {
            // ���s�J�n
            execute();
        }
    }
}

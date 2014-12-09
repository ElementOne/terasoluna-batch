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

import jp.terasoluna.fw.collector.AbstractCollectorConfig;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.springframework.validation.Validator;

/**
 * FileCollector�ݒ荀��
 */
public class FileCollectorConfig<P> extends AbstractCollectorConfig {
    /** FileQueryDAO */
    protected FileQueryDAO fileQueryDAO = null;

    /** �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j */
    protected String fileName = null;

    /** 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X */
    protected Class<P> clazz = null;

    /**
     * �R���X�g���N�^
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     */
    public FileCollectorConfig(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz) {
        this.fileQueryDAO = fileQueryDAO;
        this.fileName = fileName;
        this.clazz = clazz;
    }

    /**
     * �L���[�T�C�Y��ݒ肷��
     * @param queueSize �L���[�T�C�Y
     * @return FileCollectorConfig&lt;P&gt;
     */
    public FileCollectorConfig<P> addQueueSize(int queueSize) {
        this.setQueueSize(queueSize);
        return this;
    }

    /**
     * CollectorExceptionHandler��ݒ肷��
     * @param exceptionHandler CollectorExceptionHandler
     * @return FileCollectorConfig&lt;P&gt;
     */
    public FileCollectorConfig<P> addExceptionHandler(
            CollectorExceptionHandler exceptionHandler) {
        this.setExceptionHandler(exceptionHandler);
        return this;
    }

    /**
     * Validator��ݒ肷��
     * @param validator Validator
     * @return FileCollectorConfig&lt;P&gt;
     */
    public FileCollectorConfig<P> addValidator(Validator validator) {
        this.setValidator(validator);
        return this;
    }

    /**
     * ValidationErrorHandler��ݒ肷��
     * @param validationErrorHandler ValidationErrorHandler
     * @return FileCollectorConfig&lt;P&gt;
     */
    public FileCollectorConfig<P> addValidationErrorHandler(
            ValidationErrorHandler validationErrorHandler) {
        this.setValidationErrorHandler(validationErrorHandler);
        return this;
    }

    /**
     * �R���X�g���N�^�ŏ��������s����t���O��ݒ肷��
     * @param executeByConstructor �R���X�g���N�^�ŏ��������s����t���O
     * @return FileCollectorConfig&lt;P&gt;
     */
    public FileCollectorConfig<P> addExecuteByConstructor(
            boolean executeByConstructor) {
        this.setExecuteByConstructor(executeByConstructor);
        return this;
    }

    /**
     * FileQueryDAO
     * @return FileQueryDAO
     */
    public FileQueryDAO getFileQueryDAO() {
        return fileQueryDAO;
    }

    /**
     * FileQueryDAO
     * @param fileQueryDAO FileQueryDAO
     */
    public void setFileQueryDAO(FileQueryDAO fileQueryDAO) {
        this.fileQueryDAO = fileQueryDAO;
    }

    /**
     * �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @return �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @return 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     */
    public Class<P> getClazz() {
        return clazz;
    }

    /**
     * 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     */
    public void setClazz(Class<P> clazz) {
        this.clazz = clazz;
    }
}

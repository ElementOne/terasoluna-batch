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

package jp.terasoluna.fw.collector.vo;

import jp.terasoluna.fw.collector.exception.CollectorExceptionHandlerStatus;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;

/**
 * DataValueObject.<br>
 * �L���[�Ƀf�[�^���O���i�[����ۂ̔��B
 */
public class DataValueObject {
    /** �f�[�^�J�E���g�i�擾�����f�[�^�������ڂ̃f�[�^���������i1����=1�j�j */
    protected long dataCount = -1;

    /** ���s���ʃf�[�^ */
    protected Object value = null;

    /** ����������O */
    protected Throwable throwable = null;

    /** ���̓`�F�b�N�X�e�[�^�X */
    protected ValidateErrorStatus validateStatus = null;

    /** �R���N�^�X�e�[�^�X */
    protected CollectorStatus collectorStatus = null;

    /** ��O�n���h���X�e�[�^�X */
    protected CollectorExceptionHandlerStatus exceptionHandlerStatus = null;

    /**
     * �R���X�g���N�^�i���s���ʃf�[�^�j<br>
     * @param value Object ���s���ʃf�[�^
     */
    public DataValueObject(Object value) {
        this.value = value;
    }

    /**
     * �R���X�g���N�^�i���s���ʃf�[�^�j<br>
     * @param value Object ���s���ʃf�[�^
     * @param dataCount long �f�[�^�J�E���^
     */
    public DataValueObject(Object value, long dataCount) {
        this.value = value;
        this.dataCount = dataCount;
    }

    /**
     * �R���X�g���N�^�i����������O�j<br>
     * @param throwable Throwable ����������O
     */
    public DataValueObject(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * �R���X�g���N�^�i����������O�j<br>
     * @param throwable Throwable ����������O
     * @param dataCount long �f�[�^�J�E���^
     */
    public DataValueObject(Throwable throwable, long dataCount) {
        this.throwable = throwable;
        this.dataCount = dataCount;
    }

    /**
     * �R���X�g���N�^�i���̓`�F�b�N�X�e�[�^�X�j<br>
     * @param validateStatus ValidateStatus ���̓`�F�b�N�X�e�[�^�X
     */
    public DataValueObject(ValidateErrorStatus validateStatus) {
        this.validateStatus = validateStatus;
    }

    /**
     * �R���X�g���N�^�i�R���N�^�X�e�[�^�X�j<br>
     * @param collectorStatus CollectorStatus �R���N�^�X�e�[�^�X
     */
    public DataValueObject(CollectorStatus collectorStatus) {
        this.collectorStatus = collectorStatus;
    }

    /**
     * �f�[�^�J�E���g���擾����.<br>
     * @return long
     */
    public long getDataCount() {
        return dataCount;
    }

    /**
     * ���s���ʃf�[�^<br>
     * @return ���s���ʃf�[�^
     */
    public Object getValue() {
        return value;
    }

    /**
     * ����������O<br>
     * @return ����������O
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * ����������O<br>
     * @param throwable ����������O
     */
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * ���̓`�F�b�N�X�e�[�^�X<br>
     * @return ���̓`�F�b�N�X�e�[�^�X
     */
    public ValidateErrorStatus getValidateStatus() {
        return validateStatus;
    }

    /**
     * �R���N�^�X�e�[�^�X<br>
     * @return �R���N�^�X�e�[�^�X
     */
    public CollectorStatus getCollectorStatus() {
        return collectorStatus;
    }

    /**
     * �R���N�^�X�e�[�^�X<br>
     * @param collectorStatus �R���N�^�X�e�[�^�X
     */
    public void setCollectorStatus(CollectorStatus collectorStatus) {
        this.collectorStatus = collectorStatus;
    }

    /**
     * ��O�n���h���X�e�[�^�X<br>
     * @return ��O�n���h���X�e�[�^�X
     */
    public CollectorExceptionHandlerStatus getExceptionHandlerStatus() {
        return exceptionHandlerStatus;
    }

    /**
     * ��O�n���h���X�e�[�^�X<br>
     * @param exceptionHandlerStatus ��O�n���h���X�e�[�^�X
     */
    public void setExceptionHandlerStatus(
            CollectorExceptionHandlerStatus exceptionHandlerStatus) {
        this.exceptionHandlerStatus = exceptionHandlerStatus;
    }
}

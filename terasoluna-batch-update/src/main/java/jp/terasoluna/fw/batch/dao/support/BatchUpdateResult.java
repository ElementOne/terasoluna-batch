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

package jp.terasoluna.fw.batch.dao.support;

/**
 * �o�b�`�X�V���s���ʃN���X<br>
 */
public class BatchUpdateResult {

    /**
     * �o�b�`�X�V���s�Ɏg�p�����o�b�`�X�V�T�|�[�g
     */
    private BatchUpdateSupport executeBatchUpdateSupport = null;

    /**
     * �o�b�`�X�V���s����.<br>
     */
    private Integer executeBatchResult = null;

    /**
     * �o�b�`�X�V���s���ɔ���������O.<br>
     */
    private Throwable executeBatchException = null;

    /**
     * �R���X�g���N�^
     */
    public BatchUpdateResult() {
    }

    /**
     * �R���X�g���N�^
     * @param executeBatchUpdateSupport BatchUpdateSupport �X�V���s�Ɏg�p�����o�b�`�X�V�T�|�[�g
     * @param executeBatchResult Integer �o�b�`�X�V���s����
     */
    public BatchUpdateResult(BatchUpdateSupport executeBatchUpdateSupport,
            Integer executeBatchResult) {
        this.executeBatchUpdateSupport = executeBatchUpdateSupport;
        this.executeBatchResult = executeBatchResult;
    }

    /**
     * �R���X�g���N�^
     * @param executeBatchUpdateSupport BatchUpdateSupport �X�V���s�Ɏg�p�����o�b�`�X�V�T�|�[�g
     * @param executeBatchException Throwable �o�b�`�X�V���s���ɔ���������O
     */
    public BatchUpdateResult(BatchUpdateSupport executeBatchUpdateSupport,
            Throwable executeBatchException) {
        this.executeBatchUpdateSupport = executeBatchUpdateSupport;
        this.executeBatchException = executeBatchException;
    }

    /**
     * �o�b�`�X�V���s�Ɏg�p�����o�b�`�X�V�T�|�[�g���擾����.<br>
     * @return
     */
    public BatchUpdateSupport getExecuteBatchUpdateSupport() {
        return executeBatchUpdateSupport;
    }

    /**
     * �o�b�`�X�V���s�Ɏg�p�����o�b�`�X�V�T�|�[�g��ݒ肷��.<br>
     * @param executeBatchUpdateSupport
     */
    public void setExecuteBatchUpdateSupport(
            BatchUpdateSupport executeBatchUpdateSupport) {
        this.executeBatchUpdateSupport = executeBatchUpdateSupport;
    }

    /**
     * �o�b�`�X�V���s���ʂ��擾����.<br>
     * @return
     */
    public Integer getExecuteBatchResult() {
        return executeBatchResult;
    }

    /**
     * �o�b�`�X�V���s���ʂ�ݒ肷��.<br>
     * @param executeBatchResult
     */
    public void setExecuteBatchResult(Integer executeBatchResult) {
        this.executeBatchResult = executeBatchResult;
    }

    /**
     * �o�b�`�X�V���s���ɔ���������O���擾����.<br>
     * @return
     */
    public Throwable getExecuteBatchException() {
        return executeBatchException;
    }

    /**
     * �o�b�`�X�V���s���ɔ���������O��ݒ肷��.<br>
     * @param executeBatchException
     */
    public void setExecuteBatchException(Throwable executeBatchException) {
        this.executeBatchException = executeBatchException;
    }

}

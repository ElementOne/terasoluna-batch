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

package jp.terasoluna.fw.batch.executor.concurrent;

import jp.terasoluna.fw.batch.executor.AbstractJobBatchExecutor;
import jp.terasoluna.fw.batch.executor.vo.BLogicResult;

/**
 * �o�b�`�T�[�o���g�����N���X�B<br>
 * <br>
 * �񓯊��o�b�`�G�O�[�L���[�^����Ă΂�A�w�肳�ꂽ�W���u�V�[�P���X�R�[�h����W���u�����s����B
 * @see jp.terasoluna.fw.batch.executor.AbstractJobBatchExecutor
 */
public class BatchServantImpl extends AbstractJobBatchExecutor implements
                                                              BatchServant {

    /**
     * �W���u���s�X�e�[�^�X
     */
    private BLogicResult result = new BLogicResult();

    /**
     * �W���u�V�[�P���X�R�[�h
     */
    private String jobSequenceId = null;

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            // �G���[���b�Z�[�W�̏�����
            this.initDefaultErrorMessage();
            // �o�b�`���s
            this.result = this.executeBatch(this.jobSequenceId);
        } finally {
            closeApplicationContext(defaultApplicationContext);
        }
    }

    /**
     * �W���u�V�[�P���X�R�[�h
     * @return the jobSequenceId
     */
    public String getJobSequenceId() {
        return jobSequenceId;
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.executor.concurrent.BatchServant#setJobSequenceId(java.lang.String)
     */
    public void setJobSequenceId(String jobSequenceId) {
        this.jobSequenceId = jobSequenceId;
    }

    /**
     * �W���u���s�X�e�[�^�X
     * @return the result
     */
    public BLogicResult getResult() {
        return result;
    }
}

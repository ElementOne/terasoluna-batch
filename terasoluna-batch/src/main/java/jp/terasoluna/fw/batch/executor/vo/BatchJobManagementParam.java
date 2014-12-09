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

package jp.terasoluna.fw.batch.executor.vo;

/**
 * �W���u1���擾�pDAO�̓��̓p�����[�^�B<br>
 */
public class BatchJobManagementParam {
    /**
     * �t�B�[���h [jobSequenceId] ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     */
    private String jobSequenceId;

    /**
     * �t�B�[���h [forUpdate] ���ڂ̌^ [boolean]<br>
     * FOR UPDATE�t���O
     */
    private boolean ForUpdate;

    /**
     * �t�B�[���h [jobSequenceId]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     * @param jobSequenceId �t�B�[���h[jobSequenceId]�Ɋi�[�������l
     */
    public void setJobSequenceId(final String jobSequenceId) {
        this.jobSequenceId = jobSequenceId;
    }

    /**
     * �t�B�[���h[jobSequenceId]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     * @return �t�B�[���h[jobSequenceId]�Ɋi�[����Ă���l
     */
    public String getJobSequenceId() {
        return jobSequenceId;
    }

    /**
     * �t�B�[���h [forUpdate]�̃Z�b�^�[���\�b�h ���ڂ̌^ [boolean]<br>
     * FOR UPDATE�t���O
     * @param argForUpdate �t�B�[���h[forUpdate]�Ɋi�[�������l
     */
    public void setForUpdate(final boolean argForUpdate) {
        this.ForUpdate = argForUpdate;
    }

    /**
     * �t�B�[���h[forUpdate]�̃Q�b�^�[���\�b�h ���ڂ̌^ [boolean]<br>
     * FOR UPDATE�t���O
     * @return �t�B�[���h[forUpdate]�Ɋi�[����Ă���l
     */
    public boolean getForUpdate() {
        return ForUpdate;
    }

    /**
     * ���̃o�����[�I�u�W�F�N�g�̕�����\�����擾���܂��B �I�u�W�F�N�g�̃V�����[�͈͂ł���toString����Ȃ��_�ɒ��ӂ��ė��p���Ă��������B
     * @return �o�����[�I�u�W�F�N�g�̕�����\���B
     */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("BatchJobManagementParam[");
        buf.append("jobSequenceId=" + jobSequenceId);
        buf.append(",forUpdate=" + ForUpdate);
        buf.append("]");
        return buf.toString();
    }
}

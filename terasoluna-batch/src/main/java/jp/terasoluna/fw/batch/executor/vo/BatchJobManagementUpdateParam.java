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

import java.sql.Timestamp;

/**
 * �W���u���R�[�h�X�V�pDAO�̓��̓p�����[�^�B<br>
 */
public class BatchJobManagementUpdateParam {
    /**
     * �t�B�[���h [jobSequenceId] ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     */
    private String jobSequenceId;

    /**
     * �t�B�[���h [errAppStatus] ���ڂ̌^ [java.lang.String]<br>
     * BLogic�X�e�[�^�X
     */
    private String blogicAppStatus;

    /**
     * �t�B�[���h [curAppStatus] ���ڂ̌^ [java.lang.String]<br>
     * �X�e�[�^�X���X�g
     */
    private String CurAppStatus;

    /**
     * �t�B�[���h [updDateTime] ���ڂ̌^ [java.sql.Timestamp]<br>
     * �X�V�����i�~���b�j
     */
    private Timestamp UpdDateTime;

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
     * �t�B�[���h [errAppStatus]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * BLogic�X�e�[�^�X
     * @param argErrAppStatus �t�B�[���h[BLogicAppStatus]�Ɋi�[�������l
     */
    public void setBLogicAppStatus(final String argBLogicAppStatus) {
        blogicAppStatus = argBLogicAppStatus;
    }

    /**
     * �t�B�[���h[BLogicAppStatus]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * BLogic�X�e�[�^�X
     * @return �t�B�[���h[BLogicAppStatus]�Ɋi�[����Ă���l
     */
    public String getBLogicAppStatus() {
        return blogicAppStatus;
    }

    /**
     * �t�B�[���h [curAppStatus]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �X�e�[�^�X���X�g
     * @param argCurAppStatus �t�B�[���h[curAppStatus]�Ɋi�[�������l
     */
    public void setCurAppStatus(final String argCurAppStatus) {
        CurAppStatus = argCurAppStatus;
    }

    /**
     * �t�B�[���h[curAppStatus]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �X�e�[�^�X���X�g
     * @return �t�B�[���h[curAppStatus]�Ɋi�[����Ă���l
     */
    public String getCurAppStatus() {
        return CurAppStatus;
    }

    /**
     * �t�B�[���h [updDateTime]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.sql.Timestamp]<br>
     * �X�V�����i�~���b�j
     * @param argUpdDateTime �t�B�[���h[updDateTime]�Ɋi�[�������l
     */
    public void setUpdDateTime(final Timestamp argUpdDateTime) {
        UpdDateTime = argUpdDateTime;
    }

    /**
     * �t�B�[���h[updDateTime]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.sql.Timestamp]<br>
     * �X�V�����i�~���b�j
     * @return �t�B�[���h[updDateTime]�Ɋi�[����Ă���l
     */
    public Timestamp getUpdDateTime() {
        return UpdDateTime;
    }

    /**
     * ���̃o�����[�I�u�W�F�N�g�̕�����\�����擾���܂��B �I�u�W�F�N�g�̃V�����[�͈͂ł���toString����Ȃ��_�ɒ��ӂ��ė��p���Ă��������B
     * @return �o�����[�I�u�W�F�N�g�̕�����\���B
     */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("BatchJobManagementUpdateParam[");
        buf.append("jobSequenceId=" + jobSequenceId);
        buf.append(",BLogicAppStatus=" + blogicAppStatus);
        buf.append(",curAppStatus=" + CurAppStatus);
        buf.append(",updDateTime=" + UpdDateTime);
        buf.append("]");
        return buf.toString();
    }
}

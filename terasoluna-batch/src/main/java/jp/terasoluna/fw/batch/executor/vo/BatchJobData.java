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
 * �W���u���s���̃p�����[�^�B<br>
 */
public class BatchJobData {
    /**
     * �t�B�[���h [jobSequenceId] ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     */
    private String jobSequenceId;

    /**
     * �t�B�[���h [jobAppCd] ���ڂ̌^ [java.lang.String]<br>
     * �W���u�Ɩ��R�[�h
     */
    private String fJobAppCd;

    /**
     * �t�B�[���h [jobArgNm1] ���ڂ̌^ [java.lang.String]<br>
     * ����1
     */
    private String fJobArgNm1;

    /**
     * �t�B�[���h [jobArgNm2] ���ڂ̌^ [java.lang.String]<br>
     * ����2
     */
    private String fJobArgNm2;

    /**
     * �t�B�[���h [jobArgNm3] ���ڂ̌^ [java.lang.String]<br>
     * ����3
     */
    private String fJobArgNm3;

    /**
     * �t�B�[���h [jobArgNm4] ���ڂ̌^ [java.lang.String]<br>
     * ����4
     */
    private String fJobArgNm4;

    /**
     * �t�B�[���h [jobArgNm5] ���ڂ̌^ [java.lang.String]<br>
     * ����5
     */
    private String fJobArgNm5;

    /**
     * �t�B�[���h [jobArgNm6] ���ڂ̌^ [java.lang.String]<br>
     * ����6
     */
    private String fJobArgNm6;

    /**
     * �t�B�[���h [jobArgNm7] ���ڂ̌^ [java.lang.String]<br>
     * ����7
     */
    private String fJobArgNm7;

    /**
     * �t�B�[���h [jobArgNm8] ���ڂ̌^ [java.lang.String]<br>
     * ����8
     */
    private String fJobArgNm8;

    /**
     * �t�B�[���h [jobArgNm9] ���ڂ̌^ [java.lang.String]<br>
     * ����9
     */
    private String fJobArgNm9;

    /**
     * �t�B�[���h [jobArgNm10] ���ڂ̌^ [java.lang.String]<br>
     * ����10
     */
    private String fJobArgNm10;

    /**
     * �t�B�[���h [jobArgNm11] ���ڂ̌^ [java.lang.String]<br>
     * ����11
     */
    private String fJobArgNm11;

    /**
     * �t�B�[���h [jobArgNm12] ���ڂ̌^ [java.lang.String]<br>
     * ����12
     */
    private String fJobArgNm12;

    /**
     * �t�B�[���h [jobArgNm13] ���ڂ̌^ [java.lang.String]<br>
     * ����13
     */
    private String fJobArgNm13;

    /**
     * �t�B�[���h [jobArgNm14] ���ڂ̌^ [java.lang.String]<br>
     * ����14
     */
    private String fJobArgNm14;

    /**
     * �t�B�[���h [jobArgNm15] ���ڂ̌^ [java.lang.String]<br>
     * ����15
     */
    private String fJobArgNm15;

    /**
     * �t�B�[���h [jobArgNm16] ���ڂ̌^ [java.lang.String]<br>
     * ����16
     */
    private String fJobArgNm16;

    /**
     * �t�B�[���h [jobArgNm17] ���ڂ̌^ [java.lang.String]<br>
     * ����17
     */
    private String fJobArgNm17;

    /**
     * �t�B�[���h [jobArgNm18] ���ڂ̌^ [java.lang.String]<br>
     * ����18
     */
    private String fJobArgNm18;

    /**
     * �t�B�[���h [jobArgNm19] ���ڂ̌^ [java.lang.String]<br>
     * ����19
     */
    private String fJobArgNm19;

    /**
     * �t�B�[���h [jobArgNm20] ���ڂ̌^ [java.lang.String]<br>
     * ����20
     */
    private String fJobArgNm20;

    /**
     * �t�B�[���h [blogicAppStatus] ���ڂ̌^ [java.lang.String]<br>
     * BLogic�X�e�[�^�X
     */
    private String fBLogicAppStatus;

    /**
     * �t�B�[���h [curAppStatus] ���ڂ̌^ [java.lang.String]<br>
     * �X�e�[�^�X
     */
    private String fCurAppStatus;

    /**
     * �t�B�[���h [addDateTime] ���ڂ̌^ [java.sql.Timestamp]<br>
     * �o�^�����i�~���b�j
     */
    private Timestamp fAddDateTime;

    /**
     * �t�B�[���h [updDateTime] ���ڂ̌^ [java.sql.Timestamp]<br>
     * �X�V�����i�~���b�j
     */
    private Timestamp fUpdDateTime;

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
     * �t�B�[���h [jobAppCd]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �W���u�Ɩ��R�[�h
     * @param argJobAppCd �t�B�[���h[jobAppCd]�Ɋi�[�������l
     */
    public void setJobAppCd(final String argJobAppCd) {
        fJobAppCd = argJobAppCd;
    }

    /**
     * �t�B�[���h[jobAppCd]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �W���u�Ɩ��R�[�h
     * @return �t�B�[���h[jobAppCd]�Ɋi�[����Ă���l
     */
    public String getJobAppCd() {
        return fJobAppCd;
    }

    /**
     * �t�B�[���h [jobArgNm1]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����1
     * @param argJobArgNm1 �t�B�[���h[jobArgNm1]�Ɋi�[�������l
     */
    public void setJobArgNm1(final String argJobArgNm1) {
        fJobArgNm1 = argJobArgNm1;
    }

    /**
     * �t�B�[���h[jobArgNm1]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����1
     * @return �t�B�[���h[jobArgNm1]�Ɋi�[����Ă���l
     */
    public String getJobArgNm1() {
        return fJobArgNm1;
    }

    /**
     * �t�B�[���h [jobArgNm2]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����2
     * @param argJobArgNm2 �t�B�[���h[jobArgNm2]�Ɋi�[�������l
     */
    public void setJobArgNm2(final String argJobArgNm2) {
        fJobArgNm2 = argJobArgNm2;
    }

    /**
     * �t�B�[���h[jobArgNm2]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����2
     * @return �t�B�[���h[jobArgNm2]�Ɋi�[����Ă���l
     */
    public String getJobArgNm2() {
        return fJobArgNm2;
    }

    /**
     * �t�B�[���h [jobArgNm3]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����3
     * @param argJobArgNm3 �t�B�[���h[jobArgNm3]�Ɋi�[�������l
     */
    public void setJobArgNm3(final String argJobArgNm3) {
        fJobArgNm3 = argJobArgNm3;
    }

    /**
     * �t�B�[���h[jobArgNm3]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����3
     * @return �t�B�[���h[jobArgNm3]�Ɋi�[����Ă���l
     */
    public String getJobArgNm3() {
        return fJobArgNm3;
    }

    /**
     * �t�B�[���h [jobArgNm4]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����4
     * @param argJobArgNm4 �t�B�[���h[jobArgNm4]�Ɋi�[�������l
     */
    public void setJobArgNm4(final String argJobArgNm4) {
        fJobArgNm4 = argJobArgNm4;
    }

    /**
     * �t�B�[���h[jobArgNm4]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����4
     * @return �t�B�[���h[jobArgNm4]�Ɋi�[����Ă���l
     */
    public String getJobArgNm4() {
        return fJobArgNm4;
    }

    /**
     * �t�B�[���h [jobArgNm5]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����5
     * @param argJobArgNm5 �t�B�[���h[jobArgNm5]�Ɋi�[�������l
     */
    public void setJobArgNm5(final String argJobArgNm5) {
        fJobArgNm5 = argJobArgNm5;
    }

    /**
     * �t�B�[���h[jobArgNm5]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����5
     * @return �t�B�[���h[jobArgNm5]�Ɋi�[����Ă���l
     */
    public String getJobArgNm5() {
        return fJobArgNm5;
    }

    /**
     * �t�B�[���h [jobArgNm6]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����6
     * @param argJobArgNm6 �t�B�[���h[jobArgNm6]�Ɋi�[�������l
     */
    public void setJobArgNm6(final String argJobArgNm6) {
        fJobArgNm6 = argJobArgNm6;
    }

    /**
     * �t�B�[���h[jobArgNm6]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����6
     * @return �t�B�[���h[jobArgNm6]�Ɋi�[����Ă���l
     */
    public String getJobArgNm6() {
        return fJobArgNm6;
    }

    /**
     * �t�B�[���h [jobArgNm7]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����7
     * @param argJobArgNm7 �t�B�[���h[jobArgNm7]�Ɋi�[�������l
     */
    public void setJobArgNm7(final String argJobArgNm7) {
        fJobArgNm7 = argJobArgNm7;
    }

    /**
     * �t�B�[���h[jobArgNm7]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����7
     * @return �t�B�[���h[jobArgNm7]�Ɋi�[����Ă���l
     */
    public String getJobArgNm7() {
        return fJobArgNm7;
    }

    /**
     * �t�B�[���h [jobArgNm8]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����8
     * @param argJobArgNm8 �t�B�[���h[jobArgNm8]�Ɋi�[�������l
     */
    public void setJobArgNm8(final String argJobArgNm8) {
        fJobArgNm8 = argJobArgNm8;
    }

    /**
     * �t�B�[���h[jobArgNm8]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����8
     * @return �t�B�[���h[jobArgNm8]�Ɋi�[����Ă���l
     */
    public String getJobArgNm8() {
        return fJobArgNm8;
    }

    /**
     * �t�B�[���h [jobArgNm9]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����9
     * @param argJobArgNm9 �t�B�[���h[jobArgNm9]�Ɋi�[�������l
     */
    public void setJobArgNm9(final String argJobArgNm9) {
        fJobArgNm9 = argJobArgNm9;
    }

    /**
     * �t�B�[���h[jobArgNm9]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����9
     * @return �t�B�[���h[jobArgNm9]�Ɋi�[����Ă���l
     */
    public String getJobArgNm9() {
        return fJobArgNm9;
    }

    /**
     * �t�B�[���h [jobArgNm10]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����10
     * @param argJobArgNm10 �t�B�[���h[jobArgNm10]�Ɋi�[�������l
     */
    public void setJobArgNm10(final String argJobArgNm10) {
        fJobArgNm10 = argJobArgNm10;
    }

    /**
     * �t�B�[���h[jobArgNm10]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����10
     * @return �t�B�[���h[jobArgNm10]�Ɋi�[����Ă���l
     */
    public String getJobArgNm10() {
        return fJobArgNm10;
    }

    /**
     * �t�B�[���h [jobArgNm11]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����11
     * @param argJobArgNm11 �t�B�[���h[jobArgNm11]�Ɋi�[�������l
     */
    public void setJobArgNm11(final String argJobArgNm11) {
        fJobArgNm11 = argJobArgNm11;
    }

    /**
     * �t�B�[���h[jobArgNm11]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����11
     * @return �t�B�[���h[jobArgNm11]�Ɋi�[����Ă���l
     */
    public String getJobArgNm11() {
        return fJobArgNm11;
    }

    /**
     * �t�B�[���h [jobArgNm12]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����12
     * @param argJobArgNm12 �t�B�[���h[jobArgNm12]�Ɋi�[�������l
     */
    public void setJobArgNm12(final String argJobArgNm12) {
        fJobArgNm12 = argJobArgNm12;
    }

    /**
     * �t�B�[���h[jobArgNm12]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����12
     * @return �t�B�[���h[jobArgNm12]�Ɋi�[����Ă���l
     */
    public String getJobArgNm12() {
        return fJobArgNm12;
    }

    /**
     * �t�B�[���h [jobArgNm13]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����13
     * @param argJobArgNm13 �t�B�[���h[jobArgNm13]�Ɋi�[�������l
     */
    public void setJobArgNm13(final String argJobArgNm13) {
        fJobArgNm13 = argJobArgNm13;
    }

    /**
     * �t�B�[���h[jobArgNm13]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����13
     * @return �t�B�[���h[jobArgNm13]�Ɋi�[����Ă���l
     */
    public String getJobArgNm13() {
        return fJobArgNm13;
    }

    /**
     * �t�B�[���h [jobArgNm14]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����14
     * @param argJobArgNm14 �t�B�[���h[jobArgNm14]�Ɋi�[�������l
     */
    public void setJobArgNm14(final String argJobArgNm14) {
        fJobArgNm14 = argJobArgNm14;
    }

    /**
     * �t�B�[���h[jobArgNm14]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����14
     * @return �t�B�[���h[jobArgNm14]�Ɋi�[����Ă���l
     */
    public String getJobArgNm14() {
        return fJobArgNm14;
    }

    /**
     * �t�B�[���h [jobArgNm15]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����15
     * @param argJobArgNm15 �t�B�[���h[jobArgNm15]�Ɋi�[�������l
     */
    public void setJobArgNm15(final String argJobArgNm15) {
        fJobArgNm15 = argJobArgNm15;
    }

    /**
     * �t�B�[���h[jobArgNm15]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����15
     * @return �t�B�[���h[jobArgNm15]�Ɋi�[����Ă���l
     */
    public String getJobArgNm15() {
        return fJobArgNm15;
    }

    /**
     * �t�B�[���h [jobArgNm16]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����16
     * @param argJobArgNm16 �t�B�[���h[jobArgNm16]�Ɋi�[�������l
     */
    public void setJobArgNm16(final String argJobArgNm16) {
        fJobArgNm16 = argJobArgNm16;
    }

    /**
     * �t�B�[���h[jobArgNm16]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����16
     * @return �t�B�[���h[jobArgNm16]�Ɋi�[����Ă���l
     */
    public String getJobArgNm16() {
        return fJobArgNm16;
    }

    /**
     * �t�B�[���h [jobArgNm17]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����17
     * @param argJobArgNm17 �t�B�[���h[jobArgNm17]�Ɋi�[�������l
     */
    public void setJobArgNm17(final String argJobArgNm17) {
        fJobArgNm17 = argJobArgNm17;
    }

    /**
     * �t�B�[���h[jobArgNm17]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����17
     * @return �t�B�[���h[jobArgNm17]�Ɋi�[����Ă���l
     */
    public String getJobArgNm17() {
        return fJobArgNm17;
    }

    /**
     * �t�B�[���h [jobArgNm18]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����18
     * @param argJobArgNm18 �t�B�[���h[jobArgNm18]�Ɋi�[�������l
     */
    public void setJobArgNm18(final String argJobArgNm18) {
        fJobArgNm18 = argJobArgNm18;
    }

    /**
     * �t�B�[���h[jobArgNm18]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����18
     * @return �t�B�[���h[jobArgNm18]�Ɋi�[����Ă���l
     */
    public String getJobArgNm18() {
        return fJobArgNm18;
    }

    /**
     * �t�B�[���h [jobArgNm19]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����19
     * @param argJobArgNm19 �t�B�[���h[jobArgNm19]�Ɋi�[�������l
     */
    public void setJobArgNm19(final String argJobArgNm19) {
        fJobArgNm19 = argJobArgNm19;
    }

    /**
     * �t�B�[���h[jobArgNm19]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����19
     * @return �t�B�[���h[jobArgNm19]�Ɋi�[����Ă���l
     */
    public String getJobArgNm19() {
        return fJobArgNm19;
    }

    /**
     * �t�B�[���h [jobArgNm20]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����20
     * @param argJobArgNm20 �t�B�[���h[jobArgNm20]�Ɋi�[�������l
     */
    public void setJobArgNm20(final String argJobArgNm20) {
        fJobArgNm20 = argJobArgNm20;
    }

    /**
     * �t�B�[���h[jobArgNm20]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * ����20
     * @return �t�B�[���h[jobArgNm20]�Ɋi�[����Ă���l
     */
    public String getJobArgNm20() {
        return fJobArgNm20;
    }

    /**
     * �t�B�[���h [blogicAppStatus]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * BLogic�X�e�[�^�X
     * @param argBLogicAppStatus �t�B�[���h[blogicAppStatus]�Ɋi�[�������l
     */
    public void setErrAppStatus(final String argBLogicAppStatus) {
        fBLogicAppStatus = argBLogicAppStatus;
    }

    /**
     * �t�B�[���h[blogicAppStatus]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * BLogic�X�e�[�^�X
     * @return �t�B�[���h[blogicAppStatus]�Ɋi�[����Ă���l
     */
    public String getBLogicAppStatus() {
        return fBLogicAppStatus;
    }

    /**
     * �t�B�[���h [curAppStatus]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �X�e�[�^�X
     * @param argCurAppStatus �t�B�[���h[curAppStatus]�Ɋi�[�������l
     */
    public void setCurAppStatus(final String argCurAppStatus) {
        fCurAppStatus = argCurAppStatus;
    }

    /**
     * �t�B�[���h[curAppStatus]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �X�e�[�^�X
     * @return �t�B�[���h[curAppStatus]�Ɋi�[����Ă���l
     */
    public String getCurAppStatus() {
        return fCurAppStatus;
    }

    /**
     * �t�B�[���h [addDateTime]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.sql.Timestamp]<br>
     * �o�^�����i�~���b�j
     * @param argAddDateTime �t�B�[���h[addDateTime]�Ɋi�[�������l
     */
    public void setAddDateTime(final Timestamp argAddDateTime) {
        fAddDateTime = argAddDateTime;
    }

    /**
     * �t�B�[���h[addDateTime]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.sql.Timestamp]<br>
     * �o�^�����i�~���b�j
     * @return �t�B�[���h[addDateTime]�Ɋi�[����Ă���l
     */
    public Timestamp getAddDateTime() {
        return fAddDateTime;
    }

    /**
     * �t�B�[���h [updDateTime]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.sql.Timestamp]<br>
     * �X�V�����i�~���b�j
     * @param argUpdDateTime �t�B�[���h[updDateTime]�Ɋi�[�������l
     */
    public void setUpdDateTime(final Timestamp argUpdDateTime) {
        fUpdDateTime = argUpdDateTime;
    }

    /**
     * �t�B�[���h[updDateTime]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.sql.Timestamp]<br>
     * �X�V�����i�~���b�j
     * @return �t�B�[���h[updDateTime]�Ɋi�[����Ă���l
     */
    public Timestamp getUpdDateTime() {
        return fUpdDateTime;
    }

    /**
     * ���̃o�����[�I�u�W�F�N�g�̕�����\�����擾���܂��B �I�u�W�F�N�g�̃V�����[�͈͂ł���toString����Ȃ��_�ɒ��ӂ��ė��p���Ă��������B
     * @return �o�����[�I�u�W�F�N�g�̕�����\���B
     */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("BatchJobData[");
        buf.append("jobSequenceId=" + jobSequenceId);
        buf.append(",jobAppCd=" + fJobAppCd);
        buf.append(",jobArgNm1=" + fJobArgNm1);
        buf.append(",jobArgNm2=" + fJobArgNm2);
        buf.append(",jobArgNm3=" + fJobArgNm3);
        buf.append(",jobArgNm4=" + fJobArgNm4);
        buf.append(",jobArgNm5=" + fJobArgNm5);
        buf.append(",jobArgNm6=" + fJobArgNm6);
        buf.append(",jobArgNm7=" + fJobArgNm7);
        buf.append(",jobArgNm8=" + fJobArgNm8);
        buf.append(",jobArgNm9=" + fJobArgNm9);
        buf.append(",jobArgNm10=" + fJobArgNm10);
        buf.append(",jobArgNm11=" + fJobArgNm11);
        buf.append(",jobArgNm12=" + fJobArgNm12);
        buf.append(",jobArgNm13=" + fJobArgNm13);
        buf.append(",jobArgNm14=" + fJobArgNm14);
        buf.append(",jobArgNm15=" + fJobArgNm15);
        buf.append(",jobArgNm16=" + fJobArgNm16);
        buf.append(",jobArgNm17=" + fJobArgNm17);
        buf.append(",jobArgNm18=" + fJobArgNm18);
        buf.append(",jobArgNm19=" + fJobArgNm19);
        buf.append(",jobArgNm20=" + fJobArgNm20);
        buf.append(",blogicAppStatus=" + fBLogicAppStatus);
        buf.append(",curAppStatus=" + fCurAppStatus);
        buf.append(",addDateTime=" + fAddDateTime);
        buf.append(",updDateTime=" + fUpdDateTime);
        buf.append("]");
        return buf.toString();
    }
}

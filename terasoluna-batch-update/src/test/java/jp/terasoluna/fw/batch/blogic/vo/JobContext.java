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

package jp.terasoluna.fw.batch.blogic.vo;

import java.io.Serializable;

import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupportImpl;

public class JobContext extends BLogicParam implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 2544252352103871021L;

    /** �r�W�l�X���W�b�N�̓��̓p�����[�^ */
    protected BLogicParam blogicParam = null;

    /** �o�b�`�X�V�T�|�[�g */
    protected BatchUpdateSupport batchUpdateSupport = null;

    /**
     * �R���X�g���N�^.<br>
     */
    public JobContext() {
        this.blogicParam = new BLogicParam();
        this.batchUpdateSupport = new BatchUpdateSupportImpl();
    }

    /**
     * �R���X�g���N�^.<br>
     * @param blogicParam BLogicParam
     */
    public JobContext(BLogicParam blogicParam) {
        this.blogicParam = blogicParam;
        this.batchUpdateSupport = new BatchUpdateSupportImpl();
    }

    /**
     * �r�W�l�X���W�b�N�̓��̓p�����[�^��ݒ肷��B
     * @param blogicParam BLogicParam
     */
    public void setBlogicParam(BLogicParam blogicParam) {
        this.blogicParam = blogicParam;
    }

    /**
     * �t�B�[���h [jobSequenceId]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     * @param jobSequenceId �t�B�[���h[jobSequenceId]�Ɋi�[�������l
     */
    public void setJobSequenceId(final String jobSequenceId) {
        this.blogicParam.setJobSequenceId(jobSequenceId);
    }

    /**
     * �t�B�[���h[jobSequenceId]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     * @return �t�B�[���h[jobSequenceId]�Ɋi�[����Ă���l
     */
    public String getJobSequenceId() {
        return this.blogicParam.getJobSequenceId();
    }

    /**
     * �t�B�[���h [jobAppCd]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * �W���u�V�[�P���X�R�[�h
     * @param argJobAppCd �t�B�[���h[jobAppCd]�Ɋi�[�������l
     */
    public void setJobAppCd(String jobAppCd) {
        this.blogicParam.setJobAppCd(jobAppCd);
    }

    /**
     * �t�B�[���h[fJobAppCd]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * �W���u�Ɩ��R�[�h
     * @return �t�B�[���h[fJobAppCd]�Ɋi�[����Ă���l
     */
    public String getJobAppCd() {
        return this.blogicParam.getJobAppCd();
    }

    /**
     * �t�B�[���h [jobArgNm1]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����1
     * @param argJobArgNm1 �t�B�[���h[jobArgNm1]�Ɋi�[�������l
     */
    public void setJobArgNm1(final String argJobArgNm1) {
        this.blogicParam.setJobArgNm1(argJobArgNm1);
    }

    /**
     * �t�B�[���h[jobArgNm1]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����1
     * @return �t�B�[���h[jobArgNm1]�Ɋi�[����Ă���l
     */
    public String getJobArgNm1() {
        return this.blogicParam.getJobArgNm1();
    }

    /**
     * �t�B�[���h [jobArgNm2]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����2
     * @param argJobArgNm2 �t�B�[���h[jobArgNm2]�Ɋi�[�������l
     */
    public void setJobArgNm2(final String argJobArgNm2) {
        this.blogicParam.setJobArgNm2(argJobArgNm2);
    }

    /**
     * �t�B�[���h[jobArgNm2]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����2
     * @return �t�B�[���h[jobArgNm2]�Ɋi�[����Ă���l
     */
    public String getJobArgNm2() {
        return this.blogicParam.getJobArgNm2();
    }

    /**
     * �t�B�[���h [jobArgNm3]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����3
     * @param argJobArgNm3 �t�B�[���h[jobArgNm3]�Ɋi�[�������l
     */
    public void setJobArgNm3(final String argJobArgNm3) {
        this.blogicParam.setJobArgNm3(argJobArgNm3);
    }

    /**
     * �t�B�[���h[jobArgNm3]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����3
     * @return �t�B�[���h[jobArgNm3]�Ɋi�[����Ă���l
     */
    public String getJobArgNm3() {
        return this.blogicParam.getJobArgNm3();
    }

    /**
     * �t�B�[���h [jobArgNm4]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����4
     * @param argJobArgNm4 �t�B�[���h[jobArgNm4]�Ɋi�[�������l
     */
    public void setJobArgNm4(final String argJobArgNm4) {
        this.blogicParam.setJobArgNm4(argJobArgNm4);
    }

    /**
     * �t�B�[���h[jobArgNm4]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����4
     * @return �t�B�[���h[jobArgNm4]�Ɋi�[����Ă���l
     */
    public String getJobArgNm4() {
        return this.blogicParam.getJobArgNm4();
    }

    /**
     * �t�B�[���h [jobArgNm5]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����5
     * @param argJobArgNm5 �t�B�[���h[jobArgNm5]�Ɋi�[�������l
     */
    public void setJobArgNm5(final String argJobArgNm5) {
        this.blogicParam.setJobArgNm5(argJobArgNm5);
    }

    /**
     * �t�B�[���h[jobArgNm5]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����5
     * @return �t�B�[���h[jobArgNm5]�Ɋi�[����Ă���l
     */
    public String getJobArgNm5() {
        return this.blogicParam.getJobArgNm5();
    }

    /**
     * �t�B�[���h [jobArgNm6]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����6
     * @param argJobArgNm6 �t�B�[���h[jobArgNm6]�Ɋi�[�������l
     */
    public void setJobArgNm6(final String argJobArgNm6) {
        this.blogicParam.setJobArgNm6(argJobArgNm6);
    }

    /**
     * �t�B�[���h[jobArgNm6]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����6
     * @return �t�B�[���h[jobArgNm6]�Ɋi�[����Ă���l
     */
    public String getJobArgNm6() {
        return this.blogicParam.getJobArgNm6();
    }

    /**
     * �t�B�[���h [jobArgNm7]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����7
     * @param argJobArgNm7 �t�B�[���h[jobArgNm7]�Ɋi�[�������l
     */
    public void setJobArgNm7(final String argJobArgNm7) {
        this.blogicParam.setJobArgNm7(argJobArgNm7);
    }

    /**
     * �t�B�[���h[jobArgNm7]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����7
     * @return �t�B�[���h[jobArgNm7]�Ɋi�[����Ă���l
     */
    public String getJobArgNm7() {
        return this.blogicParam.getJobArgNm7();
    }

    /**
     * �t�B�[���h [jobArgNm8]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����8
     * @param argJobArgNm8 �t�B�[���h[jobArgNm8]�Ɋi�[�������l
     */
    public void setJobArgNm8(final String argJobArgNm8) {
        this.blogicParam.setJobArgNm8(argJobArgNm8);
    }

    /**
     * �t�B�[���h[jobArgNm8]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����8
     * @return �t�B�[���h[jobArgNm8]�Ɋi�[����Ă���l
     */
    public String getJobArgNm8() {
        return this.blogicParam.getJobArgNm8();
    }

    /**
     * �t�B�[���h [jobArgNm9]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����9
     * @param argJobArgNm9 �t�B�[���h[jobArgNm9]�Ɋi�[�������l
     */
    public void setJobArgNm9(final String argJobArgNm9) {
        this.blogicParam.setJobArgNm9(argJobArgNm9);
    }

    /**
     * �t�B�[���h[jobArgNm9]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����9
     * @return �t�B�[���h[jobArgNm9]�Ɋi�[����Ă���l
     */
    public String getJobArgNm9() {
        return this.blogicParam.getJobArgNm9();
    }

    /**
     * �t�B�[���h [jobArgNm10]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����10
     * @param argJobArgNm10 �t�B�[���h[jobArgNm10]�Ɋi�[�������l
     */
    public void setJobArgNm10(final String argJobArgNm10) {
        this.blogicParam.setJobArgNm10(argJobArgNm10);
    }

    /**
     * �t�B�[���h[jobArgNm10]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����10
     * @return �t�B�[���h[jobArgNm10]�Ɋi�[����Ă���l
     */
    public String getJobArgNm10() {
        return this.blogicParam.getJobArgNm10();
    }

    /**
     * �t�B�[���h [jobArgNm11]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����11
     * @param argJobArgNm11 �t�B�[���h[jobArgNm11]�Ɋi�[�������l
     */
    public void setJobArgNm11(final String argJobArgNm11) {
        this.blogicParam.setJobArgNm11(argJobArgNm11);
    }

    /**
     * �t�B�[���h[jobArgNm11]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����11
     * @return �t�B�[���h[jobArgNm11]�Ɋi�[����Ă���l
     */
    public String getJobArgNm11() {
        return this.blogicParam.getJobArgNm11();
    }

    /**
     * �t�B�[���h [jobArgNm12]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����12
     * @param argJobArgNm12 �t�B�[���h[jobArgNm12]�Ɋi�[�������l
     */
    public void setJobArgNm12(final String argJobArgNm12) {
        this.blogicParam.setJobArgNm12(argJobArgNm12);
    }

    /**
     * �t�B�[���h[jobArgNm12]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����12
     * @return �t�B�[���h[jobArgNm12]�Ɋi�[����Ă���l
     */
    public String getJobArgNm12() {
        return this.blogicParam.getJobArgNm12();
    }

    /**
     * �t�B�[���h [jobArgNm13]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����13
     * @param argJobArgNm13 �t�B�[���h[jobArgNm13]�Ɋi�[�������l
     */
    public void setJobArgNm13(final String argJobArgNm13) {
        this.blogicParam.setJobArgNm13(argJobArgNm13);
    }

    /**
     * �t�B�[���h[jobArgNm13]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����13
     * @return �t�B�[���h[jobArgNm13]�Ɋi�[����Ă���l
     */
    public String getJobArgNm13() {
        return this.blogicParam.getJobArgNm13();
    }

    /**
     * �t�B�[���h [jobArgNm14]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����14
     * @param argJobArgNm14 �t�B�[���h[jobArgNm14]�Ɋi�[�������l
     */
    public void setJobArgNm14(final String argJobArgNm14) {
        this.blogicParam.setJobArgNm14(argJobArgNm14);
    }

    /**
     * �t�B�[���h[jobArgNm14]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����14
     * @return �t�B�[���h[jobArgNm14]�Ɋi�[����Ă���l
     */
    public String getJobArgNm14() {
        return this.blogicParam.getJobArgNm14();
    }

    /**
     * �t�B�[���h [jobArgNm15]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����15
     * @param argJobArgNm15 �t�B�[���h[jobArgNm15]�Ɋi�[�������l
     */
    public void setJobArgNm15(final String argJobArgNm15) {
        this.blogicParam.setJobArgNm15(argJobArgNm15);
    }

    /**
     * �t�B�[���h[jobArgNm15]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����15
     * @return �t�B�[���h[jobArgNm15]�Ɋi�[����Ă���l
     */
    public String getJobArgNm15() {
        return this.blogicParam.getJobArgNm15();
    }

    /**
     * �t�B�[���h [jobArgNm16]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����16
     * @param argJobArgNm16 �t�B�[���h[jobArgNm16]�Ɋi�[�������l
     */
    public void setJobArgNm16(final String argJobArgNm16) {
        this.blogicParam.setJobArgNm16(argJobArgNm16);
    }

    /**
     * �t�B�[���h[jobArgNm16]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����16
     * @return �t�B�[���h[jobArgNm16]�Ɋi�[����Ă���l
     */
    public String getJobArgNm16() {
        return this.blogicParam.getJobArgNm16();
    }

    /**
     * �t�B�[���h [jobArgNm17]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����17
     * @param argJobArgNm17 �t�B�[���h[jobArgNm17]�Ɋi�[�������l
     */
    public void setJobArgNm17(final String argJobArgNm17) {
        this.blogicParam.setJobArgNm17(argJobArgNm17);
    }

    /**
     * �t�B�[���h[jobArgNm17]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����17
     * @return �t�B�[���h[jobArgNm17]�Ɋi�[����Ă���l
     */
    public String getJobArgNm17() {
        return this.blogicParam.getJobArgNm17();
    }

    /**
     * �t�B�[���h [jobArgNm18]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����18
     * @param argJobArgNm18 �t�B�[���h[jobArgNm18]�Ɋi�[�������l
     */
    public void setJobArgNm18(final String argJobArgNm18) {
        this.blogicParam.setJobArgNm18(argJobArgNm18);
    }

    /**
     * �t�B�[���h[jobArgNm18]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����18
     * @return �t�B�[���h[jobArgNm18]�Ɋi�[����Ă���l
     */
    public String getJobArgNm18() {
        return this.blogicParam.getJobArgNm18();
    }

    /**
     * �t�B�[���h [jobArgNm19]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����19
     * @param argJobArgNm19 �t�B�[���h[jobArgNm19]�Ɋi�[�������l
     */
    public void setJobArgNm19(final String argJobArgNm19) {
        this.blogicParam.setJobArgNm19(argJobArgNm19);
    }

    /**
     * �t�B�[���h[jobArgNm19]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����19
     * @return �t�B�[���h[jobArgNm19]�Ɋi�[����Ă���l
     */
    public String getJobArgNm19() {
        return this.blogicParam.getJobArgNm19();
    }

    /**
     * �t�B�[���h [jobArgNm20]�̃Z�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����20
     * @param argJobArgNm20 �t�B�[���h[jobArgNm20]�Ɋi�[�������l
     */
    public void setJobArgNm20(final String argJobArgNm20) {
        this.blogicParam.setJobArgNm20(argJobArgNm20);
    }

    /**
     * �t�B�[���h[jobArgNm20]�̃Q�b�^�[���\�b�h. ���ڂ̌^ [java.lang.String]<br>
     * ����20
     * @return �t�B�[���h[jobArgNm20]�Ɋi�[����Ă���l
     */
    public String getJobArgNm20() {
        return this.blogicParam.getJobArgNm20();
    }

    /**
     * �o�b�`�X�V�T�|�[�g���擾����.<br>
     * @return BatchUpdateSupport
     */
    public BatchUpdateSupport getBatchUpdateSupport() {
        return batchUpdateSupport;
    }

    /**
     * �o�b�`�X�V�T�|�[�g��ݒ肷��.<br>
     * @param batchUpdateSupport BatchUpdateSupport
     */
    public void setBatchUpdateSupport(BatchUpdateSupport batchUpdateSupport) {
        this.batchUpdateSupport = batchUpdateSupport;
    }

}

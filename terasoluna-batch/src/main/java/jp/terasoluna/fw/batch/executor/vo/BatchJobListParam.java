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

import java.util.List;

/**
 * �W���u���X�g�擾�pDAO�̓��̓p�����[�^�B<br>
 */
public class BatchJobListParam {
    /**
     * �t�B�[���h [jobAppCd] ���ڂ̌^ [java.lang.String]<br>
     * �W���u�Ɩ��R�[�h
     */
    private String JobAppCd;

    /**
     * �t�B�[���h [curAppStatusList] ���ڂ̌^ [java.util.List]<br>
     * �X�e�[�^�X���X�g
     */
    private List<String> CurAppStatusList;

    /**
     * �t�B�[���h [jobAppCd]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �W���u�Ɩ��R�[�h
     * @param argJobAppCd �t�B�[���h[jobAppCd]�Ɋi�[�������l
     */
    public void setJobAppCd(final String argJobAppCd) {
        this.JobAppCd = argJobAppCd;
    }

    /**
     * �t�B�[���h[jobAppCd]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.lang.String]<br>
     * �W���u�Ɩ��R�[�h
     * @return �t�B�[���h[jobAppCd]�Ɋi�[����Ă���l
     */
    public String getJobAppCd() {
        return JobAppCd;
    }

    /**
     * �t�B�[���h [curAppStatusList]�̃Z�b�^�[���\�b�h ���ڂ̌^ [java.util.List]<br>
     * �X�e�[�^�X���X�g
     * @param argCurAppStatusList �t�B�[���h[curAppStatusList]�Ɋi�[�������l
     */
    public void setCurAppStatusList(final List<String> argCurAppStatusList) {
        this.CurAppStatusList = argCurAppStatusList;
    }

    /**
     * �t�B�[���h[curAppStatusList]�̃Q�b�^�[���\�b�h ���ڂ̌^ [java.util.List]<br>
     * �X�e�[�^�X���X�g
     * @return �t�B�[���h[curAppStatusList]�Ɋi�[����Ă���l
     */
    public List<String> getCurAppStatusList() {
        return CurAppStatusList;
    }

    /**
     * ���̃o�����[�I�u�W�F�N�g�̕�����\�����擾���܂��B �I�u�W�F�N�g�̃V�����[�͈͂ł���toString����Ȃ��_�ɒ��ӂ��ė��p���Ă��������B
     * @return �o�����[�I�u�W�F�N�g�̕�����\���B
     */
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("BatchJobListParam[");
        buf.append("jobAppCd=" + JobAppCd);
        buf.append(",curAppStatusList=" + CurAppStatusList);
        buf.append("]");
        return buf.toString();
    }
}

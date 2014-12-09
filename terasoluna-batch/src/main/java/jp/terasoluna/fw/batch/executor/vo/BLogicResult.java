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
 * �r�W�l�X���W�b�N���s���ʃp�����[�^�B<br>
 */
public class BLogicResult {

    /**
     * �r�W�l�X���W�b�N�̖߂�l
     */
    protected int blogicStatus = -1;

    /**
     * �r�W�l�X���W�b�N�Ŕ���������O
     */
    protected Throwable blogicThrowable = null;

    /**
     * �r�W�l�X���W�b�N�̖߂�l
     * @return the blogicStatus
     */
    public int getBlogicStatus() {
        return blogicStatus;
    }

    /**
     * �r�W�l�X���W�b�N�̖߂�l
     * @param blogicStatus the blogicStatus to set
     */
    public void setBlogicStatus(int blogicStatus) {
        this.blogicStatus = blogicStatus;
    }

    /**
     * �r�W�l�X���W�b�N�Ŕ���������O
     * @return the blogicThrowable
     */
    public Throwable getBlogicThrowable() {
        return blogicThrowable;
    }

    /**
     * �r�W�l�X���W�b�N�Ŕ���������O
     * @param blogicThrowable the blogicThrowable to set
     */
    public void setBlogicThrowable(Throwable blogicThrowable) {
        this.blogicThrowable = blogicThrowable;
    }

}

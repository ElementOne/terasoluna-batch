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

package jp.terasoluna.fw.batch.executor;

import jp.terasoluna.fw.batch.executor.vo.BLogicResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;

/**
 * �o�b�`�G�O�[�L���[�^�C���^�t�F�[�X�B
 */
public interface BatchExecutor {

    /**
     * <h6>�o�b�`���s.</h6>
     * @param jobRecord ���s����W���u���R�[�h
     * @return �r�W�l�X���W�b�N���s����
     */
    BLogicResult executeBatch(BatchJobData jobRecord);

}

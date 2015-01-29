/*
 * Copyright (c) 2014 NTT DATA Corporation
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

package jp.terasoluna.fw.batch.executor.dao;

import jp.terasoluna.fw.batch.executor.vo.*;
import org.apache.ibatis.session.RowBounds;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * �t���[�����[�N�ɂ��DB�A�N�Z�X���s���Ɏg�p�����DAO�B
 */
public interface SystemDao {

    /**
     * ����������ŃW���u�Ǘ��e�[�u���̃��R�[�h���擾����B
     *
     * @param batchJobListParam �擾����
     * @return �W���u�Ǘ��e�[�u�����R�[�h
     */
    List<BatchJobListResult> selectJobList(BatchJobListParam batchJobListParam);

    /**
     * ����������ŃW���u�Ǘ��e�[�u���̃��R�[�h���擾����B
     *
     * @param rowBounds �擾�s����
     * @param batchJobListParam �擾����
     * @return �W���u�Ǘ��e�[�u�����R�[�h
     */
    List<BatchJobListResult> selectJobList(RowBounds rowBounds,
                                           BatchJobListParam batchJobListParam);

    /**
     * �W���u�Ǘ��e�[�u���̓��背�R�[�h���擾����B
     *
     * @param batchJobManagementParam ���R�[�h�̓������
     * @return �W���u�Ǘ��e�[�u�����R�[�h
     */
    BatchJobData selectJob(BatchJobManagementParam batchJobManagementParam);

    /**
     * ���ݎ������擾����B
     *
     * @return ���ݎ���
     */
    Timestamp readCurrentTime();

    /**
     * ���ݓ��t���擾����B
     *
     * @return ���ݓ��t
     */
    Date readCurrentDate();

    /**
     * �W���u�Ǘ��e�[�u�����X�V����B
     *
     * @param batchJobManagementUpdateParam �W���u�Ǘ��e�[�u���̍X�V�����E���e
     * @return �X�V����
     */
    int updateJobTable(
            BatchJobManagementUpdateParam batchJobManagementUpdateParam);
}

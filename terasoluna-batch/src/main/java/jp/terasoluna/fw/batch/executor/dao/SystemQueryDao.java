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

import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListParam;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobManagementParam;
import org.apache.ibatis.session.RowBounds;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * フレームワークにより行われるDB参照用DAO。
 */
public interface SystemQueryDao {

    /**
     * 特定条件下でジョブ管理テーブルのレコードを取得する。
     *
     * @param batchJobListParam 取得条件
     * @return ジョブ管理テーブルレコード
     */
    List<BatchJobListResult> selectJobList(BatchJobListParam batchJobListParam);

    /**
     * 特定条件下でジョブ管理テーブルのレコードを取得する。
     *
     * @param rowBaounds 取得行制限
     * @param batchJobListParam 取得条件
     * @return ジョブ管理テーブルレコード
     */
    List<BatchJobListResult> selectJobList(RowBounds rowBaounds,
                                           BatchJobListParam batchJobListParam);

    /**
     * ジョブ管理テーブルの特定レコードを取得する。
     *
     * @param batchJobManagementParam レコードの特定条件
     * @return ジョブ管理テーブルレコード
     */
    BatchJobData selectJob(BatchJobManagementParam batchJobManagementParam);

    /**
     * 現在時刻を取得する。
     *
     * @return 現在時刻
     */
    Timestamp currentTimeReader();

    /**
     * 現在日付を取得する。
     *
     * @return 現在日付
     */
    Date currentDateReader();
}

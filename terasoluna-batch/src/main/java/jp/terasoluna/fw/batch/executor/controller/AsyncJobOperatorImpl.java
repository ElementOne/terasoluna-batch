/*
 * Copyright (c) 2016 NTT DATA Corporation
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

package jp.terasoluna.fw.batch.executor.controller;

import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.executor.repository.JobControlFinder;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListResult;
import jp.terasoluna.fw.logger.TLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * 非同期型ジョブの起動において、DIコンテナ上のエントリポイントとなる実装クラス。
 * <p>
 * {@code start()}メソッド実行後、実行対象ジョブの検索によるポーリング、検索したジョブの起動、
 * ポーリングループの終了監視を行う。<br>
 * 検索のポーリングは、実行対象となるジョブが都度検索でヒットする場合は連続して行うが、
 * 実行対象となるジョブが見つからない場合は一定時間スリープした後に再度検索を開始する。
 * このスリープ時間はプロパティファイルの{@code polling.interval}にて指定することができる。単位はms(ミリ秒)。
 * 指定しなかった場合、デフォルト1000msでスリープする。
 * </p>
 *
 * @since 3.6
 */
public class AsyncJobOperatorImpl implements JobOperator {

    /**
     * ロガー。<br>
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(AsyncJobOperatorImpl.class);

    /**
     * ジョブのポーリング間隔。<br>
     */
    @Value("${polling.interval:3000}")
    protected long jobIntervalTime;

    /**
     * ジョブの検索機能。<br>
     */
    protected JobControlFinder jobControlFinder;

    /**
     * ジョブの起動機能。<br>
     */
    protected AsyncJobLauncher asyncJobLauncher;

    /**
     * 終了条件監視機能。<br>
     */
    protected AsyncBatchStopper asyncBatchStopper;

    /**
     * コンストラクタ。<br>
     * ジョブの起動とポーリングループの終了条件監視に必要となる機能を設定する。
     *
     * @param jobControlFinder ジョブの検索機能
     * @param asyncJobLauncher       ジョブの起動機能
     * @param asyncBatchStopper      終了条件監視機能
     */
    public AsyncJobOperatorImpl(JobControlFinder jobControlFinder,
            AsyncJobLauncher asyncJobLauncher,
            AsyncBatchStopper asyncBatchStopper) {
        Assert.notNull(jobControlFinder, LOGGER.getLogMessage(LogId.EAL025056,
                this.getClass().getSimpleName(), "JobControlFinder"));
        Assert.notNull(asyncJobLauncher, LOGGER.getLogMessage(LogId.EAL025056,
                this.getClass().getSimpleName(), "AsyncJobLauncher"));
        Assert.notNull(asyncBatchStopper, LOGGER.getLogMessage(LogId.EAL025056,
                this.getClass().getSimpleName(), "AsyncBatchStopper"));

        this.jobControlFinder = jobControlFinder;
        this.asyncJobLauncher = asyncJobLauncher;
        this.asyncBatchStopper = asyncBatchStopper;
    }

    /**
     * ジョブを起動する。<br>
     *
     * @param args 起動時引数
     * @return 終了ステータスコード
     */
    @Override
    public int start(String[] args) {
        try {
            while (!asyncBatchStopper.canStop()) {
                BatchJobListResult batchJobListResult = jobControlFinder
                        .resolveBatchJobResult(args);
                if (batchJobListResult == null) {
                    pollingSleep();
                    continue;
                }
                // ジョブの実行
                asyncJobLauncher
                        .executeJob(batchJobListResult.getJobSequenceId());
            }
        } finally {
            asyncJobLauncher.shutdown();
        }
        return 0;
    }

    /**
     * ポーリングにより実行対象となるジョブが見つからない場合一定時間スリープさせる。<br>
     * スリープの時間は{@code jobIntervalTime}プロパティで定められる。
     */
    protected void pollingSleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(jobIntervalTime);
        } catch (InterruptedException e) {
            throw new BatchException(e);
        }
    }
}

/*
 * Copyright (c) 2012 NTT DATA Corporation
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

package jp.terasoluna.fw.collector.db;

/**
 * {@link DaoCollectorPrePostProcess#postprocessException(DaoCollector, Throwable)} の結果
 */
public enum DaoCollectorPrePostProcessStatus {
    /**
     * 例外をスローする
     */
    THROW,

    /**
     * 例外をスローせずにリトライする
     */
    RETRY,

    /**
     * 例外をスローせずにコレクタを終了する
     */
    END
}

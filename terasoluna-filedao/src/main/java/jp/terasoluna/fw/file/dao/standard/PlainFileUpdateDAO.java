/*
 * Copyright (c) 2007 NTT DATA Corporation
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

package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.dao.FileLineWriter;

/**
 * ファイル書込み用のFileLineWriter生成クラス。
 * <p>
 * ファイル行オブジェクトを用いないファイル出力用のFileLineWriterを生成する。
 * </p>
 * 行オブジェクトに設定出来るアノテーションの説明は{@link PlainFileLineWriter} のJavaDocを参考して下さい。
 */
public class PlainFileUpdateDAO extends AbstractFileUpdateDAO {

    /**
     * FileLineWriter取得用メソッド。
     * @param <T> 1行分の文字列を格納するファイル行オブジェクトクラス
     * @param fileName ファイル名
     * @param clazz パラメータクラス
     * @return ファイル行オブジェクトを用いないファイル出力用のFileLineWriter
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> FileLineWriter<T> execute(String fileName, Class<T> clazz) {

        // FileLineWriterを生成する。
        PlainFileLineWriter fileLineWriter = new PlainFileLineWriter(fileName,
                clazz, getColumnFormatterMap());

        return (FileLineWriter<T>) fileLineWriter;
    }
}

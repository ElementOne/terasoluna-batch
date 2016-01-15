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

package jp.terasoluna.fw.collector.db;

import java.lang.reflect.InvocationTargetException;

import jp.terasoluna.fw.collector.LogId;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.logger.TLogger;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Queueing1NRelationResultHandlerImplの実装クラス<br>
 * <p>
 * QueueingResultHandlerImplの1:Nマッピング対応版。<br>
 * 1:Nマッピング使用時、iBATISは1:N構造のオブジェクトが完成する前に ResultHandler#handleResult()に渡すため、 このコレクタでは、
 * ResultHandler#handleResult()に渡された結果を、次回同メソッドが実行されたときに キューに格納する。 最後に渡された結果は、iBATISが処理を終えた時点でキューに格納する。<br>
 * また、1:Nマッピング使用時、iBATISは全てのデータを取得し終わるまで、 ResultHandler#handleResult()に渡したオブジェクトを、全てiBATIS内部に保持し続けるため、
 * このコレクタでは、ResultHandler#handleResult()に渡されたオブジェクトのシャローコピーをキューに格納し、
 * ResultHandler#handleResult()に渡されたオブジェクトの全プロパティを初期化する。
 * </p>
 * <p>
 * 使用上の注意：
 * <ul>
 * <li>resultMap要素のgroupBy属性に書かれた列を第1ソートキーとしてソート(ORDER BY)すること。<br>
 * (ソートキーを誤った場合、またはソートをしない場合、 1:N構造のコレクタ結果クラスが不完全な状態でBLogicを実行してしまうので注意すること)</li>
 * </ul>
 * </p>
 * <p>
 * 使いどころ
 * <ul>
 * <li>コレクタ用のsqlMapでiBATISの1:Nマッピングを利用し、かつ、 データ量が多く、メモリを節約する必要があるとき<br>
 * (1:Nマッピングを利用しない場合は、 オブジェクトのシャローコピーや初期化は不要であるため、 QueueingResultHandlerImplを使用すること)</li>
 * </ul>
 * </p>
 * <p>
 * sqlMap記述例1(1:Nマッピング)：
 * 
 * <pre>
 * &lt;resultMap id=&quot;rmap_JB1231_SQL&quot; class=&quot;sample.JB1231Data&quot; &lt;b&gt;groupBy=&quot;col1&quot;&lt;/b&gt;&gt;
 *   &lt;result property=&quot;col1&quot;/&gt;
 *   &lt;result property=&quot;col2&quot;/&gt;
 *   &lt;result property=&quot;col3&quot;/&gt;
 *   &lt;result property=&quot;detail1&quot; resultMap=&quot;rmap_JB1231_SQL_detail1&quot;/&gt;
 * &lt;/resultMap&gt;
 * &lt;resultMap id=&quot;rmap_JB1231_SQL_detail1&quot; class=&quot;sample.Detail1&quot;&gt;
 *   &lt;result property=&quot;d12&quot; column=&quot;d12&quot;/&gt;
 *   &lt;result property=&quot;d13&quot; column=&quot;d13&quot;/&gt;
 * &lt;/resultMap&gt;
 * &lt;select id=&quot;JB1231_SQL&quot; resultMap=&quot;rmap_JB1231_SQL&quot;&gt;
 *     SELECT
 *       t1.col1 as col1,
 *       t1.col2 as col2,
 *       t1.col3 as col3,
 *       d1.col2 as d12,
 *       d1.col3 as d13,
 *     FROM (sample_table1 t1
 *       left outer join sample_table1_detail1 d1 on t1.col1 = d1.col1)
 *     &lt;b&gt;ORDER BY col1&lt;/b&gt;, ...
 * &lt;/select&gt;
 * </pre>
 * 
 * sqlMap記述例2(1:M:Nマッピング)：
 * 
 * <pre>
 * &lt;resultMap id=&quot;rmap_JB1231_SQL&quot; class=&quot;sample.JB1231Data&quot; &lt;b&gt;groupBy=&quot;col1&quot;&lt;/b&gt;&gt;
 *   &lt;result property=&quot;col1&quot;/&gt;
 *   &lt;result property=&quot;col2&quot;/&gt;
 *   &lt;result property=&quot;col3&quot;/&gt;
 *   &lt;result property=&quot;detail1&quot; resultMap=&quot;rmap_JB1231_SQL_detail1&quot;/&gt;
 *   &lt;result property=&quot;detail2&quot; resultMap=&quot;rmap_JB1231_SQL_detail2&quot;/&gt;
 * &lt;/resultMap&gt;
 * &lt;resultMap id=&quot;rmap_JB1231_SQL_detail1&quot; class=&quot;sample.Detail1&quot;&gt;
 *   &lt;result property=&quot;d12&quot; column=&quot;d12&quot;/&gt;
 *   &lt;result property=&quot;d13&quot; column=&quot;d13&quot;/&gt;
 * &lt;/resultMap&gt;
 * &lt;resultMap id=&quot;rmap_JB1231_SQL_detail2&quot; class=&quot;sample.Detail2&quot;&gt;
 *   &lt;result property=&quot;d22&quot; column=&quot;d22&quot;/&gt;
 *   &lt;result property=&quot;d23&quot; column=&quot;d23&quot;/&gt;
 * &lt;/resultMap&gt;
 * &lt;select id=&quot;JB1231_SQL&quot; resultMap=&quot;rmap_JB1231_SQL&quot;&gt;
 *   SELECT * FROM (
 *     SELECT
 *       t1.col1 as col1,
 *       t1.col2 as col2,
 *       t1.col3 as col3,
 *       d1.col2 as d12,
 *       d1.col3 as d13,
 *       null as d22,
 *       null as d23
 *     FROM (sample_table1 t1
 *       left outer join sample_table1_detail1 d1 on t1.col1 = d1.col1)
 *     UNION ALL
 *     SELECT
 *       t1.col1 as col1,
 *       t1.col2 as col2,
 *       t1.col3 as col3,
 *       null as d12,
 *       null as d13,
 *       d2.col2 as d22,
 *       d2.col3 as d23
 *     FROM (sample_table1 t1
 *       left outer join sample_table1_detail2 d2 on t1.col1 = d2.col1)
 *   ) AS A &lt;b&gt;ORDER BY col1&lt;/b&gt;, ...
 * &lt;/select&gt;
 * </pre>
 * 
 * </p>
 * @deprecated
 * 1:Nマッピングを行なう際は、MyBatis3のselectステートメントのresultOrdered属性をtrueにすることにより、
 * {@code Queueing1NRelationResultHandlerImpl}、および{@code DaoCollectorConfig#setRelation1n()}の設定が不要となる。
 * @see <a href="http://mybatis.github.io/mybatis-3/ja/sqlmap-xml.html">http://mybatis.github.io/mybatis-3/ja/sqlmap-xml.html</a>
 */
@Deprecated
public class Queueing1NRelationResultHandlerImpl<T> extends
        QueueingResultHandlerImpl<T> {

    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(Queueing1NRelationResultHandlerImpl.class);

    /**
     * 前回handleResultメソッドに渡された<code>Row</code>データをキューに格納する。
     */
    @Override
    public void delayCollect() {
        if (this.prevRow == null) {
            return;
        }
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        try {
            // オブジェクトのシャローコピーを作成
            Object copy = BeanUtils.cloneBean(this.prevRow);
            PropertyUtils.copyProperties(this.prevRow, this.prevRow
                    .getClass().newInstance());

            if (this.daoCollector != null) {
                // 取得したオブジェクトのシャローコピーを1件キューにつめる
                this.daoCollector.addQueue(new DataValueObject(copy,
                        this.dataCount.incrementAndGet()));
            }
        } catch (IllegalAccessException e) {
            throw new SystemException(e);
        } catch (InstantiationException e) {
            throw new SystemException(e);
        } catch (InvocationTargetException e) {
            throw new SystemException(e);
        } catch (NoSuchMethodException e) {
            throw new SystemException(e);
        } catch (InterruptedException e) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(LogId.TAL041002, Thread.currentThread()
                        .getName());
            }
            // InterruptedException発生によりスレッドの「割り込み状態」はクリアされる。
            // 呼び出し元に割り込みが発生したことを通知する必要があるため、「割り込み状態」を再度保存する。
            Thread.currentThread().interrupt();
        }
    }
}

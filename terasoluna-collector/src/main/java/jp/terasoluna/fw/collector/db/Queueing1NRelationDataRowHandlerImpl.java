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
 * Queueing1NRelationDataRowHandlerImpl�̎����N���X<br>
 * <p>
 * IBatisDbCollectorImpl��1:N�}�b�s���O�Ή��ŁB<br>
 * 1:N�}�b�s���O�g�p���AiBATIS��1:N�\���̃I�u�W�F�N�g����������O�� RowHandler#handleRow(DataRowHandler#handleRow)�ɓn�����߁A ���̃R���N�^�ł́A
 * RowHandler#handleRow(DataRowHandler#handleRow)�ɓn���ꂽ���ʂ��A���񓯃��\�b�h�����s���ꂽ�Ƃ��� �L���[�Ɋi�[����B �Ō�ɓn���ꂽ���ʂ́AiBATIS���������I�������_�ŃL���[�Ɋi�[����B<br>
 * �܂��A1:N�}�b�s���O�g�p���AiBATIS�͑S�Ẵf�[�^���擾���I���܂ŁA RowHandler#handleRow(DataRowHandler#handleRow)�ɓn�����I�u�W�F�N�g���A�S��iBATIS�����ɕێ��������邽�߁A
 * ���̃R���N�^�ł́ARowHandler#handleRow(DataRowHandler#handleRow)�ɓn���ꂽ�I�u�W�F�N�g�̃V�����[�R�s�[���L���[�Ɋi�[���A
 * RowHandler#handleRow(DataRowHandler#handleRow)�ɓn���ꂽ�I�u�W�F�N�g�̑S�v���p�e�B������������B
 * </p>
 * <p>
 * �g�p��̒��ӁF
 * <ul>
 * <li>resultMap�v�f��groupBy�����ɏ����ꂽ����1�\�[�g�L�[�Ƃ��ă\�[�g(ORDER BY)���邱�ƁB<br>
 * (�\�[�g�L�[��������ꍇ�A�܂��̓\�[�g�����Ȃ��ꍇ�A 1:N�\���̃R���N�^���ʃN���X���s���S�ȏ�Ԃ�BLogic�����s���Ă��܂��̂Œ��ӂ��邱��)</li>
 * </ul>
 * </p>
 * <p>
 * �g���ǂ���
 * <ul>
 * <li>�R���N�^�p��sqlMap��iBATIS��1:N�}�b�s���O�𗘗p���A���A �f�[�^�ʂ������A��������ߖ񂷂�K�v������Ƃ�<br>
 * (1:N�}�b�s���O�𗘗p���Ȃ��ꍇ�́A �I�u�W�F�N�g�̃V�����[�R�s�[�⏉�����͕s�v�ł��邽�߁A IBatisDbCollectorImpl���g�p���邱��)</li>
 * </ul>
 * </p>
 * <p>
 * sqlMap�L�q��1(1:N�}�b�s���O)�F
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
 * sqlMap�L�q��2(1:M:N�}�b�s���O)�F
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
 */
public class Queueing1NRelationDataRowHandlerImpl extends
                                                 QueueingDataRowHandlerImpl
                                                                           implements
                                                                           QueueingDataRowHandler {

    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(Queueing1NRelationDataRowHandlerImpl.class);

    /**
     * �O��handleRow���\�b�h�ɓn���ꂽ<code>Row</code>�f�[�^���L���[�Ɋi�[����B
     */
    public void delayCollect() {
        if (this.prevRow != null) {
            if (!Thread.currentThread().isInterrupted()) {
                long dtcnt = this.dataCount.incrementAndGet();
                try {
                    // �I�u�W�F�N�g�̃V�����[�R�s�[���쐬
                    Object copy = BeanUtils.cloneBean(this.prevRow);
                    PropertyUtils.copyProperties(this.prevRow, this.prevRow
                            .getClass().newInstance());

                    if (this.dbCollector != null) {
                        // �擾�����I�u�W�F�N�g�̃V�����[�R�s�[��1���L���[�ɂ߂�
                        this.dbCollector.addQueue(new DataValueObject(copy,
                                dtcnt));
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
                    // �ďo�����̗�O�L���b�`���ɃL���[���Ńu���b�N����Ȃ��悤�A�{�X���b�h���u���荞�݁v��ԂƂ���B
                    Thread.currentThread().interrupt();
                    throw new InterruptedRuntimeException(e);
                }
            } else {
                throw new InterruptedRuntimeException();
            }
        }
    }
}

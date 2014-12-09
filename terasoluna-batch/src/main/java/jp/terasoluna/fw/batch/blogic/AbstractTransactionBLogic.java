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

package jp.terasoluna.fw.batch.blogic;

import java.sql.SQLException;
import java.util.Map;

import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.logger.TLogger;

import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * �g�����U�N�V�����Ǘ����s���r�W�l�X���W�b�N���ۃN���X�B<br>
 * <br>
 * �t���[�����[�N���Ńg�����U�N�V�����Ǘ����s�������ꍇ�A���̒��ۃN���X���p�����AAbstractTransactionBLogic#doMain���\�b�h���������ăr�W�l�X���W�b�N���쐬����B<br>
 * ���̒��ۃN���X���p�������r�W�l�X���W�b�N�̃g�����U�N�V�����̐U�����͈ȉ��̒ʂ�ł���B
 * <ol>
 * <li>�r�W�l�X���W�b�N�J�n���ꂽ���A�g�����U�N�V�������J�n�����B</li>
 * <li>���s��O���X���[���ꂽ���A�g�����U�N�V�����J�n���܂Ń��[���o�b�N�����B</li>
 * <li>�r�W�l�X���W�b�N�I����A�R�~�b�g����A�g�����U�N�V�������I�������B</li>
 * </ol>
 * @see jp.terasoluna.fw.batch.blogic.BLogic
 * @see jp.terasoluna.fw.batch.blogic.AbstractBLogic
 */
public abstract class AbstractTransactionBLogic extends AbstractBLogic {

    /**
     * �v���Z�X�I���R�[�h�i�ُ�j
     */
    private static final int PROCESS_END_STATUS_FAILURE = 255;

    /**
     * ���O.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(AbstractTransactionBLogic.class);

    /**
     * transactionManagerMap.
     */
    private Map<?, ?> transactionManagerMap = null;

    /**
     * transactionStatusMap
     */
    private Map<String, TransactionStatus> transactionStatusMap = null;

    /**
     * �o�b�`�������s���\�b�h.
     * @see jp.terasoluna.fw.batch.blogic.BLogic#execute(java.lang.String[], com.ibatis.sqlmap.client.SqlMapClient)
     */
    public int execute(BLogicParam param) {
        int status = PROCESS_END_STATUS_FAILURE;
        ApplicationContext ctx = getApplicationContext();

        this.transactionManagerMap = ctx
                .getBeansOfType(PlatformTransactionManager.class);

        // �g�����U�N�V�����J�n
        this.transactionStatusMap = startTransactions(this.transactionManagerMap);

        try {
            // �又��
            status = doMain(param);

            // �g�����U�N�V�����R�~�b�g
            commitTransactions(this.transactionManagerMap,
                    this.transactionStatusMap);
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new BatchException(e);
        } finally {
            // �g�����U�N�V�����I���i���R�~�b�g�����[���o�b�N�j
            boolean et = endTransactions(this.transactionManagerMap,
                    this.transactionStatusMap);
            if (!et) {
                LOGGER.error(LogId.EAL025001);
            }
        }

        return status;
    }

    /**
     * �又��.
     * @param param
     * @return
     * @throws SQLException
     */
    public abstract int doMain(BLogicParam param);

    /**
     * �g�����U�N�V�����J�n.
     * @param trnMngMap PlatformTransactionManager�}�b�v
     * @return TransactionStatus�}�b�v
     */
    private Map<String, TransactionStatus> startTransactions(Map<?, ?> trnMngMap) {
        return BatchUtil.startTransactions(
                BatchUtil.getTransactionDefinition(), trnMngMap, LOGGER);
    }

    /**
     * �g�����U�N�V�����R�~�b�g.
     * @param trnMngMap PlatformTransactionManager�}�b�v
     * @param tranStatMap TransactionStatus�}�b�v
     */
    private void commitTransactions(Map<?, ?> trnMngMap,
            Map<String, TransactionStatus> tranStatMap) {
        BatchUtil.commitTransactions(trnMngMap, tranStatMap, LOGGER);
    }

    /**
     * �g�����U�N�V�����I���i���R�~�b�g�����[���o�b�N�j.
     * @param trnMngMap PlatformTransactionManager�}�b�v
     * @param tranStatMap TransactionStatus�}�b�v
     * @return ����Ȃ�true
     */
    private boolean endTransactions(Map<?, ?> trnMngMap,
            Map<String, TransactionStatus> tranStatMap) {
        return BatchUtil.endTransactions(trnMngMap, tranStatMap, LOGGER);
    }
}

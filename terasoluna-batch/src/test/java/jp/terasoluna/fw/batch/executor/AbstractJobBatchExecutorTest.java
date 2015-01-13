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

import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.executor.dao.SystemDao;
import jp.terasoluna.fw.batch.executor.vo.BLogicResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.executor.vo.BatchJobManagementParam;
import jp.terasoluna.fw.batch.executor.vo.BatchJobManagementUpdateParam;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;
import jp.terasoluna.fw.ex.unit.util.TerasolunaPropertyUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;

import static org.mockito.Mockito.*;

public class AbstractJobBatchExecutorTest extends DaoTestCase {

    /**
     * ���p����DAO�N���X
     */
    private SystemDao sysDao = null;

    private PlatformTransactionManager transactionManager = null;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        deleteFromTable("job_control");

        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000001', 'B000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000002', 'B000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000003', 'B000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000004', 'B000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
		update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000005', 'B000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL)");

        sysDao = getBean("sysDao");
        transactionManager = getBean("adminTransactionManager");

        TerasolunaPropertyUtils.saveProperties();

    }

    @Override
    protected void onTearDownAfterTransaction() throws Exception {

        TerasolunaPropertyUtils.restoreProperties();
        super.onTearDownAfterTransaction();
    }

    /**
     * �W���u�X�e�[�^�X����̃e�X�g <br>
     * <br>
     * �e�X�g�T�v�F �N�����u0�v�Ō��݃W���u�X�e�[�^�X�������{�u0�v�̏ꍇ�A �W���u�X�e�[�^�X���茋�ʂ����s���u1�v��ԋp���邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF���s���u1�v���ԋp����邱�Ƃ��m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testJudgmentStatus01() throws Exception {

        BatchJobData param = new BatchJobData();
        param.setJobAppCd("B000001");
        param.setCurAppStatus("0");
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        String result = exe.judgmentStatus(param, "0000000001", "0", "0");

        assertEquals("1", result);

    }

    /**
     * �W���u�X�e�[�^�X����̃e�X�g <br>
     * <br>
     * �e�X�g�T�v�F �N�����u0�v�Ō��݃W���u�X�e�[�^�X�����s���u1�v�̏ꍇ�A �W���u�X�e�[�^�X���茋�ʂ�null��ԋp���邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځFnull���ԋp����邱�Ƃ��m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testJudgmentStatus02() throws Exception {

        BatchJobData param = new BatchJobData();
        param.setJobAppCd("B000001");
        param.setCurAppStatus("1");

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        String result = exe.judgmentStatus(param, "0000000001", "0", "0");

        assertNull(result);
    }

    /**
     * �W���u�X�e�[�^�X����̃e�X�g <br>
     * <br>
     * �e�X�g�T�v�F �N�����u0�v�Ō��݃W���u�X�e�[�^�X�������ρu2�v�̏ꍇ�A �W���u�X�e�[�^�X���茋�ʂ�null��ԋp���邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځFnull���ԋp����邱�Ƃ��m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testJudgmentStatus03() throws Exception {

        BatchJobData param = new BatchJobData();
        param.setJobAppCd("B000001");
        param.setCurAppStatus("2");

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        String result = exe.judgmentStatus(param, "0000000001", "0", "0");

        assertNull(result);
    }

    /**
     * �W���u�X�e�[�^�X����̃e�X�g <br>
     * <br>
     * �e�X�g�T�v�F ����I�����u1�v�Ō��݃W���u�X�e�[�^�X�����s���u0�v�̏ꍇ�A �W���u�X�e�[�^�X���茋�ʂ�null��ԋp���邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځFnull���ԋp����邱�Ƃ��m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testJudgmentStatus04() throws Exception {

        BatchJobData param = new BatchJobData();
        param.setJobAppCd("B000001");
        param.setCurAppStatus("0");

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        String result = exe.judgmentStatus(param, "0000000001", "1", "0");

        assertNull(result);

    }

    /**
     * �W���u�X�e�[�^�X����̃e�X�g <br>
     * <br>
     * �e�X�g�T�v�F ����I�����u1�v�Ō��݃W���u�X�e�[�^�X�����s���u1�v�̏ꍇ�A �W���u�X�e�[�^�X���茋�ʂ������ρu2�v��ԋp���邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF�����ρu2�v���ԋp����邱�Ƃ��m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testJudgmentStatus05() throws Exception {

        BatchJobData param = new BatchJobData();
        param.setJobAppCd("B000001");
        param.setCurAppStatus("1");

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        String result = exe.judgmentStatus(param, "0000000001", "1", "0");

        assertEquals("2", result);

    }

    /**
     * �W���u�X�e�[�^�X����̃e�X�g <br>
     * <br>
     * �e�X�g�T�v�F ����I�����u1�v�Ō��݃W���u�X�e�[�^�X�������ρu2�v�̏ꍇ�A �W���u�X�e�[�^�X���茋�ʂ�null��ԋp���邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځFnull���ԋp����邱�Ƃ��m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testJudgmentStatus06() throws Exception {

        BatchJobData param = new BatchJobData();
        param.setJobAppCd("B000001");
        param.setCurAppStatus("2");

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        String result = exe.judgmentStatus(param, "0000000001", "1", "0");

        assertNull(result);

    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �N�����u0�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000001�v�̃W���u�X�e�[�^�X��
     * �����{�u0�v�̏ꍇtrue���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * true���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testUpdateBatchStatus01() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.updateBatchStatus("0000000001", "0", null,
                sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000001'",
                BatchJobData.class);

        assertTrue(result);
        assertEquals("1", row.getCurAppStatus());
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �N�����u0�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000002�v�̃W���u�X�e�[�^�X��
     * ���s���u1�v�̏ꍇfalse���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testUpdateBatchStatus02() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.updateBatchStatus("0000000002", "0", null,
                sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000002'",
                BatchJobData.class);

        assertFalse(result);
        assertEquals("1", row.getCurAppStatus());
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �N�����u0�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000003�v�̃W���u�X�e�[�^�X��
     * �����ρu2�v�̏ꍇfalse���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testUpdateBatchStatus03() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.updateBatchStatus("0000000003", "0", null,
                sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000003'",
                BatchJobData.class);

        assertFalse(result);
        assertEquals("2", row.getCurAppStatus());
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ����I�����u1�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000001�v�̃W���u�X�e�[�^�X��
     * �����{�u0�v�̏ꍇ false���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testUpdateBatchStatus04() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.updateBatchStatus("0000000001", "1", "0",
                sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000001'",
                BatchJobData.class);

        assertFalse(false);
        assertEquals("0", row.getCurAppStatus());
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ����I�����u1�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000002�v�̃W���u�X�e�[�^�X��
     * ���s���u1�v�̏ꍇ true���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * true���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testUpdateBatchStatus05() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.updateBatchStatus("0000000002", "1", "0",
                sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000002'",
                BatchJobData.class);

        assertTrue(result);
        assertEquals("2", row.getCurAppStatus());
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ����I�����u1�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000003�v�̃W���u�X�e�[�^�X��
     * �����ρu2�v�̏ꍇ false���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testUpdateBatchStatus06() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.updateBatchStatus("0000000003", "1", "0",
                sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000003'",
                BatchJobData.class);

        assertFalse(result);
        assertEquals("2", row.getCurAppStatus());
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * DB�t�F�[���I�[�o�̔�����z�肵�ATransactionManager#getTransaction(tranDef)�ɂ��
     * �g�����U�N�V�����J�n����TransactionException�����������ꍇ�A�ďo�����ɃX���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF<br>
     * TransactionException���X���[����邱�ƁB
     * 
     * @throws Exception
     */
    public void testUpdateBatchStatus07() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        PlatformTransactionManager transactionManager = new PlatformTransactionManagerStub01();
        try {
            exe.updateBatchStatus("0000000003", "1", "0",
                sysDao, transactionManager);
            fail();
        } catch (TransactionException e) {
            assertEquals("�g�����U�N�V�����J�n�m�F", e.getMessage());
        }
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * DB�t�F�[���I�[�o�̔�����z�肵�AJobUtil.updateJobStatus()�ɂ��
     * DB�X�V����DataAccessException�����������ꍇ�A�ďo�����ɃX���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF<br>
     * DataAccessException���X���[����邱�ƁB
     * 
     * @throws Exception
     */
    public void testUpdateBatchStatus08() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        SystemDao sysDao = mock(SystemDao.class);
        when(sysDao.updateJobTable(any(BatchJobManagementUpdateParam.class)))
                .thenThrow(new DataAccessException("DB�X�e�[�^�X�X�V����O�m�F�p") {});
        when(sysDao.selectJob(any(BatchJobManagementParam.class)))
                .thenReturn(new BatchJobData(){{
                    setJobSequenceId("0000000003");
                    setCurAppStatus("1");
                    setErrAppStatus("1");
                }});
        try {
            exe.updateBatchStatus("0000000003", "1", "0",
                sysDao, transactionManager);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DB�X�e�[�^�X�X�V����O�m�F�p", e.getMessage());
        }
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * DB�t�F�[���I�[�o�̔�����z�肵�AJobUtil.selectJob(jobSequenceId, true, sysDao)�ɂ��
     * DB�Q�Ǝ���DataAccessException�����������ꍇ�A�ďo�����ɃX���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF<br>
     * DataAccessException���X���[����邱�ƁB
     * 
     * @throws Exception
     */
    public void testUpdateBatchStatus09() throws Exception {
        SystemDao sysDao = mock(SystemDao.class);
        when(sysDao.selectJob(any(BatchJobManagementParam.class)))
                .thenThrow(new DataAccessException("DB�X�e�[�^�X�Q�Ǝ���O�m�F�p") {
                });
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        try {
            exe.updateBatchStatus("0000000005", "1", "0",
                    sysDao, transactionManager);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DB�X�e�[�^�X�Q�Ǝ���O�m�F�p", e.getMessage());
        }
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * DB�t�F�[���I�[�o�̔�����z�肵�AtransactionManager.commit(tranStatus)�ɂ��
     * �g�����U�N�V�����R�~�b�g���s����TransactionException�����������ꍇ�A�ďo�����ɃX���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF<br>
     * TransactionException���X���[����邱�ƁB
     * 
     * @throws Exception
     */
    public void testUpdateBatchStatus10() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        PlatformTransactionManager transactionManager = new PlatformTransactionManagerStub02();
        try {
            exe.updateBatchStatus("0000000005", "1", "0", sysDao, transactionManager);
            fail();
        } catch (TransactionException e) {
            assertEquals("�R�~�b�g�m�F�p", e.getMessage());
        }
    }

    /**
     * �W���u�X�e�[�^�X�X�V���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * DB�t�F�[���I�[�o�ȊO�̗�O������z�肵�AtransactionManager��null�̏ꍇ�ɂ��
     * �g�����U�N�V�����J�n����NullPointerException�����������ꍇ�A
     * BatchException�Ƀ��b�v���ꂽ��ԂŌďo�����ɃX���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF<br>
     * BatchException���X���[����邱�ƁB
     *
     * @throws Exception
     */
    public void testUpdateBatchStatus11() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        try {
            exe.updateBatchStatus("0000000005", "1", "0", sysDao, null);
            fail();
        } catch (BatchException e) {
            assertTrue(e.getCause() instanceof NullPointerException);
        }
    }

    /**
     * �W���u�X�e�[�^�X�X�V�i�W���u�I���j���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ����I�����u1�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000001�v�̃W���u�X�e�[�^�X��
     * �����{�u0�v�̏ꍇ false���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��Ȃ����ƁB
     * @throws Exception
     */
    public void testEndBatchStatus01() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        BLogicResult blogicResult = new BLogicResult();
        boolean result = exe.endBatchStatus("0000000001", blogicResult, sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000001'",
                BatchJobData.class);

        assertFalse(result);
        assertEquals("0", row.getCurAppStatus());

    }

    /**
     * �W���u�X�e�[�^�X�X�V�i�W���u�I���j���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ����I�����u1�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000002�v�̃W���u�X�e�[�^�X��
     * ���s���u1�v�̏ꍇ true���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * true���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testEndBatchStatus02() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        BLogicResult blogicResult = new BLogicResult();
        boolean result = exe.endBatchStatus("0000000002", blogicResult,
                sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000002'",
                BatchJobData.class);

        assertTrue(result);
        assertEquals("2", row.getCurAppStatus());

    }

    /**
     * �W���u�X�e�[�^�X�X�V�i�W���u�I���j���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ����I�����u1�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000003�v�̃W���u�X�e�[�^�X��
     * �����ρu2�v�̏ꍇ false���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��Ȃ����ƁB
     * @throws Exception
     */
    public void testEndBatchStatus03() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        BLogicResult blogicResult = new BLogicResult();
        boolean result = exe.endBatchStatus("0000000003", blogicResult, sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000003'",
                BatchJobData.class);

        assertFalse(result);
        assertEquals("2", row.getCurAppStatus());

    }

    /**
     * �W���u�X�e�[�^�X�X�V�i�W���u�J�n�j���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �N�����u0�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000001�v�̃W���u�X�e�[�^�X��
     * �����{�u0�v�̏ꍇ true���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * true���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testStartBatchStatus01() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.startBatchStatus("0000000001", sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000001'",
                BatchJobData.class);

        assertTrue(result);
        assertEquals("1", row.getCurAppStatus());

    }

    /**
     * �W���u�X�e�[�^�X�X�V�i�W���u�J�n�j���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �N�����u0�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000002�v�̃W���u�X�e�[�^�X��
     * ���s���u1�v�̏ꍇfalse���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��Ȃ����ƁB
     * @throws Exception
     */
    public void testStartBatchStatus02() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.startBatchStatus("0000000002", sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000002'",
                BatchJobData.class);

        assertFalse(result);
        assertEquals("1", row.getCurAppStatus());

    }

    /**
     * �W���u�X�e�[�^�X�X�V�i�W���u�J�n�j���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �N�����u0�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000003�v�̃W���u�X�e�[�^�X��
     * �����ρu2�v�̏ꍇ false���ԋp����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * false���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��Ȃ����ƁB
     * @throws Exception
     */
    public void testStartBatchStatus03() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        boolean result = exe.startBatchStatus("0000000003", sysDao, transactionManager);

        BatchJobData row = queryForRowObject(
                "select * from job_control where job_seq_id = '0000000003'",
                BatchJobData.class);

        assertFalse(result);
        assertEquals("2", row.getCurAppStatus());

    }

    /**
     * �W���u���s���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �N�����u0�v�ŃW���u�Ǘ��e�[�u���ɓo�^����Ă��� �W���u�V�[�P���X�R�[�h�u0000000001�v�̃W���u��
     * ����Ɏ��s����邱�Ƃ��m�F����B <br>
     * <br>
     * �m�F���ځF<br>
     * �W���u�I���R�[�h��0���ԋp����邱�ƁB<br>
     * DB�̃X�e�[�^�X���X�V����Ă��邱�ƁB
     * @throws Exception
     */
    public void testExecuteBatch01() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        exe.sysDao = sysDao;
        exe.sysTransactionManager = transactionManager;
        BLogicResult result = exe.executeBatch("0000000001");

        assertNotNull(result);
        assertEquals(0, result.getBlogicStatus());
    }

    public void testExecuteBatch02() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        exe.sysDao = null;
        exe.sysTransactionManager = null;
        BLogicResult result = exe.executeBatch("0000000001");

        assertNotNull(result);
        assertEquals(-1, result.getBlogicStatus());
    }

    public void testExecuteBatch03() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        exe.sysDao = sysDao;
        exe.sysTransactionManager = transactionManager;
        BLogicResult result = exe.executeBatch("0000000000");

        assertNotNull(result);
        assertEquals(-1, result.getBlogicStatus());
    }

    public void testExecuteBatch04() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        exe.sysDao = sysDao;
        exe.sysTransactionManager = transactionManager;
        exe.changeStartStatus = true;
        BLogicResult result = exe.executeBatch("0000000001");

        assertNotNull(result);
        assertEquals(0, result.getBlogicStatus());
    }

    public void testExecuteBatch05() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        exe.sysDao = sysDao;
        exe.sysTransactionManager = transactionManager;
        exe.changeStartStatus = true;
        BLogicResult result = exe.executeBatch("0000000003");

        assertNotNull(result);
        // �X�V�X�e�[�^�X�s�����̂��߁A�r�W�l�X���W�b�N�͖����{�̂܂ܕԋp�����B
        assertEquals(-1, result.getBlogicStatus());
    }

    /**
     * GetJobIntervalTime���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F getter���\�b�h�̃e�X�g�ł��邽�߁A�����l�����m�ɕԂ���邱�Ƃ��m�F����B
     * @throws Exception
     */
    public void testGetJobIntervalTime01() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        assertEquals(3000, exe.getJobIntervalTime());
    }

    /**
     * getExecutorEndMonitoringFile���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�Fgetter���\�b�h�̃e�X�g�ł��邽�߁A�����l��null�ł��邱�Ƃ��m�F����B
     * @throws Exception
     */
    public void testGetExecutorEndMonitoringFile01() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        assertNotNull(exe.getExecutorEndMonitoringFile());
        assertEquals("/tmp/batch_terminate_file", exe
                .getExecutorEndMonitoringFile());
    }

    /**
     * getExecutorJobTerminateWaitIntervalTime���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�Fgetter���\�b�h�̃e�X�g�ł��邽�߁A�����l��5000�ł��邱�Ƃ��m�F����B
     * @throws Exception
     */
    public void testGetExecutorJobTerminateWaitIntervalTime01()
                                                               throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        assertEquals(3000, exe.getExecutorJobTerminateWaitIntervalTime());
    }

    /**
     * setChangeStartStatus���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�Fsetter���\�b�h�̃e�X�g�ł��邽�߁A�����l��false�ł��邱�Ƃ��m�F����B
     * @throws Exception
     */
    public void testSetChangeStartStatus01() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        assertFalse(exe.changeStartStatus);

    }

    /**
     * setChangeStartStatus���\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�Fsetter���\�b�h�̃e�X�g�ł��邽�߁A������true��^���Atrue���ԋp����邱�Ƃ��m�F����B
     * @throws Exception
     */
    public void testSetChangeStartStatus02() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        exe.setChangeStartStatus(true);

        assertTrue(exe.changeStartStatus);
    }

    public void testInitParameter01() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils.removeProperty("polling.interval");
        exe.initParameter();

        assertEquals(1000, exe.jobIntervalTime);

    }

    public void testInitParameter02() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils.removeProperty("polling.interval");
        TerasolunaPropertyUtils.addProperty("polling.interval", "5000");
        exe.initParameter();

        assertEquals(5000, exe.jobIntervalTime);

    }

    public void testInitParameter03() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils.removeProperty("polling.interval");
        TerasolunaPropertyUtils.addProperty("polling.interval", "test");
        exe.initParameter();

        assertEquals(1000, exe.jobIntervalTime);

    }

    public void testInitParameter04() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils.removeProperty("executor.endMonitoringFile");
        exe.initParameter();

        assertNull(exe.executorEndMonitoringFile);

    }

    public void testInitParameter05() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils.removeProperty("executor.endMonitoringFile");
        TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
                "/tmp/batch_terminate_file2");
        exe.initParameter();

        assertEquals("/tmp/batch_terminate_file2",
                exe.executorEndMonitoringFile);

    }

    public void testInitParameter06() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils
                .removeProperty("executor.jobTerminateWaitInterval");
        exe.initParameter();

        assertEquals(5000, exe.executorJobTerminateWaitIntervalTime);

    }

    public void testInitParameter07() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils
                .removeProperty("executor.jobTerminateWaitInterval");
        TerasolunaPropertyUtils.addProperty(
                "executor.jobTerminateWaitInterval", "8000");
        exe.initParameter();

        assertEquals(8000, exe.executorJobTerminateWaitIntervalTime);

    }

    public void testInitParameter08() {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils
                .removeProperty("executor.jobTerminateWaitInterval");
        TerasolunaPropertyUtils.addProperty(
                "executor.jobTerminateWaitInterval", "test");
        exe.initParameter();

        assertEquals(5000, exe.executorJobTerminateWaitIntervalTime);

    }

    public void testInitSystemDatasourceDao01() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        TerasolunaPropertyUtils.removeProperty("systemDataSource.sysDAO");
        exe.sysDao = null;
        exe.initSystemDatasourceDao();

        assertNull(exe.sysDao);
    }

    public void testInitSystemDatasourceDao02() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        TerasolunaPropertyUtils.removeProperty("systemDataSource.sysDao");
        TerasolunaPropertyUtils
                .addProperty("systemDataSource.sysDAO", "test");
        exe.sysDao = null;
        exe.initSystemDatasourceDao();

        assertNull(exe.sysDao);
    }

    public void testInitSystemDatasourceDao03() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        TerasolunaPropertyUtils.removeProperty("systemDataSource.sysDAO");
        TerasolunaPropertyUtils.addProperty("systemDataSource.sysDAO",
                "adminTransactionManager");
        exe.sysDao = null;
        exe.initSystemDatasourceDao();

        assertNull(exe.sysDao);
    }

    public void testInitSystemDatasourceDao07() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        TerasolunaPropertyUtils
                .removeProperty("systemDataSource.transactionManager");
        TerasolunaPropertyUtils.addProperty("systemDataSource.transactionManager",
                "sysDAO");
        exe.sysTransactionManager = null;
        exe.initSystemDatasourceDao();

        assertNull(exe.sysTransactionManager);
    }

    public void testInitSystemDatasourceDao08() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        TerasolunaPropertyUtils
                .removeProperty("systemDataSource.transactionManager");
        TerasolunaPropertyUtils.addProperty(
                "systemDataSource.transactionManager", "test");
        exe.sysTransactionManager = null;
        exe.initSystemDatasourceDao();

        assertNull(exe.sysTransactionManager);
    }

    public void testInitSystemDatasourceDao09() throws Exception {

        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();
        TerasolunaPropertyUtils
                .removeProperty("systemDataSource.transactionManager");
        TerasolunaPropertyUtils.addProperty(
                "systemDataSource.transactionManager", "adminQueryDao");
        exe.sysTransactionManager = null;
        exe.initSystemDatasourceDao();

        assertNull(exe.sysTransactionManager);
    }

    /**
     * testInitDefaultAppContext001
     * @throws Exception
     */
    public void testInitDefaultAppContext001() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        exe.initDefaultAppContext();
    }

    /**
     * testInitDefaultAppContext002
     * @throws Exception
     */
    public void testInitDefaultAppContext002() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils
                .removeProperty("beanDefinition.admin.classpath");
        TerasolunaPropertyUtils.removeProperty("beanDefinition.admin.default");

        exe.initDefaultAppContext();
    }

    /**
     * testInitDefaultAppContext003
     * @throws Exception
     */
    public void testInitDefaultAppContext003() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils
                .removeProperty("beanDefinition.admin.classpath");
        TerasolunaPropertyUtils
                .removeProperty("beanDefinition.admin.dataSource");

        exe.initDefaultAppContext();
    }

    /**
     * testInitDefaultAppContext004
     * @throws Exception
     */
    public void testInitDefaultAppContext004() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils
                .removeProperty("beanDefinition.admin.dataSource");
        TerasolunaPropertyUtils
                .removeProperty("beanDefinition.admin.dataSource");

        exe.initDefaultAppContext();
    }

    /**
     * testInitSystemDatasourceDao001
     * @throws Exception
     */
    public void testInitSystemDatasourceDao001() throws Exception {
        AbstractJobBatchExecutor exe = new AsyncBatchExecutor();

        TerasolunaPropertyUtils
                .removeProperty("beanDefinition.admin.dataSource");
        TerasolunaPropertyUtils
                .removeProperty("beanDefinition.admin.dataSource");

        exe.initDefaultAppContext();
        exe.initSystemDatasourceDao();
    }
}

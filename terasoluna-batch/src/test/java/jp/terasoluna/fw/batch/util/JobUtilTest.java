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

package jp.terasoluna.fw.batch.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.executor.dao.SystemDao;
import jp.terasoluna.fw.batch.executor.vo.*;
import jp.terasoluna.fw.batch.mock.MockSystemDao;
import jp.terasoluna.fw.ex.unit.io.impl.CollectionSource;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;
import jp.terasoluna.fw.ex.unit.util.AssertUtils;
import jp.terasoluna.fw.ex.unit.util.SystemEnvUtils;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import static org.mockito.Mockito.*;

public class JobUtilTest extends DaoTestCase {

    /**
     * ���p����DAO�N���X
     */
    private SystemDao systemDao = null;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        deleteFromTable("job_control");

        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000001', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000002', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000003', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
        update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000004', 'B000002', '0000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");

        systemDao = getBean("systemDao");
    }

    /**
     * testSelectJobList01
     * @throws Exception
     */
    public void testSelectJobList01() throws Exception {

        List<BatchJobListResult> result = JobUtil.selectJobList(this.systemDao);

        List<BatchJobListResult> except = new ArrayList<BatchJobListResult>();

        BatchJobListResult bean01 = new BatchJobListResult();
        bean01.setJobSequenceId("0000000001");
        BatchJobListResult bean02 = new BatchJobListResult();
        bean02.setJobSequenceId("0000000002");
        BatchJobListResult bean03 = new BatchJobListResult();
        bean03.setJobSequenceId("0000000003");
        BatchJobListResult bean04 = new BatchJobListResult();
        bean04.setJobSequenceId("0000000004");

        except.add(bean01);
        except.add(bean02);
        except.add(bean03);
        except.add(bean04);

        AssertUtils.assertInputEquals(new CollectionSource<BatchJobListResult>(
                except), new CollectionSource<BatchJobListResult>(result));

    }

    /**
     * testSelectJobList02
     * @throws Exception
     */
    public void testSelectJobList02() throws Exception {

        List<BatchJobListResult> list = JobUtil.selectJobList(this.systemDao, 0,
                2);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());

    }

    /**
     * testSelectJobList03
     * @throws Exception
     */
    public void testSelectJobList03() throws Exception {

        List<BatchJobListResult> list = JobUtil.selectJobList("B000002",
                this.systemDao);

        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());
        assertEquals("0000000003", list.get(2).getJobSequenceId());
        assertEquals("0000000004", list.get(3).getJobSequenceId());

    }

    /**
     * testSelectJobList04
     * @throws Exception
     */
    public void testSelectJobList04() throws Exception {

        List<BatchJobListResult> list = JobUtil.selectJobList("B000002",
                this.systemDao, 0, 2);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());

    }

    /**
     * testSelectJobList05
     * @throws Exception
     */
    public void testSelectJobList05() throws Exception {

        List<String> curAppStatusList = new ArrayList<String>();

        List<BatchJobListResult> list = JobUtil.selectJobList("B000002",
                curAppStatusList, this.systemDao, 0, 2);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("0000000001", list.get(0).getJobSequenceId());
        assertEquals("0000000002", list.get(1).getJobSequenceId());

    }

    /**
     * testSelectJobList06<br>
     * ���O�����F<br>
     * selectJobList���\�b�h�ɑ΂��āA�ȉ��̒l�������Ƃ��Ď��s<br>
     * �E�J�n�C���f�b�N�X�A�擾������-1�̒l��^����<br>
     * �EQueryDao��MockQueryDao��^����<br>
     * <br>
     * ���Ҍ��ʁF<br>
     * �EBatchException���Ԃ邱��<br>
     * <br>
     * @throws Exception
     */
    public void testSelectJobList06() throws Exception {
        try {
            JobUtil.selectJobList("hoge", new MockSystemDao(), -1, -1);
            fail("��O���������܂���ł���");
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(BatchException.class, e.getClass());
        }
    }

    /**
     * testSelectJob01
     * @throws Exception
     */
    public void testSelectJob01() throws Exception {

        BatchJobData result = JobUtil.selectJob("0000000001", true,
                this.systemDao);

        assertEquals("0000000001", result.getJobSequenceId());
        assertEquals("B000002", result.getJobAppCd());

    }

    /**
     * testSelectJob02
     * @throws Exception
     */
    public void testSelectJob02() throws Exception {
        MockSystemDao mockSysQueryDao = new MockSystemDao();
        mockSysQueryDao.addResult(new RuntimeException("��O�������̃��b�Z�[�W"));
        assertEquals(null, JobUtil.selectJob("hoge", true, mockSysQueryDao));
    }

    public void testSelectJob03() throws Exception {
        SystemDao systemDao = mock(SystemDao.class);
        when(systemDao.selectJob(any(BatchJobManagementParam.class)))
                .thenThrow(new DataAccessException("DB�X�e�[�^�X�擾����O�m�F�p") {});

        try {
            JobUtil.selectJob("0000000001", true,
                systemDao);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DB�X�e�[�^�X�擾����O�m�F�p", e.getMessage());
        }
    }

    /**
     * testGetCurrentTime01
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentTime01() throws Exception {

        Timestamp result = JobUtil.getCurrentTime(this.systemDao);

        assertNotNull(result);
        System.out.println(result);

    }

    /**
     * testGetCurrentTime02
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentTime02() throws Exception {

        Timestamp result = JobUtil.getCurrentTime(null);

        assertNull(result);
    }

    public void testGetCurrentTime03() throws Exception {
        SystemDao systemDao = mock(SystemDao.class);
        when(systemDao.readCurrentTime()).thenThrow(
                new DataAccessException("DB�X�e�[�^�X�擾����O�m�F�p") {});
        try {
            JobUtil.getCurrentTime(systemDao);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DB�X�e�[�^�X�擾����O�m�F�p", e.getMessage());
        }
    }

    /**
     * testGetCurrentDate01
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentDate01() throws Exception {

        Date result = JobUtil.getCurrentDate(this.systemDao);

        assertNotNull(result);

        Date today = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(sf.format(today), result.toString());

    }

    /**
     * testGetCurrentDate02
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void testGetCurrentDate02() throws Exception {

        Date result = JobUtil.getCurrentDate(null);

        assertNull(result);

    }

    /**
     * testGetenv01
     * @throws Exception
     */
    public void testGetenv01() throws Exception {
        String result = JobUtil.getenv("");
        assertEquals("", result);
    }

    /**
     * testGetenv02<br>
     * ���O�����F<br>
     * ���O�Ɉȉ��̃R�}���h��ݒ肵�A���ϐ���ݒ肵�Ă�������<br>
     * eclipse�Ŏ��s����ۂ͎��s�̍\���Őݒ肷�邱��<br>
     * SET JOB_APP_CD=B000001
     * @throws Exception
     */
    public void testGetenv02() throws Exception {
        try {
            SystemEnvUtils.setEnv("JOB_APP_CD", "B000001");
            String result = JobUtil.getenv("JOB_APP_CD");
            assertEquals("B000001", result);
        } finally {
            SystemEnvUtils.restoreEnv();
        }
    }

    /**
     * testUpdateJobStatus01
     * @throws Exception
     */
    public void testUpdateJobStatus01() throws Exception {

        boolean result = JobUtil.updateJobStatus("0000000002", "0", null, this.systemDao);

        assertEquals(true, result);

    }

    /**
     * testUpdateJobStatus02<br>
     * ���O�����F<br>
     * 1. updateJobStatus���\�b�h�ɑ΂��āA�ȉ��̒l�������Ƃ��Ď��s<br>
     * �EupdateJobTable()���s����DataAccessException�ȊO�̗�O���X���[����SystemDao<br>
     * ���Ҍ��ʁF<br>
     * 1. false���Ԃ邱��(���s���\�b�h����SystemDao.updateJobTable()���\�b�h�ōX�V���s�l�̗�O���X���[�����)<br>
     * @throws Exception
     */
    public void testUpdateJobStatus02() throws Exception {
        SystemDao mockDao = new SystemDao() {
            public List<BatchJobListResult> selectJobList(BatchJobListParam batchJobListParam) {
                return null;
            }

            public List<BatchJobListResult> selectJobList(RowBounds rowBaounds, BatchJobListParam batchJobListParam) {
                return null;
            }

            public BatchJobData selectJob(BatchJobManagementParam batchJobManagementParam) {
                return null;
            }

            public Timestamp readCurrentTime() {
                return new Timestamp(System.currentTimeMillis());
            }

            public java.sql.Date readCurrentDate() {
                return null;
            }

            public int updateJobTable(BatchJobManagementUpdateParam batchJobManagementUpdateParam) {
                throw new RuntimeException("��O�������̃��b�Z�[�W");
            }
        };
        assertFalse(JobUtil.updateJobStatus("hoge", "piyo", "foo", mockDao));
    }

    /**
     * testUpdateJobStatus03<br>
     * ���O�����F<br>
     * 1.updateJobStatus���\�b�h�ɑ΂��āA�ȉ��̒l�������Ƃ��Ď��s<br>
     * �EUpdateDao�̌��ʂ�-1��ԋp����SystemDao<br>
     * ���Ҍ��ʁF<br>
     * 1.false���Ԃ邱��(���s���\�b�h���ŗ�O����������)<br>
     * <br>
     * @throws Exception
     */
    public void testUpdateJobStatus03() throws Exception {
        SystemDao mockDao = new SystemDao() {
            public List<BatchJobListResult> selectJobList(BatchJobListParam batchJobListParam) {
                return null;
            }

            public List<BatchJobListResult> selectJobList(RowBounds rowBaounds, BatchJobListParam batchJobListParam) {
                return null;
            }

            public BatchJobData selectJob(BatchJobManagementParam batchJobManagementParam) {
                return null;
            }

            public Timestamp readCurrentTime() {
                return new Timestamp(System.currentTimeMillis());
            }

            public java.sql.Date readCurrentDate() {
                return null;
            }

            public int updateJobTable(BatchJobManagementUpdateParam batchJobManagementUpdateParam) {
                return -1;
            }
        };
        assertFalse(JobUtil.updateJobStatus("hoge", "piyo", "foo", mockDao));
    }

    public void testUpdateJobStatus04() throws Exception {
        SystemDao mockSystemDao = mock(SystemDao.class);
        when(mockSystemDao.updateJobTable(any(BatchJobManagementUpdateParam.class)))
                .thenThrow(new DataAccessException("DB�X�e�[�^�X�X�V����O�m�F�p") {});
        try {
            JobUtil.updateJobStatus("0000000002", "0", null, mockSystemDao);
            fail();
        } catch (DataAccessException e) {
            assertEquals("DB�X�e�[�^�X�X�V����O�m�F�p", e.getMessage());
        }
    }

	/**
     * testJobUtil001
     * @throws Exception
     */
    public void testJobUtil001() throws Exception {
        JobUtil ju = new JobUtil();
        assertNotNull(ju);
    }
}

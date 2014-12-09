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

package jp.terasoluna.fw.batch.executor.concurrent;

import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;

/**
 * ���O����<br>
 * <br>
 * 
 * �E�W���u�Ǘ��e�[�u���ɃW���u���o�^����Ă��邱�ƁB<br>
 * �EbeanDef�t�H���_�ɃW���uBean��`�t�@�C�������݂��邱�ƁB<br>
 * �EBean��`�t�@�C���ɐݒ肳�ꂽ�r�W�l�X���W�b�N�����݂��邱��<br>
 * 
 */
public class BatchServantImplTest extends DaoTestCase {

	
	@Override
	protected void onSetUpBeforeTransaction() throws Exception {
		deleteFromTable("job_control");

		update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000001', 'B000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
		update("INSERT INTO job_control (job_seq_id, job_app_cd, job_arg_nm1, job_arg_nm2, job_arg_nm3, job_arg_nm4, job_arg_nm5, job_arg_nm6, job_arg_nm7, job_arg_nm8, job_arg_nm9, job_arg_nm10, job_arg_nm11, job_arg_nm12, job_arg_nm13, job_arg_nm14, job_arg_nm15, job_arg_nm16, job_arg_nm17, job_arg_nm18, job_arg_nm19, job_arg_nm20, blogic_app_status, cur_app_status, add_date_time, upd_date_time) VALUES ('0000000002', 'B000002', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL)");
	}

	/**
	 * testRun01()<br>
	 * <br>
	 * ���O��ԁF�W���u�Ǘ��e�[�u���̃W���u�V�[�P���X�R�[�h��0000000001���o�^����Ă��邱��<br>
	 * ���O��ԁF�W���u�Ǘ��e�[�u���̃W���u�Ɩ��R�[�h��B000001���o�^����Ă��邱��<br>
	 * <br>
	 * �e�X�g�T�v�F����ɃW���u�����s����邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځF�W���u�V�[�P���X�R�[�h������������<br>
	 * �m�F���ځF�r�W�l�X���W�b�N���ʂ�����������<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testRun01() throws Exception {

		BatchServantImpl exe = new BatchServantImpl();


		exe.setJobSequenceId("0000000001");
		exe.run();

		assertEquals(0, exe.getResult().getBlogicStatus());
		assertEquals("0000000001", exe.getJobSequenceId());
		
	}

	/**
	 * testRun02()<br>
	 * <br>
	 * ���O��ԁF�W���u�Ǘ��e�[�u���̃W���u�V�[�P���X�R�[�h��0000000000���o�^����Ă��Ȃ�����<br>
	 * <br>
	 * �e�X�g�T�v�F����ɃW���u�����s����邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځF�r�W�l�X���W�b�N���ʂ�����������<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testRun02() throws Exception {

		BatchServantImpl exe = new BatchServantImpl();
		exe.setJobSequenceId("0000000000");
		exe.run();

		assertEquals(-1, exe.getResult().getBlogicStatus());
	}



	/**
	 * testRun03()<br>
	 * <br>
	 * ���O��ԁF�W���u�Ǘ��e�[�u���̃W���u�V�[�P���X�R�[�h��0000000002���o�^����Ă��邱��<br>
	 * ���O��ԁF�W���u�Ǘ��e�[�u���̃W���u�Ɩ��R�[�h��B000002���o�^����Ă��邱��<br>
	 * <br>
	 * �e�X�g�T�v�F����ɃW���u�����s����邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځF�r�W�l�X���W�b�N���ʂ�����������<br>
	 * <br>
	 * 
	 * @throws Exception
	 */	public void testRun03() throws Exception {

		BatchServantImpl exe = new BatchServantImpl();
		exe.setJobSequenceId("0000000002");
		exe.run();

		assertEquals(1, exe.getResult().getBlogicStatus());
	}


}

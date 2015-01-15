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

import java.sql.Date;
import java.sql.Timestamp;

/**
 * ����DAO�C���^�t�F�[�X�B
 */
public interface CommonDao {

    /**
     * �V�[�P���X������V�X�e���R�[�h���擾����B
     *
     * @param seqName �V�[�P���X��
     * @return �V�X�e���R�[�h
     */
    String readSysCode(String seqName);

    /**
     * ���ݎ�����\���I�u�W�F�N�g���擾����B
     * @return ���ݎ�����\���I�u�W�F�N�g
     */
    Timestamp readCurrentTime();

    /**
     * ���ݓ��t��\���I�u�W�F�N�g���擾����B
     * @return ���ݓ��t��\���I�u�W�F�N�g
     */
    Date readCurrentDate();

}

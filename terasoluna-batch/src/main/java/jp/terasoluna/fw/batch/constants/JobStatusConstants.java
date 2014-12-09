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

package jp.terasoluna.fw.batch.constants;

/**
 * �W���u�X�e�[�^�X�p�萔��`�N���X�B
 */
public class JobStatusConstants {

    /**
     * �W���u�X�e�[�^�X�F�����{.
     */
    public static final String JOB_STATUS_UNEXECUTION = "0";

    /**
     * �W���u�X�e�[�^�X�F���s��.
     */
    public static final String JOB_STATUS_EXECUTING = "1";

    /**
     * �W���u�X�e�[�^�X�F������.
     */
    public static final String JOB_STATUS_PROCESSED = "2";

    /**
     * �W���u�X�e�[�^�X�F���s.
     */
    public static final String JOB_STATUS_FAILURE = "3";

}

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

import junit.framework.TestCase;

/**
 * RetryabbleExecuteException�̃e�X�g�P�[�X�B
 */
public class RetryableExecuteExceptionTest extends TestCase {

    /**
     * �R���X�g���N�^�̃e�X�g�P�[�X�B
     * �����ɓn����錴����O��not null�̂Ƃ��AgetCause()�ɂ�茴����O�Ƃ��̃��b�Z�[�W���擾�ł��邱�ƁB
     */
    public void testRetryableExecuteException01() {
        RetryableExecuteException exception = new RetryableExecuteException(new Exception("������O"));
        assertEquals("������O", exception.getCause().getMessage());
    }

    /**
     * �R���X�g���N�^�̃e�X�g�P�[�X�B
     * �����ɓn����錴����O��null�̂Ƃ��A�A�T�[�V�����G���[���������邱�ƁB
     */
    public void testRetryableExecuteException02() {
        try {
            RetryableExecuteException executeException = new RetryableExecuteException(null);
            fail("�A�T�[�V�����G���[���������܂���B");
        } catch (IllegalArgumentException e) {
        }
    }
}

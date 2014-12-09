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

package jp.terasoluna.fw.batch.executor;

import org.springframework.util.Assert;

/**
 * �񓯊��o�b�`�G�O�[�L���[�^���̃��g���C�\��O�B<br>
 * <br>
 * �񓯊��o�b�`�G�O�[�L���[�^���Ń��C�����\�b�h���Ń��g���C�\�ȃG���[�Ƃ��ĕߑ��\
 * �ł��邱�Ƃ��������߂̗�O�ł���B<br>
 * ���M���O���j�Ƃ��Ă͌݊������l�����A�{��O�N���X�Ō�����O�����b�v���ăX���[����ۂ̓��O�o�͂����A
 * ���C�����\�b�h���Ō�����O�ɑ΂��ďo�͂��邱�ƁB<br>
 * 
 * @see jp.terasoluna.fw.batch.executor.AbstractJobBatchExecutor
 * @see jp.terasoluna.fw.batch.executor.AsyncBatchExecutor
 */
public class RetryableExecuteException extends RuntimeException {

    /**
     * �V���A���o�[�W����UID�B
     */
    private static final long serialVersionUID = -298806643234717470L;

    /**
     * �R���X�g���N�^�B
     * �񓯊��o�b�`�G�O�[�L���[�^�̃��C�����\�b�h�Ń��g���C�\�ȗ�O��n�����ƁB<br>
     * 
     * @param cause ���g���C�\�Ȍ�����O
     * @see jp.terasoluna.fw.batch.executor.AsyncBatchExecutor#main(String[])
     */
    public RetryableExecuteException(Throwable cause) {
        super(cause);
        Assert.notNull(cause);
    }
}

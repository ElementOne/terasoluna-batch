/*
 * Copyright (c) 2012 NTT DATA Corporation
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

package jp.terasoluna.fw.collector.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * �L���[�C���O�̏I����ʒm����BlockingQueue�C���^�t�F�[�X
 */
public interface NotificationBlockingQueue<E> extends BlockingQueue<E> {
    /**
     * �L���[�C���O�̏I����ʒm����B
     * <p>
     * �L���[�ɗv�f������̂�҂��Ă���X���b�h������ꍇ�A���̃u���b�N����������B �L���[�ɗv�f���l�߂�X���b�h�́A�L���[�C���O�������������ƂŁA�K�����̃��\�b�h�����s���邱�ƁB
     * </p>
     */
    void finishQueueing();
}

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

package jp.terasoluna.fw.collector;

import junit.framework.TestCase;

/**
 * CollectorThreadFactory�̃e�X�g�P�[�X�B
 */
public class CollectorThreadFactoryTest extends TestCase {

    /**
     * �����X���b�h���f�[�����X���b�h�̏ꍇ�A��f�[�����X���b�h���擾�ł��邱�ƁB
     *
     * @throws Exception �\�����Ȃ���O
     */
    public void testNewThread01() throws Exception {
        Thread ownThread = new Thread();
        ownThread.setDaemon(true);
        CollectorThreadFactory factory = new CollectorThreadFactory();
        Thread target = factory.newThread(ownThread);
        assertFalse(target.isDaemon());
    }

    /**
     * �����X���b�h�����D��x�̏ꍇ�A�m�[�}���̗D��x�X���b�h���擾�ł��邱�ƁB
     *
     * @throws Exception �\�����Ȃ���O
     */
    public void testNewThread02() throws Exception {
        Thread ownThread = new Thread();
        ownThread.setPriority(Thread.MAX_PRIORITY);
        CollectorThreadFactory factory = new CollectorThreadFactory();
        Thread target = factory.newThread(ownThread);
        assertEquals(Thread.NORM_PRIORITY, target.getPriority());
    }
}

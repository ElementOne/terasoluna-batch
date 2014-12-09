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

package jp.terasoluna.fw.collector;

import java.io.Closeable;
import java.util.Iterator;

/**
 * �R���N�^�C���^�t�F�[�X<br>
 * @param &lt;P&gt;
 */
public interface Collector<P> extends Iterator<P>, Iterable<P>, Closeable {
    /**
     * 1���O�̗v�f��Ԃ��܂��B<br>
     * <p>
     * 1���ڂ̏ꍇ��null���Ԃ�܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * @return &lt;P&gt;
     */
    P getPrevious();

    /**
     * ���݂̗v�f��Ԃ��܂��B<br>
     * <p>
     * null�̏ꍇ�͌��݂̗v�f�����݂��Ȃ����Ƃ������܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * @return &lt;P&gt;
     */
    P getCurrent();

    /**
     * �|�C���^�����̗v�f�Ɉڂ����Ɏ��̗v�f��Ԃ��܂��B<br>
     * <p>
     * null�̏ꍇ�͎��̗v�f�����݂��Ȃ����Ƃ������܂��B<br>
     * �|�C���^�͈ړ����܂���B
     * </p>
     * @return &lt;P&gt;
     */
    P getNext();
}

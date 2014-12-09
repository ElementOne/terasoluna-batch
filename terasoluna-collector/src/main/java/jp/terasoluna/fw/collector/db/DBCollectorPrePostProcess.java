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

package jp.terasoluna.fw.collector.db;

/**
 * DBCollector��SQL���s���̑O�㏈���C���^�t�F�[�X
 */
public interface DBCollectorPrePostProcess {
    /**
     * SQL���s�J�n�O�Ɏ��s����郁�\�b�h.
     */
    <P> void preprocess(DBCollector<P> collector);

    /**
     * SQL���s�I�����Ɏ��s����郁�\�b�h.<br>
     * SQL���s���ɗ�O�����������ꍇ�́ApostprocessException���\�b�h�̎��ɁA���̃��\�b�h�����s�����B
     */
    <P> void postprocessComplete(DBCollector<P> collector);

    /**
     * SQL���s�I�����i��O�j�Ɏ��s����郁�\�b�h.
     */
    <P> DBCollectorPrePostProcessStatus postprocessException(
            DBCollector<P> collector, Throwable throwable);
}

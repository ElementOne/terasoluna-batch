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

package jp.terasoluna.fw.collector.db;

import org.apache.ibatis.session.ResultHandler;

/**
 * QueueingDataRowHandler�C���^�t�F�[�X<br>
 * DataRowHandler�̊g���C���^�t�F�[�X�B
 */
public interface QueueingDataRowHandler extends ResultHandler {

    /**
     * �O��handleRow���\�b�h�ɓn���ꂽ<code>Row</code>�f�[�^���L���[�Ɋi�[����B
     */
    void delayCollect();

    /**
     * DBCollector��ݒ肷��B<br>
     * @param dbCollector DBCollector&lt;?&gt;
     */
    void setDbCollector(DBCollector<?> dbCollector);
}

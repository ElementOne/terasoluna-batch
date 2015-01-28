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
 * QueueingResultHandler�C���^�t�F�[�X<br>
 * ResultHandler�̊g���C���^�t�F�[�X�B
 */
public interface QueueingResultHandler extends ResultHandler {

    /**
     * �O��handleResult���\�b�h�ɓn���ꂽ<code>Row</code>�f�[�^���L���[�Ɋi�[����B
     */
    void delayCollect();

    /**
     * DaoCollector��ݒ肷��B<br>
     * @param daoCollector daoCollector&lt;?&gt;
     */
    void setDaoCollector(DaoCollector<?> daoCollector);
}

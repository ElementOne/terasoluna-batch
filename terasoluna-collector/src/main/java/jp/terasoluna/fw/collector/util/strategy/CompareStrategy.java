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

package jp.terasoluna.fw.collector.util.strategy;

/**
 * 2�̃I�u�W�F�N�g�����������������Ȃ����𔻒f������@������/�񋟂��邽�߂̃C���^�t�F�[�X�B
 */
public interface CompareStrategy<T> {

    /**
     * value1��value2�����������ۂ��𔻒f����B
     * @param value1 ��r���inull�ȊO�̃I�u�W�F�N�g�j�B
     * @param value2 ��r��inull�ȊO�̃I�u�W�F�N�g�j�B
     * @return value1��value2���������ꍇtrue/�������Ȃ��ꍇfalse
     */
    public boolean equalsObjects(T value1, T value2);

}

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
 * CompareStrategy�����N���X.<br>
 * ��r�ΏۃI�u�W�F�N�g��equals���\�b�h�Ŕ�r����X�g���e�W�B<br>
 * �R���g���[���u���C�N����ɂ����āA
 * <ul>
 * <li>Comparable�����N���X�����AcompareTo���\�b�h�ł͂Ȃ�equals���\�b�h�Ŕ�r������</li>
 * <li>Comparable�����N���X�ł͂Ȃ����AEqualsBuilder#reflectionEquals�ł͂Ȃ�equals���\�b�h�Ŕ�r������</li>
 * </ul>
 * �Ƃ����ꍇ�ɁA���̃N���X�𗘗p����B<br>
 * �Ȃ��A���̃N���X�̓X�e�[�g���X�ł��邽�߁A��r�̂��тɃC���X�^���X���쐬���Ȃ����K�v�͖����B
 */
public class EqualsCompareStrategy implements CompareStrategy<Object> {

    /**
     * {@inheritDoc}
     */
    public boolean equalsObjects(Object value1, Object value2) {
        return (value1.equals(value2));
    }
}

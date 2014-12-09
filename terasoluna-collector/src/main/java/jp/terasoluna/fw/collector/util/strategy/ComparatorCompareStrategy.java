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

import java.util.Comparator;

/**
 * CompareStrategy�����N���X.<br>
 * �O��Comparator��compare���\�b�h�Ŕ�r����X�g���e�W�B
 * compare���\�b�h��0��Ԃ��Γ������A0�ȊO��Ԃ��Γ������Ȃ��A�Ƃ������ʂɂȂ�B<br>
 * ���̃N���X�́A������Comparator��CompareStrategy�Ƃ��ė��p���邽�߂ɗp�ӂ��Ă���B<br>
 * �R���g���[���u���C�N����̂��߂ɁA�V�K�ɔ�r���W�b�N����������ꍇ�́A
 * ���̃N���X�ɗ^����Comparator��V�K�ɍ쐬����̂ł͂Ȃ��A
 * CompareStrategy�����N���X���쐬���邱�Ƃ𐄏�����B<br>
 * (CompareStrategy��Comparator�ƈقȂ�A2�̃I�u�W�F�N�g�̑召�֌W�����߂�d�l���s�v�B)<br>
 * �Ȃ��A�O��Comparator���X�e�[�g���X�ł���΁A���̃N���X���X�e�[�g���X�ł���B
 * ���̂��߁A�O��Comparator���X�e�[�g���X�ł���΁A
 * ��r�̂��тɊO��Comparator�₱�̃N���X�̃C���X�^���X���쐬���Ȃ����K�v�͖����B
 * @see Comparator
 * @see CompareStrategy
 */
public class ComparatorCompareStrategy implements CompareStrategy<Object> {

    /**
     * �O��Comparator.
     */
    @SuppressWarnings("rawtypes")
    private Comparator comparator = null;

    /**
     * �R���X�g���N�^.
     * @param comparator 2�̃I�u�W�F�N�g���r����Comparator
     */
    public ComparatorCompareStrategy(Comparator<?> comparator) {
        this.comparator = comparator;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean equalsObjects(Object value1, Object value2) {
        return (comparator.compare(value1, value2) == 0);
    }
}

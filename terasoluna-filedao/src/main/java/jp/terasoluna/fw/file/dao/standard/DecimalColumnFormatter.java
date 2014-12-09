/*
 * Copyright (c) 2007 NTT DATA Corporation
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

package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * �J�����t�H�[�}�b�g(�t�@�C�������j���s���N���X�B
 * <p>
 * �t�@�C���s�I�u�W�F�N�g����f�[�^���擾���A�������FileUpdateDAO�ɕԋp����B �A�m�e�[�V�����̋L�q�ɏ]��Date�^�̃t�H�[�}�b�g�������s���B
 * </p>
 */
public class DecimalColumnFormatter implements ColumnFormatter {

    /**
     * ���l�t�H�[�}�b�g�ɑΉ�����<code>DecimalFormat</code>��ێ�����}�b�v�B
     */
    private Map<String, DecimalFormatLocal> dfMap = new ConcurrentHashMap<String, DecimalFormatLocal>();

    /**
     * BigDecimal�^�̃t�H�[�}�b�g�������s���A�������ԋp����B
     * <p>
     * ����<code>columnFormat</code>��<code>null</code>�������́A �󕶎��������ꍇ�́A�t�H�[�}�b�g������������Ƃ��ĕԋp����B
     * @param t �t�@�C���s�I�u�W�F�N�g
     * @param method �J�����t�H�[�}�b�g���s�������̃Q�b�^���\�b�h
     * @param columnFormat �J�����t�H�[�}�b�g�p�̕�����
     * @return ������
     * @throws IllegalArgumentException �t�@�C���s�I�u�W�F�N�g��getter���\�b�h�̃A�N�Z�X�Ɏ��s�����Ƃ�
     * @throws IllegalAccessException �t�@�C���s�I�u�W�F�N�g�ւ̐ݒ肪���s�����Ƃ�
     * @throws InvocationTargetException �t�@�C���s�I�u�W�F�N�g�̃��\�b�h����O���X���[�����Ƃ�
     */
    public String format(Object t, Method method, String columnFormat)
                                                                      throws IllegalArgumentException,
                                                                      IllegalAccessException,
                                                                      InvocationTargetException {

        Object column = method.invoke(t);

        // �J�����l��null�̏ꍇ�͋󕶎���Ԃ��B
        if (column == null) {
            return "";
        }

        // ���l�̃t�H�[�}�b�g
        if (columnFormat != null && !"".equals(columnFormat)) {
            DecimalFormatLocal dfLocal = dfMap.get(columnFormat);
            if (dfLocal == null) {
                dfLocal = new DecimalFormatLocal(columnFormat);
                dfMap.put(columnFormat, dfLocal);
            }
            return dfLocal.get().format(column);
        } else {
            return BigDecimal.class.cast(column).toPlainString();
        }

    }
}

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * DateFormat��ThreadLocal�N���X�B<br>
 * SimpleDateFormat���X���b�h�Z�[�t�ł͂Ȃ����߁AThreadLocal���g�p����<br>
 * �X���b�h�Z�[�t�ɂ���B
 */
public class DateFormatLocal extends ThreadLocal<DateFormat> {

    /**
     * �t�H�[�}�b�g�p�^�[��
     */
    private String pattern = null;

    /**
     * �R���X�g���N�^
     */
    public DateFormatLocal(String pattern) {
        this.pattern = pattern;
    }

    /**
     * �������B
     * @return DateFormat�C���X�^���X
     */
    @Override
    protected DateFormat initialValue() {
        // �X���b�h���̏�����
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        return sdf;
    }
}

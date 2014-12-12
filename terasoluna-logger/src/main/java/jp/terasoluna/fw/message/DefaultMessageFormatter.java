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
package jp.terasoluna.fw.message;

import java.text.MessageFormat;

/**
 * �f�t�H���g���b�Z�[�W�t�H�[�}�b�^�����B <br>
 * <p>
 * {@link MessageFormat}�N���X�Ńt�H�[�}�b�g���܂��B
 * </p>
 * 
 */
public class DefaultMessageFormatter implements MessageFormatter {

    /*
     * (non-Javadoc)
     * 
     * @see jp.terasoluna.fw.message.MessageFormatter#format(java.lang.String, java.lang.Object[])
     */
    public String format(String pattern, Object... args) {
        return MessageFormat.format(pattern, args);
    }

}

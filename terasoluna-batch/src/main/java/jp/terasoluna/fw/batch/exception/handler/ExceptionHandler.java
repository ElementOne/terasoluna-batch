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

package jp.terasoluna.fw.batch.exception.handler;

/**
 * ��O�n���h���C���^�t�F�[�X.
 */
public interface ExceptionHandler {

    /**
     * �r�W�l�X���W�b�N��O���̃n���h�����O
     * @param e ����������O
     * @return ��O�n���h�����O��̃X�e�[�^�X��Ԃ��B
     */
    int handleThrowableException(Throwable e);

}

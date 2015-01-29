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

package jp.terasoluna.fw.batch.mock;

/**
 * �R�[���o�b�N�C���^�t�F�[�X�ł��B
 * 
 * <p>
 * ���b�NRepository�̃��\�b�h�ŕԂ�l�̌^��void�̂��̂ɑ΂��āA���������b�N�����邽�߂Ɏg�p���܂��B
 * </p>
 */
public interface Callback {
    /**
     * �R�[���o�b�N�����s���܂��B
     * 
     * @param args �R�[���o�b�N����
     */
    void execute(Object[] args);
}

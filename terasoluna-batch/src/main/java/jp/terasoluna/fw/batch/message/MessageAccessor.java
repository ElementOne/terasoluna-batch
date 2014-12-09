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

package jp.terasoluna.fw.batch.message;

/**
 * ���b�Z�[�W���擾����C���^�t�F�[�X�B<br>
 */
public interface MessageAccessor {

    /**
     * ���b�Z�[�W���擾����B<br>
     * ���b�Z�[�WID�Ŏw�肵�����b�Z�[�W���擾���܂��B<br>
     * ���b�Z�[�WID�ɑΉ����郁�b�Z�[�W�����݂��Ȃ��ꍇ�A��O�iNoSuchMessageException�j���X���[����܂��B<br>
     * �u��������z�񂪈����ɓn���ꂽ�ꍇ�A���b�Z�[�W��u�����ĕԋp���܂��B<br>
     * �u����������w�肵�Ȃ��ꍇ�Anull���w�肵�Ă��������B<br>
     * �u��������z��̗v�f���͒u�������̏����ƑΉ�����悤�ɐݒ肵�Ă��������B
     * @param code ���b�Z�[�WID
     * @param args �u��������̔z��
     * @return String ���b�Z�[�W
     */
    public String getMessage(String code, Object[] args);

}

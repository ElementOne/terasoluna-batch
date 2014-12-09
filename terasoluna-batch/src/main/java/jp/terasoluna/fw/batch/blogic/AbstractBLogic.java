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

package jp.terasoluna.fw.batch.blogic;

import org.springframework.context.support.ApplicationObjectSupport;

/**
 * �r�W�l�X���W�b�N���ۃN���X�B<br>
 * <br>
 * �C�ӂɃg�����U�N�V�������Ǘ��������ꍇ��AbstractBLogic���p�����邱�ƁB<br>
 * BLogic�C���^�t�F�[�X�Ƃ̑���_�Ƃ��āAApplicationObjectSupport���p�����Ă���A<br>
 * �p���N���X���œǂݍ��񂾃R���e�i����Bean�𒼐ڎ擾���邱�Ƃ��\�B<br>
 * @see org.springframework.context.support.ApplicationObjectSupport
 * @see jp.terasoluna.fw.batch.blogic.BLogic
 * @see jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic
 */
public abstract class AbstractBLogic extends ApplicationObjectSupport implements
                                                                     BLogic {
}

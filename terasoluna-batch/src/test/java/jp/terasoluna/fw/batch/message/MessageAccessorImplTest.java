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

import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jp.terasoluna.fw.batch.message.MessageAccessor;
import jp.terasoluna.fw.batch.util.MessageUtil;
import jp.terasoluna.fw.util.PropertyUtil;
import junit.framework.TestCase;

/**
 * ���O����<br>
 * <br>
 * 
 * �Esrc/test/resources�t�H���_�z����AppricationResources.properties�����݂��邱�ƁB<br>
 * <br>
 * 
 * �E�v���p�e�BMessageAccessor.default�̒l���ݒ肳��Ă��邱�ƁB<br>
 * <fieldset><legend>batch.properties�ݒ��</legend> #���b�Z�[�W�\�[�X�A�N�Z�T��Bean��<br>
 * MessageAccessor.default=msgAcc
 * 
 * </fieldset> <br>
 * �EBean��`�t�@�C���Ƀv���p�e�B�Őݒ肳�ꂽ�̒l��Bean�����ݒ肳��Ă��邱�ƁB<br>
 * <fieldset><legend>AdminContext.xml�ݒ��</legend>
 * 
 * &lt;!-- ���b�Z�[�W�A�N�Z�T --&gt;<br>
 * &lt;bean id=&quot;msgAcc&quot;
 * class=&quot;jp.terasoluna.fw.batch.message.MessageAccessorImpl&quot;
 * /&gt;
 * 
 * </fieldset> <br>
 * �Emessages.properties�t�@�C�������݂��邱��<br>
 * 
 */
public class MessageAccessorImplTest extends TestCase {

	/**
	 * �v���p�e�B�l�擾�l
	 */
	private String value = null;

	/**
	 * �R���e�i�p�̃t�B�[���h
	 */
	private ApplicationContext context;

	/**
	 * MessageAccessor�N���X�̃t�B�[���h
	 */
	private MessageAccessor messageAccessor;

	@Override
	protected void setUp() throws Exception {

		// ���b�Z�[�W�\�[�X�A�N�Z�T��Bean���擾
		context = new ClassPathXmlApplicationContext(
				"beansDef/AdminContext.xml");
		value = PropertyUtil.getProperty("messageAccessor.default");
		messageAccessor = (MessageAccessor) context.getBean(value,
				MessageAccessor.class);
	}

	/**
	 * testGetMessage01()<br>
	 * <br>
	 * ���O��ԁFerrors.alphaNumericString���ݒ肳��Ă��邱��<br>
	 * <br>
	 * �e�X�g�T�v�F�p�����[�^��1�����b�Z�[�W�L�[�ɊY�����郁�b�Z�[�W�𐳏�Ɏ擾���邱�Ƃ��ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځF�v���p�e�B�ɐݒ肵�����b�Z�[�W���擾����Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessage01() throws Exception {

		String[] args = { "test1" };

		String result = messageAccessor.getMessage("errors.alphaNumericString",
				args);

		assertEquals("test1�ɂ͔��p�p�����œ��͂��Ă�������.", result);
	}

	/**
	 * testGetMessage02()<br>
	 * <br>
	 * ���O��ԁFerrors.alphaNumericString���ݒ肳��Ă��邱��<br>
	 * <br>
	 * �e�X�g�T�v�F�p�����[�^��null��ݒ肵�A
	 * ���b�Z�[�W�L�[�ɊY�����郁�b�Z�[�W���擾�����Ƃ��p�����[�^���ϊ����ꂸ�Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځF�v���p�e�B�ɐݒ肵�����b�Z�[�W���擾����Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessage02() throws Exception {

		String[] args = { "test1" };

		String result = messageAccessor.getMessage("errors.alphaNumericString",
				null);

		assertEquals("{0}�ɂ͔��p�p�����œ��͂��Ă�������.", result);
	}

	/**
	 * testGetMessage03()<br>
	 * <br>
	 * �e�X�g�T�v�F���݂��Ȃ����b�Z�[�W�L�[�̏ꍇ�ANoSuchMessageException���X���[����邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFNoSuchMessageException���X���[����Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessage03() throws Exception {

		try {
			String result = messageAccessor.getMessage("test", null);
			fail();
		} catch (NoSuchMessageException e) {
			// �������Ȃ�
		}

	}

	/**
	 * testGetMessage03()<br>
	 * <br>
	 * �e�X�g�T�v�F���b�Z�[�W�L�[�ɋ󕶎���ݒ肵���ꍇ�ANoSuchMessageException���X���[����邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFNoSuchMessageException���X���[����Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessage04() throws Exception {

		try {
			String result = messageAccessor.getMessage("", null);
			fail();
		} catch (NoSuchMessageException e) {
			// �������Ȃ�
		}
	}

	/**
	 * testGetMessage03()<br>
	 * <br>
	 * �e�X�g�T�v�F���b�Z�[�W�L�[��null��ݒ肵���ꍇ�ANoSuchMessageException���X���[����邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFNoSuchMessageException���X���[����Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessage05() throws Exception {

		try {
			String result = messageAccessor.getMessage(null, null);
			fail();
		} catch (NoSuchMessageException e) {
			// �������Ȃ�
		}
	}

}

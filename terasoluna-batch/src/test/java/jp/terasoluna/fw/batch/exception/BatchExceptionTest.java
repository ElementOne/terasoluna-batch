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

package jp.terasoluna.fw.batch.exception;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jp.terasoluna.fw.batch.exception.BatchException;
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
 * <fieldset><legend>batch.properties�ݒ��</legend>
 * #���b�Z�[�W�\�[�X�A�N�Z�T��Bean��<br>
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
public class BatchExceptionTest extends TestCase {

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
		MessageUtil.setMessageAccessor(messageAccessor);
	}
	/**
	 * testBatchException01()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testBatchException01() throws Exception {

		BatchException result = new BatchException();
		assertNotNull(result);
	}

	/**
	 * testBatchException02()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFmessage��"test"���ݒ肳��Ă��邱��<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testBatchException02() throws Exception {

		BatchException result = new BatchException("test");
		assertNotNull(result);
		assertEquals("test", result.getMessage());
	}

	/**
	 * testBatchException03()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFmessage��"test"���ݒ肳��Ă��邱��<br>
	 * �m�F���ځFcause��RuntimeException�ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testBatchException03() throws Exception {

		BatchException result = new BatchException("test",
				new RuntimeException());

		assertNotNull(result);
		assertEquals("test", result.getMessage());
		assertEquals("java.lang.RuntimeException", result.getCause().toString());
	}

	/**
	 * testBatchException04()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFcause��RuntimeException�ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testBatchException04() throws Exception {

		BatchException result = new BatchException(new RuntimeException());

		assertNotNull(result);
		assertEquals("java.lang.RuntimeException", result.getCause().toString());
	}

	/**
	 * testCreateException01()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFerrors.required�L�[�̃��b�Z�[�W���ݒ肳��Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException01() throws Exception {

		BatchException result = BatchException
				.createException("errors.required");

		assertNotNull(result);
		assertEquals("{0}�͓��͕K�{���ڂł�.", result.getMessage());
	}

	/**
	 * testCreateException02()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځF���b�Z�[�W���ݒ肳��Ă��Ȃ����Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException02() throws Exception {

		BatchException result = BatchException.createException(null);

		assertNotNull(result);
		assertEquals("Message not found. CODE:[null]", result.getMessage());
	}

	/**
	 * testCreateException03()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځF���b�Z�[�W���ݒ肳��Ă��Ȃ����Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException03() throws Exception {

		BatchException result = BatchException.createException("");

		assertNotNull(result);
		assertEquals("Message not found. CODE:[]", result.getMessage());
	}

	/**
	 * testCreateException04()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFaerrors.required�L�[�̃��b�Z�[�W��arg���ݒ肳��Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException04() throws Exception {

		BatchException result = BatchException.createException(
				"errors.required", "test1");

		assertNotNull(result);
		assertEquals("test1�͓��͕K�{���ڂł�.", result.getMessage());
	}

	/**
	 * testCreateException05()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFaerrors.required�L�[�̃��b�Z�[�W��arg��3�ݒ肳��Ă��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException05() throws Exception {

		BatchException result = BatchException.createException("errors.range",
				"test1", "10", "20");

		assertNotNull(result);
		assertEquals("test1�ɂ�10����20�܂ł͈̔͂œ��͂��Ă�������.", result.getMessage());
	}

	/**
	 * testCreateException06()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFerrors.required�L�[�̃��b�Z�[�W���ݒ肳��Ă��邱�Ƃ��m�F����<br>
	 * �m�F���ځFcause��RuntimeException�ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException06() throws Exception {

		BatchException result = BatchException.createException(
				"errors.required", new RuntimeException());

		assertNotNull(result);
		assertEquals("{0}�͓��͕K�{���ڂł�.", result.getMessage());
		assertEquals("java.lang.RuntimeException", result.getCause().toString());
		
	}

	/**
	 * testCreateException07()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFaerrors.required�L�[�̃��b�Z�[�W��arg���ݒ肳��Ă��邱�Ƃ��m�F����<br>
	 * �m�F���ځFcause��RuntimeException�ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException07() throws Exception {

		BatchException result = BatchException.createException(
				"errors.required", new RuntimeException(), "test1");

		assertNotNull(result);
		assertEquals("test1�͓��͕K�{���ڂł�.", result.getMessage());
		assertEquals("java.lang.RuntimeException", result.getCause().toString());
	}

	/**
	 * testCreateException08()<br>
	 * <br>
	 * �e�X�g�T�v�FBatchException���C���X�^���X���ꂽ���Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFBatchException��null�łȂ�����<br>
	 * �m�F���ځFerrors.range�L�[�̃��b�Z�[�W��arg��3�ݒ肳��Ă��邱�Ƃ��m�F����<br>
	 * �m�F���ځFcause��RuntimeException�ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testCreateException08() throws Exception {

		BatchException result = BatchException.createException("errors.range",
				new RuntimeException(), "test1", "10", "20");

		assertNotNull(result);
		assertEquals("test1�ɂ�10����20�܂ł͈̔͂œ��͂��Ă�������.", result.getMessage());
		assertEquals("java.lang.RuntimeException", result.getCause().toString());
	}

	/**
	 * testGetLogMessage01()<br>
	 * <br>
	 * �e�X�g�T�v�F�ԋp���ꂽ���O���b�Z�[�W������ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFerrors.required�L�[�̃��b�Z�[�W�����������O���b�Z�[�W�ƂȂ��Ă��邩�m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetLogMessage01() throws Exception {

		BatchException exception = BatchException.createException(
				"errors.required", "test1");

		String result = exception.getLogMessage();

		assertEquals("[errors.required] test1�͓��͕K�{���ڂł�. (\n\ttest1\n)", result);

	}

	/**
	 * testGetMessageId01()<br>
	 * <br>
	 * �e�X�g�T�v�F�ݒ肳�ꂽ���b�Z�[�W�L�[�����������b�Z�[�W�L�[���m�F����<br>
	 * <br>
	 * �m�F���ځFerrors.required�L�[�̃��b�Z�[�W���������擾�ł��邩�m�F����<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessageId01() throws Exception {

		BatchException exception = BatchException.createException(
				"errors.required", "test1");

		String result = exception.getMessageId();

		assertEquals("errors.required", result);

	}

	/**
	 * testGetMessageId02()<br>
	 * <br>
	 * �e�X�g�T�v�F�ݒ肳�ꂽ���b�Z�[�W�L�[��null�̏ꍇ�A���ʂ�null�ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځFnull�ł��邱��<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessageId02() throws Exception {

		BatchException exception = BatchException.createException(
				null);

		String result = exception.getMessageId();

		assertNull(result);

	}
	

	/**
	 * testGetMessageId03()<br>
	 * <br>
	 * �e�X�g�T�v�F�ݒ肳�ꂽ���b�Z�[�W�L�[���󕶎��̏ꍇ�A���ʂ��󕶎��ł��邱�Ƃ��m�F����<br>
	 * <br>
	 * �m�F���ځF�󕶎��ł��邱��<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void testGetMessageId03() throws Exception {

		BatchException exception = BatchException.createException(
				"");

		String result = exception.getMessageId();

		assertEquals("", result);

	}
	
	
}

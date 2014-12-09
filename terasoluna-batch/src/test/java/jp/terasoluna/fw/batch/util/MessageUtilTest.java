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

package jp.terasoluna.fw.batch.util;

import jp.terasoluna.fw.batch.message.MessageAccessor;
import jp.terasoluna.fw.ex.unit.util.ReflectionUtils;
import jp.terasoluna.fw.util.PropertyUtil;
import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ���O����<br>
 * <br>
 * �Esrc/test/resources�t�H���_�z����AppricationResources.properties�����݂��邱�ƁB<br>
 * <br>
 * �E�v���p�e�BMessageAccessor.default�̒l���ݒ肳��Ă��邱�ƁB<br>
 * <fieldset><legend>batch.properties�ݒ��</legend> #���b�Z�[�W�\�[�X�A�N�Z�T��Bean��<br>
 * MessageAccessor.default=msgAcc </fieldset> <br>
 * �EBean��`�t�@�C���Ƀv���p�e�B�Őݒ肳�ꂽ�̒l��Bean�����ݒ肳��Ă��邱�ƁB<br>
 * <fieldset><legend>AdminContext.xml�ݒ��</legend> &lt;!-- ���b�Z�[�W�A�N�Z�T --&gt;<br>
 * &lt;bean id=&quot;msgAcc&quot; class=&quot;jp.terasoluna.fw.batch.message.MessageAccessorImpl&quot; /&gt; </fieldset> <br>
 * �Emessages.properties�t�@�C�������݂��邱��<br>
 */
public class MessageUtilTest extends TestCase {

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
     * testGetMessage01()<br>
     * <br>
     * ���O��ԁFmessages.properties��errors.alphaNumericString���ݒ肳��Ă��邱��<br>
     * <br>
     * �e�X�g�T�v�F���b�Z�[�W�L�[�ɊY�����郁�b�Z�[�W�𐳏�Ɏ擾���邱�Ƃ��ł��邱�Ƃ��m�F����<br>
     * <br>
     * �m�F���ځF�v���p�e�B�ɐݒ肵�����b�Z�[�W���擾����Ă��邱�Ƃ��m�F����<br>
     * <br>
     * @throws Exception
     */
    public void testGetMessage01() throws Exception {

        String result = MessageUtil.getMessage("errors.alphaNumericString");

        assertEquals("{0}�ɂ͔��p�p�����œ��͂��Ă�������.", result);
    }

    /**
     * testGetMessage02()<br>
     * <br>
     * ���O��ԁFerrors.alphaNumericString���ݒ肳��Ă��邱��<br>
     * <br>
     * �e�X�g�T�v�F�p�����[�^��1�����b�Z�[�W�L�[�ɊY�����郁�b�Z�[�W�𐳏�Ɏ擾���邱�Ƃ��ł��邱�Ƃ��m�F����<br>
     * <br>
     * �m�F���ځF�v���p�e�B�ɐݒ肵�����b�Z�[�W���擾����Ă��邱�Ƃ��m�F����<br>
     * <br>
     * @throws Exception
     */
    public void testGetMessage02() throws Exception {

        String[] args = { "test1" };
        String result = MessageUtil.getMessage("errors.alphaNumericString",
                args);

        assertEquals("test1�ɂ͔��p�p�����œ��͂��Ă�������.", result);
    }

    /**
     * testGetMessage03()<br>
     * <br>
     * ���O��ԁFerrors.range���ݒ肳��Ă��邱��<br>
     * <br>
     * �e�X�g�T�v�F�p�����[�^���R�����b�Z�[�W�L�[�ɊY�����郁�b�Z�[�W�𐳏�Ɏ擾���邱�Ƃ��ł��邱�Ƃ��m�F����<br>
     * <br>
     * �m�F���ځF�v���p�e�B�ɐݒ肵�����b�Z�[�W���擾����Ă��邱�Ƃ��m�F����<br>
     * <br>
     * @throws Exception
     */
    public void testGetMessage03() throws Exception {

        Object[] args = { "test1", 10, 20 };
        String result = MessageUtil.getMessage("errors.range", args);

        assertEquals("test1�ɂ�10����20�܂ł͈̔͂œ��͂��Ă�������.", result);
    }

    /**
     * testGetMessage04()<br>
     * <br>
     * ���O��ԁFtest�Ƃ������b�Z�[�W�L�[���ݒ肳��Ă��Ȃ�����<br>
     * <br>
     * �e�X�g�T�v�F���b�Z�[�W�L�[�ɊY�����郁�b�Z�[�W���擾���邱�Ƃ��ł��Ȃ����Ƃ��m�F����<br>
     * <br>
     * �m�F���ځF�G���[���b�Z�[�W���擾����Ă��邱�Ƃ��m�F����<br>
     * <br>
     * @throws Exception
     */
    public void testGetMessage04() throws Exception {

        String[] args = { "test1" };
        String result = MessageUtil.getMessage("test", args);

        assertNotNull(result);
        assertEquals("Message not found. CODE:[test]", result);
    }

    /**
     * testGetMessage04()<br>
     * <br>
     * �e�X�g�T�v�Fnull��ݒ肵���ꍇ�ɊY�����郁�b�Z�[�W���擾���邱�Ƃ��ł��Ȃ����Ƃ��m�F����<br>
     * <br>
     * �m�F���ځF�G���[���b�Z�[�W���擾����Ă��邱�Ƃ��m�F����<br>
     * <br>
     * @throws Exception
     */
    public void testGetMessage05() throws Exception {

        String result = MessageUtil.getMessage(null);

        assertNotNull(result);
        assertEquals("Message not found. CODE:[null]", result);
    }

    /**
     * testGetMessage06
     */
    public void testGetMessage06() {
        Thread th = Thread.currentThread();
        ThreadGroup g = th.getThreadGroup();
        try {
            ReflectionUtils.setField(th, "group", null);
            String result = MessageUtil.getMessage("hoge");
            assertEquals("Message not found. CODE:[hoge]", result);
        } finally {
            ReflectionUtils.setField(th, "group", g);
        }
    }

    /**
     * testSetMessageAccessor01
     * @throws Exception
     */
    public void testSetMessageAccessor01() throws Exception {
        MessageUtil.setMessageAccessor(null);
        assertTrue(true);
    }

    /**
     * testRemoveMessageAccessor01
     * @throws Exception
     */
    public void testRemoveMessageAccessor01() throws Exception {
        MessageUtil.removeMessageAccessor();
        assertTrue(true);
    }

    /**
     * testGetThreadGroup01
     * @throws Exception
     */
    public void testGetThreadGroup01() throws Exception {
        // Thread.currentThread().setContextClassLoader(null);
        ReflectionUtils.invoke(MessageUtil.class, "getThreadGroup");
        // MessageUtil.getThreadGroup();
        assertTrue(true);
    }

    /**
     * testMessageUtil001
     * @throws Exception
     */
    public void testMessageUtil001() throws Exception {
        MessageUtil mu = new MessageUtil();
        assertNotNull(mu);
    }

}

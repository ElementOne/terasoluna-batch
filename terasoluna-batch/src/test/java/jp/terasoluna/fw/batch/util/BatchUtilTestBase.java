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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.batch.exception.IllegalClassTypeException;
import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

/**
 * 
 * 
 */
public abstract class BatchUtilTestBase extends TestCase {
    // private Log log = LogFactory.getLog(BatchUtilTest.class);
    private Log log = getLog();

    abstract Log getLog();

    /**
     * testBatchUtil001
     * @throws Exception
     */
    public void testBatchUtil001() throws Exception {
        BatchUtil bu = new BatchUtil();
        assertNotNull(bu);
    }

    /**
     * �����񌋍����\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ���͂��ꂽ������z��̓��e������Ɍ�������邩�ǂ������m�F����B <br>
     * �m�F���ځF<br>
     * �������ꂽ�����񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testCat01() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String[] args = { "test1", "test2" };

        // �e�X�g���{
        String result = BatchUtil.cat((Object[]) args);

        // ���ʌ���
        assertEquals("test1test2", result);
    }

    /**
     * �����񌋍����\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * null���ݒ肳�ꂽ�ꍇnull��ԋp���邱�Ƃ��m�F����B <br>
     * �m�F���ځF<br>
     * null���ԋp����邱��<br>
     * @throws Exception
     */
    public void testCat02() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String[] args = null;

        // �e�X�g���{
        String result = BatchUtil.cat((Object[]) args);

        // ���ʌ���
        assertNull(result);
    }

    /**
     * �����񌋍����\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * �󕶎����ݒ肳�ꂽ�ꍇ�A�󕶎���ԋp���邱�Ƃ��m�F����B <br>
     * �m�F���ځF<br>
     * �󕶎����ԋp����邱��<br>
     * @throws Exception
     */
    public void testCat03() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String[] args = { "" };

        // �e�X�g���{
        String result = BatchUtil.cat((Object[]) args);

        // ���ʌ���
        assertEquals("", result);
    }

    /**
     * �����񌋍����\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * 3�ȏ�̕����񂪐ݒ肳��Ă���A�Ԃ̕������null���܂܂�Ă���ꍇ�Anull�͔�΂��Č��������������ԋp���邱�Ƃ��m�F����B <br>
     * �m�F���ځF<br>
     * null���΂������������񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testCat04() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String[] args = { "test1", null, "test2" };

        // �e�X�g���{
        String result = BatchUtil.cat((Object[]) args);

        // ���ʌ���
        assertEquals("test1test2", result);
    }

    /**
     * �C���t�H���O�̊J�n���b�Z�[�W���擾���郁�\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ���͂ɕ������^�������A�z�肵�����O�����񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �m�F���ځF<br>
     * ���͕����񂪖��ߍ��܂ꂽ���O�����񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testGetInfoLogStartMsg01() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String arg = "test1";

        // �e�X�g���{
        String result = BatchUtil.getInfoLogStartMsg(arg);

        // ���ʌ���
        assertEquals("[test1] �����J�n", result);
    }

    /**
     * �C���t�H���O�̊J�n���b�Z�[�W���擾���郁�\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ���͂�null��^�������A�z�肵�����O�����񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �m�F���ځF<br>
     * ���͕����񂪖��ߍ��܂�Ȃ����O�����񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testGetInfoLogStartMsg02() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String arg = null;

        // �e�X�g���{
        String result = BatchUtil.getInfoLogStartMsg(arg);

        // ���ʌ���
        assertEquals("[] �����J�n", result);
    }

    /**
     * �C���t�H���O�̊J�n���b�Z�[�W���擾���郁�\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ���͂ɋ󕶎���^�������A�z�肵�����O�����񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �m�F���ځF<br>
     * ���͕����񂪖��ߍ��܂�Ȃ����O�����񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testGetInfoLogStartMsg03() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String arg = "";

        // �e�X�g���{
        String result = BatchUtil.getInfoLogStartMsg(arg);

        // ���ʌ���
        assertEquals("[] �����J�n", result);
    }

    /**
     * �C���t�H���O�̏I�����b�Z�[�W���擾���郁�\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ���͂ɕ������^�������A�z�肵�����O�����񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �m�F���ځF<br>
     * ���͕����񂪖��ߍ��܂ꂽ���O�����񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testGetInfoLogEndMsg01() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String arg = "test1";

        // �e�X�g���{
        String result = BatchUtil.getInfoLogEndMsg(arg);

        // ���ʌ���
        assertEquals("[test1] �����I��", result);
    }

    /**
     * �C���t�H���O�̏I�����b�Z�[�W���擾���郁�\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ���͂ɋ󕶎���^�������A�z�肵�����O�����񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �m�F���ځF<br>
     * ���͕����񂪖��ߍ��܂�Ȃ����O�����񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testGetInfoLogEndMsg02() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String arg = null;

        // �e�X�g���{
        String result = BatchUtil.getInfoLogEndMsg(arg);

        // ���ʌ���
        assertEquals("[] �����I��", result);
    }

    /**
     * �C���t�H���O�̏I�����b�Z�[�W���擾���郁�\�b�h�̃e�X�g<br>
     * <br>
     * �e�X�g�T�v�F<br>
     * ���͂ɋ󕶎���^�������A�z�肵�����O�����񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �m�F���ځF<br>
     * ���͕����񂪖��ߍ��܂�Ȃ����O�����񂪕ԋp����邱��<br>
     * @throws Exception
     */
    public void testGetInfoLogEndMsg03() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        String arg = "";

        // �e�X�g���{
        String result = BatchUtil.getInfoLogEndMsg(arg);

        // ���ʌ���
        assertEquals("[] �����I��", result);
    }

    /**
     * testGetTransactionDefinition01
     * @throws Exception
     */
    public void testGetTransactionDefinition01() throws Exception {

        // �e�X�g���{
        TransactionDefinition result = BatchUtil.getTransactionDefinition();

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testGetTransactionDefinition02
     * @throws Exception
     */
    public void testGetTransactionDefinition02() throws Exception {

        // �e�X�g���{
        TransactionDefinition result = BatchUtil.getTransactionDefinition(
                TransactionDefinition.PROPAGATION_REQUIRED,
                TransactionDefinition.ISOLATION_DEFAULT,
                TransactionDefinition.TIMEOUT_DEFAULT, false);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testChangeListToArray01
     * @throws Exception
     */
    public void testChangeListToArray01() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");

        // �e�X�g���{
        String[] result = BatchUtil.changeListToArray(list, String.class);

        // ���ʌ���
        assertEquals("test1", result[0]);
        assertEquals("test2", result[1]);
        assertEquals("test3", result[2]);
    }

    /**
     * testChangeListToArray02
     * @throws Exception
     */
    public void testChangeListToArray02() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        try {

            // �e�X�g���{
            @SuppressWarnings("unused")
            String[] result = BatchUtil.changeListToArray(list, null);

            // ���ʌ���
            fail();
        } catch (IllegalClassTypeException e) {
            assertNotNull(e);
        }

    }

    /**
     * testChangeListToArray03
     * @throws Exception
     */
    public void testChangeListToArray03() throws Exception {

        // �e�X�g���̓f�[�^�ݒ�
        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        try {
            // �e�X�g���{
            @SuppressWarnings("unused")
            String[] result = BatchUtil.changeListToArray(list, Integer.class);

            // ���ʌ���
            fail();
        } catch (IllegalClassTypeException e) {
            assertNotNull(e);
        }

    }

    /**
     * testGetProperties01
     * @throws Exception
     */
    public void testGetProperties01() throws Exception {

        // �e�X�g���{
        List<String> result = BatchUtil.getProperties("messages", "errors");

        // ���ʌ���
        assertEquals(26, result.size());
    }

    /**
     * testStartTransaction01
     * @throws Exception
     */
    public void testStartTransaction01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = null;

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran);

        // ���ʌ���
        assertNull(result);
    }

    /**
     * testStartTransaction02
     * @throws Exception
     */
    public void testStartTransaction02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransaction03
     * @throws Exception
     */
    public void testStartTransaction03() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran, log);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransaction04
     * @throws Exception
     */
    public void testStartTransaction04() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran,
                TransactionDefinition.PROPAGATION_REQUIRED,
                TransactionDefinition.ISOLATION_DEFAULT,
                TransactionDefinition.TIMEOUT_DEFAULT, false);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransaction05
     * @throws Exception
     */
    public void testStartTransaction05() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran,
                TransactionDefinition.PROPAGATION_REQUIRED,
                TransactionDefinition.ISOLATION_DEFAULT,
                TransactionDefinition.TIMEOUT_DEFAULT, false, log);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransaction06
     * @throws Exception
     */
    public void testStartTransaction06() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionDefinition def = BatchUtil.getTransactionDefinition();

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran, def);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransaction07
     * @throws Exception
     */
    public void testStartTransaction07() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionDefinition def = BatchUtil.getTransactionDefinition();

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran, def, log);

        // ���ʌ���
        assertNotNull(result);
    }

    public void testStartTransaction08() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionDefinition def = BatchUtil.getTransactionDefinition();

        // �e�X�g���{
        TransactionStatus result = BatchUtil.startTransaction(tran, null, log);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransactions01
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testStartTransactions01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        TransactionDefinition def = BatchUtil.getTransactionDefinition();
        Map<String, PlatformTransactionManager> tranMap = new HashMap();
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        tranMap.put("tran", tran);

        // �e�X�g���{
        Map<String, TransactionStatus> result = BatchUtil.startTransactions(
                def, tranMap);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransactions02
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testStartTransactions02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        TransactionDefinition def = BatchUtil.getTransactionDefinition();
        Map<String, PlatformTransactionManager> tranMap = new HashMap();
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        tranMap.put("tran", tran);

        // �e�X�g���{
        Map<String, TransactionStatus> result = BatchUtil.startTransactions(
                def, tranMap, log);

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testStartTransactions03
     * @throws Exception
     */
    public void testStartTransactions03() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        TransactionDefinition def = BatchUtil.getTransactionDefinition();
        Map<String, PlatformTransactionManager> tranMap = new LinkedHashMap<String, PlatformTransactionManager>();
        PlatformTransactionManagerStub3 tran1 = new PlatformTransactionManagerStub3();
        tran1.setFailStartTx(true);
        tranMap.put("tran1", tran1);
        PlatformTransactionManagerStub3 tran2 = new PlatformTransactionManagerStub3();
        tranMap.put("tran2", tran2);
        PlatformTransactionManagerStub3 tran3 = new PlatformTransactionManagerStub3();
        tranMap.put("tran3", tran3);
        PlatformTransactionManagerStub3 tran4 = new PlatformTransactionManagerStub3();
        tranMap.put("tran4", tran4);

        // �e�X�g���{
        try {
            BatchUtil.startTransactions(def, tranMap, log);
            fail();
        } catch (TransactionException e) {
            // ���ʌ���
            assertTrue(tran1.wasCalledGetTransaction());
            assertFalse(tran2.wasCalledGetTransaction());
            assertFalse(tran3.wasCalledGetTransaction());
            assertFalse(tran4.wasCalledGetTransaction());
            assertFalse(tran1.wasCalledRollback());
            assertFalse(tran2.wasCalledRollback());
            assertFalse(tran3.wasCalledRollback());
            assertFalse(tran4.wasCalledRollback());
        }
    }

    /**
     * testStartTransactions04
     * @throws Exception
     */
    public void testStartTransactions04() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        TransactionDefinition def = BatchUtil.getTransactionDefinition();
        Map<String, PlatformTransactionManager> tranMap = new LinkedHashMap<String, PlatformTransactionManager>();
        PlatformTransactionManagerStub3 tran1 = new PlatformTransactionManagerStub3();
        tranMap.put("tran1", tran1);
        PlatformTransactionManagerStub3 tran2 = new PlatformTransactionManagerStub3();
        tran2.setFailStartTx(true);
        tranMap.put("tran2", tran2);
        PlatformTransactionManagerStub3 tran3 = new PlatformTransactionManagerStub3();
        tranMap.put("tran3", tran3);
        PlatformTransactionManagerStub3 tran4 = new PlatformTransactionManagerStub3();
        tranMap.put("tran4", tran4);

        // �e�X�g���{
        try {
            BatchUtil.startTransactions(def, tranMap, log);
            fail();
        } catch (TransactionException e) {
            // ���ʌ���
            assertTrue(tran1.wasCalledGetTransaction());
            assertTrue(tran2.wasCalledGetTransaction());
            assertFalse(tran3.wasCalledGetTransaction());
            assertFalse(tran4.wasCalledGetTransaction());
            assertTrue(tran1.wasCalledRollback());
            assertFalse(tran2.wasCalledRollback());
            assertFalse(tran3.wasCalledRollback());
            assertFalse(tran4.wasCalledRollback());
        }
    }

    /**
     * testStartTransactions05
     * @throws Exception
     */
    public void testStartTransactions05() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        TransactionDefinition def = BatchUtil.getTransactionDefinition();
        Map<String, PlatformTransactionManager> tranMap = new LinkedHashMap<String, PlatformTransactionManager>();
        PlatformTransactionManagerStub3 tran1 = new PlatformTransactionManagerStub3();
        tranMap.put("tran1", tran1);
        PlatformTransactionManagerStub3 tran2 = new PlatformTransactionManagerStub3();
        tranMap.put("tran2", tran2);
        PlatformTransactionManagerStub3 tran3 = new PlatformTransactionManagerStub3();
        tranMap.put("tran3", tran3);
        PlatformTransactionManagerStub3 tran4 = new PlatformTransactionManagerStub3();
        tran4.setFailStartTx(true);
        tranMap.put("tran4", tran4);

        // �e�X�g���{
        try {
            BatchUtil.startTransactions(def, tranMap, log);
            fail();
        } catch (TransactionException e) {
            // ���ʌ���
            assertTrue(tran1.wasCalledGetTransaction());
            assertTrue(tran2.wasCalledGetTransaction());
            assertTrue(tran3.wasCalledGetTransaction());
            assertTrue(tran4.wasCalledGetTransaction());
            assertTrue(tran1.wasCalledRollback());
            assertTrue(tran2.wasCalledRollback());
            assertTrue(tran3.wasCalledRollback());
            assertFalse(tran4.wasCalledRollback());
        }
    }

    /**
     * testStartTransactions06
     * @throws Exception
     */
    public void testStartTransactions06() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        TransactionDefinition def = BatchUtil.getTransactionDefinition();
        Map<String, PlatformTransactionManager> tranMap = new LinkedHashMap<String, PlatformTransactionManager>();
        PlatformTransactionManagerStub3 tran1 = new PlatformTransactionManagerStub3();
        tranMap.put("tran1", tran1);
        PlatformTransactionManagerStub3 tran2 = new PlatformTransactionManagerStub3();
        tranMap.put("tran2", tran2);
        PlatformTransactionManagerStub3 tran3 = new PlatformTransactionManagerStub3();
        tranMap.put("tran3", tran3);
        PlatformTransactionManagerStub3 tran4 = new PlatformTransactionManagerStub3();
        tranMap.put("tran4", tran4);

        // �e�X�g���{
        Map<?,?> statMap = BatchUtil.startTransactions(def, tranMap, log);
        // ���ʌ���
        assertTrue(statMap instanceof LinkedHashMap);
        assertTrue(tran1.wasCalledGetTransaction());
        assertTrue(tran2.wasCalledGetTransaction());
        assertTrue(tran3.wasCalledGetTransaction());
        assertTrue(tran4.wasCalledGetTransaction());
        assertFalse(tran1.wasCalledRollback());
        assertFalse(tran2.wasCalledRollback());
        assertFalse(tran3.wasCalledRollback());
        assertFalse(tran4.wasCalledRollback());
    }

    /**
     * testCommitTransaction01
     * @throws Exception
     */
    public void testCommitTransaction01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        try {
            BatchUtil.commitTransaction(tran, stat);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testCommitTransaction02
     * @throws Exception
     */
    public void testCommitTransaction02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        try {
            BatchUtil.commitTransaction(tran, stat, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testCommitTransactions01
     * @throws Exception
     */
    public void testCommitTransactions01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        Map<String, PlatformTransactionManager> tranMap = new HashMap<String, PlatformTransactionManager>();
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        tranMap.put("tran", tran);
        Map<String, TransactionStatus> statMap = new HashMap<String, TransactionStatus>();

        // �e�X�g���{
        try {
            BatchUtil.commitTransactions(tranMap, statMap);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testCommitTransactions02
     * @throws Exception
     */
    public void testCommitTransactions02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        Map<String, PlatformTransactionManager> tranMap = new HashMap<String, PlatformTransactionManager>();
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        tranMap.put("tran", tran);
        Map<String, TransactionStatus> statMap = new HashMap<String, TransactionStatus>();
        statMap.put("tran", new TransactionStatusStub());

        // �e�X�g���{
        try {
            BatchUtil.commitTransactions(tranMap, statMap, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testCommitTransactions03
     * @throws Exception
     */
    public void testCommitTransaction03() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        Map<String, PlatformTransactionManager> tranMap = new HashMap<String, PlatformTransactionManager>();
        PlatformTransactionManagerStub4 tran = new PlatformTransactionManagerStub4();
        tranMap.put("tran1", tran);
        tranMap.put("tran2", tran);
        tranMap.put("tran3", tran);
        tranMap.put("tran4", tran);
        Map<String, TransactionStatus> statMap = new LinkedHashMap<String, TransactionStatus>();
        TransactionStatusStub status1 = new TransactionStatusStub();
        status1.setName("stat1");
        TransactionStatusStub status2 = new TransactionStatusStub();
        status2.setName("stat2");
        TransactionStatusStub status3 = new TransactionStatusStub();
        status3.setName("stat3");
        TransactionStatusStub status4 = new TransactionStatusStub();
        status4.setName("stat4");
        // LinkedHashMap�ɏ�����transactionStatus��ǉ��B
        statMap.put("tran1", status1);
        statMap.put("tran2", status2);
        statMap.put("tran3", status3);
        statMap.put("tran4", status4);

        // �e�X�g���{
        BatchUtil.commitTransactions(tranMap, statMap, log);
        
        // ���ʌ���
        List<TransactionStatus> statusList = tran.getStatusList();
        // statMap��put���Ƌt���ɃR�~�b�g����Ă��邱�ƁB
        assertEquals("stat4", ((TransactionStatusStub) statusList.get(0)).getName());
        assertEquals("stat3", ((TransactionStatusStub) statusList.get(1)).getName());
        assertEquals("stat2", ((TransactionStatusStub) statusList.get(2)).getName());
        assertEquals("stat1", ((TransactionStatusStub) statusList.get(3)).getName());
    }

    /**
     * testEndTransaction01
     * @throws Exception
     */
    public void testEndTransaction01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        try {
            BatchUtil.endTransaction(tran, stat);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testEndTransaction02
     * @throws Exception
     */
    public void testEndTransaction02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        try {
            BatchUtil.endTransaction(tran, stat, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testEndTransactions01
     * @throws Exception
     */
    public void testEndTransactions01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        Map<String, PlatformTransactionManager> tranMap = new HashMap<String, PlatformTransactionManager>();
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        tranMap.put("tran", tran);
        Map<String, TransactionStatus> statMap = new HashMap<String, TransactionStatus>();
        statMap.put("tran", new TransactionStatusStub());

        // �e�X�g���{
        boolean result = false;
        try {
            result = BatchUtil.endTransactions(tranMap, statMap);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertTrue(result);
    }

    /**
     * testEndTransactions02
     * @throws Exception
     */
    public void testEndTransactions02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        Map<String, PlatformTransactionManager> tranMap = new HashMap<String, PlatformTransactionManager>();
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        PlatformTransactionManager tran2 = new PlatformTransactionManagerStub2();
        tranMap.put("tran", tran);
        tranMap.put("tran2", tran2);
        Map<String, TransactionStatus> statMap = new HashMap<String, TransactionStatus>();
        statMap.put("tran", new TransactionStatusStub());
        statMap.put("tran2", new TransactionStatusStub());

        // �e�X�g���{
        boolean result = true;
        try {
            result = BatchUtil.endTransactions(tranMap, statMap, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertFalse(result);
    }

    /**
     * testEndTransactions03
     * @throws Exception
     */
    public void testEndTransactions03() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        Map<String, PlatformTransactionManager> tranMap = new HashMap<String, PlatformTransactionManager>();
        PlatformTransactionManagerStub4 tran = new PlatformTransactionManagerStub4();
        tranMap.put("tran1", tran);
        tranMap.put("tran2", tran);
        tranMap.put("tran3", tran);
        tranMap.put("tran4", tran);

        // TransactionStatus�͑S�ă��[���o�b�N����邱�Ƃ�z�肵�A������tranMap��put�Ƌt���Ŏ��{����邱�ƁB
        Map<String, TransactionStatus> statMap = new LinkedHashMap<String, TransactionStatus>();
        TransactionStatusStub stat1 = new TransactionStatusStub();
        stat1.setName("stat1");
        stat1.setRollbackOnly();

        TransactionStatusStub stat2 = new TransactionStatusStub();
        stat2.setName("stat2");
        stat2.setRollbackOnly();

        TransactionStatusStub stat3 = new TransactionStatusStub();
        stat3.setName("stat3");
        stat3.setRollbackOnly();

        TransactionStatusStub stat4 = new TransactionStatusStub();
        stat4.setName("stat4");
        stat4.setRollbackOnly();

        statMap.put("tran1", stat1);
        statMap.put("tran2", stat2);
        statMap.put("tran3", stat3);
        statMap.put("tran4", stat4);

        // �e�X�g���{
        boolean result = BatchUtil.endTransactions(tranMap, statMap, log);

        // ���ʌ���
        assertTrue(result);
        List<TransactionStatus> statList = tran.getStatusList();
        // statMap��put�Ƌt���Ń��[���o�b�N����Ă��邱�ƁB
        assertEquals("stat4", ((TransactionStatusStub) statList.get(0)).getName());
        assertEquals("stat3", ((TransactionStatusStub) statList.get(1)).getName());
        assertEquals("stat2", ((TransactionStatusStub) statList.get(2)).getName());
        assertEquals("stat1", ((TransactionStatusStub) statList.get(3)).getName());
    }

    /**
     * testSetSavepoint01
     * @throws Exception
     */
    public void testSetSavepoint01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�

        // �e�X�g���{
        Object result = null;
        try {
            result = BatchUtil.setSavepoint(new TransactionStatusStub());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testSetSavepoint02
     * @throws Exception
     */
    public void testSetSavepoint02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�

        // �e�X�g���{
        Object result = null;
        try {
            result = BatchUtil.setSavepoint(new TransactionStatusStub(), log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testReleaseSavepoint01
     * @throws Exception
     */
    public void testReleaseSavepoint01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�

        // �e�X�g���{
        try {
            BatchUtil.releaseSavepoint(new TransactionStatusStub(),
                    new Object());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testReleaseSavepoint02
     * @throws Exception
     */
    public void testReleaseSavepoint02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�

        // �e�X�g���{
        try {
            BatchUtil.releaseSavepoint(new TransactionStatusStub(),
                    new Object(), log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testRollbackSavepoint01
     * @throws Exception
     */
    public void testRollbackSavepoint01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�

        // �e�X�g���{
        try {
            BatchUtil.rollbackSavepoint(new TransactionStatusStub(),
                    new Object());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testRollbackSavepoint02
     * @throws Exception
     */
    public void testRollbackSavepoint02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�

        // �e�X�g���{
        try {
            BatchUtil.rollbackSavepoint(new TransactionStatusStub(),
                    new Object(), log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testRollbackTransaction01
     * @throws Exception
     */
    public void testRollbackTransaction01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        try {
            BatchUtil.rollbackTransaction(tran, stat);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testRollbackTransaction02
     * @throws Exception
     */
    public void testRollbackTransaction02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        try {
            BatchUtil.rollbackTransaction(tran, stat, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
    }

    /**
     * testCommitRestartTransaction01
     * @throws Exception
     */
    public void testCommitRestartTransaction01() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.commitRestartTransaction(tran, stat);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testCommitRestartTransaction02
     * @throws Exception
     */
    public void testCommitRestartTransaction02() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.commitRestartTransaction(tran, stat, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testCommitRestartTransaction03
     * @throws Exception
     */
    public void testCommitRestartTransaction03() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();
        TransactionDefinition def = BatchUtil.getTransactionDefinition();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.commitRestartTransaction(tran, stat, def);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testCommitRestartTransaction04
     * @throws Exception
     */
    public void testCommitRestartTransaction04() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();
        TransactionDefinition def = BatchUtil.getTransactionDefinition();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.commitRestartTransaction(tran, stat, def, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testRollbackRestartTransaction001
     * @throws Exception
     */
    public void testRollbackRestartTransaction001() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.rollbackRestartTransaction(tran, stat);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testRollbackRestartTransaction002
     * @throws Exception
     */
    public void testRollbackRestartTransaction002() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.rollbackRestartTransaction(tran, stat, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testRollbackRestartTransaction003
     * @throws Exception
     */
    public void testRollbackRestartTransaction003() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();
        TransactionDefinition def = BatchUtil.getTransactionDefinition();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.rollbackRestartTransaction(tran, stat, def);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testRollbackRestartTransaction004
     * @throws Exception
     */
    public void testRollbackRestartTransaction004() throws Exception {
        // �e�X�g���̓f�[�^�ݒ�
        PlatformTransactionManager tran = new PlatformTransactionManagerStub();
        TransactionStatus stat = new TransactionStatusStub();
        TransactionDefinition def = BatchUtil.getTransactionDefinition();

        // �e�X�g���{
        TransactionStatus result = null;
        try {
            result = BatchUtil.rollbackRestartTransaction(tran, stat, def, log);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        // ���ʌ���
        assertNotNull(result);
    }

    /**
     * testGetMemoryInfo001
     * @throws Exception
     */
    public void testGetMemoryInfo001() throws Exception {
        String info = BatchUtil.getMemoryInfo();

        assertNotNull(info);
        assertTrue(info.startsWith("Java memory info : "));
    }
}

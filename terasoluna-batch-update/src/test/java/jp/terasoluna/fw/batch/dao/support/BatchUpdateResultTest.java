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

package jp.terasoluna.fw.batch.dao.support;

import static org.junit.Assert.*;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.utlib.UTUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BatchUpdateResultTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * testBatchUpdateResult001
     */
    @Test
    public void testBatchUpdateResult001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        // �e�X�g
        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // ����
        assertNotNull(bur);
    }

    /**
     * testBatchUpdateResultBatchUpdateSupportInteger001
     */
    @Test
    public void testBatchUpdateResultBatchUpdateSupportInteger001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = null;
        Integer executeBatchResult = null;

        // �e�X�g
        try {
            bur = new BatchUpdateResult(executeBatchUpdateSupport,
                    executeBatchResult);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(null, executeBatchExceptionField);
        assertEquals(executeBatchUpdateSupport, executeBatchUpdateSupportField);
        assertEquals(executeBatchResult, executeBatchResultField);
    }

    /**
     * testBatchUpdateResultBatchUpdateSupportInteger002
     */
    @Test
    public void testBatchUpdateResultBatchUpdateSupportInteger002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = new BatchUpdateSupportImpl();
        Integer executeBatchResult = Integer.valueOf(0);

        // �e�X�g
        try {
            bur = new BatchUpdateResult(executeBatchUpdateSupport,
                    executeBatchResult);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(null, executeBatchExceptionField);
        assertEquals(executeBatchUpdateSupport, executeBatchUpdateSupportField);
        assertEquals(executeBatchResult, executeBatchResultField);
    }

    /**
     * testBatchUpdateResultThrowable001
     */
    @Test
    public void testBatchUpdateResultThrowable001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = null;
        Throwable executeBatchException = null;

        // �e�X�g
        try {
            bur = new BatchUpdateResult(executeBatchUpdateSupport,
                    executeBatchException);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(executeBatchException, executeBatchExceptionField);
        assertEquals(null, executeBatchUpdateSupportField);
        assertEquals(null, executeBatchResultField);
    }

    /**
     * testBatchUpdateResultThrowable002
     */
    @Test
    public void testBatchUpdateResultThrowable002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = new BatchUpdateSupportImpl();
        Throwable executeBatchException = new BatchException();

        // �e�X�g
        try {
            bur = new BatchUpdateResult(executeBatchUpdateSupport,
                    executeBatchException);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(executeBatchException, executeBatchExceptionField);
        assertEquals(executeBatchUpdateSupport, executeBatchUpdateSupportField);
        assertEquals(null, executeBatchResultField);
    }

    /**
     * testGetExecuteBatchUpdateSupport001
     */
    @Test
    public void testGetExecuteBatchUpdateSupport001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        BatchUpdateSupport ebus = bur.getExecuteBatchUpdateSupport();

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(null, ebus);
    }

    /**
     * testGetExecuteBatchUpdateSupport002
     */
    @Test
    public void testGetExecuteBatchUpdateSupport002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = new BatchUpdateSupportImpl();
        Integer executeBatchResult = Integer.valueOf(0);

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            UTUtil.setPrivateField(bur, "executeBatchUpdateSupport",
                    executeBatchUpdateSupport);
            UTUtil.setPrivateField(bur, "executeBatchResult",
                    executeBatchResult);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        BatchUpdateSupport ebus = bur.getExecuteBatchUpdateSupport();

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNotNull(executeBatchUpdateSupportField);
        assertNotNull(executeBatchResultField);
        assertEquals(executeBatchUpdateSupport, ebus);
    }

    /**
     * testSetExecuteBatchUpdateSupport001
     */
    @Test
    public void testSetExecuteBatchUpdateSupport001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = new BatchUpdateSupportImpl();

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        bur.setExecuteBatchUpdateSupport(executeBatchUpdateSupport);

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNotNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchUpdateSupport, executeBatchUpdateSupportField);

    }

    /**
     * testSetExecuteBatchUpdateSupport002
     */
    @Test
    public void testSetExecuteBatchUpdateSupport002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = null;

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        bur.setExecuteBatchUpdateSupport(executeBatchUpdateSupport);

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchUpdateSupport, executeBatchUpdateSupportField);

    }

    /**
     * testGetExecuteBatchResult001
     */
    @Test
    public void testGetExecuteBatchResult001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = null;
        Integer executeBatchResult = null;

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            UTUtil.setPrivateField(bur, "executeBatchUpdateSupport",
                    executeBatchUpdateSupport);
            UTUtil.setPrivateField(bur, "executeBatchResult",
                    executeBatchResult);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        Integer ebr = bur.getExecuteBatchResult();

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchResult, ebr);

    }

    /**
     * testGetExecuteBatchResult002
     */
    @Test
    public void testGetExecuteBatchResult002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        BatchUpdateSupport executeBatchUpdateSupport = new BatchUpdateSupportImpl();
        Integer executeBatchResult = Integer.valueOf(0);

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            UTUtil.setPrivateField(bur, "executeBatchUpdateSupport",
                    executeBatchUpdateSupport);
            UTUtil.setPrivateField(bur, "executeBatchResult",
                    executeBatchResult);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        Integer ebr = bur.getExecuteBatchResult();

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNotNull(executeBatchUpdateSupportField);
        assertNotNull(executeBatchResultField);
        assertEquals(executeBatchResult, ebr);

    }

    /**
     * testSetExecuteBatchResult001
     */
    @Test
    public void testSetExecuteBatchResult001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        Integer executeBatchResult = null;

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        bur.setExecuteBatchResult(executeBatchResult);

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchResult, executeBatchResultField);

    }

    /**
     * testSetExecuteBatchResult002
     */
    @Test
    public void testSetExecuteBatchResult002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        Integer executeBatchResult = Integer.valueOf(0);

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        bur.setExecuteBatchResult(executeBatchResult);

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNotNull(executeBatchResultField);
        assertEquals(executeBatchResult, executeBatchResultField);

    }

    /**
     * testGetExecuteBatchException001
     */
    @Test
    public void testGetExecuteBatchException001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        Throwable executeBatchException = null;

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            UTUtil.setPrivateField(bur, "executeBatchException",
                    executeBatchException);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        Throwable ebe = bur.getExecuteBatchException();

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchException, ebe);

    }

    /**
     * testGetExecuteBatchException002
     */
    @Test
    public void testGetExecuteBatchException002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        Throwable executeBatchException = new BatchException();

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            UTUtil.setPrivateField(bur, "executeBatchException",
                    executeBatchException);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        Throwable ebe = bur.getExecuteBatchException();

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchException, ebe);

    }

    /**
     * testSetExecuteBatchException001
     */
    @Test
    public void testSetExecuteBatchException001() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        Throwable executeBatchException = null;

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        bur.setExecuteBatchException(executeBatchException);

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchException, executeBatchExceptionField);
    }

    /**
     * testSetExecuteBatchException002
     */
    @Test
    public void testSetExecuteBatchException002() {
        // �p�����[�^
        BatchUpdateResult bur = null;

        Throwable executeBatchException = new BatchException();

        try {
            bur = new BatchUpdateResult();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // �e�X�g
        bur.setExecuteBatchException(executeBatchException);

        // ����
        assertNotNull(bur);
        Object executeBatchExceptionField = null;
        Object executeBatchUpdateSupportField = null;
        Object executeBatchResultField = null;
        try {
            executeBatchExceptionField = UTUtil.getPrivateField(bur,
                    "executeBatchException");
            executeBatchUpdateSupportField = UTUtil.getPrivateField(bur,
                    "executeBatchUpdateSupport");
            executeBatchResultField = UTUtil.getPrivateField(bur,
                    "executeBatchResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(executeBatchExceptionField);
        assertNull(executeBatchUpdateSupportField);
        assertNull(executeBatchResultField);
        assertEquals(executeBatchException, executeBatchExceptionField);
    }

}

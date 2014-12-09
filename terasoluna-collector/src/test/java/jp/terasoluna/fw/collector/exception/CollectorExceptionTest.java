/**
 * @(#)CollectorExceptionTest.java
 * �V�X�e����:TERASOLUNA-IDE
 * �t�@�C���o�[�W����:$Id: CollectorExceptionTest.java 6835 2012-01-13 13:44:36Z bthashidumets $
 * Copyright 2009 NTT DATA Corporation.
 */
package jp.terasoluna.fw.collector.exception;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * [���̃N���X�̐����������܂��傤]
 * @version $Revision$
 */
public class CollectorExceptionTest {

    /**
     * [���\�b�h�̐����������܂��傤]
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * [���\�b�h�̐����������܂��傤]
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * [���\�b�h�̐����������܂��傤]
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * [���\�b�h�̐����������܂��傤]
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * {@link jp.terasoluna.fw.collector.exception.CollectorException#CollectorException()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testCollectorException001() {
        CollectorException ex = new CollectorException();

        assertNotNull(ex);
    }

    /**
     * {@link jp.terasoluna.fw.collector.exception.CollectorException#CollectorException(java.lang.String)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testCollectorExceptionString001() {
        CollectorException ex = new CollectorException("hoge");

        assertNotNull(ex);
    }

    /**
     * {@link jp.terasoluna.fw.collector.exception.CollectorException#CollectorException(java.lang.Throwable)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testCollectorExceptionThrowable001() {
        Exception cause = new Exception();
        CollectorException ex = new CollectorException(cause);

        assertNotNull(ex);
    }

    /**
     * {@link jp.terasoluna.fw.collector.exception.CollectorException#CollectorException(java.lang.String, java.lang.Throwable)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testCollectorExceptionStringThrowable001() {
        Exception cause = new Exception();
        CollectorException ex = new CollectorException("hoge", cause);

        assertNotNull(ex);
    }

}

package jp.terasoluna.fw.collector.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import jp.terasoluna.fw.collector.AbstractCollector;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.CollectorExceptionHandlerStub1;
import jp.terasoluna.fw.collector.CollectorExceptionHandlerStub2;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandlerStatus;
import jp.terasoluna.fw.collector.validate.ExceptionValidationErrorHandler;
import jp.terasoluna.fw.collector.validate.ValidationErrorException;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.file.dao.FileLineException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * �R���N�^�ƃR���g���[���u���C�N�̑g�ݍ��킹�����B 
 * �@�\���������ɊY�����邪�AJUnit�ł��������\�ł��邱�ƁA �g�ݍ��킹�̃p�^�[��������ɂ킽�邱�Ƃ���A JUnit�̎����Ƃ����B
 */
public class ControlBreakCheckerTest2 {

    /**
     * �u���C�N�L�[�̃J������
     */
    private static final String BREAK_KEY = "column1";

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
     * �R���g���[���u���C�N�Ƒg�ݍ��킹�̃e�X�g�B<br>
     * BUG_B30020�̋��������B
     * <p>
     * <ul>
     * <li>��O�n���h���Ȃ�(���̂܂܃G���[���X���[����)</li>
     * </ul>
     * ���Ғl�͈ȉ��̒ʂ�
     * ���̓f�[�^�F[1(bkv1), 2(bkv1), 3(bkv1), (��O), 5(bkv2), 6(bkv2), 7(bkv3), 
     * (��O), 9(bkv3), 10(bkv4), 11(bkv4), (��O),
     * 13(bkv5), 14(bkv5), 15(bkv5)]
     * ���ʁF[1(��1), 2, 3(��4), (��O)(��2��4), 5(��2), 6(��3), 7(��1��4), 
     * (��O)(��2��4), 9(��2��3), 10(��1), 11(��4), (��O)(��2��4), 13(��2), 14, 15(��3)]
     * ��1:�O�u���C�N ��2:�O�u���C�N����s�\(�O�u���C�N���莞�ɗ�O�X���[)
     * ��3:��u���C�N ��4:��u���C�N����s�\(��u���C�N���莞�ɗ�O�X���[)
     */
    @Test
    public void testDefaultHandler001() throws Exception {

        ControlBreakCheckerTestBean bean01 = new ControlBreakCheckerTestBean("aaa", "1", "1");
        ControlBreakCheckerTestBean bean02 = new ControlBreakCheckerTestBean("aaa", "2", "1");
        ControlBreakCheckerTestBean bean03 = new ControlBreakCheckerTestBean("aaa", "3", "1");
        ControlBreakCheckerTestBean bean04 = new ControlBreakCheckerTestBean("bbb", "4", "Exception");
        ControlBreakCheckerTestBean bean05 = new ControlBreakCheckerTestBean("bbb", "5", "1");
        ControlBreakCheckerTestBean bean06 = new ControlBreakCheckerTestBean("bbb", "6", "1");
        ControlBreakCheckerTestBean bean07 = new ControlBreakCheckerTestBean("ccc", "7", "1");
        ControlBreakCheckerTestBean bean08 = new ControlBreakCheckerTestBean("ccc", "8", "Exception");
        ControlBreakCheckerTestBean bean09 = new ControlBreakCheckerTestBean("ccc", "9", "1");
        ControlBreakCheckerTestBean bean10 = new ControlBreakCheckerTestBean("ddd", "10", "1");
        ControlBreakCheckerTestBean bean11 = new ControlBreakCheckerTestBean("ddd", "11", "1");
        ControlBreakCheckerTestBean bean12 = new ControlBreakCheckerTestBean("ddd", "12", "Exception");
        ControlBreakCheckerTestBean bean13 = new ControlBreakCheckerTestBean("eee", "13", "1");
        ControlBreakCheckerTestBean bean14 = new ControlBreakCheckerTestBean("eee", "14", "1");
        ControlBreakCheckerTestBean bean15 = new ControlBreakCheckerTestBean("eee", "15", "1");
        ControlBreakCheckerTestBean[] beans = { bean01, bean02, bean03, bean04,
                bean05, bean06, bean07, bean08, bean09, bean10, bean11, bean12,
                bean13, bean14, bean15 };

        // �E���̓`�F�b�N�G���[�n���h���F�Ȃ�
        ValidationErrorHandler argValidationErrorHandler = null;
        // ��O�n���h���F�Ȃ�
        CollectorExceptionHandler argExceptionHandler = null;
        Collector<ControlBreakCheckerTestBean> collector = createTestCollector(
                argValidationErrorHandler, argExceptionHandler, beans);

        // ####################��1�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��2�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        // ####################��3�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=(�G���[)
        try {
            ControlBreakChecker.isBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            assertEquals("���̓G���[", e.getMessage());
        }

        // ####################��4�v�f(��O)####################
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            // �|�C���^��i�߂�
        }
        try {
            ControlBreakChecker.isPreBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            // �O�u���C�N����s�\
        }
        try {
            ControlBreakChecker.isBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            // ��u���C�N����s�\
        }
        // ####################��5�v�f####################
        assertTrue(collector.hasNext());
        collector.next();
        // �O�u���C�N=(�G���[)
        try {
            ControlBreakChecker.isPreBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            assertEquals("���̓G���[", e.getMessage());
        }

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��6�v�f####################
        assertTrue(collector.hasNext());
        collector.next();
        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("bbb", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��7�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("ccc", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=(�G���[)
        try {
            ControlBreakChecker.isBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            assertEquals("���̓G���[", e.getMessage());
        }
        // ####################��8�v�f�̓G���[####################
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            // �|�C���^��i�߂�
        }
        try {
            ControlBreakChecker.isPreBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            // �O�u���C�N����s�\
        }
        try {
            ControlBreakChecker.isBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            // ��u���C�N����s�\
        }
        // ####################��9�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=(�G���[)
        try {
            ControlBreakChecker.isPreBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            assertEquals("���̓G���[", e.getMessage());
        }

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("ccc", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��10�v�f####################
        assertTrue(collector.hasNext());
        collector.next();
        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("ddd", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��11�v�f####################
        assertTrue(collector.hasNext());
        collector.next();
        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=(�G���[)
        try {
            ControlBreakChecker.isBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            assertEquals("���̓G���[", e.getMessage());
        }

        // ####################��12�v�f�̓G���[####################
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            // �|�C���^��i�߂�
        }
        try {
            ControlBreakChecker.isPreBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            // �O�u���C�N����s�\
        }
        try {
            ControlBreakChecker.isBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            // ��u���C�N����s�\
        }
        // ####################��13�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=(�G���[)
        try {
            ControlBreakChecker.isPreBreak(collector, BREAK_KEY);
            fail();
        } catch (FileLineException e) {
            assertEquals("���̓G���[", e.getMessage());
        }
        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��14�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��15�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("eee", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        collector.close();
    }

    /**
     * �R���g���[���u���C�N�Ƒg�ݍ��킹�̃e�X�g�B<br>
     * BUG_B30020�̋��������B
     * <p>
     * <ul>
     * <li>��O�n���h������(END)</li>
     * <li>���̓f�[�^��̃u���C�N�O�ŗ�O��END</li>
     * </ul>
     * ���Ғl�͈ȉ��̒ʂ�
     * ���̓f�[�^�F[1(bkv1), 2(bkv1), (��O), 4(bkv2)]
     * ���ʁF[1(��1), 2(��3)]
     * ��1:�O�u���C�N ��2:�O�u���C�N����s�\(�O�u���C�N���莞�ɗ�O�X���[)
     * ��3:��u���C�N ��4:��u���C�N����s�\(��u���C�N���莞�ɗ�O�X���[)
     */
    @Test
    public void testErrorHandlerReturnsEnd001() throws Exception {
        ControlBreakCheckerTestBean bean01 = new ControlBreakCheckerTestBean("aaa", "1", "1");
        ControlBreakCheckerTestBean bean02 = new ControlBreakCheckerTestBean("aaa", "2", "1");
        ControlBreakCheckerTestBean bean03 = new ControlBreakCheckerTestBean("aaa", "3", "Exception"); // ��O
        ControlBreakCheckerTestBean bean04 = new ControlBreakCheckerTestBean("bbb", "4", "1");

        ControlBreakCheckerTestBean[] beans = { bean01, bean02, bean03, bean04 };

        // �E���̓`�F�b�N�G���[�n���h���F�Ȃ�
        ValidationErrorHandler argValidationErrorHandler = null;
        // ��O�n���h���F����(END��Ԃ�)
        CollectorExceptionHandler argExceptionHandler = new CollectorExceptionHandlerStub1();
        Collector<ControlBreakCheckerTestBean> collector = createTestCollector(
                argValidationErrorHandler, argExceptionHandler, beans);

        // ####################��1�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��2�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��3�v�f####################
        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }

        collector.close();
    }

    /**
     * �R���g���[���u���C�N�Ƒg�ݍ��킹�̃e�X�g�B<br>
     * BUG_B30020�̋��������B
     * <p>
     * <ul>
     * <li>��O�n���h������(END)</li>
     * <li>���̓f�[�^��̃u���C�N��ŗ�O��END</li>
     * </ul>
     * ���Ғl�͈ȉ��̒ʂ�
     * ���̓f�[�^�F[1(bkv1), 2(bkv1), 3(bkv1), (��O), 5(bkv2)]
     * ���ʁF[1(��1), 2, 3(��3)]
     * ��1:�O�u���C�N ��2:�O�u���C�N����s�\(�O�u���C�N���莞�ɗ�O�X���[)
     * ��3:��u���C�N ��4:��u���C�N����s�\(��u���C�N���莞�ɗ�O�X���[)
     */
    @Test
    public void testErrorHandlerReturnsEnd002() throws Exception {
        ControlBreakCheckerTestBean bean01 = new ControlBreakCheckerTestBean("aaa", "1", "1");
        ControlBreakCheckerTestBean bean02 = new ControlBreakCheckerTestBean("aaa", "2", "1");
        ControlBreakCheckerTestBean bean03 = new ControlBreakCheckerTestBean("aaa", "3", "1");
        ControlBreakCheckerTestBean bean04 = new ControlBreakCheckerTestBean("bbb", "4", "Exception"); // ��O
        ControlBreakCheckerTestBean bean05 = new ControlBreakCheckerTestBean("bbb", "5", "1");

        ControlBreakCheckerTestBean[] beans = { bean01, bean02, bean03, bean04,
                bean05 };

        // �E���̓`�F�b�N�G���[�n���h���F�Ȃ�
        ValidationErrorHandler argValidationErrorHandler = null;
        // ��O�n���h���F����(END��Ԃ�)
        CollectorExceptionHandler argExceptionHandler = new CollectorExceptionHandlerStub1();
        Collector<ControlBreakCheckerTestBean> collector = createTestCollector(
                argValidationErrorHandler, argExceptionHandler, beans);

        // ####################��1�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��2�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��3�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��4�v�f####################
        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }

        collector.close();
    }

    /**
     * �R���g���[���u���C�N�Ƒg�ݍ��킹�̃e�X�g�B<br>
     * BUG_B30020�̋��������B
     * <p>
     * <ul>
     * <li>��O�n���h������(SKIP)</li>
     * <li>��u���C�N��ɗ�O(�u���C�N����\)</li>
     * </ul>
     * ���Ғl�͈ȉ��̒ʂ�
     * ���̓f�[�^�F[1(bkv1), 2(bkv1), (��O), 4(bkv1), 5(bkv1), (��O), 7(bkv2), 8(bkv2), (��O), 10(bkv3), 11(bkv3), (��O)]
     * ���ʁF[1(��1), 2, 4, 5(��3), 7(��1), 8(��3), 10(��1), 11(��3)]
     * ��1:�O�u���C�N ��2:�O�u���C�N����s�\(�O�u���C�N���莞�ɗ�O�X���[)
     * ��3:��u���C�N ��4:��u���C�N����s�\(��u���C�N���莞�ɗ�O�X���[)
     */
    @Test
    public void testErrorHandlerReturnsSkip001() throws Exception {
        ControlBreakCheckerTestBean bean01 = new ControlBreakCheckerTestBean("aaa", "1", "1");
        ControlBreakCheckerTestBean bean02 = new ControlBreakCheckerTestBean("aaa", "2", "1");
        ControlBreakCheckerTestBean bean03 = new ControlBreakCheckerTestBean("aaa", "3", "Exception");
        ControlBreakCheckerTestBean bean04 = new ControlBreakCheckerTestBean("aaa", "4", "1");
        ControlBreakCheckerTestBean bean05 = new ControlBreakCheckerTestBean("aaa", "5", "1");
        ControlBreakCheckerTestBean bean06 = new ControlBreakCheckerTestBean("aaa", "6", "Exception");
        ControlBreakCheckerTestBean bean07 = new ControlBreakCheckerTestBean("bbb", "7", "1");
        ControlBreakCheckerTestBean bean08 = new ControlBreakCheckerTestBean("bbb", "8", "1");
        ControlBreakCheckerTestBean bean09 = new ControlBreakCheckerTestBean("ccc", "9", "Exception");
        ControlBreakCheckerTestBean bean10 = new ControlBreakCheckerTestBean("ccc", "10", "1");
        ControlBreakCheckerTestBean bean11 = new ControlBreakCheckerTestBean("ccc", "11", "1");
        ControlBreakCheckerTestBean bean12 = new ControlBreakCheckerTestBean("ccc", "12", "Exception");

        ControlBreakCheckerTestBean[] beans = { bean01, bean02, bean03, bean04,
                bean05, bean06, bean07, bean08, bean09, bean10, bean11, bean12 };

        // �E���̓`�F�b�N�G���[�n���h���F�Ȃ�
        ValidationErrorHandler argValidationErrorHandler = null;
        // ��O�n���h���F����(SKIP��Ԃ�)
        CollectorExceptionHandler argExceptionHandler = new CollectorExceptionHandlerStub2(CollectorExceptionHandlerStatus.SKIP);
        Collector<ControlBreakCheckerTestBean> collector = createTestCollector(
                argValidationErrorHandler, argExceptionHandler, beans);

        // ####################��1�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��2�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��3�v�f####################
        // ####################��4�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��5�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��6�v�f####################
        // ####################��7�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("bbb", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��8�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("bbb", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��9�v�f####################
        // ####################��10�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("ccc", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��11�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("ccc", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��12�v�f####################
        collector.close();
    }

    /**
     * �R���g���[���u���C�N�Ƒg�ݍ��킹�̃e�X�g�B<br>
     * BUG_B30019�̋��������B
     * <p>
     * <ul>
     * <li>���̓`�F�b�N�G���[�n���h������(���̂܂ܗ�O���X���[����)</li>
     * </ul>
     * ���Ғl�͈ȉ��̒ʂ�
     * ���̓f�[�^�F[1(bkv1), 2(bkv1), 3(bkv1), 4(���̓`�F�b�N�G���[)(bkv2), 5(bkv2), 6(bkv2), 7(bkv3), 
     * 8(���̓`�F�b�N�G���[)(bkv3), 9(bkv3), 10(bkv4), 11(bkv4), 12(���̓`�F�b�N�G���[)(bkv4), 13(bkv5), 14(bkv5), 15(bkv5)]
     * ���ʁF[1(��1), 2, 3(��3), 4(���̓`�F�b�N�G���[)(��1), 5, 6(��3), 7(��1),
     * 8(���̓`�F�b�N�G���[), 9(��3), 10(��1), 11, 12(���̓`�F�b�N�G���[)(��3), 13(��1), 14, 15(��3)]
     * ��1:�O�u���C�N ��2:�O�u���C�N����s�\(�O�u���C�N���莞�ɗ�O�X���[)
     * ��3:��u���C�N ��4:��u���C�N����s�\(��u���C�N���莞�ɗ�O�X���[)
     */
    @Test
    public void testValidateErrorHandler001() throws Exception {

        ControlBreakCheckerTestBean bean01 = new ControlBreakCheckerTestBean("aaa", "1", "1");
        ControlBreakCheckerTestBean bean02 = new ControlBreakCheckerTestBean("aaa", "2", "1");
        ControlBreakCheckerTestBean bean03 = new ControlBreakCheckerTestBean("aaa", "3", "1");
        ControlBreakCheckerTestBean bean04 = new ControlBreakCheckerTestBean("bbb", "4", "validateError");
        ControlBreakCheckerTestBean bean05 = new ControlBreakCheckerTestBean("bbb", "5", "1");
        ControlBreakCheckerTestBean bean06 = new ControlBreakCheckerTestBean("bbb", "6", "1");
        ControlBreakCheckerTestBean bean07 = new ControlBreakCheckerTestBean("ccc", "7", "1");
        ControlBreakCheckerTestBean bean08 = new ControlBreakCheckerTestBean("ccc", "8", "validateError");
        ControlBreakCheckerTestBean bean09 = new ControlBreakCheckerTestBean("ccc", "9", "1");
        ControlBreakCheckerTestBean bean10 = new ControlBreakCheckerTestBean("ddd", "10", "1");
        ControlBreakCheckerTestBean bean11 = new ControlBreakCheckerTestBean("ddd", "11", "1");
        ControlBreakCheckerTestBean bean12 = new ControlBreakCheckerTestBean("ddd", "12", "validateError");
        ControlBreakCheckerTestBean bean13 = new ControlBreakCheckerTestBean("eee", "13", "1");
        ControlBreakCheckerTestBean bean14 = new ControlBreakCheckerTestBean("eee", "14", "1");
        ControlBreakCheckerTestBean bean15 = new ControlBreakCheckerTestBean("eee", "15", "1");
        ControlBreakCheckerTestBean[] beans = { bean01, bean02, bean03, bean04,
                bean05, bean06, bean07, bean08, bean09, bean10, bean11, bean12,
                bean13, bean14, bean15 };

        // �E���̓`�F�b�N�G���[�n���h���F����
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        // ��O�n���h���F�Ȃ�
        CollectorExceptionHandler argExceptionHandler = null;
        Collector<ControlBreakCheckerTestBean> collector = createTestCollector(
                argValidationErrorHandler, argExceptionHandler, beans);

        // ####################��1�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��2�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��3�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("aaa", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��4�v�f####################
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
            // �|�C���^����i�߂�
        }

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("bbb", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��5�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��6�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("bbb", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��7�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("ccc", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��8�v�f####################
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
            // �|�C���^����i�߂�
        }

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��9�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("ccc", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��10�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("ddd", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��11�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��12�v�f####################
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
            // �|�C���^����i�߂�
        }

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("ddd", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ####################��13�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=TRUE
        assertTrue(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));
        assertEquals("eee", ControlBreakChecker.getPreBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��14�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=FALSE
        assertFalse(ControlBreakChecker.isBreak(collector, BREAK_KEY));

        // ####################��15�v�f####################
        assertTrue(collector.hasNext());
        collector.next();

        // �O�u���C�N=FALSE
        assertFalse(ControlBreakChecker.isPreBreak(collector, BREAK_KEY));

        // ��u���C�N=TRUE
        assertTrue(ControlBreakChecker.isBreak(collector, BREAK_KEY));
        assertEquals("eee", ControlBreakChecker.getBreakKey(collector,
                BREAK_KEY).get(BREAK_KEY));

        collector.close();
    }

    /**
     * �e�X�g�f�[�^��񋟂���R���N�^�𐶐�����B 
     * Validator�@�\�������ACollectorTestBean���i�[�ł���B
     * CollectorTestBean1��column3��"validateError"���Ɠ��̓`�F�b�N�G���[�ƂȂ�
     * "Exception"���Ɠ��̓G���[����������B
     * @param argValidationErrorHandler ValidationErrorHandler
     * @param argExceptionHandler ExceptionHandler
     * @param CollectorTestBean[] beans �R���N�^�̒��g�ɂȂ�CollectorTestBean1�̔z��
     * @return �e�X�g�f�[�^��񋟂���R���N�^
     */
    private static Collector<ControlBreakCheckerTestBean> createTestCollector(
            final ValidationErrorHandler argValidationErrorHandler,
            final CollectorExceptionHandler argExceptionHandler,
            final ControlBreakCheckerTestBean[] beans) {
        Collector<ControlBreakCheckerTestBean> collector = new AbstractCollector<ControlBreakCheckerTestBean>() {
            {
                this.validator = new Validator() {

                    // ���̓`�F�b�N���s���Bcolumn3��"validateError"�̏ꍇ�͓��̓`�F�b�N�G���[�Ƃ���B
                    public void validate(Object target, Errors errors) {
                        ControlBreakCheckerTestBean data = (ControlBreakCheckerTestBean) target;
                        if ("validateError".equals(data.getColumn3())) {
                            errors.rejectValue("column3", "errors.numeric");
                        }
                    }

                    public boolean supports(Class<?> clazz) {
                        return (clazz == ControlBreakCheckerTestBean.class);
                    }
                };
                this.validationErrorHandler = argValidationErrorHandler;
                this.exceptionHandler = argExceptionHandler;
            }

            // column3��"Exception"��������FileLineException�i���̓G���[�j�Ƃ������Ƃɂ���
            public Integer call() throws Exception {

                for (int count = 0; count < beans.length; count++) {
                    if ("Exception".equals(beans[count].getColumn3())) {
                        addQueue(new DataValueObject(new FileLineException("���̓G���["), count));
                    } else {
                        addQueue(new DataValueObject(beans[count], count));
                    }
                }

                setFinish();
                return 0;
            }

        };

        return collector;

    }

}

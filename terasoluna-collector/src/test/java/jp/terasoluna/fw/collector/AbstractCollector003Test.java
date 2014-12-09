package jp.terasoluna.fw.collector;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandlerStatus;
import jp.terasoluna.fw.collector.file.SkipValidationErrorHandler;
import jp.terasoluna.fw.collector.validate.ExceptionValidationErrorHandler;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.validate.ValidationErrorException;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.file.dao.FileLineException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AbstractCollector003Test {

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
     * �f�[�^��0���̏ꍇ�̃e�X�g�B
     * ���̓f�[�^�F[]
     * �o�̓f�[�^�F[]
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testNormal001() throws Exception {
        int dataNum = 0;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f���������Ƃ��m�F
        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^��1���̏ꍇ
     * ���̓f�[�^�F[1]
     * �o�̓f�[�^�F[1]
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testNormal002() throws Exception {
        int dataNum = 1;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^��3���̏ꍇ
     * ���̓f�[�^�F[1, 2, 3]
     * �o�̓f�[�^�F[1, 2, 3]
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testNormal003() throws Exception {
        int dataNum = 3;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertTrue(collector.hasNext());
        assertEquals("3", collector.next().getHoge());
        assertEquals("2", collector.getPrevious().getHoge());
        assertEquals("3", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("2", collector.getPrevious().getHoge());
        assertEquals("3", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^��bean�Ƃ���null���܂܂��ꍇ
     * ���̓f�[�^�F[null, 2, 3, 4, 5, 6]
     * �o�̓f�[�^�F[null, 2, 3, 4, 5, 6]
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testContainsNull001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Arrays.asList(1);
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertNull(collector.next());
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^��bean�Ƃ���null���܂܂��ꍇ
     * ���̓f�[�^�F[1, 2, null, 4, 5, 6]
     * �o�̓f�[�^�F[1, 2, null, 4, 5, 6]
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testContainsNull002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Arrays.asList(3);
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        // ��3�v�f
        assertTrue(collector.hasNext());
        assertNull(collector.next());
        assertEquals("2", collector.getPrevious().getHoge());
        assertNull(collector.getCurrent());
        assertEquals("4", collector.getNext().getHoge());

        // ��4�v�f
        assertTrue(collector.hasNext());
        assertEquals("4", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("4", collector.getCurrent().getHoge());
        assertEquals("5", collector.getNext().getHoge());

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^��bean�Ƃ���null���܂܂��ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, null]
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, null]
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testContainsNull003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Arrays.asList(6);
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertNull(collector.next());
        assertEquals("5", collector.getPrevious().getHoge());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.CONTINUE
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, 6]
     * ��ValidateErrorStatus.CONTINUE�̏ꍇ�A���̓`�F�b�N�G���[���̂������������ƂɂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorContinue001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler(ValidateErrorStatus.CONTINUE);
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.CONTINUE
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, 6]
     * ��ValidateErrorStatus.CONTINUE�̏ꍇ�A���̓`�F�b�N�G���[���̂������������ƂɂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorContinue002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(3);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler(ValidateErrorStatus.CONTINUE);
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.CONTINUE
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, 6]
     * ��ValidateErrorStatus.CONTINUE�̏ꍇ�A���̓`�F�b�N�G���[���̂������������ƂɂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorContinue003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(6);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler(ValidateErrorStatus.CONTINUE);
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.SKIP
     * �o�̓f�[�^�F[2, 3, 4, 5, 6]
     * ��ValidateErrorStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorSkip001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��3�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��4�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.SKIP
     * �o�̓f�[�^�F[1, 2, 4, 5, 6]
     * ��ValidateErrorStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorSkip002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(3);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "4");

        // ��3�v�f
        assertNextData(collector, "4", "2", "4", "5");

        // ��4�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.SKIP
     * �o�̓f�[�^�F[1, 2, 3, 4, 5]
     * ��ValidateErrorStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorSkip003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(6);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2(���̓`�F�b�N�G���[), 3, 4(���̓`�F�b�N�G���[), 5(���̓`�F�b�N�G���[), 6, 7(���̓`�F�b�N�G���[), 8(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.SKIP
     * �o�̓f�[�^�F[3, 6]
     * ��ValidateErrorStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorSkip004() throws Exception {
        int dataNum = 8;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1, 2, 4, 5, 7, 8);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("3", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("3", collector.getCurrent().getHoge());
        assertEquals("6", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("3", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("3", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2(���̓`�F�b�N�G���[), 3(���̓`�F�b�N�G���[), 4(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.SKIP
     * �o�̓f�[�^�F[]
     * ��ValidateErrorStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorSkip005() throws Exception {
        int dataNum = 4;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1, 2, 3, 4);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f���������Ƃ��m�F
        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.END
     * �o�̓f�[�^�F[]
     * ��ValidateErrorStatus.END�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorEnd001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler(ValidateErrorStatus.END);
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f���������Ƃ��m�F
        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.END
     * �o�̓f�[�^�F[1, 2]
     * ��ValidateErrorStatus.END�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorEnd002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(3);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler(ValidateErrorStatus.END);
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̕ԋp�l�FValidateErrorStatus.END
     * �o�̓f�[�^�F[1, 2]
     * ��ValidateErrorStatus.END�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorEnd003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(6);
        ValidationErrorHandler argValidationErrorHandler = new SkipValidationErrorHandler(ValidateErrorStatus.END);
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ (���̓`�F�b�N�t���R���N�^�̃f�t�H���g�ݒ�)
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * �����̓`�F�b�N�G���[�t���̏o�̓f�[�^�́Anext()�̂ݗ�O���X���[����AgetPrevious()�AgetCurrent()�AgetNext()�ł̓f�[�^���擾�ł���B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithoutExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
        }
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ (���̓`�F�b�N�t���R���N�^�̃f�t�H���g�ݒ�)
     * ���̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * �����̓`�F�b�N�G���[�t���̏o�̓f�[�^�́Anext()�̂ݗ�O���X���[����AgetPrevious()�AgetCurrent()�AgetNext()�ł̓f�[�^���擾�ł���B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithoutExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(3);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
        }
        assertEquals("2", collector.getPrevious().getHoge());
        assertEquals("3", collector.getCurrent().getHoge());
        assertEquals("4", collector.getNext().getHoge());

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ (���̓`�F�b�N�t���R���N�^�̃f�t�H���g�ݒ�)
     * ���̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * �����̓`�F�b�N�G���[�t���̏o�̓f�[�^�́Anext()�̂ݗ�O���X���[����AgetPrevious()�AgetCurrent()�AgetNext()�ł̓f�[�^���擾�ł���B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithoutExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(6);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * �����̓`�F�b�N�G���[�t���̏o�̓f�[�^�́Anext()�̂ݗ�O���X���[����AgetPrevious()�AgetCurrent()�AgetNext()�ł̓f�[�^���擾�ł���B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithThrowExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
        }
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * �����̓`�F�b�N�G���[�t���̏o�̓f�[�^�́Anext()�̂ݗ�O���X���[����AgetPrevious()�AgetCurrent()�AgetNext()�ł̓f�[�^���擾�ł���B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithThrowExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(3);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
        }
        assertEquals("2", collector.getPrevious().getHoge());
        assertEquals("3", collector.getCurrent().getHoge());
        assertEquals("4", collector.getNext().getHoge());

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * �����̓`�F�b�N�G���[�t���̏o�̓f�[�^�́Anext()�̂ݗ�O���X���[����AgetPrevious()�AgetCurrent()�AgetNext()�ł̓f�[�^���擾�ł���B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithThrowExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(6);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (ValidationErrorException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[2, 3, 4, 5, 6]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithSkipExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��3�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��4�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[1, 2, 4, 5, 6]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithSkipExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(3);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "4");

        // ��3�v�f
        assertNextData(collector, "4", "2", "4", "5");

        // ��4�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[1, 2, 3, 4, 5]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithSkipExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(6);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2(���̓`�F�b�N�G���[), 3, 4(���̓`�F�b�N�G���[), 5(���̓`�F�b�N�G���[), 6, 7(���̓`�F�b�N�G���[), 8(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[3, 6]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithSkipExceptionHandler004() throws Exception {
        int dataNum = 8;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1, 2, 4, 5, 7, 8);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("3", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("3", collector.getCurrent().getHoge());
        assertEquals("6", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("3", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("3", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2(���̓`�F�b�N�G���[), 3(���̓`�F�b�N�G���[), 4(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithSkipExceptionHandler005() throws Exception {
        int dataNum = 4;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1, 2, 3, 4);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f���������Ƃ��m�F
        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1(���̓`�F�b�N�G���[), 2, 3, 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.END
     * �o�̓f�[�^�F[]
     * ��CollectorExceptionHandlerStatus.END�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithEndExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(1);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new EndCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f���������Ƃ��m�F
        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3(���̓`�F�b�N�G���[), 4, 5, 6]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.END
     * �o�̓f�[�^�F[1, 2]
     * ��CollectorExceptionHandlerStatus.END�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithEndExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(3);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new EndCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * ���̓`�F�b�N�G���[�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, 6(���̓`�F�b�N�G���[)]
     * ValidationErrorHandler�̌��ʁFValidationErrorException���X���[
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.END
     * �o�̓f�[�^�F[1, 2, 3, 4, 5]
     * ��CollectorExceptionHandlerStatus.END�̏ꍇ�A���̓`�F�b�N�G���[�����������f�[�^�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurValidationErrorThrowWithEndExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Arrays.asList(6);
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Collections.emptyList();
        CollectorExceptionHandler argExceptionHandler = new EndCollectorExceptionHandler();
        Exception thrownException = null;
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[(��O), 2, 3, 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[2, 3, 4, 5, 6]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���͎��ɗ�O�����������v�f�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithSkipExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1);
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��3�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��4�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[1, 2, (��O), 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[1, 2, 4, 5, 6]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���͎��ɗ�O�����������v�f�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithSkipExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(3);
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "4");

        // ��3�v�f
        assertNextData(collector, "4", "2", "4", "5");

        // ��4�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, (��O)]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[1, 2, 3, 4, 5]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���͎��ɗ�O�����������v�f�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithSkipExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(6);
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[(��O), (��O), 3, (��O), (��O), 6, (��O), (��O)]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[3, 6]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���͎��ɗ�O�����������v�f�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithSkipExceptionHandler004() throws Exception {
        int dataNum = 8;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1, 2, 4, 5, 7, 8);
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("3", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("3", collector.getCurrent().getHoge());
        assertEquals("6", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("3", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("3", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[(��O), (��O), (��O), (��O)]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.SKIP
     * �o�̓f�[�^�F[]
     * ��CollectorExceptionHandlerStatus.SKIP�̏ꍇ�A���͎��ɗ�O�����������v�f�́A�X�L�b�v����A�o�͂���Ȃ��B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithSkipExceptionHandler005() throws Exception {
        int dataNum = 4;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1, 2, 3, 4);
        CollectorExceptionHandler argExceptionHandler = new SkipCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f���������Ƃ��m�F
        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[(��O), 2, 3, 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.END
     * �o�̓f�[�^�F[]
     * ��CollectorExceptionHandlerStatus.END�̏ꍇ�A���͎��ɗ�O�����������v�f�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithEndExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1);
        CollectorExceptionHandler argExceptionHandler = new EndCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f���������Ƃ��m�F
        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertNull(collector.getPrevious());
        assertNull(collector.getCurrent());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[1, 2, (��O), 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.END
     * �o�̓f�[�^�F[1, 2]
     * ��CollectorExceptionHandlerStatus.END�̏ꍇ�A���͎��ɗ�O�����������v�f�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithEndExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(3);
        CollectorExceptionHandler argExceptionHandler = new EndCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��ɗ�O�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, (��O)]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.END
     * �o�̓f�[�^�F[1, 2, 3, 4, 5]
     * ��CollectorExceptionHandlerStatus.END�̏ꍇ�A���͎��ɗ�O�����������v�f�̒��O�̃f�[�^���Ō�̃f�[�^�����ƂȂ�B
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithEndExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(6);
        CollectorExceptionHandler argExceptionHandler = new EndCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ƀ����^�C����O�����������ꍇ
     * ���̓f�[�^�F[(�����^�C����O), 2, 3, 4, 5, 6]
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[(�����^�C����O), 2, 3, 4, 5, 6]
     * �������^�C����O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�Ń����^�C����O�����̂܂܃X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurRuntimeExceptionWithoutExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1);
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertNull(collector.getPrevious());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ƀ����^�C����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, (�����^�C����O), 4, 5, 6]
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[1, 2, (�����^�C����O), 4, 5, 6]
     * �������^�C����O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�Ń����^�C����O�����̂܂܃X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurRuntimeExceptionWithoutExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(3);
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }

        // ��3�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("2", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("4", collector.getNext().getHoge());

        // ��4�v�f
        assertTrue(collector.hasNext());
        assertEquals("4", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("4", collector.getCurrent().getHoge());
        assertEquals("5", collector.getNext().getHoge());

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ƀ����^�C����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, (�����^�C����O)]
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, (�����^�C����O)]
     * �������^�C����O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�Ń����^�C����O�����̂܂܃X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurRuntimeExceptionWithoutExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(6);
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }

        // ��6�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ƀ����^�C����O�����������ꍇ
     * ���̓f�[�^�F[(�����^�C����O), 2, 3, 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[(�����^�C����O), 2, 3, 4, 5, 6]
     * �������^�C����O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�Ń����^�C����O�����̂܂܃X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurRuntimeExceptionWithThrowExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1);
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertNull(collector.getPrevious());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ƀ����^�C����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, (�����^�C����O), 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[1, 2, (�����^�C����O), 4, 5, 6]
     * �������^�C����O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�Ń����^�C����O�����̂܂܃X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurRuntimeExceptionWithThrowExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(3);
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }

        // ��3�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("2", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("4", collector.getNext().getHoge());

        // ��4�v�f
        assertTrue(collector.hasNext());
        assertEquals("4", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("4", collector.getCurrent().getHoge());
        assertEquals("5", collector.getNext().getHoge());

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ƀ����^�C����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, (�����^�C����O)]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, (�����^�C����O)]
     * �������^�C����O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�Ń����^�C����O�����̂܂܃X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurRuntimeExceptionWithThrowExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(6);
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = new FileLineException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }

        // ��6�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (FileLineException e) {
            assertSame(thrownException, e);
        }
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ɍ�����O�����������ꍇ
     * ���̓f�[�^�F[(������O), 2, 3, 4, 5, 6]
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[(SystemException�Ƀ��b�v���ꂽ������O), 2, 3, 4, 5, 6]
     * ��������O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()��SystemException�Ɍ�����O�����b�v����X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithoutExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1);
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = new IOException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertNull(collector.getPrevious());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ɍ�����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, (������O), 4, 5, 6]
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[1, 2, (SystemException�Ƀ��b�v���ꂽ������O), 4, 5, 6]
     * ��������O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()��SystemException�Ɍ�����O�����b�v����X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithoutExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(3);
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = new IOException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }

        // ��3�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("2", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("4", collector.getNext().getHoge());

        // ��4�v�f
        assertTrue(collector.hasNext());
        assertEquals("4", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("4", collector.getCurrent().getHoge());
        assertEquals("5", collector.getNext().getHoge());

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ɍ�����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, (������O)]
     * CollectorExceptionHandler�F�Ȃ�
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, (SystemException�Ƀ��b�v���ꂽ������O)]
     * ��������O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()��SystemException�Ɍ�����O�����b�v����X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithoutExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(6);
        CollectorExceptionHandler argExceptionHandler = null;
        Exception thrownException = new IOException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }

        // ��6�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ɍ�����O�����������ꍇ
     * ���̓f�[�^�F[(������O), 2, 3, 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[(SystemException�Ƀ��b�v���ꂽ������O), 2, 3, 4, 5, 6]
     * ��������O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()��SystemException�Ɍ�����O�����b�v����X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithThrowExceptionHandler001() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(1);
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = new IOException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertNull(collector.getPrevious());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("2", collector.getCurrent().getHoge());
        assertEquals("3", collector.getNext().getHoge());

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ɍ�����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, (������O), 4, 5, 6]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[1, 2, (SystemException�Ƀ��b�v���ꂽ������O), 4, 5, 6]
     * ��������O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()��SystemException�Ɍ�����O�����b�v����X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithThrowExceptionHandler002() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(3);
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = new IOException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertTrue(collector.hasNext());
        assertEquals("2", collector.next().getHoge());
        assertEquals("1", collector.getPrevious().getHoge());
        assertEquals("2", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }

        // ��3�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("2", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("4", collector.getNext().getHoge());

        // ��4�v�f
        assertTrue(collector.hasNext());
        assertEquals("4", collector.next().getHoge());
        try {
            collector.getPrevious();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("4", collector.getCurrent().getHoge());
        assertEquals("5", collector.getNext().getHoge());

        // ��5�v�f
        assertNextData(collector, "5", "4", "5", "6");

        // ��6�v�f
        assertTrue(collector.hasNext());
        assertEquals("6", collector.next().getHoge());
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        assertEquals("6", collector.getCurrent().getHoge());
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �f�[�^���͎��Ɍ�����O�����������ꍇ
     * ���̓f�[�^�F[1, 2, 3, 4, 5, (������O)]
     * CollectorExceptionHandler�̌��ʁFCollectorExceptionHandlerStatus.THROW
     * �o�̓f�[�^�F[1, 2, 3, 4, 5, (SystemException�Ƀ��b�v���ꂽ������O)]
     * ��������O�����v�f�́Anext()�AgetPrevious()�AgetCurrent()�AgetNext()��SystemException�Ɍ�����O�����b�v����X���[�����
     * �EhasNext()�Anext()�AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ��A�o�̓f�[�^�ɑ��������̂ł��邱��
     * �E���̃f�[�^��������Ԃ�next()�����s�����NoSuchElementException���X���[����邱��
     * �E���̃f�[�^��������Ԃ�next()�����s���Ă��AgetPrevious()�AgetCurrent()�AgetNext()�̌��ʂ�next()���s�O�ƕς��Ȃ�����
     */
    @Test
    public void testOccurExceptionWithThrowExceptionHandler003() throws Exception {
        int dataNum = 6;
        List<Integer> validationErrorOccurPoints = Collections.emptyList();
        ValidationErrorHandler argValidationErrorHandler = new ExceptionValidationErrorHandler();
        List<Integer> exceptionOccurPoints = Arrays.asList(6);
        CollectorExceptionHandler argExceptionHandler = new ThrowCollectorExceptionHandler();
        Exception thrownException = new IOException("test");
        List<Integer> nullBeanPoints = Collections.emptyList();
        Collector<AbstractCollectorTestBean> collector = createTestCollector(dataNum, validationErrorOccurPoints, argValidationErrorHandler, exceptionOccurPoints, argExceptionHandler, thrownException, nullBeanPoints);

        // ��1�v�f
        assertTrue(collector.hasNext());
        assertEquals("1", collector.next().getHoge());
        assertNull(collector.getPrevious());
        assertEquals("1", collector.getCurrent().getHoge());
        assertEquals("2", collector.getNext().getHoge());

        // ��2�v�f
        assertNextData(collector, "2", "1", "2", "3");

        // ��3�v�f
        assertNextData(collector, "3", "2", "3", "4");

        // ��4�v�f
        assertNextData(collector, "4", "3", "4", "5");

        // ��5�v�f
        assertTrue(collector.hasNext());
        assertEquals("5", collector.next().getHoge());
        assertEquals("4", collector.getPrevious().getHoge());
        assertEquals("5", collector.getCurrent().getHoge());
        try {
            collector.getNext();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }

        // ��6�v�f
        assertTrue(collector.hasNext());
        try {
            collector.next();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertNull(collector.getNext());

        assertFalse(collector.hasNext());

        try {
            collector.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        assertEquals("5", collector.getPrevious().getHoge());
        try {
            collector.getCurrent();
            fail();
        } catch (SystemException e) {
            assertSame(thrownException, e.getCause());
        }
        assertNull(collector.getNext());

        collector.close();
    }

    /**
     * �P���Ƀf�[�^���擾�ł���ꍇ�Ɏg�p�ł���ȈՃA�T�[�V�������\�b�h�B<br>
     * assertTrue(collector.hasNext());<br>
     * assertEquals("2", collector.next().getHoge());<br>
     * assertEquals("1", collector.getPrevious().getHoge());<br>
     * assertEquals("2", collector.getCurrent().getHoge());<br>
     * assertEquals("3", collector.getNext().getHoge());<br>
     * ��<br>
     * assertNextData("2", "1", "2", "3");<br>
     * �ƋL�q�ł���B<br>
     * ��O�����P�[�X��null��Ԃ��P�[�X�ł́A���̃��\�b�h�͎g�p�����A�ʂɃA�T�[�V���������{���邱�ƁB
     * @param collector �R���N�^
     * @param nextExpectedHoge collector.next().getHoge()�̊��Ғl
     * @param getPreviousExpectedHoge collector.getPrevious().getHoge()�̊��Ғl
     * @param getCurrentExpectedHoge collector.getCurrent().getHoge()�̊��Ғl
     * @param getNextExpectedHoge collector.getNext().getHoge()�̊��Ғl
     */
    private static void assertNextData(Collector<AbstractCollectorTestBean> collector, String nextExpectedHoge, String getPreviousExpectedHoge, String getCurrentExpectedHoge, String getNextExpectedHoge) {
        assertTrue(collector.hasNext());
        assertEquals(nextExpectedHoge, collector.next().getHoge());
        assertEquals(getPreviousExpectedHoge, collector.getPrevious().getHoge());
        assertEquals(getCurrentExpectedHoge, collector.getCurrent().getHoge());
        assertEquals(getNextExpectedHoge, collector.getNext().getHoge());
    }

    /**
     * �e�X�g�f�[�^��񋟂���R���N�^�𐶐�����B
     * <p>
     * ��1) ���̓f�[�^�F[1, 2, 3, 4, 5]�̍���<br>
     * createTestCollector(5, Collections.EMPTY_LIST, new ExceptionValidationErrorHandler(), Collections.EMPTY_LIST, null, null, Collections.EMPTY_LIST);<br>
     * <br>
     * ��2) ���̓f�[�^�F[1, null, 3, null, 5]�̍���<br>
     * createTestCollector(5, Collections.EMPTY_LIST, new ExceptionValidationErrorHandler(), Collections.EMPTY_LIST, null, null, Arrays.asList(2, 4));<br>
     * <br>
     * ��3) ���̓f�[�^�F[1, 2(���̓`�F�b�N�G���[), 3, 4(���̓`�F�b�N�G���[), 5]�̍���<br>
     * createTestCollector(5, Arrays.asList(2, 4), new ExceptionValidationErrorHandler(), Collections.EMPTY_LIST, null, null, Collections.EMPTY_LIST);<br>
     * <br>
     * ��4) ���̓f�[�^�F[1, (��O), 3, 4(��O), 5]�̍���<br>
     * createTestCollector(5, Collections.EMPTY_LIST, new ExceptionValidationErrorHandler(), Arrays.asList(2, 4), null, ��O, Collections.EMPTY_LIST);<br>
     * <br>
     * ���^�C�v�Z�[�t�ɂ���ꍇ�́ACollections.EMPTY_LIST�̑���ɁACollections.emptyList()��List��Integer���^�̕ϐ��ɓ���Ďg�p����B
     * @param dataNum �f�[�^�̌�
     * @param validationErrorOccurPoints ���̓`�F�b�N�G���[�����|�C���g
     * @param argValidationErrorHandler ValidationErrorHandler
     * @param exceptionOccurPoints ��O�����|�C���g
     * @param argExceptionHandler ExceptionHandler
     * @param thrownException �X���[�����O
     * @param nullBeanPoints Bean�̑����null���l�߂�|�C���g
     * @return �e�X�g�f�[�^��񋟂���R���N�^
     */
    private static Collector<AbstractCollectorTestBean> createTestCollector(final int dataNum, final List<Integer> validationErrorOccurPoints, final ValidationErrorHandler argValidationErrorHandler, final List<Integer> exceptionOccurPoints, final CollectorExceptionHandler argExceptionHandler, final Exception thrownException, final List<Integer> nullBeanPoints) {
        Collector<AbstractCollectorTestBean> collector = new AbstractCollector<AbstractCollectorTestBean>() {
            {
                this.validator = new Validator() {

                    public void validate(Object target, Errors errors) {
                        AbstractCollectorTestBean data = (AbstractCollectorTestBean) target;
                        if (validationErrorOccurPoints.contains(new Integer(data.getHoge()))) {
                            errors.rejectValue("hoge", "errors.required");
                        }
                    }

                    public boolean supports(Class<?> clazz) {
                        return (clazz == AbstractCollectorTestBean.class);
                    }
                };
                this.validationErrorHandler = argValidationErrorHandler;
                this.exceptionHandler = argExceptionHandler;
            }

            public Integer call() throws Exception {
                for (int count = 1; count <= dataNum; count++) {
                    if (exceptionOccurPoints.contains(count)) {
                        addQueue(new DataValueObject(thrownException, count));
                    } else if (nullBeanPoints.contains(count)) {
                        addQueue(new DataValueObject(null, count));
                    } else {
                        AbstractCollectorTestBean bean = new AbstractCollectorTestBean();
                        bean.setHoge(String.valueOf(count));
                        addQueue(new DataValueObject(bean, count));
                    }
                }

                setFinish();
                return 0;
            }

        };

        return collector;

    }

    /**
     * CollectorExceptionHandlerStatus.END��ԋp����CollectorExceptionHandler�B
     */
    private static class EndCollectorExceptionHandler implements CollectorExceptionHandler {
        public CollectorExceptionHandlerStatus handleException(
                DataValueObject dataValueObject) {
            return CollectorExceptionHandlerStatus.END;
        }
    }

    /**
     * CollectorExceptionHandlerStatus.SKIP��ԋp����CollectorExceptionHandler�B
     */
    private static class SkipCollectorExceptionHandler implements CollectorExceptionHandler {
        public CollectorExceptionHandlerStatus handleException(
                DataValueObject dataValueObject) {
            return CollectorExceptionHandlerStatus.SKIP;
        }
    }

    /**
     * CollectorExceptionHandlerStatus.THROW��ԋp����CollectorExceptionHandler�B
     */
    private static class ThrowCollectorExceptionHandler implements CollectorExceptionHandler {
        public CollectorExceptionHandlerStatus handleException(
                DataValueObject dataValueObject) {
            return CollectorExceptionHandlerStatus.THROW;
        }
    }

}

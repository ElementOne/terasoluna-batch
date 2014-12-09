package jp.terasoluna.fw.collector;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import jp.terasoluna.fw.collector.concurrent.ArrayBlockingQueueEx;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandlerStatus;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.vo.CollectorStatus;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.ex.unit.util.ReflectionUtils;
import jp.terasoluna.fw.exception.SystemException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractCollector002Test {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCollector.setVerbose(true);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCollector.setVerbose(false);
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 
     */
    @Test
    public void testExecute001() {
        AbstractCollectorStubHasNext1<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext1<AbstractCollectorTestBean>();

        // �e�X�g
        try {
            col.execute();
        } catch (SystemException e) {
            assertNotNull(e);
            assertEquals(SystemException.class, e.getClass());
            return;
        }

        fail();
        return;
    }

    /**
     * 
     */
    @Test
    public void testHasNext001() {
        AbstractCollectorStubHasNext2<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext2<AbstractCollectorTestBean>();

        // �e�X�g
        boolean result = col.hasNext();

        assertFalse(result);
    }

    /**
     * 
     */
    @Test
    public void testHasNext002() {
        AbstractCollectorStubHasNext3<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext3<AbstractCollectorTestBean>();

        // �e�X�g
        boolean result = col.hasNext();

        assertFalse(result);
    }

    /**
     * 
     */
    @Test
    public void testHasNext003() {
        AbstractCollectorStubHasNext3<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext3<AbstractCollectorTestBean>();

        // �e�X�g
        boolean result = col.hasNext();

        assertFalse(result);
    }

    /**
     * 
     */
    @Test
    public void testHasNext004() {
        AbstractCollectorStubHasNext4<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext4<AbstractCollectorTestBean>();

        // �e�X�g
        boolean result = col.hasNext();

        assertFalse(result);
    }

    /**
     * 
     */
    @Test
    public void testNext001() {
        AbstractCollectorStubNext1<AbstractCollectorTestBean> col = new AbstractCollectorStubNext1<AbstractCollectorTestBean>();

        AbstractCollectorTestBean result = null;
        try {
            result = col.next();
            fail();
        } catch (NoSuchElementException e) {
            assertNotNull(e);
            assertNull(result);
        }
    }

    /**
     * 
     */
    @Test
    public void testNext002() {
        AbstractCollectorStubNext2<AbstractCollectorTestBean> col = new AbstractCollectorStubNext2<AbstractCollectorTestBean>();

        @SuppressWarnings("unused")
        AbstractCollectorTestBean result = null;
        try {
            result = col.next();
        } catch (Exception e) {
            assertNotNull(e);
            assertEquals(SystemException.class, e.getClass());
            assertEquals(Exception.class, e.getCause().getClass());
            assertEquals(null, e.getCause().getMessage());
            return;
        }

        fail();
    }

    /**
     * 
     */
    @Test
    public void testNext003() {
        AbstractCollectorStubNext3<AbstractCollectorTestBean> col = new AbstractCollectorStubNext3<AbstractCollectorTestBean>();

        @SuppressWarnings("unused")
        AbstractCollectorTestBean result = null;
        try {
            result = col.next();
        } catch (Exception e) {
            assertNotNull(e);
            assertEquals(SystemException.class, e.getClass());
            assertEquals(Exception.class, e.getCause().getClass());
            assertEquals("hoge", e.getCause().getMessage());
            return;
        }

        fail();
    }

    /**
     * 
     */
    @Test
    public void testNext004() {
        AbstractCollectorStubNext4<AbstractCollectorTestBean> col = new AbstractCollectorStubNext4<AbstractCollectorTestBean>();
        col.exceptionHandler = new CollectorExceptionHandlerStub1();

        AbstractCollectorTestBean result = null;

        try {
            result = col.next();
            fail();
        } catch (NoSuchElementException e) {
            assertNotNull(e);
            assertNull(result);
        }
    }

    /**
     * 
     */
    @Test
    public void testGetNext() {
        AbstractCollectorStubGetNext1<AbstractCollectorTestBean> col = new AbstractCollectorStubGetNext1<AbstractCollectorTestBean>();
        col.exceptionHandler = new CollectorExceptionHandlerStub1();

        AbstractCollectorTestBean result = null;

        try {
            result = col.getNext();
            fail();
        } catch (Throwable throwable) {
            // NOP
        }

        assertNull(result);
    }

    /**
     * testFinalize
     * @throws Throwable
     */
    @Test
    public void testFinalize() throws Throwable {
        AbstractCollectorStubGetNext1<AbstractCollectorTestBean> col = new AbstractCollectorStubGetNext1<AbstractCollectorTestBean>();

        col.finalize();
    }

    // @Test
    // public void testGetPrevious() {
    // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testGetCurrent() {
    // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testClose() {
    // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testRemove() {
    // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testIterator() {
    // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testSetQueueSize() {
    // // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testGetSleepWait() {
    // // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testSetSleepWait() {
    // // fail("�܂���������Ă��܂���");
    // }
    //
    // @Test
    // public void testCloseQuietly() {
    // // fail("�܂���������Ă��܂���");
    // }

    
    /**
     * hasNext()�̃e�X�g
     * �X�L�b�v�����ɂ����āAValidateErrorStatus��SKIP�̏ꍇ�̓���m�F
     */
    @Test
    public void testHasNext005() throws InterruptedException{
        AbstractCollectorStubHasNext5<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext5<AbstractCollectorTestBean>(2);
        DataValueObject vo1 = new DataValueObject("hoge");
        DataValueObject skip = new DataValueObject(ValidateErrorStatus.SKIP); 
        
        col.addQueue(skip);
        col.addQueue(vo1);
        
        // �e�X�g
        boolean result = col.hasNext();

        assertTrue(result);
    }    


    /**
     * hasNext()�̃e�X�g
     * �X�L�b�v�����ɂ����āAValidateErrorStatus��END�̏ꍇ�̓���m�F
     */
    @Test
    public void testHasNext006() throws InterruptedException{
        AbstractCollectorStubHasNext5<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext5<AbstractCollectorTestBean>(2);
        DataValueObject vo1 = new DataValueObject(new Object());
        DataValueObject end = new DataValueObject(ValidateErrorStatus.END); 
        
        col.addQueue(end);
        col.addQueue(vo1);
        
        // �e�X�g
        boolean result = col.hasNext();

        assertFalse(result);
    }

    /**
     * hasNext()�̃e�X�g
     * �X�L�b�v�����ɂ����āA��O���������A��O�n���h���̏������ʂ�SKIP�ƂȂ����ꍇ�̓���m�F
     */
    @Test
    public void testHasNext007() throws Exception {
		AbstractCollectorStubHasNext5<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext5<AbstractCollectorTestBean>(
				2, CollectorExceptionHandlerStatus.SKIP);
        DataValueObject vo1 = new DataValueObject(new Object());
        DataValueObject skip = new DataValueObject(new Exception("hasNext()�e�X�g�F��O�n���h���̏�������SKIP")); 

        col.addQueue(skip);
        col.addQueue(vo1);

        // �e�X�g
        boolean result = col.hasNext();

        assertTrue(result);
    }
    
    /**
     * hasNext()�̃e�X�g
     * �X�L�b�v�����ɂ����āA��O���������A��O�n���h���̏������ʂ�END�ƂȂ����ꍇ�̓���m�F
     */
    @Test
    public void testHasNext008() throws Exception {
		AbstractCollectorStubHasNext5<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext5<AbstractCollectorTestBean>(
				2, CollectorExceptionHandlerStatus.END);
        DataValueObject vo1 = new DataValueObject(new Object());
        DataValueObject end = new DataValueObject(new Exception("hasNext()�e�X�g�F��O�n���h���̏�������END")); 

        col.addQueue(end);
        col.addQueue(vo1);
        
        // �e�X�g
        boolean result = col.hasNext();

        assertFalse(result);
    }
    
    /**
     * handleException()�̃e�X�g
     * ����ς݂łȂ�DataValueObject���n���ꂽ�ꍇ�AexceptionHandler#handleException�̌��ʂ�Ԃ����Ƃ��m�F
     */
    @Test
    public void testHandleException001() throws Exception {
		AbstractCollectorStubHasNext5<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext5<AbstractCollectorTestBean>(
				2, CollectorExceptionHandlerStatus.END);
        DataValueObject end = new DataValueObject(new Exception("hasNext()�e�X�g�F��O�n���h���̏�������END"));
    	
        // �e�X�g
        assertEquals(CollectorExceptionHandlerStatus.END, col.handleException(end));
        assertEquals(CollectorExceptionHandlerStatus.END, end.getExceptionHandlerStatus());
    }
    
    /**
     * handleException()�̃e�X�g
     * exceptionHandlerStatus �ɔ���ς݂̌��ʂ��i�[����Ă���ꍇ�́A���߂Ĕ���͍s�킸����ς݂̌��ʂ�Ԃ����Ƃ��m�F�B
     * �{�e�X�g�R�[�h�ł́AhandleExeption �̌��ʂ� CollectorExceptionHandlerStatus.END �ƂȂ��Ă��Ă�
     * ����ς݂̌��� CollectorExceptionHandlerStatus.SKIP ���ԋp����邱�Ƃ��m�F���Ă���B
     */
    @Test
    public void testHandleException002() throws Exception {
		AbstractCollectorStubHasNext5<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext5<AbstractCollectorTestBean>(
				2, CollectorExceptionHandlerStatus.END);
        DataValueObject skip = new DataValueObject(new Exception("hasNext()�e�X�g�F��O�n���h���̏�������SKIP"));
        skip.setExceptionHandlerStatus(CollectorExceptionHandlerStatus.SKIP);
    	
        // �e�X�g
        assertEquals(skip.getExceptionHandlerStatus(), col.handleException(skip));
    }
    
    /**
     * handleException()�̃e�X�g
     * exceptionHandler == null��Collector�ł���ꍇ�Anull��Ԃ����Ƃ��m�F
     */
    @Test
    public void testHandleException003() throws Exception {
		AbstractCollectorStubHasNext5<AbstractCollectorTestBean> col = new AbstractCollectorStubHasNext5<AbstractCollectorTestBean>(2);
        DataValueObject skip = new DataValueObject("hoge");
    	
        // �e�X�g
        assertNull(col.handleException(skip));
    }
    
    /**
     * setFinish()�̃e�X�g
     * NotificationBlockingQueue�̃C���X�^���X�ł���ꍇ�A
     * �L���[�ɑ΂��L���[�C���O�̏I����ʒm���鏈�������s���邱�Ƃ��m�F����
     */
    @Test
    public void testSetFinish001() throws Exception {
    	// AbstractCollectorStubSetFinish1�ł�ArrayBlockingQueue���g�p����
    	AbstractCollectorStubSetFinish1<AbstractCollectorTestBean> col = new AbstractCollectorStubSetFinish1<AbstractCollectorTestBean>();
    	// �L���[���쐬����B�L���[��execute���\�b�h���ł̂�create�����B
    	col.execute();

    	// AbstractCollector�̏I���t���Ofinish���m�F
    	boolean finish = (Boolean) ReflectionUtils.getField(col, AbstractCollector.class, "finish");
    	assertFalse(finish);
    	
    	// �e�X�g���{
    	col.setFinish();
    	// �I���t���O���L���[�ɓ����Ă��邱�Ƃ��m�F
    	assertEquals(CollectorStatus.END, col.getQueue().peek().getCollectorStatus());
    	// finishQueueingFlag���m�F�B
    	// finishQueueing�����s����Ă���΃t���O��true�ɂȂ��Ă���͂��B
    	ArrayBlockingQueueEx<DataValueObject> arrayBlockingQueueEx = (ArrayBlockingQueueEx<DataValueObject>) col.getQueue();
    	boolean finishQueueingFlag = (Boolean) ReflectionUtils.getField(arrayBlockingQueueEx, "finishQueueingFlag");
    	assertTrue(finishQueueingFlag);
    	
    	// AbstractCollector�̏I���t���Ofinish���m�F
    	finish = (Boolean) ReflectionUtils.getField(col, AbstractCollector.class, "finish");
    	assertTrue(finish);
    }

    /**
     * setFinish()�̃e�X�g
     * NotificationBlockingQueue�̃C���X�^���X�łȂ��ꍇ�A
     * �L���[�ɑ΂��L���[�C���O�̏I����ʒm���鏈�������s�����I�����邱�Ƃ��m�F����
     */
    @Test
    public void testSetFinish002() throws Exception {
    	// AbstractCollectorStubSetFinish2�ł�ArrayBlockingQueue���g�p����
    	AbstractCollectorStubSetFinish2<AbstractCollectorTestBean> col = new AbstractCollectorStubSetFinish2<AbstractCollectorTestBean>();
    	// �L���[���쐬����B�L���[��execute���\�b�h���ł̂�create�����B
    	col.execute();
    	
    	// AbstractCollector�̏I���t���Ofinish���m�F
    	boolean finish = (Boolean) ReflectionUtils.getField(col, AbstractCollector.class, "finish");
    	assertFalse(finish);
    	
    	// �e�X�g���{
    	col.setFinish();
    	// �I���t���O���L���[�ɓ����Ă��邱�Ƃ��m�F
    	assertEquals(CollectorStatus.END, col.getQueue().peek().getCollectorStatus());
    	
    	// AbstractCollector�̏I���t���Ofinish���m�F
    	finish = (Boolean) ReflectionUtils.getField(col, AbstractCollector.class, "finish");
    	assertTrue(finish);    	
    }
}

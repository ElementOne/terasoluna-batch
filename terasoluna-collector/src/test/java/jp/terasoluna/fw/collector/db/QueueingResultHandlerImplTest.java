package jp.terasoluna.fw.collector.db;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueingResultHandlerImplTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        QueueingResultHandlerImpl.setVerbose(true);
    }

    @After
    public void tearDown() throws Exception {
        QueueingResultHandlerImpl.setVerbose(false);
        Thread.interrupted();
    }

    /**
     * testHandleResult
     */
    @Test
    public void testHandleResult001() {
        QueueingResultHandlerImpl drh = new QueueingResultHandlerImpl();
        DummyResultContext ctxInNull = new DummyResultContext();
        ctxInNull.setResultObject(null);
        assertNotNull(drh);
        try {
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
            drh.handleResult(ctxInNull);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * testHandleResult
     */
    @Test
    public void testHandleResult002() {
        QueueingResultHandlerImpl drh = new QueueingResultHandlerImpl();

        assertNotNull(drh);
        try {
            DummyResultContext context = new DummyResultContext();
            context.setResultObject("hoge1");
            drh.handleResult(context);
            context = new DummyResultContext();
            context.setResultObject("hoge2");
            drh.handleResult(context);
            DummyResultContext contextInNull = new DummyResultContext();
            contextInNull.setResultObject(null);
            drh.handleResult(contextInNull);
            context = new DummyResultContext();
            context.setResultObject("hoge3");
            drh.handleResult(context);
            context = new DummyResultContext();
            context.setResultObject("hoge4");
            drh.handleResult(context);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * testHandleResult
     */
    @Test
    public void testHandleResult003() {
        QueueingResultHandlerImpl drh = new QueueingResultHandlerImpl();
        DaoCollector<HogeBean> daoCollector = new DaoCollectorStub004(5);
        drh.setDaoCollector(daoCollector);

        assertNotNull(drh);

        try {
            DummyResultContext context = new DummyResultContext();
            context.setResultObject("hoge1");
            drh.handleResult(context);
            context.setResultObject("hoge2");
            drh.handleResult(context);
            DummyResultContext contextInNull = new DummyResultContext();
            contextInNull.setResultObject(null);
            drh.handleResult(contextInNull);
            context.setResultObject("hoge3");
            drh.handleResult(context);
            context.setResultObject("hoge4");
            drh.handleResult(context);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testHandleResult004() throws Exception {
        final QueueingResultHandlerImpl drh = new QueueingResultHandlerImpl();
        DaoCollector<HogeBean> daoCollector = new DaoCollectorStub001();
        drh.setDaoCollector(daoCollector);

        ExecutorService service = Executors.newSingleThreadExecutor();
        ErrorFeedBackRunnable runnable = new ErrorFeedBackRunnable() {
            @Override
            public void doRun() {
                Thread.currentThread().interrupt();
                // ���荞�ݔ�������handleResult�͏������ꂸ�A�X���b�h�����荞�ݏ�Ԃ̂܂܂ł��邱�ƁB
                DummyResultContext context = new DummyResultContext();
                context.setResultObject("hoge1");
                drh.handleResult(context);
                assertTrue(Thread.currentThread().isInterrupted());
                assertNull(drh.prevRow);
            }
        };
        service.submit(runnable);
        runnable.throwErrorOrExceptionIfThrown();
    }

    /**
     * testHandleResult
     */
    @Test
    public void testHandleResult005() {
        QueueingResultHandlerImpl drh = new QueueingResultHandlerImpl();
        DaoCollectorStub001 daoCollector = new DaoCollectorStub001();
        drh.setDaoCollector(daoCollector);

        assertNotNull(drh);

        DummyResultContext context = new DummyResultContext();
        context.setResultObject("hoge1");
        drh.handleResult(context);
        context.setResultObject("hoge2");
        drh.handleResult(context);
        daoCollector.exceptionFlag = true;
        context.setResultObject("hoge3");
        drh.handleResult(context);
        assertTrue(Thread.currentThread().isInterrupted());
    }

    @Test
    public void testDelayCollect001() throws Exception {
        final QueueingResultHandlerImpl drh = new QueueingResultHandlerImpl();
        DaoCollectorStub004 daoCollector = new DaoCollectorStub004(1);
        drh.setDaoCollector(daoCollector);
        drh.prevRow = "rowObject";

        ExecutorService service = Executors.newSingleThreadExecutor();
        ErrorFeedBackRunnable runnable = new ErrorFeedBackRunnable() {
            @Override
            public void doRun() throws Exception {
                Thread.currentThread().interrupt();
                // ���荞�ݔ�������delayCollect�͏������ꂸ�A�X���b�h�����荞�ݏ�Ԃ̂܂܂ł��邱�ƁB
                drh.delayCollect();
                assertTrue(Thread.currentThread().isInterrupted());
            }
        };
        service.submit(runnable);
        runnable.throwErrorOrExceptionIfThrown();

        // ���荞�ݗ�O�ɂ��L���[�C���O����Ă��Ȃ����ƁB
        assertEquals(0, daoCollector.getQueue().size());
    }

    @Test
    public void testDelayCollect002() throws Exception {
        final QueueingResultHandlerImpl drh = new QueueingResultHandlerImpl();
        DaoCollectorStub003 daoCollector = new DaoCollectorStub003();
        drh.setDaoCollector(daoCollector);
        drh.prevRow = "rowObject";
        ExecutorService service = Executors.newSingleThreadExecutor();
        ErrorFeedBackRunnable runnable = new ErrorFeedBackRunnable() {
            @Override
            public void doRun() throws Exception {
                drh.delayCollect();
                assertTrue(Thread.currentThread().isInterrupted());
            }
        };
        Future<?> future = service.submit(runnable);
        while (true) {
            try {
                // �R���N�^�̃L���[�}���X���b�h���u���b�N�����܂ŏ����҂�
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            if (daoCollector.isBlocked()) {
                break;
            }
        }
        // �^�X�N�L�����Z�������s�B
        future.cancel(true);
        runnable.throwErrorOrExceptionIfThrown();
    }

    /**
     * �G���[���t�B�[�h�o�b�N�ł���Runnable�����B
     * �ʃX���b�h�Ŏ��{���������e�� doRun() throws Exception �Ɏ�������B
     * �����I�����AthrowErrorOrExceptionIfThrown���\�b�h�����s����ƁA
     * doRun���\�b�h�ɂđz��O�̃G���[�����������ꍇ�ɁA���̃G���[���X���[�����B
     */
    abstract class ErrorFeedBackRunnable implements Runnable {
        private Exception exception;
        private Error error;
        private CountDownLatch latch = new CountDownLatch(1);

        public void run() {
            try {
                doRun();
            } catch (Exception e) {
                exception = e;
            } catch (Error e) {
                error = e;
            } finally {
                latch.countDown();
            }
        }

        public void throwErrorOrExceptionIfThrown() throws Exception {
            latch.await();
            if (error != null) {
                throw error;
            } else if (exception != null) {
                throw exception;
            }
        }

        abstract void doRun() throws Exception;
    }

}

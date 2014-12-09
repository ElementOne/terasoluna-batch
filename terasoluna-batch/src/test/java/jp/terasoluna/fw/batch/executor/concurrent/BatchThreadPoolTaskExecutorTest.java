package jp.terasoluna.fw.batch.executor.concurrent;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


public class BatchThreadPoolTaskExecutorTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * setQueueCapacity�̈����Ɋւ�炸�AqueueCapacity��0���ݒ肳��邱�ƁB
     */
    public void testSetQueueCapacity() throws Exception {
        BatchThreadPoolTaskExecutor executor = new BatchThreadPoolTaskExecutor();
        executor.setQueueCapacity(100);
        Field f = ThreadPoolTaskExecutor.class.getDeclaredField("queueCapacity");
        f.setAccessible(true);
        assertEquals(0, f.getInt(executor));
    }

    /**
     * queueCapacity��0���ݒ肳��邱�ƁB
     */
    public void testBatchThreadPoolTaskExecutor() throws Exception {
        BatchThreadPoolTaskExecutor executor = new BatchThreadPoolTaskExecutor();
        Field f = ThreadPoolTaskExecutor.class.getDeclaredField("queueCapacity");
        f.setAccessible(true);
        assertEquals(0, f.getInt(executor));
    }

}

package jp.terasoluna.fw.collector.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.ex.unit.util.ReflectionUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArrayBlockingQueueExTest {

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
     * �R���X�g���N�^�̊m�F
     * ArrayBlockingQueueEx(int capacity, boolean fair)
     * fair�̎w�肪true�̏ꍇ
     */
    @Test
    public void testConstructor001() {
    	int capacity = 3;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity, true);
    	
    	// capacity �̊m�F
    	int capacityResult = (Integer) ReflectionUtils.getField(queue, "capacity");
    	assertEquals(capacity, capacityResult);
    	
    	// fair �̊m�F(true �ɐݒ肵���ꍇ�́AFairSync, false �̏ꍇ�� NonFairSysc)
    	//    ReflectionUtil �𗘗p���Aqueue �� lock ���擾
    	//    lock�̌�������Fair �isync��FairSync �̃C���X�^���X�j�ł��邱�Ƃ��m�F����B
    	ReentrantLock lockResult = ReflectionUtils.getField(queue, ArrayBlockingQueue.class, "lock");
    	assertTrue(lockResult.isFair());    	
    	
    }

    /**
     * �R���X�g���N�^�̊m�F
     * ArrayBlockingQueueEx(int capacity, boolean fair)
     * fair�̎w�肪false�̏ꍇ
     */
    @Test
    public void testConstructor002() {
    	int capacity = 3;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity, false);
    	
    	// capacity �̊m�F
    	int capacityResult = (Integer) ReflectionUtils.getField(queue, "capacity");
    	assertEquals(capacity, capacityResult);
    	
    	// fair �̊m�F(true �ɐݒ肵���ꍇ�́AFairSync, false �̏ꍇ�� NonFairSysc)
    	//    ReflectionUtil �𗘗p���Aqueue �� lock ���擾
    	//    lock�̌�������NonFair �isync��FairSync �̃C���X�^���X�ł͂Ȃ��j�ł��邱�Ƃ��m�F����B
    	ReentrantLock lockResult = ReflectionUtils.getField(queue, ArrayBlockingQueue.class, "lock");
    	assertFalse(lockResult.isFair());
    	
    }

    /**
     * �R���X�g���N�^�̊m�F
     * ArrayBlockingQueueEx(int capacity)
     */
    @Test
    public void testConstructor003() {
    	int capacity = 3;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// capacity �̊m�F
    	int capacityResult = (Integer) ReflectionUtils.getField(queue, "capacity");
    	assertEquals(capacity, capacityResult);
    	
    }
    

    // AbstractCollector#setFinish �̎����ɂĎ��{����B
//    @Test
//    public void testFinishQueueing001() {
//    }
    
    
    
    /**
     * poll(long timeout, TimeUnit unit) �̃e�X�g
     * �ُ�n�FInterruptedException�̊m�F
     */
    @Test
    public void testPoll001() throws Exception{
    	int capacity = 1;
    	
    	final long timeout = 20000;
    	final TimeUnit unit = TimeUnit.MILLISECONDS;
    	final CountDownLatch threadSync = new CountDownLatch(1);

    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �ʃX���b�h��offer�����s�i�L���[����Ȃ̂ő҂��ɂȂ�j
        ErrorFeedBackThread thread01 = new ErrorFeedBackThread() {
        	@Override
        	public void doRun() throws Exception {
        		long timeStart = System.currentTimeMillis();
        		threadSync.countDown();
        		try {
        			// �҂���Ԃ�����
        			queue.poll(timeout, unit);
        			fail();
        		} catch (InterruptedException e) {
        			// ���Ғʂ�
        		}
    			long timeEnd = System.currentTimeMillis();
    			long timeDiff = timeEnd - timeStart;
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if(timeDiff < 950) {
    				fail();
    			}
        	}
        };
        
    	thread01.start();

    	threadSync.await();
    	Thread.sleep(1000);
    	
    	// ���荞��
    	thread01.interrupt();
    	
    	thread01.throwErrorOrExceptionIfThrown();
    }

    /**
     * poll(long timeout, TimeUnit unit) �̃e�X�g
     * ����n�F�^�C���A�E�g��ɃL���[����̏ꍇ��null��Ԃ��m�F
     */
    @Test
    public void testPoll002() throws Exception{
    	int capacity = 1;
    	
    	long timeout = 1000;
    	TimeUnit unit = TimeUnit.MILLISECONDS;

    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �L���[����ł��邱�Ƃ̊m�F
    	assertEquals(0, queue.size());
    	
    	// poll�̎��s�Ə������Ԍv��
    	long timeStart = System.currentTimeMillis();
    	DataValueObject objResult = queue.poll(timeout, unit);
    	long timeEnd = System.currentTimeMillis();
    	long timeDiff = timeEnd - timeStart;
    	
    	// ���ʊm�F
    	// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    	if (timeDiff < (timeout-50)) {
    		fail();
    	}
    	assertNull(objResult);
    }

    /**
     * poll(long timeout, TimeUnit unit) �̃e�X�g
     * ����n�F�L���[�C���O�̏I���ʒm��ɃL���[����̏ꍇ��null��Ԃ��m�F
     */
    @Test
    public void testPoll003() throws Exception{
    	int capacity = 1;

    	long timeout = 20000;
    	final long sleeptime = 1000;
    	TimeUnit unit = TimeUnit.MILLISECONDS;
    	final CountDownLatch threadSync = new CountDownLatch(1);

    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	Thread thread01 = new Thread() {
    		@Override
    		public void run() {
    			try {
    				threadSync.await();
					sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			queue.finishQueueing();
    		}
    	};
    	
    	// �L���[����ł��邱�Ƃ̊m�F
    	assertEquals(0, queue.size());

    	thread01.start();
    	
    	// poll�̎��s�Ə������Ԍv��
    	long timeStart = System.currentTimeMillis();
    	threadSync.countDown();
    	DataValueObject objResult = queue.poll(timeout, unit);
    	long timeEnd = System.currentTimeMillis();
    	long timeDiff = timeEnd - timeStart;
    	
    	// ���ʊm�F
    	// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    	if (timeDiff < (sleeptime-50)) {
    		fail();
    	}
    	assertNull(objResult);
    }

    /**
     * poll(long timeout, TimeUnit unit) �̃e�X�g
     * ����n�F�L���[�̐擪���擾���폜���邱�Ƃ̊m�F
     */
    @Test
    public void testPoll004() throws Exception{
        int capacity = 3;

    	long timeout = 100;
    	TimeUnit unit = TimeUnit.MILLISECONDS;

    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	DataValueObject obj1 = new DataValueObject("hoge");
    	DataValueObject obj2 = new DataValueObject("fuga");
    	DataValueObject obj3 = new DataValueObject("piyo");
    	
    	// �L���[�ɗv�f���l�߂�iadd�g�p�j
    	queue.add(obj1);
    	queue.add(obj2);
    	queue.add(obj3);
    	int sizeBefore = queue.size();
    	
    	// poll�ŗv�f��1�擾
    	DataValueObject objPoll = queue.poll(timeout, unit);
    	int sizeAfter = queue.size();
    	
    	// �擾�����v�f��add�ōŏ��ɋl�߂��v�f�i�擪�̗v�f�j�ł��邱�Ƃ��m�F
    	assertEquals(obj1, objPoll);
    	
    	// �v�f����1�����Ă��邱�Ƃ��m�F
    	assertEquals(1, sizeBefore - sizeAfter);
    }

    /**
     * poll() �̃e�X�g
     * ����n�Fpoll()�ɂ��notFull�V�O�i�������M����邱�Ƃ̊m�F
     */
    @Test
    public void testPoll005() throws Exception {
        int capacity = 3;
        final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
        final CountDownLatch threadSync = new CountDownLatch(1);
        int count = 0;

        // �ʃX���b�h��put�����s
        // put�Ԋu��poll�Ԋu���Z�����邱�ƂŁA�L���[���ő�܂Ŗ��܂�Aput�҂���Ԃ����B
        ErrorFeedBackThread thread01 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                threadSync.countDown();
                for (int idx=0; idx < 10; idx++) {
                    Thread.sleep(500);
                    queue.put(new DataValueObject(idx));
                }
            }
        };

        thread01.start();
        threadSync.await();

        // poll�����s
        // poll�Ԋu��put�Ԋu��蒷�����邱�ƂŁA�L���[���ő�܂Ŗ��܂�Aput�҂���Ԃ����B
        for (int idx=0; idx < 10; idx++) {
            Thread.sleep(1000);
            DataValueObject obj = queue.poll();
            if (obj != null) {
                count++;
            }
        }

        // poll��10��l�����Ă��邱�Ƃ��m�F
        // notFull�V�O�i�������M����Ă��Ȃ���΁Aput���f�b�g���b�N�ƂȂ�l��10��擾�ł��Ȃ��B
        assertEquals(10, count);
    }

    /**
     * poll() �̃e�X�g
     * ����n�Fpoll()�ŃL���[����l�����ԂɎ擾�ł��邱�Ƃ̊m�F
     */
    @Test
    public void testPoll006() throws Exception {
        int capacity = 3;
        final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);

        DataValueObject obj1 = new DataValueObject("Value1");
        DataValueObject obj2 = new DataValueObject("Value2");
        DataValueObject obj3 = new DataValueObject("Value3");

        // �L���[��3���}��
        queue.put(obj1);
        queue.put(obj2);
        queue.put(obj3);

        // poll()���{
        int queueSize0 = queue.size();
        DataValueObject pollObject1 = queue.poll();
        int queueSize1 = queue.size();
        DataValueObject pollObject2 = queue.poll();
        int queueSize2 = queue.size();
        DataValueObject pollObject3 = queue.poll();
        int queueSize3 = queue.size();
        DataValueObject pollObject4 = queue.poll();
        int queueSize4 = queue.size();

        // ���ʊm�F
        assertEquals(3, queueSize0);
        assertEquals(2, queueSize1);
        assertEquals(1, queueSize2);
        assertEquals(0, queueSize3);
        assertEquals(0, queueSize4);
        assertEquals(obj1, pollObject1);
        assertEquals(obj2, pollObject2);
        assertEquals(obj3, pollObject3);
        assertNull(pollObject4);
    }

    /**
     * offer(E o, long timeout, TimeUnit unit) �̃e�X�g
     * �ُ�n�FNullPointerException�̊m�F
     */
    @Test
    public void testOffer001() {
    	int capacity = 3;
    	
    	long timeout = 15;
    	TimeUnit unit = TimeUnit.SECONDS;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	try {
    		queue.offer(null, timeout, unit);
    		fail();
    	} catch (NullPointerException e) {
    		/* NOP */ 
    	} catch (Exception ex) {
    		fail();
    	}
    }

    /**
     * offer(E o, long timeout, TimeUnit unit) �̃e�X�g
     * �ُ�n�FInterruptedException�̊m�F
     */
    @Test
    public void testOffer002() throws Exception {
    	int capacity = 1;
    	
    	final long timeout = 20000;
    	final TimeUnit unit = TimeUnit.MILLISECONDS;
    	DataValueObject obj1 = new DataValueObject("hoge1");
    	boolean result1 = false;
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �L���[�������ς��ɂ���
    	result1 = queue.offer(obj1, timeout, unit);
    	assertTrue(result1);
    	
    	// �ʃX���b�h��offer�����s�i�L���[�������ς��Ȃ̂ő҂��ɂȂ�j
        ErrorFeedBackThread thread01 = new ErrorFeedBackThread() {
        	@Override
        	public void doRun() throws Exception {
    			DataValueObject obj2 = new DataValueObject("hoge2");
        		
    			// �҂���Ԃ�����
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			try {
    				queue.offer(obj2, timeout, unit);
    				fail();
    			} catch (InterruptedException e) {
    				// ���Ғʂ�
    			}
    			long timeEnd = System.currentTimeMillis();
    			long timeDiff = timeEnd - timeStart;
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if(timeDiff < 950) {
    				fail();
    			}
        	}
        };
        
    	thread01.start();

    	threadSync.await();
    	Thread.sleep(1000);
    	
    	// ���荞��
    	thread01.interrupt();
    	
    	thread01.throwErrorOrExceptionIfThrown();
    }


    /**
     * offer(E o, long timeout, TimeUnit unit) �̃e�X�g
     * ����n�F �w�肳�ꂽ�v�f�����̃L���[�̖����ɑ}������i���Ԓʂ�ɂȂ��Ă��邱�Ɓj + ����n�̍ۂ� return true
     */
    @Test
    public void testOffer003() throws Exception {
    	int capacity = 3;
    	
    	long timeout = 15;
    	TimeUnit unit = TimeUnit.SECONDS;
    	DataValueObject obj1 = new DataValueObject("hoge1");
    	DataValueObject obj2 = new DataValueObject("hoge2");
    	boolean result1;
    	boolean result2;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
		result1 = queue.offer(obj1, timeout, unit);
		result2 = queue.offer(obj2, timeout, unit);
		
		assertTrue(result1);
		assertEquals(obj1.getValue(), queue.poll(timeout, unit).getValue());
		assertTrue(result2);
		assertEquals(obj2.getValue(), queue.poll(timeout, unit).getValue());
    }

    /**
     * offer(E o, long timeout, TimeUnit unit) �̃e�X�g
     * ����n�F ���b�N�����܂܃^�C���A�E�g�����ꍇ
     */
    @Test
    public void testOffer004() throws Exception{
    	int capacity = 1;
    	
    	long timeout = 1000;
    	TimeUnit unit = TimeUnit.MILLISECONDS;
    	DataValueObject obj1 = new DataValueObject("hoge1");
    	DataValueObject obj2 = new DataValueObject("hoge2");
    	boolean result2;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
		// �L���[�������ς��ɂ���
		queue.offer(obj1, timeout, unit);
		
		// �L���[�ɓ���悤�Ƃ���i�^�C���A�E�g�ɂȂ�܂ő҂��Afalse��Ԃ��j
		long timeStart = System.currentTimeMillis();
		result2 = queue.offer(obj2, timeout, unit);
		long timeEnd = System.currentTimeMillis();
		long timeDiff = timeEnd - timeStart;
		
		// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
		if (timeDiff < (timeout-50)) {
			fail();
		}
		assertFalse(result2);
		assertEquals(obj1.getValue(), queue.poll(timeout, unit).getValue());
		assertNull(queue.poll(timeout, unit));
    }
    
    /**
     * offer(E o, long timeout, TimeUnit unit) �̃e�X�g
     * ����n�F signal �ʒm�̊m�F
     */
    @Test
    public void testOffer005() throws Exception{
    	int capacity = 1;
    	
    	long timeout = 50;
    	TimeUnit unit = TimeUnit.MILLISECONDS;
    	DataValueObject obj1 = new DataValueObject("hoge");
    	
    	final AtomicBoolean checkflg = new AtomicBoolean();
    	
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �X���b�h��peek�����s������i�ŏ��̓L���[����Ȃ̂ő҂j
    	Thread thread01 = new Thread() {
    		public void run() {
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			queue.peek();
    			long timeEnd = System.currentTimeMillis();
    			long timeDiff = timeEnd - timeStart;
    			
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if (timeDiff < 950) {
    				fail();
    			}
    			checkflg.set(true);
    		}
    	};
    	thread01.start();
    	
    	threadSync.await();
    	Thread.sleep(1000);
    	// �t���O���m�F����ioffer���O��peek�������Ă��Ȃ����Ɓj
    	assertFalse(checkflg.get());
    	// �L���[�ɗv�f���l�߂�i������ɂ��V�O�i�����o���peek�������͂��j
    	queue.offer(obj1, timeout, unit);

    	// �X���b�h���������ԕ������҂�
    	thread01.join();

    	// �t���O���m�F����
    	assertTrue(checkflg.get());
    }

    
    /**
     * offer(E o) �̃e�X�g
     * �ُ�n�F NullPointerException �̊m�F
     */
    @Test
    public void testOffer006() throws Exception{
        int capacity = 1;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	try {
    		queue.offer(null);
    		fail();
    	} catch (NullPointerException e) {
    		/* NOP */
    	} catch (Exception e) {
    		fail();
    	}
		
    }
    
    /**
     * offer(E o) �̃e�X�g
     * ����n�F�L���[�̖����ɗv�f��}�����邱�Ƃ̊m�F
     */
    @Test
    public void testOffer007() throws Exception{
    	int capacity = 3;
    	long timeout = 50;
    	TimeUnit unit = TimeUnit.MILLISECONDS;
    	
    	DataValueObject obj1 = new DataValueObject("hoge");
    	DataValueObject obj2 = new DataValueObject("fuga");
    	DataValueObject obj3 = new DataValueObject("piyo");
    	boolean result1 = false;
    	boolean result2 = false;
    	boolean result3 = false;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	result1 = queue.offer(obj1);
    	result2 = queue.offer(obj2);
    	result3 = queue.offer(obj3);
    	
    	assertTrue(result1);
    	assertTrue(result2);
    	assertTrue(result3);
    	assertEquals(obj1.getValue(), queue.poll(timeout, unit).getValue());
    	assertEquals(obj2.getValue(), queue.poll(timeout, unit).getValue());
    	assertEquals(obj3.getValue(), queue.poll(timeout, unit).getValue());
    }

    /**
     * offer(E o) �̃e�X�g
     * ����n�F �v�f���ǉ��s�̏ꍇ��false��Ԃ��m�F
     */
    @Test
    public void testOffer008() throws Exception{
    	int capacity = 1;
    	DataValueObject obj1 = new DataValueObject("hoge");
    	DataValueObject obj2 = new DataValueObject("fuga");
    	boolean result2 = false;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	queue.offer(obj1);
    	result2 = queue.offer(obj2);
    	
    	assertFalse(result2);
    }

    /**
     * offer(E o) �̃e�X�g
     * ����n�F signal �̊m�F
     */
    @Test
    public void testOffer009() throws Exception{
    	int capacity = 1;
    	
    	DataValueObject obj1 = new DataValueObject("hoge");
    	
    	final AtomicBoolean checkflg = new AtomicBoolean();
    	
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �X���b�h��peek�����s������i�ŏ��̓L���[����Ȃ̂ő҂j
    	Thread thread01 = new Thread() {
    		public void run() {
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			queue.peek();
    			long timeEnd = System.currentTimeMillis();
    			long timeDiff = timeEnd - timeStart;
    			
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if (timeDiff < 950) {
    				fail();
    			}
    			checkflg.set(true);
    		}
    	};
    	thread01.start();
    	
    	threadSync.await();
    	Thread.sleep(1000);
    	// �t���O���m�F����ioffer���O��peek�������Ă��Ȃ����Ɓj
    	assertFalse(checkflg.get());
    	// �L���[�ɗv�f���l�߂�i������ɂ��V�O�i�����o���peek�������͂��j
    	queue.offer(obj1);

    	// �X���b�h���������ԕ������҂�
    	thread01.join();

    	// �t���O���m�F����
    	assertTrue(checkflg.get());
    }

    /**
     * put(E o) �̃e�X�g
     * �ُ�n�F NullPointerException �̊m�F
     */
    @Test
    public void testPut001() throws Exception{
    	int capacity = 1;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	try {
    		queue.put(null);
    		fail();
    	} catch (NullPointerException e) {
    		/* NOP */
    	} catch (Exception e) {
    		fail();
    	}
    }

    /**
     * put(E o) �̃e�X�g
     * �ُ�n�F InterruptedException �̊m�F
     */
    @Test
    public void testPut002() throws Exception{
    	int capacity = 1;
    	
    	DataValueObject obj1 = new DataValueObject("hoge1");
    	
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �L���[�������ς��ɂ���
    	queue.put(obj1);
    		    		
    	// �ʃX���b�h��put�����s�i�L���[�������ς��Ȃ̂ő҂��ɂȂ�j
        ErrorFeedBackThread thread01 = new ErrorFeedBackThread() {
        	@Override
        	public void doRun() throws Exception {
    			DataValueObject obj2 = new DataValueObject("hoge2");
        		
    			// �҂���Ԃ�����
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			try {
    				queue.put(obj2);
    				fail();
    			} catch (InterruptedException e) {
    				// ���Ғʂ�
    			}
    			long timeEnd = System.currentTimeMillis();
    			long timeDiff = timeEnd - timeStart;
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if(timeDiff < 950) {
    				fail();
    			}
           	}
        };
        
    	thread01.start();

    	threadSync.await();
    	Thread.sleep(1000);
    	
    	// ���荞��
    	thread01.interrupt();
    	
    	thread01.throwErrorOrExceptionIfThrown();
    }

    /**
     * put(E o) �̃e�X�g
     * ����n�F �w�肳�ꂽ�v�f�����̃L���[�̖����ɒǉ����邱�Ƃ̊m�F
     */
    @Test
    public void testPut003() throws Exception{
    	int capacity = 3;
    	long timeout = 50;
    	TimeUnit unit = TimeUnit.MILLISECONDS;
    	
    	DataValueObject obj1 = new DataValueObject("hoge");
    	DataValueObject obj2 = new DataValueObject("fuga");
    	DataValueObject obj3 = new DataValueObject("piyo");
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	queue.put(obj1);
    	queue.put(obj2);
    	queue.put(obj3);
    	
    	assertEquals(obj1.getValue(), queue.poll(timeout, unit).getValue());
    	assertEquals(obj2.getValue(), queue.poll(timeout, unit).getValue());
    	assertEquals(obj3.getValue(), queue.poll(timeout, unit).getValue());

    }

    /**
     * put(E o)�̃e�X�g
     * ����n�F��Ԃ����p�\�ɂȂ�܂őҋ@���Ă���v�f��ǉ����邱�Ƃ̊m�F
     */
    @Test
    public void testPut004() throws Exception {
    	int capacity = 1;
    	long timeout = 50;
    	TimeUnit unit = TimeUnit.MILLISECONDS;
    	
    	final AtomicBoolean checkflg = new AtomicBoolean();
    	
    	DataValueObject obj1 = new DataValueObject("hoge");
    	final DataValueObject obj2 = new DataValueObject("fuga");
    	
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	queue.put(obj1);
    	
    	Thread thread01 = new Thread() {
    		public void run() {
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			try {
    				queue.put(obj2);	// �L���[���󂭂܂ő҂��Ă�����s����
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			long timeEnd = System.currentTimeMillis();
    			long timeDiff = timeEnd - timeStart;
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if(timeDiff < 950) {
    				fail();
    			}
    			checkflg.set(true);
    		}
    	};
    	thread01.start();
    	threadSync.await();
    	Thread.sleep(1000);
    	// �t���O���m�F����ipoll���O��put�������Ă��Ȃ����Ɓj
    	assertFalse(checkflg.get());
    	queue.poll(timeout, unit);

    	thread01.join();
    	
    	assertTrue(checkflg.get());
   		assertEquals(obj2.getValue(), queue.poll(timeout, unit).getValue());
    	
    }
    
    /**
     * put(E o) �̃e�X�g
     * ����n�F signal�̊m�F
     */
    @Test
    public void testPut005() throws Exception{
    	int capacity = 1;
    	
    	DataValueObject obj1 = new DataValueObject("hoge");
    	
    	final AtomicBoolean checkflg = new AtomicBoolean();
    	
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �X���b�h��peek�����s������i�ŏ��̓L���[����Ȃ̂ő҂j
    	Thread thread01 = new Thread() {
    		public void run() {
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			queue.peek();
    			long timeEnd = System.currentTimeMillis();
    			long timeDiff = timeEnd - timeStart;
    			
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if (timeDiff < 950) {
    				fail();
    			}
    			checkflg.set(true);
    		}
    	};
    	thread01.start();
    	
    	// �L���[�ɗv�f���l�߂�i������ɂ��V�O�i�����o���peek�������͂��j
    	threadSync.await();
    	Thread.sleep(1000);
    	// �t���O���m�F����iput���O��peek�������Ă��Ȃ����Ɓj
    	assertFalse(checkflg.get());
    	queue.put(obj1);

    	// �X���b�h���������ԕ������҂�
    	thread01.join();

    	// �t���O���m�F����
    	assertTrue(checkflg.get());
    }

    /**
     * peek() �̃e�X�g
     * ����n�F�L���[�̐擪���擾���邪�폜���Ȃ��m�F
     */
    @Test
    public void testPeek001() throws Exception{
    	int capacity = 3;
    	long timeout = 50;
    	TimeUnit unit = TimeUnit.MILLISECONDS;
    	
    	DataValueObject obj1 = new DataValueObject("hoge");
    	DataValueObject obj2 = new DataValueObject("fuga");
    	DataValueObject obj3 = new DataValueObject("piyo");
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	queue.put(obj1);
    	queue.put(obj2);
    	queue.put(obj3);

    	assertEquals(obj1.getValue(), queue.peek().getValue());
    	
    	assertEquals(obj1.getValue(), queue.poll(timeout, unit).getValue());
    	assertEquals(obj2.getValue(), queue.poll(timeout, unit).getValue());
    	assertEquals(obj3.getValue(), queue.poll(timeout, unit).getValue());
    }

    /**
     * peek() �̃e�X�g
     * ����n�F�L���[����̏ꍇ�A�L���[�ɗv�f������̂�҂m�F
     */
    @Test
    public void testPeek002() throws Exception{
    	int capacity = 1;
    	final long waittime = 1000;
    	
    	final DataValueObject obj1 = new DataValueObject("hoge");
    	
    	final CountDownLatch threadSync = new CountDownLatch(1);
		
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	Thread thread01 = new Thread() {
    		@Override
    		public void run() {
    			try {
    				threadSync.await();
					sleep(waittime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			queue.add(obj1);
    		}
    	};
    	
    	// �L���[����ł��邱�Ƃ̊m�F
    	assertEquals(0, queue.size());

    	thread01.start();
    	
    	long timeStart = System.currentTimeMillis(); 
    	threadSync.countDown();
    	// �ʃX���b�h�ŃL���[�֗v�f�ǉ������Ȃ���peek()�͂��܂ł��҂�������
    	DataValueObject objPeek = queue.peek();
    	long timeEnd = System.currentTimeMillis();
    	long timeDiff = timeEnd - timeStart;
    	
    	// �L���[�ɗv�f������̂�҂������Ƃ��m�F
    	// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    	if(timeDiff < (waittime-50)) {
    		fail();
    	}
    	// �L���[�̗v�f���ǉ�����Ă��邱�Ƃ̊m�F
    	assertEquals(obj1.getValue(), objPeek.getValue());
    	
    }
    
    /**
     * peek() �̃e�X�g
     * ����n�F�L���[����̏ꍇ�A�L���[�C���O�̏I���ʒm��҂m�F
     */
    @Test
    public void testPeek003() throws Exception{
    	int capacity = 1;
    	final long waittime = 1000;
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	Thread thread01 = new Thread() {
    		@Override
    		public void run() {
    			try {
					threadSync.await();
					sleep(waittime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			queue.finishQueueing();
    		}
    	};
    	
    	// �L���[����ł��邱�Ƃ̊m�F
    	assertEquals(0, queue.size());

    	thread01.start();
    	
    	// �ʃX���b�h�ŃL���[�C���O�I���ʒm�����Ȃ���peek()�͂��܂ł��҂�������
    	// �L���[�̗v�f���ǉ�����Ă��Ȃ��ꍇnull���Ԃ�
    	long timeStart = System.currentTimeMillis();
    	threadSync.countDown();
    	DataValueObject objPeek = queue.peek();
    	long timeEnd = System.currentTimeMillis();
    	long timeDiff = timeEnd - timeStart;
    	
    	// �L���[�C���O�I���܂ő҂������Ƃ��m�F
    	// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    	if(timeDiff < (waittime-50)) {
    		fail();
    	}
    	// Peek�̌��ʂ�NULL�ł��邱�Ƃ��m�F
    	assertNull(objPeek);
    }
    
    /**
     * peek() �̃e�X�g
     * �ُ�n�F�L���[����őҋ@���Ă��鎞��InterruptedExeception�����������ꍇ��null��Ԃ��m�F
     */
    @Test
    public void testPeek004() throws Exception {
    	int capacity = 1;
    	final long sleeptime = 1000;
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);

    	// �ʃX���b�h��peek�����s�i�L���[����Ȃ̂ő҂��ɂȂ�j
        ErrorFeedBackThread thread01 = new ErrorFeedBackThread() {
          	@Override
           	public void doRun() throws Exception {
          		// �҂���Ԃ�����
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			DataValueObject result = queue.peek();
    			long timeEnd = System.currentTimeMillis();
    			// result�̊m�F
    			assertNull(result);
    			// �������Ԃ̊m�F�iinterrupt�ɂ���ďI���������j
    			long timeDiff = timeEnd - timeStart;
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if(timeDiff < (sleeptime-50)) {
    				fail();
    			}
            }
        };
        
    	thread01.start();

    	// �����҂�
    	threadSync.await();
    	Thread.sleep(sleeptime);
    	
    	// ���荞��
    	thread01.interrupt();
    	
    	thread01.throwErrorOrExceptionIfThrown();
    }

    /**
     * isEmpty() �̃e�X�g
     * ����n�F�L���[�̗v�f���Ȃ��ꍇ��true��Ԃ��m�F�i�ҋ@�������ʗv�f���Ȃ��j
     */
    @Test
    public void testIsEmpty001() throws Exception {
    	int capacity = 1;
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	Thread thread01 = new Thread() {
    		@Override
    		public void run() {
    			try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			queue.finishQueueing();
    		}
    	};
    	
    	thread01.start();
    	
    	// �ʃX���b�h�ŃL���[�C���O�I���ʒm���s����true��Ԃ�
    	assertTrue(queue.isEmpty());
    }
    
    /**
     * isEmpty() �̃e�X�g
     * ����n�F�L���[�̗v�f������ꍇ��false��Ԃ��m�F�i�ҋ@���ɗv�f���ǉ������j
     */
    @Test
    public void testIsEmpty002() throws Exception {
    	int capacity = 1;
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	Thread thread01 = new Thread() {
    		@Override
    		public void run() {
    			try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			DataValueObject obj1 = new DataValueObject("hoge");
    			queue.add(obj1);
    		}
    	};
    	
    	thread01.start();
    	
    	// �ʃX���b�h�ŃL���[�C���O�I���ʒm���s����true��Ԃ�
    	assertFalse(queue.isEmpty());
    	
    }

    /**
     * isEmpty() �̃e�X�g
     * ����n�F�L���[�̗v�f������ꍇ��false��Ԃ��m�F�i�ŏ�����v�f������j
     */
    @Test
    public void testIsEmpty003() throws Exception {
    	int capacity = 1;
		DataValueObject obj1 = new DataValueObject("hoge");
		    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	queue.add(obj1);
    	
    	assertFalse(queue.isEmpty());
    	
    }

    /**
     * isEmpty() �̃e�X�g
     * ����n�F�L���[�̗v�f���Ȃ��ꍇ��true��Ԃ��m�F�i�ҋ@���Ȃ��j
     */
    @Test
    public void testIsEmpty004() throws Exception {
    	int capacity = 1;
    	
    	ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	queue.finishQueueing();
    	
    	assertTrue(queue.isEmpty());
    }

    /**
     * isEmpty() �̃e�X�g
     * �ُ�n�F�L���[����őҋ@���Ă��鎞��InterruptedExeception�����������ꍇ��true��Ԃ��m�F
     */
    @Test
    public void testIsEmpty005() throws Exception {
    	int capacity = 1;
    	final long sleeptime = 1000;
    	final CountDownLatch threadSync = new CountDownLatch(1);
    	
    	final ArrayBlockingQueueEx<DataValueObject> queue = new ArrayBlockingQueueEx<DataValueObject>(capacity);
    	
    	// �ʃX���b�h��isEmpty�����s�i�L���[����Ȃ̂ő҂��ɂȂ�j
        ErrorFeedBackThread thread01 = new ErrorFeedBackThread() {
           	@Override
           	public void doRun() throws Exception {
    			// �҂���Ԃ�����
    			long timeStart = System.currentTimeMillis();
    			threadSync.countDown();
    			boolean result = queue.isEmpty();
    			long timeEnd = System.currentTimeMillis();
    			// result�̊m�F
    			assertTrue(result);
    			// �������Ԃ̊m�F�iinterrupt�ɂ���ďI���������j
    			long timeDiff = timeEnd - timeStart;
    			// ��1000�~���b�̑҂��m�F(50�~���b�̌덷�����e)
    			if(timeDiff < (sleeptime-50)) {
    				fail();
    			}
            }
        };
        
    	thread01.start();

   		// �����҂�
    	threadSync.await();
   		Thread.sleep(sleeptime);
   		
   		// ���荞��
   		thread01.interrupt();
   		
   		thread01.throwErrorOrExceptionIfThrown();
    }

    /**
     * �G���[���t�B�[�h�o�b�N�ł���X���b�h�B
     * �ʃX���b�h�Ŏ��{���������e�� doRun() throws Exception �Ɏ�������B
     * �����I�����AthrowErrorOrExceptionIfThrown���\�b�h�����s����ƁA
     * doRun���\�b�h�ɂđz��O�̃G���[�����������ꍇ�ɁA���̃G���[���X���[�����B
     */
    abstract class ErrorFeedBackThread extends Thread {
        private Exception exception;
        private Error error;
        @Override
        public void run() {
            try {
                doRun();
            } catch (Exception e) {
                exception = e;
            } catch (Error e) {
                error = e;
            }
        }
        
        public void throwErrorOrExceptionIfThrown() throws Exception {
            join();
            if (error != null) {
                throw error;
            } else if (exception != null) {
                throw exception;
            }
        }
        
        abstract void doRun() throws Exception;
    }
}

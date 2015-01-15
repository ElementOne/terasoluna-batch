package jp.terasoluna.fw.batch.executor;

import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import jp.terasoluna.fw.batch.annotation.util.MockApplicationContext;
import jp.terasoluna.fw.batch.executor.AsyncBatchExecutor.BatchServantTaskEndTracker;
import jp.terasoluna.fw.batch.executor.SecurityManagerEx.ExitException;
import jp.terasoluna.fw.batch.executor.concurrent.BatchServant;
import jp.terasoluna.fw.batch.executor.concurrent.BatchThreadPoolTaskExecutor;
import jp.terasoluna.fw.batch.executor.dao.SystemDao;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListResult;
import jp.terasoluna.fw.ex.unit.util.SystemEnvUtils;
import jp.terasoluna.fw.ex.unit.util.TerasolunaPropertyUtils;
import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

public class AsyncBatchExecutorTest extends TestCase {

    final SecurityManager sm = System.getSecurityManager();

    public AsyncBatchExecutorTest() {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TerasolunaPropertyUtils.saveProperties();
        System.setSecurityManager(new SecurityManagerEx());
    }

    @Override
    protected void tearDown() throws Exception {
        TerasolunaPropertyUtils.restoreProperties();
        SystemEnvUtils.restoreEnv();
        System.setSecurityManager(sm);
        super.tearDown();
    }

    /**
     * main�e�X�g01 �y�ُ�n�z
     * 
     * <pre>
     * ���O����
     * �Eadmin.dataSource��bean��`�t�@�C������擾����queryDao��null
     * �E�N��������foo
     * �m�F����
     * �EIAL025007�̃��O���o�͂���邱��
     * �EIAL025006�̃��O��foo���o�͂���邱��
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain01() throws Exception {
        TerasolunaPropertyUtils.addProperty("beanDefinition.admin.dataSource",
                "AsyncBatchExecutorTest01.xml");
        // TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
        // "src/test/resources/test_terminate");

        try {
            AsyncBatchExecutor.main(new String[] { "foo" });
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g02 �y�ُ�n�z
     * 
     * <pre>
     * ���O����
     * �Eadmin.dataSource��bean��`�t�@�C������擾����updateDao��null
     * �E�N��������foo
     * �m�F����
     * �EIAL025008�̃��O���o�͂���邱��
     * �EIAL025006�̃��O��foo���o�͂���邱��
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain02() throws Exception {
        TerasolunaPropertyUtils.addProperty("beanDefinition.admin.dataSource",
                "AsyncBatchExecutorTest02.xml");
        // TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
        // "src/test/resources/test_terminate");

        try {
            AsyncBatchExecutor.main(new String[] { "foo" });
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g03 �y�ُ�n�z
     * 
     * <pre>
     * ���O����
     * �Eadmin.dataSource��bean��`�t�@�C������擾����transactionManager��null
     * �E�N��������foo
     * �m�F����
     * �EIAL025016�̃��O���o�͂���邱��
     * �EIAL025006�̃��O��foo���o�͂���邱��
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain03() throws Exception {
        TerasolunaPropertyUtils.addProperty("beanDefinition.admin.dataSource",
                "AsyncBatchExecutorTest03.xml");
        // TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
        // "src/test/resources/test_terminate");

        try {
            AsyncBatchExecutor.main(new String[] { "foo" });
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g04 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�N����������
     * �E���ϐ�JOB_APP_CD�����ݒ�
     * �m�F����
     * �EIAL025006�̃��O��joAppCd���o�͂���Ȃ�����
     * </pre>
     */
    public void testMain04() throws Exception {
        TerasolunaPropertyUtils.addProperty("beanDefinition.admin.dataSource",
                "AsyncBatchExecutorTest01.xml");
        // TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
        // "src/test/resources/test_terminate");
        SystemEnvUtils.removeEnv(AsyncBatchExecutor.ENV_JOB_APP_CD);
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g05 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�N����������
     * �E���ϐ�JOB_APP_CD��hoge���ݒ�
     * �m�F����
     * �EIAL025006�̃��O��hoge���o�͂���邱��
     * </pre>
     */
    public void testMain05() throws Exception {
        TerasolunaPropertyUtils.addProperty("beanDefinition.admin.dataSource",
                "AsyncBatchExecutorTest01.xml");
        // TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
        // "src/test/resources/test_terminate");
        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g06 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�f�[�^�x�[�X�ُ펞�̃��g���C�񐔁x��&quot;abc&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain06() throws Exception {
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.dbAbnormalRetryMax");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.dbAbnormalRetryMax", "abc");

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g07 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�f�[�^�x�[�X�ُ펞�̃��g���C�Ԋu�i�~���b�j�x��&quot;abc&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain07() throws Exception {
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.dbAbnormalRetryInterval");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.dbAbnormalRetryInterval", "abc");

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g08 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�f�[�^�x�[�X�ُ펞�̃��g���C�񐔂����Z�b�g����O�񂩂�̔����Ԋu�i�~���b�j�x��&quot;abc&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain08() throws Exception {
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.dbAbnormalRetryReset");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.dbAbnormalRetryReset", "abc");

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g09 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�W���u���s���g���C�Ԋu�i�~���b�j�x��&quot;abc&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain09() throws Exception {
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.executeRetryInterval");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.executeRetryInterval", "abc");

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g10 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�W���u���s���g���C�񐔁x��&quot;abc&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain10() throws Exception {
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.executeRetryCountMax");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.executeRetryCountMax", "abc");

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g11 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�󂫃X���b�h�c��臒l�̃f�t�H���g�l�x��&quot;abc&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain11() throws Exception {
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.availableThreadThresholdCount");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.availableThreadThresholdCount", "abc");

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g12 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�󂫃X���b�h�c��臒l�ȉ��̏ꍇ�̃E�F�C�g���ԁi�~���b�j�̃f�t�H���g�l�x��&quot;abc&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��255
     * </pre>
     */
    public void testMain12() throws Exception {
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.availableThreadThresholdWait");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.availableThreadThresholdWait", "abc");

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(255, e.state);
        }
    }

    /**
     * main�e�X�g13 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�W���u���s���g���C�Ԋu�i�~���b�j�x��&quot;10&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��0
     * </pre>
     */
    public void testMain13() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String endFilePath = srcUrl.getPath();

        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.executeRetryInterval");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.executeRetryInterval", "10");
        TerasolunaPropertyUtils.removeProperty("executor.endMonitoringFile");
        TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
                endFilePath);

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(0, e.state);
        }
    }

    /**
     * main�e�X�g14 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�W���u���s���g���C�񐔁x��&quot;10&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��0
     * </pre>
     */
    public void testMain14() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String endFilePath = srcUrl.getPath();

        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.executeRetryCountMax");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.executeRetryCountMax", "10");
        TerasolunaPropertyUtils.removeProperty("executor.endMonitoringFile");
        TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
                endFilePath);

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(0, e.state);
        }
    }

    /**
     * main�e�X�g15 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�󂫃X���b�h�c��臒l�̃f�t�H���g�l�x��&quot;10&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��0
     * </pre>
     */
    public void testMain15() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String endFilePath = srcUrl.getPath();

        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.availableThreadThresholdCount");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.availableThreadThresholdCount", "10");
        TerasolunaPropertyUtils.removeProperty("executor.endMonitoringFile");
        TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
                endFilePath);

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(0, e.state);
        }
    }

    /**
     * main�e�X�g16 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�󂫃X���b�h�c��臒l�ȉ��̏ꍇ�̃E�F�C�g���ԁi�~���b�j�̃f�t�H���g�l�x��&quot;10&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��0
     * </pre>
     */
    public void testMain16() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String endFilePath = srcUrl.getPath();

        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.availableThreadThresholdWait");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.availableThreadThresholdWait", "10");
        TerasolunaPropertyUtils.removeProperty("executor.endMonitoringFile");
        TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
                endFilePath);

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(0, e.state);
        }
    }

    /**
     * main�e�X�g17 �y����n�z
     * 
     * <pre>
     * ���O����
     * �E�w�󂫃X���b�h�c��臒l�ȉ��̏ꍇ�̃E�F�C�g���ԁi�~���b�j�̃f�t�H���g�l�x��&quot;10&quot;���ݒ�
     * �m�F����
     * �E���^�[���R�[�h��0
     * </pre>
     */
    public void testMain17() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String endFilePath = srcUrl.getPath();

        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.availableThreadThresholdWait");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.availableThreadThresholdWait", "10");
        TerasolunaPropertyUtils.removeProperty("executor.endMonitoringFile");
        TerasolunaPropertyUtils.addProperty("executor.endMonitoringFile",
                endFilePath);

        SystemEnvUtils.setEnv(AsyncBatchExecutor.ENV_JOB_APP_CD, "hoge");
        try {
            AsyncBatchExecutor.main(new String[] {});
            fail("��O�͔������܂���");
        } catch (ExitException e) {
            assertEquals(0, e.state);
        }
    }

    /**
     * testCloseRootApplicationContext001
     * @throws Exception
     */
    public void testCloseRootApplicationContext001() throws Exception {
        ApplicationContext context = null;
        AsyncBatchExecutor.closeRootApplicationContext(context);
    }

    /**
     * testCloseRootApplicationContext002
     * @throws Exception
     */
    public void testCloseRootApplicationContext002() throws Exception {
        ApplicationContext context = new AbstractApplicationContext() {

            @Override
            protected void closeBeanFactory() {
            }

            @Override
            public ConfigurableListableBeanFactory getBeanFactory()
                                                                   throws IllegalStateException {
                return null;
            }

            @Override
            protected void refreshBeanFactory() throws BeansException,
                                               IllegalStateException {
            }
        };

        AsyncBatchExecutor.closeRootApplicationContext(context);
    }

    /**
     * testLogOutputTaskExecutor001
     * @throws Exception
     */
    public void testLogOutputTaskExecutor001() throws Exception {
        Log log = new Log() {
            public void debug(Object message) {
            }

            public void debug(Object message, Throwable t) {
            }

            public void error(Object message) {
            }

            public void error(Object message, Throwable t) {
            }

            public void fatal(Object message) {
            }

            public void fatal(Object message, Throwable t) {
            }

            public void info(Object message) {
            }

            public void info(Object message, Throwable t) {
            }

            public boolean isDebugEnabled() {
                return false;
            }

            public boolean isErrorEnabled() {
                return false;
            }

            public boolean isFatalEnabled() {
                return false;
            }

            public boolean isInfoEnabled() {
                return false;
            }

            public boolean isTraceEnabled() {
                return false;
            }

            public boolean isWarnEnabled() {
                return false;
            }

            public void trace(Object message) {
            }

            public void trace(Object message, Throwable t) {
            }

            public void warn(Object message) {
            }

            public void warn(Object message, Throwable t) {
            }
        };

        ThreadPoolTaskExecutor taskExec = new BatchThreadPoolTaskExecutor();
        taskExec.setCorePoolSize(1);
        taskExec.setMaxPoolSize(1);
        taskExec.initialize();

        AsyncBatchExecutor.logOutputTaskExecutor(log, taskExec);
    }

    /**
     * testCheckEndFile001
     * @throws Exception
     */
    public void testCheckEndFile001() throws Exception {
        String endFilePath = null;
        boolean result = AsyncBatchExecutor.checkEndFile(endFilePath);
        assertFalse(result);
    }

    /**
     * testCheckEndFile002
     * @throws Exception
     */
    public void testCheckEndFile002() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");

        String endFilePath = srcUrl.getPath();
        boolean result = AsyncBatchExecutor.checkEndFile(endFilePath);
        assertTrue(result);
    }

    /**
     * testCheckTaskQueue001
     * @throws Exception
     */
    public void testCheckTaskQueue001() throws Exception {
        ThreadPoolTaskExecutor taskExec = new BatchThreadPoolTaskExecutor();
        taskExec.setCorePoolSize(1);
        taskExec.setMaxPoolSize(1);
        taskExec.initialize();

        boolean result = AsyncBatchExecutor.checkTaskQueue(taskExec);
        assertTrue(result);
    }

    /**
     * testCheckTaskQueue002
     * @throws Exception
     */
    public void testCheckTaskQueue002() throws Exception {
        ThreadPoolTaskExecutor taskExec = new ThreadPoolTaskExecutor();
        taskExec.setCorePoolSize(1);
        taskExec.setMaxPoolSize(1);
        taskExec.setQueueCapacity(1);
        taskExec.initialize();

        Runnable task = new Runnable() {
            public void run() {
                try {
                    System.out.println("aaa");
                    Thread.sleep(5000);
                    System.out.println("bbb");
                } catch (InterruptedException e) {
                    System.out.println("ccc");
                }
            }
        };
        taskExec.execute(task);

        Thread.sleep(1000);

        boolean result = AsyncBatchExecutor.checkTaskQueue(taskExec);
        assertTrue(result);

        taskExec.shutdown();
        Thread.sleep(500);
    }

    /**
     * testCheckTaskQueue003
     * @throws Exception
     */
    public void testCheckTaskQueue003() throws Exception {
        ThreadPoolTaskExecutor taskExec = new BatchThreadPoolTaskExecutor();
        taskExec.setCorePoolSize(1);
        taskExec.setMaxPoolSize(1);
        taskExec.initialize();

        Runnable task = new Runnable() {
            public void run() {
                try {
                    System.out.println("aaa");
                    Thread.sleep(5000);
                    System.out.println("bbb");
                } catch (InterruptedException e) {
                    System.out.println("ccc");
                }
            }
        };
        taskExec.execute(task);

        Thread.sleep(1000);

        boolean result = AsyncBatchExecutor.checkTaskQueue(taskExec);
        assertFalse(result);

        taskExec.shutdown();
        Thread.sleep(500);
    }

    /**
     * testExecuteJob001
     * @throws Exception
     */
    public void testExecuteJob001() throws Exception {
        // �p�����[�^
        AsyncBatchExecutor executor = null;
        ApplicationContext ctx = null;
        ThreadPoolTaskExecutor taskExecutor = null;
        String batchTaskServantName = null;
        BatchJobListResult batchJobListResult = null;

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����
        assertFalse(result);
    }

    /**
     * testExecuteJob002
     * @throws Exception
     */
    public void testExecuteJob002() throws Exception {
        // �p�����[�^
        AsyncBatchExecutor executor = new AsyncBatchExecutor() {
            @Override
            public SystemDao getSystemDao() {
                return null;
            }
        };
        ApplicationContext ctx = new MockApplicationContext();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        String batchTaskServantName = "batchTaskServant";
        BatchJobListResult batchJobListResult = new BatchJobListResult();

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����
        assertFalse(result);
    }

    /**
     * testExecuteJob004
     * @throws Exception
     */
    public void testExecuteJob004() throws Exception {
        // �p�����[�^
        AsyncBatchExecutor executor = new AsyncBatchExecutor() {
            @Override
            public PlatformTransactionManager getSysTransactionManager() {
                return null;
            }
        };
        ApplicationContext ctx = new MockApplicationContext();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        String batchTaskServantName = "batchTaskServant";
        BatchJobListResult batchJobListResult = new BatchJobListResult();

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����
        assertFalse(result);
    }

    /**
     * testExecuteJob005
     * @throws Exception
     */
    public void testExecuteJob005() throws Exception {
        // �p�����[�^
        AsyncBatchExecutor executor = new AsyncBatchExecutor();
        ApplicationContext ctx = new MockApplicationContext();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        String batchTaskServantName = "batchTaskServant";
        BatchJobListResult batchJobListResult = new BatchJobListResult();

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����
        assertFalse(result);
    }

    /**
     * testExecuteJob006
     * @throws Exception
     */
    public void testExecuteJob006() throws Exception {
        // �p�����[�^
        AsyncBatchExecutor executor = new AsyncBatchExecutor();
        MockApplicationContext ctx = new MockApplicationContext() {
            @Override
            public Object getBean(String key, Class arg1) throws BeansException {
                return null;
            }
        };
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        String batchTaskServantName = "batchServant";
        BatchJobListResult batchJobListResult = new BatchJobListResult();

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����
        assertFalse(result);
    }

    /**
     * testExecuteJob007
     * @throws Exception
     */
    public void testExecuteJob007() throws Exception {
        // �p�����[�^
        AsyncBatchExecutor executor = new AsyncBatchExecutor() {
            @Override
            protected boolean startBatchStatus(String jobSequenceId,
                    SystemDao sysDao, PlatformTransactionManager transactionManager) {
                return false;
            }
        };
        MockApplicationContext ctx = new MockApplicationContext();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor() {
            private static final long serialVersionUID = 5460413290026263577L;

            @Override
            public void execute(Runnable task) {
                throw new IllegalStateException("BatchServant�����s���ꂽ��G���[");
            }
        };
        String batchTaskServantName = "batchServant";
        BatchJobListResult batchJobListResult = new BatchJobListResult();

        BatchServant batchServant = new BatchServant() {
            public void setJobSequenceId(String jobSequenceId) {
            }

            public void run() {
            }
        };
        ctx.addBeanMap(batchTaskServantName, batchServant);

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����(�W���u�Ǘ��e�[�u���X�e�[�^�X�X�V������ɏI�����Ȃ������ꍇ���|�[�����O���[�v�͌p��������B)
        assertTrue(result);
    }

    /**
     * testExecuteJob008
     * @throws Exception
     */
    public void testExecuteJob008() throws Exception {
        // �p�����[�^
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.executeRetryCountMax");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.executeRetryCountMax", "10");
        TerasolunaPropertyUtils
                .removeProperty("batchTaskExecutor.executeRetryInterval");
        TerasolunaPropertyUtils.addProperty(
                "batchTaskExecutor.executeRetryInterval", "10");

        AsyncBatchExecutor executor = new AsyncBatchExecutor() {
            @Override
            protected boolean startBatchStatus(String jobSequenceId,
                    SystemDao sysDao, PlatformTransactionManager transactionManager) {
                return true;
            }
        };
        MockApplicationContext ctx = new MockApplicationContext();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor() {
            @Override
            public void execute(Runnable task) {
                throw new TaskRejectedException("hoge");
            }
        };
        String batchTaskServantName = "batchServant";
        BatchJobListResult batchJobListResult = new BatchJobListResult();

        BatchServant batchServant = new BatchServant() {
            public void setJobSequenceId(String jobSequenceId) {
            }

            public void run() {
            }
        };
        ctx.addBeanMap(batchTaskServantName, batchServant);
        AsyncBatchExecutor.destroyCandidateThreadGroupList.clear();

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����
        assertFalse(result);
        assertSame(taskExecutor.getThreadGroup(), AsyncBatchExecutor.destroyCandidateThreadGroupList.get(0));
    }

    /**
     * testExecuteJob009
     * @throws Exception
     */
    public void testExecuteJob009() throws Exception {
        // �p�����[�^
        AsyncBatchExecutor executor = new AsyncBatchExecutor() {
            @Override
            protected boolean startBatchStatus(String jobSequenceId,
                    SystemDao sysDao, PlatformTransactionManager transactionManager) {
                return true;
            }
        };
        MockApplicationContext ctx = new MockApplicationContext();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor() {
            @Override
            public void execute(Runnable task) {
            }
        };
        String batchTaskServantName = "batchServant";
        BatchJobListResult batchJobListResult = new BatchJobListResult();

        BatchServant batchServant = new BatchServant() {
            public void setJobSequenceId(String jobSequenceId) {
            }

            public void run() {
            }
        };
        ctx.addBeanMap(batchTaskServantName, batchServant);

        // �e�X�g
        boolean result = AsyncBatchExecutor.executeJob(executor, ctx,
                taskExecutor, batchTaskServantName, batchJobListResult);

        // ����
        assertTrue(result);
    }

    /**
     * testBatchServantTaskEndTrackerRun001
     * run���\�b�h���s���ɁA
     * BatchServantTaskEndTracker�Ƀ��b�v����Ă���BatchServant��run���\�b�h�����s����A
     * AsyncBatchExecutor.destroyCandidateThreadGroupList�ɁA
     * �R���X�g���N�^�ŗ^����ThreadGroup���o�^����邱�Ƃ��m�F����B
     * @throws Exception
     */
    public void testBatchServantTaskEndTrackerRun001() throws Exception {
        // �p�����[�^
        final AtomicBoolean runWasExecuted = new AtomicBoolean(false);
        BatchServant batchServant = new BatchServant() {
            public void setJobSequenceId(String jobSequenceId) {
            }
            
            public void run() {
                runWasExecuted.set(true);
            }
        };
        ThreadGroup testTG = new ThreadGroup("testBatchServantTaskEndTrackerRun001-TG");
        BatchServantTaskEndTracker tracker = new BatchServantTaskEndTracker(batchServant, testTG);
        AsyncBatchExecutor.destroyCandidateThreadGroupList.clear();

        // �e�X�g
        tracker.run();

        try {
            // ����
            assertTrue(runWasExecuted.get());
            assertSame(testTG, AsyncBatchExecutor.destroyCandidateThreadGroupList.get(0));
        } finally {
            testTG.destroy();
            AsyncBatchExecutor.destroyCandidateThreadGroupList.remove(testTG);
        }
    }

    /**
     * testDestroyThreadGroupsIfPossible001
     * AsyncBatchExecutor.destroyCandidateThreadGroupList��0���̂Ƃ��A
     * �ǂ�ThreadGroup��destroy���Ȃ����Ƃ��m�F����B
     * (�A�N�e�B�u�X���b�h����0��ThreadGroup�ł��A
     *  AsyncBatchExecutor.destroyCandidateThreadGroupList�ɓo�^����Ă��Ȃ����
     *  destroy���Ȃ����Ƃ��m�F����B)
     * @throws Exception
     */
    public void testDestroyThreadGroupsIfPossible001() throws Exception {
        // �p�����[�^
        ThreadGroup testTG = new ThreadGroup("testDestroyThreadGroupsIfPossible001-TG");
        AsyncBatchExecutor.destroyCandidateThreadGroupList.clear();

        // �e�X�g
        AsyncBatchExecutor.destroyThreadGroupsIfPossible();

        try {
            // ����
            assertFalse(testTG.isDestroyed());
        } finally {
            testTG.destroy();
        }
    }

    /**
     * testDestroyThreadGroupsIfPossible002
     * AsyncBatchExecutor.destroyCandidateThreadGroupList��1���ł���A
     * �o�^����Ă���ThreadGroup�̃A�N�e�B�u�X���b�h����0�̏ꍇ�A
     * �EThreadGroup��destroy���邱��
     * �Edestroy����ThreadGroup��AsyncBatchExecutor.destroyCandidateThreadGroupList������폜���邱��
     * ���m�F����B
     * @throws Exception
     */
    public void testDestroyThreadGroupsIfPossible002() throws Exception {
        // �p�����[�^
        ThreadGroup testTG = new ThreadGroup("testDestroyThreadGroupsIfPossible002-TG");
        AsyncBatchExecutor.destroyCandidateThreadGroupList.clear();
        AsyncBatchExecutor.destroyCandidateThreadGroupList.add(testTG);

        // �e�X�g
        AsyncBatchExecutor.destroyThreadGroupsIfPossible();

        // ����
        assertTrue(testTG.isDestroyed());
        assertEquals(0, AsyncBatchExecutor.destroyCandidateThreadGroupList.size());
    }

    /**
     * testDestroyThreadGroupsIfPossible003
     * AsyncBatchExecutor.destroyCandidateThreadGroupList��1���ł���A
     * �o�^����Ă���ThreadGroup�̃A�N�e�B�u�X���b�h����1�̏ꍇ�A
     * �EThreadGroup��destroy���Ȃ�����
     * �EThreadGroup��AsyncBatchExecutor.destroyCandidateThreadGroupList������폜���Ȃ�����
     * ���m�F����B
     * @throws Exception
     */
    public void testDestroyThreadGroupsIfPossible003() throws Exception {
        // �p�����[�^
        ThreadGroup testTG = new ThreadGroup("testDestroyThreadGroupsIfPossible003-TG");
        AsyncBatchExecutor.destroyCandidateThreadGroupList.clear();
        AsyncBatchExecutor.destroyCandidateThreadGroupList.add(testTG);
        Thread testThread = new Thread(testTG, "testDestroyThreadGroupsIfPossible003-T") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        };
        testThread.start();

        // �e�X�g
        AsyncBatchExecutor.destroyThreadGroupsIfPossible();
        assertEquals(1, AsyncBatchExecutor.destroyCandidateThreadGroupList.size());
        assertEquals(testTG, AsyncBatchExecutor.destroyCandidateThreadGroupList.get(0));

        try {
            // ����
            assertFalse(testTG.isDestroyed());
        } finally {
            testThread.interrupt();
            while (testTG.activeCount() != 0) {
                Thread.sleep(100);
            }
            testTG.destroy();
            AsyncBatchExecutor.destroyCandidateThreadGroupList.remove(testTG);
        }
    }

    /**
     * testDestroyThreadGroupsIfPossible005
     * AsyncBatchExecutor.destroyCandidateThreadGroupList��4���ł���A
     * �A�N�e�B�u�X���b�h����1��ThreadGroup��2�A
     * �A�N�e�B�u�X���b�h����0��ThreadGroup��2�A�o�^����Ă���ꍇ�A
     * �E�A�N�e�B�u�X���b�h����1��ThreadGroup��destroy����Ȃ�����
     * �E�A�N�e�B�u�X���b�h����0��ThreadGroup��destroy����邱��
     * �Edestroy����ThreadGroup��AsyncBatchExecutor.destroyCandidateThreadGroupList����폜���邱��
     * �Edestroy���Ȃ�����ThreadGroup��AsyncBatchExecutor.destroyCandidateThreadGroupList����폜���Ȃ�����
     * ���m�F����B
     * @throws Exception
     */
    public void testDestroyThreadGroupsIfPossible004() throws Exception {
        // �p�����[�^
        ThreadGroup testTG1 = new ThreadGroup("testDestroyThreadGroupsIfPossible004-TG1");
        ThreadGroup testTG2 = new ThreadGroup("testDestroyThreadGroupsIfPossible004-TG2");
        ThreadGroup testTG3 = new ThreadGroup("testDestroyThreadGroupsIfPossible004-TG3");
        ThreadGroup testTG4 = new ThreadGroup("testDestroyThreadGroupsIfPossible004-TG4");
        AsyncBatchExecutor.destroyCandidateThreadGroupList.clear();
        AsyncBatchExecutor.destroyCandidateThreadGroupList.add(testTG1);
        AsyncBatchExecutor.destroyCandidateThreadGroupList.add(testTG2);
        AsyncBatchExecutor.destroyCandidateThreadGroupList.add(testTG3);
        AsyncBatchExecutor.destroyCandidateThreadGroupList.add(testTG4);
        Thread testThread1 = new Thread(testTG1, "testDestroyThreadGroupsIfPossible004-T1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        };
        Thread testThread3 = new Thread(testTG3, "testDestroyThreadGroupsIfPossible003-T3") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        };
        testThread1.start();
        testThread3.start();

        // �e�X�g
        AsyncBatchExecutor.destroyThreadGroupsIfPossible();
        assertEquals(2, AsyncBatchExecutor.destroyCandidateThreadGroupList.size());
        assertEquals(testTG1, AsyncBatchExecutor.destroyCandidateThreadGroupList.get(0));
        assertEquals(testTG3, AsyncBatchExecutor.destroyCandidateThreadGroupList.get(1));

        try {
            // ����
            assertFalse(testTG1.isDestroyed());
            assertTrue(testTG2.isDestroyed());
            assertFalse(testTG3.isDestroyed());
            assertTrue(testTG4.isDestroyed());
        } finally {
            testThread1.interrupt();
            testThread3.interrupt();
            while (testTG1.activeCount() != 0) {
                Thread.sleep(100);
            }
            testTG1.destroy();
            AsyncBatchExecutor.destroyCandidateThreadGroupList.remove(testTG1);
            while (testTG3.activeCount() != 0) {
                Thread.sleep(100);
            }
            testTG3.destroy();
            AsyncBatchExecutor.destroyCandidateThreadGroupList.remove(testTG3);
        }
    }

    /**
     * testLogActiveThreadGroupsInfo001
     * activeGroupCount���s���ThreadGroup�������Ȃ��󋵂ɂ����ẮA
     * enumerate��1�񂾂����s���A
     * enumerate�̖߂�l��enumerate�̈����̔z��v�f��
     * �𖞂������Ƃ��m�F����B
     * @throws Exception
     */
    public void testLogActiveThreadGroupsInfo001() throws Exception {
        final AtomicInteger enumerateCallCount = new AtomicInteger(0);
        final AtomicBoolean enumerateSuccess = new AtomicBoolean(false);
        ThreadGroup testParentTG = new ThreadGroup("testLogActiveThreadGroupsInfo001-ParentTG") {
            @Override
            public int activeGroupCount() {
                return super.activeGroupCount();
            }
            @Override
            public int enumerate(ThreadGroup[] list) {
                enumerateCallCount.incrementAndGet();
                int ret = super.enumerate(list);
                enumerateSuccess.set(ret < list.length);
                return ret;
            }
        };

        Thread testThread = new Thread(testParentTG, "testLogActiveThreadGroupsInfo001-ParentT") {
            @Override
            public void run() {
                ThreadGroup testTG = new ThreadGroup("testLogActiveThreadGroupsInfo001-TG");

                // �e�X�g
                AsyncBatchExecutor.logActiveThreadGroupsInfo();

                testTG.destroy();
            }
            
        };
        testThread.start();
        testThread.join();

        // ����
        assertEquals(1, enumerateCallCount.get());
        assertTrue(enumerateSuccess.get());
    }

    /**
     * testLogActiveThreadGroupsInfo002
     * activeGroupCount���s���ThreadGroup�����������Ƃɂ��A
     * enumerate�̖߂�l��enumerate�̈����̔z��v�f��(activeGroupCount�̖߂�l+1)
     * �𖞂����Ȃ��Ƃ��A
     * enumerate�̖߂�l��enumerate�̈����̔z��v�f��
     * �𖞂����悤�A�ēxactiveGroupCount��enumerate�����s���邱�Ƃ��m�F����B
     * @throws Exception
     */
    public void testLogActiveThreadGroupsInfo002() throws Exception {
        final AtomicBoolean firstCount = new AtomicBoolean(true);
        final AtomicInteger enumerateCallCount = new AtomicInteger(0);
        final AtomicBoolean enumerateSuccess = new AtomicBoolean(false);
        ThreadGroup testParentTG = new ThreadGroup("testLogActiveThreadGroupsInfo002-ParentTG") {
            @Override
            public int activeGroupCount() {
                if (firstCount.get()) {
                    // �ŏ���1�񂾂��A1���Ȃ��l��Ԃ����ƂŁA
                    // activeGroupCount���s���ThreadGroup���������󋵂��G�~�����[�g�B
                    firstCount.set(false);
                    return super.activeGroupCount() - 1;
                }
                return super.activeGroupCount();
            }
            @Override
            public int enumerate(ThreadGroup[] list) {
                enumerateCallCount.incrementAndGet();
                int ret = super.enumerate(list);
                enumerateSuccess.set(ret < list.length);
                return ret;
            }
        };

        Thread testThread = new Thread(testParentTG, "testLogActiveThreadGroupsInfo002-ParentT") {
            @Override
            public void run() {
                ThreadGroup testTG = new ThreadGroup("testLogActiveThreadGroupsInfo002-TG");

                // �e�X�g
                AsyncBatchExecutor.logActiveThreadGroupsInfo();

                testTG.destroy();
            }
        };
        testThread.start();
        testThread.join();

        // ����
        assertEquals(2, enumerateCallCount.get());
        assertTrue(enumerateSuccess.get());
    }
}

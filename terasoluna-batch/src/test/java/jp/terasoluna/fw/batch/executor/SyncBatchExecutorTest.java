package jp.terasoluna.fw.batch.executor;

import jp.terasoluna.fw.batch.executor.SecurityManagerEx.ExitException;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.ex.unit.util.ReflectionUtils;
import jp.terasoluna.fw.ex.unit.util.SystemEnvUtils;
import junit.framework.TestCase;

public class SyncBatchExecutorTest extends TestCase {

    final SecurityManager sm = System.getSecurityManager();

    public SyncBatchExecutorTest() {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setSecurityManager(new SecurityManagerEx());
    }

    @Override
    protected void tearDown() throws Exception {
        SystemEnvUtils.restoreEnv();
        System.setSecurityManager(sm);
        super.tearDown();
    }

    /**
     * main�e�X�g01�y�ُ�n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N��������foo��n��
     * �EbeansDef/foo.xml�����݂��Ȃ�
     * �m�F����
     * �E�I���R�[�h��-1�ł��邱��
     * �EID��WAL025002��WARN���O���o�͂���邱��
     * </pre>
     * @throws Exception
     */
    public void testMain01() throws Exception {
        try {
            SyncBatchExecutor.main(new String[] { "foo" });
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(-1, e.state);
        }
    }

    /**
     * main�e�X�g02�y����n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N��������TestSyncBatchExecutor01��n��
     * �EbeansDef/TestSyncBatchExecutor01.xml�����݂���
     * �EbeanName��TestSyncBatchExecutor01��Bean�����[�h�����
     * �m�F����
     * �E�I���R�[�h��100�ł��邱��
     * </pre>
     * @throws Exception
     */
    public void testMain02() throws Exception {
        try {
            SyncBatchExecutor.main(new String[] { "TestSyncBatchExecutor01" });
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(100, e.state);
        }
    }

    /**
     * main�e�X�g03�y�ُ�n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N�������ɉ����n���Ȃ�
     * �E���ϐ�JOB_APP_CD�����ݒ�
     * �m�F����
     * �E�I���R�[�h��-1�ł��邱��
     * </pre>
     * @throws Exception
     */
    public void testMain03() throws Exception {
        try {
            SystemEnvUtils.removeEnv(SyncBatchExecutor.ENV_JOB_APP_CD);
            SyncBatchExecutor.main(new String[] {});
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(-1, e.state);
        }
    }

    /**
     * main�e�X�g04�y�ُ�n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N�������ɉ����n���Ȃ�
     * �E���ϐ�JOB_APP_CD��TestSyncBatchExecutor01��n��
     * �EbeansDef/TestSyncBatchExecutor01.xml�����݂���
     * �EbeanName��TestSyncBatchExecutor01��Bean�����[�h�����
     * �m�F����
     * �E�I���R�[�h��100�ł��邱��
     * </pre>
     * @throws Exception
     */
    public void testMain04() throws Exception {
        try {
            SystemEnvUtils.setEnv(SyncBatchExecutor.ENV_JOB_APP_CD,
                    "TestSyncBatchExecutor01");
            SyncBatchExecutor.main(new String[] {});
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(100, e.state);
        }
    }

    /**
     * main�e�X�g05�y����n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N��������TestSyncBatchExecutor01 param1 param2 param3 param4 param5 param6 param7 param8 param9 param10 param11 param12 param13 param14 param15 param16 param17 param18 param19 param20 ��n��
     * �EbeansDef/TestSyncBatchExecutor01.xml�����݂���
     * �EbeanName��TestSyncBatchExecutor01��Bean�����[�h�����
     * �m�F����
     * �Eparam1�`20�܂�ID:DAL025044�̃��O�ɏo�͂���邱��
     * �E�I���R�[�h��100�ł��邱��
     * </pre>
     * @throws Exception
     */
    public void testMain05() throws Exception {
        try {
            SyncBatchExecutor.main(new String[] { "TestSyncBatchExecutor01",
                    "param1", "param2", "param3", "param4", "param5", "param6",
                    "param7", "param8", "param9", "param10", "param11",
                    "param12", "param13", "param14", "param15", "param16",
                    "param17", "param18", "param19", "param20" });
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(100, e.state);
        }
    }

    /**
     * main�e�X�g06�y����n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N��������TestSyncBatchExecutor01 param1 param2 param3 param4 param5 param6 param7 param8 param9 param10 param11 param12 param13 param14 param15 param16 param17 param18 param19 param20 param21��n��
     * �EbeansDef/TestSyncBatchExecutor01.xml�����݂���
     * �EbeanName��TestSyncBatchExecutor01��Bean�����[�h�����
     * �m�F����
     * �Eparam1�`20�܂�ID:DAL025044�̃��O�ɏo�͂���邱��
     * �E�I���R�[�h��100�ł��邱��
     * </pre>
     * @throws Exception
     */
    public void testMain06() throws Exception {
        try {
            SyncBatchExecutor.main(new String[] { "TestSyncBatchExecutor01",
                    "param1", "param2", "param3", "param4", "param5", "param6",
                    "param7", "param8", "param9", "param10", "param11",
                    "param12", "param13", "param14", "param15", "param16",
                    "param17", "param18", "param19", "param20", "param21" });
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(100, e.state);
        }
    }

    /**
     * main�e�X�g07�y����n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N��������TestSyncBatchExecutor01��n��
     * �EbeansDef/TestSyncBatchExecutor01.xml�����݂���
     * �EbeanName��TestSyncBatchExecutor01��Bean�����[�h�����
     * �E���ϐ�JOB_SEQ_ID��seq01���ݒ肳��Ă���
     * �m�F����
     * �E�I���R�[�h��100�ł��邱��
     * �EID:DAL025044��DEBUG���O��jobSequenceId=seq01���ӂ��܂�邱��
     * </pre>
     * @throws Exception
     */
    public void testMain07() throws Exception {
        try {
            SystemEnvUtils.setEnv(SyncBatchExecutor.ENV_JOB_SEQ_ID, "seq01");
            SyncBatchExecutor.main(new String[] { "TestSyncBatchExecutor01" });
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(100, e.state);
        }
    }

    /**
     * main�e�X�g08�y����n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N��������TestSyncBatchExecutor01���ݒ肳��Ă���
     * �E���ϐ�JOB_ARG_NM1�`20�ɂ��ꂼ��param1 param2 param3 param4 param5 param6 param7 param8 param9 param10 param11 param12 param13 param14 param15 param16 param17 param18 param19 param20���ݒ肳��Ă���
     * �EbeansDef/TestSyncBatchExecutor01.xml�����݂���
     * �EbeanName��TestSyncBatchExecutor01��Bean�����[�h�����
     * �m�F����
     * �Eparam1�`20�܂�ID:DAL025044�̃��O�ɏo�͂���邱��
     * �E�I���R�[�h��100�ł��邱��
     * </pre>
     * @throws Exception
     */
    public void testMain08() throws Exception {
        try {
            for (int i = 1; i <= 20; i++) {
                SystemEnvUtils.setEnv("JOB_ARG_NM" + i, "param" + i);
            }
            SyncBatchExecutor.main(new String[] { "TestSyncBatchExecutor01" });
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(100, e.state);
        }
    }

    /**
     * main�e�X�g09�y����n�z
     * 
     * <pre>
     * ���O����
     * �ESyncBatchExecutor�̋N��������TestSyncBatchExecutor01���ݒ肳��Ă���
     * �E���ϐ�JOB_ARG_NM1�`20�ɂ��ꂼ��param1 param2 param3 param4 param5 param6 param7 param8 param9 param10 param11 param12 param13 param14 param15 param16 param17 param18 param19 param20 param21���ݒ肳��Ă���
     * �EbeansDef/TestSyncBatchExecutor01.xml�����݂���
     * �EbeanName��TestSyncBatchExecutor01��Bean�����[�h�����
     * �m�F����
     * �Eparam1�`20�܂�ID:DAL025044�̃��O�ɏo�͂���邱��
     * �E�I���R�[�h��100�ł��邱��
     * </pre>
     * @throws Exception
     */
    public void testMain09() throws Exception {
        try {
            for (int i = 1; i <= 21; i++) {
                SystemEnvUtils.setEnv("JOB_ARG_NM" + i, "param" + i);
            }
            SyncBatchExecutor.main(new String[] { "TestSyncBatchExecutor01" });
            fail("�ُ�ł�");
        } catch (ExitException e) {
            assertEquals(100, e.state);
        }
    }

    /**
     * getParam�e�X�g01
     * 
     * <pre>
     * ���O�����F
     * getParam��get+������+��O�����̃��\�b�h�����݂���
     * �m�F���ځF
     * �Eget+������+��O�����̃��\�b�h�̌��ʂ��ԋp����邱��
     * </pre>
     * @throws Exception
     */
    public void testGetParam01() throws Exception {
        String getParam = ReflectionUtils.invoke(SyncBatchExecutor.class,
                "getParam", new Class<?>[] { Object.class, String.class,
                        int.class }, new Object[] { new GetParamBean(), "Foo",
                        1 });
        assertEquals("foo1", getParam);
    }

    /**
     * getParam�e�X�g02
     * 
     * <pre>
     * ���O�����F
     * 
     * �m�F���ځF
     * �Enull���ԋp����邱��
     * �E�X�^�b�N�g���[�X��java.lang.SecurityException���o�͂���邱��
     * </pre>
     * @throws Exception
     */
    // public void testGetParam02() throws Exception {
    // �����s�\
    // }
    /**
     * getParam�e�X�g03
     * 
     * <pre>
     * ���O�����F
     * get+������+��O�����̃��\�b�h�����݂��Ȃ�
     * �m�F���ځF
     * �Enull���ԋp����邱��
     * �E�X�^�b�N�g���[�X��java.lang.NoSuchMethodException���o�͂���邱��
     * </pre>
     * @throws Exception
     */
    public void testGetParam03() throws Exception {
        String getParam = ReflectionUtils.invoke(SyncBatchExecutor.class,
                "getParam", new Class<?>[] { Object.class, String.class,
                        int.class }, new Object[] { new BatchJobData(),
                        "HogeMethod", 1 });
        assertEquals(null, getParam);
    }

    /**
     * getParam�e�X�g04
     * 
     * <pre>
     * ���O�����F
     * 
     * �m�F���ځF
     * �Enull���ԋp����邱��
     * �E�X�^�b�N�g���[�X��IllegalArgumentException���o�͂���邱��
     * </pre>
     * @throws Exception
     */
    // public void testGetParam04() throws Exception {
    // �����s�\
    // }
    /**
     * getParam�e�X�g05
     * 
     * <pre>
     * ���O�����F
     * 
     * �m�F���ځF
     * �Enull���ԋp����邱��
     * �E�X�^�b�N�g���[�X��IllegalAccessException���o�͂���邱��
     * </pre>
     * @throws Exception
     */
    // public void testGetParam05() throws Exception {
    // �����s�\
    // }
    /**
     * getParam�e�X�g06
     * 
     * <pre>
     * ���O�����F
     * 
     * �m�F���ځF
     * �Enull���ԋp����邱��
     * �E�X�^�b�N�g���[�X��java.lang.reflect.InvocationTargetException���o�͂���邱��
     * </pre>
     * @throws Exception
     */
    public void testGetParam06() throws Exception {
        String getParam = ReflectionUtils.invoke(SyncBatchExecutor.class,
                "getParam", new Class<?>[] { Object.class, String.class,
                        int.class }, new Object[] { new GetParamBean(), "Foo",
                        6 });
        assertEquals(null, getParam);
    }

    /**
     * GetParamBean
     */
    public static class GetParamBean {
        private String foo1 = "foo1";

        public String getFoo1() {
            return foo1;
        }

        public String getFoo6() {
            throw new NullPointerException();
        }
    }

    /**
     * testSetParam01
     */
    public void testSetParam01() {
        SetParamBean bean = new SetParamBean();
        ReflectionUtils
                .invoke(SyncBatchExecutor.class, "setParam", new Class<?>[] {
                        Object.class, String.class, int.class, String.class },
                        new Object[] { bean, "Foo", 1, "hoge" });
        assertEquals("hoge", bean.getFoo1());
    }

    // SecurityException�̃X�^�b�N�g���[�X���o�͂���邱��
    // public void testSetParam02() {
    // �����ł���
    // }

    /**
     * testSetParam03<br>
     * NoSuchMethodException�̃X�^�b�N�g���[�X���o�͂���邱��
     */
    public void testSetParam03() {
        SetParamBean bean = new SetParamBean();
        ReflectionUtils
                .invoke(SyncBatchExecutor.class, "setParam", new Class<?>[] {
                        Object.class, String.class, int.class, String.class },
                        new Object[] { bean, "Foo", 3, "hoge" });
        assertEquals(null, bean.getFoo1());
    }

    // IllegalArgumentException�̃X�^�b�N�g���[�X���o�͂���邱��
    // public void testSetParam04() {
    // �����ł���
    // }

    // IllegalAccessException�̃X�^�b�N�g���[�X���o�͂���邱��
    // public void testSetParam05() {
    // �����ł���
    // }

    /**
     * testSetParam06<br>
     * InvocationTargetException�̃X�^�b�N�g���[�X���o�͂���邱��
     */
    public void testSetParam06() {
        SetParamBean bean = new SetParamBean();
        ReflectionUtils
                .invoke(SyncBatchExecutor.class, "setParam", new Class<?>[] {
                        Object.class, String.class, int.class, String.class },
                        new Object[] { bean, "Foo", 5, "hoge" });
        assertEquals(null, bean.getFoo1());
    }

    /**
     * SetParamBean
     */
    public static class SetParamBean {
        private String foo1;

        public String getFoo1() {
            return foo1;
        }

        public void setFoo1(String foo1) {
            this.foo1 = foo1;
        }

        public void setFoo5(String foo1) {
            throw new NullPointerException();
        }
    }
}

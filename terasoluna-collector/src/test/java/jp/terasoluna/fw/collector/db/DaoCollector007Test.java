/**
 * 
 */
package jp.terasoluna.fw.collector.db;

import java.util.List;

import jp.terasoluna.fw.collector.CollectorTestUtil;
import jp.terasoluna.fw.collector.dao.UserListQueryResultHandleDao;
import jp.terasoluna.fw.collector.util.MemoryInfo;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * DaoCollectorTest
 * Call�̓���m�F�ƁADaoCollectorPrePostProcess�A�g�̊m�F�p
 */
public class DaoCollector007Test extends DaoTestCase {

    /**
     * Log.
     */
    private static Log logger = LogFactory.getLog(DaoCollector007Test.class);

    private UserListQueryResultHandleDao userListQueryResultHandleDao = null;

    private int previousThreadCount = 0;

    @Override
    protected void addConfigLocations(List<String> configLocations) {
        configLocations.add("jp/terasoluna/fw/collector/db/dataSource.xml");
    }

    public void setUserListQueryResultHandleDao(UserListQueryResultHandleDao userListQueryResultHandleDao) {
        this.userListQueryResultHandleDao = userListQueryResultHandleDao;
    }

    @Override
    protected void onSetUp() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        System.gc();
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        super.onSetUp();
    }

    @Override
    protected void onTearDown() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        System.gc();
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        CollectorTestUtil.allInterrupt();
        super.onTearDown();
    }

    /**
     * Call()�̃e�X�g�i���g���C�@�\�j
     * Call()���\�b�h�̏����ɂ����āAexpStatus��RETRY�̎��Ƀ��[�v�������J��Ԃ����s���邱�Ƃ��m�F����
     */
    @Test
    public void testCall001() {
    	DaoCollectorPrePostProcessStub002 dbcppp = new DaoCollectorPrePostProcessStub002();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collectDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	try {
    		dbc.call();
    	} catch (Exception e) {
    		fail();
    	}
    	
    	assertTrue(dbcppp.getRetryFlag());
    	
    }
    
    /**
     * Call()�̃e�X�g
     * SQL���s���ɃG���[��������������I������ꍇ
     * @throws Exception
     */
    @Test
    public void testCall002() throws Exception {
    	DaoCollectorPrePostProcessStub004 dbcppp = new DaoCollectorPrePostProcessStub004();
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collect", null);
    	config.setExecuteByConstructor(true);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	dbc.resultHandler = new QueueingResultHandlerImpl();
    	Integer returncode = new Integer(99);
    	
    	// Call���s
    	try {
    		returncode = dbc.call();
    	} catch (Exception e) {
    		fail();
    	}
    	
    	// Call�߂�l�m�F
    	assertEquals(0, returncode.intValue());
    	// PrePostProcess���s���ʊm�F
    	assertTrue(dbcppp.getExecPreprocFlg());
    	assertTrue(dbcppp.getExecPostProcCompFlg());
    	assertFalse(dbcppp.getExecPostProcExcpFlg());
    }

    /**
     * Call()�̃e�X�g
     * SQL���s���ɃG���[���������A�G���[�̃X�e�[�^�X��THROW�ł���ꍇ
     * @throws Exception
     */
    @Test
    public void testCall003() throws Exception {
    	DaoCollectorPrePostProcessStub004 dbcppp = new DaoCollectorPrePostProcessStub004();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collectDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	dbc.resultHandler = new QueueingResultHandlerImpl();
    	Integer returncode = new Integer(99);
    	
    	// Call���s
    	try {
    		returncode = dbc.call();
    	} catch (Exception e) {
    		fail();
    	}
    	
    	// Call�߂�l�m�F
    	assertEquals(-1, returncode.intValue());
    	// PrePostProcess���s���ʊm�F
    	assertTrue(dbcppp.getExecPreprocFlg());
    	assertTrue(dbcppp.getExecPostProcCompFlg());
    	assertTrue(dbcppp.getExecPostProcExcpFlg());
    }

    /**
     * Call()�̃e�X�g
     * SQL���s���ɃG���[���������A�G���[�̃X�e�[�^�X��NULL�ł���ꍇ
     * @throws Exception
     */
    @Test
    public void testCall004() throws Exception {
    	DaoCollectorPrePostProcessStub005 dbcppp = new DaoCollectorPrePostProcessStub005();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collectDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	dbc.resultHandler = new QueueingResultHandlerImpl();
    	Integer returncode = new Integer(99);
    	
    	// Call���s
    	try {
    		returncode = dbc.call();
    	} catch (Exception e) {
    		fail();
    	}
    	
    	// Call�߂�l�m�F
    	assertEquals(-1, returncode.intValue());
    	// PrePostProcess���s���ʊm�F
    	assertTrue(dbcppp.getExecPreprocFlg());
    	assertTrue(dbcppp.getExecPostProcCompFlg());
    	assertTrue(dbcppp.getExecPostProcExcpFlg());
    }

    /**
     * Call()�̃e�X�g
     * SQL���s���ɃG���[���������A�G���[�̃X�e�[�^�X��END�ł���ꍇ
     * @throws Exception
     */
    @Test
    public void testCall005() throws Exception {
    	DaoCollectorPrePostProcessStub006 dbcppp = new DaoCollectorPrePostProcessStub006();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collectDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	dbc.resultHandler = new QueueingResultHandlerImpl();
    	Integer returncode = new Integer(99);
    	
    	// Call���s
    	try {
    		returncode = dbc.call();
    	} catch (Exception e) {
    		fail();
    	}
    	
    	// Call�߂�l�m�F
    	assertEquals(0, returncode.intValue());
    	// PrePostProcess���s���ʊm�F
    	assertTrue(dbcppp.getExecPreprocFlg());
    	assertTrue(dbcppp.getExecPostProcCompFlg());
    	assertTrue(dbcppp.getExecPostProcExcpFlg());
    }
    
    /**
     * Call()�̃e�X�g
     * SQL���s���ɃG���[���������A�G���[�̃X�e�[�^�X��RETRY�ł���ꍇ
     * @throws Exception
     */
//    @Test
//    public void testCall00x() throws Exception {
//    	// testCall001�Ɠ��e���d�����邽�ߏȗ�
//    	fail();
//    }
    
    
    /**
     * preprocess()�̃e�X�g
     * DaoCollector����DaoCollectorPrePostProcess#preprocess(DaoCollector<P> collector)
     * �ւ̒l�̎󂯓n��������ɂł��邱�Ƃ��m�F����
     */
    @Test
    public void testPreprocess001() throws Exception{
    	DaoCollectorPrePostProcessStub003 dbcppp = new DaoCollectorPrePostProcessStub003();
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collectDummy", null);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	// preprocess���s�O�̊m�F�iresultHandler��null�j
    	assertNull(dbc.resultHandler);
    	
    	// preprocess���s�i�p�����[�^������ɓn���resultHandler���ݒ肳���j
    	dbc.preprocess();
    	
    	// preprocess���s��m�F�iresultHandler���ݒ肳��Ă��邱�Ɓj
    	assertTrue(dbc.resultHandler instanceof QueueingResultHandlerImpl);
    }

    /**
     * postprocessException(Throwable th)�̃e�X�g
     * DaoCollector����DaoCollectorPrePostProcess#postprocessException(DaoCollector<P> collector, Throwable throwable)
     * �ւ̒l�̎󂯓n��������ɂł��邱�Ƃ��m�F����
     */
    @Test
    public void testPostprocessException001() throws Exception {
    	DaoCollectorPrePostProcessStub003 dbcppp = new DaoCollectorPrePostProcessStub003();
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collectDummy", null);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	
    	Exception ex = new Exception("postprocessException�e�X�g");
    	// preprocess���s�i�p�����[�^������ɓn���DaoCollectorPreProcessStatus.THROW���߂�l�ɂȂ�j
    	DaoCollectorPrePostProcessStatus status = dbc.postprocessException(ex);
    	
    	// preprocess���s��m�F�istatus��THROW�Ȃ�OK�j
    	assertEquals(DaoCollectorPrePostProcessStatus.THROW, status);

    }

    /**
     * postprocessComplete()�̃e�X�g
     * DaoCollector����DaoCollectorPrePostProcess#postprocessComplete(DaoCollector<P> collector)
     * �ւ̒l�̎󂯓n��������ɂł��邱�Ƃ��m�F����
     */
    @Test
    public void testPostprocessComplete001() throws Exception {
    	DaoCollectorPrePostProcessStub003 dbcppp = new DaoCollectorPrePostProcessStub003();
    	DaoCollectorConfig config = new DaoCollectorConfig(
    			this.userListQueryResultHandleDao, "collectDummy", null);
    	config.setDaoCollectorPrePostProcess(dbcppp);
    	DaoCollector<UserBean> dbc = new DaoCollector<UserBean>(config);
    	// preprocess���s�O�̊m�F�iresultHandler��null�j
    	assertNull(dbc.resultHandler);
    	
    	// preprocess���s�i�p�����[�^������ɓn���resultHandler���ݒ肳���j
    	dbc.postprocessComplete();
    	
    	// preprocess���s��m�F�iresultHandler���ݒ肳��Ă��邱�Ɓj
    	assertTrue(dbc.resultHandler instanceof QueueingResultHandlerImpl);

    }
}

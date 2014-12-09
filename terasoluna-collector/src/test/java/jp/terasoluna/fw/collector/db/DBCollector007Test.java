/**
 * 
 */
package jp.terasoluna.fw.collector.db;

import java.util.List;

import jp.terasoluna.fw.collector.CollectorTestUtil;
import jp.terasoluna.fw.collector.util.MemoryInfo;
import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * DBCollectorTest
 * Call�̓���m�F�ƁADBCollectorPrePostProcess�A�g�̊m�F�p
 */
public class DBCollector007Test extends DaoTestCase {

    /**
     * Log.
     */
    private static Log logger = LogFactory.getLog(DBCollector007Test.class);

    private QueryRowHandleDAO queryRowHandleDAO = null;

    private int previousThreadCount = 0;

    @Override
    protected void addConfigLocations(List<String> configLocations) {
        configLocations.add("jp/terasoluna/fw/collector/db/dataSource.xml");
    }

    public void setQueryRowHandleDAO(QueryRowHandleDAO queryRowHandleDAO) {
        this.queryRowHandleDAO = queryRowHandleDAO;
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
    	DBCollectorPrePostProcessStub002 dbcppp = new DBCollectorPrePostProcessStub002();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserListDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
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
    	DBCollectorPrePostProcessStub004 dbcppp = new DBCollectorPrePostProcessStub004();
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserList", null);
    	config.setExecuteByConstructor(true);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
    	dbc.rowHandler = new QueueingDataRowHandlerImpl();
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
    	DBCollectorPrePostProcessStub004 dbcppp = new DBCollectorPrePostProcessStub004();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserListDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
    	dbc.rowHandler = new QueueingDataRowHandlerImpl();
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
    	DBCollectorPrePostProcessStub005 dbcppp = new DBCollectorPrePostProcessStub005();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserListDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
    	dbc.rowHandler = new QueueingDataRowHandlerImpl();
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
    	DBCollectorPrePostProcessStub006 dbcppp = new DBCollectorPrePostProcessStub006();
    	// config�̈����Ɏw�肵��SQLID�͑��݂��Ȃ��e�[�u�����Q�ƁiCall��Exception���N��������j
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserListDummy", null);
    	config.setExecuteByConstructor(true);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
    	dbc.rowHandler = new QueueingDataRowHandlerImpl();
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
     * DBCollector����DBCollectorPrePostProcess#preprocess(DBCollector<P> collector)
     * �ւ̒l�̎󂯓n��������ɂł��邱�Ƃ��m�F����
     */
    @Test
    public void testPreprocess001() throws Exception{
    	DBCollectorPrePostProcessStub003 dbcppp = new DBCollectorPrePostProcessStub003();
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserListDummy", null);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
    	// preprocess���s�O�̊m�F�irowHandler��null�j
    	assertNull(dbc.rowHandler);
    	
    	// preprocess���s�i�p�����[�^������ɓn���rowHandler���ݒ肳���j
    	dbc.preprocess();
    	
    	// preprocess���s��m�F�irowHandler���ݒ肳��Ă��邱�Ɓj
    	assertTrue(dbc.rowHandler instanceof QueueingDataRowHandlerImpl);
    }

    /**
     * postprocessException(Throwable th)�̃e�X�g
     * DBCollector����DBCollectorPrePostProcess#postprocessException(DBCollector<P> collector, Throwable throwable)
     * �ւ̒l�̎󂯓n��������ɂł��邱�Ƃ��m�F����
     */
    @Test
    public void testPostprocessException001() throws Exception {
    	DBCollectorPrePostProcessStub003 dbcppp = new DBCollectorPrePostProcessStub003();
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserListDummy", null);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
    	
    	Exception ex = new Exception("postprocessException�e�X�g");
    	// preprocess���s�i�p�����[�^������ɓn���DBCollectorPreProcessStatus.THROW���߂�l�ɂȂ�j
    	DBCollectorPrePostProcessStatus status = dbc.postprocessException(ex);
    	
    	// preprocess���s��m�F�istatus��THROW�Ȃ�OK�j
    	assertEquals(DBCollectorPrePostProcessStatus.THROW, status);

    }

    /**
     * postprocessComplete()�̃e�X�g
     * DBCollector����DBCollectorPrePostProcess#postprocessComplete(DBCollector<P> collector)
     * �ւ̒l�̎󂯓n��������ɂł��邱�Ƃ��m�F����
     */
    @Test
    public void testPostprocessComplete001() throws Exception {
    	DBCollectorPrePostProcessStub003 dbcppp = new DBCollectorPrePostProcessStub003();
    	DBCollectorConfig config = new DBCollectorConfig(
    			this.queryRowHandleDAO, "selectUserListDummy", null);
    	config.setDbCollectorPrePostProcess(dbcppp);
    	DBCollector<UserBean> dbc = new DBCollector<UserBean>(config);
    	// preprocess���s�O�̊m�F�irowHandler��null�j
    	assertNull(dbc.rowHandler);
    	
    	// preprocess���s�i�p�����[�^������ɓn���rowHandler���ݒ肳���j
    	dbc.postprocessComplete();
    	
    	// preprocess���s��m�F�irowHandler���ݒ肳��Ă��邱�Ɓj
    	assertTrue(dbc.rowHandler instanceof QueueingDataRowHandlerImpl);

    }
}

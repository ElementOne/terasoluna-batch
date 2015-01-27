/**
 * 
 */
package jp.terasoluna.fw.collector.db;

import java.util.List;

import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.CollectorTestUtil;
import jp.terasoluna.fw.collector.dao.UserListQueryRowHandleDao;
import jp.terasoluna.fw.collector.util.MemoryInfo;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DaoCollectorTest
 */
public class DaoCollector003Test extends DaoTestCase {

    /**
     * Log.
     */
    private static Log logger = LogFactory.getLog(DaoCollector003Test.class);

    private UserListQueryRowHandleDao userListQueryRowHandleDao = null;

    private int previousThreadCount = 0;

    @Override
    protected void addConfigLocations(List<String> configLocations) {
        configLocations.add("jp/terasoluna/fw/collector/db/dataSource.xml");
    }

    public void setUserListQueryRowHandleDao(UserListQueryRowHandleDao userListQueryRowHandleDao) {
        this.userListQueryRowHandleDao = userListQueryRowHandleDao;
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
        this.previousThreadCount = CollectorTestUtil.getCollectorThreadCount();
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
     * {@link jp.terasoluna.fw.collector.db.DaoCollector#(java.lang.Object, java.lang.String, java.lang.Object, int)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    public void testDaoCollectorObjectStringObjectInt001()
                                                                        throws Exception {
        if (this.userListQueryRowHandleDao == null) {
            fail("userListQueryRowHandleDao��null�ł��B");
        }

        int count_first = 0;

        Collector<UserBean> it = new DaoCollector<UserBean>(
                this.userListQueryRowHandleDao, "collect", null, 1);
        try {
            for (UserBean user : it) {
                count_first++;
            }
        } finally {
            DaoCollector.closeQuietly(it);
        }

        // �R���N�^�X���b�h���`�F�b�N
        assertTrue(CollectorTestUtil
                .lessThanCollectorThreadCount(0 + this.previousThreadCount));

        for (int i = 0; i < 2; i++) {
            int count = 0;

            long startTime = System.currentTimeMillis();

            Collector<UserBean> it2 = new DaoCollector<UserBean>(
                    this.userListQueryRowHandleDao, "collect", null, 1);
            try {
                for (UserBean user : it2) {
                    if (logger.isInfoEnabled() && user == null) {
                        logger.info("UserBean is null.##############");
                    }

                    count++;
                }
            } finally {
                DaoCollector.closeQuietly(it2);
            }

            // �R���N�^�X���b�h���`�F�b�N
            assertTrue(CollectorTestUtil
                    .lessThanCollectorThreadCount(0 + this.previousThreadCount));

            long endTime = System.currentTimeMillis();

            if (logger.isInfoEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("i:[");
                sb.append(String.format("%04d", i));
                sb.append("]");
                sb.append(" milliSec:[");
                sb.append(String.format("%04d", (endTime - startTime)));
                sb.append("]");
                logger.info(sb.toString());
            }

            assertEquals(count_first, count);
        }
    }

}

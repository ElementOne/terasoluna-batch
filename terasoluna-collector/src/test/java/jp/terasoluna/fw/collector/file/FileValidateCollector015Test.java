package jp.terasoluna.fw.collector.file;

import java.net.URL;
import java.util.List;

import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.CollectorTestUtil;
import jp.terasoluna.fw.collector.util.MemoryInfo;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Validator;

public class FileValidateCollector015Test extends DaoTestCase {
    /**
     * Log.
     */
    private static Log logger = LogFactory
            .getLog(FileValidateCollector015Test.class);

    private FileQueryDAO csvFileQueryDAO = null;

    private int previousThreadCount = 0;

    public void setCsvFileQueryDAO(FileQueryDAO csvFileQueryDAO) {
        this.csvFileQueryDAO = csvFileQueryDAO;
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

    @Override
    protected void addConfigLocations(List<String> configLocations) {
        configLocations.add("jp/terasoluna/fw/collector/db/dataSource.xml");
    }

    public void testFileValidateCollector015() throws Exception {
        if (this.csvFileQueryDAO == null) {
            fail("csvFileQueryDAO��null�ł��B");
        }

        URL url = getClass().getClassLoader().getResource("USER_TEST.csv");
        if (logger.isDebugEnabled()) {
            if (url != null) {
                logger.debug("url.getPath() : " + url.getPath());
            } else {
                logger.debug("url.getPath() : " + null);
            }
        }

        if (url == null) {
            fail("url��null�ł��B");
        }

        // Collector<B000001Data> ac = new FileValidateCollector<B000001Data>(
        // this.csvFileQueryDAO, url.getPath(), B000001Data.class, 100);

        int count_first = 0;
        Validator validator = null;

        Collector<B000001Data> it = new FileValidateCollector<B000001Data>(
                this.csvFileQueryDAO, url.getPath(), B000001Data.class, 100,
                null, validator);

        try {
            for (B000001Data data : it) {
                if (logger.isInfoEnabled()) {

                    StringBuilder sb = new StringBuilder();
                    if (data != null) {
                        sb.append("UserId:[");
                        sb.append(String.format("%2s", data.getId()));
                        sb.append("],");
                        sb.append("FirstName:[");
                        sb.append(String.format("%4s", data.getFirstname()));
                        sb.append("],");
                        sb.append("FamilyName:[");
                        sb.append(String.format("%4s", data.getFamilyname()));
                        sb.append("],");
                        sb.append("UserAge:[");
                        sb.append(String.format("%2s", data.getAge()));
                        sb.append("])");
                        if (false) {
                            logger.info(sb.toString());
                        }
                    }
                }
                count_first++;

                if (count_first > 10) {
                    break;
                }

            }
        } finally {
            // �N���[�Y
            FileValidateCollector.closeQuietly(it);
        }

        // �R���N�^�X���b�h���`�F�b�N
        assertTrue(CollectorTestUtil
                .lessThanCollectorThreadCount(0 + this.previousThreadCount));

        for (int i = 0; i < 7; i++) {
            int count = 0;

            long startTime = System.currentTimeMillis();

            Collector<B000001Data> it2 = new FileValidateCollector<B000001Data>(
                    this.csvFileQueryDAO, url.getPath(), B000001Data.class,
                    100, null, validator);
            try {
                for (B000001Data data : it2) {
                    if (logger.isInfoEnabled()) {
                        StringBuilder sb = new StringBuilder();
                        if (data != null) {
                            sb.append("UserId:[");
                            sb.append(String.format("%2s", data.getId()));
                            sb.append("],");
                            sb.append("FirstName:[");
                            sb
                                    .append(String.format("%4s", data
                                            .getFirstname()));
                            sb.append("],");
                            sb.append("FamilyName:[");
                            sb.append(String
                                    .format("%4s", data.getFamilyname()));
                            sb.append("],");
                            sb.append("UserAge:[");
                            sb.append(String.format("%2s", data.getAge()));
                            sb.append("])");
                            if (false) {
                                logger.info(sb.toString());
                            }
                        } else {
                            sb.append("UserBean is null.##############");
                            logger.info(sb.toString());
                        }
                    }
                    count++;

                    if (i % 2 == 0 && count > 10) {
                        break;
                    }
                }
            } finally {
                // �N���[�Y
                FileValidateCollector.closeQuietly(it2);
            }

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

            if (i % 2 == 0) {
                assertEquals(count_first, count);
            }
        }

        // �R���N�^�X���b�h���`�F�b�N
        assertTrue(CollectorTestUtil
                .lessThanCollectorThreadCount(1 + this.previousThreadCount));

    }

}

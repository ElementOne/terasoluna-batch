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

public class FileValidateCollector020Test extends DaoTestCase {
    /**
     * Log.
     */
    private static Log logger = LogFactory
            .getLog(FileValidateCollector020Test.class);

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

    public void testFileValidateCollector020() throws Exception {
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

        int count_first = 0;
        ValidatorStub validator = new ValidatorStub();
        SkipValidationErrorHandler validatorErrorHandler = new SkipValidationErrorHandler();

        Collector<B000001Data> it = new FileValidateCollector<B000001Data>(
                this.csvFileQueryDAO, url.getPath(), B000001Data.class,
                validator, validatorErrorHandler);

        try {
            // it = ac.execute();

            for (B000001Data data : it) {
                count_first++;

            }
        } finally {
            // �N���[�Y
            FileValidateCollector.closeQuietly(it);
        }

        // �R���N�^�X���b�h���`�F�b�N
        assertTrue(CollectorTestUtil
                .lessThanCollectorThreadCount(0 + this.previousThreadCount));

        // Validator#supports���\�b�h���Ă΂ꂽ��
        assertEquals(1000, validator.getCallSupports());

        // Validator#validate���\�b�h���Ă΂ꂽ��
        assertEquals(1000, validator.getCallValidate());

        // �G���[�����������t�B�[���h�̌���
        assertEquals(0, validatorErrorHandler.getErrorFieldCount());

        // ���[�v�����񐔁i�G���[�̕����Ȃ��Ȃ�j
        assertEquals(1000, count_first);
    }

}

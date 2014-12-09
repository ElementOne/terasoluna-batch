package jp.terasoluna.fw.collector.file;

import java.net.URL;
import java.util.List;

import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.CollectorTestUtil;
import jp.terasoluna.fw.collector.util.MemoryInfo;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.ex.unit.testcase.DaoTestCase;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileValidateCollector022Test extends DaoTestCase {
    /**
     * Log.
     */
    private static Log logger = LogFactory
            .getLog(FileValidateCollector022Test.class);

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

    public void testFileValidateCollector022() throws Exception {
        if (this.csvFileQueryDAO == null) {
            fail("csvFileQueryDAO��null�ł��B");
        }

        URL url = getClass().getClassLoader().getResource(
                "USER_TEST5_NAME_EMPTY.csv");
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
        SkipValidationErrorHandler validatorErrorHandler = new SkipValidationErrorHandler(
                ValidateErrorStatus.END);

        Collector<B000001Data> it = new FileValidateCollector<B000001Data>(
                this.csvFileQueryDAO, url.getPath(), B000001Data.class,
                validator, validatorErrorHandler);

        try {
            // it = ac.execute();

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

            }
        } finally {
            // �N���[�Y
            FileValidateCollector.closeQuietly(it);
        }

        if (logger.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append(" CallSupports:[");
            sb.append(validator.getCallSupports());
            sb.append("]");
            sb.append(" CallValidate:[");
            sb.append(validator.getCallValidate());
            sb.append("]");
            sb.append(" ErrorFieldCount:[");
            sb.append(validatorErrorHandler.getErrorFieldCount());
            sb.append("]");
            sb.append(" count_first:[");
            sb.append(count_first);
            sb.append("]");
            logger.info(sb.toString());
        }

        // �R���N�^�X���b�h���`�F�b�N
        assertTrue(CollectorTestUtil
                .lessThanCollectorThreadCount(0 + this.previousThreadCount));

        // Validator#supports���\�b�h���Ă΂ꂽ��
        assertEquals(13, validator.getCallSupports());

        // Validator#validate���\�b�h���Ă΂ꂽ��
        assertEquals(13, validator.getCallValidate());

        // �G���[�����������t�B�[���h�̌���
        assertEquals(1, validatorErrorHandler.getErrorFieldCount());

        // ���[�v�����񐔁i�G���[�̕����Ȃ��Ȃ�j
        assertEquals(12, count_first);
    }

}

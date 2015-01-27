/**
 * 
 */
package jp.terasoluna.fw.collector.db;

import static org.junit.Assert.*;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandlerStatus;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 */
public class DaoCollectorConfigTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#DaoCollectorConfig(Object, String, Object)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testDaoCollectorConfig001() {
        Object queryRowHandleDao = null;
        String methodName = null;
        Object bindParams = null;

        // �e�X�g
        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        assertNotNull(config);
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#DaoCollectorConfig(Object, String, Object)} 
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testDaoCollectorConfig002() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        // �e�X�g
        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#addQueueSize(int)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testAddQueueSize001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        int queueSize = 123;

        // �e�X�g
        config.addQueueSize(queueSize);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
        assertEquals(queueSize, config.getQueueSize());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#addExceptionHandler(jp.terasoluna.fw.collector.exception.CollectorExceptionHandler)} 
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testAddExceptionHandler001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        CollectorExceptionHandler exceptionHandler = new CollectorExceptionHandler() {
            public CollectorExceptionHandlerStatus handleException(
                    DataValueObject dataValueObject) {
                return null;
            }
        };

        // �e�X�g
        config.addExceptionHandler(exceptionHandler);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
        assertEquals(exceptionHandler, config.getExceptionHandler());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#addValidator(org.springframework.validation.Validator)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testAddValidator001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        Validator validator = new Validator() {
            @SuppressWarnings("unchecked")
            public boolean supports(Class clazz) {
                return false;
            }

            public void validate(Object target, Errors errors) {
            }
        };

        // �e�X�g
        config.addValidator(validator);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
        assertEquals(validator, config.getValidator());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#addValidationErrorHandler(jp.terasoluna.fw.collector.validate.ValidationErrorHandler)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testAddValidationErrorHandler001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        ValidationErrorHandler validationErrorHandler = new ValidationErrorHandler() {
            public ValidateErrorStatus handleValidationError(
                    DataValueObject dataValueObject, Errors errors) {
                return null;
            }
        };

        // �e�X�g
        config.addValidationErrorHandler(validationErrorHandler);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
        assertEquals(validationErrorHandler, config.getValidationErrorHandler());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#addRelation1n(boolean)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testAddRelation1n001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        boolean relation1n = true;

        // �e�X�g
        config.addRelation1n(relation1n);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
        assertEquals(relation1n, config.isRelation1n());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#addDaoCollectorPrePostProcess(jp.terasoluna.fw.collector.db.DaoCollectorPrePostProcess)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testAddDaoCollectorPrePostProcess001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        DaoCollectorPrePostProcess daoCollectorPrePostProcess = new DaoCollectorPrePostProcess() {
            public <P> void postprocessComplete(DaoCollector<P> collector) {
            }

            public <P> DaoCollectorPrePostProcessStatus postprocessException(
                    DaoCollector<P> collector, Throwable throwable) {
                return null;
            }

            public <P> void preprocess(DaoCollector<P> collector) {
            }
        };

        // �e�X�g
        config.addDaoCollectorPrePostProcess(daoCollectorPrePostProcess);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
        assertEquals(daoCollectorPrePostProcess, config
                .getDaoCollectorPrePostProcess());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#addExecuteByConstructor(boolean)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testAddExecuteByConstructor001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        boolean executeByConstructor = true;

        // �e�X�g
        config.addExecuteByConstructor(executeByConstructor);

        assertNotNull(config);
        assertEquals(queryRowHandleDao, config.getQueryRowHandleDao());
        assertEquals(methodName, config.getMethodName());
        assertEquals(bindParams, config.getBindParams());
        assertEquals(executeByConstructor, config.isExecuteByConstructor());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#getQueryRowHandleDao()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testGetQueryRowHandleDAO001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        // �e�X�g
        Object result = config.getQueryRowHandleDao();

        assertNotNull(result);
        assertEquals(queryRowHandleDao, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#setQueryRowHandleDao(Object)} 
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testSetQueryRowHandleDAO001() {
        Object queryRowHandleDao = new Object();
        Object queryRowHandleDao2 = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        // �e�X�g
        config.setQueryRowHandleDao(queryRowHandleDao2);

        assertNotNull(config.getQueryRowHandleDao());
        assertEquals(queryRowHandleDao2, config.getQueryRowHandleDao());
    }

    /**
     * {@link DaoCollectorConfig#getMethodName()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testGetMethodName001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        // �e�X�g
        String result = config.getMethodName();

        assertNotNull(result);
        assertEquals(methodName, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#setMethodName(String)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testSetMethodName001() {
        Object queryRowHandleDao = new Object();

        String methodName = "hoge";
        String methodName2 = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        // �e�X�g
        config.setMethodName(methodName2);

        assertNotNull(config.getMethodName());
        assertEquals(methodName, config.getMethodName());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#getBindParams()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testGetBindParams001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        // �e�X�g
        Object result = config.getBindParams();

        assertNotNull(result);
        assertEquals(bindParams, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#setBindParams(java.lang.Object)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testSetBindParams001() {
        Object queryRowHandleDao = new Object();

        String methodName = "hoge";
        Object bindParams = new Object();
        Object bindParams2 = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        // �e�X�g
        config.setBindParams(bindParams2);

        assertNotNull(config.getBindParams());
        assertEquals(bindParams2, config.getBindParams());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#isRelation1n()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testIsRelation1n001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();
        boolean relation1n = true;

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);
        config.setRelation1n(relation1n);

        // �e�X�g
        boolean result = config.isRelation1n();

        assertNotNull(result);
        assertEquals(relation1n, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#setRelation1n(boolean)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testSetRelation1n001() {
        Object queryRowHandleDao = new Object();

        String methodName = "hoge";
        Object bindParams = new Object();
        boolean relation1n = true;

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        // �e�X�g
        config.setRelation1n(relation1n);

        assertNotNull(config.isRelation1n());
        assertEquals(relation1n, config.isRelation1n());
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#getDaoCollectorPrePostProcess()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void testGetDaoCollectorPrePostProcess001() {
        Object queryRowHandleDao = new Object();
        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);
        DaoCollectorPrePostProcess daoCollectorPrePostProcess = new DaoCollectorPrePostProcess() {
            public <P> void postprocessComplete(DaoCollector<P> collector) {
            }

            public <P> DaoCollectorPrePostProcessStatus postprocessException(
                    DaoCollector<P> collector, Throwable throwable) {
                return null;
            }

            public <P> void preprocess(DaoCollector<P> collector) {
            }
        };
        config.setDaoCollectorPrePostProcess(daoCollectorPrePostProcess);

        // �e�X�g
        DaoCollectorPrePostProcess result = config
                .getDaoCollectorPrePostProcess();

        assertNotNull(result);
        assertEquals(daoCollectorPrePostProcess, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoCollectorConfig#setDaoCollectorPrePostProcess(jp.terasoluna.fw.collector.db.DaoCollectorPrePostProcess)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @Test
    public void setDaoCollectorPrePostProcess() {
        Object queryRowHandleDao = new Object();

        String methodName = "hoge";
        Object bindParams = new Object();

        DaoCollectorConfig config = new DaoCollectorConfig(queryRowHandleDao,
                methodName, bindParams);

        DaoCollectorPrePostProcess daoCollectorPrePostProcess = new DaoCollectorPrePostProcess() {
            public <P> void postprocessComplete(DaoCollector<P> collector) {
            }

            public <P> DaoCollectorPrePostProcessStatus postprocessException(
                    DaoCollector<P> collector, Throwable throwable) {
                return null;
            }

            public <P> void preprocess(DaoCollector<P> collector) {
            }
        };
        config.setDaoCollectorPrePostProcess(daoCollectorPrePostProcess);
        DaoCollectorPrePostProcess daoCollectorPrePostProcess2 = new DaoCollectorPrePostProcess() {
            public <P> void postprocessComplete(DaoCollector<P> collector) {
            }

            public <P> DaoCollectorPrePostProcessStatus postprocessException(
                    DaoCollector<P> collector, Throwable throwable) {
                return null;
            }

            public <P> void preprocess(DaoCollector<P> collector) {
            }
        };

        // �e�X�g
        config.setDaoCollectorPrePostProcess(daoCollectorPrePostProcess2);

        assertNotNull(config.getDaoCollectorPrePostProcess());
        assertEquals(daoCollectorPrePostProcess2, config
                .getDaoCollectorPrePostProcess());
    }

}

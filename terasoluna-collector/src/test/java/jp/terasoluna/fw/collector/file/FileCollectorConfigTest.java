/**
 * 
 */
package jp.terasoluna.fw.collector.file;

import static org.junit.Assert.*;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandlerStatus;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.validate.ValidationErrorHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.file.dao.FileLineIterator;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

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
public class FileCollectorConfigTest {

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
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#FileCollectorConfig(jp.terasoluna.fw.file.dao.FileQueryDAO, java.lang.String, java.lang.Class)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFileCollectorConfig001() {
        FileQueryDAO fileQueryDAO = null;
        String fileName = null;
        Class clazz = null;

        // �e�X�g
        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        assertNotNull(config);
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#FileCollectorConfig(jp.terasoluna.fw.file.dao.FileQueryDAO, java.lang.String, java.lang.Class)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFileCollectorConfig002() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        // �e�X�g
        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        assertNotNull(config);
        assertEquals(fileQueryDAO, config.getFileQueryDAO());
        assertEquals(fileName, config.getFileName());
        assertEquals(clazz, config.getClazz());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#addQueueSize(int)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddQueueSize001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        int queueSize = 987;

        // �e�X�g
        config.addQueueSize(queueSize);

        assertNotNull(config);
        assertEquals(fileQueryDAO, config.getFileQueryDAO());
        assertEquals(fileName, config.getFileName());
        assertEquals(clazz, config.getClazz());
        assertEquals(queueSize, config.getQueueSize());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#addExceptionHandler(jp.terasoluna.fw.collector.exception.CollectorExceptionHandler)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddExceptionHandler001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        CollectorExceptionHandler exceptionHandler = new CollectorExceptionHandler() {
            public CollectorExceptionHandlerStatus handleException(
                    DataValueObject dataValueObject) {
                return null;
            }
        };

        // �e�X�g
        config.addExceptionHandler(exceptionHandler);

        assertNotNull(config);
        assertEquals(fileQueryDAO, config.getFileQueryDAO());
        assertEquals(fileName, config.getFileName());
        assertEquals(clazz, config.getClazz());
        assertEquals(exceptionHandler, config.getExceptionHandler());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#addValidator(org.springframework.validation.Validator)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddValidator001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        Validator validator = new Validator() {
            public boolean supports(Class clazz) {
                return false;
            }

            public void validate(Object target, Errors errors) {
            }
        };

        // �e�X�g
        config.addValidator(validator);

        assertNotNull(config);
        assertEquals(fileQueryDAO, config.getFileQueryDAO());
        assertEquals(fileName, config.getFileName());
        assertEquals(clazz, config.getClazz());
        assertEquals(validator, config.getValidator());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#addValidationErrorHandler(jp.terasoluna.fw.collector.validate.ValidationErrorHandler)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddValidationErrorHandler001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        ValidationErrorHandler validationErrorHandler = new ValidationErrorHandler() {
            public ValidateErrorStatus handleValidationError(
                    DataValueObject dataValueObject, Errors errors) {
                return null;
            }
        };

        // �e�X�g
        config.addValidationErrorHandler(validationErrorHandler);

        assertNotNull(config);
        assertEquals(fileQueryDAO, config.getFileQueryDAO());
        assertEquals(fileName, config.getFileName());
        assertEquals(clazz, config.getClazz());
        assertEquals(validationErrorHandler, config.getValidationErrorHandler());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#addExecuteByConstructor(boolean)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testAddExecuteByConstructor001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        boolean executeByConstructor = true;

        // �e�X�g
        config.addExecuteByConstructor(executeByConstructor);

        assertNotNull(config);
        assertEquals(fileQueryDAO, config.getFileQueryDAO());
        assertEquals(fileName, config.getFileName());
        assertEquals(clazz, config.getClazz());
        assertEquals(executeByConstructor, config.isExecuteByConstructor());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#getFileQueryDAO()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetFileQueryDAO001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        // �e�X�g
        FileQueryDAO result = config.getFileQueryDAO();

        assertNotNull(result);
        assertEquals(fileQueryDAO, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#setFileQueryDAO(jp.terasoluna.fw.file.dao.FileQueryDAO)}
     * �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSetFileQueryDAO001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        FileQueryDAO fileQueryDAO2 = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        // �e�X�g
        config.setFileQueryDAO(fileQueryDAO2);

        assertNotNull(config.getFileQueryDAO());
        assertEquals(fileQueryDAO2, config.getFileQueryDAO());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#getFileName()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetFileName001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        // �e�X�g
        String result = config.getFileName();

        assertNotNull(result);
        assertEquals(fileName, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#setFileName(java.lang.String)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSetFileName001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };

        String fileName = "hoge";
        String fileName2 = "hogehoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        // �e�X�g
        config.setFileName(fileName2);

        assertNotNull(config.getFileName());
        assertEquals(fileName2, config.getFileName());
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#getClazz()} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetClazz001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };
        String fileName = "hoge";
        Class clazz = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        // �e�X�g
        Class result = config.getClazz();

        assertNotNull(result);
        assertEquals(clazz, result);
    }

    /**
     * {@link jp.terasoluna.fw.collector.file.FileCollectorConfig#setClazz(java.lang.Class)} �̂��߂̃e�X�g�E���\�b�h�B
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testSetClazz001() {
        FileQueryDAO fileQueryDAO = new FileQueryDAO() {
            public <T> FileLineIterator<T> execute(String fileName,
                    Class<T> clazz) {
                return null;
            }
        };

        String fileName = "hoge";
        Class clazz = B000001Data.class;
        Class clazz2 = B000001Data.class;

        FileCollectorConfig config = new FileCollectorConfig(fileQueryDAO,
                fileName, clazz);

        // �e�X�g
        config.setClazz(clazz2);

        assertNotNull(config.getClazz());
        assertEquals(clazz2, config.getClazz());
    }

}

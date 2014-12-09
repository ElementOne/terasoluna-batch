package jp.terasoluna.fw.file.dao.standard;

import java.util.Map;

import jp.terasoluna.fw.file.dao.standard.AbstractFileLineIterator;
import jp.terasoluna.fw.file.dao.standard.ColumnParser;

/**
 * AbstractFileLineIterator�̎����N���X�B
 * <p>
 * �����
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public class AbstractFileLineIteratorImpl02<T> extends
                                               AbstractFileLineIterator<T> {

    public AbstractFileLineIteratorImpl02(String fileName, Class<T> clazz,
            Map<String, ColumnParser> columnParserMap) {
        super(fileName, clazz, columnParserMap);
    }

    @Override
    protected char getDelimiter() {
        return 0;
    }

    @Override
    protected char getEncloseChar() {
        return 0;
    }

    @Override
    protected String[] separateColumns(String fileLineString) {
        return null;
    }

    @Override
    protected boolean isCheckColumnAnnotationCount() {
        return false;
    }

}

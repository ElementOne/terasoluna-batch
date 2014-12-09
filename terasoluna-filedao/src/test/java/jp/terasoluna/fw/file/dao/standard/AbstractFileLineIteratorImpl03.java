package jp.terasoluna.fw.file.dao.standard;

import java.util.Map;

import jp.terasoluna.fw.file.dao.standard.AbstractFileLineIterator;
import jp.terasoluna.fw.file.dao.standard.ColumnParser;

/**
 * AbstractFileLineIterator�̎����N���X�B
 * <p>
 * �ȉ��̎���������Ă���B<br>
 * <ul>
 * <li>{@link #getEncloseChar()}��'\"'��Ԃ�
 * <li>{@link #getDelimiter()}��','��Ԃ�
 * </ul>
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public class AbstractFileLineIteratorImpl03<T> extends
                                               AbstractFileLineIterator<T> {

    public AbstractFileLineIteratorImpl03(String string, Class<T> clazz,
            Map<String, ColumnParser> textSetterMap) {
        super(string, clazz, textSetterMap);
    }

    @Override
    protected char getDelimiter() {
        return ',';
    }

    @Override
    protected char getEncloseChar() {
        return '\"';
    }

    @Override
    protected String[] separateColumns(String fileLineString) {
        return null;
    }

}

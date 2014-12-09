package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

import jp.terasoluna.fw.file.dao.standard.ColumnParser;

/**
 * ColumnParser�����N���X
 * <p>
 * {@link #parse(String, Object, Method, String)}�́A�K�� InvocationTargetException���X���[����B
 */
public class AbstractFileLineIterator_ColumnParserStub03 implements
                                                        ColumnParser {

    public void parse(String column, Object t, Method method,
            String columnFormat) throws IllegalArgumentException,
                                IllegalAccessException,
                                InvocationTargetException, ParseException {
        throw new InvocationTargetException(
                new Exception("ColumnParser�ł̃G���[�ł�"));
    }

}

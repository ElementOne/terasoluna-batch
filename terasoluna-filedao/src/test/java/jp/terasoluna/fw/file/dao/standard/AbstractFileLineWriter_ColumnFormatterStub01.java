package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.terasoluna.fw.file.dao.standard.ColumnFormatter;

/**
 * ColumnFormatter�̃X�^�u�N���X�B<br>
 * ���͂Ɋ֌W�Ȃ�null��Ԃ��B
 * @author ���O
 */
public class AbstractFileLineWriter_ColumnFormatterStub01 implements
                                                         ColumnFormatter {

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.file.dao.standard.ColumnFormatter#format(java.lang.Object, java.lang.reflect.Method,
     * java.lang.String)
     */
    public String format(Object t, Method method, String string)
                                                                throws IllegalArgumentException,
                                                                IllegalAccessException,
                                                                InvocationTargetException {
        return null;
    }

}

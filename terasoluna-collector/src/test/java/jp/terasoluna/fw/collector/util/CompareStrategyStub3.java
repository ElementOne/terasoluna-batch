package jp.terasoluna.fw.collector.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.terasoluna.fw.collector.util.strategy.CompareStrategy;

/**
 * Date��Hour�̐��x�Ŕ�r����CompareStrategy�B
 * (���̃X�^�u�́A�X���b�h�Ԃŋ��L���Ă͂Ȃ�Ȃ��B)
 */
public class CompareStrategyStub3 implements CompareStrategy<Date> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH");

    public boolean equalsObjects(Date value1, Date value2) {
        return sdf.format(value1).equals(sdf.format(value2));
    }
}

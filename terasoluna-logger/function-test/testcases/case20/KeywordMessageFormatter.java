package case20;

import java.text.MessageFormat;

import org.apache.log4j.MDC;

import jp.terasoluna.fw.message.MessageFormatter;

public class KeywordMessageFormatter implements MessageFormatter {

    public String format(String pattern, Object... args) {
        // �u�L�[���[�h,���b�Z�[�W�{���v�Ƃ����`����z��
        String[] vals = pattern.split(",");
        String pat = null;
        if (vals.length > 1) {
            // �L�[���[�h���ݒ肳��Ă���ꍇ
            String keyword = vals[0];
            pat = vals[1];
            // �L�[���[�h��MDC�ɐݒ肷��
            MDC.put("keyword", keyword);
        } else {
            pat = pattern;
        }
        return MessageFormat.format(pat, args);
    }
}

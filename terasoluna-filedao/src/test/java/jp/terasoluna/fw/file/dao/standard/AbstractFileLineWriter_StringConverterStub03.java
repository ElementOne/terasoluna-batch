package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.StringConverter;

/**
 * StringConverter�X�^�u�N���X�B<br>
 * ���̓f�[�^��"_convert()"��ǉ��������ʂ�Ԃ�
 * @author ���O
 */
public class AbstractFileLineWriter_StringConverterStub03 implements
                                                         StringConverter {

    /**
     * �R���X�g���N�^
     */
    public AbstractFileLineWriter_StringConverterStub03() {
    }

    /**
     * �ϊ��������s��Ȃ��B
     */
    public String convert(String s) {
        return s + "_convert()";
    }
}

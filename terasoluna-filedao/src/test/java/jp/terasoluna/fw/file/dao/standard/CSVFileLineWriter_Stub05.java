package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����������A�����������Ȃ��t�@�C���s�I�u�W�F�N�g�X�^�u
 * <p>
 * FileFormat�A�m�e�[�V�����ŁA��؂蕶����ݒ肵�Ă���B
 */
@FileFormat(delimiter = '�A')
public class CSVFileLineWriter_Stub05 {
    @InputFileColumn(columnIndex = 0)
    @OutputFileColumn(columnIndex = 0)
    private String dummy;

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}

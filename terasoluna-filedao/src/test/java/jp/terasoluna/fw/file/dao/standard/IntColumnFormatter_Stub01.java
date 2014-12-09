package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * public��get���\�b�h�����t�@�C���s�I�u�W�F�N�g�B
 */
@FileFormat()
public class IntColumnFormatter_Stub01 {

    @InputFileColumn(columnIndex = 0)
    @OutputFileColumn(columnIndex = 0)
    private int intValue;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
}

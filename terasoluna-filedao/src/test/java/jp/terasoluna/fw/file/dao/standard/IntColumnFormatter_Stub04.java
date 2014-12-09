package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * �����𕡐���get���\�b�h�����t�@�C���s�I�u�W�F�N�g�B
 */
@FileFormat()
public class IntColumnFormatter_Stub04 {

    @InputFileColumn(columnIndex = 0)
    @OutputFileColumn(columnIndex = 0)
    private int intValue;

    public int getIntValue(int a, String b) {
        return this.intValue;
    }
}

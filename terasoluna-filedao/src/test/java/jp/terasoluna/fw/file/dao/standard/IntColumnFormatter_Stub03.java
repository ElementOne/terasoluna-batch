package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * public��get���\�b�h����O���X���[����t�@�C���s�I�u�W�F�N�g�B
 */
@FileFormat()
public class IntColumnFormatter_Stub03 {

    @SuppressWarnings("unused")
    @InputFileColumn(columnIndex = 0)
    @OutputFileColumn(columnIndex = 0)
    private int intValue;

    public int getIntValue() {
        throw new UnsupportedOperationException("getIntValue()����̗�O�ł�");
    }
}

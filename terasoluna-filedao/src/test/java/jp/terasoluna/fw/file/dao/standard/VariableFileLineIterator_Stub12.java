package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u�N���X
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat(delimiter = ',', encloseChar = '\"', lineFeedChar = "\r")
 * <li>����
 * <ul>
 * <li>�Ȃ�
 * </ul>
 * </ul>
 */
@FileFormat(delimiter = ',', encloseChar = '\"', lineFeedChar = "\r")
public class VariableFileLineIterator_Stub12 {
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

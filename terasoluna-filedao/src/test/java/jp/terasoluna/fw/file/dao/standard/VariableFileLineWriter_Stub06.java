package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u�N���X
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat(encloseChar='\"')
 * <li>����
 * <ul>
 * <li>@OutputFileColumn(columnIndex = 0)<br>
 * String column01
 * </ul>
 * </ul>
 */
@FileFormat(encloseChar = '\"')
public class VariableFileLineWriter_Stub06 {

    @OutputFileColumn(columnIndex = 0)
    private String column01 = null;

    public String getColumn01() {
        return column01;
    }

    public void setColumn01(String column01) {
        this.column01 = column01;
    }
}

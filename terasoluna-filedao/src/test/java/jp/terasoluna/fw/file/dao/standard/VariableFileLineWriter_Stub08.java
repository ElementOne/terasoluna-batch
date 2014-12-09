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
 * <li>@OutputFileColumn(columnIndex = 1)<br>
 * String column02
 * </ul>
 * </ul>
 */
@FileFormat(encloseChar = '\"')
public class VariableFileLineWriter_Stub08 {

    @OutputFileColumn(columnIndex = 0)
    private String column01 = null;

    @OutputFileColumn(columnIndex = 1)
    private String column02 = null;

    public String getColumn01() {
        return column01;
    }

    public void setColumn01(String column01) {
        this.column01 = column01;
    }

    public String getColumn02() {
        return column02;
    }

    public void setColumn02(String column02) {
        this.column02 = column02;
    }

}

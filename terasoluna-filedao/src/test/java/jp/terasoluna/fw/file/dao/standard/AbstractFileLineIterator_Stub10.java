package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat(encloseChar='\"')
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex=0)<br>
 * String column1
 * <li>@InputFileColumn(columnIndex=1, bytes=5)<br>
 * String column2
 * </ul>
 * </ul>
 */
@FileFormat()
public class AbstractFileLineIterator_Stub10 {

    @InputFileColumn(columnIndex = 0)
    private String column1 = null;

    @InputFileColumn(columnIndex = 1, bytes = 5)
    private String column2 = null;

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

}

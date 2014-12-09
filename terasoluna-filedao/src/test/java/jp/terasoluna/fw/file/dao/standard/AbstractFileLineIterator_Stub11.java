package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat()
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex=1)<br>
 * String column2
 * <li>@InputFileColumn(columnIndex=0)<br>
 * int column1
 * </ul>
 * </ul>
 */
@FileFormat()
public class AbstractFileLineIterator_Stub11 {

    @InputFileColumn(columnIndex = 1)
    private String column2 = null;

    @InputFileColumn(columnIndex = 0)
    private int column1 = 0;

    public int getColumn1() {
        return column1;
    }

    public void setColumn1(int column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

}

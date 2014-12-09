package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u�N���X
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat()
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex = 1, bytes = 5)<br>
 * String column01
 * </ul>
 * <ul>
 * <li>@InputFileColumn(columnIndex = 1, bytes = 3)<br>
 * String column02
 * </ul>
 * </ul>
 */
@FileFormat(lineFeedChar = "\r\n")
public class FixedFileLineIterator_Stub02 {

    @InputFileColumn(columnIndex = 0, bytes = 5)
    private String column1 = null;

    @InputFileColumn(columnIndex = 1, bytes = 3)
    private String column2 = null;

    /**
     * @return column1 ��߂��܂��B
     */
    public String getColumn1() {
        return column1;
    }

    /**
     * @param column1 �ݒ肷�� column1�B
     */
    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    /**
     * @return column2 ��߂��܂��B
     */
    public String getColumn2() {
        return column2;
    }

    /**
     * @param column2 �ݒ肷�� column2�B
     */
    public void setColumn2(String column2) {
        this.column2 = column2;
    }
}

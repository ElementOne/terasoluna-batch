package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.StringConverterToUpperCase;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat()
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex = 0, stringConverter = StringConverterToUpperCase.class)<br>
 * String column1
 * <li>String noMappingColumn1
 * <li>@InputFileColumn(columnIndex = 1, stringConverter = StringConverterToUpperCase.class)<br>
 * String column2
 * </ul>
 * </ul>
 */
@FileFormat()
public class AbstractFileLineIterator_Stub44 {

    @InputFileColumn(columnIndex = 0, stringConverter = StringConverterToUpperCase.class)
    private String column1 = null;

    private String noMappingColumn1 = null;

    @InputFileColumn(columnIndex = 1, stringConverter = StringConverterToUpperCase.class)
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

    public String getNoMappingColumn1() {
        return noMappingColumn1;
    }

    public void setNoMappingColumn1(String noMappingColumn1) {
        this.noMappingColumn1 = noMappingColumn1;
    }

}

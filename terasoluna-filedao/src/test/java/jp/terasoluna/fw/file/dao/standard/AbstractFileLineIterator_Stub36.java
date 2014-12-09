package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u
 * <p>
 * AbstractFileLineIterator_Stub35���p������T�u�N���X�B<br>
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat()
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex=2)<br>
 * String noMappingColumn3
 * <li>String noMappingColumn3
 * </ul>
 * </ul>
 */
@FileFormat()
public class AbstractFileLineIterator_Stub36 extends
                                            AbstractFileLineIterator_Stub35 {

    @InputFileColumn(columnIndex = 2)
    private String column3 = null;

    private String noMappingColumn3 = null;

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getNoMappingColumn3() {
        return noMappingColumn3;
    }

    public void setNoMappingColumn3(String noMappingColumn3) {
        this.noMappingColumn3 = noMappingColumn3;
    }

}

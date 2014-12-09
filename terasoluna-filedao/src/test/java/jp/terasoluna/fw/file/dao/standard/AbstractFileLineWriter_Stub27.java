package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * AbstractFileLineWriter�̎����ŗ��p����t�@�C���s�I�u�W�F�N�g�̃X�^�u�N���X�B<br>
 * <br>
 * �ȉ���@FileFormat�̐ݒ������<br>
 * <ul>
 * <li>encloseChar�F'\"'</li>
 * <li>���̑��F�f�t�H���g�l</li>
 * </ul>
 * <br>
 * <code>@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
 * <ul>
 * <li>�t�B�[���h�FString column1
 *     <code>@OutputFileColumn</code>�ݒ�<br>
 * > columnIndex�F0<br>
 * > ���̑����ځF�f�t�H���g�l</li> </ul>
 * @author ���O
 */
@FileFormat(encloseChar = '\"')
public class AbstractFileLineWriter_Stub27 {

    /**
     * column1
     */
    @OutputFileColumn(columnIndex = 0)
    private String column1 = null;

    /**
     * column1���擾����B
     * @return column1
     */
    public String getColumn1() {
        return column1;
    }

    /**
     * column1��ݒ肷��B
     * @param column1 column1
     */
    public void setColumn1(String column1) {
        this.column1 = column1;
    }

}

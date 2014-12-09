package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * AbstractFileLineWriter�̎����ŗ��p����t�@�C���s�I�u�W�F�N�g�̃X�^�u�N���X�B<br>
 * <br>
 * �ȉ���@FileFormat�̐ݒ������<br>
 * <ul>
 * <li>�S���ځF�f�t�H���g�l</li>
 * </ul>
 * <br>
 * <code>@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
 * <ul>
 * <li>�t�B�[���h�FString column1
 *     <code>@OutputFileColumn</code>�ݒ�<br>
 * > columnIndex�F0<br>
 * > bytes�F48<br>
 * > ���̑����ځF�f�t�H���g�l</li> </ul>
 * <ul>
 * <li>�t�B�[���h�FString column2 <code>@OutputFileColumn</code>�ݒ�<br>
 * > columnIndex�F1<br>
 * > bytes�F0(��������0)<br>
 * > ���̑����ځF�f�t�H���g�l</li>
 * </ul>
 * <ul>
 * <li>�t�B�[���h�FString column3 <code>@OutputFileColumn</code>�ݒ�<br>
 * > columnIndex�F2<br>
 * > bytes�F48<br>
 * > ���̑����ځF�f�t�H���g�l</li>
 * </ul>
 * @author ���O
 */
@FileFormat()
public class AbstractFileLineWriter_Stub36 {

    /**
     * column1
     */
    @OutputFileColumn(columnIndex = 0, bytes = 48)
    private String column1 = null;

    /**
     * column2
     */
    @OutputFileColumn(columnIndex = 1, bytes = 0)
    private String column2 = null;

    /**
     * column3
     */
    @OutputFileColumn(columnIndex = 2, bytes = 48)
    private String column3 = null;

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

    /**
     * column2���擾����B
     * @return column2
     */
    public String getColumn2() {
        return column2;
    }

    /**
     * column2��ݒ肷��B
     * @param column2 column2
     */
    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    /**
     * column3���擾����B
     * @return column3
     */
    public String getColumn3() {
        return column3;
    }

    /**
     * column3��ݒ肷��B
     * @param column3 column3
     */
    public void setColumn3(String column3) {
        this.column3 = column3;
    }
}

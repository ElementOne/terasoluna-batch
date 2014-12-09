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
 * <code>@OutputFileColumn</code>�ݒ�Ȃ��̃t�B�[���h������<br>
 * <ul>
 * <li>�t�B�[���h�FString noMappingColumn1</li>
 * <li>�t�B�[���h�FString noMappingColumn2</li>
 * <li>�t�B�[���h�FString noMappingColumn3</li>
 * </ul>
 * <br>
 * <code>@OutputFileColumn�ݒ肠��̃t�B�[���h������<br>
 * <ul>
 * <li>�t�B�[���h�FString column1
 *     <code>@OutputFileColumn</code>�ݒ�<br>
 * > columnIndex�F1(0�����Ԃł���B)<br>
 * > ���̑����ځF�f�t�H���g�l</li> </ul>
 * <ul>
 * <li>�t�B�[���h�FString column2 <code>@OutputFileColumn</code>�ݒ�<br>
 * > columnIndex�F2(0�����Ԃł���B)<br>
 * > ���̑����ځF�f�t�H���g�l</li>
 * </ul>
 * @author ���O
 */
@FileFormat()
public class AbstractFileLineWriter_Stub18 {

    /**
     * noMappingColumn1
     */
    private String noMappingColumn1 = null;

    /**
     * noMappingColumn2
     */
    private String noMappingColumn2 = null;

    /**
     * noMappingColumn3
     */
    private String noMappingColumn3 = null;

    /**
     * column1
     */
    @OutputFileColumn(columnIndex = 1)
    private String column1 = null;

    /**
     * column2
     */
    @OutputFileColumn(columnIndex = 2)
    private String column2 = null;

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
     * noMappingColumn1���擾����B
     * @return noMappingColumn1
     */
    public String getNoMappingColumn1() {
        return noMappingColumn1;
    }

    /**
     * noMappingColumn1��ݒ肷��B
     * @param noMappingColumn1 noMappingColumn1
     */
    public void setNoMappingColumn1(String noMappingColumn1) {
        this.noMappingColumn1 = noMappingColumn1;
    }

    /**
     * noMappingColumn2���擾����B
     * @return noMappingColumn2
     */
    public String getNoMappingColumn2() {
        return noMappingColumn2;
    }

    /**
     * noMappingColumn2��ݒ肷��B
     * @param noMappingColumn2 noMappingColumn2
     */
    public void setNoMappingColumn2(String noMappingColumn2) {
        this.noMappingColumn2 = noMappingColumn2;
    }

    /**
     * noMappingColumn3���擾����B
     * @return noMappingColumn3
     */
    public String getNoMappingColumn3() {
        return noMappingColumn3;
    }

    /**
     * noMappingColumn3��ݒ肷��B
     * @param noMappingColumn3 noMappingColumn3
     */
    public void setNoMappingColumn3(String noMappingColumn3) {
        this.noMappingColumn3 = noMappingColumn3;
    }

}

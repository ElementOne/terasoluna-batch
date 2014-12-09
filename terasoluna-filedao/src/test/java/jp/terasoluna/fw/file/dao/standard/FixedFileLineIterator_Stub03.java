package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.StringConverterToUpperCase;
import jp.terasoluna.fw.file.annotation.TrimType;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u�N���X
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat()
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex = 0, bytes = 5, stringConverter = StringConverterToUpperCase.class, trimChar = '0', trimType
 * = TrimType.LEFT) String shopId
 * </ul>
 * </ul>
 */
@FileFormat(lineFeedChar = "\r\n")
public class FixedFileLineIterator_Stub03 {

    @InputFileColumn(columnIndex = 0, bytes = 5, stringConverter = StringConverterToUpperCase.class, trimChar = '0', trimType = TrimType.LEFT)
    private String shopId = null;

    /**
     * @return shopId ��߂��܂��B
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * @param shopId �ݒ肷�� shopId�B
     */
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

}

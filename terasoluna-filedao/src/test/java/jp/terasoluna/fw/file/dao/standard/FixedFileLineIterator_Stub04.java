package jp.terasoluna.fw.file.dao.standard;

import java.math.BigDecimal;
import java.util.Date;

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
 * <li>@InputFileColumn(columnIndex = 0, bytes = 10, columnFormat = "yyyy/MM/dd") Date hiduke
 * </ul>
 * <ul>
 * <li>@InputFileColumn(columnIndex = 1, bytes = 5, stringConverter = StringConverterToUpperCase.class, trimChar = '0', trimType
 * = TrimType.LEFT) String shopId
 * </ul>
 * <ul>
 * <li>@InputFileColumn(columnIndex = 2, bytes = 9, columnFormat = "#########") BigDecimal uriage
 * </ul>
 * </ul>
 */
@FileFormat(lineFeedChar = "\r\n")
public class FixedFileLineIterator_Stub04 {

    @InputFileColumn(columnIndex = 0, bytes = 10, columnFormat = "yyyy/MM/dd")
    private Date hiduke = null;

    @InputFileColumn(columnIndex = 1, bytes = 5, stringConverter = StringConverterToUpperCase.class, trimChar = '0', trimType = TrimType.LEFT)
    private String shopId = null;

    @InputFileColumn(columnIndex = 2, bytes = 9, columnFormat = "#########")
    private BigDecimal uriage = null;

    /**
     * @return hiduke ��߂��܂��B
     */
    public Date getHiduke() {
        return hiduke;
    }

    /**
     * @param hiduke �ݒ肷�� hiduke�B
     */
    public void setHiduke(Date hiduke) {
        this.hiduke = hiduke;
    }

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

    /**
     * @return uriage ��߂��܂��B
     */
    public BigDecimal getUriage() {
        return uriage;
    }

    /**
     * @param uriage �ݒ肷�� uriage�B
     */
    public void setUriage(BigDecimal uriage) {
        this.uriage = uriage;
    }
}

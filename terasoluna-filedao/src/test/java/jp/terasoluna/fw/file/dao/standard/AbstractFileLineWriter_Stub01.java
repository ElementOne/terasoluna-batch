package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * AbstractFileLineWriter�̎����ŗ��p����t�@�C���s�I�u�W�F�N�g�̃X�^�u�N���X�B<br>
 * <br>
 * �ȉ���@FileFormat�̐ݒ������<br>
 * <ul>
 * <li>delimiter�F"|"(�f�t�H���g�l�ȊO)</li>
 * <li>encloseChar�F"\""(�f�t�H���g�l�ȊO)</li>
 * <li>lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)</li>
 * <li>fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)</li>
 * <li>overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)</li>
 * </ul>
 * <br>
 * �t�B�[���h�͎����Ȃ�<br>
 * @author ���O
 */
@FileFormat(delimiter = '|', encloseChar = '\"', lineFeedChar = "\r", fileEncoding = "UTF-8", overWriteFlg = true)
public class AbstractFileLineWriter_Stub01 {
    @OutputFileColumn(columnIndex = 0)
    private String dummy;

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}

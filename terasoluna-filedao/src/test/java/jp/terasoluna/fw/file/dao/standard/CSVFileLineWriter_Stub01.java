package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����������A�����������Ȃ��t�@�C���s�I�u�W�F�N�g�X�^�u
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat(delimiter�ȊO�̐ݒ�̓f�t�H���g�l�ł͂Ȃ�)
 */
@FileFormat(lineFeedChar = "\r", encloseChar = '\"', fileEncoding = "UTF-8", headerLineCount = 1, trailerLineCount = 1, overWriteFlg = true)
public class CSVFileLineWriter_Stub01 {
    @InputFileColumn(columnIndex = 0)
    @OutputFileColumn(columnIndex = 0)
    private String dummy;

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}

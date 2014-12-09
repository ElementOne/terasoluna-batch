package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;

/**
 * AbstractFileLineWriter�̎����ŗ��p����t�@�C���s�I�u�W�F�N�g�̃X�^�u�N���X�B<br>
 * <br>
 * �ȉ���@FileFormat�̐ݒ������<br>
 * <ul>
 * <li>delimiter�F";"(encloseChar�Ɠ����l)</li>
 * <li>encloseChar�F";"(delimiter�Ɠ����l)</li>
 * <li>lineFeedChar�F"\r"(�f�t�H���g�l�ȊO)</li>
 * <li>fileEncoding�F"UTF-8"(�f�t�H���g�l�ȊO)</li>
 * <li>overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)</li>
 * </ul>
 * <br>
 * �t�B�[���h�͎����Ȃ�<br>
 * @author ���O
 */
@FileFormat(delimiter = ';', encloseChar = ';', lineFeedChar = "\r", fileEncoding = "UTF-8", overWriteFlg = true)
public class AbstractFileLineWriter_Stub04 {

}

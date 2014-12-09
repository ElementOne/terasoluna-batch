package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;

/**
 * AbstractFileLineWriter�̎����ŗ��p����t�@�C���s�I�u�W�F�N�g�̃X�^�u�N���X�B<br>
 * <br>
 * �ȉ���@FileFormat�̐ݒ������<br>
 * <ul>
 * <li>delimiter�F"|"(�f�t�H���g�l�ȊO)</li>
 * <li>encloseChar�F"\""(�f�t�H���g�l�ȊO)</li>
 * <li>lineFeedChar�F""(�󕶎��A�f�t�H���g�l)</li>
 * <li>fileEncoding�F""(�󕶎��A�f�t�H���g�l)</li>
 * <li>overWriteFlg�Ftrue(�f�t�H���g�l�ȊO)</li>
 * </ul>
 * <br>
 * �t�B�[���h�͎����Ȃ�<br>
 * @author ���O
 */
@FileFormat(delimiter = '|', encloseChar = '\"', lineFeedChar = "", fileEncoding = "", overWriteFlg = true)
public class AbstractFileLineWriter_Stub03 {

}

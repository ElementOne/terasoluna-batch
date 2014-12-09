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
 * > ���̑����ځF�f�t�H���g�l</li> </ul> �e�t�B�[���h��getter/setter���\�b�h�������Ȃ��B
 * @author ���O
 */
@FileFormat()
public class AbstractFileLineWriter_Stub25 {

    /**
     * column1
     */
    @SuppressWarnings("unused")
    @OutputFileColumn(columnIndex = 0)
    private String column1 = null;

}

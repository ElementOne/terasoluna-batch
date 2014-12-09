package jp.terasoluna.fw.file.dao.standard;

import java.util.Map;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.dao.standard.AbstractFileLineWriter;
import jp.terasoluna.fw.file.dao.standard.ColumnFormatter;

/**
 * AbstractFileLineWriter��Impl�N���X�B<br>
 * �����<br>
 * �u��؂蕶���v�Ɓu�͂ݕ����v��ݒ�ł���B<br>
 * �R���X�g���N�^��init()�����s���Ă��Ȃ����߁A�R���X�g���N�^�ȊO�̎����ł�<br>
 * �C���X�^���X������A�K��init()�����s���Ă��痘�p���邱�ƁB
 * @author ���O
 * @param <T> �t�@�C���s�I�u�W�F�N�g
 */
public class AbstractFileLineWriterImpl01<T> extends AbstractFileLineWriter<T> {

    /**
     * ��؂蕶��
     */
    private char delimiter = Character.MIN_VALUE;

    /**
     * �͂ݕ���
     */
    private char encloseChar = Character.MIN_VALUE;

    /**
     * �R���X�g���N�^�B
     * @param fileName �t�@�C����
     * @param clazz �p�����[�^�N���X
     * @param columnFormatterMap �e�L�X�g�擾���[��
     */
    public AbstractFileLineWriterImpl01(String fileName, Class<T> clazz,
            Map<String, ColumnFormatter> columnFormatterMap) {
        super(fileName, clazz, columnFormatterMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);
        this.delimiter = fileFormat.delimiter();
        this.encloseChar = fileFormat.encloseChar();
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�ɃA�m�e�[�V�������ݒ肳��Ă��鎖���`�F�b�N���邩�ǂ�����Ԃ��B
     * @return �`�F�b�N���s���ꍇ��true�B
     */
    @Override
    protected boolean isCheckColumnAnnotationCount() {
        return false;
    }

    /**
     * delimiter���擾����B
     * @return delimiter
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * delimiter��ݒ肷��B
     * @param delimiter delimiter
     */
    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * encloseChar���擾����B
     * @return encloseChar
     */
    public char getEncloseChar() {
        return encloseChar;
    }

    /**
     * encloseChar��ݒ肷��B
     * @param encloseChar encloseChar
     */
    public void setEncloseChar(char encloseChar) {
        this.encloseChar = encloseChar;
    }
}

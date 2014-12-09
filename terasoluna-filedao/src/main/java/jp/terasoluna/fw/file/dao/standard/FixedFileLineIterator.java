/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.file.dao.standard;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * �Œ蒷�t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^�擾)�N���X�B
 * <p>
 * �Œ蒷�t�@�C������f�[�^��ǂݍ��݁A 1�s���̃f�[�^���t�@�C���s�I�u�W�F�N�g�Ɋi�[����B
 * </p>
 * <b>�����p����t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V��������</b><br>
 * �@�D@{@link FileFormat}�̐ݒ荀��<br>
 * <div align="center">
 * <table width="90%" border="1" bgcolor="#FFFFFF">
 * <tr>
 * <td><b>�_�����ږ�</b></td>
 * <td><b>�������ږ�</b></td>
 * <td><b>�f�t�H���g�l</b></td>
 * <td><b>�K�{��</b></td>
 * </tr>
 * <tr>
 * <td> <code>�s��؂蕶��</code></td>
 * <td> <code>lineFeedChar</code></td>
 * <td> <code>�V�X�e���̍s��؂蕶��</code></td>
 * <td> <code>�I�v�V����</code></td>
 * <tr>
 * <tr>
 * <td> <code>�t�@�C���G���R�[�f�B���O</code></td>
 * <td> <code>fileEncodeing</code></td>
 * <td> <code>�V�X�e���̃t�@�C���G���R�[�f�B���O</code></td>
 * <td> <code>�I�v�V����</code></td>
 * <tr>
 * <tr>
 * <td> <code>�w�b�_�s��</code></td>
 * <td> <code>headerLineCount</code></td>
 * <td> <code>0</code></td>
 * <td> <code>�I�v�V����</code></td>
 * <tr>
 * <tr>
 * <td> <code>�g���C���s��</code></td>
 * <td> <code>trailerLineCount</code></td>
 * <td> <code>0</code></td>
 * <td> <code>�I�v�V����</code></td>
 * <tr>
 * </table>
 * </div> <br>
 * �A�D@{@link InputFileColumn}�A@{@link jp.terasoluna.fw.file.annotation.OutputFileColumn}�̐ݒ荀��<br>
 * <div align="center">
 * <table width="90%" border="1" bgcolor="#FFFFFF">
 * <tr>
 * <td><b>�_�����ږ�</b></td>
 * <td><b>�������ږ�</b></td>
 * <td><b>�f�t�H���g�l</b></td>
 * <td><b>�K�{��</b></td>
 * </tr>
 * <tr>
 * <td> <code>�J�����C���f�b�N�X</code></td>
 * <td> <code>columnIndex</code></td>
 * <td>-</td>
 * <td> <code>�K�{</code></td>
 * </tr>
 * <tr>
 * <td> <code>�t�H�[�}�b�g</code></td>
 * <td> <code>columnFormat</code></td>
 * <td> <code>""</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�o�C�g��</code></td>
 * <td> <code>bytes</code></td>
 * <td> <code>-1</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�p�f�B���O���</code></td>
 * <td> <code>paddingType</code></td>
 * <td> <code>�p�f�B���O�Ȃ�</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�p�f�B���O����</code></td>
 * <td> <code>paddingChar</code></td>
 * <td> <code>' '</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�g�������</code></td>
 * <td> <code>trimType</code></td>
 * <td> <code>�g�����Ȃ�</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�g��������</code></td>
 * <td> <code>trimChar</code></td>
 * <td> <code>' '</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�����ϊ����</code></td>
 * <td> <code>stringConverter</code></td>
 * <td> <code>NullStringConverter.class</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * </table>
 * </div> <br>
 * <b>�����ӎ���</b><br>
 * <ul>
 * �@
 * <li>��؂蕶����ݒ肷�邱�Ƃ͏o���Ȃ��B(�G���[����)</li> �@
 * <li>�͂ݕ�����ݒ肷�邱�Ƃ͏o���Ȃ��B(�G���[����)</li>
 * </ul>
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public class FixedFileLineIterator<T> extends AbstractFileLineIterator<T> {

    /**
     * ��؂蕶���B�Œ蒷�t�@�C���́u,(�J���})�v�ŌŒ�B
     */
    private static final char DELIMITER = ',';

    /**
     * �͂ݕ����B�Œ蒷�t�@�C���́u'\u0000'�v�ŌŒ�B
     */
    private static final char ENCLOSE_CHAR = Character.MIN_VALUE;

    /**
     * �R���X�g���N�^�B
     * <p>
     * ��؂蕶���������l�ȊO�̏ꍇ�A �͂ݕ����������l�ȊO�̏ꍇ��<code>FileException</code>���X���[�����B
     * @param fileName �t�@�C����
     * @param clazz ���ʃN���X
     * @param columnParserMap �t�H�[�}�b�g�������X�g
     */
    public FixedFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> columnParserMap) {

        super(fileName, clazz, columnParserMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // ��؂蕶���������l�ȊO�̏ꍇ�A��O���X���[����B
        if (fileFormat.delimiter() != DELIMITER) {
            throw new FileException("Delimiter can not change.",
                    new IllegalStateException(), fileName);
        }

        // �͂ݕ����������l�ȊO�̏ꍇ�A��O���X���[����B
        if (fileFormat.encloseChar() != ENCLOSE_CHAR) {
            throw new FileException("EncloseChar can not change.",
                    new IllegalStateException(), fileName);
        }

        // �s��؂蕶���������ꍇ�A�w�b�_�E�g���C���͗��p�s�Ȃ̂ŗ�O���X���[����B
        if ("".equals(fileFormat.lineFeedChar())
                && (fileFormat.headerLineCount() > 0 || fileFormat
                        .trailerLineCount() > 0)) {
            throw new FileException("HeaderLineCount or trailerLineCount cannot be used.",
                    new IllegalStateException(), fileName);
        }
        
        // �s��؂蕶����ݒ肷��B
        // �Œ蒷�̏ꍇ�̂݉��s�����������邽�߁Asuper�N���X�ł̐ݒ���㏑������B
        setLineFeedChar(fileFormat.lineFeedChar());

        // �������������s���B
        super.init();
    }

    /**
     * �ǂݍ��񂾌Œ蒷�̃��R�[�h���A�m�e�[�V������byte���A columnIndex�ɏ]���ĕ�������B<br>
     * ����<code>fileLineString</code>��<code>null</code>�������� �󕶎��̏ꍇ�́A�v�f�������Ȃ�<code>String</code>�z���Ԃ��܂��B
     * <p>
     * �����̏����́A<br>
     * <ul>
     * <li>�t�@�C���s�I�u�W�F�N�g�Œ�`�����o�C�g���̍��v�ƃt�@�C������ǂݎ����1�s������̃o�C�g�����r����</li>
     * <li>�t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����Œ�`����bytes�����̕�����𐶐�����</li>
     * </ul>
     * <p>
     * @param fileLineString �Œ蒷�t�@�C����1���R�[�h���̕�����
     * @return �f�[�^���P�s�̕�����𕪉����������z��
     */
    protected String[] separateColumns(String fileLineString) {

        // ���R�[�h������null���󕶎��̏ꍇ�͗v�f�O�̔z���ԋp
        if (fileLineString == null || "".equals(fileLineString)) {
            return new String[0];
        }

        int[] columnBytes = getColumnBytes();
        String[] results = new String[columnBytes.length];

        // �t�@�C���s�I�u�W�F�N�g�Œ�`�����o�C�g���̍��v�ƃt�@�C������ǂݎ����1�s������̃o�C�g�����r����
        try {
            // �t�@�C������ǂݎ����1�s������̃o�C�g���ƁA�t�@�C���s�I�u�W�F�N�g��
            // ��`�����o�C�g���̍��v���r����B
            byte[] bytes = fileLineString.getBytes(getFileEncoding());
            if (getTotalBytes() != bytes.length) {
                throw new FileException("Total Columns byte is different "
                        + "from Total FileLineObject's columns byte.",
                        new IllegalStateException(), this.getFileName());
            }

            // �@�t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����Œ�`����bytes�����̕�����𐶐�����
            int byteIndex = 0;

            for (int i = 0; i < columnBytes.length; i++) {
                results[i] = new String(bytes, byteIndex, columnBytes[i],
                        getFileEncoding());
                byteIndex += columnBytes[i];
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileException(
                    "fileEncoding which isn't supported was set.", e, this
                            .getFileName());
        }

        return results;
    }

    /**
     * �ΏۃJ�����ɑ΂���o�C�g���`�F�b�N���s������Ԃ��B<br>
     * FixedFileLineIterator�͌Œ蒷�̂��߁A�s�P�ʂŃo�C�g���`�F�b�N���s���B<br>
     * ���̂��߃J�����ɑ΂���o�C�g���`�F�b�N�͍s��Ȃ��B
     * @param inputFileColumn �ΏۃJ������InputFileColumn���
     * @return false
     */
    @Override
    protected boolean isCheckByte(InputFileColumn inputFileColumn) {
        return false;
    }

    /**
     * �ΏۃJ�����ɑ΂���o�C�g���`�F�b�N���s������Ԃ��B<br>
     * FixedFileLineIterator�͌Œ蒷�̂��߁A�s�P�ʂŃo�C�g���`�F�b�N���s���B<br>
     * ���̂��߃J�����ɑ΂���o�C�g���`�F�b�N�͍s��Ȃ��B
     * @param columnByte �ΏۃJ�����̃o�C�g��
     * @return false
     */
    @Override
    protected boolean isCheckByte(int columnByte) {
        return false;
    }

    /**
     * �͂ݕ������ݒ肳��Ă��Ȃ������`�F�b�N���邩��Ԃ��B<br>
     * FixedFileLineIterator�͌Œ蒷�̂��߁A�͂ݕ����͐ݒ肵�Ȃ��B<br>
     * ���̂��߈͂ݕ������ݒ肳��Ă��Ȃ������`�F�b�N����B
     * @return �`�F�b�N���s���ꍇ��true�B
     */
    @Override
    protected boolean isCheckEncloseChar() {
        return true;
    }

    /**
     * ��؂蕶�����擾����B<br>
     * �Œ蒷�t�@�C���́u,(�J���})�v�ŌŒ�B
     * @return �s��؂蕶��
     */
    @Override
    public char getDelimiter() {

        return DELIMITER;
    }

    /**
     * �͂ݕ������擾����B<br>
     * �Œ蒷�t�@�C���́u'\u0000'�v�ŌŒ�B
     * @return �͂ݕ���
     */
    @Override
    public char getEncloseChar() {

        return ENCLOSE_CHAR;
    }
}

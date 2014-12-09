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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * �ϒ��t�@�C���t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^�擾)�N���X�B
 * <p>
 * �ϒ��t�@�C������f�[�^��ǂݍ��݁A1�s���̃f�[�^���t�@�C���s�I�u�W�F�N�g�� �i�[����B<br>
 * CSV�t�@�C���ł͋�؂蕶�����J���}�ŌŒ肳��Ă��邪�A�ϒ��t�@�C���ł� �J���}�ȊO�𗘗p���邱�Ƃ��\�B
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
 * </tr>
 * <tr>
 * <td> <code>��؂蕶��</code></td>
 * <td> <code>delimiter</code></td>
 * <td> <code>','</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�͂ݕ���</code></td>
 * <td> <code>encloseChar</code></td>
 * <td> <code>�Ȃ�('\u0000')</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�t�@�C���G���R�[�f�B���O</code></td>
 * <td> <code>fileEncodeing</code></td>
 * <td> <code>�V�X�e���̃t�@�C���G���R�[�f�B���O</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�w�b�_�s��</code></td>
 * <td> <code>headerLineCount</code></td>
 * <td> <code>0</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * <tr>
 * <td> <code>�g���C���s��</code></td>
 * <td> <code>trailerLineCount</code></td>
 * <td> <code>0</code></td>
 * <td> <code>�I�v�V����</code></td>
 * </tr>
 * </table>
 * </div> <br>
 * �A�D@{@link jp.terasoluna.fw.file.annotation.InputFileColumn}�A@{@link jp.terasoluna.fw.file.annotation.OutputFileColumn}�̐ݒ荀��<br>
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
 * <li>��؂蕶����Caracter.MIN_VALUE��ݒ肷�邱�Ƃ͏o���Ȃ��B(�G���[����)</li>
 * </ul>
 * @param <T> �t�@�C���s�I�u�W�F�N�g
 */
public class VariableFileLineIterator<T> extends AbstractFileLineIterator<T> {

    /**
     * ��؂蕶���B
     */
    private char delimiter = ',';

    /**
     * �͂ݕ����B
     */
    private char encloseChar = Character.MIN_VALUE;

    /**
     * �R���X�g���N�^�B
     * @param fileName �t�@�C����
     * @param clazz �t�@�C���s�I�u�W�F�N�g�N���X
     * @param columnParserMap �e�L�X�g�ݒ胋�[��
     */
    public VariableFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> columnParserMap) {

        super(fileName, clazz, columnParserMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // ��؂蕶����Character.MIN_VALUE�̏ꍇ�A��O���X���[����B
        if (fileFormat.delimiter() == Character.MIN_VALUE) {
            throw new FileException("Delimiter can not use '\\u0000'.",
                    new IllegalStateException(), fileName);
        }

        // ���s�������ɋ�؂蕶�����܂܂�Ă���ꍇ�A��O���X���[����B
        if (fileFormat.lineFeedChar().indexOf(fileFormat.delimiter()) >= 0) {
            throw new FileException(
                    "delimiter is the same as lineFeedChar and is no use.",
                    new IllegalStateException(), fileName);
        }

        // �͂ݕ�����ݒ肷��B
        this.encloseChar = fileFormat.encloseChar();

        // ��؂蕶����ݒ肷��B
        this.delimiter = fileFormat.delimiter();

        // �������������s���B
        super.init();
    }

    /**
     * �ǂݍ��񂾃t�@�C���̃��R�[�h���A��؂蕶���A �͂ݕ����ɏ]���� �����z��ɕϊ�����B<br>
     * ����<code>fileLineString</code>��<code>null</code>�������� �󕶎��̏ꍇ�́A�v�f�������Ȃ�<code>String</code>�z���Ԃ��܂��B
     * @param fileLineString �ϒ��t�@�C����1���R�[�h���̕�����
     * @return �����z��
     */
    protected String[] separateColumns(String fileLineString) {

        if (fileLineString == null || "".equals(fileLineString)) {
            return new String[0];
        }

        // 1�J�������̕�������i�[���镶���V�[�P���X
        StringBuilder columnBuilder = new StringBuilder();

        // �`�F�b�N�Ώە����̒��O�̕���
        char previousChar = Character.MIN_VALUE;

        // ��������i�[���邽�߂̔z��
        List<String> columnList = new ArrayList<String>();

        boolean isEnclosed = true;
        boolean isEscaped = false;

        int fieldCount = 0;
        char[] columnEncloseChar = getColumnEncloseChar();

        if (!isEnclosed()) {
            return fileLineString.split(Character.toString(delimiter), -1);
        } else {
            for (char currentChar : fileLineString.toCharArray()) {
                if (previousChar == Character.MIN_VALUE) {
                    previousChar = currentChar;
                }
                if (previousChar == getEncloseCharcter(columnEncloseChar,
                        fieldCount)) {
                    if (isEnclosed) {
                        if (currentChar == getEncloseCharcter(
                                columnEncloseChar, fieldCount)) {
                            isEnclosed = false;
                        }
                    } else {
                        if (currentChar == getEncloseCharcter(
                                columnEncloseChar, fieldCount)) {
                            if (isEscaped) {
                                columnBuilder.append(currentChar);
                                isEscaped = false;
                            } else {
                                isEscaped = true;
                            }
                        } else if (currentChar == getDelimiter()) {
                            if (isEscaped) {
                                columnList.add(columnBuilder.toString());
                                previousChar = Character.MIN_VALUE;
                                columnBuilder.delete(0, columnBuilder.length());
                                isEnclosed = true;
                                isEscaped = false;
                                fieldCount++;
                            } else {
                                columnBuilder.append(currentChar);
                                isEscaped = false;
                            }
                        } else {
                            columnBuilder.append(currentChar);
                        }
                    }
                } else {
                    if (currentChar != getDelimiter()) {
                        columnBuilder.append(currentChar);
                    } else {
                        columnList.add(columnBuilder.toString());
                        previousChar = Character.MIN_VALUE;
                        columnBuilder.delete(0, columnBuilder.length());
                        fieldCount++;
                    }
                }
            }
            columnList.add(columnBuilder.toString());
            return columnList.toArray(new String[columnList.size()]);
        }
    }

    /**
     * �J�����ɑΉ�����͂ݕ������擾����B
     * @param index �J�����̃C���f�b�N�X
     * @return �͂ݕ���
     */
    private char getEncloseCharcter(char[] columnEncloseChar, int index) {
        if (columnEncloseChar.length == 0 || index >= columnEncloseChar.length) {
            return this.encloseChar;
        } else {
            return columnEncloseChar[index];
        }
    }

    /**
     * ��؂蕶�����擾����B
     * @return ��؂蕶��
     */
    @Override
    public char getDelimiter() {

        return delimiter;
    }

    /**
     * �͂ݕ������擾����B
     * @return �͂ݕ���
     */
    @Override
    public char getEncloseChar() {

        return encloseChar;
    }
}

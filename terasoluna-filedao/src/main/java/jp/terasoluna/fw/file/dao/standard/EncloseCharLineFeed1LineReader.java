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

import java.io.IOException;
import java.io.Reader;

import jp.terasoluna.fw.file.dao.FileException;

/**
 * �t�@�C������f�[�^���̃f�[�^��1�s���ǂݎ��A������Ƃ��Čďo���ɕԋp����B
 */
public class EncloseCharLineFeed1LineReader implements LineReader {

    /**
     * ��؂蕶���B
     */
    private char delimiterCharacter = Character.MIN_VALUE;

    /**
     * �͂ݕ����B
     */
    private char encloseCharacter = Character.MIN_VALUE;

    /**
     * �J�������Ƃ̈͂ݕ����B
     */
    private char[] columnEncloseCharacter = null;

    /**
     * �t�@�C���A�N�Z�X�p�̕����X�g���[���B
     */
    private Reader reader = null;

    /**
     * �s��؂蕶���B
     */
    private String lineFeedChar = null;

    /**
     * �R���X�g���N�^�B
     * @param delimiterCharacter ��؂蕶��
     * @param encloseCharacter �͂ݕ���
     * @param columnEncloseCharacter �J�������Ƃ̈͂ݕ���
     * @param reader �t�@�C���A�N�Z�X�p�̕����X�g���[��
     * @param lineFeedChar �s��؂蕶��
     * @throws IllegalArgumentException �����̐ݒ肪�Ԉ�����ꍇ�B
     */
    public EncloseCharLineFeed1LineReader(char delimiterCharacter,
            char encloseCharacter, char[] columnEncloseCharacter,
            Reader reader, String lineFeedChar) {

        if (delimiterCharacter == Character.MIN_VALUE) {
            throw new IllegalArgumentException(
                    "delimiterCharacter can not use '\\u0000'.");
        }

        if (columnEncloseCharacter == null) {
            throw new IllegalArgumentException(
                    "columnEncloseCharacter is required.");
        }

        if (reader == null) {
            throw new IllegalArgumentException("reader is required.");
        }

        if (lineFeedChar == null) {
            throw new IllegalArgumentException("lineFeedChar is required.");
        }

        if (lineFeedChar.length() != 1) {
            throw new IllegalArgumentException("lineFeedChar should be defined"
                    + " by 1 digit of character string.");
        }

        this.delimiterCharacter = delimiterCharacter;
        this.encloseCharacter = encloseCharacter;
        this.columnEncloseCharacter = columnEncloseCharacter;
        this.reader = reader;
        this.lineFeedChar = lineFeedChar;
    }

    /**
     * �t�@�C������f�[�^���̃f�[�^��1�s���ǂݎ��A������Ƃ��Čďo���ɕԋp����B
     * @return �f�[�^���̂P�s���̕�����
     * @throws FileException Reader�̏����ŗ�O�����������ꍇ�B
     */
    public String readLine() {
        // 1�J�������̕�������i�[����o�b�t�@
        StringBuilder currentLineStringBuilder = new StringBuilder();

        // �`�F�b�N�Ώە����̒��O�̕���
        char previousChar = Character.MIN_VALUE;

        // �`�F�b�N�Ώە���
        char currentChar = Character.MIN_VALUE;

        // �͂ݕ������I�����Ă��邩�m�F����t���O�Btrue�Ȃ�J�����͈͂ݕ����ň͂܂�Ă���B
        boolean isEnclosed = true;

        // �G�X�P�[�v�V�[�P���X��ǂݍ��񂾂�true�ɁB����ȊO�̏ꍇ��false�B
        boolean isEscape = false;

        // �s��؂蕶����1�����ځB
        char lineFeedChar1 = lineFeedChar.charAt(0);

        int chr = 0;
        int fieldCount = 0;
        try {
            while ((chr = reader.read()) != -1) {
                currentChar = (char) chr;
                if (previousChar == Character.MIN_VALUE) {
                    previousChar = currentChar;
                }
                if (previousChar == getEncloseCharcter(fieldCount)) {
                    if (isEnclosed) {
                        if (currentChar == getEncloseCharcter(fieldCount)) {
                            isEnclosed = false;
                        }
                        currentLineStringBuilder.append(currentChar);
                    } else {
                        if (currentChar == getEncloseCharcter(fieldCount)
                                && !isEscape) {
                            isEscape = true;
                        } else if (currentChar == getEncloseCharcter(fieldCount)
                                && isEscape) {
                            isEscape = false;
                            currentLineStringBuilder
                                    .append(getEncloseCharcter(fieldCount));
                            currentLineStringBuilder.append(currentChar);
                        } else if (currentChar == delimiterCharacter) {
                            if (isEscape) {
                                currentLineStringBuilder
                                        .append(getEncloseCharcter(fieldCount));
                                previousChar = Character.MIN_VALUE;
                                isEnclosed = true;
                                isEscape = false;
                                fieldCount++;
                            }
                            currentLineStringBuilder.append(currentChar);
                        } else if (currentChar == lineFeedChar1) {
                            if (isEscape) {
                                currentLineStringBuilder
                                        .append(getEncloseCharcter(fieldCount));
                                previousChar = Character.MIN_VALUE;
                                isEnclosed = true;
                                isEscape = false;
                                break;
                            } else {
                                currentLineStringBuilder.append(currentChar);
                            }
                        } else {
                            if (isEscape) {
                                currentLineStringBuilder
                                        .append(getEncloseCharcter(fieldCount));
                                previousChar = Character.MIN_VALUE;
                                isEnclosed = true;
                                isEscape = false;
                            }
                            currentLineStringBuilder.append(currentChar);
                        }
                    }
                } else if (previousChar == lineFeedChar1) {
                    previousChar = Character.MIN_VALUE;
                    isEnclosed = true;
                    isEscape = false;
                    break;
                } else {
                    if (currentChar == delimiterCharacter) {
                        fieldCount++;
                        currentLineStringBuilder.append(currentChar);
                        previousChar = Character.MIN_VALUE;
                        isEnclosed = true;
                        isEscape = false;
                    } else if (currentChar == lineFeedChar1) {
                        previousChar = Character.MIN_VALUE;
                        isEnclosed = true;
                        isEscape = false;
                        break;
                    } else {
                        currentLineStringBuilder.append(currentChar);
                    }
                }
            }
        } catch (IOException e) {
            throw new FileException("Reader control operation was failed.", e);
        }

        return currentLineStringBuilder.toString();
    }

    /**
     * �J�����ɑΉ�����͂ݕ������擾����B
     * @param index �J�����̃C���f�b�N�X
     * @return �͂ݕ���
     */
    private char getEncloseCharcter(int index) {
        if (columnEncloseCharacter.length == 0
                || index >= columnEncloseCharacter.length) {
            return encloseCharacter;
        } else {
            return columnEncloseCharacter[index];
        }
    }
}

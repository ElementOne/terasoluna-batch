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
public class LineFeed2LineReader implements LineReader {

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
     * @param reader �t�@�C���A�N�Z�X�p�̕����X�g���[��
     * @param lineFeedChar �s��؂蕶��
     * @throws IllegalArgumentException �����̐ݒ肪�Ԉ�����ꍇ�B
     */
    public LineFeed2LineReader(Reader reader, String lineFeedChar) {

        if (reader == null) {
            throw new IllegalArgumentException("reader is required.");
        }

        if (lineFeedChar == null) {
            throw new IllegalArgumentException("lineFeedChar is required.");
        }

        if (lineFeedChar.length() != 2) {
            throw new IllegalArgumentException("lineFeedChar should be defined"
                    + " by 2 digit of character string.");
        }

        this.reader = reader;
        this.lineFeedChar = lineFeedChar;
    }

    /**
     * �t�@�C������f�[�^���̃f�[�^��1�s���ǂݎ��A������Ƃ��Čďo���ɕԋp����B
     * @return �f�[�^���̂P�s���̕�����
     * @throws FileException Reader�̏����ŗ�O�����������ꍇ�B
     */
    public String readLine() {
        StringBuilder currentLineStringBuilder = new StringBuilder();

        // �`�F�b�N�Ώە����̒��O�̕���
        char previousChar = Character.MIN_VALUE;

        // �`�F�b�N�Ώە���
        char currentChar = Character.MIN_VALUE;

        // �s��؂蕶����1�����ځB
        char lineFeedChar1 = lineFeedChar.charAt(0);

        // �s��؂蕶����2�����ځB
        char lineFeedChar2 = lineFeedChar.charAt(1);

        int chr = 0;
        try {
            while ((chr = reader.read()) != -1) {
                currentChar = (char) chr;
                if (currentChar == lineFeedChar2) {
                    if (previousChar == lineFeedChar1) {
                        currentLineStringBuilder.delete(
                                (currentLineStringBuilder.length() - 1),
                                (currentLineStringBuilder.length()));
                        break;
                    }
                }
                previousChar = currentChar;
                currentLineStringBuilder.append(currentChar);
            }
        } catch (IOException e) {
            throw new FileException("Reader control operation was failed.", e);
        }
        return currentLineStringBuilder.toString();
    }

}

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
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.annotation.TrimType;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * FileDAO�p�̃��[�e�B���e�B�B
 * <p>
 * �p�f�B���O�����A�g����������񋟂���B
 * </p>
 */
public class FileDAOUtility {

    /**
     * �t�@�C���G���R�[�f�B���O�̃L���b�V���B
     */
    private static final Map<String, Map<Character, Boolean>> encodingCache = new ConcurrentHashMap<String, Map<Character, Boolean>>();

    /**
     * �p�f�B���O�����B<br>
     * <br>
     * �J�����̕�������A�m�e�[�V�����Ŏw�肳�ꂽ�����Ńp�f�B���O����B<br>
     * ������ɒǉ������̂́A�p�f�B���O�����upaddingChar�v�Ŏw�肵�������B<br>
     * �p�f�B���O�����͔��p1�����ł���̂ŁA�S�p���������͂��ꂽ�ꍇ�͓��̓G���[�ƂȂ�B
     * @param columnString �p�f�B���O�����O�̂P�J�������̕�����
     * @param fileEncoding �t�@�C���G���R�[�f�B���O
     * @param columnBytes �p�f�B���O�������1�J�����̃o�C�g��
     * @param paddingChar �p�f�B���O����
     * @param paddingType �p�f�B���O�^�C�v
     * @return �p�f�B���O�����ς̂P�J�������̕�����
     */
    public static String padding(String columnString, String fileEncoding,
            int columnBytes, char paddingChar, PaddingType paddingType) {

        // NONE�̂Ƃ��͂��̂܂ܕ������ԋp����
        if (PaddingType.NONE.equals(paddingType)) {
            return columnString;
        }

        // ���p�����̔���
        if (!isHalfWidthChar(fileEncoding, paddingChar)) {
            throw new FileException("Padding char is not half-width character.");
        }

        try {
            // �p�f�B���O������̃o�C�g�����Ώە����񂪒����ꍇ�̓p�f�B���O�������Ȃ��B
            int paddingSize = columnBytes
                    - columnString.getBytes(fileEncoding).length;

            if (paddingSize <= 0) {
                return columnString;
            }

            StringBuilder columnBuilder = new StringBuilder(columnBytes);

            char[] fillChars = new char[paddingSize];
            Arrays.fill(fillChars, paddingChar);

            if (PaddingType.LEFT.equals(paddingType)) {
                columnBuilder.append(fillChars).append(columnString);
                return columnBuilder.toString();

            } else if (PaddingType.RIGHT.equals(paddingType)) {
                columnBuilder.append(columnString).append(fillChars);
                return columnBuilder.toString();

            } else {
                return columnString;
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileException("Specified Encoding : " + fileEncoding
                    + " is not supported", e);
        }
    }

    /**
     * �g���������B<br>
     * <br>
     * �J�����̕�������A�m�e�[�V�����Ŏw�肳�ꂽ�����Ńg��������B<br>
     * �����񂩂��菜�����̂́A�g���������utrimChar�v�Ŏw�肵�������B<br>
     * �g���������͔��p1�����ł���̂ŁA�S�p���������͂��ꂽ�ꍇ�͓��̓G���[�ƂȂ�B
     * @param columnString �g���������O�̂P�J�������̕�����
     * @param fileEncoding �t�@�C���G���R�[�f�B���O
     * @param trimChar �g��������(���p)
     * @param trimType �g�����^�C�v
     * @return �g����������̂P�J�������̕�����
     */
    public static String trim(String columnString, String fileEncoding,
            char trimChar, TrimType trimType) {

        // NONE�̂Ƃ��͂��̂܂ܕ������ԋp����
        if (TrimType.NONE.equals(trimType)) {
            return columnString;
        }

        // ���p�����̔���
        if (!isHalfWidthChar(fileEncoding, trimChar)) {
            throw new FileException("Trim char is not half-width character.");
        }

        int start = 0;
        int length = columnString.length();

        if (TrimType.LEFT.equals(trimType) || TrimType.BOTH.equals(trimType)) {
            while ((start < length) && columnString.charAt(start) == trimChar) {
                start++;
            }
        }
        if (TrimType.RIGHT.equals(trimType) || TrimType.BOTH.equals(trimType)) {
            while ((start < length)
                    && columnString.charAt(length - 1) == trimChar) {
                length--;
            }
        }
        return columnString.substring(start, length);
    }

    /**
     * ���p�������`�F�b�N���s���B
     * @param fileEncoding �t�@�C���G���R�[�f�B���O
     * @param checkChar �`�F�b�N���s������
     * @return ���p�����̏ꍇ��true��ԋp����
     * @throws FileException ���݂��Ȃ��G���R�[�f�B���O�̏ꍇ
     */
    private static boolean isHalfWidthChar(String fileEncoding, char checkChar)
                                                                               throws FileException {

        Map<Character, Boolean> cache = encodingCache.get(fileEncoding);
        if (cache == null) {
            cache = new ConcurrentHashMap<Character, Boolean>();
            encodingCache.put(fileEncoding, cache);
        }

        Boolean result = cache.get(checkChar);
        if (result == null) {
            try {
                result = (1 == Character.toString(checkChar).getBytes(
                        fileEncoding).length);
                cache.put(checkChar, result);
            } catch (UnsupportedEncodingException e) {
                throw new FileException("Specified Encoding : " + fileEncoding
                        + " is not supported", e);
            }
        }
        return result.booleanValue();
    }
}

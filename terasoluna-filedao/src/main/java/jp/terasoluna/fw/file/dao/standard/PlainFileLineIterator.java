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

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import jp.terasoluna.fw.file.dao.FileLineException;

/**
 * �t�@�C���s�I�u�W�F�N�g��p���Ȃ��t�@�C���Ǎ��@�\�B
 * <p>
 * �e�L�X�g�t�@�C������1�s���̃f�[�^��ǂݎ�蕶����Ƃ��Čďo���ɕԋp����B ���̃t�@�C���A�N�Z�X�@�\�Ƃ͈قȂ�A�t�@�C���s�I�u�W�F�N�g���g��Ȃ��B
 * </p>
 * <b>�����p����t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V��������</b><br>
 * �@�D@{@link jp.terasoluna.fw.file.annotation.FileFormat}�̐ݒ荀��<br>
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
 * <b>�����ӎ���</b><br>
 * <ul>
 * �@
 * <li>��؂蕶���ƈ͂ݕ����̐ݒ�͖�������B</li>
 * </ul>
 */
public class PlainFileLineIterator extends AbstractFileLineIterator<Object> {

    /**
     * ��؂蕶���B
     */
    private char delimiter = ',';

    /**
     * �͂ݕ����B
     */
    private char encloseChar = Character.MIN_VALUE;

    /**
     * ���݃t�@�C�����͏����ς݂̃f�[�^�����̍s���B
     */
    private int currentLineCount = 0;

    /**
     * �g���C���������m�F�p�t���O�B
     */
    private boolean readTrailer = false;

    /**
     * �R���X�g���N�^�B
     * @param fileName �t�@�C����
     * @param clazz ���ʃN���X
     * @param columnParserMap �t�H�[�}�b�g�������X�g
     */
    @SuppressWarnings("unchecked")
    public PlainFileLineIterator(String fileName, Class clazz,
            Map<String, ColumnParser> columnParserMap) {
        super(fileName, clazz, columnParserMap);

        // �������������s���B
        super.init();
    }

    /**
     * �����񕪊������B
     * <p>
     * �f�[�^���̃f�[�^�P�s�����t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����̋L�q�ɏ]���J�����ɕ�������B<br>
     * ���̃N���X�ł͏����͎�������Ă��Ȃ����߁A<code>UnsupportedOperationException</code>���X���[�����B
     * </p>
     * @param fileLineString �f�[�^���̃f�[�^�P�s��
     * @return �f�[�^���P�s�̕�����𕪉����������z��
     */
    @Override
    public String[] separateColumns(String fileLineString) {
        throw new UnsupportedOperationException(
                "separateColumns(String) isn't supported.");
    }

    /**
     * �J��Ԃ������Ńt�@�C���s�I�u�W�F�N�g��ԋp����B
     * <p>
     * ���̍s�̃��R�[�h�̏����t�@�C���s�I�u�W�F�N�g�Ɋi�[���ĕԋp����B<br>
     * </p>
     * @return �t�@�C���̂P�s���̕�����
     */
    @Override
    public String next() {
        if (readTrailer) {
            throw new FileLineException(
                    "Data part should be called before trailer part.",
                    new IllegalStateException(), getFileName(),
                    currentLineCount);
        }
        if (!hasNext()) {
            throw new FileLineException(
                    "The data which can be acquired doesn't exist.",
                    new NoSuchElementException(), getFileName(),
                    currentLineCount + 1, null, -1);
        }
        currentLineCount++;
        return readLine();
    }

    /**
     * �f�[�^���̃f�[�^��ǂݔ�΂��������s���B<br>
     * @param skipLines �ǂݔ�΂��s���B
     */
    public void skip(int skipLines) {
        for (int i = 0; i < skipLines; i++) {
            if (!hasNext()) {
                throw new FileLineException(
                        "The data which can be acquired doesn't exist.",
                        new NoSuchElementException(), getFileName(),
                        currentLineCount + 1);
            }
            readLine();
            currentLineCount++;
        }
    }

    /**
     * �g���C�����̃f�[�^���擾����B<br>
     * @return �g���C�����̕����񃊃X�g
     * @see jp.terasoluna.fw.file.dao.standard.AbstractFileLineIterator#getTrailer()
     */
    @Override
    public List<String> getTrailer() {
        List<String> trailer = super.getTrailer();
        readTrailer = true;
        return trailer;
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

    /**
     * ���݃t�@�C�����͏����ς݂̃f�[�^�����̍s�����擾����B
     * @return ���݃t�@�C�����͏����ς݂̃f�[�^�����̍s���B
     */
    @Override
    public int getCurrentLineCount() {
        return currentLineCount;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�ɃA�m�e�[�V�������ݒ肳��Ă��鎖���`�F�b�N���邩�ǂ�����Ԃ��B<br>
     * PlainFileLineIterator�ł̓`�F�b�N���s��Ȃ����߁Afalse�B
     * @return false
     */
    @Override
    protected boolean isCheckColumnAnnotationCount() {
        return false;
    }
}

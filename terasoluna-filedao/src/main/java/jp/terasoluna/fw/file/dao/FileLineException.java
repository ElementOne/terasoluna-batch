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

package jp.terasoluna.fw.file.dao;

/**
 * �t�@�C������s�̃f�[�^��ǂݎ��ۂɔ���������O�����b�v����N���X�B<br>
 * �G���[�̏��Ƃ��Ĉȉ������B<br>
 * <ul>
 * <li>�G���[���b�Z�[�W</li>
 * <li>������O</li>
 * <li>�G���[�����������f�[�^�̃f�[�^�����s�ԍ�</li>
 * <li>�G���[�����������f�[�^�̃J������</li>
 * <li>�G���[�����������f�[�^�̃J�����C���f�b�N�X</li>
 * </ul>
 * <b>�����ӎ���</b> FileLineException�Ɋi�[�����s�ԍ����̓t�@�C���ɑ΂���s�ԍ��ł͂Ȃ� �f�[�^�����̍s�ԍ��ł��B<br>
 * �w�b�_�������݂���ꍇ�̓t�@�C���̍s�ԍ��ƍ���Ȃ��׋C�����邱�ƁB<br>
 */
public class FileLineException extends FileException {

    /**
     * �V���A���o�[�W����UID�B
     */
    private static final long serialVersionUID = 2922540035720279260L;

    /**
     * �G���[�����������f�[�^�̃J�������B<br>
     * ���ݒ莞��<code>null</code>�����B<br>
     */
    private String columnName = null;

    /**
     * �G���[�����������f�[�^�̃J�����ԍ��B<br>
     * ���ݒ莞��<code>-1</code>�����B<br>
     */
    private int columnIndex = -1;

    /**
     * �G���[�����������f�[�^�̃f�[�^�����s�ԍ��B<br>
     * �t�@�C���̍s�ԍ��ł͂Ȃ��f�[�^�����̍s�ԍ��ł��B<br>
     * ���ݒ莞��<code>-1</code>�����B<br>
     */
    private int lineNo = -1;

    /**
     * �R���X�g���N�^�B
     * @param e ������O
     */
    public FileLineException(Exception e) {
        super(e);
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     */
    public FileLineException(String message) {
        super(message);
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param e ������O
     */
    public FileLineException(String message, Exception e) {
        super(message, e);
    }

    /**
     * �R���X�g���N�^�B
     * @param e ������O
     * @param fileName �t�@�C����
     * @param lineNo �G���[�����������f�[�^�̃f�[�^�����s�ԍ�
     */
    public FileLineException(Exception e, String fileName, int lineNo) {
        super(e, fileName);
        this.lineNo = lineNo;
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param e ������O
     * @param fileName �t�@�C����
     * @param lineNo �G���[�����������f�[�^�̃f�[�^�����s�ԍ�
     */
    public FileLineException(String message, Exception e, String fileName,
            int lineNo) {
        super(message, e, fileName);
        this.lineNo = lineNo;
    }

    /**
     * �R���X�g���N�^�B
     * @param e ������O
     * @param fileName �t�@�C����
     * @param lineNo �G���[�����������f�[�^�̃f�[�^�����s�ԍ�
     * @param columnName �J������
     * @param columnIndex �G���[�����������J�����ԍ�
     */
    public FileLineException(Exception e, String fileName, int lineNo,
            String columnName, int columnIndex) {
        super(e, fileName);
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.lineNo = lineNo;
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param e ������O
     * @param fileName �t�@�C����
     * @param lineNo �G���[�����������f�[�^�̃f�[�^�����s�ԍ�
     * @param columnName �J������
     * @param columnIndex �G���[�����������J�����ԍ�
     */
    public FileLineException(String message, Exception e, String fileName,
            int lineNo, String columnName, int columnIndex) {
        super(message, e, fileName);
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.lineNo = lineNo;
    }

    /**
     * �J���������擾����B
     * @return �J������
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * �G���[�����������s�̍s�ԍ����擾����B
     * @return �G���[�����������s�̍s�ԍ�
     */
    public int getLineNo() {
        return this.lineNo;
    }

    /**
     * �G���[�����������J�����̃J�����ԍ����擾����B
     * @return �G���[�����������J�����̃J�����ԍ��i0����J�n�j
     */
    public int getColumnIndex() {
        return columnIndex;
    }

}

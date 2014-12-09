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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.annotation.TrimType;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineIterator;

import org.apache.commons.lang.StringUtils;

/**
 * �t�@�C���A�N�Z�X(�f�[�^�擾)�p�̋��ʃN���X�B
 * <p>
 * �t�@�C���A�N�Z�X(�f�[�^�擾)���s��3�̃N���X(CSV�A�Œ蒷�A�ϒ�) �ɋ��ʂ��鏈�����܂Ƃ߂����ۃN���X�B �t�@�C���̎�ނɑΉ�����T�u�N���X���������s���B<br>
 * �g�p���{@link jp.terasoluna.fw.file.dao.FileLineIterator}���Q�Ƃ̂��ƁB
 * </p>
 * �t�@�C���擾�����̓f�[�^��擪���珇�Ԃɓǂݍ��ނ��߁A���L�̎菇�ŌĂяo�����悤�Ɏ�������K�v������܂��B<br>
 * <ul>
 * <li>�w�b�_���擾(getHeader())</li>
 * <li>�X�L�b�v����(skip())</li>
 * <li>�f�[�^���擾����(hasNext()�AreadLine())</li>
 * <li>�g���C�����擾(getTrailer())</li>
 * </ul>
 * �����A�g���C�����̎擾���s���Ɠ����Ŏc���Ă���f�[�^����S���X�L�b�v���邽�߁A �����r���Ƀg���C�������擾����ƃf�[�^���̎擾���o���Ȃ��Ȃ�܂��B<br>
 * �g���C�����̎擾��f�[�^���擾���������s�����<code>IllegalStateException<code>���������܂��B<br>
 * @see jp.terasoluna.fw.file.dao.FileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineIterator
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public abstract class AbstractFileLineIterator<T> implements
                                                  FileLineIterator<T> {

    /**
     * ����������������킷�s�ԍ��B
     */
    private static final int INITIAL_LINE_NO = -1;

    /**
     * �t�@�C�����B
     */
    private String fileName = null;

    /**
     * ���ʃN���X�B
     */
    private Class<T> clazz = null;

    /**
     * �s��؂蕶���B
     */
    private String lineFeedChar = System.getProperty("line.separator");

    /**
     * �t�@�C���G���R�[�f�B���O�B
     */
    private String fileEncoding = System.getProperty("file.encoding");

    /**
     * �w�b�_�s���B
     */
    private int headerLineCount = 0;

    /**
     * �g���C���s���B
     */
    private int trailerLineCount = 0;

    /**
     * �t�@�C�����͏����ς݂̃f�[�^���̍s���B
     */
    private int currentLineCount = 0;

    /**
     * �t�@�C���A�N�Z�X�p�̕����X�g���[���B
     */
    private BufferedReader reader = null;

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ��B
     */
    private Field[] fields = null;

    /**
     * �t�@�C���s�I�u�W�F�N�g�̓��͐ݒ�A�m�e�[�V�������i�[����ϐ��B
     */
    private InputFileColumn[] inputFileColumns = null;

    /**
     * �e�J�������Ƃ̃J����Index���i�[����ϐ��B
     */
    private int[] columnIndexs = null;

    /**
     * �e�J�������Ƃ̃J�����̃t�H�[�}�b�g���i�[����ϐ��B
     */
    private String[] columnFormats = null;

    /**
     * �e�J�������Ƃ̃o�C�g�����i�[����ϐ��B
     */
    private int[] columnBytes = null;

    /**
     * 1�s���̃o�C�g�����i�[����ϐ��B
     */
    private int totalBytes = 0;

    /**
     * �e�J�������Ƃ̃p�f�B���O��ʂ��i�[����ϐ��B
     */
    private PaddingType[] paddingTypes = null;

    /**
     * �e�J�������Ƃ̃p�f�B���O�������i�[����ϐ��B
     */
    private char[] paddingChars = null;

    /**
     * �e�J�������Ƃ̃g������ʂ��i�[����ϐ��B
     */
    private TrimType[] trimTypes;

    /**
     * �e�J�����̃g�����������i�[����ϐ��B
     */
    private char[] trimChars;

    /**
     * �e�J�������Ƃ̈͂ݕ������i�[����ϐ��B
     */
    private char[] columnEncloseChar;

    /**
     * �t�@�C���s�I�u�W�F�N�g�̃X�g�����O�R���o�[�^���i�[����ϐ��B
     */
    private StringConverter[] stringConverters = null;

    /**
     * �t�@�C���s�I�u�W�F�N�g�̃X�g�����O�R���o�[�^���i�[����}�b�v�B
     */
    @SuppressWarnings("unchecked")
    private static Map<Class, StringConverter> stringConverterCacheMap = new HashMap<Class, StringConverter>();

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field�ɑΉ�����setter���\�b�h���i�[����B
     */
    private Method[] methods = null;

    /**
     * �J�����p�[�T�[���i�[����}�b�v�B
     */
    private Map<String, ColumnParser> columnParserMap = null;

    /**
     * �w�b�_���̕����񃊃X�g�B
     */
    private List<String> header = new ArrayList<String>();

    /**
     * �g���C�����̕����񃊃X�g�B
     */
    private List<String> trailer = new ArrayList<String>();

    /**
     * �g���C���������m�F�p�t���O�B
     */
    private boolean readTrailer = false;

    /**
     * �g���C�����̈ꎞ�i�[�p�̃L���[�B
     */
    private Queue<String> trailerQueue = null;

    /**
     * 1�s���̕������ǂݍ��ރI�u�W�F�N�g
     */
    private LineReader lineReader = null;

    /**
     * �����������m�F�p�t���O�B
     */
    private boolean calledInit = false;

    /**
     * �͂ݕ����m�F�p�t���O�B
     */
    private boolean enclosed = false;

    /**
     * �R���X�g���N�^�B<br>
     * �����̃`�F�b�N�y�сA�t�@�C���s�I�u�W�F�N�g��FileFormat�A�m�e�[�V���� �ݒ�̃`�F�b�N���s���B<br>
     * �`�F�b�N���ʖ�肪����ꍇ�͗�O�𔭐�����B<br>
     * @param fileName �t�@�C�����Ō�Ɉړ�
     * @param clazz �t�@�C���s�I�u�W�F�N�g�N���X
     * @param columnParserMap �t�H�[�}�b�g�������X�g
     * @throws FileException �����������Ŏ��s�����ꍇ�B
     */
    public AbstractFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> columnParserMap) {
        // �t�@�C�����̕K�{�`�F�b�N���s���B
        if (fileName == null || "".equals(fileName)) {
            throw new FileException("fileName is required.",
                    new IllegalArgumentException(), fileName);
        }

        // �t�@�C���s�I�u�W�F�N�g�N���X�̕K�{�`�F�b�N���s���B
        if (clazz == null) {
            throw new FileException("clazz is required.",
                    new IllegalArgumentException(), fileName);
        }

        // �t�H�[�}�b�g�������X�g�̕K�{�`�F�b�N���s���B
        if (columnParserMap == null || columnParserMap.isEmpty()) {
            throw new FileException("columnFormaterMap is required.",
                    new IllegalArgumentException(), fileName);
        }

        // �t�@�C���s�I�u�W�F�N�g�N���X���C���X�^���X���ł��邩���`�F�b�N����B
        try {
            clazz.newInstance();
        } catch (InstantiationException e) {
            throw new FileException("Failed in instantiation of clazz.", e,
                    fileName);
        } catch (IllegalAccessException e) {
            throw new FileException(
                    "clazz's nullary  constructor is not accessible", e,
                    fileName);
        }

        this.fileName = fileName;
        this.clazz = clazz;
        this.columnParserMap = columnParserMap;

        // FileFormat�A�m�e�[�V�����̐ݒ���`�F�b�N����B
        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // �t�@�C���s�I�u�W�F�N�g��Class��FileFormat�A�m�e�[�V���������邩�`�F�b�N����B
        if (fileFormat == null) {
            throw new FileException("FileFormat annotation is not found.",
                    new IllegalStateException(), fileName);
        }

        // ��؂蕶���ƈ͂ݕ����������ꍇ�A��O���X���[����B
        if (fileFormat.delimiter() == fileFormat.encloseChar()) {
            throw new FileException(
                    "Delimiter is the same as EncloseChar and is no use.",
                    new IllegalStateException(), fileName);
        }

        // �s��؂蕶�����`�F�b�N����B�ݒ肪�Ȃ��ꍇ�̓V�X�e���f�t�H���g�l�𗘗p����B
        if (fileFormat.lineFeedChar() != null
                && !"".equals(fileFormat.lineFeedChar())) {
            this.lineFeedChar = fileFormat.lineFeedChar();
        }

        // �t�@�C���G���R�[�f�B���O���`�F�b�N����B�ݒ肪�Ȃ��ꍇ�̓V�X�e���f�t�H���g�l�𗘗p����B
        if (fileFormat.fileEncoding() != null
                && !"".equals(fileFormat.fileEncoding())) {
            this.fileEncoding = fileFormat.fileEncoding();
        }

        // �w�b�_�s����ݒ肷��B
        this.headerLineCount = fileFormat.headerLineCount();

        // �g���C���s����ݒ肷��B
        this.trailerLineCount = fileFormat.trailerLineCount();
    }

    /**
     * ���̍s�̃��R�[�h�����邩�ǂ����m�F����B<br>
     * �J��Ԃ������ł���ɗv�f������ꍇ�� true ��Ԃ��܂��B
     * @return �J��Ԃ������ł���ɗv�f������ꍇ�� <code>true</code>
     * @throws FileException ���[�_����IOException�����������ꍇ�B
     */
    public boolean hasNext() {
        try {
            reader.mark(1);
            if (reader.read() != -1) {
                return true;
            }
        } catch (IOException e) {
            throw new FileException("Processing of reader was failed.", e,
                    fileName);
        } finally {
            try {
                reader.reset();
            } catch (IOException e) {
                throw new FileException(
                        "Processing of reader#reset was failed.", e, fileName);
            }
        }

        return false;
    }

    /**
     * �J��Ԃ������Ńt�@�C���s�I�u�W�F�N�g��ԋp����B<br>
     * <p>
     * ���̍s�̃��R�[�h�̏����t�@�C���s�I�u�W�F�N�g�Ɋi�[���ĕԋp���܂��B<br>
     * �J��Ԃ������Ŏ��̗v�f��Ԃ��܂��B<br>
     * </p>
     * ���̍s�̃��R�[�h�̏��̓t�@�C���s�I�u�W�F�N�g��InputFileColumn�̒�`�� ��Â��Ċi�[�����B<br>
     * �����A�t�@�C���s�I�u�W�F�N�g�̃}�b�s���O�t�B�[���h�̐��ƍ���Ȃ� ���R�[�h��񂪗����ꍇ�͗�O�𔭐�����B<br>
     * �܂��AInputFileColumn�ɐݒ肳�ꂽ�o�C�g���ƈႤ��񂪗����ꍇ����O�𔭐�����B<br>
     * ����ł͂Ȃ��ꍇ�͈ȉ��̏��ԂŃf�[�^���������i�[����B<br>
     * <ul>
     * �@�@
     * <li>�g��������</li> �@�@
     * <li>�p�f�B���O����</li> �@�@
     * <li>������ϊ�����</li> �@�@
     * <li>�^�ϊ�(�}�b�s���O)����</li>
     * </ul>
     * @return �t�@�C���s�I�u�W�F�N�g
     * @throws FileException �t�@�C���s�I�u�W�F�N�g�̐����Ɏ��s�����ꍇ�B
     * @throws FileLineException �t�@�C���s�I�u�W�F�N�g�̎擾�Ɏ��s�����ꍇ�B
     */
    public T next() {
        if (readTrailer) {
            throw new FileLineException(
                    "Data part should be called before trailer part.",
                    new IllegalStateException(), fileName, currentLineCount);
        }

        if (!hasNext()) {
            throw new FileLineException(
                    "The data which can be acquired doesn't exist.",
                    new NoSuchElementException(), fileName,
                    currentLineCount + 1);
        }

        T fileLineObject = null;

        // ���̍s�f�[�^��ǂށBhasNext()�`�F�b�N���s�������߁Anull�̏ꍇ�Ȃ��B
        String currentString = readLine();
        currentLineCount++;

        // �t�@�C���s�I�u�W�F�N�g��V���ɐ������鏈���B
        try {
            fileLineObject = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new FileException(
                    "Failed in an instantiate of a FileLineObject.", e,
                    fileName);
        } catch (IllegalAccessException e) {
            throw new FileException(
                    "Failed in an instantiate of a FileLineObject.", e,
                    fileName);
        }

        // CSV�̋�؂蕶���ɂ��������ē��̓f�[�^�𕪉�����B
        // ��؂蕶���̓A�m�e�[�V��������擾����B
        String[] columns = separateColumns(currentString);

        // �t�@�C������ǂݎ�����J�������ƃt�@�C���s�I�u�W�F�N�g�̃J���������r����B
        if (fields.length != columns.length) {
            throw new FileLineException("Column Count is different from "
                    + "FileLineObject's column counts",
                    new IllegalStateException(), fileName, currentLineCount);
        }

        int columnIndex = -1;
        String columnString = null;

        for (int i = 0; i < fields.length; i++) {

            // JavaBean�̓��͗p�̃A�m�e�[�V������ݒ肷��B
            columnIndex = columnIndexs[i];

            // 1�J�����̕�������Z�b�g����B
            columnString = columns[columnIndex];

            // �J�����̃o�C�g���`�F�b�N�B
            if (isCheckByte(columnBytes[i])) {
                try {
                    if (columnString.getBytes(fileEncoding).length != columnBytes[i]) {
                        throw new FileLineException(
                                "Data size is different from a set point "
                                        + "of a column.",
                                new IllegalStateException(), fileName,
                                currentLineCount, fields[i].getName(),
                                columnIndex);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new FileException(
                            "fileEncoding which isn't supported was set.", e,
                            fileName);
                }
            }

            // �g��������
            columnString = FileDAOUtility.trim(columnString, fileEncoding,
                    trimChars[i], trimTypes[i]);

            // �p�f�B���O����
            columnString = FileDAOUtility.padding(columnString, fileEncoding,
                    columnBytes[i], paddingChars[i], paddingTypes[i]);

            // ������ϊ��̏����B
            columnString = stringConverters[i].convert(columnString);

            // �l���i�[���鏈���B
            // JavaBean�̑����̌^�̖��O�ɂ���ď�����U�蕪����B
            ColumnParser columnParser = columnParserMap.get(fields[i].getType()
                    .getName());
            try {
                columnParser.parse(columnString, fileLineObject, methods[i],
                        columnFormats[i]);
            } catch (IllegalArgumentException e) {
                throw new FileLineException("Failed in coluomn data parsing.",
                        e, fileName, currentLineCount, fields[i].getName(),
                        columnIndex);
            } catch (IllegalAccessException e) {
                throw new FileLineException("Failed in coluomn data parsing.",
                        e, fileName, currentLineCount, fields[i].getName(),
                        columnIndex);
            } catch (InvocationTargetException e) {
                throw new FileLineException("Failed in coluomn data parsing.",
                        e, fileName, currentLineCount, fields[i].getName(),
                        columnIndex);
            } catch (ParseException e) {
                throw new FileLineException("Failed in coluomn data parsing.",
                        e, fileName, currentLineCount, fields[i].getName(),
                        columnIndex);
            }

        }

        return fileLineObject;
    }

    /**
     * �T�|�[�g���Ȃ��B<br>
     * Iterator�Œ�`����Ă��郁�\�b�h�B<br>
     * FileQueryDAO�ł͎������Ȃ��̂ŁA���̃N���X����Ăяo�����ꍇ�A UnsupportedOperationException���X���[����B
     * @throws UnsupportedOperationException ���̃��\�b�h�̓T�|�[�g���Ȃ��B
     */
    public void remove() {
        throw new UnsupportedOperationException("remove() isn't supported.");
    }

    /**
     * �������������s���B<br>
     * �����������ōs�������͈ȉ��ł��B�B
     * <ul>
     * <li>�t�@�C���s�I�u�W�F�N�g�̑���(Field)�̎擾</li>
     * <li>�����ϊ���ʃI�u�W�F�N�g(stringConverters)�̐���</li>
     * <li>�t�@�C���s�I�u�W�F�N�g�̑����ɑ΂���Z�b�^���\�b�h(methods)�̎擾</li>
     * <li>�t�@�C������f�[�^��Ǎ��ނ��߂�LineReader�̐���</li>
     * <li>�w�b�_���̎擾</li>
     * <li>�g���C���L���[�̏�����</li>
     * </ul>
     * init()��AbstracFileLineIterator���p������N���X�̃R���X�g���N�^�� �Ăԃ��\�b�h�ł���B<br>
     * ���ʌ݊����̂��߁A2��ȏ���s�ł��Ȃ��悤�ɂ��Ă���B
     * @throws FileException �����������Ŏ��s�����ꍇ�B
     * @throws FileLineException �J�����Ɋ֘A���鏉���������Ŏ��s�����ꍇ�B
     */
    protected void init() {
        if (!calledInit) {
            // ���t���N�V���������̊�{���𐶐�����B
            buildFields();

            if (isCheckEncloseChar()) {
                // �J�������Ƃ̈͂ݕ������ݒ肳��Ă���ꍇ�A��O���X���[����B
                if (isEnclosed()) {
                    throw new FileException(
                            "columnEncloseChar can not change.",
                            new IllegalStateException(), fileName);
                }
            }

            if (isCheckColumnAnnotationCount()) {
                // �t�@�C���s�I�u�W�F�N�g�ɃA�m�e�[�V�������ݒ肳��Ă��Ȃ��ꍇ�A��O���X���[����B
                if (fields.length == 0) {
                    throw new FileException("InputFileColumn is not found.",
                            new IllegalStateException(), fileName);
                }
            }

            buildStringConverters();
            buildMethods();

            // �t�@�C������f�[�^��Ǎ��ނ��߂�LineReader�𐶐�����B
            buildLineReader();

            // �w�b�_���ƃg���C�����̎擾���邽�߂̊�{���𐶐�����B
            buildHeader();
            buildTrailerQueue();

            calledInit = true;
        }
    }

    /**
     * �t�@�C������f�[�^��Ǎ��ނ��߂�LineReader�𐶐�����B<br>
     * �t�@�C����Reader�̐�������сA���p���ׂ�LineReader�̐������s���B<br>
     * �s��؂蕶����0,1,2���ł͂Ȃ��ꍇ�͗�O�𔭐�����B
     * @throws FileException LineReader�̐����Ɏ��s�����ꍇ�B
     */
    private void buildLineReader() {
        // �Ώۃt�@�C���ɑ΂���Reader���擾����B
        try {
            this.reader = new BufferedReader(new InputStreamReader(
                    (new FileInputStream(fileName)), fileEncoding));
            if (!reader.markSupported()) {
                throw new FileException(
                        "BufferedReader of this JVM dose not support mark method");
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileException("Failed in generation of reader.", e,
                    fileName);
        } catch (FileNotFoundException e) {
            throw new FileException("Failed in generation of reader.", e,
                    fileName);
        }

        // �s��؂蕶���ƈ͂ݕ����̏��Ɋ�Â���LineReader�𐶐�����B
        if (lineFeedChar.length() == 2) {
            // �s��؂蕶����2����
            if (!enclosed) {
                // �͂ݕ�������
                lineReader = new LineFeed2LineReader(reader, lineFeedChar);
            } else {
                // �͂ݕ�������
                lineReader = new EncloseCharLineFeed2LineReader(getDelimiter(),
                        getEncloseChar(), columnEncloseChar, reader,
                        lineFeedChar);
            }
        } else if (lineFeedChar.length() == 1) {
            // �s��؂蕶����1����
            if (!enclosed) {
                // �͂ݕ�������
                lineReader = new LineFeed1LineReader(reader, lineFeedChar);
            } else {
                // �͂ݕ�������
                lineReader = new EncloseCharLineFeed1LineReader(getDelimiter(),
                        getEncloseChar(), columnEncloseChar, reader,
                        lineFeedChar);
            }
        } else if (lineFeedChar.length() == 0) {
            // �s��؂蕶����0����
            lineReader = new LineFeed0LineReader(reader, fileEncoding,
                    totalBytes);
        } else {
            throw new FileException(
                    "lineFeedChar length must be 0 or 1 or 2. but: "
                            + lineFeedChar.length(),
                    new IllegalStateException(), fileName);
        }
    }

    /**
     * InputFileColumn�A�m�e�[�V�������ݒ肳��Ă���t�@�C���s�I�u�W�F�N�g�� �����̔z��𐶐�����B<br>
     * �擾�Ώۑ����̓t�@�C���s�I�u�W�F�N�g�ƌp�����̑S�N���X�̑����ł��B<br>
     * �擾����������{@link InputFileColumn#columnIndex()}���������Əd������ ����ꍇ�͗�O����������B<br>
     * �܂��A{@link InputFileColumn#columnIndex()}�̍ő�l���J�����̐��ƍ���Ȃ� �ꍇ����O����������B<br>
     * �t�@�C���s�I�u�W�F�N�g�̑����̐ݒ�ɖ�肪�����ꍇ�� InputFileColumn�A�m�e�[�V�����ݒ肪���鑮���̂ݐ������z��ɂ���B<br>
     * @throws FileException �J�����C���f�b�N�X���d�������ꍇ�B
     */
    @SuppressWarnings("unchecked")
    private void buildFields() {
        // �t�B�[���h�I�u�W�F�N�g�𐶐�
        List<Field[]> allFields = new ArrayList<Field[]>();

        // �t�B�[���h�I�u�W�F�N�g�𐶐�
        Class tempClass = clazz;
        Field[] declaredFieldArray = null;
        int allFieldCount = 0;
        while (tempClass != null) {
            declaredFieldArray = tempClass.getDeclaredFields();
            allFields.add(declaredFieldArray);
            allFieldCount += declaredFieldArray.length;
            tempClass = tempClass.getSuperclass();
        }

        // �J�����C���f�b�N�X�̒�`�̏��Ԃɕ��ёւ�
        Field[] dataColumnFields = new Field[allFieldCount];

        InputFileColumn inputFileColumn = null;
        int maxColumnIndex = -1;
        int columnIndex = -1;
        int columnCount = 0;

        for (Field[] fields : allFields) {
            for (Field field : fields) {
                inputFileColumn = field.getAnnotation(InputFileColumn.class);
                if (inputFileColumn != null) {
                    // �}�b�s���O�\�Ȍ^�̃t�B�[���h�Ȃ̂��m�F����B
                    if (columnParserMap.get(field.getType().getName()) == null) {
                        throw new FileException(
                                "There is a type which isn't supported in a "
                                        + "mapping target field in FileLineObject.",
                                new IllegalStateException(), fileName);
                    }

                    columnIndex = inputFileColumn.columnIndex();
                    // �J����Index���}�C�i�X�l�Ȃ̂��m�F����B
                    if (columnIndex < 0) {
                        throw new FileException(
                                "Column Index in FileLineObject is the minus "
                                        + "number.",
                                new IllegalStateException(), fileName);
                    }
                    // �J����Index���t�B�[���h���𒴂��Ă��邩���邩�m�F����B
                    if (dataColumnFields.length <= columnIndex) {
                        throw new FileException(
                                "Column Index in FileLineObject is bigger than "
                                        + "the total number of the field.",
                                new IllegalStateException(), fileName);
                    }
                    // �J����Index���d�����ĂȂ��̂��m�F����B
                    if (dataColumnFields[columnIndex] == null) {
                        dataColumnFields[columnIndex] = field;
                        if (maxColumnIndex < columnIndex) {
                            maxColumnIndex = columnIndex;
                        }
                        columnCount++;
                    } else {
                        throw new FileException("Column Index is duplicate : "
                                + columnIndex, fileName);
                    }
                }
            }
        }

        // columnIndex���A�ԂŒ�`����Ă��邩���`�F�b�N����
        if (columnCount != (maxColumnIndex + 1)) {
            throw new FileException(
                    "columnIndex in FileLineObject is not sequential order.",
                    new IllegalStateException(), fileName);
        }

        // �t�B�[���h���R�s�[(null�̕����폜)
        if (dataColumnFields.length == columnCount) {
            this.fields = dataColumnFields;
        } else {
            this.fields = new Field[columnCount];
            System.arraycopy(dataColumnFields, 0, this.fields, 0, columnCount);
        }

        // InputFileColumn�i�A�m�e�[�V�����j�I�u�W�F�N�g��ϐ��Ɋi�[����B�iStringConverter�ȊO�j
        inputFileColumns = new InputFileColumn[fields.length];
        columnIndexs = new int[fields.length];
        columnFormats = new String[fields.length];
        columnBytes = new int[fields.length];
        paddingTypes = new PaddingType[fields.length];
        paddingChars = new char[fields.length];
        trimTypes = new TrimType[fields.length];
        trimChars = new char[fields.length];

        // �͂ݕ����ݒ�B�܂�FileFormat�̐ݒ��K�p����B
        columnEncloseChar = new char[fields.length];
        if (getEncloseChar() != Character.MIN_VALUE) {
            enclosed = true;
            for (int index = 0; index < fields.length; index++) {
                columnEncloseChar[index] = getEncloseChar();
            }
        }

        for (int i = 0; i < fields.length; i++) {
            inputFileColumns[i] = fields[i]
                    .getAnnotation(InputFileColumn.class);
            columnIndexs[i] = inputFileColumns[i].columnIndex();
            columnFormats[i] = inputFileColumns[i].columnFormat();
            columnBytes[i] = inputFileColumns[i].bytes();
            totalBytes += columnBytes[i];
            paddingTypes[i] = inputFileColumns[i].paddingType();
            paddingChars[i] = inputFileColumns[i].paddingChar();
            trimTypes[i] = inputFileColumns[i].trimType();
            trimChars[i] = inputFileColumns[i].trimChar();

            // �͂ݕ����ݒ�BinputFileColumns�̐ݒ�ŏ㏑��������B
            if (inputFileColumns[i].columnEncloseChar() != Character.MIN_VALUE) {
                columnEncloseChar[i] = inputFileColumns[i].columnEncloseChar();
                enclosed = true;
            }
        }
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑����̕����ϊ���ʃI�u�W�F�N�g�̔z��𐶐�����B<br>
     * �������ꂽ�����ϊ���ʃI�u�W�F�N�g�C���X�^���X�̓L���b�V�����A ���l�̐ݒ肪���鑮���ŗ��p����B<br>
     * �ݒ肳�ꂽ�����ϊ���ʃI�u�W�F�N�g���C���X�^���X���o���Ȃ����̂̏ꍇ�� ��O����������B<br>
     * @throws FileLineException �����ϊ���ʃI�u�W�F�N�g�̐����Ɏ��s�����ꍇ�B
     */
    private void buildStringConverters() {

        // �����ϊ���ʂ̔z��𐶐�
        StringConverter[] dataColumnStringConverters = new StringConverter[fields.length];

        InputFileColumn inputFileColumn = null;
        Class<? extends StringConverter> converterKind = null;

        for (int i = 0; i < fields.length; i++) {

            // JavaBean�̓��͗p�̃A�m�e�[�V�������擾����B
            inputFileColumn = inputFileColumns[i];

            // inputFileColumn.stringConverter()�̓��e�ɂ�菈����U�蕪����B
            try {
                // �����ϊ���ʂ̃A�m�e�[�V�������擾����B
                converterKind = inputFileColumn.stringConverter();

                // �}�b�v���Ɏ擾���������ϊ���ʂƈ�v����L�[�����݂��邩���肷��B
                if (stringConverterCacheMap.containsKey(converterKind)) {
                    // �}�b�v����I�u�W�F�N�g���擾���A�����ϊ���ʂ̔z��ɃZ�b�g����B
                    dataColumnStringConverters[i] = stringConverterCacheMap
                            .get(converterKind);

                } else {
                    // �C���X�^���X�𐶐����A�����ϊ���ʂ̔z��ɃZ�b�g����B
                    dataColumnStringConverters[i] = converterKind.newInstance();
                    stringConverterCacheMap.put(converterKind,
                            dataColumnStringConverters[i]);
                }

            } catch (InstantiationException e) {
                throw new FileLineException(
                        "Failed in an instantiate of a stringConverter.", e,
                        fileName, INITIAL_LINE_NO, fields[i].getName(),
                        inputFileColumn.columnIndex());

            } catch (IllegalAccessException e) {
                throw new FileLineException(
                        "Failed in an instantiate of a stringConverter.", e,
                        fileName, INITIAL_LINE_NO, fields[i].getName(),
                        inputFileColumn.columnIndex());
            }
        }
        this.stringConverters = dataColumnStringConverters;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑�����setter���\�b�h�̃��\�b�h�I�u�W�F�N�g�̔z��𐶐�����B<br>
     * �����ɑ΂���setter���\�b�h�͈ȉ��̃��[���Ō�������B<br>
     * <ul>
     * <li>�������̍ŏ��̕�����啶���ɂ���������̐擪�Ɂuset�v���������́B</li>
     * </ul>
     * setter���\�b�h�������ł��Ȃ��ꍇ�͗�O����������B
     * @throws FileException setter���\�b�h��������Ȃ������ꍇ�B
     */
    private void buildMethods() {
        Method[] dataColumnSetMethods = new Method[fields.length];
        StringBuilder setterName = new StringBuilder();
        String fieldName = null;

        for (int i = 0; i < fields.length; i++) {
            // JavaBean���珈���̑ΏۂƂȂ鑮���̑��������擾����B
            fieldName = fields[i].getName();

            // �����������ɁAsetter���\�b�h�̖��O�𐶐�����B
            setterName.setLength(0);
            setterName.append("set");
            setterName.append(StringUtils.upperCase(fieldName.substring(0, 1)));
            setterName.append(fieldName.substring(1, fieldName.length()));

            // setter�̃��t���N�V�����I�u�W�F�N�g���擾����B
            // fields[i].getType()�ň����̌^���w�肵�Ă���B
            try {
                dataColumnSetMethods[i] = clazz.getMethod(
                        setterName.toString(), new Class[] { fields[i]
                                .getType() });
            } catch (NoSuchMethodException e) {
                throw new FileException(
                        "The setter method of column doesn't exist.", e,
                        fileName);
            }
        }
        this.methods = dataColumnSetMethods;
    }

    /**
     * �w�b�_���̎擾���s���B<br>
     * �w�肳�ꂽ�s�����̃f�[�^�����݂��Ȃ��ꍇ�ɗ�O��Ԃ��B<br>
     * @throws FileException �w�b�_���̎擾�Ɏ��s�����ꍇ�B
     */
    private void buildHeader() {
        if (0 < headerLineCount) {
            for (int i = 0; i < headerLineCount; i++) {
                if (!hasNext()) {
                    throw new FileException(
                            "The data which can be acquired doesn't exist.",
                            new NoSuchElementException(), fileName);
                }
                try {
                    header.add(lineReader.readLine());
                } catch (FileException e) {
                    throw new FileException(
                            "Error occurred by reading processing of a File.",
                            e, fileName);
                }
            }
        }
    }

    /**
     * �g���C���L���[�̏��������s���B<br>
     * �g���C�����̓f�[�^����S���ǂ񂾌�̕����ō\������܂����A �t�@�C���͑O�����ɏ����ɓǂ܂�邽�߁A<br>
     * ���擾�����f�[�^���f�[�^���̏��Ȃ̂��g���C�����̏�񂩂����f�ł��Ȃ��B<br>
     * ���̂��߁A�L���[�Ƀf�[�^�����Ď擾����B<br>
     * �w�肳�ꂽ�s�����̃f�[�^�����݂��Ȃ��ꍇ�ɗ�O��Ԃ��B<br>
     * @throws FileException �g���C���L���[�̏��������������s�����ꍇ�B
     */
    private void buildTrailerQueue() {
        if (0 < trailerLineCount) {
            // �g���C���L���[�C���X�^���X�𐶐�����B
            trailerQueue = new ArrayBlockingQueue<String>(trailerLineCount);

            // �g���C���L���[�̃g���C���s�����̃f�[�^��ǉ�����B
            for (int i = 0; i < trailerLineCount; i++) {
                if (!hasNext()) {
                    throw new FileException(
                            "The data which can be acquired doesn't exist.",
                            new NoSuchElementException(), fileName);
                }
                try {
                    trailerQueue.add(lineReader.readLine());
                } catch (FileException e) {
                    throw new FileException(
                            "Error occurred by reading processing of a File.",
                            e, fileName);
                }
            }
        }
    }

    /**
     * �t�@�C���Ǐ������s���B<br>
     * @throws FileException �t�@�C���Ǐ����Ŏ��s�����ꍇ�B
     */
    public void closeFile() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new FileException("Processing of reader was failed.", e,
                    fileName);
        }
    }

    /**
     * �w�b�_���̃f�[�^���擾����B<br>
     * �f�[�^���ƃg���C�����̎擾�����̎��s�ۂƊ֌W�Ȃ��w�b�_�����擾���邱�Ƃ� �o����B
     * @return header �w�b�_���̕����񃊃X�g
     */
    public List<String> getHeader() {
        return header;
    }

    /**
     * �g���C�����̃f�[�^���擾����B<br>
     * �g���C�����̃f�[�^���擾����ƃf�[�^���̃f�[�^���擾���邱�Ƃ͏o���Ȃ��B<br>
     * ���R�̓g���C�����̃f�[�^�擾���ɁA�f�[�^���̏���S���X�L�b�v���邽�߂ł��B<br>
     * <b>�����ӎ���</b><br>
     * �f�[�^���̃f�[�^��S���擾����ȑO�Ƀg���C�������擾���Ȃ����ƁB<br>
     * @return �g���C�����̕����񃊃X�g
     * @throws FileException �f�[�^�s�擾�����Ŏ��s�����ꍇ�B
     */
    public List<String> getTrailer() {
        // �g���C�����̃L���b�V�����Ȃ��ꍇ�Ɏ��s����B
        if (!readTrailer) {
            String currentData = null;
            // �c���Ă���f�[�^�����΂��������s���B
            while (hasNext()) {
                try {
                    currentData = lineReader.readLine();
                } catch (FileException e) {
                    throw new FileException(
                            "Processing of lineReader was failed.", e, fileName);
                }
                if (0 < trailerLineCount) {
                    trailerQueue.poll();
                    trailerQueue.add(currentData);
                }
            }

            // �t�@�C���̃g���C���������L���b�V���Ɋi�[����B
            if (0 < trailerLineCount) {
                int trailerQueueLength = trailerQueue.size();

                for (int i = 0; i < trailerQueueLength; i++) {
                    trailer.add(trailerQueue.poll());
                }
            }
            readTrailer = true;
        }
        return trailer;
    }

    /**
     * �t�@�C������f�[�^���̃f�[�^��1�s���ǂݎ��A������Ƃ��Čďo���ɕԋp����B<br>
     * �g���C���������݂���ꍇ�̓g���C���L���[����f�[�^���擾���Č��ʕ������ ����B<br>
     * ���̌�LineReader����1�s���̕�������擾���g���C���L���[�Ɋi�[����B<br>
     * �g���C���������݂��Ȃ��ꍇ��LineReader����擾����1�s���̕���������ʕ������ ����B<br>
     * �����A����1�s���̃f�[�^���Ȃ��ꍇ��null��Ԃ��B
     * @return �f�[�^���̂P�s���̕�����
     * @throws FileException �f�[�^�s�擾�����Ŏ��s�����ꍇ�B
     */
    protected String readLine() {

        // ���̍s�f�[�^���Ȃ��ꍇ��null��Ԃ��B
        if (!hasNext()) {
            return null;
        }

        // ����1�s���̕�������擾����B
        String currentReadLineString = null;
        try {
            currentReadLineString = lineReader.readLine();
        } catch (FileException e) {
            throw new FileException("Processing of lineReader was failed.", e,
                    fileName);
        }

        // �g���C���L���[�����݂���ꍇ�́A���ʂƂ��ăL���[�̐擪�f�[�^��Ԃ��B
        // ���擾����1�s���̕�����̓g���C���L���[�ɓ����B
        if (0 < trailerLineCount) {
            String pollingLineString = trailerQueue.poll();
            trailerQueue.add(currentReadLineString);
            return pollingLineString;
        }

        return currentReadLineString;
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
                        new NoSuchElementException(), fileName,
                        currentLineCount + 1);
            }
            readLine();
            currentLineCount++;
        }
    }

    /**
     * ��؂蕶�����擾����B
     * @return �s��؂蕶���B
     */
    protected abstract char getDelimiter();

    /**
     * �͂ݕ������擾����B
     * @return �͂ݕ����B
     */
    protected abstract char getEncloseChar();

    /**
     * �f�[�^���̃f�[�^�P�s�����t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����̋L�q�� �]���J�����ɕ�������B<br>
     * ����<code>fileLineString</code>��<code>null</code>�������� �󕶎��̏ꍇ�́A�v�f�������Ȃ�<code>String</code>�z���Ԃ��܂��B<br>
     * �T�u�N���X�͂��̃��\�b�h���I�[�o�[���C�h���܂��B
     * @param fileLineString �f�[�^���̃f�[�^�P�s��
     * @return �f�[�^���P�s�̕�����𕪉����������z��
     */
    protected abstract String[] separateColumns(String fileLineString);

    /**
     * �ΏۃJ�����ɑ΂���o�C�g���`�F�b�N���s������Ԃ��B
     * @param inputFileColumn �ΏۃJ������InputFileColumn���
     * @return �o�C�g�����ݒ肳��Ă���(1�o�C�g�ȏ�)�ꍇ��true�B
     */
    protected boolean isCheckByte(InputFileColumn inputFileColumn) {
        if (0 < inputFileColumn.bytes()) {
            return true;
        }
        return false;
    }

    /**
     * �ΏۃJ�����ɑ΂���o�C�g���`�F�b�N���s������Ԃ��B
     * @param columnByte �ΏۃJ�����̃o�C�g��
     * @return �o�C�g�����ݒ肳��Ă���(1�o�C�g�ȏ�)�ꍇ��true�B
     */
    protected boolean isCheckByte(int columnByte) {
        if (0 < columnByte) {
            return true;
        }
        return false;
    }

    /**
     * �s��؂蕶�����擾����B
     * @return �s��؂蕶��
     */
    protected String getLineFeedChar() {
        return lineFeedChar;
    }

    /**
     * �s��؂蕶����ݒ肷��B
     * @param �s��؂蕶��
     */
    protected void setLineFeedChar(String lineFeedChar) {
        this.lineFeedChar = lineFeedChar;
    }

    /**
     * �t�@�C���G���R�[�f�B���O�擾����B
     * @return �t�@�C���G���R�[�f�B���O
     */
    protected String getFileEncoding() {
        return fileEncoding;
    }

    /**
     * �w�b�_�s�����擾����B
     * @return �w�b�_�s��
     */
    protected int getHeaderLineCount() {
        return headerLineCount;
    }

    /**
     * �g���C���s�����擾����B
     * @return �g���C���s��
     */
    protected int getTrailerLineCount() {
        return trailerLineCount;
    }

    /**
     * ���݃t�@�C�����͏����ς݂̃f�[�^�����̍s�����擾����B
     * @return �t�@�C�����͏����ς݂̃f�[�^���̍s���B
     */
    public int getCurrentLineCount() {
        return currentLineCount;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ����擾����B
     * @return �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ�
     */
    protected Field[] getFields() {
        return fields;
    }

    /**
     * �t�@�C�������擾����B
     * @return fileName �t�@�C����
     */
    protected String getFileName() {
        return fileName;
    }

    /**
     * �J�����̈͂ݕ������擾����B
     * @return columnEncloseChar �͂ݕ���
     */
    protected char[] getColumnEncloseChar() {
        return columnEncloseChar;
    }

    /**
     * �͂ݕ������ݒ肳��Ă��邩��Ԃ��B
     * @return enclosed �͂ݕ���
     */
    protected boolean isEnclosed() {
        return enclosed;
    }

    /**
     * �e�J�����̃o�C�g�����擾����B
     * @return columnBytes �e�J�����̃o�C�g��
     */
    protected int[] getColumnBytes() {
        return columnBytes;
    }

    /**
     * 1�s���̃o�C�g�����擾����B
     * @return totalBytes 1�s���̃o�C�g��
     */
    protected int getTotalBytes() {
        return totalBytes;
    }

    /**
     * �͂ݕ������ݒ肳��Ă��Ȃ������`�F�b�N���邩�ǂ�����Ԃ��B
     * @return �`�F�b�N���s���ꍇ��true�B
     */
    protected boolean isCheckEncloseChar() {
        return false;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�ɃA�m�e�[�V�������ݒ肳��Ă��鎖���`�F�b�N���邩�ǂ�����Ԃ��B
     * @return �`�F�b�N���s���ꍇ��true�B
     */
    protected boolean isCheckColumnAnnotationCount() {
        return true;
    }
}

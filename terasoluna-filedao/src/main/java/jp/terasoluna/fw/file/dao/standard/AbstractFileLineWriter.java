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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;
import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.annotation.TrimType;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineWriter;
import org.apache.commons.lang3.StringUtils;


/**
 * �t�@�C���A�N�Z�X(�f�[�^����)�p�̋��ʃN���X�B
 * <p>
 * �t�@�C���A�N�Z�X(�f�[�^����)���s��3�̃N���X(CSV�A�Œ蒷�A�ϒ�) �ɋ��ʂ��鏈�����܂Ƃ߂����ۃN���X�B �t�@�C���̎�ނɑΉ�����T�u�N���X���������s���B<br>
 * �g�p���{@link jp.terasoluna.fw.file.dao.FileLineWriter}���Q�Ƃ̂��ƁB
 * </p>
 * <p>
 * �t�@�C���擾�����͉��L�̎菇�ŌĂяo�����悤�Ɏ������邱�ƁB
 * <ul>
 * <li>����������(init()�A�C���X�^���X�������K���P��s�Ȃ�)</li>
 * <li>�w�b�_���擾(printHeaderLine())</li>
 * <li>�f�[�^���擾����(printDataLine())</li>
 * <li>�g���C�����擾(printTrailerLine())</li>
 * </ul>
 * ��L�̏��Ԃł̂ݐ��m�ɏo�͂ł���B<br>
 * </p>
 * @see jp.terasoluna.fw.file.dao.FileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineWriter
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public abstract class AbstractFileLineWriter<T> implements FileLineWriter<T> {

    /**
     * �������������̍s�ԍ��B
     */
    private static final int INITIAL_LINE_NO = -1;

    /**
     * �t�@�C���A�N�Z�X�i�o�́j�p�̕����X�g���[���B
     */
    private Writer writer = null;

    /**
     * �t�@�C���A�N�Z�X���s���t�@�C�����B
     */
    private String fileName = null;

    /**
     * �t�@�C���G���R�[�f�B���O�B
     */
    private String fileEncoding = System.getProperty("file.encoding");

    /**
     * �p�����[�^�N���X�̃N���X�B
     */
    private Class<T> clazz = null;

    /**
     * �s��؂蕶���B
     */
    private String lineFeedChar = System.getProperty("line.separator");

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ��B
     */
    private Field[] fields = null;

    /**
     * �t�@�C���s�I�u�W�F�N�g�̏o�͐ݒ�A�m�e�[�V�������i�[����ϐ��B
     */
    private OutputFileColumn[] outputFileColumns = null;

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
     * ���\�b�h�I�u�W�F�N�g
     */
    private Method[] methods = null;

    /**
     * �J�����t�H�[�}�b�g(�t�@�C�������j���i�[����}�b�v�B
     */
    private Map<String, ColumnFormatter> columnFormatterMap = null;

    /**
     * �f�[�^���o�͊m�F�p�t���O�B
     */
    private boolean writeData = false;

    /**
     * �g���C�����o�͊m�F�p�t���O�B
     */
    private boolean writeTrailer = false;

    /**
     * �������ݏ����ς݃f�[�^���̍s���B
     */
    private int currentLineCount = 0;

    /**
     * �������������s�t���O�B
     */
    private boolean calledInit = false;

    /**
     * �͂ݕ����m�F�p�t���O�B
     */
    private boolean enclosed = false;

    /**
     * �R���X�g���N�^�B<br>
     * ������<code>@FileFormat</code>�A�m�e�[�V�����̐ݒ�`�F�b�N����B <code>@FileFormat</code>�A�m�e�[�V�������ݒ肳��Ă��Ȃ��ꍇ�͔񌟍���O���X���[����B<br>
     * ��؂蕶���ƈ͂ݕ����ɓ��ꕶ�����ݒ肳��Ă���ꍇ�́A�񌟍���O���X���[����B<br>
     * �s��؂蕶�����R�����ȏ�̏ꍇ�́A�񌟍���O���X���[����B<br>
     * @param fileName �t�@�C����
     * @param clazz �p�����[�^�N���X
     * @param columnFormatterMap �e�L�X�g�擾���[��
     */
    public AbstractFileLineWriter(String fileName, Class<T> clazz,
            Map<String, ColumnFormatter> columnFormatterMap) {

        if (fileName == null || "".equals(fileName)) {
            throw new FileException("fileName is required.",
                    new IllegalArgumentException(), fileName);
        }

        if (clazz == null) {
            throw new FileException("clazz is required.",
                    new IllegalArgumentException(), fileName);
        }

        if (columnFormatterMap == null || columnFormatterMap.isEmpty()) {
            throw new FileException("columnFormatterMap is required.",
                    new IllegalArgumentException(), fileName);
        }

        this.fileName = fileName;
        this.clazz = clazz;
        this.columnFormatterMap = columnFormatterMap;

        // FileFormat�Ɋւ���`�F�b�N�����B

        // �t�@�C���t�H�[�}�b�g���擾����B
        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // @FileFormat�������ꍇ�A��O���X���[����B
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

        // �����R�[�h������������B
        if (fileFormat.fileEncoding() != null
                && !"".equals(fileFormat.fileEncoding())) {
            this.fileEncoding = fileFormat.fileEncoding();
        }

        // �s��؂蕶�����`�F�b�N����B�ݒ肪�Ȃ��ꍇ�̓V�X�e���f�t�H���g�l�𗘗p����B
        if (fileFormat.lineFeedChar() != null
                && !"".equals(fileFormat.lineFeedChar())) {
            this.lineFeedChar = fileFormat.lineFeedChar();
        }

        // �s��؂蕶����3�����ȏ�̏ꍇ�A��O���X���[����B
        if (lineFeedChar.length() > 2) {
            throw new FileException("lineFeedChar length must be 1 or 2. but: "
                    + lineFeedChar.length(), new IllegalStateException(),
                    fileName);
        }
    }

    /**
     * �������������s���B<br>
     * �����������ōs�������͈ȉ��ł��B�B
     * <ul>
     * <li>�t�@�C���s�I�u�W�F�N�g�̑���(Field)�̎擾</li>
     * <li>�����ϊ���ʃI�u�W�F�N�g(stringConverters)�̐���</li>
     * <li>�t�@�C���s�I�u�W�F�N�g�̑����ɑ΂���Z�b�^���\�b�h(methods)�̎擾</li>
     * <li>�Ώۃt�@�C���̏㏑���ݒ�̊m�F</li>
     * <li>�t�@�C���ւ̃X�g���[�����J��</li>
     * </ul>
     * init()��AbstracFileLineWriter���p������N���X�̃R���X�g���N�^�� �Ăԃ��\�b�h�ł���B<br>
     * ���ʌ݊����̂��߁A2��ȏ���s�ł��Ȃ��悤�ɂ��Ă���B
     */
    protected void init() {
        if (!calledInit) {
            // �t�@�C���t�H�[�}�b�g���擾����B
            FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

            buildFields();

            if (isCheckEncloseChar()) {
                // �J�������Ƃ̈͂ݕ������ݒ肳��Ă���ꍇ�A��O���X���[����B
                if (enclosed) {
                    throw new FileException(
                            "columnEncloseChar can not change.",
                            new IllegalStateException(), fileName);
                }
            }

            if (isCheckColumnAnnotationCount()) {
                // �t�@�C���s�I�u�W�F�N�g�ɃA�m�e�[�V�������ݒ肳��Ă��Ȃ��ꍇ�A��O���X���[����B
                if (fields.length == 0) {
                    throw new FileException("OutputFileColumn is not found.",
                            new IllegalStateException(), fileName);
                }
            }

            buildStringConverters();
            buildMethods();

            // �㏑���t���O���m�F
            if (fileFormat.overWriteFlg()) {
                File file = new File(fileName);
                if (file.exists()) {
                    file.delete();
                }
            }

            // �t�@�C���I�[�v��
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        (new FileOutputStream(fileName, true)), fileEncoding));
            } catch (UnsupportedEncodingException e) {
                throw new FileException("Failed in generation of writer.", e,
                        fileName);
            } catch (FileNotFoundException e) {
                throw new FileException("Failed in generation of writer.", e,
                        fileName);
            }
            calledInit = true;
        }
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑����̃t�B�[���h�I�u�W�F�N�g�̔z��𐶐�����B
     */
    @SuppressWarnings("unchecked")
    private void buildFields() {
        List<Field[]> fieldList = new ArrayList<Field[]>();

        // �t�B�[���h�I�u�W�F�N�g�𐶐�
        Class tempClass = clazz;
        Field[] declaredFieldArray = null;
        int allFieldCount = 0;
        while (tempClass != null) {
            declaredFieldArray = tempClass.getDeclaredFields();
            fieldList.add(declaredFieldArray);
            allFieldCount += declaredFieldArray.length;
            tempClass = tempClass.getSuperclass();
        }

        // �J�����C���f�b�N�X�̒�`�̏��Ԃɕ��ёւ�
        Field[] dataColumnFields = new Field[allFieldCount];

        OutputFileColumn outputFileColumn = null;
        int maxColumnIndex = -1;
        int columnIndex = -1;
        int columnCount = 0;

        for (Field[] fields : fieldList) {
            for (Field field : fields) {
                outputFileColumn = field.getAnnotation(OutputFileColumn.class);
                if (outputFileColumn != null) {
                    // �}�b�s���O�\�Ȍ^�̃t�B�[���h�Ȃ̂��m�F����B
                    if (columnFormatterMap.get(field.getType().getName()) == null) {
                        throw new FileException(
                                "There is a type which isn't supported in a "
                                        + "mapping target field in FileLineObject.",
                                new IllegalStateException(), fileName);
                    }

                    columnIndex = outputFileColumn.columnIndex();
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

        // OutputFileColumn�i�A�m�e�[�V�����j��List�I�u�W�F�N�g�Ɋi�[
        outputFileColumns = new OutputFileColumn[fields.length];
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
            outputFileColumns[i] = fields[i]
                    .getAnnotation(OutputFileColumn.class);
            columnIndexs[i] = outputFileColumns[i].columnIndex();
            columnFormats[i] = outputFileColumns[i].columnFormat();
            columnBytes[i] = outputFileColumns[i].bytes();
            paddingTypes[i] = outputFileColumns[i].paddingType();
            paddingChars[i] = outputFileColumns[i].paddingChar();
            trimTypes[i] = outputFileColumns[i].trimType();
            trimChars[i] = outputFileColumns[i].trimChar();
            // �͂ݕ����ݒ�BinputFileColumns�̐ݒ�ŏ㏑��������B
            if (outputFileColumns[i].columnEncloseChar() != Character.MIN_VALUE) {
                columnEncloseChar[i] = outputFileColumns[i].columnEncloseChar();
                enclosed = true;
            }
        }
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑����̕����ϊ���ʃI�u�W�F�N�g�̔z��𐶐�����B<br>
     */
    private void buildStringConverters() {

        // �����ϊ���ʂ̔z��𐶐�
        StringConverter[] dataColumnStringConverters = new StringConverter[fields.length];

        OutputFileColumn outputFileColumn = null;
        Class<? extends StringConverter> converterKind = null;

        for (int i = 0; i < fields.length; i++) {
            // JavaBean�̓��͗p�̃A�m�e�[�V�������擾����B
            // outputFileColumn = fields[i].getAnnotation(OutputFileColumn.class);
            outputFileColumn = outputFileColumns[i];

            // OutputFileColumn.stringConverter()�̓��e�ɂ�菈����U�蕪����B
            try {
                // �����ϊ���ʂ̃A�m�e�[�V�������擾����B
                converterKind = outputFileColumn.stringConverter();

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
                        outputFileColumn.columnIndex());

            } catch (IllegalAccessException e) {
                throw new FileLineException(
                        "Failed in an instantiate of a stringConverter.", e,
                        fileName, INITIAL_LINE_NO, fields[i].getName(),
                        outputFileColumn.columnIndex());
            }
        }
        this.stringConverters = dataColumnStringConverters;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑�����getter���\�b�h�̃��\�b�h�I�u�W�F�N�g�̔z��𐶐�����B<br>
     * �����ɑ΂���getter���\�b�h�͈ȉ��̃��[���Ō�������B<br>
     * <ul>
     * <li>�������̍ŏ��̕�����啶���ɂ���������̐擪�Ɂuget�v���������́B</li>
     * <li>is�`()�Ahas�`()�Ȃǂ�getter���\�b�h�͌����ΏۊO�ł��B</li>
     * </ul>
     * getter���\�b�h�������ł��Ȃ��ꍇ�͗�O����������B
     * @throws FileException getter���\�b�h��������Ȃ������ꍇ�B
     */
    private void buildMethods() {
        Method[] dataColumnGetMethods = new Method[fields.length];
        StringBuilder getterName = new StringBuilder();
        String fieldName = null;

        for (int i = 0; i < fields.length; i++) {
            // JavaBean���珈���̑ΏۂƂȂ鑮���̑��������擾����B
            fieldName = fields[i].getName();

            // �����������ɁAgetter���\�b�h�̖��O�𐶐�����B
            getterName.setLength(0);
            getterName.append("get");
            getterName.append(StringUtils.upperCase(fieldName.substring(0, 1)));
            getterName.append(fieldName.substring(1, fieldName.length()));

            // getter�̃��t���N�V�����I�u�W�F�N�g���擾����B
            try {
                dataColumnGetMethods[i] = clazz
                        .getMethod(getterName.toString());
            } catch (NoSuchMethodException e) {
                throw new FileException(
                        "The getter method of column doesn't exist.", e,
                        fileName);
            }
        }
        this.methods = dataColumnGetMethods;
    }

    /**
     * �w�b�_���ւ̏����ݏ����B
     * @param headerLine �w�b�_���֏������ޕ�����̃��X�g
     */
    public void printHeaderLine(List<String> headerLine) {
        if (writeData || writeTrailer) {
            throw new FileException("Header part should be called before "
                    + "data part or trailer part.",
                    new IllegalStateException(), fileName);
        }
        printList(headerLine);
    }

    /**
     * �f�[�^���ւ̏������ݏ����B
     * @param t �f�[�^���֏������ރt�@�C���s�I�u�W�F�N�g
     */
    public void printDataLine(T t) {
        checkWriteTrailer();
        // �t�@�C���������݂̏�����
        StringBuilder fileLineBuilder = new StringBuilder();

        // �Œ蒷�t�@�C���̏ꍇ
        // (��؂蕶���A�͂ݕ������Ȃ��ꍇ�͌Œ蒷�t�@�C���Ɣ��f����B)
        if (getDelimiter() == Character.MIN_VALUE
                && getEncloseChar() == Character.MIN_VALUE) {
            for (int i = 0; i < fields.length; i++) {
                fileLineBuilder.append(getColumn(t, i));
            }
        } else {
            for (int i = 0; i < fields.length; i++) {
                // �͂ݕ����A��؂蕶���̒ǉ������B
                if (columnEncloseChar[i] != Character.MIN_VALUE) {
                    fileLineBuilder.append(columnEncloseChar[i]);
                    fileLineBuilder.append(getColumn(t, i));
                    fileLineBuilder.append(columnEncloseChar[i]);
                } else {
                    fileLineBuilder.append(getColumn(t, i));
                }
                fileLineBuilder.append(getDelimiter());
            }
            // ��ԍŌ�̋�؂蕶�����폜����B
            if (fileLineBuilder.length() > 0) {
                fileLineBuilder.deleteCharAt(fileLineBuilder.length() - 1);
            }
        }

        // �s��؂蕶����ǉ�����B
        fileLineBuilder.append(getLineFeedChar());

        // �t�@�C���ւ̏������ݏ����B
        try {
            getWriter().write(fileLineBuilder.toString());
        } catch (IOException e) {
            throw new FileException("Processing of writer was failed.", e,
                    fileName);
        }
        currentLineCount++;
        setWriteData(true);
    }

    /**
     * �g���C�����ւ̏����ݏ����B
     * @param trailerLine �g���C�����֏������ޕ�����̃��X�g
     */
    public void printTrailerLine(List<String> trailerLine) {
        printList(trailerLine);
        writeTrailer = true;
    }

    /**
     * �w�b�_���A�g���C�����̏������ݗp�̋��ʃ��\�b�h�B
     * @param stringList ������̃��X�g
     */
    private void printList(List<String> stringList) {
        for (String stringData : stringList) {
            try {
                getWriter().write(stringData);
                getWriter().write(lineFeedChar);
            } catch (IOException e) {
                throw new FileException("Processing of writer was failed.", e,
                        fileName);
            }
        }
    }

    /**
     * �t�@�C���N���[�Y�����B
     */
    public void closeFile() {
        try {
            getWriter().flush();
            getWriter().close();
        } catch (IOException e) {
            throw new FileException("Closing of writer was failed.", e,
                    fileName);
        }
    }

    /**
     * <p>
     * �t�@�C���s�I�u�W�F�N�g����J�����C���f�b�N�X�ƈ�v���鑮���̒l���擾����B
     * </p>
     * <p>
     * �������擾����ہA�t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����̋L�q�ɂ�� �ȉ��̏������s���B<br>
     * <li>�g��������<br>
     * <li>�p�f�B���O<br>
     * <li>�����ϊ�����<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����ŃJ�����̃o�C�g�����w�肳��Ă���ꍇ�A<br>
     * �ԋp���镶���񂪃o�C�g���ƈ�v���Ă��邩�m�F����B
     * </p>
     * @param t �t�@�C���s�I�u�W�F�N�g
     * @param index �J�����̃C���f�b�N�X
     * @return �J�����̕�����
     */
    protected String getColumn(T t, int index) {
        // �t�@�C���ɏ������ރJ�����̕�����B
        String columnString = null;

        // �t�@�C���s�I�u�W�F�N�g(t)����J�����C���f�b�N�X�ƈ�v���鑮���̒l���擾����B
        ColumnFormatter columnFormatter = columnFormatterMap.get(methods[index]
                .getReturnType().getName());
        try {
            columnString = columnFormatter.format(t, methods[index],
                    columnFormats[index]);
        } catch (IllegalArgumentException e) {
            throw new FileLineException("Failed in column data formatting.", e,
                    fileName, currentLineCount + 1, fields[index].getName(),
                    columnIndexs[index]);
        } catch (IllegalAccessException e) {
            throw new FileLineException("Failed in column data formatting.", e,
                    fileName, currentLineCount + 1, fields[index].getName(),
                    columnIndexs[index]);
        } catch (InvocationTargetException e) {
            throw new FileLineException("Failed in column data formatting.", e,
                    fileName, currentLineCount + 1, fields[index].getName(),
                    columnIndexs[index]);
        }

        if (columnString == null) {
            columnString = "";
        }

        // �g��������
        columnString = FileDAOUtility.trim(columnString, fileEncoding,
                trimChars[index], trimTypes[index]);

        // �p�f�B���O����
        columnString = FileDAOUtility.padding(columnString, fileEncoding,
                columnBytes[index], paddingChars[index], paddingTypes[index]);

        // ������ϊ�����
        // OutputFileColumn.stringConverter()�̓��e�ɂ�菈����U�蕪����B
        columnString = stringConverters[index].convert(columnString);

        // �J�����̃o�C�g���`�F�b�N�B
        if (isCheckByte(columnBytes[index])) {
            try {
                // �Œ蒷�o�͎��ABytes�l�̐ݒ肪���̗�O
                if (columnBytes[index] <= 0) {
                    throw new FileLineException("bytes is not set "
                            + "or a number equal to or less than 0 is set.",
                            new IllegalStateException(), getFileName(),
                            currentLineCount + 1, fields[index].getName(),
                            columnIndexs[index]);
                }
                // �ݒ肳�ꂽBytes�l�ƃf�[�^�̃T�C�Y���Ⴄ�ꍇ�͗�O����
                if (columnString.getBytes(fileEncoding).length != columnBytes[index]) {
                    throw new FileLineException(
                            "The data size is different from bytes value of "
                                    + "the set value of the column .",
                            new IllegalStateException(), fileName,
                            currentLineCount + 1, fields[index].getName(),
                            columnIndexs[index]);
                }
            } catch (UnsupportedEncodingException e) {
                throw new FileException(
                        "fileEncoding which isn't supported was set.", e,
                        fileName);
            }
        }
        return columnString;
    }

    /**
     * �t�@�C�������擾����B
     * @return fileName �t�@�C����
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * �s��؂蕶����ݒ肷��B
     * @return lineFeedChar �s��؂蕶��
     */
    protected String getLineFeedChar() {
        return lineFeedChar;
    }

    /**
     * �s��؂蕶����ݒ肷��B
     * @param lineFeedChar �s��؂蕶��
     */
    protected void setLineFeedChar(String lineFeedChar) {
        this.lineFeedChar = lineFeedChar;
    }

    /**
     * �J�����t�H�[�}�b�g(�t�@�C�������j�������i�[����}�b�v���擾����B
     * @param columnFormatterMap �J�����t�H�[�}�b�g(�t�@�C�������j���i�[����}�b�v
     */
    public void setColumnFormatterMap(
            Map<String, ColumnFormatter> columnFormatterMap) {
        this.columnFormatterMap = columnFormatterMap;
    }

    /**
     * �t�@�C���A�N�Z�X�i�o�́j�p�̕����X�g���[�����擾����B
     * @return bufferedWriter �t�@�C���A�N�Z�X�i�o�́j�p�̕����X�g���[��
     */
    protected Writer getWriter() {
        return writer;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ����擾����B
     * @return fields �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ�
     */
    protected Field[] getFields() {
        return fields;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���ɑΉ�����getter���\�b�h���i�[����ϐ����擾����B
     * @return methods �t�@�C���s�I�u�W�F�N�g��Field���ɑΉ�����getter���\�b�h���i�[����ϐ�
     */
    protected Method[] getMethods() {
        return methods;
    }

    /**
     * �f�[�^���̏o�͂��J�n����Ă��邩�ǂ����𔻒肷��t���O�B
     * @param writeData �t���O
     */
    protected void setWriteData(boolean writeData) {
        this.writeData = writeData;
    }

    /**
     * �g���C�����̏������I����Ă��邩�ǂ����𔻒肷��B<br>
     * �������������Ă���ꍇ�A��O���X���[����B
     */
    protected void checkWriteTrailer() {
        if (writeTrailer) {
            throw new FileException("Header part or data part should be "
                    + "called before TrailerPart", new IllegalStateException(),
                    fileName);
        }
    }

    /**
     * ��؂蕶�����擾����B
     * @return ��؂蕶��
     */
    public abstract char getDelimiter();

    /**
     * �͂ݕ������擾����B
     * @return �͂ݕ���
     */
    public abstract char getEncloseChar();

    /**
     * �ΏۃJ�����ɑ΂���o�C�g���`�F�b�N���s������Ԃ��B
     * <p>
     * �Œ蒷�t�@�C���`���̏ꍇ�͏��<code>true</code>��ԋp���ăo�C�g���`�F�b�N���s���B<br>
     * �ϒ��A�b�r�u�`����<code>bytes</code>���w�肳��Ă���Ƃ��ɂ� true��ԋp���o�C�g���`�F�b�N���s���B
     * </p>
     * @param outputFileColumn �ΏۃJ������OutputFileColumn���
     * @return �o�C�g�����ݒ肳��Ă���(1�o�C�g�ȏ�)�ꍇ��true�B
     */
    protected boolean isCheckByte(OutputFileColumn outputFileColumn) {

        if (0 < outputFileColumn.bytes()) {
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

    /**
     * �J�����̈͂ݕ������擾����B
     * @return columnEncloseChar �͂ݕ���
     */
    protected char[] getColumnEncloseChar() {
        return columnEncloseChar;
    }
}

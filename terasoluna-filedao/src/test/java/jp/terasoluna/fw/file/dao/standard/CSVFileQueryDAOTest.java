/*
 * $Id: CSVFileQueryDAOTest.java 5576 2007-11-15 13:13:32Z pakucn $
 * 
 * Copyright (c) 2006 NTT DATA Corporation
 * 
 */

package jp.terasoluna.fw.file.dao.standard;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.dao.FileLineIterator;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.CSVFileQueryDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> CSV�t�@�C���ǎ�p��FileLineIterator�����N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileQueryDAO
 */
public class CSVFileQueryDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(CSVFileQueryDAOTest.class);
    }

    /**
     * �������������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        VMOUTUtil.initialize();
    }

    /**
     * �I���������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public CSVFileQueryDAOTest(String name) {
        super(name);
    }

    /**
     * testExecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) fileName:CSVFleQueryDAO01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:FileFormat�A�m�e�[�V���������X�^�u���g�p<br>
     * CSVFileQueryDAO_Stub01<br>
     * (���) FileQueryDAO.columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     * �v�f1<br>
     * key:"java.lang.String"<br>
     * value:ColumnParser�C���X�^���X<br>
     * CSVFileLineIterator_ColumnParserStub01�C���X�^���X<br>
     * �����<br>
     * <br>
     * ���Ғl�F(�߂�l) FileLineIterator<T>:CSVFileLineIterator<T>�̃C���X�^���X<br>
     * (��ԕω�) CSVFileLineIterator:1��Ă΂��B<br>
     * ����1�F����fileName<br>
     * ����2�F����clazz<br>
     * ����3�FFileQueryDAO.columnParserMap<br>
     * <br>
     * ����p�^�[�� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExecute01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        CSVFileQueryDAO fileQueryDAO = new CSVFileQueryDAO();

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<CSVFileQueryDAO_Stub01> clazz = CSVFileQueryDAO_Stub01.class;

        // �O������̐ݒ�
        Map<String, ColumnParser> columnParser = new HashMap<String, ColumnParser>();
        ColumnParser parser = new CSVFileQueryDAO_ColumnParserStub01();
        columnParser.put("java.lang.String", parser);
        UTUtil.setPrivateField(fileQueryDAO, "columnParserMap", columnParser);

        // �e�X�g���{
        FileLineIterator fileLineIterator = fileQueryDAO.execute(fileName,
                clazz);

        // �ԋp�l�̊m�F
        assertEquals(CSVFileLineIterator.class, fileLineIterator.getClass());

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(CSVFileLineIterator.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(CSVFileLineIterator.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnParser, arguments.get(2));
    }
}

/*
 * $Id: FixedFileQueryDAOTest.java 5576 2007-11-15 13:13:32Z pakucn $
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
 * {@link jp.terasoluna.fw.file.dao.standard.FixedFileQueryDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �Œ蒷�t�@�C���ǎ�p��FileLineIterator�����N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileQueryDAO
 */
public class FixedFileQueryDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FixedFileQueryDAOTest.class);
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
    public FixedFileQueryDAOTest(String name) {
        super(name);
    }

    /**
     * testExecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) fileName:"aaa.txt"<br>
     * (����) clazz:not null(FileFormat�A�m�e�[�V���������X�^�u���g�p)<br>
     * (���) FileQueryDAO.columnParserMap:�ȉ��̐ݒ������HashMap�̃C���X�^���X<br>
     * �v�f1<br>
     * key:"java.lang.String"<br>
     * value:ColumnParser�C���X�^���X<br>
     * FixedFileLineIterator_ColumnParserStub01�C���X�^���X<br>
     * �����<br>
     * <br>
     * ���Ғl�F(�߂�l) fileLineIterator:FixedFileLineIterator�̃C���X�^���X<br>
     * (��ԕω�) FixedFileLineIterator():FixedFileLineIterator()�̃R���X�g���N�^��1��Ă΂��B<br>
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
        FixedFileQueryDAO fileQueryDAO = new FixedFileQueryDAO();

        // �����̐ݒ�
        URL url = FixedFileLineIteratorTest.class.getResource("/aaa.txt");
        String fileName = url.getPath();
        Class<FixedFileQueryDAO_Stub01> clazz = FixedFileQueryDAO_Stub01.class;

        // �O������̐ݒ�
        Map<String, ColumnParser> columnParser = new HashMap<String, ColumnParser>();
        ColumnParser parser = new FixedFileQueryDAO_ColumnParserStub01();
        columnParser.put("java.lang.String", parser);
        UTUtil.setPrivateField(fileQueryDAO, "columnParserMap", columnParser);

        // �e�X�g���{
        FileLineIterator fileLineiterator = fileQueryDAO.execute(fileName,
                clazz);

        // �ԋp�l�̊m�F
        assertEquals(FixedFileLineIterator.class, fileLineiterator.getClass());

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(FixedFileLineIterator.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(FixedFileLineIterator.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertSame(columnParser, arguments.get(2));
    }
}

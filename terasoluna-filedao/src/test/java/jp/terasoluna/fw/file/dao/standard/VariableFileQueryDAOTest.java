/*
 * $Id:$
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
 * {@link jp.terasoluna.fw.file.dao.standard.VariableFileQueryDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �ϒ��t�@�C���ǎ�p��FileLineIterator�����N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileQueryDAO
 */
public class VariableFileQueryDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(VariableFileQueryDAOTest.class);
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
    public VariableFileQueryDAOTest(String name) {
        super(name);
    }

    /**
     * testExcecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:VariableFileQueryDAO_execute01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:VariableFileQueryDAO_Stub01<br>
     * �@�����<br>
     * (���) AbstractFileQueryDAO.columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser.java<br>
     * <br>
     * ���Ғl�F(�߂�l) FileLineIterator:VariableFileLineIterator�̃C���X�^���X<br>
     * (��ԕω�) VariableFileLineIterator:�R���X�g���N�^��1��Ă΂�邱�ƁB<br>
     * �p�����[�^�Ɉ������n����邱�ƁB<br>
     * <br>
     * ������null�ł͂Ȃ��ꍇ�AVariableFileLineIterator�̃C���X�^���X���擾�ł��邱�ƁB<br>
     * ���̃��\�b�h�́AVariableFileLineWriter�̃R���X�g���N�^���Ăяo�������Ȃ̂ŁA�����̃o���G�[�V�����͈�����s��Ȃ��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExcecute01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        VariableFileQueryDAO fileQueryDAO = new VariableFileQueryDAO();

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileQueryDAO_Stub01> clazz = VariableFileQueryDAO_Stub01.class;

        // �O������̐ݒ�
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        UTUtil
                .setPrivateField(fileQueryDAO, "columnParserMap",
                        columnParserMap);

        // �e�X�g���{
        FileLineIterator fileLineIterator = fileQueryDAO.execute(fileName,
                VariableFileQueryDAO_Stub01.class);

        // �ԋp�l�̊m�F
        assertEquals(VariableFileLineIterator.class, fileLineIterator
                .getClass());

        // ��ԕω��̊m�F
        List arguments = VMOUTUtil.getArguments(VariableFileLineIterator.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertEquals(columnParserMap, arguments.get(2));

    }

}

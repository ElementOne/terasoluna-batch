/*
 * $Id: PlainFileQueryDAOTest.java 5576 2007-11-15 13:13:32Z pakucn $
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
 * {@link jp.terasoluna.fw.file.dao.standard.PlainFileQueryDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���s�I�u�W�F�N�g���g�p���Ȃ��̃f�[�^�擾�p��FileLineIterator�����N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileQueryDAO
 */
public class PlainFileQueryDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(PlainFileQueryDAOTest.class);
    }

    /**
     * �������������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
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
    public PlainFileQueryDAOTest(String name) {
        super(name);
    }

    /**
     * testExcecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:PlainFleQueryDAO01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:PlainFileQueryDAO_Stub01<br>
     * �@�����<br>
     * (���) AbstractFileQueryDAO.columnParserMap:�ȉ��̗v�f������Map<String, ColumnParser>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnParser�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) FileLineIterator:PlainFileLineIterator�̃C���X�^���X<br>
     * (��ԕω�) PlainFileLineIterator#PlainFileLineIterator():1��Ă΂�邱�ƁB<br>
     * �������n����邱�Ƃ��m�F����B<br>
     * <br>
     * PlainFileLineIterator�C���X�^���X����������邱�Ƃ��m�F���� <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExcecute01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        PlainFileQueryDAO plainFileQueryDAO = new PlainFileQueryDAO();

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<PlainFileQueryDAO_Stub01> clazz = PlainFileQueryDAO_Stub01.class;

        // �O������̐ݒ�
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        columnParserMap.put("java.lang.String", new NullColumnParser());
        UTUtil.setPrivateField(plainFileQueryDAO, "columnParserMap",
                columnParserMap);

        // �e�X�g���{
        FileLineIterator result = plainFileQueryDAO.execute(fileName, clazz);

        // �ԋp�l�̊m�F
        assertEquals(PlainFileLineIterator.class, result.getClass());

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(PlainFileLineIterator.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(PlainFileLineIterator.class,
                "<init>", 0);
        assertSame(fileName, arguments.get(0));
        assertSame(clazz, arguments.get(1));
        assertSame(columnParserMap, arguments.get(2));
    }

}

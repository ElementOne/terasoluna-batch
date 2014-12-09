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

import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.VariableFileUpdateDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �ϒ��t�@�C���p��FileLineWriter�𐶐�����B<br>
 * AbstractFileUpdateDAO�̃T�u�N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileUpdateDAO
 */
public class VariableFileUpdateDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(VariableFileUpdateDAOTest.class);
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
    public VariableFileUpdateDAOTest(String name) {
        super(name);
    }

    /**
     * testExecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:VariableFileUpdateDAO_execute01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:VariableFileUpdateDAO_Stub01<br>
     * �@�����<br>
     * (���) AbstractFileUpdateDAO.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) FileLineWriter:VariableFileLineWriter�̃C���X�^���X<br>
     * (��ԕω�) VariableFileLineWriter:�R���X�g���N�^��1��Ă΂�邱�ƁB<br>
     * �������Ăяo���p�����[�^�ɓn���Ă��邱�ƁB<br>
     * <br>
     * ���������ꂼ��not null�ł���΁A�߂�l���A���Ă��邱�Ƃ��m�F����B<br>
     * ���̃��\�b�h�́AVariableFileLineWriter�̃R���X�g���N�^���Ăяo�������Ȃ̂ŁA�����̃o���G�[�V�����͈�����s��Ȃ��B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExecute01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        VariableFileUpdateDAO fileUpdateDAO = new VariableFileUpdateDAO();

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<VariableFileUpdateDAO_Stub01> clazz = VariableFileUpdateDAO_Stub01.class;

        // �O������̐ݒ�
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        UTUtil.setPrivateField(fileUpdateDAO, "columnFormatterMap",
                columnFormatterMap);

        // �e�X�g���{
        FileLineWriter fileLineWriter = fileUpdateDAO.execute(fileName, clazz);

        // �ԋp�l�̊m�F
        assertEquals(VariableFileLineWriter.class, fileLineWriter.getClass());

        // ��ԕω��̊m�F
        List arguments = VMOUTUtil.getArguments(VariableFileLineWriter.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertEquals(columnFormatterMap, arguments.get(2));
    }

}

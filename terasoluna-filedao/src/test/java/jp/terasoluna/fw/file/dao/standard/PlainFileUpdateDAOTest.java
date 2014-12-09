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
 * {@link jp.terasoluna.fw.file.dao.standard.PlainFileUpdateDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���s�I�u�W�F�N�g��p���Ȃ��t�@�C���o�͗p��FileLineWriter�𐶐�����B<br>
 * AbstractFileUpdateDAO�̃T�u�N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileUpdateDAO
 */
public class PlainFileUpdateDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(PlainFileUpdateDAOTest.class);
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
    public PlainFileUpdateDAOTest(String name) {
        super(name);
    }

    /**
     * testExecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:PlainFileUpdateDAO01.txt<br>
     * �@�f�[�^�������Ȃ��t�@�C���̃p�X<br>
     * (����) clazz:PlainFileUpdateDAO_Stub01<br>
     * �@�����<br>
     * (���) AbstractFileUpdateDAO.columnFormatterMap:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) FileLineWriter:PlainFileLineWriter�̃C���X�^���X<br>
     * (��ԕω�) PlainFileLineWriter#PlainFileLineWriter():�P��Ăяo����邱��<br>
     * �������n����邱��<br>
     * <br>
     * ����p�^�[��<br>
     * ���������ꂼ��not null�ł���΁A�߂�l���A���Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExecute01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        PlainFileUpdateDAO fileUpdateDAO = new PlainFileUpdateDAO();

        // �����̐ݒ�
        URL url = this.getClass().getResource("File_Empty.txt");
        String fileName = url.getPath();
        Class<PlainFileUpdateDAO_Stub01> clazz = PlainFileUpdateDAO_Stub01.class;

        // �O������̐ݒ�
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        UTUtil.setPrivateField(fileUpdateDAO, "columnFormatterMap",
                columnFormatterMap);

        // �e�X�g���{
        FileLineWriter fileLineWriter = fileUpdateDAO.execute(fileName, clazz);

        // �ԋp�l�̊m�F
        assertEquals(PlainFileLineWriter.class, fileLineWriter.getClass());

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(PlainFileLineWriter.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(PlainFileLineWriter.class,
                "<init>", 0);
        assertSame(fileName, arguments.get(0));
        assertSame(clazz, arguments.get(1));
        assertSame(columnFormatterMap, arguments.get(2));
    }
}

/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.CSVFileUpdateDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> CSV�t�@�C���p��FileLineWriter�𐶐�����B<br>
 * AbstractFileUpdateDAO�̃T�u�N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileUpdateDAO
 */
public class CSVFileUpdateDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(CSVFileUpdateDAOTest.class);
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
    public CSVFileUpdateDAOTest(String name) {
        super(name);
    }

    /**
     * testExecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE.F <br>
     * <br>
     * ���͒l�F(����) fileName:String�C���X�^���X<br>
     * (����) clazz:Class<T>�C���X�^���X<br>
     * (���) AbstractFileUpdateDAO.columnFormatterMap: HashMap�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) fileLineWriter:CSVFileLineWriter<T>�C���X�^���X<br>
     * (��ԕω�) CSVFileLineWriter#CSVFileLineWriter():1��Ă΂��B<br>
     * �Ă΂��Ƃ��̈������m�F����B<br>
     * (��ԕω�) AbstractFileUpdateDAO#getColumnFormatterMap(): 1��Ă΂��<br>
     * <br>
     * ����p�^�[��<br>
     * ���������ꂼ��not null�ł���΁A�߂�l���A���Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExecute01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        CSVFileUpdateDAO fileUpdateDAO = new CSVFileUpdateDAO();

        // �����̐ݒ�
        String fileName = "aaa";
        Class<CSVFileUpdateDAO_Stub01> clazz = CSVFileUpdateDAO_Stub01.class;

        // �O������̐ݒ�
        HashMap<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        UTUtil.setPrivateField(fileUpdateDAO, "columnFormatterMap",
                columnFormatterMap);

        // �e�X�g���{
        FileLineWriter<CSVFileUpdateDAO_Stub01> fileLineWriter = fileUpdateDAO
                .execute(fileName, clazz);

        // �ԋp�l�̊m�F
        assertEquals(CSVFileLineWriter.class, fileLineWriter.getClass());

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(CSVFileLineWriter.class,
                "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(AbstractFileUpdateDAO.class,
                "getColumnFormatterMap"));
        List arguments = VMOUTUtil.getArguments(CSVFileLineWriter.class,
                "<init>", 0);
        assertEquals(fileName, arguments.get(0));
        assertEquals(clazz, arguments.get(1));
        assertEquals(columnFormatterMap, arguments.get(2));

        // �N���[�Y����
        fileLineWriter.closeFile();
        // �e�X�g��t�@�C�����폜
        File file = new File("aaa");
        file.delete();
    }

}

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
import java.util.Map;

import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.FixedFileUpdateDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �Œ蒷�t�@�C���p��FileLineWriter�𐶐�����B<br>
 * AbstractFileUpdateDAO�̃T�u�N���X�B
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileUpdateDAO
 */
public class FixedFileUpdateDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FixedFileUpdateDAOTest.class);
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
    public FixedFileUpdateDAOTest(String name) {
        super(name);
    }

    /**
     * testExecute01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) fileName:not null ����""(�󕶎�)�łȂ�<br>
     * String�C���X�^���X<br>
     * "aaa"<br>
     * (����) clazz: not null(FileFormat�A�m�e�[�V���������X�^�u���g�p)<br>
     * (���) getColumnFormatterMap�i�j:�ȉ��̗v�f������Map<String, ColumnFormatter>�C���X�^���X<br>
     * �E"java.lang.String"=NullColumnFormatter�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) fileLineWriter:FixedFileLineWriter�̃C���X�^���X<br>
     * (��ԕω�) FixedFileLineWriter#FixedFileLineWriter(): 1��Ă΂��<br>
     * �������m�F����<br>
     * <br>
     * ����p�^�[��<br>
     * ���������ꂼ��not null�ł���΁A�߂�l���A���Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExecute01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FixedFileUpdateDAO fileUpdateDAO = new FixedFileUpdateDAO();

        // �����̐ݒ�
        String fileName = "aaa";
        Class<FixedFileUpdateDAO_Stub01> clazz = FixedFileUpdateDAO_Stub01.class;

        // �O������̐ݒ�
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();
        columnFormatterMap.put("java.lang.String", new NullColumnFormatter());
        UTUtil.setPrivateField(fileUpdateDAO, "columnFormatterMap",
                columnFormatterMap);

        // �e�X�g���{
        FileLineWriter<FixedFileUpdateDAO_Stub01> fileLineWriter = fileUpdateDAO
                .execute(fileName, clazz);

        // �ԋp�l�̊m�F
        assertEquals(FixedFileLineWriter.class.getName(), fileLineWriter
                .getClass().getName());

        // ��ԕω��̊m�F
        assertEquals(1, VMOUTUtil.getCallCount(FixedFileLineWriter.class,
                "<init>"));
        List arguments = VMOUTUtil.getArguments(FixedFileLineWriter.class,
                "<init>", 0);
        assertSame(fileName, arguments.get(0));
        assertSame(clazz, arguments.get(1));
        assertSame(columnFormatterMap, arguments.get(2));

        // �㏈��
        fileLineWriter.closeFile();
        // �e�X�g��t�@�C�����폜
        File file = new File("aaa");
        file.delete();
    }

}

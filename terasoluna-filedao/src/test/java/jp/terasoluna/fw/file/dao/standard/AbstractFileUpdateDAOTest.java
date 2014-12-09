/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao.standard;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.standard.AbstractFileUpdateDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���̃f�[�^�擾�p��FileLineWriter�𐶐�����B<br>
 * �i�e�X�g���s�̂��߁A�X�^�u��p�ӂ���j
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.AbstractFileUpdateDAO
 */
public class AbstractFileUpdateDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(AbstractFileUpdateDAOTest.class);
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
    public AbstractFileUpdateDAOTest(String name) {
        super(name);
    }

    /**
     * testSetColumnFormatterMap01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) columnFormatterMap: Map<String, ColumnFormatter>�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.columnFormatterMap:�����Ŏw�肵��Map<String, ColumnFormatter>�C���X�^���X<br>
     * <br>
     * columnFormatterMap��setter���\�b�h�̒l�� �������ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetColumnFormatterMap01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        AbstractFileUpdateDAO abstractFileUpdateDAO = new AbstractFileUpdateDAO_Stub01();

        // �����̐ݒ�
        Map<String, ColumnFormatter> columnFormatterMap = new HashMap<String, ColumnFormatter>();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        abstractFileUpdateDAO.setColumnFormatterMap(columnFormatterMap);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(abstractFileUpdateDAO,
                "columnFormatterMap");
        assertSame(columnFormatterMap, result);
    }

    /**
     * testGetColumnFormatterMap01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) columnFormatterMap: Map<String, ColumnFormatter>�C���X�^���Xl<br>
     * <br>
     * ���Ғl�F(�߂�l) columnFormatterMap: Map<String, ColumnFormatter>�C���X�^���X<br>
     * <br>
     * columnFormatterMap��getter���\�b�h���������l�� �擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetColumnFormatterMap01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        AbstractFileUpdateDAO abstractFileUpdateDAO = new AbstractFileUpdateDAO_Stub01();

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<String, ColumnFormatter> textGetterMap = new HashMap<String, ColumnFormatter>();
        UTUtil.setPrivateField(abstractFileUpdateDAO, "columnFormatterMap",
                textGetterMap);

        // �e�X�g���{
        Map<String, ColumnFormatter> result = abstractFileUpdateDAO
                .getColumnFormatterMap();

        // �ԋp�l�̊m�F
        assertSame(textGetterMap, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

}

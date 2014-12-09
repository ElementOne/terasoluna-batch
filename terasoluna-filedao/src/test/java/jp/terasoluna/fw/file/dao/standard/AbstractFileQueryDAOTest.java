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
 * {@link jp.terasoluna.fw.file.dao.standard.AbstractFileQueryDAO} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���̃f�[�^�擾�p��FileLineIterator�𐶐�����B<br>
 * �i�e�X�g���s�̂��߁A�X�^�ul��p�ӂ���j
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.standard.AbstractFileQueryDAO
 */
public class AbstractFileQueryDAOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(AbstractFileQueryDAOTest.class);
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
    public AbstractFileQueryDAOTest(String name) {
        super(name);
    }

    /**
     * testSetColumnParserMap01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) columnParserMap:Map<String, ColumnParser>�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.columnParserMap:�����Ŏw�肵�� Map<String, ColumnParser>�C���X�^���X<br>
     * <br>
     * columnParserMap��setter���\�b�h�̒l���������ݒ肳��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetColumnParserMap01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        AbstractFileQueryDAO abstractFileQueryDAO = new AbstractFileQueryDAO_Stub01();

        // �����̐ݒ�
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        abstractFileQueryDAO.setColumnParserMap(columnParserMap);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        Object result = UTUtil.getPrivateField(abstractFileQueryDAO,
                "columnParserMap");
        assertSame(columnParserMap, result);
    }

    /**
     * testGetColumnParserMap01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) this.columnParserMap:Map<String, ColumnParser>�C���X�^���X<br>
     * <br>
     * ���Ғl�F(�߂�l) columnParserMap:Map<String, ColumnParser>�C���X�^���X<br>
     * <br>
     * columnParserMap��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetColumnParserMap01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        AbstractFileQueryDAO abstractFileQueryDAO = new AbstractFileQueryDAO_Stub01();

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        Map<String, ColumnParser> columnParserMap = new HashMap<String, ColumnParser>();
        UTUtil.setPrivateField(abstractFileQueryDAO, "columnParserMap",
                columnParserMap);

        // �e�X�g���{
        Map<String, ColumnParser> result = abstractFileQueryDAO
                .getColumnParserMap();

        // �ԋp�l�̊m�F
        assertSame(columnParserMap, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

}

/*
 * $Id:$
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */

package jp.terasoluna.fw.file.dao;

import java.util.List;

import jp.terasoluna.fw.file.ut.VMOUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.file.dao.FileLineException} �N���X�̃e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> �t�@�C���A�N�Z�X�@�\�Ŕ��������O�����b�v����RuntimeException
 * <p>
 * @author ���c�N�i
 * @see jp.terasoluna.fw.file.dao.FileLineException
 */
public class FileLineExceptionTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        // junit.swingui.TestRunner.run(FileLineExceptionTest.class);
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
    public FileLineExceptionTest(String name) {
        super(name);
    }

    /**
     * testFileLineExceptionException01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) e:not null<br>
     * Exception�̃C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) columnName:null<br>
     * (��ԕω�) columnIndex:-1<br>
     * (��ԕω�) lineNo:-1<br>
     * (��ԕω�) FileLineException.cause: ����e�Ɠ����̃I�u�W�F�N�g�B<br>
     * (��ԕω�) FileException:1��Ă΂��B<br>
     * <br>
     * �R���X�g���N�^�̈����Ŏ󂯎����Exception�����b�v���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionException01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        Exception exception = new Exception();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileLineException fe = new FileLineException(exception);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertNull(UTUtil.getPrivateField(fe, "columnName"));
        assertEquals(-1, UTUtil.getPrivateField(fe, "columnIndex"));
        assertEquals(-1, UTUtil.getPrivateField(fe, "lineNo"));
        assertSame(exception, fe.getCause());

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(1, arguments.size());
        assertSame(exception, arguments.get(0));
    }

    /**
     * testFileLineExceptionString01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:not null<br>
     * ���String�C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) columnName:null<br>
     * (��ԕω�) columnIndex:-1<br>
     * (��ԕω�) lineNo:-1<br>
     * (��ԕω�) FileLineException.detailMessage: ����message�Ɠ����̃I�u�W�F�N�g�B<br>
     * (��ԕω�) FileException:1��Ă΂��B<br>
     * <br>
     * �R���X�g���N�^�����ɗ�O���b�Z�[�W��ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀ��b�Z�[�W���i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionString01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String message = new String();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileLineException fe = new FileLineException(message);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertNull(UTUtil.getPrivateField(fe, "columnName"));
        assertEquals(-1, UTUtil.getPrivateField(fe, "columnIndex"));
        assertEquals(-1, UTUtil.getPrivateField(fe, "lineNo"));
        assertEquals(message, fe.getMessage());

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(1, arguments.size());
        assertSame(message, arguments.get(0));
    }

    /**
     * testFileLineExceptionStringException01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:not null<br>
     * ���String�C���X�^���X<br>
     * (����) exceotion:not null<br>
     * Exception�̃C���X�^���X<br>
     * <br>
     * ���Ғl�F(��ԕω�) columnName:null<br>
     * (��ԕω�) columnIndex:-1<br>
     * (��ԕω�) lineNo:-1<br>
     * (��ԕω�) FileLineException.detailMessage: ����message�Ɠ����̃I�u�W�F�N�g�B<br>
     * (��ԕω�) FileLineException.caouse: ����exception�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileException:1��Ă΂��B<br>
     * <br>
     * �R���X�g���N�^�����ɗ�O���b�Z�[�W��ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀ��b�Z�[�W���i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionStringException01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String message = new String();
        Exception exception = new Exception();

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileLineException fe = new FileLineException(message, exception);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertNull(UTUtil.getPrivateField(fe, "columnName"));
        assertEquals(-1, UTUtil.getPrivateField(fe, "columnIndex"));
        assertEquals(-1, UTUtil.getPrivateField(fe, "lineNo"));
        assertSame(message, fe.getMessage());
        assertSame(exception, fe.getCause());

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(2, arguments.size());
        assertSame(message, arguments.get(0));
        assertSame(exception, arguments.get(1));
    }

    /**
     * testFileLineExceptionExceptionStringint01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) exception:not null<br>
     * Exception�C���X�^���X<br>
     * (����) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * (����) lineNo:1<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.colmnName:null<br>
     * (��ԕω�) this.columnIndex:-1<br>
     * (��ԕω�) this.lineNo:1<br>
     * (��ԕω�) FileLineException.cause: ����exception�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.fileName: ����fileName�Ɠ����I�u�W�F�N�g<br>
     * (��ԕω�) FileException:1��Ă΂��B<br>
     * <br>
     * �R���X�g���N�^�����Ƀt�@�C������ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀt�@�C�������i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionExceptionStringint01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileLineException fe = new FileLineException(exception, fileName,
                lineNo);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertNull(UTUtil.getPrivateField(fe, "columnName"));
        assertEquals(-1, UTUtil.getPrivateField(fe, "columnIndex"));
        assertEquals(1, UTUtil.getPrivateField(fe, "lineNo"));
        assertSame(exception, fe.getCause());
        assertSame(fileName, UTUtil.getPrivateField(fe, "fileName"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(2, arguments.size());
        assertSame(exception, arguments.get(0));
        assertSame(fileName, arguments.get(1));
    }

    /**
     * testFileLineExceptionStringExceptionStringint01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:���String�C���X�^���X<br>
     * (����) e:Exception�C���X�^���X<br>
     * (����) fileName:���String�C���X�^���X<br>
     * (����) lineNo:1<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileLineException.cause: ����e�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.fileName: ����fileName�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.detailMessage: ����message�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) lineNo:����lineNo�Ɠ����l<br>
     * (��ԕω�) FileException:1��Ă΂��B<br>
     * <br>
     * �R���X�g���N�^�����Ƀt�@�C������ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀt�@�C�������i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionStringExceptionStringint01()
                                                                 throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(exception, fe.getCause());
        assertSame(fileName, UTUtil.getPrivateField(fe, "fileName"));
        assertSame(message, fe.getMessage());
        assertEquals(1, UTUtil.getPrivateField(fe, "lineNo"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(3, arguments.size());
        assertSame(message, arguments.get(0));
        assertSame(exception, arguments.get(1));
        assertSame(fileName, arguments.get(2));
    }

    /**
     * testFileLineExceptionExceptionStringintStringint01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) exception:not null<br>
     * Exception�C���X�^���X<br>
     * (����) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * (����) lineNo:1<br>
     * (����) columnName:not null<br>
     * ���String�C���X�^���X<br>
     * (����) columnIndex:1<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileLineException.cause: ����exception�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.fileName: ����fileName�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.lineNo: ����lineNo�Ɠ����l<br>
     * (��ԕω�) FileLineException.columnName: ����columnName�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.columnIndex: ����columnIndex�Ɠ����l<br>
     * (��ԕω�) FileException:1��Ă΂��B<br>
     * <br>
     * �R���X�g���N�^�����Ƀt�@�C������ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀt�@�C�������i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionExceptionStringintStringint01()
                                                                    throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileLineException fe = new FileLineException(exception, fileName,
                lineNo, columnName, columnIndex);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(exception, fe.getCause());
        assertEquals(fileName, UTUtil.getPrivateField(fe, "fileName"));
        assertEquals(lineNo, UTUtil.getPrivateField(fe, "lineNo"));
        assertEquals(columnName, UTUtil.getPrivateField(fe, "columnName"));
        assertEquals(columnIndex, UTUtil.getPrivateField(fe, "columnIndex"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(2, arguments.size());
        assertSame(exception, arguments.get(0));
        assertSame(fileName, arguments.get(1));
    }

    /**
     * testFileLineExceptionStringExceptionStringintStringint01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) message:not null<br>
     * ���String�C���X�^���X<br>
     * (����) exception:not null<br>
     * Exception�C���X�^���X<br>
     * (����) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * (����) lineNo:1<br>
     * (����) columnName:not null<br>
     * ���String�C���X�^���X<br>
     * (����) columnIndex:1<br>
     * <br>
     * ���Ғl�F(��ԕω�) FileLineException.message: ����message�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.cause: ����exception�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.fileName: ����fileName�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.lineNo: ����lineNo�Ɠ����l<br>
     * (��ԕω�) FileLineException.columnName: ����columnName�Ɠ����̃I�u�W�F�N�g<br>
     * (��ԕω�) FileLineException.columnIndex: ����columnIndex�Ɠ����l<br>
     * (��ԕω�) FileException:1��Ă΂��B<br>
     * <br>
     * �R���X�g���N�^�����Ƀt�@�C������ݒ肷��B<br>
     * ��O�I�u�W�F�N�g������Ƀt�@�C�������i�[����Ă��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testFileLineExceptionStringExceptionStringintStringint01()
                                                                          throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        // �R���X�g���N�^�Ȃ̂ŕs�v

        // �����̐ݒ�
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo, columnName, columnIndex);

        // �ԋp�l�̊m�F
        // �Ȃ�

        // ��ԕω��̊m�F
        assertSame(message, fe.getMessage());
        assertSame(exception, fe.getCause());
        assertEquals(fileName, UTUtil.getPrivateField(fe, "fileName"));
        assertEquals(lineNo, UTUtil.getPrivateField(fe, "lineNo"));
        assertEquals(columnName, UTUtil.getPrivateField(fe, "columnName"));
        assertEquals(columnIndex, UTUtil.getPrivateField(fe, "columnIndex"));

        assertTrue(VMOUTUtil.isCalled(FileException.class, "<init>"));
        assertEquals(1, VMOUTUtil.getCallCount(FileException.class, "<init>"));
        List arguments = VMOUTUtil.getArguments(FileException.class, "<init>",
                0);
        assertEquals(3, arguments.size());
        assertSame(message, arguments.get(0));
        assertSame(exception, arguments.get(1));
        assertSame(fileName, arguments.get(2));
    }

    /**
     * testGetColumnName01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) message:not null<br>
     * ���String�C���X�^���X<br>
     * (���) exception:not null<br>
     * Exception�C���X�^���X<br>
     * (���) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * (���) lineNo:1<br>
     * (���) columnName:not null<br>
     * ���String�C���X�^���X<br>
     * (���) columnIndex:0<br>
     * <br>
     * ���Ғl�F(�߂�l) String:�O�������columnName�Ɠ���̃I�u�W�F�N�g<br>
     * <br>
     * columnName��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testGetColumnName01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo, columnName, columnIndex);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        String result = fe.getColumnName();

        // �ԋp�l�̊m�F
        assertSame(columnName, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetLineNo01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) message:not null<br>
     * ���String�C���X�^���X<br>
     * (���) exception:not null<br>
     * Exception�C���X�^���X<br>
     * (���) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * (���) lineNo:1<br>
     * (���) columnName:not null<br>
     * ���String�C���X�^���X<br>
     * (���) columnIndex:0<br>
     * <br>
     * ���Ғl�F(�߂�l) int:�O�������lineNo�Ɠ����l<br>
     * <br>
     * lineNo��getter���\�b�h���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testGetLineNo01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        String message = new String();
        Exception exception = new Exception();
        String fileName = new String();
        int lineNo = 1;
        String columnName = new String();
        int columnIndex = 1;
        FileLineException fe = new FileLineException(message, exception,
                fileName, lineNo, columnName, columnIndex);

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        // �Ȃ�

        // �e�X�g���{
        int result = fe.getLineNo();

        // �ԋp�l�̊m�F
        assertEquals(lineNo, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

    /**
     * testGetColumnIndex01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(���) message:not null<br>
     * ���String�C���X�^���X<br>
     * (���) exception:not null<br>
     * Exception�C���X�^���X<br>
     * (���) fileName:not null<br>
     * ���String�C���X�^���X<br>
     * (���) lineNo:-1<br>
     * (���) columnName:not null<br>
     * ���String�C���X�^���X<br>
     * (���) columnIndex:1<br>
     * <br>
     * ���Ғl�F(�߂�l) int:�O�������columnIndex�Ɠ����l<br>
     * <br>
     * �����̒l���������l���擾���邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */

    public void testGetColumnIndex01() throws Exception {
        // �e�X�g�Ώۂ̃C���X�^���X��
        FileLineException fe = new FileLineException(new String());

        // �����̐ݒ�
        // �Ȃ�

        // �O������̐ݒ�
        int columnIndex = 1;
        UTUtil.setPrivateField(fe, "columnIndex", columnIndex);

        // �e�X�g���{
        int result = fe.getColumnIndex();

        // �ԋp�l�̊m�F
        assertEquals(columnIndex, result);

        // ��ԕω��̊m�F
        // �Ȃ�
    }

}

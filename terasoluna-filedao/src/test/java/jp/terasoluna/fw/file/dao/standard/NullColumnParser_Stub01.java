/*
 * $Id: NullColumnParser_Stub01.java 6004 2008-01-11 10:18:33Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */
package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����������A���������t�@�C���s�I�u�W�F�N�g�X�^�u
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat()
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex = 0) <br>
 * @OutputFileColumn(columnIndex = 0) <br>
 *                               a</li> <li>@InputFileColumn(columnIndex = 1) <br>
 * @OutputFileColumn(columnIndex = 1) <br>
 *                               b</li>
 *                               </ul>
 *                               </ul>
 */
@FileFormat()
public class NullColumnParser_Stub01 {

    @SuppressWarnings("unused")
    @InputFileColumn(columnIndex = 0)
    @OutputFileColumn(columnIndex = 0)
    private String a;

    @SuppressWarnings("unused")
    @InputFileColumn(columnIndex = 1)
    @OutputFileColumn(columnIndex = 1)
    private String b;

    public void setA(String a) {
        this.a = a;
    }

    @SuppressWarnings("unused")
    private void setAPrivate(String a) {
        this.a = a;
    }

    public void setAException(String a) throws Exception {
        throw new Exception();
    }

    public void setAAndB(String a, String b) {
        this.a = a;
        this.b = b;
    }
}

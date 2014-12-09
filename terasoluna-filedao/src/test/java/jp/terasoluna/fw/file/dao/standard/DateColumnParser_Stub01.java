/*
 * $Id: DateColumnParser_Stub01.java 6004 2008-01-11 10:18:33Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */
package jp.terasoluna.fw.file.dao.standard;

import java.util.Date;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

/**
 * FileFormat�A�m�e�[�V�����̐ݒ�����A�t�@�C���s�I�u�W�F�N�g�X�^�u�N���X
 * <p>
 * �ȉ��̐ݒ������<br>
 * <ul>
 * <li>@FileFormat()
 * <li>����
 * <ul>
 * <li>@InputFileColumn(columnIndex=0)<br>
 * Date a
 * <li>@InputFileColumn(columnIndex=1)<br>
 * Date b
 * </ul>
 * </ul>
 * <p>
 * ������public�ň���1��setter�����B
 */

@FileFormat()
public class DateColumnParser_Stub01 {

    @SuppressWarnings("unused")
    @InputFileColumn(columnIndex = 0)
    private Date a;

    @SuppressWarnings("unused")
    @InputFileColumn(columnIndex = 1)
    private Date b;

    public void setA(Date a) {
        this.a = a;
    }

    @SuppressWarnings("unused")
    private void setAPrivate(Date a) {
        this.a = a;
    }

    public void setAException(Date a) throws Exception {
        throw new Exception();
    }

    public void setAAndB(Date a, Date b) {
        this.a = a;
        this.b = b;
    }
}

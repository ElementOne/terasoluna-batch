/*
 * $Id: FileContent.java 5230 2007-09-28 10:04:13Z anh $
 *
 * Copyright (c) 2006 NTT DATA Corporation
 *
 */
package jp.terasoluna.fw.file.ut;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * �t�@�C���̒��g���o�C�i����r���邽�߂̃��b�p�[�N���X�B <BR>
 * <BR>
 * �ʏ��UTUtil.assertEqualsFile()���\�b�h������̂ŁA���̃N���X�𒼐� �g���K�v���͏��Ȃ��Ǝv����B
 * @author �ؑ��^�K
 * @version 2003.08.20
 */
public class FileContent {

    /**
     * �ێ�����t�@�C���I�u�W�F�N�g�B
     */
    private File file = null;

    /**
     * �R���X�g���N�^�B
     * @param file ���g���r�������t�@�C��
     */
    public FileContent(File file) {
        this.file = file;
    }

    /**
     * �ێ����Ă���File�I�u�W�F�N�g���擾����B
     * @return File�I�u�W�F�N�g
     */
    public File getFile() {
        return file;
    }

    /**
     * �n�b�V���R�[�h��Ԃ��B
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        long hash = 0L;
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(getFile());
            for (long i = 0; i < getFile().length(); i++) {
                int input = stream.read();
                if (input == -1) {
                    printReadBytes(i);
                    break;
                }
                hash = (hash << 1) + hash + (long) input;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (int) ((hash >> 32) + hash);
    }

    /**
     * �t�@�C�����o�C�i����r����B
     * @param other ��r�ΏƂ̃t�@�C��
     * @return ��r���ʁB����Ȃ�true�A�قȂ��false��Ԃ��B
     */
    public boolean equals(FileContent other) {
        if (hashCode() != other.hashCode()) {
            printHashCode();
            other.printHashCode();
            return false;
        }
        if (getFile().length() != other.getFile().length()) {
            printLength();
            other.printLength();
            return false;
        }

        FileInputStream stream1 = null;
        FileInputStream stream2 = null;
        try {
            stream1 = new FileInputStream(getFile());
            stream2 = new FileInputStream(other.getFile());
            for (long i = 0; i < getFile().length(); i++) {
                int input1 = stream1.read();
                int input2 = stream2.read();
                if (input1 != input2) {
                    System.out.println("first " + i + " bytes are same.");
                    return false;
                }
                if (input1 == -1) {
                    printReadBytes(i);
                    break;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stream1 != null) {
                    stream1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (stream2 != null) {
                    stream2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * toString�̃I�[�o�[���C�h�B�t�@�C���̃p�X��Ԃ������ɂȂ��Ă���B
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getFile().toString();
    }

    /**
     * �ǂݍ��񂾃o�C�g����W���o�͂ɏo�͂���B
     * @param bytes �ǂݍ��񂾃o�C�g��
     */
    private void printReadBytes(long bytes) {
        printLength();
        System.out.println("but only " + bytes + " bytes can be read.");
    }

    /**
     * �n�b�V���R�[�h��W���o�͂ɏo�͂���B
     */
    private void printHashCode() {
        System.out.println("hashCode of " + toString() + " is " + hashCode());
    }

    /**
     * �t�@�C���̒�����W���o�͂ɏo�͂���B
     */
    private void printLength() {
        System.out
                .println(toString() + " is " + getFile().length() + " bytes.");
    }
}

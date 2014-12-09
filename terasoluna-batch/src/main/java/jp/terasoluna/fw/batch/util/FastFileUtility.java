/*
 * Copyright (c) 2011 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.batch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.util.FileUtility;

/**
 * �t�@�C���R�s�[�������ɍs�����[�e�B���e�B�B<br>
 * <br>
 * TERASOLUNA�o�b�`�t���[�����[�N��FileUtility�ɂ��R�s�[���s���ƁA�t�@�C�����b�N�A�`�����N1024�o�C�g�ɂ��o�C�g�ڑ����ɂ��A����NFS�ւ̃t�@�C���R�s�[�ɂĐ��\�򉻂���������B<br>
 * ���̂��߁ANFS���g�p�����t�@�C���R�s�[�Ő��\���o�Ȃ��ꍇ�� ���N���X�ɂ��t�@�C���R�s�[���s�����ƁB
 */
public class FastFileUtility extends FileUtility {

    /**
     * �t�@�C�����R�s�[����B
     * <p>
     * �R�s�[���̃t�@�C���̃p�X���󂯎��A �R�s�[��̃p�X�Ƀt�@�C�����R�s�[����B<br>
     * �R�s�[��Ƀt�@�C�������݂���ꍇ�A�㏑���ŃR�s�[�����B<br>
     * </p>
     * @param srcFile �R�s�[���̃t�@�C���̃p�X
     * @param newFile �R�s�[��̃t�@�C���̃p�X
     * @throws �t�@�C���@�\��O
     */
    public static void copyFile(String srcFile, String newFile) {

        if (srcFile == null) {
            throw new FileException("srcFile is null.");
        }
        if (newFile == null) {
            throw new FileException("newFile is null.");
        }

        File srcFileObject = new File(srcFile);
        // �R�s�[���̃p�X�Ƀt�@�C�������݂��Ȃ��ꍇ�A�G���[�𓊂��ď������I������B
        if (!srcFileObject.exists()) {
            throw new FileException(srcFile + " is not exist.");
        }
        File outputFileObject = new File(newFile);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel ic = null;
        FileChannel oc = null;

        try {
            fis = new FileInputStream(srcFileObject);
            fos = new FileOutputStream(outputFileObject);
            ic = fis.getChannel();
            oc = fos.getChannel();
            ic.transferTo(0, ic.size(), oc);
        } catch (IOException e) {
            throw new FileException("File control operation was failed.", e);
        } finally {
            closeQuietly(oc);
            closeQuietly(ic);
            closeQuietly(fos);
            closeQuietly(fis);
        }
    }

    /**
     * Channel���N���[�Y����B<br>
     * <p>
     * �����ɓn���ꂽchannel��null�łȂ���΃N���[�Y����B<br>
     * �܂��A�N���[�Y����ۂ�IOException��O�����������ꍇ�͖�������B<br>
     * </p>
     * @param channel Channel
     */
    public static void closeQuietly(Channel channel) {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException e) {
            // �Ȃɂ����Ȃ�
        }
    }

    /**
     * FileInputStream���N���[�Y����B<br>
     * <p>
     * �����ɓn���ꂽstream��null�łȂ���΃N���[�Y����B<br>
     * �܂��A�N���[�Y����ۂ�IOException��O�����������ꍇ�͖�������B<br>
     * </p>
     * @param stream FileInputStream
     */
    public static void closeQuietly(FileInputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // �Ȃɂ����Ȃ�
        }
    }

    /**
     * FileOutputStream���N���[�Y����B<br>
     * <p>
     * �����ɓn���ꂽstream��null�łȂ���΃N���[�Y����B<br>
     * �܂��A�N���[�Y����ۂ�IOException��O�����������ꍇ�͖�������B<br>
     * </p>
     * @param fos FileOutputStream
     */
    public static void closeQuietly(FileOutputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // �Ȃɂ����Ȃ�
        }
    }
}

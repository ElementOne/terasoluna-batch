/*
 * Copyright (c) 2007 NTT DATA Corporation
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

package jp.terasoluna.fw.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;

import jp.terasoluna.fw.file.dao.FileException;

/**
 * �t�@�C������@�\����������N���X�B
 * <p>
 * ���̃N���X�́A�r�W�l�X���W�b�N���璼�ڗ��p���邱�Ƃ��\�ł���B<br>
 * FileUtility�N���X�͈ȉ��̋@�\���������Ă���B
 * <ul>
 * <li>�t�@�C�����̕ύX��t�@�C���̈ړ�</li>
 * <li>�t�@�C���̃R�s�[</li>
 * <li>�t�@�C���̍폜</li>
 * <li>�t�@�C���̌���</li>
 * </ul>
 * �Ȃ��A�t�@�C���@�\�Ŏg�p����p�X�͑��΃p�X�A��΃p�X�̗����𗘗p�\�����A<br>
 * �{�N���X�𒼐ڗ��p����ꍇ�Ɍ����΃p�X�݂̂������p�ł��Ȃ��B<br>
 * ���΃p�X�𗘗p�������ꍇ�́A<code>FileControlImpl</code>�N���X�ɂ�� �{�N���X�����b�v���ė��p���邱�ƁB
 * </p>
 */
public class FileUtility {

    /**
     * �t�@�C���̑��݂��邩�ǂ����������t���O
     */
    private static boolean checkFileExist = false;

    /**
     * �t�@�C�����R�s�[����B
     * <p>
     * �R�s�[���̃t�@�C���̃p�X���󂯎��A �R�s�[��̃p�X�Ƀt�@�C�����R�s�[����B<br>
     * �R�s�[��Ƀt�@�C�������݂���ꍇ�A���̃t�@�C�����폜������A �t�@�C���̃R�s�[�����s����B<br>
     * �R�s�[���̃p�X�Ƀt�@�C�������݂��Ȃ��ꍇ�A�񌟍���O���X���[����B<br>
     * �t�@�C���̃R�s�[�Ɏ��s�����ꍇ�A�񌟍���O���X���[����B
     * </p>
     * @param srcFile �R�s�[���̃t�@�C���̃p�X
     * @param newFile �R�s�[��̃t�@�C���̃p�X
     * @throws �t�@�C���@�\��O
     */
    public static void copyFile(String srcFile, String newFile) {

        checkAbsolutePath(srcFile);
        checkAbsolutePath(newFile);

        File srcFileObject = new File(srcFile);
        // �R�s�[���̃p�X�Ƀt�@�C�������݂��Ȃ��ꍇ�A�G���[�𓊂��ď������I������B
        if (!srcFileObject.exists()) {
            throw new FileException(srcFile + " is not exist.", srcFile);
        }

        File newFileObject = new File(newFile);
        // �ړ���̃t�@�C�������݂���ꍇ�A���̃t�@�C�����폜����B
        if (newFileObject.exists() && checkFileExist) {
            boolean result = newFileObject.delete();
            if (!result) {
                throw new FileException("File control operation was failed.",
                        newFile);
            }
        } else if (newFileObject.exists() && !checkFileExist) {
            throw new FileException(newFile + " is exist.", newFile);
        }

        FileOutputStream fos = null;
        FileChannel outputFileChannel = null;
        FileInputStream ios = null;
        FileChannel inputFileChannel = null;
        FileLock inputFileLock = null;
        FileLock outputFileLock = null;
        try {
            fos = new FileOutputStream(newFileObject, true);
            outputFileChannel = fos.getChannel();

            ios = new FileInputStream(srcFileObject);
            inputFileChannel = ios.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            inputFileLock = inputFileChannel.lock(0L, Long.MAX_VALUE, true);
            outputFileLock = outputFileChannel.lock(0L, Long.MAX_VALUE, false);

            while (inputFileChannel.position() < inputFileChannel.size()) {
                buffer.clear();
                inputFileChannel.read(buffer);
                buffer.flip();
                outputFileChannel.write(buffer);
            }
        } catch (FileNotFoundException e) {
            throw new FileException("File control operation was failed.", e);
        } catch (IOException e) {
            throw new FileException("File control operation was failed.", e);
        } finally {
            try {
                if (inputFileLock != null) {
                    inputFileLock.release();
                }
                if (outputFileLock != null) {
                    outputFileLock.release();
                }

                if (ios != null) {
                    ios.close();
                }

                if (fos != null) {
                    fos.close();
                }

                if (outputFileChannel != null) {
                    outputFileChannel.close();
                }

                if (inputFileChannel != null) {
                    inputFileChannel.close();
                }
            } catch (IOException e) {
                // �������Ȃ��B(��O�𖳎�����)
            }
        }
    }

    /**
     * �t�@�C���폜�B
     * <p>
     * �폜����t�@�C���̃p�X���󂯎��A�t�@�C�����폜����B<br>
     * �폜����t�@�C�������݂��Ȃ��ꍇ�A�񌟍���O���X���[����B<br>
     * �폜�Ɏ��s�����ꍇ�A�񌟍���O���X���[����B
     * </p>
     * @param srcFile �폜����t�@�C���̃p�X
     * @throws �t�@�C���@�\��O
     */
    public static void deleteFile(String srcFile) {

        checkAbsolutePath(srcFile);

        File srcFileObject = new File(srcFile);

        // �폜�Ώۂ̃t�@�C�������݂��Ȃ��ꍇ�A�G���[�𓊂��ď������I������B
        if (!srcFileObject.exists()) {
            throw new FileException(srcFile + " is not exist.", srcFile);
        }

        boolean result = srcFileObject.delete();

        if (!result) {
            throw new FileException("File control operation was failed.",
                    srcFile);
        }
    }

    /**
     * �t�@�C�������B
     * <p>
     * ��������t�@�C���̃��X�g���󂯎��A�t�@�C������������B<br>
     * �������ĐV�����쐬����t�@�C���̃p�X�ɁA �����J�n�܂łɃt�@�C�������݂����ꍇ�A ���̃t�@�C�����폜�����̂��A�t�@�C������������B<br>
     * ��������t�@�C�����X�g�Ɋ܂܂��t�@�C�������݂��Ȃ��ꍇ�A �񌟍���O���X���[����B<br>
     * �t�@�C���̌����Ɏ��s�����ꍇ�A�񌟍���O���X���[����B
     * </p>
     * @param fileList ��������t�@�C���̃��X�g
     * @param newFile �������Ăł���t�@�C���̃p�X
     * @throws �t�@�C���@�\��O
     */
    public static void mergeFile(List<String> fileList, String newFile) {

        checkAbsolutePath(newFile);

        File newFileObject = new File(newFile);

        // �ړ���̃t�@�C�������݂���ꍇ�A���̃t�@�C�����폜����B
        if (newFileObject.exists() && checkFileExist) {
            boolean result = newFileObject.delete();
            if (!result) {
                throw new FileException("File control operation was failed.",
                        newFile);
            }
        } else if (newFileObject.exists() && !checkFileExist) {
            throw new FileException(newFile + " is exist.", newFile);
        }

        FileOutputStream fos = null;
        FileChannel outputFileChannel = null;
        FileLock outputFileLock = null;

        try {
            fos = new FileOutputStream(newFileObject, true);
            outputFileChannel = fos.getChannel();
            outputFileLock = outputFileChannel.lock(0L, Long.MAX_VALUE, false);

            File srcFileObject = null;

            for (String srcFile : fileList) {

                checkAbsolutePath(srcFile);

                srcFileObject = new File(srcFile);

                // �}�[�W���̃t�@�C�������݂��Ȃ��ꍇ�A�G���[�𓊂��ď������I������B
                if (!srcFileObject.exists()) {
                    throw new FileException(srcFile + " is not exist.", srcFile);
                }

                FileInputStream fis = null;
                FileChannel inputFileChannel = null;
                FileLock inputFileLock = null;

                try {
                    fis = new FileInputStream(srcFileObject);
                    inputFileChannel = fis.getChannel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    inputFileLock = inputFileChannel.lock(0L, Long.MAX_VALUE,
                            true);

                    while (inputFileChannel.position() < inputFileChannel
                            .size()) {
                        buffer.clear();
                        inputFileChannel.read(buffer);
                        buffer.flip();
                        outputFileChannel.write(buffer);
                    }
                } finally {
                    if (inputFileLock != null) {
                        inputFileLock.release();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                    if (inputFileChannel != null) {
                        inputFileChannel.close();
                    }
                }

            }
        } catch (FileNotFoundException e) {
            throw new FileException("File control operation was failed.", e);
        } catch (IOException e) {
            throw new FileException("File control operation was failed.", e);
        } finally {
            try {
                if (outputFileLock != null) {
                    outputFileLock.release();
                }

                if (fos != null) {
                    fos.close();
                }

                if (outputFileChannel != null) {
                    outputFileChannel.close();
                }

            } catch (IOException e) {
                // �������Ȃ��B(��O�𖳎�����)
            }

        }
    }

    /**
     * �t�@�C�����̕ύX��t�@�C���̈ړ��B
     * <p>
     * �ړ����̃t�@�C���̃p�X���󂯎��A�ړ���̃p�X�Ƀf�[�^���ړ�������B<br>
     * �ړ���̃p�X�Ƀt�@�C�������݂���ꍇ�A���̃t�@�C�����폜������A �t�@�C���̈ړ������s����B<br>
     * �ړ����̃t�@�C�������݂��Ȃ��ꍇ�A�񌟍���O���X���[����B<br>
     * �t�@�C���̈ړ��Ɏ��s�����ꍇ�A�񌟍���O���X���[����B
     * </p>
     * @param srcFile �ړ��O�̃p�X
     * @param newFile �ړ���̃p�X
     * @throws �t�@�C���@�\��O
     */
    public static void renameFile(String srcFile, String newFile) {

        checkAbsolutePath(srcFile);
        checkAbsolutePath(newFile);

        File srcFileObject = new File(srcFile);
        File newFileObject = new File(newFile);

        // �ړ����Ƃ̃t�@�C�������݂��Ȃ��ꍇ�A�G���[�𓊂��ď������I������B
        if (!srcFileObject.exists()) {
            throw new FileException(srcFile + " is not exist.", srcFile);
        }

        // �ړ���̃t�@�C�������݂���ꍇ�A���̃t�@�C�����폜����B
        if (newFileObject.exists() && checkFileExist) {
            boolean result = newFileObject.delete();
            if (!result) {
                throw new FileException("File control operation was failed.",
                        newFile);
            }
        } else if (newFileObject.exists() && !checkFileExist) {
            throw new FileException(newFile + " is exist.", newFile);
        }

        boolean result = true;
        result = srcFileObject.renameTo(newFileObject);

        if (!result) {
            throw new FileException("File control operation was failed.");
        }
    }

    /**
     * �t�@�C�������݂��邩�ǂ����̃t���O���擾����B
     * @return �t�@�C�������݂��邩�ǂ����̃t���O
     */
    public static boolean isCheckFileExist() {
        return checkFileExist;
    }

    /**
     * �t�@�C�������݂��邩�ǂ����̃t���O��ݒ肷��B
     * @param checkFileExist �t�@�C�������݂��邩�ǂ����̃t���O
     */
    public static void setCheckFileExist(boolean checkFileExist) {
        FileUtility.checkFileExist = checkFileExist;
    }

    /**
     * �����̃p�X����΃p�X�ł��邱�Ƃ��m�F����B ��΃p�X�łȂ��ꍇ�A��O���X���[����B
     * @param filePath �t�@�C���̃p�X
     * @throws �t�@�C���@�\��O
     */
    private static void checkAbsolutePath(String filePath) {
        if (filePath == null) {
            throw new FileException("File path is not set.", filePath);
        }
        File file = new File(filePath);
        if (!file.isAbsolute()) {
            throw new FileException("File path is not absolute.", filePath);
        }
    }
}

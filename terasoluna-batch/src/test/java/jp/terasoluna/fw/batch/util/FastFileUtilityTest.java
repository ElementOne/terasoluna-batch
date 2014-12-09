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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.channels.Channel;

import jp.terasoluna.fw.ex.unit.util.AssertUtils;
import jp.terasoluna.fw.ex.unit.util.ReflectionUtils;
import jp.terasoluna.fw.file.dao.FileException;
import junit.framework.TestCase;

/**
 * �O������F�e�e�X�g���\�b�h���Ƃ̑O��������m�F���邱��
 */
public class FastFileUtilityTest extends TestCase {
    /**
     * testConstructor
     */
    public void testConstructor() {
        // �J�o���b�W�𖞂������߂����̍���
        ReflectionUtils.newInstance(FastFileUtility.class);
        assertTrue(true);
    }

    /**
     * testCopyFile01()<br>
     * <br>
     * ���O��ԁF�N���X�p�X/testdata �z����test01.txt�����݂��邱�ƁB<br>
     * <br>
     * �e�X�g�T�v�F�t�@�C�����ǂݍ��܂�A����ɃR�s�[����邱�Ƃ��m�F����B<br>
     * <br>
     * �m�F���ځF�R�s�[��̃t�@�C�����R�s�[���Ɠ��e���������m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String srcFile = srcUrl.getPath();
        String newFile = srcFile.replaceAll("01", "01A");
        File newFileCheck = new File(newFile);
        if (newFileCheck.exists()) {
            newFileCheck.delete();
        }

        // �e�X�g���{
        FastFileUtility.copyFile(srcFile, newFile);

        // ���ʌ���
        File expected = new File(srcFile);
        File actual = new File(newFile);

        AssertUtils.assertFileEquals(expected, actual);
    }

    /**
     * testCopyFile02()<br>
     * <br>
     * ���O��ԁF�N���X�p�X/testdata �z����test01.txt�����݂��邱�ƁB<br>
     * ���O��ԁF�N���X�p�X/testdata �z����test02.txt�����݂��Ȃ����ƁB<br>
     * <br>
     * �e�X�g�T�v�F�t�@�C�����ǂݍ��܂ꂸ�AFileException���X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * �m�F���ځFFileException���X���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF��O�ɁuC:\\tmp\\test02.txt is not exist.�v���b�Z�[�W���ݒ肳��Ă��邱�ƁB<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile02() throws Exception {

        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String srcFile = srcUrl.getPath().replaceAll("01", "02");
        String newFile = srcFile.replaceAll("02", "02A");

        try {
            FastFileUtility.copyFile(srcFile, newFile);
            fail();
        } catch (FileException e) {
            assertTrue(e.getMessage().endsWith("test02.txt is not exist."));
        }
    }

    /**
     * testCopyFile03()<br>
     * <br>
     * ���O��ԁF�N���X�p�X/testdata �z����test03.txt�����݂��A�ǂݎ�茠�����Ȃ�����<br>
     * <br>
     * �e�X�g�T�v�F�t�@�C�����ǂݍ��܂ꂸ�AFileException���X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * �m�F���ځFFileException���X���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF��O�ɁuFile control operation was failed.�v���b�Z�[�W���ݒ肳��Ă��邱�ƁB<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile03() throws Exception {

        URL srcUrl = this.getClass().getResource("/testdata/test03.txt");
        String srcFile = srcUrl.getPath();
        String newFile = srcFile.replaceAll("03", "03A");

        try {
            FastFileUtility.copyFile(srcFile, newFile);
            fail();
        } catch (FileException e) {
            assertEquals("File control operation was failed.", e.getMessage());
        }
    }

    /**
     * testCopyFile04()<br>
     * <br>
     * ���O��ԁF�N���X�p�X/testdata �z����test01.txt�����݂��邱�ƁB<br>
     * <br>
     * �e�X�g�T�v�FsrcFile��null��ݒ肵���ꍇ�A�t�@�C�����ǂݍ��܂ꂸ�AFileException���X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * �m�F���ځFFileException���X���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF��O�ɁuFile control operation was failed.�v���b�Z�[�W���ݒ肳��Ă��邱�ƁB<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile04() throws Exception {

        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String srcFile = null;
        String newFile = srcUrl.getPath().replaceAll("01", "04A");

        try {
            FastFileUtility.copyFile(srcFile, newFile);
            fail();
        } catch (FileException e) {
        }
    }

    /**
     * testCopyFile05()<br>
     * <br>
     * ���O��ԁF�Ȃ�<br>
     * <br>
     * �e�X�g�T�v�F�N���X�p�X/testdata �z����test01.txt�����݂��AnewFile��null���ݒ肳�ꂽ�ꍇ�A FileException���X���[����邱�Ƃ��m�F����B<br>
     * <br>
     * �m�F���ځFFileException���X���[����邱�ƁB<br>
     * <br>
     * �m�F���ځF��O�ɁuFile control operation was failed.�v���b�Z�[�W���ݒ肳��Ă��邱�ƁB<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile05() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String srcFile = srcUrl.getPath();
        String newFile = null;

        try {
            FastFileUtility.copyFile(srcFile, newFile);
            fail();
        } catch (FileException e) {
        }
    }

    /**
     * testCopyFile06()<br>
     * <br>
     * ���O��ԁF�N���X�p�X/testdata �z���ɗe�ʂ̑傫��test06.txt���邱��<br>
     * <br>
     * �e�X�g�T�v�F�t�@�C�����ǂݍ��܂�A����ɃR�s�[����邱�Ƃ��m�F����B<br>
     * <br>
     * �m�F���ځF�R�s�[��̃t�@�C�����R�s�[���Ɠ��e���������m�F����B<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile06() throws Exception {

        
        // 583MB�t�@�C�����쐬
        URL parent = this.getClass().getResource("/testdata");
        String target = parent.getPath() + "/test06.txt";
        File f = new File(target);
        if (!f.exists()) {
            Writer writer = null;
            try {
                writer = new FileWriter(f);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 1024; i++) {
                    for (int j = 0; j < 10; j++) {
                        sb.append("TERASOLUNA");
                    }
                    sb.append("\r\n");
                }
                String record = sb.toString();
                for (int i = 0; i < 5856; i++) {
                    writer.write(record);
                }
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        // �e�X�g�f�[�^�ݒ�
        URL srcUrl = this.getClass().getResource("/testdata/test06.txt");

        String srcFile = srcUrl.getPath();
        String newFile = srcFile.replaceAll("test06\\.txt", "test06A\\.txt");
        File newFileCheck = new File(newFile);
        if (newFileCheck.exists()) {
            newFileCheck.delete();
        }

        // �e�X�g���{
        FastFileUtility.copyFile(srcFile, newFile);

        // ���ʌ���
        File expected = new File(srcFile);
        File actual = new File(newFile);

        AssertUtils.assertFileEquals(expected, actual);

    }

    /**
     * testCloseQuietly001
     * @throws Exception
     */
    public void testCloseQuietly001() throws Exception {
        Channel channel = new Channel() {
            public void close() throws IOException {
            }

            public boolean isOpen() {
                return false;
            }
        };

        // �e�X�g
        FastFileUtility.closeQuietly(channel);
    }

    /**
     * testCloseQuietly002
     * @throws Exception
     */
    public void testCloseQuietly002() throws Exception {
        Channel channel = new Channel() {
            public void close() throws IOException {
                throw new IOException();
            }

            public boolean isOpen() {
                return false;
            }
        };

        // �e�X�g
        FastFileUtility.closeQuietly(channel);
    }

    /**
     * testCloseQuietly011
     * @throws Exception
     */
    public void testCloseQuietly011() throws Exception {
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        FileInputStream channel = new FileInputStream(srcUrl.getPath());

        // �e�X�g
        FastFileUtility.closeQuietly(channel);
    }

    /**
     * testCloseQuietly012
     * @throws Exception
     */
    public void testCloseQuietly012() throws Exception {
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        FileInputStream channel = new FileInputStream(srcUrl.getPath()) {
            @Override
            public void close() throws IOException {
                throw new IOException();
            }
        };

        // �e�X�g
        FastFileUtility.closeQuietly(channel);
    }

    /**
     * testCloseQuietly021
     * @throws Exception
     */
    public void testCloseQuietly021() throws Exception {
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String srcFile = srcUrl.getPath();
        String newFile = srcFile.replaceAll("01", "01A");
        File newFileCheck = new File(newFile);
        if (newFileCheck.exists()) {
            newFileCheck.delete();
        }

        FileOutputStream channel = new FileOutputStream(newFile);

        // �e�X�g
        FastFileUtility.closeQuietly(channel);
    }

    /**
     * testCloseQuietly022
     * @throws Exception
     */
    public void testCloseQuietly022() throws Exception {
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String srcFile = srcUrl.getPath();
        String newFile = srcFile.replaceAll("01", "01A");
        File newFileCheck = new File(newFile);
        if (newFileCheck.exists()) {
            newFileCheck.delete();
        }

        FileOutputStream channel = new FileOutputStream(newFile) {
            @Override
            public void close() throws IOException {
                throw new IOException();
            }
        };

        // �e�X�g
        FastFileUtility.closeQuietly(channel);
    }

}

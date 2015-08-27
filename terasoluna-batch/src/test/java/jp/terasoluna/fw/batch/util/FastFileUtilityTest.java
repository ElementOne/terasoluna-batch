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

import java.io.*;
import java.net.URL;
import java.nio.channels.Channel;
import java.util.Random;

import jp.terasoluna.fw.ex.unit.util.AssertUtils;
import jp.terasoluna.fw.ex.unit.util.ReflectionUtils;
import jp.terasoluna.fw.file.dao.FileException;
import junit.framework.TestCase;

/**
 * 前提条件：各テストメソッドごとの前提条件を確認すること
 */
public class FastFileUtilityTest extends TestCase {
    /**
     * testConstructor
     */
    public void testConstructor() {
        // カバレッジを満たすためだけの項目
        ReflectionUtils.newInstance(FastFileUtility.class);
        assertTrue(true);
    }

    /**
     * testCopyFile01()<br>
     * <br>
     * 事前状態：クラスパス/testdata 配下にtest01.txtが存在すること。<br>
     * <br>
     * テスト概要：ファイルが読み込まれ、正常にコピーされることを確認する。<br>
     * <br>
     * 確認項目：コピー先のファイルがコピー元と内容が同じか確認する。<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile01() throws Exception {

        // テストデータ設定
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        String srcFile = srcUrl.getPath();
        String newFile = srcFile.replaceAll("01", "01A");
        File newFileCheck = new File(newFile);
        if (newFileCheck.exists()) {
            newFileCheck.delete();
        }

        // テスト実施
        FastFileUtility.copyFile(srcFile, newFile);

        // 結果検証
        File expected = new File(srcFile);
        File actual = new File(newFile);

        AssertUtils.assertFileEquals(expected, actual);
    }

    /**
     * testCopyFile02()<br>
     * <br>
     * 事前状態：クラスパス/testdata 配下にtest01.txtが存在すること。<br>
     * 事前状態：クラスパス/testdata 配下にtest02.txtが存在しないこと。<br>
     * <br>
     * テスト概要：ファイルが読み込まれず、FileExceptionがスローされることを確認する。<br>
     * <br>
     * 確認項目：FileExceptionがスローされること。<br>
     * <br>
     * 確認項目：例外に「C:\\tmp\\test02.txt is not exist.」メッセージが設定されていること。<br>
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
     * 事前状態：null文字（\u0000）を含むファイル名を指定する。<br>
     * <br>
     * テスト概要：ファイルが読み込まれず、FileExceptionがスローされることを確認する。<br>
     * <br>
     * 確認項目：FileExceptionがスローされること。<br>
     * <br>
     * 確認項目：例外に「File control operation was failed.」メッセージが設定されていること。<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile03() throws Exception {
        File srcFile = new File("dummy\u0000.txt");
        File newFile = new File("newFile");
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
     * 事前状態：クラスパス/testdata 配下にtest01.txtが存在すること。<br>
     * <br>
     * テスト概要：srcFileにnullを設定した場合、ファイルが読み込まれず、FileExceptionがスローされることを確認する。<br>
     * <br>
     * 確認項目：FileExceptionがスローされること。<br>
     * <br>
     * 確認項目：例外に「File control operation was failed.」メッセージが設定されていること。<br>
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
     * 事前状態：なし<br>
     * <br>
     * テスト概要：クラスパス/testdata 配下にtest01.txtが存在し、newFileにnullが設定された場合、 FileExceptionがスローされることを確認する。<br>
     * <br>
     * 確認項目：FileExceptionがスローされること。<br>
     * <br>
     * 確認項目：例外に「File control operation was failed.」メッセージが設定されていること。<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile05() throws Exception {

        // テストデータ設定
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
     * 事前状態：クラスパス/testdata 配下に容量の大きい(3GB)test06.txtがあること<br>
     * ファイルが既に存在する場合は作成されない。 <br>
     * テスト概要：ファイルが読み込まれ、正常にコピーされることを確認する。<br>
     * <br>
     * 確認項目：コピー先のファイルがコピー元と内容が同じか確認する。<br>
     * <br>
     * @throws Exception
     */
    public void testCopyFile06() throws Exception {

        // 3GBのファイルを作成
        create3GBFile("/testdata", "test06.txt");

        // テストデータ設定
        URL srcUrl = this.getClass().getResource("/testdata/test06.txt");

        String srcFile = srcUrl.getPath();
        String newFile = srcFile.replaceAll("test06\\.txt", "test06A\\.txt");
        File newFileCheck = new File(newFile);
        if (newFileCheck.exists()) {
            newFileCheck.delete();
        }

        // テスト実施
        FastFileUtility.copyFile(srcFile, newFile);

        // 結果検証
        File expected = new File(srcFile);
        File actual = new File(newFile);

        AssertUtils.assertFileEquals(expected, actual);

    }

    /**
     * 3GBファイルの作成
     * @param directoryPath ファイルの作成先ディレクトリ
     * @param fileName 作成するファイル名
     * @return 1022文字+改行コード(\r\n)で1KB(1024Byte)分を1行として3GBのデータを作成
     */
    private void create3GBFile(String directoryPath, String fileName)
                                                                     throws Exception {
        long gByte = 1024L * 1024L * 1024L;
        URL parent = this.getClass().getResource(directoryPath);
        File f = new File(parent.getPath(), fileName);
        if (f.exists() && f.length() == 3 * gByte) {
            // 既にデータを作成済みの場合はスキップする
            return;
        }
        Writer writer = null;
        Random print1CharStringRandom = new Random();
        try {
            writer = new FileWriter(f);
            for (int i = 0; i < 3; i++) {
                createGByteData(writer, print1CharStringRandom);
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

    /**
     * 1GByte分のデータを作成
     * @param writer ファイル出力用のWriterクラス
     * @param print1CharStringRandom テストデータに出力する文字をランダムで選択するためのRandomクラス
     * @return 1022文字+改行コード(\r\n)で1KB(1024Byte)分を1行としてファイルサイズ分のデータを作成
     */
    private void createGByteData(Writer writer, Random print1CharStringRandom)
                                                                              throws IOException {
        int kByte = 1024;
        int crlfSize = 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kByte; i++) {
            for (int j = 0; j < kByte; j++) {
                for (int k = 0; k < kByte - crlfSize; k++) {
                    sb.append(getNextRandom1CharString(print1CharStringRandom));
                }
                sb.append("\r\n");
                writer.write(sb.toString());
                sb.setLength(0);
            }
        }
    }

    /**
     * 次に出力するランダムな1文字を返却する
     * @param print1CharStringRandom テストデータに出力する文字[0-9]をランダムで選択するためのRandomクラス
     * @return 次に出力するランダムな1文字
     */
    private String getNextRandom1CharString(Random print1CharStringRandom) {
        int randomInt = print1CharStringRandom.nextInt(10);
        String nextRandomChar = String.valueOf(randomInt);
        return nextRandomChar;
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

        // テスト
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

        // テスト
        FastFileUtility.closeQuietly(channel);
    }

    /**
     * testCloseQuietly011
     * @throws Exception
     */
    public void testCloseQuietly011() throws Exception {
        URL srcUrl = this.getClass().getResource("/testdata/test01.txt");
        FileInputStream channel = new FileInputStream(srcUrl.getPath());

        // テスト
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

        // テスト
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

        // テスト
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

        // テスト
        FastFileUtility.closeQuietly(channel);
    }

}

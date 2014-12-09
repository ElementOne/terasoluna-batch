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

package jp.terasoluna.fw.collector.util;

import java.io.IOException;

import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineIterator;
import jp.terasoluna.fw.file.dao.FileLineWriter;

/**
 * CollectorUtility�N���X.
 */
public class CollectorUtility {

    /**
     * �R���X�g���N�^.
     */
    protected CollectorUtility() {
    }

    /**
     * Collector���N���[�Y����B<br>
     * <p>
     * �����ɓn���ꂽcollector��null�łȂ���΃N���[�Y����B<br>
     * �܂��A�N���[�Y����ۂɗ�O�����������ꍇ�͖�������B<br>
     * </p>
     * @param collector Collector
     */
    public static void closeQuietly(Collector<?> collector) {
        try {
            if (collector != null) {
                collector.close();
            }
        } catch (IOException e) {
            // �Ȃɂ����Ȃ�
        }
    }

    /**
     * FileLineIterator���N���[�Y����B<br>
     * <p>
     * �����ɓn���ꂽiterator��null�łȂ���΃N���[�Y����B<br>
     * �܂��A�N���[�Y����ۂ�FileException��O�����������ꍇ�͖�������B<br>
     * </p>
     * @param <T>
     * @param iterator FileLineIterator&lt;T&gt;
     */
    public static <T> void closeQuietly(FileLineIterator<T> iterator) {
        try {
            if (iterator != null) {
                iterator.closeFile();
            }
        } catch (FileException e) {
            // �Ȃɂ����Ȃ�
        }
    }

    /**
     * FileLineWriter���N���[�Y����B<br>
     * <p>
     * �����ɓn���ꂽwriter��null�łȂ���΃N���[�Y����B<br>
     * �܂��A�N���[�Y����ۂ�FileException��O�����������ꍇ�͖�������B<br>
     * </p>
     * @param <T>
     * @param writer FileLineWriter&lt;T&gt;
     */
    public static <T> void closeQuietly(FileLineWriter<T> writer) {
        try {
            if (writer != null) {
                writer.closeFile();
            }
        } catch (FileException e) {
            // �Ȃɂ����Ȃ�
        }
    }
}

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

package jp.terasoluna.fw.collector.file;

import jp.terasoluna.fw.collector.AbstractCollector;
import jp.terasoluna.fw.collector.LogId;
import jp.terasoluna.fw.collector.exception.CollectorExceptionHandler;
import jp.terasoluna.fw.collector.vo.DataValueObject;
import jp.terasoluna.fw.file.dao.FileLineIterator;
import jp.terasoluna.fw.file.dao.FileQueryDAO;
import jp.terasoluna.fw.logger.TLogger;

/**
 * FileCollector.<br>
 * �Ɨ������ʃX���b�h���N�����AFileQueryDAO��񓯊��Ŏ��s����B
 * @param &ltP&gt
 */
public class FileCollector<P> extends AbstractCollector<P> {

    /**
     * Log.
     */
    private static TLogger LOGGER = TLogger.getLogger(FileCollector.class);

    /** FileQueryDAO */
    protected FileQueryDAO fileQueryDAO = null;

    /** �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j */
    protected String fileName = null;

    /** 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X */
    protected Class<P> clazz = null;

    /**
     * FileCollector�R���X�g���N�^.<br>
     */
    protected FileCollector() {
    }

    /**
     * FileCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     */
    public FileCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz));
    }

    /**
     * FileCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param exceptionHandler ��O�n���h��
     */
    public FileCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, CollectorExceptionHandler exceptionHandler) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addExceptionHandler(exceptionHandler));
    }

    /**
     * FileCollector�R���X�g���N�^.<br>
     * @param fileQueryDAO FileQueryDAO�C���X�^���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param queueSize �L���[�̃T�C�Y�i1�ȏ��ݒ肷�邱�Ɓj
     * @param exceptionHandler ��O�n���h��
     */
    public FileCollector(FileQueryDAO fileQueryDAO, String fileName,
            Class<P> clazz, int queueSize,
            CollectorExceptionHandler exceptionHandler) {
        this(new FileCollectorConfig<P>(fileQueryDAO, fileName, clazz)
                .addQueueSize(queueSize).addExceptionHandler(exceptionHandler));
    }

    /**
     * FileCollector�R���X�g���N�^.<br>
     * @param config FileCollectorConfig FileCollector�ݒ荀��
     */
    public FileCollector(FileCollectorConfig<P> config) {
        if (config == null) {
            throw new IllegalArgumentException("The parameter is null.");
        }

        this.fileQueryDAO = config.getFileQueryDAO();
        this.fileName = config.getFileName();
        this.clazz = config.getClazz();
        if (config.getQueueSize() > 0) {
            setQueueSize(config.getQueueSize());
        }
        this.exceptionHandler = config.getExceptionHandler();

        if (config.isExecuteByConstructor()) {
            // ���s�J�n
            execute();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    public Integer call() throws Exception {
        FileLineIterator<P> fli = null;
        long dataCount = 0;
        try {
            // FileQueryDAO���s
            fli = this.fileQueryDAO.execute(this.fileName, this.clazz);

            if (fli != null) {
                while (fli.hasNext()) {
                    dataCount++;
                    try {
                        Object value = fli.next();

                        // �擾�����f�[�^��1���L���[�ɂ߂�
                        addQueue(new DataValueObject(value, dataCount));
                    } catch (InterruptedException e) {
                        if (LOGGER.isTraceEnabled()) {
                            LOGGER.trace(LogId.TAL041002, Thread
                                    .currentThread().getName());
                        }
                        break;
                    } catch (Throwable e) {
                        // ����������O���L���[�ɂ߂�
                        try {
                            addQueue(new DataValueObject(e, dataCount));
                        } catch (InterruptedException ie) {
                            LOGGER.warn(LogId.WAL041003, e);
                            LOGGER.warn(LogId.WAL041003, ie);
                            break;
                        }
                        // ���̍s��ǂނ��߁A���[�v�͌p������
                    }
                }
            }
        } catch (Throwable e) {
            // �V���b�g�_�E�����͔���������O���L���[�ɋl�߂Ȃ�
            if (!isFinish()) {
                // ����������O���L���[�ɂ߂�
                try {
                    addQueue(new DataValueObject(e, dataCount));
                } catch (InterruptedException ie) {
                    LOGGER.warn(LogId.WAL041003, e);
                    LOGGER.warn(LogId.WAL041003, ie);
                }
            }

            return Integer.valueOf(-1);
        } finally {
            try {
                // �t�@�C���N���[�Y
                if (fli != null) {
                    fli.closeFile();
                }
            } catch (Throwable e) {
                // �������Ȃ�
            } finally {
                // �I���t���O�𗧂Ă�
                setFinish();
                if (verboseLog.get() && LOGGER.isTraceEnabled()) {
                    LOGGER.trace(LogId.TAL041018, dataCount);
                }
            }
        }

        return Integer.valueOf(0);
    }
}

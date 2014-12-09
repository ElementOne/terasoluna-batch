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

package jp.terasoluna.fw.batch.dao.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.fw.logger.TLogger;

/**
 * �o�b�`�X�V�T�|�[�g�N���X<br>
 * <p>
 * �{�N���X�𗘗p�����Ƃ�UpdateDAO�̃o�b�`�X�V�����̎��s��SqlID���ɐ��񂳂ꂽ��Ԃōs�����Ƃ��ł���B<br>
 * SqlID�Ń\�[�g���邱�Ƃ�JDBC��PreparedStatement#executeBatch�̎��s�񐔂����点�鎖�ɂ�萫�\�Ɋ�^����B
 * </p>
 * <p>
 * sort���\�b�h�����s������SQL�����s�����ꍇ�́A����ɒǉ�����SQL-ID�̏����ێ������B<br>
 * <p>
 * <li>���Ƃ��Έȉ��̂悤�ȏ���SQL-ID��ǉ������ꍇ</li><br>
 * A C B A B C B C A
 * </p>
 * <p>
 * <li>sort���\�b�h�����s������SQL�����s�����ꍇ�̎��s��</li><br>
 * A A A C C C B B B
 * </p>
 * <p>
 * <li>sort���\�b�h�����s���SQL�����s�����ꍇ�̎��s��</li><br>
 * A A A B B B C C C
 * </p>
 * </p>
 * <p>
 * <b> ���}���`�X���b�h�Z�[�t�ł͂Ȃ����ߕ����X���b�h�ŗ��p����ꍇ�́A�e�X���b�h���ƂɕʃC���X�^���X�𐶐����邱�ƁB </b>
 * </p>
 * @see UpdateDAO
 */
public class BatchUpdateSupportImpl implements BatchUpdateSupport {

    /**
     * ���K�[.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(BatchUpdateExecutor.class);

    /** UpdateDAO */
    protected UpdateDAO updateDAO = null;

    /** SqlID���\�[�g���鎞�Ɏg�p����Comparator */
    protected volatile Comparator<String> comparator = null;

    /** �o�b�`���sSQL��ێ�����. */
    protected final Map<String, Queue<SqlHolder>> batchSqlsMap = new LinkedHashMap<String, Queue<SqlHolder>>();

    /**
     * �\�[�g�t���O
     */
    protected volatile boolean sortMode = false;

    /** �o�b�`���sSQL�o�^���� */
    protected volatile AtomicLong count = new AtomicLong(0);

    /** SQL-ID�̎��s���� */
    private String[] sqlIdOrder = null;

    /**
     * �o�b�`�X�V�T�|�[�g�N���X�R���X�g���N�^.
     */
    public BatchUpdateSupportImpl() {
        // �������Ȃ�
    }

    /**
     * �o�b�`�X�V�T�|�[�g�N���X�R���X�g���N�^.
     * @param updateDAO UpdateDAO
     */
    public BatchUpdateSupportImpl(UpdateDAO updateDAO) {
        this(updateDAO, (Comparator<String>) null);
    }

    /**
     * �o�b�`�X�V�T�|�[�g�N���X�R���X�g���N�^.
     * @param updateDAO UpdateDAO
     * @param comparator Comparator&lt;String&gt;
     */
    public BatchUpdateSupportImpl(UpdateDAO updateDAO,
            Comparator<String> comparator) {
        this.updateDAO = updateDAO;
        this.comparator = comparator;

        if (this.updateDAO == null) {
            LOGGER.warn(LogId.WAL036002);
        }
    }

    /**
     * �o�b�`�X�V�T�|�[�g�N���X�R���X�g���N�^.
     * @param updateDAO UpdateDAO
     * @param sqlIdOrder SQL-ID�̎��s�������w�肷��
     */
    public BatchUpdateSupportImpl(UpdateDAO updateDAO, String... sqlIdOrder) {
        this.updateDAO = updateDAO;
        this.sqlIdOrder = sqlIdOrder;

        if (this.updateDAO == null) {
            LOGGER.warn(LogId.WAL036002);
        }
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupportIf#addBatch(java.lang.String, java.lang.Object)
     */
    public void addBatch(final String sqlID, final Object bindParams) {
        if (sqlID == null || sqlID.length() == 0) {
            LOGGER.warn(LogId.WAL036003);
            return;
        }

        Queue<SqlHolder> sqlQueue = this.batchSqlsMap.get(sqlID);

        if (sqlQueue == null) {
            // �Ď擾
            sqlQueue = this.batchSqlsMap.get(sqlID);

            if (sqlQueue == null) {
                sqlQueue = new ConcurrentLinkedQueue<SqlHolder>();
                this.batchSqlsMap.put(sqlID, sqlQueue);
            }
        }

        if (sqlQueue != null) {
            sqlQueue.add(new SqlHolder(sqlID, bindParams));
            this.count.incrementAndGet();
        }
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#executeBatch()
     */
    public int executeBatch() {
        return executeBatch(this.updateDAO, this.comparator, this.sqlIdOrder);
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#executeBatch(jp.terasoluna.fw.dao.UpdateDAO)
     */
    public int executeBatch(UpdateDAO updateDAO) {
        return executeBatch(updateDAO, this.comparator, this.sqlIdOrder);
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#executeBatch(jp.terasoluna.fw.dao.UpdateDAO,
     * java.util.Comparator)
     */
    public int executeBatch(UpdateDAO updateDAO, Comparator<String> comparator) {
        return executeBatch(updateDAO, comparator, null);
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#executeBatch(jp.terasoluna.fw.dao.UpdateDAO,
     * java.lang.String[])
     */
    public int executeBatch(UpdateDAO updateDAO, String... sqlIdOrder) {
        return executeBatch(updateDAO, null, sqlIdOrder);
    }

    /**
     * �o�b�`���s���s���B
     * @param updateDAO UpdateDAO
     * @param comparator Comparator&lt;String&gt;
     * @param sqlIdOrder SQL-ID�̎��s�������w�肷��
     * @return SQL�̎��s����
     */
    protected int executeBatch(UpdateDAO updateDAO,
            Comparator<String> comparator, String[] sqlIdOrder) {
        int result = -1000;

        if (updateDAO == null) {
            LOGGER.warn(LogId.WAL036002);
            return ERROR_UPDATE_DAO_IS_NULL;
        }

        List<SqlHolder> sqlHolderList = new ArrayList<SqlHolder>();

        // SQL-ID���X�g���擾
        List<String> keyList = new ArrayList<String>(this.batchSqlsMap.keySet());

        if (sqlIdOrder != null) {
            List<String> sqlIdOrderList = Arrays.asList(sqlIdOrder);

            // �����`�F�b�N
            if (keyList.size() > sqlIdOrderList.size()) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(LogId.WAL036004, keyList.size(), sqlIdOrderList
                            .size());
                }
                return ERROR_UNKNOWN_SQL_ID;
            }
            // SQL-ID���݃`�F�b�N
            for (String key : keyList) {
                if (!sqlIdOrderList.contains(key)) {
                    LOGGER.warn(LogId.WAL036005, key);
                    return ERROR_UNKNOWN_SQL_ID;
                }
            }
            // �L�[���X�g���㏑��
            keyList = sqlIdOrderList;
        } else if (this.sortMode || comparator != null) {
            // SQL-ID�Ń\�[�g����
            if (comparator != null) {
                Collections.sort(keyList, comparator);
            } else {
                Collections.sort(keyList);
            }
        }

        for (String key : keyList) {
            if (this.batchSqlsMap.containsKey(key)) {
                sqlHolderList.addAll(this.batchSqlsMap.get(key));
            }
        }

        // �o�b�`�X�V���s
        result = updateDAO.executeBatch(sqlHolderList);

        this.batchSqlsMap.clear();
        this.count.set(0);
        this.sortMode = false;

        return result;
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#sort()
     */
    public void sort() {
        this.sortMode = true;
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#sort(java.util.Comparator)
     */
    public void sort(Comparator<String> comparator) {
        this.sortMode = true;
        this.comparator = comparator;
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupportIf#clear()
     */
    public void clear() {
        this.batchSqlsMap.clear();
        this.count.set(0);
        this.sortMode = false;
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#size()
     */
    public long size() {
        return this.count.get();
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#getSqlHolderList()
     */
    public List<SqlHolder> getSqlHolderList() {
        return getSqlHolderList(this.comparator);
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#getSqlHolderList(java.util.Comparator)
     */
    public List<SqlHolder> getSqlHolderList(Comparator<String> comparator) {
        return getSqlHolderList(comparator, null);
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport#getSqlHolderList(java.lang.String[])
     */
    public List<SqlHolder> getSqlHolderList(String... sqlIdOrder) {
        return getSqlHolderList(null, sqlIdOrder);
    }

    /**
     * SQL-ID�Ő��񂳂ꂽSqlHolder���X�g���擾����B
     * @param comparator Comparator&lt;String&gt;
     * @param sqlIdOrder SQL-ID�̎��s�������w�肷��
     * @return SqlHolder���X�g
     */
    protected List<SqlHolder> getSqlHolderList(Comparator<String> comparator,
            String[] sqlIdOrder) {
        List<SqlHolder> sqlHolderList = new ArrayList<SqlHolder>();

        // SQL-ID���X�g���擾
        List<String> keyList = new ArrayList<String>(this.batchSqlsMap.keySet());

        if (sqlIdOrder != null) {
            List<String> sqlIdOrderList = Arrays.asList(sqlIdOrder);

            // �����`�F�b�N
            if (keyList.size() > sqlIdOrderList.size()) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(LogId.WAL036004, keyList.size(), sqlIdOrderList
                            .size());
                }
                return null;
            }
            // SQL-ID���݃`�F�b�N
            for (String key : keyList) {
                if (!sqlIdOrderList.contains(key)) {
                    LOGGER.warn(LogId.WAL036005, key);
                    return null;
                }
            }
            // �L�[���X�g���㏑��
            keyList = sqlIdOrderList;
        } else if (this.sortMode || comparator != null) {
            // SQL-ID�Ń\�[�g����
            if (comparator != null) {
                Collections.sort(keyList, comparator);
            } else {
                Collections.sort(keyList);
            }
        }

        for (String key : keyList) {
            if (this.batchSqlsMap.containsKey(key)) {
                sqlHolderList.addAll(this.batchSqlsMap.get(key));
            }
        }

        this.sortMode = false;

        return sqlHolderList;
    }
}

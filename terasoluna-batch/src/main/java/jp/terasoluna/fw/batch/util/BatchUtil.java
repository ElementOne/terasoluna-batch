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

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.batch.exception.IllegalClassTypeException;
import jp.terasoluna.fw.logger.TLogger;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * �o�b�`�����p���[�e�B���e�B�B<br>
 * <br>
 * �e��o�b�`�����ɂĎg�p���郆�[�e�B���e�B���\�b�h���`����B
 */
public class BatchUtil {
    /** ���K�[. */
    private static final TLogger LOGGER = TLogger.getLogger(BatchUtil.class);

    /**
     * �R���X�g���N�^
     */
    protected BatchUtil() {
    }

    /**
     * �ėp�����񌋍����\�b�h�B
     * @param args �C�ӂ̒l
     * @return ��������������������
     */
    public static String cat(Object... args) {

        StringBuilder str = new StringBuilder();

        if (args == null) {
            return null;
        }

        for (Object o : args) {
            if (o != null) {
                str.append(o);
            }
        }

        return str.toString();
    }

    /**
     * �C���t�H���O�̊J�n���b�Z�[�W���擾����B
     * @param jobCd �W���u�Ɩ��R�[�h
     * @return String ���b�Z�[�W
     */
    public static String getInfoLogStartMsg(String jobCd) {

        return BatchUtil.cat("[", jobCd, "] ", "�����J�n");
    }

    /**
     * �C���t�H���O�̏I�����b�Z�[�W���擾����B
     * @param jobCd �W���u�Ɩ��R�[�h
     * @return String ���b�Z�[�W
     */
    public static String getInfoLogEndMsg(String jobCd) {

        return BatchUtil.cat("[", jobCd, "] ", "�����I��");
    }

    /**
     * �f�t�H���g��TransactionDefinition���擾����
     * @return
     */
    public static TransactionDefinition getTransactionDefinition() {
        return new DefaultTransactionDefinition();
    }

    /**
     * �f�t�H���g��TransactionDefinition���擾����
     * @param propagationBehavior �g�����U�N�V�����`�����[�h(@see TransactionDefinition) �f�t�H���g�FTransactionDefinition.PROPAGATION_REQUIRED
     * @param isolationLevel �g�����U�N�V�����������x��(@see TransactionDefinition) �f�t�H���g�FTransactionDefinition.ISOLATION_DEFAULT
     * @param timeout �g�����U�N�V�����^�C���A�E�g(�b) �f�t�H���g�FTransactionDefinition.TIMEOUT_DEFAULT (�^�C���A�E�g�Ȃ�)
     * @param readOnly ���[�h�I�����[�g�����U�N�V���� �f�t�H���g�Ffalse
     * @return
     */
    public static TransactionDefinition getTransactionDefinition(
            int propagationBehavior, int isolationLevel, int timeout,
            boolean readOnly) {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition();
        td.setPropagationBehavior(propagationBehavior);
        td.setIsolationLevel(isolationLevel);
        td.setTimeout(timeout);
        td.setReadOnly(readOnly);
        return td;
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tran PlatformTransactionManager
     * @return TransactionStatus
     */
    public static TransactionStatus startTransaction(
            PlatformTransactionManager tran) {
        return startTransaction(tran, getTransactionDefinition(), null);
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tran PlatformTransactionManager
     * @param log Log
     * @return TransactionStatus
     */
    public static TransactionStatus startTransaction(
            PlatformTransactionManager tran, Log log) {
        return startTransaction(tran, getTransactionDefinition(), log);
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tran PlatformTransactionManager
     * @param propagationBehavior �g�����U�N�V�����`�����[�h(@see TransactionDefinition) �f�t�H���g�FTransactionDefinition.PROPAGATION_REQUIRED
     * @param isolationLevel �g�����U�N�V�����������x��(@see TransactionDefinition) �f�t�H���g�FTransactionDefinition.ISOLATION_DEFAULT
     * @param timeout �g�����U�N�V�����^�C���A�E�g(�b) �f�t�H���g�FTransactionDefinition.TIMEOUT_DEFAULT (�^�C���A�E�g�Ȃ�)
     * @param readOnly ���[�h�I�����[�g�����U�N�V���� �f�t�H���g�Ffalse
     * @return TransactionStatus
     */
    public static TransactionStatus startTransaction(
            PlatformTransactionManager tran, int propagationBehavior,
            int isolationLevel, int timeout, boolean readOnly) {
        return startTransaction(tran, getTransactionDefinition(
                propagationBehavior, isolationLevel, timeout, readOnly), null);
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tran PlatformTransactionManager
     * @param propagationBehavior �g�����U�N�V�����`�����[�h(@see TransactionDefinition) �f�t�H���g�FTransactionDefinition.PROPAGATION_REQUIRED
     * @param isolationLevel �g�����U�N�V�����������x��(@see TransactionDefinition) �f�t�H���g�FTransactionDefinition.ISOLATION_DEFAULT
     * @param timeout �g�����U�N�V�����^�C���A�E�g(�b) �f�t�H���g�FTransactionDefinition.TIMEOUT_DEFAULT (�^�C���A�E�g�Ȃ�)
     * @param readOnly ���[�h�I�����[�g�����U�N�V���� �f�t�H���g�Ffalse
     * @param log Log
     * @return TransactionStatus
     */
    public static TransactionStatus startTransaction(
            PlatformTransactionManager tran, int propagationBehavior,
            int isolationLevel, int timeout, boolean readOnly, Log log) {
        return startTransaction(tran, getTransactionDefinition(
                propagationBehavior, isolationLevel, timeout, readOnly), log);
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tran PlatformTransactionManager
     * @param def TransactionDefinition
     * @return TransactionStatus
     */
    public static TransactionStatus startTransaction(
            PlatformTransactionManager tran, TransactionDefinition def) {
        return startTransaction(tran, def, null);
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tran PlatformTransactionManager
     * @param def TransactionDefinition
     * @param log Log
     * @return TransactionStatus
     */
    public static TransactionStatus startTransaction(
            PlatformTransactionManager tran, TransactionDefinition def, Log log) {
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025033, tran);
                if (def != null) {
                    logger.debug(LogId.DAL025034, def.getPropagationBehavior(),
                            def.getIsolationLevel(), def.getTimeout(), def
                                    .isReadOnly(), def.getName());
                }
            } else {
                String msg033 = LOGGER.getLogMessage(LogId.DAL025033, tran);
                log.debug(msg033);
                if (def != null) {
                    String msg034 = LOGGER.getLogMessage(LogId.DAL025034, def
                            .getPropagationBehavior(), def.getIsolationLevel(),
                            def.getTimeout(), def.isReadOnly(), def.getName());
                    log.debug(msg034);
                }
            }
        }

        TransactionStatus stat = null;
        if (tran != null) {
            stat = tran.getTransaction(def);
        }

        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025035, stat);
            } else {
                String msg035 = LOGGER.getLogMessage(LogId.DAL025035, stat);
                log.debug(msg035);
            }
        }
        return stat;
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tranDef TransactionDefinition
     * @param tranMap PlatformTransactionManager�}�b�v
     * @return TransactionStatus�}�b�v
     */
    public static Map<String, TransactionStatus> startTransactions(
            TransactionDefinition tranDef, Map<?, ?> tranMap) {
        return startTransactions(tranDef, tranMap, null);
    }

    /**
     * �g�����U�N�V�������J�n������
     * @param tranDef TransactionDefinition
     * @param tranMap PlatformTransactionManager�}�b�v
     * @param log Log
     * @return TransactionStatus�}�b�v
     */
    public static Map<String, TransactionStatus> startTransactions(
            TransactionDefinition tranDef, Map<?, ?> tranMap, Log log) {
        Map<String, TransactionStatus> statMap = new LinkedHashMap<String, TransactionStatus>();

        Set<?> entrySet = tranMap.entrySet();

        if (entrySet != null) {
            for (Object entObj : entrySet) {
                String key = null;
                PlatformTransactionManager ptm = null;

                // �}�b�v�G���g���[
                if (entObj instanceof Map.Entry) {
                    Map.Entry<?, ?> ent = (Entry<?, ?>) entObj;
                    if (ent != null) {
                        // �L�[���o��
                        if (ent.getKey() instanceof String) {
                            key = (String) ent.getKey();
                        }
                        // �g�����U�N�V�����}�l�[�W�����o��
                        if (ent.getValue() instanceof PlatformTransactionManager) {
                            ptm = (PlatformTransactionManager) ent.getValue();
                        }
                    }
                }

                if (ptm != null) {

                    if (log != null && log.isDebugEnabled()) {
                        if (log instanceof TLogger) {
                            TLogger logger = (TLogger) log;
                            logger.debug(LogId.DAL025033, key);
                            if (tranDef != null) {
                                logger.debug(LogId.DAL025034, tranDef
                                        .getPropagationBehavior(), tranDef
                                        .getIsolationLevel(), tranDef
                                        .getTimeout(), tranDef.isReadOnly(),
                                        tranDef.getName());
                            }
                        } else {
                            String msg033 = LOGGER.getLogMessage(
                                    LogId.DAL025033, key);
                            log.debug(msg033);
                            if (tranDef != null) {
                                String msg034 = LOGGER.getLogMessage(
                                        LogId.DAL025034, tranDef
                                                .getPropagationBehavior(),
                                        tranDef.getIsolationLevel(), tranDef
                                                .getTimeout(), tranDef
                                                .isReadOnly(), tranDef
                                                .getName());
                                log.debug(msg034);
                            }
                        }
                    }

                    // �g�����U�N�V�����J�n
                    TransactionStatus trnStat = null;
                    try {
                        trnStat = ptm.getTransaction(tranDef);
                    } catch (TransactionException e) {
                        if (log != null && log.isErrorEnabled()) {
                            if (log instanceof TLogger) {
                                TLogger logger = (TLogger) log;
                                logger.error(LogId.EAL025048, e, key);
                            } else {
                                String message = LOGGER.getLogMessage(
                                        LogId.EAL025048, key);
                                log.error(message, e);
                            }
                        }
                        endTransactions(tranMap, statMap, log);
                        throw e;
                    }

                    // �g�����U�N�V�����X�e�[�^�X���i�[
                    if (statMap != null) {
                        statMap.put(key, trnStat);
                    }

                    if (log != null && log.isDebugEnabled()) {
                        if (log instanceof TLogger) {
                            TLogger logger = (TLogger) log;
                            logger.debug(LogId.DAL025036, key, trnStat);
                        } else {
                            String msg036 = LOGGER.getLogMessage(
                                    LogId.DAL025036, key, trnStat);
                            log.debug(msg036);
                        }
                    }
                }
            }
        }

        return statMap;
    }

    /**
     * �g�����U�N�V�������R�~�b�g������ �R�l�N�V�����̃R�~�b�g���s��
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     */
    public static void commitTransaction(PlatformTransactionManager tran,
            TransactionStatus stat) {
        commitTransaction(tran, stat, null);
    }

    /**
     * �g�����U�N�V�������R�~�b�g������ �R�l�N�V�����̃R�~�b�g���s��
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param log Log
     */
    public static void commitTransaction(PlatformTransactionManager tran,
            TransactionStatus stat, Log log) {
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025037, stat);
            } else {
                String msg037 = LOGGER.getLogMessage(LogId.DAL025037, stat);
                log.debug(msg037);
            }
        }

        if (tran != null && stat != null) {
            tran.commit(stat);
        }
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025038, stat);
            } else {
                String msg038 = LOGGER.getLogMessage(LogId.DAL025038, stat);
                log.debug(msg038);
            }
        }
    }

    /**
     * �g�����U�N�V�������R�~�b�g������
     * @param tranMap �g�����U�N�V�����}�b�v
     * @param statMap �g�����U�N�V������ԃ}�b�v
     */
    public static void commitTransactions(Map<?, ?> tranMap,
            Map<String, TransactionStatus> statMap) {
        commitTransactions(tranMap, statMap, null);
    }

    /**
     * �g�����U�N�V�������R�~�b�g������
     * @param tranMap �g�����U�N�V�����}�b�v
     * @param statMap �g�����U�N�V������ԃ}�b�v
     * @param log ���K�[
     */
    public static void commitTransactions(Map<?, ?> tranMap,
            Map<String, TransactionStatus> statMap, Log log) {

        Set<Entry<String, TransactionStatus>> statSet = statMap.entrySet();

        if (statSet.isEmpty()) {
            return;
        }

        Stack<Entry<String, TransactionStatus>> stack = new Stack<Entry<String, TransactionStatus>>();
        for (Entry<String, TransactionStatus> stat : statSet) {
            stack.push(stat);
        }

        while (!stack.isEmpty()) {
            // �g�����U�N�V�����J�n�̋t���X�e�[�^�X�ŃR�~�b�g�����{����B
            Entry<String, TransactionStatus> statEntry = stack.pop();
            String key = statEntry.getKey();
            TransactionStatus trnStat = statEntry.getValue();
            if (trnStat == null) {
                continue;
            }

            // �g�����U�N�V�����}�l�[�W�����o��
            Object ptmObj = tranMap.get(key);
            if (ptmObj == null || !(ptmObj instanceof PlatformTransactionManager)) {
                continue;
            }
            PlatformTransactionManager ptm = (PlatformTransactionManager) ptmObj;

            if (log != null && log.isDebugEnabled()) {
                if (log instanceof TLogger) {
                    TLogger logger = (TLogger) log;
                    logger.debug(LogId.DAL025039, key, trnStat);
                    logger.debug(LogId.DAL025038, trnStat);
                } else {
                    String msg039 = LOGGER.getLogMessage(
                            LogId.DAL025039, key, trnStat);
                    log.debug(msg039);
                    String msg038 = LOGGER.getLogMessage(
                            LogId.DAL025038, trnStat);
                    log.debug(msg038);
                }
            }
            // �R�~�b�g
            ptm.commit(trnStat);
        }
    }

    /**
     * �g�����U�N�V�������I��������i���R�~�b�g�����[���o�b�N�j
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     */
    public static void endTransaction(PlatformTransactionManager tran,
            TransactionStatus stat) {
        endTransaction(tran, stat, null);
    }

    /**
     * �g�����U�N�V�������I��������i���R�~�b�g�����[���o�b�N�j
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param log Log
     */
    public static void endTransaction(PlatformTransactionManager tran,
            TransactionStatus stat, Log log) {
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025040, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025040, stat);
                log.debug(message);
            }
        }

        if (tran != null && stat != null && !stat.isCompleted()) {
            tran.rollback(stat);
        }

        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025041, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025041, stat);
                log.debug(message);
            }
        }
    }

    /**
     * �g�����U�N�V�������I��������i���R�~�b�g�����[���o�b�N�j
     * @param tranMap PlatformTransactionManager�}�b�v
     * @param statMap TransactionStatus�}�b�v
     * @return �����ŗ^����ꂽPlatformTransactionManager���S�Đ���ɏI���ł����ꍇ��true��ԋp����
     */
    public static boolean endTransactions(Map<?, ?> tranMap,
            Map<String, TransactionStatus> statMap) {
        return endTransactions(tranMap, statMap, null);
    }

    /**
     * �g�����U�N�V�������I��������i���R�~�b�g�����[���o�b�N�j
     * @param tranMap PlatformTransactionManager�}�b�v
     * @param statMap TransactionStatus�}�b�v
     * @param log Log
     * @return �����ŗ^����ꂽPlatformTransactionManager���S�Đ���ɏI���ł����ꍇ��true��ԋp����
     */
    public static boolean endTransactions(Map<?, ?> tranMap,
            Map<String, TransactionStatus> statMap, Log log) {
        boolean isNormal = true;

        Set<Entry<String, TransactionStatus>> statSet = statMap.entrySet();

        if (statSet == null || statSet.isEmpty()) {
            return isNormal;
        }

        Stack<Entry<String, TransactionStatus>> stack = new Stack<Entry<String, TransactionStatus>>();
        for (Entry<String, TransactionStatus> stat : statSet) {
            stack.push(stat);
        }

        while (!stack.isEmpty()) {
            // �g�����U�N�V�����J�n�̋t���Ńg�����U�N�V�������I������B
            Entry<String, TransactionStatus> statEntry = stack.pop();
            String key = statEntry.getKey();
            TransactionStatus trnStat = statEntry.getValue();

            if (trnStat == null) {
                continue;
            }
            
            // �g�����U�N�V�����}�l�[�W�����o��
            Object ptmObj = tranMap.get(key);
            if (ptmObj == null || !(ptmObj instanceof PlatformTransactionManager)) {
                continue;
            }
            PlatformTransactionManager ptm = (PlatformTransactionManager) ptmObj;

            // �g�����U�N�V�����I���i�g�����U�N�V�������������̏ꍇ�̓��[���o�b�N����j
            if (trnStat.isCompleted()) {
                continue;
            }

            if (log != null && log.isDebugEnabled()) {
                if (log instanceof TLogger) {
                    TLogger logger = (TLogger) log;
                    logger.debug(LogId.DAL025042, key, trnStat);
                    logger.debug(LogId.DAL025045, trnStat);
                } else {
                    String message42 = LOGGER.getLogMessage(
                            LogId.DAL025042, key, trnStat);
                    log.debug(message42);
                    String message45 = LOGGER.getLogMessage(
                            LogId.DAL025045, trnStat);
                    log.debug(message45);
                }
            }

            // ���[���o�b�N
            try {
                ptm.rollback(trnStat);
            } catch (TransactionException e) {
                if (log != null && log.isErrorEnabled()) {
                    if (log instanceof TLogger) {
                        TLogger logger = (TLogger) log;
                        logger.error(LogId.EAL025045, e, key);
                    } else {
                        String message = LOGGER.getLogMessage(
                               LogId.DAL025045, key);
                        log.error(message, e);
                    }
                }
                isNormal = false;
                // ��O���������Ă��r���I�������A���̃g�����U�N�V�����I�������݂�
            }

            if (log != null && log.isDebugEnabled()) {
                if (log instanceof TLogger) {
                    TLogger logger = (TLogger) log;
                    logger.debug(LogId.DAL025041, trnStat);
                } else {
                    String message = LOGGER.getLogMessage(
                            LogId.DAL025041, trnStat);
                    log.debug(message);
                }
            }
        }
        return isNormal;
    }

    /**
     * �Z�[�u�|�C���g��ݒ肷��
     * @param stat TransactionStatus
     * @return Object �Z�[�u�|�C���g
     */
    public static Object setSavepoint(TransactionStatus stat) {
        return setSavepoint(stat, null);
    }

    /**
     * �Z�[�u�|�C���g��ݒ肷��
     * @param stat TransactionStatus
     * @param log Log
     * @return Object �Z�[�u�|�C���g
     */
    public static Object setSavepoint(TransactionStatus stat, Log log) {
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025046, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025046, stat);
                log.debug(message);
            }
        }

        Object savepoint = stat.createSavepoint();

        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025047, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025047, stat);
                log.debug(message);
            }
        }

        return savepoint;
    }

    /**
     * �Z�[�u�|�C���g�������[�X����
     * @param stat TransactionStatus
     * @param savepoint �Z�[�u�|�C���g
     */
    public static void releaseSavepoint(TransactionStatus stat, Object savepoint) {
        releaseSavepoint(stat, savepoint, null);
    }

    /**
     * �Z�[�u�|�C���g�������[�X����
     * @param stat TransactionStatus
     * @param savepoint �Z�[�u�|�C���g
     * @param log Log
     */
    public static void releaseSavepoint(TransactionStatus stat,
            Object savepoint, Log log) {
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025048, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025048, stat);
                log.debug(message);
            }
        }

        stat.releaseSavepoint(savepoint);

        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025049, savepoint);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025049,
                        savepoint);
                log.debug(message);
            }
        }
    }

    /**
     * �Z�[�u�|�C���g�܂Ń��[���o�b�N������
     * @param stat TransactionStatus
     * @param savepoint �Z�[�u�|�C���g
     */
    public static void rollbackSavepoint(TransactionStatus stat,
            Object savepoint) {
        rollbackSavepoint(stat, savepoint, null);
    }

    /**
     * �Z�[�u�|�C���g�܂Ń��[���o�b�N������
     * @param stat TransactionStatus
     * @param savepoint �Z�[�u�|�C���g
     * @param log Log
     */
    public static void rollbackSavepoint(TransactionStatus stat,
            Object savepoint, Log log) {
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025050, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025050, stat);
                log.debug(message);
            }
        }

        stat.rollbackToSavepoint(savepoint);

        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025051, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025051, stat);
                log.debug(message);
            }
        }
    }

    /**
     * �g�����U�N�V�����J�n�܂Ń��[���o�b�N����B
     * @param tran �g�����U�N�V�����}�l�[�W��
     * @param stat TransactionStatus
     */
    public static void rollbackTransaction(PlatformTransactionManager tran,
            TransactionStatus stat) {
        rollbackTransaction(tran, stat, null);
    }

    /**
     * �g�����U�N�V�����J�n�܂Ń��[���o�b�N����B
     * @param tran �g�����U�N�V�����}�l�[�W��
     * @param stat TransactionStatus
     * @param log Log
     */
    public static void rollbackTransaction(PlatformTransactionManager tran,
            TransactionStatus stat, Log log) {
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025052, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025052, stat);
                log.debug(message);
            }
        }
        if (tran != null && stat != null && !stat.isCompleted()) {
            tran.rollback(stat);
        }
        if (log != null && log.isDebugEnabled()) {
            if (log instanceof TLogger) {
                TLogger logger = (TLogger) log;
                logger.debug(LogId.DAL025053, stat);
            } else {
                String message = LOGGER.getLogMessage(LogId.DAL025053, stat);
                log.debug(message);
            }
        }

    }

    /**
     * �g�����U�N�V�������R�~�b�g�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @return
     */
    public static TransactionStatus commitRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat) {
        commitTransaction(tran, stat, null);
        endTransaction(tran, stat, null);
        return startTransaction(tran);
    }

    /**
     * �g�����U�N�V�������R�~�b�g�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param log Log
     */
    public static TransactionStatus commitRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat, Log log) {
        commitTransaction(tran, stat, log);
        endTransaction(tran, stat, log);
        return startTransaction(tran, log);
    }

    /**
     * �g�����U�N�V�������R�~�b�g�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param def TransactionDefinition
     */
    public static TransactionStatus commitRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat,
            TransactionDefinition def) {
        commitTransaction(tran, stat, null);
        endTransaction(tran, stat, null);
        return startTransaction(tran, def);
    }

    /**
     * �g�����U�N�V�������R�~�b�g�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param def TransactionDefinition
     * @param log Log
     */
    public static TransactionStatus commitRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat,
            TransactionDefinition def, Log log) {
        commitTransaction(tran, stat, log);
        endTransaction(tran, stat, log);
        return startTransaction(tran, def, log);
    }

    /**
     * �g�����U�N�V���������[���o�b�N�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     */
    public static TransactionStatus rollbackRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat) {
        rollbackTransaction(tran, stat, null);
        endTransaction(tran, stat, null);
        return startTransaction(tran);
    }

    /**
     * �g�����U�N�V���������[���o�b�N�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param log Log
     */
    public static TransactionStatus rollbackRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat, Log log) {
        rollbackTransaction(tran, stat, log);
        endTransaction(tran, stat, log);
        return startTransaction(tran, log);
    }

    /**
     * �g�����U�N�V���������[���o�b�N�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param def TransactionDefinition
     */
    public static TransactionStatus rollbackRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat,
            TransactionDefinition def) {
        rollbackTransaction(tran, stat, null);
        endTransaction(tran, stat, null);
        return startTransaction(tran, def);
    }

    /**
     * �g�����U�N�V���������[���o�b�N�����A�g�����U�N�V�������ēx�J�n������
     * @param tran PlatformTransactionManager
     * @param stat TransactionStatus
     * @param def TransactionDefinition
     * @param log Log
     */
    public static TransactionStatus rollbackRestartTransaction(
            PlatformTransactionManager tran, TransactionStatus stat,
            TransactionDefinition def, Log log) {
        rollbackTransaction(tran, stat, log);
        endTransaction(tran, stat, log);
        return startTransaction(tran, def, log);
    }

    /**
     * List��z��^�ɕϊ����� List�̒��ɕ����̌^���������Ă���ꍇ�͎g�p�ł��Ȃ�
     * @param <E> �ԋp�l�̌^
     * @param list ���̓f�[�^
     * @param clazz �ԋp�l�̌^������킷Class�^�̃C���X�^���X
     * @return List�̒��g��z��ɂ�������
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <E> E[] changeListToArray(List<E> list, Class clazz) {

        if (clazz == null) {
            throw new IllegalClassTypeException();
        }

        // SQL�̎��s�F�l�̎擾
        List<E> castedList = list;

        // �z��ɕϊ�
        E[] retArray = (E[]) Array.newInstance(clazz, castedList.size());
        try {
            castedList.toArray(retArray);
        } catch (ArrayStoreException e) {
            throw new IllegalClassTypeException(e);
        }

        return retArray;
    }

    /**
     * .properties�t�@�C������O���[�v�L�[�w��Œl�����o�� �O���[�v�L�[�ɍ��v�����L�[�ɑ΂��ď����\�[�g���s���Ă��� �ԋp���X�g�֒l���Z�b�g���Ă���
     * @param propertyName .properties�t�@�C���̖��O�i.properties�͕K�v�Ȃ��j
     * @param grpKey �O���[�v�L�[
     * @return propertyName�ɑ��݂���grpKeyPrefix
     */
    public static List<String> getProperties(String propertyName, String grpKey) {

        Properties properties = PropertyUtil.loadProperties(propertyName);
        Enumeration<String> propNames = PropertyUtil.getPropertyNames(
                properties, grpKey);

        List<String> propNamesList = Collections.list(propNames);
        Collections.sort(propNamesList);

        List<String> resultList = new ArrayList<String>();

        for (String key : propNamesList) {
            resultList.add(PropertyUtil.getProperty(key));
        }

        return resultList;
    }

    /**
     * Java ���z�}�V���̃��������e�ʁA�g�p�ʁA �g�p�����݂�ő僁�����e�ʂ̏���Ԃ��܂��B
     * @return Java ���z�}�V���̃��������
     */
    public static String getMemoryInfo() {
        DecimalFormat f1 = new DecimalFormat("#,###KB");
        DecimalFormat f2 = new DecimalFormat("##.#");

        Runtime rt = Runtime.getRuntime();
        long free = rt.freeMemory() / 1024;
        long total = rt.totalMemory() / 1024;
        long max = rt.maxMemory() / 1024;
        long used = total - free;
        double ratio = (used * 100 / (double) total);

        StringBuilder sb = new StringBuilder();

        sb.append("Java memory info : ");
        sb.append("used=");
        sb.append(f1.format(used));
        sb.append(" (");
        sb.append(f2.format(ratio));
        sb.append("%), ");
        sb.append("total=");
        sb.append(f1.format(total));
        sb.append(", ");
        sb.append("max=");
        sb.append(f1.format(max));

        return sb.toString();
    }
}

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

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.LogId;
import jp.terasoluna.fw.collector.util.strategy.ComparatorCompareStrategy;
import jp.terasoluna.fw.collector.util.strategy.CompareStrategy;
import jp.terasoluna.fw.logger.TLogger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * �R���g���[���u���C�N�`�F�b�J�[.<br>
 */
public class ControlBreakChecker {

    /**
     * Log.
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(ControlBreakChecker.class);

    /**
     * �R���X�g���N�^.
     */
    protected ControlBreakChecker() {
    }

    /**
     * �O�����R���g���[���u���C�N���胁�\�b�h.<br>
     * @param collector Collector&lt;?&gt;
     * @param keys String...
     * @return true:�R���g���[���u���C�N���s��/false:�R���g���[���u���C�N���Ȃ�
     */
    public static boolean isPreBreak(Collector<?> collector, String... keys) {
        return isPreBreak(collector, null, keys);
    }

    /**
     * �O�����R���g���[���u���C�N���胁�\�b�h.<br>
     * keys�̐���compareStrategies�̐��ƁA��r���ɗ��p�����CompareStrategy�̊֌W�͈ȉ��̂悤�ɂȂ�.<br>
     * <ul>
     * <li>keys : compareStrategies = N : N (�܂���N�ȏ�)�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[i]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : 1�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[0]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : M (N &gt; M)�̏ꍇ
     * <ul>
     * <li>keys[i] (i &lt; M)��CompareStrategy��compareStrategies[i]</li>
     * <li>keys[i] (i &gt;= M)��CompareStrategy��null</li>
     * </ul>
     * </li>
     * </ul>
     * ��r�d�l�́A{@link #equalsObjects(Object, Object, CompareStrategy)} ���Q�Ƃ̂���.
     * @param collector Collector&lt;?&gt;
     * @param compareStrategies CompareStrategy&lt;?&gt;[]
     * @param keys String[]
     * @return true:�R���g���[���u���C�N���s��/false:�R���g���[���u���C�N���Ȃ�
     * @see #equalsObjects(Object, Object, CompareStrategy)
     */
    public static boolean isPreBreak(Collector<?> collector,
            CompareStrategy<?>[] compareStrategies, String[] keys) {
        if (collector != null) {
            Object current = collector.getCurrent();
            Object other = collector.getPrevious();

            return isBreakInternal(current, other, compareStrategies, keys);
        }
        return false;
    }

    /**
     * �㏈���R���g���[���u���C�N���胁�\�b�h.<br>
     * @param collector Collector&lt;?&gt;
     * @param keys String...
     * @return true:�R���g���[���u���C�N���s��/false:�R���g���[���u���C�N���Ȃ�
     */
    public static boolean isBreak(Collector<?> collector, String... keys) {
        return isBreak(collector, null, keys);
    }

    /**
     * �㏈���R���g���[���u���C�N���胁�\�b�h.<br>
     * keys�̐���compareStrategies�̐��ƁA��r���ɗ��p�����CompareStrategy�̊֌W�͈ȉ��̂悤�ɂȂ�.<br>
     * <ul>
     * <li>keys : compareStrategies = N : N (�܂���N�ȏ�)�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[i]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : 1�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[0]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : M (N &gt; M)�̏ꍇ
     * <ul>
     * <li>keys[i] (i &lt; M)��CompareStrategy��compareStrategies[i]</li>
     * <li>keys[i] (i &gt;= M)��CompareStrategy��null</li>
     * </ul>
     * </li>
     * </ul>
     * ��r�d�l�́A{@link #equalsObjects(Object, Object, CompareStrategy)} ���Q�Ƃ̂���.
     * @param collector Collector&lt;?&gt;
     * @param compareStrategies CompareStrategy&lt;?&gt;[]
     * @param keys String[]
     * @return true:�R���g���[���u���C�N���s��/false:�R���g���[���u���C�N���Ȃ�
     * @see #equalsObjects(Object, Object, CompareStrategy)
     */
    public static boolean isBreak(Collector<?> collector,
            CompareStrategy<?>[] compareStrategies, String[] keys) {
        if (collector != null) {
            Object current = collector.getCurrent();
            Object other = collector.getNext();

            return isBreakInternal(current, other, compareStrategies, keys);
        }
        return false;
    }

    /**
     * �R���g���[���u���C�N���胁�\�b�h.<br>
     * ���̃��\�b�h�́Aver.1.1.x�ȑO�Ƃ̌݊�����ۂ��߂Ɏc���Ă���.<br>
     * �V�K�ɍ쐬����R�[�h�̏ꍇ�́A<br>
     * {@link #isBreak(Collector, CompareStrategy[], String[])}�A<br>
     * {@link #isPreBreak(Collector, CompareStrategy[], String[])}�A<br>
     * {@link #isBreakInternal(Object, Object, CompareStrategy[], String...)}<br>
     * ���g�p���邱��.<br>
     * @param current Object ��r���I�u�W�F�N�g
     * @param other Object ��r��I�u�W�F�N�g
     * @param comparators Comparator&lt;?&gt;[]
     * @param keys String...
     * @return true:�R���g���[���u���C�N���s��/false:�R���g���[���u���C�N���Ȃ�
     * @see #isBreak(Collector, CompareStrategy[], String[])
     * @see #isPreBreak(Collector, CompareStrategy[], String[])
     * @see #isBreakInternal(Object, Object, CompareStrategy[], String...)
     */
    protected static boolean isBreakInternal(Object current, Object other,
            Comparator<?>[] comparators, String... keys) {

        // comparator -> compareStrategy�ւ̋l�ߑւ����s��
        if (comparators != null) {
            CompareStrategy<?>[] compareStrategies = new CompareStrategy[comparators.length];

            for (int i = 0; i < comparators.length; i++) {
                compareStrategies[i] = new ComparatorCompareStrategy(
                        comparators[i]);
            }
            return isBreakInternal(current, other, compareStrategies, keys);
        } else {
            return isBreakInternal(current, other, (CompareStrategy[]) null,
                    keys);
        }

    }

    /**
     * �R���g���[���u���C�N���胁�\�b�h.<br>
     * keys�̐���compareStrategies�̐��ƁA��r���ɗ��p�����CompareStrategy�̊֌W�͈ȉ��̂悤�ɂȂ�.<br>
     * <ul>
     * <li>keys : compareStrategies = N : N (�܂���N�ȏ�)�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[i]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : 1�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[0]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : M (N &gt; M)�̏ꍇ
     * <ul>
     * <li>keys[i] (i &lt; M)��CompareStrategy��compareStrategies[i]</li>
     * <li>keys[i] (i &gt;= M)��CompareStrategy��null</li>
     * </ul>
     * </li>
     * </ul>
     * ��r�ɂ� {@link #equalsObjects(Object, Object, CompareStrategy)} ���\�b�h���g�p����.
     * @param current Object ��r���I�u�W�F�N�g
     * @param other Object ��r��I�u�W�F�N�g
     * @param compareStrategies CompareStrategy&lt;?&gt;[]
     * @param keys String...
     * @return true:�R���g���[���u���C�N���s��/false:�R���g���[���u���C�N���Ȃ�
     * @see #equalsObjects(Object, Object, CompareStrategy)
     */
    protected static boolean isBreakInternal(Object current, Object other,
            CompareStrategy<?>[] compareStrategies, String... keys) {

        // key���X�g�����������null�̏ꍇ��false
        if (keys == null || keys.length == 0) {
            // �R���g���[���u���C�N�Ȃ�
            return false;
        }

        // �Е���null�ŁA�����Е���not null�̏ꍇ��true
        if ((current != null && other == null)
                || (current == null && other != null)) {
            // �R���g���[���u���C�N����
            return true;
        }

        if (other != null && current != null) {

            for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
                String key = keys[keyIndex];
                CompareStrategy<?> compareStrategy = null;

                if (compareStrategies != null) {
                    if (compareStrategies.length == 1) {
                        compareStrategy = compareStrategies[0];
                    } else if (keyIndex < compareStrategies.length) {
                        compareStrategy = compareStrategies[keyIndex];
                    }
                }

                if (key != null && key.length() != 0) {
                    Object currentValue = null;
                    Object otherValue = null;

                    // �l���擾����
                    try {
                        currentValue = PropertyUtils.getProperty(current, key);
                    } catch (Exception e) {
                        logOutputPropNotFound(e, current, key);
                        // ���O���o�͂��Ď��̍��ڂ��`�F�b�N
                        continue;
                    }

                    // �l���擾����
                    try {
                        otherValue = PropertyUtils.getProperty(other, key);
                    } catch (Exception e) {
                        logOutputPropNotFound(e, other, key);
                        // ���O���o�͂��Ď��̍��ڂ��`�F�b�N
                        continue;
                    }

                    // ��r
                    if (!equalsObjects(currentValue, otherValue, compareStrategy)) {
                        return true;
                    }
                }
            }
        }
        // �R���g���[���u���C�N�Ȃ�
        return false;
    }

    /**
     * �O�����R���g���[���u���C�N�L�[�擾.<br>
     * @param collector Collector&lt;?&gt;
     * @param keys String...
     * @return �R���g���[���u���C�N�L�[���X�g
     */
    public static Map<String, Object> getPreBreakKey(Collector<?> collector,
            String... keys) {
        return getPreBreakKey(collector, null, keys);
    }

    /**
     * �O�����R���g���[���u���C�N�L�[�擾.<br>
     * keys�̐���compareStrategies�̐��ƁA��r���ɗ��p�����CompareStrategy�̊֌W�͈ȉ��̂悤�ɂȂ�.<br>
     * <ul>
     * <li>keys : compareStrategies = N : N (�܂���N�ȏ�)�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[i]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : 1�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[0]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : M (N &gt; M)�̏ꍇ
     * <ul>
     * <li>keys[i] (i &lt; M)��CompareStrategy��compareStrategies[i]</li>
     * <li>keys[i] (i &gt;= M)��CompareStrategy��null</li>
     * </ul>
     * </li>
     * </ul>
     * ��r�d�l�́A{@link #equalsObjects(Object, Object, CompareStrategy)} ���Q�Ƃ̂���.
     * @param collector Collector&lt;?&gt;
     * @param compareStrategies CompareStrategy&lt;?&gt;[]
     * @param keys String[]
     * @return �R���g���[���u���C�N�L�[���X�g
     * @see #equalsObjects(Object, Object, CompareStrategy)
     */
    public static Map<String, Object> getPreBreakKey(Collector<?> collector,
            CompareStrategy<?>[] compareStrategies, String[] keys) {
        if (collector != null) {
            Object current = collector.getCurrent();
            Object other = collector.getPrevious();

            return getBreakKeyInternal(current, other,
                    (CompareStrategy<?>[]) compareStrategies, keys);
        }
        return new LinkedHashMap<String, Object>();
    }

    /**
     * �㏈���R���g���[���u���C�N�L�[�擾.<br>
     * @param collector Collector&lt;?&gt;
     * @param keys String...
     * @return �R���g���[���u���C�N�L�[���X�g
     */
    public static Map<String, Object> getBreakKey(Collector<?> collector,
            String... keys) {
        return getBreakKey(collector, null, keys);
    }

    /**
     * �㏈���R���g���[���u���C�N�L�[�擾.<br>
     * keys�̐���compareStrategies�̐��ƁA��r���ɗ��p�����CompareStrategy�̊֌W�͈ȉ��̂悤�ɂȂ�.<br>
     * <ul>
     * <li>keys : compareStrategies = N : N (�܂���N�ȏ�)�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[i]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : 1�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[0]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : M (N &gt; M)�̏ꍇ
     * <ul>
     * <li>keys[i] (i &lt; M)��CompareStrategy��compareStrategies[i]</li>
     * <li>keys[i] (i &gt;= M)��CompareStrategy��null</li>
     * </ul>
     * </li>
     * </ul>
     * ��r�d�l�́A{@link #equalsObjects(Object, Object, CompareStrategy)} ���Q�Ƃ̂���.
     * @param collector Collector&lt;?&gt;
     * @param compareStrategies CompareStrategy&lt;?&gt;[]
     * @param keys String[]
     * @return �R���g���[���u���C�N�L�[���X�g
     * @see #equalsObjects(Object, Object, CompareStrategy)
     */
    public static Map<String, Object> getBreakKey(Collector<?> collector,
            CompareStrategy<?>[] compareStrategies, String[] keys) {
        if (collector != null) {
            Object current = collector.getCurrent();
            Object other = collector.getNext();

            return getBreakKeyInternal(current, other,
                    (CompareStrategy<?>[]) compareStrategies, keys);
        }
        return new LinkedHashMap<String, Object>();
    }

    /**
     * �R���g���[���u���C�N�L�[�擾.<br>
     * ���̃��\�b�h�́Aver.1.1.x�ȑO�Ƃ̌݊�����ۂ��߂Ɏc���Ă���.<br>
     * �V�K�ɍ쐬����R�[�h�̏ꍇ�́A<br>
     * {@link #getBreakKey(Collector, CompareStrategy[], String[])}�A<br>
     * {@link #getPreBreakKey(Collector, CompareStrategy[], String[])}�A<br>
     * {@link #getBreakKeyInternal(Object, Object, CompareStrategy[], String...)}<br>
     * ���g�p���邱��.<br>
     * @param current Object ��r���I�u�W�F�N�g
     * @param other Object ��r��I�u�W�F�N�g
     * @param comparators Comparator&lt;?&gt;[]
     * @param keys String...
     * @return �R���g���[���u���C�N�L�[���X�g
     * @see #getBreakKey(Collector, CompareStrategy[], String[])
     * @see #getPreBreakKey(Collector, CompareStrategy[], String[])
     * @see #getBreakKeyInternal(Object, Object, CompareStrategy[], String...)
     */
    protected static Map<String, Object> getBreakKeyInternal(Object current,
            Object other, Comparator<?>[] comparators, String... keys) {

        // comparator -> compareStrategy�ւ̋l�ߑւ����s��
        if (comparators != null) {
            CompareStrategy<?>[] compareStrategies = new CompareStrategy[comparators.length];

            for (int i = 0; i < comparators.length; i++) {
                compareStrategies[i] = new ComparatorCompareStrategy(
                        comparators[i]);
            }
            return getBreakKeyInternal(current, other, compareStrategies, keys);
        } else {
            return getBreakKeyInternal(current, other,
                    (CompareStrategy[]) null, keys);
        }
    }

    /**
     * �R���g���[���u���C�N�L�[�擾.<br>
     * keys�̐���compareStrategies�̐��ƁA��r���ɗ��p�����CompareStrategy�̊֌W�͈ȉ��̂悤�ɂȂ�.<br>
     * <ul>
     * <li>keys : compareStrategies = N : N (�܂���N�ȏ�)�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[i]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : 1�̏ꍇ
     * <ul>
     * <li>keys[i]��CompareStrategy��compareStrategies[0]</li>
     * </ul>
     * </li>
     * <li>keys : compareStrategies = N : M (N &gt; M)�̏ꍇ
     * <ul>
     * <li>keys[i] (i &lt; M)��CompareStrategy��compareStrategies[i]</li>
     * <li>keys[i] (i &gt;= M)��CompareStrategy��null</li>
     * </ul>
     * </li>
     * </ul>
     * ��r�ɂ� {@link #equalsObjects(Object, Object, CompareStrategy)} ���\�b�h���g�p����.
     * @param current Object ��r���I�u�W�F�N�g
     * @param other Object ��r��I�u�W�F�N�g
     * @param compareStrategies CompareStrategy&lt;?&gt;[]
     * @param keys String...
     * @return �R���g���[���u���C�N�L�[���X�g
     * @see #equalsObjects(Object, Object, CompareStrategy)
     */
    protected static Map<String, Object> getBreakKeyInternal(Object current,
            Object other, CompareStrategy<?>[] compareStrategies,
            String... keys) {
        boolean inBreak = false;
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        // key���X�g�����������null�̏ꍇ��false
        if (keys == null || keys.length == 0) {
            // �R���g���[���u���C�N�Ȃ�
            return result;
        }

        for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
            String key = keys[keyIndex];
            CompareStrategy<?> compareStrategy = null;
            Object currentValue = null;
            Object otherValue = null;

            if (compareStrategies != null) {
                if (compareStrategies.length == 1) {
                    compareStrategy = compareStrategies[0];
                } else if (keyIndex < compareStrategies.length) {
                    compareStrategy = compareStrategies[keyIndex];
                }
            }

            if (key != null && key.length() != 0) {

                // �l���擾����
                if (current != null) {
                    try {
                        currentValue = PropertyUtils.getProperty(current, key);
                    } catch (Exception e) {
                        logOutputPropNotFound(e, current, key);
                        // ���O���o�͂��Ď��̍��ڂ��`�F�b�N
                        continue;
                    }
                }

                // �l���擾����
                if (other != null) {
                    try {
                        otherValue = PropertyUtils.getProperty(other, key);
                    } catch (Exception e) {
                        logOutputPropNotFound(e, other, key);
                        // ���O���o�͂��Ď��̍��ڂ��`�F�b�N
                        continue;
                    }
                }

                if (!inBreak) {
                    // �Е���null�ŁA�����Е���not null�̏ꍇ��true
                    if ((current != null && other == null)
                            || (current == null && other != null)) {
                        // �R���g���[���u���C�N����
                        inBreak = true;
                    }

                    // ��r
                    if (!equalsObjects(currentValue, otherValue, compareStrategy)) {
                        // �R���g���[���u���C�N����
                        inBreak = true;
                    }
                }
            }

            if (inBreak) {
                result.put(key, currentValue);
            }
        }
        return result;
    }

    /**
     * ����I�u�W�F�N�g�ƕʂ̃I�u�W�F�N�g�����������ǂ�����r����.<br>
     * <code>equalsObjects(value1, value2, null);</code> �����s����̂Ɠ����ł���.<br>
     * ���̃��\�b�h�́Aver.1.1.x�ȑO�Ƃ̃R���p�C���݊�����ۂ��߂Ɏc���Ă���.<br>
     * 
     * �ȉ��̃N���X��<b>����</b>�AComparable�����N���X�̃C���X�^���X���m�̔�r�́A
     * ver.1.1.x�ȑO�Ƃ͔�r���ʂ��قȂ�\��������.<br>
     * <ul>
     * <li>ver.1.1.x�ȑO�ł�equals���\�b�h�Ŕ�r����A���A
     * Comparable�̎�����equals�̎����Ɉ�ѐ�������N���X.
     * <ul>
     * <li>java.math.BigInteger</li>
     * <li>java.lang.Byte</li>
     * <li>java.lang.Double</li>
     * <li>java.lang.Float</li>
     * <li>java.lang.Integer</li>
     * <li>java.lang.Long</li>
     * <li>java.lang.Short</li>
     * <li>java.lang.Boolean</li>
     * <li>java.lang.Character</li>
     * <li>java.lang.String</li>
     * <li>java.util.Date(java.sql.Date���̃T�u�N���X���܂܂Ȃ�)</li>
     * </ul>
     * </li>
     * </ul>
     * 
     * ���̃��\�b�h�̑���ɁA<br>
     * {@link #equalsObjects(Object, Object, CompareStrategy)}<br>
     * ���g�p���邱��.<br>
     * @param value1 Object ��r���I�u�W�F�N�g
     * @param value2 Object ��r��I�u�W�F�N�g
     * @return �������ꍇ:true / �����łȂ��ꍇ:false
     * @see #equalsObjects(Object, Object, CompareStrategy)
     * @deprecated ���̃��\�b�h�̑���ɁA{@link #equalsObjects(Object, Object, CompareStrategy)}���g�p���邱��.
     */
    protected static boolean equalsObjects(Object value1, Object value2) {
        return equalsObjects(value1, value2, null);
    }

    /**
     * ���O�o�́i�v���p�e�B��������Ȃ������ꍇ�j.<br>
     * @param e Exception
     * @param obj Object
     * @param key String
     */
    protected static void logOutputPropNotFound(Exception e, Object obj,
            String key) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(LogId.WAL041002, key, obj == null ? null : obj
                    .getClass().getSimpleName(),
                    e == null ? null : e.getMessage());
        }
    }

    /**
     * ����I�u�W�F�N�g�ƕʂ̃I�u�W�F�N�g�����������ǂ�����r����.<br>
     * <ul>
     * <li>�ǂ����null�łȂ��ꍇ�A�ȉ��̂悤�ɔ�r����.
     * <ul>
     * <li>compareStrategy��null�łȂ��ꍇ�AcompareStrategy�Ŕ�r</li>
     * <li>compareStrategy��null�ł���ꍇ�A��r���I�u�W�F�N�g�̌^�ɉ����āA�ȉ��̂悤�ɔ�r
     * <ul>
     * <li>��r���I�u�W�F�N�g��Comparable�����N���X�̃C���X�^���X�̏ꍇ�AComparable#compareTo�Ŕ�r
     * <li>��r���I�u�W�F�N�g��Class�܂��͂��̃X�[�p�[�N���X�̃C���X�^���X�̏ꍇ�AObject#equals�Ŕ�r
     * <li>��L2�ȊO�̏ꍇ�Aorg.apache.commons.lang.builder.EqualsBuilder#reflectionEquals�Ŕ�r
     * </ul>
     * </li>
     * </ul>
     * </li>
     * <li>�ǂ����null�̏ꍇ�A�������Ƃ݂Ȃ�.</li>
     * <li>�ǂ�������݂̂�null�̏ꍇ�A�����Ȃ��Ƃ݂Ȃ�.</li>
     * </ul>
     * @param value1 Object ��r���I�u�W�F�N�g
     * @param value2 Object ��r��I�u�W�F�N�g
     * @param compareStrategy CompareStrategy
     * @return �������ꍇ:true / �����łȂ��ꍇ:false
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected static boolean equalsObjects(Object value1,
            Object value2, CompareStrategy compareStrategy) {

        if (value1 != null && value2 != null) {
            // value1,value2�̂ǂ���ɂ��l�������Ă���ꍇ
            if (compareStrategy != null) {
                return compareStrategy.equalsObjects(value1, value2);
            } else {
                Class<? extends Object> clazz = value1.getClass();

                if (value1 instanceof Comparable) {
                    // value1��Comparable���������Ă���ꍇ��compareTo�Ŕ�r����
                    return (((Comparable) value1).compareTo(value2) == 0);
                } else if (clazz.isAssignableFrom(Class.class)) {
                    // value1��Class�܂��͂��̃X�[�p�[�N���X�̃C���X�^���X�̏ꍇObject#equals�Ŕ�r����
                    return value1.equals(value2);
                } else {
                    // ����ȊO�̏ꍇ��reflectionEquals�Ŕ�r����
                    return EqualsBuilder.reflectionEquals(value1, value2);
                }
            }
        } else if (value1 == null && value2 == null) {
            // value1,value2�̂ǂ���ɂ��l�������Ă��Ȃ��ꍇ
            return true;
        }
        // value1,value2�̂ǂ��炩����ɒl�������Ă���ꍇ
        return false;

    }
}

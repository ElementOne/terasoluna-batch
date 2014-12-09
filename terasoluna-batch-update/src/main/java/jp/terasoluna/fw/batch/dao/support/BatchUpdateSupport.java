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

import java.util.Comparator;
import java.util.List;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;

/**
 * �o�b�`�X�V�T�|�[�g�C���^�t�F�[�X<br>
 * <p>
 * �{�N���X�̃��\�b�h���s�O��sort���\�b�h�����s���Ă���ꍇ�A<br>
 * �������̓R���X�g���N�^����s���\�b�h�Ȃǂ�Comparator��n�����ꍇ�́A<br>
 * �\�[�g���ɕ��ёւ�����B<br>
 * </p>
 * @see UpdateDAO
 */
public interface BatchUpdateSupport {
    /** UpdateDAO��null�̂Ƃ��̃G���[�X�e�[�^�X */
    int ERROR_UPDATE_DAO_IS_NULL = -100;

    /** sqlIdOrder�Ɏw�肳��Ă��Ȃ�SQL-ID���w�肳��Ă���Ƃ��̃G���[�X�e�[�^�X */
    int ERROR_UNKNOWN_SQL_ID = -200;

    /**
     * �o�b�`���s�pSQL��ǉ�����B<br>
     * @param sqlID String
     * @param bindParams Object
     */
    void addBatch(final String sqlID, final Object bindParams);

    /**
     * �o�b�`���s���s���B<br>
     * <p>
     * �o�b�`���s�����������ꍇ�́A�ێ����Ă���o�b�`���sSQL���X�g�̍폜���s���B
     * </p>
     * <p>
     * �{���\�b�h�Ŏ��s����ꍇ�́A�R���X�g���N�^������UpdateDAO��n���Ă������ƁB<br>
     * UpdateDAO���R���X�g���N�^�����œn����Ă��Ȃ��ꍇ�́A-100���ԋp�����B
     * </p>
     * @return SQL�̎��s����
     * @see UpdateDAO
     */
    int executeBatch();

    /**
     * �o�b�`���s���s���B<br>
     * <p>
     * �o�b�`���s�����������ꍇ�́A�ێ����Ă���o�b�`���sSQL���X�g�̍폜���s���B
     * </p>
     * <p>
     * �{���\�b�h�Ŏ��s����ꍇ�́A�R���X�g���N�^�����œn���ꂽUpdateDAO�͎g�p����Ȃ��B<br>
     * UpdateDAO��null�̏ꍇ�́A-100���ԋp�����B
     * </p>
     * @param updateDAO UpdateDAO
     * @return SQL�̎��s����
     * @see UpdateDAO
     */
    int executeBatch(UpdateDAO updateDAO);

    /**
     * �o�b�`���s���s���B<br>
     * <p>
     * �o�b�`���s�����������ꍇ�́A�ێ����Ă���o�b�`���sSQL���X�g�̍폜���s���B
     * </p>
     * <p>
     * �{���\�b�h�Ŏ��s����ꍇ�́A�R���X�g���N�^�����œn���ꂽUpdateDAO�͎g�p����Ȃ��B<br>
     * UpdateDAO��null�̏ꍇ�́A-100���ԋp�����B
     * </p>
     * <p>
     * Comparator��n�����Ƃɂ��SQL���s���������𐧌�ł���B<br>
     * ��Comparator��n�����ꍇ�͕K���\�[�g���s����BComparator�̑召���f�Ɋ�Â��A�����Ń\�[�g���s����B
     * </p>
     * @param updateDAO UpdateDAO
     * @param comparator Comparator&lt;String&gt;
     * @return SQL�̎��s����
     * @see UpdateDAO
     */
    int executeBatch(UpdateDAO updateDAO, Comparator<String> comparator);

    /**
     * �o�b�`���s���s���B<br>
     * <p>
     * �o�b�`���s�����������ꍇ�́A�ێ����Ă���o�b�`���sSQL���X�g�̍폜���s���B
     * </p>
     * <p>
     * �{���\�b�h�Ŏ��s����ꍇ�́A�R���X�g���N�^�����œn���ꂽUpdateDAO�͎g�p����Ȃ��B<br>
     * UpdateDAO��null�̏ꍇ�́A-100���ԋp�����B
     * </p>
     * <p>
     * �������ȍ~�Ɏ��s����SQL-ID�����Ԃɐݒ肷�邱�Ƃɂ��SQL���s�����𐧌�ł���B<br>
     * sqlIdOrder��SQL-ID���w�肵���ꍇ�́A�K�����̏��Ԃ�SQL�����s�����B<br>
     * �܂��A���̍ۂ�sqlIdOrder�Ɏw�肳��Ă��Ȃ�SQL-ID�����݂����ꍇ�͎��s���ꂸ��-200���ԋp�����B<br>
     * </p>
     * @param updateDAO UpdateDAO
     * @param sqlIdOrder SQL-ID�̎��s�������w�肷��
     * @return SQL�̎��s����
     * @see UpdateDAO
     */
    int executeBatch(UpdateDAO updateDAO, String... sqlIdOrder);

    /**
     * �o�b�`���sSQL���X�g�̃\�[�g���s���B<br>
     * <p>
     * executeBatch��getSqlHolderList�����s����O�ɖ{���\�b�h�����s���邱�ƂŁA<br>
     * SQL�̎��s������SQL-ID�̏����ɕ��ёւ��邱�Ƃ��ł���B
     * </p>
     * <p>
     * ���{���\�b�h���s�^�C�~���O�Ŏ��ۂɓ����Ń\�[�g���s���邩�ǂ����͋K�肵�Ȃ�
     * </p>
     */
    void sort();

    /**
     * �o�b�`���sSQL���X�g�̃\�[�g���s���B<br>
     * <p>
     * executeBatch��getSqlHolderList�����s����O�ɖ{���\�b�h�����s���邱�ƂŁA<br>
     * SQL�̎��s������Comparator�ɏ]���ĕ��ёւ��邱�Ƃ��ł���B
     * </p>
     * <p>
     * Comparator��n�����Ƃɂ��SQL�̕��ёւ������𐧌�ł���B<br>
     * �i��Comparator��n�����ꍇ�͕K���\�[�g���s����BComparator�̑召���f�Ɋ�Â��A�����Ń\�[�g���s����B�j
     * </p>
     * <p>
     * ���{���\�b�h���s�^�C�~���O�Ŏ��ۂɓ����Ń\�[�g���s���邩�ǂ����͋K�肵�Ȃ�
     * </p>
     * @param comparator Comparator&lt;String&gt;
     */
    void sort(Comparator<String> comparator);

    /**
     * �o�b�`���sSQL���X�g�̓��e���폜����B<br>
     * <p>
     * �ێ����Ă���o�b�`���sSQL���X�g�̍폜���s���B
     * </p>
     */
    void clear();

    /**
     * �o�b�`���sSQL���X�g�̓o�^�������擾����B<br>
     * @return �o�b�`���sSQL���X�g�̓o�^����
     */
    long size();

    /**
     * SQL-ID�Ő��񂳂ꂽSqlHolder���X�g���擾����B<br>
     * <p>
     * �ێ����Ă���o�b�`���sSQL���X�g��SQL-ID���ɐ��񂵂����X�g��ԋp����B
     * </p>
     * <p>
     * ���{���\�b�h�����s���Ă��ێ����Ă���o�b�`���sSQL���X�g�͍폜����Ȃ��B
     * </p>
     * @return SqlHolder���X�g
     */
    List<SqlHolder> getSqlHolderList();

    /**
     * SQL-ID�Ő��񂳂ꂽSqlHolder���X�g���擾����B<br>
     * <p>
     * �ێ����Ă���o�b�`���sSQL���X�g��SQL-ID���ɐ��񂵂����X�g��ԋp����B
     * </p>
     * <p>
     * ���{���\�b�h�����s���Ă��ێ����Ă���o�b�`���sSQL���X�g�͍폜����Ȃ��B
     * </p>
     * <p>
     * Comparator��n�����Ƃɂ��SQL�̕��ёւ������𐧌�ł���B<br>
     * ��Comparator��n�����ꍇ�͕K���\�[�g���s����BComparator�̑召���f�Ɋ�Â��A�����Ń\�[�g���s����B
     * </p>
     * @param comparator Comparator&lt;String&gt;
     * @return SqlHolder���X�g
     */
    List<SqlHolder> getSqlHolderList(Comparator<String> comparator);

    /**
     * SQL-ID�Ő��񂳂ꂽSqlHolder���X�g���擾����B<br>
     * <p>
     * �ێ����Ă���o�b�`���sSQL���X�g��SQL-ID���ɐ��񂵂����X�g��ԋp����B
     * </p>
     * <p>
     * ���{���\�b�h�����s���Ă��ێ����Ă���o�b�`���sSQL���X�g�͍폜����Ȃ��B
     * </p>
     * <p>
     * ���s����SQL-ID�����Ԃɐݒ肷�邱�Ƃɂ��擾��SqlHolder���X�g�̏����𐧌�ł���B<br>
     * sqlIdOrder��SQL-ID���w�肵���ꍇ�́A�K�����̏��Ԃ�SQL�����ёւ�����B<br>
     * �܂��A���̍ۂ�sqlIdOrder�Ɏw�肳��Ă��Ȃ�SQL-ID�����݂����ꍇ��null���ԋp�����B<br>
     * </p>
     * @param sqlIdOrder SQL-ID�̎��s�������w�肷��
     * @return SqlHolder���X�g
     */
    List<SqlHolder> getSqlHolderList(String... sqlIdOrder);

}

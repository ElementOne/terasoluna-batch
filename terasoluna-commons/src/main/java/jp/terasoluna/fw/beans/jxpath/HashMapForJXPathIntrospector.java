/*
 * Copyright (c) 2012 NTT DATA Corporation
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

package jp.terasoluna.fw.beans.jxpath;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * commons-JXPath�̃o�O(JXPATH-152)���pHashMap�B
 * <p>
 * commons-JXPath-1.3��
 * JXPathIntrospector�̎����ɍ��킹�āA
 * put��get�̂݁A�X���b�h�Z�[�t�����Ă���B<br>
 * ����������ɂ́AReadWriteLock�𗘗p���Ă���A
 * put�̎��s���́A���̃X���b�h��put��get�����s�ł��Ȃ�(�ꎞ�I�ɑ҂���ԂƂȂ�)���A
 * put�̎��s���łȂ���΁A�����̃X���b�h�œ�����get�����s���邱�Ƃ��ł���B
 * </p>
 * @see JXPATH152PatchActivator
 */
public class HashMapForJXPathIntrospector<K, V> extends HashMap<K, V> {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = 1944915046869984094L;

    /**
     * �ǂݍ��݃��b�N�Ə������݃��b�N�̃y�A�𐧌䂷��ReadWriteLock�B
     */
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * �ǂݍ��݃��b�N�B
     */
    private final Lock readLock = readWriteLock.readLock();

    /**
     * �������݃��b�N�B
     */
    private final Lock writeLock = readWriteLock.writeLock();

    /**
     * �w�肳�ꂽ Map �Ɠ����}�b�s���O�ŐV�K HashMapForJXPathIntrospector ���쐬����B
     * @param m �����}�b�s���O��ێ������}�b�v(JXPathIntrospector����擾�����}�b�v)
     * @throws NullPointerException �w�肳�ꂽ�}�b�v�� null �̏ꍇ
     */
    public HashMapForJXPathIntrospector(Map<? extends K, ? extends V> m) {
        super(m);
    }

    /**
     * �L�[�Ƀ}�b�s���O����Ă���l��Ԃ��B
     * <p>
     * ���̃��\�b�h�́A�ǂݍ��݃��b�N���l��������ԂŁA{@link HashMap#get(Object)}�ɈϏ�����B<br>
     * </p>
     * @param key �L�[
     * @see HashMap#get(Object)
     */
    @Override
    public V get(Object key) {
        readLock.lock();
        try {
            return super.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * �w�肳�ꂽ�L�[�Ŏw�肳�ꂽ�l���}�b�s���O����B
     * <p>
     * ���̃��\�b�h�́A�������݃��b�N���l��������ԂŁA{@link HashMap#put(Object, Object)}�ɈϏ�����B<br>
     * </p>
     * @param key �L�[
     * @param value �l
     * @see HashMap#put(Object, Object)
     */
    @Override
    public V put(K key, V value) {
        writeLock.lock();
        try {
            return super.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}

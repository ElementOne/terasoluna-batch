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

package jp.terasoluna.fw.collector.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AbstractCollector�pArrayBlockingQueue�T�u�N���X�B
 * <p>
 * {@link ArrayBlockingQueue#peek()}�� {@link ArrayBlockingQueue#isEmpty()}�ɁA �L���[����ł���΁A�L���[�ɗv�f�����邩�A�L���[�C���O�I���t���O���オ��܂� �҂@�\�����Ă���B<br>
 * ArrayBlockingQueue���́A�u���b�N������s���Ă���Condition�t�B�[���h�� �T�u�N���X�Ɍ��J����Ă��Ȃ����߁A ���̃N���X�ł�ArrayBlockingQueue�Ə璷�Ȏ��������Ă���B<br>
 * </p>
 * <p>
 * ������AbstractCollector�Ɏg�p�������̂ɍi���Ă��邽�߁A ���ׂẴ��\�b�h���g�p�ł���킯�ł͂Ȃ��B<br>
 * ���̃N���X�ŃI�[�o�[���C�h���Ă��郁�\�b�h�ȊO�ŁA �L���[�̏�Ԃ�ύX���郁�\�b�h��A�҂����������郁�\�b�h�����s���Ă͂Ȃ�Ȃ��B
 * </p>
 * <p>
 * �L���[�ɗv�f���l�ߏI�������́A�L���[�ɗv�f���l�߂�X���b�h�ŁA�K��finishQueueing���\�b�h�����s���邱�ƁB
 * </p>
 * @param <E> �R���N�V�������ɑ��݂���v�f�̌^
 */
public class ArrayBlockingQueueEx<E> extends ArrayBlockingQueue<E>
                                                                  implements
                                                                  NotificationBlockingQueue<E> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7441765139909417804L;

    /**
     * �L���[�̑���𓯊������郍�b�N�B
     */
    protected final ReentrantLock queueLock = new ReentrantLock();

    /**
     * �L���[����łȂ��Ȃ����Ƃ��ɑ��M�����V�O�i���B
     */
    protected final Condition notEmpty = queueLock.newCondition();

    /**
     * �L���[��Full�łȂ��Ȃ����Ƃ��ɑ��M�����V�O�i���B
     */
    protected final Condition notFull = queueLock.newCondition();

    /**
     * �L���[�T�C�Y�B
     */
    protected final int capacity;

    /**
     * �L���[�C���O�I���t���O�B
     */
    protected volatile boolean finishQueueingFlag = false;

    /**
     * �w�肳�ꂽ (�Œ�) �e�ʂ���юw�肳�ꂽ�A�N�Z�X�|���V�[���g�p���āAArrayBlockingQueue ���쐬����B
     * @param capacity �L���[�̗e��
     * @param fair true �̏ꍇ�A�}���܂��͍폜���Ƀu���b�N���ꂽ�X���b�h�ɑ΂���L���[�A�N�Z�X�́AFIFO �̏����ŏ��������B false �̏ꍇ�A�A�N�Z�X�����͎w�肳��Ȃ��B
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int, boolean)
     */
    public ArrayBlockingQueueEx(int capacity, boolean fair) {
        super(capacity, fair);
        this.capacity = capacity;
    }

    /**
     * �w�肳�ꂽ (�Œ�) �e�ʂ���уf�t�H���g�̃A�N�Z�X�|���V�[���g�p���āAArrayBlockingQueue ���쐬����B
     * @param capacity �L���[�̗e��
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int)
     */
    public ArrayBlockingQueueEx(int capacity) {
        super(capacity);
        this.capacity = capacity;
    }

    /**
     * �L���[�C���O�̏I����ʒm����B
     * <p>
     * �L���[�ɗv�f������̂�҂��Ă���X���b�h������ꍇ�A���̃u���b�N����������B �L���[�ɗv�f���l�߂�X���b�h�́A�L���[�C���O�������������ƂŁA�K�����̃��\�b�h�����s���邱�ƁB
     * </p>
     */
    public void finishQueueing() {
        queueLock.lock();
        try {
            finishQueueingFlag = true;

            // �v�f�̓���҂����s���Ă���X���b�h�̃u���b�N����������
            notEmpty.signalAll();
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * �w�肳�ꂽ�v�f�����̃L���[�̖����ɑ}������B�K�v�ɉ����A��Ԃ����p�\�ɂȂ�̂��w�肳�ꂽ���Ԃ܂őҋ@����B
     * <p>
     * ���̃��\�b�h�̒�`�́A{@link ArrayBlockingQueue#offer(Object, long, TimeUnit)}�Ɠ����B
     * </p>
     * @param o �ǉ�����v�f
     * @param timeout �����𒆎~����܂ł̑ҋ@���ԁB�P�ʂ� unit
     * @param unit timeout �p�����[�^�̉��ߕ��@���w�肷�� TimeUnit
     * @return ���������ꍇ�� true�A��Ԃ����p�\�ɂȂ�O�Ɏw�肳�ꂽ�ҋ@���Ԃ��o�߂����ꍇ�� false
     * @throws InterruptedException �ҋ@���Ɋ��荞�݂����������ꍇ
     * @throws NullPointerException �w�肳�ꂽ�v�f�� null �ł���ꍇ
     * @see ArrayBlockingQueue#offer(Object, long, TimeUnit)
     */
    @Override
    public boolean offer(E o, long timeout, TimeUnit unit)
                                                          throws InterruptedException {
        if (o == null) {
            throw new NullPointerException();
        }
        long nanos = unit.toNanos(timeout);
        queueLock.lockInterruptibly();
        try {
            while (size() == capacity) {

                // �L���[���󂭂̂�҂�
                nanos = notFull.awaitNanos(nanos);
                if (nanos <= 0) {

                    // �^�C���A�E�g
                    return false;
                }
            }
            boolean success = super.offer(o);
            if (success) {

                // �v�f�̓���҂����s���Ă���X���b�h�̃u���b�N����������
                notEmpty.signal();
            }
            return success;
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * �\�ł���΁A���̃L���[�̖����Ɏw�肳�ꂽ�v�f��}������B���̃L���[�������ς��ł���ꍇ�ɂ́A�����ɕԂ��B
     * <p>
     * ���̃��\�b�h�̒�`�́A{@link ArrayBlockingQueue#offer(Object)}�Ɠ����B
     * </p>
     * @param o �ǉ�����v�f
     * @return �v�f�����̃L���[�ɒǉ��\�ȏꍇ�� true�A�����łȂ��ꍇ�� false
     * @throws NullPointerException �w�肳�ꂽ�v�f�� null �ł���ꍇ
     * @see ArrayBlockingQueue#offer(Object)
     */
    @Override
    public boolean offer(E o) {
        queueLock.lock();
        try {
            if (size() == capacity) {
                return false;
            }
            boolean success = super.offer(o);
            if (success) {

                // �v�f�̓���҂����s���Ă���X���b�h�̃u���b�N����������
                notEmpty.signal();
            }
            return success;
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * �w�肳�ꂽ�v�f�����̃L���[�̖����ɒǉ�����B�K�v�ɉ����A��Ԃ����p�\�ɂȂ�܂őҋ@����B
     * @param o �ǉ�����v�f
     * @throws InterruptedException �ҋ@���Ɋ��荞�݂����������ꍇ
     * @throws NullPointerException �w�肳�ꂽ�v�f�� null �ł���ꍇ
     */
    @Override
    public void put(E o) throws InterruptedException {
        if (o == null) {
            throw new NullPointerException();
        }
        queueLock.lock();
        try {
            while (size() == capacity) {

                // �L���[���󂭂̂�҂�
                notFull.await();
            }
            super.put(o);

            // �v�f�̓���҂����s���Ă���X���b�h�̃u���b�N����������
            notEmpty.signal();
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * �L���[�̐擪���擾���邪�A�폜���Ȃ��B
     * <p>
     * �g���d�l�F<b> �L���[����̏ꍇ�́A�L���[�ɗv�f�����邩�A�L���[�C���O�̏I�����ʒm�����܂ő҂B<br>
     * �L���[�C���O�̏I�����ʒm���ꂽ��A�L���[����̏ꍇ�� null ��Ԃ��B
     * </p>
     * <p>
     * �L���[�ɗv�f������ꍇ��A�L���[�C���O�̏I�����ʒm���ꂽ��̎d�l�́A {@link ArrayBlockingQueue#peek()}�Ɠ����B
     * </p>
     * @return �L���[�̐擪�B�L���[�C���O�I����ɃL���[����̏ꍇ�� null
     */
    @Override
    public E peek() {
        queueLock.lock();
        try {
            while (!finishQueueingFlag && size() == 0) {
                try {

                    // �L���[�ɗv�f������̂��̂�҂�
                    notEmpty.await();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return super.peek();
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * ���̃L���[�̐擪���擾����э폜����B���̃L���[�ɗv�f�����݂��Ȃ��ꍇ�́A�K�v�ɉ����Ďw�肳�ꂽ���Ԃ����ҋ@����B
     * <p>
     * �g���d�l�F<b> �L���[�C���O�̏I�����ʒm���ꂽ��A�L���[����̏ꍇ�́A�^�C���A�E�g��҂����� null ��Ԃ��B
     * </p>
     * <p>
     * �L���[�C���O�̏I�����ʒm�����O�̎d�l�́A {@link ArrayBlockingQueue#poll(long, TimeUnit)}�Ɠ����B
     * </p>
     * @param timeout �����𒆎~����܂ł̑ҋ@���ԁB�P�ʂ� unit
     * @param unit timeout �p�����[�^�̉��ߕ��@���w�肷�� TimeUnit
     * @return ���̃L���[�̐擪�B�w�肳�ꂽ�ҋ@���Ԃ��o�߁A���邢�̓L���[�C���O�̏I�����ʒm���ꂽ����v�f�����݂��Ȃ��ꍇ�� null
     * @throws InterruptedException �ҋ@���Ɋ��荞�݂����������ꍇ
     */
    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        queueLock.lock();
        try {
            while (!finishQueueingFlag && size() == 0) {

                // �L���[�ɗv�f������̂��̂�҂�
                nanos = notEmpty.awaitNanos(nanos);
                if (nanos <= 0) {

                    // �^�C���A�E�g
                    return null;
                }
            }
            if (finishQueueingFlag && size() == 0) {
                // �L���[�C���O�̏I�����ʒm���ꂽ��A���A�L���[����
                return null;
            }
            E elm = super.poll(timeout, unit);
            if (elm != null) {

                // �L���[�̋󂫑҂����s���Ă���X���b�h�̃u���b�N����������
                notFull.signal();
            }
            return elm;
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * ���̃L���[�̐擪���擾����э폜����B
     * @return ���̃L���[�̐擪�B�v�f�����݂��Ȃ��ꍇ�� null
     */
    @Override
    public E poll() {
        queueLock.lock();
        try {
            E elm = super.poll();
            if (elm != null) {

                // �L���[�̋󂫑҂����s���Ă���X���b�h�̃u���b�N����������
                notFull.signal();
            }
            return elm;
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * �L���[�ɗv�f���Ȃ��ꍇ�� true ��Ԃ��B
     * <p>
     * �g���d�l�F<b> �L���[����̏ꍇ�́A�L���[�ɗv�f�����邩�A�L���[�C���O�̏I�����ʒm�����܂ő҂B<br>
     * �L���[�C���O�̏I�����ʒm���ꂽ��A�L���[����̏ꍇ�� true ��Ԃ��B
     * </p>
     */
    @Override
    public boolean isEmpty() {
        queueLock.lock();
        try {
            while (!finishQueueingFlag && size() == 0) {
                try {

                    // �L���[�ɗv�f������̂��̂�҂�
                    notEmpty.await();
                } catch (InterruptedException e) {
                    return true;
                }
            }
            return super.isEmpty();
        } finally {
            queueLock.unlock();
        }
    }

}

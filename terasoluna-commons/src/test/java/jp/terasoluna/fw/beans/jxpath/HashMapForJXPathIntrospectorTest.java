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

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.jxpath.HashMapForJXPathIntrospector} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * commons-JXPath�̃o�O(JXPATH-152)���pHashMap�B<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.HashMapForJXPathIntrospector
 */
public class HashMapForJXPathIntrospectorTest extends TestCase {


    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testHashMapForJXPathIntrospector01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) HashMap<br>
     *                {
     *                  "aaa"="xxx",
     *                  "bbb"="yyy"
     *                }<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) HashMapForJXPathIntrospector<br>
     *                {
     *                  "aaa"="xxx",
     *                  "bbb"="yyy"
     *                }<br>
     *         
     * <br>
     * �R���X�g���N�^�����ɗ^����Map�̃G���g���[�����ׂĊ܂ރC���X�^���X�������邱�Ƃ̊m�F�B
     * (�X�[�p�[�N���X�ւ̈Ϗ��m�F�B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHashMapForJXPathIntrospector01() throws Exception {
        // �O����
        HashMap<String, String> srcMap = new HashMap<String, String>();
        srcMap.put("aaa", "xxx");
        srcMap.put("bbb", "yyy");

        // �e�X�g���{
        HashMapForJXPathIntrospector<String, String> map = new HashMapForJXPathIntrospector<String, String>(srcMap);

        // ����
        assertEquals(2, map.size());
        assertEquals("xxx", map.get("aaa"));
        assertEquals("yyy", map.get("bbb"));
    }

    /**
     * testGet01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) "aaa"<br>
     *         (���) {
     *                  "aaa"="xxx"
     *                }<br>
     *         (���) put��2�b������L�[�I�u�W�F�N�g��put��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) "xxx"<br>
     *         (��ԕω�) get�������ɂ͏I�����Ȃ�<br>
     *         
     * <br>
     * put����get���҂�����邱�Ƃ̊m�F�B
     * (�߂�l�́A�X�[�p�[�N���X�ւ̈Ϗ��m�F�B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet01() throws Exception {
        // �O����
        final HashMapForJXPathIntrospector<Object, String> map = new HashMapForJXPathIntrospector<Object, String>(new HashMap<Object, String>());
        map.put("aaa", "xxx");
        new Thread() {

            @Override
            public void run() {
                map.put(new WaitHashObject(2000), "wait");
            }
            
        }.start();
        Thread.sleep(500);

        // �e�X�g���{
        long start = System.currentTimeMillis();
        String ret = map.get("aaa");
        long time = System.currentTimeMillis() - start;

        // ����
        assertTrue(time > 1000); // ��1500�~���b
        assertEquals("xxx", ret);
    }

    /**
     * testGet02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) "aaa"<br>
     *         (���) {
     *                  "aaa"="xxx"
     *                  get��2�b������L�[�I�u�W�F�N�g="wait"
     *                }<br>
     *         (���) get��2�b������L�[�I�u�W�F�N�g��get��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) "xxx"<br>
     *         (��ԕω�) get�͑����ɏI������<br>
     *         
     * <br>
     * put���łȂ����get�͓����ɕ����X���b�h�Ŏg�p�\�ł��邱�Ƃ̊m�F�B
     * (�߂�l�́A�X�[�p�[�N���X�ւ̈Ϗ��m�F�B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet02() throws Exception {
        // �O����
        final HashMapForJXPathIntrospector<Object, String> map = new HashMapForJXPathIntrospector<Object, String>(new HashMap<Object, String>());
        map.put("aaa", "xxx");
        final WaitHashObject waitKey = new WaitHashObject(2000);
        map.put(waitKey, "wait");
        new Thread() {

            @Override
            public void run() {
                map.get(waitKey);
            }

        }.start();
        Thread.sleep(500);

        // �e�X�g���{
        long start = System.currentTimeMillis();
        String ret = map.get("aaa");
        long time = System.currentTimeMillis() - start;

        // ����
        assertTrue(time < 500); // �ق�0�~���b
        assertEquals("xxx", ret);
    }

    /**
     * testPut01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"aaa"<br>
     *         (����) value:"xxx"<br>
     *         (���) {
     *                  (��)
     *                }<br>
     *         (���) put��2�b������L�[�I�u�W�F�N�g��put��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �G���g���["aaa"="xxx"���ǉ������<br>
     *         (��ԕω�) put�������ɂ͏I�����Ȃ�<br>
     *         
     * <br>
     * put����put���҂�����邱�Ƃ̊m�F�B
     * (�G���g���[�ǉ��́A�X�[�p�[�N���X�ւ̈Ϗ��m�F�B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut01() throws Exception {
        // �O����
        final HashMapForJXPathIntrospector<Object, String> map = new HashMapForJXPathIntrospector<Object, String>(new HashMap<Object, String>());
        new Thread() {

            @Override
            public void run() {
                map.put(new WaitHashObject(2000), "wait");
            }
            
        }.start();
        Thread.sleep(500);

        // �e�X�g���{
        long start = System.currentTimeMillis();
        map.put("aaa", "xxx");
        long time = System.currentTimeMillis() - start;

        // ����
        assertTrue(time > 1000); // ��1500�~���b
        assertEquals("xxx", map.get("aaa"));
    }

    /**
     * testPut01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"aaa"<br>
     *         (����) value:"xxx"<br>
     *         (���) {
     *                  get��2�b������L�[�I�u�W�F�N�g="wait"
     *                }<br>
     *         (���) get��2�b������L�[�I�u�W�F�N�g��get��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �G���g���["aaa"="xxx"���ǉ������<br>
     *         (��ԕω�) put�������ɂ͏I�����Ȃ�<br>
     *         
     * <br>
     * get����put���҂�����邱�Ƃ̊m�F�B
     * (�G���g���[�ǉ��́A�X�[�p�[�N���X�ւ̈Ϗ��m�F�B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut02() throws Exception {
        // �O����
        final HashMapForJXPathIntrospector<Object, String> map = new HashMapForJXPathIntrospector<Object, String>(new HashMap<Object, String>());
        final WaitHashObject waitKey = new WaitHashObject(2000);
        map.put(waitKey, "wait");
        new Thread() {

            @Override
            public void run() {
                map.get(waitKey);
            }

        }.start();
        Thread.sleep(500);

        // �e�X�g���{
        long start = System.currentTimeMillis();
        map.put("aaa", "xxx");
        long time = System.currentTimeMillis() - start;

        // ����
        assertTrue(time > 1000); // ��1500�~���b
        assertEquals("xxx", map.get("aaa"));
    }

    /**
     * HashMap�ւ�put��get�Ɏ��Ԃ�������L�[�I�u�W�F�N�g�B
     * put��get�̍ۂɗ��p�����hashCode���\�b�h�ɁA�X���[�v�����Ă���B
     * �X���[�v���Ԃ̓R���X�g���N�^�Ŏw�肷��B
     */
    private static class WaitHashObject {
        private long sleepMillis;
        public WaitHashObject(long sleepMillis) {
            this.sleepMillis = sleepMillis;
            
        }
        @Override
        public int hashCode() {
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
            }
            return super.hashCode();
        }
        
    }
}

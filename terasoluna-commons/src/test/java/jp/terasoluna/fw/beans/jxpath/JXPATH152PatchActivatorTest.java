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

import java.security.AccessControlContext;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.DomainCombiner;
import java.security.Permissions;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jxpath.JXPathIntrospector;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.jxpath.JXPATH152PatchActivator} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * commons-JXPath�̃o�O(JXPATH-152)�p�p�b�`���A�N�e�B�x�[�g����N���X�B<br>
 * �O������F<br>
 * �E���̃e�X�g�����s����ہAJVM�̃Z�L�����e�B�}�l�[�W����L���ɂ��Ȃ�����<br>
 * �E���̃e�X�g�����s����ہA����JVM���œ����ɑ��̃e�X�g�����s���Ȃ�����<br>
 *   (�ꎞ�I�ɃZ�L�����e�B�}�l�[�W����L���ɂ��邽�߁A�L���ɂȂ��Ă���Ԃɑ��̃e�X�g�����삷��ƁA
 *    ���̃e�X�g�ŃG���[����������\��������B
 *    �Ȃ��A�����Ɏ��s�������Ȃ���΁A����JVM���ł��Ƃ��瑼�̃e�X�g�����s���Ă��悢�B)<br>
 * �E���̃e�X�g�����s����ہAJRE�̃Z�L�����e�B�|���V�[�t�@�C��<br>
 *   (�f�t�H���g�ł́AJRE�C���X�g�[���f�B���N�g��/lib/security/java.policy)�ɂāA<br>
 *   permission java.lang.reflect.ReflectPermission "suppressAccessChecks";<br>
 *   �̌�����^���Ȃ����ƁB(�C���X�g�[����̏�Ԃ̂܂܂ł���΁A���̌����͂Ȃ��B)<br>
 * �E���̃e�X�g�����s����ہA�e�X�g�ΏۂƂȂ�class�t�@�C���ƁA�e�X�g�P�[�X��class�t�@�C���́A����̃f�B���N�g���ɏo�͂��Ȃ����ƁB
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.JXPATH152PatchActivator
 */
@SuppressWarnings("unchecked")
public class JXPATH152PatchActivatorTest extends TestCase {

    private Map byClassBak = null;

    private Map byInterfaceBak = null;


    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // �N���X���[�h����Ă��Ȃ���΃N���X���[�h
        // (�N���X���[�h����static�C�j�V�����C�U���s)
        new JXPATH152PatchActivator();

        // JXPATH152PatchActivator��static�C�j�V�����C�U���s��̏�Ԃ�ێ�
        // (tearDown��static�C�j�V�����C�U���s�O�̏�Ԃɖ߂��Ă��܂�Ȃ��悤��)
        byClassBak = (Map) UTUtil.getPrivateField(JXPathIntrospector.class, "byClass");
        byInterfaceBak = (Map) UTUtil.getPrivateField(JXPathIntrospector.class, "byInterface");
        LogUTUtil.flush();
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
        UTUtil.setPrivateField(JXPathIntrospector.class, "byClass", byClassBak);
        UTUtil.setPrivateField(JXPathIntrospector.class, "byInterface", byInterfaceBak);
    }

    /**
     * testActivate01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �|<br>
     *         (���) JXPathIntrospector.byClasst:HashMap<br>
     *         (���) JXPathIntrospector.byInterface:HashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JXPathIntrospector.byClass��HashMapForJXPathIntrospector�ɂȂ��Ă���B<br>
     *         (��ԕω�) JXPathIntrospector.byInterface��HashMapForJXPathIntrospector�ɂȂ��Ă���B<br>
     *         (��ԕω�) ���O:���O���x���FINFO<br>
     *                    JXPATH-152 Patch activation succeeded.<br>
     *         
     * <br>
     * �Z�L�����e�B�}�l�[�W���ɑj�~����Ȃ���΁A
     * JXPathIntrospector��byClass��byInterface��HashMapForJXPathIntrospector�C���X�^���X�ɂȂ��Ă��邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testActivate01() throws Exception {
        // �O����
        UTUtil.setPrivateField(JXPathIntrospector.class, "byClass", new HashMap());
        UTUtil.setPrivateField(JXPathIntrospector.class, "byInterface", new HashMap());

        // �e�X�g���{
        UTUtil.invokePrivate(JXPATH152PatchActivator.class, "activate");

        // ����
        assertTrue(LogUTUtil.checkInfo("JXPATH-152 Patch activation succeeded."));
        assertTrue(UTUtil.getPrivateField(JXPathIntrospector.class, "byClass").getClass() == HashMapForJXPathIntrospector.class);
        assertTrue(UTUtil.getPrivateField(JXPathIntrospector.class, "byInterface").getClass() == HashMapForJXPathIntrospector.class);
    }

    /**
     * testActivate01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �|<br>
     *         (���) JXPathIntrospector.byClasst:HashMap<br>
     *         (���) JXPathIntrospector.byInterface:HashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JXPathIntrospector.byClass��HashMap�̂܂܁B<br>
     *         (��ԕω�) JXPathIntrospector.byInterface��HashMap�̂܂܁B<br>
     *         (��ԕω�) ���O:���O���x���FFATAL<br>
     *                    JXPATH-152 Patch activation failed.<br>
     *         
     * <br>
     * �Z�L�����e�B�}�l�[�W���ɑj�~���ꂽ�ꍇ�A
     * FATAL���O���o�͂��邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testActivate02() throws Exception {
        // �A�N�Z�X���ݒ�
        final ProtectionDomain testTargetProtectionDomain = JXPATH152PatchActivator.class.getProtectionDomain();
        DomainCombiner domainCombiner = new DomainCombiner() {
            public ProtectionDomain[] combine(
                    ProtectionDomain[] currentDomains,
                    ProtectionDomain[] assignedDomains) {
                ProtectionDomain[] ret = new ProtectionDomain[currentDomains.length];
                for (int i = 0; i < currentDomains.length ;i++) {
                    // �e�X�g�P�[�X�N���X�⃉�C�u�����ɂ���N���X���A
                    // �����ΏۃN���X(���܂܂��N���X�p�X�ɂ���N���X)�ȊO�́A�S����ɑ΂��錠����^����
                    if (currentDomains[i].getCodeSource() != testTargetProtectionDomain.getCodeSource()) {
                        Permissions permissions = new Permissions();
                        permissions.add(new AllPermission());
                        ProtectionDomain pd = new ProtectionDomain(currentDomains[i].getCodeSource(), permissions);
                        ret[i] = pd;
                    } else {
                        // �����ΏۃN���X(���܂܂��N���X�p�X�ɂ���N���X)�́A�f�t�H���g�̌����̂܂�
                        // (Field#setAccessible���֎~�����)
                        ret[i] = currentDomains[i];
                    }
                }
                return ret;
            }
        };
        AccessControlContext acc = new AccessControlContext(AccessController.getContext(), domainCombiner);
        System.setSecurityManager(new SecurityManager());

        // ��L��DomainCombiner�ŕҏW�����A�N�Z�X���ݒ�ŁA�e�X�g�����s
        AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {

            public Void run() throws Exception {
                try {
                    // �O����
                    UTUtil.setPrivateField(JXPathIntrospector.class, "byClass", new HashMap());
                    UTUtil.setPrivateField(JXPathIntrospector.class, "byInterface", new HashMap());

                    // �e�X�g���{
                    UTUtil.invokePrivate(JXPATH152PatchActivator.class, "activate");

                    // ����
                    assertTrue(LogUTUtil.checkFatal("JXPATH-152 Patch activation failed.", new AccessControlException("")));
                    assertTrue(UTUtil.getPrivateField(JXPathIntrospector.class, "byClass").getClass() == HashMap.class);
                    assertTrue(UTUtil.getPrivateField(JXPathIntrospector.class, "byInterface").getClass() == HashMap.class);
                } finally {
                    System.setSecurityManager(null);
                }
                
                return null;
            }
            
        }, acc);
    }
}

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

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;

import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * commons-JXPath�̃o�O(JXPATH-152)�p�p�b�`���A�N�e�B�x�[�g����N���X�B
 * <p>
 * �N���X���[�h�̃^�C�~���O�ŁA
 * JXPathIntrospector�́A��������Ă���private static�t�B�[���h(byClass, byInterface)����HashMap��
 * �p�b�`�I�u�W�F�N�g({@link HashMapForJXPathIntrospector})�ɍ����ւ���B<br>
 * </p>
 * <p>
 * ���̃A�N�e�B�x�[�^���g�p����ɂ́A�A�v���P�[�V�����N�����ɁA���̃N���X�����[�h����(���[�h����Ă��Ȃ��Ƃ��Ƀ��[�h����)�K�v������B<br>
 * applicationContext.xml�ɁA�ȉ��̋L�q��ǉ����邱�ƂŁA�\�ł���B
 * <fieldset>
 * <legend>applicationContext.xml</legend>
 * &lt;bean id=&quot;jxpathPatchActivator&quot; class=&quot;jp.terasoluna.fw.beans.jxpath.JXPATH152PatchActivator&quot;/&gt;
 * </fieldset>
 * </p>
 * <p>
 * ���L�����F<br>
 * ���̃N���X�́AJXPathIntrospector��private static�t�B�[���h�ɃA�N�Z�X���邽�߁A
 * �Z�L�����e�B�}�l�[�W����ݒ肵�Ă���ꍇ�ɂ́A
 * private�t�B�[���h�ւ̃A�N�Z�X��(java.lang.reflect.ReflectPermission��suppressAccessChecks)��t�^����K�v������B<br>
 * �Ȃ��Aprivate�t�B�[���h�A�N�Z�X�͓������[�h�Ŏ��s����(�Ăяo�����̃N���X�̌������Ⴂ�ꍇ�ł��A���̃N���X�ɗ^����ꂽ�����Ŏ��s����)���߁A
 * �A�N�Z�X���̕t�^�Ώۂ��i�肽���ꍇ�́A�ȉ��̗�̂悤�ɁA
 * ���̃N���X���܂�jar�t�@�C���݂̂Ɍ��肵��private�t�B�[���h�ւ̃A�N�Z�X����t�^����΂悢�B
 * <fieldset>
 * <legend>�Z�L�����e�B�|���V�[�t�@�C���̐ݒ��(Tomcat�̗�)</legend>
 * <pre>
 * grant codeBase "file:${catalina.home}/webapps/��ContextRoot��/WEB-INF/lib/�����̃N���X���܂�jar�t�@�C������" {
 *     permission java.lang.reflect.ReflectPermission "suppressAccessChecks";
 * };
 * </pre>
 * </fieldset>
 * (�A�N�Z�X���̕t�^�Ώۂ��i��K�v�������ꍇ�́A�ucodeBase "file:�`"�v�̕������ȗ�����B)<br>
 * �Z�L�����e�B�}�l�[�W�����L���������s���Ńp�b�`���A�N�e�B�x�[�g�ł��Ȃ������ꍇ��A
 * commons-JXPath-1.3�Ƃ�JXPathIntrospector�̎������قȂ�o�[�W������commons-JXPath���g�p����Ȃǂ���
 * �p�b�`���A�N�e�B�x�[�g�ł��Ȃ������ꍇ�ɂ́AFATAL���O���o�͂���B<br>
 * �������AError���������Ȃ�����A�p�b�`���A�N�e�B�x�[�g�ł��Ȃ��Ă��A��O�̓X���[���Ȃ��B<br>
 * ���������āAError����������ꍇ�������A�p�b�`���A�N�e�B�x�[�g�ł��Ȃ������ꍇ�A�p�b�`���������Ă��Ȃ���ԂŃA�v���P�[�V�������p������B
 * </p>
 * @see HashMapForJXPathIntrospector
 */
public class JXPATH152PatchActivator {

    /**
     * ���O�N���X�B
     */
    private static final Log log = LogFactory.getLog(JXPATH152PatchActivator.class);

    static {
        // �������[�h�Ŏ��s����B
        // (�Ăяo�����̃N���X�̌������Ⴂ�ꍇ�ł��A���̃N���X�ɗ^����ꂽ�����Ŏ��s����B)
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                activate();
                return null;
            }
        });
    }

    /**
     * �p�b�`���A�N�e�B�x�[�g����B
     */
    @SuppressWarnings("unchecked")
    private static void activate() {
        try {
            // ���Ƃ��Ǝg�p����Ă���Map�I�u�W�F�N�g���擾
            Field byClassField = JXPathIntrospector.class
                    .getDeclaredField("byClass");
            byClassField.setAccessible(true);
            Map byClass = (Map) byClassField.get(null);

            Field byInterfaceField = JXPathIntrospector.class
                    .getDeclaredField("byInterface");
            byInterfaceField.setAccessible(true);
            Map byInterface = (Map) byInterfaceField.get(null);

            // Map�I�u�W�F�N�g�������ւ���
            byClassField.set(null, new HashMapForJXPathIntrospector(byClass));
            byInterfaceField.set(null, new HashMapForJXPathIntrospector(
                    byInterface));

            log.info("JXPATH-152 Patch activation succeeded.");
        } catch (Exception e) {
            // �Z�L�����e�B�}�l�[�W���̐����ɂ���O��A
            // �z�肵�Ă��Ȃ��o�[�W������commons-JXPath���g�p�������Ƃɂ���O
            log.fatal("JXPATH-152 Patch activation failed.", e);
        }
    }
}

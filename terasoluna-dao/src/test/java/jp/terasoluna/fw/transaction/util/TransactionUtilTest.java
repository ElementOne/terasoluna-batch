/*
 * Copyright (c) 2007 NTT DATA Corporation
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

package jp.terasoluna.fw.transaction.util;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.SimpleTransactionStatus;

/**
 * TransactionUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 * {@link jp.terasoluna.fw.util.TransactionUtil} �N���X�̃e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ��O�𔭐��������Ƃ����[���o�b�N�����s���邳���Ɏg�p����TransactionUtil�N���X�B<br>
 * setRollbackOnly���\�b�h�g�p����isRollbackOnly�X�e�[�^�X��true�ɕύX����B
 * <p>
 * 
 * @see jp.terasoluna.fw.util.TransactionUtil
 */
public class TransactionUtilTest extends TestCase {

    /**
     * ��O�������������ׂ̈Ƀ��\�b�h��p�ӁB
     */
    protected Method exceptionalMethod;

    /**
     * �R���X�g���N�^�B
     */
    public TransactionUtilTest() {
        try {
            // �e�X�g���郁�\�b�h���L���b�V��
            exceptionalMethod = ITestBean.class.getMethod("exceptional",
                    new Class[] { Throwable.class });
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Shouldn't happen", ex);
        }
    }

    /**
     * testSetRollbackOnly() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F(�Ăяo�����\�b�h) setRollbackOnly();<br>
     * (���) isRollbackOnly:false<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) isRollbackOnly:"true"<br>
     * 
     * <br>
     * TransactionUtil�N���X��setRollbackOnly���\�b�h���Ăяo���ꂽ���AisRollbackOnly��true�ɂȂ�܂��B
     * <br>
     * 
     * @throws Throwable
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetRollbackOnly() throws Throwable {

        // �O����
        TransactionAttribute txatt = new DefaultTransactionAttribute();

        MapTransactionAttributeSource tas = new MapTransactionAttributeSource();
        tas.register(exceptionalMethod, txatt);
        SimpleTransactionStatus status = new SimpleTransactionStatus();

        // ���b�N�쐬
        PlatformTransactionManager mock = createMock(PlatformTransactionManager.class);

        // getTransaction���\�b�h�͈���txatt�A�߂�lstatus��1��Ă΂��
        expect(mock.getTransaction(txatt)).andReturn(status);
        expectLastCall().times(1);

        // commit���\�b�h�͈���status�A�߂�l��void��1��Ă΂��
        mock.commit(status);
        expectLastCall().times(1);

        // ���b�N��L����
        replay(mock);

        TestBean outer = new TestBean() {
            @Override
            public void exceptional(Throwable t) throws Throwable {

                // �e�X�g���s
                TransactionUtil.setRollbackOnly();

                SimpleTransactionStatus sts = new SimpleTransactionStatus();
                sts = (SimpleTransactionStatus) TransactionAspectSupport
                        .currentTransactionStatus();

                // ����
                assertEquals(true, sts.isRollbackOnly());

            }
        };
        ITestBean outerProxy = (ITestBean) advised(outer, mock, tas);
        outerProxy.exceptional(null);

        // ���b�N���K��ʂ�̃��\�b�h�A����щ񐔂��Ăяo����Ă���������
        verify(mock);

    }

    /**
     * �A�h�o�C�X�I�u�W�F�N�g�ƃg�����U�N�V�����̃Z�b�g�A�b�v�p�̃I�u�W�F�N�g���쐬����e���v���[�g���\�b�h�B<br>
     * TransactionInterceptor���쐬���Ă����K�p���܂��B
     * 
     * @param target
     *            TransactionUtilTest null�l
     * @param ptm
     *            Proxy0 �C���^�[�t�F�[�X�̂��߂�EasyMock
     * @param tas
     *            MapTransactionAttributeSource �n�b�V���}�b�v
     * @return pf ProxyFactory JDBC�C���^�[�t�F�[�X�̃v���L�V
     */
    Object advised(Object target, PlatformTransactionManager ptm,
            TransactionAttributeSource tas) {
        TransactionInterceptor ti = new TransactionInterceptor();
        ti.setTransactionManager(ptm);
        assertEquals(ptm, ti.getTransactionManager());
        ti.setTransactionAttributeSource(tas);
        assertEquals(tas, ti.getTransactionAttributeSource());

        ProxyFactory pf = new ProxyFactory(target);
        pf.addAdvice(0, ti);
        return pf.getProxy();
    }
}

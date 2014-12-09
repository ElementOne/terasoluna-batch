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

package jp.terasoluna.fw.batch.exception.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.terasoluna.fw.batch.constants.LogId;
import jp.terasoluna.fw.logger.TLogger;

/**
 * ��O�n���h���̃f�t�H���g����.
 */
public class DefaultExceptionHandler implements ExceptionHandler {
    /**
     * ���K�[�B
     */
    private static final TLogger LOGGER = TLogger
            .getLogger(DefaultExceptionHandler.class);

    /**
     * �f�t�H���g��O�n���h���̃��^�[���R�[�h.
     */
    protected static final int DEFAULT_EXCEPTION_HANDLER_STATUS = 255;

    /**
     * ��O�N���X�ƕԋp����X�e�[�^�X�l�̃}�b�v<br>
     * <p>
     * Bean��`�ɗ�O�̌^�ƑΉ�����X�e�[�^�X�l�Ƃ̃}�b�s���O���`���邱�ƂŁA��O���Ƃɕԋp����X�e�[�^�X�l��ς��邱�Ƃ��ł���B<br>
     * �}�b�s���O�ݒ���ȗ������ꍇ�́A���ׂĂ̗�O�ɑ΂��ăX�e�[�^�X�l255��ԋp����B<br>
     * </p>
     * <p>
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;"><br>
     * <legend>Bean��`�L�q��</legend>
     *
     * <pre>
     * &lt;property name=&quot;exceptionToStatusMap&quot;&gt;
     *   &lt;map&gt;
     *     &lt;entry key=&quot;jp.terasoluna.fw.batch.exception.BatchException&quot; value=&quot;123&quot;/&gt;
     *     &lt;entry key=&quot;java.lang.Exception&quot; value=&quot;100&quot;/&gt;
     *   &lt;/map&gt;
     * &lt;/property&gt;
     * </pre>
     *
     * </fieldset>
     * </p>
     */
    protected Map<Class<? extends Throwable>, Integer> exceptionToStatusMap = null;

    /**
     * ��O�N���X�ƕԋp����X�e�[�^�X�l�̃}�b�v��ݒ肷��B
     * @param exceptionToStatusMap Map&lt;Class&lt;? extends Throwable&gt;, Integer&gt;
     */
    public void setExceptionToStatusMap(
            Map<Class<? extends Throwable>, Integer> exceptionToStatusMap) {
        this.exceptionToStatusMap = exceptionToStatusMap;
    }

    /*
     * (non-Javadoc)
     * @see jp.terasoluna.fw.batch.exception.handler.ExceptionHandler#handleThrowableException(java.lang.Throwable)
     */
    public int handleThrowableException(Throwable e) {
        // WARN���O���o�͂���
        LOGGER.warn(LogId.WAL025007,e);

        // ��O�N���X�ƕԋp����X�e�[�^�X�l�̃}�b�v���ݒ肳��Ă����ꍇ�͂���ɏ]��
        if (this.exceptionToStatusMap != null && e != null) {
            Class<? extends Throwable> exClass = e.getClass();

            Set<Entry<Class<? extends Throwable>, Integer>> es = this.exceptionToStatusMap
                    .entrySet();

            for (Entry<Class<? extends Throwable>, Integer> ent : es) {
                if (ent != null && ent.getKey() != null
                        && ent.getValue() != null) {
                    Class<? extends Throwable> entClass = ent.getKey();

                    if (entClass.isAssignableFrom(exClass)) {
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug(LogId.DAL025017, exClass.getName(),entClass.getName(),ent.getValue());
                        }

                        return ent.getValue().intValue();
                    }
                }
            }
        }

        // �f�t�H���g�̃X�e�[�^�X�l��Ԃ��B
        return DEFAULT_EXCEPTION_HANDLER_STATUS;
    }
}

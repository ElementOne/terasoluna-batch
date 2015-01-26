/*
 * Copyright (c) 2015 NTT DATA Corporation
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

package jp.terasoluna.fw.util;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

import java.util.Date;
import java.util.Map;

/**
 * DateUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 * static�C�j�V�����C�U�̓�����e�X�g�ΏۂƂ��Ă��邽�߁A
 * ���̃��\�b�h�����s�����DateUtilTest01�Ƃ̓e�X�g�P�[�X�𕪊����Ă���B
 */
public class DateUtilTest02 extends PropertyTestCase {
    @Override
    protected void setUpData() throws Exception {
        addProperty("wareki.gengo.0.name", "����");
        addProperty("wareki.gengo.0.roman", "H");
        addProperty("wareki.gengo.0.startDate", "1989/01/08");
        addProperty("wareki.gengo.1.name", "���a");
        addProperty("wareki.gengo.1.roman", "S");
        addProperty("wareki.gengo.1.startDate", "1926/12/25");
        addProperty("wareki.gengo.2.name", "�吳");
        addProperty("wareki.gengo.2.roman", "T");
        addProperty("wareki.gengo.2.startDate", "1912/07/30");
        addProperty("wareki.gengo.3.name", "����");
        addProperty("wareki.gengo.3.roman", "M");
        addProperty("wareki.gengo.3.startDate", "1868/09/04");
        addProperty("wareki.gengo.4.name", "����");
        addProperty("wareki.gengo.4.roman", "H");
        addProperty("wareki.gengo.5.name", "����");
        addProperty("wareki.gengo.5.roman", "H");
        addProperty("wareki.gengo.5.startDate", "asdf");
    }

    /**
     * Constructor for DateUtilTest02.
     * @param arg0 constructor argument
     */
    public DateUtilTest02(String arg0) {
        super(arg0);
    }

    @Override
    protected void cleanUpData() throws Exception {
        clearProperty();
    }

    /**
     * testStatic01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) �v���p�e�B:wareki.gengo.0.name = ����<br>
     *                 �@�@wareki.gengo.0.roman = H<br>
     *                 �@�@wareki.gengo.0.startDate = 1989/01/08<br>
     *                 �@�@wareki.gengo.1.name = ���a<br>
     *                 �@�@wareki.gengo.1.roman = S<br>
     *                 �@�@wareki.gengo.1.startDate = 1926/12/25<br>
     *                 �@�@wareki.gengo.2.name = �吳<br>
     *                 �@�@wareki.gengo.2.roman = T<br>
     *                 �@�@wareki.gengo.2.startDate = 1912/07/30<br>
     *                 �@�@wareki.gengo.3.name = ����<br>
     *                 �@�@wareki.gengo.3.roman = M<br>
     *                 �@�@wareki.gengo.3.startDate = 1868/09/04<br>
     *                 �@�@wareki.gengo.4.name = ����<br>
     *                 �@�@wareki.gengo.4.roman = H<br>
     *                 �@�@wareki.gengo.5.name = ����<br>
     *                 �@�@wareki.gengo.5.roman = H<br>
     *                 �@�@wareki.gengo.5.startDate = asdf<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) �v���C�x�[�g�t�B�[���h:�v���C�x�[�g�t�B�[���h�ł���uGENGO_NAME�v�uGENGO_ROMAN�v�uGENGO_BEGIN_DATES�v�uGENGO_BEGIN_YEARS�v�̃T�C�Y���S�ł��邱�ƁB<br>
     *         (��ԕω�) ���O:<error���x��><br>
     *                    ���b�Z�[�W�Fwareki.gengo.4.startDate not found<br>
     *                    <error���x��><br>
     *                    ���b�Z�[�W�FUnparseable date: "asdf"<br>
     *
     * <br>
     * ���ׂẴp�^�[����ԗ�����e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testStatic01() throws Exception {

        // ���ʊm�F
        //�v���C�x�[�g�t�B�[���h�̌�����4���ł��邱�Ƃ��m�F����B
        Map GENGO_NAME
                = (Map) UTUtil.getPrivateField(DateUtil.class, "GENGO_NAME");
        Map GENGO_ROMAN
                = (Map) UTUtil.getPrivateField(DateUtil.class, "GENGO_ROMAN");
        Date[] GENGO_BEGIN_DATES
                = (Date[]) UTUtil.getPrivateField(DateUtil.class, "GENGO_BEGIN_DATES");
        int[] GENGO_BEGIN_YEARS
                = (int[]) UTUtil.getPrivateField(DateUtil.class, "GENGO_BEGIN_YEARS");
        assertEquals(4, GENGO_NAME.size());
        assertEquals(4, GENGO_ROMAN.size());
        assertEquals(4, GENGO_BEGIN_DATES.length);
        assertEquals(4, GENGO_BEGIN_YEARS.length);
        assertTrue(LogUTUtil.checkError("wareki.gengo.4.startDate not found"));
        assertTrue(LogUTUtil.checkError("Unparseable date: \"asdf\""));
    }
}

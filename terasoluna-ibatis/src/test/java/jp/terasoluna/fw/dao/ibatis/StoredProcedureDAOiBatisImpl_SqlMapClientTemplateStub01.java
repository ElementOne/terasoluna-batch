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

package jp.terasoluna.fw.dao.ibatis;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * {@link StoredProcedureDAOiBatisImpl}の試験のために使用されるスタブ。
 * 
 * {@link StoredProcedureDAOiBatisImpl}からの呼び出し確認用に使用される。
 * 
 */
public class StoredProcedureDAOiBatisImpl_SqlMapClientTemplateStub01 extends SqlMapClientTemplate {

    /**
     * コンストラクタ
     */
    public StoredProcedureDAOiBatisImpl_SqlMapClientTemplateStub01() {

        //SqlMapClientの設定
        SqlMapClient sqlMapClient = new StoredProcedureDAOiBatisImpl_SqlMapClientStub01();
        setSqlMapClient(sqlMapClient);

        //データソースの設定
        DataSource dataSource = new StoredProcedureDAOiBatisImpl_DataSourceStub01();
        setDataSource(dataSource);
    }

    /**
     * QueryDAOiBatisImplテスト用queryForObjectメソッド
     */
    @Override
    public Object queryForObject(String statementName, Object parameterObject) throws DataAccessException {
        called = true;
        this.statementName = statementName;
        this.parameterObject = parameterObject;
        return "abc";
    }

    /*
     * 呼び出し確認用変数
     */
    private boolean called = false;
    private String statementName = null;
    private Object parameterObject = null;

    public boolean isCalled() {
        return called;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public String getStatementName() {
        return statementName;
    }

}

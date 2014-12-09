package jp.terasoluna.fw.batch.util;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import jp.terasoluna.fw.dao.QueryDAO;

public class QueryDAOStub01 implements QueryDAO {

    public <E> E executeForObject(String sqlID, Object bindParams, Class clazz) {
        throw new DataAccessException("DB�X�e�[�^�X�擾����O�m�F�p") {
            private static final long serialVersionUID = -5930697938939839029L;
        };
    }

    public Map<String, Object> executeForMap(String sqlID, Object bindParams) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public <E> E[] executeForObjectArray(String sqlID, Object bindParams,
            Class clazz) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public <E> E[] executeForObjectArray(String sqlID, Object bindParams,
            Class clazz, int beginIndex, int maxCount) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams, int beginIndex, int maxCount) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public <E> List<E> executeForObjectList(String sqlID, Object bindParams) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public <E> List<E> executeForObjectList(String sqlID, Object bindParams,
            int beginIndex, int maxCount) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams, int beginIndex, int maxCount) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }
}

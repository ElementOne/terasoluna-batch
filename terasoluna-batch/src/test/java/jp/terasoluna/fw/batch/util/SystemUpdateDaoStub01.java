package jp.terasoluna.fw.batch.util;

import jp.terasoluna.fw.batch.executor.dao.SystemUpdateDao;
import jp.terasoluna.fw.batch.executor.vo.BatchJobManagementUpdateParam;
import org.springframework.dao.DataAccessException;

public class SystemUpdateDaoStub01 implements SystemUpdateDao {
    public int updateJobTable(BatchJobManagementUpdateParam batchJobManagementUpdateParam) {
        throw new DataAccessException("DB�X�e�[�^�X�X�V����O�m�F�p") {
            private static final long serialVersionUID = 2702384362766047436L;
        };
    }
}

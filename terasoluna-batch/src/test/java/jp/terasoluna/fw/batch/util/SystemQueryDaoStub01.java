package jp.terasoluna.fw.batch.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import jp.terasoluna.fw.batch.executor.dao.SystemQueryDao;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListParam;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobManagementParam;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

public class SystemQueryDaoStub01 implements SystemQueryDao {

    public List<BatchJobListResult> selectJobList(BatchJobListParam batchJobListParam) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public List<BatchJobListResult> selectJobList(RowBounds rowBaounds, BatchJobListParam batchJobListParam) {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }

    public BatchJobData selectJob(BatchJobManagementParam batchJobManagementParam) {
        throw new DataAccessException("DB�X�e�[�^�X�擾����O�m�F�p") {};
    }

    public Timestamp currentTimeReader() {
        throw new DataAccessException("DB�X�e�[�^�X�擾����O�m�F�p") {};
    }

    public Date currentDateReader() {
        throw new IllegalArgumentException("�e�X�g�g�p�ΏۊO��O");
    }
}

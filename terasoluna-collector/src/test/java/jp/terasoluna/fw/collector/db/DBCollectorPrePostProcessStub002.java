package jp.terasoluna.fw.collector.db;

import java.util.concurrent.atomic.AtomicInteger;

public class DBCollectorPrePostProcessStub002 implements DBCollectorPrePostProcess {

    protected AtomicInteger preprocessExecCount = new AtomicInteger(0);
    
    protected boolean retryFlag = false;

    public <P> void preprocess(DBCollector<P> collector) {
    	// preprocess���\�b�h���Ă΂�邽�тɎ��s�񐔂��C���N�������g
    	preprocessExecCount.getAndIncrement();
    	if(preprocessExecCount.get() >= 2) {
    		// 2��ȏケ�̏��������s����Ă���ꍇ�̓��g���C�t���O��true�ɂ���
    		retryFlag = true;
    	}
    }

    public <P> void postprocessComplete(DBCollector<P> collector) {
    	/* NOP */
    }

    public <P> DBCollectorPrePostProcessStatus postprocessException(
            DBCollector<P> collector, Throwable throwable) {
        if (preprocessExecCount.get() == 1) {
        	// 1��ڂ�RETRY��Ԃ��ă��g���C������
        	return DBCollectorPrePostProcessStatus.RETRY;
        } else {
        	// 2��ڈȍ~��END��Ԃ��ďI��������
        	return DBCollectorPrePostProcessStatus.END;
        }
    }

    public boolean getRetryFlag() {
    	return retryFlag;
    }
}

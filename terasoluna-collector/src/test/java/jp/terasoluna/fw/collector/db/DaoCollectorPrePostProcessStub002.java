package jp.terasoluna.fw.collector.db;

import java.util.concurrent.atomic.AtomicInteger;

public class DaoCollectorPrePostProcessStub002 implements DaoCollectorPrePostProcess {

    protected AtomicInteger preprocessExecCount = new AtomicInteger(0);
    
    protected boolean retryFlag = false;

    public <P> void preprocess(DaoCollector<P> collector) {
    	// preprocess���\�b�h���Ă΂�邽�тɎ��s�񐔂��C���N�������g
    	preprocessExecCount.getAndIncrement();
    	if(preprocessExecCount.get() >= 2) {
    		// 2��ȏケ�̏��������s����Ă���ꍇ�̓��g���C�t���O��true�ɂ���
    		retryFlag = true;
    	}
    }

    public <P> void postprocessComplete(DaoCollector<P> collector) {
    	/* NOP */
    }

    public <P> DaoCollectorPrePostProcessStatus postprocessException(
            DaoCollector<P> collector, Throwable throwable) {
        if (preprocessExecCount.get() == 1) {
        	// 1��ڂ�RETRY��Ԃ��ă��g���C������
        	return DaoCollectorPrePostProcessStatus.RETRY;
        } else {
        	// 2��ڈȍ~��END��Ԃ��ďI��������
        	return DaoCollectorPrePostProcessStatus.END;
        }
    }

    public boolean getRetryFlag() {
    	return retryFlag;
    }
}

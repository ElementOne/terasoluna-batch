package jp.terasoluna.fw.collector.db;


public class DBCollectorPrePostProcessStub003 implements DBCollectorPrePostProcess {

    public <P> void preprocess(DBCollector<P> collector) {
    	collector.rowHandler = new QueueingDataRowHandlerImpl();
    }

    public <P> void postprocessComplete(DBCollector<P> collector) {
    	collector.rowHandler = new QueueingDataRowHandlerImpl();
    }

    public <P> DBCollectorPrePostProcessStatus postprocessException(
            DBCollector<P> collector, Throwable throwable) {
    	if("postprocessException�e�X�g".equals(throwable.getMessage())) {
    		return DBCollectorPrePostProcessStatus.THROW;
    	} else {
    		return DBCollectorPrePostProcessStatus.END;
    	}
    }

}

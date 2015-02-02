package jp.terasoluna.fw.collector;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jp.terasoluna.fw.collector.db.ValidatorStub;
import jp.terasoluna.fw.collector.validate.ValidateErrorStatus;
import jp.terasoluna.fw.collector.validate.ValidationErrorException;
import jp.terasoluna.fw.collector.vo.DataValueObject;

public class AbstractCollectorStub17<P> extends AbstractCollector<P> {

    public AbstractCollectorStub17() {

        this.validator = new ValidatorStub();

    }

    @Override
    protected ValidateErrorStatus validate(DataValueObject dataValueObject) {
        throw new ValidationErrorException();
    }

    @Override
    protected BlockingQueue<DataValueObject> createQueue() {
        if (this.currentQueue == null) {
            // currentキュー生成
            this.currentQueue = createCurrentQueue();
        }
        if (this.previousQueue == null) {
            // previousキュー生成
            this.previousQueue = createPreviousQueue();
        }
        return new LinkedBlockingQueue<DataValueObject>();
    }

    public Integer call() throws Exception {
        Thread.sleep(1000);
        return null;
    }

    @Override
    protected boolean isFinish() {
        return false;
    }

}

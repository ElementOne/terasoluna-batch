package jp.terasoluna.fw.collector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import jp.terasoluna.fw.collector.vo.DataValueObject;

public class AbstractCollectorStubSetFinish2<P> extends AbstractCollector<P> {
    public AbstractCollectorStubSetFinish2() {
    	super();
    }

    public Integer call() throws Exception {
        return null;
    }

    /**
     * ArrayBlockingQueue�^�̃L���[���쐬����
     * @return
     */
    protected BlockingQueue<DataValueObject> createQueue() {
        if (this.currentQueue == null) {
            // current�L���[����
            this.currentQueue = createCurrentQueue();
        }
        if (this.previousQueue == null) {
            // previous�L���[����
            this.previousQueue = createPreviousQueue();
        }
        return new ArrayBlockingQueue<DataValueObject>(this.queueSize);
    }

}

package jp.terasoluna.fw.collector.db;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import jp.terasoluna.fw.collector.vo.DataValueObject;

public class DBCollectorStub004 extends DBCollector<HogeBean> {

    protected BlockingQueue<DataValueObject> inQueue = null;

    /**
     * �R���X�g���N�^�B
     *
     * @param queueCount �u���b�N���ꂸ�ɃL���[�ɋl�߂��鐔
     */
    public DBCollectorStub004(int queueCount) {
        this.inQueue = new ArrayBlockingQueue<DataValueObject>(queueCount);
    }

    @Override
    protected void addQueue(DataValueObject dataValueObject)
            throws InterruptedException {
        // �L���[�̗e�ʂ𒴂��Ă���ꍇ�A�����Ńu���b�N����B
        inQueue.put(dataValueObject);
    }

    @Override
    public void setFinish(boolean finish) {
        super.setFinish(finish);
    }

    @Override
    public BlockingQueue<DataValueObject> getQueue() {
        return inQueue;
    }
}

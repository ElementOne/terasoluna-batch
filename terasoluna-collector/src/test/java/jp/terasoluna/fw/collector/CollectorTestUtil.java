package jp.terasoluna.fw.collector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CollectorTestUtil {

    /**
     * Log.
     */
    private static Log logger = LogFactory.getLog(CollectorTestUtil.class);

    /**
     * �S�ẴR���N�^�X���b�h�Ɋ��荞�݂�������
     */
    public static void allInterrupt() {
        int activeCount = Thread.activeCount();
        Thread[] ta = new Thread[activeCount + 10];
        int threadCount = Thread.enumerate(ta);
        for (int i = 0; i < threadCount; i++) {
            if (ta[i] == null) {
                continue;
            }
            String threadName = ta[i].getName();

            if (threadName != null
                    && threadName
                            .startsWith(CollectorThreadFactory.COLLECTOR_THREAD_NAME_PREFIX)) {
                ta[i].interrupt();
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }

    /**
     * �R���N�^�X���b�h�����w��ȉ��ł��邩�`�F�b�N����
     * @param count
     * @return
     */
    public static boolean lessThanCollectorThreadCount(int count) {
        int retry = 0;

        while (count < getCollectorThreadCount()) {
            if (retry++ > 100) {
                if (logger.isWarnEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Thread count error.");
                    sb.append(" expected:[");
                    sb.append(count);
                    sb.append("]");
                    sb.append(" actual:[");
                    sb.append(getCollectorThreadCount());
                    sb.append("]");
                    logger.warn(sb.toString());
                }
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

    /**
     * �R���N�^�X���b�h�̐���Ԃ�
     * @return �R���N�^�X���b�h�̐�
     */
    public static int getCollectorThreadCount() {
        int collectorThreadCount = 0;
        int activeCount = Thread.activeCount();
        Thread[] ta = new Thread[activeCount + 10];
        int threadCount = Thread.enumerate(ta);
        for (int i = 0; i < threadCount; i++) {
            if (ta[i] == null) {
                continue;
            }
            String threadName = ta[i].getName();

            if (threadName != null
                    && threadName
                            .startsWith(CollectorThreadFactory.COLLECTOR_THREAD_NAME_PREFIX)) {
                collectorThreadCount++;
            }
        }
        return collectorThreadCount;
    }
}

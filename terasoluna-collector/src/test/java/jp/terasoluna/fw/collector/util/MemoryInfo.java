package jp.terasoluna.fw.collector.util;

import java.text.DecimalFormat;

public class MemoryInfo {
    /**
     * Java ���z�}�V���̃��������e�ʁA�g�p�ʁA �g�p�����݂�ő僁�����e�ʂ̏���Ԃ��܂��B
     * @return Java ���z�}�V���̃��������
     */
    public static String getMemoryInfo() {
        DecimalFormat f1 = new DecimalFormat("#,###KB");
        DecimalFormat f2 = new DecimalFormat("##.#");

        Runtime rt = Runtime.getRuntime();
        long free = rt.freeMemory() / 1024;
        long total = rt.totalMemory() / 1024;
        long max = rt.maxMemory() / 1024;
        long used = total - free;
        double ratio = (used * 100 / (double) total);

        StringBuilder sb = new StringBuilder();

        sb.append("Java memory info : ");
        sb.append("used=");
        sb.append(f1.format(used));
        sb.append(" (");
        sb.append(f2.format(ratio));
        sb.append("%), ");
        sb.append("total=");
        sb.append(f1.format(total));
        sb.append(", ");
        sb.append("max=");
        sb.append(f1.format(max));

        return sb.toString();
    }
}

package jp.terasoluna.fw.collector.util;

/**
 * �R���g���[���u���C�N�ƃR���N�^�̌��������p��JavaBean
 *
 */
public class ControlBreakCheckerTestBean {

    public ControlBreakCheckerTestBean() {
    }

    public ControlBreakCheckerTestBean(String column1, String column2, String column3) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
    }

    /**
     * �R���g���[���u���C�N�L�[
     */
    private String column1 = null;

    /**
     * �A�ԁB
     */
    private String column2 = null;

    /**
     * ���̓G���[�ΏہB
     * "Exception"�̎����̓G���[�ɂȂ�A"validateError"�̂Ƃ����̓`�F�b�N�G���[�ɂȂ�
     */
    private String column3 = null;

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

}

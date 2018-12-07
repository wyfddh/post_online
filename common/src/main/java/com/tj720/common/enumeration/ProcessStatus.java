package com.tj720.common.enumeration;

/**
 * @Author: 程荣凯
 * @Date: 2018/9/30 16:51
 */
public enum ProcessStatus {

    DRAFT("0","草稿"),
    SUBMITTING("1","提交中"),
    PENDING_APPROVAL("2","待审批"),
    PASSED("3","已通过"),
    REJECTED("4","待审批"),
    ENDED("5","已完结"),
    PENDING_PUBLISH("6","待发布"),
    PUBLISHED("7","已发布");
    private String value;
    private String desc;

    ProcessStatus(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }

}

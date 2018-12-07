package com.tj720.model.common.video;

public class PostLs {
    private String xid;

    private Integer lsCode;

    private String lsKey;

    private String lsType;

    private String ext1;

    private String ext2;

    private String ext3;

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid == null ? null : xid.trim();
    }

    public int getLsCode() {
        return lsCode;
    }

    public void setLsCode(Integer lsCode) {
        this.lsCode = lsCode;
    }

    public String getLsKey() {
        return lsKey;
    }

    public void setLsKey(String lsKey) {
        this.lsKey = lsKey == null ? null : lsKey.trim();
    }

    public String getLsType() {
        return lsType;
    }

    public void setLsType(String lsType) {
        this.lsType = lsType == null ? null : lsType.trim();
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }
}
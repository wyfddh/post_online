package com.tj720.model.common.copyright;

public class CopyrightSetting {
    private String xid;

    private String watermarkStatus;

    private String watermarkPath;

    private String copyrightStatus;

    private String copyrightContent;

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid == null ? null : xid.trim();
    }

    public String getWatermarkStatus() {
        return watermarkStatus;
    }

    public void setWatermarkStatus(String watermarkStatus) {
        this.watermarkStatus = watermarkStatus == null ? null : watermarkStatus.trim();
    }

    public String getWatermarkPath() {
        return watermarkPath;
    }

    public void setWatermarkPath(String watermarkPath) {
        this.watermarkPath = watermarkPath == null ? null : watermarkPath.trim();
    }

    public String getCopyrightStatus() {
        return copyrightStatus;
    }

    public void setCopyrightStatus(String copyrightStatus) {
        this.copyrightStatus = copyrightStatus == null ? null : copyrightStatus.trim();
    }

    public String getCopyrightContent() {
        return copyrightContent;
    }

    public void setCopyrightContent(String copyrightContent) {
        this.copyrightContent = copyrightContent == null ? null : copyrightContent.trim();
    }
}
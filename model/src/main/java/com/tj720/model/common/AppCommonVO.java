package com.tj720.model.common;


import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 邮政app端展示通用实体类
 */
public class AppCommonVO implements Serializable{

    private static final long serialVersionUID = -8388386383107181627L;


    Date appCreateTime;

    String  appCreateTimeStr;   //(年月日时)

    String appUpdatTime;

	String remarks;	// 备注


    public Date getAppCreateTime() {
        return appCreateTime;
    }

    public void setAppCreateTime(Date appCreateTime) {
        this.appCreateTime = appCreateTime;
    }

    public String getAppCreateTimeStr() {
        return appCreateTimeStr;
    }

    public void setAppCreateTimeStr(String appCreateTimeStr) {
        this.appCreateTimeStr = appCreateTimeStr;
    }

    public String getAppUpdatTime() {
        return appUpdatTime;
    }

    public void setAppUpdatTime(String appUpdatTime) {
        this.appUpdatTime = appUpdatTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

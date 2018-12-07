package com.tj720.model.common;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable{


    private static final long serialVersionUID = 1618015696962058719L;

    private String attId;       //附件id

    private String attName;     //附件名称

    private Long attSize;       //附件大小

    private String attPath;     //附件路径

    private String attIsjunk;    //删除标志（null、0：正常，1：删除）

    private Date attDate;        //文件上传时间

    private String attType;      //类型（存与其相关联的表的名字）

    private String attrUser;     //上传人id

    private String attFkId;      //关联id

    private Integer attFileType;    //文件类型

    private String attSource;       //附件源

    private String isMain;          //是否主要/主图


    public String getAttId() {
        return attId;
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public Long getAttSize() {
        return attSize;
    }

    public void setAttSize(Long attSize) {
        this.attSize = attSize;
    }

    public String getAttPath() {
        return attPath;
    }

    public void setAttPath(String attPath) {
        this.attPath = attPath;
    }

    public String getAttIsjunk() {
        return attIsjunk;
    }

    public void setAttIsjunk(String attIsjunk) {
        this.attIsjunk = attIsjunk;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType;
    }

    public String getAttrUser() {
        return attrUser;
    }

    public void setAttrUser(String attrUser) {
        this.attrUser = attrUser;
    }

    public String getAttFkId() {
        return attFkId;
    }

    public void setAttFkId(String attFkId) {
        this.attFkId = attFkId;
    }

    public Integer getAttFileType() {
        return attFileType;
    }

    public void setAttFileType(Integer attFileType) {
        this.attFileType = attFileType;
    }

    public String getAttSource() {
        return attSource;
    }

    public void setAttSource(String attSource) {
        this.attSource = attSource;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }
}
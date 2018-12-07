package com.tj720.model.common.education;

import com.tj720.model.common.Attachment;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Education  implements Serializable{


    private static final long serialVersionUID = 2748302992476484417L;


    private String  rowNum;   //当前数据行号

    private String id;

    private String educationId;

    private String title;

    private String picId;

    private String msg;

    private String msgPicId;

    private String isdelete;

    private String sort;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String updater;

    private String other1;

    private String other2;

    private String other3;


    //主图url
    private String mainPicUrl;
    //图片集合
    private List<Attachment> picList;

    @Transient
    private String createTimeYear;     //创建时间转换(年)

    @Transient
    private String createTimeMonthDay;     //创建时间转换(月日)

    @Transient
    private String createTimeStr;     //创建时间转换(年月日时分)

    @Transient
    private String updateTimeStr;     //修改时间转换(年月日时分)


    public String getCreateTimeYear() {
        return createTimeYear;
    }

    public void setCreateTimeYear(String createTimeYear) {
        this.createTimeYear = createTimeYear;
    }

    public String getCreateTimeMonthDay() {
        return createTimeMonthDay;
    }

    public void setCreateTimeMonthDay(String createTimeMonthDay) {
        this.createTimeMonthDay = createTimeMonthDay;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }




    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId == null ? null : educationId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId == null ? null : picId.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getMsgPicId() {
        return msgPicId;
    }

    public void setMsgPicId(String msgPicId) {
        this.msgPicId = msgPicId == null ? null : msgPicId.trim();
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete == null ? null : isdelete.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1 == null ? null : other1.trim();
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2 == null ? null : other2.trim();
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3 == null ? null : other3.trim();
    }


    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    public List<Attachment> getPicList() {
        return picList;
    }

    public void setPicList(List<Attachment> picList) {
        this.picList = picList;
    }
}
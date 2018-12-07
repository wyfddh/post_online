package com.tj720.model.common.volunteer;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 杜昶
 * @Date: 2018/11/8 10:55
 */
public class PostVolunteerActivities implements Serializable {
    private static final long serialVersionUID = -5970167499532523866L;

    /** 主键id */
    private String id;

    /** 活动名称 */
    private String activitiesName;

    /** 活动地点 */
    private String activitiesPlace;

    /** 活动开始时间 */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private String startTime;

    /** 活动结束时间 */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private String endTime;

    /** 结束报名时间 */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private String endSignTime;

    /** 需求人数 */
    private Integer needNumber;

    /** 报名需求 */
    private String signRequire;

    /** 活动封面图片id */
    private String coverId;

    /** 备注 */
    private String remark;

    private Integer isdelete;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivitiesName() {
        return activitiesName;
    }

    public void setActivitiesName(String activitiesName) {
        this.activitiesName = activitiesName;
    }

    public String getActivitiesPlace() {
        return activitiesPlace;
    }

    public void setActivitiesPlace(String activitiesPlace) {
        this.activitiesPlace = activitiesPlace;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndSignTime() {
        return endSignTime;
    }

    public void setEndSignTime(String endSignTime) {
        this.endSignTime = endSignTime;
    }

    public Integer getNeedNumber() {
        return needNumber;
    }

    public void setNeedNumber(Integer needNumber) {
        this.needNumber = needNumber;
    }

    public String getSignRequire() {
        return signRequire;
    }

    public void setSignRequire(String signRequire) {
        this.signRequire = signRequire;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

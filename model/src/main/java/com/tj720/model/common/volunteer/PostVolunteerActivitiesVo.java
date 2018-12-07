package com.tj720.model.common.volunteer;

/**
 * @author 杜昶
 * @Date: 2018/11/8 11:56
 */
public class PostVolunteerActivitiesVo extends PostVolunteerActivities {

    /** 封面链接 */
    private String coverUrl;

    private String startTimeStr;

    private String endTimeStr;

    private String endSignTimeStr;

    private String updateStr;

    private Integer status;

    private Integer  applyCount;   //申请人数

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getEndSignTimeStr() {
        return endSignTimeStr;
    }

    public void setEndSignTimeStr(String endSignTimeStr) {
        this.endSignTimeStr = endSignTimeStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateStr() {
        return updateStr;
    }

    public void setUpdateStr(String updateStr) {
        this.updateStr = updateStr;
    }
}

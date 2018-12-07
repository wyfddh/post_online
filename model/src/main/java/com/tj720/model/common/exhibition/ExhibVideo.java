package com.tj720.model.common.exhibition;

import java.util.Date;

public class ExhibVideo {
    private String id;

    private String videoId;

    private String exhibId;

    private String videoType;

    private String videoName;

    private String videoInfo;

    private String videoAssort;

    private String associatSub;

    private String associatCollect;

    private String origin;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String updater;

    private Integer sequence;

    private Integer status;

    private Integer isdelete;

    private String other1;

    private String other2;

    private String other3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    public String getExhibId() {
        return exhibId;
    }

    public void setExhibId(String exhibId) {
        this.exhibId = exhibId == null ? null : exhibId.trim();
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType == null ? null : videoType.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo == null ? null : videoInfo.trim();
    }

    public String getVideoAssort() {
        return videoAssort;
    }

    public void setVideoAssort(String videoAssort) {
        this.videoAssort = videoAssort == null ? null : videoAssort.trim();
    }

    public String getAssociatSub() {
        return associatSub;
    }

    public void setAssociatSub(String associatSub) {
        this.associatSub = associatSub == null ? null : associatSub.trim();
    }

    public String getAssociatCollect() {
        return associatCollect;
    }

    public void setAssociatCollect(String associatCollect) {
        this.associatCollect = associatCollect == null ? null : associatCollect.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
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
}
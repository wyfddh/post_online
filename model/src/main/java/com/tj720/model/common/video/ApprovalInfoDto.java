package com.tj720.model.common.video;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/22 16:11
 */
public class ApprovalInfoDto {
    /**
     * 资料类型
     */
    private String videoType;
    /**
     * 关联主体
     */
    private String relativeObject;
    /**
     * 关联藏品
     */
    private String relativeCollection;
    /**
     * 审批人
     */
    private String approval;
    /**
     * 操作类型
     */
    private String type;

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getRelativeObject() {
        return relativeObject;
    }

    public void setRelativeObject(String relativeObject) {
        this.relativeObject = relativeObject;
    }

    public String getRelativeCollection() {
        return relativeCollection;
    }

    public void setRelativeCollection(String relativeCollection) {
        this.relativeCollection = relativeCollection;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

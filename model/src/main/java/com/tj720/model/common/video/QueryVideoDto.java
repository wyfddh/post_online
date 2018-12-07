package com.tj720.model.common.video;

import java.util.Date;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/30 12:06
 */
public class QueryVideoDto {
    /**
     * 流程实例id
     */
    private String id;
    /**
     * 编号
     */
    private String videoCode;
    /**
     * 资料名称
     */
    private String videoName;
    /**
     * 申请人
     */
    private String apply;
    /**
     * 所属部门
     */
    private String applyOrg;
    /**
     * 申请原因
     */
    private String applyReason;
    /**
     * 申请日期
     */
    private Date applyTime;
    /**
     * 备注
     */
    private String remarks;
    /**
     *
     */
    private String partyId;

    private String applyStatus;

    private String approval;

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getApplyOrg() {
        return applyOrg;
    }

    public void setApplyOrg(String applyOrg) {
        this.applyOrg = applyOrg;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }
}



package com.tj720.model.common.video;

import java.util.Date;

/**
 * 影视查询申请dto
 * @Author: 程荣凯
 * @Date: 2018/10/30 10:07
 */
public class QueryApplyDto {
    /**
     * 影视资料ID
     */
    private String postVideoId;

    private String processInstId;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    private String[] ids;

    /**
     * 申请人
     */
    private String apply;
    /**
     * 申请人部门
     */
    private String applyOrg;
    /**
     * 申请时间
     */
    private Date applyTime;
    /**
     * 申请原因
     */
    private String applyReason;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 审批人
     */
    private String approval;

    private String actionType;

    private String currentUserId;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getPostVideoId() {
        return postVideoId;
    }

    public void setPostVideoId(String postVideoId) {
        this.postVideoId = postVideoId;
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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
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

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }
}

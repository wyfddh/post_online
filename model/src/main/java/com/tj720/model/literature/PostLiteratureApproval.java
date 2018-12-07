package com.tj720.model.literature;

import java.io.Serializable;
import java.util.Date;

/**
 * post_literature_approval
 * @author 
 */
public class PostLiteratureApproval implements Serializable {
    private String id;

    /**
     * 审批人
     */
    private String approveId;

    /**
     * 操作（1通过2驳回3通过并提交审批）
     */
    private String operation;

    /**
     * 指定审批人
     */
    private String appointApproveId;

    /**
     * 备注
     */
    private String approveRemark;

    /**
     * 操作时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAppointApproveId() {
        return appointApproveId;
    }

    public void setAppointApproveId(String appointApproveId) {
        this.appointApproveId = appointApproveId;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
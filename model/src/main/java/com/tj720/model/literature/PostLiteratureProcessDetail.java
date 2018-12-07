package com.tj720.model.literature;

import java.io.Serializable;
import java.util.Date;

/**
 * post_literature_process_detail
 * @author 
 */
public class PostLiteratureProcessDetail implements Serializable {
    private String id;

    /**
     * 审核关联id
     */
    private String processId;

    /**
     * 流程当前状态（1待办2已办3已完成）
     */
    private String processStatus;

    /**
     * 操作（-1发起审批0未操作1通过审批2驳回审批3通过并提交审批）
     */
    private String processOperation;

    private String processOperationName;

    /**
     * 处于当前流程的用户
     */
    private String processUserId;

    /**
     * 用户名
     */
    private String processUserName;

    /**
     * 部门名
     */
    private String processOrgName;

    /**
     * 备注
     */
    private String processRemark;

    /**
     * 创建时间
     */
    private Date createTime;

    private String createTimeStr;

    private static final long serialVersionUID = 1L;

    public String getProcessOperationName() {
        return processOperationName;
    }

    public void setProcessOperationName(String processOperationName) {
        this.processOperationName = processOperationName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessOperation() {
        return processOperation;
    }

    public void setProcessOperation(String processOperation) {
        this.processOperation = processOperation;
    }

    public String getProcessUserId() {
        return processUserId;
    }

    public void setProcessUserId(String processUserId) {
        this.processUserId = processUserId;
    }

    public String getProcessUserName() {
        return processUserName;
    }

    public void setProcessUserName(String processUserName) {
        this.processUserName = processUserName;
    }

    public String getProcessOrgName() {
        return processOrgName;
    }

    public void setProcessOrgName(String processOrgName) {
        this.processOrgName = processOrgName;
    }

    public String getProcessRemark() {
        return processRemark;
    }

    public void setProcessRemark(String processRemark) {
        this.processRemark = processRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
package com.tj720.model.literature;

import java.io.Serializable;
import java.util.Date;

/**
 * post_literature_process
 * @author 
 */
public class PostLiteratureProcess implements Serializable {
    private String id;

    /**
     * 文献关联id
     */
    private String literatureId;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 部门
     */
    private String department;

    /**
     * 申请理由
     */
    private String applyReasons;

    /**
     * 申请备注
     */
    private String applyRemark;

    /**
     * 申请日期
     */
    private String applyDate;

    /**
     * 计划归还日期
     */
    private String planReturnDate;

    /**
     * 借阅时间
     */
    private Date borrowingDate;

    private String borrowingDateStr;

    /**
     * 实际归还日期
     */
    private Date realReturnDate;

    private String realReturnDateStr;

    /**
     * 审批人
     */
    private String approveId;

    /**
     * 附件
     */
    private String attachmentId;

    /**
     * 信息来源（1pc端2纸质申请）
     */
    private String informationSources;

    /**
     * 申请方式（1借阅2下载）
     */
    private String applyType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 最后修改人
     */
    private String updater;

    /**
     * 申请状态（1待审批2已通过3已驳回）
     */
    private String approveStatus;

    /**
     * 借阅状态（-1无0申请中1申请通过2已借阅3已归还4已超期）
     */
    private String applyStatus;
    //借阅状态名
    private String applyStatusName;

    //文献名
    private String literatureName;
    //申请人名
    private String applicantName;
    //部门名
    private String departmentName;
    //文献类型id
    private String literatureType;
    //文献类型名
    private String literatureTypeName;
    //申请状态名
    private String approveStatusName;
    //申请方式名
    private String applyTypeName;
    //审批人名
    private String approveName;
    //审批操作
    private String approveOperation;
    //审批备注
    private String approveRemark;
    //前一个审批人
    private String preApproveId;
    //分类索书号
    private String callNo;
    //信息来源名
    private String informationSourcesName;
    //库存数量
    private String inventoryState;

    private static final long serialVersionUID = 1L;

    public String getInventoryState() {
        return inventoryState;
    }

    public void setInventoryState(String inventoryState) {
        this.inventoryState = inventoryState;
    }

    public String getInformationSourcesName() {
        return informationSourcesName;
    }

    public void setInformationSourcesName(String informationSourcesName) {
        this.informationSourcesName = informationSourcesName;
    }

    public String getApplyStatusName() {
        return applyStatusName;
    }

    public void setApplyStatusName(String applyStatusName) {
        this.applyStatusName = applyStatusName;
    }

    public String getBorrowingDateStr() {
        return borrowingDateStr;
    }

    public void setBorrowingDateStr(String borrowingDateStr) {
        this.borrowingDateStr = borrowingDateStr;
    }

    public String getRealReturnDateStr() {
        return realReturnDateStr;
    }

    public void setRealReturnDateStr(String realReturnDateStr) {
        this.realReturnDateStr = realReturnDateStr;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public String getPreApproveId() {
        return preApproveId;
    }

    public void setPreApproveId(String preApproveId) {
        this.preApproveId = preApproveId;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public String getApproveOperation() {
        return approveOperation;
    }

    public void setApproveOperation(String approveOperation) {
        this.approveOperation = approveOperation;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    public String getApplyTypeName() {
        return applyTypeName;
    }

    public void setApplyTypeName(String applyTypeName) {
        this.applyTypeName = applyTypeName;
    }

    public String getApproveStatusName() {
        return approveStatusName;
    }

    public void setApproveStatusName(String approveStatusName) {
        this.approveStatusName = approveStatusName;
    }

    public String getLiteratureType() {
        return literatureType;
    }

    public void setLiteratureType(String literatureType) {
        this.literatureType = literatureType;
    }

    public String getLiteratureTypeName() {
        return literatureTypeName;
    }

    public void setLiteratureTypeName(String literatureTypeName) {
        this.literatureTypeName = literatureTypeName;
    }

    public String getLiteratureName() {
        return literatureName;
    }

    public void setLiteratureName(String literatureName) {
        this.literatureName = literatureName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiteratureId() {
        return literatureId;
    }

    public void setLiteratureId(String literatureId) {
        this.literatureId = literatureId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getApplyReasons() {
        return applyReasons;
    }

    public void setApplyReasons(String applyReasons) {
        this.applyReasons = applyReasons;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getPlanReturnDate() {
        return planReturnDate;
    }

    public void setPlanReturnDate(String planReturnDate) {
        this.planReturnDate = planReturnDate;
    }

    public Date getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getInformationSources() {
        return informationSources;
    }

    public void setInformationSources(String informationSources) {
        this.informationSources = informationSources;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
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
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }
}
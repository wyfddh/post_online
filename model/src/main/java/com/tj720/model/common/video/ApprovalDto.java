package com.tj720.model.common.video;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/29 15:01
 */
public class ApprovalDto {
    private String id;
    private String[] ids;
    private String actionType;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    private String authSetting;
    private String type;
    private String approval;
    private String remark;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    private String currentUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getAuthSetting() {
        return authSetting;
    }

    public void setAuthSetting(String authSetting) {
        this.authSetting = authSetting;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

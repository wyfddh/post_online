package com.tj720.model.common.publiccurator;

import com.tj720.model.common.Attachment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * post_public_curator   公众策展
 * @author  xa
 */
//@Data
public class PostPublicCurator implements Serializable {


    private static final long serialVersionUID = -8535356218896768804L;
    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 图片id，以逗号隔开
     */
    private String datumIds;

    /**
     * 藏品关联id
     */
    private String collectId;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 主题描述
     */
    private String themeContent;

    /**
     * 藏品数量
     */
    private String amount;

    /**
     * 处理状态（0：待处理，1:已处理）
     */
    private String processState;

    /**
     * 处理结果（0:采用 1：不采用）
     */
    private String processResult;

    /**
     * 数据状态（0：已删除，1:正常）
     */
    private String dataState;


    /**
     * 备注
     */
    private String remark;

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
     * 创建用户
     */
    private String userName;

    /**
     * 备用字段2
     */
    private String other2;

    /**
     * 备用字段3
     */
    private String other3;


    //主图url
    private String mainPicUrl;
    //图片集合
    private List<Attachment> picList;

    private String objectListStr;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDatumIds() {
        return datumIds;
    }

    public void setDatumIds(String datumIds) {
        this.datumIds = datumIds;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeContent() {
        return themeContent;
    }

    public void setThemeContent(String themeContent) {
        this.themeContent = themeContent;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }

    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    public List<Attachment> getPicList() {
        return picList;
    }

    public void setPicList(List<Attachment> picList) {
        this.picList = picList;
    }

    public String getObjectListStr() {
        return objectListStr;
    }

    public void setObjectListStr(String objectListStr) {
        this.objectListStr = objectListStr;
    }
}
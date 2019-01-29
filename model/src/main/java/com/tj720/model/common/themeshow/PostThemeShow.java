package com.tj720.model.common.themeshow;

import com.tj720.model.common.AppCommonVO;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collectiontype.PostCollectionType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PostThemeShow extends  AppCommonVO  implements Serializable{


    private static final long serialVersionUID = 5350557632635455435L;

    private String collectId;

    private String themeContent;

    private String amount;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String updater;

    private String other2;

    private String other3;

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getThemeContent() {
        return themeContent;
    }

    public void setThemeContent(String themeContent) {
        this.themeContent = themeContent;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    private String id;        //主题展览id

    private String userId;      // 用户id

    private String userName;    // 用户名

    private String datumIds;     //图片id，以逗号隔开

    private String themeName;    //主题名称

    private String themeDescribe;     //主题描述

    private String themeSource;     //主题来源

    private String collectionAmount;     //藏品数量

    private String pageRecommend;      //首页推荐（1：是0：否）

    private String selectedTopics;    //精选专题（1：是0：否）

    private String processState;    // 处理状态（0：待处理，1:已处理）

    private String processResult;   // 处理结果（1:采用 0：不采用）

    private String dataState;        //数据状态（0：已删除，1:正常）

    private String createBy;         //创建人

    private Date createDate;         //创建时间

    private String updateBy;        //更新人

    private Date updateDate;       //更新时间

    private String collectIds;      // 藏品id

    //推荐状态标记
    private String pageRecommendCheck;

    //主图url
    private String mainPicUrl;

    //图片集合
    private List<Attachment> picList;


    //藏品列表中间表
    private List<PostCollectionType> objectList;

    //藏品列表
    private List<Collect> collectionList;

    private List<CollectDto> collectDtoList;

    private String objectListStr;

    private  String  hasCollected;  //是否收藏，1、已收藏  0、未收藏

    public List<CollectDto> getCollectDtoList() {
        return collectDtoList;
    }

    public void setCollectDtoList(List<CollectDto> collectDtoList) {
        this.collectDtoList = collectDtoList;
    }

    public String getHasCollected() {
        return hasCollected;
    }

    public void setHasCollected(String hasCollected) {
        this.hasCollected = hasCollected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatumIds() {
        return datumIds;
    }

    public void setDatumIds(String datumIds) {
        this.datumIds = datumIds;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeDescribe() {
        return themeDescribe;
    }

    public void setThemeDescribe(String themeDescribe) {
        this.themeDescribe = themeDescribe;
    }

    public String getThemeSource() {
        return themeSource;
    }

    public void setThemeSource(String themeSource) {
        this.themeSource = themeSource;
    }

    public String getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(String collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public String getPageRecommend() {
        return pageRecommend;
    }

    public void setPageRecommend(String pageRecommend) {
        this.pageRecommend = pageRecommend;
    }

    public String getSelectedTopics() {
        return selectedTopics;
    }

    public void setSelectedTopics(String selectedTopics) {
        this.selectedTopics = selectedTopics;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPageRecommendCheck() {
        return pageRecommendCheck;
    }

    public void setPageRecommendCheck(String pageRecommendCheck) {
        this.pageRecommendCheck = pageRecommendCheck;
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

    public List<PostCollectionType> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<PostCollectionType> objectList) {
        this.objectList = objectList;
    }

    public String getObjectListStr() {
        return objectListStr;
    }

    public void setObjectListStr(String objectListStr) {
        this.objectListStr = objectListStr;
    }

    public List<Collect> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Collect> collectionList) {
        this.collectionList = collectionList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(String collectIds) {
        this.collectIds = collectIds;
    }
}
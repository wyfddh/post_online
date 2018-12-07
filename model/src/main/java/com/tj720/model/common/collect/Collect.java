package com.tj720.model.common.collect;

import com.tj720.model.common.AppCommonVO;
import com.tj720.model.common.Attachment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Collect extends AppCommonVO implements Serializable{
    private String id;

    private String colleId;

    private String msg;

    private String pictureids;

    private String name;

    private String typeId;

    private String sonTypeId;

    private String commend;

    private String isdelete;

    private String sort;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String updater;

    private String other1;

    private String other2;

    private String other3;

    //主图url
    private String mainPicUrl;
    //图片集合
    private List<Attachment> picList;

    private  String  hasCollected;  //是否收藏，1、已收藏  0、未收藏


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
        this.id = id == null ? null : id.trim();
    }

    public String getColleId() {
        return colleId;
    }

    public void setColleId(String colleId) {
        this.colleId = colleId == null ? null : colleId.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getPictureids() {
        return pictureids;
    }

    public void setPictureids(String pictureids) {
        this.pictureids = pictureids == null ? null : pictureids.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getSonTypeId() {
        return sonTypeId;
    }

    public void setSonTypeId(String sonTypeId) {
        this.sonTypeId = sonTypeId == null ? null : sonTypeId.trim();
    }

    public String getCommend() {
        return commend;
    }

    public void setCommend(String commend) {
        this.commend = commend == null ? null : commend.trim();
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete == null ? null : isdelete.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
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
}
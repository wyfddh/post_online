package com.tj720.model.common.social;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

public class PostSocial implements Serializable{

    private static final long serialVersionUID = 4410925835520005892L;


    private String id;          //社教id

    private String socialName;     //社教名称

    private String cooperationMode;     //合作方式

    private String cooperationUnit;    //合作单位

    private Date holdTime;     //举办时间

    @Transient
    private String holdTimeStr;     //举办时间转换

    private String adoptionPatterns;       //采用模式

    private String keyWord;      //关键词

    private String head;       //负责人

    private String socialInstruction;       //社教说明

    private String socialPurpose;       //社教宗旨

    private String attachment;     //附件

    private String remark;      //备注

    private String creator;    //创建人

    private String updater;     //最后修改人

    private Date createTime;    //创建时间

    private Date updateTime;    //修改时间

    private Integer status;       //状态

    private Integer sort;      //排序，越大越靠前

    private Integer isdelete;   //状态1删除 0保存

    private String ext1;       //备用字段1

    private String ext2;       //备用字段2

    private String ext3;       //备用字段3

    private String  datumIds;     //资料批量id


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getCooperationMode() {
        return cooperationMode;
    }

    public void setCooperationMode(String cooperationMode) {
        this.cooperationMode = cooperationMode;
    }

    public String getCooperationUnit() {
        return cooperationUnit;
    }

    public void setCooperationUnit(String cooperationUnit) {
        this.cooperationUnit = cooperationUnit;
    }

    public Date getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Date holdTime) {
        this.holdTime = holdTime;
    }

    public String getHoldTimeStr() {
        return holdTimeStr;
    }

    public void setHoldTimeStr(String holdTimeStr) {
        this.holdTimeStr = holdTimeStr;
    }

    public String getAdoptionPatterns() {
        return adoptionPatterns;
    }

    public void setAdoptionPatterns(String adoptionPatterns) {
        this.adoptionPatterns = adoptionPatterns;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSocialInstruction() {
        return socialInstruction;
    }

    public void setSocialInstruction(String socialInstruction) {
        this.socialInstruction = socialInstruction;
    }

    public String getSocialPurpose() {
        return socialPurpose;
    }

    public void setSocialPurpose(String socialPurpose) {
        this.socialPurpose = socialPurpose;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getDatumIds() {
        return datumIds;
    }

    public void setDatumIds(String datumIds) {
        this.datumIds = datumIds;
    }
}
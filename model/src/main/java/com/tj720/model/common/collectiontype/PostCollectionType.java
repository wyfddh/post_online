package com.tj720.model.common.collectiontype;

import java.io.Serializable;
import java.util.Date;

public class PostCollectionType implements Serializable{


    private static final long serialVersionUID = -8267133894577833676L;

    private String id;      //主键id

    private String themeShowId;       //主题展id   或者公共策展id

    private String collectId;        //藏品id

    private String status;       //状态（1-正常   0-无效）

    private String isdelete;     //状态（0-正常   1-删除）

    private String sort;       //排序

    private String createBy;    //创建人

    private Date createDate;    //创建时间

    private String updateBy;    //更新人

    private Date updateDate;    //更新时间

    private String other1;     //备用字段1

    private String other2;     //备用字段2

    private String other3;    //备用字段3


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThemeShowId() {
        return themeShowId;
    }

    public void setThemeShowId(String themeShowId) {
        this.themeShowId = themeShowId;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
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
}
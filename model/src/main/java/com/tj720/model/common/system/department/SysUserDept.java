package com.tj720.model.common.system.department;

import java.io.Serializable;
import java.util.Date;

public class SysUserDept implements Serializable{


    private static final long serialVersionUID = 1157001482003705622L;

    private String id;    //用户部门编号

    private String userId;       //用户编号

    private String deptId;       //部门编号

    private String sort;       //排序

    private String remark;     //备注

    private Integer status;    //状态

    private Integer isdelete;      //是否删除

    private String  isman;      //

    private String ext1;      //备用字段1

    private String ext2;     //备用字段2

    private String ext3;     //备用字段3

    private Date createTime;    //创建时间

    private Date updateTime;    //创建时间

    private String creator;     //创建者

    private String updater;     //修改者


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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getIsman() {
        return isman;
    }

    public void setIsman(String isman) {
        this.isman = isman;
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
}
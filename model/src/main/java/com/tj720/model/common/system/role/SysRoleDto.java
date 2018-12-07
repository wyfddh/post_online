package com.tj720.model.common.system.role;

import java.util.Date;

public class SysRoleDto {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.id
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.role_code
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String roleCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.name
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.sort
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.remark
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.status
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.creator
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.updater
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String updater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.create_time
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.update_time
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.ext1
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String ext1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.ext2
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String ext2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.ext3
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    private String ext3;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.id
     *
     * @return the value of sys_role.id
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    /**
     * 用户数
     */
    private int userNumber;

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.id
     *
     * @param id the value for sys_role.id
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.role_code
     *
     * @return the value of sys_role.role_code
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.role_code
     *
     * @param roleCode the value for sys_role.role_code
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.name
     *
     * @return the value of sys_role.name
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.name
     *
     * @param name the value for sys_role.name
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.sort
     *
     * @return the value of sys_role.sort
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.sort
     *
     * @param sort the value for sys_role.sort
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.remark
     *
     * @return the value of sys_role.remark
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.remark
     *
     * @param remark the value for sys_role.remark
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.status
     *
     * @return the value of sys_role.status
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.status
     *
     * @param status the value for sys_role.status
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.creator
     *
     * @return the value of sys_role.creator
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.creator
     *
     * @param creator the value for sys_role.creator
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.updater
     *
     * @return the value of sys_role.updater
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.updater
     *
     * @param updater the value for sys_role.updater
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.create_time
     *
     * @return the value of sys_role.create_time
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.create_time
     *
     * @param createTime the value for sys_role.create_time
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.update_time
     *
     * @return the value of sys_role.update_time
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.update_time
     *
     * @param updateTime the value for sys_role.update_time
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.ext1
     *
     * @return the value of sys_role.ext1
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.ext1
     *
     * @param ext1 the value for sys_role.ext1
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.ext2
     *
     * @return the value of sys_role.ext2
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.ext2
     *
     * @param ext2 the value for sys_role.ext2
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.ext3
     *
     * @return the value of sys_role.ext3
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.ext3
     *
     * @param ext3 the value for sys_role.ext3
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    public SysRoleDto(SysRole sysRole,int userNumber) {
        this.id = sysRole.getId();
        this.name = sysRole.getName();
        this.roleCode = sysRole.getRoleCode();
        this.sort = sysRole.getSort();
        this.status = sysRole.getStatus();
        this.creator = sysRole.getCreator();
        this.createTime = sysRole.getCreateTime();
        this.updater = sysRole.getUpdater();
        this.updateTime = sysRole.getUpdateTime();
        this.ext1 = sysRole.getExt1();
        this.ext2 = sysRole.getExt2();
        this.ext3 = sysRole.getExt3();
        this.userNumber = userNumber;
    }
}
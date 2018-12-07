package com.tj720.model.common.system.user;

import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.department.SysUserDept;
import com.tj720.model.common.system.role.SysRole;
import com.tj720.model.common.system.role.SysRoleAuth;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MyPC on 2018/10/10.
 */
public class SysUserDto implements Serializable {

    /** 旧密码 */
    private String oldPassword;

    private SysUser sysUser;

    private SysUserDept sysUserDept;

    private SysRoleAuth sysRoleAuth;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysUserDept getSysUserDept() {
        return sysUserDept;
    }

    public void setSysUserDept(SysUserDept sysUserDept) {
        this.sysUserDept = sysUserDept;
    }

    public SysRoleAuth getSysRoleAuth() {
        return sysRoleAuth;
    }

    public void setSysRoleAuth(SysRoleAuth sysRoleAuth) {
        this.sysRoleAuth = sysRoleAuth;
    }

    private String id;

    private String userName;

    private String name;

    private String email;

    private String phone;

    private String deptName;

    private String deptId;

    private String pId;

    private String roleName;

    private String roleId;

    private String password;

    private String surePassword;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String updater;

    private Integer sequence;

    private Integer status;

    private Integer isdelete;

    public String getId(){
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurePassword() {
        return surePassword;
    }

    public void setSurePassword(String surePassword) {
        this.surePassword = surePassword;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public SysUserDto(SysUser sysUser, SysDepartment sysDepartment, SysRole sysRole) {
        this.id = sysUser.getId();
        this.userName = sysUser.getUserName();
        this.name = sysUser.getName();
        this.email = sysUser.getEmail();
        this.phone = sysUser.getPhone();
        this.deptName = sysDepartment.getDepartmentName();
        this.deptId = sysDepartment.getDepartmentId();
        this.pId = sysDepartment.getParentId();
        this.roleName = sysRole.getName();
        this.roleId = sysRole.getId();
        this.password = sysUser.getPassword();
        this.surePassword = sysUser.getSurePassword();
        this.remark = sysUser.getRemark();
        this.createTime = sysUser.getCreateTime();
        this.updateTime = sysUser.getUpdateTime();
        this.creator = sysUser.getCreator();
        this.updater = sysUser.getUpdater();
        this.sequence = sysUser.getSequence();
        this.status = sysUser.getStatus();
        this.isdelete = sysUser.getIsdelete();

    }

    public SysUserDto() {
    }

    ;
}

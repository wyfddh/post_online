package com.tj720.dao;


import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.common.system.user.SysUserDto;
import com.tj720.model.common.system.user.SysUserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface SysUserMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
//    int countByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
//    int deleteByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
    int insertSelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
//    List<SysUser> selectByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
    SysUser selectByPrimaryKey(@Param("id") String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
//    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
//    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
    int updateByPrimaryKeySelective(SysUser record);


    int  updateUserPassword(SysUserDto  sysUserDto);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Fri Oct 12 09:08:48 CST 2018
     */
    int updateByPrimaryKey(SysUser record);

    List<SysUser> selectByWhereParams(String param);

    List<SysUserDto> getSysUserList(HashMap map);

    int selectSysUserCount(HashMap map);

    List<SysUserDto> getSysUserById(@Param(value="id")String id);

    /**
     * 根据deptId查询用户记录
     */
    SysUserDto getUserById(String deptId);

    List<SysUser> getUserByDepartmentName(String name);

    List<SysUser> getUserByRoleCode(String code);

    List<SysUser> getAllUserInfo();

    List<SysUser> getByMap(HashMap<String, Object> map);

    int countByMap(HashMap<String, Object> map);
}
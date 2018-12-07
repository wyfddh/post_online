package com.tj720.dao;

import com.tj720.model.common.system.menu.SysFunction;
import com.tj720.model.common.system.menu.SysResAuth;
import com.tj720.model.common.system.menu.SysResAuthExample;
import java.util.HashMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysResAuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
//    int countByExample(SysResAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
//    int deleteByExample(SysResAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
    int insert(SysResAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
    int insertSelective(SysResAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
//    List<SysResAuth> selectByExample(SysResAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
    SysResAuth selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
//    int updateByExampleSelective(@Param("record") SysResAuth record, @Param("example") SysResAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
//    int updateByExample(@Param("record") SysResAuth record, @Param("example") SysResAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
    int updateByPrimaryKeySelective(SysResAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_res_auth
     *
     * @mbggenerated Wed Oct 10 15:47:07 CST 2018
     */
    int updateByPrimaryKey(SysResAuth record);

    /**
     * 查询功能列表
     * @param roleId
     * @return
     */
    List<SysFunction> queryFunctionList(String roleId);

    /**
     * 查询功能列表
     * @param userId 用户id
     * @param type 功能类型
     * @return
     */
    List<SysFunction> getFunctionByUser(@Param("userId") String userId,@Param("type") String type);

    void deleteByRoleId(String roleId);

    List<SysResAuth> selectByMap(HashMap<String, Object> map);

    void deleteByFunctionId(String functionId);
}
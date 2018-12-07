package com.tj720.dao;

import com.tj720.model.literature.PostLiteratureProcessDetail;
import java.util.HashMap;
import java.util.List;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.model.common.system.menu.MipMenu;
import com.tj720.model.common.system.menu.MipMenuExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MipMenuMapper extends BaseDao<MipMenu> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_menu
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int countByExample(MipMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_menu
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int deleteByExample(MipMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_menu
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int insert(MipMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_menu
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int insertSelective(MipMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_menu
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    List<MipMenu> selectByExample(MipMenuExample example);
    
    /**
     * 获取角色有权限的所有菜单
     * @param roleIds
     * @return
     */
    List<MipMenu> selectMenusByRoleIds(String roleIds[]);
    
    MipMenu selectByPrimaryKey(String id);

    List<MipMenu> selectMenuByMap(HashMap<String, Object> map);

    int updateByPrimaryKeySelective(MipMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_menu
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int updateByExampleSelective(@Param("record") MipMenu record, @Param("example") MipMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_menu
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int updateByExample(@Param("record") MipMenu record, @Param("example") MipMenuExample example);

}
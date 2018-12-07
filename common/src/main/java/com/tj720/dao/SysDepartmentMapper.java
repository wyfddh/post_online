package com.tj720.dao;

import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.menu.MenuTreeDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
@Repository
public interface SysDepartmentMapper{


    /**
     * 获取某个节点的子节点数目,用于删除的特殊判断
     */
   int getChildCount(String departmentId);
    /**
     * 根据id删除部门
     */
    int deleteByPrimaryKey(String departmentId);

    /**
     * 添加部门记录
     */
    int insertSelective(SysDepartment record);

    /**
     * 根据id查询部门记录
     */
    SysDepartment selectByPrimaryKey(String departmentId);

    /**
     * 根据userid查询部门记录
     */
    SysDepartment getDeptById(String userId);

    /**
     * 查询部门记录集合
     */
    List<SysDepartment> departmentList(Map<String,Object> map);

    /**
     * 查询部门记录数
     */
    int count(Map<String, Object> map);

    /**
     * 修改部门记录
     */
    int updateByPrimaryKeySelective(SysDepartment record);

    /**
     * 修改部门状态
     */
    int updateDeptStatus(@Param("status") String status,@Param("departmentId") String departmentId);


   /**
    * 查询部门记录集合（id和名称）
    */
   List<SysDepartment> getDeptOptions();


    /**
     * 查询通过名称和父级id查询是否重复
     */
    int countByNameAndParentId(@Param("parentId") String parentId, @Param("departmentName") String departmentName, @Param("departmentId") String departmentId);


   /**
    * 查询部门记录集合
    */
    List<MenuTreeDto> getAllDeptList();

    List<SysDepartment> getAllOrgInfo();

    /**
     * 查询顶级部门个数
     * @return
     */
   Integer getLevel1Org();


}
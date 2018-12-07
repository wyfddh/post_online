package com.tj720.dao;


import com.tj720.model.common.system.department.SysUserDept;
import com.tj720.model.common.system.department.SysUserDeptExample;
import java.util.HashMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SysUserDeptMapper{

    /**
     * 计数
     */
//    int countByExample(SysUserDeptExample example);

    /**
     * 删除
     */
//    int deleteByExample(SysUserDeptExample example);

    /**
     * 根据id删除
     */
    int deleteByPrimaryKey(String userId);

    /**
     * 根据id删除
     */
    int deleteByPrimaryKey(String id,String deptId);

    /**
     * 添加
     */
    int insert(SysUserDept record);

    /**
     * 添加
     */
    int insertSelective(SysUserDept record);

    /**
     * 数据集合
     */
//    List<SysUserDept> selectByExample(SysUserDeptExample example);

    /**
     * 通过id查询
     */
    SysUserDept selectByPrimaryKey(String id);


    /**
     * 通过用户id查询部门
     */
    SysUserDept getDeptById(String userId);


    /**
     * 修改
     */
//    int updateByExampleSelective(@Param("record") SysUserDept record, @Param("example") SysUserDeptExample example);

    /**
     * 修改
     */
//    int updateByExample(@Param("record") SysUserDept record, @Param("example") SysUserDeptExample example);

    /**
     * 修改
     */
    int updateByPrimaryKeySelective(SysUserDept record);

    /**
     * 修改
     */
    int updateByPrimaryKey(SysUserDept record);


    /**
     * 统计关联对象数
     * @param deptId 部门id
     * @param userId 用户id
     * @return
     */
    int countUserDept(@Param("deptId ") String deptId,@Param("userId") String userId);



    /**
     * 查询关联对象
     * @param userId 用户id
     * @param deptId 部门id
     * @return
     */
    List<SysUserDept> getUserDeptByCondition(@Param("userId") String userId, @Param("deptId ") String deptId);

    List<SysUserDept> getAllUserDeptInfo();

    List<SysUserDept> getByMap(HashMap<String, Object> map);

    int updateByMap(HashMap<String, Object> map);
}
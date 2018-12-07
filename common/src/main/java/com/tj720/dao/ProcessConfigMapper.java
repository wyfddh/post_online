package com.tj720.dao;

import com.tj720.model.common.system.process.ProcessConfig;
import com.tj720.model.common.system.process.ProcessConfigExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ProcessConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
//    int countByExample(ProcessConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
//    int deleteByExample(ProcessConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
    int deleteByPrimaryKey(String xid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
    int insert(ProcessConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
    int insertSelective(ProcessConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
//    List<ProcessConfig> selectByExample(ProcessConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
    ProcessConfig selectByPrimaryKey(String xid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
//    int updateByExampleSelective(@Param("record") ProcessConfig record, @Param("example") ProcessConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
//    int updateByExample(@Param("record") ProcessConfig record, @Param("example") ProcessConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
    int updateByPrimaryKeySelective(ProcessConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table process_config
     *
     * @mbggenerated Thu Sep 27 10:46:44 CST 2018
     */
    int updateByPrimaryKey(ProcessConfig record);

    /**
     *  获取流程配置列表
     * @param processDefName 流程定义名称
     * @param startRow 开始页数
     * @param pageSize 每页页数
     * @return 流程配置列表
     */
    List<ProcessConfig> getProcessConfigList(@Param("processDefName") String processDefName,
                                             @Param("startRow") int startRow, @Param("pageSize") int pageSize);

    /**
     * 统计流程配置总数
     * @param processDefName 流程定义名称
     * @return 流程配置总数
     */
    Integer countProcessConfigList(@Param("processDefName") String processDefName);

    /**
     * 根据条件查询流程配置
     * @param map 参数列表
     * @return 流程配置
     */
    ProcessConfig getProcessConfigByCondition(HashMap<String, Object> map);
}
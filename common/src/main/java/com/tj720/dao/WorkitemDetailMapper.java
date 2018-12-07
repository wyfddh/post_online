package com.tj720.dao;


import com.tj720.model.common.system.process.WorkitemDetail;
import com.tj720.model.common.system.process.WorkitemDetailExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkitemDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
//    int countByExample(WorkitemDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
//    int deleteByExample(WorkitemDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
    int deleteByPrimaryKey(String xid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
    int insert(WorkitemDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
    int insertSelective(WorkitemDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
//    List<WorkitemDetail> selectByExample(WorkitemDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
    WorkitemDetail selectByPrimaryKey(String xid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
//    int updateByExampleSelective(@Param("record") WorkitemDetail record, @Param("example") WorkitemDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
//    int updateByExample(@Param("record") WorkitemDetail record, @Param("example") WorkitemDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
    int updateByPrimaryKeySelective(WorkitemDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workitem_detail
     *
     * @mbggenerated Sun Sep 30 15:25:00 CST 2018
     */
    int updateByPrimaryKey(WorkitemDetail record);
}
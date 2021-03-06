package com.tj720.dao;


import com.tj720.model.common.area.MipArea;
import com.tj720.model.common.area.MipAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MipAreaMapper{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int countByExample(MipAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int deleteByExample(MipAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int insert(MipArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int insertSelective(MipArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    List<MipArea> selectByExample(MipAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    MipArea selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int updateByExampleSelective(@Param("record") MipArea record, @Param("example") MipAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
//    int updateByExample(@Param("record") MipArea record, @Param("example") MipAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int updateByPrimaryKeySelective(MipArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_area
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int updateByPrimaryKey(MipArea record);

	List<MipArea> getAreaJson();

    List<MipArea> getCityListByPid(Integer pid);
}
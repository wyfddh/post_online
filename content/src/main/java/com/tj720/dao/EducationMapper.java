package com.tj720.dao;


import com.tj720.model.common.education.Education;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationMapper {

    int deleteByPrimaryKey(String id);

    int insert(Education record);

    int insertSelective(Education record);

    Education selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Education record);

    int updateByPrimaryKey(Education record);

    int selectCountByEducation(Map map);

    List<Education> selectListByEducation(Map map);

    int updateEducationByIds(@Param("ids")List<String> ids);



    //Web端接口
    int  countWebEdu(Map map);

    List<Education>  selectListWebByEducation(Map map);

    Education  selectWebEduByKey(String id);

    Education  getDetailEducationById(@Param("start") Integer start);



}
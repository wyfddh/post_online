package com.tj720.dao;

import com.tj720.model.common.research.Research;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchMapper {

    int deleteByPrimaryKey(String id);

    int insert(Research record);

    int insertSelective(Research record);

    Research selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Research record);

    int updateByPrimaryKey(Research record);

    int selectCountByResearch(Map map);

    List<Research> selectListByResearch(Map map);

    int updateResearchByIds(@Param("ids")List<String> ids);



    //Web端接口
    int  countWebResearch(Map map);

    List<Research>  selectListWebByResearch(Map map);

    Research  selectWebReserarchByKey(String id);

    Research getDetailResearchById(@Param("start") Integer start);

}
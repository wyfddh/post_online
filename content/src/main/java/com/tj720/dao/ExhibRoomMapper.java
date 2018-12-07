package com.tj720.dao;

import com.tj720.model.common.exhibition.ExhibRoom;
import com.tj720.model.common.exhibition.ExhibRoomExample;
import java.util.HashMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibRoomMapper {
//    int countByExample(ExhibRoomExample example);

//    int deleteByExample(ExhibRoomExample example);

    int deleteByPrimaryKey(String id);

    int insert(ExhibRoom record);

    int insertSelective(ExhibRoom record);

    int insertByForeachRoom(@Param("list") List<ExhibRoom> list);

//    List<ExhibRoom> selectByExample(ExhibRoomExample example);

    ExhibRoom selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") ExhibRoom record, @Param("example") ExhibRoomExample example);

//    int updateByExample(@Param("record") ExhibRoom record, @Param("example") ExhibRoomExample example);

    int updateByPrimaryKeySelective(ExhibRoom record);

    int updateByPrimaryKey(ExhibRoom record);

    /**
     * 根据陈展id删除展厅
     * @param exhibId
     * @return
     */
    Integer deleteByExhibId(String exhibId);

    List<ExhibRoom> selectByMap(HashMap<String, Object> map1);
}
package com.tj720.dao;

import com.tj720.model.common.exhibition.Exhibition;
import com.tj720.model.common.exhibition.ExhibitionExample;
import com.tj720.model.common.exhibition.ExhibitionVo;
import java.util.HashMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionMapper {
//    int countByExample(ExhibitionExample example);

//    int deleteByExample(ExhibitionExample example);

    int deleteByPrimaryKey(String id);

    int insert(Exhibition record);

    int insertSelective(Exhibition record);

//    List<Exhibition> selectByExample(ExhibitionExample example);

    Exhibition selectByPrimaryKey(String id);

    List<ExhibitionVo> selectListByExhibVo(Map map);

    int selectCountByExhibVo(Map map);

//    int updateByExampleSelective(@Param("record") Exhibition record, @Param("example") ExhibitionExample example);

//    int updateByExample(@Param("record") Exhibition record, @Param("example") ExhibitionExample example);

    int updateByPrimaryKeySelective(Exhibition record);

    int updateByPrimaryKey(Exhibition record);

    int updateExhibByIds(@Param("ids")List<String> ids);

    /**
     * 根据id删除展陈
     * @param parma
     * @return
     */
    Integer deleteExhibColleById(Map<String, Object> parma);

    List<Exhibition> selectByMap(HashMap<String, Object> map);
}
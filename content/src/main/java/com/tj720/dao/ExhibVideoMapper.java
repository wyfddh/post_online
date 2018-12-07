package com.tj720.dao;

import com.tj720.model.common.exhibition.ExhibVideo;
import com.tj720.model.common.exhibition.ExhibVideoExample;
import com.tj720.model.common.video.PostExhibVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibVideoMapper {
//    int countByExample(ExhibVideoExample example);

//    int deleteByExample(ExhibVideoExample example);

    int deleteByPrimaryKey(String id);

    int insert(ExhibVideo record);

    int insertSelective(ExhibVideo record);

    int insertByForeachVideo(@Param("list") List<PostExhibVideo> list);

//    List<ExhibVideo> selectByExample(ExhibVideoExample example);

    List<PostExhibVideo> selectForEnhib(String exhibId);

    ExhibVideo selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") ExhibVideo record, @Param("example") ExhibVideoExample example);

//    int updateByExample(@Param("record") ExhibVideo record, @Param("example") ExhibVideoExample example);

    int updateByPrimaryKeySelective(ExhibVideo record);

    int updateByPrimaryKey(ExhibVideo record);

    /**
     * 根据陈展id删除影音资料
     * @param exhibId
     * @return
     */
    Integer deleteByExhibId(String exhibId);
}
package com.tj720.dao;

import com.tj720.model.common.video.PostActionDetail;
import com.tj720.model.common.video.PostActionDetailExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostActionDetailMapper {
//    int countByExample(PostActionDetailExample example);

//    int deleteByExample(PostActionDetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(PostActionDetail record);

    int insertSelective(PostActionDetail record);

//    List<PostActionDetail> selectByExample(PostActionDetailExample example);

    PostActionDetail selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") PostActionDetail record, @Param("example") PostActionDetailExample example);

//    int updateByExample(@Param("record") PostActionDetail record, @Param("example") PostActionDetailExample example);

    int updateByPrimaryKeySelective(PostActionDetail record);

    int updateByPrimaryKey(PostActionDetail record);
}
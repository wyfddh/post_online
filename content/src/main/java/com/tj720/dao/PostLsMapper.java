package com.tj720.dao;

import com.tj720.model.common.video.PostLs;
import com.tj720.model.common.video.PostLsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostLsMapper {
//    int countByExample(PostLsExample example);

//    int deleteByExample(PostLsExample example);

    int deleteByPrimaryKey(String xid);

    int insert(PostLs record);

    int insertSelective(PostLs record);

//    List<PostLs> selectByExample(PostLsExample example);

    PostLs selectByPrimaryKey(String xid);

//    int updateByExampleSelective(@Param("record") PostLs record, @Param("example") PostLsExample example);

//    int updateByExample(@Param("record") PostLs record, @Param("example") PostLsExample example);

    int updateByPrimaryKeySelective(PostLs record);

    int updateByPrimaryKey(PostLs record);

    /**
     * 获取最大流水号
     * @param lsKey 流水key
     * @param lsType 流水类型
     * @return
     */
    Integer getMaxLsCode(@Param("lsKey") String lsKey,@Param("lsType") String lsType);

}
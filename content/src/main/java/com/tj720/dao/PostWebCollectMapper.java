package com.tj720.dao;

import com.tj720.model.common.postwebcollect.PostWebCollect;
import com.tj720.model.common.postwebcollect.PostWebCollectExample;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface PostWebCollectMapper {
//    long countByExample(PostWebCollectExample example);

//    int deleteByExample(PostWebCollectExample example);

    int deleteByPrimaryKey(String id);

    int insert(PostWebCollect record);


    /**
     *   添加收藏
     **/
    int insertSelective(PostWebCollect record);


    /**
     * 根据用户id和cid查询出收藏类
     * @param
     * @return
     */
   PostWebCollect  getWebCollect(Map map);

//    List<PostWebCollect> selectByExample(PostWebCollectExample example);

    PostWebCollect selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") PostWebCollect record, @Param("example") PostWebCollectExample example);

//    int updateByExample(@Param("record") PostWebCollect record, @Param("example") PostWebCollectExample example);

    int updateByPrimaryKeySelective(PostWebCollect record);

    int updateByPrimaryKey(PostWebCollect record);
}
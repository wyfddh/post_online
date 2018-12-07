package com.tj720.dao;

import com.tj720.model.common.video.PostVideoComments;
import com.tj720.model.common.video.PostVideoCommentsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostVideoCommentsMapper {
//    int countByExample(PostVideoCommentsExample example);

//    int deleteByExample(PostVideoCommentsExample example);

    int deleteByPrimaryKey(String id);

    int insert(PostVideoComments record);

    int insertSelective(PostVideoComments record);

//    List<PostVideoComments> selectByExample(PostVideoCommentsExample example);

    PostVideoComments selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") PostVideoComments record, @Param("example") PostVideoCommentsExample example);

//    int updateByExample(@Param("record") PostVideoComments record, @Param("example") PostVideoCommentsExample example);

    int updateByPrimaryKeySelective(PostVideoComments record);

    int updateByPrimaryKey(PostVideoComments record);

    /**
     * 查询影视标注列表
     * @param videoId 影视资料id
     * @return
     */
    List<PostVideoComments> getPostVideoCommentsList(@Param("videoId") String videoId);

    /**
     * 新增影视资料标注列表
     * @param list 影视标注列表
     * @param videoId 影视资料id
     * @return
     */
    Integer addPostVideoComments(@Param("list") List<PostVideoComments> list,@Param("videoId") String videoId);

    /**
     * 删除影视资料标注列表
     * @param videoId 影视资料id
     * @return
     */
    Integer deletePostVideoComments(@Param("videoId") String videoId);
}
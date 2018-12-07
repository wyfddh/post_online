package com.tj720.dao;


import com.tj720.model.common.research.Research;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.model.common.stampstory.PostStampStoryExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PostStampStoryMapper{

    long countByExample(PostStampStoryExample example);

    int deleteByExample(PostStampStoryExample example);

    int insert(PostStampStory record);

    List<PostStampStory> selectByExample(PostStampStoryExample example);


    /**
     * 查询邮票故事记录数
     */
    Integer count(@Param("storyType") String storyType,@Param("createTime") String createTime,
        @Param("currentPage") Integer currentPage,@Param("size") Integer size,@Param("orderBy") String orderBy);


    /**
     * 查询邮票故事集合
     */
    List<PostStampStory> getStampStoryList(@Param("storyType") String storyType,@Param("createTime") String createTime,
              @Param("currentPage") Integer currentPage,@Param("size") Integer size,@Param("orderBy") String orderBy);

    /**
     * 根据id查询邮票故事
     */
    PostStampStory selectByPrimaryKey(String id);


    /**
     * 添加邮票故事
     */
    int insertSelective(PostStampStory record);


    /**
     * 修改邮票故事
     */
    int updateByPrimaryKeySelective(PostStampStory record);


    /**
     * 根据id删除邮票故事
     */
    int deleteByPrimaryKey(String id);


    /**
     * 根据id批量删除邮票故事
     */
    int updateStampStoryByIds(@Param("ids")List<String> ids);


    /**
     * 查询邮政故事类型集合
     */
    List<PostStampStory>  getStoryTypeOptions();


    int updateByExampleSelective(@Param("record") PostStampStory record, @Param("example") PostStampStoryExample example);

    int updateByExample(@Param("record") PostStampStory record, @Param("example") PostStampStoryExample example);

    int updateByPrimaryKey(PostStampStory record);


    //Web端接口
    /**
     * 查询邮票故事记录数
     */
    Integer  countWeb(@Param("storyType") String storyType,@Param("createTime") String createTime,
                  @Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 查询邮票故事集合
     */
    List<PostStampStory>  getStampStoryWebList(@Param("storyType") String storyType,@Param("createTime") String
            createTime,
                                           @Param("currentPage") Integer currentPage,@Param("size") Integer size);

    /**
     * 根据id查询邮票故事
     */
    PostStampStory   selectWebStoyrByPrimaryKey(String id);


    //查询（上一页、下一页）
    PostStampStory   getDetailStoryById(@Param("start") Integer start,@Param("storyType")String  storyType);



}
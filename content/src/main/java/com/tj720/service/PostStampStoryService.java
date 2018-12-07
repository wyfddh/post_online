package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.utils.Page;

import java.util.List;


public interface PostStampStoryService{


    /**
     * 查询邮票故事集合
     */
    JsonResult  getStampStoryList(String storyType,String createTime,String orderBy, Page page) throws Exception;



    /**
     * 根据id查询邮票故事
     */
    JsonResult selectByPrimaryKey(String id) throws Exception;



    /**
     * 添加邮票故事
     */
    JsonResult  insertSelective(PostStampStory record,String picids);


    /**
     * 修改邮票故事
     */
    JsonResult  updateByPrimaryKeySelective(PostStampStory record,String picids);


    /**
     * 根据id删除邮票故事
     */
    JsonResult  deleteByPrimaryKey(String id);



    /**
     *  根据id批量删除邮票故事
     */
    JsonResult updateStampStoryByIds(List<String> ids);




    /**
     *  邮票故事类型集合
     */
    List<PostStampStory>  getStoryTypeOptions();


}

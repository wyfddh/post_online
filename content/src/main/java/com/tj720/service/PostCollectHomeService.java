package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.collecthome.PostCollectHome;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.utils.Page;

import java.util.List;


public interface PostCollectHomeService{


    /**
     * 查询集邮之家集合
     */
    JsonResult  getHomeList(String collectHomeTheme,String orderBy, String createTime, Page page) throws Exception;



    /**
     * 根据id查询集邮之家
     */
    JsonResult selectByPrimaryKey(String id) throws Exception;



    /**
     * 添加集邮之家
     */
    JsonResult  insertSelective(PostCollectHome record);


    /**
     * 修改集邮之家
     */
    JsonResult  updateByPrimaryKeySelective(PostCollectHome record);


    /**
     * 根据id删除集邮之家
     */
    JsonResult  deleteByPrimaryKey(String id);



    /**
     *  根据id批量删除集邮之家
     */
    JsonResult updateHomeByIds(List<String> ids);




    /**
     *  集邮资讯主题集合
     */
    List<PostCollectHome>  getHomeOptions();


}

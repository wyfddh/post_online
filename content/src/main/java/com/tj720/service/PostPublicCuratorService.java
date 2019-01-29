package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.utils.Page;

import java.util.List;


public interface PostPublicCuratorService {


    /**
     * 查询公众策展集合
     */
    JsonResult  getCuratorList(String themeName, String processState, String processResult, Page page) throws Exception;



    /**
     * 根据id查询公众策展
     */
    JsonResult selectByPrimaryKey(String id) throws Exception;



    /**
     * 添加公众策展
     */
//    JsonResult  insertSelective(PostPublicCurator record,String picids);


    /**
     * 修改公众策展
     */
    JsonResult  updateByPrimaryKeySelective(PostThemeShow record, String picids);


    /**
     * 根据id删除公众策展
     */
    JsonResult  deleteByPrimaryKey(String id);



    /**
     *  根据id批量删除公众策展
     */
    JsonResult updateHomeByIds(List<String> ids);


    public JsonResult approvalInfo(String id,String approval,String remarks);



}

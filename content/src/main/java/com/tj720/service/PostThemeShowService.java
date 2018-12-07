package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.utils.Page;

import java.util.List;


public interface PostThemeShowService{


    /**
     * 根据id删除主题来源
     */
    JsonResult deleteByPrimaryKey(String id) throws Exception;

    /**
     * 根据id批量删除主题来源
     */
    JsonResult batchRemove(String[] ids) throws Exception;


    /**
     * 根据id批量删除主题来源(假删除,改状态)
     */
   JsonResult updateThemebByIds(List<String> ids);



    /**
     * 查询主题展览集合
     */
    JsonResult themeshowList(String themeName,String themeSource,String orderBy, Page page) throws Exception;


    /**
     * 根据id查询主题展览
     */
    JsonResult selectByPrimaryKey(String id) throws Exception;



    /**
     * 查询主题来源集合
     */
    JsonResult getSourceOptions() throws Exception;



    /**
     * 添加主题来源
     */
    JsonResult  insertSelective(PostThemeShow record,String picids);


    /**
     * 修改主题来源
     */
    JsonResult  updateByPrimaryKeySelective(PostThemeShow record,String picids);



    /**
     * 修改主题来源
     */
    void  updateByPrimaryKeySelective(PostThemeShow record);


    /**
     * 首页推荐
     */
    Integer CountSytj();

    /**
     * 精彩专题
     */
    Integer CountJczj();







}

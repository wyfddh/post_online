package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.utils.Page;
import java.util.List;



public interface PostInformationManagerService{




    /**
     * 查询资讯管理集合
     */
    JsonResult  getInformationList(String informationType,String createTime,String orderBy, Page page) throws Exception;



    /**
     * 根据id查询资讯管理
     */
    JsonResult selectByPrimaryKey(String id) throws Exception;



    /**
     * 添加资讯管理
     */
    JsonResult  insertSelective(PostInformationManage  record, String  picids);


    /**
     * 修改资讯管理
     */
    JsonResult  updateByPrimaryKeySelective(PostInformationManage  record,String  picids);


    /**
     * 根据id删除资讯管理
     */
    JsonResult  deleteByPrimaryKey(String id);



    /**
     *  根据id批量删除资讯管理
     */
    JsonResult  updateInfomationByIds(List<String> ids);





}

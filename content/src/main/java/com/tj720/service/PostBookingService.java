package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.utils.Page;

/**
 * 预约管理服务类
 * @Author: xa
 * @Date: 2018/11/3 11:41
 */
public interface PostBookingService{



    /**
     * 查询预约管理集合
     */
    JsonResult  getBookingList(String bookingType,String contacts, String orderTime,String userId,String orderBy,Page page) throws Exception;

    /**
     * 根据用户id查询预约列表
     * @param userId 用户id
     * @param page
     * @return
     */
    JsonResult  getBookingList(String userId,Page page);


    /**
     * 根据id查询预约管理
     */
    JsonResult  selectByPrimaryKey(String id,String userId) throws Exception;



    /**
     * 添加预约管理
     */
    JsonResult  insertSelective(PostBookingManage record);



    /**
     * 修改预约管理
     */
    JsonResult  updateByPrimaryKeySelective(PostBookingManage record);



}

package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.volunteer.PostVolunteerActivities;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesDto;
import com.tj720.utils.Page;

/**
 * @author 杜昶
 * @Date: 2018/11/8 11:38
 */
public interface PostVolunteerActivitiesService {

    /**
     * 查询志愿者活动列表
     * @param dto
     * @param page
     * @return
     */
    JsonResult getActivitiesList(PostVolunteerActivitiesDto dto, Page page);

    /**
     * 添加志愿者活动
     * @param postVolunteerActivities
     * @return
     */
    JsonResult addActivities(PostVolunteerActivities postVolunteerActivities);

    /**
     * 根据id获取活动信息
     * @param id
     * @return
     */
    JsonResult getActivitiesById(String id);

    /**
     * 修改志愿者活动
     * @param postVolunteerActivities
     * @return
     */
    JsonResult updateActivities(PostVolunteerActivities postVolunteerActivities);

    /**
     * 查询志愿者申请列表
     * @param id
     * @param page
     * @return
     */
    JsonResult getVolunteerApplyList(String id, Page page);


    /**
     * 根据id删除活动信息
     * @param id
     * @return
     */
    JsonResult deleteByPrimaryKey(String id);




}

package com.tj720.dao;

import com.tj720.model.common.volunteer.PostVolunteerActivities;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesDto;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 杜昶
 * @Date: 2018/11/8 11:45
 */
@Repository
public interface PostVolunteerActivitiesMapper {

    /**
     * 查询志愿者活动总数
     * @param dto
     * @return
     */
    Integer getActivitiesCount(PostVolunteerActivitiesDto dto);

    /**
     * 查询志愿者活动列表
     * @param dto
     * @return
     */
    List<PostVolunteerActivitiesVo> getActivitiesList(PostVolunteerActivitiesDto dto);

    /**
     * 新增志愿者活动
     * @param postVolunteerActivities
     * @return
     */
    Integer addActivities(PostVolunteerActivities postVolunteerActivities);

    /**
     * 根据id获取活动信息
     * @param id
     * @return
     */
    PostVolunteerActivitiesVo getActivitiesById(String id);

    /**
     * 根据id修改活动
     * @param postVolunteerActivities
     * @return
     */
    Integer updateActivities(PostVolunteerActivities postVolunteerActivities);

    /**
     * 查询我的志愿者申请
     * @param dto
     * @return
     */
    Integer countActivitiesListByUser(PostVolunteerActivitiesDto dto);

    /**
     * 查询我的志愿者申请
     * @param dto
     * @return
     */
    List<PostVolunteerActivitiesVo> getActivitiesListByUser(PostVolunteerActivitiesDto dto);


    /**
     * 查询志愿者活动列表  web端
     * @param dto
     * @return
     */
    List<PostVolunteerActivitiesVo> getWebActivitiesList(PostVolunteerActivitiesDto dto);


    /**
     * 查询志愿者活动总数  web端
     * @param dto
     * @return
     */
    Integer getWebActivitiesCount(PostVolunteerActivitiesDto dto);

    /**
     * 查询手机号是否已报名活动
     * @param param
     * @return
     */
    Integer countActivitiesJoined(Map<String, String> param);
    /**
     * 修改志愿者活动(假删除)
     */
    Integer  updateByPrimaryKeySelective(PostVolunteerActivities postVolunteerActivities);




}

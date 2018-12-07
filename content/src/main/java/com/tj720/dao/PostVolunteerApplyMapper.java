package com.tj720.dao;

import com.tj720.model.common.volunteer.PostVolunteerActivities;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 杜昶
 * @Date: 2018/11/8 17:33
 */
@Repository
public interface PostVolunteerApplyMapper {

    /**
     * 新增志愿者申请
     * @param postVolunteerApply
     * @return
     */
    Integer addApplyVolunteer(PostVolunteerApply postVolunteerApply);

    /**
     * 查询志愿者申请总数
     * @param id
     * @return
     */
    Integer getVolunteerApplyCount(String id);

    /**
     * 根据获活动id获取志愿者申请列表
     * @param param
     * @return
     */
    List<PostVolunteerApply> getVolunteerApplyList(Map<String, Object> param);


    /**
     * 根据活动id获取志愿者活动
     * @param  id
     * @return
     */
    PostVolunteerActivities  selectByPrimaryKey(String id);


}

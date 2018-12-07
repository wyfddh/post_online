package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostVolunteerActivitiesMapper;
import com.tj720.dao.PostVolunteerApplyMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.volunteer.PostVolunteerActivities;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesDto;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesVo;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import com.tj720.service.AttachmentService;
import com.tj720.service.PostVolunteerActivitiesService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杜昶
 * @Date: 2018/11/8 11:39
 */
@Service
public class PostVolunteerActivitiesServiceImpl implements PostVolunteerActivitiesService {
    //private String userId = "sysadmin";
    @Autowired
    private PostVolunteerActivitiesMapper postVolunteerActivitiesMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PostVolunteerApplyMapper postVolunteerApplyMapper;

    @Override
    public JsonResult getActivitiesList(PostVolunteerActivitiesDto dto, Page page) {
        Integer count = postVolunteerActivitiesMapper.getActivitiesCount(dto);
        page.setAllRow(count);
        dto.setStart(page.getStart());
        List<PostVolunteerActivitiesVo> list = postVolunteerActivitiesMapper.getActivitiesList(dto);
        if(list!=null && list.size()>0){
            for(PostVolunteerActivitiesVo  vo : list){
                System.out.println("id是   "+list.get(0).getId());
                vo.setApplyCount(postVolunteerApplyMapper.getVolunteerApplyCount(vo.getId()));
            }
        }
        return new JsonResult(1, list);
    }

    @Override
    public JsonResult addActivities(PostVolunteerActivities postVolunteerActivities) {
        String  userId = Tools.getUserId();
        if (StringUtils.isBlank(postVolunteerActivities.getActivitiesName())) {
            return new JsonResult(0, null, "120000002");
        }
        if (StringUtils.isBlank(postVolunteerActivities.getActivitiesPlace())) {
            return new JsonResult(0, null, "120000003");
        }
        if (null == postVolunteerActivities.getStartTime()) {
            return new JsonResult(0, null, "120000004");
        }
        if (null == postVolunteerActivities.getEndTime()) {
            return new JsonResult(0, null, "120000005");
        }
        if (null == postVolunteerActivities.getEndSignTime()) {
            return new JsonResult(0, null, "120000006");
        }
        if (null == postVolunteerActivities.getNeedNumber() || postVolunteerActivities.getNeedNumber() < 1) {
            return new JsonResult(0, null, "120000007");
        }
        if (StringUtils.isBlank(postVolunteerActivities.getCoverId())) {
            return new JsonResult(0, null, "120000008");
        }
        postVolunteerActivities.setId(IdUtils.getIncreaseIdByNanoTime());
        postVolunteerActivities.setCreateBy(userId);
        postVolunteerActivities.setUpdateBy(userId);
        Integer index = postVolunteerActivitiesMapper.addActivities(postVolunteerActivities);
        if (index > 0 ){
            return new JsonResult(1);
        } else {
            return new JsonResult(0, null, "120000001");
        }
    }

    @Override
    public JsonResult getActivitiesById(String id) {
        PostVolunteerActivitiesVo vo = postVolunteerActivitiesMapper.getActivitiesById(id);
        String coverId = vo.getCoverId();
        if (!StringUtils.isEmpty(coverId)) {
            Attachment attachment = attachmentService.getAttachmentsById(coverId);
            if (null != attachment) {
                vo.setCoverUrl(attachment.getAttPath());
            }
        }
        return new JsonResult(1, vo);

    }

    @Override
    public JsonResult updateActivities(PostVolunteerActivities postVolunteerActivities) {
        String  userId = Tools.getUserId();
        if (StringUtils.isBlank(postVolunteerActivities.getActivitiesName())) {
            return new JsonResult(0, null, "120000002");
        }
        if (StringUtils.isBlank(postVolunteerActivities.getActivitiesPlace())) {
            return new JsonResult(0, null, "120000003");
        }
        if (null == postVolunteerActivities.getStartTime()) {
            return new JsonResult(0, null, "120000004");
        }
        if (null == postVolunteerActivities.getEndTime()) {
            return new JsonResult(0, null, "120000005");
        }
        if (null == postVolunteerActivities.getEndSignTime()) {
            return new JsonResult(0, null, "120000006");
        }
        if (null == postVolunteerActivities.getNeedNumber() || postVolunteerActivities.getNeedNumber() < 1) {
            return new JsonResult(0, null, "120000007");
        }
        if (StringUtils.isBlank(postVolunteerActivities.getCoverId())) {
            return new JsonResult(0, null, "120000008");
        }
        postVolunteerActivities.setUpdateBy(userId);
        Integer index = postVolunteerActivitiesMapper.updateActivities(postVolunteerActivities);
        if (index > 0 ){
            return new JsonResult(1);
        } else {
            return new JsonResult(0, null, "120000009");
        }
    }

    @Override
    public JsonResult getVolunteerApplyList(String id, Page page) {
        Integer count = postVolunteerApplyMapper.getVolunteerApplyCount(id);
        page.setAllRow(count);
        Map<String, Object> param = new HashMap<String, Object>(3);
        param.put("start", page.getStart());
        param.put("size", page.getSize());
        param.put("id", id);
        List<PostVolunteerApply> applyList = postVolunteerApplyMapper.getVolunteerApplyList(param);
        return new JsonResult(1, applyList);
    }

    @Override
    public JsonResult deleteByPrimaryKey(String id) {
        JsonResult jsonResult = null;
        PostVolunteerActivities postVolunteerActivities = postVolunteerActivitiesMapper.getActivitiesById(id);
        if (postVolunteerActivities != null) {
            postVolunteerActivities.setIsdelete(1);
            postVolunteerActivities.setUpdateBy(Tools.getUserId());
            postVolunteerActivities.setUpdateDate(new Date());
            try {
                postVolunteerActivitiesMapper.updateByPrimaryKeySelective(postVolunteerActivities);
                jsonResult = new JsonResult(1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResult = new JsonResult("60000015");
            }
        }
        return jsonResult;
    }
}

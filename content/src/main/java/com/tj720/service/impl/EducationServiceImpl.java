package com.tj720.service.impl;/**
 * Created by MyPC on 2018/10/26.
 */

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.CollectMapper;
import com.tj720.dao.EducationMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.education.Education;
import com.tj720.model.common.education.EducationDto;
import com.tj720.service.AttachmentService;
import com.tj720.service.EducationService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: EducationServiceImpl
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/26
 * @Version: 1.0
 **/
@Service
public class EducationServiceImpl implements EducationService {




    private Education setTimeInfo(Education education, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String userId = Tools.getUserId();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        education.setUpdateTime(date);
        education.setUpdater(userId);
        // 0代表 没有创建的用户，录入创建人和创建时间
        if ("0".equals(type)) {
            education.setId(IdUtils.getIncreaseIdByNanoTime());
            education.setCreateTime(date);
            education.setCreator(userId);
            education.setIsdelete("0");
        }
        return education;
    }

    private List<EducationDto> getListUseEducations(List<Education> listEducation) {
        List<EducationDto> educationDtos = new ArrayList<>();
        for (int i = 0, j = listEducation.size(); i < j; i++) {
            EducationDto educationDto = new EducationDto();
            Education education = listEducation.get(i);
            educationDto.setId(education.getId());
            educationDto.setEducationId(education.getEducationId());
            educationDto.setTitle(education.getTitle());
            String picId = education.getPicId();
            educationDto.setPicId(picId);
            educationDto.setMsg(education.getMsg());
            educationDto.setMsgPicId(education.getMsgPicId());
            educationDto.setIsdelete(education.getIsdelete());
            educationDto.setSort(education.getSort());
            educationDto.setCreateTime(education.getCreateTime());
            educationDto.setUpdateTime(education.getUpdateTime());
            educationDto.setCreator(education.getCreator());
            educationDto.setUpdater(education.getUpdater());
            educationDto.setOther1(education.getOther1());
            educationDto.setOther2(education.getOther2());
            educationDto.setOther3(education.getOther3());
            List<Attachment> attList = getListUseAttachments(picId);
            educationDto.setAttachmentList(attList);
            educationDtos.add(educationDto);
        }
        return educationDtos;
    }

    private List<Attachment> getListUseAttachments(String pictureids) {
        Map map = new HashMap();
        String[] picIds = (pictureids + ",").split(",");
        List<Attachment> attList = new ArrayList<>();
        for (int ii = 0, jj = picIds.length; ii < jj; ii++) {
            Attachment attachments = new Attachment();
            map.put("attId", picIds[ii]);

            List<Attachment> listAttachment = collectMapper.getAttachmentById(map);
            Attachment attachment = listAttachment.get(0);
           /* if(!attachments.getAttPath().contains(config.getRootUrl())){

            }*/
            attachments.setAttPath(config.getRootUrl() + attachment.getAttPath());
            attachments.setAttId(attachment.getAttId());
            attList.add(attachments);
        }
        return attList;
    }

    @Autowired
    private   EducationMapper educationMapper;

    @Autowired
    private  CollectMapper collectMapper;

    @Autowired
    private  AttachmentService  attachmentService;

    @Autowired
    private  Config config;

    @Override
    public JsonResult addEducation(Education education) {
        try {
            education = setTimeInfo(education, "0");
            if(education!= null){
                education.setMsg(education.getMsg().trim());
            }
            int count = educationMapper.insertSelective(education);
            if (count > 0) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "41000013");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateEducation(Education education) {
        try {
            education = setTimeInfo(education, "1");
            int count = educationMapper.updateByPrimaryKeySelective(education);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "41000016");
            }
        } catch (Exception e) {
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateEducationType(Education education) {
        try {
            int count = educationMapper.updateByPrimaryKeySelective(education);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "41000017");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult getOneEducation(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            } else {
                Education education = educationMapper.selectByPrimaryKey(id);
                EducationDto educationDto = new EducationDto();
                educationDto.setId(education.getId());
                educationDto.setEducationId(education.getEducationId());
                educationDto.setTitle(education.getTitle());
                String picId = education.getPicId();
                educationDto.setPicId(picId);
                educationDto.setMsg(education.getMsg());
                educationDto.setMsgPicId(education.getMsgPicId());
                educationDto.setSort(education.getSort());
                educationDto.setCreateTime(education.getCreateTime());
                educationDto.setUpdateTime(education.getUpdateTime());
                educationDto.setCreator(education.getCreator());
                educationDto.setUpdater(education.getUpdater());
                educationDto.setOther1(education.getOther1());
                educationDto.setOther2(education.getOther2());
                educationDto.setOther3(education.getOther3());
                List<Attachment> attList = getListUseAttachments(picId);
                educationDto.setAttachmentList(attList);
                if (education.getId() != null) {
                    return new JsonResult(1, educationDto);
                } else {
                    return new JsonResult(0, "20000003");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "20000003");
        }

    }

    @Override
    public JSONObject getListEducation(String title, String startTime, String orderBy, Integer
            currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();

        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(title)) {
                map.put("title", title);
            }
            if (StringUtils.isNotBlank(startTime)) {
                map.put("startTime", startTime);
            }
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Integer count = educationMapper.selectCountByEducation(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<Education> listEducation = educationMapper.selectListByEducation(map);

            for(Education education : listEducation){
                //获取图片url
                education.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getListByIds(education.getPicId());
                for (Attachment attachment : picList) {
                    if(! attachment.getAttPath().contains(config.getRootUrl())){
                        attachment.setAttPath(config.getRootUrl()+attachment.getAttPath());
                    }
                }
                education.setPicList(picList);
            }


            List<EducationDto> listEducationDto = getListUseEducations(listEducation);
            String jsonString = JSON.toJSONString(listEducationDto);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);

        } catch (NumberFormatException e) {
            jsonObject.put("msg", "");
            jsonObject.put("count", null);
            jsonObject.put("data", null);
            jsonObject.put("error", e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public JsonResult updateEducationByIds(List<String> ids) {
        try {
            Integer size = ids.size();
            Integer i = educationMapper.updateEducationByIds(ids);
            if (size == i) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "41000014");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }

    }

    @Override
    public JsonResult deleteEducationById(String id) {
        try {
            Education education = new Education();
            education.setIsdelete("1");
            education.setId(id);
            int count = educationMapper.updateByPrimaryKeySelective(education);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "41000018");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }


}

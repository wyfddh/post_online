package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PostStampStoryMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.AttachmentService;
import com.tj720.service.PictureService;
import com.tj720.service.PostStampStoryService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: PostStampStoryServiceImpl
 * @Description: 邮政故事实现类
 * @Author: xa
 * @Date: 2018/10/29
 * @Version: 1.0
 **/
@Service
public class PostStampStoryServiceImpl implements PostStampStoryService{

    @Autowired
    private  PostStampStoryMapper  postStampStoryMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private Config config;


    @Override
    public JsonResult getStampStoryList(String storyType, String createTime,String orderBy,Page page) throws Exception{
        try {
            int count = postStampStoryMapper.count(storyType,createTime,page.getCurrentPage(), page.getSize(),orderBy);
            page.setAllRow(count);
            List<PostStampStory> stampStoryList = postStampStoryMapper.getStampStoryList(storyType, createTime, page.getStart(),
                    page.getSize(),orderBy);
            //查询分页数据

            for (PostStampStory postStampStory : stampStoryList){

                //获取图片url
                postStampStory.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getListByIds(postStampStory.getDatumIds());
                for (Attachment attachment : picList) {
                    if(! attachment.getAttPath().contains(config.getRootUrl())){
                        attachment.setAttPath(config.getRootUrl()+attachment.getAttPath());
                    }

                    if (attachment.getIsMain().equals("1")){
                        postStampStory.setMainPicUrl(attachment.getAttPath());
                        break;
                    }
                }
                postStampStory.setPicList(picList);
            }

            return new JsonResult(1,stampStoryList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"60000012");
        }

    }

    @Override
    public JsonResult selectByPrimaryKey(String id) throws Exception{
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostStampStory postStampStory = postStampStoryMapper.selectByPrimaryKey(id);
            if(!StringUtils.isBlank(postStampStory.getDatumIds())){
                //获取图片url
                postStampStory.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getListByIds(postStampStory.getDatumIds());
                for (Attachment attachment : picList) {
                    if(! attachment.getAttPath().contains(config.getRootUrl())){
                        attachment.setAttPath(config.getRootUrl()+attachment.getAttPath());
                    }

                    if (attachment.getIsMain().equals("1")){
                        postStampStory.setMainPicUrl(attachment.getAttPath());
                        break;
                    }
                }
                postStampStory.setPicList(picList);
            }

            return new JsonResult(1,postStampStory);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult insertSelective(PostStampStory record,String picids){
        JsonResult jsonResult = null;
        //设置主图
        Attachment attachment = null;
        String  userId = Tools.getUserId();
        try {
            pictureService.setMain(picids, picids, null);
            record.setDatumIds(picids);
            record.setUpdater(userId );
            record.setUpdateTime(new Date());
            //新增
            if (StringUtils.isBlank(record.getId())) {
                record.setId(IdUtils.getIncreaseIdByNanoTime());
                record.setDataState("1");
                record.setCreator(userId );
                record.setCreateTime(new Date());

                postStampStoryMapper.insertSelective(record);

            }
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("60000013");
        }

        return jsonResult;
    }

    @Override
    public JsonResult updateByPrimaryKeySelective(PostStampStory record,String picids){
        JsonResult jsonResult = null;
        //设置主图
        Attachment attachment = null;

        try {
            pictureService.setMain(picids, picids, null);
            record.setDatumIds(picids);
            record.setUpdater(Tools.getUserId());
            record.setUpdateTime(new Date());

            postStampStoryMapper.updateByPrimaryKeySelective(record);

            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("60000014");
        }
        return jsonResult;
    }

    @Override
    public JsonResult deleteByPrimaryKey(String id) {
        JsonResult jsonResult = null;
        PostStampStory postStampStory = postStampStoryMapper.selectByPrimaryKey(id);
        if (postStampStory != null) {
            postStampStory.setDataState("0");
            postStampStory.setUpdateTime(new Date());
            postStampStory.setUpdater(Tools.getUserId());

            try {
                postStampStoryMapper.updateByPrimaryKeySelective(postStampStory);
                jsonResult = new JsonResult(1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResult = new JsonResult("60000015");
            }
        }
        return jsonResult;
    }

    @Override
    public JsonResult updateStampStoryByIds(List<String> ids){
        try {
            Integer count=  postStampStoryMapper.updateStampStoryByIds(ids);
            if (count >0){
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "60000016");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public  List<PostStampStory>  getStoryTypeOptions(){
        return postStampStoryMapper.getStoryTypeOptions();
    }
}

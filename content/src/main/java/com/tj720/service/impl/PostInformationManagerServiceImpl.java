package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PostInformationManageMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.service.AttachmentService;
import com.tj720.service.ImageUtiilService;
import com.tj720.service.PictureService;
import com.tj720.service.PostInformationManagerService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: PostInformationManagerServiceImpl
 * @Description: 资讯管理业务实现类
 * @Author: xa
 * @Date: 2018/10/30
 * @Version: 1.0
 **/
@Service
public class PostInformationManagerServiceImpl implements PostInformationManagerService{

    @Autowired
    private PostInformationManageMapper  postInformationManageMapper;

    @Autowired
    private AttachmentService  attachmentService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private Config config;

    @Autowired
    ImageUtiilService imageUtiilService;


    @Override
    public JsonResult getInformationList(String informationType, String createTime,String orderBy, Page page) throws Exception{
        try {
            int count = postInformationManageMapper.count(informationType,createTime,orderBy,page.getCurrentPage(), page.getSize());
            page.setAllRow(count);
            List<PostInformationManage> informationList = postInformationManageMapper.getInformationList(informationType, createTime, orderBy, page.getStart(),
                    page.getSize());
            //查询分页数据

            for (PostInformationManage postInformationManage : informationList){

                //获取图片url
                postInformationManage.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(postInformationManage.getDatumIds());
                for (Attachment attachment : picList) {
                    if ("1".equals(attachment.getIsMain())){
                        postInformationManage.setMainPicUrl(attachment.getAttPath());
                        break;
                    }
                }
                postInformationManage.setPicList(picList);
            }

            return new JsonResult(1,informationList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"70000012");
        }
    }

    @Override
    public JsonResult selectByPrimaryKey(String id) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"000020");
            }
            PostInformationManage postInformationManage = postInformationManageMapper.selectByPrimaryKey(id);
            if(!StringUtils.isBlank(postInformationManage.getDatumIds())){
                //获取图片url
                postInformationManage.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(postInformationManage.getDatumIds());
                for (Attachment attachment : picList) {
                    if ("1".equals(attachment.getIsMain())){
                        postInformationManage.setMainPicUrl(attachment.getAttPath());
                        break;
                    }
                }
                postInformationManage.setPicList(picList);
            }
            postInformationManage.setInformationContent(imageUtiilService.getContent(postInformationManage.getInformationContent(),"1"));
            return new JsonResult(1,postInformationManage);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    @Transactional
    public JsonResult insertSelective(PostInformationManage record,String picids){
        JsonResult jsonResult = null;
        //设置主图
        Attachment attachment = null;
        String userId = Tools.getUserId();
        try {
            pictureService.setMain(picids, picids, null);
            record.setDatumIds(picids);
            record.setUpdater(userId);
            record.setUpdateTime(new Date());
            //新增
            if (StringUtils.isBlank(record.getId())) {
                record.setId(IdUtils.getIncreaseIdByNanoTime());
                record.setDataState("1");
                record.setCreator(userId);
                record.setCreateTime(new Date());
                record.setInformationContent(imageUtiilService.backContent(record.getInformationContent()));
                postInformationManageMapper.insertSelective(record);

            }
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("70000013");
        }

        return jsonResult;
    }

    @Override
    @Transactional
    public JsonResult updateByPrimaryKeySelective(PostInformationManage record, String picids) {
        JsonResult jsonResult = null;
        //设置主图
        Attachment attachment = null;

        try {
            pictureService.setMain(picids, picids, null);
            record.setDatumIds(picids);
            record.setUpdater(Tools.getUserId());
            record.setUpdateTime(new Date());

            postInformationManageMapper.updateByPrimaryKeySelective(record);

            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("60000014");
        }
        return jsonResult;
    }

    @Override
    public JsonResult deleteByPrimaryKey(String id){
        JsonResult jsonResult = null;
        PostInformationManage postInformationManage = postInformationManageMapper.selectByPrimaryKey(id);
        if (postInformationManage != null) {
            postInformationManage.setDataState("0");
            postInformationManage.setUpdateTime(new Date());
            postInformationManage.setUpdater(Tools.getUserId());

            try {
                postInformationManageMapper.updateByPrimaryKeySelective(postInformationManage);
                jsonResult = new JsonResult(1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResult = new JsonResult("70000015");
            }
        }
        return jsonResult;
    }

    @Override
    public JsonResult updateInfomationByIds(List<String> ids) {
        try {
            Integer count= postInformationManageMapper.updateInformationyByIds(ids);
            if (count >0){
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "70000016");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }
}

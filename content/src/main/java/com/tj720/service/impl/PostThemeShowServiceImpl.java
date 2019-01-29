package com.tj720.service.impl;


import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PostCollectionTypeMapper;
import com.tj720.dao.PostThemeShowMapper;
import com.tj720.dao.SysDictMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collectiontype.PostCollectionType;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.AttachmentService;
import com.tj720.service.PictureService;
import com.tj720.service.PostThemeShowService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PostThemeShowServiceImpl implements PostThemeShowService{

    @Autowired
    private PostThemeShowMapper postThemeShowMapper;

    @Autowired
    private PostCollectionTypeMapper  postCollectionTypeMapper;

    @Autowired
    private  AttachmentService   attachmentService;


    @Autowired
    private PictureService pictureService;

    @Autowired
    private Config config;

    @Autowired
    SysDictMapper sysDictMapper;

    @Override
    public JsonResult deleteByPrimaryKey(String id) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostThemeShow info = new PostThemeShow();
            info.setId(id);
            info.setDataState("0");
            info.setUpdateBy(Tools.getUserId());
            info.setUpdateDate(new Date());
            int count = postThemeShowMapper.updateByPrimaryKeySelective(info);
            if (count > 0) {
                return new JsonResult(1,"删除成功");
            } else {
                return new JsonResult(0, null, "20000009");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    @Transactional
    public JsonResult batchRemove(String[] ids) throws Exception {
        try {
            if(ids.length<0){
                return new JsonResult(0,"参数错误");
            }
            int count = postThemeShowMapper.batchRemove(ids);
            if (count>0){
                return new JsonResult(1,null);
            }
            else{
                return new JsonResult(0,null,"200006");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    @Transactional
    public JsonResult updateThemebByIds(List<String> ids) {
        try {
            Integer count= postThemeShowMapper.updateThemebByIds(ids);
            if (count >0){
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "30000014");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult themeshowList(String themeName,String themeSource,String orderBy, Page page) throws Exception {
        try {
            int count = postThemeShowMapper.count(themeName,themeSource,orderBy,page.getCurrentPage(), page.getSize());
            page.setAllRow(count);
            List<PostThemeShow> postThemeShows = postThemeShowMapper.themeshowList(themeName,themeSource,orderBy,page.getStart(),
                    page.getSize());

            //查询分页数据

            for (PostThemeShow  postThemeShow  : postThemeShows){
                List<CollectDto> collectList = postCollectionTypeMapper.selectCollectByThemeId(postThemeShow.getId());
                postThemeShow.setCollectionAmount(String.valueOf(collectList.size()));
                /*if (postThemeShow.getPageRecommend().equals("1")) {
                    postThemeShow.setPageRecommendCheck("checked");
                }*/
                //获取图片url
                postThemeShow.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
                for (Attachment attachment : picList) {
//                    attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
                    if ("1".equals(attachment.getIsMain())){
                        postThemeShow.setMainPicUrl(attachment.getAttPath());
                        break;
                    }
                }
                postThemeShow.setPicList(picList);
            }

            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
           e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult selectByPrimaryKey(String id) throws Exception{
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostThemeShow postThemeShow = postThemeShowMapper.selectByPrimaryKey(id);
            if(!StringUtils.isBlank(postThemeShow.getDatumIds())){
                //获取图片url
                postThemeShow.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
                for (Attachment attachment : picList) {
//                    attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
                    if ("1".equals(attachment.getIsMain())){
                        postThemeShow.setMainPicUrl(attachment.getAttPath());
                        break;
                    }
                }
                postThemeShow.setPicList(picList);
            }
            if (null != postThemeShow) {
                List<CollectDto> collectList = postCollectionTypeMapper.selectCollectByThemeId(postThemeShow.getId());
                List<CollectDto> res = new ArrayList<CollectDto>();
                for (int i = 0, length = collectList.size(); i < length; i++) {
                    String type = "";
                    String sonType = "";
                    CollectDto dto = collectList.get(i);
                    String sonTypeId = dto.getSonTypeId();
                    if (!org.apache.commons.lang.StringUtils.isBlank(sonTypeId)) {
                        sonType = "-" + sysDictMapper.getDictById(sonTypeId).getDictName();
                    }
                    String typeId = dto.getTypeId();
                    type = sysDictMapper.getDictById(typeId).getDictName();
                    dto.setTypeFullName(type + sonType);
                    res.add(dto);
                }
                postThemeShow.setCollectDtoList(res);
            }
            return new JsonResult(1,postThemeShow);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }

    }

    @Override
    public JsonResult getSourceOptions() throws Exception{
        try {
            List<PostThemeShow> sourceOptions = postThemeShowMapper.getSourceOptions();
            return new JsonResult(1,sourceOptions);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    @Transactional
    public JsonResult  insertSelective(PostThemeShow record,String picids){
        String  userId = Tools.getUserId();
        try {
            picids = record.getDatumIds();
            String objectListStr = record.getObjectListStr();
            JSONArray jsonArray= JSONArray.fromObject(objectListStr);
            List<PostCollectionType> collectionListArray =(List<PostCollectionType>)JSONArray.toCollection(jsonArray,PostCollectionType.class);

                //设置图片
                pictureService.setMain(picids, picids, null);
                record.setDatumIds(picids);
                record.setId(IdUtils.getIncreaseIdByNanoTime());
                record.setCollectionAmount(Integer.toString(collectionListArray.size()));
                record.setThemeSource("1");
                record.setDataState("1");
                record.setCreateBy(userId);
                record.setCreateDate(new Date());
                record.setUpdateBy(userId);
                record.setUpdateDate(new Date());
                postThemeShowMapper.insertSelective(record);

            //插入关联藏品数据
            //postCollectionTypeMapper.deleteByThemeId(record.getId());
            for(PostCollectionType collInfo:collectionListArray){
                collInfo.setThemeShowId(record.getId());
                collInfo.setStatus("1");
                collInfo.setCreateBy(userId);
                collInfo.setCreateDate(new Date());
                collInfo.setUpdateBy(Tools.getUserId());
                collInfo.setUpdateDate(new Date());
                collInfo.setId(IdUtils.getIncreaseIdByNanoTime());
            }
            postCollectionTypeMapper.batchSave(collectionListArray);
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
        return new JsonResult(1,record);
    }

    @Override
    @Transactional
    public JsonResult  updateByPrimaryKeySelective(PostThemeShow record,String picids){
        String  userId = Tools.getUserId();
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }

            String objectListStr = record.getObjectListStr();
            JSONArray jsonArray= JSONArray.fromObject(objectListStr);
            List<PostCollectionType> collectionListArray =(List<PostCollectionType>)JSONArray.toCollection(jsonArray,PostCollectionType.class);


            if(StringUtils.isNotBlank(record.getId())){
                pictureService.setMain(picids, picids, null);
                record.setDatumIds(picids);
                record.setUpdateBy(userId);
                record.setUpdateDate(new Date());
                int count = postThemeShowMapper.updateByPrimaryKeySelective(record);


                //插入关联藏品数据
                postCollectionTypeMapper.deleteByThemeId(record.getId());
                if(collectionListArray!=null && collectionListArray.size()>0){
                    for(PostCollectionType collInfo:collectionListArray){
                        collInfo.setThemeShowId(record.getId());
                        collInfo.setStatus("1");
                        collInfo.setCreateBy(userId);
                        collInfo.setCreateDate(new Date());
                        collInfo.setUpdateBy(userId);
                        collInfo.setUpdateDate(new Date());
                        collInfo.setId(IdUtils.getIncreaseIdByNanoTime());
                    }
                    postCollectionTypeMapper.batchSave(collectionListArray);
                }



                if (count >0){
                    return new JsonResult(1,null);
                }
                else{
                    return new JsonResult(0,null,"200006");
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"修改主题展数据失败");
        }
        return  null;
    }

    @Override
    public void updateByPrimaryKeySelective(PostThemeShow record) {
        postThemeShowMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer CountSytj(){
        return  postThemeShowMapper.CountSytj();
    }

    @Override
    public Integer CountJczj(){
        return  postThemeShowMapper.CountJczj();
    }



}

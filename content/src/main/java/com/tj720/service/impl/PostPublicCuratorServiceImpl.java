package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PostCollectionTypeMapper;
import com.tj720.dao.PostPublicCuratorMapper;
import com.tj720.dao.PostThemeShowMapper;
import com.tj720.dao.SysDictMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collectiontype.PostCollectionType;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.AttachmentService;
import com.tj720.service.PictureService;
import com.tj720.service.PostPublicCuratorService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import javax.tools.Tool;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: PosPublicCuratorServiceImpl
 * @Description: 公众策展实现类
 * @Author: xa
 * @Date:
 * @Version: 1.0
 **/
@Service
public class PostPublicCuratorServiceImpl implements PostPublicCuratorService{


    @Autowired
    private PostPublicCuratorMapper postPublicCuratorMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PostCollectionTypeMapper postCollectionTypeMapper;

    @Autowired
    private PostThemeShowMapper postThemeShowMapper;

    @Autowired
    private Config config;

    @Autowired
    SysDictMapper sysDictMapper;

    @Override
    public JsonResult getCuratorList(String themeName, String processState, String processResult, Page page) throws Exception{
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isNotBlank(userId)) {

                Integer count = postPublicCuratorMapper.count(themeName, processState, processResult, page.getCurrentPage(), page.getSize());
                page.setAllRow(count);
                List<PostThemeShow> curatorList = postPublicCuratorMapper.getCuratorList(themeName, processState, processResult, page.getStart(),
                        page.getSize(),userId);
                //查询分页数据
                for (PostThemeShow postThemeShow : curatorList){
                    List<CollectDto> collectList = postCollectionTypeMapper.selectCollectByThemeId(postThemeShow.getId());
                    postThemeShow.setCollectionAmount(String.valueOf(collectList.size()));
                    //获取图片url
                    postThemeShow.setMainPicUrl("");
                    List<Attachment> picList = new ArrayList<Attachment>();
                    picList = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
                    for (Attachment attachment : picList) {
                        if ("1".equals(attachment.getIsMain())){
                            postThemeShow.setMainPicUrl(attachment.getAttPath());
                            break;
                        }
                    }
                    postThemeShow.setPicList(picList);
                }
                return new JsonResult(1,curatorList);
            }  else {
                return new JsonResult(0,null,"111116");
            }


        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"90000012");
        }
    }

    @Override
    public JsonResult selectByPrimaryKey(String id) throws Exception {
//        PostThemeShow postPublicCurator  =  null;
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostThemeShow postPublicCurator = postPublicCuratorMapper.selectByPrimaryKey(id);


            if (null != postPublicCurator) {

                if(!StringUtils.isBlank(postPublicCurator.getDatumIds())){
                    //获取图片url
                    postPublicCurator.setMainPicUrl("");
                    List<Attachment> picList = new ArrayList<Attachment>();
                    picList = attachmentService.getFileTransPathList(postPublicCurator.getDatumIds());
                    for (Attachment attachment : picList) {
//                        if(!attachment.getAttPath().contains(CheckConfig.getRootUrl())){
//                            attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
//                        }
                        if ("1".equals(attachment.getIsMain())){
                            postPublicCurator.setMainPicUrl(attachment.getAttPath());
                            break;
                        }
                    }
                    postPublicCurator.setPicList(picList);
                }

                List<CollectDto> collectList = postCollectionTypeMapper.selectCollectByThemeId(postPublicCurator.getId());
                List<CollectDto> res = new ArrayList<CollectDto>();
                for (int i = 0, length = collectList.size(); i < length; i++) {
                    String type = "";
                    String sonType = "";
                    CollectDto dto = collectList.get(i);
                    String sonTypeId = dto.getSonTypeId();
                    if (!StringUtils.isBlank(sonTypeId)) {
                        sonType = "-" + sysDictMapper.getDictById(sonTypeId).getDictName();
                    }
                    String typeId = dto.getTypeId();
                    type = sysDictMapper.getDictById(typeId).getDictName();
                    dto.setTypeFullName(type + sonType);
                    res.add(dto);
                }
                postPublicCurator.setCollectDtoList(res);
            }
            return new JsonResult(1,postPublicCurator);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

//    @Override
//    public JsonResult insertSelective(PostPublicCurator record,String picids) {
//        JsonResult jsonResult = null;
//        //设置主图
//        Attachment attachment = null;
//
//        try {
//            pictureService.setMain(picids, picids, null);
//            record.setDatumIds(picids);
//            record.setProcessState("0");
//            record.setProcessResult("-1");
//            record.setUpdater("sysadmin");
//            record.setUpdateTime(new Date());
//            //新增
//            if (StringUtils.isBlank(record.getId())) {
//                record.setId(IdUtils.getIncreaseIdByNanoTime());
//                record.setDataState("1");
//                record.setCreator("sysadmin");
//                record.setCreateTime(new Date());
//
//                postPublicCuratorMapper.insertSelective(record);
//
//            }
//            jsonResult = new JsonResult(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new JsonResult("90000013");
//        }
//
//        return jsonResult;
//    }

    @Override
    @Transactional
    public JsonResult updateByPrimaryKeySelective(PostThemeShow record,String picids) {
        JsonResult  jsonResult = null;
        String objectListStr = record.getObjectListStr();
        JSONArray jsonArray= JSONArray.fromObject(objectListStr);
        List<PostCollectionType> collectionListArray =(List<PostCollectionType>)JSONArray.toCollection(jsonArray,PostCollectionType.class);
        String  userId = Tools.getUserId();
        try {
            if(StringUtils.isNotBlank(record.getId())){
                pictureService.setMain(record.getDatumIds(), record.getDatumIds(), null);
                if (StringUtils.isNotBlank(record.getProcessResult())) {
                    record.setProcessState("1");
                }
                record.setCollectionAmount(Integer.toString(collectionListArray.size()));
                record.setUpdateBy(userId);
                record.setUpdateDate(new Date());

                postThemeShowMapper.updateByPrimaryKeySelective(record);
                //插入关联藏品数据
                postCollectionTypeMapper.deleteByThemeId(record.getId());
                for(PostCollectionType collInfo:collectionListArray){
                    collInfo.setThemeShowId(record.getId());   //这里是公共策展id
                    collInfo.setStatus("1");
                    collInfo.setCreateBy(userId);
                    collInfo.setCreateDate(new Date());
                    collInfo.setUpdateBy(userId);
                    collInfo.setUpdateDate(new Date());
                    collInfo.setId(IdUtils.getIncreaseIdByNanoTime());
                }
            postCollectionTypeMapper.batchSave(collectionListArray);
                jsonResult = new JsonResult(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("90000014");
        }
        return jsonResult;
    }

    @Override
    public JsonResult deleteByPrimaryKey(String id){
        JsonResult jsonResult = null;
        String userId = Tools.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            PostThemeShow postPublicCurator = postPublicCuratorMapper.selectByPrimaryKey(id);
            if (postPublicCurator != null) {
                postPublicCurator.setDataState("0");
                postPublicCurator.setUpdateDate(new Date());
                postPublicCurator.setUpdateBy(Tools.getUserId());

                try {
                    postThemeShowMapper.updateByPrimaryKeySelective(postPublicCurator);
                    jsonResult = new JsonResult(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonResult = new JsonResult("90000015");
                }
            }
        } else {
            jsonResult = new JsonResult("111116");
        }

        return jsonResult;
    }

    @Override
    public JsonResult updateHomeByIds(List<String> ids) {
        try {
            Integer count=  postPublicCuratorMapper.updateCuratorByIds(ids);
            if (count >0){
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "90000016");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "系统异常");
        }
    }
    @Override
    public JsonResult approvalInfo(String id,String approval,String remarks){
        try {
            PostThemeShow record = new PostThemeShow();
            record.setId(id);
            record.setProcessState("1");
            record.setProcessResult(approval);
            record.setRemarks(remarks);
            postPublicCuratorMapper.updateByPrimaryKeySelective(record);
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0, null, "系统异常");
        }

    }

}

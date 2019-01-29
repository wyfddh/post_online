package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.CollectMapper;
import com.tj720.dao.PubUserMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.pubuser.PubUserDto;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.AttachmentService;
import com.tj720.service.ImageUtiilService;
import com.tj720.service.PictureService;
import com.tj720.service.PubUserService;
import com.tj720.utils.DateFormartUtil;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 余超
 * @Date: 2018/10/8 11:31
 */
@Service
public class PubUserServiceImpl implements PubUserService {
    @Autowired
    ImageUtiilService imageUtiilService;

    @Autowired
    PubUserMapper pubUserMapper;

    @Autowired
    private AttachmentService attachmentService;


    @Autowired
    private  CollectMapper collectMapper;


    @Autowired
    private PictureService pictureService;

    @Autowired
    private Config config;

    private PubUser setTimeInfo(PubUser pubUser, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pubUser.setUpdateTime(date);
        pubUser.setUpdater(Tools.getUserId());
        // 0代表 没有创建的用户，录入创建人和创建时间
        if ("0".equals(type)) {
            pubUser.setCreateTime(date);
            pubUser.setCreator(Tools.getUserId());
        }
        return pubUser;
    }


    @Override
    public JsonResult addPubUser(PubUser pubUser) {
        try {
            pubUser = setTimeInfo(pubUser, "0");
            int count = pubUserMapper.insertSelective(pubUser);
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
    public JsonResult updatePubUser(PubUser pubUser) {
        try {
            pubUser = setTimeInfo(pubUser, "1");
            pubUser.setUpdater(Tools.getUserId());
            pubUser.setUpdateTime(new Date());
            int count = pubUserMapper.updateByPrimaryKeySelective(pubUser);
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
    public JsonResult modifyUserType(String userStatus, String id) {
        JsonResult jsonResult = null;
        try {
            PubUser pubUser = pubUserMapper.selectByPrimaryKey(id);
            //所属博物馆上线
            if (pubUser !=null){
                pubUser.setStatus(Integer.valueOf(userStatus));
                pubUser.setUpdater(Tools.getUserId());
                pubUser.setUpdateTime(new Date());
                pubUserMapper.updateByPrimaryKeySelective(pubUser);
                jsonResult = new JsonResult(1);
            } else {
                return new JsonResult("41000016");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("系统异常");
        }

        return jsonResult;
    }



    @Override
    public JsonResult getOnePubUser(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            } else {
                PubUser pubUser = pubUserMapper.selectByPrimaryKey(id);
                //查询时清除密码
                pubUser.setPassword("");
                pubUser.setSurePassword("");
                pubUser.setBirthdayStr(DateFormartUtil.stampToDateSimple(pubUser.getBirthday()));

                //获取图片url
                pubUser.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(pubUser.getAvatarurl());
                if(null != picList && picList.size()>0){
                    for (Attachment attachment : picList) {
//                        if(! attachment.getAttPath().contains(CheckConfig.getRootUrl())){
//                            attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
//                        }
                        if (attachment.getIsMain().equals("1")){
                            pubUser.setMainPicUrl(attachment.getAttPath());
                            break;
                        }
                    }
                    pubUser.setPicList(picList);
                }else{
                    pubUser.setPicList(null);
                }


                if (pubUser.getId() != null) {
                    return new JsonResult(1, pubUser);
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
    public JSONObject getListPubUser(String name, String phone, String createTime, String orderBy, Integer
            currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();

        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(name)) {
                map.put("name", name);
            }
            if (StringUtils.isNotBlank(phone)) {
                map.put("phone", phone);
            }
            if (StringUtils.isNotBlank(createTime)) {
                map.put("createTime", createTime);
            }
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Integer count = pubUserMapper.selectCountByPubUser(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<PubUser> listPubUser = pubUserMapper.selectListByPubUser(map);

            for (PubUser pubUser  : listPubUser){
                //查询时清除密码
                pubUser.setPassword("");
                pubUser.setSurePassword("");
                //获取图片url
                pubUser.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(pubUser.getAvatarurl());
                if(null != picList && picList.size()>0){
                    for (Attachment attachment : picList) {
//                        if(! attachment.getAttPath().contains(CheckConfig.getRootUrl())){
//                            attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
//                        }
                        if (attachment.getIsMain().equals("1")){
                            pubUser.setMainPicUrl(attachment.getAttPath());
                            break;
                        }
                    }
                    pubUser.setPicList(picList);
                }else{
                    pubUser.setPicList(null);
                }

            }


            String jsonString = JSON.toJSONString(listPubUser);
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
    @Transactional
    public JsonResult updatePubUserByIds(List<String> ids) {
        try {
            Integer size = ids.size();
            Integer i = pubUserMapper.updatePubUserByIds(ids);
            if (size.equals(i)) {
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
    public JsonResult deletePubUserById(String id) {
        try {
            PubUser pubUser = new PubUser();
            pubUser.setIsdelete(1);
            pubUser.setId(id);
            int count = pubUserMapper.updateByPrimaryKeySelective(pubUser);
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





    @Override
    public JsonResult getCollectsByUserId(String userId,String type,Integer currentPage, Integer size){
        Map map = new HashMap();
        try {
            if(StringUtils.isBlank(userId)){
                return new JsonResult(0,"参数错误");
            }
            Page page = new Page();
            if (StringUtils.isNotBlank(userId)) {
                map.put("userId", userId);
            }
            if (StringUtils.isNotBlank(type)) {
                map.put("type", type);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Integer count = pubUserMapper.getCollectsCountByUserId(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            //根据用户id 查询相关的藏品list
            List<Attachment> picList1 = new ArrayList<Attachment>();
            List<Collect> collectList = new ArrayList<Collect>();

            collectList = pubUserMapper.getCollectsByUserId(map);
            for(Collect  collect: collectList){
                if(StringUtils.isNotBlank(collect.getPictureids())){
                    picList1 = attachmentService.getFileTransPathList(collect.getPictureids());
                    if(picList1.size()>0 && StringUtils.isNotBlank(picList1.get(0).getAttPath())){
                        //if(! collect.getMainPicUrl().contains(CheckConfig.getRootUrl())){
                        collect.setMainPicUrl(picList1.get(0).getAttPath());
                        //}
                    }
                }


               /* for (Attachment attachment : picList1) {
                    if(! attachment.getAttPath().contains(CheckConfig.getRootUrl())){
                        attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
                    }
                }
                collect.setPicList(picList1);*/
            }
            return new JsonResult(1,collectList,page);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getThemeByUserId(String userId,String type,Integer currentPage, Integer size){
        Map map = new HashMap();
        try {
            if(StringUtils.isBlank(userId)){
                return new JsonResult(0,"参数错误");
            }
            Page page = new Page();
            if (StringUtils.isNotBlank(userId)) {
                map.put("userId", userId);
            }
            if (StringUtils.isNotBlank(type)) {
                map.put("type", type);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Integer count = pubUserMapper.getThemeCountByUserId(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            //根据用户id 查询相关的主题list
            List<Attachment> picList1 = new ArrayList<Attachment>();
            List<PostThemeShow> themeList = new ArrayList<PostThemeShow>();
            themeList = pubUserMapper.getThemeByUserId(map);
            for(PostThemeShow  postThemeShow: themeList){
                picList1 = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
                if(picList1.size()>0 && StringUtils.isNotBlank(picList1.get(0).getAttPath())){
                    //if(! postThemeShow.getMainPicUrl().contains(CheckConfig.getRootUrl())){
                      postThemeShow.setMainPicUrl(picList1.get(0).getAttPath());
                    //}
                }
               /* for (Attachment attachment : picList1) {
                    if(! attachment.getAttPath().contains(CheckConfig.getRootUrl())){
                        attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
                    }
                }
                postThemeShow.setPicList(picList1);*/
            }


            return new JsonResult(1,themeList,page);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }



    @Override
    public JsonResult getUserDtoByUid(String userId){
        Map map = new HashMap();
        try {
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "参数错误");
            }
            Page page = new Page();
            if (StringUtils.isNotBlank(userId)) {
                map.put("userId", userId);
            }

            List<Attachment> picList1 = new ArrayList<Attachment>();
            List<PubUserDto> userDtoList = pubUserMapper.getUserDtoByUid(userId);
            for(PubUserDto  pubUserDto: userDtoList){
//                pubUserDto.setAvatarurl(attachmentService.getFileTransPathByPath(pubUserDto.getAvatarurl()));
                picList1 = attachmentService.getFileTransPathList(pubUserDto.getAvatarurl());
                //if(picList1.size()>0 && StringUtils.isNotBlank(picList1.get(0).getAttPath())){
                if(null!=picList1 &&  picList1.size()>0){
                    pubUserDto.setAvatarurl(picList1.get(0).getAttPath());
//                    pubUserDto.setAvatarurl(attachmentService.getFileTransPathByPath(pubUserDto.getAvatarurl()));
                    pubUserDto.setPicList(picList1);
                }else{
                    pubUserDto.setPicList(null);
                }

            }

            return new JsonResult(1,userDtoList,page);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }

    }

    @Override
    public List<PubUser> getListByPhone(String phone) {

        return pubUserMapper.getListByPhone(phone);
    }


}

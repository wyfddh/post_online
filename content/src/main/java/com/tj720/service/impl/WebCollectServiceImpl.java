package com.tj720.service.impl;/**
 * Created by MyPC on 2018/10/24.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.*;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collecthome.PostCollectHome;
import com.tj720.model.common.education.Education;
import com.tj720.model.common.education.EducationDto;
import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.model.common.postwebcollect.PostWebCollect;
import com.tj720.model.common.research.Research;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.model.common.system.user.MipUser;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.*;
import com.tj720.utils.DateFormartUtil;
import com.tj720.utils.MyString;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import com.tj720.utils.common.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @ClassName: CollectServiceImpl
 * @Description: TODO
 * @Author:  xa
 * @Date: 2018/11/8
 * @Version: 1.0
 **/
@Service
public class WebCollectServiceImpl implements PostWebCollectService{


    @Autowired
    private PostWebCollectMapper  postWebCollectMapper;



    @Override
    public JsonResult insertSelective(String userId,String cid,String type,String hasCollected){
        PostWebCollect postWebCollect = new PostWebCollect();
        try {
            if(userId == null){
                return new JsonResult(0, "参数有误");
            }
            if(StringUtils.isNotBlank(cid) && StringUtils.isNotBlank(type)){
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", userId);
                map.put("cid", cid);
                postWebCollect = postWebCollectMapper.getWebCollect(map);
                if ("1".equals(hasCollected)){
                    if (postWebCollect == null){
                        postWebCollect = new PostWebCollect();
                        postWebCollect.setId(IdUtils.getIncreaseIdByNanoTime());
                        postWebCollect.setUserId(userId);
                        postWebCollect.setType(type);
                        postWebCollect.setCreatetime(new Date());
                        postWebCollect.setStatus((byte) 1);
                        postWebCollect.setHasCollected(String.valueOf(1));
                        try{
                            postWebCollectMapper.insertSelective(postWebCollect);
                            return new JsonResult(1, "添加收藏成功");
                        }catch(Exception e) {
                            e.printStackTrace();
                            return new JsonResult(3, "系统异常！");
                        }
                    }else{
                        if (postWebCollect.getStatus() == 1) {
                            return new JsonResult(4, "重复收藏！");
                        } else if (postWebCollect.getStatus() == 0) {
                            postWebCollect.setStatus((byte) 1);
                            postWebCollect.setCreatetime(new Date());
                            postWebCollect.setHasCollected(String.valueOf(1));
                            try {
                                postWebCollectMapper.updateByPrimaryKeySelective(postWebCollect);
                                return new JsonResult(1);
                            } catch (Exception e) {
                                return new JsonResult(3, "系统异常！");
                            }
                        } else {
                            return new JsonResult(3, "系统异常！");
                        }
                    }
                }else{
                    if(postWebCollect == null){
                        return  new JsonResult(0, "数据获取异常");
                    }
                    postWebCollect.setUserId(userId);
                    postWebCollect.setCreatetime(new Date());
                    postWebCollect.setStatus((byte) 0);
                    postWebCollect.setHasCollected(String.valueOf(0));
                    postWebCollectMapper.updateByPrimaryKeySelective(postWebCollect);
                    return new JsonResult(1, "取消收藏成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "添加收藏失败");
        }
        return null;
    }

    /**
     * 添加收藏/取消收藏
     *
     * @param userId       用户id
     * @param cid          藏品id
     * @param type         类型
     * @param hasCollected 状态
     * @return
     */
    @Override
    public JsonResult updateCollectionStatus(String userId, String cid, String type, String hasCollected) {
        try {
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(cid)|| StringUtils.isEmpty(type)|| StringUtils.isEmpty(hasCollected))
            {
                return new JsonResult("140000004");
            }
            HashMap<String,Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("cid", cid);
            map.put("type", type);
            map.put("status", 1);
            PostWebCollect postWebCollect =  postWebCollectMapper.getWebCollect(map);
            //添加收藏
            if (hasCollected.equals("1")){
                if (null != postWebCollect){
                    return new JsonResult("重复收藏了！");
                }
                PostWebCollect collect = new PostWebCollect();
                collect.setId(IdUtils.getIncreaseIdByNanoTime());
                collect.setUserId(userId);
                collect.setCid(cid);
                collect.setType(type);
                collect.setCreatetime(new Date());
                collect.setStatus((byte) 1);
                collect.setHasCollected(String.valueOf(hasCollected));
                postWebCollectMapper.insertSelective(collect);
                //return new JsonResult(1,postWebCollect);
                return new JsonResult(1,collect);
            }
            //取消收藏
            else {
                if (postWebCollect != null){

                    postWebCollectMapper.deleteByPrimaryKey(postWebCollect.getId());
                    return new JsonResult(1);
                }else {
                    return new JsonResult("140000006");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("140000003");
        }

    }




















}

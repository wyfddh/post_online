package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PostCollectHomeMapper;
import com.tj720.dao.PostStampStoryMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.collecthome.PostCollectHome;
import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.AttachmentService;
import com.tj720.service.PictureService;
import com.tj720.service.PostCollectHomeService;
import com.tj720.service.PostStampStoryService;
import com.tj720.utils.DateFormartUtil;
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
 * @ClassName:  PostCollectHomeServiceImpl
 * @Description:  集邮之家实现类
 * @Author: xa
 * @Date: 2018/10/31
 * @Version: 1.0
 **/
@Service
public class PostCollectHomeServiceImpl implements PostCollectHomeService {

    @Autowired
    private PostCollectHomeMapper  postCollectHomeMapper;


    @Override
    public JsonResult getHomeList(String collectHomeTheme, String createTime,String orderBy, Page page) throws Exception {
        try {
            int count = postCollectHomeMapper.count(collectHomeTheme,createTime,orderBy,page.getCurrentPage(), page.getSize());
            page.setAllRow(count);
            List<PostCollectHome> homeList = postCollectHomeMapper.getHomeList(collectHomeTheme, createTime,orderBy,
                    page.getStart(), page.getSize());


            return new JsonResult(1,homeList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult selectByPrimaryKey(String id) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostCollectHome postCollectHome = postCollectHomeMapper.selectByPrimaryKey(id);
            return new JsonResult(1,postCollectHome);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult insertSelective(PostCollectHome record) {
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }
            record.setId(IdUtils.getIncreaseIdByNanoTime());
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            record.setCreator(Tools.getUserId());
            record.setDataState("1");
            int count =   postCollectHomeMapper.insert(record);
            if (count >0){
                return new JsonResult(1,null);
            }
            else{
                return new JsonResult(0,null,"80000013");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"添加集邮之家异常");
        }
    }

    @Override
    public JsonResult updateByPrimaryKeySelective(PostCollectHome record) {
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }
            if(StringUtils.isNotBlank(record.getId())){
                record.setUpdater(Tools.getUserId());
                record.setUpdateTime(new Date());
                int count =  postCollectHomeMapper.updateByPrimaryKeySelective(record);
                if (count >0){
                    return new JsonResult(1,null);
                }
                else{
                    return new JsonResult(0,null,"80000014");
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"修改集邮之家异常");
        }
        return  null;
    }

    @Override
    public JsonResult deleteByPrimaryKey(String id) {
        JsonResult jsonResult = null;
        PostCollectHome postCollectHome = postCollectHomeMapper.selectByPrimaryKey(id);
        if (postCollectHome != null) {
            postCollectHome.setDataState("0");
            postCollectHome.setUpdateTime(new Date());
            postCollectHome.setUpdater(Tools.getUserId());

            try {
                postCollectHomeMapper.updateByPrimaryKeySelective(postCollectHome);
                jsonResult = new JsonResult(1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResult = new JsonResult("70000015");
            }
        }
        return jsonResult;
    }

    @Override
    public JsonResult updateHomeByIds(List<String> ids) {
        try {
            Integer count=  postCollectHomeMapper.updateHomeByIds(ids);
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

    @Override
    public List<PostCollectHome> getHomeOptions() {

        return postCollectHomeMapper.getHomeOptions();
    }


}

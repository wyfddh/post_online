package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostVideoMapper;
import com.tj720.dao.PostVideoTypeMapper;
import com.tj720.model.common.system.menu.MenuTreeDto;
import com.tj720.model.common.video.PostVideo;
import com.tj720.model.common.video.PostVideoType;
import com.tj720.model.common.video.PostVideoTypeExample;
import com.tj720.service.PostVideoTypeService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/30 19:47
 */
@Service
public class PostVideoTypeServiceImpl implements PostVideoTypeService {
    @Autowired
    PostVideoTypeMapper postVideoTypeMapper;

    private PostVideoType setActionInfo(PostVideoType postVideoType, String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        postVideoType.setUpdater(Tools.getUserId());
        postVideoType.setUpdateTime(date);
        if (type == "0"){
            postVideoType.setCreateTime(date);
            postVideoType.setCreator(Tools.getUserId());
        }
        return postVideoType;
    }
    @Override
    public JsonResult addPostVideoType(PostVideoType postVideoType) {
        if (StringUtils.isEmpty(postVideoType) || StringUtils.isEmpty(postVideoType.getName())){
            return new JsonResult("200510");
        }
        try {
            JsonResult postVideoTypeByName = getPostVideoTypeByName(postVideoType.getName());
            if (null != postVideoTypeByName && postVideoTypeByName.getSuccess()==1){
                List<PostVideoType> postVideoTypes = (List<PostVideoType>)postVideoTypeByName.getData();
                if (postVideoTypes != null && postVideoTypes.size()>0){
                    return new JsonResult("200512");
                }
            }
            postVideoType.setId(IdUtils.getIncreaseIdByNanoTime());
            postVideoType = setActionInfo(postVideoType,"0");
            postVideoType.setSort("100");
            postVideoType.setStatus((byte)1);
            if (StringUtils.isEmpty(postVideoType.getPid())){
                postVideoType.setTypeLevel("1");
            }else{
                PostVideoType postVideoType1 = postVideoTypeMapper.selectByPrimaryKey(postVideoType.getPid());
                if (null != postVideoType1){
                    String levelStr = postVideoType1.getTypeLevel()==null?"0":postVideoType1.getTypeLevel();
                    Integer level = Integer.valueOf(levelStr)+1;
                    postVideoType.setTypeLevel(level.toString());
                }else {
                    postVideoType.setTypeLevel("1");
                }
            }
            int count = postVideoTypeMapper.insertSelective(postVideoType);
            return new JsonResult(1,count);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }
    }

    @Override
    public JsonResult updatePostVideoType(PostVideoType postVideoType) {
        if (StringUtils.isEmpty(postVideoType)){
            return new JsonResult("200510");
        }
        try {
            postVideoType = setActionInfo(postVideoType,"1");
            if (!StringUtils.isEmpty(postVideoType.getTypeLevel())){
                PostVideoType postVideoType1 = postVideoTypeMapper.selectByPrimaryKey(postVideoType.getPid());
                if (null != postVideoType1){
                    String levelStr = postVideoType1.getTypeLevel()==null?"0":postVideoType1.getTypeLevel();
                    Integer level = Integer.valueOf(levelStr)+1;
                    postVideoType.setTypeLevel(level.toString());
                }
            }
            int count = postVideoTypeMapper.updateByPrimaryKeySelective(postVideoType);
            return new JsonResult(1,count);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }
    }

    @Override
    public JsonResult deletePostVideoType(String id) {
        if (StringUtils.isEmpty(id)){
            return new JsonResult("200510");
        }
        try {
            int count = postVideoTypeMapper.deleteByPrimaryKey(id);
            return new JsonResult(1,count);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200506");
        }
    }

    @Override
    public JsonResult queryPostVideoTypeList(String keywords, String level, String status,Integer orderBy,Page page) {
        try {
            HashMap<String,Object> condition = new HashMap<String,Object>();
            condition.put("keywords",keywords);
            condition.put("level",level);
            condition.put("status",status);
            condition.put("orderBy",orderBy);
            Integer count = postVideoTypeMapper.countPostVideoTypeList(condition);
            page.setAllRow(count);
            condition.put("currentPage",page.getStart());
            condition.put("size",page.getSize());
            List<PostVideoType> postVideoTypeList = postVideoTypeMapper.queryPostVideoTypeList(condition);
            return new JsonResult(1,postVideoTypeList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }
    }

    @Override
    public JsonResult queryPostVideoTypeById(String id) {
        try {
            PostVideoType postVideoType = postVideoTypeMapper.queryPostVideoTypeById(id);
            return new JsonResult(1,postVideoType);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }
    }

    @Override
    public JsonResult queryPostVideoTypeListTree() {
        try{
            List<MenuTreeDto> menuTreeDtos = postVideoTypeMapper.queryPostVideoTypeListTree();
            return new JsonResult(1,menuTreeDtos);
        }catch (Exception e){
            return new JsonResult("200507");
        }

    }

    private JsonResult getPostVideoTypeByName(String name){
        try {
//            PostVideoTypeExample example = new PostVideoTypeExample();
//            PostVideoTypeExample.Criteria criteria = example.createCriteria();
//            criteria.andNameEqualTo(name);
//            List<PostVideoType> postVideoTypes = postVideoTypeMapper.selectByExample(example);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("name",name);
            List<PostVideoType> postVideoTypes = postVideoTypeMapper.selectByMap(map);
            return new JsonResult(1,postVideoTypes);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }
    }

    @Override
    public JsonResult changeTypeStatus(String id, int status) {
        try {
            PostVideoType postVideoType = new PostVideoType();
            postVideoType.setId(id);
            postVideoType.setStatus((byte)status);
            postVideoTypeMapper.updateByPrimaryKeySelective(postVideoType);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }


    }

    @Override
    public JsonResult queryPostVideoTypeListDist(String currentId) {
        try {
            List<PostVideoType> postVideoTypeList = postVideoTypeMapper.queryPostVideoTypeListDist(currentId);
            return new JsonResult(1,postVideoTypeList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }
    }
}

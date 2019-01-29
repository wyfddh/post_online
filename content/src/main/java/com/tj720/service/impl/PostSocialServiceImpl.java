package com.tj720.service.impl;





import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostSocialMapper;
import com.tj720.dao.SysDepartmentMapper;
import com.tj720.model.common.social.PostSocial;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.service.PostSocialService;
import com.tj720.service.ResAuthService;
import com.tj720.utils.DateFormartUtil;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PostSocialServiceImpl implements PostSocialService {

    @Autowired
    private PostSocialMapper postSocialMapper;

    @Autowired
    SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    ResAuthService resAuthService;
    
    @Override
    public JsonResult deleteByPrimaryKey(String id) throws Exception{
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            int count =   postSocialMapper.deleteByPrimaryKey(id);
            if (count >0){
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
    public JSONObject getSocials(String keywords, String orderBy,Integer currentPage, Integer size,String module) throws Exception {
        JSONObject jsonObject = new JSONObject();
//        try {
            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();
            if (StringUtils.isNotBlank(keywords)){
                map.put("keywords",keywords);
            }
            if (StringUtils.isNotBlank(orderBy)){
                map.put("orderBy",orderBy);
            }
            String userId = Tools.getUserId();
//        JSONObject jsonObject = new JSONObject();
            if (null != userId) {
                try {
                    SysDepartment deptById = sysDepartmentMapper.getDeptById(userId);
                    String orgId = deptById.getDepartmentId();
                    map.put("userId", userId);
                    map.put("orgId", orgId);
                    JsonResult jsonResult = resAuthService.getDataAuthRule(userId, module);
                    String dataRule = (String) jsonResult.getData();
                    if (dataRule.equals("")) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "");
                        jsonObject.put("count", 0);
                        jsonObject.put("data", null);
                        return jsonObject;
                    }
                    map.put("dataRule", dataRule);
                    map.put("userId", userId);
                    //符合条件总数
                    Integer count = postSocialMapper.count(map);
                    page.setAllRow(count);
                    map.put("start", page.getStart());
                    map.put("end", page.getSize());
                    //查询分页数据
                    List<PostSocial> socialList = postSocialMapper.getSocials(map);
                    String jsonString = JSON.toJSONString(socialList);
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "");
                    jsonObject.put("count", page.getAllRow());
                    jsonObject.put("data", jsonString);
                } catch (Exception e) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", e.getMessage());
                    jsonObject.put("count", 0);
                    jsonObject.put("data", null);
                }
                return jsonObject;
            }else {
                return jsonObject;
            }
    }

    @Override
    public JsonResult insert(PostSocial record) throws Exception {
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }
            record.setId(IdUtils.getIncreaseIdByNanoTime());
            record.setHoldTime(record.getHoldTimeStr() == null ? null : DateFormartUtil.string2DateSimple(record
                    .getHoldTimeStr()));
            //record.setHoldTime(DateFormartUtil.string2DateSimple(record.getHoldTimeStr()));
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            record.setCreator(Tools.getUserId());
            int count =   postSocialMapper.insertSelective(record);
            if (count >0){
                return new JsonResult(1,null);
            }
            else{
                return new JsonResult(0,null,"200006");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"添加社教数据失败");
        }
    }

    @Override
    public JsonResult selectByPrimaryKey(String id) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostSocial postSocial = postSocialMapper.selectByPrimaryKey(id);
            return new JsonResult(1,postSocial);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult updateByPrimaryKeySelective(PostSocial record) throws Exception {
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }
            if(StringUtils.isNotBlank(record.getId())){
                record.setHoldTime(DateFormartUtil.string2DateSimple(record.getHoldTimeStr()));
                record.setUpdater(Tools.getUserId());
                record.setUpdateTime(new Date());
                int count =   postSocialMapper.updateByPrimaryKeySelective(record);
                if (count >0){
                    return new JsonResult(1,null);
                }
                else{
                    return new JsonResult(0,null,"200006");
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"修改社教数据失败");
        }
        return  null;
    }

    @Override
    public JsonResult updateByPrimaryKey(PostSocial record) throws Exception {
        return null;
    }


    @Override
    public JsonResult batchRemove(@RequestBody List<PostSocial> postSocials) throws Exception{
        try{
            if(null!=postSocials && postSocials.size()>0){
                int count = postSocialMapper.batchRemove(postSocials);
                if (count >0){
                    return new JsonResult(1,null);
                }
                else{
                    return new JsonResult(0,null,"200006");
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"删除社教数据失败");
        }
        return null;
    }

    @Override
    public JsonResult batchRemoves(String[] ids) throws Exception {
        try {
            if(ids.length<0){
                return new JsonResult(0,"参数错误");
            }
            int count = postSocialMapper.batchRemoves(ids);
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


}

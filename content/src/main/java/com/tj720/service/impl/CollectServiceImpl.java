package com.tj720.service.impl;/**
 * Created by MyPC on 2018/10/24.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.AttachmentMapper;
import com.tj720.dao.CollectMapper;
import com.tj720.dao.SysDictMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.SelectDto;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collect.CollectExample;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.AttachmentService;
import com.tj720.service.CollectService;
import com.tj720.service.PictureService;
import com.tj720.service.SysDictSevice;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import com.tj720.utils.common.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: CollectServiceImpl
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/24
 * @Version: 1.0
 **/
@Service
public class CollectServiceImpl implements CollectService {



    private Collect setTimeInfo(Collect collect, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String  userId =  Tools.getUserId();
        collect.setUpdateTime(date);
        collect.setUpdater(userId);
        // 0代表 没有创建的用户，录入创建人和创建时间
        if ("0".equals(type)) {
            collect.setId(IdUtils.getIncreaseIdByNanoTime());
            collect.setCreateTime(date);
            collect.setCreator(userId);
        }
        return collect;
    }

    private List<CollectDto> getListUseCollect(List<Collect> listCollect) {
        List<CollectDto> collectDtos = new ArrayList<>();
        for (int i = 0, j = listCollect.size(); i < j; i++) {
            CollectDto collectDto = new CollectDto();
            Collect collect = listCollect.get(i);
            collectDto.setCollect(collect);
            String typeId = collect.getTypeId();
            String typeName = sysDictSevice.getDictById(typeId).getDictName();
            collectDto.setTypeId(typeId);
            collectDto.setType(typeName);
            String sonTypeId = collect.getSonTypeId();
            if (!Utils.isEmpty(sonTypeId)) {
                String SonTypeName = sysDictSevice.getDictById(sonTypeId).getDictName();
                collectDto.setTypeId(sonTypeId);
                collectDto.setSonType(SonTypeName);
            } else {
                collectDto.setTypeId(null);
                collectDto.setSonType(null);
            }
            collectDtos.add(collectDto);
        }
        return collectDtos;
    }

    private List<CollectDto> getListUseCollects(List<Collect> listCollect) {
        List<CollectDto> collectDtos = new ArrayList<>();
        for (int i = 0, j = listCollect.size(); i < j; i++) {
            CollectDto collectDto = new CollectDto();
            Collect collect = listCollect.get(i);
            collectDto.setCollect(collect);
            collectDto.setId(collect.getId());
            collectDto.setColleId(collect.getColleId());
            collectDto.setMsg(collect.getMsg());
            String pictureids = collect.getPictureids();
            collectDto.setPictureids(pictureids);
            List<Attachment> attList = getListUseAttachments(pictureids);
            collectDto.setAttachmentList(attList);
            collectDto.setName(collect.getName());
            collectDto.setCommend(collect.getCommend());
            String typeId = collect.getTypeId();
            String typeName = sysDictSevice.getDictById(typeId).getDictName();
            collectDto.setTypeId(typeId);
            collectDto.setType(typeName);
            String sonTypeId = collect.getSonTypeId();
            if (!Utils.isEmpty(sonTypeId)) {
                String SonTypeName = sysDictSevice.getDictById(sonTypeId).getDictName();
                collectDto.setTypeId(sonTypeId);
                collectDto.setSonType(SonTypeName);
            } else {
                collectDto.setTypeId(null);
                collectDto.setSonType(null);
            }
            collectDtos.add(collectDto);
        }
        return collectDtos;
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
            attachments.setAttPath(config.getRootUrl() + attachment.getAttPath());
            attachments.setAttId(attachment.getAttId());
            attList.add(attachments);
        }
        return attList;
    }

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    SysDictMapper sysDictMapper;

    @Autowired
    SysDictSevice sysDictSevice;

    @Autowired
    AttachmentMapper attachmentMapper;


    @Autowired
    private AttachmentService attachmentService;


    @Autowired
    private PictureService pictureService;

    @Autowired
    private Config config;


    @Override
    public JsonResult addCollect(Collect collect) {
        try {
            collect = setTimeInfo(collect, "0");
            String collectName = collect.getName();
//            CollectExample example = new CollectExample();
//            CollectExample.Criteria criteria = example.createCriteria();
//            criteria.andNameEqualTo(collectName);
//            Integer count = collectMapper.countByExample(example);

            Integer count = collectMapper.countByName(collectName);
            if (count > 0) {
                return new JsonResult(0, null, "40000012");
            } else {
                int i = collectMapper.insertSelective(collect);
                if (i == 1) {
                    return new JsonResult(1, null);
                } else {
                    return new JsonResult(0, null, "20000003");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateCollect(Collect collect) {
        try {
            collect = setTimeInfo(collect, "1");
            int count = collectMapper.updateByPrimaryKeySelective(collect);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "40000016");
            }
        } catch (Exception e) {
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateCollectType(Collect collect) {
        try {
            int count = collectMapper.updateByPrimaryKeySelective(collect);

            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "40000017");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult getOneCollect(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            } else {
                Collect collect = collectMapper.selectByPrimaryKey(id);
                if (collect.getId() != null) {
                    Map map = new HashMap();
                    CollectDto collectDto = new CollectDto();
                    collectDto.setCollect(collect);
                    String typeId = collect.getTypeId();
                    String sonTypeName = null;
                    String pictureids = collect.getPictureids();
                    List<Attachment> attList = getListUseAttachments(pictureids);
                    collectDto.setAttachmentList(attList);
                    String typeName = sysDictSevice.getDictById(typeId).getDictName();
                    collectDto.setTypeId(typeId);
                    collectDto.setType(typeName);
                    String sonTypeId = collect.getSonTypeId();
                    if (!Utils.isEmpty(sonTypeId)) {
                        sonTypeName = sysDictSevice.getDictById(sonTypeId).getDictName();
                        collectDto.setSonTypeId(sonTypeId);
                        collectDto.setSonType(sonTypeName);
                    } else {
                        collectDto.setSonTypeId(null);
                        collectDto.setSonType(null);
                    }
                    return new JsonResult(1, collectDto);
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
    public JSONObject getListCollect(String name, String typeId, String sonTypeId, String orderBy, Integer currentPage,
                                     Integer size) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(name)) {
                map.put("name", name);
            }
            if (StringUtils.isNotBlank(typeId)) {
                map.put("typeId", typeId);
            }
            if (StringUtils.isNotBlank(sonTypeId)) {
                map.put("sonTypeId", sonTypeId);
            }
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            int count = collectMapper.selectCountByCollect(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<Collect> listCollect = collectMapper.selectListByCollect(map);
            List<CollectDto> listCollectDto = getListUseCollects(listCollect);

            String jsonString = JSON.toJSONString(listCollectDto, SerializerFeature.DisableCircularReferenceDetect);
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
    public JsonResult updateCollectByIds(List<String> ids) {
        try {
            Integer size = ids.size();
            Integer i = collectMapper.updateCollectByIds(ids);
            if (size == i) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "40000014");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }

    }

    @Override
    public JsonResult deleteCollectById(String id) {
        try {
            Collect collect = new Collect();
            collect.setIsdelete("1");
            collect.setId(id);
            int count = collectMapper.updateByPrimaryKeySelective(collect);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "40000018");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    /**
     * 根据父id查询业务字典列表
     *
     * @param pid 父id
     * @return
     */
    @Override
    public JsonResult getSysDictListByPid(String pid) {
        try {
            if (StringUtils.isEmpty(pid)) {
                return new JsonResult(0, null, "40000019");
            } else {
                List<SysDict> sysDictList = sysDictMapper.getDictByPid(pid);
                //List<SysDict> sysDictList = sysDictSevice.getSysDictListByPid(pid);
                return new JsonResult(1, sysDictList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }

    }

    @Override
    public Integer CountDcjx(){
        return collectMapper.CountDcjx();
    }

    /**
     * 根据id 给图片设置主图
     *
     * @param attachment
     * @return
     */
    public JsonResult updateAttachmentByid(Attachment attachment) {
        try {
            if (StringUtils.isEmpty(attachment.getAttId())) {
                return new JsonResult(0, null, "40000019");
            } else {
                int count = attachmentMapper.updateByPrimaryKeySelective(attachment);
                if (count > 0 ) {
                    return new JsonResult(1, null);
                } else {
                    return new JsonResult(0, null, "40000021");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }

    }

     @Override
    //select4  要求数据格式  name,value,selected,disabled
    public JSONObject selectBaiDuListByParam(String typeId){
        // 如果params 为空，则查询的是藏品的类型集合，否则就是根据类型查询集合数据
        JSONObject  jSONObject   = new JSONObject();
        Map map = new HashMap();
        List<Collect> listCollect = null;
        try {
            if (Utils.isEmpty(typeId)) {
                listCollect = collectMapper.selectByParams(map);
                if (Utils.isEmpty(listCollect)) {
                    //return new JsonResult(0, null, "40000020");
                    jSONObject.put("msg",40000020);
                    return jSONObject;
                } else {
                    List<CollectDto> listCollectDto = getListUseCollect(listCollect);
                    jSONObject.put("data",listCollectDto);
                    return jSONObject;
                    //return new JsonResult(1, listCollectDto);
                }
            } else {
                map.put("typeId", typeId);
                listCollect = collectMapper.selectByParams(map);
                if (Utils.isEmpty(listCollect)) {
                    jSONObject.put("msg",40000020);
                    return jSONObject;
                    //return new JsonResult(0, null, "40000020");
                } else {
                    List<CollectDto> listCollectDto = getListUseCollects(listCollect);
                    ArrayList<SelectDto> selects = new ArrayList<SelectDto>();
                    for(CollectDto collectDto:listCollectDto){
                        SelectDto  selectDto = new SelectDto();
                        selectDto.setName(collectDto.getType());
                        selectDto.setValue(collectDto.getTypeId());
                        selects.add(selectDto);
                    }
                    jSONObject.put("code",0);
                    jSONObject.put("msg","success");
                    jSONObject.put("data",selects);
                    return jSONObject;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject.put("msg",40000020);
            return jSONObject;
            //return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult getCollectdcByUid(String userId, String type, Integer currentPage, Integer size) {
            Map map = new HashMap();
            Page page = new Page();
            try {
                if (StringUtils.isNotBlank(userId)) {
                    map.put("userId", userId);
                }
                if (StringUtils.isNotBlank(type)) {
                    map.put("type", type);
                }
                page.setSize(size);
                page.setCurrentPage(currentPage);
                int count = collectMapper.countCollectdcByUid(map);
                page.setAllRow(count);
                map.put("start", page.getStart());
                map.put("end", page.getSize());
                List<CollectDto>  collectList = collectMapper.getCollectdcByUid(map);
                List<Attachment> picList1 = new ArrayList<Attachment>();
                for(CollectDto collect : collectList){
                    if(StringUtils.isNotBlank(collect.getPictureids())){
                        picList1 = attachmentService.getListByIds(collect.getPictureids());
                        if(picList1.size()>0 && StringUtils.isNotBlank(picList1.get(0).getAttPath())){
                            collect.setPicUrl(config.getRootUrl()+picList1.get(0).getAttPath());
                            break;
                        }
                    }

                }
                return new JsonResult(1,collectList);
            }catch (Exception e){
                e.printStackTrace();
                return new JsonResult(0,"系统异常");
            }
    }

    @Override
    public JsonResult  selectIndexCollectList(){
        try {
            List<CollectDto> collectList = collectMapper.selectIndexCollectList();
            List<Attachment> picList1 = new ArrayList<Attachment>();
            for(CollectDto collect : collectList){
                if(StringUtils.isNotBlank(collect.getPictureids())){
                    picList1 = attachmentService.getListByIds(collect.getPictureids());
                    if(picList1.size()>0 && StringUtils.isNotBlank(picList1.get(0).getAttPath())){
                        collect.setPicUrl(config.getRootUrl()+picList1.get(0).getAttPath());
                        break;
                    }
                }
            }
            return new JsonResult(1,collectList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }


    @Override
    public JsonResult selectListByParams(String parmas) {
        // 如果params 为空，则查询的是藏品的类型集合，否则就是根据类型查询集合数据
        Map map = new HashMap();
        List<Collect> listCollect = null;
        try {
            if (Utils.isEmpty(parmas)) {
                listCollect = collectMapper.selectByParams(map);
                if (Utils.isEmpty(listCollect)) {
                    return new JsonResult(0, null, "40000020");
                } else {
                    List<CollectDto> listCollectDto = getListUseCollect(listCollect);
                    return new JsonResult(1, listCollectDto);
                }
            } else {
                map.put("typeId", parmas);
                listCollect = collectMapper.selectByParams(map);
                if (Utils.isEmpty(listCollect)) {
                    return new JsonResult(0, null, "40000020");
                } else {
                    List<CollectDto> listCollectDto = getListUseCollects(listCollect);
                    return new JsonResult(1, listCollectDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult selectListByIds(String ids, String typeId, String sonTypeId) {
        if (Utils.isEmpty(ids)) {
            return new JsonResult(0, null, "20000003");
        }
        List<CollectDto> collectList = new ArrayList<CollectDto>();
        if ("all".equals(ids)) {
            if ("all".equals(sonTypeId)) {
                sonTypeId = null;
            }
            Collect collect = new Collect();
            collect.setTypeId(typeId);
            collect.setSonTypeId(sonTypeId);
            collectList = collectMapper.selectListByTypeAndSonType(collect);
        } else {
            String[] idArr = ids.split(",");
            collectList = collectMapper.selectListByIds(idArr);
        }
        List<CollectDto> res = new ArrayList<CollectDto>();
        for (int i = 0, length = collectList.size(); i < length; i++) {
            String type = "";
            String sonType = "";
            CollectDto dto = collectList.get(i);
            sonTypeId = dto.getSonTypeId();
            if (!StringUtils.isBlank(sonTypeId)) {
                sonType = "-" + sysDictMapper.getDictById(sonTypeId).getDictName();
            }
            type = sysDictMapper.getDictById(typeId).getDictName();
            dto.setTypeFullName(type + sonType);
            res.add(dto);
        }
        return new JsonResult(1, res);
    }

    @Override
    public JsonResult selectListByTypeAndSonType(Collect collect) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(1);
        String typeId = collect.getTypeId();
        String sonTypeId = collect.getSonTypeId();
        if (Utils.isEmpty(typeId) || Utils.isEmpty(sonTypeId)) {
            jsonResult.setMsg("查询条件为空！");
            return jsonResult;
        }
        if ("all".equals(sonTypeId)) {
            collect.setSonTypeId(null);
        }
        List<CollectDto> collectList = collectMapper.selectListByTypeAndSonType(collect);
        if (CollectionUtils.isEmpty(collectList)) {
            jsonResult.setMsg("无数据");
            return jsonResult;
        }
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>(collectList.size());
        Map<String, Object> resMap = null;
        for (CollectDto col : collectList) {
            resMap = new HashMap<String, Object>(4);
            resMap.put("name", col.getName());
            resMap.put("value", col.getId());
            resMap.put("selected", "");
            resMap.put("disabled", "");
            resList.add(resMap);
        }
        jsonResult.setCode(0);
        jsonResult.setData(resList);
        return jsonResult;
    }
}

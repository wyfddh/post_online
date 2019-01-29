package com.tj720.service.impl;/**
 * Created by MyPC on 2018/10/15.
 */

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.*;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.exhibition.*;
import com.tj720.model.common.interfacecollect.InterfaceCollect;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.video.PostExhibVideo;
import com.tj720.model.common.video.PostVideo;
import com.tj720.service.AttachmentService;
import com.tj720.service.ExhibitionService;
import com.tj720.service.PictureService;
import com.tj720.service.ResAuthService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import com.tj720.utils.common.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: ExhibitionServiceImpl
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/15
 * @Version: 1.0
 **/
@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    @Autowired
    ExhibitionMapper exhibitionMapper;

    @Autowired
    ExhibCollectionMapper exhibCollectionMapper;

    @Autowired
    ExhibRoomMapper exhibRoomMapper;

    @Autowired
    ExhibVideoMapper exhibVideoMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private Config config;

    @Autowired
    SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    ResAuthService resAuthService;


//    private static String userId = Tools.getUserId();


    private Date getNowDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private Date getUseDate(Date date) {
        if (date == null) {
            return getNowDate();
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                String format = dateFormat.format(date);
                date = dateFormat.parse(format);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return date;
        }

    }

    // 切面处理 创建人和创建时间
    private ExhibitionDto setTimeInfo(ExhibitionDto exhibitionDto, String type, String classType) {
        String exhibId = exhibitionDto.getExhibition().getId();

        if (StringUtils.isBlank(exhibId)) {
            exhibId = IdUtils.getIncreaseIdByNanoTime();
        }

        Date date = getNowDate();

        //if ("Exhibition".equals(classType)) {
        //
        //}
//    展陈主表
        Exhibition exhibition = exhibitionDto.getExhibition();
        exhibition.setUpdater(Tools.getUserId());
        exhibition.setUpdateTime(date);

// 因为新增展陈时，藏品-展厅-影视都可能一次性录入多个，所以 需要集合来存取；
        List<ExhibRoom> listExhibRoom = exhibitionDto.getListExhibRoom();
        for (ExhibRoom exhibRoom : listExhibRoom) {
            exhibRoom.setUpdater(Tools.getUserId());
            exhibRoom.setUpdateTime(date);
            // 在新增展陈时 同时新增的 多个 展厅，需要录入关联的展陈id

        }

        List<InterfaceCollect> listExhibCollection = exhibitionDto.getListExhibCollection();
        for (InterfaceCollect exhibCollection : listExhibCollection) {
            exhibCollection.setUpdater(Tools.getUserId());
            exhibCollection.setUpdateTime(date);
        }

        List<PostExhibVideo> listExhibVideo = exhibitionDto.getListExhibVideo();
        for (PostVideo exhibVideo : listExhibVideo) {
            exhibVideo.setUpdater(Tools.getUserId());
            exhibVideo.setUpdateTime(date);
        }

        // 0代表 没有创建的用户，录入创建人和创建时间
        if ("0".equals(type)) {
            String id = IdUtils.getIncreaseIdByNanoTime();
            exhibition.setCreator(Tools.getUserId());
            exhibition.setCreateTime(date);
            exhibition.setId(exhibId);
            exhibition.setIsdelete(0);
            exhibition.setStatus(1);

            for (ExhibRoom exhibRoom : listExhibRoom) {
                String roomId = IdUtils.getIncreaseIdByNanoTime();
                exhibRoom.setCreator(Tools.getUserId());
                exhibRoom.setCreateTime(date);
                // 在新增展陈时 同时新增的 多个 展厅，需要录入关联的展陈id
                exhibRoom.setId(roomId);
                exhibRoom.setIsdelete(0);
                exhibRoom.setStatus(1);
                exhibRoom.setExhibId(exhibId);
            }

            for (InterfaceCollect exhibCollection : listExhibCollection) {
                String collectId = IdUtils.getIncreaseIdByNanoTime();
                exhibCollection.setCreator(Tools.getUserId());
                exhibCollection.setCreateTime(date);
                exhibCollection.setStatus(1);
                // 在新增展陈时 同时新增的 多个 展厅，需要录入关联的展陈id
                exhibCollection.setId(collectId);
                exhibCollection.setExhibId(exhibId);
            }

            for (PostExhibVideo exhibVideo : listExhibVideo) {
                String videoId = IdUtils.getIncreaseIdByNanoTime();
                exhibVideo.setCreator(Tools.getUserId());
                exhibVideo.setCreateTime(date);
                exhibVideo.setStatus("1");
                // 在新增展陈时 同时新增的 多个 展厅，需要录入关联的展陈id
                exhibVideo.setId(videoId);
                exhibVideo.setExhibId(exhibId);
            }
        }
        return exhibitionDto;
    }


    @Override
    @Transactional
    public JsonResult addExhibition(ExhibitionDto exhibitionDto, String type,String picids) {
        try {
            // exhibitionCount = 1 用作标识，防止后面代码 exhibitionCount > 0 不能执行
            int exhibitionCount = 1, exhibRoomSize = 0, exhibColleSize = 0, exhibVideoSize = 0,
                    exhibColleCount = 0, exhibVideoCount =0, exhibiRoomCount = 0;

            exhibitionDto = setTimeInfo(exhibitionDto, "0", null);


            picids = exhibitionDto.getExhibition().getDatumIds();
            //设置图片
            pictureService.setMain(picids, picids, null);
            exhibitionDto.getExhibition().setDatumIds(picids);

            //前端校验必填项
            if ("add".equals(type)) {
                Exhibition exhibition = exhibitionDto.getExhibition();
                exhibition.setCreateTime(new Date());
                exhibition.setUpdateTime(new Date());
                exhibition.setStatus(1);
                exhibition.setCollectNum(exhibitionDto.getListExhibCollection().size());
                exhibition.setVideoNum(exhibitionDto.getListExhibVideo().size());
                exhibition.setVideoId(null);
                exhibitionCount = exhibitionMapper.insert(exhibition);
            }
            // 批量插入展厅
            List<ExhibRoom> listExhibRoom = exhibitionDto.getListExhibRoom();
            exhibRoomSize = listExhibRoom.size();
            if (!Utils.isEmpty(listExhibRoom)) {
                exhibiRoomCount = exhibRoomMapper.insertByForeachRoom(listExhibRoom);
            } else {
                //return new JsonResult(0, "展厅无数据插入！", "2000001");
            }

            // 批量插入藏品
            List<InterfaceCollect> listExhibCollection = exhibitionDto.getListExhibCollection();
            exhibColleSize = listExhibCollection.size();
            if (!Utils.isEmpty(listExhibCollection)) {
                exhibColleCount = exhibCollectionMapper.insertByForeachCollection(listExhibCollection);
            } else {
                //return new JsonResult(0, "藏品无数据插入！", "2000001");
            }

            // 批量插入影视
            List<PostExhibVideo> listExhibVideo = exhibitionDto.getListExhibVideo();
            exhibVideoSize = listExhibVideo.size();
            if (!Utils.isEmpty(listExhibVideo)) {
                exhibVideoCount = exhibVideoMapper.insertByForeachVideo(listExhibVideo);
            } else {
                //return new JsonResult(0, "影视无数据插入！", "2000001");
            }

            if (exhibitionCount > 0 && (exhibRoomSize == exhibiRoomCount) && (exhibColleSize == exhibColleCount)
                    && (exhibVideoSize == exhibVideoCount)) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "30000013");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Spring 事务需要捕获异常来处理回滚，所以无法进行return
            throw new RuntimeException();
        }


    }

    @Override
    public JsonResult deleteExhibition(ExhibitionDto exhibitionDto) {


        return null;
    }

    @Override
    @Transactional
    public JsonResult deleteExhibitionById(String id, String type) {
        Date date = getNowDate();
        try {
            Exhibition exhibition = new Exhibition();
            exhibition.setId(id);
            exhibition.setIsdelete(1);
            exhibition.setUpdater(Tools.getUserId());
            exhibition.setUpdateTime(date);
            int exhibCount = exhibitionMapper.updateByPrimaryKeySelective(exhibition);
            // 是否删除 展厅-藏品-影视的表
            // 通过type 判定，是否有删除的藏品  如果没有就不调用删除管理藏品的方法

            int roomResult = deleteExhibiRoomById(id, date).getSuccess();

            int colleResult = deleteExhibColleById(id, date).getSuccess();

            int videoResult = deleteExhibVideoById(id, date).getSuccess();


            if (exhibCount > 0 && (1 == roomResult) && (1 == colleResult) && (1 == videoResult)) {
                return new JsonResult(1, "删除展陈成功");
            } else {
                return new JsonResult(0, "删除展陈失败", "2000002");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //return new JsonResult(0, "系统异常", "2000002");
            throw new RuntimeException();
        }
    }

    public JsonResult deleteExhibiRoomById(String id, Date date) {
        if (date == null) {
            date = getNowDate();
        }
        int exhibRoomCount = 0;
        try {
            Integer count = exhibRoomMapper.deleteByExhibId(id);
            if (count > - 1) {
                return new JsonResult(1);
            } else {
                return new JsonResult(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "系统异常", "2000003");
            //throw new RuntimeException();
        }
    }

    public JsonResult deleteExhibColleById(String id, Date date) {
        if (date == null) {
            date = getNowDate();
        }
        int exhibColleCount = 0;
        try {
            Integer count = exhibCollectionMapper.deleteByExhibId(id);
            if (count > - 1) {
                return new JsonResult(1);
            } else {
                return new JsonResult(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "藏品删除异常", "2000004");
            //throw new RuntimeException();
        }
    }

    public JsonResult deleteExhibVideoById(String id, Date date) {
        if (date == null) {
            date = getNowDate();
        }
        int exhibVideoCount = 0;
        try {
            Integer count = exhibVideoMapper.deleteByExhibId(id);
            if (count > - 1) {
                return new JsonResult(1);
            } else {
                return new JsonResult(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "影视删除异常", "2000005");
            //throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public JsonResult updateExhibition(ExhibitionDto exhibitionDto, String picids) {
        // 编辑的时候，子表先删除旧数据，然后在新增新数据， 主表只能去修改，保留id
        Date date = getNowDate();
        try {


            Exhibition exhibition = exhibitionDto.getExhibition();

            String deleteId = exhibition.getId();

            /*if(StringUtils.isNotBlank(picids)){
                pictureService.setMain(picids, picids, null);
                exhibition.setDatumIds(picids);
            }*/

            exhibition.setRoomNum(exhibitionDto.getListExhibRoom().size());
            exhibition.setVideoNum(exhibitionDto.getListExhibVideo().size());
            exhibition.setCollectNum(exhibitionDto.getListExhibCollection().size());
            exhibition.setUpdateTime(new Date());
            int exhibResult = exhibitionMapper.updateByPrimaryKeySelective(exhibition);

            int roomResult = deleteExhibiRoomById(deleteId, date).getSuccess();

            int colleResult = deleteExhibColleById(deleteId, date).getSuccess();

            int videoResult = deleteExhibVideoById(deleteId, date).getSuccess();

            if (exhibResult > 0 && roomResult == 1 && colleResult == 1 && videoResult == 1) {
                int addCount = addExhibition(exhibitionDto, null,picids).getSuccess();
                if (addCount == 1) {
                    return new JsonResult(1, null);
                } else {
                    return new JsonResult(0, "修改展陈时，新增数据异常", "2000006");
                }
            } else {
                return new JsonResult(0, "修改展陈时，删除数据异常", "2000006");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "系统异常！", "2000006");
        }

    }

    @Override
    @Transactional
    public JsonResult getOneExhibition(String exhibId) {
        try {
            ExhibitionDto exhibitionDto = new ExhibitionDto();
//            ExhibitionExample example = new ExhibitionExample();
//            ExhibitionExample.Criteria criteria = example.createCriteria();
//            criteria.andIsdeleteEqualTo(0).andIdEqualTo(exhibId);
//                    //.andCollectIdEqualTo("3");
//            //criteria.andIdEqualTo(exhibId);
//            List<Exhibition> listExhibi = exhibitionMapper.selectByExample(example);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("isdelete",0);
            map.put("id",exhibId);
            List<Exhibition> listExhibi = exhibitionMapper.selectByMap(map);

            if (listExhibi.size() == 1) {
                Exhibition exhibition = listExhibi.get(0);


             /*   if(!StringUtils.isBlank(exhibition.getDatumIds())){
                    //获取图片url
                    exhibition.setMainPicUrl("");

                    List<Attachment> picList = new ArrayList<Attachment>();
                    picList = attachmentService.getListByIds(exhibition.getDatumIds());
                    for (Attachment attachment : picList) {
                        attachment.setAttPath(CheckConfig.getRootUrl()+attachment.getAttPath());
                    }
                    exhibition.setPicList(picList);
                }*/


                exhibitionDto.setExhibition(exhibition);
            } else {
                return new JsonResult(0, null, "30000015");
            }
//            ExhibRoomExample exhibRoomExample = new ExhibRoomExample();
//            ExhibRoomExample.Criteria criteriaRoom = exhibRoomExample.createCriteria();
//            criteriaRoom.andIsdeleteEqualTo(0);
//            criteriaRoom.andExhibIdEqualTo(exhibId);
//            List<ExhibRoom> listExhibRoom = exhibRoomMapper.selectByExample(exhibRoomExample);

            HashMap<String,Object> map1 = new HashMap<String,Object>();
            map1.put("isdelete",0);
            map1.put("exhibId",exhibId);
            List<ExhibRoom> listExhibRoom = exhibRoomMapper.selectByMap(map1);


            if (!Utils.isEmpty(listExhibRoom)) {
                exhibitionDto.setListExhibRoom(listExhibRoom);
            }
            //else {
            //    return new JsonResult(0, "展厅信息查询失败！", "2000004");
            //}

//            ExhibCollectionExample exhibCollectionExample = new ExhibCollectionExample();
//            ExhibCollectionExample.Criteria criteriaColle = exhibCollectionExample.createCriteria();
//            criteriaColle.andIsdeleteEqualTo(0);
//            criteriaColle.andExhibIdEqualTo(exhibId);
//            int colleCount = exhibCollectionMapper.countByExample(exhibCollectionExample);
            List<InterfaceCollect> listExhColl = exhibCollectionMapper.selectForExhib(exhibId);
            if (!Utils.isEmpty(listExhibi)) {
                exhibitionDto.setListExhibCollection(listExhColl);
            }

//            ExhibVideoExample exhibVideoExample = new ExhibVideoExample();
//            ExhibVideoExample.Criteria criteriaVideo = exhibVideoExample.createCriteria();
//            criteriaVideo.andIsdeleteEqualTo(0);
//            criteriaVideo.andIdEqualTo(exhibId);
//            int videoCount = exhibVideoMapper.countByExample(exhibVideoExample);
            List<PostExhibVideo> listExhVideo = exhibVideoMapper.selectForEnhib(exhibId);
            if (!Utils.isEmpty(listExhVideo)) {
                exhibitionDto.setListExhibVideo(listExhVideo);
            }
            return new JsonResult(1, exhibitionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JSONObject getListExhibition(String name, String planTime, String orderBy, Integer currentPage, Integer
            size,String module) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();

            Page page = new Page();
            if (StringUtils.isNotBlank(name)) {
                map.put("name", name);
            }
            if (StringUtils.isNotBlank(planTime)) {
                map.put("planTime", planTime);
            }
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
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
                page.setSize(size);
                page.setCurrentPage(currentPage);
                Integer count = exhibitionMapper.selectCountByExhibVo(map);
                page.setAllRow(count);
                map.put("start", page.getStart());
                map.put("end", page.getSize());
                List<ExhibitionVo> listExhibition = exhibitionMapper.selectListByExhibVo(map);

                for (ExhibitionVo exhibitionVo : listExhibition) {
                    //获取图片url
                    exhibitionVo.setMainPicUrl("");
                    List<Attachment> picList = new ArrayList<Attachment>();
                    if (StringUtils.isNotBlank(exhibitionVo.getDatumIds())) {
                        picList = attachmentService.getListByIds(exhibitionVo.getDatumIds());
                        for (Attachment attachment : picList) {
                            if (!attachment.getAttPath().contains(config.getRootUrl())) {
                                attachment.setAttPath(config.getRootUrl() + attachment.getAttPath());
                            }
                        }
                        exhibitionVo.setPicList(picList);
                    }
                }


                String jsonString = JSON.toJSONString(listExhibition);
                jsonObject.put("code", 0);
                jsonObject.put("success", 1);
                jsonObject.put("msg", "");
                jsonObject.put("count", page.getAllRow());
                jsonObject.put("data", jsonString);
            } catch(NumberFormatException e){
                jsonObject.put("msg", "");
                jsonObject.put("count", null);
                jsonObject.put("data", null);
                jsonObject.put("error", e.getMessage());
            }
            return jsonObject;
        }else{
            return jsonObject;
        }
    }

    @Override
    public JsonResult updateExhibByIds(List<String> ids) {
        try {
            Integer size = ids.size();
            Integer i = exhibitionMapper.updateExhibByIds(ids);
            if (size.equals(i)) {
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
    public JsonResult deleteExhibColleById(String id) {
        Map<String, Object> parma = new HashMap<String, Object>(2);
        parma.put("id", id);
        parma.put("updator", Tools.getUserId());
        Integer count = exhibitionMapper.deleteExhibColleById(parma);
        if (count > -1) {
            return new JsonResult(1);
        } else {
            return new JsonResult(0, null, "20000003");
        }
    }
}

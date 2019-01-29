package com.tj720.service.impl;/**
 * Created by MyPC on 2018/10/24.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tj720.controller.attachment.FileBase64;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.*;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collecthome.PostCollectHome;
import com.tj720.model.common.collectiontype.PostCollectionType;
import com.tj720.model.common.education.Education;
import com.tj720.model.common.education.EducationDto;
import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.model.common.publiccurator.PostPublicCuratorDto;
import com.tj720.model.common.publiccurator.PostPublicCuratorVo;
import com.tj720.model.common.research.Research;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.model.common.volunteer.PostVolunteerActivities;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesDto;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesVo;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import com.tj720.service.*;
import com.tj720.utils.DateFormartUtil;
import com.tj720.utils.MyString;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import com.tj720.utils.common.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CollectServiceImpl
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/24
 * @Version: 1.0
 **/
@Service
public class WebInterfaceServiceImpl implements WebInterfaceService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    ImageUtiilService imageUtilService;

    @Autowired
    private  CollectMapper collectMapper;

    @Autowired
    private SysDictSevice sysDictSevice;

    @Autowired
    private AttachmentService attachmentService;


    @Autowired
    private PictureService pictureService;

    @Autowired
    private Config config;


    @Autowired
    private  PostBookingManageMapper postBookingManageMapper;


    @Autowired
    private  PostThemeShowMapper  postThemeShowMapper;


    @Autowired
    private PostInformationManageMapper  postInformationManageMapper;


    @Autowired
    private  PostStampStoryMapper  postStampStoryMapper;


    @Autowired
    private  ResearchMapper  researchMapper;


    @Autowired
    private  EducationMapper  educationMapper;


    @Autowired
    private PostCollectHomeMapper  postCollectHomeMapper;

    @Autowired
    private PostVolunteerActivitiesMapper postVolunteerActivitiesMapper;

    @Autowired
    private PostVolunteerApplyMapper postVolunteerApplyMapper;

    @Autowired
    private PostPublicCuratorMapper postPublicCuratorMapper;

    @Autowired
    private PostPublicCuratorCollectMapper postPublicCuratorCollectMapper;

    @Autowired
    SysDictMapper sysDictMapper;

    @Autowired
    private PostCollectionTypeMapper  postCollectionTypeMapper;

    @Autowired
    CollectService collectService;

    @Autowired
    private PubUserMapper pubUserMapper;

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
            List<Attachment> attList = attachmentService.getFileTransPathList(pictureids);
            collectDto.setAttachmentList(attList);
            collectDto.setName(collect.getName());
            collectDto.setCommend(collect.getCommend());
            String typeId = collect.getTypeId();
            if (StringUtils.isNotBlank(typeId)) {
                String typeName = sysDictSevice.getDictById(typeId).getDictName();
                collectDto.setTypeId(typeId);
                collectDto.setType(typeName);
            }


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
        ExecutorService ex = Executors.newFixedThreadPool(20);
        Map map = new HashMap();
        String[] picIds = (pictureids + ",").split(",");
        List<Attachment> attList = new ArrayList<>();
        for (int ii = 0, jj = picIds.length; ii < jj; ii++) {
            map.put("attId", picIds[ii]);
            Future future1 = ex.submit(new ThreadWithCallback(map));
            try {
                attList.add((Attachment) future1.get());
            } catch (InterruptedException e) {

            } catch (ExecutionException e) {

            }
        }

        ex.shutdown();
        return attList;
    }

    class ThreadWithCallback implements Callable {

        private List<Attachment> attList = new ArrayList<>();
        private Map map = new HashMap();

        public ThreadWithCallback(Map map) {
            this.map = map;
        }

        //相当于Thread的run方法
        @Override
        public Object call() throws Exception {
            List<Attachment> listAttachment = collectMapper.getAttachmentById(map);
            Attachment attachment = listAttachment.get(0);
            String url = config.getImageUrl() + attachment.getAttPath();
            String s = FileBase64.GetImageStrFromUrl(url);
            attachment.setAttPath(s);
            return attachment;
        }
    }


    private List<EducationDto> getListUseEducations(List<Education> listEducation) {
        List<EducationDto> educationDtos = new ArrayList<>();
        for (int i = 0, j = listEducation.size(); i < j; i++) {
            EducationDto educationDto = new EducationDto();
            Education education = listEducation.get(i);
            educationDto.setId(education.getId());
            educationDto.setRowNum(education.getRowNum());
            educationDto.setEducationId(education.getEducationId());
            educationDto.setTitle(education.getTitle());
            String picId = education.getPicId();
            educationDto.setPicId(picId);
            educationDto.setMsg(education.getMsg());
            educationDto.setMsgPicId(education.getMsgPicId());
            educationDto.setIsdelete(education.getIsdelete());
            educationDto.setSort(education.getSort());
            educationDto.setCreateTime(education.getCreateTime());
            educationDto.setCreateTimeStr(education.getCreateTimeStr());
            educationDto.setUpdateTime(education.getUpdateTime());
            educationDto.setCreator(education.getCreator());
            educationDto.setUpdater(education.getUpdater());
            educationDto.setOther1(education.getOther1());
            educationDto.setOther2(education.getOther2());
            educationDto.setOther3(education.getOther3());
            List<Attachment> attList = attachmentService.getFileTransPathList(picId);
            educationDto.setAttachmentList(attList);
            educationDtos.add(educationDto);
        }
        return educationDtos;
    }

    //典藏精选
    @Override
    public JSONObject selectListIndexByWebCollect(String name, String typeId, String sonTypeId, String orderBy, String userId){
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(name)) {
                map.put("name", name);
            }
            map.put("typeId", typeId);
            map.put("sonTypeId", sonTypeId);
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            if (StringUtils.isNotBlank(userId)) {
                map.put("userId", userId);
            }
            List<Collect> listCollect = collectMapper.selectListIndexByWebCollect(map);
            List<CollectDto> listCollectDto = getListUseCollects(listCollect);
            String jsonString = JSON.toJSONString(listCollectDto, SerializerFeature.DisableCircularReferenceDetect);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);
            jsonObject.put("msg", "");
            jsonObject.put("page", page);
            jsonObject.put("data", jsonString);

        } catch (NumberFormatException e) {
            jsonObject.put("msg", "");
            jsonObject.put("data", Collections.EMPTY_LIST);
            jsonObject.put("error", e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public JSONObject selectListByWebCollect(String name, String typeId, String sonTypeId, String orderBy,String userId, Integer currentPage, Integer size){
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(name)) {
                map.put("name", name);
            }
            //if (StringUtils.isNotBlank(typeId)) {
            //    map.put("typeId", typeId);
            //}
            map.put("typeId", typeId);
            map.put("sonTypeId", sonTypeId);
            //if (StringUtils.isNotBlank(sonTypeId)) {
            //    map.put("sonTypeId", sonTypeId);
            //}
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            if (StringUtils.isNotBlank(userId)) {
                map.put("userId", userId);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            int count = collectMapper.selectCountByWebCollect(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<Collect> listCollect = collectMapper.selectListByWebCollect(map);
            List<CollectDto> listCollectDto = getListUseCollects(listCollect);
            String jsonString = JSON.toJSONString(listCollectDto, SerializerFeature.DisableCircularReferenceDetect);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);
            jsonObject.put("msg", "");
            jsonObject.put("page", page);
            jsonObject.put("data", jsonString);

        } catch (NumberFormatException e) {
            jsonObject.put("msg", "");
            jsonObject.put("data", Collections.EMPTY_LIST);
            jsonObject.put("error", e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public JSONObject selectListByWebCollectAndUser(String typeId,String userId, Integer currentPage, Integer size){
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(typeId)) {
                map.put("typeId", typeId);
            }
            map.put("userId",userId);
            page.setSize(size);
            page.setCurrentPage(currentPage);
            int count = collectMapper.countListByWebCollectAndUser(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<Collect> listCollect = collectMapper.selectListByWebCollectAndUser(map);
            List<CollectDto> listCollectDto = getListUseCollects(listCollect);
            String jsonString = JSON.toJSONString(listCollectDto, SerializerFeature.DisableCircularReferenceDetect);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);
            jsonObject.put("msg", "");
            jsonObject.put("page", page);
            jsonObject.put("data", jsonString);

        } catch (NumberFormatException e) {
            jsonObject.put("msg", "");
            jsonObject.put("data", Collections.EMPTY_LIST);
            jsonObject.put("error", e.getMessage());
        }
        return jsonObject;
    }




    @Override
    public JsonResult getOneWebCollect(String id) {
        Collect collect = new Collect();
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            } else {
                collect = collectMapper.selectWebByPrimaryKey(id);

                if (null == collect){
                    return new JsonResult(1,null);
                }
                collect.setMsg(imageUtilService.getContent(collect.getMsg(),"1"));
                if (StringUtils.isNotBlank(collect.getId())){
                    Map map = new HashMap();
                    CollectDto collectDto = new CollectDto();
                    collectDto.setCollect(collect);
                    String typeId = collect.getTypeId();
                    String sonTypeName = null;
                    String pictureids = collect.getPictureids();

                    if(StringUtils.isNotBlank(pictureids)){
                        List<Attachment> attList = attachmentService.getFileTransPathList(pictureids);
                        collectDto.setAttachmentList(attList);
                    }

                    if(collectDto.getCreateTime()!=null){
                        String appTimeStr = DateFormartUtil.stampToDateSimple(collect.getCreateTime());
                        collectDto.setAppCreateTimeStr(appTimeStr);
                    }
                    if(StringUtils.isNotBlank(typeId)){
                        String typeName = sysDictSevice.getDictById(typeId).getDictName();
                        collectDto.setTypeId(typeId);
                        collectDto.setType(typeName);
                    }

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

    /**
     * 查一个藏品
     *
     * @param id     藏品id
     * @param userId
     * @return
     */
    @Override
    public JsonResult getOneWebCollectAndUser(String id, String userId) {
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            } else {
                CollectDto collect = collectMapper.getCollectByIdAndUser(id,userId);
                if (collect.getId() != null) {
                    collect.setMsg(imageUtilService.getContent(collect.getMsg(),"1"));
                    Map map = new HashMap();
                    CollectDto collectDto = new CollectDto();
                    collectDto.setCollect(collect);
                    collectDto.setHasCollected(collect.getHasCollected());
                    String typeId = collect.getTypeId();
                    String sonTypeName = null;
                    String pictureids = collect.getPictureids();
                    List<Attachment> attList = attachmentService.getFileTransPathList(pictureids);
                    collectDto.setAttachmentList(attList);
                    if(collectDto.getCreateTime()!=null){
                        String appTimeStr = DateFormartUtil.stampToDateSimple(collect.getCreateTime());
                        collectDto.setAppCreateTimeStr(appTimeStr);
                    }
                    if(StringUtils.isNotBlank(typeId)){
                        String typeName = sysDictSevice.getDictById(typeId).getDictName();
                        collectDto.setTypeId(typeId);
                        collectDto.setType(typeName);
                    }

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
    public JsonResult insertSelectiveWeb(PostBookingManage record){
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }
          /*  if(StringUtils.isBlank(record.getContacts())){
                return new JsonResult(0,"姓名不能为空");
            }*/
            record.setId(IdUtils.getIncreaseIdByNanoTime());
            record.setUserId(Tools.getUserId());
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            record.setCreator(Tools.getUserId());
            record.setDataState("1");
            int count =  postBookingManageMapper.insertSelectiveWeb(record);
            if (count >0){
                return new JsonResult(1,null);
            }
            else{
                return new JsonResult(0,null,"200006");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"添加预约管理失败");
        }
    }

    @Override
    public JsonResult  themeshowWebList(String themeName, String themeSource,@RequestParam
            ("userId") String userId, String id, @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "1") Integer size)
            throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(themeName)) {
                map.put("themeName", themeName);
            }
            if (StringUtils.isNotBlank(themeSource)) {
                map.put("themeSource", themeSource);
            }
//                if (StringUtils.isNotBlank(userId)) {
//                    map.put("userId", userId);
//                }

                    map.put("id", id);
            map.put("userId", userId);
            page.setSize(size);
            page.setCurrentPage(currentPage);
            map.put("currentPage", page.getStart());    //
            map.put("size", page.getSize());
            int count = postThemeShowMapper.countWeb(map);
            page.setAllRow(count);

            List<PostThemeShow> postThemeShows = postThemeShowMapper.themeshowWebList(map);

            //查询分页数据

            if(postThemeShows != null){
                for (PostThemeShow  postThemeShow  : postThemeShows){
                    //app端时间处理年月日
                    String appTimeStr = DateFormartUtil.stampToDateSimple(postThemeShow.getCreateDate());
                    postThemeShow.setAppCreateTimeStr(appTimeStr);
                    //获取图片url
                    postThemeShow.setMainPicUrl("");
                    List<Attachment> picList = new ArrayList<Attachment>();
                    if(StringUtils.isNotBlank(postThemeShow.getDatumIds())){
                        String attId = postThemeShow.getDatumIds();
                        String[] attIds = attId.split(",");
                        String url =  request.getContextPath()+"/attach/getFileTransPath.do?id="+attIds[0];
                        postThemeShow.setMainPicUrl(url);
//                        if(null != picList && picList.size()>0){
//                            for (Attachment attachment : picList) {
//                                if ("1".equals(attachment.getIsMain())){
//                                    postThemeShow.setMainPicUrl(attachment.getAttPath());
//                                    break;
//                                }
//                            }
//                            postThemeShow.setPicList(picList);
//                        }else{
//                            postThemeShow.setPicList(null);
//                        }
                    }
                }
            }
            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }
    @Override
    public JsonResult getThemShow(String id){
        try {
            PostThemeShow postThemeShow = postThemeShowMapper.selectByPrimaryKey(id);
            String attId = postThemeShow.getDatumIds();
            String[] attIds = attId.split(",");
            String url =  request.getContextPath()+"/attach/getFileTransPath.do?id="+attIds[0];
            postThemeShow.setMainPicUrl(url);
            return new JsonResult(1,postThemeShow);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }




    @Override
    public JsonResult  themeshowWebRelationList(String themeName, String themeSource,@RequestParam
            ("userId") String userId, String id, @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "1") Integer size)
            throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(themeName)) {
                map.put("themeName", themeName);
            }
            if (StringUtils.isNotBlank(themeSource)) {
                map.put("themeSource", themeSource);
            }
//                if (StringUtils.isNotBlank(userId)) {
//                    map.put("userId", userId);
//                }
//                if (StringUtils.isNotBlank(id)) {
//                    map.put("userId", id);
//                }
            map.put("userId", userId);
            map.put("id", id);
            page.setSize(size);
            page.setCurrentPage(currentPage);
            map.put("currentPage", page.getStart());    //
            map.put("size", page.getSize());
            int count = postThemeShowMapper.countWebRelation(map);
            page.setAllRow(count);

            List<PostThemeShow> postThemeShows = postThemeShowMapper.themeshowWebListRelation(map);

            //查询分页数据

            if(postThemeShows != null){
                for (PostThemeShow  postThemeShow  : postThemeShows){
                    //app端时间处理年月日
                    String appTimeStr = DateFormartUtil.stampToDateSimple(postThemeShow.getCreateDate());
                    postThemeShow.setAppCreateTimeStr(appTimeStr);
                    //获取图片url
                    postThemeShow.setMainPicUrl("");
                    List<Attachment> picList = new ArrayList<Attachment>();
                    if(StringUtils.isNotBlank(postThemeShow.getDatumIds())){
                        picList = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
                        if(null != picList && picList.size()>0){
                            for (Attachment attachment : picList) {

                                if ("1".equals(attachment.getIsMain())){
                                    postThemeShow.setMainPicUrl(attachment.getAttPath());
                                    break;
                                }
                            }
                            postThemeShow.setPicList(picList);
                        }else{
                            postThemeShow.setPicList(null);
                        }
                    }
                }
            }


            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }






    @Override
    public JsonResult themeshowWebNoPages(String themeName, String themeSource)
            throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(themeName)) {
                map.put("themeName", themeName);
            }
            if (StringUtils.isNotBlank(themeSource)) {
                map.put("themeSource", themeSource);
            }


            List<PostThemeShow> postThemeShows = postThemeShowMapper.themeshowWebList(map);
            //查询分页数据
            if(postThemeShows != null){
                for (PostThemeShow  postThemeShow  : postThemeShows){

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
            }

            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult themeshowWebNoPages(String themeName, String themeSource,String userId)
            throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(themeName)) {
                map.put("themeName", themeName);
            }
            if (StringUtils.isNotBlank(themeSource)) {
                map.put("themeSource", themeSource);
            }
            map.put("userId",userId);
            List<PostThemeShow> postThemeShows = postThemeShowMapper.themeshowWebList(map);
            //查询分页数据
            if(postThemeShows != null){
                for (PostThemeShow  postThemeShow  : postThemeShows){
                    String attId = postThemeShow.getDatumIds();
                    String[] attIds = attId.split(",");
                    String url = request.getContextPath()+"/attach/getFileTransPath.do?id="+attIds[0];
                    postThemeShow.setMainPicUrl(url);
                    //获取图片url
//                    postThemeShow.setMainPicUrl("");
//                    List<Attachment> picList = new ArrayList<Attachment>();
//                    picList = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
//                    for (Attachment attachment : picList) {
//                        if ("1".equals(attachment.getIsMain())){
//                            postThemeShow.setMainPicUrl(attachment.getAttPath());
//                            break;
//                        }
//                    }
//                    postThemeShow.setPicList(picList);
                }
            }

            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult themeshowWebNoPagesPlus(String id,String userId)
            throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            List<CollectDto> postThemeShows = postThemeShowMapper.getCollectsByThemId(id,userId);
            //查询分页数据
            if(postThemeShows != null){
                for (CollectDto  postThemeShow  : postThemeShows){
                    String attId = postThemeShow.getPictureids();
                    String[] attIds = attId.split(",");
                    String url =  request.getContextPath()+"/attach/getFileTransPath.do?id="+attIds[0];
                    postThemeShow.setMainPicUrl(url);
                    //获取图片url
//                    postThemeShow.setMainPicUrl("");
//                    List<Attachment> picList = new ArrayList<Attachment>();
//                    picList = attachmentService.getFileTransPathList(postThemeShow.getPictureids());
//                    for (Attachment attachment : picList) {
//                        String url = attachment.getAttPath();
//                        if ("1".equals(attachment.getIsMain()) ||picList.size()==1){
//                            postThemeShow.setMainPicUrl(url);
//                            break;
//                        }
//                    }
//                    postThemeShow.setPicList(picList);
                }
            }
            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }


    @Override
    public JsonResult selectWebByPrimaryKey(String id) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostThemeShow postThemeShow = postThemeShowMapper.selectWebByPrimaryKey(id);

            //根据主题展id 查询相关的藏品list
            List<Attachment> picList1 = new ArrayList<Attachment>();
            List<Collect> collectList = new ArrayList<Collect>();
            collectList = postThemeShowMapper.getCollectsById(id);
            if(collectList.size()>0 && collectList!=null){
                for(Collect  collect: collectList){
                    picList1 = attachmentService.getFileTransPathList(collect.getPictureids());
                    collect.setPicList(picList1);
                }
                postThemeShow.setCollectionList(collectList);
                //app端时间处理   年月日
                String appTimeStr = DateFormartUtil.stampToDateSimple(postThemeShow.getCreateDate());
                postThemeShow.setAppCreateTimeStr(appTimeStr);
                if(!StringUtils.isBlank(postThemeShow.getDatumIds())){
                    //获取图片url
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
            }

            return new JsonResult(1,postThemeShow);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult themeshowWebztList(String themeName, String themeSource, String id, @RequestParam
            (defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "3") Integer size) throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(themeName)) {
                map.put("themeName", themeName);
            }
            if (StringUtils.isNotBlank(themeSource)) {
                map.put("themeSource", themeSource);
            }
            if (StringUtils.isNotBlank(id)) {
                map.put("id", id);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            map.put("currentPage", page.getStart());    //
            map.put("size", page.getSize());
            int count = postThemeShowMapper.countWebzt(map);
            page.setAllRow(count);
            List<PostThemeShow> postThemeShows = postThemeShowMapper.themeshowWebztList(map);

            //查询分页数据
            if(postThemeShows != null){
                for (PostThemeShow  postThemeShow  : postThemeShows){
                    //app端时间处理    年月日
                    String appTimeStr = DateFormartUtil.stampToDateSimple(postThemeShow.getCreateDate());
                    postThemeShow.setAppCreateTimeStr(appTimeStr);
                    //获取图片url
                    postThemeShow.setMainPicUrl("");
                    List<Attachment> picList = new ArrayList<Attachment>();
                    if(StringUtils.isNotBlank(postThemeShow.getDatumIds())){
                        picList = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
                        for (Attachment attachment : picList) {
                            if ("1".equals(attachment.getIsMain())){
                                postThemeShow.setMainPicUrl(attachment.getAttPath());
                                break;
                            }
                        }
                        postThemeShow.setPicList(picList);
                    }

                }
            }


            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult themeshowWebztList(String themeName, String themeSource, String id,String userId, @RequestParam
            (defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "3") Integer size) throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(themeName)) {
                map.put("themeName", themeName);
            }
            if (StringUtils.isNotBlank(themeSource)) {
                map.put("themeSource", themeSource);
            }
            if (StringUtils.isNotBlank(id)) {
                map.put("id", id);
            }
            map.put("selectedTopics",1);
            map.put("userId",userId);
            page.setSize(size);
            page.setCurrentPage(currentPage);
            map.put("currentPage", page.getStart());    //
            map.put("size", page.getSize());
            int count = postThemeShowMapper.countWebzt(map);
            page.setAllRow(count);
            List<PostThemeShow> postThemeShows = postThemeShowMapper.themeshowWebztList(map);

            //查询分页数据
            if(postThemeShows != null){
                for (PostThemeShow  postThemeShow  : postThemeShows){
                    //app端时间处理    年月日
                    String appTimeStr = DateFormartUtil.stampToDateSimple(postThemeShow.getCreateDate());
                    postThemeShow.setAppCreateTimeStr(appTimeStr);
                    //获取图片url
                    postThemeShow.setMainPicUrl("");
                    List<Attachment> picList = new ArrayList<Attachment>();
                    if(StringUtils.isNotBlank(postThemeShow.getDatumIds())){
                        picList = attachmentService.getFileTransPathList(postThemeShow.getDatumIds());
                        for (Attachment attachment : picList) {
                            if ("1".equals(attachment.getIsMain())){
                                postThemeShow.setMainPicUrl(attachment.getAttPath());
                                break;
                            }
                        }
                        postThemeShow.setPicList(picList);
                    }

                }
            }


            return new JsonResult(1,postThemeShows);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult themeshowsyWebztList(String themeName, String themeSource, @RequestParam
            (defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "1") Integer size) throws Exception {
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(themeName)) {
                map.put("themeName", themeName);
            }
            if (StringUtils.isNotBlank(themeSource)) {
                map.put("themeSource", themeSource);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            int count = postThemeShowMapper.countsyWeb(map);
            page.setAllRow(count);
            map.put("currentPage", page.getStart());    //
            map.put("size", page.getSize());
            PostThemeShow postThemeShow = postThemeShowMapper.themeshowsyWebList(map);

            //查询分页数据
            if(postThemeShow != null){
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


            return new JsonResult(1,postThemeShow);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    /**
     * 查询精选专题
     *
     * @param userId 用户id
     * @param page   分页
     * @return
     */
    @Override
    public JsonResult getJxztList(String userId, Page page) {
        HashMap map = new HashMap();
        try {
            map.put("userId",userId);
            map.put("selectedTopics",1);
            if (null != page){
                int count = postThemeShowMapper.countJxztList(map);
                page.setAllRow(count);
                map.put("currentPage", page.getStart());    //
                map.put("size", page.getSize());
            }
            List<PostThemeShow> postThemeShows = postThemeShowMapper.getJxztList(map);

            //查询分页数据
            if(postThemeShows != null && postThemeShows.size()>0){
                for (PostThemeShow postThemeShow : postThemeShows) {
//                    List<Attachment> picList = new ArrayList<Attachment>();
                    String attId = postThemeShow.getDatumIds();
                   String[] attIds = attId.split(",");
                    String url =  request.getContextPath()+"/attach/getFileTransPath.do?id="+attIds[0];
                    postThemeShow.setMainPicUrl(url);
//                    postThemeShow.setPicList(picList);
                }
                return new JsonResult(1,postThemeShows);
            }else {
                return new JsonResult(1,null);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult getInformationWebList(String informationType, String createTime,String orderBy, Page page) throws Exception {
        try {
            int count = postInformationManageMapper.countWeb(informationType,createTime,orderBy,page.getCurrentPage(), page.getSize());
            page.setAllRow(count);
            List<PostInformationManage> informationList = postInformationManageMapper.getInformationWebList(informationType, createTime,
                    orderBy,page.getStart(), page.getSize());
            //查询分页数据
            List<Attachment> picList = new ArrayList<Attachment>();
            if (null != informationList && informationList.size()>0) {
                for (PostInformationManage postInformationManage : informationList) {
                    //时间处理
                    Date createTime1 = postInformationManage.getCreateTime();
                    String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                    String appTimeStr = DateFormartUtil.stampToDateSimple(postInformationManage.getCreateTime());
                    postInformationManage.setCreateTimeStr(timeStr);
                    postInformationManage.setAppCreateTimeStr(appTimeStr);//app端展示时间  年月日
                    String[] times = timeStr.split("-");
                    postInformationManage.setCreateTimeYear(times[0]);
                    postInformationManage.setCreateTimeMonthDay(times[1] + "-" + times[2].substring(0, 2));
                    String txtcontent = postInformationManage.getInformationContent();
                    if (!MyString.isEmpty(txtcontent)) {
                        //剔出<html>的标签
                        txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                        //去除字符串中的空格,回车,换行符,制表符
                        txtcontent = txtcontent.replaceAll("&nbsp;", "");
                        txtcontent = txtcontent.trim();
                    }
                    txtcontent = imageUtilService.getContent(txtcontent,"1");
                    postInformationManage.setInformationContent(txtcontent);
                    //获取图片url
                    picList = attachmentService.getFileTransPathList(postInformationManage.getDatumIds());
                    if (null != picList && picList.size() > 0) {
                        for (Attachment attachment : picList) {
                            if ("1".equals(attachment.getIsMain())) {
                                postInformationManage.setMainPicUrl(attachment.getAttPath());
                                break;
                            }
                        }
                        postInformationManage.setPicList(picList);
                    } else {
                        postInformationManage.setPicList(null);
                    }
                }
            }
            return new JsonResult(1,informationList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,Collections.EMPTY_LIST,"70000012");
        }
    }


    @Override
    public JsonResult selectWebInfoByPrimaryKey(String id) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"000020");
            }
            PostInformationManage postInformationManage = postInformationManageMapper.selectWebInfoByPrimaryKey(id);
            postInformationManage.setInformationContent(imageUtilService.getContent(postInformationManage.getInformationContent(),"1"));
            if(postInformationManage.getCreateTime()!=null){
                Date createTime1 = postInformationManage.getCreateTime();
                String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                postInformationManage.setCreateTimeStr(timeStr);
                String[] times= timeStr.split("-");
                postInformationManage.setCreateTimeYear(times[0]);
                postInformationManage.setCreateTimeMonthDay(times[1]+"-"+times[2]);
            }
            if(postInformationManage.getCreateTime()!=null){
                //app端展示时间  年月日
                String appTimeStr = DateFormartUtil.stampToDateSimple(postInformationManage.getCreateTime());
                postInformationManage.setAppCreateTimeStr(appTimeStr);
            }

          /*  String txtcontent = postInformationManage.getInformationContent();
            if (!MyString.isEmpty(txtcontent)) {
                //剔出<html>的标签
                txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                //去除字符串中的空格,回车,换行符,制表符
                txtcontent = txtcontent.replaceAll("&nbsp;", "");
            }
            postInformationManage.setInformationContent(txtcontent);*/
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
            return new JsonResult(1,postInformationManage);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getStampStoryWebList(String storyType, String createTime, Page page) throws Exception {
        try {
            int count = postStampStoryMapper.countWeb(storyType,createTime,page.getCurrentPage(), page.getSize());
            page.setAllRow(count);
            List<PostStampStory> stampStoryList = postStampStoryMapper.getStampStoryWebList(storyType, createTime, page.getStart(),
                    page.getSize());
            //查询分页数据

            for (PostStampStory postStampStory : stampStoryList){
                //时间处理
                Date createTime1 = postStampStory.getCreateTime();
                String timeStr = DateFormartUtil.stampToDateSimple(createTime1);
                postStampStory.setCreateTimeStr(timeStr);
                String[] times= timeStr.split("-");
                postStampStory.setCreateTimeYear(times[0]);
                postStampStory.setCreateTimeMonthDay(times[1]+"-"+times[2]);
                if("1".equals(postStampStory.getStoryType())){
                    postStampStory.setStoryTypeStr("名著");
                }else if("2".equals(postStampStory.getStoryType())){
                    postStampStory.setStoryTypeStr("孝道");
                }else if("3".equals(postStampStory.getStoryType())){
                    postStampStory.setStoryTypeStr("历史人物");
                }else if("4".equals(postStampStory.getStoryType())){
                    postStampStory.setStoryTypeStr("爱情");
                }else if("5".equals(postStampStory.getStoryType())){
                    postStampStory.setStoryTypeStr("纪念日");
                }else if("6".equals(postStampStory.getStoryType())){
                    postStampStory.setStoryTypeStr("革命战争");
                }else if("0".equals(postStampStory.getStoryType())){
                    postStampStory.setStoryTypeStr("全部");
                }

                if(StringUtils.isNotBlank(postStampStory.getStoryContent())){
                    //富文本去除html
                    String txtcontent = postStampStory.getStoryContent();
                    if (!MyString.isEmpty(txtcontent)) {
                        //剔出<html>的标签
                        txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                        //去除字符串中的空格,回车,换行符,制表符
                        txtcontent = txtcontent.replaceAll("&nbsp;", "");

                        txtcontent = txtcontent.trim();
                    }
                    txtcontent = imageUtilService.getContent(txtcontent,"1");
                    postStampStory.setStoryContent(txtcontent);
                }



                //获取图片url
                postStampStory.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(postStampStory.getDatumIds());
                for (Attachment attachment : picList) {
                    if ("1".equals(attachment.getIsMain())){
                        postStampStory.setMainPicUrl(attachment.getAttPath());
                        break;
                    }
                }
                postStampStory.setPicList(picList);
            }

            return new JsonResult(1,stampStoryList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"60000012");
        }
    }

    @Override
    public JsonResult selectWebStoyrByPrimaryKey(String id) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostStampStory postStampStory = postStampStoryMapper.selectWebStoyrByPrimaryKey(id);
            //时间处理
            postStampStory.setStoryContent(imageUtilService.getContent(postStampStory.getStoryContent(),"1"));
            Date createTime1 = postStampStory.getCreateTime();
            String timeStr = DateFormartUtil.stampToDateSimple(createTime1);
            postStampStory.setCreateTimeStr(timeStr);
            String[] times= timeStr.split("-");
            postStampStory.setCreateTimeYear(times[0]);
            postStampStory.setCreateTimeMonthDay(times[1]+"-"+times[2]);
            if("1".equals(postStampStory.getStoryType())){
                postStampStory.setStoryTypeStr("名著");
            }else if("2".equals(postStampStory.getStoryType())){
                postStampStory.setStoryTypeStr("孝道");
            }else if("3".equals(postStampStory.getStoryType())){
                postStampStory.setStoryTypeStr("历史人物");
            }else if("4".equals(postStampStory.getStoryType())){
                postStampStory.setStoryTypeStr("爱情");
            }else if("5".equals(postStampStory.getStoryType())){
                postStampStory.setStoryTypeStr("纪念日");
            }else if("6".equals(postStampStory.getStoryType())){
                postStampStory.setStoryTypeStr("革命战争");
            }else if("0".equals(postStampStory.getStoryType())){
                postStampStory.setStoryTypeStr("全部");
            }


           /* if(StringUtils.isNotBlank(postStampStory.getStoryContent())){
                //富文本去除html
                String txtcontent = postStampStory.getStoryContent();
                if (!MyString.isEmpty(txtcontent)) {
                    //剔出<html>的标签
                    txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                    //去除字符串中的空格,回车,换行符,制表符
                    txtcontent = txtcontent.replaceAll("&nbsp;", "");
                }
                postStampStory.setStoryContent(txtcontent);
            }*/



            if(!StringUtils.isBlank(postStampStory.getDatumIds())){
                //获取图片url
                postStampStory.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                picList = attachmentService.getFileTransPathList(postStampStory.getDatumIds());
                for (Attachment attachment : picList) {
                    postStampStory.setMainPicUrl(attachment.getAttPath());
                }
                postStampStory.setPicList(picList);
            }

            return new JsonResult(1,postStampStory);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult selectWebReserarchByKey(String id) {
        Research research = null;
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            }
            if(StringUtils.isNotBlank(id)){
                research = researchMapper.selectWebReserarchByKey(id);
                research.setMsg(imageUtilService.getContent(research.getMsg(),"1"));
                if(research !=null){
                    //时间处理
                    Date createTime1 = research.getCreateTime();
                    String timeStr = DateFormartUtil.stampToDateSimple(createTime1);
                    research.setCreateTimeStr(timeStr);
                    String[] times= timeStr.split("-");
                    research.setCreateTimeYear(times[0]);
                    research.setCreateTimeMonthDay(times[1]+"-"+times[2]);

                       /*String txtcontent =  research.getMsg();
                       if (!MyString.isEmpty(txtcontent)) {
                           //剔出<html>的标签
                           txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                           //去除字符串中的空格,回车,换行符,制表符
                           txtcontent = txtcontent.replaceAll("&nbsp;", "");
                       }
                       research.setMsg(txtcontent);*/
                }
            }

            return new JsonResult(1, research);

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "20000003");
        }
    }


    @Override
    public JSONObject selectListWebByResearch(String startTime, String endTime, String orderBy, Integer currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();

        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(startTime)) {
                map.put("startTime", startTime);
            }
            if (StringUtils.isNotBlank(endTime)) {
                map.put("endTime", endTime);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Integer count = researchMapper.countWebResearch(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<Research> listResearch = researchMapper.selectListWebByResearch(map);

            for(Research  research : listResearch){
                //时间处理
                Date createTime1 = research.getCreateTime();
                String timeStr = DateFormartUtil.stampToDateSimple(createTime1);
                research.setCreateTimeStr(timeStr);
                String[] times= timeStr.split("-");
                research.setCreateTimeYear(times[0]);
                research.setCreateTimeMonthDay(times[1]+"-"+times[2]);
                //富文本处理
                String txtcontent =  research.getMsg();
                if (!MyString.isEmpty(txtcontent)) {
                    //剔出<html>的标签
                    txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                    //去除字符串中的空格,回车,换行符,制表符
                    txtcontent = txtcontent.replaceAll("&nbsp;", "");

                    txtcontent = txtcontent.trim();
                }
                txtcontent = imageUtilService.getContent(txtcontent,"1");
                research.setMsg(txtcontent);


            }


            String jsonString = JSON.toJSONString(listResearch);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);
            jsonObject.put("msg", "");
            //jsonObject.put("count", page.getAllRow());
            jsonObject.put("page", page);
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
    public JsonResult selectWebEduByKey(String id){
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            } else {
                Education education = educationMapper.selectWebEduByKey(id);
                EducationDto educationDto = new EducationDto();
                if(StringUtils.isNotBlank(education.getId())){
                    educationDto.setId(education.getId());
                }
                educationDto.setEducationId(education.getEducationId());
                educationDto.setTitle(education.getTitle());
                String picId = education.getPicId();
                educationDto.setPicId(picId);
                educationDto.setMsg(education.getMsg());
                educationDto.setMsgPicId(education.getMsgPicId());
                educationDto.setSort(education.getSort());

                //时间处理
                Date createTime1 = education.getCreateTime();
                String timeStr = DateFormartUtil.stampToDateSimple(createTime1);
                educationDto.setCreateTimeStr(timeStr);
                String[] times= timeStr.split("-");
                educationDto.setCreateTimeYear(times[0]);
                educationDto.setCreateTimeMonthDay(times[1]+"-"+times[2]);
                //富文本处理
             /*   String txtcontent =  education.getMsg();
                if (!MyString.isEmpty(txtcontent)) {
                    //剔出<html>的标签
                    txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                    //去除字符串中的空格,回车,换行符,制表符
                    txtcontent = txtcontent.replaceAll("&nbsp;", "");
                }
                educationDto.setMsg(txtcontent);*/

                educationDto.setCreateTime(education.getCreateTime());
                educationDto.setUpdateTime(education.getUpdateTime());
                educationDto.setCreator(education.getCreator());
                educationDto.setUpdater(education.getUpdater());
                educationDto.setOther1(education.getOther1());
                educationDto.setOther2(education.getOther2());
                educationDto.setOther3(education.getOther3());
                educationDto.setMsg(imageUtilService.getContent(educationDto.getMsg(),"1"));

                List<Attachment> attList = attachmentService.getFileTransPathList(picId);
                if (attList.size() > 0) {
                    for (Attachment attachment : attList){
                        if (!attachment.getAttPath().contains(config.getRootUrl())) {
                            educationDto.setPicId((attachment.getAttPath()));
                        }
                    }
                    education.setPicList(attList);
                }
                educationDto.setAttachmentList(attList);
                if (education.getId() != null) {
                    return new JsonResult(1, educationDto);
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
    public JSONObject selectListWebByEducation(String title, String startTime, String endTime, String orderBy, Integer currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        String  jsonString = null;
        Map map = new HashMap();
        try {
            Page page = new Page();
            if (StringUtils.isNotBlank(title)) {
                map.put("title", title);
            }
            if (StringUtils.isNotBlank(startTime)) {
                map.put("startTime", startTime);
            }
            if (StringUtils.isNotBlank(endTime)) {
                map.put("endTime", endTime);
            }
            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            Integer count = educationMapper.countWebEdu(map);
            page.setAllRow(count);

            List<Education> listEducation = educationMapper.selectListWebByEducation(map);


            //List<EducationDto> listEducationDto = getListUseEducations(listEducation);
            for (Education  education: listEducation) {
                //获取图片url
                education.setMainPicUrl("");
                List<Attachment> picList = new ArrayList<Attachment>();
                if (StringUtils.isNotBlank(education.getPicId())) {
                    picList = attachmentService.getFileTransPathList(education.getPicId());
                    if (picList.size() > 0) {
                        for (Attachment attachment : picList) {
                            education.setMainPicUrl(attachment.getAttPath());

                        }
                        education.setPicList(picList);
                    }

                    //时间处理
                    Date createTime1 = education.getCreateTime();
                    String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                    education.setCreateTimeStr(timeStr);
                    String[] times = timeStr.split("-");
                    education.setCreateTimeYear(times[0] + "-" + times[1] + "-" + times[2].substring(0, 2));
                    education.setCreateTimeMonthDay(times[2].substring(2));

                    //富文本处理
                    String txtcontent =  education.getMsg();
                    if (!MyString.isEmpty(txtcontent)) {
                        //剔出<html>的标签
                        txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                        //去除字符串中的空格,回车,换行符,制表符
                        txtcontent = txtcontent.replaceAll("&nbsp;", "");
                        txtcontent = txtcontent.replaceAll("&ensp;", "");
                        txtcontent = txtcontent.replaceAll("&emsp;", "");
                        txtcontent = txtcontent.trim();
                        txtcontent = txtcontent.replaceAll("\\s","");
                        txtcontent = txtcontent.replaceAll("\\s*|\t|\r|\n","");
                        Pattern p = Pattern.compile("\\*s|\t|\r|\n");
                        Matcher m = p.matcher(txtcontent);
                        txtcontent= m.replaceAll("");
                    }
                    txtcontent = imageUtilService.getContent(txtcontent,"1");
                    education.setMsg(txtcontent);


                }
            }

            jsonString = JSON.toJSONString(listEducation);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);
            jsonObject.put("page", page);
            jsonObject.put("msg", "");
            jsonObject.put("data", jsonString);

        }catch(NumberFormatException e) {
            jsonObject.put("msg", "");
            jsonObject.put("data", null);
            jsonObject.put("error", e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public JsonResult getHomeWebList(String collectHomeTheme, String createTime, Page page) throws Exception {
        try {
            int count = postCollectHomeMapper.countWeb(collectHomeTheme,createTime,page.getCurrentPage(), page.getSize());
            page.setAllRow(count);
            List<PostCollectHome> homeList = postCollectHomeMapper.getHomeWebList(collectHomeTheme, createTime,
                    page.getStart(), page.getSize());
            for (PostCollectHome postCollectHome : homeList) {
                //时间处理
                Date createTime1 = postCollectHome.getCreateTime();
                String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                postCollectHome.setCreateTimeStr(timeStr);
                String[] times = timeStr.split("-");
                postCollectHome.setCreateTimeYear(times[0]);
                postCollectHome.setCreateTimeMonthDay(times[1] + "-" + times[2]);
                String txtcontent = postCollectHome.getInformationContent();

                if (!MyString.isEmpty(txtcontent)) {
                    //剔出<html>的标签
                    txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                    //去除字符串中的空格,回车,换行符,制表符
                    txtcontent = txtcontent.replaceAll("&nbsp;", "");
                    txtcontent = txtcontent.trim();
                }
                txtcontent = imageUtilService.getContent(txtcontent,"1");
                postCollectHome.setInformationContent(txtcontent);
            }
            return new JsonResult(1,homeList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult selectByWebHome(String id) throws Exception {
        try {
            if(org.apache.commons.lang3.StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostCollectHome postCollectHome = postCollectHomeMapper.selectByWebHome(id);
            postCollectHome.setInformationContent(imageUtilService.getContent(postCollectHome.getInformationContent(),"1"));
            Date createTime1 = postCollectHome.getCreateTime();
            String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
            postCollectHome.setCreateTimeStr(timeStr);
            String[] times = timeStr.split("-");
            postCollectHome.setCreateTimeYear(times[0]);
            postCollectHome.setCreateTimeMonthDay(times[1] + "-" + times[2]);
          /*  String txtcontent = postCollectHome.getInformationContent();
            if (!MyString.isEmpty(txtcontent)) {
                //剔出<html>的标签
                txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                //去除字符串中的空格,回车,换行符,制表符
                txtcontent = txtcontent.replaceAll("&nbsp;", "");
            }
            postCollectHome.setInformationContent(txtcontent);*/
            return new JsonResult(1,postCollectHome);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getOnePageWebResearch(Integer currentPage){

        try {
            Map<String,Object> resultMap = new HashMap<String,Object>();
            //当前页信息
            if(currentPage-1>=0){
                Research researchInfo =  researchMapper.getDetailResearchById(currentPage-1);
                researchInfo.setMsg(imageUtilService.getContent(researchInfo.getMsg(),"1"));
                resultMap.put("researchInfo",researchInfo);
            }else{
                return new JsonResult(0,"参数异常");
            }

            //上一页
            if(currentPage-2>=0){
                Research researchInfoLast =  researchMapper.getDetailResearchById(currentPage-2);
                if(null != researchInfoLast) {
                    researchInfoLast.setMsg(imageUtilService.getContent(researchInfoLast.getMsg(), "1"));
                }
                resultMap.put("researchInfoLast",researchInfoLast);
            }

            //下一页
            if(currentPage>=0){
                Research researchInfoNext =  researchMapper.getDetailResearchById(currentPage);
                if(null != researchInfoNext){
                    researchInfoNext.setMsg(imageUtilService.getContent(researchInfoNext.getMsg(),"1"));
                }
                resultMap.put("researchInfoNext",researchInfoNext);
            }
            return new JsonResult(1,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getOnePageWebInfo(Integer currentPage,String informationType){
        try {
            Map<String,Object> resultMap = new HashMap<String,Object>();
            //当前页信息
            if(currentPage-1>=0){
                if("1".equals(informationType)){
                    PostInformationManage informationManage = postInformationManageMapper.getDetailInfoById(currentPage -
                            1,null);
                    if(null != informationManage){
                        informationManage.setInformationContent(imageUtilService.getContent(informationManage.getInformationContent(),"1"));
                    }
                    resultMap.put("informationManage",informationManage);
                }else{
                    PostInformationManage informationManage = postInformationManageMapper.getDetailInfoById(currentPage -
                            1,informationType);
                    if(null != informationManage) {
                        informationManage.setInformationContent(imageUtilService.getContent(informationManage.getInformationContent(), "1"));
                    }
                    resultMap.put("informationManage", informationManage);
                }

            }else{
                return new JsonResult(0,"参数异常");
            }

            //上一页
            if(currentPage-2>=0){
                if("1".equals(informationType)){
                    PostInformationManage infoManageLast = postInformationManageMapper.getDetailInfoById(currentPage
                            - 2,null);
                    if(null != infoManageLast) {
                        infoManageLast.setInformationContent(imageUtilService.getContent(infoManageLast.getInformationContent(), "1"));
                    }
                    resultMap.put("infoManageLast", infoManageLast);
                }else{

                        PostInformationManage infoManageLast = postInformationManageMapper.getDetailInfoById(currentPage - 2, informationType);
                    if(null != infoManageLast) {
                        infoManageLast.setInformationContent(imageUtilService.getContent(infoManageLast.getInformationContent(), "1"));
                    }
                    resultMap.put("infoManageLast", infoManageLast);
                }

            }

            //下一页
            if(currentPage>=0){
                if("1".equals(informationType)){
                    PostInformationManage infoManageNext = postInformationManageMapper.getDetailInfoById(currentPage,
                            null);
                    if(null != infoManageNext) {
                        infoManageNext.setInformationContent(imageUtilService.getContent(infoManageNext.getInformationContent(), "1"));
                    }
                    resultMap.put("infoManageNext",infoManageNext);
                }else{
                    PostInformationManage infoManageNext = postInformationManageMapper.getDetailInfoById(currentPage,informationType);
                    if(null != infoManageNext) {
                        infoManageNext.setInformationContent(imageUtilService.getContent(infoManageNext.getInformationContent(), "1"));
                    }
                    resultMap.put("infoManageNext",infoManageNext);
                }
            }
            return new JsonResult(1,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getDetailStoryById(Integer currentPage,String  storyType) {
        try {
            Map<String,Object> resultMap = new HashMap<String,Object>();
            //当前页信息
            if(currentPage-1>=0){
               /* if("1".equals(storyType)){
                    PostStampStory postStampStory = postStampStoryMapper.getDetailStoryById(currentPage - 1,null);
                    resultMap.put("postStampStory",postStampStory);
                }*/
                PostStampStory postStampStory = postStampStoryMapper.getDetailStoryById(currentPage - 1,storyType);
                if(postStampStory!=null){
                    if("1".equals(postStampStory.getStoryType())){
                        postStampStory.setStoryTypeStr("名著");
                    }else if("2".equals(postStampStory.getStoryType())){
                        postStampStory.setStoryTypeStr("孝道");
                    }else if("3".equals(postStampStory.getStoryType())){
                        postStampStory.setStoryTypeStr("历史人物");
                    }else if("4".equals(postStampStory.getStoryType())){
                        postStampStory.setStoryTypeStr("爱情");
                    }else if("5".equals(postStampStory.getStoryType())){
                        postStampStory.setStoryTypeStr("纪念日");
                    }else if("6".equals(postStampStory.getStoryType())){
                        postStampStory.setStoryTypeStr("革命战争");
                    }else if("0".equals(postStampStory.getStoryType())){
                        postStampStory.setStoryTypeStr("全部");
                    }
                    postStampStory.setStoryContent(imageUtilService.getContent(postStampStory.getStoryContent(),"1"));
                    resultMap.put("postStampStory",postStampStory);
                }

            }else{
                return new JsonResult(0,"参数异常");
            }

            //上一页
            if(currentPage-2>=0){
               /* if("1".equals(storyType)){
                    PostStampStory postStampStory = postStampStoryMapper.getDetailStoryById(currentPage - 1,null);
                    resultMap.put("postStampStory",postStampStory);
                }*/
                PostStampStory postStampStoryLast = postStampStoryMapper.getDetailStoryById(currentPage - 2,storyType);

                if( postStampStoryLast!=null){
                    postStampStoryLast.setStoryContent(imageUtilService.getContent(postStampStoryLast.getStoryContent(),"1"));
                    if("1".equals(postStampStoryLast.getStoryType())){
                        postStampStoryLast.setStoryTypeStr("名著");
                    }else if("2".equals(postStampStoryLast.getStoryType())){
                        postStampStoryLast.setStoryTypeStr("孝道");
                    }else if("3".equals(postStampStoryLast.getStoryType())){
                        postStampStoryLast.setStoryTypeStr("历史人物");
                    }else if("4".equals(postStampStoryLast.getStoryType())){
                        postStampStoryLast.setStoryTypeStr("爱情");
                    }else if("5".equals(postStampStoryLast.getStoryType())){
                        postStampStoryLast.setStoryTypeStr("纪念日");
                    }else if("6".equals(postStampStoryLast.getStoryType())){
                        postStampStoryLast.setStoryTypeStr("革命战争");
                    }else if("0".equals(postStampStoryLast.getStoryType())){
                        postStampStoryLast.setStoryTypeStr("全部");
                    }
                    resultMap.put("postStampStoryLast",postStampStoryLast);
                }

            }

            //下一页
            if(currentPage>=0){
              /*  if("1".equals(storyType)){
                    PostStampStory postStampStory = postStampStoryMapper.getDetailStoryById(currentPage - 1,null);
                    resultMap.put("postStampStory",postStampStory);
                }*/
                PostStampStory postStampStoryNext = postStampStoryMapper.getDetailStoryById(currentPage,storyType);
                if(postStampStoryNext!=null){
                    postStampStoryNext.setStoryContent(imageUtilService.getContent(postStampStoryNext.getStoryContent(),"1"));
                    if("1".equals(postStampStoryNext.getStoryType())){
                        postStampStoryNext.setStoryTypeStr("名著");
                    }else if("2".equals(postStampStoryNext.getStoryType())){
                        postStampStoryNext.setStoryTypeStr("孝道");
                    }else if("3".equals(postStampStoryNext.getStoryType())){
                        postStampStoryNext.setStoryTypeStr("历史人物");
                    }else if("4".equals(postStampStoryNext.getStoryType())){
                        postStampStoryNext.setStoryTypeStr("爱情");
                    }else if("5".equals(postStampStoryNext.getStoryType())){
                        postStampStoryNext.setStoryTypeStr("纪念日");
                    }else if("6".equals(postStampStoryNext.getStoryType())){
                        postStampStoryNext.setStoryTypeStr("革命战争");
                    }else if("0".equals(postStampStoryNext.getStoryType())){
                        postStampStoryNext.setStoryTypeStr("全部");
                    }
                    resultMap.put("postStampStoryNext",postStampStoryNext);
                }

            }
            return new JsonResult(1,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getDetailEducationById(Integer currentPage) {
        try {
            Map<String,Object> resultMap = new HashMap<String,Object>();
            //当前页信息
            if(currentPage-1>=0){
                Education educationInfo = educationMapper.getDetailEducationById(currentPage - 1);

                    //时间处理
                    if(educationInfo!=null){
                        Date createTime1 = educationInfo.getCreateTime();
                        if(createTime1!=null){
                            String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                            educationInfo.setCreateTimeStr(timeStr);
                            String[] times = timeStr.split("-");
                            educationInfo.setCreateTimeYear(times[0] + "-" + times[1] + "-" + times[2].substring(0, 2));
                            educationInfo.setCreateTimeMonthDay(times[2].substring(2));
                        }
                        educationInfo.setMsg(imageUtilService.getContent(educationInfo.getMsg(),"1"));
                    }
                resultMap.put("educationInfo",educationInfo);
            }else{
                return new JsonResult(0,"参数异常");
            }

            //上一页
            if(currentPage-2>=0){
                Education educationLast = educationMapper.getDetailEducationById(currentPage - 2);

                //时间处理
                if(educationLast!=null){
                    Date createTime1 = educationLast.getCreateTime();
                    if(createTime1!=null){
                        String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                        educationLast.setCreateTimeStr(timeStr);
                        String[] times = timeStr.split("-");
                        educationLast.setCreateTimeYear(times[0] + "-" + times[1] + "-" + times[2].substring(0, 2));
                        educationLast.setCreateTimeMonthDay(times[2].substring(2));
                    }
                    educationLast.setMsg(imageUtilService.getContent(educationLast.getMsg(),"1"));
                }



                //富文本处理
               /* String txtcontent =  educationLast.getMsg();
                if (!MyString.isEmpty(txtcontent)) {
                    //剔出<html>的标签
                    txtcontent = txtcontent.replaceAll("</?[^>]+>", "");
                    //去除字符串中的空格,回车,换行符,制表符
                    txtcontent = txtcontent.replaceAll("&nbsp;", "");
                }
                educationLast.setMsg(txtcontent);*/

                resultMap.put("educationLast",educationLast);
            }

            //下一页
            if(currentPage>=0){
                Education educationNext = educationMapper.getDetailEducationById(currentPage);
                //时间处理
                if(educationNext!=null){
                    Date createTime1 = educationNext.getCreateTime();

                    if(createTime1!=null){
                        String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                        educationNext.setCreateTimeStr(timeStr);
                        String[] times = timeStr.split("-");
                        educationNext.setCreateTimeYear(times[0] + "-" + times[1] + "-" + times[2].substring(0, 2));
                        educationNext.setCreateTimeMonthDay(times[2].substring(2));
                    }
                    educationNext.setMsg(imageUtilService.getContent(educationNext.getMsg(),"1"));
                }


                resultMap.put("educationNext",educationNext);
            }
            return new JsonResult(1,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getDetailHomeById(Integer currentPage) {
        try {
            Map<String,Object> resultMap = new HashMap<String,Object>();
            //当前页信息
            if(currentPage-1>=0){
                PostCollectHome homeInfo = postCollectHomeMapper.getDetailHomeById(currentPage - 1);
                homeInfo.setInformationContent(imageUtilService.getContent(homeInfo.getInformationContent(),"1"));
                resultMap.put("homeInfo",homeInfo);
            }else{
                return new JsonResult(0,"参数异常");
            }

            //上一页
            if(currentPage-2>=0){
                PostCollectHome  postHomeLast = postCollectHomeMapper.getDetailHomeById(currentPage - 2);
                postHomeLast.setInformationContent(imageUtilService.getContent(postHomeLast.getInformationContent(),"1"));
                resultMap.put("postHomeLast",postHomeLast);
            }

            //下一页
            if(currentPage>=0){
                PostCollectHome postHomeNext = postCollectHomeMapper.getDetailHomeById(currentPage);
                postHomeNext.setInformationContent(imageUtilService.getContent(postHomeNext.getInformationContent(),"1"));
                resultMap.put("postHomeNext",postHomeNext);
            }
            return new JsonResult(1,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getVolunteerActivitiesList(Page page) {
        PostVolunteerActivitiesDto dto = new PostVolunteerActivitiesDto();
        Integer count = postVolunteerActivitiesMapper.getActivitiesCount(dto);
        page.setAllRow(count);
        dto.setStart(page.getStart());
        dto.setSize(page.getSize());
        dto.setOrderBy("1");//按更新时间排序
        List<PostVolunteerActivitiesVo> voList = postVolunteerActivitiesMapper.getActivitiesList(dto);
        List<PostVolunteerActivitiesVo> result = new ArrayList<PostVolunteerActivitiesVo>();
        for (PostVolunteerActivitiesVo vo : voList) {
            String coverId = vo.getCoverId();
            Attachment attachment = attachmentService.getFileTransPath(coverId);
            if (null != attachment) {
                vo.setCoverUrl(attachment.getAttPath());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat rsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date startTime = sdf.parse(vo.getStartTime());
                Date endTime = sdf.parse(vo.getEndTime());
                Date endSignTime = sdf.parse(vo.getEndSignTime());
                vo.setUpdateStr(sdf1.format(vo.getUpdateDate()));
                vo.setStartTimeStr(sdf.format(startTime));
                vo.setEndTimeStr(sdf.format(endTime));
                vo.setEndSignTimeStr(rsdf.format(endSignTime));
                Date today = new Date();
                if (today.after(endTime)) {  //这里为了跟前台一致，后台取反，实际应将after改为before
                    vo.setStatus(1);
                } else {
                    vo.setStatus(0);
                }
            } catch (ParseException e) {

            }
            result.add(vo);
        }
        return new JsonResult(1, result);
    }

    @Override
    public JsonResult getWebActivitiesList(Page page){
        PostVolunteerActivitiesDto dto = new PostVolunteerActivitiesDto();
        Integer count = postVolunteerActivitiesMapper.getWebActivitiesCount(dto);
        page.setAllRow(count);
        dto.setStart(page.getStart());
        dto.setSize(page.getSize());
        List<PostVolunteerActivitiesVo> voList = postVolunteerActivitiesMapper.getWebActivitiesList(dto);
        List<PostVolunteerActivitiesVo> result = new ArrayList<PostVolunteerActivitiesVo>();
        for (PostVolunteerActivitiesVo vo : voList) {
            String coverId = vo.getCoverId();
            Attachment attachment = attachmentService.getFileTransPath(coverId);
            if (null != attachment) {
                vo.setCoverUrl(attachment.getAttPath());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat rsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date startTime = sdf.parse(vo.getStartTime());
                Date endTime = sdf.parse(vo.getEndTime());
                Date endSignTime = sdf.parse(vo.getEndSignTime());
                vo.setUpdateStr(sdf1.format(vo.getUpdateDate()));
                vo.setStartTimeStr(sdf.format(startTime));
                vo.setEndTimeStr(sdf.format(endTime));
                vo.setEndSignTimeStr(rsdf.format(endSignTime));
                Date today = new Date();
                if (today.after(endTime)) {  //这里为了跟前台一致，后台取反，实际应将after改为before
                    vo.setStatus(1);
                } else {
                    vo.setStatus(0);
                }
            } catch (ParseException e) {

            }
            result.add(vo);
        }
        return new JsonResult(1, result);
    }

    @Override
    public JsonResult getVolunteerActivitiesListByUser(String userId, Page page) {
        PostVolunteerActivitiesDto dto = new PostVolunteerActivitiesDto();
        dto.setUserId(userId);
        Integer count = postVolunteerActivitiesMapper.countActivitiesListByUser(dto);
        page.setAllRow(count);
        dto.setStart(page.getStart());
        dto.setSize(page.getSize());
        List<PostVolunteerActivitiesVo> voList = postVolunteerActivitiesMapper.getActivitiesListByUser(dto);
        List<PostVolunteerActivitiesVo> result = new ArrayList<PostVolunteerActivitiesVo>();
        for (PostVolunteerActivitiesVo vo : voList) {
            String coverId = vo.getCoverId();
            Attachment attachment = attachmentService.getFileTransPath(coverId);
            if (null != attachment) {
                vo.setCoverUrl(attachment.getAttPath());
//                vo.setCoverUrl(null);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat rsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date startTime = sdf.parse(vo.getStartTime());
                Date endTime = sdf.parse(vo.getEndTime());
                Date endSignTime = sdf.parse(vo.getEndSignTime());
                vo.setUpdateStr(sdf1.format(vo.getUpdateDate()));
                vo.setStartTimeStr(sdf.format(startTime));
                vo.setEndTimeStr(sdf.format(endTime));
                vo.setEndSignTimeStr(rsdf.format(endSignTime));
                Date today = new Date();
                if (today.before(endTime)) {
                    vo.setStatus(1);
                } else {
                    vo.setStatus(0);
                }
            } catch (ParseException e) {

            }
            result.add(vo);
        }
        return new JsonResult(1, result);
    }

    @Override
    public JsonResult  selectIndexCollectList(){
        try {
            List<CollectDto> collectList = collectMapper.selectIndexCollectList();
            List<Attachment> picList1 = new ArrayList<Attachment>();
            for(CollectDto collect : collectList){
                if(StringUtils.isNotBlank(collect.getPictureids())){
                    picList1 = attachmentService.getFileTransPathList(collect.getPictureids());
                    if(picList1.size()>0 && StringUtils.isNotBlank(picList1.get(0).getAttPath())){

                        collect.setAttachmentList(picList1);
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
    public JsonResult addApplyVolunteer(PostVolunteerApply postVolunteerApply) {
        String  userId  = Tools.getUserId();
        if (StringUtils.isBlank(postVolunteerApply.getApplyName())) {
            return new JsonResult(0, null, "120000010");
        }
        if (StringUtils.isBlank(postVolunteerApply.getApplyPhone())) {
            return new JsonResult(0, null, "120000011");
        }
        if (null == postVolunteerApply.getApplyAge()) {
            return new JsonResult(0, null, "120000012");
        }
        if (StringUtils.isBlank(postVolunteerApply.getApplyJob())) {
            return new JsonResult(0, null, "120000013");
        }
        if (StringUtils.isBlank(postVolunteerApply.getApplyReason())) {
            return new JsonResult(0, null, "120000014");
        }
        String activitiesId = postVolunteerApply.getActivitiesId();
        String phone = postVolunteerApply.getApplyPhone();
        Map<String, String> param = new HashMap<String, String>(2);
        param.put("activitiesId", activitiesId);
        param.put("phone", phone);
        Integer count = postVolunteerActivitiesMapper.countActivitiesJoined(param);
        if (null != count && count > 0) {
            return new JsonResult(0, null, "120000017");
        }
        PostVolunteerActivities activities = postVolunteerActivitiesMapper.getActivitiesById(postVolunteerApply.getActivitiesId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (new Date().after(sdf.parse(activities.getEndSignTime()))) {
                return new JsonResult(0, null, "120000016");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return new JsonResult(0, null , "20000003");
        }


        postVolunteerApply.setId(IdUtils.getIncreaseIdByNanoTime());
        postVolunteerApply.setCreateBy(userId );
        postVolunteerApply.setUpdateBy(userId );
        Integer index = postVolunteerApplyMapper.addApplyVolunteer(postVolunteerApply);
        if (index > 0) {
            return new JsonResult(1, postVolunteerApply);
        } else {
            return new JsonResult(0, null, "120000015");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult addPublicCurator(PostPublicCuratorDto dto) {
        /**
         * 数据校验
         */
        String collectIds = dto.getCollectIds().trim();
        String themeName = dto.getThemeName();
        String themeContent = dto.getThemeContent();
        String datumIds = dto.getDatumIds();
        String userId = dto.getUserId();
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(dto.getUserName())) {
            return new JsonResult(0, null, "000021");
        }
        if (StringUtils.isBlank(themeName)) {
            return new JsonResult(0, null, "130000001");
        }
        if (StringUtils.isBlank(themeContent)) {
            return new JsonResult(0, null, "130000002");
        }
        if (StringUtils.isBlank(datumIds)) {
            return new JsonResult(0, null, "130000003");
        }
        if (StringUtils.isBlank(collectIds)) {
            return new JsonResult(0, null, "130000004");
        }
        /**
         * 创建保存对象
         */
        String[] collectIdArr = collectIds.split(",");
        String themeId = IdUtils.getIncreaseIdByNanoTime();

        PostThemeShow postThemeShow = new PostThemeShow();
        postThemeShow.setId(themeId);
        postThemeShow.setUserId(userId);
        postThemeShow.setUserName(dto.getUserName());
        postThemeShow.setDatumIds(dto.getDatumIds());
        postThemeShow.setThemeName(themeName);
        postThemeShow.setThemeDescribe(themeContent);
        postThemeShow.setThemeSource("2");
        postThemeShow.setCollectionAmount(Integer.toString(collectIdArr.length));
        postThemeShow.setPageRecommend("0");
        postThemeShow.setSelectedTopics("0");
        postThemeShow.setProcessState("0");
        postThemeShow.setProcessResult("-1");
        postThemeShow.setDataState("1");
        postThemeShow.setCreateBy(userId);
        postThemeShow.setCreateDate(new Date());
        postThemeShow.setUpdateBy(userId);
        postThemeShow.setUpdateDate(new Date());
        Integer index = postThemeShowMapper.insertSelective(postThemeShow);

        try {
            pictureService.setMain(datumIds, datumIds, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (index > 0) {
            /**
             * 保存关联藏品
             */
            List<PostCollectionType> list = new ArrayList<PostCollectionType>(collectIdArr.length);
            PostCollectionType postCollectionType = null;
            for (int i = 0, length = collectIdArr.length; i < length; i++) {
                postCollectionType = new PostCollectionType();
                postCollectionType.setId(IdUtils.getIncreaseIdByNanoTime());
                postCollectionType.setThemeShowId(themeId);
                postCollectionType.setCollectId(collectIdArr[i]);
                postCollectionType.setStatus("1");
                postCollectionType.setIsdelete("0");
                postCollectionType.setSort("0");
                postCollectionType.setCreateBy(userId);
                postCollectionType.setCreateDate(new Date());
                postCollectionType.setUpdateBy(userId);
                postCollectionType.setUpdateDate(new Date());
                postCollectionType.setOther1("");
                postCollectionType.setOther2("");
                postCollectionType.setOther3("");
                list.add(postCollectionType);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                try {
                    index = postCollectionTypeMapper.batchSave(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                if (index < 1 && index != list.size()) {
                    throw new RuntimeException("藏品保存失败");
                } else {
                    return new JsonResult(1);
                }
            }
        }
        return new JsonResult(0, null, "130000005");
    }

    @Override
    public JsonResult getPublicCuratorList(String userId, Integer status, Page page) {
        if (StringUtils.isBlank(userId)) {
            return new JsonResult(0, null, "000021");
        }
        Map<String, Object> param = new HashMap<String, Object>(2);
        param.put("userId", userId);
        param.put("status", status);
        Integer count = postPublicCuratorMapper.getPublicCuratorCount(param);
        page.setAllRow(count);
        param.put("start", page.getStart());
        param.put("size", page.getSize());
        List<PostPublicCuratorVo> voList = postPublicCuratorMapper.getPublicCuratorList(param);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (PostPublicCuratorVo vo : voList) {
            vo.setCreateTimeStr(sdf.format(vo.getCreateTime()));
            Attachment attachment = attachmentService.getFileTransPath(vo.getDatumIds());
            if (null != attachment) {
                vo.setDatumUrl(attachment.getAttPath());
            }
        }
        return new JsonResult(1, voList);
    }

    @Override
    public JsonResult getPublicCuratorById(String id) {
        PostPublicCuratorVo vo = postPublicCuratorMapper.getPublicCuratorById(id);
        if (null != vo) {
            Attachment attachment = attachmentService.getFileTransPath(vo.getDatumIds());
            if (null != attachment) {
                vo.setDatumUrl(attachment.getAttPath());
            }
            List<CollectDto> collects = postPublicCuratorCollectMapper.getCuratorCollectListByCuratorId(id);
            for (int i = 0, length = collects.size(); i < length; i++) {
                String type = "";
                String sonType = "";
                CollectDto dto = collects.get(i);
                String sonTypeId = dto.getSonTypeId();
                if (!org.apache.commons.lang.StringUtils.isBlank(sonTypeId)) {
                    sonType = "-" + sysDictMapper.getDictById(sonTypeId).getDictName();
                }
                attachment = attachmentService.getFileTransPath(dto.getPictureids());
                if (null != attachment) {
                    dto.setPictureUrl(attachment.getAttPath());
                }
                String typeId = dto.getTypeId();
                type = sysDictMapper.getDictById(typeId).getDictName();
                dto.setTypeFullName(type + sonType);
            }
            vo.setCollects(collects);
        }
        return new JsonResult(1, vo);
    }

    @Override
    public JsonResult selectCollectListByIds(String ids, String typeId, String sonTypeId) {
        if (Utils.isEmpty(ids)) {
            return new JsonResult(0, null, "20000003");
        }
        if (StringUtils.isBlank(typeId) || StringUtils.isBlank(sonTypeId)) {
            return new JsonResult(0, null, "130000006");
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
            Attachment attachment = attachmentService.getFileTransPath(dto.getPictureids());
            if (null != attachment) {
                dto.setPictureUrl(attachment.getAttPath());
            }
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
    public JsonResult getSelectCollectByTypeAndSonType(Collect collect) {
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
            resMap.put("label", col.getName());
            resMap.put("value", col.getId());
            resMap.put("selected", "");
            resMap.put("disabled", "");
            resList.add(resMap);
        }
        jsonResult.setCode(0);
        jsonResult.setData(resList);
        return jsonResult;
    }

    @Override
    public JsonResult getAppThemeById(String id, String userId) {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostThemeShow postThemeShow = postThemeShowMapper.getAppThemeById(id,userId);
            //根据主题展id 查询相关的藏品list
            List<Attachment> picList1 = new ArrayList<Attachment>();
            List<Collect> collectList = new ArrayList<Collect>();
            collectList = postThemeShowMapper.getCollectsById(id);
            if(collectList.size()>0 && collectList!=null){
                for(Collect  collect: collectList){
                    picList1 = attachmentService.getFileTransPathList(collect.getPictureids());
                    collect.setPicList(picList1);
                }
                postThemeShow.setCollectionList(collectList);
                //app端时间处理   年月日
                String appTimeStr = DateFormartUtil.stampToDateSimple(postThemeShow.getCreateDate());
                postThemeShow.setAppCreateTimeStr(appTimeStr);
                if(!StringUtils.isBlank(postThemeShow.getDatumIds())){
                    //获取图片url
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
            }

            return new JsonResult(1,postThemeShow);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }
}

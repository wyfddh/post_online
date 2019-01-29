package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostShortcutentranceMapper;
import com.tj720.dao.PostVideoMapper;
import com.tj720.dao.SysDepartmentMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.DelayTime;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.menu.SysFunction;
import com.tj720.model.common.system.menu.SysFunctionMenuDto;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.common.video.*;
import com.tj720.model.common.wf.WfAction;
import com.tj720.model.common.wf.WfDetail;
import com.tj720.service.*;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 影视资料实现服务类
 * @Author: 程荣凯
 * @Date: 2018/10/22 11:41
 */
@Service("postVideoService")
public class PostVideoServiceImpl implements PostVideoService{
    @Autowired
    WfService wfService;
    @Autowired
    SysNoticeService sysNoticeService;
    /**
     * 影视资料dao层
     */
    @Autowired
    PostVideoMapper postVideoMapper;
    @Autowired
    PostLsService postLsService;
    @Autowired
    ResAuthService resAuthService;
    @Autowired
    PostShortcutentranceMapper postShortcutentranceMapper;
    @Autowired
    SysDepartmentMapper sysDepartmentMapper;


    private PostShortcutentrance setActionInfo(PostShortcutentrance postVideoType, String type){
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

    private PostVideo setActionInfo(PostVideo postVideo, String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        postVideo.setUpdater(Tools.getUserId());
        postVideo.setUpdateTime(date);
        if (type == "0"){
            postVideo.setCreateTime(date);
            postVideo.setCreator(Tools.getUserId());
        }
        return postVideo;
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getNowDate(){
        Date date= new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
    /**
     * 影视资料查询列表
     *
     * @param keywords
     * @param videoMark
     * @param status
     * @param page
     * @return
     */
    @Override
    public JsonResult getPostVideoQueryList(String keywords, String videoMark, String status
            ,String currentUserId, String uploadOrg,Page page,String userType,String module) {
        try {
            HashMap<String,Object> condition = new HashMap<String,Object>();
            condition.put("keywords",keywords);
            condition.put("videoMark",videoMark);
            condition.put("status",status);
            condition.put("currentUserId",currentUserId);
            condition.put("uploadOrg",uploadOrg);
            String userId = Tools.getUserId();
            if (null != userId) {
                SysDepartment deptById = sysDepartmentMapper.getDeptById(userId);
                String orgId = deptById.getDepartmentId();
                condition.put("userId", userId);
                condition.put("orgId", orgId);
                JsonResult jsonResult = resAuthService.getDataAuthRule(currentUserId, module);
                String dataRule = (String) jsonResult.getData();
                if (dataRule.equals("")) {
                    return new JsonResult(0, null, "111116");
                }
                condition.put("dataRule", dataRule);
                //非影视部
                if ("0".equals(userType)){
                    Integer integer = postVideoMapper.countPostVideoQueryList(condition);
                    page.setAllRow(integer);
                    condition.put("currentPage",page.getStart());
                    condition.put("size",page.getSize());
                    List<PostVideo> postVideoList = postVideoMapper.getPostVideoQueryList(condition);
                    return new JsonResult(1,postVideoList);
                }else {
                    Integer integer = postVideoMapper.countPostVideoQueryListPlus(condition);
                    page.setAllRow(integer);
                    condition.put("currentPage",page.getStart());
                    condition.put("size",page.getSize());
                    List<PostVideo> postVideoList = postVideoMapper.getPostVideoQueryListPlus(condition);
                    return new JsonResult(1,postVideoList);
                }
            }else {
                return new JsonResult(0,null,"111116");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    /**
     * 根据条件查询影视资料列表
     *
     * @param keywords  关键词
     * @param videoMark 资料分类
     * @param status    资料状态
     * @return
     */
    @Override
    public JsonResult getPostVideoList(String keywords, String videoMark, String status,Page page
            ,String uploadOrg,String userId,String userType,String module) {
        try {
            HashMap<String,Object> condition = new HashMap<String,Object>();
            condition.put("keywords",keywords);
            condition.put("videoMark",videoMark);
            condition.put("status",status);
            condition.put("uploadOrg",uploadOrg);
            condition.put("userId",userId);
            String userId1 = Tools.getUserId();
            if (null != userId) {
                SysDepartment deptById = sysDepartmentMapper.getDeptById(userId);
                String orgId = deptById.getDepartmentId();
                condition.put("userId", userId1);
                condition.put("orgId", orgId);
                JsonResult jsonResult = resAuthService.getDataAuthRule(userId1, module);
                String dataRule = (String) jsonResult.getData();
                if (dataRule.equals("")) {
                    return new JsonResult(0, null, "111116");
                }
                condition.put("dataRule", dataRule);
                //非影视部
                if ("0".equals(userType)) {
                    Integer integer = postVideoMapper.countPostVideoList(condition);
                    page.setAllRow(integer);
                    condition.put("currentPage", page.getStart());
                    condition.put("size", page.getSize());
                    List<PostVideo> postVideoList = postVideoMapper.getPostVideoList(condition);
                    return new JsonResult(1, postVideoList);
                } else {
                    Integer integer = postVideoMapper.countPostVideoListPlus(condition);
                    page.setAllRow(integer);
                    condition.put("currentPage", page.getStart());
                    condition.put("size", page.getSize());
                    List<PostVideo> postVideoList = postVideoMapper.getPostVideoListPlus(condition);
                    return new JsonResult(1, postVideoList);
                }
            }else{
                return new JsonResult(0,null,"111116");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult queryVedilListForCompreQuery(String keywords, String videoMark, String status, Page pageInfo, String orderBy) {
        Map<String, Object> param = new HashMap<String, Object>(5);
        param.put("keywords", keywords);
        param.put("videoMark", videoMark);
        param.put("status", status);
        Integer count = postVideoMapper.countVedilListForCompreQuery(param);
        pageInfo.setAllRow(count);
        param.put("currentPage",pageInfo.getStart());
        param.put("size",pageInfo.getSize());
        param.put("orderBy", orderBy);
        List<PostVideo> postVideoList = postVideoMapper.queryVedilListForCompreQuery(param);
        return new JsonResult(1, postVideoList);
    }

    /**
     * 添加影视资料
     * @param postVideo 影视资料对象
     * @return
     */
    @Override
    public JsonResult addPostVideo(PostVideo postVideo) {
        //影视资料对象是否为空
        if (StringUtils.isEmpty(postVideo)){
            return new JsonResult(0,null,"000020");
        }
        //资料编码是否为空
        if (StringUtils.isEmpty(postVideo.getVideoCode())){
            return new JsonResult(0,null,"000020");
        }
        try {
            List<PostVideo> postVideoListByCode = getPostVideoListByCode(postVideo.getVideoCode());
            //资料编码是否重复
            if (null != postVideoListByCode && postVideoListByCode.size() >0){
                //更新资料编码
                postVideo.setVideoCode(postLsService.getLsCode("videoList","video"));
            }
            List<PostVideo> postVideoListByName = getPostVideoListByCode(postVideo.getVideoName());
            //资料编码是否重复
            if (null != postVideoListByName && postVideoListByName.size() >0){
                return new JsonResult(0,null,"200509");
            }
            //设置主键
            postVideo.setId(IdUtils.getIncreaseIdByNanoTime());
            postVideo = setActionInfo(postVideo,"0");
            int count = postVideoMapper.insertSelective(postVideo);
            postVideoListByCode = getPostVideoListByCode(postVideo.getVideoCode());
            if (count > 0){
                return new JsonResult(1,postVideoListByCode);
            }else {
                return new JsonResult(0,null,"200504");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200504");
        }
    }

    /**
     * 修改影视资料
     *
     * @param postVideo 影视资料对象
     * @return
     */
    @Override
    public JsonResult updateVideo(PostVideo postVideo) {
        //影视资料对象是否为空
        if (StringUtils.isEmpty(postVideo)){
            return new JsonResult(0,null,"000020");
        }
        //资料编码是否为空
//        if (StringUtils.isEmpty(postVideo.getVideoCode())){
//            return new JsonResult(0,null,"000020");
//        }
        try {
                postVideo = setActionInfo(postVideo,"1");
                int count = postVideoMapper.updateByPrimaryKeySelective(postVideo);
                JsonResult postVideoListByCode = getPostVideo(postVideo.getId());
                if (count > 0){
                    return new JsonResult(1,postVideoListByCode.getData());
                }else {
                    return new JsonResult(0,null,"200505");
                }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200505");
        }
    }

    @Override
    public JsonResult updateVideoPlus(PostVideo postVideo) {
        //影视资料对象是否为空
        if (StringUtils.isEmpty(postVideo)){
            return new JsonResult(0,null,"000020");
        }
        //资料编码是否为空
//        if (StringUtils.isEmpty(postVideo.getVideoCode())){
//            return new JsonResult(0,null,"000020");
//        }
        try {
//            postVideo = setActionInfo(postVideo,"1");
            int count = postVideoMapper.updateByPrimaryKeySelective(postVideo);
            JsonResult postVideoListByCode = getPostVideo(postVideo.getId());
            if (count > 0){
                return new JsonResult(1,postVideoListByCode.getData());
            }else {
                return new JsonResult(0,null,"200505");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200505");
        }
    }

    /**
     * 删除影视资料
     *
     * @param id 影视资料id
     * @return
     */
    @Override
    @Transactional
    public JsonResult deleteVideo(String id) {
        //影视资料对象是否为空
        if (StringUtils.isEmpty(id)){
            return new JsonResult(0,null,"000020");
        }
        try {

            int count = postVideoMapper.deleteByPrimaryKey(id);
            if (count > 0){
                //删除上传流程
                WfAction wfActionByPartyId = wfService.getWfActionByPartyId(id, "1");
                if (null != wfActionByPartyId){
                    wfService.deleteWfDetailByProcessId(wfActionByPartyId.getXid());
                    wfService.deleteWfAction(wfActionByPartyId.getXid());
                }

                //删除查询流程
                WfAction wfActionByPartyId1 = wfService.getWfActionByPartyId(id, "2");
                if (null != wfActionByPartyId1){
                    wfService.deleteWfDetailByProcessId(wfActionByPartyId1.getXid());
                    wfService.deleteWfAction(wfActionByPartyId1.getXid());
                }
                return new JsonResult(1,null);
            }else {
                return new JsonResult(0,null,"200506");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200506");
        }
    }

    /**
     * 修改影视资料状态
     *
     * @param id          影视资料ID
     * @param authSetting 权限
     * @return
     */
    @Override
    public JsonResult changeAuthSetting(String id, String authSetting) {
        PostVideo postVideo = new PostVideo();
        postVideo.setId(id);
        postVideo.setAuthSetting(authSetting);
        try {
            postVideo = setActionInfo(postVideo,"1");
            int count = postVideoMapper.updateByPrimaryKeySelective(postVideo);
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200505");
        }
    }

    /**
     * @param ids         影视资料id列表
     * @param authSetting 权限
     * @return
     */
    @Override
    @Transactional
    public JsonResult batchChangeAuthSetting(List<String> ids, String authSetting) {
        try {
            postVideoMapper.bacthUpdateAuthSetting(ids,authSetting);
            return new JsonResult(1, null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0, "200505");
        }

    }

    /**
     * 根据影视资料id撤回申请
     *
     * @param id 影视资料id
     * @return
     */
    @Override
    @Transactional
    public JsonResult revokeProcess(String id,String currentUserId) {
        WfAction wfAction = wfService.getWfActionByPartyId(id,"1");
        if (null == wfAction){
            return new JsonResult("000001");
        }
        wfAction.setApplyStatus("0");
        wfAction.setUpdateTime(new Date());
        wfAction.setUpdater(currentUserId);
        JsonResult jsonResult = wfService.updateWfAction(wfAction);
        PostVideo postVideo = postVideoMapper.getPostVideo(wfAction.getPartyId());
        wfService.addWfDetail(wfAction.getXid(),"2","2","1",
                "撤回",new Date(),currentUserId,currentUserId,postVideo.getUploadOrg());
        //添加审批人的待办流程记录
        if (null != jsonResult && jsonResult.getSuccess()==1){
            postVideo.setStatus("1");
            JsonResult jsonResult1 = updateVideo(postVideo);
            return jsonResult1;
        }
        return jsonResult;
    }

    /**
     * 根据影视资料id提交申请
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public JsonResult commitProcess(String id,String currentUserId) {

        JsonResult jsonResult = getPostVideo(id);
        if (StringUtils.isEmpty(jsonResult) || jsonResult.getSuccess()==0){
            return new JsonResult("200511");
        }
        PostVideo postVideo = (PostVideo) jsonResult.getData();
        //检测当前用户是不是影视部
        if (checkUserOrg(currentUserId)){
            postVideo.setStatus("4");
            JsonResult jsonResult1 = updateVideo(postVideo);
            checkVideoCode(postVideo);
            return  jsonResult1;
        }
        postVideo.setStatus("2");
        JsonResult jsonResult1 = updateVideo(postVideo);
        if (StringUtils.isEmpty(jsonResult1) || jsonResult1.getSuccess()==0){
            return jsonResult1;
        }
        try {
            WfAction wfAction = wfService.getWfActionByPartyId(postVideo.getId(),"1");
            wfAction.setApplyStatus("1");
            wfAction.setUpdater(currentUserId);
            wfAction.setUpdateTime(new Date());
            wfService.updateWfAction(wfAction);
            //设置2小时定时提交
            Timer timer1 = new Timer();
            timer1.schedule(new MyTask(wfAction.getXid(),currentUserId,wfService,this,sysNoticeService), DelayTime.getDelayTime());
            //添加审批记录
//                List<SysUser> users = roleAuthService.getUserListByRole(postVideo.getApproval());
            //添加申请人的已办流程记录
            wfService.addWfDetail(wfAction.getXid(),"1","2","2",
                    "提交申请",new Date(),currentUserId,currentUserId,postVideo.getUploadOrg());
            return new JsonResult(1,null);

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }

    }

    /**
     * 发布
     *
     * @param id
     * @param currentUserId
     * @return
     */
    @Override
    public JsonResult publishProcess(String id, String currentUserId) {
        try {
            PostVideo postVideo = new PostVideo();
            postVideo.setId(id);
            postVideo.setStatus("4");
            updateVideo(postVideo);
            postVideo = postVideoMapper.getPostVideo(id);
            checkVideoCode(postVideo);
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }
    }

    /**
     * 审批影视资料上传流程
     *
     * @param id          影视资料id
     * @param authSetting 权限
     * @param type        操作类型
     * @param approval    审批人
     * @param remark      备注
     * @return
     */
    @Override
    @Transactional
    public JsonResult approvalProcess(String id, String authSetting, String type, String approval, String remark,String currentUserId) {
        JsonResult jsonResult = getPostVideo(id);
        if (StringUtils.isEmpty(jsonResult) || jsonResult.getSuccess()==0){
            return new JsonResult("200511");
        }
        PostVideo postVideo = (PostVideo) jsonResult.getData();
        try {
            WfAction wfAction = wfService.getWfActionByPartyId(id,"1");
            if ("1".equals(type)){
                wfAction.setApplyStatus("2");
                wfAction.setUpdater(currentUserId);
                wfAction.setUpdateTime(new Date());
                wfService.updateWfAction(wfAction);
//                WfDetail wfDetail = wfService.getWfDetailByAction(wfAction.getXid());
                WfDetail wfDetail = wfService.getWfDetailByUserIdAndPid(currentUserId,wfAction.getXid(),"1");
                wfDetail.setActionResult("4");
                wfDetail.setActionName("通过");
                wfDetail.setStatus("3");
                wfDetail.setApplyOrg(sysDepartmentMapper.getDeptById(Tools.getUserId()).getDepartmentId());
                wfService.updateWfDetail(wfDetail);
                //更新所有流程记录的状态为已办结
                wfService.updateWfDetailByProcessId(wfAction.getXid(),"3");

                //修改当前业务表编号
                postVideo.setStatus("4");
                postVideo = checkVideoCode(postVideo);
                if (null != authSetting){
                    postVideo.setAuthSetting(authSetting);
                }
                updateVideo(postVideo);

                //添加消息通知
                String content = getNowDate()+";您提交的"+postVideo.getVideoName()+"上传申请已审批通过";
                sysNoticeService.sendNoticePlus(wfAction.getApply(),"1","确定",currentUserId,
                        content,wfAction.getXid(),currentUserId);
            }else if ("3".equals(type)){
                //更新当前操作状态

                WfDetail wfDetail = wfService.getWfDetailByUserIdAndPid(currentUserId,wfAction.getXid(),"1");
                wfDetail.setStatus("2");
                wfDetail.setActionResult("3");
                wfDetail.setActionName("通过并提交");
                wfDetail.setApplyOrg(sysDepartmentMapper.getDeptById(Tools.getUserId()).getDepartmentId());
                wfService.updateWfDetail(wfDetail);
                wfService.addWfDetail(wfAction.getXid(),"4","1","3","通过并提交",new Date(),approval,currentUserId,postVideo.getUploadOrg());
                if (null != authSetting){
                    postVideo.setAuthSetting(authSetting);
                    postVideo.setApproval(approval);
                    updateVideo(postVideo);
                }
                //添加消息通知
                String content = getNowDate()+";您有一条"+postVideo.getVideoName()+"上传申请需要审批";
                sysNoticeService.sendNoticePlus(approval,"1","确定",currentUserId,
                        content,wfAction.getXid(),currentUserId);
            }else if ("2".equals(type)){
                wfAction.setApplyStatus("2");
                wfAction.setUpdater(currentUserId);
                wfAction.setUpdateTime(new Date());
                wfService.updateWfAction(wfAction);
                WfDetail wfDetail = wfService.getWfDetailByUserIdAndPid(currentUserId,wfAction.getXid(),"1");
               //查询发送人
//                String ext1 = wfDetail.getExt1();
//                wfService.addWfDetail(wfAction.getXid(),"5","1","1","提交中",new Date(),currentUserId,ext1,postVideo.getUploadOrg());
                //设置当前操作人状态为已办
                wfDetail.setStatus("2");
                wfDetail.setActionResult("5");
                wfDetail.setActionName("驳回");
                wfDetail.setApplyOrg(sysDepartmentMapper.getDeptById(Tools.getUserId()).getDepartmentId());
                wfService.updateWfDetail(wfDetail);
                postVideo.setStatus("5");
                updateVideo(postVideo);
                //添加消息通知
                String content = getNowDate()+";您提交的"+postVideo.getVideoName()+"上传申请已被驳回";
                sysNoticeService.sendNoticePlus(wfAction.getApply(),"1","确定",currentUserId,
                        content,wfAction.getXid(),currentUserId);
            }
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }
    }

    /**
     * 审批影视资料上传流程
     *
     * @param ids
     * @param authSetting   权限
     * @param type          操作类型
     * @param approval      审批人
     * @param remark        备注
     * @param currentUserId
     * @return
     */
    @Override
    public JsonResult batchApprovalProcess(String[] ids, String authSetting, String type, String approval, String remark, String currentUserId) {
        try {
            for (String id : ids) {
                JsonResult jsonResult = approvalProcess(id, authSetting, type, approval, remark, currentUserId);
            }
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }

    }

    @Override
    public JsonResult getUplodApprovalList(String keywords, String source, String status, Page page,String currentUserId) {
        try {
            HashMap<String,Object> condition = new HashMap<String,Object>();
            condition.put("keywords",keywords);
            condition.put("source",source);
            condition.put("status",status);
            condition.put("currentUserId",currentUserId);
            Integer integer = postVideoMapper.countUploadApprovalList(condition);
            page.setAllRow(integer);
            condition.put("currentPage",page.getStart());
            condition.put("size",page.getSize());

            List<UploadApprovalDto> uploadApprovalList = postVideoMapper.getUploadApprovalList(condition);
            return new JsonResult(1,uploadApprovalList);
        }catch (Exception e){
            return new JsonResult("111116");
        }

    }

    /**
     * 根据id查询影视资料
     *
     * @param id
     * @return
     */
    @Override
    public JsonResult getPostVideo(String id) {
        try {
            PostVideo postVideo = postVideoMapper.getPostVideo(id);
            return new JsonResult(1,postVideo);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }

    }

    /**
     * 根据影视资料编号查询影视资料
     * @param videoCode
     * @return
     */
    private List<PostVideo> getPostVideoListByCode(String videoCode){
//        PostVideoExample example = new PostVideoExample();
//        PostVideoExample.Criteria criteria = example.createCriteria();
//        criteria.andVideoCodeEqualTo(videoCode);
//        List<PostVideo> postVideos = postVideoMapper.selectByExample(example);
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("videoCode",videoCode);
        List<PostVideo> postVideos = postVideoMapper.selectByMap(map);

        return postVideos;
    }

    /**
     * 根据影视资料名称查询影视资料
     * @param videoName
     * @return
     */
    private List<PostVideo> getPostVideoListByName(String videoName){
//        PostVideoExample example = new PostVideoExample();
//        PostVideoExample.Criteria criteria = example.createCriteria();
//        criteria.andVideoNameEqualTo(videoName);
//        List<PostVideo> postVideos = postVideoMapper.selectByExample(example);
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("videoName",videoName);
        List<PostVideo> postVideos = postVideoMapper.selectByMap(map);
        return postVideos;
    }
    @Override
    /**
     * 修改影视资料编号为正式编号
     * @param postVideo
     *
     * @return
     */
    @Transactional
    public PostVideo checkVideoCode(PostVideo postVideo){
        if (StringUtils.isEmpty(postVideo)){
            return postVideo;
        }else {
            if (StringUtils.isEmpty(postVideo.getVideoCode())){
                return postVideo;
            }
            if (postVideo.getVideoCode().contains("LS")){
//                if (postVideo.getStatus()=="4"){
                    String code = "Y"+postVideo.getVideoType()+postVideo.getSaveType();
                    String ls = postLsService.getLsCodePlus("video_List_formal","video");
                    postLsService.addLsCode("video_List_formal","video");
                    code = code+ls;
                    postVideo.setVideoCode(code);
                    postVideoMapper.updateByPrimaryKeySelective(postVideo);
//                }
            }
        }
        return postVideo;
    }

    /**
     * 保存影视资料查询申请
     *
     * @param postVideoId 影视资料ID
     * @param apply       申请人
     * @param applyOrg    申请人部门
     * @param applyTime   申请时间
     * @param applyReason 申请原因
     * @param remarks     备注
     * @param approval    审批人
     * @return
     */
    @Override
    @Transactional
    public JsonResult saveQueryApply(String postVideoId, String apply, String applyOrg
            , Date applyTime, String applyReason, String remarks, String approval) {
        try {
//            WfAction tempAction = wfService.getWfActionByPartyId(postVideoId,"2",apply);
//            if (null != tempAction){
//                return new JsonResult("51000001");
//            }
            WfAction temp =  wfService.getWfActionByPartyIdAndStatus(postVideoId,"2",apply,"1");
            if (null != temp){
                return new JsonResult("51000001");
            }
            WfAction wfAction = wfService.addWfAction(postVideoId,"2","0"
                    ,apply,applyOrg, applyTime,applyReason,"1"
                    ,remarks,approval);
            wfService.addWfDetail(wfAction.getXid(),"0","2","0","创建申请",
                    new Date(),apply,apply,applyOrg);
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            return new JsonResult("200504");
        }
    }

    @Override
    @Transactional
    public JsonResult saveAndSubmitQueryApply(String postVideoId, String apply, String applyOrg, Date applyTime, String applyReason, String remarks, String approval) {
        try {
            WfAction temp =  wfService.getWfActionByPartyIdAndStatus(postVideoId,"2",apply,"1");
            if (null != temp){
                return new JsonResult("51000001");
            }
            WfAction wfAction = wfService.addWfAction(postVideoId,"2","0"
                    ,apply,applyOrg, applyTime,applyReason,"1"
                    ,remarks,approval);
            wfService.addWfDetail(wfAction.getXid(),"0","2","0","创建申请",
                    new Date(),apply,apply,applyOrg);
            submitQueryApply(wfAction.getXid(),applyTime,apply,applyOrg,remarks,approval);
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            return new JsonResult("200504");
        }
    }

    /**
     * 保存影视资料查询申请
     *
     * @param ids         影视资料ID
     * @param apply       申请人
     * @param applyOrg    申请人部门
     * @param applyTime   申请时间
     * @param applyReason 申请原因
     * @param remarks     备注
     * @param approval    审批人
     * @return
     */
    @Transactional
    @Override
    public JsonResult batchSaveQueryApply(String[] ids, String apply, String applyOrg, Date applyTime, String applyReason, String remarks, String approval) {
        try {
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                WfAction temp =  wfService.getWfActionByPartyIdAndStatus(id,"2",apply,"1");
                if (null != temp){
                    continue;
                }
                JsonResult jsonResult = saveQueryApply(id, apply, applyOrg, applyTime, applyReason, remarks, approval);
            }
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }

    }

    /**
     * @param ids
     * @param apply
     * @param applyOrg
     * @param applyTime
     * @param applyReason
     * @param remarks
     * @param approval
     * @return
     */
    @Override
    @Transactional
    public JsonResult batchSaveAndSubmitQueryApply(String[] ids, String apply, String applyOrg, Date applyTime, String applyReason, String remarks, String approval) {
        try {
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                WfAction temp =  wfService.getWfActionByPartyIdAndStatus(id,"2",apply,"1");
                if (null != temp){
                    continue;
                }
                JsonResult jsonResult = saveQueryApply(id, apply, applyOrg, applyTime, applyReason, remarks, approval);
                if (jsonResult.getSuccess()==1){
                    WfAction wfAction = (WfAction)jsonResult.getData();
                    submitQueryApply(wfAction.getXid(),applyTime,apply,applyOrg,remarks,approval);
                }
            }
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }
    }

    /**
     * 提交影视资料查询申请
     *
     * @param processInstId 流程实例id
     * @return
     */
    @Override
    @Transactional
    public JsonResult submitQueryApply(String processInstId,Date applyTime,String apply,String applyOrg
            ,String remarks,String approval) {
        //更新流程状态
        try {
            WfAction wfAction = new WfAction();
            wfAction.setXid(processInstId);
            wfAction.setApplyStatus("1");
            wfAction.setUpdater(apply);
            wfAction.setUpdateTime(new Date());
            wfService.updateWfAction(wfAction);

            wfService.addWfDetail(processInstId,"1","2","1","提交申请",
                    new Date(),apply,apply,applyOrg);
            //添加待办任务
            wfService.addWfDetail(processInstId,"1","1","1","审批",
                    new Date(),approval,apply,applyOrg);
            WfAction wfActionById = wfService.getWfActionById(processInstId);
            JsonResult postVideoResult = getPostVideo(wfActionById.getPartyId());
            PostVideo postVideo = (PostVideo)postVideoResult.getData();
            //添加消息通知
            String content = getNowDate()+";您有一条"+postVideo.getVideoName()+"查询申请需要审批";
            sysNoticeService.sendNoticePlus(approval,"1","确定",apply,
                    content,wfAction.getXid(),apply);
            return new JsonResult(1);
        }catch (Exception e){
            return new JsonResult("200504");
        }
    }

    /**
     * 撤回影视资料查询申请
     *
     * @param processInstId 流程实例ID
     * @param apply         操作人
     * @param applyOrg      操作部门
     * @return
     */
    @Override
    @Transactional
    public JsonResult revokeQueryApply(String processInstId, String apply, String applyOrg) {
       try {
           WfAction wfAction = new WfAction();
           wfAction.setXid(processInstId);
           wfAction.setApplyStatus("0");
           wfAction.setUpdater(apply);
           wfAction.setUpdateTime(new Date());
           wfService.updateWfAction(wfAction);
           wfService.addWfDetail(processInstId,"2","1","0","撤回",
                   new Date(),apply,apply,applyOrg);
           return new JsonResult(1);
       }catch (Exception e){
           e.printStackTrace();
           return new JsonResult("200504");
       }
    }

    /**
     * 审批影视资料查询流程
     *
     * @param processInstId 流程实例ID
     * @param type    操作类型
     * @param approval      审批人
     * @param apply         操作人
     * @param applyOrg      操作人部门
     * @param remarks
     * @return
     */
    @Override
    @Transactional
    public JsonResult approvalQueryApply(String processInstId, String type, String approval,
                                         String apply, String applyOrg, String remarks,String currentUserId) {
        if (StringUtils.isEmpty(type)){
            return new JsonResult("200504");
        }
        try {
            WfAction wfAction = new WfAction();
            WfAction wfAction1 = wfService.getWfActionById(processInstId);
            wfAction.setXid(processInstId);
            if ("1".equals(type)){
                wfAction.setApplyStatus("2");
                wfAction.setUpdater(currentUserId);
                wfAction.setUpdateTime(new Date());
                wfService.updateWfAction(wfAction);
                WfDetail wfDetail = wfService.getWfDetailByUserIdAndPid(currentUserId,processInstId,"1");
                wfDetail.setActionResult("2");
                wfDetail.setActionName("通过");
                wfDetail.setStatus("3");
                wfDetail.setApplyOrg(sysDepartmentMapper.getDeptById(Tools.getUserId()).getDepartmentName());
                wfDetail.setRemark(remarks);
                wfService.updateWfDetail(wfDetail);
                WfAction wfActionById = wfService.getWfActionById(processInstId);
                //更新所有任务为已完结
                wfService.updateWfDetailByProcessId(processInstId,"3");
                JsonResult postVideoResult = getPostVideo(wfActionById.getPartyId());
                PostVideo postVideo = (PostVideo)postVideoResult.getData();
                //消息通知
                String content = getNowDate()+";您提交的"+postVideo.getVideoName()+"查询申请已审批通过";
                sysNoticeService.sendNoticePlus(wfAction1.getApply(),"1","确定",apply,
                        content,wfAction.getXid(),apply);

            }else if ("3".equals(type)){
                wfAction.setApplyStatus("1");
                wfAction.setUpdater(currentUserId);
                wfAction.setUpdateTime(new Date());

                wfService.updateWfAction(wfAction);
                //更新当前操作状态
                WfDetail wfDetail = wfService.getWfDetailByUserIdAndPid(currentUserId,processInstId,"1");
                wfDetail.setStatus("2");
                wfDetail.setActionResult("1");
                wfDetail.setActionName("通过并提交");
                wfDetail.setRemark(remarks);
                wfDetail.setApplyOrg(sysDepartmentMapper.getDeptById(Tools.getUserId()).getDepartmentName());
                wfService.updateWfDetail(wfDetail);
                wfService.addWfDetail(processInstId,"4","1","3","通过并提交",new Date(),approval,currentUserId,applyOrg);
                WfAction wfActionById = wfService.getWfActionById(processInstId);
                JsonResult postVideoResult = getPostVideo(wfActionById.getPartyId());
                PostVideo postVideo = (PostVideo)postVideoResult.getData();
                //消息通知
                String content = getNowDate()+";您有一条"+postVideo.getVideoName()+"查询申请需要审批";
                sysNoticeService.sendNoticePlus(approval,"1","确定",apply,
                        content,wfAction.getXid(),apply);
            }else if ("2".equals(type)){
                wfAction.setApplyStatus("3");
                wfAction.setUpdater(currentUserId);
                wfAction.setUpdateTime(new Date());
                wfService.updateWfAction(wfAction);
                WfDetail wfDetail = wfService.getWfDetailByUserIdAndPid(currentUserId,processInstId,"1");
                //查询发送人
//                String ext1 = wfDetail.getExt1();
//                wfService.addWfDetail(processInstId,"5","3","1","草稿",new Date(),currentUserId,ext1,applyOrg);
                //设置当前操作人状态为已办
                wfDetail.setStatus("2");
                wfDetail.setActionResult("3");
                wfDetail.setActionName("驳回");
                wfDetail.setRemark(remarks);
                wfDetail.setApplyOrg(sysDepartmentMapper.getDeptById(Tools.getUserId()).getDepartmentName());
                wfService.updateWfDetail(wfDetail);
                WfAction wfActionById = wfService.getWfActionById(processInstId);
                //更新所有任务为已完结
                wfService.updateWfDetailByProcessId(processInstId,"3");
                JsonResult postVideoResult = getPostVideo(wfActionById.getPartyId());
                PostVideo postVideo = (PostVideo)postVideoResult.getData();
                //消息通知
                String content = getNowDate()+";您提交的"+postVideo.getVideoName()+"查询申请已被驳回";
                sysNoticeService.sendNoticePlus(wfAction1.getApply(),"1","确定",apply,
                        content,wfAction.getXid(),apply);
            }
            return new JsonResult(1,null);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }
    }

    /**
     * 查询申请管理列表
     *
     * @param keywords      关键词
     * @param applyOrg      申请部门
     * @param applyStatus   申请状态
     * @param currentUserId 当前用户id
     * @param page      分页
     * @return
     */
    @Override
    public JsonResult getQueryVideoList(String keywords, String applyOrg, String applyStatus, String currentUserId, Page page,String module) {
        try {
            HashMap<String,Object> condition = new HashMap<String,Object>();
            condition.put("keywords",keywords);
            condition.put("applyOrg",applyOrg);
            condition.put("applyStatus",applyStatus);
            condition.put("apply",currentUserId);


            String userId = Tools.getUserId();
            if (null != userId){
                SysDepartment deptById = sysDepartmentMapper.getDeptById(userId);
                String orgId = deptById.getDepartmentId();
                condition.put("userId",userId);
                condition.put("orgId",orgId);
                JsonResult jsonResult = resAuthService.getDataAuthRule(currentUserId,module);
                String dataRule = (String) jsonResult.getData();
                if (dataRule.equals("")){
                    return new JsonResult(0,null,"111116");
                }
                condition.put("dataRule",dataRule);
                Integer integer = postVideoMapper.countQueryVideoList(condition);
                page.setAllRow(integer);
                condition.put("currentPage",page.getStart());
                condition.put("size",page.getSize());
                List<QueryVideoDto> postVideoList = postVideoMapper.getQueryVideoList(condition);
                return new JsonResult(1,postVideoList);
            }else{
                return new JsonResult(0,null,"111116");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }



    /**
     * 查询审批管理
     *
     * @param keywords
     * @param applyOrg
     * @param applyStatus
     * @param currentUserId
     * @param page
     * @return
     */
    @Override
    public JsonResult getQueryApprovalVideoList(String keywords, String applyOrg, String applyStatus, String currentUserId, Page page) {
        try {
            HashMap<String,Object> condition = new HashMap<String,Object>();
            condition.put("keywords",keywords);
            condition.put("applyOrg",applyOrg);
            condition.put("applyStatus",applyStatus);
            condition.put("apply",currentUserId);
            Integer integer = postVideoMapper.countQueryApprovalVideoList(condition);
            page.setAllRow(integer);
            condition.put("currentPage",page.getStart());
            condition.put("size",page.getSize());
            List<QueryVideoDto> postVideoList = postVideoMapper.getQueryApprovalVideoList(condition);
            return new JsonResult(1,postVideoList);

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    /**
     * 查询关键词列表
     *
     * @return
     */
    @Override
    public JsonResult queryKeywordsList(Page page) {
        try {
            Integer count = postVideoMapper.countKeywordsList();
            page.setAllRow(count);
            HashMap<String,Object> condition = new HashMap<String,Object>();
            condition.put("currentPage",page.getStart());
            condition.put("size",page.getSize());
           List<HashMap<String,Object>> map = postVideoMapper.queryKeywordsList(condition);
           return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集统计表
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjTable(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCjTable(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集统计饼图
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjPie(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCjPie(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集折线图
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjLine(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCjLine(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集统计柱状图
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjBar(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCjBar(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集统计表
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCxTable(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCxTable(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集统计饼图
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCxPie(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCxPie(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集折线图
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCxLine(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCxLine(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集统计柱状图
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCxBar(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCxBarPlus(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    @Override
    public JsonResult getVideoCxBarTop10(HashMap<String, Object> condition) {
        try {
            List<HashMap<String,Object>> map = postVideoMapper.getVideoCxBarTop10(condition);
            return new JsonResult(1,map);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    @Override
    public JsonResult getVideoCjReport1(HashMap<String, Object> condition,String type) {
        try {
            HashMap<String,Object> data = new HashMap<String,Object>();
            if ("1".equals(type)){
                List<HashMap<String,Object>> table = postVideoMapper.getVideoCjTable(condition);
                List<HashMap<String,Object>> pie = postVideoMapper.getVideoCjPie(condition);
                List<HashMap<String,Object>> line = postVideoMapper.getVideoCjLine(condition);
                data.put("table",table);
                HashMap<String,Object> pieData = new HashMap<String,Object>();
                pieData.put("data",pie);
                String[] legend = new String[]{"图片","视频","音频"};
                pieData.put("legend",legend);
                data.put("pie",pieData);
                HashMap<String,Object> lineData = new HashMap<String,Object>();
                List<HashMap<String,Object>> yAxis = new ArrayList<HashMap<String,Object>>();
                List<String> xAxis = new ArrayList<String>();
                for (HashMap<String, Object> map : line) {
                    xAxis.add(map.get("updateTime").toString());
                }
                for (int i=0;i<legend.length;i++){
                    HashMap<String,Object> temp = new HashMap<String,Object>();
                    temp.put("name",legend[i]);
                    List<Object> values = new ArrayList<Object>();
                    for (HashMap<String, Object> map : line) {
                        values.add(map.get(legend[i]));
                    }
                    temp.put("data",values);
                    temp.put("type","line");
                    temp.put("stack","总量"+i);
                    yAxis.add(temp);
                }
                lineData.put("data",yAxis);
                lineData.put("legend",legend);
                lineData.put("xAxis",xAxis);
                data.put("line",lineData);
            }else {
                List<HashMap<String,Object>> table = postVideoMapper.getVideoCxTable(condition);
                List<HashMap<String,Object>> pie = postVideoMapper.getVideoCxPie(condition);
                List<HashMap<String,Object>> line = postVideoMapper.getVideoCxLine(condition);
                data.put("table",table);
                String[] legend1 = new String[]{"展陈","社教","研究","新闻发布","其他"};
                String[] legend = new String[]{"申请","下载"};
                HashMap<String,Object> pieData = new HashMap<String,Object>();
                pieData.put("data",pie);
                pieData.put("legend",legend1);
                data.put("pie",pieData);
                HashMap<String,Object> lineData = new HashMap<String,Object>();
                List<HashMap<String,Object>> yAxis = new ArrayList<HashMap<String,Object>>();
                List<String> xAxis = new ArrayList<String>();
                for (HashMap<String, Object> map : line) {
                    xAxis.add(map.get("updateTime").toString());
                }
                for (int i=0;i<legend.length;i++){
                    HashMap<String,Object> temp = new HashMap<String,Object>();
                    temp.put("name",legend[i]);
                    List<Object> values = new ArrayList<Object>();
                    for (HashMap<String, Object> map : line) {
                        values.add(map.get(legend[i]));
                    }
                    temp.put("data",values);
                    temp.put("type","line");
                    temp.put("stack","总量"+i);
                    yAxis.add(temp);
                }
                lineData.put("data",yAxis);
                lineData.put("legend",legend);
                lineData.put("xAxis",xAxis);
                data.put("line",lineData);
            }
            return new JsonResult(1,data);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }
    @Override
    public JsonResult getVideoCjReport2(HashMap<String, Object> condition,String type) {
        try {
            HashMap<String,Object> data = new HashMap<String,Object>();
            if ("1".equals(type)){
                List<HashMap<String,Object>> bar = postVideoMapper.getVideoCjBarPlus(condition);
                HashMap<String,Object> barData = new HashMap<String,Object>();
                String[] legend = new String[]{"图片","视频","音频"};
                List<HashMap<String,Object>> yAxis = new ArrayList<HashMap<String,Object>>();
                List<String> xAxis = new ArrayList<String>();
                for (HashMap<String, Object> map : bar) {
                    xAxis.add(map.get("orgName").toString());
                }
                for (int i=0;i<legend.length;i++){
                    HashMap<String,Object> temp = new HashMap<String,Object>();
                    temp.put("name",legend[i]);
                    List<Object> values = new ArrayList<Object>();
                    for (HashMap<String, Object> map : bar) {
                        values.add(map.get(legend[i]));
                    }
                    temp.put("data",values);
                    temp.put("type","bar");
                    temp.put("barGap",0);
                    yAxis.add(temp);
                }
                barData.put("data",yAxis);
                barData.put("legend",legend);
                barData.put("xAxis",xAxis);
                data.put("bar",barData);
            }else {
                List<HashMap<String,Object>> bar = postVideoMapper.getVideoCxBarPlus(condition);
                HashMap<String,Object> barData = new HashMap<String,Object>();
                String[] legend = new String[]{"申请","下载"};
                List<HashMap<String,Object>> yAxis = new ArrayList<HashMap<String,Object>>();
                List<String> xAxis = new ArrayList<String>();
                for (HashMap<String, Object> map : bar) {
                    xAxis.add(map.get("orgName").toString());
                }
                for (int i=0;i<legend.length;i++){
                    HashMap<String,Object> temp = new HashMap<String,Object>();
                    temp.put("name",legend[i]);
                    List<Object> values = new ArrayList<Object>();
                    for (HashMap<String, Object> map : bar) {
                        values.add(map.get(legend[i]));
                    }
                    temp.put("data",values);
                    temp.put("type","bar");
                    temp.put("barGap",0);
                    yAxis.add(temp);
                }
                barData.put("data",yAxis);
                barData.put("legend",legend);
                barData.put("xAxis",xAxis);
                data.put("bar",barData);
                List<HashMap<String,Object>> top10 = postVideoMapper.getVideoCxBarTop10(condition);
                HashMap<String,Object> top10Data = new HashMap<String,Object>();
                List<HashMap<String,Object>> yAxis1 = new ArrayList<HashMap<String,Object>>();
                List<String> xAxis1 = new ArrayList<String>();
                for (HashMap<String, Object> map : top10) {
                    xAxis1.add(map.get("apply").toString());
                }
                for (int i=0;i<legend.length;i++){
                    HashMap<String,Object> temp = new HashMap<String,Object>();
                    temp.put("name",legend[i]);
                    List<Object> values = new ArrayList<Object>();
                    for (HashMap<String, Object> map : top10) {
                        values.add(map.get(legend[i]));
                    }
                    temp.put("data",values);
                    temp.put("type","bar");
                    temp.put("barGap",0);
                    yAxis1.add(temp);
                }
                top10Data.put("data",yAxis1);
                top10Data.put("legend",legend);
                top10Data.put("xAxis",xAxis1);
                data.put("top10",top10Data);
            }
            return new JsonResult(1,data);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询待办
     *
     * @param currentUserId 用户id
     * @return
     */
    @Override
    public JsonResult getUndoTask(String currentUserId) {
        try {
            HashMap<String,Object> condition = new HashMap<String, Object>(5);
            condition.put("currentUserId",currentUserId);
            List<HashMap<String, Object>> undoTask = postVideoMapper.getUndoTask(condition);
            return new JsonResult(1,undoTask);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询已办
     *
     * @param currentUserId 用户id
     * @return
     */
    @Override
    public JsonResult getDoneTask(String currentUserId) {
        try {
            HashMap<String,Object> condition = new HashMap<String, Object>(5);
            condition.put("currentUserId",currentUserId);
            List<HashMap<String, Object>> doneTask = postVideoMapper.getDoneTask(condition);
            return new JsonResult(1,doneTask);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询已办结
     *
     * @param currentUserId 用户id
     * @return
     */
    @Override
    public JsonResult getFinishTask(String currentUserId) {
        try {
            HashMap<String,Object> condition = new HashMap<String, Object>(5);
            condition.put("currentUserId",currentUserId);
            List<HashMap<String, Object>> finishTask = postVideoMapper.getFinishTask(condition);
            return new JsonResult(1,finishTask);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视采集统计
     *
     * @return
     */
    @Override
    public JsonResult getVideoCjCount(String module) {
        try {
            String userId = Tools.getUserId();
            HashMap<String,Object> condition = new HashMap<String,Object>();
            if (null != userId) {
                SysDepartment deptById = sysDepartmentMapper.getDeptById(userId);
                String orgId = deptById.getDepartmentId();
                condition.put("userId", userId);
                condition.put("orgId", orgId);
                JsonResult jsonResult = resAuthService.getDataAuthRule(userId, module);
                String dataRule = (String) jsonResult.getData();
                if (dataRule.equals("")) {
                    return new JsonResult(0, null, "111116");
                }
                condition.put("dataRule", dataRule);
                HashMap<String, Object> count = postVideoMapper.getVideoCjCount(condition);
                return new JsonResult(1, count);
            }else{
                return new JsonResult("111116");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视查询统计
     *
     * @return
     */
    @Override
    public JsonResult getVideoCxCount(String module) {
        try {
            String userId = Tools.getUserId();
            HashMap<String,Object> condition = new HashMap<String,Object>();
            if (null != userId) {
                SysDepartment deptById = sysDepartmentMapper.getDeptById(userId);
                String orgId = deptById.getDepartmentId();
                condition.put("userId", userId);
                condition.put("orgId", orgId);
                JsonResult jsonResult = resAuthService.getDataAuthRule(userId, module);
                String dataRule = (String) jsonResult.getData();
                if (dataRule.equals("")) {
                    return new JsonResult(0, null, "111116");
                }
                condition.put("dataRule", dataRule);
                HashMap<String, Integer> count = new HashMap<>(3);
                Integer videoOpenCount = postVideoMapper.getVideoOpenCount(condition);
                Integer videoCxApply = postVideoMapper.getVideoCxApply(condition);
                Integer videoCxApproval = postVideoMapper.getVideoCxApproval(condition);
                count.put("videoOpenCount", videoOpenCount);
                count.put("videoCxApply", videoCxApply);
                count.put("videoCxApproval", videoCxApproval);
                return new JsonResult(1, count);
            }else {
                return new JsonResult("111116");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询快捷入口设置
     *
     * @param currentUserId
     * @param type 类型(1、影视,2、文献)
     * @return
     */
    @Override
    public JsonResult getShortcutEntrance(String currentUserId,String currentId,String type) {
        List<SysFunctionMenuDto> list = new ArrayList<SysFunctionMenuDto>();
        try {
            JsonResult roleAuthList = resAuthService.queryResAuthList(currentUserId);
            if (null == roleAuthList || roleAuthList.getSuccess() == 0 || roleAuthList.getData() == null){
                return null;
            }
            List<SysFunction> functions = (List<SysFunction>)roleAuthList.getData();
            HashSet<String> keyMap = new HashSet<String>();
            for (int i=0;i<functions.size();i++){
                keyMap.add(functions.get(i).getId());
            }
            list = resAuthService.queryMenuListByPid(list,keyMap,currentId,"0");
            List<HashMap<String,Object>> shortcutEntrance = new ArrayList<HashMap<String,Object>>();
            List<HashMap<String, Object>> shortcutEntrance1 = postShortcutentranceMapper.getShortcutEntrance(currentUserId,type);
            if (null == shortcutEntrance1 || shortcutEntrance1.size()==0){
                if (null != list && list.size()>0){
                    for (SysFunctionMenuDto sysFunctionMenuDto : list) {
                        List<SysFunctionMenuDto> tempList = sysFunctionMenuDto.getList();
                        if (null != tempList && tempList.size()>0){
                            for (SysFunctionMenuDto functionMenuDto : tempList) {
                                if ("1".equals(functionMenuDto.getExt1())){
                                    HashMap<String,Object> temp = new HashMap<String,Object>();
                                    temp.put("id",functionMenuDto.getKey());
                                    temp.put("name",functionMenuDto.getName());
                                    temp.put("type",functionMenuDto.getType());
                                    temp.put("icon",functionMenuDto.getExt2());
                                    temp.put("url",functionMenuDto.getUrl());
                                    temp.put("checked","1");
                                    shortcutEntrance.add(temp);
                                }
                            }
                        }else{
                            if ("1".equals(sysFunctionMenuDto.getExt1())){
                                HashMap<String,Object> temp = new HashMap<String,Object>();
                                temp.put("id",sysFunctionMenuDto.getKey());
                                temp.put("name",sysFunctionMenuDto.getName());
                                temp.put("type",sysFunctionMenuDto.getType());
                                temp.put("icon",sysFunctionMenuDto.getExt2());
                                temp.put("url",sysFunctionMenuDto.getUrl());
                                temp.put("checked","1");
                                shortcutEntrance.add(temp);
                            }
                        }

                    }
                }
            }else {
                HashSet<String> set = new HashSet<String>();
                for (HashMap<String, Object> map : shortcutEntrance1) {
                    if (!StringUtils.isEmpty(map)){
                        set.add(map.get("id").toString());
                    }
                }
                if (null != list && list.size()>0){
                    for (SysFunctionMenuDto sysFunctionMenuDto : list) {
                        List<SysFunctionMenuDto> tempList = sysFunctionMenuDto.getList();
                        if (null != tempList && tempList.size()>0){
                            for (SysFunctionMenuDto functionMenuDto : tempList) {
                                if ("1".equals(functionMenuDto.getExt1())) {
                                    HashMap<String, Object> temp = new HashMap<String, Object>();
                                    temp.put("id", functionMenuDto.getKey());
                                    temp.put("name", functionMenuDto.getName());
                                    temp.put("type", functionMenuDto.getType());
                                    temp.put("icon", functionMenuDto.getExt2());
                                    temp.put("url", functionMenuDto.getUrl());
                                    if (set.contains(functionMenuDto.getKey())) {
                                        temp.put("checked", "1");
                                    } else {
                                        temp.put("checked", "0");
                                    }
                                    shortcutEntrance.add(temp);
                                }
                            }
                        }else {
                            if ("1".equals(sysFunctionMenuDto.getExt1())) {
                                HashMap<String, Object> temp = new HashMap<String, Object>();
                                temp.put("id", sysFunctionMenuDto.getKey());
                                temp.put("name", sysFunctionMenuDto.getName());
                                temp.put("type", sysFunctionMenuDto.getType());
                                temp.put("icon", sysFunctionMenuDto.getExt2());
                                temp.put("url", sysFunctionMenuDto.getUrl());
                                if (set.contains(sysFunctionMenuDto.getKey())) {
                                    temp.put("checked", "1");
                                } else {
                                    temp.put("checked", "0");
                                }
                                shortcutEntrance.add(temp);
                            }
                        }

                    }
                }
            }
            return new JsonResult(1,shortcutEntrance);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("51000003");
        }
    }

    /**
     * 设置快捷入口
     *
     * @param currentUserId
     * @param shortcutEntrance
     * @param type 类型(1、影视,2、文献)
     * @return
     */
    @Override
    @Transactional
    public JsonResult setShortcutEntrance(String currentUserId, List<String> shortcutEntrance,String type) {
        try {
            int count  = postShortcutentranceMapper.deleteShortcutEntrance(currentUserId,type);
            int result = 0;
            for (String temp : shortcutEntrance) {
                PostShortcutentrance postShortcutentrance = new PostShortcutentrance();
                postShortcutentrance.setXid(IdUtils.getIncreaseIdByNanoTime());
                postShortcutentrance.setOwner(currentUserId);
                postShortcutentrance.setOwnType("user");
                postShortcutentrance.setPartyId(temp);
                postShortcutentrance.setPartyType(type);
                postShortcutentrance = setActionInfo(postShortcutentrance,"0");
                result = result+postShortcutentranceMapper.insertSelective(postShortcutentrance);
            }
            return new JsonResult(1,result);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("51000002");
        }
    }

    /**
     * 检测用户是不是影视部
     *
     * @param userId
     * @return
     */
    @Override
    public boolean checkUserOrg(String userId) {
        String pid = postVideoMapper.getOrgPidByUser(userId);
        //馆领导
        if ("-1".equals(pid)){
            return true;
        }else {
            List<SysUser> ysb = postVideoMapper.checkUserOrg(userId, "ysb");
            if (ysb != null && ysb.size()>0){
                return true;
            }
        }
        return false;
    }

    /**
     * 查询影视资料关联的附件
     *
     * @param postVideoId
     * @return
     */
    @Override
    public List<Attachment> getPostVideoAttachment(String postVideoId) {
        List<Attachment> attachments = postVideoMapper.getPostVideoAttachment(postVideoId);
        return attachments;
    }

    /**
     * 查询流程状态
     *
     * @param userId
     * @param postVideoId
     * @return
     */
    @Override
    public JsonResult getProcessUserStatus(String userId, String postVideoId,String type) {
        try {
            String status = postVideoMapper.getProcessUserStatus(userId,postVideoId,type);
            return new JsonResult(1,status);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    @Override
    public JsonResult getPostVideoForExhib(PostVideo postVideo) {
        List<PostVideo> list = postVideoMapper.getPostVideoForExhib(postVideo);
        for (PostVideo video : list) {

        }
        JsonResult result = new JsonResult(1, list);
        result.setCode(0);
        return result;
    }

    /**
     * 根据关键词查询影视资料列表
     *
     * @param keyId 关键词
     * @return
     */
    @Override
    public JsonResult getVideoListByKeywords(String keyId) {
        try {
            List<PostVideo> postVideoList = postVideoMapper.getVideoListByKeywords(keyId);
            return new JsonResult(1,postVideoList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }
}


package com.tj720.controller;

import com.tj720.config.FileUploadConfig;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.DelayTime;
import com.tj720.model.common.system.menu.MenuTreeDto;
import com.tj720.model.common.video.*;
import com.tj720.model.common.wf.WfAction;
import com.tj720.model.common.wf.WfDetail;
import com.tj720.service.*;
import com.tj720.service.impl.MyTask;
import com.tj720.utils.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/22 11:41
 */
@RestController
@RequestMapping("/PostVideo")
public class PostVideoController {
    @Autowired
    PostVideoService postVideoService;
    @Autowired
    PostVideoCommentsService postVideoCommentsService;
    @Autowired
    WfService wfService;
    @Autowired
    RoleAuthService roleAuthService;
    @Autowired
    PostVideoTypeService postVideoTypeService;
    //消息通知
    @Autowired
    SysNoticeService sysNoticeService;
    @Autowired
    Config config;
    @Autowired
    PostLsService postLsService;

    private FileUploadConfig fileUploadConfig;

//    private String userCode = "sysadmin";

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
     * 查询影视资料列表
     * @param keywords 关键词
     * @param videoMark 影视分类
     * @param status 状态
     * @param userId 用户id
     * @param uploadOrg 用户部门
     * @param userType 用户类型（影视部/非影视部）
     * @param page
     * @param limit
     * @return
     */
    @ControllerAop(action = "查询影视资料列表")
    @RequestMapping("/queryPostVideoList")
    public LayUiTableJson queryPostVideoList(String keywords, String videoMark, String status,@RequestParam() String userId,
                                             @RequestParam() String uploadOrg,@RequestParam() String userType,
                                             @RequestParam() String module,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult postVideoList = postVideoService.getPostVideoList(keywords, videoMark, status
                , pageInfo,uploadOrg,userId,userType,module);
        if (postVideoList != null && postVideoList.getSuccess() == 1){
            if (null != postVideoList){
                List<PostVideo> postVideos = (List<PostVideo>)postVideoList.getData();
                return new LayUiTableJson(0,postVideoList.getMsg(),pageInfo.getAllRow(),postVideos);
            }else {
                return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
        }

    }
    @ControllerAop(action = "查询影视资料列表")
    @RequestMapping("/queryVedilListForCompreQuery")
    public LayUiTableJson queryVedilListForCompreQuery(String keywords, String videoMark, String status, @RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit, String orderBy) {
        try {
            Page pageInfo = new Page();
            pageInfo.setCurrentPage(page);
            pageInfo.setSize(limit);
            JsonResult postVideoList = postVideoService.queryVedilListForCompreQuery(keywords, videoMark, status
                    , pageInfo, orderBy);
            if (postVideoList != null && postVideoList.getSuccess() == 1){
                if (null != postVideoList){
                    List<PostVideo> postVideos = (List<PostVideo>)postVideoList.getData();
                    return new LayUiTableJson(0,postVideoList.getMsg(),pageInfo.getAllRow(),postVideos);
                }else {
                    return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
                }
            }else {
                return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LayUiTableJson(1,"20000003",0,null);
    }

    /**
     * 影视资料dto
     * @param postVideoDto
     * @return
     */
    @ControllerAop(action = "上传影视资料")
    @RequestMapping("/addPostVideo")
    public JsonResult addPostVideo(@RequestBody PostVideoDto postVideoDto){
        PostVideo postVideo = postVideoDto.getPostVideo();
        //新增影视资料
        //设置为草稿状态
        postVideo.setStatus("1");
        JsonResult jsonResult = postVideoService.addPostVideo(postVideo);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            postLsService.addLsCode("videoList","video");
            postVideo = ((List<PostVideo>)jsonResult.getData()).get(0);
            //新增编目信息
            List<PostVideoComments> postVideoComments = postVideoDto.getPostVideoComments();
            if(null != postVideoComments && postVideoComments.size()>0){
                JsonResult PostVideoCommentsResult = postVideoCommentsService.addPostVideoCommentList(postVideo.getId()
                        , postVideoComments);
                if (null == PostVideoCommentsResult || PostVideoCommentsResult.getSuccess() == 0){
                    return PostVideoCommentsResult;
                }
            }
            //添加流程信息
            String actionType = postVideoDto.getActionType();
            //0:草稿,1:提交中,2:不走流程直接发布
            //草稿
            if ("1".equals(actionType)){
                boolean flag = postVideoService.checkUserOrg(Tools.getUserId());
                //非影视部
                if (!flag){
                    WfAction wfAction = wfService.addWfAction(postVideo.getId(),"1","0"
                            ,postVideoDto.getCurrentUserId(),postVideo.getUploadOrg(), new Date(),postVideo.getVideoName(),"1"
                            ,postVideo.getApproval());

                    wfService.addWfDetail(wfAction.getXid(),"0","2","1","创建申请",
                            new Date(),postVideoDto.getCurrentUserId(),postVideoDto.getCurrentUserId(),postVideo.getUploadOrg());
                }

            }
            //保存并提交
            else if ("2".equals(actionType)){
                WfAction wfAction = wfService.addWfAction(postVideo.getId(),"1","0"
                        ,postVideoDto.getCurrentUserId(),postVideo.getUploadOrg(), new Date(),postVideo.getVideoName(),"1"
                        ,postVideo.getApproval());
                wfService.addWfDetail(wfAction.getXid(),"0","2","1","创建申请",
                        new Date(),postVideoDto.getCurrentUserId(),postVideoDto.getCurrentUserId(),postVideo.getUploadOrg());
                postVideo.setStatus("2");
                postVideoService.updateVideo(postVideo);
                WfAction wfAction1 = wfService.getWfActionByPartyId(postVideo.getId(),"1");
                wfAction.setApplyStatus("1");
                wfService.updateWfAction(wfAction1);
                //添加审批记录
//                List<SysUser> users = roleAuthService.getUserListByRole(postVideo.getApproval());
                //添加申请人的已办流程记录
                wfService.addWfDetail(wfAction1.getXid(),"1","2","2","提交申请",
                        new Date(),postVideoDto.getCurrentUserId(),postVideoDto.getCurrentUserId(),postVideo.getUploadOrg());

                //启动定时任务(2小时后自动提交流程)
                Timer timer1 = new Timer();
//                timer1.schedule(new MyTask(wfAction.getXid()),2*60*60*100);
                timer1.schedule(new MyTask(wfAction.getXid(),postVideoDto.getCurrentUserId(),wfService,postVideoService,sysNoticeService), DelayTime.getDelayTime());

            }
            //保存并发布
            else if ("3".equals(actionType)){
                //直接发布资料
                postVideo.setStatus("4");
                postVideoService.updateVideo(postVideo);
                WfAction wfAction1 = wfService.getWfActionByPartyId(postVideo.getId(),"1");
//                wfService.addWfDetail(wfAction1.getXid(),"1","3","4","发布",
//                        new Date(),postVideoDto.getCurrentUserId(),postVideoDto.getCurrentUserId(),postVideo.getUploadOrg());
                postVideoService.checkVideoCode(postVideo);
            }
            return jsonResult;
        }else {
            return jsonResult;
        }
    }

    /**
     * 修改影视资料
     * @param postVideoDto 影视资料dto
     * @return
     */
    @ControllerAop(action = "修改影视资料")
    @RequestMapping("/updatePostVideo")
    public JsonResult updatePostVideo(@RequestBody PostVideoDto postVideoDto){
        PostVideo postVideo = postVideoDto.getPostVideo();
        //新增影视资料
        //设置为草稿状态
//        postVideo.setStatus("1");
        JsonResult jsonResult = postVideoService.updateVideo(postVideo);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            //修改标注信息
            List<PostVideoComments> postVideoComments = postVideoDto.getPostVideoComments();
            if(null != postVideoComments && postVideoComments.size()>0){
                JsonResult PostVideoCommentsResult = postVideoCommentsService.updatePostVideoCommentList(postVideo.getId(),postVideoComments);
                if (null == PostVideoCommentsResult || PostVideoCommentsResult.getSuccess() == 0){
                    return PostVideoCommentsResult;
                }
            }
            String actionType = postVideoDto.getActionType();
            //如果是非影视部编辑
            if ("1".equals(actionType)){
                //修改审批人(暂不处理)
                WfAction wfActionByPartyId = wfService.getWfActionByPartyId(postVideo.getId(), "1");
                //若果是草稿
//                if ("1".equals(wfActionByPartyId.getApplyStatus())){
//
//                }
            }
            return jsonResult;
        }else {
            return jsonResult;
        }
    }

    /**
     * 删除影视资料
     * @param id 资料id
     * @return
     */
    @ControllerAop(action = "删除影视资料")
    @RequestMapping("/deletePostVideo")
    public JsonResult deletePostVideo(@RequestParam String id){
        JsonResult jsonResult = postVideoService.deleteVideo(id);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            //根据id删除标注信息
            JsonResult PostVideoCommentsResult = postVideoCommentsService.deletePostVideoCommentList(id);
            if (null == PostVideoCommentsResult || PostVideoCommentsResult.getSuccess() == 0){
                return PostVideoCommentsResult;
            }
            //删除关联流程
            return jsonResult;
        }else {
            return jsonResult;
        }
    }

    /**
     * 撤回影视资料流程
     * @param id
     * @param currentUserId 用户
     * @return
     */
    @ControllerAop(action = "撤回影视资料流程")
    @RequestMapping("/revokeProcess")
    public JsonResult revokeProcess(@RequestParam String id,String currentUserId){
        JsonResult jsonResult = postVideoService.revokeProcess(id,currentUserId);
        return jsonResult;
    }

    /**
     * 提交影视资料流程
     * @param id
     * @return
     */
    @ControllerAop(action = "提交影视资料流程")
    @RequestMapping("/commitProcess")
    public JsonResult commitProcess(@RequestParam String id,@RequestParam String currentUserId){
        JsonResult jsonResult = postVideoService.commitProcess(id,currentUserId);
        return jsonResult;
    }
    @ControllerAop(action = "发布影视资料流程")
    @RequestMapping("/publishProcess")
    public JsonResult publishProcess(@RequestParam String id,@RequestParam String currentUserId){
        JsonResult jsonResult = postVideoService.publishProcess(id,currentUserId);
        return jsonResult;
    }

    /**
     * 审批
     * @param approvalDto 审批信息
     * @return
     */
    @ControllerAop(action = "审批影视资料流程")
    @RequestMapping("/approvalProcess")
    public JsonResult approvalProcess(@RequestBody ApprovalDto approvalDto){
        JsonResult jsonResult = postVideoService.approvalProcess(approvalDto.getId(),approvalDto.getAuthSetting()
                ,approvalDto.getActionType(),approvalDto.getApproval(),approvalDto.getRemark(),approvalDto.getCurrentUserId());
        return jsonResult;
    }
    @ControllerAop(action = "批量审批影视资料流程")
    @RequestMapping("/batchApprovalProcess")
    public JsonResult batchApprovalProcess(@RequestBody ApprovalDto approvalDto){
        JsonResult jsonResult = postVideoService.batchApprovalProcess(approvalDto.getIds(),approvalDto.getAuthSetting()
                ,approvalDto.getActionType(),approvalDto.getApproval(),approvalDto.getRemark(),approvalDto.getCurrentUserId());
        return jsonResult;
    }

    /**
     * 设置权限
     * @param id
     * @param authSetting
     * @return
     */
    @ControllerAop(action = "设置权限")
    @RequestMapping("/setAuthSetting")
    public JsonResult setAuthSetting(@RequestParam String id,@RequestParam String authSetting){
        JsonResult jsonResult = postVideoService.changeAuthSetting(id,authSetting);
        return jsonResult;
    }

    /**
     * 批量设置权限
     * @param map
     * @return
     */
    @ControllerAop(action = "批量设置权限")
    @RequestMapping("/batchSetAuthSetting")
    public JsonResult batchSetAuthSetting(@RequestBody HashMap<String,Object> map){
        List<String> ids = (List<String>) map.get("ids");
        String authSetting = map.get("map").toString();
        JsonResult jsonResult = postVideoService.batchChangeAuthSetting(ids,authSetting);
        return jsonResult;
    }
    @ControllerAop(action = "查询影视资料详情")
    @RequestMapping("queryPostVideoDtoById")
    public JsonResult queryPostVideoDtoById(String id){
        JsonResult postVideo = postVideoService.getPostVideo(id);
        if (null != postVideo && postVideo.getSuccess() == 1){
            PostVideo postVideoInfo = (PostVideo)postVideo.getData();
            //查询标注信息
            JsonResult postVideoCommentList = postVideoCommentsService.getPostVideoCommentList(id);
            //查询编目信息
            return postVideo;
        }else {
            return postVideo;
        }
    }
    @ControllerAop(action = "查询流程详情")
    @RequestMapping("/getActionInfoListByProcessId")
    public LayUiTableJson getActionInfoListByProcessId(@RequestParam String processInstId){
        JsonResult actionInfo = wfService.getWfDetailByProcessId(processInstId);
        if (actionInfo != null && actionInfo.getSuccess() == 1){
            List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
            return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
        }else {
            return new LayUiTableJson(0,actionInfo.getMsg(),0,null);
        }
    }

    /**
     * 查询上传资料的审批记录
     * @param id
     * @return
     */
    @ControllerAop(action = "查询上传资料的审批记录")
    @RequestMapping("/getUploadVideoInfo")
    public LayUiTableJson getUploadVideoInfo(@RequestParam String id){
        WfAction wfActionByPartyId = wfService.getWfActionByPartyId(id, "1");
        if (null != wfActionByPartyId){
            String processId = wfActionByPartyId.getXid();
            JsonResult actionInfo = wfService.getWfDetailByProcessId(processId);
            if (actionInfo != null && actionInfo.getSuccess() == 1){
                List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
                return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
            }else {
                return new LayUiTableJson(0,actionInfo.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(0,"无数据",0,null);
        }


    }

    /**
     * 查询上传审批列表
     * @param keywords 关键词
     * @param source 来源
     * @param status 状态
     * @param currentUserId 当前登录用户
     * @param page
     * @param limit
     * @return
     */
    @ControllerAop(action = "查询上传审批列表")
    @RequestMapping("/getUploadApprovalList")
    public LayUiTableJson getUploadApprovalList(String keywords,String source,String status
            ,String currentUserId
            ,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult uplodApprovalList = postVideoService.getUplodApprovalList(keywords, source, status, pageInfo,currentUserId);
        if (null == uplodApprovalList || uplodApprovalList.getSuccess()==0){
            return new LayUiTableJson(0,"系统异常",0,null);
        }else {
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<UploadApprovalDto>) uplodApprovalList.getData());
        }
    }

    /**
     * 查询影视资料查询列表
     * @param keywords 关键词
     * @param videoMark 影视分类
     * @param status 状态
     * @param userType 用户类型（影视部/非影视部）
     * @param page
     * @param limit
     * @return
     */
    @ControllerAop(action = "查询影视资料查询列表")
    @RequestMapping("/queryPostVideoQueryList")
    public LayUiTableJson queryPostVideoQueryList(String keywords, String videoMark, String status,
                                                  @RequestParam String currentUserId,@RequestParam String uploadOrg, @RequestParam() String userType,@RequestParam() String module
            ,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult postVideoList = postVideoService.getPostVideoQueryList(keywords, videoMark, status,
                currentUserId,uploadOrg
                , pageInfo,userType,module);
        if (postVideoList != null && postVideoList.getSuccess() == 1){
            if (null != postVideoList){
                List<PostVideo> postVideos = (List<PostVideo>)postVideoList.getData();
                return new LayUiTableJson(0,postVideoList.getMsg(),pageInfo.getAllRow(),postVideos);
            }else {
                return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(0,"查询失败",0,null);
        }

    }

    /**
     * 保存查询申请
     * @param queryApplyDto
     * @return
     */
    @ControllerAop(action = "保存查询申请")
    @RequestMapping("/saveQueryApply")
    public JsonResult saveQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.saveQueryApply(queryApplyDto.getPostVideoId(), queryApplyDto.getApply(),
                queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
                queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult;
    }

    /**
     * 保存查询申请
     * @param queryApplyDto
     * @return
     */
    @ControllerAop(action = "批量保存查询申请")
    @RequestMapping("/batchSaveQueryApply")
    public JsonResult batchSaveQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.batchSaveQueryApply(queryApplyDto.getIds(), queryApplyDto.getApply(),
                queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
                queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult;
    }


    /**
     * 保存并提交查询申请
     * @param queryApplyDto
     * @return
     */
    @ControllerAop(action = "保存并提交查询申请")
    @RequestMapping("/saveAndSumitQueryApply")
    public JsonResult saveAndSumitQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.saveAndSubmitQueryApply(queryApplyDto.getPostVideoId(), queryApplyDto.getApply(),
                queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
                queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult;
    }

    /**
     * 保存并提交查询申请
     * @param queryApplyDto
     * @return
     */
    @ControllerAop(action = "批量保存并提交查询申请")
    @RequestMapping("/batchSaveAndSumitQueryApply")
    public JsonResult batchSaveAndSumitQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.batchSaveAndSubmitQueryApply(queryApplyDto.getIds(), queryApplyDto.getApply(),
        queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
        queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult;
    }

    /**
     * 撤回查询申请
     * @param queryApplyDto
     */
    @ControllerAop(action = "撤回查询申请")
    @RequestMapping("/revokeQueryApply")
    public JsonResult revokeQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.revokeQueryApply(queryApplyDto.getProcessInstId(), queryApplyDto.getApply()
                , queryApplyDto.getApplyOrg());
        return jsonResult;
    }

    /**
     * 提交查询申请
     * @param queryApplyDto
     * @return
     */
    @ControllerAop(action = "提交查询申请")
    @RequestMapping("/submitQueryApply")
    public JsonResult submitQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult1 = postVideoService.submitQueryApply(queryApplyDto.getProcessInstId(), queryApplyDto.getApplyTime(), queryApplyDto.getApply()
                , queryApplyDto.getApplyOrg(), queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult1;
    }

    /**
     * 审批查询申请
     * @param queryApplyDto
     * @return
     */
    @ControllerAop(action = "审批查询申请")
    @RequestMapping("/approvalQueryApply")
    public JsonResult approvalQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.approvalQueryApply(queryApplyDto.getProcessInstId(), queryApplyDto.getActionType()
                , queryApplyDto.getApproval(), queryApplyDto.getApply(), queryApplyDto.getApplyOrg(), queryApplyDto.getRemarks(),
                queryApplyDto.getCurrentUserId());
        return jsonResult;
    }

    /**
     * 查询查询资料的审批记录
     * @param id
     * @return
     */
    @ControllerAop(action = "查询查询资料的审批记录")
    @RequestMapping("/getQueryVideoInfo")
    public LayUiTableJson getQueryVideoInfo(@RequestParam String id,@RequestParam String currentUserId){
        WfAction wfActionByPartyId = wfService.getWfActionByPartyId(id, "2",currentUserId);
        if (null != wfActionByPartyId){
            String processId = wfActionByPartyId.getXid();
            JsonResult actionInfo = wfService.getWfDetailByProcessId(processId);
            if (actionInfo != null && actionInfo.getSuccess() == 1){
                List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
                return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
            }else {
                return new LayUiTableJson(1,actionInfo.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,"无数据",0,null);
        }


    }

    /**
     * 查询查询资料的审批记录
     * @param processInstId
     * @return
     */
    @ControllerAop(action = "查询查询资料的审批记录")
    @RequestMapping("/getQueryApplyRecord")
    public LayUiTableJson getQueryApplyRecord(@RequestParam String processInstId){
            JsonResult actionInfo = wfService.getWfDetailByProcessId(processInstId);
            if (actionInfo != null && actionInfo.getSuccess() == 1){
                List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
                return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
            }else {
                return new LayUiTableJson(1,actionInfo.getMsg(),0,null);
            }
    }

    /**
     * 申请管理列表
     * @param keywords
     * @param applyOrg
     * @param applyStatus
     * @param currentUserId
     * @param userType
     * @param page
     * @param limit
     * @return
     */
    @ControllerAop(action = "查询申请管理列表")
    @RequestMapping("/getQueryVideoList")
    public LayUiTableJson getQueryVideoList(String keywords,String applyOrg,String applyStatus,String currentUserId,@RequestParam String module
            ,String userType,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoService.getQueryVideoList(keywords,applyOrg,applyStatus,currentUserId,
                pageInfo,module);
        if (null == jsonResult || jsonResult.getSuccess()==0){
            return new LayUiTableJson(1,"查询失败",0,null);
        }else {
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<QueryVideoDto>)jsonResult.getData());
        }
    }


    /**
     * 查询审批列表
     * @param keywords
     * @param applyOrg
     * @param applyStatus
     * @param currentUserId
     * @param userType
     * @param page
     * @param limit
     * @return
     */
    @ControllerAop(action = "查询审批列表")
    @RequestMapping("/getQueryApprovalVideoList")
    public LayUiTableJson getQueryApprovalVideoList(String keywords,String applyOrg,String applyStatus,String currentUserId
            ,String userType,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoService.getQueryApprovalVideoList(keywords,applyOrg,applyStatus,currentUserId,
                pageInfo);
        if (null == jsonResult || jsonResult.getSuccess()==0){
            return new LayUiTableJson(1,"查询失败",0,null);
        }else {
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<QueryVideoDto>)jsonResult.getData());
        }
    }

    @ControllerAop(action = "查询申请人信息")
    @RequestMapping("/getApplyInfo")
    public JsonResult getApplyInfo(@RequestParam String processInstId){
        try {
            WfAction wfAction = wfService.getWfActionById(processInstId);
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }
    }
    @ControllerAop(action = "更新申请")
    @RequestMapping("/updateApply")
    public JsonResult updateApply( String processInstId,String applyReason,String remarks,String approval){
        try {
            WfAction wfAction = wfService.getWfActionById(processInstId);
            wfAction.setActionName(applyReason);
            wfAction.setApproval(approval);
            wfAction.setRemark(remarks);
            wfService.updateWfAction(wfAction);
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }

    }
    @ControllerAop(action = "更新申请并提交")
    @RequestMapping("/updateApplyAndSubmit")
    public JsonResult updateApplyAndSubmit(String processInstId,String applyReason,String remarks,String approval){
        try {
            WfAction wfAction = wfService.getWfActionById(processInstId);
            wfAction.setActionName(applyReason);
            wfAction.setApproval(approval);
            wfAction.setRemark(remarks);
            wfService.updateWfAction(wfAction);
            JsonResult jsonResult1 = postVideoService.submitQueryApply(wfAction.getXid(), wfAction.getApplyTime(), wfAction.getApply()
                    , wfAction.getApplyOrg(),remarks,approval);
            return jsonResult1;
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }

    }
    @ControllerAop(action = "删除申请")
    @RequestMapping("/deleteApply")
    public JsonResult deleteApply(@RequestParam String processInstId){
        JsonResult jsonResult = wfService.deleteWfAction(processInstId);
        return jsonResult;
    }
    @ControllerAop(action = "查询关键词")
    @RequestMapping("/queryKeywordsList")
    public LayUiTableJson queryKeywordsList(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoService.queryKeywordsList(pageInfo);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<HashMap>)jsonResult.getData());
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 查询资料分类列表
     * @param keywords 关键词
     * @param typeLevel 级别
     * @param status 状态
     * @param page 当前页数
     * @param limit 每页大小
     * @return
     */
    @ControllerAop(action = "查询影视资料分类列表")
    @RequestMapping("/queryPostVideoTypeList")
    public LayUiTableJson queryPostVideoTypeList(String keywords,String typeLevel,String status,@RequestParam(defaultValue = "1") Integer orderBy
            ,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeList(keywords, typeLevel, status,orderBy, pageInfo);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<PostVideoType>)jsonResult.getData());
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 添加分类
     * @param postVideoType
     * @return
     */
    @ControllerAop(action = "添加分类")
    @RequestMapping("/addPostVideoType")
    public JsonResult addPostVideoType(PostVideoType postVideoType){
        JsonResult jsonResult = postVideoTypeService.addPostVideoType(postVideoType);
        return jsonResult;
    }

    /**
     * 修改分类
     * @param postVideoType
     * @return
     */
    @ControllerAop(action = "修改分类")
    @RequestMapping("/updatePostVideoType")
    public JsonResult updatePostVideoType(PostVideoType postVideoType){
        JsonResult jsonResult = postVideoTypeService.updatePostVideoType(postVideoType);
        return jsonResult;
    }

    /**
     * 删除分类
     * @param id 分类id
     * @return
     */
    @ControllerAop(action = "删除分类")
    @RequestMapping("/deletePostVideoType")
    public JsonResult deletePostVideoType(@RequestParam String id){
        JsonResult jsonResult = postVideoTypeService.deletePostVideoType(id);
        return jsonResult;
    }
    @ControllerAop(action = "查询分类详情")
    @RequestMapping("/queryPostVideoTypeById")
    public JsonResult queryPostVideoTypeById(@RequestParam String id){
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeById(id);
        return jsonResult;
    }
    @ControllerAop(action = "查询分类列表")
    @RequestMapping("/queryPostVideoTypeListTree")
    public LayUiTableJson queryPostVideoTypeListTree(){
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeListTree();
        List<MenuTreeDto> list = (List<MenuTreeDto>)jsonResult.getData();
        List<HashMap> data = new ArrayList<HashMap>();
        for (MenuTreeDto menuTreeDto : list) {
            HashMap map = new HashMap();
            map.put("id",menuTreeDto.getId());
            map.put("name",menuTreeDto.getFunctionname());
            map.put("pId",menuTreeDto.getPid());
            map.put("open",null);
            map.put("chkDisabled","false");
            data.add(map);
        }

        return new LayUiTableJson(0,null,data.size(),data);

    }
    @ControllerAop(action = "查询分类列表")
    @RequestMapping("/queryPostVideoTypeListDist")
    public JsonResult queryPostVideoTypeListDist(String currentId){
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeListDist(currentId);
        return jsonResult;

    }
    @ControllerAop(action = "更新分类状态")
    @RequestMapping("/changeTypeStatus")
    public JsonResult changeTypeStatus(@RequestParam String id,@RequestParam int status){
        JsonResult jsonResult = postVideoTypeService.changeTypeStatus(id,status);
        return jsonResult;
    }
    @ControllerAop(action = "查询影视统计")
    @RequestMapping("/getVideoReport1")
    public JsonResult getVideoReport1(String status,String startTime,String endTime,String type){
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult jsonResult = postVideoService.getVideoCjReport1(condition,type);
        return jsonResult;
    }
    @ControllerAop(action = "查询影视统计")
    @RequestMapping("/getVideoReport2")
    public JsonResult getVideoReport2(String status,String startTime,String endTime,String type){
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult jsonResult = postVideoService.getVideoCjReport2(condition,type);
        return jsonResult;
    }

    /**
     * 查询待办
     * @param currentUserId 用户id
     * @return
     */
    @ControllerAop(action = "查询待办")
    @RequestMapping("/getUndoTask")
    public JsonResult getUndoTask(@RequestParam String currentUserId){
        JsonResult undoTask = postVideoService.getUndoTask(currentUserId);
        return undoTask;
    }
    /**
     * 查询已办
     * @param currentUserId 用户id
     * @return
     */
    @ControllerAop(action = "查询已办")
    @RequestMapping("/getDoneTask")
    public JsonResult getDoneTask(@RequestParam String currentUserId){
        JsonResult doneTask = postVideoService.getDoneTask(currentUserId);
        return doneTask;
    }
    /**
     * 查询已办结
     * @param currentUserId 用户id
     * @return
     */
    @ControllerAop(action = "查询已办结")
    @RequestMapping("/getFinishTask")
    public JsonResult getFinishTask(@RequestParam String currentUserId){
        JsonResult finishTask = postVideoService.getFinishTask(currentUserId);
        return finishTask;
    }

    /**
     * 查询影视采集统计
     * @return
     */
    @ControllerAop(action = "查询影视采集统计")
    @RequestMapping("/getVideoCjCount")
    public JsonResult getVideoCjCount(@RequestParam String module){
        JsonResult count = postVideoService.getVideoCjCount(module);
        return count;
    }
    /**
     * 查询影视查询统计
     * @return
     */
    @ControllerAop(action = "查询影视查询统计")
    @RequestMapping("/getVideoCxCount")
    public JsonResult getVideoCxCount(@RequestParam String module){
        JsonResult count = postVideoService.getVideoCxCount(module);
        return count;
    }

    /**
     * 加载快捷入口
     * @param currentUserId
     * @return
     */
    @ControllerAop(action = "加载快捷入口")
    @RequestMapping("/getShortcutEntrance")
    public JsonResult getShortcutEntrance(@RequestParam String currentUserId,@RequestParam String currentId,@RequestParam String type){
        JsonResult jsonResult = postVideoService.getShortcutEntrance(currentUserId,currentId,type);
        return jsonResult;
    }
    @ControllerAop(action = "设置快捷入口")
    @RequestMapping("/setShortcutEntrance")
    public JsonResult setShortcutEntrance(@RequestBody ShortcutEntranceDto shortcutEntranceDto){
        JsonResult jsonResult = postVideoService.setShortcutEntrance(shortcutEntranceDto.getCurrentUserId()
                ,shortcutEntranceDto.getShortcutEntrance(),shortcutEntranceDto.getType());
        return jsonResult;
    }

    /**
     * 查询流程记录
     * @param processInstId
     * @return
     */
    @ControllerAop(action = "查询流程记录")
    @RequestMapping("/getWfAction")
    public JsonResult getWfAction(@RequestParam String processInstId){
        try {
            WfAction wfActionById = wfService.getWfActionById(processInstId);
            return new JsonResult(1,wfActionById);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }
    @ControllerAop(action = "根据通知查询流程记录")
    @RequestMapping("/getWfActionByNotice")
    public JsonResult getWfActionByNotice(@RequestParam String id){
        JsonResult jsonResult = wfService.getWfActionByNotice(id);
        return jsonResult;
    }

    /**
     * 下载
     * @param postVideoId
     * @param request
     * @param response
     * @throws IOException
     */
    @ControllerAop(action = "下载影视资料")
    @RequestMapping("/downloadVideoFile")
    public void  downloadVideoFile(String postVideoId, HttpServletRequest request, HttpServletResponse response)throws IOException{
        JsonResult postVideo = postVideoService.getPostVideo(postVideoId);
        PostVideo video = (PostVideo)postVideo.getData();
        String zipName = video.getVideoName()+".zip";
        List<Attachment> fileList = postVideoService.getPostVideoAttachment(postVideoId);//查询数据库中记录
        for (Attachment attachment : fileList) {
            String downPath = config.getFtpRootPath() + config.getRootPath() + attachment.getAttPath();
            attachment.setAttPath(downPath);
        }
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

        try {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            zipName = new String(zipName.getBytes("UTF-8"), "ISO-8859-1");

            String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";
            Pattern pa = Pattern.compile(regex);
            Matcher ma = pa.matcher(zipName);
            if(ma.find()){
                zipName = ma.replaceAll("");
            }
            response.setHeader("Content-disposition", "attachment; filename=" +zipName);
//            FtpUtil ftpUtil = new FtpUtil(CheckConfig.getFtpUrl(), Integer.valueOf(CheckConfig.getFtpPort()), CheckConfig.getFtpUserName(), CheckConfig.getFtpPassWord());
//            ftpUtil.downFtpFiletoZip(fileList,out,response);
            fileUploadConfig = new FileUploadConfig(config);
            fileUploadConfig.downloadFileToZip(fileList,out,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量下载
     * @param postVideoId
     * @param request
     * @param response
     * @throws IOException
     */
    @ControllerAop(action = "批量下载影视资料")
    @RequestMapping("/batchDownloadVideoFile")
    public void  batchDownloadVideoFile(String postVideoId, HttpServletRequest request, HttpServletResponse response)throws IOException{
        String[] list = postVideoId.split(",");
        String zipName = "影视资料.xls";
        List<HashMap<String,Object>> attachments = new ArrayList<HashMap<String,Object>>();
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        for (String id : list) {
            JsonResult postVideo = postVideoService.getPostVideo(id);
            PostVideo video = (PostVideo)postVideo.getData();
            String fileName = video.getVideoName();
            List<Attachment> fileList = postVideoService.getPostVideoAttachment(id);//查询数据库中记录
            for (Attachment attachment : fileList) {
                String downPath = config.getFtpRootPath() + config.getRootPath() + attachment.getAttPath();
                attachment.setAttPath(downPath);
            }
            HashMap<String,Object> temp = new HashMap<String,Object>();
            temp.put("fileName",fileName);
            temp.put("attachment",fileList);
            attachments.add(temp);
        }
        try {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";
            Pattern pa = Pattern.compile(regex);
            Matcher ma = pa.matcher(zipName);
            if(ma.find()){
                zipName = ma.replaceAll("");
            }
            response.setHeader("Content-disposition", "attachment; filename=" +zipName);
//            FtpUtil ftpUtil = new FtpUtil(CheckConfig.getFtpUrl(), Integer.valueOf(CheckConfig.getFtpPort()), CheckConfig.getFtpUserName(), CheckConfig.getFtpPassWord());
//            ftpUtil.downFtpFiletoZipPlus(attachments,out,response);
            fileUploadConfig = new FileUploadConfig(config);
            fileUploadConfig.downloadFileToZipPlus(attachments,out,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ControllerAop(action = "导出影视统计")
    @RequestMapping("/exportVideoCjReport1")
    public void exportVideoCjReport1(String status,String startTime,String endTime,String type,
                                     HttpServletRequest request, HttpServletResponse response)throws IOException{
        String zipName = "影视采集统计1.xls";
        //交叉表数据
        String[] tableRow = new String[]{"存储类型","文物馆藏","中心活动","收集资料","拍摄素材","播出成片","其他"};
        if ("2".equals(type)){
            tableRow = new String[]{"操作类型","展陈","社教","研究","新闻发布","其他"};
        }
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult videoCjTable = postVideoService.getVideoCjTable(condition);
        List<Object[]> tableData = new ArrayList<Object[]>();
        if ("2".equals(type)){
            videoCjTable = postVideoService.getVideoCxTable(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjTable.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[6];
                temp[0] = map.get("type").toString();
                temp[1] = map.get("zc").toString();
                temp[2] = map.get("sj").toString();
                temp[3] = map.get("yj").toString();
                temp[4] = map.get("xwfb").toString();
                temp[5] = map.get("qt").toString();
                tableData.add(temp);
            }
        }else {
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjTable.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[7];
                temp[0] = map.get("saveType").toString();
                temp[1] = map.get("W").toString();
                temp[2] = map.get("Z").toString();
                temp[3] = map.get("S").toString();
                temp[4] = map.get("P").toString();
                temp[5] = map.get("B").toString();
                temp[6] = map.get("Q").toString();
                tableData.add(temp);
            }
        }


        //饼图数据
        String[] pieRow = new String[]{"存储类型","总数"};
        if ("2".equals(type)){
            pieRow = new String[]{"申请原因","总数"};
        }
        JsonResult videoCjPie = postVideoService.getVideoCjPie(condition);
        List<Object[]> pieData = new ArrayList<Object[]>();
        if ("2".equals(type)){
            videoCjPie = postVideoService.getVideoCxPie(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjPie.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[2];
                temp[0] = map.get("name").toString();
                temp[1] = map.get("value").toString();
                pieData.add(temp);
            }
        }else{
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjPie.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[2];
                temp[0] = map.get("name").toString();
                temp[1] = map.get("value").toString();
                pieData.add(temp);
            }
        }

        //折线图数据
        String[] lineRow = new String[]{"操作日期","图片","视频","音频"};
        if ("2".equals(type)){
            lineRow = new String[]{"操作日期","申请","下载"};
        }
        JsonResult videoCjLine = postVideoService.getVideoCjLine(condition);
        List<Object[]> linedata = new ArrayList<Object[]>();
        if ("2".equals(type)){
            videoCjLine = postVideoService.getVideoCxLine(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjLine.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[3];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String updateTime = simpleDateFormat.format((Date) map.get("updateTime"));
                temp[0] = updateTime;
                temp[1] = map.get("申请").toString();
                temp[2] = map.get("下载").toString();
                linedata.add(temp);
            }
        }else {
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjLine.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[4];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String updateTime = simpleDateFormat.format((Date) map.get("updateTime"));
                temp[0] = updateTime;
                temp[1] = map.get("图片").toString();
                temp[2] = map.get("视频").toString();
                temp[3] = map.get("音频").toString();
                linedata.add(temp);
            }
        }
        String execelName1 = "采集类型交叉表统计";
        String execelName2 = "采集类型饼图统计";
        String execelName3 = "采集类型折线图统计";
        if ("2".equals(type)){
            execelName1 = "操作类型交叉表统计";
            execelName2 = "操作类型饼图统计";
            execelName3 = "操作类型折线图统计";
        }

        List<HashMap<String,Object>> excelInfo = new ArrayList<HashMap<String,Object>>();

        HashMap ext1 = new HashMap();
        ext1.put("title",execelName1);
        ext1.put("rowName",tableRow);
        ext1.put("dataList",tableData);
        excelInfo.add(ext1);

        HashMap ext2 = new HashMap();
        ext2.put("title",execelName2);
        ext2.put("rowName",pieRow);
        ext2.put("dataList",pieData);
        excelInfo.add(ext2);

        HashMap ext3 = new HashMap();
        ext3.put("title",execelName3);
        ext3.put("rowName",lineRow);
        ext3.put("dataList",linedata);
        excelInfo.add(ext3);

        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.exportMultiSheet(response,excelInfo);
//        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
    }
    @ControllerAop(action = "导出影视统计")
    @RequestMapping("/exportVideoCjReport2")
    public void exportVideoCjReport2(String status,String startTime,String endTime,String type,
                                     HttpServletRequest request, HttpServletResponse response)throws IOException{
        String zipName = "影视采集统计2.zip";
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        if ("2".equals(type)){
            //折线图数据
            String[] barRow = new String[]{"部门","申请","下载"};
            JsonResult videoCjBar = postVideoService.getVideoCxBar(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjBar.getData();
            List<Object[]> barData = new ArrayList<Object[]>();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[3];
                temp[0] = map.get("orgName").toString();
                temp[1] = map.get("申请").toString();
                temp[2] = map.get("下载").toString();
                barData.add(temp);
            }

//            ExportExcelUtil exportExcelUtil = new ExportExcelUtil("操作类型部门统计",barRow,barData);
//            HSSFWorkbook sheets = exportExcelUtil.exportMulti(response);

            //折线图数据
            String[] barRow1 = new String[]{"用户名","申请","下载"};
            JsonResult videoCjBar1 = postVideoService.getVideoCxBarTop10(condition);
            List<HashMap<String,Object>> list1 = (List<HashMap<String,Object>>)videoCjBar1.getData();
            List<Object[]> barData1 = new ArrayList<Object[]>();
            for (HashMap<String, Object> map : list1) {
                Object[] temp = new String[3];
                temp[0] = map.get("apply").toString();
                temp[1] = map.get("申请").toString();
                temp[2] = map.get("下载").toString();
                barData1.add(temp);
            }

//            ExportExcelUtil exportExcelUtil1 = new ExportExcelUtil("操作类型用户统计",barRow1,barData1);
//            HSSFWorkbook sheets1 = exportExcelUtil1.exportMulti(response);
//            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
//            try
//            {
//                request.setCharacterEncoding("UTF-8");//设定请求字符编码
//                response.setContentType("application/x-msdownload;charset=utf-8");
//                response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
//                ExportExcelUtil.doCompress(sheets,"操作类型部门统计.xls",out);
//                ExportExcelUtil.doCompress(sheets1,"操作类型用户统计.xls",out);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }finally {
//                if(out != null){
//                    try {out.close();} catch (IOException e) {}
//                }
//            }
            List<HashMap<String,Object>> excelInfo = new ArrayList<HashMap<String,Object>>();

            HashMap ext1 = new HashMap();
            ext1.put("title","操作类型部门统计");
            ext1.put("rowName",barRow);
            ext1.put("dataList",barData);
            excelInfo.add(ext1);

            HashMap ext2 = new HashMap();
            ext2.put("title","操作类型用户统计");
            ext2.put("rowName",barRow1);
            ext2.put("dataList",barData1);
            excelInfo.add(ext2);

            ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
            exportExcelUtil.exportMultiSheet(response,excelInfo);
        }else {
            //折线图数据
            String[] barRow = new String[]{"部门","图片","视频","音频"};
            JsonResult videoCjBar = postVideoService.getVideoCjBar(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjBar.getData();
            List<Object[]> barData = new ArrayList<Object[]>();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[4];
                temp[0] = map.get("orgName").toString();
                temp[1] = map.get("图片").toString();
                temp[2] = map.get("视频").toString();
                temp[3] = map.get("音频").toString();
                barData.add(temp);
            }

            ExportExcelUtil exportExcelUtil = new ExportExcelUtil("采集类型部门统计",barRow,barData);
           exportExcelUtil.export(response);
        }
    }
    @ControllerAop(action = "检验当前用户身份")
    @RequestMapping("/checkOrg")
    public JsonResult checkOrg(@RequestParam String userId){
        try {
            boolean result = postVideoService.checkUserOrg(userId);
            return new JsonResult(1,result);
        }catch (Exception e){
            return new JsonResult("");
        }

    }
    @ControllerAop(action = "检验当前流程状态")
    @RequestMapping("/getProcessUserStatus")
    public JsonResult getProcessUserStatus(@RequestParam String userId,@RequestParam String postVideoId,@RequestParam
            String type){
        JsonResult jsonResult = postVideoService.getProcessUserStatus(userId,postVideoId,type);
        return jsonResult;
    }

    /**
     * 根据关键词查询影视列表
     * @param keyId
     * @return
     */
    @ControllerAop(action = "根据关键词查询影视列表")
    @RequestMapping("/getVideoListByKeywords")
    public LayUiTableJson getVideoListByKeywords(@RequestParam String keyId){
        JsonResult jsonResult = postVideoService.getVideoListByKeywords(keyId);
        if (jsonResult.getSuccess()==1){
            if (null != jsonResult.getData()){
                List<PostVideo> postVideos = (List<PostVideo>)jsonResult.getData();
                return new LayUiTableJson(0,"查询成功",postVideos.size(),postVideos);
            }else {
                return new LayUiTableJson(0,"查询成功",0,null);
            }
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 查询展陈页面影视资料
     * @param postVideo
     * @return
     */
    @ControllerAop(action = "根据关键词查询影视列表")
    @RequestMapping("/getPostVideoForExhib")
    public JsonResult getPostVideoForExhib(PostVideo postVideo){
        try {
            return postVideoService.getPostVideoForExhib(postVideo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }


}


package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.video.PostVideo;
import com.tj720.utils.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 影视资料服务类
 * @Author: 程荣凯
 * @Date: 2018/10/22 11:41
 */
@Service
public interface PostVideoService {
    /**
     * 根据条件查询影视资料列表
     * @param keywords 关键词
     * @param videoMark 资料分类
     * @param status 资料状态
     * @param page 分页参数
     * @param uploadOrg 当前部门
     * @param approval 当前用户
     * @param userType 用户是否为影视部用户
     * @return
     */
    public JsonResult getPostVideoList(String keywords,String videoMark,String status,Page page,String uploadOrg,String approval,String userType);

    /**
     * 添加影视资料
     * @param postVideo 影视资料对象
     * @return
     */
    public JsonResult addPostVideo(PostVideo postVideo);

    /**
     * 修改影视资料
     * @param postVideo 影视资料对象
     * @return
     */
    public JsonResult updateVideo(PostVideo postVideo);

    /**
     * 删除影视资料
     * @param id 影视资料id
     * @return
     */
    public JsonResult deleteVideo(String id);

    /**
     * 修改影视资料状态
     * @param id 影视资料ID
     * @param authSetting 权限
     * @return
     */
    public JsonResult changeAuthSetting(String id,String authSetting);

    /**
     *
     * @param ids 影视资料id列表
     * @param authSetting 权限
     * @return
     */
    @Transactional
    public JsonResult batchChangeAuthSetting(List<String> ids, String authSetting);

    /**
     * 根据影视资料id撤回申请
     * @param id 影视资料id
     * @return
     */
    public JsonResult revokeProcess(String id,String currentUserId);

    /**
     * 根据影视资料id提交申请
     * @param id
     * @return
     */
    public JsonResult commitProcess(String id,String currentUserId);

    /**
     * 发布
     * @param id
     * @param currentUserId
     * @return
     */
    public JsonResult publishProcess(String id,String currentUserId);

    /**
     * 审批影视资料上传流程
     * @param id 影视资料id
     * @param authSetting 权限
     * @param type 操作类型
     * @param approval 审批人
     * @param remark 备注
     * @return
     */
    public JsonResult approvalProcess(String id,String authSetting
            ,String type,String approval,String remark,String currentUserId);
    /**
     * 审批影视资料上传流程
     * @param ids
     * @param authSetting 权限
     * @param type 操作类型
     * @param approval 审批人
     * @param remark 备注
     * @return
     */
    public JsonResult batchApprovalProcess(String[] ids,String authSetting
            ,String type,String approval,String remark,String currentUserId);

    /**
     * 根据id查询影视资料
     * @param id
     * @return
     */
    public JsonResult getPostVideo(String id);

    /**
     * 查询影视资料上传列表
     * @param keywords
     * @param source
     * @param status
     * @param page
     * @param currentUserId
     * @return
     */
    public JsonResult getUplodApprovalList(String keywords,String source,String status,Page page,String currentUserId);

    /**
     * 影视资料查询列表
     * @param keywords
     * @param videoMark
     * @param status
     * @param page
     * @return
     */
    public JsonResult getPostVideoQueryList(String keywords,String videoMark,String status,
                                            String currentUserId,String uploadOrg,Page page,String userType);

    /**
     * 保存影视资料查询申请
     * @param postVideoId 影视资料ID
     * @param apply 申请人
     * @param applyOrg 申请人部门
     * @param applyTime 申请时间
     * @param applyReason 申请原因
     * @param remarks 备注
     * @param approval 审批人
     * @return
     */
    public JsonResult saveQueryApply(String postVideoId, String apply, String applyOrg, Date applyTime ,
                                     String applyReason,String remarks,String approval);

    public JsonResult saveAndSubmitQueryApply(String postVideoId, String apply, String applyOrg, Date applyTime ,
                           String applyReason,String remarks,String approval);
    /**
     * 保存影视资料查询申请
     * @param ids 影视资料ID
     * @param apply 申请人
     * @param applyOrg 申请人部门
     * @param applyTime 申请时间
     * @param applyReason 申请原因
     * @param remarks 备注
     * @param approval 审批人
     * @return
     */
    public JsonResult batchSaveQueryApply(String[] ids, String apply, String applyOrg, Date applyTime ,
                        String applyReason,String remarks,String approval);

    /**
     *
     * @param ids
     * @param apply
     * @param applyOrg
     * @param applyTime
     * @param applyReason
     * @param remarks
     * @param approval
     * @return
     */
    public JsonResult batchSaveAndSubmitQueryApply(String[] ids, String apply, String applyOrg, Date applyTime ,
                                          String applyReason,String remarks,String approval);
    /**
     * 保存影视资料查询申请
     * @param processInstId 流程实例ID
     * @param apply 申请人
     * @param applyOrg 申请人部门
     * @param applyTime 申请时间
     * @param remarks 备注
     * @param approval 审批人
     * @return
     */
    public JsonResult submitQueryApply(String processInstId,Date applyTime
            ,String apply,String applyOrg,String remarks,String approval);

    /**
     * 撤回影视资料查询申请
     * @param processInstId 流程实例ID
     * @param apply 操作人
     * @param applyOrg 操作部门
     * @return
     */
    public JsonResult revokeQueryApply(String processInstId,String apply,String applyOrg);

    /**
     * 审批影视资料查询
     * @param processInstId 流程实例ID
     * @param actionType 操作类型
     * @param approval 审批人
     * @param apply 操作人
     * @param applyOrg 操作人部门
     * @param remarks
     * @return
     */
    public JsonResult approvalQueryApply(String processInstId,String actionType,String approval,
                          String apply,String applyOrg,String remarks,String currentUserId);

    /**
     * 查询申请管理列表
     * @param keywords 关键词
     * @param applyOrg 申请部门
     * @param applyStatus 申请状态
     * @param currentUserId 当前用户id
     * @param pageInfo 分页
     * @return
     */
    public JsonResult getQueryVideoList(String keywords,String applyOrg,String applyStatus,String currentUserId,
                      Page pageInfo);

    /**
     * 查询审批管理
     * @param keywords
     * @param applyOrg
     * @param applyStatus
     * @param currentUserId
     * @param pageInfo
     * @return
     */
    public JsonResult getQueryApprovalVideoList(String keywords,String applyOrg,String applyStatus,String currentUserId,
                                        Page pageInfo);

    /**
     * 查询关键词列表
     * @return
     */
    public JsonResult queryKeywordsList(Page page);

    /**
     * 查询影视采集统计表
     * @param condition
     * @return
     */
    public JsonResult getVideoCjTable(HashMap<String,Object> condition);
    /**
     * 查询影视采集统计饼图
     * @param condition
     * @return
     */
    public JsonResult getVideoCjPie(HashMap<String,Object> condition);

    /**
     * 查询影视采集统计柱状图
     * @param condition
     * @return
     */
    public JsonResult getVideoCjBar(HashMap<String,Object> condition);

    /**
     * 查询影视采集折线图
     * @param condition
     * @return
     */
    public JsonResult getVideoCjLine(HashMap<String,Object> condition);

    /**
     * 查询影视采集统计表
     * @param condition
     * @return
     */
    public JsonResult getVideoCxTable(HashMap<String,Object> condition);
    /**
     * 查询影视采集统计饼图
     * @param condition
     * @return
     */
    public JsonResult getVideoCxPie(HashMap<String,Object> condition);

    /**
     * 查询影视采集统计柱状图
     * @param condition
     * @return
     */
    public JsonResult getVideoCxBar(HashMap<String,Object> condition);

    public JsonResult getVideoCxBarTop10(HashMap<String,Object> condition);
    /**
     * 查询影视采集折线图
     * @param condition
     * @return
     */
    public JsonResult getVideoCxLine(HashMap<String,Object> condition);
    /**
     * 查询图表1
     * @param condition 查询条件
     * @param type 查询类型(1：采集:/2：查询)
     * @return
     */
    public JsonResult getVideoCjReport1(HashMap<String,Object> condition,String type);
    /**
     * 查询图表2
     * @param condition 查询条件
     * @param type 查询类型(1：采集:/2：查询)
     * @return
     */
    public JsonResult getVideoCjReport2(HashMap<String,Object> condition, String type);

    /**
     * 查询待办
     * @param currentUserId 用户id
     * @return
     */
    public JsonResult getUndoTask(String currentUserId);

    /**
     * 查询已办
     * @param currentUserId 用户id
     * @return
     */
    public JsonResult getDoneTask(String currentUserId);

    /**
     * 查询已办结
     * @param currentUserId 用户id
     * @return
     */
    public JsonResult getFinishTask(String currentUserId);

    /**
     * 查询影视采集统计
     * @return
     */
    public JsonResult getVideoCjCount();

    /**
     * 查询影视查询统计
     * @return
     */
    public JsonResult getVideoCxCount();

    /**
     * 查询快捷入口设置
     * @param currentUserId
     * @return
     */
    public JsonResult getShortcutEntrance(String currentUserId,String currentId,String type);

    /**
     * 设置快捷入口
     * @param currentUserId
     * @return
     */
    public JsonResult setShortcutEntrance(String currentUserId,List<String> shortcutEntrance,String type);

    /**
     * 检测用户是不是影视部
     * @param userId
     * @return
     */
    public boolean checkUserOrg(String userId);

    /**
     * 查询影视资料关联的附件
     * @param postVideoId
     * @return
     */
    public List<Attachment> getPostVideoAttachment(String postVideoId);

    public PostVideo checkVideoCode(PostVideo postVideo);

    /**
     * 查询流程状态
     * @param userId
     * @param postVideoId
     * @return
     */
    public JsonResult getProcessUserStatus(String userId,String postVideoId,String type);

    /**
     * 查询展陈页面影视资料
     * @param postVideo
     * @return
     */
    JsonResult getPostVideoForExhib(PostVideo postVideo);

    /**
     * 根据关键词查询影视资料列表
     * @param keyId 关键词
     * @return
     */
    public JsonResult getVideoListByKeywords(String keyId);

    /**
     * 综合查询查询公开影视资料列表
     * @param keywords 关键字
     * @param videoMark 分类
     * @param status 状态
     * @param pageInfo 分页信息
     * @return
     */
    JsonResult queryVedilListForCompreQuery(String keywords, String videoMark, String status, Page pageInfo, String orderBy);
}

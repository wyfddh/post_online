package com.tj720.service;


import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.wf.WfAction;
import com.tj720.model.common.wf.WfDetail;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 流程服务接口
 * @Author: 程荣凯
 * @Date: 2018/10/25 20:04
 */
@Service
public interface WfService {
    /**
     * 新增流程记录
     * @param wfAction
     * @return
     */
    public JsonResult addWfAction(WfAction wfAction);

    /**
     * 新增流程记录
     * @param partyId 业务表id
     * @param partyType 业务表类型
     * @param applyStatus 申请状态
     * @param apply 申请人
     * @param applyOrg 申请人所在部门
     * @param applyTime 申请时间
     * @param actionName 操作名称
     * @param actionType 操作类型
     * @param remarks 备注
     * @param approval 审批人
     * @return
     */
    public WfAction addWfAction(String partyId,String partyType,String applyStatus,String apply,String applyOrg,
                                  Date applyTime,String actionName,String actionType,String remarks,String approval);

    /**
     * 新增流程记录
     * @param partyId 业务表id
     * @param partyType 业务表类型
     * @param applyStatus 申请状态
     * @param apply 申请人
     * @param applyOrg 申请人所在部门
     * @param applyTime 申请时间
     * @param actionName 操作名称
     * @param actionType 操作类型
     * @return
     */
    public WfAction addWfAction(String partyId,String partyType,String applyStatus,String apply,String applyOrg,
                                Date applyTime,String actionName,String actionType,String approval);

    /**
     * 删除流程记录
     * @param actionId
     * @return
     */
    public JsonResult deleteWfAction(String actionId);

    /**
     * 根据关联表删除所有流程信息
     * @param partyId
     * @return
     */
//    public JsonResult deleteWfActionByPartyId(String partyId);

    /**
     * 修改流程记录
     * @param wfAction
     * @return
     */
    public JsonResult updateWfAction(WfAction wfAction);

    public JsonResult updateWfActionPlus(WfAction wfAction);

    /**
     * 修改流程记录状态
     * @param actionId
     * @param status
     * @return
     */
    public JsonResult updateWfStatus(String actionId,String status);

    /**
     * 添加流程操作详情
     * @param wfDetail
     * @return
     */
    public JsonResult addWfDetail(WfDetail wfDetail);

    public JsonResult addWfDetailPlus(WfDetail wfDetail);


    /**
     * 添加流程操作详情
     * @param processId 流程实例ID
     * @param actionType 操作类型（1:通过；2：通过并提交；3：驳回）
     * @param actionStatus 操作状态(1:待办；2：已办；3：已办结)
     * @param actionResult 操作结果（1：待办；2：已办；3：已办结）
     * @param actionName 操作名称
     * @param actionTime 操作时间
     * @param apply 审批人
     * @param ext1 提交人
     * @return
     */
    public JsonResult addWfDetail(String processId, String actionType, String actionStatus
            , String actionResult, String actionName, Date actionTime,String apply,String ext1,String applyOrg);


    public JsonResult addWfDetailPlus(String processId, String actionType, String actionStatus
            , String actionResult, String actionName, Date actionTime,String apply,String ext1,String applyOrg);
    /**
     * 修改流程操作详情
     * @param wfDetail
     * @return
     */
    public JsonResult updateWfDetail(WfDetail wfDetail);

    /**
     * 根据流程id删除流程记录
     * @param processId
     * @return
     */
    public JsonResult deleteWfDetailByProcessId(String processId);

    /**
     * 根据流程实例ID修改流程详情
     * @param processId 流程实例id
     * @param status 状态
     * @return
     */
    public JsonResult updateWfDetailByProcessId(String processId,String status);

    /**
     * 根据用户id和流程实例ID修改流程详情
     * @param userId 用户ID
     * @param processId 流程实例ID
     * @param status 状态
     * @param actionResult 操作结果
     * @return
     */
    public JsonResult updateWfDetailByUserIdAndPid(String userId,String processId,String status,String actionResult);

    /**
     * 根据用户id，流程实例id，流程状态查询流程详情
     * @param userId 用户id
     * @param processId 流程实例ID
     * @param status 流程状态
     * @return
     */
    public WfDetail getWfDetailByUserIdAndPid(String userId,String processId,String status);

    /**
     * 根据业务表id和业务表类型查询流程实例信息
     * @param partyId
     * @param partyType
     * @return
     */
    WfAction getWfActionByPartyId(String partyId,String partyType);

    /**
     * 根据业务表id和业务表类型查询流程实例信息
     * @param partyId
     * @param partyType
     * @param apply 申请人
     * @return
     */
    WfAction getWfActionByPartyId(String partyId,String partyType,String apply);

    WfAction getWfActionByPartyIdAndStatus(String partyId,String partyType,String apply,String status);
    /**
     * 查询用户流程操作信息
     * @param userId
     * @param status
     * @return
     */
    public JsonResult queryWfDetailList(String userId,String status);


    public JsonResult getWfDetailByProcessId(String processId);

    public WfAction getWfActionById(String id);

    public JsonResult getWfActionByNotice(String id);
}

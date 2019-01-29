package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.WfActionMapper;
import com.tj720.dao.WfDetailMapper;
import com.tj720.model.common.system.menu.SysFunction;
import com.tj720.model.common.wf.WfAction;
import com.tj720.model.common.wf.WfDetail;
import com.tj720.model.common.wf.WfDetailExample;
import com.tj720.service.WfService;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/25 20:12
 */
@Service
public class WfServiceImpl implements WfService{
    @Autowired
    WfActionMapper wfActionMapper;
    @Autowired
    WfDetailMapper wfDetailMapper;

    private WfAction setAction(WfAction wfAction, String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        wfAction.setUpdater(Tools.getUserId());
        wfAction.setUpdateTime(date);
        if (type == "0"){
            wfAction.setCreatTime(date);
            wfAction.setCreator(Tools.getUserId());
        }
        return wfAction;
    }
    private WfDetail setAction1(WfDetail wfDetail, String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        wfDetail.setUpdater(Tools.getUserId());
        wfDetail.setUpdateTime(date);
        if (type == "0"){
            wfDetail.setCreatTime(date);
            wfDetail.setCreator(Tools.getUserId());
        }
        return wfDetail;
    }
    /**
     * 新增流程记录
     *
     * @param wfAction
     * @return
     */
    @Override
    public JsonResult addWfAction(WfAction wfAction) {
        if (StringUtils.isEmpty(wfAction)){
            return new JsonResult("200510");
        }
        try {
            wfAction.setXid(IdUtils.getIncreaseIdByNanoTime());
            wfAction = setAction(wfAction,"0");
            int count = wfActionMapper.insertSelective(wfAction);
            wfAction = getWfActionById(wfAction.getXid());
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }

    }

    /**
     * 新增流程记录
     *
     * @param partyId     业务表id
     * @param partyType   业务表类型
     * @param applyStatus 申请状态
     * @param apply       申请人
     * @param applyOrg    申请人所在部门
     * @param applyTime   申请时间
     * @param actionName  操作名称
     * @param actionType  操作类型
     * @return
     */
    @Override
    public WfAction addWfAction(String partyId, String partyType, String applyStatus, String apply
            , String applyOrg, Date applyTime, String actionName, String actionType,String approval) {
        WfAction wfAction = new WfAction();
        wfAction.setApplyStatus(applyStatus);
        wfAction.setApply(apply);
        wfAction.setActionName(actionName);
        wfAction.setActionType(actionType);
        wfAction.setApplyOrg(applyOrg);
        wfAction.setApplyTime(applyTime);
        wfAction.setApproval(approval);
        wfAction.setPartyId(partyId);
        //1表示上传流程，2表示查询申请流程
        wfAction.setPartyType(partyType);
        JsonResult jsonResult = addWfAction(wfAction);
        if (null == jsonResult || jsonResult.getSuccess() == 0){
            return new WfAction();
        }else {
            return (WfAction) jsonResult.getData();
        }
    }

    /**
     * 新增流程记录
     *
     * @param partyId     业务表id
     * @param partyType   业务表类型
     * @param applyStatus 申请状态
     * @param apply       申请人
     * @param applyOrg    申请人所在部门
     * @param applyTime   申请时间
     * @param actionName  操作名称
     * @param actionType  操作类型
     * @param remarks 备注
     * @param approval 审批人
     * @return
     */
    @Override
    public WfAction addWfAction(String partyId, String partyType, String applyStatus, String apply
            , String applyOrg, Date applyTime, String actionName, String actionType,String remarks,String approval) {
        WfAction wfAction = new WfAction();
        wfAction.setApplyStatus(applyStatus);
        wfAction.setApply(apply);
        wfAction.setActionName(actionName);
        wfAction.setActionType(actionType);
        wfAction.setApplyOrg(applyOrg);
        wfAction.setApplyTime(applyTime);
        wfAction.setApproval(approval);
        wfAction.setPartyId(partyId);
        wfAction.setRemark(remarks);
        //1表示上传流程，2表示查询申请流程
        wfAction.setPartyType(partyType);
        JsonResult jsonResult = addWfAction(wfAction);
        if (null == jsonResult || jsonResult.getSuccess() == 0){
            return new WfAction();
        }else {
            return (WfAction) jsonResult.getData();
        }
    }

    /**
     * 删除流程记录
     *
     * @param actionId
     * @return
     */
    @Override
    public JsonResult deleteWfAction(String actionId) {
        if (StringUtils.isEmpty(actionId)){
            return new JsonResult("200510");
        }
        WfAction wfAction1 = getWfActionById(actionId);
        if (StringUtils.isEmpty(wfAction1)){
            return new JsonResult("200511");
        }
        try {

            int count = wfActionMapper.deleteByPrimaryKey(actionId);
            return new JsonResult(1,count);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200506");
        }
    }

    /**
     * 修改流程记录
     *
     * @param wfAction
     * @return
     */
    @Override
    public JsonResult updateWfAction(WfAction wfAction) {
        if (StringUtils.isEmpty(wfAction)){
            return new JsonResult("200510");
        }
        WfAction wfAction1 = getWfActionById(wfAction.getXid());
        if (StringUtils.isEmpty(wfAction1)){
            return new JsonResult("200511");
        }
        try {
            wfAction = setAction(wfAction,"1");
            int count = wfActionMapper.updateByPrimaryKeySelective(wfAction);
            wfAction = getWfActionById(wfAction.getXid());
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }
    }

    @Override
    public JsonResult updateWfActionPlus(WfAction wfAction) {
        if (StringUtils.isEmpty(wfAction)){
            return new JsonResult("200510");
        }
        WfAction wfAction1 = getWfActionById(wfAction.getXid());
        if (StringUtils.isEmpty(wfAction1)){
            return new JsonResult("200511");
        }
        try {
//            wfAction = setAction(wfAction,"1");
            int count = wfActionMapper.updateByPrimaryKeySelective(wfAction);
            wfAction = getWfActionById(wfAction.getXid());
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }
    }

    /**
     * 修改流程记录状态
     *
     * @param actionId
     * @param status
     * @return
     */
    @Override
    public JsonResult updateWfStatus(String actionId, String status) {
        WfAction wfAction = new WfAction();
        wfAction.setXid(actionId);
        wfAction.setApplyStatus(status);
        JsonResult jsonResult = updateWfAction(wfAction);
        return jsonResult;
    }

    /**
     * 添加流程操作详情
     *
     * @param wfDetail
     * @return
     */
    @Override
    public JsonResult addWfDetail(WfDetail wfDetail) {
        if (StringUtils.isEmpty(wfDetail)){
            return new JsonResult("200510");
        }
        try {
            wfDetail.setXid(IdUtils.getIncreaseIdByNanoTime());
            wfDetail = setAction1(wfDetail,"0");
            int count = wfDetailMapper.insertSelective(wfDetail);
            wfDetail = getWfDetailById(wfDetail.getXid());
            return new JsonResult(1,wfDetail);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }
    }

    /**
     * 添加流程操作详情
     *
     * @param wfDetail
     * @return
     */
    @Override
    public JsonResult addWfDetailPlus(WfDetail wfDetail) {
        if (StringUtils.isEmpty(wfDetail)){
            return new JsonResult("200510");
        }
        try {
            wfDetail.setXid(IdUtils.getIncreaseIdByNanoTime());
//            wfDetail = setAction1(wfDetail,"0");
            wfDetail.setUpdateTime(new Date());
            wfDetail.setUpdater("sysadmin");
            wfDetail.setCreator("sysadmin");
            wfDetail.setCreatTime(new Date());
            int count = wfDetailMapper.insertSelective(wfDetail);
            wfDetail = getWfDetailById(wfDetail.getXid());
            return new JsonResult(1,wfDetail);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }
    }
    /**
     * 添加流程操作详情
     *
     * @param processId    流程实例ID
     * @param actionType   操作类型（1:通过；2：通过并提交；3：驳回）
     * @param actionStatus 操作状态(1:待办；2：已办；3：已办结)
     * @param actionResult 操作结果（1：待办；2：已办；3：已办结）
     * @param actionName   操作名称
     * @param actionTime   操作时间
     * @param apply        审批人
     * @param ext1         提交人
     * @return
     */
    @Override
    public JsonResult addWfDetail(String processId, String actionType, String actionStatus, String actionResult
            , String actionName, Date actionTime, String apply, String ext1,String applyOrg) {
        WfDetail wfDetail = new WfDetail();
        wfDetail.setActionName(actionName);
        wfDetail.setActionResult(actionResult);
        wfDetail.setActionTime(actionTime);
        wfDetail.setActionType(actionType);
        wfDetail.setApply(apply);
        wfDetail.setExt1(ext1);
        wfDetail.setStatus(actionStatus);
        wfDetail.setProcessId(processId);
        wfDetail.setApplyOrg(applyOrg);
        JsonResult jsonResult = addWfDetail(wfDetail);
        return jsonResult;
    }

    /**
     * 添加流程操作详情
     *
     * @param processId    流程实例ID
     * @param actionType   操作类型（1:通过；2：通过并提交；3：驳回）
     * @param actionStatus 操作状态(1:待办；2：已办；3：已办结)
     * @param actionResult 操作结果（1：待办；2：已办；3：已办结）
     * @param actionName   操作名称
     * @param actionTime   操作时间
     * @param apply        审批人
     * @param ext1         提交人
     * @return
     */
    @Override
    public JsonResult addWfDetailPlus(String processId, String actionType, String actionStatus, String actionResult
            , String actionName, Date actionTime, String apply, String ext1,String applyOrg) {
        WfDetail wfDetail = new WfDetail();
        wfDetail.setActionName(actionName);
        wfDetail.setActionResult(actionResult);
        wfDetail.setActionTime(actionTime);
        wfDetail.setActionType(actionType);
        wfDetail.setApply(apply);
        wfDetail.setExt1(ext1);
        wfDetail.setStatus(actionStatus);
        wfDetail.setProcessId(processId);
        wfDetail.setApplyOrg(applyOrg);
        wfDetail.setCreatTime(new Date());
        JsonResult jsonResult = addWfDetailPlus(wfDetail);
        return jsonResult;
    }

    /**
     * 修改流程操作详情
     *
     * @param wfDetail
     * @return
     */
    @Override
    public JsonResult updateWfDetail(WfDetail wfDetail) {
        if (StringUtils.isEmpty(wfDetail)){
            return new JsonResult("200510");
        }
        WfDetail wfDetail1 = getWfDetailById(wfDetail.getXid());
        if (StringUtils.isEmpty(wfDetail1)){
            return new JsonResult("200511");
        }
        try {
            wfDetail = setAction1(wfDetail,"1");
            int count = wfDetailMapper.updateByPrimaryKeySelective(wfDetail);
            wfDetail = getWfDetailById(wfDetail.getXid());
            return new JsonResult(1,wfDetail);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }
    }

    /**
     * 根据关联表删除所有流程信息
     *
     * @param processId 流程id
     * @return
     */
    @Override
    public JsonResult deleteWfDetailByProcessId(String processId) {
        try{
           int count = wfDetailMapper.deleteWfDetailByProcessId(processId);
           return new JsonResult(1,count);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }

    }



    /**
     * 根据流程实例ID修改流程详情
     *
     * @param processId 流程实例id
     * @param status    状态
     * @return
     */
    @Override
    public JsonResult updateWfDetailByProcessId(String processId, String status) {

        try {
            int count = wfDetailMapper.updateWfDetailByProcessId(processId,status);
            return new JsonResult(1,count);
        }catch (Exception e){
            return new JsonResult("200504");
        }


    }

    /**
     * 根据用户id和流程实例ID修改流程详情
     *
     * @param userId       用户ID
     * @param processId    流程实例ID
     * @param status       状态
     * @param actionResult 操作结果
     * @return
     */
    @Override
    public JsonResult updateWfDetailByUserIdAndPid(String userId, String processId, String status, String actionResult) {
        try {
            int count = wfDetailMapper.updateWfDetailByUserIdAndPid(userId, processId, status, actionResult);
            return new JsonResult(1,count);
        }catch (Exception e){
            return new JsonResult("200504");
        }

    }

    /**
     * 根据用户id，流程实例id，流程状态查询流程详情
     *
     * @param userId    用户id
     * @param processId 流程实例ID
     * @param status    流程状态
     * @return
     */
    @Override
    public WfDetail getWfDetailByUserIdAndPid(String userId, String processId, String status) {
        WfDetail wfDetail = wfDetailMapper.getWfDetailByUserIdAndPid(userId,processId,status);
        return wfDetail;
    }

    /**
     * 根据业务表id和业务表类型查询流程实例信息
     *
     * @param partyId
     * @param partyType
     * @return
     */
    @Override
    public WfAction getWfActionByPartyId(String partyId, String partyType) {
        WfAction wfAction = wfActionMapper.getWfActionByPartyId(partyId,partyType,null);
        return wfAction;
    }

    /**
     * 根据业务表id和业务表类型查询流程实例信息
     *
     * @param partyId
     * @param partyType
     * @param apply     申请人
     * @return
     */
    @Override
    public WfAction getWfActionByPartyId(String partyId, String partyType, String apply) {
        WfAction wfAction = wfActionMapper.getWfActionByPartyId(partyId,partyType,apply);
        return wfAction;
    }

    @Override
    public WfAction getWfActionByPartyIdAndStatus(String partyId, String partyType, String apply, String status) {
        WfAction wfAction = wfActionMapper.getWfActionByPartyIdAndStatus(partyId,partyType,apply,status);
        return wfAction;
    }

    /**
     * 查询用户流程操作信息
     *
     * @param userId
     * @param status
     * @return
     */
    @Override
    public JsonResult queryWfDetailList(String userId, String status) {
        try {
//            WfDetailExample example = new WfDetailExample();
//            WfDetailExample.Criteria criteria = example.createCriteria();
//            criteria.andApplyEqualTo(userId);
//            if (null != status){
//                criteria.andStatusEqualTo(status);
//            }
//            List<WfDetail> wfDetails = wfDetailMapper.selectByExample(example);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("apply",userId);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(status)) {
                map.put("status",status);
            }
            List<WfDetail> wfDetails = wfDetailMapper.getByMap(map);
            return new JsonResult(1,wfDetails);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }

    }

    @Override
    public JsonResult getWfDetailByProcessId(String processId) {
        try {
            List<WfDetail> wfDetailList = wfDetailMapper.getWfDetailByPid(processId);
            return new JsonResult(1,wfDetailList);
        }catch (Exception e){
            return new JsonResult("200507");
        }

    }

    @Override
    public WfAction getWfActionById(String actionId){
        WfAction wfAction = wfActionMapper.selectByPrimaryKey(actionId);
        return wfAction;
    }

    private WfDetail getWfDetailById(String actionId){
        WfDetail wfDetail = wfDetailMapper.selectByPrimaryKey(actionId);
        return wfDetail;
    }

    @Override
    public JsonResult getWfActionByNotice(String id) {
        try {
            WfAction wfAction = wfActionMapper.getWfActionByNotice(id);
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            return new JsonResult("200507");
        }
    }
}

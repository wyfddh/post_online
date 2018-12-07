package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostLiteratureProcessDetailMapper;
import com.tj720.dao.PostLiteratureProcessMapper;
import com.tj720.dao.SysUserDeptMapper;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.department.SysUserDept;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.literature.PostLiterature;
import com.tj720.model.literature.PostLiteratureProcess;
import com.tj720.model.literature.PostLiteratureProcessDetail;
import com.tj720.model.literature.PostLiteratureWithBLOBs;
import com.tj720.service.PostLiteratureProcessService;
import com.tj720.service.PostLiteratureService;
import com.tj720.service.SysDictSevice;
import com.tj720.service.SysNoticeService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wyf
 * @date 2018/10/25 16:39
 **/
@Service("postLiteratureProcessService")
public class PostLiteratureProcessServiceImpl implements PostLiteratureProcessService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;
    @Autowired
    private PostLiteratureProcessMapper postLiteratureProcessMapper;
    @Autowired
    private SysDictSevice sysDictSevice;
    @Autowired
    private PostLiteratureService postLiteratureService;
    @Autowired
    private PostLiteratureProcessDetailMapper processDetailMapper;
    @Autowired
    private SysNoticeService sysNoticeService;


    @Override
    public JsonResult getApproveList() {
        JsonResult jsonResult = null;
        try {
            List<SysUser> list = sysUserMapper.getUserByRoleCode("literatureManager");
            jsonResult = new JsonResult(1,list);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }

    @Override
    @Transactional
    public JsonResult postLiteratureProcessSave(PostLiteratureProcess postLiteratureProcess) {
        JsonResult jsonResult = null;

        String id = IdUtils.getIncreaseIdByNanoTime();
        postLiteratureProcess.setId(id);
        postLiteratureProcess.setCreateTime(new Date());
        postLiteratureProcess.setCreator(postLiteratureProcess.getApplicant());
        postLiteratureProcess.setUpdater(postLiteratureProcess.getApplicant());
        postLiteratureProcess.setApproveStatus("1");
        postLiteratureProcess.setApplyStatus("0");
        postLiteratureProcess.setUpdateTime(new Date());
        try {


            JsonResult postLiteratureJson = postLiteratureService.selectByPrimaryKey(postLiteratureProcess.getLiteratureId());
            if (postLiteratureJson.getSuccess()==0 || postLiteratureJson.getData() == null){
                return new JsonResult("111116");
            }
            PostLiteratureWithBLOBs postLiterature = (PostLiteratureWithBLOBs) postLiteratureJson.getData();

            postLiteratureProcessMapper.insertSelective(postLiteratureProcess);

            //新增一条申请人的流程申请记录
            PostLiteratureProcessDetail processDetail1 = new PostLiteratureProcessDetail();
            String id1 = IdUtils.getIncreaseIdByNanoTime();
            processDetail1.setId(id1);
            processDetail1.setProcessId(id);
            processDetail1.setProcessStatus("2");
            processDetail1.setProcessOperation("-1");
            processDetail1.setProcessUserId(postLiteratureProcess.getApplicant());
            processDetail1.setProcessUserName(postLiteratureProcess.getApplicantName());
            processDetail1.setProcessOrgName(postLiteratureProcess.getDepartmentName());
            processDetail1.setProcessRemark(postLiteratureProcess.getApplyRemark());
            processDetail1.setCreateTime(new Date());
            processDetailMapper.insertSelective(processDetail1);
            //新增一条审批人的流程审批记录
            PostLiteratureProcessDetail processDetail2 = new PostLiteratureProcessDetail();
            String id2 = IdUtils.getIncreaseIdByNanoTime();
            processDetail2.setId(id2);
            processDetail2.setProcessId(id);
            processDetail2.setProcessStatus("1");
            processDetail2.setProcessOperation("0");
            processDetail2.setProcessUserId(postLiteratureProcess.getApproveId());
            processDetail2.setProcessUserName(postLiteratureProcess.getApproveName());
            //添加消息通知

            String content = getNowDate()+";您有一条"+postLiterature.getDataName()+"申请需要审批";
            sysNoticeService.sendNoticePlus(postLiteratureProcess.getApproveId(),"2","确定",postLiteratureProcess.getApplicant(),
                    content,postLiteratureProcess.getId(),postLiteratureProcess.getApplicant());
            //查审批人部门
            SysDepartment dept = processDetailMapper.getDaptByUserId(postLiteratureProcess.getApproveId());
            processDetail2.setProcessOrgName(dept.getDepartmentName());
            processDetail2.setProcessRemark("");
            processDetail2.setCreateTime(new Date());
            processDetailMapper.insertSelective(processDetail2);
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
            throw new RuntimeException();
        }

        return jsonResult;
    }

    @Override
    public JsonResult savePaperBorrowing(PostLiteratureProcess postLiteratureProcess) {
        JsonResult jsonResult = null;
        String id = IdUtils.getIncreaseIdByNanoTime();
        postLiteratureProcess.setId(id);
        postLiteratureProcess.setCreateTime(new Date());
        postLiteratureProcess.setCreator(postLiteratureProcess.getApplicant());
        postLiteratureProcess.setUpdater(postLiteratureProcess.getApplicant());
        postLiteratureProcess.setApproveStatus("2");
        postLiteratureProcess.setApplyStatus("1");
        postLiteratureProcess.setUpdateTime(new Date());
        postLiteratureProcess.setInformationSources("2");

        try {
            //根据用户查询部门
            SysUserDept deptById = sysUserDeptMapper.getDeptById(postLiteratureProcess.getApplicant());
            postLiteratureProcess.setDepartment(deptById.getDeptId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            postLiteratureProcess.setApplyDate(simpleDateFormat.format(new Date()));

            postLiteratureProcessMapper.insertSelective(postLiteratureProcess);
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }



    @Override
    public JSONObject postLiteratureProcessList(String key, String department, String approveStatus, String orderBy,
                                                Integer currentPage, Integer size) {
        //分页对象
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);

        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(key)) {
            map.put("key", key);
        }
        if (StringUtils.isNotBlank(department)) {
            map.put("department", department);
        }
        if (StringUtils.isNotBlank(approveStatus)) {
            map.put("approveStatus", approveStatus);
        }
        if (StringUtils.isNotBlank(orderBy)) {
            map.put("orderBy", orderBy);
        } else {
            //默认1
            map.put("orderBy", 1);
        }
        String userId  = Tools.getUserId();
        map.put("userId",userId);
        //符合检索条件的数量
        Integer count = postLiteratureProcessMapper.countPostLiteratureProcessList(map);

        page.setAllRow(count);
        Integer start = page.getStart();
        map.put("start", start);
        map.put("end", size);

        List<PostLiteratureProcess> list = postLiteratureProcessMapper.getPostLiteratureProcessList(map);
        String jsonString = null;
        if (list != null && list.size() > 0) {
            List<PostLiteratureProcess> postLiteratureProcesses = handleData(list);
            jsonString = JSON.toJSONString(postLiteratureProcesses);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", page.getAllRow());
        jsonObject.put("data", jsonString);

        return jsonObject;
    }

    @Override
    public JsonResult getLiteratureName() {
        JsonResult jsonResult = null;

        List<PostLiterature> list = postLiteratureService.getLiteratureName();
        jsonResult = new JsonResult(1,list);
        return jsonResult;
    }

    @Override
    @Transactional
    public JsonResult approveSave(PostLiteratureProcess postLiteratureProcess) {
        JsonResult jsonResult = null;

        if (StringUtils.isNotBlank(postLiteratureProcess.getId())) {
            postLiteratureProcess.setUpdateTime(new Date());
            postLiteratureProcess.setUpdater(postLiteratureProcess.getApproveId());
            try {
                String userId = Tools.getUserId();
                //通过并提交审批
                if ("3".equals(postLiteratureProcess.getApproveOperation())) {


                    //修改前一个审批人审批状态为已办
                    PostLiteratureProcessDetail processDetail1 = processDetailMapper.getProcessDetail(postLiteratureProcess.getId(),postLiteratureProcess.getPreApproveId());
                    processDetail1.setProcessStatus("2");
                    processDetail1.setProcessOperation("3");
                    processDetail1.setCreateTime(new Date());
                    processDetail1.setProcessRemark(postLiteratureProcess.getApproveRemark());
                    processDetailMapper.updateByPrimaryKeySelective(processDetail1);
                    //新增一条审批记录审批状态为待办
                    PostLiteratureProcessDetail processDetail2 = new PostLiteratureProcessDetail();

                    String id2 = IdUtils.getIncreaseIdByNanoTime();
                    processDetail2.setId(id2);
                    processDetail2.setProcessId(postLiteratureProcess.getId());
                    processDetail2.setProcessStatus("1");
                    processDetail2.setProcessOperation("0");
                    processDetail2.setProcessUserId(postLiteratureProcess.getApproveId());
                    processDetail2.setProcessUserName(postLiteratureProcess.getApproveName());

                    //查审批人部门
                    SysDepartment dept = processDetailMapper.getDaptByUserId(postLiteratureProcess.getApproveId());
                    processDetail2.setProcessOrgName(dept.getDepartmentName());
                    processDetail2.setProcessRemark("");
                    processDetail2.setCreateTime(new Date());
                    processDetailMapper.insertSelective(processDetail2);

                    //添加消息通知
                    JsonResult literatureById = postLiteratureService.getLiteratureById(postLiteratureProcess.getLiteratureId());
                    if (literatureById.getSuccess()==0 || literatureById.getData() == null){
                        return new JsonResult("111116");
                    }
                    PostLiterature postLiterature = (PostLiterature)literatureById.getData();
                    String content = getNowDate()+";您有一条"+postLiterature.getDataName()+"申请需要审批";
                    sysNoticeService.sendNoticePlus(postLiteratureProcess.getApproveId(),"2","确定",postLiteratureProcess.getApplicant(),
                            content,postLiteratureProcess.getId(),userId);

                    //驳回
                } else if ("2".equals(postLiteratureProcess.getApproveOperation())) {
                    postLiteratureProcess.setApproveStatus("3");
                    postLiteratureProcess.setApplyStatus("-1");

                    PostLiteratureProcessDetail processDetail1 = processDetailMapper.getProcessDetail(postLiteratureProcess.getId(),postLiteratureProcess.getPreApproveId());
                    processDetail1.setProcessStatus("3");
                    processDetail1.setProcessOperation("2");
                    processDetail1.setCreateTime(new Date());
                    processDetail1.setProcessRemark(postLiteratureProcess.getApproveRemark());
                    processDetailMapper.updateByPrimaryKeySelective(processDetail1);
                    //该流程已完成，更新所有该流程成员状态为已完成
                    processDetailMapper.batchUpdateProcessStatus(postLiteratureProcess.getId(),"3");

                    //添加消息通知
                    JsonResult literatureById = postLiteratureService.getLiteratureById(postLiteratureProcess.getLiteratureId());
                    if (literatureById.getSuccess()==0 || literatureById.getData() == null){
                        return new JsonResult("111116");
                    }
                    PostLiterature postLiterature = (PostLiterature)literatureById.getData();
                    String content = getNowDate()+";您的"+postLiterature.getDataName()+"申请已被驳回";
                    sysNoticeService.sendNoticePlus(postLiteratureProcess.getApplicant(),"2","确定",postLiteratureProcess.getApproveId(),
                            content,postLiteratureProcess.getId(),userId);
                    //通过
                } else if ("1".equals(postLiteratureProcess.getApproveOperation())) {
                    postLiteratureProcess.setApproveStatus("2");
                    postLiteratureProcess.setApplyStatus("1");

                    PostLiteratureProcessDetail processDetail1 = processDetailMapper.getProcessDetail(postLiteratureProcess.getId(),postLiteratureProcess.getPreApproveId());
                    processDetail1.setProcessStatus("3");
                    processDetail1.setProcessOperation("1");
                    processDetail1.setCreateTime(new Date());
                    processDetail1.setProcessRemark(postLiteratureProcess.getApproveRemark());
                    processDetailMapper.updateByPrimaryKeySelective(processDetail1);
                    //该流程已完成，更新所有该流程成员状态为已完成
                    processDetailMapper.batchUpdateProcessStatus(postLiteratureProcess.getId(),"3");
                    //添加消息通知
                    JsonResult literatureById = postLiteratureService.getLiteratureById(postLiteratureProcess.getLiteratureId());
                    if (literatureById.getSuccess()==0 || literatureById.getData() == null){
                        return new JsonResult("111116");
                    }
                    PostLiterature postLiterature = (PostLiterature)literatureById.getData();
                    String content = getNowDate()+";您的"+postLiterature.getDataName()+"申请已通过审批";
                    sysNoticeService.sendNoticePlus(postLiteratureProcess.getApplicant(),"2","确定",postLiteratureProcess.getApproveId(),
                            content,postLiteratureProcess.getId(),userId);
                }
                postLiteratureProcessMapper.updateByPrimaryKeySelective(postLiteratureProcess);
                jsonResult = new JsonResult(1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResult = new JsonResult("111116");
                throw new RuntimeException();
            }

        } else {
            jsonResult = new JsonResult("111116");
        }


        return jsonResult;
    }

    @Override
    public JsonResult batchApprove(String[] arr, String setting, String approveName, String preApproveId,
            String approveId, String approveRemark) {
        JsonResult jsonResult = null;

        try {
            String userId = Tools.getUserId();
            List<String> keys = Arrays.asList(arr);
            //查询所选文献流程
            List<PostLiteratureProcess> processList = postLiteratureProcessMapper.getProcessListByIdList(keys);
            for (PostLiteratureProcess process : processList) {
                process.setUpdateTime(new Date());
                process.setUpdater(approveId);

                //通过并提交审批
                if ("3".equals(setting)) {

                    //修改前一个审批人审批状态为已办
                    PostLiteratureProcessDetail processDetail1 = processDetailMapper.getProcessDetail(process.getId(),preApproveId);
                    processDetail1.setProcessStatus("2");
                    processDetail1.setProcessOperation("3");
                    processDetail1.setCreateTime(new Date());
                    processDetail1.setProcessRemark(approveRemark);
                    processDetailMapper.updateByPrimaryKeySelective(processDetail1);
                    //新增一条审批记录审批状态为待办
                    PostLiteratureProcessDetail processDetail2 = new PostLiteratureProcessDetail();

                    String id2 = IdUtils.getIncreaseIdByNanoTime();
                    processDetail2.setId(id2);
                    processDetail2.setProcessId(process.getId());
                    processDetail2.setProcessStatus("1");
                    processDetail2.setProcessOperation("0");
                    processDetail2.setProcessUserId(approveId);
                    processDetail2.setProcessUserName(approveName);

                    //查审批人部门
                    SysDepartment dept = processDetailMapper.getDaptByUserId(approveId);
                    processDetail2.setProcessOrgName(dept.getDepartmentName());
                    processDetail2.setProcessRemark("");
                    processDetail2.setCreateTime(new Date());
                    processDetailMapper.insertSelective(processDetail2);

                    //添加消息通知
                    JsonResult literatureById = postLiteratureService.getLiteratureById(process.getLiteratureId());
                    if (literatureById.getSuccess()==0 || literatureById.getData() == null){
                        return new JsonResult("111116");
                    }
                    PostLiterature postLiterature = (PostLiterature)literatureById.getData();
                    String content = getNowDate()+";您有一条"+postLiterature.getDataName()+"申请需要审批";
                    sysNoticeService.sendNoticePlus(approveId,"2","确定",process.getApplicant(),
                            content,process.getId(),userId);

                    //驳回
                } else if ("2".equals(setting)) {
                    process.setApproveStatus("3");
                    process.setApplyStatus("-1");

                    PostLiteratureProcessDetail processDetail1 = processDetailMapper.getProcessDetail(process.getId(),preApproveId);
                    processDetail1.setProcessStatus("3");
                    processDetail1.setProcessOperation("2");
                    processDetail1.setCreateTime(new Date());
                    processDetail1.setProcessRemark(approveRemark);
                    processDetailMapper.updateByPrimaryKeySelective(processDetail1);
                    //该流程已完成，更新所有该流程成员状态为已完成
                    processDetailMapper.batchUpdateProcessStatus(process.getId(),"3");

                    //添加消息通知
                    JsonResult literatureById = postLiteratureService.getLiteratureById(process.getLiteratureId());
                    if (literatureById.getSuccess()==0 || literatureById.getData() == null){
                        return new JsonResult("111116");
                    }
                    PostLiterature postLiterature = (PostLiterature)literatureById.getData();
                    String content = getNowDate()+";您的"+postLiterature.getDataName()+"申请已被驳回";
                    sysNoticeService.sendNoticePlus(process.getApplicant(),"2","确定",userId,
                            content,process.getId(),userId);
                    //通过
                } else if ("1".equals(setting)) {
                    process.setApproveStatus("2");
                    process.setApplyStatus("1");

                    PostLiteratureProcessDetail processDetail1 = processDetailMapper.getProcessDetail(process.getId(),preApproveId);
                    processDetail1.setProcessStatus("3");
                    processDetail1.setProcessOperation("1");
                    processDetail1.setCreateTime(new Date());
                    processDetail1.setProcessRemark(approveRemark);
                    processDetailMapper.updateByPrimaryKeySelective(processDetail1);
                    //该流程已完成，更新所有该流程成员状态为已完成
                    processDetailMapper.batchUpdateProcessStatus(process.getId(),"3");
                    //添加消息通知
                    JsonResult literatureById = postLiteratureService.getLiteratureById(process.getLiteratureId());
                    if (literatureById.getSuccess()==0 || literatureById.getData() == null){
                        return new JsonResult("111116");
                    }
                    PostLiterature postLiterature = (PostLiterature)literatureById.getData();
                    String content = getNowDate()+";您的"+postLiterature.getDataName()+"申请已通过审批";
                    sysNoticeService.sendNoticePlus(process.getApplicant(),"2","确定",userId,
                            content,process.getId(),userId);
                }
                postLiteratureProcessMapper.updateByPrimaryKeySelective(process);

            }



            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }


        return jsonResult;
    }

    @Override
    public JSONObject borrowingList(String key, String department, String borrowStatus, String orderBy,
                                    Integer currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);

        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(key)) {
            map.put("key", key);
        }
        if (StringUtils.isNotBlank(department)) {
            map.put("department", department);
        }
        if (StringUtils.isNotBlank(borrowStatus)) {
            map.put("borrowStatus", borrowStatus);
        }
        if (StringUtils.isNotBlank(orderBy)) {
            map.put("orderBy", orderBy);
        } else {
            //默认1
            map.put("orderBy", 1);
        }
        Integer count = postLiteratureProcessMapper.countBorrowingList(map);
        page.setAllRow(count);
        Integer start = page.getStart();
        map.put("start", start);
        map.put("end", size);

        List<PostLiteratureProcess> list = postLiteratureProcessMapper.getBorrowingList(map);

        String jsonString = null;
        if (list != null && list.size() > 0) {
            List<PostLiteratureProcess> postLiteratureProcesses = handleBorrowingData(list);
            jsonString = JSON.toJSONString(postLiteratureProcesses);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", page.getAllRow());
        jsonObject.put("data", jsonString);
        return jsonObject;
    }

    @Override
    public JsonResult modifyState(String id, String status) {
        JsonResult jsonResult = null;
        String userId  = Tools.getUserId();
        PostLiteratureProcess postLiteratureProcess = postLiteratureProcessMapper.selectByPrimaryKey(id);

        if (postLiteratureProcess != null) {
            postLiteratureProcess.setUpdateTime(new Date());
            postLiteratureProcess.setUpdater(userId);
            String literatureId = postLiteratureProcess.getLiteratureId();
            //获取文献
            JsonResult literatureById = postLiteratureService.selectByPrimaryKey(literatureId);
            PostLiteratureWithBLOBs literature = (PostLiteratureWithBLOBs) literatureById.getData();
            String inventoryState = literature.getInventoryState();
            int inventory = Integer.parseInt(inventoryState);
            //2借出3归还4超期
            if ("2".equals(status)) {
                //如果该记录显示已借出，则不再继续
                if ("2".equals(postLiteratureProcess.getApplyStatus())) {
                    return new JsonResult("111119");
                }
                //库存-1
                if (inventory > 0) {
                    int newInventory = inventory - 1;
                    literature.setInventoryState(newInventory + "");
                } else {
                    return new JsonResult("111118");
                }
                postLiteratureProcess.setBorrowingDate(new Date());
            } else if ("3".equals(status)) {
                //如果该记录显示已归还，则不再继续
                if ("3".equals(postLiteratureProcess.getApplyStatus())) {
                    return new JsonResult("111120");
                }
                //库存+1
                int newInventory = inventory + 1;
                literature.setInventoryState(newInventory + "");
                postLiteratureProcess.setRealReturnDate(new Date());
            }
            postLiteratureProcess.setApplyStatus(status);
            postLiteratureProcessMapper.updateByPrimaryKeySelective(postLiteratureProcess);
            postLiteratureService.update(literature);
            jsonResult = new JsonResult(1);
        } else {
            jsonResult = new JsonResult("111116");
        }

        return jsonResult;
    }

    private List<PostLiteratureProcess> handleData(List<PostLiteratureProcess> list) {
        //获取文献类型字典数据
        List<SysDict> literatureTypeList = sysDictSevice.getDictListByKey("literature_type");
        //获取申请状态字典数据
        List<SysDict> approveStatusList = sysDictSevice.getDictListByKey("approve_status");
        for (SysDict sysDict : approveStatusList) {

        }
        for (PostLiteratureProcess postLiteratureProcess : list) {
            for (SysDict sysDict : literatureTypeList) {
                if (StringUtils.isNotBlank(postLiteratureProcess.getLiteratureType()) && sysDict.getDictCode().equals(postLiteratureProcess.getLiteratureType())) {
                    postLiteratureProcess.setLiteratureTypeName(sysDict.getDictName());
                    break;
                }
            }
            for (SysDict sysDict : approveStatusList) {
                if (StringUtils.isNotBlank(postLiteratureProcess.getApproveStatus()) && sysDict.getDictCode().equals(postLiteratureProcess.getApproveStatus())) {
                    postLiteratureProcess.setApproveStatusName(sysDict.getDictName());
                    break;
                }
            }
            //1借阅2下载
            if ("1".equals(postLiteratureProcess.getApplyType())) {
                postLiteratureProcess.setApplyTypeName("借阅");
            } else if ("2".equals(postLiteratureProcess.getApplyType())) {
                postLiteratureProcess.setApplyTypeName("下载");
            }
        }
        return list;
    }

    private List<PostLiteratureProcess> handleBorrowingData(List<PostLiteratureProcess> list) {
        //获取借阅状态字典数据
        List<SysDict> borrowStatusList = sysDictSevice.getDictListByKey("borrow_status");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (PostLiteratureProcess postLiteratureProcess : list) {
            for (SysDict sysDict : borrowStatusList) {
                if (StringUtils.isNotBlank(postLiteratureProcess.getApplyStatus()) && sysDict.getDictCode().equals(postLiteratureProcess.getApplyStatus())) {
                    postLiteratureProcess.setApplyStatusName(sysDict.getDictName());
                    break;
                }
            }
            if ("1".equals(postLiteratureProcess.getInformationSources())) {
                postLiteratureProcess.setInformationSourcesName("PC端");
            } else if ("2".equals(postLiteratureProcess.getInformationSources())) {
                postLiteratureProcess.setInformationSourcesName("纸质申请");
            }
            if (postLiteratureProcess.getBorrowingDate() != null) {
                postLiteratureProcess.setBorrowingDateStr(format.format(postLiteratureProcess.getBorrowingDate()));
            } else {
                postLiteratureProcess.setBorrowingDateStr("");
            }
            if (postLiteratureProcess.getRealReturnDate() != null) {
                postLiteratureProcess.setRealReturnDateStr(format.format(postLiteratureProcess.getRealReturnDate()));
            } else {
                postLiteratureProcess.setRealReturnDateStr("");
            }

        }

        return list;
    }

    /**
     * 超期扫描任务
     *
     * @return
     */
    @Override
    public JsonResult overdueTask() {
        try {
            List<PostLiteratureProcess> postLiteratureProcesses = postLiteratureProcessMapper.getProcessDetailOverdueList();
            for (PostLiteratureProcess postLiteratureProcess : postLiteratureProcesses) {
                //添加消息通知
                JsonResult literatureById = postLiteratureService.getLiteratureById(postLiteratureProcess.getLiteratureId());
                if (literatureById.getSuccess()==0 || literatureById.getData() == null){
                    return new JsonResult("111116");
                }
                PostLiterature postLiterature = (PostLiterature)literatureById.getData();
                String content = getNowDate()+";您的"+postLiterature.getDataName()+"借阅已超期，请及时归还";
                sysNoticeService.sendNoticePlus(postLiteratureProcess.getApplicant(),"2","确定",postLiteratureProcess.getApplicant(),
                        content,postLiteratureProcess.getId(),postLiteratureProcess.getApplicant());
            }
            System.out.println("超期任务执行成功");
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("超期任务执行失败");
            return new JsonResult("000001");
        }

    }

    /**
     * 根据id查询文献流程
     *
     * @param id
     * @return
     */
    @Override
    public JsonResult getLiteratureProcessById(String id) {
        try {
            HashMap<String,Object> postLiteratureProcess = postLiteratureProcessMapper.getLiteratureProcessById(id);
            return new JsonResult(1,postLiteratureProcess);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("000001");
        }
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

    @Override
    public JsonResult getWfActionByNotice(String id) {
        try {
            PostLiteratureProcess wfAction = postLiteratureProcessMapper.getWfActionByNotice(id);
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            return new JsonResult("200507");
        }
    }


}

package com.tj720.controller.postLiterature;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.literature.PostLiteratureProcess;
import com.tj720.service.PostLiteratureProcessService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyf
 * @date 2018/10/25 16:38
 **/
@RestController
@RequestMapping("/postLiteratureProcess")
public class PostLiteratureProcessController {

    @Autowired
    private PostLiteratureProcessService postLiteratureProcessService;

    /**
     * @author wyf
     * @description  获取审批人
     * @date  2018/10/25 16:42
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "获取审批人")
    @RequestMapping("getApproveList")
    public JsonResult getApproveList() {

        JsonResult jsonResult = postLiteratureProcessService.getApproveList();

        return jsonResult;
    }
    /**
     * @author wyf
     * @description  获取资料名称
     * @date  2018/11/16 11:28
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "获取资料名称")
    @RequestMapping("getLiteratureName")
    public JsonResult getLiteratureName() {

        JsonResult jsonResult = postLiteratureProcessService.getLiteratureName();

        return jsonResult;
    }

    /**
     * @author wyf
     * @description   保存申请
     * @date  2018/10/26 15:14
     * @param    postLiteratureProcess  封装实体
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "保存申请")
    @RequestMapping("postLiteratureProcessSave")
    public JsonResult postLiteratureProcessSave(@RequestBody PostLiteratureProcess postLiteratureProcess) {

        JsonResult jsonResult = postLiteratureProcessService.postLiteratureProcessSave(postLiteratureProcess);

        return jsonResult;
    }

    /**
     * @author wyf
     * @description   获取申请列表
     * @date  2018/10/26 16:24
     * @param key  查询条件
     * @param department  部门
     * @param approveStatus  申请状态
     * @param orderBy   排序
     * @param currentPage  当前页
     * @param size  每页大小
     * @return net.sf.json.JSONObject
     */
    @ControllerAop(action = "获取申请列表")
    @RequestMapping("postLiteratureProcessList")
    public JSONObject postLiteratureProcessList(String key,String department,String approveStatus,String orderBy,
            @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        JSONObject jsonObject = postLiteratureProcessService.postLiteratureProcessList(key,department,approveStatus,orderBy,currentPage,size);

        return jsonObject;
    }

    /**
     * @author wyf
     * @description 保存审批
     * @date  2018/11/19 19:18
     * @param
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "保存审批")
    @RequestMapping("approveSave")
    public JsonResult approveSave(@RequestBody PostLiteratureProcess postLiteratureProcess) {

        JsonResult jsonResult = postLiteratureProcessService.approveSave(postLiteratureProcess);

        return jsonResult;
    }
    /**
     * @author wyf
     * @description  批量审批
     * @date  2018/12/3 11:53
     * @param arr     需审批的id数组
     * @param setting     审批类型
     * @param approveName    审批人名
     * @param preApproveId   前一个审批人
     * @param approveId      审批人id
     * @param approveRemark   审批备注
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "批量审批")
    @RequestMapping("batchApprove")
    public JsonResult batchApprove(@RequestParam(value = "arr[]") String[] arr,String setting,
            String approveName,String preApproveId,String approveId,String approveRemark) {

        JsonResult jsonResult = postLiteratureProcessService.batchApprove(arr,setting,approveName,preApproveId,approveId,approveRemark);

        return jsonResult;
    }

    /**
     * @author wyf
     * @description  借阅列表
     * @date  2018/11/19 20:26
     * @param key
     * @param department
     * @param borrowStatus
     * @param orderBy
     * @param currentPage
     * @param size
     * @return net.sf.json.JSONObject
     */
    @ControllerAop(action = "借阅列表")
    @RequestMapping("borrowingList")
    public JSONObject borrowingList(String key,String department,String borrowStatus,String orderBy,
            @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        JSONObject jsonObject = postLiteratureProcessService.borrowingList(key,department,borrowStatus,orderBy,currentPage,size);

        return jsonObject;
    }

    /**
     * @author wyf
     * @description  保存纸质借阅申请
     * @date  2018/11/29 18:42
     * @param
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "保存纸质借阅申请")
    @RequestMapping("savePaperBorrowing")
    public JsonResult savePaperBorrowing(@RequestBody PostLiteratureProcess postLiteratureProcess) {

        JsonResult jsonResult = postLiteratureProcessService.savePaperBorrowing(postLiteratureProcess);

        return jsonResult;
    }
    @ControllerAop(action = "修改状态")
    @RequestMapping("modifyState")
    public JsonResult modifyState(String id,String status) {

        JsonResult jsonResult = postLiteratureProcessService.modifyState(id,status);

        return jsonResult;
    }
    @ControllerAop(action = "获取文献申请流程")
    @RequestMapping("getLiteratureProcessById")
    public JsonResult getLiteratureProcessById(@RequestParam String id){
        JsonResult jsonResult = postLiteratureProcessService.getLiteratureProcessById(id);
        return jsonResult;
    }

    @RequestMapping("/getWfActionByNotice")
    public JsonResult getWfActionByNotice(@RequestParam String id){
        JsonResult jsonResult = postLiteratureProcessService.getWfActionByNotice(id);
        return jsonResult;
    }
}

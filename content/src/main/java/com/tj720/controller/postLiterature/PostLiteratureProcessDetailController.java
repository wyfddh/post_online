package com.tj720.controller.postLiterature;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.literature.PostLiteratureProcessDetail;
import com.tj720.service.PostLiteratureProcessDetailService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyf
 * @date 2018/11/19 10:18
 **/
@RestController
@RequestMapping("/postLiteratureProcessDetail")
public class PostLiteratureProcessDetailController {

    @Autowired
    private PostLiteratureProcessDetailService postLiteratureProcessDetailService;

    @ControllerAop(action = "获取文献申请流程详细列表")
    @RequestMapping("processDetailList")
    public JSONObject processDetailList(String id,@RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {

        JSONObject jsonObject = postLiteratureProcessDetailService.processDetailList(id,currentPage,size);

        return jsonObject;
    }
    @ControllerAop(action = "获取单个文献申请流程详细")
    @RequestMapping("getProcessDetailByProcess")
    public JsonResult getProcessDetailByProcess(@RequestParam String processId){
        JsonResult jsonResult = postLiteratureProcessDetailService.getProcessDetailByProcess(processId);
        return jsonResult;
    }

}

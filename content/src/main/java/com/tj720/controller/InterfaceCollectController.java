package com.tj720.controller;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.service.InterfaceCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口藏品controller
 * @author 杜昶
 * @Date: 2018/11/16 10:48
 */
@RestController
@RequestMapping("/interfaceCollect")
public class InterfaceCollectController extends BaseController {

    @Autowired
    private InterfaceCollectService interfaceCollectService;

    /**
     * 获取接口藏品类型
     * @return
     */
    @RequestMapping("/getCollctTypeList")
    public JsonResult getCollctTypeList() {
        try {
            return interfaceCollectService.getCollctTypeList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    /**
     * 根据藏品列表和藏品名称，模糊查询藏品下拉
     * @param culCategory 藏品类型
     * @param culName 藏品名称
     * @return
     */
    @RequestMapping("/getCollectByTypeAndName")
    public JsonResult getCollectByTypeAndName(String culCategory, String culName) {
        try {
            return interfaceCollectService.getCollectByTypeAndName(culCategory, culName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    /**
     * 根据藏品id获取藏品信息
     * @param culId
     * @return
     */
    @RequestMapping("/getCollectByCulId")
    public JsonResult getCollectByCulId(String culId) {
        try {
            return interfaceCollectService.getCollectByCulId(culId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    @RequestMapping("/getShareCulFieldAndDataList")
    public Map<String, Object> getShareCulFieldAndDataList(String keyWord, Integer pageIndex, Integer pageSize) {
        Map<String, Object> result = new HashMap<String, Object>(4);
        result.put("code", 1);
        result.put("msg", "网络异常");
        try {
            if (null == pageIndex) {
                pageIndex = 1;
            }
            if (null == pageSize) {
                pageIndex = 10;
            }
            return interfaceCollectService.getShareCulFieldAndDataList(keyWord, pageIndex, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.PostLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取流水号
 * @Author: 程荣凯
 * @Date: 2018/10/23 16:30
 */
@RestController
@RequestMapping("/PostLs")
public class PostLsController {
    @Autowired
    PostLsService postLsService;
    @RequestMapping("/getPostLs")
    public JsonResult getPostLs(@RequestParam("lsKey") String lsKey, String lsType){
        String lsCode = postLsService.getLsCode(lsKey, lsType);
        return new JsonResult(1,lsCode);
    }
}

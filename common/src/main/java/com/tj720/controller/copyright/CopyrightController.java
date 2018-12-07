package com.tj720.controller.copyright;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.copyright.CopyrightSetting;
import com.tj720.service.CopyrightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Author: 程荣凯
 * @Date: 2018/11/16 15:41
 */
@RestController
@RequestMapping("/Copyright")
public class CopyrightController {
    @Autowired
    CopyrightService copyrightService;

    /**
     * 查询版权设置
     * @return
     */
    @RequestMapping("/getCopyright")
    public JsonResult getCopyright(){
        JsonResult jsonResult = copyrightService.getCopyrightInfo();
        return jsonResult;
    }

    /**
     * 保存版权设置
     * @param copyrightSetting
     * @return
     */
    @RequestMapping("saveCopyright")
    public JsonResult saveCopyright(CopyrightSetting copyrightSetting){
        JsonResult jsonResult = copyrightService.saveCopyrightInfo(copyrightSetting);
        return jsonResult;
    }
}

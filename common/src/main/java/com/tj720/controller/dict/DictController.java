package com.tj720.controller.dict;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.SysDictSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/15 15:56
 */
@RequestMapping("/Dict")
public class DictController {
    @Autowired
    SysDictSevice sysDictSevice;
    @RequestMapping("/getDictData")
    public JsonResult getDictData(String[] dicts){
        Map<String, Object> dictListByArr = sysDictSevice.getDictListByArr(dicts);
        return new JsonResult(1,dictListByArr);
    }
}

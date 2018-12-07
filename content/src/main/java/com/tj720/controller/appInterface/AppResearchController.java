package com.tj720.controller.appInterface;


import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.publiccurator.PostPublicCuratorDto;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import com.tj720.service.SysDictSevice;
import com.tj720.service.WebInterfaceService;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *   App端调用接口
 * @Author:  xa
 * @Date: 2018/11/12
 */
@RestController
public class AppResearchController{

    @Autowired
    private  WebInterfaceService  webCollectService;


   /**
     * 查询学术研究列表
     *
     * @param
     * @return
     */
    @RequestMapping("/getAppResearchList")
    public JsonResult  getAppResearchList(String startTime, String endTime, String orderBy,
                                         @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam
                                                 (defaultValue = "3") Integer size){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JSONObject reserarchObject = webCollectService.selectListWebByResearch(startTime, endTime, orderBy,
                currentPage, size);
        return  new JsonResult(1,reserarchObject,pageInfo);
    }



    /**
     * 查询单个学术研究(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageAppResearch")
    public JsonResult  getOnePageAppResearch(Integer rowNum){
        JsonResult result = webCollectService.getOnePageWebResearch(rowNum);
        return  new JsonResult(1,result);
    }


    /**
     * 查询单个学术研究
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOneAppResearch")
    public JsonResult getOneAppResearch(@RequestParam("id") String id) {
        return  webCollectService.selectWebReserarchByKey(id);
    }





}

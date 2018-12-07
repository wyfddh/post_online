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
public class AppInfoController{

    @Autowired
    private  WebInterfaceService  webCollectService;

    @Autowired
    private  SysDictSevice  sysDictSevice;




    /**
     * @Author  xa
     * @Description  查询资讯分类数据
     * @return
     */
    @RequestMapping("/getAppInfoType")
    public LayUiTableJson  getAppInfoType() throws Exception{
        try{
            List<SysDict> sysDictList = sysDictSevice.getSysDictListByType("info_manager");
            return new LayUiTableJson(1,null,0,sysDictList,1);
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiTableJson(0,"查询失败",0,null);
        }
    }






    /**
     * @Author  xa
     * @Description  查询资讯管理数据
     * @return
     */
    @RequestMapping("/getAppInfoList")
    public JsonResult  getAppInfoList(String informationType,String createTime,@RequestParam(defaultValue =
            "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        JsonResult informationList = null;
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        if("1".equals(informationType)){
            informationList =  webCollectService.getInformationWebList(null, createTime,null, pageInfo);
        }else{
            informationList =  webCollectService.getInformationWebList(informationType, createTime,null, pageInfo);
        }

        return  new JsonResult(1,informationList,pageInfo);
    }



    /**
     * 查询单个资讯(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageAppInfo")
    public JsonResult getOnePageAppInfo(Integer rowNum,String informationType){
        JsonResult result = webCollectService.getOnePageWebInfo(rowNum,informationType);
        return  new JsonResult(1,result);
    }





    /**
     * 根据id获取资讯管理（id）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getAppInfoById")
    public JsonResult getAppInfoById(@RequestParam  String id) throws Exception {
        return  webCollectService.selectWebInfoByPrimaryKey(id);
    }





}

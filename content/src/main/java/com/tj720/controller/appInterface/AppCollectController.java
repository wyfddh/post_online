package com.tj720.controller.appInterface;


import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.publiccurator.PostPublicCuratorDto;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import com.tj720.service.SysDictSevice;
import com.tj720.service.WebInterfaceService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   App端调用接口
 * @Author:  xa
 * @Date: 2018/11/12
 */
@RestController
public class AppCollectController{

    @Autowired
    private  WebInterfaceService  webCollectService;

    @Autowired
    private  SysDictSevice  sysDictSevice;





    /**
     * @Author  xa
     * @Description  查询藏品分类数据(二级分类)
     * @return
     */
    @RequestMapping("/getAppCollectType")
    public LayUiTableJson  getAppCollectType() throws Exception{
        try {
            List<SysDict> sysDictList = sysDictSevice.getSysDictListByType("post_collect_two");
            return new LayUiTableJson(1,null,0,sysDictList);
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiTableJson(0,"查询失败",0,null);
        }
    }


    /**
     * 查询藏品列表
     *
     * @param
     * @return
     */
    @RequestMapping("/getAppCollectList")
    public JSONObject getAppCollectList(@RequestParam String typeId,@RequestParam String userId,
                 @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "8") Integer size){
        return webCollectService.selectListByWebCollectAndUser(typeId, userId, currentPage, size);
    }



    /**
     * 查询单个藏品
     *
     * @param id
     * @return
     */
    @RequestMapping("/getAppOneCollect")
    public JsonResult getAppOneCollect(@RequestParam("id") String id,@RequestParam String userId){
        return  webCollectService.getOneWebCollectAndUser(id,userId);
    }




}

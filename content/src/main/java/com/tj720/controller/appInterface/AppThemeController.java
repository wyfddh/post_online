package com.tj720.controller.appInterface;


import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.WebInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppThemeController{

    @Autowired
    private WebInterfaceService  webCollectService;




    /**
     * @Author  xa
     * @Description 查询主题展数据(含首页推荐)
     * @return
     */
    @RequestMapping("/getAppThemeList")
    public JsonResult getAppThemeList(String themeName, String themeSource, @RequestParam("id") String id,@RequestParam String userId,
    @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "6") Integer size) throws Exception{
        Map<String, Object> data = new HashMap<String, Object>();
        //首页推荐主题展
        JsonResult jsonResult1 = webCollectService.themeshowWebList(null, null,userId,null, 1, 1);
        data.put("postThemeShow",jsonResult1.getData());


        JsonResult themeList = webCollectService.themeshowWebNoPages(null, null,userId);
        List<CollectDto> lists = (List<CollectDto>) themeList.getData();
        data.put("themeList",lists);

        JsonResult collectList1 = webCollectService.themeshowWebNoPagesPlus(id,userId);
        List<CollectDto> collectList = (List<CollectDto>) collectList1.getData();
        data.put("collectList",collectList);

        //精选专题
        JsonResult ztlist = webCollectService.getJxztList(userId,null);
        List<PostThemeShow> ztlists = (List<PostThemeShow>) ztlist.getData();
        data.put("ztlists",ztlists);

        JsonResult jsonResult = new JsonResult(1, data);
        return  jsonResult;
    }



    /**
     * 根据id获取主题展信息（id）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getAppThemeById")
    public JsonResult getAppThemeById(@RequestParam  String id,@RequestParam String userId) throws Exception{
        return  webCollectService.getAppThemeById(id,userId);
    }








    /**
     * @Author  xa
     * @Description 查询主题展数据(精选专题)
     * @return
     */
    @RequestMapping("/getAppThemeztList")
    public JsonResult getAppThemeztList(String themeName, String themeSource,@RequestParam("id") String id,
                                        @RequestParam
            (defaultValue = "1") Integer
            currentPage, @RequestParam(defaultValue = "4") Integer size) throws Exception{
        return  webCollectService.themeshowWebztList(themeName,themeSource,id,currentPage,size);

    }







}

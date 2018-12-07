package com.tj720.controller.appInterface;


import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.collect.Collect;
import com.tj720.service.WebInterfaceService;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class AppIndexController{

    @Autowired
    private WebInterfaceService  webCollectService;



    /**
     * 查询首页列表(主题、典藏精选、资讯)
     *
     * @param
     * @return
     */
    @RequestMapping("/getAppIndexData")
    public JsonResult  getAppIndexData(String  userId)throws Exception{
        Map<String, Object> data = new HashMap<>();
        Page page = new Page();
        page.setSize(3);

        //首页推荐主题展
        JsonResult jsonResult1 = webCollectService.themeshowWebList(null, null,userId,null, 1, 1);
        data.put("postThemeShow",jsonResult1.getData());


        //典藏精选
        JSONObject jsonObject1 = webCollectService.selectListIndexByWebCollect(null, null, null,null, userId);
        List<Collect> collectList = (List<Collect>) jsonObject1.get("data");
        data.put("collectList",collectList);



        //资讯动态
        JsonResult informationWebList = webCollectService.getInformationWebList(null, null,null, page);
        //List<PostInformationManage> data1 = (List<PostInformationManage>) informationWebList.getData();
        data.put("informationWebList",informationWebList);
        JsonResult jsonResult = new JsonResult(1, data);
        return jsonResult;
    }





}

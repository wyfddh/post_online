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
public class AppStoryController {

    @Autowired
    private  WebInterfaceService  webCollectService;

    @Autowired
    private  SysDictSevice  sysDictSevice;





    /**
     * 首页典藏精选藏品
     *
     * @param
     * @return
     */
    @RequestMapping("/getAppIndexjxCollect")
    public JsonResult getAppIndexjxCollect(){
        return  webCollectService.selectIndexCollectList();
    }



    /***
     * @Author  xa
     * @Description  查询邮政故事分类数据
     * @return
     */
    @RequestMapping("/getAppStoryType")
    public LayUiTableJson getAppStoryType() throws Exception{
        try {
            List<SysDict> sysDictList = sysDictSevice.getSysDictListByType("stamp_story");
            return new LayUiTableJson(1,null,0,sysDictList,1);
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiTableJson(0,"查询失败",0,null,0);
        }
    }



    /**
     * 查询单个邮政故事(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageAppStory")
    public JsonResult getOnePageAppStory(Integer rowNum,String  storyType){
        JsonResult result = webCollectService.getDetailStoryById(rowNum,storyType);
        return  new JsonResult(1,result);
    }




    /**
     * @Author  xa
     * @Description  查询邮政故事数据
     * @return
     */
    @RequestMapping("/getStampStoryAppList")
    public JsonResult  getStampStoryAppList(String storyType,@RequestParam(defaultValue = "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        JsonResult stampStoryList = null;
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        if("0".equals(storyType)){
            stampStoryList =  webCollectService.getStampStoryWebList(null, null, pageInfo);
        }else{
            stampStoryList =  webCollectService.getStampStoryWebList(storyType, null, pageInfo);
        }
        return  new JsonResult(1,stampStoryList,pageInfo);
    }



   /**
     * 根据id获取邮政故事（id）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getStoryAppById")
    public JsonResult getStoryAppById(@RequestParam  String id) throws Exception {
        return  webCollectService.selectWebStoyrByPrimaryKey(id);
    }




}

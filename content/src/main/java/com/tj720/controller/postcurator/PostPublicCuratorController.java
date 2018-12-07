package com.tj720.controller.postcurator;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.PostPublicCuratorService;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/curator")
public class PostPublicCuratorController{


    @Autowired
    private PostPublicCuratorService  postPublicCuratorService;



    /**
     * @Author  xa
     * @Description  查询公众策展数据
     * @return
     */
    @ControllerAop(action = "查询公众策展数据")
    @RequestMapping("/getCuratorList")
    @ResponseBody
    public LayUiTableJson getCuratorList(String themeName, String processState, String processResult,@RequestParam(defaultValue = "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);

        JsonResult curatorList = postPublicCuratorService.getCuratorList(themeName, processState, processResult, pageInfo);
        System.out.println("curatorList  :"+ curatorList);
        if (curatorList != null && curatorList.getSuccess() == 1){
            if (null != curatorList){
                return new LayUiTableJson(0,curatorList.getMsg(),pageInfo.getAllRow(),(List)curatorList.getData());
            }else {
                return new LayUiTableJson(0,curatorList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,curatorList.getMsg(),0,null);
        }
    }




    /**
     * @Author  xa
     * @Description 根据id删除公众策展
     * @return
     */
    @ControllerAop(action = "根据id删除公众策展")
    @RequestMapping("/deleteById")
    @ResponseBody
    public JsonResult deleteByPrimaryKey(String id) throws Exception{
        return  postPublicCuratorService.deleteByPrimaryKey(id);
    }



    /**
     *  批量删除公众策展 ids
     * @param
     * @return
     */
    @ControllerAop(action = "批量删除公众策展")
    @RequestMapping("/updatetCuratorByIds")
    public JsonResult updatetCuratorByIds(@RequestParam(value="ids")String[] ids){
        List<String> asList = Arrays.asList(ids);
        return  postPublicCuratorService.updateHomeByIds(asList);
    }



    /**
     * 新增公众策展
     * @param  record
     * @return
     * @throws Exception
     * 废弃，post_public_Curator已与post_theme_show合并
     */
//    @ControllerAop(action = "新增公众策展")
//    @RequestMapping("/saveCurator")
//    @ResponseBody
//    public JsonResult saveCollectHome(PostPublicCurator  record,String picids) throws Exception{
//        return  postPublicCuratorService.insertSelective(record,picids);
//    }




    /**
     * 根据id获取公众策展（id）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据id获取公众策展")
    @RequestMapping("/getCuratorById")
    @ResponseBody
    public JsonResult selectByPrimaryKey(String id) throws Exception {
        return  postPublicCuratorService.selectByPrimaryKey(id);
    }



    /**
     * 修改公众策展
     * @param record
     * @TODO: 表结构合并，修改逻辑
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改公众策展")
    @RequestMapping("/updateCurator")
    @ResponseBody
    public JsonResult updateByPrimaryKeySelective(PostThemeShow record, String picids) throws Exception{
        return  postPublicCuratorService.updateByPrimaryKeySelective(record,picids);
    }




}

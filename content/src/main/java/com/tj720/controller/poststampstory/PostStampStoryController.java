package com.tj720.controller.poststampstory;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.stampstory.PostStampStory;
import com.tj720.service.PostStampStoryService;
import com.tj720.service.SysDictSevice;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stampstory")
public class PostStampStoryController{

    @Autowired
    private SysDictSevice sysDictSevice;

    @Autowired
    private PostStampStoryService  postStampStoryService;

    /**
     * @Author  xa
     * @Description  查询邮政故事数据
     * @return
     */
    @ControllerAop(action = "查询邮政故事数据")
    @RequestMapping("/getStampStoryList")
    @ResponseBody
    public LayUiTableJson getThemeList(String storyType,String createTime,@RequestParam(defaultValue = "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size,@RequestParam(defaultValue = "1") String
            orderBy) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult stampStoryList = postStampStoryService.getStampStoryList(storyType, createTime,orderBy, pageInfo);
        if (stampStoryList != null && stampStoryList.getSuccess() == 1){
            if (null != stampStoryList){
                return new LayUiTableJson(0,stampStoryList.getMsg(),pageInfo.getAllRow(),(List)stampStoryList.getData());
            }else {
                return new LayUiTableJson(0,stampStoryList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,stampStoryList.getMsg(),0,null);
        }

    }




    /**
     * @Author  xa
     * @Description 根据id删除邮政故事
     * @return
     */
    @ControllerAop(action = "根据id删除邮政故事")
    @RequestMapping("/deleteById")
    @ResponseBody
    public JsonResult deleteByPrimaryKey(String id) throws Exception{
        return postStampStoryService.deleteByPrimaryKey(id);
    }



    /**
     *  批量删除邮政故事 ids
     * @param
     * @return
     */
    @ControllerAop(action = "批量删除邮政故事")
    @RequestMapping("/updateStampStoryByIds")
    public JsonResult updateThemebByIds(@RequestParam(value="ids")String[] ids) {
        List<String> asList = Arrays.asList(ids);
        return  postStampStoryService.updateStampStoryByIds(asList);
    }



    /**
     * 新增邮政故事
     * @param  record
     * @return
     * @throws Exception
     */
    @ControllerAop(action = "新增邮政故事")
    @RequestMapping("/saveStampStory")
    @ResponseBody
    public JsonResult saveStampStory(PostStampStory record, String picids) throws Exception{
        return  postStampStoryService.insertSelective(record,picids);
    }


    /**
     * 根据id获取邮政故事（id）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据id获取邮政故事")
    @RequestMapping("/getStoryById")
    @ResponseBody
    public JsonResult selectByPrimaryKey(String id) throws Exception {
        return postStampStoryService.selectByPrimaryKey(id);
    }



    /**
     * 修改邮政故事
     * @param record
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改邮政故事")
    @RequestMapping("/updateStampStory")
    @ResponseBody
    public JsonResult updateByPrimaryKeySelective(PostStampStory record,String picids) throws Exception{
        return  postStampStoryService.updateByPrimaryKeySelective(record,picids);
    }


    /**
     * 邮票故事类型集合
     * @param
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "邮票故事类型集合")
    @RequestMapping("/getStoryTypeOptions")
    @ResponseBody
    public JsonResult getStoryTypeOptions(){
        List<PostStampStory> storyTypeOptions = postStampStoryService.getStoryTypeOptions();
        return new JsonResult(1,storyTypeOptions);
    }


    /**
     * 从字典表取下拉框数据
     * @param   arr 请求的参数数组
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "邮票故事获取数据字典数据")
    @RequestMapping("getSelectData")
    public JsonResult getListHeadData(@RequestParam(value = "arr[]") String[] arr) {
        if (arr == null || arr.length <= 0) {
            return new JsonResult(0);
        }
        Map<String, Object> map = sysDictSevice.getDictListByArr(arr);

        return new JsonResult(1, map);
    }

}

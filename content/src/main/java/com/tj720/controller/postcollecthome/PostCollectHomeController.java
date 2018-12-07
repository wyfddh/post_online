package com.tj720.controller.postcollecthome;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.collecthome.PostCollectHome;
import com.tj720.service.PostCollectHomeService;
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
@RequestMapping("/collecthome")
public class PostCollectHomeController{

    @Autowired
    private SysDictSevice sysDictSevice;

    @Autowired
    private PostCollectHomeService postCollectHomeService;

    /**
     * @Author  xa
     * @Description  查询集邮之家数据
     * @return
     */
    @ControllerAop(action = "查询集邮之家数据")
    @RequestMapping("/getCollectHomeList")
    @ResponseBody
    public LayUiTableJson getHomeList(String collectHomeTheme,String createTime,@RequestParam(defaultValue = "1")
            String orderBy,@RequestParam(defaultValue = "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult homeList = postCollectHomeService.getHomeList(collectHomeTheme, createTime,orderBy, pageInfo);
        if (homeList != null && homeList.getSuccess() == 1){
            if (null != homeList){
                return new LayUiTableJson(0,homeList.getMsg(),pageInfo.getAllRow(),(List)homeList.getData());
            }else {
                return new LayUiTableJson(0,homeList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,homeList.getMsg(),0,null);
        }

    }




    /**
     * @Author  xa
     * @Description 根据id删除集邮之家
     * @return
     */
    @ControllerAop(action = "根据id删除集邮之家")
    @RequestMapping("/deleteById")
    @ResponseBody
    public JsonResult deleteByPrimaryKey(String id) throws Exception{
        return postCollectHomeService.deleteByPrimaryKey(id);
    }



    /**
     *  批量删除集邮之家  ids
     * @param
     * @return
     */
    @ControllerAop(action = "批量删除集邮之家")
    @RequestMapping("/updateHomeByIds")
    public JsonResult updateHomeByIds(@RequestParam(value="ids")String[] ids) {
        List<String> asList = Arrays.asList(ids);
        return  postCollectHomeService.updateHomeByIds(asList);
    }



    /**
     * 新增集邮之家
     * @param  record
     * @return
     * @throws Exception
     */
    @ControllerAop(action = "新增集邮之家")
    @RequestMapping("/saveCollectHome")
    @ResponseBody
    public JsonResult saveCollectHome(PostCollectHome record) throws Exception{
        return postCollectHomeService.insertSelective(record);
    }


    /**
     * 根据id获取集邮之家（id）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据id获取集邮之家")
    @RequestMapping("/getHomeById")
    @ResponseBody
    public JsonResult selectByPrimaryKey(String id) throws Exception {
        return postCollectHomeService.selectByPrimaryKey(id);
    }



    /**
     * 修改集邮之家
     * @param record
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改集邮之家")
    @RequestMapping("/updateCollectHome")
    @ResponseBody
    public JsonResult updateByPrimaryKeySelective(PostCollectHome record) throws Exception{
        return  postCollectHomeService.updateByPrimaryKeySelective(record);
    }


    /**
     * 集邮资讯主题集合
     * @param
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "集邮资讯主题集合")
    @RequestMapping("/getHomeOptions")
    @ResponseBody
    public JsonResult getHomeOptions(){
        List<PostCollectHome> homeOptions = postCollectHomeService.getHomeOptions();
        return new JsonResult(1,homeOptions);
    }


    /**
     * 从字典表取下拉框数据
     * @param   arr 请求的参数数组
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "集邮之家获取数据字典数据")
    @RequestMapping("getSelectData")
    public JsonResult getListHeadData(@RequestParam(value = "arr[]") String[] arr) {
        if (arr == null || arr.length <= 0) {
            return new JsonResult(0);
        }
        Map<String, Object> map = sysDictSevice.getDictListByArr(arr);

        return new JsonResult(1, map);
    }

}

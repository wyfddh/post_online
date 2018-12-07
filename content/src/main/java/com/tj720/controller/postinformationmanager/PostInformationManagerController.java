package com.tj720.controller.postinformationmanager;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.service.PostInformationManagerService;
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
@RequestMapping("/informationmanager")
public class PostInformationManagerController{

    @Autowired
    private SysDictSevice sysDictSevice;

    @Autowired
    private PostInformationManagerService postInformationManagerService;

    /**
     * @Author  xa
     * @Description  查询资讯管理数据
     * @return
     */
    @ControllerAop(action = "查询资讯管理数据")
    @RequestMapping("/getInfoManagerList")
    @ResponseBody
    public LayUiTableJson getInfoManagerList(String informationType,String createTime,@RequestParam(defaultValue = "1") String orderBy,
          @RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult informationList = postInformationManagerService.getInformationList(informationType, createTime, orderBy, pageInfo);
        if (informationList != null && informationList.getSuccess() == 1){
            if (null != informationList){
                return new LayUiTableJson(0,informationList.getMsg(),pageInfo.getAllRow(),(List)informationList.getData());
            }else {
                return new LayUiTableJson(0,informationList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,informationList.getMsg(),0,null);
        }

    }




    /**
     * @Author  xa
     * @Description 根据id删除资讯管理
     * @return
     */
    @ControllerAop(action = "根据id删除资讯管理")
    @RequestMapping("/deleteById")
    @ResponseBody
    public JsonResult deleteByPrimaryKey(String id) throws Exception{
        return postInformationManagerService.deleteByPrimaryKey(id);
    }



    /**
     *  批量删除资讯管理 ids
     * @param
     * @return
     */
    @ControllerAop(action = "批量删除资讯管理")
    @RequestMapping("/updateInfoManagerByIds")
    public JsonResult updateInfoManagerByIds(@RequestParam(value="ids")String[] ids){
        List<String> asList = Arrays.asList(ids);
        return  postInformationManagerService.updateInfomationByIds(asList);
    }



    /**
     * 新增资讯管理
     * @param  record
     * @return
     * @throws Exception
     */
    @ControllerAop(action = "新增资讯管理")
    @RequestMapping("/saveInfoManager")
    @ResponseBody
    public JsonResult saveInfoManager(PostInformationManage record, String picids) throws Exception{
        return  postInformationManagerService.insertSelective(record,picids);
    }


    /**
     * 根据id获取资讯管理（id）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据id获取资讯管理")
    @RequestMapping("/getInfoManagerById")
    @ResponseBody
    public JsonResult selectByPrimaryKey(String id) throws Exception {
        return postInformationManagerService.selectByPrimaryKey(id);
    }



    /**
     * 修改资讯管理
     * @param record
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改资讯管理")
    @RequestMapping("/updateInfoManager")
    @ResponseBody
    public JsonResult updateByPrimaryKeySelective(PostInformationManage record,String picids) throws Exception{
        return  postInformationManagerService.updateByPrimaryKeySelective(record,picids);
    }




    /**
     * 从字典表取下拉框数据
     * @param   arr 请求的参数数组
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "咨询管理获取数据字典数据")
    @RequestMapping("getSelectData")
    public JsonResult getListHeadData(@RequestParam(value = "arr[]") String[] arr) {
        if (arr == null || arr.length <= 0) {
            return new JsonResult(0);
        }
        Map<String, Object> map = sysDictSevice.getDictListByArr(arr);

        return new JsonResult(1, map);
    }

}

package com.tj720.controller.webInterface;/**
 * Created by MyPC on 2018/11/1.
 */

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.area.MipArea;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.service.MipAreaService;
import com.tj720.service.PubUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: PubUserController
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/11/1
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/webpubUser")
public class WebPubUserController extends BaseController{

    @Autowired
    PubUserService pubUserService;
    @Autowired
    MipAreaService mipAreaService;
    /**
     * 添加公众用户
     *
     * @param pubUser 公众用户
     * @return
     */
    @ControllerAop(action = "添加公众用户")
    @RequestMapping("/addPubUser")
    public JsonResult addPubUser(@RequestBody PubUser pubUser) {

        return pubUserService.addPubUser(pubUser);
    }

    /**
     * 修改公众用户
     *
     * @param pubUser 公众用户
     * @return
     */
    @ControllerAop(action = "修改公众用户")
    @RequestMapping("/updatePubUser")
    public JsonResult updatePubUser(@RequestBody PubUser pubUser){
        response.setContentType("application/json;charset=utf-8");
        return  pubUserService.updatePubUser(pubUser);
    }




    /**
     * 修改公众用户状态
     * @param userStatus  用户冻结状态 0:冻结 1:取消冻结
     ** @param id      主键id
     * @return
     */
    @ControllerAop(action = "修改公众用户状态")
    @RequestMapping("/modifyUserStatus")
    public JsonResult modifyRecommend(String userStatus, String id){
        JsonResult jsonResult =  pubUserService.modifyUserType(userStatus,id);
        return jsonResult;
    }





    /**
     * 删除公众用户 假删
     *
     * @param id 公众用户
     * @return
     */
    @ControllerAop(action = "删除用户")
    @RequestMapping("/deletePubUserById")
    public JsonResult deletePubUserById(@RequestParam("id") String id) {
        return pubUserService.deletePubUserById(id);
    }

    /**
     * 批量删除公众用户 假删
     *
     * @param ids 公众用户
     * @return
     */
    @ControllerAop(action = "批量删除用户")
    @RequestMapping("/updatePubUserByIds")
    public JsonResult updatePubUserByIds(@RequestParam(value = "ids") String[] ids) {
        List asList = Arrays.asList(ids);
        return pubUserService.updatePubUserByIds(asList);
    }


    /**
     * 查询公众用户列表 layui url只能用get方法
     *
     * @param
     * @return
     */
    @ControllerAop(action = "查询公众用户列表")
    @RequestMapping("/getListPubUser")
    public JSONObject getListPubUser(String name, String phone,String createTime,@RequestParam(defaultValue = "1") String orderBy,
                                     @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        return  pubUserService.getListPubUser(name,phone,createTime, orderBy, currentPage, size);
    }


    /**
     * 查询单个公众用户
     *
     * @param id
     * @return
     */
    @ControllerAop(action = "查询单个公众用户")
    @RequestMapping("/getOnePubUser")
    public JsonResult getOnePubUser(@RequestParam("id") String id) {

        return pubUserService.getOnePubUser(id);
    }

    @ControllerAop(action = "获取省市数据")
    @RequestMapping("/getArea.do")
    public JSONObject getArea() {
        JSONObject json = new JSONObject();
        JSONObject proJson = new JSONObject();
        JSONObject cityJson = new JSONObject();
        JSONArray proJsonArray = new JSONArray();
        //省
        List<MipArea> provinces = new ArrayList<MipArea>();
        //市
        List<MipArea> citys = new ArrayList<MipArea>();
        //所有数据
        provinces = mipAreaService.getCityListByPid(0);
        for (MipArea mipPro : provinces) {
            proJson.put("value", mipPro.getId());
            proJson.put("label", mipPro.getName());
            JSONArray cityJSONArray = new JSONArray();
            citys = mipAreaService.getCityListByPid(mipPro.getId());
            for (MipArea mipCity : citys) {
                cityJson.put("value", mipCity.getId());
                cityJson.put("label", mipCity.getName());
                cityJSONArray.add(cityJson);
            }
            proJson.put("children", cityJSONArray);
            proJsonArray.add(proJson);
        }
        json.put("options", proJsonArray);
        json.put("success", 1);
        return json;

    }

    @ControllerAop(action = "获取省市数据app端")
    @RequestMapping("/getAreaApp.do")
    public JSONObject getAreaApp() {
        JSONObject json = new JSONObject();
        JSONObject proJson = new JSONObject();
        JSONObject cityJson = new JSONObject();
        JSONArray proJsonArray = new JSONArray();
        //省
        List<MipArea> provinces = new ArrayList<MipArea>();
        //市
        List<MipArea> citys = new ArrayList<MipArea>();
        //所有数据
        provinces = mipAreaService.getCityListByPid(0);
        for (MipArea mipPro : provinces) {
            proJson.put("id", mipPro.getId());
            proJson.put("name", mipPro.getName());
            JSONArray cityJSONArray = new JSONArray();
            citys = mipAreaService.getCityListByPid(mipPro.getId());
            for (MipArea mipCity : citys) {
                cityJson.put("id", mipCity.getId());
                cityJson.put("name", mipCity.getName());
                cityJSONArray.add(cityJson);
            }
            proJson.put("children", cityJSONArray);
            proJsonArray.add(proJson);
        }
        json.put("options", proJsonArray);
        return json;

    }
}

package com.tj720.controller;/**
 * Created by MyPC on 2018/10/24.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.Collect;
import com.tj720.service.CollectService;
import com.tj720.utils.Tools;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: CollectController
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/24
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    CollectService collectService;


    /**
     * 添加藏品
     *
     * @param collect 藏品
     * @return
     */
    @ControllerAop(action = "添加藏品")
    @RequestMapping("/addCollect")
    public JsonResult addCollect(@RequestBody Collect collect) {

        return collectService.addCollect(collect);
    }

    /**
     * 修改藏品
     *
     * @param collect 藏品
     * @return
     */
    @ControllerAop(action = "修改藏品")
    @RequestMapping("/updateCollect")
    public JsonResult updateCollect(@RequestBody Collect collect) {

        return collectService.updateCollect(collect);
    }

    /**
     * 控制藏品状态
     *
     * @param collect 藏品，前台需传入一个状态标识
     * @return
     */
    @ControllerAop(action = "修改藏品状态")
    @RequestMapping("/updateCollectType")
    public JsonResult updateCollectType(@RequestBody Collect collect){
        //查询典藏精选数，限制3
        Integer countDcjx = collectService.CountDcjx();
        if ("0".equals(collect.getCommend()) || countDcjx<3) {
            collect.setCommend(collect.getCommend());
            collect.setUpdateTime(new Date());
            collect.setUpdater(Tools.getUserId());
            collectService.updateCollectType(collect);
            return new JsonResult(1);
        }else{
            return new JsonResult("200513");
        }


    }


    /**
     * 删除藏品 假删
     *
     * @param id 藏品
     * @return
     */
    @ControllerAop(action = "删除藏品")
    @RequestMapping("/deleteCollectById")
    public JsonResult deleteCollectById(@RequestParam("id") String id) {
        return collectService.deleteCollectById(id);
    }

    /**
     * 批量删除藏品 假删
     *
     * @param ids 藏品
     * @return
     */
    @ControllerAop(action = "批量删除藏品")
    @RequestMapping("/updateCollectByIds")
    public JsonResult updateCollectByIds(@RequestParam(value = "ids") String[] ids) {
        List asList = Arrays.asList(ids);
        return collectService.updateCollectByIds(asList);
    }


    /**
     * 查询藏品列表 layui url只能用get方法
     *
     * @param
     * @return
     */
    @ControllerAop(action = "查询藏品列表")
    @RequestMapping("/getListCollect")
    public JSONObject getListCollect(String name, String typeId, String sonTypeId,@RequestParam(defaultValue = "1") String orderBy,
                                     @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        return collectService.getListCollect(name, typeId, sonTypeId, orderBy, currentPage, size);
    }


    /**
     * 查询单个藏品
     *
     * @param id
     * @return
     */
    @ControllerAop(action="查询单个藏品")
    @RequestMapping("/getOneCollect")
    public JsonResult getOneCollect(@RequestParam("id") String id) {

        return collectService.getOneCollect(id);
    }

    /**
     * 根据父id查询业务字典列表
     *
     * @param pid 父id
     * @return
     */
    @ControllerAop(action = "根据父id查询业务字典列表")
    @RequestMapping("/getSysDictListByPid")
    public JsonResult getSysDictListByPid(@RequestParam("pid") String pid) {
        return collectService.getSysDictListByPid(pid);
    }

    /**
     * 根据id 给图片设置主图  is_main = 1; 是主图
     *
     * @param attachment
     * @return
     */
    @ControllerAop(action = "给图片设置主图")
    @RequestMapping("/updateAttachmentByid")
    public JsonResult updateAttachmentByid(@RequestBody Attachment attachment) {
        return collectService.updateAttachmentByid(attachment);
    }




    /**
     * 查询藏品类型
     *
     * @param
     * @return
     */
    @ControllerAop(action = "查询藏品类型")
    @RequestMapping("/selectTypeList")
    public JsonResult selectTypeList() {
        return collectService.selectListByParams(null);
    }

    /**
     * 根据类型查询藏品列表
     *
     * @param
     * @return
     */
    @ControllerAop(action = "根据藏品类型查询藏品列表")
    @RequestMapping("/selectListByTypeId")
    public JsonResult selectListByType(@RequestParam("typeId") String typeId) {
        return collectService.selectListByParams(typeId);
    }

    /**
     * 根据ids查询藏品列表
     * @param ids 字符串格式id集合 如：1,2,3
     * @param typeId
     * @param sonTypeId
     * @return
     */
    @ControllerAop(action = "根据ids查询藏品列表")
    @RequestMapping("/selectListByIds")
    public JsonResult selectListByIds(String ids, String typeId, String sonTypeId) {
        try {
            return collectService.selectListByIds(ids, typeId, sonTypeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    /**
     *  select4  要求数据格式  name,value,selected,disabled
     * 根据类型查询藏品列表
     * @param
     * @return
     */
    @ControllerAop(action="根据类型查询藏品列表")
    @RequestMapping("/selectBaiDuListByParam")
    public JSONObject selectBaiDuListByParam(@RequestParam("typeId") String typeId){
        return collectService.selectBaiDuListByParam(typeId);
    }

    /**
     * 根据typeId和typeSonId
     * @param typeId
     * @param sonTypeId
     * @return
     */
    @ControllerAop(action = "通过藏品类型和子类型查询藏品类别")
    @RequestMapping("/selectListByTypeAndSonType")
    public JsonResult selectListByTypeAndSonType(@RequestParam("typeId") String typeId, @RequestParam("sonTypeId") String sonTypeId) {
        try {
            Collect collect = new Collect();
            collect.setTypeId(typeId);
            collect.setSonTypeId(sonTypeId);
            return collectService.selectListByTypeAndSonType(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }


}

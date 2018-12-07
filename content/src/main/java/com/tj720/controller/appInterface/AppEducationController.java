package com.tj720.controller.appInterface;


import com.tj720.controller.framework.JsonResult;
import com.tj720.service.SysDictSevice;
import com.tj720.service.WebInterfaceService;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *   App端调用接口
 * @Author:  xa
 * @Date: 2018/11/12
 */
@RestController
public class AppEducationController {

    @Autowired
    private  WebInterfaceService  webCollectService;

    @Autowired
    private  SysDictSevice  sysDictSevice;







    /**
     * 查询单个教育(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageAppEdu")
    public JsonResult getOnePageAppEdu(Integer rowNum){
        JsonResult result = webCollectService.getDetailEducationById(rowNum);
        return  new JsonResult(1,result);
    }




    /**
     * 查询教育活动列表
     *
     * @param
     * @return
     */
    @RequestMapping("/getAppEducationList")
    public JsonResult  getAppEducationList(String title, String startTime, String endTime,@RequestParam(defaultValue = "1") String orderBy,
                                           @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "7") Integer size) {
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JSONObject educationObject = webCollectService.selectListWebByEducation(title, startTime, endTime, orderBy,
                currentPage, size);
        String page = educationObject.getString("page");
        return  new JsonResult(1,educationObject,page);
    }


    /**
     * 查询单个教育活动
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOneAppEducation")
    public JsonResult  getOneAppEducation(@RequestParam("id") String id) {
        return  webCollectService.selectWebEduByKey(id);
    }




    /*//**
     * 获取志愿者活动列表
     * @param currentPage
     * @param size
     * @return
     *//*
    @RequestMapping("/getVolunteerActivitiesList")
    public JsonResult getVolunteerActivitiesList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "4") Integer size) {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);
        try {
            JsonResult jsonResult = webCollectService.getVolunteerActivitiesList(page);
            if (jsonResult.getSuccess() == 1) {
                return new JsonResult(1, jsonResult, page);
            } else {
                return new JsonResult(0, null, jsonResult.getErrorCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    *//**
     * 申请志愿者活动
     * @param postVolunteerApply
     * @return
     *//*
    @RequestMapping("/addApplyVolunteer")
    public JsonResult addApplyVolunteer(@RequestBody PostVolunteerApply postVolunteerApply) {
        try {
            return webCollectService.addApplyVolunteer(postVolunteerApply);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    *//**
     * 添加策展
     * @param dto
     * @return
     *//*
    @RequestMapping("/addPublicCurator")
    public JsonResult addPublicCurator(PostPublicCuratorDto dto) {
        try {
            return webCollectService.addPublicCurator(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    *//**
     * 查询公共策展列表
     * @param currentPage
     * @param size
     * @param status 状态 -1-已提交 0-全部 1-已采用
     * @param userId
     * @return
     *//*
    @RequestMapping("/getPublicCuratorList")
    public JsonResult getPublicCuratorList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "4") Integer size
            , @RequestParam(defaultValue = "0") Integer status, String userId) {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);
        try {
            JsonResult jsonResult = webCollectService.getPublicCuratorList(userId,status, page);
            if (jsonResult.getSuccess() == 1) {
                return new JsonResult(1, jsonResult, page);
            } else {
                return new JsonResult(0, null, jsonResult.getErrorCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    *//**
     * 根据id查询公共策展
     * @param id
     * @return
     *//*
    @RequestMapping("/getPublicCuratorById")
    public JsonResult getPublicCuratorById(String id) {
        try {
            return webCollectService.getPublicCuratorById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    *//**
     * 根据ids查询藏品列表
     * @param ids 字符串格式id集合 如：1,2,3
     * @param typeId 若为全部则传all
     * @param sonTypeId 若为全部则传all
     * @return
     *//*
    @RequestMapping("/selectCollectListByIds")
    public JsonResult selectCollectListByIds(String ids, String typeId, String sonTypeId) {
        try {
            return webCollectService.selectCollectListByIds(ids, typeId, sonTypeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    *//**
     * 获取藏品类型下拉
     * @param key
     * @return
     *//*
    @RequestMapping("/getSelectDataByKey")
    public JsonResult getSelectDataByKey(String key) {
        try {
            List<SysDict> dictListByKey = sysDictSevice.getDictListByKey(key);
            return new JsonResult(1,dictListByKey);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }

    *//**
     * 获取藏品类型二级下拉
     * @param pid
     * @return
     *//*
    @RequestMapping("/getSelectDataByPid")
    public JsonResult getSelectDataByPid(String pid) {
        try {
            List<SysDict> dictListByKey = sysDictSevice.getDictListByPid(pid);
            return new JsonResult(1,dictListByKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }

    *//**
     * 根据typeId 和 sonTypeId查询藏品列表
     * @param typeId
     * @param sonTypeId
     * @return
     *//*
    @RequestMapping("/getSelectCollectByTypeAndSonType")
    public JsonResult getSelectCollectByTypeAndSonType(String typeId, String sonTypeId) {
        try {
            Collect collect = new Collect();
            collect.setTypeId(typeId);
            collect.setSonTypeId(sonTypeId);
            return webCollectService.getSelectCollectByTypeAndSonType(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }*/





}

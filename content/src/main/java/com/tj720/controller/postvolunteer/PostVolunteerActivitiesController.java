package com.tj720.controller.postvolunteer;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.volunteer.PostVolunteerActivities;
import com.tj720.model.common.volunteer.PostVolunteerActivitiesDto;
import com.tj720.service.PostVolunteerActivitiesService;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 杜昶
 * @Date: 2018/11/8 10:40
 */
@RestController
@RequestMapping("/volunteer")
public class PostVolunteerActivitiesController extends BaseController {

    @Autowired
    private PostVolunteerActivitiesService postVolunteerActivitiesService;

    /**
     * 查询志愿者活动列表
     * @param dto
     * @return
     */
    @ControllerAop(action = "查询志愿者活动列表")
    @RequestMapping("/getActivitiesList")
    public LayUiTableJson getActivitiesList(PostVolunteerActivitiesDto dto,@RequestParam(defaultValue = "1") String orderBy) {
        if (null == dto.getCurrentPage()) {
            dto.setCurrentPage(1);
        }
        if (null == dto.getSize()) {
            dto.setSize(10);
        }
        if (null == dto.getOrderBy()) {
            dto.setOrderBy(orderBy);
        }
        Page page = new Page();
        page.setCurrentPage(dto.getCurrentPage());
        page.setSize(dto.getSize());

        try {
            JsonResult jsonResult = postVolunteerActivitiesService.getActivitiesList(dto,page);
            if (jsonResult.getSuccess() == 1) {
                return new LayUiTableJson(0, jsonResult.getMsg(), page.getAllRow(), (List) jsonResult.getData());
            } else {
                return new LayUiTableJson(1, jsonResult.getMsg(), 0, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return new LayUiTableJson(1, "系统异常", 0, null);
    }

    /**
     * 新增志愿者活动
     * @param postVolunteerActivities
     * @return
     */
    @ControllerAop(action = "新增志愿者活动")
    @RequestMapping("/addActivities")
    public JsonResult addActivities(@RequestBody PostVolunteerActivities postVolunteerActivities) {
        try {
            return postVolunteerActivitiesService.addActivities(postVolunteerActivities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 根据id获取活动信息
     * @param id
     * @return
     */
    @ControllerAop(action = "获取单个活动信息")
    @RequestMapping("/getActivitiesById")
    private JsonResult getActivitiesById(String id) {
        try {
            return postVolunteerActivitiesService.getActivitiesById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 修改志愿者活动
     * @param postVolunteerActivities
     * @return
     */
    @ControllerAop(action = "修改志愿者活动")
    @RequestMapping("/updateActivities")
    private JsonResult updateActivities(@RequestBody PostVolunteerActivities postVolunteerActivities) {
        try {
            return postVolunteerActivitiesService.updateActivities(postVolunteerActivities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 查询志愿者申请列表
     * @param id
     * @param currentPage
     * @param size
     * @return
     */
    @ControllerAop(action = "查询志愿者申请列表")
    @RequestMapping("/getVolunteerApplyList")
    private LayUiTableJson getVolunteerApplyList(@RequestParam String id,@RequestParam(defaultValue = "1") Integer currentPage
            ,@RequestParam(defaultValue = "10") Integer size) {
        try {
            Page page = new Page();
            page.setCurrentPage(currentPage);
            page.setSize(size);
            JsonResult jsonResult = postVolunteerActivitiesService.getVolunteerApplyList(id, page);
            if (jsonResult.getSuccess() == 1) {
                return new LayUiTableJson(0, jsonResult.getMsg(), page.getAllRow(), (List) jsonResult.getData());
            } else {
                return new LayUiTableJson(1, jsonResult.getMsg(), 0, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LayUiTableJson(1, "系统异常", 0, null);
    }



    /**
     * @Description 根据id删除志愿者申请
     * @return
     */
    @ControllerAop(action = "删除单个志愿者申请")
    @RequestMapping("/deleteVolunteerById")
    @ResponseBody
    public JsonResult deleteVolunteerById(String id) throws Exception{
        return  postVolunteerActivitiesService.deleteByPrimaryKey(id);
    }







}


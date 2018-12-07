package com.tj720.controller;/**
 * Created by MyPC on 2018/10/26.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.education.Education;
import com.tj720.service.EducationService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: EducationController
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/26
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/education")
public class EducationController {

    @Autowired
    EducationService educationService;

    /**
     * 添加教育活动
     *
     * @param education 教育活动
     * @return
     */
    @ControllerAop(action = "添加教育活动")
    @RequestMapping("/addEducation")
    public JsonResult addEducation(@RequestBody Education education) {

        return educationService.addEducation(education);
    }

    /**
     * 修改教育活动
     *
     * @param education 教育活动
     * @return
     */
    @ControllerAop(action = "修改教育活动")
    @RequestMapping("/updateEducation")
    public JsonResult updateEducation(@RequestBody Education education) {

        return educationService.updateEducation(education);
    }


    /**
     * 删除教育活动 假删
     *
     * @param id 教育活动
     * @return
     */
    @ControllerAop(action = "删除教育活动")
    @RequestMapping("/deleteEducationById")
    public JsonResult deleteEducationById(@RequestParam("id") String id) {
        return educationService.deleteEducationById(id);
    }

    /**
     * 批量删除教育活动 假删
     *
     * @param ids 教育活动
     * @return
     */
    @ControllerAop(action = "批量删除教育活动")
    @RequestMapping("/updateEducationByIds")
    public JsonResult updateEducationByIds(@RequestParam(value = "ids") String[] ids) {
        List asList = Arrays.asList(ids);
        return educationService.updateEducationByIds(asList);
    }


    /**
     * 查询教育活动列表 layui url只能用get方法
     *
     * @param
     * @return
     */
    @ControllerAop(action = "查询教育活动列表")
    @RequestMapping("/getListEducation")
    public JSONObject getListEducation(String title, String startTime,@RequestParam(defaultValue = "1") String orderBy,
                                      @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        return educationService.getListEducation(title, startTime,orderBy, currentPage, size);
    }


    /**
     * 查询单个教育活动
     *
     * @param id
     * @return
     */
    @ControllerAop(action = "查询单个教育活动")
    @RequestMapping("/getOneEducation")
    public JsonResult getOneEducation(@RequestParam("id") String id) {

        return educationService.getOneEducation(id);
    }



}

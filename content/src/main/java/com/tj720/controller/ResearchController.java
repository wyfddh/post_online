package com.tj720.controller;/**
 * Created by MyPC on 2018/10/26.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.research.Research;
import com.tj720.service.ResearchService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: ResearchController
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/26
 * @Version: 1.0
 **/
    @RestController
    @RequestMapping("/research")
    public class ResearchController {

        @Autowired
        ResearchService researchService;

        /**
         * 添加学术研究
         *
         * @param research 学术研究
         * @return
         */
        @ControllerAop(action = "添加学术研究")
        @RequestMapping("/addResearch")
        public JsonResult addResearch(@RequestBody Research research) {

            return researchService.addResearch(research);
        }

        /**
         * 修改学术研究
         *
         * @param research 学术研究
         * @return
         */
        @ControllerAop(action = "修改学术研究")
        @RequestMapping("/updateResearch")
        public JsonResult updateResearch(@RequestBody Research research) {

            return researchService.updateResearch(research);
        }


        /**
         * 删除学术研究 假删
         *
         * @param id 学术研究
         * @return
         */
        @ControllerAop(action = "删除学术研究")
        @RequestMapping("/deleteResearchById")
        public JsonResult deleteResearchById(@RequestParam("id") String id) {
            return researchService.deleteResearchById(id);
        }

        /**
         * 批量删除学术研究 假删
         *
         * @param ids 学术研究
         * @return
         */
        @ControllerAop(action = "批量删除学术研究")
        @RequestMapping("/updateResearchByIds")
        public JsonResult updateResearchByIds(@RequestParam(value = "ids") String[] ids) {
            List asList = Arrays.asList(ids);
            return researchService.updateResearchByIds(asList);
        }


        /**
         * 查询学术研究列表 layui url只能用get方法
         *
         * @param
         * @return
         */
        @ControllerAop(action = "查询学术研究列表")
        @RequestMapping("/getListResearch")
        public JSONObject getListResearch(String startTime,@RequestParam(defaultValue = "1") String orderBy,
                                         @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

            return researchService.getListResearch(startTime,orderBy, currentPage, size);
        }


        /**
         * 查询单个学术研究
         *
         * @param id
         * @return
         */
        @ControllerAop(action = "查询单个学术研究")
        @RequestMapping("/getOneResearch")
        public JsonResult getOneResearch(@RequestParam("id") String id) {

            return researchService.getOneResearch(id);
        }



}

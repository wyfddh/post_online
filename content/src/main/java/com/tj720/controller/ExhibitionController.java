package com.tj720.controller;/**
 * Created by MyPC on 2018/10/16.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.exhibition.ExhibitionDto;
import com.tj720.service.ExhibitionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: ExhibitionController
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/16
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/exhib")
public class ExhibitionController {

    @Autowired
    ExhibitionService exhibitionService;

    /**
     * 添加展陈
     * @param exhibitionDto 展陈
     * @return
     */
    @ControllerAop(action = "添加展陈")
    @RequestMapping("/addExhibition")
    public JsonResult addExhibition(@RequestBody ExhibitionDto exhibitionDto,String picids){
        return exhibitionService.addExhibition(exhibitionDto, "add",picids);
    }

    /**
     * 删除展陈
     * @param id 展陈列表删除
     * @return
     */
    @ControllerAop(action = "删除展陈")
    @RequestMapping("/delExhibition")
    public JsonResult delExhibition(@RequestParam("id") String id){
        return exhibitionService.deleteExhibColleById(id);
    }

    /**
     * 修改展陈
     * @param exhibitionDto 展陈编辑
     * @return
     */
    @ControllerAop(action = "修改展陈")
    @RequestMapping("/updateExhibition")
    public JsonResult updateExhibition(@RequestBody ExhibitionDto exhibitionDto,String picids){
        return exhibitionService.updateExhibition(exhibitionDto,picids);
    }

    /**
     *  查一个展陈
     * @param id 展陈编辑
     * @return
     */
    @ControllerAop(action = "查询单个展陈")
    @RequestMapping("/getOneExhibition")
    public JsonResult getOneExhibition(@RequestParam("id")String id){
        return exhibitionService.getOneExhibition(id);
    }

    /**
     *  查列表展陈
     * @param
     * @return
     */
    @ControllerAop(action = "查询展陈列表")
    @RequestMapping("/getListExhibition")
    public JSONObject getListExhibition(String name, String planTime, @RequestParam(defaultValue = "1") String orderBy,@RequestParam String module,
                                        @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {
        return exhibitionService.getListExhibition(name, planTime, orderBy, currentPage, size,module);
    }

    /**
     *  批量删除展陈 ids
     * @param
     * @return
     */
    @ControllerAop(action = "批量删除展陈")
    @RequestMapping("/updateExhibByIds")
    public JsonResult updateExhibByIds(@RequestParam(value="ids")String[] ids) {
        List<String> asList = Arrays.asList(ids);
        return exhibitionService.updateExhibByIds(asList);
    }

}

package com.tj720.controller.postbooking;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.service.PostBookingService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class PostBookingController{



    @Autowired
    private PostBookingService  postBookingService;


    /**
     * @Author  xa
     * @Description  查询预约管理数据
     * @return
     */
    @ControllerAop(action = "查询预约管理数据")
    @RequestMapping("/getBookingList")
    @ResponseBody
    public LayUiTableJson getBookingList(String bookingType, String contacts, String orderTime,
                String userId,@RequestParam(defaultValue = "1") String orderBy,@RequestParam(defaultValue = "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        userId = Tools.getUserId();
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult homeList = postBookingService.getBookingList(bookingType,contacts,orderTime, null,orderBy,pageInfo);
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
     * 根据id获取预约管理（id）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据id获取预约管理")
    @RequestMapping("/getBookingById")
    @ResponseBody
    public JsonResult selectByPrimaryKey( @RequestParam("id")String id, @RequestParam("userId")String userId) throws
            Exception {
        return  postBookingService.selectByPrimaryKey(id,userId);
    }





}

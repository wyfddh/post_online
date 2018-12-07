package com.tj720.controller.webInterface;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.PostBookingService;
import com.tj720.utils.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xa
 * @Date: 2018/11/8
 */
@RestController
@RequestMapping("/webbooking")
public class WebBookingController{

    @Autowired
    private PostBookingService  postBookingService;


    /**
     * @Author  xa
     * @Description  查询预约管理数据
     * @return
     */
    @RequestMapping("/getWebBookingList")
    public JsonResult getBookingList(@Param("userId") String userId, @RequestParam(defaultValue = "1")
            String orderBy,@RequestParam(defaultValue = "1") Integer
            currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult homeList = postBookingService.getBookingList(null,null,null,userId,orderBy,pageInfo);
        return  homeList;

    }






}

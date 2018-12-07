package com.tj720.controller.webInterface;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.PubUserService;
import com.tj720.service.WebInterfaceService;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xa
 * @Date: 2018/11/8
 */
@RestController
@RequestMapping("/webuserinfo")
public class WebUserInfoController{

    @Autowired
    private PubUserService  pubUserService;


    @Autowired
    private WebInterfaceService  webCollectService;


    /**
     * 根据用户id查询收藏信息（userId）
     * @return
     * @throws  Exception
     */
   @RequestMapping("/getUserDtoByUid")

   public JsonResult getUserDtoByUid(String userId, @RequestParam(defaultValue = "0") Integer status,
                                     @RequestParam
           (defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "3") Integer size)throws Exception{
       Map<String, Object> data = new HashMap<>();
       Page page = new Page();
       page.setCurrentPage(currentPage);
       page.setSize(2);
       try{
           //头部用户信息
           JsonResult  userDtoData = pubUserService.getUserDtoByUid(userId);
           data.put("userDtoData",userDtoData.getData());

           //我的策展
           JsonResult curatorData = webCollectService.getPublicCuratorList(userId,status,page);
           data.put("curatorData",curatorData.getData());


           //藏品信息
           JsonResult collectsData = pubUserService.getCollectsByUserId(userId, "2", currentPage, size);
           data.put("collectsData",collectsData.getData());
           //主题信息
           JsonResult themeData = pubUserService.getThemeByUserId(userId, "1", currentPage, size);
           data.put("themeData",themeData.getData());

           return  new JsonResult(1,data);
       }catch(Exception e){
           e.printStackTrace();
           return  new JsonResult(0,e.getMessage());
       }
   }











}

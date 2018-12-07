package com.tj720.controller.webInterface;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.CollectService;
import com.tj720.service.PostThemeShowService;
import com.tj720.service.PostWebCollectService;
import com.tj720.service.PubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xa
 * @Date: 2018/11/7
 */
@RestController
@RequestMapping("/collectionrelation")
public class CollectRelationController{

    @Autowired
    private PubUserService  pubUserService;


    @Autowired
    private  CollectService  collectService;

    /**
     * 根据用户获取藏品信息（userId）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getCollectsByUserId")
    public JsonResult getCollectsByUserId(String userId,@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return  pubUserService.getCollectsByUserId(userId,"2",currentPage,size);

    }



    /**
     * 根据用户获取主题信息（userId）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getThemeByUserId")
    public JsonResult getThemeByUserId(String userId,@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return  pubUserService.getThemeByUserId(userId,"1",currentPage,size);
    }


    /**
     * //根据用户id查询是否收藏（userId）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getCollectdcyUid")
    public JsonResult getCollectdcyUid(String userId, String type,@RequestParam
            (defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "3") Integer size){
        return   collectService.getCollectdcByUid(userId,type,currentPage,size);
    }





}

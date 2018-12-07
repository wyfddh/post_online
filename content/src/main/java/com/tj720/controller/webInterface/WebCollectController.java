package com.tj720.controller.webInterface;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.postwebcollect.PostWebCollect;
import com.tj720.service.PostWebCollectService;
import com.tj720.service.PubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xa
 * @Date: 2018/11/8
 */
@RestController
@RequestMapping("/webcollect")
public class WebCollectController{

    @Autowired
    private PostWebCollectService  postWebCollectService;


    /**
     * 添加收藏和取消收藏（userId、cid、type）
     * @return   根据传入hasCollected判断(1为收藏  0为取消收藏)
     * @throws  Exception
     */
    @RequestMapping("/insertWebCollect")
    public JsonResult insertWebCollect(String userId,String cid,String type,String hasCollected) throws Exception{
        return  postWebCollectService.updateCollectionStatus(userId,cid,type,hasCollected);
    }








}

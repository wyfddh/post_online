package com.tj720.service;/**
 * Created by MyPC on 2018/11/1.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.postwebcollect.PostWebCollect;
import com.tj720.model.common.pubuser.PubUser;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: PubUserService
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/11/1
 * @Version: 1.0
 **/

@Service
public interface PostWebCollectService{

    /**
     * 添加收藏
     * @param      //Web端接口
     * @return
     */
    JsonResult  insertSelective(String userId,String cid,String type,String hasCollected);

    /**
     * 添加收藏/取消收藏
     * @param userId 用户id
     * @param cid 藏品id
     * @param type 类型
     * @param hasCollected 状态
     * @return
     */
    JsonResult updateCollectionStatus(String userId,String cid,String type,String hasCollected);

    /**
     * 根据用户id和cid查询出收藏类
     * @param
     * @return
     */
    //JsonResult getWebCollect(String userId,String cid);







}



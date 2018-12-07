package com.tj720.service;/**
 * Created by MyPC on 2018/11/1.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.themeshow.PostThemeShow;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: PubUserService
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/11/1
 * @Version: 1.0
 **/

@Service
public interface PubUserService {

    /**
     * 新增公共用戶
     * @param pubUser 公共用戶Dto包括 公共用戶-展厅-公共用戶-影视数据 type==“add” 是否包括公共用戶主表
     * @return
     */
    public JsonResult addPubUser(PubUser pubUser);


    /**
     * 修改公共用戶
     * @param pubUser 公共用戶Dto包括 公共用戶-展厅-公共用戶-影视数据
     * @return
     */
    public JsonResult updatePubUser(PubUser pubUser);

    /**
     * 修改推荐状态
     * @param userStatus  用户冻结状态 0:冻结 1:取消冻结
     ** @param id      主键id
     * @return
     */
    JsonResult  modifyUserType(String userStatus, String id);






    /**
     * 查一个公共用戶
     * @param id 公共用戶id
     * @return
     */
    public JsonResult getOnePubUser(String id);

    /**
     * 查列表公共用戶
     * @return
     */
    public JSONObject getListPubUser(String name, String phone, String createTime, String orderBy, Integer
            currentPage, Integer size);

    /**
     * 根据id批量删除公共用戶
     * @param ids 公共用戶id
     * @return
     */
    public JsonResult updatePubUserByIds(List<String> ids);

    /**
     * 根据id删除公共用戶
     * @param id 公共用戶id
     * @return
     */
    public JsonResult deletePubUserById(String id);




    //Web端接口
    //根据用户id关联查询藏品
    JsonResult  getCollectsByUserId(String userId,String type,Integer currentPage, Integer size);

    //根据用户id关联查询主题展
    JsonResult   getThemeByUserId(String userId,String type,Integer currentPage, Integer size);


    /**
     * 查一个公共用戶
     * @param id 公共用戶id
     * @return
     */
    //根据用户id查询
    //getUserDtoByUid(String  userId);


    /**
     * 个人中心用户头部信息
     * @param
     * @return
     */
    JsonResult  getUserDtoByUid(String  userId);


    List<PubUser> getListByPhone(String phone);
}



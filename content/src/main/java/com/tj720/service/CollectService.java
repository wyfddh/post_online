package com.tj720.service;/**
 * Created by MyPC on 2018/10/24.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.themeshow.PostThemeShow;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: CollectService
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/24
 * @Version: 1.0
 **/
@Service
public interface CollectService {

    /**
     * 新增藏品
     * @param collect 藏品Dto包括 藏品-展厅-藏品-影视数据 type==“add” 是否包括藏品主表
     * @return
     */
    public JsonResult addCollect(Collect collect);


    /**
     * 修改藏品
     * @param collect 藏品Dto包括 藏品-展厅-藏品-影视数据
     * @return
     */
    public JsonResult updateCollect(Collect collect);

    /**
     * 修改推荐状态
     * @param collect 主键
     * @return
     */
    public JsonResult updateCollectType(Collect collect);

    /**
     * 查一个藏品
     * @param id 藏品id
     * @return
     */
    public JsonResult getOneCollect(String id);

    /**
     * 查列表藏品
     * @return
     */
    public JSONObject getListCollect(String name, String typeId, String sonTypeId, String orderBy, Integer currentPage,
                                     Integer size);

    /**
     * 根据id批量删除藏品
     * @param ids 藏品id
     * @return
     */
    public JsonResult updateCollectByIds(List<String> ids);

    /**
     * 根据id删除藏品
     * @param id 藏品id
     * @return
     */
    public JsonResult deleteCollectById(String id);


    /**
     * 根据父id查询业务字典列表
     *
     * @param pid 父id
     * @return
     */
    public  JsonResult getSysDictListByPid(String pid);



    /**
     * 典藏精选
     */
    Integer  CountDcjx();



    public  JsonResult selectListByParams(String parmas);

    public  JsonResult updateAttachmentByid(Attachment attachment);

    JSONObject selectBaiDuListByParam(String typeId);


    //根据用户id查询是否收藏
    JsonResult   getCollectdcByUid(String userId,String type,Integer currentPage, Integer size);


    //首页典藏精选
    JsonResult  selectIndexCollectList();



    /**
     * 根据ids查询藏品集合
     * @param ids
     * @param typeId
     * @param sonTypeId
     * @return
     */
    JsonResult selectListByIds(String ids, String typeId, String sonTypeId);

    /**
     * 根据typeId和sonTypeId查询藏品列表
     * @param collect
     * @return
     */
    JsonResult selectListByTypeAndSonType(Collect collect);
}

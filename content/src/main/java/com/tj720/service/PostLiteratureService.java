package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.Attachment;
import com.tj720.model.literature.PostLiterature;
import com.tj720.model.literature.PostLiteratureWithBLOBs;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @author wyf
 * @date 2018/10/16 10:16
 **/
@Service
public interface PostLiteratureService {

    /**
     * @author wyf
     * @description  获取列表数据
     * @date  2018/10/16 11:20
     * @param key   搜索条件
     * @param dataType  文献类型
     * @param status    文献状态
     * @param orderBy   排序
     * @param open
     *@param currentPage   当前页
     * @param size         每页显示数   @return com.tj720.controller.framework.JsonResult
     */
    JSONObject postLiteratureList(String key, String dataType, String status,String inventoryState, String orderBy, String open, Integer currentPage,
            Integer size,String module);

    JsonResult postLiteratureSave(PostLiteratureWithBLOBs postLiteratureWithBLOBs);

    JsonResult modifyState(String id, String status, String setting);

    JsonResult batchExport(String[] arr);

    JsonResult batchImport(HttpServletRequest request, MultipartFile file);

    void downDemo(String fileName, HttpServletRequest request, HttpServletResponse response);

    JsonResult batchSetting(String[] arr, String setting);

    JsonResult batchRecall(String[] arr);

    JsonResult getSelectData();

    /**
     * 获取文献类型下拉
     * @return
     */
    JsonResult getLiteratureType();

    /**
     * 新增文献
     * @param literature
     * @return
     */
    JsonResult saveLiteratureType(PostLiterature literature);

    /**
     * @author wyf
     * @description  获取文献名集合
     * @date  2018/11/16 11:33
     * @return java.util.List<com.tj720.model.literature.PostLiterature>
     */
    List<PostLiterature> getLiteratureName();

    /**
     * 查询图表1
     * @param condition 查询条件
     * @param type 查询类型(1：采集:/2：查询)
     * @return
     */
    public JsonResult getLiteratureCjReport1(HashMap<String,Object> condition, String type);
    /**
     * 查询图表2
     * @param condition 查询条件
     * @param type 查询类型(1：采集:/2：查询)
     * @return
     */
    public JsonResult getLiteratureCjReport2(HashMap<String,Object> condition, String type);


    /**
     * 查询文献采集统计（交叉表）
     * @param condition
     * @return
     */
    JsonResult getVideoCjTable(HashMap<String,Object> condition);
    /**
     * 查询文献采集统计（饼图）
     * @param condition
     * @return
     */
    JsonResult getVideoCjPie(HashMap<String,Object> condition);
    /**
     * 查询文献采集统计（折线图）
     * @param condition
     * @return
     */
    JsonResult getVideoCjLine(HashMap<String,Object> condition);

    /**
     * 文献分类统计
     * @param condition
     * @return
     */
    JsonResult getVideoCjBarPlus(HashMap<String,Object> condition);
    /**
     * 查询文献查询统计（交叉表）
     * @param condition
     * @return
     */
    JsonResult getVideoCxTable(HashMap<String,Object> condition);
    /**
     * 查询文献查询统计（饼图）
     * @param condition
     * @return
     */
    JsonResult getVideoCxPie(HashMap<String,Object> condition);
    /**
     * 查询文献查询统计（折线图）
     * @param condition
     * @return
     */
    JsonResult getVideoCxLine(HashMap<String,Object> condition);


    /**
     * 查询文献采集统计
     * @return
     */
    public JsonResult getVideoCjCount(String module);

    /**
     * 查询文献查询统计
     * @return
     */
    public JsonResult getVideoCxCount(String module);

    /**
     * 查询待办
     * @param currentUserId 用户id
     * @return
     */
    public JsonResult getUndoTask(String currentUserId);

    /**
     * 查询已办
     * @param currentUserId 用户id
     * @return
     */
    public JsonResult getDoneTask(String currentUserId);

    /**
     * 查询已办结
     * @param currentUserId 用户id
     * @return
     */
    public JsonResult getFinishTask(String currentUserId);

    /**
     * 根据id查询文献详情
     * @param id
     * @return
     */
    public JsonResult getLiteratureById(String id);

    /**
     * 根据id查询文献blob
     * @param id
     * @return
     */
    JsonResult selectByPrimaryKey(String id);

    void update(PostLiteratureWithBLOBs postLiterature);

    List<PostLiterature>  getLiteratureByIdList(List<String> keys);

    List<Attachment> getPostLiteratureAttachment(String postLiteratureId);
}

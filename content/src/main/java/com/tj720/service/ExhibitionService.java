package com.tj720.service;/**
 * Created by MyPC on 2018/10/15.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.exhibition.ExhibitionDto;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ExhibitionService
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/15
 * @Version: 1.0
 **/
@Service
public interface ExhibitionService {

    /**
     * 新增展陈
     * @param exhibitionDto 展陈Dto包括 展陈-展厅-藏品-影视数据 type==“add” 是否包括展陈主表
     * @return
     */
    public JsonResult addExhibition(ExhibitionDto exhibitionDto, String type, String picids);

    /**
     * 假删除展陈
     * @param exhibitionDto 展陈Dto包括 展陈-展厅-藏品-影视数据
     * @return
     */
    public JsonResult deleteExhibition(ExhibitionDto exhibitionDto);

    /**
     * 假删除展陈
     * @param id 展陈id type == "exhib",前端是否有数据
     * @return
     */
    public JsonResult deleteExhibitionById(String id, String type);


    /**
     * 修改展陈
     * @param exhibitionDto 展陈Dto包括 展陈-展厅-藏品-影视数据
     * @return
     */
    public JsonResult updateExhibition(ExhibitionDto exhibitionDto,String picids);

    /**
     * 查一个展陈
     * @param exhibId 展陈id
     * @return
     */
    public JsonResult getOneExhibition(String exhibId);

    /**
     * 查列表展陈
     * @return
     */
    public JSONObject getListExhibition(String name, String planTime, String orderBy, Integer currentPage, Integer
            size,String module);

    /**
     * 根据id批量删除展陈
     * @param ids 展陈id
     * @return
     */
    public JsonResult updateExhibByIds(List<String> ids);

    /**
     * 根据id删除展陈
     * @param id
     * @return
     */
    JsonResult deleteExhibColleById(String id);
}

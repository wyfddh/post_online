package com.tj720.service;/**
 * Created by MyPC on 2018/10/26.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.education.Education;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: EducationService
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/26
 * @Version: 1.0
 **/
@Service
public interface EducationService {

    /**
     * 新增教育活动
     * @param education 教育活动Dto包括 教育活动-展厅-教育活动-影视数据 type==“add” 是否包括教育活动主表
     * @return
     */
    public JsonResult addEducation(Education education);


    /**
     * 修改教育活动
     * @param education 教育活动Dto包括 教育活动-展厅-教育活动-影视数据
     * @return
     */
    public JsonResult updateEducation(Education education);

    /**
     * 修改推荐状态
     * @param education 主键
     * @return
     */
    public JsonResult updateEducationType(Education education);

    /**
     * 查一个教育活动
     * @param id 教育活动id
     * @return
     */
    public JsonResult getOneEducation(String id);

    /**
     * 查列表教育活动
     * @return
     */
    public JSONObject getListEducation(String title, String startTime, String orderBy, Integer
            currentPage, Integer size);

    /**
     * 根据id批量删除教育活动
     * @param ids 教育活动id
     * @return
     */
    public JsonResult updateEducationByIds(List<String> ids);

    /**
     * 根据id删除教育活动
     * @param id 教育活动id
     * @return
     */
    public JsonResult deleteEducationById(String id);



}

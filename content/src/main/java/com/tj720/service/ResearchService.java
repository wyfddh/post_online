package com.tj720.service;/**
 * Created by MyPC on 2018/10/26.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.research.Research;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ResearchService
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/26
 * @Version: 1.0
 **/
@Service
public interface ResearchService {

        /**
         * 新增学术研究
         * @param research 学术研究Dto包括 学术研究-展厅-学术研究-影视数据 type==“add” 是否包括学术研究主表
         * @return
         */
        public JsonResult addResearch(Research research);


        /**
         * 修改学术研究
         * @param research 学术研究Dto包括 学术研究-展厅-学术研究-影视数据
         * @return
         */
        public JsonResult updateResearch(Research research);

        /**
         * 修改推荐状态
         * @param research 主键
         * @return
         */
        public JsonResult updateResearchType(Research research);

        /**
         * 查一个学术研究
         * @param id 学术研究id
         * @return
         */
        public JsonResult getOneResearch(String id);

        /**
         * 查列表学术研究
         * @return
         */
        public JSONObject getListResearch(String startTime,String orderBy, Integer
                currentPage, Integer size);

        /**
         * 根据id批量删除学术研究
         * @param ids 学术研究id
         * @return
         */
        public JsonResult updateResearchByIds(List<String> ids);

        /**
         * 根据id删除学术研究
         * @param id 学术研究id
         * @return
         */
        public JsonResult deleteResearchById(String id);



}

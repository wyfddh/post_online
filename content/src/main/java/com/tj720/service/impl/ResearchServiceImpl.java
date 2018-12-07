package com.tj720.service.impl;/**
 * Created by MyPC on 2018/10/26.
 */

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.StringUtil;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.ResearchMapper;
import com.tj720.model.common.research.Research;
import com.tj720.service.ResearchService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ResearchServiceImpl
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/26
 * @Version: 1.0
 **/
@Service
public class ResearchServiceImpl implements ResearchService {




    private Research setTimeInfo(Research research, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String userId = Tools.getUserId();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        research.setUpdateTime(date);
        research.setUpdater(userId);
        // 0代表 没有创建的用户，录入创建人和创建时间
        if ("0".equals(type)) {
            research.setId(IdUtils.getIncreaseIdByNanoTime());
            research.setCreateTime(date);
            research.setCreator(userId);
            research.setIsdelete("0");
        }
        return research;
    }

    @Autowired
    ResearchMapper researchMapper;

    @Override
    public JsonResult addResearch(Research research) {
        try {
            research = setTimeInfo(research, "0");
            research.setCreateTime(new Date());
            int count = researchMapper.insertSelective(research);
            if (count > 0) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "41000013");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateResearch(Research research) {
        try {
            research = setTimeInfo(research, "1");
            int count = researchMapper.updateByPrimaryKeySelective(research);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "41000016");
            }
        } catch (Exception e) {
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult updateResearchType(Research research) {
        try {
            int count = researchMapper.updateByPrimaryKeySelective(research);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, null, "41000017");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }

    @Override
    public JsonResult getOneResearch(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return new JsonResult(0, "20000003");
            } else {
                Research research = researchMapper.selectByPrimaryKey(id);
                if (research.getId() != null) {
                    return new JsonResult(1, research);
                } else {
                    return new JsonResult(0, "20000003");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "20000003");
        }

    }

    @Override
    public JSONObject getListResearch(String startTime,String orderBy, Integer
            currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();

        try {
            String searchDateStart = "";
            if(StringUtil.isNotEmpty(startTime)) {
                String[] searchDate = {};
                searchDate = startTime.split("~");
                if(null != searchDate && searchDate.length > 0) {
                    searchDateStart = null != searchDate[0] ? searchDate[0].trim() : "";
                }
                map.put("startTime",searchDateStart);
            }

            Page page = new Page();
           /* if (StringUtils.isNotBlank(startTime)) {
                map.put("startTime", startTime);
            }*/

            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy", orderBy);
            }
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Integer count = researchMapper.selectCountByResearch(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            List<Research> listResearch = researchMapper.selectListByResearch(map);
            String jsonString = JSON.toJSONString(listResearch);
            jsonObject.put("code", 0);
            jsonObject.put("success", 1);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);

        } catch (NumberFormatException e) {
            jsonObject.put("msg", "");
            jsonObject.put("count", null);
            jsonObject.put("data", null);
            jsonObject.put("error", e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public JsonResult updateResearchByIds(List<String> ids) {
        try {
            Integer size = ids.size();
            Integer i = researchMapper.updateResearchByIds(ids);
            if (size == i) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "41000014");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }

    }

    @Override
    public JsonResult deleteResearchById(String id) {
        try {
            Research research = new Research();
            research.setIsdelete("1");
            research.setId(id);
            int count = researchMapper.updateByPrimaryKeySelective(research);
            if (count == 1) {
                return new JsonResult(1, null);
            } else {
                return new JsonResult(0, "41000018");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, null, "20000003");
        }
    }


}

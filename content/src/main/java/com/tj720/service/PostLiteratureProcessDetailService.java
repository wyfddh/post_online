package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author wyf
 * @date 2018/11/19 10:19
 **/
@Service
public interface PostLiteratureProcessDetailService {

    JSONObject processDetailList(String id, Integer currentPage, Integer size);

    /**
     * 根据流程id查询详情列表
     * @param processId
     * @return
     */
    JsonResult getProcessDetailByProcess(String processId);

}

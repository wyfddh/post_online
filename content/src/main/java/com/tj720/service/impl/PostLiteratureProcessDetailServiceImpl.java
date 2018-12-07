package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostLiteratureProcessDetailMapper;
import com.tj720.model.literature.PostLiteratureProcessDetail;
import com.tj720.service.PostLiteratureProcessDetailService;
import com.tj720.utils.Page;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wyf
 * @date 2018/11/19 10:19
 **/
@Service("PostLiteratureProcessDetailService")
public class PostLiteratureProcessDetailServiceImpl implements PostLiteratureProcessDetailService {

    @Autowired
    private PostLiteratureProcessDetailMapper processDetailMapper;

    @Override
    public JSONObject processDetailList(String id, Integer currentPage, Integer size) {

        //分页对象
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);
        JSONObject jsonObject = new JSONObject();
        String jsonString = null;
        if (StringUtils.isNotBlank(id)) {
            Integer count = processDetailMapper.countProcessDetailList(id);

            page.setAllRow(count);
            Integer start = page.getStart();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",id);
            map.put("start", start);
            map.put("end", size);

            List<PostLiteratureProcessDetail> list = processDetailMapper.getProcessDetailList(map);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (PostLiteratureProcessDetail processDetail : list) {
                if (processDetail.getCreateTime() != null) {
                    processDetail.setCreateTimeStr(format.format(processDetail.getCreateTime()));
                } else {
                    processDetail.setCreateTimeStr("");
                }

                if ("1".equals(processDetail.getProcessOperation())) {
                    processDetail.setProcessOperationName("通过");
                } else if ("2".equals(processDetail.getProcessOperation())) {
                    processDetail.setProcessOperationName("驳回");
                } else if ("3".equals(processDetail.getProcessOperation())) {
                    processDetail.setProcessOperationName("通过并提交审批");
                }
            }
            jsonString = JSON.toJSONString(list);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);

        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", 0);
            jsonObject.put("data", jsonString);
        }
        return jsonObject;
    }

    /**
     * 根据流程id查询详情列表
     *
     * @param processId
     * @return
     */
    @Override
    public JsonResult getProcessDetailByProcess(String processId) {
        try {
            List<PostLiteratureProcessDetail> processDetails = processDetailMapper.getProcessDetailByProcess(processId);
            return new JsonResult(1,processDetails);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("000001");
        }
    }
}

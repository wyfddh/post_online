package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.literature.PostLiteratureProcess;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wyf
 * @date 2018/10/25 16:39
 **/
@Service
public interface PostLiteratureProcessService {

    JsonResult getApproveList();

    @Transactional
    JsonResult postLiteratureProcessSave(PostLiteratureProcess postLiteratureProcess);

    JSONObject postLiteratureProcessList(String key, String educationNextment, String approveStatus, String orderBy,
            Integer currentPage, Integer size,String module);

    JsonResult getLiteratureName();
    @Transactional
    JsonResult approveSave(PostLiteratureProcess postLiteratureProcess);

    JSONObject borrowingList(String key, String educationNextment, String borrowStatus, String orderBy, Integer currentPage,
            Integer size,String module);

    JsonResult modifyState(String id, String status);

    /**
     * 超期扫描任务
     * @return
     */
    JsonResult overdueTask();

    /**
     * 根据id查询文献流程
     * @param id
     * @return
     */
    JsonResult getLiteratureProcessById(String id);


    public JsonResult getWfActionByNotice(String id);

    JsonResult savePaperBorrowing(PostLiteratureProcess postLiteratureProcess);

    JsonResult batchApprove(String[] arr, String setting, String approveName, String preApproveId, String approveId,
            String approveRemark);
}

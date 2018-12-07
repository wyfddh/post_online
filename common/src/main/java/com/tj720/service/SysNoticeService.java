package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.system.userlog.SysNotice;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LC on 2018/10/30.
 */
@Service
public interface SysNoticeService {

    JsonResult sendNotice(SysNotice SysNotice);
    /**
     *
     * @param receiver 通知接收人
     * @param page 页面
     * @param action 触发操作
     * @param send_to 发送对象
     * @param content 通知内容
     * @param other1 流程id
     * @param userId 发送人
     * @return
     */
    JsonResult sendNoticePlus(String receiver, String page,String action,String send_to,String content,String other1,String userId);

    JsonResult updateNotice(SysNotice SysNotice);

    /**
     * 更新通知状态为已读
     * @param id
     * @param userId
     * @return
     */
    JsonResult updateNoticePlus(String id,String userId);

    JsonResult getNoticeList(String userId);

    /**
     * 查询通知
     * @param userId
     * @param status
     * @return
     */
    JsonResult getNoticeListPlus(String userId,String status,String type);
}

package com.tj720.controller.dict;

import com.tj720.controller.framework.JsonResult;
import com.tj720.service.SysNoticeService;
import com.tj720.model.common.system.userlog.SysNotice;
import com.tj720.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: LC
 * @Date: 2018/10/30 10:00
 */
@RestController
@RequestMapping("/sys/notice")
public class NoticeController {
    @Autowired
    SysNoticeService sysNoticeSevice;


    /**
     * 发消息功能
     * @param receiver 通知接收人 page 页面 action 触发操作 send_to 发送对象 content 通知内容  remark 备注
     * @return
     */
    @RequestMapping("/sendNotice")
    public JsonResult sendNotice(String receiver,String page,String action,String send_to,String content,String other1){
        SysNotice notice = new SysNotice();
        notice.setReceiver(receiver);
        notice.setPage(page);
        notice.setAction(action);
        notice.setSendTo(send_to);
        notice.setContent(content);
        notice.setRemark(other1);
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
//        String userId = Tools.getUserId();
        notice.setCreator(Tools.getUserId());
        notice.setUpdater(Tools.getUserId());
        notice.setStatus(0);
        JsonResult jsonResult = sysNoticeSevice.sendNotice(notice);
        return jsonResult;
    }

    /**
     * 查阅消息，修改状态已读
     * @param id 消息Id
     * @return
     */
    @RequestMapping("/updateNotice")
    public JsonResult updateNotice(String id){
        SysNotice notice = new SysNotice();
        notice.setId(id);
        notice.setUpdateTime(new Date());
//        String userId = Tools.getUserId();
        notice.setUpdater(Tools.getUserId());
        notice.setStatus(1);
        JsonResult jsonResult = sysNoticeSevice.updateNotice(notice);
        return jsonResult;
    }


    /**
     * 消息列表
     * @param
     * @return
     */
    @RequestMapping("/getNoticeList")
    public JsonResult getNoticeList(){
        SysNotice notice = new SysNotice();
//        String userId = Tools.getUserId();
        JsonResult jsonResult = sysNoticeSevice.getNoticeList(Tools.getUserId());
        return jsonResult;
    }

    /**
     * 查阅消息，修改状态已读
     * @param id 消息Id
     * @return
     */
    @RequestMapping("/updateNoticePlus")
    public JsonResult updateNoticePlus(@RequestParam String id,@RequestParam String userId){
        JsonResult jsonResult = sysNoticeSevice.updateNoticePlus(id,userId);
        return jsonResult;
    }


    /**
     * 消息列表
     * @param
     * @return
     */
    @RequestMapping("/getNoticeListPlus")
    public JsonResult getNoticeListPlus(@RequestParam String userId,@RequestParam String status,@RequestParam String type){
        JsonResult jsonResult = sysNoticeSevice.getNoticeListPlus(userId,status,type);
        return jsonResult;
    }
}

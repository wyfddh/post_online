package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.SysDictMapper;
import com.tj720.dao.SysNoticeMapper;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.system.userlog.SysNotice;
import com.tj720.service.SysDictSevice;
import com.tj720.service.SysNoticeService;
import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by LC on 2018/10/30.
 */
@Service("sysNoticeService")
public class SysNoticetServiceImpl implements SysNoticeService {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Override
    @Transactional
    public JsonResult sendNotice(SysNotice notice) {
        try {
            notice.setId(IdUtils.getIncreaseIdByNanoTime());
            int count =  sysNoticeMapper.insertSelective(notice);
            if (count>0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(0,null,"200204");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200204");
        }
    }

    @Override
    public JsonResult updateNotice(SysNotice notice) {
        try {
            int count =  sysNoticeMapper.updateByPrimaryKeySelective(notice);
            if (count>0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(0,null,"200205");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200205");
        }
    }

    @Override
    public JsonResult getNoticeList(String userId) {
        try {
            List<SysNotice> noticeList =  sysNoticeMapper.getNoticeList(userId);
            return new JsonResult(1,noticeList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200205");
        }
    }

    @Override
    public JsonResult sendNoticePlus(String receiver, String page, String action, String send_to, String content, String other1, String userId) {
        SysNotice notice = new SysNotice();
        notice.setReceiver(receiver);
        notice.setPage(page);
        notice.setAction(action);
        notice.setSendTo(send_to);
        notice.setContent(content);
        notice.setOther1(other1);
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
        notice.setCreator(userId);
        notice.setUpdater(userId);
        notice.setStatus(0);
        JsonResult jsonResult = sendNotice(notice);
        return jsonResult;
    }

    @Override
    public JsonResult updateNoticePlus(String id, String userId) {
        SysNotice notice = new SysNotice();
        notice.setId(id);
        notice.setUpdateTime(new Date());
        notice.setUpdater(userId);
        notice.setStatus(1);
        JsonResult jsonResult = updateNotice(notice);
        return jsonResult;
    }

    /**
     * 查询通知
     *
     * @param userId 用户id
     * @param status 通知状态
     * @return
     */
    @Override
    public JsonResult getNoticeListPlus(String userId, String status,String type) {
        try {
            List<SysNotice> noticeList =  sysNoticeMapper.getNoticeListPlus(userId,status,type);
            return new JsonResult(1,noticeList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200205");
        }
    }
}

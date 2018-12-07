package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostBookingManageMapper;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.service.PostBookingService;
import com.tj720.utils.DateFormartUtil;
import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: PostStampStoryServiceImpl
 * @Description: 预约管理实现类
 * @Author: xa
 * @Date: 2018/11/3
 * @Version: 1.0
 **/
@Service
public class PostBookingServiceImpl implements PostBookingService{
    private String userId = "sysadmin";
    
    @Autowired
    private PostBookingManageMapper postBookingManageMapper;

    @Override
    public JsonResult getBookingList(String bookingType, String contacts, String orderTime,String userId,String
            orderBy, Page page) throws Exception {
        try {
            int count = postBookingManageMapper.count(bookingType,contacts,orderTime,
                    userId,orderBy,page.getCurrentPage(), page.getSize());
            page.setAllRow(count);
            List<PostBookingManage> bookingList = postBookingManageMapper.getBookingList(bookingType, contacts, orderTime,
                    null,orderBy, page.getStart(), page.getSize());
            for (PostBookingManage postBookingManage  : bookingList){
                //时间处理
                Date createTime1 = postBookingManage.getCreateTime();
                if(createTime1!=null){
                    String orderTime1 = postBookingManage.getOrderTime();
                    if(StringUtils.isNotBlank(orderTime1)){
                        String orderstr = orderTime1.substring(0, 10);
                        postBookingManage.setOrderTimeStr(orderstr);
                    }
                    String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                    postBookingManage.setCreateTimeStr(timeStr);
                    String[] times= timeStr.split("-");
                    postBookingManage.setCreateTimeYear(times[0]);
                    postBookingManage.setCreateTimeMonthDay(times[1]+"-"+times[2]);
                }

            }
            return new JsonResult(1,bookingList,page);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    /**
     * 根据用户id查询预约列表
     *
     * @param userId 用户id
     * @param page
     * @return
     */
    @Override
    public JsonResult getBookingList(String userId, Page page) {
        try {
            int count = postBookingManageMapper.countByUserId(userId);
            page.setAllRow(count);
            List<PostBookingManage> bookingList = postBookingManageMapper.getBookingListByUserId(userId,
                    page.getStart(), page.getSize());
            for (PostBookingManage postBookingManage  : bookingList){
                //时间处理
                Date createTime1 = postBookingManage.getCreateTime();
                String orderTime1 = postBookingManage.getOrderTime();
                String orderstr = orderTime1.substring(0, 10);
                postBookingManage.setOrderTimeStr(orderstr);
                String timeStr = DateFormartUtil.stampToDateDevelop(createTime1);
                postBookingManage.setCreateTimeStr(timeStr);
                String[] times= timeStr.split("-");
                postBookingManage.setCreateTimeYear(times[0]);
                postBookingManage.setCreateTimeMonthDay(times[1]+"-"+times[2]);
            }
            return new JsonResult(1,bookingList,page);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"111116");
        }
    }

    @Override
    public JsonResult selectByPrimaryKey(String id,String userId) throws Exception {
        try {
            if(StringUtils.isBlank(id)){
                return new JsonResult(0,"参数错误");
            }
            PostBookingManage postBookingManage = postBookingManageMapper.selectByPrimaryKey(id,userId);
            return new JsonResult(1,postBookingManage);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }



    @Override
    public JsonResult insertSelective(PostBookingManage record){
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }
          /*  if(StringUtils.isBlank(record.getContacts())){
                return new JsonResult(0,"姓名不能为空");
            }*/
            record.setId(IdUtils.getIncreaseIdByNanoTime());
            record.setContacts("guest");
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            record.setCreator(userId);
            record.setUserId(userId);
            record.setDataState("1");
            int count =  postBookingManageMapper.insertSelective(record);
            if (count >0){
                record.setOrderState("预约成功");
                return new JsonResult(1,null);
            }
            else{
                return new JsonResult(0,null,"200006");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"添加预约管理失败");
        }
    }



    @Override
    public JsonResult updateByPrimaryKeySelective(PostBookingManage record){
        try{
            if (record == null){
                return new JsonResult(0,"参数有误");
            }
            if(StringUtils.isNotBlank(record.getId())){
                record.setUpdater("sysadmin");
                record.setUpdateTime(new Date());
                record.setUserId(userId);
                int count =   postBookingManageMapper.updateByPrimaryKey(record);
                if (count >0){
                    return new JsonResult(1,null);
                }
                else{
                    return new JsonResult(0,null,"200006");
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return new JsonResult(0,"修改预约管理失败");
        }
        return  null;
    }


}

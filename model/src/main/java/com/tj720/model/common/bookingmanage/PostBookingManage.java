package com.tj720.model.common.bookingmanage;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * post_booking_manage
 * @xa
 */
//@Data
public class PostBookingManage implements Serializable{


    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private String id;

    /**
     * 预约类型 （1：个人预约，2: 团体预约）
     */
    private String bookingType;

    /**
     * 参观人数
     */
    private String amount;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactsPhone;

    /**
     * 预约时间
     */
    private String  orderTime;


    /**
     * 用户id
     */
    private String  userId;

    /**
     * 数据状态（0：已删除，1:正常）
     */
    private String dataState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 最后修改人
     */
    private String updater;

    /**
     * 预约状态
     */
    private String orderState;

    /**
     * 备用字段2
     */
    private String other2;

    /**
     * 备用字段3
     */
    private String other3;


    @Transient
    private String createTimeYear;     //创建时间转换(年)


    @Transient
    private String createTimeMonthDay;     //创建时间转换(月日)



    @Transient
    private String createTimeStr;     //创建时间转换(年月日时分)


    @Transient
    private String updateTimeStr;     //修改时间转换(年月日时分)


    @Transient
    private String orderTimeStr;     //预约时间转换(年月日时分)



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }


    public String getCreateTimeYear() {
        return createTimeYear;
    }

    public void setCreateTimeYear(String createTimeYear) {
        this.createTimeYear = createTimeYear;
    }

    public String getCreateTimeMonthDay() {
        return createTimeMonthDay;
    }

    public void setCreateTimeMonthDay(String createTimeMonthDay) {
        this.createTimeMonthDay = createTimeMonthDay;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
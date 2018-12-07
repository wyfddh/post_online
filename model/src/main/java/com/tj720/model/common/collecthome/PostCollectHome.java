package com.tj720.model.common.collecthome;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * post_collect_home
 * @author   xa
 */
public class PostCollectHome implements Serializable{


    private String  rowNum;   //当前数据行号


    /**
     * 主键
     */
    private String id;

    /**
     * 图片id，以逗号隔开
     */
    private String datumIds;

    /**
     * 集邮资讯主题
     */
    private String collectHomeTheme;

    /**
     * 集邮资讯内容
     */
    private String informationContent;

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
     * 备用字段1
     */
    private String other1;

    /**
     * 备用字段2
     */
    private String other2;

    /**
     * 备用字段3
     */
    private String other3;

    private static final long serialVersionUID = 1L;


    @Transient
    private String createTimeYear;     //创建时间转换(年)


    @Transient
    private String createTimeMonthDay;     //创建时间转换(月日)



    @Transient
    private String createTimeStr;     //创建时间转换(年月日时分)


    @Transient
    private String updateTimeStr;     //修改时间转换(年月日时分)



    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatumIds() {
        return datumIds;
    }

    public void setDatumIds(String datumIds) {
        this.datumIds = datumIds;
    }

    public String getCollectHomeTheme() {
        return collectHomeTheme;
    }

    public void setCollectHomeTheme(String collectHomeTheme) {
        this.collectHomeTheme = collectHomeTheme;
    }

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
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

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
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
}
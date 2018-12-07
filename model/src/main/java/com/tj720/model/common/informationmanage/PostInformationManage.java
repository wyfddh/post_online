package com.tj720.model.common.informationmanage;

import com.tj720.model.common.AppCommonVO;
import com.tj720.model.common.Attachment;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * post_stamp_story
 * @author   xa
 */
//@Data
public class PostInformationManage extends AppCommonVO implements Serializable{

    private static final long serialVersionUID = 1L;

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
     * 资讯主题
     */
    private String informationTheme;

    /**
     * 资讯类型
     */
    private String informationType;

    /**
     * 资讯内容
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
     * 创建时间(app)
     */
    private Date createAppTime;

    @Transient
    private String createTimeYear;     //创建时间转换(年)


    @Transient
    private String createTimeMonthDay;     //创建时间转换(月日)



    @Transient
    private String createTimeStr;     //创建时间转换(年月日时分)


    @Transient
    private String updateTimeStr;     //修改时间转换(年月日时分)




    //主图url
    private String mainPicUrl;
    //图片集合
    private List<Attachment> picList;

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

    public String getInformationTheme() {
        return informationTheme;
    }

    public void setInformationTheme(String informationTheme) {
        this.informationTheme = informationTheme;
    }

    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType;
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

    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    public List<Attachment> getPicList() {
        return picList;
    }

    public void setPicList(List<Attachment> picList) {
        this.picList = picList;
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
package com.tj720.model.common.interfacecollect;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接口藏品
 * @author 杜昶
 * @Date: 2018/11/16 12:09
 */
public class InterfaceCollect implements Serializable {
    private static final long serialVersionUID = -4145029013912905051L;

    private String id;

    private String exhibId;

    private String gsLength;

    private String source;

    private String totalregistrationno;

    private List<Map<String, String>> pictures;

    private String gsHeight;

    private String picUrl;

    private String culSize;

    private String classificationtypeName;

    private String culId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date culYear;

    private String culLevel;

    private String gsWidth;

    private String gsTextureName;

    private String culName;

    private String culCategory;

    private String entrywarehousedate;

    private String qualityunitName;

    private String gsTexture;

    private String extend25;

    private String classificationno;

    private String culCategoryName;

    private String quality;

    private String qualityunit;

    private String culremark;

    private String classificationtype;

    private String sourceName;

    private String culLevelName;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Integer status;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExhibId() {
        return exhibId;
    }

    public void setExhibId(String exhibId) {
        this.exhibId = exhibId;
    }

    public String getGsLength() {
        return gsLength;
    }

    public void setGsLength(String gsLength) {
        this.gsLength = gsLength;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTotalregistrationno() {
        return totalregistrationno;
    }

    public void setTotalregistrationno(String totalregistrationno) {
        this.totalregistrationno = totalregistrationno;
    }

    public List<Map<String, String>> getPictures() {
        return pictures;
    }

    public void setPictures(List<Map<String, String>> pictures) {
        this.pictures = pictures;
    }

    public String getGsHeight() {
        return gsHeight;
    }

    public void setGsHeight(String gsHeight) {
        this.gsHeight = gsHeight;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCulSize() {
        return culSize;
    }

    public void setCulSize(String culSize) {
        this.culSize = culSize;
    }

    public String getClassificationtypeName() {
        return classificationtypeName;
    }

    public void setClassificationtypeName(String classificationtypeName) {
        this.classificationtypeName = classificationtypeName;
    }

    public String getCulId() {
        return culId;
    }

    public void setCulId(String culId) {
        this.culId = culId;
    }

    public Date getCulYear() {
        return culYear;
    }

    public void setCulYear(Date culYear) {
        this.culYear = culYear;
    }

    public String getCulLevel() {
        return culLevel;
    }

    public void setCulLevel(String culLevel) {
        this.culLevel = culLevel;
    }

    public String getGsWidth() {
        return gsWidth;
    }

    public void setGsWidth(String gsWidth) {
        this.gsWidth = gsWidth;
    }

    public String getGsTextureName() {
        return gsTextureName;
    }

    public void setGsTextureName(String gsTextureName) {
        this.gsTextureName = gsTextureName;
    }

    public String getCulName() {
        return culName;
    }

    public void setCulName(String culName) {
        this.culName = culName;
    }

    public String getCulCategory() {
        return culCategory;
    }

    public void setCulCategory(String culCategory) {
        this.culCategory = culCategory;
    }

    public String getEntrywarehousedate() {
        return entrywarehousedate;
    }

    public void setEntrywarehousedate(String entrywarehousedate) {
        this.entrywarehousedate = entrywarehousedate;
    }

    public String getQualityunitName() {
        return qualityunitName;
    }

    public void setQualityunitName(String qualityunitName) {
        this.qualityunitName = qualityunitName;
    }

    public String getGsTexture() {
        return gsTexture;
    }

    public void setGsTexture(String gsTexture) {
        this.gsTexture = gsTexture;
    }

    public String getExtend25() {
        return extend25;
    }

    public void setExtend25(String extend25) {
        this.extend25 = extend25;
    }

    public String getClassificationno() {
        return classificationno;
    }

    public void setClassificationno(String classificationno) {
        this.classificationno = classificationno;
    }

    public String getCulCategoryName() {
        return culCategoryName;
    }

    public void setCulCategoryName(String culCategoryName) {
        this.culCategoryName = culCategoryName;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQualityunit() {
        return qualityunit;
    }

    public void setQualityunit(String qualityunit) {
        this.qualityunit = qualityunit;
    }

    public String getCulremark() {
        return culremark;
    }

    public void setCulremark(String culremark) {
        this.culremark = culremark;
    }

    public String getClassificationtype() {
        return classificationtype;
    }

    public void setClassificationtype(String classificationtype) {
        this.classificationtype = classificationtype;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCulLevelName() {
        return culLevelName;
    }

    public void setCulLevelName(String culLevelName) {
        this.culLevelName = culLevelName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

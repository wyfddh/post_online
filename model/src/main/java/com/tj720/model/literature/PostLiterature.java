package com.tj720.model.literature;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * post_literature
 * @author 
 */
public class PostLiterature implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 资料名称
     */
    private String dataName;

    /**
     * 资料类型
     */
    private String dataType;

    /**
     * ISBN统一书号
     */
    private String isbnNum;

    /**
     * 索书号
     */
    private String callNo;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 出版社
     */
    private String press;

    /**
     * 出版时间
     */
    private String publishingTime;

    /**
     * 入库人
     */
    private String warehouses;

    /**
     * 入库时间
     */
    private String warehousingTime;

    /**
     * 库存状态
     */
    private String inventoryState;

    /**
     * 作者
     */
    private String author;

    /**
     * 页数
     */
    private Integer paginalNumber;

    /**
     * 开本
     */
    private String format;

    /**
     * 版本
     */
    private String edition;

    /**
     * 从编名
     */
    private String congbianming;

    /**
     * 从编者
     */
    private String congbianzhe;

    /**
     * 附件
     */
    private String attachmentId;

    /**
     * 文献1级分类
     */
    private String literatureTypeOne;

    /**
     * 文献2级分类
     */
    private String literatureTypeTwo;

    /**
     * 关联文献
     */
    private String literatureRelated;

    /**
     * 权限设置
     */
    private String permissionsSettings;

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
     * 排序，越大越靠前
     */
    private Integer sequence;

    /**
     * 状态0删除1未发布2发布3已撤回
     */
    private String status;

    /**
     * 搜索值，用来区分种次号
     */
    private String searchValue;

    //上传文件名
    private String attachmentName;
    //入库人名
    private String warehousesName;
    //提交类型1保存草稿2保存并发布
    private String submitType;
    //文献类型名
    private String dataTypeName;
    //库存状态名
    private String inventoryStateName;
    //提交状态名
    private String statusName;
    //权限设置名
    private String permissionsSettingsName;
    //文献分类名
    private String literatureTypeName;
    //分类索书号
    private String literatureTypeIndex;

    private static final long serialVersionUID = 1L;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getLiteratureTypeIndex() {
        return literatureTypeIndex;
    }

    public void setLiteratureTypeIndex(String literatureTypeIndex) {
        this.literatureTypeIndex = literatureTypeIndex;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getLiteratureTypeName() {
        return literatureTypeName;
    }

    public void setLiteratureTypeName(String literatureTypeName) {
        this.literatureTypeName = literatureTypeName;
    }

    public String getInventoryStateName() {
        return inventoryStateName;
    }

    public void setInventoryStateName(String inventoryStateName) {
        this.inventoryStateName = inventoryStateName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPermissionsSettingsName() {
        return permissionsSettingsName;
    }

    public void setPermissionsSettingsName(String permissionsSettingsName) {
        this.permissionsSettingsName = permissionsSettingsName;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public String getWarehousesName() {
        return warehousesName;
    }

    public void setWarehousesName(String warehousesName) {
        this.warehousesName = warehousesName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getIsbnNum() {
        return isbnNum;
    }

    public void setIsbnNum(String isbnNum) {
        this.isbnNum = isbnNum;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPublishingTime() {
        return publishingTime;
    }

    public void setPublishingTime(String publishingTime) {
        this.publishingTime = publishingTime;
    }

    public String getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(String warehouses) {
        this.warehouses = warehouses;
    }

    public String getWarehousingTime() {
        return warehousingTime;
    }

    public void setWarehousingTime(String warehousingTime) {
        this.warehousingTime = warehousingTime;
    }

    public String getInventoryState() {
        return inventoryState;
    }

    public void setInventoryState(String inventoryState) {
        this.inventoryState = inventoryState;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPaginalNumber() {
        return paginalNumber;
    }

    public void setPaginalNumber(Integer paginalNumber) {
        this.paginalNumber = paginalNumber;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getCongbianming() {
        return congbianming;
    }

    public void setCongbianming(String congbianming) {
        this.congbianming = congbianming;
    }

    public String getCongbianzhe() {
        return congbianzhe;
    }

    public void setCongbianzhe(String congbianzhe) {
        this.congbianzhe = congbianzhe;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getLiteratureTypeOne() {
        return literatureTypeOne;
    }

    public void setLiteratureTypeOne(String literatureTypeOne) {
        this.literatureTypeOne = literatureTypeOne;
    }

    public String getLiteratureTypeTwo() {
        return literatureTypeTwo;
    }

    public void setLiteratureTypeTwo(String literatureTypeTwo) {
        this.literatureTypeTwo = literatureTypeTwo;
    }

    public String getLiteratureRelated() {
        return literatureRelated;
    }

    public void setLiteratureRelated(String literatureRelated) {
        this.literatureRelated = literatureRelated;
    }

    public String getPermissionsSettings() {
        return permissionsSettings;
    }

    public void setPermissionsSettings(String permissionsSettings) {
        this.permissionsSettings = permissionsSettings;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
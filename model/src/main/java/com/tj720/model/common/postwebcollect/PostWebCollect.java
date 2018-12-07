package com.tj720.model.common.postwebcollect;

import java.io.Serializable;
import java.util.Date;

/**
 * post_web_collect
 * @author   xa
 */
public class PostWebCollect implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 收藏用户id
     */
    private String userId;

    /**
     * 藏品id
     */
    private String cid;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 排序
     */
    private Integer sequence;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 收藏类型； 1：专题 2：藏品
     */
    private String type;


    /**
     * 是否收藏； 1：已收藏 2：取消收藏
     */
    private String   hasCollected;


    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createtime) {
        this.createTime = createtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getHasCollected() {
        return hasCollected;
    }

    public void setHasCollected(String hasCollected) {
        this.hasCollected = hasCollected;
    }
}
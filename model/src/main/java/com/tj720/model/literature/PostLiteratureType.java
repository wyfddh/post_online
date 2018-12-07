package com.tj720.model.literature;

import java.io.Serializable;

/**
 * 文献类型实体类
 * @author 杜昶
 * @Date: 2018/11/13 15:39
 */
public class PostLiteratureType implements Serializable {
    private static final long serialVersionUID = -4866767601757986024L;

    /** id */
    private String id;

    /** 父id */
    private String pid;

    /** 类型名称 */
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

package com.tj720.model.common.interfacecollect;

import java.io.Serializable;

/**
 * 接口藏品类型
 * @author 杜昶
 * @Date: 2018/11/16 10:46
 */
public class InterfaceCollectType implements Serializable {
    private static final long serialVersionUID = -8683194513576803618L;

    private String typeCode;

    private String typepid;

    private String id;

    private Integer sort;

    private String typegroupid;

    private String value;

    private String typename;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypepid() {
        return typepid;
    }

    public void setTypepid(String typepid) {
        this.typepid = typepid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTypegroupid() {
        return typegroupid;
    }

    public void setTypegroupid(String typegroupid) {
        this.typegroupid = typegroupid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}

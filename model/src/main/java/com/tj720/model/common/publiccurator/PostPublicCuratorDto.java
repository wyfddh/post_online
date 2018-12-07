package com.tj720.model.common.publiccurator;

/**
 * @author 杜昶
 * @Date: 2018/11/9 12:04
 */
public class PostPublicCuratorDto extends PostPublicCurator {

    /** 关联藏品ids */
    private String collectIds;

    public String getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(String collectIds) {
        this.collectIds = collectIds;
    }
}

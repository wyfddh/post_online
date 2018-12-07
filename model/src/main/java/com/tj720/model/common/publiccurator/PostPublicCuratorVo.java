package com.tj720.model.common.publiccurator;

import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;

import java.util.List;

/**
 * @author 杜昶
 * @Date: 2018/11/9 13:58
 */
public class PostPublicCuratorVo extends PostPublicCurator {

    private String createTimeStr;

    private String datumUrl;

    private List<CollectDto> collects;

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getDatumUrl() {
        return datumUrl;
    }

    public void setDatumUrl(String datumUrl) {
        this.datumUrl = datumUrl;
    }

    public List<CollectDto> getCollects() {
        return collects;
    }

    public void setCollects(List<CollectDto> collects) {
        this.collects = collects;
    }
}

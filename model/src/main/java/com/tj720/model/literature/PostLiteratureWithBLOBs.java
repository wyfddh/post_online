package com.tj720.model.literature;

import java.io.Serializable;

/**
 * post_literature
 * @author 
 */
public class PostLiteratureWithBLOBs extends PostLiterature implements Serializable {
    /**
     * 检索词
     */
    private String retrievalWords;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getRetrievalWords() {
        return retrievalWords;
    }

    public void setRetrievalWords(String retrievalWords) {
        this.retrievalWords = retrievalWords;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
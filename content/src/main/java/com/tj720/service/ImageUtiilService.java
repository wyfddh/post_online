package com.tj720.service;

import org.springframework.stereotype.Service;

/**
 * @Author: 程荣凯11
 * @Date: 2019/1/10 18:57
 */
@Service
public interface ImageUtiilService {
    /**
     * 获取编辑器内容转化
     * @param content 编辑器内容git
     * @param type 类型（1，转化，0，不转化）
     * @return
     */
    public String getContent(String content,String type);

    /**
     * 拿到转换前的数据
     * @param content
     * @return
     */
    public String backContent(String content);
}

package com.tj720.service;

import com.tj720.controller.framework.JsonResult;

/**
 * 索书号生成
 * @author 杜昶
 */
public interface PostLiteratureSerialNumberService {
    /**
     * 生成索书号
     * @param dataName
     * @param selectID
     * @return
     */
    JsonResult getSerialNumber(String dataName, String selectID);
}

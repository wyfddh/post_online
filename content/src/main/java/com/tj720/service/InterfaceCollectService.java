package com.tj720.service;

import com.tj720.controller.framework.JsonResult;

import java.util.Map;

/**
 * @author 杜昶
 * @Date: 2018/11/16 10:51
 */
public interface InterfaceCollectService {

    /**
     * 获取接口藏品类型
     * @return
     */
    JsonResult getCollctTypeList();

    /**
     * 根据藏品列表和藏品名称，模糊查询藏品下拉
     * @param culCategory
     * @param culName
     * @return
     */
    JsonResult getCollectByTypeAndName(String culCategory, String culName);

    /**
     * 根据id获取藏品信息
     * @param culId
     * @return
     */
    JsonResult getCollectByCulId(String culId);

    /**
     * 查询邮政馆内藏品
     * @param keyWord
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Map<String, Object> getShareCulFieldAndDataList(String keyWord, Integer pageIndex, Integer pageSize);
}

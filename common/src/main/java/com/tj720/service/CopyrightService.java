package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.copyright.CopyrightSetting;
import org.springframework.stereotype.Service;

import java.util.jar.JarEntry;

/**
 * @Author: 程荣凯
 * @Date: 2018/11/16 15:27
 */
@Service
public interface CopyrightService {
    /**
     * 查询版权设置
     * @return
     */
    public JsonResult getCopyrightInfo();

    /**
     * 保存版权设置
     * @param copyrightSetting
     * @return
     */
    public JsonResult saveCopyrightInfo(CopyrightSetting copyrightSetting);
}

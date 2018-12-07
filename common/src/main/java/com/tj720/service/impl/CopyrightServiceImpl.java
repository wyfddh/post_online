package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.CopyrightSettingMapper;
import com.tj720.model.common.copyright.CopyrightSetting;
import com.tj720.service.CopyrightService;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 程荣凯
 * @Date: 2018/11/16 15:30
 */
@Service
public class CopyrightServiceImpl implements CopyrightService{
    @Autowired
    CopyrightSettingMapper copyrightSettingMapper;
    /**
     * 查询版权设置
     *
     * @return
     */
    @Override
    public JsonResult getCopyrightInfo() {
        try {
            CopyrightSetting copyrightSetting = copyrightSettingMapper.getCopyrightInfo();
            return new JsonResult(1,copyrightSetting);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 保存版权设置
     *
     * @param copyrightSetting
     * @return
     */
    @Override
    public JsonResult saveCopyrightInfo(CopyrightSetting copyrightSetting) {
        try {
            if (StringUtils.isEmpty(copyrightSetting.getXid())){
                copyrightSetting.setXid(IdUtils.getIncreaseIdByNanoTime());
                copyrightSettingMapper.insertSelective(copyrightSetting);
            }  else {
                copyrightSettingMapper.updateByPrimaryKeySelective(copyrightSetting);
            }
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }
}

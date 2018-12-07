package com.tj720.dao;

import com.tj720.model.common.copyright.CopyrightSetting;
import com.tj720.model.common.copyright.CopyrightSettingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyrightSettingMapper {
//    int countByExample(CopyrightSettingExample example);

//    int deleteByExample(CopyrightSettingExample example);

    int deleteByPrimaryKey(String xid);

    int insert(CopyrightSetting record);

    int insertSelective(CopyrightSetting record);

//    List<CopyrightSetting> selectByExample(CopyrightSettingExample example);

    CopyrightSetting selectByPrimaryKey(String xid);

//    int updateByExampleSelective(@Param("record") CopyrightSetting record, @Param("example") CopyrightSettingExample example);

//    int updateByExample(@Param("record") CopyrightSetting record, @Param("example") CopyrightSettingExample example);

    int updateByPrimaryKeySelective(CopyrightSetting record);

    int updateByPrimaryKey(CopyrightSetting record);

    /**
     * 查询版权设置
     * @return
     */
    CopyrightSetting getCopyrightInfo();

}
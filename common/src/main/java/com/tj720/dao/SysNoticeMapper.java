package com.tj720.dao;


import java.util.List;

import com.tj720.model.common.system.userlog.SysNotice;
import com.tj720.model.common.system.userlog.SysNoticeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysNoticeMapper {
//    default int countByExample(SysNoticeExample example) {
//        return 0;
//    }

//    int deleteByExample(SysNoticeExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysNotice record);

    int insertSelective(SysNotice record);

//    List<SysNotice> selectByExample(SysNoticeExample example);

    SysNotice selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") SysNotice record, @Param("example") SysNoticeExample example);

//    int updateByExample(@Param("record") SysNotice record, @Param("example") SysNoticeExample example);

    int updateByPrimaryKeySelective(SysNotice record);

    int updateByPrimaryKey(SysNotice record);

    //获取当前用户最新的6条未读通知
    List<SysNotice> getNoticeList(@Param("userId")String userId);

    //获取当前用户最新的6条通知
    List<SysNotice> getNoticeListPlus(@Param("userId")String userId,@Param("status")String status,@Param("type") String type);
}
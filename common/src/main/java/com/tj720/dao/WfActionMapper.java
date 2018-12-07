package com.tj720.dao;

import com.tj720.model.common.wf.WfAction;
import com.tj720.model.common.wf.WfActionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface WfActionMapper {
//    int countByExample(WfActionExample example);

//    int deleteByExample(WfActionExample example);

    int deleteByPrimaryKey(String xid);

    int insert(WfAction record);

    int insertSelective(WfAction record);

//    List<WfAction> selectByExample(WfActionExample example);

    WfAction selectByPrimaryKey(String xid);

//    int updateByExampleSelective(@Param("record") WfAction record, @Param("example") WfActionExample example);

//    int updateByExample(@Param("record") WfAction record, @Param("example") WfActionExample example);

    int updateByPrimaryKeySelective(WfAction record);

    int updateByPrimaryKey(WfAction record);

    WfAction getWfActionByPartyId(@Param("partyId") String partyId,@Param("partyType") String partyType,@Param("apply") String apply);

    WfAction getWfActionByPartyIdAndStatus(@Param("partyId") String partyId,@Param("partyType") String partyType,@Param("apply") String apply,@Param("status") String status);

    WfAction getWfActionByNotice(@Param("id") String id);
}
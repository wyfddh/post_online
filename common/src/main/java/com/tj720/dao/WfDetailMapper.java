package com.tj720.dao;

import com.tj720.model.common.wf.WfDetail;
import com.tj720.model.common.wf.WfDetailExample;
import java.util.HashMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface WfDetailMapper {
//    int countByExample(WfDetailExample example);

//    int deleteByExample(WfDetailExample example);

    int deleteByPrimaryKey(String xid);

    int insert(WfDetail record);

    int insertSelective(WfDetail record);

//    List<WfDetail> selectByExample(WfDetailExample example);

    WfDetail selectByPrimaryKey(String xid);

//    int updateByExampleSelective(@Param("record") WfDetail record, @Param("example") WfDetailExample example);

//    int updateByExample(@Param("record") WfDetail record, @Param("example") WfDetailExample example);

    int updateByPrimaryKeySelective(WfDetail record);

    int updateByPrimaryKey(WfDetail record);

    int updateWfDetailByProcessId(@Param("processInstId") String processInstId,@Param("status") String status);

    int updateWfDetailByUserIdAndPid(@Param("apply") String apply,@Param("processInstId")String processInstId
            ,@Param("status") String status, @Param("actionResult") String actionResult);

    WfDetail getWfDetailByUserIdAndPid(@Param("apply") String userId,@Param("processInstId") String processId,@Param("status") String status);


    List<WfDetail> getWfDetailByPid(@Param("processInstId") String processInstId);

    List<WfDetail> getByMap(HashMap<String, Object> map);

    Integer deleteWfDetailByProcessId(@Param("processId") String processId);
}
package com.tj720.dao;

import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.literature.PostLiteratureProcessDetail;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLiteratureProcessDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostLiteratureProcessDetail record);

    int insertSelective(PostLiteratureProcessDetail record);

    PostLiteratureProcessDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostLiteratureProcessDetail record);

    int updateByPrimaryKey(PostLiteratureProcessDetail record);

    Integer countProcessDetailList(String id);

    List<PostLiteratureProcessDetail> getProcessDetailList(Map<String, Object> map);

    SysDepartment getDaptByUserId(String id);

    PostLiteratureProcessDetail getProcessDetail(@Param("id") String id, @Param("preApproveId") String preApproveId);

    void batchUpdateProcessStatus(@Param("id")String id, @Param("status")String status);

    List<PostLiteratureProcessDetail> getProcessDetailByProcess(@Param("processId") String processId);

}
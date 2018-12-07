package com.tj720.dao;

import com.tj720.model.literature.PostLiteratureApproval;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLiteratureApprovalMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostLiteratureApproval record);

    int insertSelective(PostLiteratureApproval record);

    PostLiteratureApproval selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostLiteratureApproval record);

    int updateByPrimaryKey(PostLiteratureApproval record);
}
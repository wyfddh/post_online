package com.tj720.dao;

import com.tj720.model.literature.PostLiteratureProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLiteratureProcessMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostLiteratureProcess record);

    int insertSelective(PostLiteratureProcess record);

    PostLiteratureProcess selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostLiteratureProcess record);

    int updateByPrimaryKey(PostLiteratureProcess record);

    Integer countPostLiteratureProcessList(Map<String, Object> map);

    List<PostLiteratureProcess> getPostLiteratureProcessList(Map<String, Object> map);

    Integer countBorrowingList(Map<String, Object> map);

    List<PostLiteratureProcess> getBorrowingList(Map<String, Object> map);

    /**
     * 查询超期任务列表
     * @return
     */
    List<PostLiteratureProcess> getProcessDetailOverdueList();

    /**
     * 根据id查询文献流程
     * @param id
     * @return
     */
    HashMap<String,Object> getLiteratureProcessById(@Param("id") String id);


    PostLiteratureProcess getWfActionByNotice(@Param("id") String id);

    List<PostLiteratureProcess> getProcessListByIdList(List<String> list);

    List<PostLiteratureProcess> getProcessByLiteratureId(String literatureId);

    void updateByMap(Map<String, Object> map);
}
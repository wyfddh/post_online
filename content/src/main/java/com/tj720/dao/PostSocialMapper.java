package com.tj720.dao;


import com.tj720.model.common.social.PostSocial;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PostSocialMapper {

    /**
     * 查询社教数据集合
     */
    List<PostSocial> getSocials(Map<String, Object> map);

    /**
     * 查询社教记录数
     */
    int count(Map<String, Object> map);

    /**
     * 根据id删除社教
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据id批量删除社教(只针对已发布状态的记录)
     */
    int batchRemove(List<PostSocial> postSocials);


    /**
     * 根据id批量删除社教
     */
    int batchRemoves(String[] ids);


    /**
     * 添加社教(所有)
     */
    int insert(PostSocial record);

    /**
     * 添加社教(部分)
     */
    int insertSelective(PostSocial record);


    /**
     * 根据id查询社教
     */
    PostSocial selectByPrimaryKey(String id);


    /**
     * 修改社教(部分)
     */
    int updateByPrimaryKeySelective(PostSocial record);


    /**
     * 修改社教(所有)
     */
    int updateByPrimaryKey(PostSocial record);
}
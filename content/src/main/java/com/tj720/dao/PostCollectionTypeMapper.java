package com.tj720.dao;


import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collectiontype.PostCollectionType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PostCollectionTypeMapper {

    int deleteByThemeId(String ThemeId) throws Exception;

    List<PostCollectionType> selectByThemeId(String ThemeId) throws Exception;

    int batchSave(@Param("list")List<PostCollectionType> list) throws Exception;

    /**
     * 根据主题id查询藏品信息
     * @param id
     * @return
     */
    List<CollectDto> selectCollectByThemeId(String id);
}
package com.tj720.dao;

import com.tj720.model.literature.PostLiteratureType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杜昶
 * @Date: 2018/11/13 15:45
 */
@Repository
public interface PostLiteratureTypeMapper {

    /**
     * 根据pid获得文献类型
     * @param pid
     * @return
     */
    List<PostLiteratureType> getLiteratureTypeByPid(String pid);

    PostLiteratureType getTypeById(String id);
}

package com.tj720.dao;

import com.tj720.model.common.video.*;
import com.tj720.model.common.system.menu.MenuTreeDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
@Repository
public interface PostVideoTypeMapper {
//    int countByExample(PostVideoTypeExample example);

//    int deleteByExample(PostVideoTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(PostVideoType record);

    int insertSelective(PostVideoType record);

//    List<PostVideoType> selectByExample(PostVideoTypeExample example);

    PostVideoType selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") PostVideoType record, @Param("example") PostVideoTypeExample example);

//    int updateByExample(@Param("record") PostVideoType record, @Param("example") PostVideoTypeExample example);

    int updateByPrimaryKeySelective(PostVideoType record);

    int updateByPrimaryKey(PostVideoType record);

    Integer countPostVideoTypeList(HashMap<String, Object> condition);

    List<PostVideoType> queryPostVideoTypeList(HashMap<String, Object> condition);

    PostVideoType queryPostVideoTypeById(@Param("id") String id);

    List<MenuTreeDto> queryPostVideoTypeListTree();

    List<PostVideoType> queryPostVideoTypeListDist(@Param("currentId") String currentId);

    List<PostVideoType> selectByMap(HashMap<String, Object> map);
}
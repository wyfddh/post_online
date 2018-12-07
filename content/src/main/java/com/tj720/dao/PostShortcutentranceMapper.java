package com.tj720.dao;

import com.tj720.model.common.video.PostShortcutentrance;
import com.tj720.model.common.video.PostShortcutentranceExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public interface PostShortcutentranceMapper {
//    int countByExample(PostShortcutentranceExample example);

//    int deleteByExample(PostShortcutentranceExample example);

    int deleteByPrimaryKey(String xid);

    int insert(PostShortcutentrance record);

    int insertSelective(PostShortcutentrance record);

//    List<PostShortcutentrance> selectByExample(PostShortcutentranceExample example);

    PostShortcutentrance selectByPrimaryKey(String xid);

//    int updateByExampleSelective(@Param("record") PostShortcutentrance record, @Param("example") PostShortcutentranceExample example);

//    int updateByExample(@Param("record") PostShortcutentrance record, @Param("example") PostShortcutentranceExample example);

    int updateByPrimaryKeySelective(PostShortcutentrance record);

    int updateByPrimaryKey(PostShortcutentrance record);

    List<HashMap<String,Object>> getShortcutEntrance(@Param("currentUserId") String currentUserId,@Param("type") String type);

    Integer deleteShortcutEntrance(@Param("currentUserId") String currentUserId,@Param("type") String type);

    Integer batchAddShortcutEntrance(@Param("list") List<PostShortcutentrance> list);

}
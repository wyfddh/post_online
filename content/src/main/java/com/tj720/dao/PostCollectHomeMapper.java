package com.tj720.dao;


import com.tj720.model.common.collecthome.PostCollectHome;
import com.tj720.model.common.collecthome.PostCollectHomeExample;
import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.model.common.stampstory.PostStampStory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PostCollectHomeMapper{


    /**
     * 查询集邮之家集合记录数
     */
    Integer count(@Param("collectHomeTheme")String collectHomeTheme,@Param("createTime") String createTime,@Param
            ("orderBy") String orderBy,@Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 查询集邮之家集合
     */
    List<PostCollectHome> getHomeList(@Param("collectHomeTheme") String collectHomeTheme,@Param("createTime")
            String createTime,@Param("orderBy") String orderBy,@Param("currentPage") Integer currentPage,@Param("size") Integer size);

    /**
     * 根据id删除集邮之家
     */
    int deleteByPrimaryKey(String id);


    /**
     * 根据id批量删除集邮之家
     */
    int updateHomeByIds(@Param("ids")List<String> ids);


    /**
     * 添加集邮之家
     */
    int insertSelective(PostCollectHome record);

    /**
     * 根据id查询集邮之家
     */
    PostCollectHome selectByPrimaryKey(String id);


    /**
     * 修改集邮之家
     */
    int updateByPrimaryKeySelective(PostCollectHome  record);


    /**
     * 查询集邮资讯主题
     */
    List<PostCollectHome>  getHomeOptions();

//    long countByExample(PostCollectHomeExample example);

//    int deleteByExample(PostCollectHomeExample example);

    int insert(PostCollectHome record);

//    List<PostCollectHome> selectByExample(PostCollectHomeExample example);

//    int updateByExampleSelective(@Param("record") PostCollectHome record, @Param("example") PostCollectHomeExample example);

//    int updateByExample(@Param("record") PostCollectHome record, @Param("example") PostCollectHomeExample example);

    int updateByPrimaryKey(PostCollectHome record);




    //Web端接口
    /**
     * 查询集邮之家集合记录数
     */
    Integer  countWeb(@Param("collectHomeTheme")String collectHomeTheme,@Param("createTime") String createTime,@Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 查询集邮之家集合
     */
    List<PostCollectHome> getHomeWebList(@Param("collectHomeTheme") String collectHomeTheme,@Param("createTime")
            String createTime,@Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 根据id查询集邮之家
     */
    PostCollectHome  selectByWebHome(String id);


    //查询（上一页、下一页）
    PostCollectHome  getDetailHomeById(@Param("start") Integer start);


}
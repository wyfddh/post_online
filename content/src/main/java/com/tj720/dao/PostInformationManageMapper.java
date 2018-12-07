package com.tj720.dao;

import com.tj720.model.common.informationmanage.PostInformationManage;
import com.tj720.model.common.research.Research;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostInformationManageMapper{


    /**
     * 查询资讯管理集合
     */

    Integer count(@Param("informationType") String informationType,@Param("createTime") String createTime,
                  @Param("orderBy") String orderBy, @Param("currentPage") Integer currentPage,@Param("size") Integer
                          size);


    /**
     * 查询资讯管理集合记录数
     */
    List<PostInformationManage> getInformationList(@Param("informationType") String informationType,@Param("createTime") String createTime,
              @Param("orderBy") String orderBy,  @Param("currentPage") Integer currentPage,@Param("size") Integer size);

    /**
     * 根据id删除资讯管理
     */
    int deleteByPrimaryKey(String id);


    /**
     * 根据id批量删除资讯管理
     */
    int updateInformationyByIds(@Param("ids")List<String> ids);


    /**
     * 添加资讯管理
     */
    int insertSelective(PostInformationManage record);

    /**
     * 根据id查询资讯管理
     */
    PostInformationManage selectByPrimaryKey(String id);


    /**
     * 修改资讯管理
     */
    int updateByPrimaryKeySelective(PostInformationManage  record);



    int insert(PostInformationManage record);

    int updateByPrimaryKeyWithBLOBs(PostInformationManage  record);

    int updateByPrimaryKey(PostInformationManage  record);



    //Web端接口
    /**
     * 查询资讯管理集合
     */

    Integer  countWeb(@Param("informationType") String informationType,@Param("createTime") String createTime,
    @Param("orderBy") String orderBy, @Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 查询资讯管理集合记录数
     */
    List<PostInformationManage>  getInformationWebList(@Param("informationType") String informationType,@Param
   ("createTime") String createTime, @Param("orderBy") String orderBy,@Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 根据id查询资讯管理
     */
    PostInformationManage  selectWebInfoByPrimaryKey(String id);


    /**
     * 根据id查询资讯管理
     */
    PostInformationManage  getDetailInfoById(@Param("start") Integer start,@Param("informationType")String  informationType);


}
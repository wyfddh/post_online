package com.tj720.dao;

import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.bookingmanage.PostBookingManageExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostBookingManageMapper{


    /**
     * 查询预约管理记录数
     */
    Integer count(@Param("bookingType") String bookingType,@Param("contacts") String contacts,@Param("orderTime") String orderTime,
                  @Param("userId") String userId,@Param("orderBy") String orderBy, @Param("currentPage") Integer currentPage, @Param
                          ("size") Integer size);

    /**
     *
     * @param userId
     * @return
     */
    Integer countByUserId(@Param("userId") String userId);

    /**
     * 查询预约管理集合
     */
    List<PostBookingManage> getBookingList(@Param("bookingType") String bookingType,@Param("contacts") String contacts,@Param("orderTime") String orderTime,
        @Param("userId") String userId,@Param("orderBy") String orderBy, @Param("currentPage") Integer currentPage, @Param("size") Integer size);

    /**
     * 根据用户id查询预约列表
     */
    List<PostBookingManage> getBookingListByUserId(@Param("userId") String userId, @Param("currentPage") Integer currentPage,
                                           @Param("size") Integer size);

    /**
     * 根据id查询预约管理
     */
    PostBookingManage  selectByPrimaryKey(String id,String userId);


    /**
     * 添加预约管理
     */
    int insertSelective(PostBookingManage record);



    /**
     * 修改预约管理
     */
    int updateByPrimaryKeySelective(PostBookingManage record);




//    long countByExample(PostBookingManageExample example);

//    int deleteByExample(PostBookingManageExample example);

    int deleteByPrimaryKey(String id,String userId);

    int insert(PostBookingManage record);

//    List<PostBookingManage> selectByExample(PostBookingManageExample example);

//    int updateByExampleSelective(@Param("record") PostBookingManage record, @Param("example") PostBookingManageExample example);

//    int updateByExample(@Param("record") PostBookingManage record, @Param("example") PostBookingManageExample example);

    int updateByPrimaryKey(PostBookingManage record);



    //Web端接口
    /**
     * 添加预约管理
     */
    int insertSelectiveWeb(PostBookingManage record);

}
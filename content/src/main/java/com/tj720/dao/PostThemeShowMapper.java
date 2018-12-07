package com.tj720.dao;

import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.themeshow.PostThemeShow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface PostThemeShowMapper{

    /**
     * 查询主题展览集合
     */
    List<PostThemeShow> themeshowList(@Param("themeName") String themeName,@Param("themeSource") String themeSource,
      @Param("orderBy") String orderBy, @Param("currentPage") Integer currentPage,@Param("size") Integer size);

    /**
     * 查询主题展览记录数
     */
    int count(@Param("themeName") String themeName,@Param("themeSource") String themeSource,
              @Param("orderBy") String orderBy, @Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 查询主题来源集合
     */
    List<PostThemeShow> getSourceOptions();


    /**
     * 根据id删除主题来源
     */
    int deleteByPrimaryKey(String id);


    /**
     * 根据id批量删除主题来源
     */
    int batchRemove(String[] ids);


    /**
     * 根据id批量删除主题来源(假删除,改状态)
     */
    int updateThemebByIds(@Param("ids")List<String> ids);



    /**
     * 添加主题来源
     */
    int  insertSelective(PostThemeShow record);



    /**
     * 修改主题来源
     */
    int updateByPrimaryKeySelective(PostThemeShow record);



    /**
     * 根据id查询主题来源
     */
    PostThemeShow selectByPrimaryKey(String id);


    /**
     * 首页推荐
     */
    Integer CountSytj();

    /**
     * 精彩专题
     */
    Integer CountJczj();



    /**
     * 修改主题来源
     */
    int updateByPrimaryKey(PostThemeShow record);



    //Web端接口
    int countWeb(Map map);

    List<PostThemeShow> themeshowWebList(Map map);

    PostThemeShow selectWebByPrimaryKey(String id);


    int countWebzt(Map map);   //精选专题

    List<PostThemeShow> themeshowWebztList(Map map);    //精选专题


    //首页推荐
    int countsyWeb(Map map);

    PostThemeShow themeshowsyWebList(Map map);

    //根据id关联查询藏品
    List<Collect>  getCollectsById(String id);

    PostThemeShow getAppThemeById(@Param("id") String id,@Param("userId") String userId);


    List<CollectDto> getCollectsByThemId(@Param("id") String id,@Param("userId") String userId);

    /**
     * 查询劲炫专题
     * @param condition
     * @return
     */
    List<PostThemeShow> getJxztList(HashMap<String,Object> condition);

    /**
     * 统计精选专题
     * @param condition
     * @return
     */
    Integer countJxztList(HashMap<String,Object> condition);


}
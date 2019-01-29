package com.tj720.dao;


import com.tj720.model.common.publiccurator.PostPublicCurator;
import com.tj720.model.common.publiccurator.PostPublicCuratorDto;
import com.tj720.model.common.publiccurator.PostPublicCuratorExample;
import com.tj720.model.common.publiccurator.PostPublicCuratorVo;
import com.tj720.model.common.themeshow.PostThemeShow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


@Repository
public interface PostPublicCuratorMapper{

    Integer checkBizUserId(String id);

    /**
     * 查询公众策展记录数   用户名  处理状态  处理结果
     */
    Integer count(@Param("themeName") String themeName,@Param("processState") String processState,@Param("processResult") String processResult,
                  @Param("currentPage") Integer currentPage,@Param("size") Integer size);


    /**
     * 查询公众策展集合
     */
    List<PostThemeShow> getCuratorList(@Param("themeName") String themeName, @Param("processState") String processState, @Param("processResult") String processResult,
                                       @Param("currentPage") Integer currentPage, @Param("size") Integer size,@Param("userId") String userId);

    /**
     * 根据id查询公众策展
     */
    PostThemeShow selectByPrimaryKey(String id);


    /**
     * 根据id批量删除公众策展
     */
    int updateCuratorByIds(@Param("ids")List<String> ids);


    int deleteByPrimaryKey(String id);

//    int insertSelective(PostPublicCurator record);

//    int updateByExampleSelective(@Param("record") PostPublicCurator record, @Param("example") PostPublicCuratorExample example);

//    int updateByExample(@Param("record") PostPublicCurator record, @Param("example") PostPublicCuratorExample example);

    int updateByPrimaryKeySelective(PostThemeShow record);

    int updateByPrimaryKey(PostPublicCurator record);

    /**
     * 添加公共策展
     * @param dto
     * @return
     */
    Integer addPublicCurator(PostPublicCuratorDto dto);

    /**
     * 查询公共策展总数
     * @param param
     * @return
     */
    Integer getPublicCuratorCount(Map<String, Object> param);

    /**
     * 查询公共策展列表
     * @param param
     * @return
     */
    List<PostPublicCuratorVo> getPublicCuratorList(Map<String, Object> param);

    /**
     * 根据id获取公共策展详情
     * @param id
     * @return
     */
    PostPublicCuratorVo getPublicCuratorById(String id);
}
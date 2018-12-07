package com.tj720.dao;

import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.collect.CollectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectMapper {
//    int countByExample(CollectExample example);

//    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(String id);

    int insert(Collect record);

    int insertSelective(Collect record);

//    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

//    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    int updateCollectByIds(@Param("ids")List<String> ids);

    int selectCountByCollect(Map map);

    List<Collect> selectListByCollect(Map map);

    List<Collect> selectByParams(Map map);

    List<Attachment> getAttachmentById(Map map);

    /**
     * 典藏精选
     */
    Integer  CountDcjx();



    //Web端接口
    int selectCountByWebCollect(Map map);


    List<Collect> selectListIndexByWebCollect(Map map);

    List<Collect> selectListByWebCollect(Map map);

    Collect selectWebByPrimaryKey(String id);


    //根据用户id查询是否收藏
    List<CollectDto>  getCollectdcByUid(Map  map);

    int  countCollectdcByUid(Map  map);

    //首页典藏精选
    List<CollectDto>  selectIndexCollectList();

    /**
     * 根据id数组查询藏品
     * @param idArr
     * @return
     */
    List<CollectDto> selectListByIds(String[] idArr);

    /**
     * 根据typeId和sonTypeId查询藏品集合
     * @param collect
     * @return
     */
    List<CollectDto> selectListByTypeAndSonType(Collect collect);

    List<Collect> selectListByWebCollectAndUser(Map map);

    Integer countListByWebCollectAndUser(Map map);

    CollectDto getCollectByIdAndUser(@Param("id") String id,@Param("userId") String userId);

    Integer countByName(String collectName);
}
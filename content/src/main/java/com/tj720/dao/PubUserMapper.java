package com.tj720.dao;

import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.pubuser.PubUserDto;
import com.tj720.model.common.themeshow.PostThemeShow;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface PubUserMapper {

    int deleteByPrimaryKey(String id);

    int insert(PubUser record);

    int insertSelective(PubUser record);

    PubUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PubUser record);

    int updateByPrimaryKey(PubUser record);

    int selectCountByPubUser(Map map);

    List<PubUser> selectListByPubUser(Map map);

    int updatePubUserByIds(List ids);




    //根据用户id关联查询藏品
    List<Collect>  getCollectsByUserId(Map map);

    int  getCollectsCountByUserId(Map map);

    //根据用户id关联查询主题展
    List<PostThemeShow> getThemeByUserId(Map map);

    int  getThemeCountByUserId(Map map);


    //根据用户id关联查询
    PubUser  getUserDtoById(String id);


    /**
     * 个人中心用户头部信息
     * @param
     * @return
     */
    //根据用户id查询是否收藏
    List<PubUserDto> getUserDtoByUid(String  userId);


    List<PubUser> getListByPhone(String phone);
}
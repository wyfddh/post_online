package com.tj720.dao;

import com.tj720.model.common.postwebcollect.PostWebCollect;
import com.tj720.model.common.pubuser.PubUser;
import com.tj720.model.common.pubuser.PubUserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 前台用户登录Mapper
 * @author 杜昶
 * @Date: 2018/11/5 12:14
 */
@Repository
public interface PostLoginMapper {

    /**
     * 根据用户名/手机号查询用户信息
     * @param loginName 用户名/手机号
     * @return
     */
    PubUser getUserByUserNameOrPhone(String loginName);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    PubUser getUserByPhone(String phone);

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    PubUser getUserByUserName(String userName);

    /**
     * 保存前台用户
     * @param pubUser
     * @return
     */
    Integer insertPubUser(PubUser pubUser);

    /**
     * 修改用户手机号
     * @param pubUser
     * @return
     */
    Integer updatePubUserPhone(PubUser pubUser);

    /**
     * 修改用户信息
     * @param pubUser
     * @return
     */
    Integer changePubUserInfo(PubUser pubUser);

    /**
     * 修改用户密码
     * @param pubUserDto
     * @return
     */
    Integer updatePubUserPassword(PubUserDto pubUserDto);

    /**
     * 根据id获取用户
     * @param id 用户id
     * @return
     */
    PubUser getUserById(String id);

    /**
     * 根据类型查询用户收藏总数
     * @param postWebCollect
     * @return
     */
    Integer getCollectCountByTypeAndUserId(PostWebCollect postWebCollect);

    /**
     *
     * @param userId
     * @return
     */
    Integer getBookManage(@Param("userId") String userId);

    /**
     *
     * @param userId
     * @return
     */
    Integer getPublicCurator(@Param("userId") String userId);
}

package com.tj720.service;

import com.tj720.model.common.system.user.MipUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 用户管理
 * @Author: 程荣凯
 * @Date: 2018/9/30 12:04
 */
@Service("myService")
public interface UserService {
    /**
     * 增加用户
     * @param user 用户
     * @return 结果
     */
    public boolean addUser(MipUser user);

    /**
     * 修改用户
     * @param user 用户
     * @return 结果
     */
    public boolean updateUser(MipUser user);

    /**
     * 删除用户
     * @param user 用户
     * @return 结果
     */
    public boolean deleteUser(MipUser user);

    /**
     * 根据ID删除用户
     * @param userId 用户ID
     * @return 结果
     */
    public boolean deleteUserById(String userId);

    /**
     * 查询用户
     * @param userId 用户ID
     * @return 用户信息
     */
    public MipUser getUserInfoById(String userId);

    /**
     * 根据条件查询用户信息
     * @param condition 条件
     * @return 用户信息
     */
    public MipUser getUserInfoByCondition(HashMap<String, Object> condition);
    /**
     * 查询用户列表
     * @param condition 条件
     * @return 用户列表
     */
    public List<MipUser> getUserInfoList(HashMap<String, Object> condition);
}

package com.tj720.service;

import com.tj720.dao.PostLsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流水号服务
 * @Author: 程荣凯
 * @Date: 2018/10/23 15:50
 */
@Service
public interface PostLsService {
    /**
     * 获取流水号
     * @param lsKey 流水关键词
     * @param lsType 流水类型
     * @return
     */
    @Transactional
    public String getLsCode(String lsKey,String lsType);

    @Transactional
    public String getLsCodePlus(String lsKey,String lsType);

    public void addLsCode(String lsKey,String lsType);
}

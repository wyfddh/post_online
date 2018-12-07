package com.tj720.service.impl;

import com.tj720.dao.PostLsMapper;
import com.tj720.model.common.video.PostLs;
import com.tj720.service.PostLsService;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/23 16:03
 */
@Service
public class PostLsServiceImpl implements PostLsService{
    @Autowired
    PostLsMapper postLsMapper;
    /**
     * 获取流水号
     *
     * @param lsKey  流水关键词
     * @param lsType 流水类型
     * @return
     */
    @Override
    @Transactional
    public String getLsCode(String lsKey, String lsType) {
        Integer maxLsCode = postLsMapper.getMaxLsCode(lsKey, lsType);
        maxLsCode = maxLsCode+1;
        String id = String.format("%08d", maxLsCode);
//        addLsCode(lsKey,lsType);
        return "LS"+id;
    }

    @Override
    @Transactional
    public String getLsCodePlus(String lsKey, String lsType) {
        Integer maxLsCode = postLsMapper.getMaxLsCode(lsKey, lsType);
        maxLsCode = maxLsCode+1;
        String id = String.format("%08d", maxLsCode);
//        addLsCode(lsKey,lsType);
        return id;
    }

    @Override
    public void addLsCode(String lsKey, String lsType) {
        PostLs postLs = new PostLs();
        Integer maxLsCode = postLsMapper.getMaxLsCode(lsKey, lsType);
        maxLsCode = maxLsCode+1;
        postLs.setLsCode(maxLsCode);
        postLs.setXid(IdUtils.getIncreaseIdByNanoTime());
        postLs.setLsKey(lsKey);
        postLs.setLsType(lsType);
        postLsMapper.insertSelective(postLs);
    }

}

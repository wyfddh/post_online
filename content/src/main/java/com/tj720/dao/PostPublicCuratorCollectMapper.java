package com.tj720.dao;

import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.postpubliccuratorcollect.PostPublicCuratorCollect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 策展藏品关系表
 * @author 杜昶
 * @Date: 2018/11/9 13:03
 */
@Repository
public interface PostPublicCuratorCollectMapper {
    /**
     * 批量保存策展藏品
     * @param list
     * @return
     */
//    Integer savePublicCuratorCollect(List<PostPublicCuratorCollect> list);

    /**
     * 根据策展id查询策展藏品
     * @param id
     * @return
     */
    List<CollectDto> getCuratorCollectListByCuratorId(String id);
}

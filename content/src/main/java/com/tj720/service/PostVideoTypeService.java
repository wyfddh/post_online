package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.video.PostVideoType;
import com.tj720.utils.Page;
import org.springframework.stereotype.Service;

import java.util.jar.JarEntry;

/**
 * 资料分类接口
 * @Author: 程荣凯
 * @Date: 2018/10/30 18:42
 */
@Service
public interface PostVideoTypeService {
    public JsonResult addPostVideoType(PostVideoType postVideoType);

    public JsonResult updatePostVideoType(PostVideoType postVideoType);

    public JsonResult deletePostVideoType(String id);

    public JsonResult queryPostVideoTypeList(String keywords,String level,String status,Integer orderBy,Page page);

    public JsonResult queryPostVideoTypeById(String id);

    public JsonResult queryPostVideoTypeListTree();

    public JsonResult changeTypeStatus(String id,int status);

    public JsonResult queryPostVideoTypeListDist(String currentId);
}

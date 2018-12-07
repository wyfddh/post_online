package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.model.common.video.PostVideoComments;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 影视资料标注服务接口
 * @Author: 程荣凯
 * @Date: 2018/10/22 17:14
 */
@Service
public interface PostVideoCommentsService {
    /**
     * 查询标注信息列表
     * @param postVideoId 影视资料id
     * @return
     */
    public JsonResult getPostVideoCommentList(String postVideoId);

    /**
     * 新增标注信息
     * @param postVideoId 影视资料id
     * @param postVideoCommentsList 影视资料标注列表
     * @return
     */
    public JsonResult addPostVideoCommentList(String postVideoId, List<PostVideoComments> postVideoCommentsList);

    /**
     * 修改标注信息
     * @param postVideoId 影视资料id
     * @param postVideoCommentsList 影视资料标注列表
     * @return
     */
    public JsonResult updatePostVideoCommentList(String postVideoId, List<PostVideoComments> postVideoCommentsList);

    /**
     * 删除标注信息
     * @param postVideoId 影视资料id
     * @return
     */
    public JsonResult deletePostVideoCommentList(String postVideoId);
}

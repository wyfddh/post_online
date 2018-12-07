package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostVideoCommentsMapper;
import com.tj720.model.common.video.PostVideo;
import com.tj720.model.common.video.PostVideoComments;
import com.tj720.service.PostVideoCommentsService;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 影视资料标注服务实现
 * @Author: 程荣凯
 * @Date: 2018/10/22 17:19
 */
@Service
public class PostVideoCommentsServiceImpl implements PostVideoCommentsService {
    @Autowired
    PostVideoCommentsMapper postVideoCommentsMapper;
    private static String userId = "sysadmin";

    private PostVideoComments setActionInfo(PostVideoComments postVideoComments, String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            String format = dateFormat.format(date);
            date = dateFormat.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }
        postVideoComments.setUpdater(userId);
        postVideoComments.setUpdateTime(date);
        if (type == "0"){
            postVideoComments.setCreateTime(date);
            postVideoComments.setCreator(userId);
        }
        return postVideoComments;
    }
    /**
     * 查询标注信息列表
     * @param postVideoId 影视资料id
     * @return
     */
    @Override
    public JsonResult getPostVideoCommentList(String postVideoId) {
        try {
            List<PostVideoComments> postVideoCommentsList = postVideoCommentsMapper.getPostVideoCommentsList(postVideoId);
            return new JsonResult(1,postVideoCommentsList);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1,null,"200507");
        }
    }

    /**
     * 新增标注信息
     *
     * @param postVideoId           影视资料id
     * @param postVideoCommentsList 影视资料标注列表
     * @return
     */
    @Override
    public JsonResult addPostVideoCommentList(String postVideoId, List<PostVideoComments> postVideoCommentsList) {
        try {
            Integer count = 0;
            for (PostVideoComments postVideoComments : postVideoCommentsList) {
                postVideoComments.setId(IdUtils.getIncreaseIdByNanoTime());
                postVideoComments.setVideoId(postVideoId);
                postVideoComments = setActionInfo(postVideoComments,"0");
                int integer = postVideoCommentsMapper.insertSelective(postVideoComments);
                count = count+integer;
            }
            if (count>0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(1,null,"200504");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1,null,"200504");
        }
    }

    /**
     * 修改标注信息
     *
     * @param postVideoId           影视资料id
     * @param postVideoCommentsList 影视资料标注列表
     * @return
     */
    @Override
    public JsonResult updatePostVideoCommentList(String postVideoId, List<PostVideoComments> postVideoCommentsList) {
        try {
            Integer integer = postVideoCommentsMapper.deletePostVideoComments(postVideoId);
            JsonResult result = addPostVideoCommentList(postVideoId,postVideoCommentsList);
            if (result != null && result.getSuccess() == 1){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(1,null,"200505");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1,null,"200505");
        }
    }

    /**
     * 删除标注信息
     *
     * @param postVideoId 影视资料id
     * @return
     */
    @Override
    public JsonResult deletePostVideoCommentList(String postVideoId) {
        try {
            Integer integer = postVideoCommentsMapper.deletePostVideoComments(postVideoId);
            if (integer > 0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(1,null,"200506");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1,null,"200506");
        }
    }
}

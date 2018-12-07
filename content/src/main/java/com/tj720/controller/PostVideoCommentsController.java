package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.model.common.video.PostVideoComments;
import com.tj720.model.common.video.PostVideoCommentsDto;
import com.tj720.service.PostVideoCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/24 16:37
 */
@RestController
@RequestMapping("/PostVideoComments")
public class PostVideoCommentsController {
    @Autowired
    PostVideoCommentsService postVideoCommentsService;

    /**
     * 查询
     * @param postVideoId
     * @return
     */
    @RequestMapping("/queryPostVideoComments")
    public JsonResult queryPostVideoComments(String postVideoId){
        JsonResult postVideoCommentList = postVideoCommentsService.getPostVideoCommentList(postVideoId);
        return postVideoCommentList;
    }
    @RequestMapping("/addPostVideoComments")
    public JsonResult addPostVideoComments(@RequestBody PostVideoCommentsDto postVideoCommentsDto){
        return null;
    }
}

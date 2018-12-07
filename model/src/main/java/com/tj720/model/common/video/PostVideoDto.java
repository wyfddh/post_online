package com.tj720.model.common.video;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PostVideoDto {
    /**
     * 影视资料对象
     */
    private PostVideo postVideo;
    /**
     *资料批注
     */
    private List<PostVideoComments> postVideoComments;
    /**
     * 编目信息
     */
    private String currentUserId;

    private String currentOrgId;
    /**
     * 操作类型
     */
    private String actionType;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public PostVideo getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(PostVideo postVideo) {
        this.postVideo = postVideo;
    }

    public List<PostVideoComments> getPostVideoComments() {
        return postVideoComments;
    }

    public void setPostVideoComments(List<PostVideoComments> postVideoComments) {
        this.postVideoComments = postVideoComments;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentOrgId() {
        return currentOrgId;
    }

    public void setCurrentOrgId(String currentOrgId) {
        this.currentOrgId = currentOrgId;
    }
}
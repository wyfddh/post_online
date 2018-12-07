package com.tj720.model.common.volunteer;

/**
 * @author 杜昶
 * @Date: 2018/11/8 11:01
 */
public class PostVolunteerActivitiesDto extends PostVolunteerActivities {

    /** 活动状态 1-进行中 2-已结束 */
    private Integer activitiesStatus;

    private Integer start;

    private Integer currentPage;

    private Integer size;

    private String userId;

    private  String  orderBy;


    public Integer getActivitiesStatus() {
        return activitiesStatus;
    }

    public void setActivitiesStatus(Integer activitiesStatus) {
        this.activitiesStatus = activitiesStatus;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}

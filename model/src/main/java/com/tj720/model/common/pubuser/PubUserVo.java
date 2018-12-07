package com.tj720.model.common.pubuser;

/**
 * @author 杜昶
 * @Date: 2018/11/5 17:13
 */
public class PubUserVo extends PubUser {

    /** 用户头像链接 */
    private String avatarLink;

    /** String格式生日 */
    private String birthdayStr;

    /** 收藏专题总数 */
    private Integer collectSpecialCount;

    /** 收藏藏品总数 */
    private Integer collectCollectionCount;

    private Integer publicCuratorCount;

    private Integer bookManage;

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public Integer getCollectSpecialCount() {
        return collectSpecialCount;
    }

    public void setCollectSpecialCount(Integer collectSpecialCount) {
        this.collectSpecialCount = collectSpecialCount;
    }

    public Integer getCollectCollectionCount() {
        return collectCollectionCount;
    }

    public void setCollectCollectionCount(Integer collectCollectionCount) {
        this.collectCollectionCount = collectCollectionCount;
    }

    public Integer getPublicCuratorCount() {
        return publicCuratorCount;
    }

    public void setPublicCuratorCount(Integer publicCuratorCount) {
        this.publicCuratorCount = publicCuratorCount;
    }

    public Integer getBookManage() {
        return bookManage;
    }

    public void setBookManage(Integer bookManage) {
        this.bookManage = bookManage;
    }
}

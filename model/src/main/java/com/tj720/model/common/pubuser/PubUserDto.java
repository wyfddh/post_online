package com.tj720.model.common.pubuser;

import com.tj720.model.common.Attachment;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.themeshow.PostThemeShow;

import java.util.List;

/**
 * @author 杜昶
 * @Date: 2018/11/5 15:19
 */
public class PubUserDto extends PubUser {

    /** 短信验证码 */
    private String verificationCode;

    /** 旧密码 */
    private String oldPassword;

    //主图url
    private String mainPicUrl;

    //图片集合
    private List<Attachment> picList;


    //藏品列表
    private List<Collect>  collectionList;



    //专题列表
    private List<PostThemeShow>  themeList;

    //登陆类型
    private String loginType;

    //收藏专题数
    private  String   themeAmount;
    //收藏藏品数
    private  String   collectAmount;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    @Override
    public String getMainPicUrl() {
        return mainPicUrl;
    }

    @Override
    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    @Override
    public List<Attachment> getPicList() {
        return picList;
    }

    @Override
    public void setPicList(List<Attachment> picList) {
        this.picList = picList;
    }

    public List<Collect> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Collect> collectionList) {
        this.collectionList = collectionList;
    }

    public List<PostThemeShow> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<PostThemeShow> themeList) {
        this.themeList = themeList;
    }

    public String getThemeAmount() {
        return themeAmount;
    }

    public void setThemeAmount(String themeAmount) {
        this.themeAmount = themeAmount;
    }

    public String getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(String collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}

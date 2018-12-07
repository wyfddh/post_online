package com.tj720.utils.oss;

import com.tj720.controller.springbeans.Config;

/**
 * @Auther: caiming
 * @Date: 2018/11/22 19:56
 * @Description:
 */
public class OSSConfig {

    private String endpoint;       //连接区域地址
    private String accessKeyId;    //连接keyId
    private String accessKeySecret;    //连接秘钥
    private String bucketName;     //需要存储的bucketName
    private String picLocation;    //图片保存路径

    public OSSConfig(Config config) {
        this.endpoint = config.getEndpoint();
        this.bucketName = config.getBucketName();
        this.picLocation = config.getPicLocation();
        this.accessKeyId = config.getAccessKeyId();
        this.accessKeySecret = config.getAccessKeySecret();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPicLocation() {
        return picLocation;
    }

    public void setPicLocation(String picLocation) {
        this.picLocation = picLocation;
    }
}

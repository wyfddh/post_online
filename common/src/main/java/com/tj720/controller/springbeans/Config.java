package com.tj720.controller.springbeans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Config{
	@Value("${web.sct}")
	private String sct;
	
	@Value("${redis.host}")
	private String redisIp;
	
	@Value("${redis.port}")
	private int redisPort;
	
	@Value("${redis.info}")
	private String redisPwd;
	
	@Value("${web.redisPoolSize}")
	private int redisPoolSize;
	
	@Value("${web.redisKeyPrefix}")
	private String redisKeyPrefix;
	
	@Value("${web.cacheTime}")
	private int cacheTime;
	
	@Value("${web.loginInforTime}")
	private int loginInforTime;
	
	@Value("${web.fileSize}")
	private int fileSize;
	
	@Value("${web.imageType}")
	private String imageType;
	
	@Value("${web.fileType}")
	private String fileType;
	
	@Value("${web.docType}")
	private String docType;
	
	@Value("${web.audioType}")
	private String audioType;
	
	@Value("${web.videoType}")
	private String videoType;
	
	@Value("${web.cacheTime}")
	private int monitorThreadNum;
	
	@Value("${web.monitorCacheTime}")
	private int monitorCacheTime;
	
	@Value("${web.monitorTryTimes}")
	private int monitorTryTimes;
	
	@Value("${web.monitorEmailSendIndex}")
	private int monitorEmailSendIndex;
	
	@Value("${mail.username}")
	private String mail;
	
	@Value("${web.rootPath}")
	private String rootPath;
	
	@Value("${web.rootUrl}")
	private String rootUrl;
	
	@Value("${org.platform_id}")
	private int platformId;
	
	@Value("${pc.rootpath}")
	private String pcRootpath;
	
	@Value("${mobile.rootpath}")
	private String mbRootpath;
	
	@Value("${org.province_id}")
	private int provinceId;
	
	@Value("${web.logo}")
	private String logo;
	
	@Value("${web.hostname}")
	private String hostname;
	
	@Value("${duanxin.regCode}")
	private String regCode;
	
	@Value("${duanxin.regPasswod}")
	private String regPasswod;
	
	@Value("${baidu.ak}")
	private String ak;
	
	@Value("${org.city_id}")
	private String cityId;
	
	@Value("${ffmpegInstallPath}")
	private String ffmpegInstallPath;
	
	@Value("${web.imageUrl}")
	private String imageUrl;
	
	@Value("${org.phone_start}")
	private String phoneStart;
	
	@Value("${es.host}")
	private String esHost;
	@Value("${es.port}")
	private int esPort;
	@Value("${es.nodeIpInfo}")
	private String esNodeIpInfo;
	@Value("${es.clusterName}")
	private String esClusterName;
	@Value("${isCommentary}")
	private int isCommentary;
	
	//ftp-start
	@Value("${isFtp}")
	private boolean isFtp;
	@Value("${ftp.url}")
	private String ftpUrl;
	@Value("${ftp.port}")
	private String ftpPort;
	@Value("${ftp.userName}")
	private String ftpUserName;
	@Value("${ftp.info}")
	private String ftpPassWord;
	@Value("${ftp.rootPath}")
	private String ftpRootPath;

	/**
	 * web项目域名
	 */
	@Value("${webProject.path}")
	private String webProjectPath;

	/**
	 * 外部藏品接口链接
	 */
	@Value("${interfaceCollect.path}")
	private String interfaceCollectPath;

	/**
	 * oss配置
	 */
	@Value("${oss.endpoint}")
	private String endpoint;
	@Value("${oss.bucketName}")
	private String bucketName;
	@Value("${oss.picLocation}")
	private String picLocation;
	@Value("${oss.accessKeyId}")
	private String accessKeyId;
	@Value("${oss.accessKeySecret}")
	private String accessKeySecret;

	public String getSct() {
		return sct;
	}
	public String getRedisIp() {
		return redisIp;
	}
	public int getRedisPort() {
		return redisPort;
	}
	public String getRedisPwd() {
		return redisPwd;
	}
	public int getRedisPoolSize() {
		return redisPoolSize;
	}
	public String getRedisKeyPrefix() {
		return redisKeyPrefix;
	}
	public int getCacheTime() {
		return cacheTime;
	}
	public int getLoginInforTime() {
		return loginInforTime;
	}
	public int getFileSize() {
		return fileSize;
	}
	public String getImageType() {
		return imageType;
	}
	public String getFileType() {
		return fileType;
	}
	public String getDocType() {
		return docType;
	}
	public String getAudioType() {
		return audioType;
	}
	public String getVideoType() {
		return videoType;
	}
	public int getMonitorThreadNum() {
		return monitorThreadNum;
	}
	public int getMonitorCacheTime() {
		return monitorCacheTime;
	}
	public int getMonitorTryTimes() {
		return monitorTryTimes;
	}
	public int getMonitorEmailSendIndex() {
		return monitorEmailSendIndex;
	}
	public String getMail() {
		return mail;
	}
	public String getRootPath() {
		return rootPath;
	}
	public String getRootUrl() {
		return rootUrl;
	}
	public int getPlatformId() {
		return platformId;
	}
	public String getPcRootpath() {
		return pcRootpath;
	}
	public String getMbRootpath() {
		return mbRootpath;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public String getLogo() {
		return logo;
	}
	public String getHostname() {
		return hostname;
	}
	public String getRegCode() {
		return regCode;
	}
	public String getRegPasswod() {
		return regPasswod;
	}
	public String getAk() {
		return ak;
	}
	public String getCityId() {
		return cityId;
	}
	public String getFfmpegInstallPath() {
		return ffmpegInstallPath;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getPhoneStart() {
		return phoneStart;
	}
	public String getEsHost() {
		return esHost;
	}
	public int getEsPort() {
		return esPort;
	}
	public String getEsNodeIpInfo() {
		return esNodeIpInfo;
	}
	public String getEsClusterName() {
		return esClusterName;
	}
	public int getIsCommentary() {
		return isCommentary;
	}
	public boolean isFtp() {
		return isFtp;
	}
	public String getFtpUrl() {
		return ftpUrl;
	}
	public String getFtpPort() {
		return ftpPort;
	}
	public String getFtpUserName() {
		return ftpUserName;
	}
	public String getFtpPassWord() {
		return ftpPassWord;
	}
	public String getFtpRootPath() {
		return ftpRootPath;
	}
	public String getWebProjectPath() {
		return webProjectPath;
	}
	public String getInterfaceCollectPath() {
		return this.interfaceCollectPath;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getBucketName() {
		return bucketName;
	}

	public String getPicLocation() {
		return picLocation;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}
}

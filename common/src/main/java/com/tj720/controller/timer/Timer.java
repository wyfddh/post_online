package com.tj720.controller.timer;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 
 * @author: caiming
 * @date: 2018年9月3日
 */
@Component
public class Timer {
	
	Logger logger = Logger.getLogger(Timer.class);
	private static boolean isStar = false;
	private static String YAO = "__key*__:*";
	
	/**
	 * 启动redis缓存可以到期触发事件
	 * </p>
	 */
	@Scheduled(fixedDelay= 60 * 24 * 60 * 60 *1000) 
	public void starRedisKeyExpiredListener() {
		try {
			if(!isStar){
				//注释掉即停止使用redis，开启redis时，则打开
//				JedisService.psubscribe(YAO);
				isStar = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	

}

package com.tj720.controller.listener;

import java.util.Date;


import com.tj720.common.constant.KeyConstants;
import com.tj720.service.jedis.JedisService;
import com.tj720.utils.Aes;
import redis.clients.jedis.JedisPubSub;
/**
 * 
 * @author: caiming
 * @date: 2018年9月2日
 */
public class RedisKeyExpiredListener extends JedisPubSub {

	
    public RedisKeyExpiredListener() {
		super();
	}

	@Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String key) {
    	//自己判断 key是否需要进行处理
        if(!key.startsWith("expire:")) return;
       
        //获取id
        int index = key.lastIndexOf(":");
        String id = key.substring(index + 1);
        //svae db
        if(key.startsWith(KeyConstants.USER_IP_TOKEN_CODE_KEY)) {
        	//更新用户token
        	reloadToken(key);
        }
        this.unsubscribe();
    }

    /**
     * 重新加载web端用户token
     */
    public void reloadToken(String key) {
    	String oldKey = key.replaceFirst(KeyConstants.USER_IP_TOKEN_CODE_KEY, "");
    	String userid = oldKey.split("##")[0];
    	String token = Aes.encrypt(userid+(new Date()).getTime());
		JedisService.set(key, token);
    }
}
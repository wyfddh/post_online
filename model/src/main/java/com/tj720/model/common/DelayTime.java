package com.tj720.model.common;

/**
 * 定时任务的时间
 * @Author: 程荣凯
 * @Date: 2018/11/3 20:07
 */
public class DelayTime {
    private final static long DELAT_TIME = 2*60*60*100;//两分钟

    public static long getDelayTime() {
        return DELAT_TIME;
    }
}

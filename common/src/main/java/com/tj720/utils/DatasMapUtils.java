package com.tj720.utils;/**
 * Created by MyPC on 2018/10/17.
 */


import com.tj720.utils.common.Utils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName: DatasMapUtils
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/17
 * @Version: 1.0
 **/
public class DatasMapUtils {

    public static HashMap<String, Object> getUseMapData(HashMap hashMap, Object[] objects) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0, j = objects.length; i < j; i++) {
            Object objKey = objects[i].toString();
            Object objValue = hashMap.get(objKey);
            if ("size".equals(objKey) && objValue == null) {
                map.put("size", 10);
            } else if ("currentPage".equals(objKey) && objValue == null) {
                map.put("currentPage", 1);
            } else {
                if (objValue != null) {
                    map.put(objKey.toString(), objValue);
                }
            }

        }
        return map;
    }

    public static Date getFormatDate(Date date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            String formatDate = dateFormat.format(date);
            date = dateFormat.parse(formatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  date;
    }

    public static Date getFormatDate(String dates, String format) {
        Date date = null;
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            String formatDate = dateFormat.format(dates);
            date = dateFormat.parse(formatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  date;
    }
}

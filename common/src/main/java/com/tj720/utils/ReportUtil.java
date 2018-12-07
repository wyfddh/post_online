package com.tj720.utils;

import com.tj720.model.common.report.ReportFormatSimple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * echarts图表格式转换
 * @Author: 程荣凯
 * @Date: 2018/11/1 17:18
 */
public class ReportUtil {
    /**
     * 获取饼图格式
     * @param data
     * @param key
     * @return
     */
    public static ReportFormatSimple getPieData(List<HashMap<String,Object>> data,String key){
        HashSet<String> legend = new HashSet<String>();
        for (HashMap datum : data) {
            if (!legend.contains(datum.get(key))){
                legend.add(datum.get(key).toString());
            }
        }
        return new ReportFormatSimple(legend,null,data);
    }

    /**
     * 获取折线图
     * @param data
     * @param legend
     * @param key
     * @return
     */
    public static ReportFormatSimple getLineData(List<HashMap<String,Object>> data,HashSet<String> legend,String key){
        HashSet<String> xAxis = new HashSet<String>();
        for (HashMap datum : data) {
            if (!xAxis.contains(datum.get(key))){
                xAxis.add(datum.get(key).toString());
            }
        }
        return new ReportFormatSimple(legend,xAxis,data);
    }

    /**
     * 获取柱状图
     * @param data
     * @param legend
     * @param key
     * @return
     */
    public static ReportFormatSimple getBarData(List<HashMap<String,Object>> data,HashSet<String> legend,String key){
        HashSet<String> xAxis = new HashSet<String>();
        for (HashMap datum : data) {
            if (!xAxis.contains(datum.get(key))){
                xAxis.add(datum.get(key).toString());
            }
        }
        return new ReportFormatSimple(legend,xAxis,data);
    }
}

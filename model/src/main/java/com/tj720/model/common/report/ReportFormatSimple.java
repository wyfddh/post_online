package com.tj720.model.common.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/11/1 18:21
 */
public class ReportFormatSimple implements Serializable {
    protected static final long serialVersionUID = 7553249056983455066L;
    /**
     * 分类栏
     */
    private HashSet<String> legend;
    /**
     * x轴
     */
    private HashSet<String> xAxis;
    /**
     * 数据
     */
    private List<HashMap<String,Object>> data;

    public ReportFormatSimple(HashSet<String> legend,HashSet<String> xAxis,List<HashMap<String,Object>> data){
        this.legend = legend;
        this.xAxis = xAxis;
        this.data = data;
    }
}

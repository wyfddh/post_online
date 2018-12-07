package com.tj720.model.common.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
* @author chengrongkai
* @version 创建时间：2018年8月1日 上午11:41:49
* @ClassName 类名称
* @Description 类描述
*/
public class ReportEchartsFormat implements Serializable{
	protected static final long serialVersionUID = 7553249056983455066L;
	//报表标题
	protected HashMap<String,Object> title;
	//报表分类
	protected HashMap<String,Object> legend;
	//tooltip
	protected HashMap<String,Object> tooltip;
	//报表显示数据
	protected List<HashMap<String,Object>> series;
	public HashMap<String,Object> getTitle() {
		return title;
	}
	public void setTitle(HashMap<String,Object> title) {
		this.title = title;
	}
	public HashMap<String, Object> getLegend() {
		return legend;
	}
	public void setLegend(HashMap<String, Object> legend) {
		this.legend = legend;
	}
	public HashMap<String, Object> getTooltip() {
		return tooltip;
	}
	public void setTooltip(HashMap<String, Object> tooltip) {
		this.tooltip = tooltip;
	}
	public List<HashMap<String, Object>> getSeries() {
		return series;
	}
	public void setSeries(List<HashMap<String, Object>> series) {
		this.series = series;
	}
	ReportEchartsFormat(){

	}
	ReportEchartsFormat(String title,HashMap<String,Object> legend,HashMap<String,Object> tooltip,List<HashMap<String,Object>> series){
		HashMap<String,Object> data = new HashMap<String,Object>();
		data.put("text", title);
		this.title = data;
		this.legend = legend;
		this.tooltip = tooltip;
		this.series = series;
	}
	

}

package com.tj720.controller.framework.common.model;
import java.io.Serializable;

public class DataResult implements Serializable {
	private static final long serialVersionUID = 7553249056983455065L;
	private String code;
	private String msg;
	private int count;
	private String data;

	public DataResult(String code,String msg,int count,String data){
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}


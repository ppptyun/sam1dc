package com.samil.stdadt.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultVO {
	private String status;	// success, error, unauthorized
	private List<String> msg;
	private Object data;
	public String getStatus() {
		return status;
	}
	public List<String> getMsg() {
		return msg;
	}
	public Object getData() {
		return data;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setMsg(String msg) {
		this.msg = new ArrayList<String>(Arrays.asList(msg));
	}
	
	public void setMsg(List<String> msg) {
		this.msg = msg;
	}
	public void setData(Object data) {
		this.data = data;
	}
}

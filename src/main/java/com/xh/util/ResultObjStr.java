package com.xh.util;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ResultObjStr {
	/**
	 * 成功
	 */
	public static int SUCCESS = 1;

	/**
	 * 失败
	 */
	public static int FAIL = -1;

	/**
	 * 异常
	 */
	public static int ERROR = -2;
	/**
	 * sessio失效
	 */
	public static int SESSION = -100;
	
	/**
	 * NO DATA.
	 */
	public static int NO_DATA = -3;


	/**
	 * 反馈状态
	 */
	private int state;

	/**
	 * 反馈信息
	 */
	private String msg;

	/**
	 * 反馈数据
	 */
	private Object data;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultObjStr() {
		super();
	}

	public ResultObjStr(int state, String msg, Object data) {
		super();
		this.state = state;
		this.msg = msg;
		this.data = data;
	}

	public String toJson(){
		try {
			return JsonHandler.objToJson(this);
		} catch (JsonProcessingException e) {
			return "{\"state\":" + ResultObjStr.ERROR + ",\"msg\":\"json error\",\"data\":null}";
		}
	}

}

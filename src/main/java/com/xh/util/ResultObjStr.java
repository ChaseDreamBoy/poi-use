package com.xh.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

	public String toJson(){
		try {
			return JsonHandler.objToJson(this);
		} catch (JsonProcessingException e) {
			return "{\"state\":" + ResultObjStr.ERROR + ",\"msg\":\"json error\",\"data\":null}";
		}
	}

}

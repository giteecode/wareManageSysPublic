package com.education.common.utils;
/**
 * 为方便处理ajax请求
 * @author zengjintao
 * @version 1.0
 * @create_at 2017年6月28日上午9:28:04
 */
public class ResultCode {

	public static final Integer UN_AUTH_ERROR_CODE = 401; //token 失效

	public static final Integer UN_AUTH_HEADER = 402; //请求头验证失败


	public static final Integer NO_PERMISSION = 406; // 权限不足
	public static final Integer SUCCESS = 1; //响应成功
	public static final Integer FAIL = 0; //响应失败

	public static final Integer EXCEL_VERIFICATION_FAIL = 3; // excel 表格数据校验异常

	public Integer code = SUCCESS;
	public String message = "操作成功";

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultCode() {
	}

	public ResultCode(Integer code) {
		this.code = code;
		if (code == FAIL) {
			this.message = "操作失败";
		}
	}
}

package com.cxp.web.common;

public class ReqStatus {
	public static final String SUCCESS = "0000";
	
	public static final String ERR = "1111";
	public static final String ERR_MSG = "未知错误";
	
	public static final String NOT_ACCESS = "0001";
	public static final String NOT_ACCESS_MSG = "没有权限";
	
	public static final String NOT_EXISTS = "0002";
	public static final String NOT_EXISTS_MSG = "访问【{0}】资源【{1}】不存在";
	
	public static final String BIZ_ERR = "0003";
	public static final String BIZ_ERR_MSG = "业务处理异常";
	
	public static final String BIZ_ERR_ACCOUNT_EMPTY = "0004";
	public static final String BIZ_ERR_ACCOUNT_EMPTY_MSG = "账号不能为空";
	
}

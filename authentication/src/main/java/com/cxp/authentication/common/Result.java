package com.cxp.authentication.common;

public class Result {
	public final static int SUCCESS = 1;
	
	public final static int lOGIN__NOT_EMPTY = 2;
	public final static int lOGIN__CAPTCHA_ERR = 3;
	public final static int lOGIN__PASSWORD_ERR = 4;
	public final static int ERR = 5;
	
	/**未登录*/
	public final static int FILTER_NO_LOGIN = 10;
	/**在其他地方被强制登录*/
	public final static int FILTER_LOGIN_OTHER = 11;
	/**没有权限*/
	public final static int FILTER_NO_ACCESS = 12;
	/**强制退出*/
	public final static int FILTER_FORCE_LOGIN_OUT = 13;
	
	
	
	private int status;
	private String msg;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}

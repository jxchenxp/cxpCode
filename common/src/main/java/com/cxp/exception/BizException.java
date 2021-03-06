package com.cxp.exception;

public class BizException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String errMsg;
	
	public BizException(String code, String errMsg) {
		this.code = code;
		this.errMsg = errMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}

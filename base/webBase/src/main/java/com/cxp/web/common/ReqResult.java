/**
 * 
 */
package com.cxp.web.common;

/**
 * @author 2490
 *
 */
public class ReqResult {
	private String resCode;
	private String msg;
	private int size;
	private Object result;
	
	public ReqResult(String resCode) {
		this(resCode,null);
	}
	
	public ReqResult(String resCode, String msg) {
		this.resCode = resCode;
		this.msg = msg;
	}
	
	
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}

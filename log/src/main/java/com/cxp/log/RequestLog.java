package com.cxp.log;

import java.util.List;
import java.util.Map;

public class RequestLog {
	private String type;
	private String userInfo;
	private String ip;
	private String uri;
	private String params;
	private String visitTime;
	private String result;
	private List<BizUseTimeRecord> useTimeRecords;
	private Map<String,Integer> bizUseTimeMap;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<BizUseTimeRecord> getUseTimeRecords() {
		return useTimeRecords;
	}
	public void setUseTimeRecords(List<BizUseTimeRecord> useTimeRecords) {
		this.useTimeRecords = useTimeRecords;
	}
	public Map<String, Integer> getBizUseTimeMap() {
		return bizUseTimeMap;
	}
	public void setBizUseTimeMap(Map<String, Integer> bizUseTimeMap) {
		this.bizUseTimeMap = bizUseTimeMap;
	}
}

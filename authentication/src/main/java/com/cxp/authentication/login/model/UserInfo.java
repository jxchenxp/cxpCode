package com.cxp.authentication.login.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserInfo {
	private Account account;
	private Dept dept;
	
	private Set<String> grantUris = new HashSet<String>();
	
	private Map<String,Object> attributes = new HashMap<String,Object>();
	
	private String currentSessionId;
	private String currentLoginIp;
	private long currentLoginTime;
	
	private String plat;
	
	private int status;
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public void addAttribute(String key,Object obj){
		attributes.put(key, obj);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key){
		return (T)attributes.get(key);
	}
	public Set<String> getGrantUris() {
		return grantUris;
	}
	public void setGrantUris(Set<String> grantUris) {
		this.grantUris = grantUris;
	}
	public String getCurrentSessionId() {
		return currentSessionId;
	}
	public void setCurrentSessionId(String currentSessionId) {
		this.currentSessionId = currentSessionId;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public String getCurrentLoginIp() {
		return currentLoginIp;
	}
	public void setCurrentLoginIp(String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}
	public long getCurrentLoginTime() {
		return currentLoginTime;
	}
	public void setCurrentLoginTime(long currentLoginTime) {
		this.currentLoginTime = currentLoginTime;
	}
	public String getPlat() {
		return plat;
	}
	public void setPlat(String plat) {
		this.plat = plat;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}

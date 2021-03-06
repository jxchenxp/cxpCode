package com.cxp.module.system.authorization.pojo;

public class Account {
	private String account;
	private String name;
	private Integer deptId;
	private String deptName;
	private boolean useCheck;
	private boolean grantCheck;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public boolean isUseCheck() {
		return useCheck;
	}
	public void setUseCheck(boolean useCheck) {
		this.useCheck = useCheck;
	}
	public boolean isGrantCheck() {
		return grantCheck;
	}
	public void setGrantCheck(boolean grantCheck) {
		this.grantCheck = grantCheck;
	}
}

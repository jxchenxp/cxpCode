package com.cxp.module.system.authorization.pojo;

public class RoleQuery {
	private String name;
	private String useRoleGrantType;
	private String grantRoleGrantType;
	private String authPath;
	private String grantAccount;
	private String account;
	
	
	public String getGrantAccount() {
		return grantAccount;
	}
	public void setGrantAccount(String grantAccount) {
		this.grantAccount = grantAccount;
	}
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
	public String getUseRoleGrantType() {
		return useRoleGrantType;
	}
	public void setUseRoleGrantType(String useRoleGrantType) {
		this.useRoleGrantType = useRoleGrantType;
	}
	public String getGrantRoleGrantType() {
		return grantRoleGrantType;
	}
	public void setGrantRoleGrantType(String grantRoleGrantType) {
		this.grantRoleGrantType = grantRoleGrantType;
	}
	public String getAuthPath() {
		return authPath;
	}
	public void setAuthPath(String authPath) {
		this.authPath = authPath;
	}
}

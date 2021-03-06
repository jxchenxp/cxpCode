package com.cxp.module.system.authorization.pojo;

public class AccountQuery {
	private String account;
	private String name;
	private Integer deptId;
	private String useRoleGrantType;
	private String grantRoleGrantType;
	private String authPath;
	private int roleId;
	private String grantAccount;
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
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
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
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

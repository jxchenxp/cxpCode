package com.cxp.module.system.authorization.pojo;

public class MenuResource {
	private String project;
	private String name;
	private String menuCode;
	private String menuResourceCode;
	private String parentCode;
	private String icon;
	private boolean leaf;
	private boolean useCheck;
	private boolean useResourceByRole;
	private boolean grantCheck;
	private boolean grantResourceByRole;
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuResourceCode() {
		return menuResourceCode;
	}
	public void setMenuResourceCode(String menuResourceCode) {
		this.menuResourceCode = menuResourceCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isUseResourceByRole() {
		return useResourceByRole;
	}
	public void setUseResourceByRole(boolean useResourceByRole) {
		this.useResourceByRole = useResourceByRole;
	}
	public boolean isGrantResourceByRole() {
		return grantResourceByRole;
	}
	public void setGrantResourceByRole(boolean grantResourceByRole) {
		this.grantResourceByRole = grantResourceByRole;
	}
}

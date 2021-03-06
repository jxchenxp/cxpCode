package com.cxp.module.system.authorization.pojo;

public class Role {
	private int id;
	private String project;
	private String name;
	private String belongDeptName;
	private boolean useCheck;
	private boolean grantCheck;
	
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBelongDeptName() {
		return belongDeptName;
	}
	public void setBelongDeptName(String belongDeptName) {
		this.belongDeptName = belongDeptName;
	}
}

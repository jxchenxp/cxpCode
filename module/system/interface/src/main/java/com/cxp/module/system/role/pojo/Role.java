package com.cxp.module.system.role.pojo;

import java.util.Date;

public class Role {
	private Integer id;
	private Integer belongToDeptId;
	private String belongToDeptName;
	private String name;
	private Integer status;
	private String comment;
	private String creator;
	private String creatorName;
	private int creatorDeptId;
	private String creatorDeptName;
	private Date gmtCreate;
	private Date gmtModify;
	
	private boolean checked;
	
	private String authPath;
	private String authAccount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public int getCreatorDeptId() {
		return creatorDeptId;
	}
	public void setCreatorDeptId(int creatorDeptId) {
		this.creatorDeptId = creatorDeptId;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreatorDeptName() {
		return creatorDeptName;
	}
	public void setCreatorDeptName(String creatorDeptName) {
		this.creatorDeptName = creatorDeptName;
	}
	public String getAuthPath() {
		return authPath;
	}
	public void setAuthPath(String authPath) {
		this.authPath = authPath;
	}
	public String getAuthAccount() {
		return authAccount;
	}
	public void setAuthAccount(String authAccount) {
		this.authAccount = authAccount;
	}
	public Integer getBelongToDeptId() {
		return belongToDeptId;
	}
	public void setBelongToDeptId(Integer belongToDeptId) {
		this.belongToDeptId = belongToDeptId;
	}
	public String getBelongToDeptName() {
		return belongToDeptName;
	}
	public void setBelongToDeptName(String belongToDeptName) {
		this.belongToDeptName = belongToDeptName;
	}
}

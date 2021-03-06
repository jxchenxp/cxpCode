package com.cxp.module.system.account.pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class Account {
	@NotNull
	private String account;
	private String password;
	private String defaultPwd;
	@NotNull
	private String name;
	private String phone;
	private String email;
	private String gender;
	@NotNull
	private Integer deptId;
	private String deptName;
	private Integer status;
	private String salt;
	private Date gmtCreate;
	private Date gmtModify;
	private Date gmtPwdModify;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Date getGmtPwdModify() {
		return gmtPwdModify;
	}
	public void setGmtPwdModify(Date gmtPwdModify) {
		this.gmtPwdModify = gmtPwdModify;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDefaultPwd() {
		return defaultPwd;
	}
	public void setDefaultPwd(String defaultPwd) {
		this.defaultPwd = defaultPwd;
	}
}

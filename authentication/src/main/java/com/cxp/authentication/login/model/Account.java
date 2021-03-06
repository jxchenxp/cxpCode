package com.cxp.authentication.login.model;

import java.util.Date;

public class Account {
	private Integer id;
	private String account;
	private String password;
	private String name;
	private String phone;
	private String gender;
	private Integer deptId;
	private Integer status;
	private Date gmtPwdModify;
	private String salt;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Date getGmtPwdModify() {
		return gmtPwdModify;
	}
	public void setGmtPwdModify(Date gmtPwdModify) {
		this.gmtPwdModify = gmtPwdModify;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
}

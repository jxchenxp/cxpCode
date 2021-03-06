package com.cxp.authentication.login.service;

import javax.servlet.http.HttpSession;

import com.cxp.authentication.login.model.UserInfo;

public interface LoginedUserManager {
	void saveLoginUser(UserInfo userInfo, HttpSession session);
	void cleanLoginUser(UserInfo userInfo);
	UserInfo getUserInfoByAccount(String account,String plat);
}

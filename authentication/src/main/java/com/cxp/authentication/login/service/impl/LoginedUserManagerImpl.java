package com.cxp.authentication.login.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.cxp.authentication.login.model.UserInfo;
import com.cxp.authentication.login.service.LoginedUserManager;

public class LoginedUserManagerImpl implements LoginedUserManager{
	private Map<String,HttpSession> accountSessionMap = new ConcurrentHashMap<String, HttpSession>();
	private Map<String,UserInfo> accountUserInfo = new ConcurrentHashMap<String, UserInfo>();
	
	@Override
	public void saveLoginUser(UserInfo userInfo, HttpSession session) {
		//保存账号和session关系
		accountSessionMap.put(userInfo.getAccount().getAccount() + "_" + userInfo.getPlat(), session);
		//保存当前登录账号的信息
		accountUserInfo.put(userInfo.getAccount().getAccount() + "_" + userInfo.getPlat(), userInfo);
	}
	@Override
	public void cleanLoginUser(UserInfo userInfo) {
		accountSessionMap.remove(userInfo.getAccount().getAccount() + "_" + userInfo.getPlat());
		accountUserInfo.remove(userInfo.getAccount().getAccount() + "_" + userInfo.getPlat());
	}
	
	@Override
	public UserInfo getUserInfoByAccount(String account,String plat) {
		return accountUserInfo.get(account + "_" + plat);
	}

}

package com.cxp.authentication.login.service;

import com.cxp.authentication.login.service.impl.LoginedUserManagerImpl;

public class LoginedUserManagerFactory {
	private static LoginedUserManager instance = new LoginedUserManagerImpl();
	public static LoginedUserManager getLoginedUserManager(){
		return instance; 
	}
}

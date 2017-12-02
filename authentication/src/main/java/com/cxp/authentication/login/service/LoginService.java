package com.cxp.authentication.login.service;

import com.cxp.authentication.login.model.Account;
import com.cxp.authentication.login.model.UserInfo;

public interface LoginService {
	public UserInfo getUserInfo(Account account);
	public UserInfo getUserInfo(Account account,String plat);
}

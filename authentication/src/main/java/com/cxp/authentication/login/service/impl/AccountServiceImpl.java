package com.cxp.authentication.login.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cxp.authentication.login.dao.AccountDao;
import com.cxp.authentication.login.model.Account;
import com.cxp.authentication.login.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	@Resource
	AccountDao accountDao;
	
	@Override
	public Account getAccount(String account) {
		return accountDao.getAccount(account);
	}
}

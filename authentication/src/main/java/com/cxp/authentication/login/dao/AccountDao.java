package com.cxp.authentication.login.dao;

import com.cxp.authentication.login.model.Account;

public interface AccountDao {
	Account getAccount(String account);
}

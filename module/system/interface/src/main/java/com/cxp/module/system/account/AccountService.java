package com.cxp.module.system.account;

import com.cxp.module.system.account.pojo.Account;
import com.cxp.page.PageQry;
import com.cxp.page.PageResult;

public interface AccountService {
	void save(Account account);
	void remove(String account);
	void update(Account account);
	void updatePassword(String account, String newPassword);
	void updatePassword(String account, String oldPassword, String newPassword);
	Account getNotNull(String account);
	Account get(String account);
	PageResult<Account> list(PageQry<Account> query);
}

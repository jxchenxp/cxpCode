package com.cxp.module.system.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxp.module.system.account.pojo.Account;
import com.cxp.page.PageInfo;


public interface SystemAccountDao {
	int save(Account account);
	int remove(String account);
	int update(Account account);
	int updatePwd(Account account);
	Account get(String account);
	int count(@Param("acc") Account account);
	List<Account> list(@Param("acc") Account account, @Param("page") PageInfo pageInfo);
}

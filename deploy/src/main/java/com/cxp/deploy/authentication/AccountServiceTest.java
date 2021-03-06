package com.cxp.deploy.authentication;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cxp.ApplicationStart;
import com.cxp.authentication.login.model.Account;
import com.cxp.authentication.login.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApplicationStart.class)
public class AccountServiceTest {
	@Resource
	private AccountService service;
	
	@Test
	public void getAccount(){
		Account account = service.getAccount("cxp");
		System.out.println("account:" + account.getAccount() + "  " + account.getPhone() + " " + account.getGmtPwdModify());
	}
	
}

package com.cxp.module.system.account.impl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.cxp.exception.BizException;
import com.cxp.exception.NoFindException;
import com.cxp.module.system.account.AccountService;
import com.cxp.module.system.account.dao.SystemAccountDao;
import com.cxp.module.system.account.pojo.Account;
import com.cxp.page.PageQry;
import com.cxp.page.PageResult;
import com.cxp.password.PasswordEncoder;

@Service("moduleAccountServieImpl")
public class AccountServiceImpl implements AccountService{
	@Resource
	private SystemAccountDao systemAccountDao;
	
	@Resource
	PasswordEncoder passwordEncoder;
	

	@Override
	public void save(Account account) {
		//生成随机盐
		Random RANDOM = new SecureRandom();
		
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);    
        account.setSalt(Base64.encodeBase64String(salt));
        account.setGmtCreate(new Date());
        account.setGmtModify(new Date());
        account.setGmtPwdModify(new Date());
        account.setDefaultPwd("1");
        
        //处理不能为空的字段
        if(StringUtils.isEmpty(account.getPhone())){
        	account.setPhone(" ");
        }
        if(StringUtils.isEmpty(account.getEmail())){
        	account.setEmail(" ");
        }
        if(StringUtils.isEmpty(account.getGender())){
        	account.setGender("3");
        }
        
        
        //对密码加密
        if(passwordEncoder != null){
        	account.setPassword(passwordEncoder.encode(account.getPassword(), salt));
        }
        
		systemAccountDao.save(account);
	}

	@Override
	public void remove(String account) {
		systemAccountDao.remove(account);
	}

	@Override
	public void update(Account account) {
		account.setGmtModify(new Date());
		
		//处理不能为空的字段
        if(StringUtils.isEmpty(account.getPhone())){
        	account.setPhone(" ");
        }
        if(StringUtils.isEmpty(account.getEmail())){
        	account.setEmail(" ");
        }
        if(StringUtils.isEmpty(account.getGender())){
        	account.setGender("3");
        }
		
		systemAccountDao.update(account);
	}
	
	@Override
	public void updatePassword(String account, String newPassword) {
		Account acc = this.getNotNull(account);
		acc.setDefaultPwd("1");
		this.updatePassword(acc, newPassword);
	}

	@Override
	public void updatePassword(String account, String oldPassword, String newPassword) {
		Account acc = this.getNotNull(account);
		acc.setDefaultPwd("0");
		
		if(passwordEncoder != null){
			oldPassword = passwordEncoder.encode(oldPassword, acc.getSalt());
		}
		
		//判断两次密码是否一致
		if(acc.getPassword().equals(oldPassword)){
			throw new BizException("0012", "旧密码不正确");
		}
		
		this.updatePassword(acc, newPassword);
	}
	
	private void updatePassword(Account account, String newPassword) {
		//对密码加密
        if(passwordEncoder != null){
        	account.setPassword(passwordEncoder.encode(newPassword, Base64.decodeBase64(account.getSalt())));
        }
        account.setGmtPwdModify(new Date());
		
		systemAccountDao.updatePwd(account);
	}

	@Override
	public Account getNotNull(String account) {
		Account acc = this.get(account);
		if(acc == null){
			throw new NoFindException("账号",account);
		}
		return acc;
	}

	@Override
	public Account get(String account) {
		return systemAccountDao.get(account);
	}

	@Override
	public PageResult<Account> list(PageQry<Account> query){
		PageResult<Account> result = new PageResult<Account>();
		int size = systemAccountDao.count(query.getQuery());
		result.setTotalSize(size);
		
		if(size > 0){
			result.setResult(systemAccountDao.list(query.getQuery(),query.getPage()));
		}else{
			result.setResult(new ArrayList<Account>(0));
		}
		return result;
	}

	
}

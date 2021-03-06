package com.cxp.authentication.login.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.Account;
import com.cxp.authentication.login.model.Dept;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.authentication.login.service.DeptService;
import com.cxp.authentication.login.service.LoadAttribute;
import com.cxp.authentication.login.service.LoginService;
import com.cxp.authentication.menu.service.MenuSerice;
import com.cxp.project.config.ProjectConfig;
import com.cxp.util.spring.SpringContextUtil;

@Service
public class LoginServiceImpl implements LoginService{
	@Resource
	DeptService deptService;

	@Resource
	ProjectConfig config;
	
	@Resource MenuSerice menuService;
	
	@Override
	public UserInfo getUserInfo(Account account) {
		return getUserInfo(account,Consts.PLAT_WEB);
	}
	
	@Override
	public UserInfo getUserInfo(Account account, String plat) {
		UserInfo userInfo = new UserInfo();
		userInfo.setAccount(account);
		
		Dept dept = deptService.getDept(account.getDeptId());
		userInfo.setDept(dept);
		
		//加载权限
		List<String> uris = menuService.listUri(config.getCode(), account.getAccount(), plat);
		Set<String> urisSet = new HashSet<String>();
		if(uris != null){
			for(String item : uris){
				urisSet.add(item); 
			}
		}
		userInfo.setGrantUris(urisSet);
		
		//加载其他属性
		ApplicationContext context = SpringContextUtil.getSpringContext();
		Map<String,LoadAttribute> loadAttributes = context.getBeansOfType(LoadAttribute.class);
		for(Entry<String, LoadAttribute> item : loadAttributes.entrySet()){
			userInfo.addAttribute(item.getValue().key(), item.getValue().loadAttribute(plat));
		}
		
		return userInfo;
	}

}

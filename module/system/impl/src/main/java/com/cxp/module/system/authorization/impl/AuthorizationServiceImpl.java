package com.cxp.module.system.authorization.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxp.module.system.authorization.AuthorizationService;
import com.cxp.module.system.authorization.dao.AuthorizationDao;
import com.cxp.module.system.authorization.pojo.Account;
import com.cxp.module.system.authorization.pojo.AccountQuery;
import com.cxp.module.system.authorization.pojo.MenuResource;
import com.cxp.module.system.authorization.pojo.Role;
import com.cxp.module.system.authorization.pojo.RoleQuery;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{
	@Resource
	private AuthorizationDao authorizationDao;

	@Override
	public List<MenuResource> listResourceForRole(String project, int roleId, String granteAccount) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("project", project);
		param.put("roleId", roleId);
		param.put("granteAccount", granteAccount);
		return authorizationDao.listResourceForRole(param);
	}
	
	@Override
	@Transactional
	public void grantResourceForRole(String project, int roleId, String codes,String resources, String granteAccount, String grantCodes, String grantResurces) {
		this.grantResourceForRole(project, roleId, grantCodes, grantResurces, granteAccount, MENU_RESOURCE_TYPE_GRANT);
		this.grantResourceForRole(project, roleId, codes, resources, granteAccount, Menu_RESOURCE_TYPE_USE);
	}
	
	@Override
	@Transactional
	public void grantResourceForRole(String project, int roleId, String codes,String resources, String grantAccount, String type) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("project", project);
		param.put("roleId", roleId);
		
		//验证登录用户是否有分配的角色权限
		param.put("account", grantAccount);//查询果使用授权账号
		List<Role> grantRoleList = authorizationDao.listGrantRoleForAccount(param);
		if(grantRoleList.size() < 1){
			new IllegalArgumentException("没有角色"+ roleId +"授权权限"); 
		}
		
		//加载授权用户拥有的所有权限信息,用于校验是否有不能授权的权限
		param.put("account", grantAccount);//使用授予权账号查询
		List<MenuResource> allGrantResource = authorizationDao.listAllGrantResourceForAccount(param);
		
		//转换成set，方便比较
		Set<String> allGrantResourceSet = new HashSet<String>();
		if(allGrantResource != null){
			for(MenuResource resource : allGrantResource){
				allGrantResourceSet.add(resource.getMenuCode() + "_" + resource.getMenuResourceCode());
			}
		}
		
		//组装资源list对象及校验是否有权限分配
		String[] menuCodes = StringUtils.isEmpty(codes) ? new String[]{} : codes.split(";");
		String[] menuResourceCodes = StringUtils.isEmpty(resources) ? new String[]{} : resources.split(";");
		if(menuCodes.length != menuResourceCodes.length){
			throw new IllegalArgumentException("menuCode和menuResourceCode的长度不一致");
		}
		List<MenuResource> resourceList = new LinkedList<MenuResource>();
		MenuResource resource;
		int len = menuCodes.length;
		for(int i = 0; i < len; i++){
			if(allGrantResourceSet.contains(menuCodes[i] + "_" + menuResourceCodes[i])){
				resource = new MenuResource();
				resource.setMenuCode(menuCodes[i]);
				resource.setMenuResourceCode(menuResourceCodes[i]);
				resourceList.add(resource);
			}else{
				throw new IllegalArgumentException("没有资源" + menuCodes[i] + "_" + menuResourceCodes[i] + "的授权权限");
			}
		}
		
		
		//先删除角色拥有的权限
		if(isGrantType(type)){
			authorizationDao.removeGrantResourceForRole(param);
		}else{
			authorizationDao.removeResourceForRole(param);
		}
		
		//重新分配权限
		if(resourceList.size() > 0){
			param.put("resourceList", resourceList);
			if(isGrantType(type)){
				authorizationDao.grantGrantResourceForRole(param);
			}else{
				authorizationDao.grantResourceForRole(param);
			}
		}
	}

	@Override
	public List<MenuResource> listResourceForAccount(String project, String account, String granteAccount) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("project", project);
		param.put("account", account);
		param.put("granteAccount", granteAccount);
		return authorizationDao.listResourceForAccount(param);
	}

	
	@Override
	@Transactional
	/**
	 * 先采用删除所有角色，再增加分配的角色，unRoleIds、grantUnRoleIds暂不使用
	 */
	public void grantRoleForAccount(String account, String roleIds, String unRoleIds, String grantAccount, String grantDeptPath, String grantRoleIds, String grantUnRoleIds, String type) {
		Map<String,Object> param = new HashMap<String, Object>();
		
		//验证账号是否可以分配角色
		param.put("pathLength", grantDeptPath.length());
		param.put("path", grantDeptPath);
		LinkedList<String> accountList = new LinkedList<String>();
		accountList.add(account);
		param.put("accountList", accountList);
		List<String> noAccAccount = authorizationDao.listNoSubDeptAccount(param);
		if(noAccAccount.size() > 0){
			throw new IllegalArgumentException("没有账号"+ account +"授权权限");
		}
		param.clear();
		
		
		//加载可授权权限
		param.put("account", grantAccount);//查询果使用授权账号
		List<Role> grantRoleList = authorizationDao.listGrantRoleForAccount(param);
		
		param.put("account", account);//插入时使用被授权账号
		
		//转换成Set,方便比较
		Set<Integer> grantRoleSet = new HashSet<Integer>();
		if(grantRoleList != null){
			for(Role role : grantRoleList){
				grantRoleSet.add(role.getId());
			}
		}
		
		
		//
		String[] roleIdArr = StringUtils.isEmpty(roleIds) ? new String[]{} : roleIds.split(";");
		//String[] unRoleIdArr = StringUtils.isEmpty(unRoleIds) ? new String[]{} : unRoleIds.split(";");
		String[] grantRoleIdArr = StringUtils.isEmpty(grantRoleIds) ? new String[]{} : grantRoleIds.split(";");
		//String[] grantUnRoleIdArr = StringUtils.isEmpty(grantUnRoleIds) ? new String[]{} : grantUnRoleIds.split(";");
		List<Integer> roleIdList = new LinkedList<Integer>();
		int roleId = 0;
		
		//使用权限处理
		if(isUseType(type) || StringUtils.isBlank(type)){
			//处理删除角色
			/*for(String roleIdStr : unRoleIdArr){
				roleId = Integer.parseInt(roleIdStr);
				if(grantRoleSet.contains(roleId)){
					roleIdList.add(roleId);
				}else{
					throw new IllegalArgumentException("没有角色"+ roleId +"授权权限");
				}
			}*/
			//if(roleIdList.size() > 0){
				//param.put("roleIdList", roleIdList);
				authorizationDao.removeRoleForAccount(param);
			//}
			
			
			//处理角色分配
			roleIdList.clear();
			for(String roleIdStr : roleIdArr){
				if(StringUtils.isBlank(roleIdStr)){
					continue;
				}
				roleId = Integer.parseInt(roleIdStr);
				if(grantRoleSet.contains(roleId)){
					roleIdList.add(roleId);
				}else{
					throw new IllegalArgumentException("没有角色"+ roleId +"授权权限");
				}
			}
			if(roleIdList.size() > 0){
				param.put("roleIdList", roleIdList);
				authorizationDao.grantRoleForAccount(param);
			}
		}
		
		//分配权限处理
		if(isGrantType(type) || StringUtils.isBlank(type)){
			//处理删除角色(授权)
			/*roleIdList.clear();
			for(String roleIdStr : grantUnRoleIdArr){
				roleId = Integer.parseInt(roleIdStr);
				if(grantRoleSet.contains(roleId)){
					roleIdList.add(roleId);
				}else{
					throw new IllegalArgumentException("没有角色"+ roleId +"授权权限");
				}
			}*/
			//if(roleIdList.size() > 0){
				//param.put("roleIdList", roleIdList);
				authorizationDao.removeGrantRoleForAccount(param);
			//}
			
			
			//处理角色分配(授权)
			roleIdList.clear();
			for(String roleIdStr : grantRoleIdArr){
				if(StringUtils.isBlank(roleIdStr)){
					continue;
				}
				roleId = Integer.parseInt(roleIdStr);
				if(grantRoleSet.contains(roleId)){
					roleIdList.add(roleId);
				}else{
					throw new IllegalArgumentException("没有角色"+ roleId +"授权权限");
				}
			}
			if(roleIdList.size() > 0){
				param.put("roleIdList", roleIdList);
				authorizationDao.grantGrantRoleForAccount(param);
			}
		}
	}
	
	@Override
	@Transactional
	public void grantResourceForAccount(String project,String account, String codes,String resources, String granteAccount, String grantDeptPath, String grantCodes, String grantResources, String type) {
		if(isGrantType(type)){
			this.grantResourceForAccount(project, account, grantCodes, grantResources, granteAccount, grantDeptPath,MENU_RESOURCE_TYPE_GRANT);
		}else if(isUseType(type)){
			this.grantResourceForAccount(project, account, codes, resources, granteAccount, grantDeptPath, Menu_RESOURCE_TYPE_USE);
		}else if(StringUtils.isBlank(type)){
			this.grantResourceForAccount(project, account, codes, resources, granteAccount, grantDeptPath, Menu_RESOURCE_TYPE_USE);
			this.grantResourceForAccount(project, account, grantCodes, grantResources, granteAccount, grantDeptPath,MENU_RESOURCE_TYPE_GRANT);
		}else{
			throw new RuntimeException("账号分配资源时类型出错type【"+ type +"】");
		}
	}

	private void grantResourceForAccount(String project,String account, String codes,String resources, String granteAccount, String grantDeptPath, String type) {
		Map<String,Object> param = new HashMap<String, Object>();
		
		//验证账号是否可以分配资源
		param.put("pathLength", grantDeptPath.length());
		param.put("path", grantDeptPath);
		LinkedList<String> accountList = new LinkedList<String>();
		accountList.add(account);
		param.put("accountList", accountList);
		List<String> noAccAccount = authorizationDao.listNoSubDeptAccount(param);
		if(noAccAccount.size() > 0){
			throw new IllegalArgumentException("没有账号"+ account +"授权权限");
		}
		param.clear();
		
		param.put("project", project);
		//加载授权用户拥有的所有权限信息,用于校验是否有不能授权的权限
		param.put("account", granteAccount);//使用授权账号查询授权账号权限
		List<MenuResource> allGrantResource = authorizationDao.listAllGrantResourceForAccount(param);
		
		param.put("account", account);//使用被授权账号
		
		//转换成set，方便比较
		Set<String> allGrantResourceSet = new HashSet<String>();
		if(allGrantResource != null){
			for(MenuResource resource : allGrantResource){
				allGrantResourceSet.add(resource.getMenuCode() + "_" + resource.getMenuResourceCode());
			}
		}
		
		//组装资源list对象及校验是否有权限分配
		String[] menuCodes = StringUtils.isEmpty(codes) ? new String[]{} : codes.split(";");
		String[] menuResourceCodes = StringUtils.isEmpty(resources) ? new String[]{} : resources.split(";");
		if(menuCodes.length != menuResourceCodes.length){
			throw new IllegalArgumentException("menuCode和menuResourceCode的长度不一致");
		}
		List<MenuResource> resourceList = new LinkedList<MenuResource>();
		MenuResource resource;
		int len = menuCodes.length;
		for(int i = 0; i < len; i++){
			if(allGrantResourceSet.contains(menuCodes[i] + "_" + menuResourceCodes[i])){
				resource = new MenuResource();
				resource.setMenuCode(menuCodes[i]);
				resource.setMenuResourceCode(menuResourceCodes[i]);
				resourceList.add(resource);
			}else{
				throw new IllegalArgumentException("没有资源" + menuCodes[i] + "_" + menuResourceCodes[i] + "的授权权限");
			}
		}
		
		//先删除用户拥有的权限
		if(isGrantType(type)){
			authorizationDao.removeGrantResourceForAccount(param);
		}else{
			authorizationDao.removeResourceForAccount(param);
		}
		
		//重新分配权限
		if(resourceList.size() > 0){
			param.put("resourceList", resourceList);
			if(isGrantType(type)){
				authorizationDao.grantGrantResourceForAccount(param);
			}else{
				authorizationDao.grantResourceForAccount(param);
			}
		}
	}
	
	
	
	@Override
	public List<MenuResource> listAllResourceForAccount(String account, String project) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("project", project);
		param.put("account", account);
		return authorizationDao.listAllResourceForAccount(param);
	}
	
	@Override
	public PageResult<Account> listAccountForRole(AccountQuery account, PageInfo page) {
		account.setAuthPath(account.getAuthPath() + "%");
		
		PageResult<Account> result = new PageResult<Account>();
		int size = authorizationDao.countAccountForRole(account);
		result.setTotalSize(size);
		
		if(size > 0){
			result.setResult(authorizationDao.listAccountForRole(account, page));
		}else{
			result.setResult(new ArrayList<Account>(0));
		}
		return result;
	}
	
	@Override
	public PageResult<Role> listRoleForAccount(RoleQuery role, PageInfo page) {
		role.setAuthPath(role.getAuthPath() + "%");
		
		PageResult<Role> result = new PageResult<Role>();
		/*int size = authorizationDao.countRoleForAccount(role);
		result.setTotalSize(size);
		
		if(size > 0){
			result.setResult(authorizationDao.listRoleForAccount(role, page));
		}else{
			result.setResult(new ArrayList<Role>(0));
		}*/
		result.setResult(authorizationDao.listRoleForAccount(role, page));
		return result;
	}
	
	@Override
	public void grantAccountForRole(int roleId, String ids, String unids, String grantAccount,String grantDeptPath,
			String grantIds, String grantUnids, String type) {
		if(isGrantType(type)){
			grantAccountForRole(roleId,grantAccount, grantDeptPath,grantIds,grantUnids,type);
		}else if(isUseType(type)){
			grantAccountForRole(roleId,grantAccount, grantDeptPath,ids,unids,type);
		}else if(StringUtils.isBlank(type)){
			grantAccountForRole(roleId,grantAccount, grantDeptPath,grantIds,grantUnids,MENU_RESOURCE_TYPE_GRANT);
			grantAccountForRole(roleId,grantAccount, grantDeptPath,ids,unids,Menu_RESOURCE_TYPE_USE);
		}else{
			throw new RuntimeException("角色分配账号时类型出错type【"+ type +"】");
		}
	}
	
	public void grantAccountForRole(int roleId, String grantAccount, String grantDeptPath, String ids, String unids,String type) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);//插入时使用被授权账号
		
		//验证登录用户是否有分配的角色权限
		param.put("account", grantAccount);//查询果使用授权账号
		List<Role> grantRoleList = authorizationDao.listGrantRoleForAccount(param);
		if(grantRoleList.size() < 1){
			new IllegalArgumentException("没有角色"+ roleId +"授权权限"); 
		}
		
		//
		String[] accountArr = StringUtils.isEmpty(ids) ? new String[]{} : ids.split(";");
		String[] unaccountArr = StringUtils.isEmpty(unids) ? new String[]{} : unids.split(";");
		List<String> accountList = new LinkedList<String>();
		
		//验证账号是否可以分配角色
		Map<String,Object> param2 = new HashMap<String, Object>();
		param2.put("pathLength", grantDeptPath.length());
		param2.put("path", grantDeptPath);
		LinkedList<String> accList = new LinkedList<String>();
		for(String account : unaccountArr){
			if(StringUtils.isBlank(account)){
				continue;
			}
			accList.add(account);
		}
		for(String account : accountArr){
			if(StringUtils.isBlank(account)){
				continue;
			}
			accList.add(account);
		}
		param2.put("accountList", accList);
		List<String> noAccAccount = authorizationDao.listNoSubDeptAccount(param2);
		if(noAccAccount.size() > 0){
			StringBuilder err = new StringBuilder();
			for(String item : noAccAccount){
				err.append(item + ",");
			}
			throw new IllegalArgumentException("没有账号"+ err.toString() +"授权权限");
		}
		param2 = null;
		
		//处理删除账号
		for(String account : unaccountArr){
			if(StringUtils.isBlank(account)){
				continue;
			}
			accountList.add(account);
		}
		if(accountList.size() > 0){
			param.put("accountList", accountList);
			if(isUseType(type)){
				authorizationDao.removeAccountForRole(param);
			}else{
				authorizationDao.removeAccountForGrantRole(param);
			}
		}
		
		//处理账号分配
		accountList.clear();
		for(String account : accountArr){
			if(StringUtils.isBlank(account)){
				continue;
			}
			accountList.add(account);
		}
		if(accountList.size() > 0){
			param.put("accountList", accountList);
			if(isUseType(type)){
				authorizationDao.grantAccountForRole(param);
			}else{
				authorizationDao.grantAccountForGrantRole(param);
			}
		}
	}
	
	private boolean isGrantType(String type){
		return MENU_RESOURCE_TYPE_GRANT.equals(type);
	}
	private boolean isUseType(String type){
		return Menu_RESOURCE_TYPE_USE.equals(type);
	}
}

package com.cxp.module.system.authorization.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cxp.module.system.authorization.pojo.Account;
import com.cxp.module.system.authorization.pojo.AccountQuery;
import com.cxp.module.system.authorization.pojo.MenuResource;
import com.cxp.module.system.authorization.pojo.Role;
import com.cxp.module.system.authorization.pojo.RoleQuery;
import com.cxp.page.PageInfo;

public interface AuthorizationDao {
	List<MenuResource> listResourceForRole(Map<String,Object> param);
	void grantResourceForRole(Map<String,Object> param);
	void removeResourceForRole(Map<String,Object> param);
	void grantGrantResourceForRole(Map<String,Object> param);
	void removeGrantResourceForRole(Map<String,Object> param);
	List<MenuResource> listResourceForAccount(Map<String,Object> param);
	void grantResourceForAccount(Map<String,Object> param);
	void removeResourceForAccount(Map<String,Object> param);
	void grantGrantResourceForAccount(Map<String,Object> param);
	void removeGrantResourceForAccount(Map<String,Object> param);
	List<Role> listRoleForAccount(@Param("role") RoleQuery role, @Param("page") PageInfo page);
	int countRoleForAccount(@Param("role") RoleQuery role);
	void grantRoleForAccount(Map<String,Object> param);
	void removeRoleForAccount(Map<String,Object> param);
	void grantGrantRoleForAccount(Map<String,Object> param);
	void removeGrantRoleForAccount(Map<String,Object> param);
	List<MenuResource> listAllResourceForAccount(Map<String,Object> param);
	List<MenuResource> listAllGrantResourceForAccount(Map<String,Object> param);
	List<Role> listGrantRoleForAccount(Map<String,Object> param);
	
	List<Account> listAccountForRole(@Param("acc") AccountQuery account, @Param("page") PageInfo page);
	void removeAccountForRole(Map<String,Object> param);
	void removeAccountForGrantRole(Map<String,Object> param);
	void grantAccountForRole(Map<String,Object> param);
	void grantAccountForGrantRole(Map<String,Object> param);
	int countAccountForRole(@Param("acc") AccountQuery account);
	List<String> listNoSubDeptAccount(Map<String,Object> param);
}

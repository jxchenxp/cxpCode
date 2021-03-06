package com.cxp.module.system.authorization;

import java.util.List;

import com.cxp.module.system.authorization.pojo.Account;
import com.cxp.module.system.authorization.pojo.AccountQuery;
import com.cxp.module.system.authorization.pojo.MenuResource;
import com.cxp.module.system.authorization.pojo.Role;
import com.cxp.module.system.authorization.pojo.RoleQuery;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;

public interface AuthorizationService {
	String MENU_RESOURCE_TYPE_GRANT = "grant";
	String Menu_RESOURCE_TYPE_USE = "use";
	
	List<MenuResource> listResourceForRole(String project, int roleId, String granteAccount);
	void grantResourceForRole(String project, int roleId, String codes,String resources, String grantAccount, String grantCodes, String grantResurces);
	void grantResourceForRole(String project, int roleId, String codes, String resources, String grantAccount, String type);
	List<MenuResource> listResourceForAccount(String project, String account, String granteAccount);
	void grantResourceForAccount(String project,String account, String codes, String resources, String grantAccount,String grantDeptPath, String grantCodes, String grantResurces, String type);
	void grantRoleForAccount(String account, String roleIds, String unRoleIds, String granteAccount,String grantDeptPath, String grantRoleIds, String grantUnRoleIds, String type);
	
	List<MenuResource> listAllResourceForAccount(String account, String project);
	
	/**
	 * 给账号分配角色时，加载角色列表
	 * @param role
	 * @param page
	 * @return
	 */
	PageResult<Role> listRoleForAccount(RoleQuery role, PageInfo page);
	/**
	 * 给角色分配账号时，加载账号列表
	 * @param account
	 * @param page
	 * @return
	 */
	PageResult<Account> listAccountForRole(AccountQuery account, PageInfo page);
	/**
	 * 为角色分配账号
	 * @param roleId
	 * @param roleIds
	 * @param unRoleIds
	 * @param granteAccount
	 * @param grantRoleIds
	 * @param grantUnRoleIds
	 * @param type 
	 */
	void grantAccountForRole(int roleId, String ids, String unids, String grantAccount,String grantDeptPath, String grantIds, String grantUnids, String type);
}

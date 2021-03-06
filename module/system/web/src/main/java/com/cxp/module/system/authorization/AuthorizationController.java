package com.cxp.module.system.authorization;

import static com.cxp.web.common.ReqUtils.success;
import static com.cxp.web.common.ReqUtils.listPage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.constructor.BaseConstructor;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.module.system.authorization.pojo.Account;
import com.cxp.module.system.authorization.pojo.AccountQuery;
import com.cxp.module.system.authorization.pojo.MenuResource;
import com.cxp.module.system.authorization.pojo.Role;
import com.cxp.module.system.authorization.pojo.RoleQuery;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;
import com.cxp.web.common.ReqResult;

@Controller
public class AuthorizationController extends BaseConstructor{
	
	@Resource
	private AuthorizationService authorizationService;
	
	@RequestMapping("/listResourceForRole")
	@ResponseBody
	public List<MenuResource> listResourceForRole(String project, Integer roleId,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		List<MenuResource> list = authorizationService.listResourceForRole(project, roleId, userInfo.getAccount().getAccount());
		return list;
	}
	
	@RequestMapping("/grantRoleForAccount")
	@ResponseBody
	public ReqResult grantRoleForAccount(HttpServletRequest request, String project, String account, String roleIds, String unRoleIds, String grantRoleIds, String grantUnRoleIds, String type){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		authorizationService.grantRoleForAccount(account, roleIds, unRoleIds, userInfo.getAccount().getAccount(), userInfo.getDept().getPath(), grantRoleIds, grantUnRoleIds,type);
		return success();
	}
	
	@RequestMapping("/grantResourceForRole")
	@ResponseBody
	public ReqResult grantResourceForRole(HttpServletRequest request, String project, Integer roleId, String menuCodes, String menuResourceCodes, String grantMenuCodes, String grantMenuResourceCodes){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		authorizationService.grantResourceForRole(project, roleId, menuCodes, menuResourceCodes, userInfo.getAccount().getAccount(), grantMenuCodes, grantMenuResourceCodes);
		return success();
	}
	
	@RequestMapping("/listResourceForAccount")
	@ResponseBody
	public List<MenuResource> listResourceForAccount(String project, String account,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		List<MenuResource> list = authorizationService.listResourceForAccount(project, account, userInfo.getAccount().getAccount());
		return list;
	}
	
	@RequestMapping("/grantResourceForAccount")
	@ResponseBody
	public ReqResult grantResourceForAccount(HttpServletRequest request, String project,String account , String menuCodes, String menuResourceCodes,  String grantMenuCodes, String grantMenuResourceCodes, String type){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		authorizationService.grantResourceForAccount(project,account, menuCodes, menuResourceCodes, userInfo.getAccount().getAccount(), userInfo.getDept().getPath(), grantMenuCodes, grantMenuResourceCodes, type);
		return success();
	}
	
	@RequestMapping("/listAllResource")
	@ResponseBody
	public List<MenuResource> listAllResource(HttpServletRequest request, String project){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		
		List<MenuResource> list = authorizationService.listAllResourceForAccount(userInfo.getAccount().getAccount(), project);
		return list;
	}
	
	@RequestMapping("/listRoleForAccount")
	@ResponseBody
	public ReqResult listRoleForAccount(RoleQuery role, PageInfo page,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		role.setAuthPath(userInfo.getDept().getPath());
		role.setGrantAccount(userInfo.getAccount().getAccount());
		
		PageResult<Role> result = authorizationService.listRoleForAccount(role, page);
		return listPage(result);
	}
	
	@RequestMapping("/listAccountForRole")
	@ResponseBody
	public ReqResult listAccountForRole(AccountQuery account, PageInfo page,HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		account.setAuthPath(userInfo.getDept().getPath());
		account.setGrantAccount(userInfo.getAccount().getAccount());
		
		PageResult<Account> result = authorizationService.listAccountForRole(account, page);
		return listPage(result);
	}
	
	@RequestMapping("/grantAccountForRole")
	@ResponseBody
	public ReqResult grantAccountForRole(HttpServletRequest request, int roleId, String ids, String unids, String grantIds, String grantUnids, String type){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		authorizationService.grantAccountForRole(roleId, ids, unids, userInfo.getAccount().getAccount(), userInfo.getDept().getPath(), grantIds, grantUnids,type);
		return success();
	}
}

package com.cxp.module.system.role;

import static com.cxp.web.common.ReqUtils.listPage;
import static com.cxp.web.common.ReqUtils.success;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.module.system.role.pojo.Role;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;
import com.cxp.web.common.ReqResult;

@RestController
@RequestMapping("/system/role")
public class RoleController {
	@Resource
	private RoleService roleService;
	
	@RequestMapping("/save")
	public ReqResult save(HttpServletRequest request,@Validated Role role){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		
		role.setCreator(userInfo.getAccount().getAccount());
		role.setCreatorDeptId(userInfo.getDept().getId());
		
		roleService.save(role);
		return success();
	}
	
	@RequestMapping("/remove")
	public ReqResult remove(int id){
		roleService.remove(id);
		return success();
	}
	
	@RequestMapping("/update")
	public ReqResult update(@Validated Role role){
		roleService.update(role);
		return success();
	}
	
	@RequestMapping("/list")
	public ReqResult list(Role role, PageInfo page, HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		role.setAuthPath(userInfo.getDept().getPath());
		role.setAuthAccount(userInfo.getAccount().getAccount());
		
		PageResult<Role> result = roleService.list(role, page);
		return listPage(result);
	}
	
	@RequestMapping("/get")
	public Role get(int id){
		Role role = roleService.getNotNull(id);
		return role;
	}
}

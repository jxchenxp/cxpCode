package com.cxp.module.system.common;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.module.system.dept.DeptService;
import com.cxp.module.system.dept.pojo.Dept;
import com.cxp.module.system.dept.pojo.DeptQuery;

@RestController
public class CommonController {
	@Resource
	private DeptService deptService;
	
	@RequestMapping("/listSubDept")
	public List<Dept> listSubDept(HttpServletRequest request, DeptQuery dept){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		
		dept.setAuthPath(userInfo.getDept().getPath());
		List<Dept> list = deptService.listSubDept(dept);
		return list;
	}
}

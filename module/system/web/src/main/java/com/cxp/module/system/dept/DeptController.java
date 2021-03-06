package com.cxp.module.system.dept;

import static com.cxp.web.common.ReqUtils.success;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.module.system.dept.pojo.Dept;
import com.cxp.module.system.dept.pojo.DeptQuery;
import com.cxp.page.PageInfo;
import com.cxp.page.PageQry;
import com.cxp.page.PageResult;
import com.cxp.web.common.ReqResult;

@RestController
@RequestMapping("/system/dept")
public class DeptController {
	@Resource
	private DeptService deptService;
	
	@RequestMapping("/list")
	public PageResult<Dept> list(HttpServletRequest request, DeptQuery dept,PageInfo page){
		PageQry<DeptQuery> query = new PageQry<DeptQuery>();
		query.setPage(page);
		query.setQuery(dept);
		
		//增加数据权限过滤
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		dept.setAuthPath(user.getDept().getPath());
		
		return deptService.list(query);
	}
	
	@RequestMapping("/save")
	public ReqResult save(HttpServletRequest request,@Validated Dept dept){
		deptService.save(dept);
		return success();
	} 
	
	@RequestMapping("/update")
	public ReqResult update(HttpServletRequest request,@Validated Dept dept){
		deptService.update(dept);
		return success();
	}
	
	@RequestMapping("/disable")
	public ReqResult disable(HttpServletRequest request,int id){
		Dept dept = new Dept();
		dept.setId(id);
		dept.setStatus(2);
		deptService.update(dept);
		return success();
	}
	
	@RequestMapping("/enable")
	public ReqResult enable(HttpServletRequest request,int id){
		Dept dept = new Dept();
		dept.setId(id);
		dept.setStatus(1);
		deptService.update(dept);
		return success();
	}
	
	@RequestMapping("/remove")
	public ReqResult remove(int id){
		deptService.remove(id);
		return success();
	}
	
	@RequestMapping("/get")
	public Dept get(int id){
		return deptService.get(id);
	}
}

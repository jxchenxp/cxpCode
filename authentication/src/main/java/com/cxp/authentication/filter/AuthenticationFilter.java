package com.cxp.authentication.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cxp.authentication.common.Result;
import com.cxp.authentication.config.AuthenticationConfigWrap;
import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.authentication.login.service.LoginedUserManagerFactory;
import com.cxp.log.RequestLogRecord;
import com.cxp.util.http.HttpUtils;

@WebFilter(filterName="authenticationFilter")
public class AuthenticationFilter implements Filter{
	@Resource
	private AuthenticationConfigWrap config;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		//验证是否是静态资源
		if((Boolean)arg0.getAttribute(Consts.IS_STATIC_RESOURCE)){
			arg2.doFilter(arg0, arg1);
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;
		
		String servletPath = req.getServletPath();
		Result result = new Result();
		result.setStatus(Result.SUCCESS);
		
		HttpSession session = req.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		
		//请求日志，增加操作人信息
		if(userInfo != null){
			RequestLogRecord.getInstance().setLoginUserInfo(userInfo.getAccount().getAccount());
		}
		
		//不需要登录验证
		if(config.getNoLoginAccess().contains(servletPath)){
			arg2.doFilter(arg0, arg1);
			return;
		}
		
		//验证是否登录;
		if(userInfo == null){
			RequestLogRecord.getInstance().changeLogType("noLogin");
			result.setStatus(Result.FILTER_NO_LOGIN);
			result.setMsg("未登录，请先登录");
		}
		
		//验证是否被强制退出
		if(result.getStatus() == Result.SUCCESS && userInfo.getStatus() == Consts.LOGIN_STATUS_FORCE_LOGIN_OUT){
			//账号被强制退出
			result.setStatus(Result.FILTER_FORCE_LOGIN_OUT);
			result.setMsg("账号已在被强制退出，请重新登录");
			//销毁session
			LoginedUserManagerFactory.getLoginedUserManager().cleanLoginUser(userInfo);
		}
		
		//验证是否只能一处登录
		if(result.getStatus() == Result.SUCCESS && config.isLoginOne()){
			if(userInfo.getStatus() == Consts.LOGIN_STATUS_LOGIN_BY_SOMEONE){
				//账号已在其他地方登录，请重新登录
				UserInfo userInfoLogined = LoginedUserManagerFactory.getLoginedUserManager().getUserInfoByAccount(userInfo.getAccount().getAccount(), userInfo.getPlat());
				result.setStatus(Result.FILTER_LOGIN_OTHER);
				result.setMsg("账号已在" + userInfoLogined.getCurrentLoginIp() + "登录，请重新登录");
				//销毁session
				LoginedUserManagerFactory.getLoginedUserManager().cleanLoginUser(userInfo);
			}
		}
		
		//不需要权限验证
		if(result.getStatus() == Result.SUCCESS && config.getNoAccess().contains(servletPath)){
			arg2.doFilter(arg0, arg1);
			return;
		}
		//验证是否有权限
		if(result.getStatus() == Result.SUCCESS && !userInfo.getGrantUris().contains(servletPath)){
			RequestLogRecord.getInstance().changeLogType("noAccess");
			result.setStatus(Result.FILTER_NO_ACCESS);
			result.setMsg("账号没有权限");
		}
		
		if(result.getStatus() == Result.SUCCESS ){
			arg2.doFilter(arg0, arg1);
		}else{
			String contextPath = req.getContextPath();
			//返回客户端
			if(HttpUtils.isAjax(req)){
				res.setContentType("application/json");
				String jsonStr = "{\"resCode\":\""+ result.getStatus() +"\",\"msg\":\""+ result.getMsg() +"\"}";
				res.getOutputStream().write(jsonStr.getBytes());
				res.flushBuffer();
			}else{
				if(result.getStatus() == Result.FILTER_NO_ACCESS){
					res.sendRedirect(contextPath + "/noAccess?code=" + result.getStatus());
				}else{
					res.sendRedirect(contextPath + "/noLogin?code=" + result.getStatus());
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}

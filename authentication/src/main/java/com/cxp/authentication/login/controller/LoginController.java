package com.cxp.authentication.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxp.authentication.captcha.Captcha;
import com.cxp.authentication.common.Result;
import com.cxp.authentication.config.AuthenticationConfigWrap;
import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.Account;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.authentication.login.service.AccountService;
import com.cxp.authentication.login.service.LoginService;
import com.cxp.authentication.login.service.LoginedUserManagerFactory;
import com.cxp.password.PasswordEncoder;

@Controller
public class LoginController {
	@Resource
	AuthenticationConfigWrap config;
	
	@Resource
	AccountService accountService;
	
	@Resource
	LoginService loginService;
	
	@Resource
	PasswordEncoder passwordEncoder;
	
	@RequestMapping("/login")
	public String login(){
		return "";
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public Result doLogin(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Result result = new Result();
		
		if(config.isUseCaptcha() && !validateCaptcha(request)){
			//验证码验证不通过
			result.setMsg("验证码不对");
			result.setStatus(Result.lOGIN__CAPTCHA_ERR);
			return result;
		}
		
		String accountStr = request.getParameter("account");
		String password = request.getParameter("password");
		String plat = request.getParameter("plat");
		plat = StringUtils.isEmpty(plat) ? Consts.PLAT_WEB : plat;
		if(StringUtils.isEmpty(accountStr) || StringUtils.isEmpty(password)){
			//验证不通过
			result.setMsg("账号或密码不能为空");
			result.setStatus(Result.lOGIN__NOT_EMPTY);
			return result;
		}
		
		
		//查询账号信息
		Account account = accountService.getAccount(accountStr);
		if(account == null){
			//账号不存在
			result.setMsg("账号或密码不对");
			result.setStatus(Result.lOGIN__PASSWORD_ERR);
			return result;
		}
		
		//对密码加密
		if(passwordEncoder != null){
			password = passwordEncoder.encode(password, Base64.decodeBase64(account.getSalt()));
		}
		
		//验证密码是否正确
		if(!account.getPassword().equals(password)){
			//密码不正确
			result.setMsg("账号或密码不对");
			result.setStatus(Result.lOGIN__PASSWORD_ERR);
			return result;
		}
		
		//加载用户其他信息
		UserInfo userInfo = loginService.getUserInfo(account,plat);
		if(userInfo == null){
			result.setMsg("加密账户信息出错");
			result.setStatus(Result.ERR);
			return result;
			//加载用户信息出错
		}
		
		//先判断是否有账号已经登录
		UserInfo userInfoLogined = LoginedUserManagerFactory.getLoginedUserManager().getUserInfoByAccount(accountStr, plat);
		if(userInfoLogined != null && config.isLoginOne()){
			//清空sessionId，是为了在filter中，可以根据sessionId判断是否在其他地方登录
			userInfoLogined.setCurrentSessionId(null);
			userInfoLogined.setStatus(Consts.LOGIN_STATUS_LOGIN_BY_SOMEONE);
		}
		
		//把用户信息放入session中
		session.setAttribute(Consts.USER_SESSION_NAME, userInfo);
		userInfo.setCurrentSessionId(session.getId());
		userInfo.setCurrentLoginIp(request.getRemoteHost());
		userInfo.setCurrentLoginTime(System.currentTimeMillis());
		userInfo.setPlat(plat);
		userInfo.setStatus(Consts.LOAGIN_STATUS);
		
		//把登录账号和sesionId对应关系保存起来
		LoginedUserManagerFactory.getLoginedUserManager().saveLoginUser(userInfo, session);
		
		result.setStatus(Result.SUCCESS);
		return result;
	}
	
	@RequestMapping("/loginOut")
	@ResponseBody
	public Result loginOut(HttpServletRequest req){
		Result result = new Result();
		
		HttpSession session = req.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		if(userInfo != null){
			LoginedUserManagerFactory.getLoginedUserManager().cleanLoginUser(userInfo);
		}
		session.invalidate();
		
		result.setStatus(Result.SUCCESS);
		return result;
	}
	
	private boolean validateCaptcha(HttpServletRequest request){
		String captchaStr = request.getParameter("captcha");
		HttpSession session = request.getSession();
		Captcha captcha = (Captcha)session.getAttribute(Consts.CAPTCHA_SESSION_NAME);
		if(captcha == null || (System.currentTimeMillis() - captcha.getCreateTime() > 1000 * 60)){
			return false;
		}
		
		//清除验证码
		session.removeAttribute(Consts.CAPTCHA_SESSION_NAME);
		
		//验证码不区分大小写
		String captchaInSession = captcha.getCaptcha().toLowerCase();
		captchaStr = captchaStr.toLowerCase();
		return captchaInSession.equals(captchaStr);
	}
}

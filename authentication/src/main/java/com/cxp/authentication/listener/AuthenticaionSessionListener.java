package com.cxp.authentication.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.authentication.login.service.LoginedUserManagerFactory;

@WebListener
public class AuthenticaionSessionListener  implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		if(userInfo != null){
			LoginedUserManagerFactory.getLoginedUserManager().cleanLoginUser(userInfo);
		}
	}


}

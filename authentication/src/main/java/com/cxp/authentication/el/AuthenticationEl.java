package com.cxp.authentication.el;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;

public class AuthenticationEl extends TagSupport{
	private static final long serialVersionUID = 1L;
	private String uri;
	
	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		System.out.println(uri);
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		for(String item : userInfo.getGrantUris()){
			System.out.println(item);
		}
		if(userInfo != null && userInfo.getGrantUris().contains(uri)){
			System.out.println("kkkkkk");
			return EVAL_PAGE;
		}
		return SKIP_BODY;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}

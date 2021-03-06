package com.cxp.authentication.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="login")
public class AuthenticationConfig {
	private String isUseCaptcha;
	private List<String> noAccess = new ArrayList<String>();
	private List<String> noLoginAccess = new ArrayList<String>();
	
	public String getIsUseCaptcha() {
		return isUseCaptcha;
	}
	public void setIsUseCaptcha(String isUseCaptcha) {
		this.isUseCaptcha = isUseCaptcha;
	}
	public void setNoAccess(List<String> noAccess) {
		this.noAccess = noAccess;
	}
	public void setNoLoginAccess(List<String> noLoginAccess) {
		this.noLoginAccess = noLoginAccess;
	}

	public List<String> getNoAccess() {
		return noAccess;
	}
	public List<String> getNoLoginAccess() {
		return noLoginAccess;
	}
}

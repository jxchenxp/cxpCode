package com.cxp.authentication.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationConfigWrap {
	@Resource
	private AuthenticationConfig config;
	
	private Set<String> noAccessSet;
	private Object lock1 = new Object();
	private Set<String> noLoginAccessSet;
	private Object lock2 = new Object();
	private Boolean useCaptcha ;
	private Object lock3 = new Object();
	

	public boolean isUseCaptcha(){
		if(useCaptcha == null){
			synchronized (lock3) {
				if(useCaptcha == null){
					useCaptcha = Boolean.parseBoolean(config.getIsUseCaptcha());
				}
			}
		}
		return useCaptcha;
	}
	
	public boolean isLoginOne(){
		return true;
	}
	
	public Set<String> getNoAccess(){
		if(noAccessSet == null){
			synchronized (lock1) {
				if(noAccessSet == null){
					noAccessSet = new HashSet<String>();
					if(config.getNoAccess() != null){
						for(String uri : config.getNoAccess()){
							noAccessSet.add(uri);
						}
					}
				}
			}
		}
		return noAccessSet;
	}
	
	public Set<String> getNoLoginAccess(){
		if(noLoginAccessSet == null){
			synchronized (lock2) {
				if(noLoginAccessSet == null){
					noLoginAccessSet = new HashSet<String>();
					if(config.getNoLoginAccess() != null){
						for(String uri : config.getNoLoginAccess()){
							noLoginAccessSet.add(uri);
						}
					}
				}
			}
		}
		return noLoginAccessSet;
	}
}

package com.cxp.authentication.login.service;

public interface LoadAttribute {
	String key();
	Object loadAttribute(String plat);
}

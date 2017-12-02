package com.cxp.password;

public interface PasswordEncoder {
	String encode(String password);
	String encode(String password, String salt);
	String encode(String password, byte[] salt);
}

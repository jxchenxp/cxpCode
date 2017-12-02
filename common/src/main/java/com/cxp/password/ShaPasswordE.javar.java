package com.cxp.password;

import org.apache.commons.codec.digest.DigestUtils;

public class ShaPasswordEncoder implements PasswordEncoder{

	@Override
	public String encode(String password) {
		return encode(password, "");
	}
	
	@Override
	public String encode(String password, byte[] salt) {
		byte[] data = password.getBytes();
		byte[] endata = new byte[data.length + salt.length];
		System.arraycopy(endata, 0, data, 0, data.length);
		System.arraycopy(endata, data.length, salt, 0, data.length);
		return DigestUtils.sha1Hex(endata);
	}

	@Override
	public String encode(String password, String salt) {
		return DigestUtils.sha1Hex(password + salt);
	}

}

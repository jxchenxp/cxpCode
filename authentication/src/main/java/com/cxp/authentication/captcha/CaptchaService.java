package com.cxp.authentication.captcha;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

public interface CaptchaService {
	BufferedImage createCaptcha(HttpServletRequest request);
}

package com.cxp.authentication.captcha;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cxp.authentication.consts.Consts;
import com.google.code.kaptcha.impl.DefaultKaptcha;

@Service
public class DefaultCaptchaServiceImpl implements CaptchaService{
	private Logger log = LoggerFactory.getLogger(DefaultCaptchaServiceImpl.class);
	@Resource
	private DefaultKaptcha kaptchaService;
	
	@Override
	public BufferedImage createCaptcha(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String text = kaptchaService.createText();
		log.info("生成验证码:{}",text);
		
		Captcha captcha = new Captcha();
		captcha.setCaptcha(text);
		captcha.setCreateTime(System.currentTimeMillis());
		session.setAttribute(Consts.CAPTCHA_SESSION_NAME, captcha);
		
		return kaptchaService.createImage(text);
	}

}

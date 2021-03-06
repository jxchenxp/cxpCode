package com.cxp.authentication.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CaptchaController {
	private Logger log = LoggerFactory.getLogger(CaptchaConfig.class);
	
	@Resource
	private CaptchaService captchaService;
	
	@RequestMapping("/captcha-img")
	public void codeImg(HttpServletRequest request, HttpServletResponse response){
		response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control",  
                "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");
        
        BufferedImage bi = captchaService.createCaptcha(request);  
        ServletOutputStream out = null;
        try {  
        	 out = response.getOutputStream();
        	 ImageIO.write(bi, "jpg", out);  
            out.flush();  
        }catch(Exception e){
        	log.error("生成验证码出错",e);
        } finally { 
        	if(out != null){
        		try {
					out.close();
				} catch (IOException e) {
					log.error("生成验证码，关闭连接出错",e);
				}  
        	}
        }  
	}
}

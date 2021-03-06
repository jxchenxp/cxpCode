package com.cxp.deploy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	private Logger log = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/index")
	public String index(){
		return "test";
	}
	
	
	
	@ExceptionHandler(value = Exception.class)
	public String handle(Exception e){
		log.error("mvc异常",e);
		return "exception";
	}
}

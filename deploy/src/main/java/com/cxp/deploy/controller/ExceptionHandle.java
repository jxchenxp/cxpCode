package com.cxp.deploy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
	private Logger log = LoggerFactory.getLogger(ExceptionHandle.class);
	
	public ExceptionHandle() {
		System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
	}
	
	@ExceptionHandler(value = Exception.class)
	public String handle(Exception e){
		log.error("mvc异常",e);
		return "exception";
	}
}

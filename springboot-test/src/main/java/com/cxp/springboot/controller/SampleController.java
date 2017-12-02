package com.cxp.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {
	@RequestMapping("hello")
	@ResponseBody
	public String hello(){
		return "hi hao";
	}
}

package com.cxp.web.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrController {
	@RequestMapping("/exception")
	@ResponseBody
	public String err(){
		return "sdfsafa";
	}
}

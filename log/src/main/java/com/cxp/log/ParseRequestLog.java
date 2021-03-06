package com.cxp.log;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.cxp.util.DateFormatSecurity;

public class ParseRequestLog{
	
	public static RequestLog parse(ServletRequest request, String type) {
		RequestLog log = new RequestLog();
		HttpServletRequest req = (HttpServletRequest) request;
		
		log.setType(type);
		log.setUri(req.getServletPath());
		log.setIp(getRemortIP(req));
		log.setVisitTime(DateFormatSecurity.format(new Date()));
		log.setParams(getParams(req));
		log.setResult("success");
		
		return log;
	}
	
	private static String getParams(HttpServletRequest req){
		Map<String,String[]> params = req.getParameterMap();
		return JSON.toJSONString(params);
	}

	private static String getRemortIP(HttpServletRequest request) {  
		if (request.getHeader("x-forwarded-for") == null) {  
			return request.getRemoteAddr();  
		}  
		return request.getHeader("x-forwarded-for");  
	}  
}

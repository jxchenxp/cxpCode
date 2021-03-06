package com.cxp.web.common;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cxp.exception.BizException;
import com.cxp.exception.NoFindException;
import com.cxp.log.RequestLogRecord;

@ControllerAdvice
@Order(value=1)
public class GlobalExceptionHandler {
	private Logger log = LoggerFactory.getLogger("com.cxp.exception.log");
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody 
	public ReqResult handle(Exception e, HttpServletResponse response){
		log.error("spring mvc捕获异常:",e);
		
		ReqResult result = new ReqResult(ReqStatus.ERR,ReqStatus.ERR_MSG);
		if(e instanceof BizException){
			BizException bizE = (BizException)e;
			result.setResCode(bizE.getCode());
			result.setMsg(bizE.getErrMsg());
		}else if(e instanceof NoFindException){
			NoFindException noFind = (NoFindException)e;
			result.setResCode(ReqStatus.NOT_EXISTS);
			result.setMsg(MessageFormat.format(ReqStatus.NOT_EXISTS_MSG, noFind.getResourceType(), noFind.getResource()));
		}else{
			result.setMsg(e.getMessage());
		}
		
		RequestLogRecord.getInstance().changeLogResult("err:" + JSON.toJSONString(result));
		return result;
	}
}

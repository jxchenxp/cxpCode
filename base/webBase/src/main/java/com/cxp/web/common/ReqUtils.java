package com.cxp.web.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.cxp.page.PageResult;

@SuppressWarnings({ "rawtypes"})
public class ReqUtils {
	public static ReqResult success(){
		return success("操作成功");
	}
	public static ReqResult success(String msg){
		return new ReqResult(ReqStatus.SUCCESS); 
	}
	public static ReqResult notAccess(){
		return notAccess("");
	}
	public static ReqResult notAccess(String resource){
		return new ReqResult(ReqStatus.NOT_ACCESS,MessageFormat.format(ReqStatus.NOT_ACCESS_MSG, resource));
	}
	
	public static ReqResult listPage(PageResult page){
		ReqResult result = new ReqResult(ReqStatus.SUCCESS);
		result.setSize(page.getTotalSize());
		result.setResult(page.getResult());
		return result;
	}
	
	public static ReqResult list(List list){
		ReqResult result = new ReqResult(ReqStatus.SUCCESS);
		if(list == null){
			list = new ArrayList<Object>(1);
		}
		result.setSize(list.size());
		result.setResult(list);
		return result;
	}
	
	public static ReqResult object(Object obj){
		ReqResult result = new ReqResult(ReqStatus.SUCCESS);
		if(obj != null){
			result.setSize(1);
			result.setResult(obj);
		}
		return result;
	}
	
	public static ReqResult err(){
		return err(ReqStatus.BIZ_ERR);
	}
	public static ReqResult err(String code){
		return err(code,ReqStatus.BIZ_ERR_MSG);
	}
	public static ReqResult err(String code, String errMsg){
		ReqResult result = new ReqResult(ReqStatus.SUCCESS);
		result.setResCode(code);
		result.setMsg(errMsg);
		return result;
	}
}

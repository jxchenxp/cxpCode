package com.cxp.log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

public class RequestLogRecord {
	private ThreadLocal<RequestLog> threadLocalLog = new ThreadLocal<RequestLog>();
	
	static class RequestLogRequestInstance{
		static RequestLogRecord instance = new RequestLogRecord();
	}
	public static RequestLogRecord getInstance(){
		return RequestLogRequestInstance.instance;
	}
	
	private RequestLogWrite logWrite = new RequestLogWrite();
	
	public RequestLogRecord() {
		//启动写请求日志线程
		new Thread(logWrite).start();
	}
	
	public void add(ServletRequest request){
		this.add(request, "normal");
	}
	
	public void add(ServletRequest request, String type){
		RequestLog log = ParseRequestLog.parse(request, type);
		threadLocalLog.set(log);
	}
	
	public void changeLogType(String type){
		threadLocalLog.get().setType(type);
	}
	
	public void changeLogResult(String result){
		threadLocalLog.get().setResult(result);
	}
	
	public void setLoginUserInfo(String userInfo){
		threadLocalLog.get().setUserInfo(userInfo);
	}
	
	public void addBizUseStartTime(String bizType){
		RequestLog log = threadLocalLog.get();
		if(log == null){
			return;
		}
		BizUseTimeRecord useTimeRecord = new BizUseTimeRecord();
		useTimeRecord.setBizType(bizType);
		useTimeRecord.setStartTime(System.currentTimeMillis());
		if(log.getUseTimeRecords() == null){
			List<BizUseTimeRecord> list = new LinkedList<BizUseTimeRecord>();
			Map<String,Integer> map = new HashMap<String, Integer>();
			map.put(bizType, list.size());
			list.add(useTimeRecord);
			log.setUseTimeRecords(list);
			log.setBizUseTimeMap(map);
		}else{
			log.getBizUseTimeMap().put(bizType, log.getUseTimeRecords().size());
			log.getUseTimeRecords().add(useTimeRecord);
		}
	}
	public void addBizUseEndTime(String bizType){
		RequestLog log = threadLocalLog.get();
		if(log == null){
			return;
		}
		Integer index = log.getBizUseTimeMap().get(bizType);
		if(index == null){
			return;
		}
		BizUseTimeRecord useTimeRecord = log.getUseTimeRecords().get(index);
		useTimeRecord.setEndTime(System.currentTimeMillis());
	}
	
	public void record(){
		logWrite.addToWrite(threadLocalLog.get());
		threadLocalLog.remove();
	}
}

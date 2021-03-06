package com.cxp.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class RequestLogWrite implements Runnable{
	private Logger log = LoggerFactory.getLogger(RequestLogWrite.class);
	private Logger requestLog = LoggerFactory.getLogger("com.cxp.request.log");
	private BlockingQueue<RequestLog> queue = new LinkedBlockingQueue<RequestLog>();
	private StringBuilder lineLog = new StringBuilder();
	private StringBuilder cacheLog = new StringBuilder();
	private long preWriteTime = 0;
	private int cacheLogSize = 0;
	
	private static int MAX_CACHE_SIZE = 100;
	private static long MAX_CACHE_TIME = 1000 * 60;
	
	public void addToWrite(RequestLog log){
		queue.offer(log);
	}
	
	@Override
	public void run() {
		Thread.currentThread().setName("request-log-write");
		
		RequestLog log;
		while(true){
			try {
				log = queue.poll(60 , TimeUnit.SECONDS);
				write(log);
			} catch (InterruptedException e) {
				this.log.error("记录请求日志出错",e);
			}
		}
	}
	
	private void format(RequestLog log){
		lineLog.delete(0,lineLog.length());
		lineLog.append(log.getType() + " ");
		lineLog.append(log.getIp() + "  ");
		lineLog.append(log.getUserInfo() + "  ");
		lineLog.append(log.getUri() + "  ");
		lineLog.append(log.getVisitTime() + "  ");
		lineLog.append(filterParams(log.getUri(), log.getParams()) + "  ");
		lineLog.append(log.getResult() + "  ");
		lineLog.append(formatBizUseTime(log.getUseTimeRecords()) + "  ");
	}
	
	/**
	 * 格式化用户用时数据
	 * @return
	 */
	private String formatBizUseTime(List<BizUseTimeRecord> list){
		StringBuilder useTimeStr = new StringBuilder();
		if(list != null){
			for(BizUseTimeRecord useTime : list){
				useTimeStr.append(useTime.getBizType() + ":["+ useTime.getStartTime() +","+ useTime.getEndTime() +","+ (useTime.getEndTime() - useTime.getStartTime()) +"]  ");
			}
		}
		return useTimeStr.toString();
	}
	
	/**
	 * 过滤敏感数据
	 * @param uri
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String filterParams(String uri, String params){
		Map<String,String> map = null;
		if("/doLogin".equals(uri)){
			map = JSON.parseObject(params,Map.class);
			if(map == null){
				map = new HashMap<String, String>(0);
			}
			map.remove("password");
		}
		return map == null ? params : JSON.toJSONString(map);
	}
	
	private void write(RequestLog log){
		if(log != null){
			//格式化日志
			format(log);

			cacheLog.append(lineLog.toString() + "\n");
			cacheLogSize++;
		}
		
		//写日志
		if((cacheLogSize > MAX_CACHE_SIZE || (System.currentTimeMillis() - preWriteTime) > MAX_CACHE_TIME)
				&& cacheLog.length() > 0){
			requestLog.info(cacheLog.toString());
			cacheLog.delete(0, cacheLog.length());
			cacheLogSize = 0;
			preWriteTime = System.currentTimeMillis();
		}
		
	}
}

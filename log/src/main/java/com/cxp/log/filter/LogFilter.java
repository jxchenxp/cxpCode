package com.cxp.log.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cxp.log.RequestLogRecord;
/**
 * 异步记录访问请求日志
 * @author 2490
 *
 */
public class LogFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(isStaticResource(((HttpServletRequest)request).getServletPath())){
			request.setAttribute("isStaticResource", true);
			chain.doFilter(request, response);
		}else{
			request.setAttribute("isStaticResource", false);
			//加入线程缓存中
			RequestLogRecord.getInstance().add(request);
			RequestLogRecord.getInstance().addBizUseStartTime("allUseTime");
			chain.doFilter(request, response);
			
			//加入日志
			RequestLogRecord.getInstance().addBizUseEndTime("allUseTime");
			RequestLogRecord.getInstance().record();
		}
	}

	@Override
	public void destroy() {
		
	}
	
	/**
	 * 验证是否是静态资源
	 * @return
	 */
	private boolean isStaticResource(String uri){
		return uri.startsWith("/static");
	}

}

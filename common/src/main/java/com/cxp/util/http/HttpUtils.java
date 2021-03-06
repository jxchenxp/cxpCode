package com.cxp.util.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class HttpUtils {
	private static final String AJAX_ACCEPT_CONTENT_TYPE = "text/html;type=ajax";
	private static final String AJAX_SOURCE_PARAM = "ajaxSource";
	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
	
	public static boolean isAjax(HttpServletRequest request){
		String acceptHeader = request.getHeader("Accept");
		String ajaxParam = request.getParameter(AJAX_SOURCE_PARAM);
		String xRequestWith = request.getHeader("X-Requested-With");
		if (XML_HTTP_REQUEST.equals(xRequestWith) || AJAX_ACCEPT_CONTENT_TYPE.equals(acceptHeader) 
				|| !StringUtils.isEmpty(ajaxParam)) {
			return true;
		} else {
			return false;
		}
	}
}

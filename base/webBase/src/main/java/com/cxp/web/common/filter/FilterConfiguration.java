package com.cxp.web.common.filter;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.cxp.log.filter.LogFilter;



@Component
public class FilterConfiguration {
	@Bean
	public FilterRegistrationBean getLoginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("requestLogFilter");
        registration.setOrder(1);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }
}

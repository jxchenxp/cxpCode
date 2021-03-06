package com.cxp.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    public static ApplicationContext context = null;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context = applicationContext;
    }

    public static ApplicationContext getSpringContext(){
    	return context;
    }
    
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String bean){
        return (T)SpringContextUtil.context.getBean(bean);
    }

	@SuppressWarnings("unchecked")
    public static <T> T getBean(@SuppressWarnings("rawtypes") Class cls){
        return (T)SpringContextUtil.context.getBean(cls);
    }
}

package com.cxp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication(scanBasePackages={"com.cxp"})
@EnableConfigurationProperties
@MapperScan("com.cxp.**.dao")
@ServletComponentScan(basePackages={"com.cxp"})
public class ApplicationStart{
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	} 
}

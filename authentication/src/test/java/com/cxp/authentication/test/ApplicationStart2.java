package com.cxp.authentication.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication(scanBasePackages={"com.cxp"})
@EnableConfigurationProperties   
public class ApplicationStart2 {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart2.class, args);
	}
}

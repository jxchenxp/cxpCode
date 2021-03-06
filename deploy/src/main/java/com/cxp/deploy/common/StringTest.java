package com.cxp.deploy.common;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

public class StringTest {
	@Test
	public void test1(){
		StringBuilder sb = new StringBuilder();
		sb.append("33333");
		StringBuilder sb2 = new StringBuilder();
		sb2.append(sb.toString());
		
		sb.delete(0, sb.length());
		System.out.println(sb.toString());
		System.out.println(sb2.toString());
		
		System.out.println(String.format("%04d", 4));
		
		Map<String,String> map = JSON.parseObject("{}",Map.class);
		System.out.println(map);
		
		int length = 4;
		System.out.println(String.format("%04d", 1));
	}
}

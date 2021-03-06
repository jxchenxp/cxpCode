package com.cxp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormatSecurity {
	private static ThreadLocal<Map<String,SimpleDateFormat>> dfl = new ThreadLocal<Map<String,SimpleDateFormat>>();
	
	private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date){
		Map<String,SimpleDateFormat> dfm = dfl.get();
		if(dfm == null){
			dfm = new HashMap<String, SimpleDateFormat>();
			dfl.set(dfm);
		}
		SimpleDateFormat df = dfm.get(DEFAULT_FORMAT);
		if(df == null){
			df = new SimpleDateFormat(DEFAULT_FORMAT);
			dfm.put(DEFAULT_FORMAT, df);
		}
		return df.format(date);
	}
}

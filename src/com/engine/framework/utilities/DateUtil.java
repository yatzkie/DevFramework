package com.engine.framework.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static void getDate(String format, long timestamp) {
		
	}
	
	public static void getTime(String format, long timestamp) {
		
	}
	
	public static String getDateTime(String format, long timestamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat( format, Locale.US);
        return dateFormat.format(new Date(timestamp));
	}
	
	public static String getDateFormat(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat( format, Locale.US);
        String date = dateFormat.format(new Date());
        return date;
	}

}

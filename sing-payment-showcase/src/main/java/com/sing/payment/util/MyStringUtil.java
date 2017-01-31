package com.sing.payment.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyStringUtil {
	
	public static  String  changeDateToStr(Date date){
		DateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public static  String  changeDateToStr(Date date, String format){
		DateFormat df =  new SimpleDateFormat(format);
		return df.format(date);
	}

}

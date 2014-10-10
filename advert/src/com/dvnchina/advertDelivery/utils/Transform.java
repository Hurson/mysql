package com.dvnchina.advertDelivery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transform {
	public static String CalendartoString(Date date){
		if(date==null) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	public static String date2String(Date date,String format){
		if(date==null) return null;
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static String CalendartoString(Calendar cal){
		if(cal==null) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=cal.getTime();
		return sdf.format(d);
	}
	public static String CalendartoStringYMD(Calendar cal){
		if(cal==null) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d=cal.getTime();
		return sdf.format(d);
	}
	public static Calendar StringtoCalendarYMD(String s) throws ParseException{
		if(s==null) return null;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(sdf.parse(s));
		return cal;
	}
	public static Calendar StringtoCalendar(String s) throws ParseException{
		if(s==null) return null;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.setTime(sdf.parse(s));
		return cal;
	}
	public static String FormatCalendar(Calendar cal){
		if(cal==null) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d=cal.getTime();
		return sdf.format(d);
	}
	public static String FormatHM(Calendar cal){
		if(cal==null) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm");
		Date d=cal.getTime();
		return sdf.format(d);
	}
	public static int StringtoInt(String temp){
		return Integer.valueOf(temp).intValue();
	}
	public static String compareCalendar4Format(Calendar cal1,Calendar cal2){
		long s=cal2.getTimeInMillis()-cal1.getTimeInMillis();
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal3=Calendar.getInstance(); 
		cal3.setTimeInMillis(s);
		cal3.add(Calendar.HOUR_OF_DAY, -8);
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		Date d=cal3.getTime();
		return sdf.format(d);
	}
	
	public static Date string4SqlDateYYYYMMDD(String date) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date gDate = new Date(sdf.parse(date).getTime());
		return gDate;
	}
	
	public static Date string2Date(String date, String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		Date gDate = new Date(sdf.parse(date).getTime());
		return gDate;
	}
}

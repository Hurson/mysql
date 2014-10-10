package com.dvnchina.advertDelivery.utils.cron;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 页面设置转为UNIX cron expressions 转换类 CronExpConversion
 */
public class CronExpConversion {
	
	public static void main(String[] args) {
		String ss = "2012-03-07 09:14:19";
		convertDateToCronExp(ss);
	}
	
	

	/**
	 * 页面设置转为UNIX cron expressions 转换算法
	 * 
	 * @param dateSring  (n-y-r s:f:m) 2012-03-07 09:14:19  
	 * @return cron expression
	 */
	public static String convertDateToCronExp(String dateSring) {
		String cronEx = "";
		if(StringUtils.isNotBlank(dateSring)){
			StringBuffer buffer = new StringBuffer();
			
			String date[] = dateSring.split(" ");
			
			String nyr = date[0]; //年-月-日
			String[] ymd = nyr.split("-"); //{year,month,day}
			String year = ymd[0];	
			String month = ymd[1];
			String day = ymd[2];
			
			String sfm = date[1]; //时:分:秒
			String[] hms = sfm.split(":");//hour:minute:second
			String hour = hms[0];
			String minute = hms[1];
			String second = hms[2];
			buffer.append(Integer.valueOf(second));
			buffer.append(CronExRelated._BLANKSPACE);
			buffer.append(Integer.valueOf(minute));
			buffer.append(CronExRelated._BLANKSPACE);
			buffer.append(Integer.valueOf(hour));
			buffer.append(CronExRelated._BLANKSPACE);
			
			buffer.append(Integer.valueOf(day));
			buffer.append(CronExRelated._BLANKSPACE);
			buffer.append(Integer.valueOf(month));
			buffer.append(CronExRelated._BLANKSPACE);
			buffer.append(CronExRelated._ANY);
			buffer.append(CronExRelated._BLANKSPACE);
			buffer.append(Integer.valueOf(year));
			cronEx = buffer.toString();
		}
		return cronEx;
	}
	
	/**
	 * 页面设置转为UNIX cron expressions 转换算法
	 * 
	 * @param date java.util.date 
	 * @return cron expression
	 */
	public static String convertDateToCronExp(Date date) {
		String cronEx = "";
		if(date != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.format(date);
			cronEx=convertDateToCronExp(sdf.format(date));
		}
		return cronEx;
	}
	
	/**
	 * 页面设置转为UNIX cron expressions 转换算法
	 * 
	 * @param everyWhat
	 * @param commonNeeds
	 *            包括 second minute hour
	 * @param monthlyNeeds
	 *            包括 第几个星期 星期几
	 * @param weeklyNeeds
	 *            包括 星期几
	 * @param userDefinedNeeds
	 *            包括具体时间点
	 * @return cron expression
	 */
	public static String convertDateToCronExp(String everyWhat, String[] commonNeeds, String[] monthlyNeeds, String weeklyNeeds,
			String userDefinedNeeds) {
		String cronEx = "";
		return cronEx;
	}

}

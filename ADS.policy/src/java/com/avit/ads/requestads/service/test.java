package com.avit.ads.requestads.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class test {
	public static void main(String[] args) {
		int locationType = 5;
		StringBuffer sb = new StringBuffer(256);
		String parentLocationCode = "152010000077";
		sb.append("select ");
		for (int i = 0; i < locationType-3; i++) {
			
			if(i == locationType-4){
				sb.append("t").append(i).append(".").append("LOCATIONCODE ");
			}else{
				sb.append("t").append(i).append(".").append("LOCATIONCODE, ");
			}
		}
		sb.append(" from ");
		for (int i = 0; i < locationType-3; i++) {
			
			if(i == locationType-4){
				sb.append("t_location_code t").append(i);
			}else{
				sb.append("t_location_code t").append(i).append(",");
			}
		}
		sb.append(" where ");
		for (int i = 0; i < locationType-4; i++) {
			sb.append("t").append(i).append(".LOCATIONCODE = t").append(i+1).append(".PARENTLOCATION").append(" and ");
		}
		sb.append("t").append(locationType-4).append(".LOCATIONCODE='").append(parentLocationCode).append("'");
		System.out.println(sb.toString());
	}

	
public static void timerScheduled() {
		
		
		Timer defaultResourcetimer = new Timer();
		
		defaultResourcetimer.schedule(defaultResourceCacheTimerTask, getFirstTime(),  1000*60*2);
	}
private static TimerTask defaultResourceCacheTimerTask = new TimerTask() {
	
	@Override
	public void run() {
		System.out.println(Calendar.getInstance().getTime());
		System.out.println("I'm running");
	}
};

private static Date getFirstTime(){
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DATE, 1);
	c.set(Calendar.HOUR_OF_DAY, 1);
	c.set(Calendar.MINUTE, 4);
	return c.getTime();
}


	
	
	

	
}

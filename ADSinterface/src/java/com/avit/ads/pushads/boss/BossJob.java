package com.avit.ads.pushads.boss;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.avit.ads.pushads.boss.service.BossService;
import com.avit.ads.util.DateUtil;

public class BossJob {

	@Inject
	private BossService bossService;
	private Logger logger = Logger.getLogger(BossJob.class);
	public void generateBossData()
	{
		Date startDate, endDate;
		startDate =new Date();
		startDate= DateUtil.addMonth(startDate, -1);
		Calendar cal =Calendar.getInstance() ;
		cal.setTime(startDate);
//		cal.set(Calendar.MONTH, 8);
//		cal.set(Calendar.DAY_OF_MONTH, 10);
//		cal.set(Calendar.MONTH, 9);//10
		cal.set(Calendar.DAY_OF_MONTH, 1);//29
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);		
		startDate =cal.getTime();
		
		endDate =new Date();		
		cal =Calendar.getInstance() ;
		cal.setTime(endDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);//29
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);		
		endDate =cal.getTime();
		//endDate= DateUtil.addDay(endDate, -1);
		bossService.syncDataByMonth(startDate, endDate);
	}
}

package com.avit.ads.syncreport;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.avit.ads.syncreport.service.ReportService;

public class ReportJob {

	@Inject
	private ReportService reportService;
	private Logger logger = Logger.getLogger(ReportJob.class);
	public void generateReportData()
	{
		try{
			reportService.generateReportData(null);
			
//			Calendar cal = Calendar.getInstance();
//			cal.set(2014,7,8);
//			
//			int i = 20; //8月8号到28号
//			while(i > 0){
//				Date date = cal.getTime();
//				reportService.generateReportData(date);
//				i--;
//				cal.add(Calendar.DAY_OF_MONTH, 1);
//				Thread.sleep(60000);
//			}
			
			
//			Calendar cal = Calendar.getInstance();
//			cal.set(2014,6,11);
//			
//			Date date = cal.getTime();
//			reportService.generateReportData(date);
			
			
		}catch(Exception ex){
			logger.error(ex);
		}
		
	}
}

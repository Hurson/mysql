package com.avit.ads.syncreport.service;

import java.util.Date;

public interface ReportService {
	public boolean downCMAFile(String downday);  
	public boolean generateReportData(Date day) throws Exception;
	public boolean backupCMA(String backupday,String sourcePath,String desPath);
}

package com.dvnchina.advertDelivery.report.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.report.bean.AdvertReport;
import com.dvnchina.advertDelivery.report.bean.QuestionReport;
import com.dvnchina.advertDelivery.report.dao.ReportDao;
import com.dvnchina.advertDelivery.report.service.ReportService;

/**
 * @ClassName ReportServiceImpl
 * @Description
 * @author panxincheng
 * @date 2013-10-28 
 */
public class ReportServiceImpl implements ReportService {

	private ReportDao reportDao;

	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public PageBeanDB queryDayReportList(AdvertReport aReport, int pageNo, int pageSize) {
		if (aReport == null || aReport.getReportType() == 0) {
			return reportDao.queryDayReportList(aReport, pageNo, pageSize);
		} else if (aReport.getReportType() == 1) {
			return reportDao.queryWeekReportList(aReport, pageNo, pageSize);
		} else {
			return reportDao.queryMonthReportList(aReport, pageNo, pageSize);
		}
	}
	/**
	 * 查询问卷报表
	 * @param pageNo
	 * @param pageSize
	 * @return PageBeanDB
	 */
	public PageBeanDB queryQuestionReportList(QuestionReport qReport, int pageNo, int pageSize)
	{
		try
		{
			PageBeanDB tempPage =reportDao.queryQuestionReportList(qReport, pageNo, pageSize);
			List<QuestionReport> reportList = tempPage.getDataList();
			if (reportList!=null )
			for (int i=0;i<reportList.size();i++) {
				
				if (reportList.get(i).getPushDateStart().compareTo(reportList.get(i).getSearchStart())<0 && !reportList.get(i).getSearchStart().equals("1970-01-01"))
				{
				reportList.get(i).setPushDateStart(reportList.get(i).getSearchStart());
				}
				if (reportList.get(i).getPushDateEnd().compareTo(reportList.get(i).getSearchEnd())>0 && !reportList.get(i).getSearchEnd().equals("1970-01-01"))
				{
				reportList.get(i).setPushDateEnd(reportList.get(i).getSearchEnd());
				}			
			}
			tempPage.setDataList(reportList);
			return tempPage;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}

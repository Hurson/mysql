package com.dvnchina.advertDelivery.report.dao;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.report.bean.AdvertReport;
import com.dvnchina.advertDelivery.report.bean.QuestionReport;

/**
 * @ClassName ReportDao
 * @Description
 * @author panxincheng
 * @date 2013-10-28
 */
public interface ReportDao {

	/**
	 * 查询日报表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return PageBeanDB
	 */
	public PageBeanDB queryDayReportList(AdvertReport aReport, int pageNo,
			int pageSize);

	/**
	 * 查询周报表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return PageBeanDB
	 */
	public PageBeanDB queryWeekReportList(AdvertReport aReport, int pageNo,
			int pageSize);

	/**
	 * 查询月报表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return PageBeanDB
	 */
	public PageBeanDB queryMonthReportList(AdvertReport aReport, int pageNo,
			int pageSize);

	/**
	 * 查询问卷报表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return PageBeanDB
	 */
	public PageBeanDB queryQuestionReportList(QuestionReport qReport,
			int pageNo, int pageSize);

}

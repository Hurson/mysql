package com.dvnchina.advertDelivery.report.service.impl;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.report.bean.OrderBean;
import com.dvnchina.advertDelivery.report.dao.AdvertReportDao;
import com.dvnchina.advertDelivery.report.service.AdvertReportService;
import com.dvnchina.advertDelivery.utils.DateUtil;

/**
 * @ClassName AdvertReportServiceImpl
 * @Description
 * @author marui
 * @date 2013-12-05
 */
public class AdvertReportServiceImpl implements AdvertReportService {

	private AdvertReportDao advertReportDao;

	@Override
	public PageBeanDB queryAdvertPublishCountList(OrderBean order, int pageNo,
			int pageSize) {
		return advertReportDao.queryAdvertReportList(order, pageNo, pageSize);
	}

	@Override
	public PageBeanDB queryReqAdvertPublishCountList(OrderBean order, int pageNo,
			int pageSize) {
		return advertReportDao.queryReqAdvertReportList(order, pageNo, pageSize);
	}

	public AdvertReportDao getAdvertReportDao() {
		return advertReportDao;
	}

	public void setAdvertReportDao(AdvertReportDao advertReportDao) {
		this.advertReportDao = advertReportDao;
	}

	@Override
	public List<OrderBean> queryOrderList() {
		OrderBean order = new OrderBean();
		//获得昨天的日期
		Date lastDate = DateUtil.addDay(new Date(), -1);
		//订单结束时间
		String orderEndTime = DateUtil.formatDate(lastDate);
		order.setEndTime(orderEndTime);
		return advertReportDao.queryOrderList(order);
	}

}

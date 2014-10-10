package com.dvnchina.advertDelivery.report.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.report.bean.OrderBean;

/**
 * @ClassName AdvertReportService
 * @Description
 * @author marui
 * @date 2013-12-05
 */
public interface AdvertReportService {
	
	/**
	 * 查询昨天结束的待统计的订单列表
	 * @param order
	 * @return
	 */
	public List<OrderBean> queryOrderList();
	
	/**
	 * 单项广告投放数报表
	 * @param orderEndTime 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAdvertPublishCountList(OrderBean order,int pageNo, int pageSize);
	
	/**
	 * 双向广告投放数报表
	 * @param orderEndTime 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryReqAdvertPublishCountList(OrderBean order,int pageNo, int pageSize);
}

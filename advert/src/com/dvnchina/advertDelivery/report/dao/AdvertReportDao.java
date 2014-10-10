package com.dvnchina.advertDelivery.report.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.report.bean.OrderBean;

/**
 * @ClassName AdvertReportDao
 * @Description
 * @author marui
 * @date 2013-12-05
 */
public interface AdvertReportDao {
	/**
	 * 查询单项广告投放报表
	 * 
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAdvertReportList(OrderBean order, int pageNo,
			int pageSize);

	/**
	 * 查询双向已投放广告投放报表
	 * 
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryReqAdvertReportList(OrderBean order, int pageNo,
			int pageSize);
	
	/**
	 * 查询待统计的订单列表
	 * @param order
	 * @return
	 */
	public List<OrderBean> queryOrderList(OrderBean order);

}

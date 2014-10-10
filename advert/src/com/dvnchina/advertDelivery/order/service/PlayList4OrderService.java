package com.dvnchina.advertDelivery.order.service;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.order.bean.PutInPlayListBean;
import com.dvnchina.advertDelivery.order.bean.RequestPlayListBean;



public interface PlayList4OrderService {
	
	/**
	 * 根据订单id和订单类型查询投放式播出单记录
	 * */
	public PutInPlayListBean getPutInPlayListByOrderId(Integer orderId);
	
	/**
	 * 根据订单id和订单类型查询请求式播出单记录
	 * */
	public List<RequestPlayListBean> getRequestPlayListByOrderId(Integer orderId);
	
	/**
	 * 检查播出单开始时间是否大于指定的时间
	 * @param orderId 订单编号
	 * @param date 时间
	 * @param orderType 订单类型
	 * @return 0-大于，1-小于
	 * */
	public int checkPlayListStartTime(Integer orderId,Date date,int orderType);
	
	/**
	 * 根据策略，时间范围查询除指定订单id外的播出单对应的订单号
	 * @param ployId 策略编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param orderType 订单类型
	 * @param orderId 订单主键
	 * @return 订单号
	 * */
	public String getPlayListOrderNo(Integer ployId,Date startDate,Date endDate,Integer orderType,Integer orderId);
	
	/**
	 * 根据策略，时间范围查询播出单对应的订单号
	 * @param ployId 策略编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param orderType 订单类型
	 * @return 订单号
	 * */
	public String getPlayListOrderNo(Integer ployId,Date startDate,Date endDate,Integer orderType);
	
	/**
	 * 根据订单编号查询订单对应的播出单类型
	 * @param orderId 订单编号
	 * @return 播出单类型码
	 */
	public Integer getTypeByOrderId(Integer orderId);
	
	/**
	 * 查询执行完毕的投放式订单编号
	 * @return 订单编号集合
	 * */
	public List<Integer> getFinishedPutInPlayList();
	
	/**
	 * 查询执行完毕的请求式订单编号
	 * @return 订单编号集合
	 * */
	public List<Integer> getFinishedRequestPlayList();
	
	/**
	 * 将播出单状态为停用
	 * */
	public void setPlayListState(List<Integer> orderIds,int orderType);
	
}

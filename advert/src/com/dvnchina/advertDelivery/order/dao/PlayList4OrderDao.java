package com.dvnchina.advertDelivery.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.order.bean.PutInPlayListBean;
import com.dvnchina.advertDelivery.order.bean.RequestPlayListBean;


public interface PlayList4OrderDao {
	
	/**
	 * 根据订单id和订单类型查询投放式播出单记录
	 * */
	public PutInPlayListBean getPutInPlayListByOrderId(Integer orderId);
	
	/**
	 * 根据订单id和订单类型查询请求式播出单记录
	 * */
	public List<RequestPlayListBean> getRequestPlayListByOrderId(Integer orderId);
	
	/**
	 * 根据订单编号查询投放式播出单的开始时间
	 * */
	public Date getPutInPlayListStart(Integer orderId);
	
	/**
	 * 根据订单编号查询请求式播出单的开始时间
	 * 开始日期:BEGIN_DATE [Date],
	 * 开始时间:BEGIN [String]
	 * */
	public Map<String,Object> getRequestPlayListStart(Integer orderId);
	
	/**
	 * 查询排除指定订单编号的投放式订单日期信息
	 * 订单主键：ORDER_ID [Integer]，
	 * 开始日期：STARTTIME [Date]，
	 * 结束日期：ENDTIME [Date]
	 * */
	public  List<Map<String,Object>> getPutInOrder(Integer ployId,Integer orderId);
	
	/**
	 * 根据日期范围和策略id查询排除指定订单编号的请求式订单编号
	 * */
	public List<Integer> getRequestOrder(Integer ployId, Date startDate, Date endDate, Integer orderId);
	
	/**
	 * 根据订单ID获取订单号
	 */
	public String getOrderNoById(List<Integer> ids);
	
	/**
	 * 查询投放式订单日期信息
	 * 订单主键：ORDER_ID [Integer]，
	 * 开始日期：STARTTIME [Date]，
	 * 结束日期：ENDTIME [Date]
	 * */
	public  List<Map<String,Object>> getPutInOrder(Integer ployId);
	
	/**
	 * 根据日期范围和策略id查询请求式订单编号
	 * */
	public  List<Integer> getRequestOrder(Integer ployId, Date startDate, Date endDate);
	
	/**
	 * 获取投放式播出单结束时间
	 * 订单主键:ORDER_ID [Integer],
	 * 结束日期时间 :ENDTIME [Date]
	 * */
	List<Map<String, Object>> getPutInPlayListEndTime();

	/**
	 * 获取请求式播出单结束时间
	 * 订单主键:ORDER_ID [Integer],
	 * 结束日期:END_DATE [Date],
	 * 结束时间:END [String]
	 * */
	List<Map<String, Object>> getRequestPlayListEndDate();
	
	/**
	 * 批量更新投放式播出单状态
	 * */
	public void updatePutInPlayListsState(List<Integer> orderIds, int state);
	
	/**
	 * 批量更新请求式播出单状态
	 * */
	public void updateRequestPlayListsState(List<Integer> orderIds, int state);
	
}

package com.dvnchina.advertDelivery.order.service;

import java.util.List;

import com.dvnchina.advertDelivery.order.bean.Order;


public interface PlayListReqService {
	/**
	 * 根据订单插入播出单记录[上传资源文件]
	 * @param orderId 订单编号
	 * @return 0-成功，1-失败
	 * */
	public int insertPlayList(Integer orderId);
	
	/**
	 * 根据播出单序号删除播出单记录[删除资源文件]
	 * @param orderId 订单编号
	 */
	public void deletePlayList(List<Integer> playListIds);
	
	/**
	 * 根据播出单序号删除播出单记录[删除资源文件]
	 * @param orderId 订单编号
	 * @return 0-成功，1-失败
	 */
	public int deletePlayList(Integer orderId);
		
	
	/**
	 * 根据订单更新播出单记录结束日期
	 * @param order 订单对象
	 * @return 0-成功，1-失败
	 */
	public int updatePlayListEndDate(Order order);

	/**
	 * 更新播出单全部内容
	 * @param orderId 订单编号
	 * @return 0-成功，1-失败
	 */
	public int updateAllPlayList(Integer orderId);

}

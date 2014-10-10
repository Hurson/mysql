package com.dvnchina.advertDelivery.awaitDoing.dao;

import java.util.List;

import com.dvnchina.advertDelivery.order.bean.Order;

public interface AwaitDoingDao {
	
	/**
	 * 根据广告IDS获取问卷代办订单
	 * @param positionIds
	 * @param contractIds
	 * @return
	 */
	public List<Order> getQuestionnaireOrderList(String positionIds,String contractIds);
	
	/**
	 * 根据广告商ID获取子广告位ID和合同ID
	 * @param customerId
	 * @return
	 */
	public List<Object[]> getPositionContractByCustomer(Integer customerId);

}

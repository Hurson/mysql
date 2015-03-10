package com.dvnchina.advertDelivery.awaitDoing.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.awaitDoing.dao.AwaitDoingDao;
import com.dvnchina.advertDelivery.awaitDoing.service.AwaitDoingService;
import com.dvnchina.advertDelivery.order.bean.Order;

public class AwaitDoingServiceImpl implements AwaitDoingService{
	
	private AwaitDoingDao awaitDoingDao;
	
	/**
	 * 根据广告IDS获取问卷代办订单
	 * @param positionIds
	 * @param contractIds
	 * @return
	 */
	public List<Order> getQuestionnaireOrderList(String positionIds,String contractIds){
		return awaitDoingDao.getQuestionnaireOrderList(positionIds,contractIds);
	}
	
	/**
	 * 根据广告商ID获取子广告位ID和合同ID
	 * @param customerId
	 * @return
	 */
	public List<Object[]> getPositionContractByCustomer(Integer customerId){
		return awaitDoingDao.getPositionContractByCustomer(customerId);
	}
	
	public void setAwaitDoingDao(AwaitDoingDao awaitDoingDao) {
		this.awaitDoingDao = awaitDoingDao;
	}

	@Override
	public List<String> getFreePositionRemind(String packageIds, String areaCodes, String startDateStr, String endDateStr) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

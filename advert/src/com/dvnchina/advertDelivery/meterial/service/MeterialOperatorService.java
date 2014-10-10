package com.dvnchina.advertDelivery.meterial.service;

import java.util.Map;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.Resource;

public interface MeterialOperatorService {

	
	public PageBeanDB queryMeterialUponLineList(int pageNo, int pageSize, Resource object);

	public Resource getMeterialUponLineById(int lid);

	/**
	 * 上线，下线操作
	 * @param state
	 * @param resource
	 */
	public void writeVerifyOpinion(char state, Resource resource, String ids);

	/**
	 * 删除下线状态的素材，在页面端会做判断，上线状态的不允许删除，所以在后台不做判断了。
	 * @param ids
	 */
	public void deleteMeterialOffline(String ids);

	public int hasBindedOrder(String ids);

}

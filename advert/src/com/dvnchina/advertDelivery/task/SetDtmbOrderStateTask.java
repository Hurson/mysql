package com.dvnchina.advertDelivery.task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.avit.dtmb.order.service.DOrderService;

public class SetDtmbOrderStateTask {
	private static Logger logger = Logger.getLogger(SetDtmbOrderStateTask.class);
	
	@Resource
	private DOrderService dOrderService;

	/**
	 * 将执行完毕的订单状态设置为"执行完毕"状态码
	 * */
	public void setOrderState() {
		logger.info("定时设置执行完毕的订单及播出单状态开始");
		try {
			dOrderService.completeOrder();
			
			logger.info("定时设置执行完毕的订单及播出单状态结束");
		} catch (Exception e) {
			logger.error("定时设置执行完毕的订单及播出单状态时出错", e);
		}
	}

}

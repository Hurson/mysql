package com.dvnchina.advertDelivery.task;

import java.util.List;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.service.OrderService;
import com.dvnchina.advertDelivery.order.service.PlayList4OrderService;

public class SetOrderStateTask {
	private static Logger logger = Logger.getLogger(SetOrderStateTask.class);
	private PlayList4OrderService playList4OrderService;
	private OrderService orderService;

	public void setPlayList4OrderService(
			PlayList4OrderService playList4OrderService) {
		this.playList4OrderService = playList4OrderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * 将执行完毕的订单状态设置为"执行完毕"状态码
	 * */
	public void setOrderState() {
		logger.info("定时设置执行完毕的订单及播出单状态开始");
		try {
			List<Integer> pIds = playList4OrderService.getFinishedPutInPlayList();
			List<Integer> rIds = playList4OrderService.getFinishedRequestPlayList();

			if (pIds != null && pIds.size() > 0) {
				orderService.restoreOrder(pIds);
				orderService.setOrderFinished(pIds);
				//playList4OrderService.setPlayListState(pIds,Constant.PUT_IN_ORDER);
			}
			if (rIds != null && rIds.size() > 0) {
				orderService.restoreOrder(rIds);
				orderService.setOrderFinished(rIds);
				//playList4OrderService.setPlayListState(rIds,Constant.REQUEST_ORDER);
			}
			//logger.info("定时设置执行完毕的订单及播出单状态结束,共有"+(pIds.size()+rIds.size())+"个订单执行完毕。");
			logger.info("定时设置执行完毕的订单及播出单状态结束");
		} catch (Exception e) {
			logger.error("定时设置执行完毕的订单及播出单状态时出错", e);
		}
	}

}

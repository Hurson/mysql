package com.dvnchina.advertDelivery.order.dao;

import com.dvnchina.advertDelivery.order.bean.PlayListReqPrecise;

public interface PlayListReqPreciseDao {
	/**
	 * 保存播出单精准
	 * @param p 精准对象
	 * @return 精准对象编号
	 */
	public Integer insertPrecise(PlayListReqPrecise p);
}

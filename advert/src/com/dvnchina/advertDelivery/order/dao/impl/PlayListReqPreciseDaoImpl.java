package com.dvnchina.advertDelivery.order.dao.impl;

import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.order.bean.PlayListReqPrecise;
import com.dvnchina.advertDelivery.order.dao.PlayListReqPreciseDao;

public class PlayListReqPreciseDaoImpl extends BaseDaoImpl implements PlayListReqPreciseDao {

	public Integer insertPrecise(PlayListReqPrecise p) {
		return (Integer) getHibernateTemplate().save(p);
	}

}

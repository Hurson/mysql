package com.avit.dtmb.position.dao;

import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;

public interface DPositionDao extends BaseDao{
	public PageBeanDB queryDPositionList(DAdPosition dAdPosition,int pageNo,int pageSize);
	
}

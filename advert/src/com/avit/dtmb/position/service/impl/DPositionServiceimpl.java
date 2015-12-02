package com.avit.dtmb.position.service.impl;

import javax.annotation.Resource;

import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.position.dao.DPositionDao;
import com.avit.dtmb.position.service.DPositionService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;

public class DPositionServiceimpl implements DPositionService {
	@Resource
	private DPositionDao dPositionDao;
	@Override
	public PageBeanDB queryDPositionList(DAdPosition adposition, int pageNo,
			int pagesize) {
		
		return dPositionDao.queryDPositionList(adposition, pageNo, pagesize);
	}

}

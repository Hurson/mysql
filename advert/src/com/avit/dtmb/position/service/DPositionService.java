package com.avit.dtmb.position.service;

import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;

public interface DPositionService {
	public PageBeanDB queryDPositionList(DAdPosition adposition,int pageNo,int pagesize);
}

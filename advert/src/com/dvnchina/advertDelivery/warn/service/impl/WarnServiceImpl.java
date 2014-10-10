package com.dvnchina.advertDelivery.warn.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.warn.bean.WarnInfo;
import com.dvnchina.advertDelivery.warn.dao.WarnDao;
import com.dvnchina.advertDelivery.warn.service.WarnService;

public class WarnServiceImpl implements WarnService {
	
	private WarnDao warnDao;

	@Override
	public List<WarnInfo> getEntityList() {
		return warnDao.getEntityList();
	}

	@Override
	public void deleteWarnInfo(Integer id) {
		warnDao.deleteWarnInfo(id);
	}


	public WarnDao getWarnDao() {
		return warnDao;
	}

	public void setWarnDao(WarnDao warnDao) {
		this.warnDao = warnDao;
	}


	public PageBeanDB queryWarning(int pageNo, int pageSize) {

		return warnDao.queryWarning(pageNo, pageSize);
	}

	@Override
	public void deleteWarnInfo(String ids) {
		warnDao.deleteWarnInfo(ids);
		
	}
	
	

}

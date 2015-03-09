package com.dvnchina.advertDelivery.sysconfig.service.impl;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.AreaOCG;
import com.dvnchina.advertDelivery.sysconfig.dao.OCGUpgradeDao;
import com.dvnchina.advertDelivery.sysconfig.service.OCGUpgradeService;


public class OCGUpgradeServiceImpl implements OCGUpgradeService {

	private OCGUpgradeDao ocgUpgradeDao;
	@Override
	public PageBeanDB queryAreaOCGList(AreaOCG ocg, int pageNo, int pageSize) {
		return ocgUpgradeDao.queryAreaOCGList(ocg, pageNo, pageSize);
	}

	@Override
	public void updateAreaOCG(String version, String ip) {
		ocgUpgradeDao.updateAreaOCG(version, ip);
		
	}

	public OCGUpgradeDao getOcgUpgradeDao() {
		return ocgUpgradeDao;
	}

	public void setOcgUpgradeDao(OCGUpgradeDao ocgUpgradeDao) {
		this.ocgUpgradeDao = ocgUpgradeDao;
	}

	
}

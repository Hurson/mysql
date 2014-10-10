package com.dvnchina.advertDelivery.ploy.service.impl;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.ploy.bean.TNoAdPloy;
import com.dvnchina.advertDelivery.ploy.dao.NoAdPloyDao;
import com.dvnchina.advertDelivery.ploy.service.NoAdPloyService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;

public class NoAdPloyServiceImpl implements NoAdPloyService {
	NoAdPloyDao noAdPloyDao;
	
	public NoAdPloyDao getNoAdPloyDao() {
		return noAdPloyDao;
	}

	public void setNoAdPloyDao(NoAdPloyDao noAdPloyDao) {
		this.noAdPloyDao = noAdPloyDao;
	}

	@Override
	public PageBeanDB queryNoAdPloyList(TNoAdPloy ploy, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return noAdPloyDao.queryNoAdPloyList(ploy, pageSize, pageNumber);
	}

	@Override
	public TNoAdPloy getNoAdPloyByID(Long ployId) {
		// TODO Auto-generated method stub
		return noAdPloyDao.getNoAdPloyByID(ployId);
	}

	@Override
	public PageBeanDB queryAdPosition(AdvertPosition adPosition,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return noAdPloyDao.queryAdPosition(adPosition, pageSize, pageNumber);
	}

	@Override
	public String checkNoAdPloy(TNoAdPloy ploy) {
		// TODO Auto-generated method stub
		return noAdPloyDao.checkNoAdPloy(ploy);
	}

	@Override
	public boolean saveOrUpdate(TNoAdPloy ploy) {
		// TODO Auto-generated method stub
		return noAdPloyDao.saveOrUpdate(ploy);
	}
	public boolean deleteNoAdPloy(String dataIds)
	{
		return noAdPloyDao.deleteNoAdPloy(dataIds);
	}

}

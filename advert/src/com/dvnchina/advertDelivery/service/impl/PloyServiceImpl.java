package com.dvnchina.advertDelivery.service.impl;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.dao.PloyDao;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.service.PloyService;

public class PloyServiceImpl implements PloyService{

	PloyDao ployDao;
	
	public PloyDao getPloyDao() {
		return ployDao;
	}

	public void setPloyDao(PloyDao ployDao) {
		this.ployDao = ployDao;
	}

	@Override
	public void getAdSiteByContract(String contractId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMarketRuleByAdSiteId(String adSiteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean judgePloyOccupied(String ployId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getChoiceArea() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getChannelListByArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertPloy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updatePloy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveUpdatePloy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deletePloy(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ploy> getAllPloyList(Map conditionMap, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getployCount(Map conditionMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getContractByAdMerchantId(String merchantId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ploy getPloyInfo(String ployId) {
		Ploy ploy = ployDao.getPloyInfo();
		return ploy;
	}

	

}
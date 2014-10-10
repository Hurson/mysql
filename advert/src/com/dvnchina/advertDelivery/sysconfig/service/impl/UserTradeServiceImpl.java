package com.dvnchina.advertDelivery.sysconfig.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.dao.UserTradeDao;
import com.dvnchina.advertDelivery.sysconfig.service.UserTradeService;

public class UserTradeServiceImpl implements UserTradeService{

	private UserTradeDao userTradeDao;
	public UserTradeDao getUserTradeDao() {
		return userTradeDao;
	}

	public void setUserTradeDao(UserTradeDao userTradeDao) {
		this.userTradeDao = userTradeDao;
	}

	public PageBeanDB queryUserTradeList(UserIndustryCategory industry, int pageNo, int pageSize) {
		
		return userTradeDao.queryUserTradeList(industry, pageNo, pageSize);
	}

	public UserIndustryCategory getUserTradeById(int id) {
		
		return userTradeDao.getUserTradeById(id);
	}

	public void saveUserTrade(UserIndustryCategory obj) {
		userTradeDao.saveUserTrade(obj);
	}

	public void updateUserTrade(UserIndustryCategory obj) {
		userTradeDao.updateUserTrade(obj);
		
	}

	public void deleteUserTrade(String ids) {
		ids = "(" + ids + ")";
		userTradeDao.deleteUserTrade(ids);
	}
	
	/**
	 * 判断是否存在用户行业
	 * @param industry
	 * @return  true 存在  false 不存在
	 */
	public boolean existsIndustry(UserIndustryCategory industry){
		return userTradeDao.existsIndustry(industry);
	}

}

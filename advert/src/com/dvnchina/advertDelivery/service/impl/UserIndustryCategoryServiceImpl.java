package com.dvnchina.advertDelivery.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.dao.UserIndustryCategoryDao;
import com.dvnchina.advertDelivery.service.UserIndustryCategoryService;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;

public class UserIndustryCategoryServiceImpl implements UserIndustryCategoryService {

	private static Logger logger = Logger.getLogger(UserIndustryCategoryServiceImpl.class);

	private UserIndustryCategoryDao userIndustryCategorydao;

	@Override
	public int getUserIndustryCategoryCount(UserIndustryCategory userIndustryCategory, String state) {

		return userIndustryCategorydao.getUserIndustryCategoryCount(userIndustryCategory, state);
	}

	@Override
	public List<UserIndustryCategory> listUserIndustryCategoryMgr(UserIndustryCategory userIndustryCategory, int x, int y,String state) {

		return userIndustryCategorydao.listUserIndustryCategoryMgr(userIndustryCategory, x, y, state);
	}

	public UserIndustryCategoryDao getUserIndustryCategorydao() {
		return userIndustryCategorydao;
	}

	public void setUserIndustryCategorydao(
			UserIndustryCategoryDao userIndustryCategorydao) {
		this.userIndustryCategorydao = userIndustryCategorydao;
	}

}

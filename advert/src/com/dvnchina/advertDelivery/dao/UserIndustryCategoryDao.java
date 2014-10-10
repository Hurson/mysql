package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;

public interface UserIndustryCategoryDao {
	
	/**
	 * 查询记录总数
	 */
	public int getUserIndustryCategoryCount(UserIndustryCategory userIndustryCategory,String state);
	
	/**
	 * 查询总的结果集
	 */
	
	public List<UserIndustryCategory> listUserIndustryCategoryMgr(UserIndustryCategory userIndustryCategory,int x,int y,String state);
	

}

package com.dvnchina.advertDelivery.sysconfig.service;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;

public interface UserTradeService {
	/**
	 * 分页查信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryUserTradeList(UserIndustryCategory industry, int pageNo, int pageSize);
	
	/**
	 * 根据ID获取配置项信息
	 * @param id
	 * @return UserIndustryCategory
	 */
	public UserIndustryCategory getUserTradeById(int id);
	
	/**
	 * 保存
	 * @param UserIndustryCategory
	 * @return int
	 */
	public void saveUserTrade(UserIndustryCategory obj);
	
	/**
	 * 修改
	 * @param UserIndustryCategory
	 * 
	 */
	public void updateUserTrade(UserIndustryCategory obj);
	
	/**
	 * 保存
	 * @param List<UserIndustryCategory>
	 * 
	 */
	public void deleteUserTrade(String ids);
	
	/**
	 * 判断是否存在用户行业
	 * @param industry
	 * @return  true 存在  false 不存在
	 */
	public boolean existsIndustry(UserIndustryCategory industry);
}

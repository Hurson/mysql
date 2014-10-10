package com.dvnchina.advertDelivery.sysconfig.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;

public interface UserRankService {
	
	/**
	 * 分页查询用户级别信息
	 * @param userRank
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryUserRankList (UserRank userRank,int pageNo, int pageSize);
	
	/**
	 * 根据ID获取用户级别信息
	 * @param id
	 * @return
	 */
	public UserRank getUserRankById(Integer id);
	
	/**
	 * 判断是否已经存在用户级别
	 * @param userRank
	 * @return
	 */
	public boolean existsUserRank(UserRank userRank);
	
	/**
	 * 保存用户级别信息（新增或修改）
	 * @param userRank
	 */
	public void saveUserRank(UserRank userRank);
	
	/**
	 * 根据ids删除用户级别
	 * @param ids
	 */
//	public void delUserRankByIds(String ids);
	
	/**
	 * 根据ids删除用户级别
	 * @param list
	 */
	public void delUserRank(List<UserRank> list);

}

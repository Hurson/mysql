package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.Location;


/**
 * 维护用户和地区的关系
 * 
 * @author Administrator
 *
 */
public interface UserLocationDao  extends BaseDao{
	
	/**
	 * 删除绑定关系，根据用户的ID
	 * @param userId
	 * @return
	 */
	public boolean deleteUserLocationBanding(Integer userId);
	
	/**
	 * 获取用户拥有的地区
	 * 
	 * @param userId
	 * @return
	 */
	public List<Location> getUserOwnLocation(Integer userId);
	
}

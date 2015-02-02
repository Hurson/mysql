package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
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
	
	/**
	 * 获取用户拥有的地区码
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserOwnLocationCodes(Integer userId);
	
	/**
	 * 获取当前用户具有访问权限的用户id
	 * @param userId
	 * @return
	 */
	public List<Integer> getAccessUserIdList(Integer userId);
	/**
	 * 分页查询所有区域
	 * @param start
	 * @param size
	 * @return
	 */
	public PageBeanDB queryAllLocations(int start, int size);
	
}

package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.UserPositionPackage;

/**
 * 用户和客户的关系接口
 * 
 * @author Administrator
 *
 */
public interface UserCustomerDao  extends BaseDao{
	/**
	 * 删除    用户和客户的绑定关系，根据用户的ID
	 * @param userId
	 * @return
	 */
	public boolean deleteUserCustomerBanding(Integer userId);
	
	/**
	 * 获取广告商   用户是所属的那些广告商
	 * 
	 * @param userId
	 * @return
	 */
	public List<Customer> getUserOwnCustomer(Integer userId);
	
	public Integer getCustomerIds(Integer userId);
	
	public List<Integer> getPositionPackageIds(Integer userId, Integer roleType);
	
	public void addUserAdvertPackage(List<UserPositionPackage> list);

	public void deleteUserAdvertPackageBinding(Integer userId);

	public int getRoleTypeByUserId(Integer id);
	
	public void deleteUserDtmbPositionBanding(Integer userId);
}

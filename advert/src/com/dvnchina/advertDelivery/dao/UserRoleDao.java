package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.sysconfig.bean.Role;



public interface UserRoleDao  extends BaseDao {
	
	/**
	 * 删除记录
	 * @param userId
	 */
	public void deleteUserRoleByUserId(Integer userId);
	
	/**
	 * 批量删除，根据用户的ID删除用户下绑定的角色
	 * 
	 * @param userId  用户的ID  java.lang.Integer
	 */
	public void deleteBatchUserRoleByUserId(Integer userId);
	
	/**
	 * 删除     用户绑定的一个角色
	 * 
	 * @param userId   用户的ID	
	 * @param roleId   角色的ID
	 */
	public void deleteSingleUserRoleBinding(Integer userId, Integer roleId);
	
	/**
	 * 查询     用户绑定的所有角色
	 * 
	 * @param userId   用户的ID	
	 */
	public List<Role> getRoleListByUserId(Integer userId);
	
}
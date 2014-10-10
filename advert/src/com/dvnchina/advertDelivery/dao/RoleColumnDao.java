package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;

public interface RoleColumnDao extends BaseDao {
	
	/**
	 * 批量删除，根据角色的ID，删除角色下的所有的栏目
	 * @param roleId   角色的ID  java.lang.Integer
	 * 
	 */
	public void deleteBatchRoleColumnBinding(Integer roleId);
	
	/**删除  角色和栏目的一个绑定，根据角色的ID和栏目的ID
	 * @param roleId   角色的ID		java.lang.Integer
	 *  				
	 * @param columnId  栏目的ID 	java.lang.Integer
	 */
	public void deleteSingleRoleColumnBinding(Integer roleId, Integer columnId);
	
	/**
	 * 根据栏目的ID，查询那些角色拥有当前栏目
	 * @param columnId 栏目的ID
	 * @return  角色的集合
	 */
	public List<Role> getRoleListByColumnId(Integer columnId);
	/**
	 * 根据栏目的ID，查询那些角色拥有当前栏目
	 * @param columnId 栏目的ID
	 * @return  角色的集合
	 */
	public List<Column> getColumnListByRoleId(Integer roleId);
	
}

package com.dvnchina.advertDelivery.sysconfig.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;

public interface RoleDao extends BaseDao{
	
	/**
	 * 分页查询角色信息
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryRoleList(Role role,int pageNo, int pageSize);
	
	/**
	 * 根据输入的角色名称验证用户是否存在
	 * @param role
	 * @return boolean
	 */
	public boolean checkRoleName(Role role);
	
	/**
	 * 获取所有的角色
	 * 
	 * @return
	 */
	public List<Role> getAllRoleList();
	
	/**
	 * 获取单个对象
	 * @param roleId
	 * @return
	 */
	public Role getRoleById(Integer roleId);
	
	/**
	 * 根本角色ID获取栏目列表
	 * @param roleId
	 * @return
	 */
	public List<Column> getColumnListByRoleId(Integer roleId);
	
	/**
	 * 检查角色是否绑定有用户记录
	 * @param ids
	 * @return
	 */
	public boolean checkRoleUserBinging(String ids);
	
	/**
	 * 根据用户ID获取角色类型
	 * @param userId
	 * @return
	 */
	public Integer getRoleTypeByUser(Integer userId);

}

package com.dvnchina.advertDelivery.sysconfig.service.impl;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.dao.RoleDao;
import com.dvnchina.advertDelivery.sysconfig.service.RoleService;

public class RoleServiceImpl implements RoleService{
	
	private RoleDao roleDao;
	
	/**
	 * 分页查询角色信息
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryRoleList(Role role,int pageNo, int pageSize){
		return roleDao.queryRoleList(role,pageNo,pageSize);
	}
	
	/**
	 * 根据输入的角色名称验证用户是否存在
	 * @param role
	 * @return boolean
	 */
	public boolean checkRoleName(Role role){
		return roleDao.checkRoleName(role);
	}
	
	/**
	 * 新增角色
	 * @param Role  
	 * @return
	 */
	public void insertRole(Role role){
		role.setCreateTime(new Date());
		role.setModifyTime(new Date());
		roleDao.save(role);
	}
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> getAllRole(){
		return roleDao.getAllRoleList();
	}
	
	/**
	 * 获取一个角色
	 * @param roleId
	 * @return
	 */
	public Role getRoleById(Integer roleId){
		return roleDao.getRoleById(roleId);
	}
	
	/**
	 * 根本角色ID获取栏目列表
	 * @param roleId
	 * @return
	 */
	public List<Column> getColumnListByRoleId(Integer roleId){
		return roleDao.getColumnListByRoleId(roleId);
	}
	
	/**
	 * 修改角色
	 * @param Role
	 * @return
	 */
	public void updateRole(Role role){
		role.setModifyTime(new Date());
		roleDao.update(role);
	}
	
	/**
	 * 检查角色是否绑定有用户记录
	 * @param ids
	 * @return
	 */
	public boolean checkRoleUserBinging(String ids){
		return roleDao.checkRoleUserBinging(ids);
	}
	
	/**
	 * 删除角色
	 * @param list
	 */
	public void delRole(List<Role> list){
		roleDao.deleteAll(list);
	}
	
	/**
	 * 根据用户ID获取角色类型
	 * @param userId
	 * @return
	 */
	public Integer getRoleTypeByUser(Integer userId){
		return roleDao.getRoleTypeByUser(userId);
	}
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

}

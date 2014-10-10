package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.bean.User;

/**
 * 系统权限
 * 
 * <li>1、用户和角色的绑定              多对一</li>
 * 
 * <li>2、角色和栏目的绑定              多对多</li>
 * 
 * <li>3、用户与地区的关系		多对多</li>
 * 
 * <li>4、用户与广告商的关系	多对多</li>
 * 
 * <li>5、用户与运营商的关系	多对多</li>
 * @author Administrator
 *
 */
public interface PurviewService {
	
	
	/******** 用户和角色的关系 **** 开始******************************************/
	
	/**
	 * 添加     用户绑定一个角色
	 * 
	 * @param java.lang.Integer
					userId  用户的ID
	 * @param java.lang.Integer  
	                roleId  角色的ID
	 * @return
	 */
	public int addUserRoleSingleBinding(Integer userId,Integer roleId);
	
	/**
	 * 添加    用户批量绑定角色
	 * @param userId
	 * @param java.util.List<Integer>
	 *                    roleIdList   用户的角色的ID集合
	 * @return
	 */
	public boolean addUserRoleBatchBinding(Integer userId,List<Integer> roleIdList);
	
	/**
	 * 删除  用户和一个角色的绑定
	 * 
	 * @param java.lang.Integer
					userId  用户的ID
	 * @param java.lang.Integer  
	                roleId  角色的ID
	 */
	public void deleteUserRoleSingleBinding(Integer userId,Integer roleId);
	
	/**
	 * 删除  用户和角色的所有绑定（注销用户）
	 * 
	 * @param userId    用户的ID 
	 * 		  			java.lang.Integer
	 */
	public void deleteUserRoleAllBinding(Integer userId);
	
	//******************************************************************************/
	/*******用户与地区的关系维护*******************************************************/
	
	/**
	 * 添加    用户批量绑定地区
	 * @param userId
	 * @param java.util.List<Integer>
	 *                    roleIdList   用户的角色的ID集合
	 * @return
	 */
	public boolean addUserLocationBatchBinding(Integer userId,String[] locationCode);
	
	/** 
	 * 删除   用户和地区的绑定
	 * 
	 * @param userId
	 * @return
	 */
	public boolean deleteUserLocationBanding(Integer userId);
	
	/**
	 * 获取地区    用户所在的地区
	 * @param userId
	 * @return
	 */
	public List<Location> getUserOwnLocation(Integer userId);
	
	
	//***********************************************************************************/
	/*******用户与客户的关系维护************************************************************/
	
	/**
	 * 添加    用户批量绑定客户
	 * @param userId
	 * @param java.util.List<Integer>
	 *                    roleIdList   用户的角色的ID集合
	 * @return
	 */
	public boolean addUserCustomerBatchBinding(Integer userId,List<Integer> customerIdList);
	
	/**
	 * 删除  用户和客户的绑定
	 * 
	 * @param userId
	 * @return
	 */
	public boolean deleteUserCustomerBatchBinding(Integer userId);
	
	/**
	 * 获取客户  用户绑定的广告商
	 * @param userId
	 * @return
	 */
	public List<Customer> getUserOwnCustomer(Integer userId);
	
	
	//**************************************************************************************/
	/***********角色和栏目的关系***************************************************************/
	/**
	 * 添加角色和栏目的绑定
	 * 
	 * @param roleId java.lang.Integer
	 * @param columnId java.lang.Integer 
	 * 					
	 * @return
	 */
	public int addRoleColumnBinding(Integer roleId,Integer columnId);
	
	/**
	 * 添加角色和栏目的绑定
	 * 
	 * @param roleId java.lang.Integer
	 * 					
	 * @param columnIdList java.util.List<Integer> 
	 * 					 
	 * @return
	 */
	public int addRoleColumnBinding(Integer roleId,List<Integer> columnIdList);

	/**
	 * 删除  角色绑定的"一个"栏目
	 * 
	 * @param roleId java.lang.Integer
	 * @param columnId java.lang.Integer  
	 * 					
	 * @return
	 */
	public int deleteRoleColumnSingleBinding(Integer roleId,Integer columnId);
	
	/**
	 * 删除  角色绑定的"所有"栏目
	 * 
	 * @param roleId java.lang.Integer
	 */
	public int deleteRoleColumnAllBinding(Integer roleId);
	
	
	//*********************************************************************************************/
	
	
	/**
	 * 根据userId,  获取用户下的所有角色
	 * 
	 * @param userId  java.lang.Integer
	 * @return List<Role>
	 */
	public List<Role> getUserOwnRoleList(Integer userId);
	
	/**
	 * 根据roleId,  获取角色是那些用户拥有
	 * 
	 * @param roleId  java.lang.Integer
	 * @return List<Column>
	 */
	public List<Column> getRoleOwnColumnList(Integer roleId);
	
	/**
	 * 根据userId,  获取用户下的所有角色下的所有栏目
	 * 
	 * @param userId  java.lang.Integer
	 * @return List<Column>
	 */
	public List<Column> getUserOwnColumnList(Integer userId);
	
	/**
	 * 根据roleId,  获取角色是那些用户拥有的
	 * 
	 * @param roleId  java.lang.Integer
	 * @return List<User>
	 */
	public List<User> getRoleIshaveUserList(Integer roleId);
	
	/**
	 * 根据columnId,  获取栏目是那些角色的绑定
	 * 
	 * @param columnId  java.lang.Integer
	 * @return List<User>
	 */
	public List<Role> getColumnIshaveRoleList(Integer columnId);
	
	/**
	 * 插入和用户相关的数据
	 * 
	 * @param user
	 * @return
	 */
	public void insertUserBandingData(User user);

	public void deleteUserAdvertPackageBinding(Integer userId);
}

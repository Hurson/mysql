package com.dvnchina.advertDelivery.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.bean.User;

/**
 * 
 * 系统安全---登录和认证
 * @author Administrator
 *
 */
public interface SecurityService {
	
	
	/**
	 * 登陆前检查
	 * @param loginName  用户登陆名
	 * @param password   用户登陆密码
	 * @return 登陆成功
	 */
	public User queryUser(String loginName,String password);
	
	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User userLogin(HttpServletRequest request,
			HttpServletResponse response,String loginName, String password);

	/**
	 * 根据userId,  获取用户下的所有角色
	 * 
	 * @param userId  java.lang.Integer
	 * @return List<Role>
	 */
	public List<Role> getUserOwnRoleList(Integer userId);
	
	/**
	 * 获取  用户下拥有权限
	 * 
	 * @param userId java.lang.String 
	 *        		 用户的Id
	 * @return
	 */
	public List<Column> getColumnByUserId(String userId);
	
	/**
	 * 获取地区    用户所在的地区
	 * @param userId
	 * @return
	 */
	public List<Location> getUserOwnLocation(Integer userId);
	
	/**
	 * 获取地区    用户所在的地区
	 * @param userId
	 * @return
	 */
	public List<Customer> getUserOwnCustomer(Integer userId);
	
	
	/**
	 * 用户退出
	 * @param user 用户
	 */
	public void logout(User user);

	List<Integer> getPositionPackageIds(Integer userId);

	Integer getCustomerIds(Integer userId);

	public int getRoleTypeByUserId(Integer id);

}

package com.dvnchina.advertDelivery.sysconfig.service;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.User;

public interface UserService {
	
	/**
	 * 分页查询用户信息
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryUserList(User user,int pageNo, int pageSize);
	
	/**
	 * 根据输入的登录名验证用户是否存在
	 * @param user
	 * @return boolean
	 */
	public boolean checkLoginName(User user);
	
	/**
	 * 新增用户
	 * @param user  
	 * @return
	 */
	public int insertUser(User user);
	
	/**
	 * 根据用户ID获取用户明细
	 * @param userId
	 * @return
	 */
	public User getUserDetailById(Integer userId);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public void updateUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public void deleteUser(Integer userId);
	
	public String changePassword(Integer userId,String oldpassword,String newpassword);
}

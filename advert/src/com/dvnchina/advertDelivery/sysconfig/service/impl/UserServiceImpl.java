package com.dvnchina.advertDelivery.sysconfig.service.impl;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.User;
import com.dvnchina.advertDelivery.sysconfig.dao.UserDao;
import com.dvnchina.advertDelivery.sysconfig.service.UserService;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	
	/**
	 * 分页查询用户信息
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryUserList(User user,int pageNo, int pageSize){
		return userDao.queryUserList(user,pageNo,pageSize);
	}
	
	/**
	 * 根据输入的登录名验证用户是否存在
	 * @param user
	 * @return boolean
	 */
	public boolean checkLoginName(User user){
		return  userDao.checkLoginName(user);
	}
	
	/**
	 * 新增用户
	 * @param user  
	 * @return
	 */
	public int insertUser(User user){
		return  userDao.save(user);
	}
	
	/**
	 * 根据用户ID获取用户明细
	 * @param userId
	 * @return
	 */
	public User getUserDetailById(Integer userId){
		return  userDao.getUserDetailById(userId);
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public void updateUser(User user){
		userDao.update(user);
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public void deleteUser(Integer userId){
		userDao.deleteUser(userId);
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public String changePassword(Integer userId,String oldpassword,String newpassword)
	{
		return userDao.changePassword(userId, oldpassword, newpassword);
	}
}

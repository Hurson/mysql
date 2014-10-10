package com.dvnchina.advertDelivery.sysconfig.dao;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.sysconfig.bean.User;

public interface UserDao extends BaseDao{
	
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
	 * 根据用户名和密码获取用户对象
	 * @param username
	 * @param passwd
	 * @return
	 */
	public User getUserByUserNameAndPasswd(String username,String passwd);
	
	/**
	 * 根据用户ID获取用户明细
	 * @param userId
	 * @return
	 */
	public User getUserDetailById(Integer userId);
	
	/**
	 * 更新用户删除标识
	 * @param userId
	 */
	public void deleteUser(Integer userId);

	public String changePassword(Integer userId,String oldpassword,String newpassword);
}

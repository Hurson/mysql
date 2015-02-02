package com.dvnchina.advertDelivery.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dvnchina.advertDelivery.dao.ColumnDao;
import com.dvnchina.advertDelivery.dao.UserCustomerDao;
import com.dvnchina.advertDelivery.dao.UserLocationDao;
import com.dvnchina.advertDelivery.dao.UserRoleDao;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.service.SecurityService;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.bean.User;
import com.dvnchina.advertDelivery.sysconfig.dao.RoleDao;
import com.dvnchina.advertDelivery.sysconfig.dao.UserDao;
import com.dvnchina.advertDelivery.utils.CookieUtils;

public class SecurityServiceImpl implements SecurityService{

	private UserDao userDao;
	private RoleDao roleDao;
	private ColumnDao columnDao;
	private UserRoleDao userRoleDao;
	private UserLocationDao userLocationDao;
	private UserCustomerDao userCustomerDao;
	
	public UserCustomerDao getUserCustomerDao() {
		return userCustomerDao;
	}

	public void setUserCustomerDao(UserCustomerDao userCustomerDao) {
		this.userCustomerDao = userCustomerDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public ColumnDao getColumnDao() {
		return columnDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}
	
	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public UserLocationDao getUserLocationDao() {
		return userLocationDao;
	}

	public void setUserLocationDao(UserLocationDao userLocationDao) {
		this.userLocationDao = userLocationDao;
	}

	@Override
	public User queryUser(String loginName, String password) {
		
		return userDao.getUserByUserNameAndPasswd(loginName, password);
	}
	
	@Override
	public User userLogin(HttpServletRequest request,
			HttpServletResponse response,String loginName, String password) {
		
		User user = userDao.getUserByUserNameAndPasswd(loginName, password);
		
		if(user != null && "0".equals(user.getState())){
			CookieUtils.setCookie(request, response, "USER_ID", ""+user.getUserId());
			CookieUtils.setCookie(request, response, "USER_NMAE", ""+user.getUserName());
			
		}
		
		return user;
	
		
	}

	@Override
	public void logout(User user) {
		
	}


	@Override
	public List<Column> getColumnByUserId(String userId) {
		List<Column> columnList = columnDao.queryColumnByUserId(userId);
		Set<Column>  columnSet = new HashSet<Column>();
		for(Column c : columnList){
			columnSet.add(c);
		}
		List<Column>    columns = new ArrayList<Column>();
		columns.addAll(columnSet);
		return columns;
	}

	

	@Override
	public List<Location> getUserOwnLocation(Integer userId) {
		return userLocationDao.getUserOwnLocation(userId);
	}

	@Override
	public String getUserOwnLocationCodes(Integer userId){
		
		StringBuffer sb = new StringBuffer();
		List<String> locationCodeList = userLocationDao.getUserOwnLocationCodes(userId);
		
		for(String locationCode : locationCodeList){
			sb.append("," + locationCode);
		}
		if(sb.length() > 0){
			return sb.toString().substring(1);
		}
		return "";
		
	}
	
	@Override
	public List<Integer> getAccessUserIdList(Integer userId){
		User user = userDao.getUserDetailById(userId);
		if(user !=null && user.getRoleType()==2){
			return userLocationDao.getAccessUserIdList(userId);
		}else {
			List<Integer> userIdList = new ArrayList<Integer>();
			userIdList.add(userId);
			return userIdList;
		}
		
	}

	@Override
	public List<Role> getUserOwnRoleList(Integer userId) {
		return userRoleDao.getRoleListByUserId(userId);
	}

	@Override
	public List<Customer> getUserOwnCustomer(Integer userId) {
		return userCustomerDao.getUserOwnCustomer(userId);
	}
	@Override
	public Integer getCustomerIds(Integer userId){
		return userCustomerDao.getCustomerIds(userId);
	}
	@Override
	public List<Integer> getPositionPackageIds(Integer userId){
		return userCustomerDao.getPositionPackageIds(userId);
	}

	@Override
	public int getRoleTypeByUserId(Integer id) {
		return userCustomerDao.getRoleTypeByUserId(id);
	}

}

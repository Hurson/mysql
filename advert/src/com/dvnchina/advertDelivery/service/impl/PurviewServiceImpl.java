package com.dvnchina.advertDelivery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.constant.PurviewConstant;
import com.dvnchina.advertDelivery.dao.RoleColumnDao;
import com.dvnchina.advertDelivery.dao.UserCustomerDao;
import com.dvnchina.advertDelivery.dao.UserLocationDao;
import com.dvnchina.advertDelivery.dao.UserRoleDao;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.model.RoleColumn;
import com.dvnchina.advertDelivery.model.UserCustomer;
import com.dvnchina.advertDelivery.model.UserLocation;
import com.dvnchina.advertDelivery.model.UserPositionPackage;
import com.dvnchina.advertDelivery.model.UserRole;
import com.dvnchina.advertDelivery.service.PurviewService;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.bean.User;
import com.dvnchina.advertDelivery.utils.StringUtil;

/**
 * 系统权限
 *  1、用户和角色的绑定 多对多 
 *  2、角色和栏目的绑定 多对多 
 *  3、用户与地区的关系 一对多
 *  4、用户与广告商的关系 多对多
 *  5、用户与运营商的关系 多对一
 *  6、运营商角色和广告位多对多关系
 * @author Administrator
 *
 */
public class PurviewServiceImpl implements PurviewService{
	
	private RoleColumnDao roleColumnDao;
	private UserRoleDao userRoleDao;
	private UserLocationDao userLocationDao;
	private UserCustomerDao userCustomerDao;

	public RoleColumnDao getRoleColumnDao() {
		return roleColumnDao;
	}

	public void setRoleColumnDao(RoleColumnDao roleColumnDao) {
		this.roleColumnDao = roleColumnDao;
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

	public UserCustomerDao getUserCustomerDao() {
		return userCustomerDao;
	}

	public void setUserCustomerDao(UserCustomerDao userCustomerDao) {
		this.userCustomerDao = userCustomerDao;
	}
	
	@Override
	public PageBeanDB queryAreasList(int pageNo, int pageSize){
		return this.userLocationDao.queryAllLocations(pageNo, pageSize);
		
	}
	@Override
	public int addRoleColumnBinding(Integer roleId, Integer columnId) {
		try {
			RoleColumn roleColumn = new RoleColumn();
			roleColumn.setRoleId(roleId);
			roleColumn.setColumnId(columnId);
			return roleColumnDao.save(roleColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int addRoleColumnBinding(Integer roleId, List<Integer> columnIdList) {
		for(Integer columnId : columnIdList){
			this.addRoleColumnBinding(roleId, columnId);
		}
		return columnIdList.size();
	}

	@Override
	public boolean addUserRoleBatchBinding(Integer userId, List<Integer> roleIdList) {
		try {
			for(Integer roleId : roleIdList){
				this.addUserRoleSingleBinding(userId, roleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int addUserRoleSingleBinding(Integer userId, Integer roleId) {
		UserRole userRole = new UserRole();
		userRole.setUserID(userId);
		userRole.setRoleID(roleId);
		return userRoleDao.save(userRole);
	}

	@Override
	public int deleteRoleColumnAllBinding(Integer roleId) {
		roleColumnDao.deleteBatchRoleColumnBinding(roleId);
		return 0;
	}

	@Override
	public int deleteRoleColumnSingleBinding(Integer roleId, Integer columnId) {
		roleColumnDao.deleteSingleRoleColumnBinding(roleId, columnId);
		return 0;
	}

	@Override
	public void deleteUserRoleAllBinding(Integer userId) {
		//userRoleDao.deleteUserRoleByUserId(userId);
		userRoleDao.deleteBatchUserRoleByUserId(userId);
		
	}

	

	@Override
	public void deleteUserRoleSingleBinding(Integer userId, Integer roleId) {
		userRoleDao.deleteSingleUserRoleBinding(userId, roleId);
	}

	@Override
	public List<Role> getColumnIshaveRoleList(Integer columnId) {
		return roleColumnDao.getRoleListByColumnId(columnId);
	}

	@Override
	public List<User> getRoleIshaveUserList(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column> getRoleOwnColumnList(Integer roleId) {
		
		return roleColumnDao.getColumnListByRoleId(roleId);
	}

	@Override
	public List<Column> getUserOwnColumnList(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> getUserOwnRoleList(Integer userId) {
		return userRoleDao.getRoleListByUserId(userId);
	}

	@Override
	public boolean addUserCustomerBatchBinding(Integer userId, List<Integer> customerIdList) {
		try {
			for (Integer customerId : customerIdList) {
				UserCustomer userCustomer = new UserCustomer();
				userCustomer.setUserId(userId);
				userCustomer.setCustomerId(customerId);
				userCustomerDao.save(userCustomer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean addUserLocationBatchBinding(Integer userId, String[] locationCodes) {
		boolean flag = true;
		try {
			for(String areaCode:locationCodes){
				if(StringUtils.isNotBlank(areaCode)){
					UserLocation userLocation =  new UserLocation();
					userLocation.setUserId(userId);
					userLocation.setAreaCode(areaCode);
					userLocationDao.save(userLocation);
				}
			}
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public boolean deleteUserLocationBanding(Integer userId) {
		
		return userLocationDao.deleteUserLocationBanding(userId);
	}
	
	@Override
	public boolean deleteUserCustomerBatchBinding(Integer userId) {
		
		return userCustomerDao.deleteUserCustomerBanding(userId);
	}
	
	

	@Override
	public List<Location> getUserOwnLocation(Integer userId) {
		
		return userLocationDao.getUserOwnLocation(userId);
	}

	@Override
	public List<Customer> getUserOwnCustomer(Integer userId) {
		
		return userCustomerDao.getUserOwnCustomer(userId);
	}

	/**
	 * 插入和用户相关的数据
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
//	@Override
//	public boolean insertUserBandingData(HttpServletRequest request, Integer userId){
//		
//		String user_roles = request.getParameter("user_roles");
//		
//		String user_locations = request.getParameter("user_locations");
//		String p_user_locations = request.getParameter("p_user_locations");//有子必有父
//		
//		boolean flag1=true,flag2=true,flag3 = true;
//		
//		if(StringUtils.isNotBlank(user_locations)){
//			String[] locationCodes = user_locations.split(PurviewConstant.SIGN_COMMA);
//			flag1 = this.addUserLocationBatchBinding(userId, locationCodes);//保存用户和地区的关系
//		}
//		
//		if(StringUtils.isNotBlank(p_user_locations)){
//			String[] locationPCodes = p_user_locations.split(PurviewConstant.SIGN_COMMA);
//			Set<String> set = new HashSet<String>();
//			for(String s : locationPCodes){
//				set.add(s);
//			}
//			String[] strSet = new String[set.size()];
//			flag1 = this.addUserLocationBatchBinding(userId, set.toArray(strSet));//保存用户和地区的关系
//		}
//		
//		if(StringUtils.isNotBlank(user_roles)){
//			List<Integer> roleIdL= StringUtil.getIntegerList(user_roles, PurviewConstant.SIGN_SEMICOLON);
//			flag2 = this.addUserRoleBatchBinding(userId, roleIdL);//保存用户和角色的关系
//		}
//		
//		String user_customers = request.getParameter("user_customers");
//		if(StringUtils.isNotBlank(user_customers) && StringUtils.isNotBlank(user_roles)){
//			List<Integer> customerIdL= StringUtil.getIntegerList(user_customers, PurviewConstant.SIGN_SEMICOLON);
//			flag3 = this.addUserCustomerBatchBinding(userId, customerIdL);//保存用户和客户的关系
//		}
//		if(flag1&&flag2&&flag3)return true;else return false;
//	}
	
	/**
	 * 插入和用户相关的数据
	 * 
	 * @param user
	 * @return
	 */
	public void insertUserBandingData(User user){
		
		//保存用户和角色的关系
		this.addUserRoleSingleBinding(user.getUserId(), user.getRoleId());
		List<Integer> customerIdL= StringUtil.getIntegerList(user.getCustomerIds(), PurviewConstant.SIGN_COMMA);
		//保存用户和广告商的关系
		this.addUserCustomerBatchBinding(user.getUserId(), customerIdL);
		
		List<Integer> advertpositionPackages = StringUtil.getIntegerList(user.getPositionIds(), PurviewConstant.SIGN_COMMA);
		this.addUserAdvertPositionPackageBinding(user.getUserId(), advertpositionPackages);
		String[] areaCodes = user.getAreaCodes().split(PurviewConstant.SIGN_COMMA);
		this.addUserLocationBatchBinding(user.getUserId(),areaCodes);
	}

	/**
	 * 保存用户和广告位关联表
	 * @param userId
	 * @param advertpositionPackages
	 */
	private void addUserAdvertPositionPackageBinding(Integer userId,
			List<Integer> advertpositionPackages) {
		List<UserPositionPackage> list = new ArrayList<UserPositionPackage>();
		for (Integer packageId : advertpositionPackages) {
			UserPositionPackage obj = new UserPositionPackage();
			obj.setUserId(userId);
			obj.setAdvertPositionPackageId(packageId);
			list.add(obj);
		}
		userCustomerDao.addUserAdvertPackage(list);
		
	}

	@Override
	public void deleteUserAdvertPackageBinding(Integer userId) {
		userCustomerDao.deleteUserAdvertPackageBinding(userId);
		
	}
	
}

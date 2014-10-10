package com.dvnchina.advertDelivery.sysconfig.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.bean.User;
import com.dvnchina.advertDelivery.sysconfig.dao.UserDao;
import com.dvnchina.advertDelivery.utils.Transform;

public class UserDaoImpl extends BaseDaoImpl implements UserDao{
	
	/**
	 * 分页查询用户信息
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryUserList(User user,int pageNo, int pageSize){
		StringBuffer sql =  new StringBuffer();
		sql.append("select u.user_id,u.name,u.user_name,u.password,u.mail,");
		sql.append(" date_format(u.create_time,'%Y-%m-%d %H:%i:%S'),date_format(u.modify_time,'%Y-%m-%d %H:%i:%S'), ");
		sql.append(" u.state,r.role_id ");
		sql.append(" from t_user u, t_user_role ur, t_role r ");
		sql.append(" where u.user_id<>1 and u.user_id = ur.user_id and ur.role_id=r.role_id and u.delflag = 0 ");
		
		if(user != null){
			if (StringUtils.isNotBlank(user.getUserName())) {
				sql.append(" and u.name like '%" + user.getUserName().trim() + "%' ");
			}
			if (StringUtils.isNotBlank(user.getLoginName())) {
				sql.append(" and u.user_name like '%" + user.getLoginName().trim() + "%' ");
			}
			if (StringUtils.isNotBlank(user.getEmail())) {
				sql.append(" and u.mail like '%" + user.getEmail().trim() + "%' ");
			}
		}
		
		sql.append(" order by u.modify_time desc");
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<User> list = getUserList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<User> getUserList(List<?> resultList) {
		List<User> list = new ArrayList<User>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			User u = new User();
			u.setUserId(toInteger(obj[0]));
			u.setUserName((String)(obj[1]));
			u.setLoginName((String)(obj[2]));
			u.setPassword((String)(obj[3]));
			u.setEmail((String)(obj[4]));
			try{
				u.setCreateTime(Transform.string2Date((String)obj[5], "yyyy-MM-dd HH:mm:ss"));
				u.setModifyTime(Transform.string2Date((String)obj[6], "yyyy-MM-dd HH:mm:ss"));
			}catch(Exception e){
				e.printStackTrace();
			}
			u.setState(((Character)(obj[7])).toString());
			u.setRoleName(getRoleById(toInteger(obj[8])).getName());
			list.add(u);
		}
		return list;
	}
	
	/**
	 * 获取角色
	 * @param roleId
	 * @return
	 */
	private Role getRoleById(Integer roleId){
		return getHibernateTemplate().get(Role.class, roleId);
	}
	
	/**
	 * 根据用户名和密码获取用户对象
	 * @param username
	 * @param passwd
	 * @return
	 */
	public User getUserByUserNameAndPasswd(String username, String password) {
		
		User result = null;
		StringBuffer sb = new StringBuffer("from User where delFlag=0 ");
		if(StringUtils.isNotBlank(username)){
			sb.append(" and loginName= '"+username+"'");
		}
//		if(StringUtils.isNotBlank(password)){
//			sb.append(" and password= '"+password+"'");
//		}
		
		List<User> userList = this.list(sb.toString());
		
		if(userList!=null &&userList.size()>0){
			result = userList.get(0);
		}else {
			result = null;
		}
		return result;
	}
	
	/**
	 * 根据输入的登录名验证用户是否存在
	 * @param user
	 * @return boolean
	 */
	public boolean checkLoginName(User user){
		StringBuffer sb = new StringBuffer("from User where delFlag=0 ");
		if(user != null){
			if(StringUtils.isNotBlank(user.getLoginName())){
				sb.append(" and loginName= '"+user.getLoginName().trim()+"'");
			}
			if(user.getUserId() != null && user.getUserId() != 0){
				sb.append(" and userId != "+user.getUserId());
			}
		}
		
		List<User> userList = this.list(sb.toString());
		
		if(userList!=null &&userList.size()>0){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 根据用户ID获取用户明细
	 * @param userId
	 * @return
	 */
	public User getUserDetailById(Integer userId){
		StringBuffer sql =  new StringBuffer();
		sql.append("select u.user_id,u.name,u.user_name,u.password,u.mail,u.create_time,u.modify_time,u.state,u.delflag");
		sql.append(" ,r.role_id,r.name as roleName,r.type ");
		sql.append(" from t_user u, t_user_role ur, t_role r ");
		sql.append(" where u.user_id = ur.user_id and ur.role_id=r.role_id and u.user_id = ?");
		User user = getUserDetail(this.getDataBySql(sql.toString(), new Object[]{userId}));
		
		
		StringBuffer sql2 =  new StringBuffer();
		sql2.append(" select c.id,c.advertisers_name from t_user u, t_user_adcustomer ua, t_customer c");
		sql2.append("  where u.user_id = ua.user_id and ua.cutomer_id = c.id and u.user_id = ? ");
		
		user = setUserCustomer(this.getDataBySql(sql2.toString(), new Object[]{userId}),user);
		
		String sql3 = "select p.id, p.position_package_name  from t_position_package p,  t_user_advertposition_package a where a.advertposition_package_id = p.id and a.user_id = ?";
		List list = this.getDataBySql(sql3, new Object[]{userId});
		String packageIds = "";
		String packageNames = "";
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			if(i== list.size()-1){
				packageIds += toInteger(obj[0]);
				packageNames += (String)obj[1];
			}else{
				packageIds += toInteger(obj[0])+",";
				packageNames += (String)obj[1]+",";
			}
		}
		user.setPositionIds(packageIds);
		user.setPositionNames(packageNames);
		return user;

	}
	
	private User getUserDetail(List<?> resultList) {
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			User u = new User();
			u.setUserId(toInteger(obj[0]));
			u.setUserName((String)(obj[1]));
			u.setLoginName((String)(obj[2]));
			u.setPassword((String)(obj[3]));
			u.setEmail((String)(obj[4]));
			u.setCreateTime((Date)obj[5]);
			u.setModifyTime((Date)obj[6]);
			u.setState(((Character)(obj[7])).toString());
			u.setDelFlag(toInteger(obj[8]));
			u.setRoleId(toInteger(obj[9]));
			u.setRoleName((String)(obj[10]));
			u.setRoleType(toInteger(obj[11]));
			return u;
		}
		return null;
	}
	
	private User setUserCustomer(List<?> resultList,User user) {
		String customerIds = "";
		String customerNames = "";
		int size = resultList.size();
		for (int i=0; i<size; i++) {
			Object[] obj = (Object[]) resultList.get(i);
			if(i==size-1){
				customerIds += toInteger(obj[0]);
				customerNames += (String)obj[1];
			}else{
				customerIds += toInteger(obj[0])+",";
				customerNames += (String)obj[1]+",";
			}
		}
		user.setCustomerIds(customerIds);
		user.setCustomerNames(customerNames);
		return user;
	}
	
	/**
	 * 更新用户删除标识
	 * @param userId
	 */
	public void deleteUser(Integer userId){
		String sql = "update t_user set delflag=1 where user_id=?";
		this.executeBySQL(sql, new Object[]{userId});
	}
	public String changePassword(Integer userId,String oldpassword,String newpassword)
	{
		StringBuffer sb = new StringBuffer("from User where delFlag=0 ");
		sb.append(" and userId= "+userId);
		sb.append(" and password= '"+oldpassword+"'");
		List<User> userList = this.list(sb.toString());
		if(userList!=null &&userList.size()>0){
			String sql = "update from User set password='"+newpassword+"'";
			sql =sql+ " where userId="+userId;
			this.executeByHQL(sql, (Object[]) null);
			return "修改成功";
		}else {
			return "旧密码错误";
		}
		//
	}
}

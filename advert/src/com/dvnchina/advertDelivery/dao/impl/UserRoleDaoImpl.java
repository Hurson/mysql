package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.dvnchina.advertDelivery.dao.UserRoleDao;
import com.dvnchina.advertDelivery.model.UserRole;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;

/**
 * 用户与角色数据操作类
 * @author Administrator
 *
 */
public class UserRoleDaoImpl extends BaseDaoImpl implements UserRoleDao{
	
	public void deleteUserRoleByUserId(Integer userId){
		String hql = "from UserRole as ur where ur.userID=?";
		List<UserRole> userRoles = list(hql,userId);
		for(UserRole q : userRoles){
			delete(q);
		}
	}

	@Override
	public void deleteBatchUserRoleByUserId(Integer userId) {
		String hql = "delete UserRole ur where ur.userID =:userId";
		this.getSession().createQuery(hql).setInteger("userId", userId).executeUpdate();
	}

	@Override
	public void deleteSingleUserRoleBinding(Integer userId, Integer roleId) {
		String hql = "delete UserRole ur where ur.userID =:userId and ur.roleID=:roleId";
		this.getSession().createQuery(hql).setInteger("userId", userId).setInteger("roleId",roleId).executeUpdate();
		
	}

	@Override
	public List<Role> getRoleListByUserId(Integer userId) {
		String sql = "select r.* from t_role r ,t_user_role ur where r.role_id= ur.role_id and ur.user_id ="+userId;
		Query query = this.getSession().createSQLQuery(sql).addEntity(Role.class);
		return query.list();
	}

}

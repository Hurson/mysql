package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.dvnchina.advertDelivery.dao.RoleColumnDao;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;

public class RoleColumnDaoImpl extends BaseDaoImpl implements RoleColumnDao {

	public void deleteBatchRoleColumnBinding(Integer roleId) {
		String hql = "delete RoleColumn rc where rc.roleId =:roleId";
		this.getSession().createQuery(hql).setInteger("roleId", roleId).executeUpdate();
		
	}
	
	public void deleteSingleRoleColumnBinding(Integer roleId, Integer columnId){
		String hql = "delete RoleColumn rc where rc.roleId =:roleId and rc.columnId =:columnId";
		this.getSession().createQuery(hql).setInteger("roleId", roleId).setInteger("columnId", columnId).executeUpdate();
		
	}

	@Override
	public List<Role> getRoleListByColumnId(Integer columnId) {
		String sql = "select r.* from t_role r ,t_columns_role cr where r.role_id= cr.role_id and cr.column_id ="+columnId;
		Query query = this.getSession().createSQLQuery(sql).addEntity(Role.class);
		return query.list();
	}

	@Override
	public List<Column> getColumnListByRoleId(Integer roleId) {
		String sql = "select c.* from t_columns c ,t_columns_role cr where c.column_id= cr.column_id and cr.role_id ="+roleId;
		Query query = this.getSession().createSQLQuery(sql).addEntity(Column.class);
		return query.list();
	}
	
}

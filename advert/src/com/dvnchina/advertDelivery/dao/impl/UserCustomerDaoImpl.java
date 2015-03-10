package com.dvnchina.advertDelivery.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;

import com.dvnchina.advertDelivery.dao.UserCustomerDao;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.UserPositionPackage;

/**
 * 用户和关系的接口的实现
 * @author Administrator
 *
 */
public class UserCustomerDaoImpl  extends BaseDaoImpl implements UserCustomerDao{

	@Override
	public boolean deleteUserCustomerBanding(Integer userId) {
		try {
			String hql = "delete UserCustomer uc where uc.userId =:userId";
			this.getSession().createQuery(hql).setInteger("userId", userId).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Customer> getUserOwnCustomer(Integer userId) {
		String sql = "select c.* from t_customer c,t_user_adcustomer uc where uc.cutomer_id =c.id and uc.user_id ="+userId;
		Query query = this.getSession().createSQLQuery(sql).addEntity(Customer.class);
		return query.list();
	}
	
	@Override
	public Integer getCustomerIds(Integer userId) {
		String sql = "select c.id from t_customer c,t_user_adcustomer uc where uc.cutomer_id =c.id and uc.user_id ="+userId;
		Query query = this.getSession().createSQLQuery(sql).addScalar("id",Hibernate.INTEGER);
		if(query.list() != null && query.list().size() > 0){
			return (Integer)query.list().get(0);
		}
		return null;
	}

	@Override
	public List<Integer> getPositionPackageIds(Integer userId, Integer roleType){
		
		String sql ="";
		Query query =null;
		List<Integer> list = new ArrayList<Integer>();
		
		if(roleType == 1){
			sql= "select t1.ad_id from t_contract_ad t1,t_contract t2, t_user_adcustomer t3 where t1.contract_id = t2.id and t2.custom_id = t3.cutomer_id and t1.contract_endtime > current_date and t3.user_id =" + userId;
			query = this.getSession().createSQLQuery(sql).addScalar("ad_id",Hibernate.INTEGER);
		}else if(roleType == 2){
			sql = "select c.advertposition_package_id from t_user_advertposition_package c where c.user_id ="+userId;
			query = this.getSession().createSQLQuery(sql).addScalar("advertposition_package_id",Hibernate.INTEGER);
		}
		
		list =query.list();
		return list;
		
	}
	
	@Override
	public void addUserAdvertPackage(List<UserPositionPackage> list){
		
			this.saveAll(list);
		
	}

	@Override
	public void deleteUserAdvertPackageBinding(Integer userId) {
		try {
			String hql = "delete UserPositionPackage uc where uc.userId =:userId";
			this.getSession().createQuery(hql).setInteger("userId", userId).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int getRoleTypeByUserId(Integer id) {
		int roleType = 0;
		String sql = "select type from t_role r, t_user_role ur where r.role_id = ur.role_id and ur.user_id = :userid";
		Query query = this.getSession().createSQLQuery(sql).addScalar("type",Hibernate.INTEGER);
		query.setInteger("userid", id);
		if(query.list() != null && query.list().size() > 0){
			roleType= (Integer) query.list().get(0);
		}
		return roleType;
	}
}

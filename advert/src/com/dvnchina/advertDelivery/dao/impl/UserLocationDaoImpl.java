package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.dvnchina.advertDelivery.dao.UserLocationDao;
import com.dvnchina.advertDelivery.model.Location;

public class UserLocationDaoImpl extends BaseDaoImpl implements UserLocationDao{

	@Override
	public boolean deleteUserLocationBanding(Integer userId) {
		
		try {
			String hql = "delete UserLocation ul where ul.userId =:userId";
			this.getSession().createQuery(hql).setInteger("userId", userId).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<Location> getUserOwnLocation(Integer userId){
		
		String sql = "select t.* from T_RELEASE_AREA t ,t_user_area ua where t.AREA_CODE = ua.release_areacode and ua.user_id ="+userId;
		Query query = this.getSession().createSQLQuery(sql).addEntity(Location.class);
		return query.list();
	}

}

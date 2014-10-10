package com.dvnchina.advertDelivery.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.dvnchina.advertDelivery.dao.LocationDao;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.model.Location;

@SuppressWarnings("unchecked")
public class LocationDaoImpl  extends BaseDaoImpl implements LocationDao{
	
	public List<Location> getAllLocation(){
		
		List list = null;
		try {
			list = list(" from Location ",null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<Location> getChildLocationList(String parentId) {
		String sql = "select * from T_RELEASE_AREA t start with AREA_CODE="+parentId+" connect by prior AREA_CODE = PARENT_CODE ";
		Query query = getSession().createSQLQuery(sql).addEntity(Location.class);
		List<Location> ll = query.list();
		return ll;
	}

	public List<Location> getColumnList(String parentId,int level) {
			List<Location> ll = new ArrayList<Location>();
			try {
				String sql = "select * from T_RELEASE_AREA t start with AREA_CODE="+parentId+" connect by prior AREA_CODE = PARENT_CODE and level <= "+level+" order by level asc";
				Query query = getSession().createSQLQuery(sql).addEntity(Column.class);
				ll = query.list();
				return ll;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
	}
	
}

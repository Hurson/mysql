package com.dvnchina.advertDelivery.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.dao.LocationDao;
import com.dvnchina.advertDelivery.model.Location;
import com.dvnchina.advertDelivery.service.LocationService;

public class LocationServiceImpl  implements LocationService{
	
	private LocationDao locationDao;
	
	@Override
	public List<Location> getAllLocation() {
		
		return locationDao.getAllLocation();
	}

	@Override
	public List<Location> getChildLocationList(String parentId) {
		
		return locationDao.getChildLocationList(parentId);
	}

	@Override
	public List<Location> getColumnList(String parentId, int level) {
		return locationDao.getColumnList(parentId, level);
	}
	
	
	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

}

package com.dvnchina.advertDelivery.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.bean.UserReleaseArea.UserReleaseAreaBean;
import com.dvnchina.advertDelivery.dao.UserAreaDao;
import com.dvnchina.advertDelivery.model.UserArea;
import com.dvnchina.advertDelivery.service.UserAreaService;

public class UserAreaServiceImpl implements UserAreaService {

    private static Logger logger = Logger.getLogger(UserAreaServiceImpl.class);
	
	private UserAreaDao userAreaDao;
	
	@Override
	public int deleteUserAreaById(Integer id) {
		int count = 0;
		if(id !=null){
			UserArea userArea = userAreaDao.getUserAreaById(id);
			userAreaDao.deleteUserAreaInfo(userArea);
			count = 1;
		}
		return count;
	}
	
	@Override
	public int getUserAreaCount(UserArea userArea, String state) {
		return userAreaDao.getUserAreaCount(userArea, state);
	}

	@Override
	public List<UserArea> listUserAreaMgr(UserArea userArea, int x, int y,String state) {
		return userAreaDao.listUserAreaMgr(userArea, x, y, state);
	}
	
	@Override
	public UserArea getUserAreaByUserId(Integer userId) {
		return userAreaDao.getUserAreaByUserId(userId);
	}

	@Override  
	public List<UserReleaseAreaBean>  getUserAndReleaseAreaList(Map map) {
		return userAreaDao.getUserAndReleaseAreaList(map);
	}

	public UserAreaDao getUserAreaDao() {
		return userAreaDao;
	}

	public void setUserAreaDao(UserAreaDao userAreaDao) {
		this.userAreaDao = userAreaDao;
	}

	@Override
	public List<UserReleaseAreaBean> getUserAndReleaseAreaList(Map map,int first, int pageSize) {
		return userAreaDao.getUserAndReleaseAreaList(map, first, pageSize);
	}

	@Override
	public int deleteUserAreaByUserId(Integer userId) {
		
		logger.debug("-------deleteUserAreaByUserId()  start -----------");
		
		int count = 0;
		
		if(userId != null){
			UserArea userArea = userAreaDao.getUserAreaByUserId(userId);
			userAreaDao.deleteUserAreaInfo(userArea);
			count = 1;
		}else{
			count = 0;
		}
		return count;
	}

}

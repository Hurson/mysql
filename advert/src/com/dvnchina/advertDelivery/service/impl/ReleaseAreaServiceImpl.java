package com.dvnchina.advertDelivery.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.dao.ReleaseAreaDao;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.service.ReleaseAreaService;

public class ReleaseAreaServiceImpl implements ReleaseAreaService {
	
	private Logger logger = Logger.getLogger(ReleaseAreaServiceImpl.class);
	
	private ReleaseAreaDao releaseAreaDao;

	@Override
	public List<ReleaseArea> listReleaseAreaMgr(ReleaseArea releaseArea, int x,int y, String state) {
		return releaseAreaDao.listReleaseAreaMgr(releaseArea, x, y, state);
	}
	
	@Override
	public int getReleaseAreaCount(ReleaseArea releaseArea, String state) {
		return releaseAreaDao.getReleaseAreaCount( releaseArea,state);
	}
	
	@Override
	public int deleteReleaseAreaById(Integer id) {
		
		int count = 0;
		
		if(StringUtils.isNotBlank(String.valueOf(id))){
			ReleaseArea releaseArea = releaseAreaDao.getReleaseAreaById(id);
			releaseAreaDao.deleteReleaseArea(releaseArea);
			count = 1;
		}
		return count;
	}
	
	@Override
	public ReleaseArea getReleaseAreaByreleaseAreaCode(String releaseAreaCode) {
		return releaseAreaDao.getReleaseAreaByreleaseAreaCode(releaseAreaCode);
	}
	
	public ReleaseAreaDao getReleaseAreaDao() {
		return releaseAreaDao;
	}

	public void setReleaseAreaDao(ReleaseAreaDao releaseAreaDao) {
		this.releaseAreaDao = releaseAreaDao;
	}

}

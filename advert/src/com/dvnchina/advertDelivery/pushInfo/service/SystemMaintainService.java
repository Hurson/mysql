package com.dvnchina.advertDelivery.pushInfo.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.pushInfo.bean.SystemMaintain;
import com.dvnchina.advertDelivery.pushInfo.dao.SystemMaintainDao;

public class SystemMaintainService {
	private Logger log = Logger.getLogger(SystemMaintainService.class);
	private SystemMaintainDao maintainDao;

	public SystemMaintain getAllMaintain() {
		return maintainDao.getAllMaintain();
	}

	public SystemMaintain findSystemMaintain() {
		return maintainDao.fin();

	}
	public List<ReleaseArea> getUserRelaArea(Integer userId){
		return maintainDao.getUserRelaArea(userId);
	}

	public void saveOrUpdate(SystemMaintain maintain)  {
		try {
			SystemMaintain systemMaintain = findSystemMaintain();
			if (systemMaintain == null) {
				systemMaintain = new SystemMaintain();
			}
			systemMaintain.setActionCode(maintain.getActionCode());
			systemMaintain.setActiveHour(maintain.getActiveHour());
			systemMaintain.setAreaCodes(maintain.getAreaCodes());
			systemMaintain.setDuration(maintain.getDuration());
			systemMaintain.setSendTime(maintain.getSendTime());
			maintainDao.saveOrUpdate(systemMaintain);
		} catch (Exception ex) {
			log.error("待机指令数据保存 error", ex);
		}
	}
	public SystemMaintainDao getMaintainDao() {
		return maintainDao;
	}

	public void setMaintainDao(SystemMaintainDao maintainDao) {
		this.maintainDao = maintainDao;
	}

	
}

package com.dvnchina.advertDelivery.task;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.service.CPSSyncService;

public class CPSSyncTask {
	private static Logger logger = Logger.getLogger(CPSSyncTask.class);
	
	private CPSSyncService cpsSyncService;
	
	
	public void cpsSync(){
		cpsSyncService.syncCPSInfo();
	}

	public CPSSyncService getCpsSyncService() {
		return cpsSyncService;
	}

	public void setCpsSyncService(CPSSyncService cpsSyncService) {
		this.cpsSyncService = cpsSyncService;
	}
	
}

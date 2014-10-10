package com.dvnchina.advertDelivery.task;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.service.VodSyncService;

/**
 * XML数据同步到数据库
 * */
public class VodSyncTask {
	private static Logger logger = Logger.getLogger(VodSyncTask.class);

	private VodSyncService vodSyncService;

	public void setVodSyncService(VodSyncService vodSyncService) {
		this.vodSyncService = vodSyncService;
	}
	public void vodSync(){
		vodSyncService.syncVodInfo();
	}

}

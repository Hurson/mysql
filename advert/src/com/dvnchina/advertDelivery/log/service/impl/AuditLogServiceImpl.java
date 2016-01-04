package com.dvnchina.advertDelivery.log.service.impl;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.log.bean.AuditLog;
import com.dvnchina.advertDelivery.log.dao.AuditLogDao;
import com.dvnchina.advertDelivery.log.service.AuditLogService;

public class AuditLogServiceImpl implements AuditLogService{
	
	private AuditLogDao auditLogDao;
	
	/**
	 * 查询审核日志记录
	 * @param log
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAuditLogList(AuditLog log, int pageNo, int pageSize){
		return auditLogDao.queryAuditLogList(log,pageNo,pageSize);
	}
	
	public PageBeanDB queryDAuditLogList(AuditLog log, int pageNo, int pageSize){
		return auditLogDao.queryDAuditLogList(log,pageNo,pageSize);
	}
	
	/**
	 * 保存审核日志
	 * @param log
	 */
	public void saveAuditLog(AuditLog log){
		auditLogDao.save(log);
	}

	public void setAuditLogDao(AuditLogDao auditLogDao) {
		this.auditLogDao = auditLogDao;
	}

}

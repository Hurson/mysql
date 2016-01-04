package com.dvnchina.advertDelivery.log.dao;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.log.bean.AuditLog;

public interface AuditLogDao extends BaseDao{
	
	/**
	 * 查询审核日志记录
	 * @param log
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAuditLogList(AuditLog log, int pageNo, int pageSize);
	/**
	 * 查询无线订单审核日志
	 * @param log
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryDAuditLogList(AuditLog log, int pageNo, int pageSize);

}

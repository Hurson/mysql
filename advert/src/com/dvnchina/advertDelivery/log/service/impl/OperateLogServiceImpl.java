package com.dvnchina.advertDelivery.log.service.impl;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.dao.OperateLogDao;
import com.dvnchina.advertDelivery.log.service.OperateLogService;

public class OperateLogServiceImpl implements OperateLogService{
	
	private OperateLogDao operateLogDao;
	/**
	 * 分页查询操作日志信息
	 * @param operateLog
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOperateLogList (OperateLog operateLog,int pageNo, int pageSize){
		return operateLogDao.queryOperateLogList(operateLog, pageNo, pageSize);
	}
	
	/**
	 * 保存操作日志
	 * @param log
	 */
	public void saveOperateLog(OperateLog log){
		operateLogDao.save(log);
	}
	
	public void setOperateLogDao(OperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}
}

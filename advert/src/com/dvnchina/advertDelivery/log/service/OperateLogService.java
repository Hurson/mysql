package com.dvnchina.advertDelivery.log.service;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.log.bean.OperateLog;

public interface OperateLogService {
	
	/**
	 * 分页查询操作日志信息
	 * @param operateLog
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOperateLogList(OperateLog operateLog,int pageNo, int pageSize);
	
	/**
	 * 保存操作日志
	 * @param log
	 */
	public void saveOperateLog(OperateLog log);

}

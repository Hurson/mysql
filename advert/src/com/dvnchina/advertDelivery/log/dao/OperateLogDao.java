package com.dvnchina.advertDelivery.log.dao;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.log.bean.OperateLog;

public interface OperateLogDao extends BaseDao{
	
	/**
	 * 分页查询操作日志信息
	 * @param operateLog
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOperateLogList (OperateLog operateLog,int pageNo, int pageSize);

}

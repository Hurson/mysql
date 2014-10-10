package com.dvnchina.advertDelivery.log.action;

import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;

public class OperateLogAction extends BaseActionSupport<Object>{

	private static final long serialVersionUID = -9151496320424353522L;
	private OperateLogService operateLogService = null;
	private PageBeanDB page = null;
	private OperateLog operateLog = null;
	
	/**
	 * 分页查询操作日志信息
	 */
	public String queryOperateLogList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = operateLogService.queryOperateLogList(operateLog,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public OperateLog getOperateLog() {
		return operateLog;
	}

	public void setOperateLog(OperateLog operateLog) {
		this.operateLog = operateLog;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	
	
}

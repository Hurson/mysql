package com.dvnchina.advertDelivery.log.action;

import java.util.List;

import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.log.bean.AuditLog;
import com.dvnchina.advertDelivery.log.service.AuditLogService;

public class AuditLogAction extends BaseAction{
	
	private static final long serialVersionUID = -8243462676305339382L;
	private AuditLogService auditLogService;
	private AuditLog auditLog;
	private List<AuditLog> auditLogList;
	
	/**
	 * 查询审核日志列表
	 * @return
	 */
//	public String queryAuditLogList(){
//		
//		try{
//			auditLogList = auditLogService.queryAuditLogList(auditLog);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 保存审核日志
	 * @return
	 */
	public String saveAuditLog(){
		try{
			auditLogService.saveAuditLog(auditLog);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public AuditLog getAuditLog() {
		return auditLog;
	}
	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}
	public void setAuditLogService(AuditLogService auditLogService) {
		this.auditLogService = auditLogService;
	}
	public List<AuditLog> getAuditLogList() {
		return auditLogList;
	}
	public void setAuditLogList(List<AuditLog> auditLogList) {
		this.auditLogList = auditLogList;
	}
}

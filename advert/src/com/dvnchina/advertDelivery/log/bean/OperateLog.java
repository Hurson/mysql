package com.dvnchina.advertDelivery.log.bean;

import java.io.Serializable;

public class OperateLog implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer id;
	/** 操作员ID */
	private Integer userId;
	/** 操作员名称 */
	private String userName;
	/** 模块名称 */
	private String moduleName;
	/** 操作类型 */
	private String operateType;
	/** 操作结果  0：成功  1：失败 */
	private Integer operateResult;
	/** 操作员IP */
	private String operateIP;
	/** 操作时间 */
	private String operateTime;
	/** 操作详情 */
	private String operateInfo;
	/** 开始时间 */
	private String beginTime;
	/** 结束时间 */
	private String endTime;
	
	public OperateLog(){
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Integer getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}
	public String getOperateIP() {
		return operateIP;
	}
	public void setOperateIP(String operateIP) {
		this.operateIP = operateIP;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateInfo() {
		return operateInfo;
	}
	public void setOperateInfo(String operateInfo) {
		this.operateInfo = operateInfo;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}

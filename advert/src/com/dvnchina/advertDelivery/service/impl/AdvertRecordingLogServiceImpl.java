package com.dvnchina.advertDelivery.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.constant.AdvertLogConstant;
import com.dvnchina.advertDelivery.service.AdvertRecordingLogService;
import com.dvnchina.advertDelivery.utils.Transform;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
@SuppressWarnings("unchecked")
public class AdvertRecordingLogServiceImpl implements Serializable,AdvertRecordingLogService{
	
	private static final long serialVersionUID = 3982977341000724693L;

	/**
	 * 操作时间
	 */
	private Date operationTime;
	/**
	 * 是否需要被解析标记 true 需要 false 不需要
	 */
	private String operationAnalystFlag;
	/**
	 * 执行相关操作模块
	 */
	private String operationModule;
	/**
	 * 执行相关操作用户
	 */
	private String operationUser;
	/**
	 * 执行相关操作类型 0 新增 1 删除 2 修改
	 */
	private String operationType;
	/**
	 * 操作状态 success 成功 failure 失败 exception 异常
	 */
	private String operationStatus;
	/**
	 * 操作前数据状态【删除和更新时使用】
	 */
	private List operationBefore;
	/**
	 * 操作后数据状态【新增和更新时使用】
	 */
	private List operationAfter;
	/**
	 * 操作的备注信息
	 */
	private String operationComment;
	/**
	 * 操作用户的IP地址
	 */
	private String operationIp;
	/**
	 * 包含初始化时的一些共有数据
	 */
	private Map availableData;
	
	public AdvertRecordingLogServiceImpl(String operationAnalystFlag,String operationModule,String operationUser,String operationType,String operationStatus,String operationComment,String operationIp){
		this.operationAnalystFlag=operationAnalystFlag;
		this.operationModule=operationModule;
		this.operationUser=operationUser;
		this.operationType=operationType;
		this.operationStatus=operationStatus;
		this.operationComment=operationComment;
		this.operationIp = operationIp;
		this.availableData = mixAvailableData(operationAnalystFlag,operationModule,operationUser,operationType,operationStatus,operationComment,operationIp);
	}
	
	public Map getAvailableData() {
		return availableData;
	}

	public void setAvailableData(Map availableData) {
		this.availableData = availableData;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public List getOperationBefore() {
		return operationBefore;
	}

	public void setOperationBefore(List operationBefore) {
		this.operationBefore = operationBefore;
	}

	public List getOperationAfter() {
		return operationAfter;
	}

	public void setOperationAfter(List operationAfter) {
		this.operationAfter = operationAfter;
	}
	
	public String getOperationAnalystFlag() {
		return operationAnalystFlag;
	}

	public void setOperationAnalystFlag(String operationAnalystFlag) {
		this.operationAnalystFlag = operationAnalystFlag;
	}

	public String getOperationModule() {
		return operationModule;
	}

	public void setOperationModule(String operationModule) {
		this.operationModule = operationModule;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public String getOperationType() {
		return operationType;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationComment() {
		return operationComment;
	}

	public void setOperationComment(String operationComment) {
		this.operationComment = operationComment;
	}
	
	/**
	 * 组装初始化时的一些现有数据
	 * @return
	 */
	public Map mixAvailableData(String operationAnalystFlag,String operationModule,String operationUser,String operationType,String operationStatus,String operationComment,String operationIp){
		Map operationResultMap = new LinkedHashMap();
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_DATE,Transform.CalendartoString(new Date()));
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_ANALYST_FLAG,operationAnalystFlag);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_IP,operationIp);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_MODULE,operationModule);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_USER,operationUser);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_TYPE,operationType);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_STATUS,operationStatus);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_COMMENT, operationComment);
		return operationResultMap;
	}

	/**
	 * 新增
	 * @return
	 */
	private String generateAddLog(List operationBefore,List operationAfter){
		Map operationResultMap = availableData;
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_BEFORE,"");
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_AFTER, operationAfter);
		return Obj2JsonUtil.map2json(operationResultMap);
	}
	
	/**
	 * 删除
	 */
	private String generateDeleteLog(List operationBefore,List operationAfter){
		Map operationResultMap = availableData;
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_BEFORE, operationBefore);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_AFTER,"");
		return Obj2JsonUtil.map2json(operationResultMap);
	}
	
	/**
	 * 修改
	 */
	private String generateUpdateLog(List operationBefore,List operationAfter){
		Map operationResultMap = availableData;
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_BEFORE, operationBefore);
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_AFTER, operationAfter);
		return Obj2JsonUtil.map2json(operationResultMap);
	}
	/**
	 * 查询
	 * @param operationBefore
	 * @param operationAfter
	 * @return
	 */
	private String generateQueryLog(List operationBefore,List operationAfter){
		Map operationResultMap = availableData;
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_BEFORE, "");
		operationResultMap.put(AdvertLogConstant.RECORD_LOG_OPERATION_AFTER, "");
		return Obj2JsonUtil.map2json(operationResultMap);
	}
	
	public String generateLog(List operationBefore,List operationAfter){
		String result = "";
		if(AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_ADD_FOR_ANALYST.equals(this.operationType)||AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_ADD_FOR_CONTENT.equals(this.operationType)){
			result=generateAddLog(operationBefore,operationAfter);
		}else if(AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_DELETE_FOR_ANALYST.equals(this.operationType)||AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_DELETE_FOR_CONTENT.equals(this.operationType)){
			result=generateDeleteLog(operationBefore,operationAfter);
		}else if(AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_UPDATE_FOR_ANALYST.equals(this.operationType)||AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_UPDATE_FOR_CONTENT.equals(this.operationType)){
			result=generateUpdateLog(operationBefore,operationAfter);
		}else if(AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_QUERY_FOR_ANALYST.equals(this.operationType)||AdvertLogConstant.RECORD_LOG_OPERATION_TYPE_QUERY_FOR_CONTENT.equals(this.operationType)){
			result=generateQueryLog(operationBefore,operationAfter);
		}
		return result;
	}
}

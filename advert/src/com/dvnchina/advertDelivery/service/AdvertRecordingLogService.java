package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

public interface AdvertRecordingLogService {
	/**
	 * 操作信息相关初始化工作
	 * @param operationAnalystFlag 是否需要被解析标记 true 需要 false 不需要
	 * @param operationModule 执行相关操作模块
	 * @param operationUser 执行相关操作用户
	 * @param operationType 执行相关操作类型 0 新增 1 删除 2 修改
	 * @param operationStatus 操作状态 success 成功 failure 失败 exception 异常
	 * @param operationComment 操作的备注信息
	 * @return
	 */
	public Map mixAvailableData(String operationAnalystFlag,String operationModule,String operationUser,String operationType,String operationStatus,String operationComment,String operationIp);
	/**
	 * 生成日志信息
	 * @param operationBefore 操作前数据状态【删除和更新时使用】
	 * @param operationAfter 操作后数据信息 【新增和更新时使用】
	 * @return
	 */
	public String generateLog(List operationBefore,List operationAfter);
}
